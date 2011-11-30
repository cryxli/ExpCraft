package li.cryx.expcraft.persist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import li.cryx.expcraft.module.ExpCraftModule;

import org.bukkit.entity.Player;

public class PersistenceDatabase extends AbstractPersistenceManager {

	private static final Logger LOG = Logger.getLogger("ExpCraftCore");

	private static final String CREATE_TABLE = //
	"CREATE TABLE ExpCraftTable (" //
			+ " id BIGINT," //
			+ " player VARCHAR(100)," //
			+ " module VARCHAR(5)," //
			+ " exp NUMERIC(8,2)," //
			+ " PRIMARY KEY (id)" //
			+ " )";

	// order: module, player
	private static final String INSERT = "INSERT INTO ExpCraftTable (id,module,player,exp) SELECT COALESCE(MAX(id)+1,1),''{0}'',''{1}'',0 FROM ExpCraftTable";

	// order: module, player
	private static final String SELECT = "SELECT exp FROM ExpCraftTable WHERE module=''{0}'' AND player=''{1}''";

	// order: module, player, exp
	private static final String UPDATE = "UPDATE ExpCraftTable SET exp={2,number,0.00} WHERE module=''{0}'' AND player=''{1}''";

	private static final String KEEP_ALIVE = "SELECT COUNT(*) FROM ExpCraftTable";

	public static void main(final String[] args) {
		System.out.println(CREATE_TABLE);
		System.out.println(MessageFormat.format(SELECT, "Fm", "ups"));
		System.out.println(MessageFormat.format(INSERT, "Fm", "ups", 19.5, 1));
		System.out.println(MessageFormat.format(UPDATE, "Fm", "ups", 22.0));
		System.out.println(KEEP_ALIVE);
	}

	private String driverClass;

	private Connection conn;

	private String dbUrl;

	private String dbUser;

	private String dbPassword;

	private Statement stmt;

	private long keepAliveInterval = 1800 * 1000;

	@Override
	protected void finalize() throws Throwable {
		flush();
		super.finalize();
	}

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
			ResultSet set = getStmt().executeQuery(
					MessageFormat.format(SELECT, module.getAbbr(), player
							.getName().toLowerCase()));

			if (set.next()) {
				// return found value
				exp = set.getDouble(1);
			} else {
				// on NO-ROW-FOUND -> execute INSERT
				getStmt().executeUpdate(
						MessageFormat.format(INSERT, module.getAbbr(), player
								.getName().toLowerCase()));
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
		String sql = MessageFormat.format(UPDATE, module.getAbbr(), player
				.getName().toLowerCase(), exp);
		try {
			getStmt().executeUpdate(sql);
		} catch (SQLException e) {
			LOG.info(sql);
			LOG.log(Level.SEVERE, "[EC] Cannot update expereience", e);
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
