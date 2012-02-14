package li.cryx.expcraft.persist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import li.cryx.expcraft.module.ExpCraftModule;

import org.bukkit.entity.Player;

/**
 * This class implements a storage mechanism using databases. The implementation
 * should be so generic that it should work with any relational database that
 * implements the SQL standard.
 * 
 * @author cryxli
 */
public class PersistenceDatabase extends AbstractPersistenceManager {

	private static final Logger LOG = Logger.getLogger("ExpCraftCore");

	/** SQL query to create the table. */
	private static final String CREATE_TABLE = //
	"CREATE TABLE ExpCraftTable (" //
			+ " id BIGINT," //
			+ " player VARCHAR(100)," //
			+ " module VARCHAR(5)," //
			+ " exp NUMERIC(8,2)," //
			+ " PRIMARY KEY (id)" //
			+ " )";

	/**
	 * SQL query to insert a new player into the table.
	 * 
	 * <p>
	 * Arguments {@link ExpCraftModule#getAbbr()}, <code>Player.getName()</code>
	 * </p>
	 */
	private static final MessageFormat INSERT = new MessageFormat(
			"INSERT INTO ExpCraftTable (id,module,player,exp) SELECT COALESCE(MAX(id)+1,1),''{0}'',''{1}'',0 FROM ExpCraftTable");

	/**
	 * SQL query to retrieve exp of a player and module
	 * 
	 * <p>
	 * Arguments {@link ExpCraftModule#getAbbr()}, <code>Player.getName()</code>
	 * </p>
	 */
	private static final MessageFormat SELECT = new MessageFormat(
			"SELECT exp FROM ExpCraftTable WHERE module=''{0}'' AND player=''{1}''");

	/**
	 * SQL query to update exp of a player and module
	 * 
	 * <p>
	 * Arguments {@link ExpCraftModule#getAbbr()}, <code>Player.getName()</code>
	 * , experience
	 * </p>
	 */
	private static final MessageFormat UPDATE = new MessageFormat(
			"UPDATE ExpCraftTable SET exp={2,number,0.00} WHERE module=''{0}'' AND player=''{1}''",
			Locale.US);

	/** SQL query to execute to keep the connection to the database alive. */
	private static final String KEEP_ALIVE = "SELECT COUNT(*) FROM ExpCraftTable";

	/** The driver class for the used JDBC driver. */
	private String driverClass;

	/** The open connection to the database. */
	private Connection conn;

	/** The connection URL to the database. */
	private String dbUrl;

	/** The database user. */
	private String dbUser;

	/** The password for the database user. */
	private String dbPassword;

	/** The statement for a DB connection. */
	private Statement stmt;

	/** Keep alive interval. Default every half an hour. */
	private long keepAliveInterval = 1800 * 1000;

	/** Before this class unloads, try to shut down the connection correctly. */
	@Override
	protected void finalize() throws Throwable {
		flush();
		super.finalize();
	}

	/** Shut down the connection to the database. */
	@Override
	public void flush() {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
			}
			stmt = null;
		}
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * Get the connection to the database, or create one. When a new connection
	 * is crate, try to create the ExtCraft table.
	 */
	private Connection getConn() {
		if (conn == null) {
			try {
				Class.forName(driverClass);
			} catch (ClassNotFoundException e) {
				LOG.log(Level.SEVERE, "[EC] " + e.getMessage(), e);
			}

			try {
				// create a new database connection
				conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				conn.setAutoCommit(true);

				try {
					// create table for ExpCraft
					Statement stmt = conn.createStatement();
					stmt.execute(CREATE_TABLE);
					stmt.close();
				} catch (SQLException ee) {
				}

				if (keepAliveInterval > 0) {
					// start ping to keep idle connection open
					startKeepAlive();
				}
			} catch (SQLException e) {
				LOG.log(Level.SEVERE, "[EC] Unable to establish connection", e);
			}
		}
		return conn;
	}

	@Override
	public double getExp(final ExpCraftModule module, final Player player) {
		double exp = 0;
		try {
			// execute SELECT
			Object[] objects = new Object[] { module.getAbbr(), //
					player.getName().toLowerCase() //
			};
			String sql = SELECT.format(objects);
			ResultSet set = getStmt().executeQuery(sql);

			if (set.next()) {
				// return found value
				exp = set.getDouble(1);
			} else {
				// on NO-ROW-FOUND -> execute INSERT
				getStmt().executeUpdate(INSERT.format(objects));
			}
			set.close();
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "[EC] Unable to read experience", e);
		}
		return exp;
	}

	private Statement getStmt() {
		if (stmt == null) {
			try {
				stmt = getConn().createStatement();
			} catch (SQLException e) {
				LOG.log(Level.SEVERE, "[EC] Cannot execute statment", e);
			}
		}
		return stmt;
	}

	public void setDatabase(final String database) {
		if ("mysql".equalsIgnoreCase(database)) {
			setDriverClass("com.mysql.jdbc.Driver");
		} else {
			setDriverClass("org.sqlite.JDBC");
		}
	}

	public void setDbPassword(final String password) {
		dbPassword = password;
	}

	public void setDbUrl(final String url) {
		if (driverClass != null && driverClass.contains("mysql")) {
			// MySQL
			dbUrl = "jdbc:mysql://" + url;
		} else {
			// SQLite
			dbUrl = "jdbc:sqlite:" + url;
		}
	}

	public void setDbUser(final String user) {
		dbUser = user;
	}

	public void setDriverClass(final String driverClass) {
		this.driverClass = driverClass;
	}

	@Override
	public void setExp(final ExpCraftModule module, final Player player,
			final double exp) {
		// execute UPDATE
		Object[] objects = new Object[] { module.getAbbr(), //
				player.getName().toLowerCase(), //
				exp //
		};
		String sql = UPDATE.format(objects);
		try {
			getStmt().executeUpdate(sql);
		} catch (SQLException e) {
			LOG.info(sql);
			LOG.log(Level.SEVERE, "[EC] Cannot update experience", e);
		}
	}

	public void setKeepAliveInterval(final int seconds) {
		keepAliveInterval = 1000 * seconds;
	}

	private void startKeepAlive() {
		Thread keepAlive = new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(keepAliveInterval);
					} catch (InterruptedException e) {
					}

					try {
						// send ping to DB
						getStmt().execute(KEEP_ALIVE);
					} catch (SQLException e) {
						LOG.log(Level.INFO, "[EC] Ping to database failed", e);
					}
				}
			}
		};
		keepAlive.setDaemon(true);
		keepAlive.start();
	}
}
