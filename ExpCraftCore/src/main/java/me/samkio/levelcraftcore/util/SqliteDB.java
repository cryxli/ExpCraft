package me.samkio.levelcraftcore.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import me.samkio.levelcraftcore.LevelCraftCore;

import org.bukkit.plugin.Plugin;

public class SqliteDB {
	public LevelCraftCore plugin;
	private Connection connection;
	private final Set<String> accountCache = new HashSet<String>();

	public SqliteDB(final LevelCraftCore instance) {
		plugin = instance;
	}

	public synchronized void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				plugin.logger.log(Level.SEVERE, "[LC]" + e);
			}
		}
	}

	public boolean contains(final String name) {
		if (!accountCache.contains(name)) {
			Connection conn = null;
			Statement st = null;
			try {
				conn = getConnection();
				st = conn.createStatement();
				ResultSet rs = st
						.executeQuery("SELECT name FROM ExperienceTable WHERE name=('"
								+ name + "')");
				while (rs.next()) {
					accountCache.add(name);
				}
				conn.commit();
			} catch (SQLException e) {
				plugin.logger.severe("[LC] Unable to get row database" + e);
			} finally {
				if (st != null) {
					try {
						st.close();
					} catch (SQLException e) {
					}
				}
			}
		}
		return accountCache.contains(name);
	}

	private Connection createConnection() {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection ret = DriverManager.getConnection("jdbc:sqlite:"
					+ plugin.getDataFolder() + "/Data/Experience.sqlite");
			ret.setAutoCommit(false);
			return ret;
		} catch (ClassNotFoundException e) {
			plugin.logger.log(Level.SEVERE, "[LC]" + e);
			return null;
		} catch (SQLException e) {
			plugin.logger.log(Level.SEVERE, "[LC]" + e);
		}
		return null;
	}

	public synchronized Connection getConnection() {
		if (connection == null) {
			connection = createConnection();
		}
		return connection;
	}

	public double getDouble(final String name, final String string) {
		Connection conn = null;
		Statement st = null;
		double level = 0;
		try {
			conn = getConnection();
			st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT " + string
					+ "Exp FROM ExperienceTable WHERE name=('" + name + "')");
			while (rs.next()) {
				level = rs.getDouble(string + "Exp");
			}
			conn.commit();
			return level;
		} catch (SQLException e) {
			plugin.logger.severe("[LC] Unable to get row database" + e);
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
				}
			}
		}
		return level;

	}

	public String getPlayerAtPos(final String string, final int i) {
		Connection conn = null;
		Statement st = null;
		int rank = 0;
		String p = "None";
		try {
			conn = getConnection();
			st = conn.createStatement();
			ResultSet rs = st
					.executeQuery("SELECT name FROM ExperienceTable ORDER BY "
							+ string + "Exp DESC");
			while (rs.next()) {
				rank++;
				if (rank == i) {
					p = rs.getString("name");
				}
			}
			conn.commit();
		} catch (SQLException e) {
			plugin.logger.severe("[LC] Unable to get row database" + e);
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
				}
			}
		}
		return p;
	}

	public int getPos(final String name, final String string) {
		Connection conn = null;
		Statement st = null;
		int rank = 0;
		try {
			conn = getConnection();
			st = conn.createStatement();
			ResultSet rs = st
					.executeQuery("SELECT name FROM ExperienceTable ORDER BY "
							+ string + "Exp DESC");
			while (rs.next()) {
				rank++;
				if (rs.getString("name").equalsIgnoreCase(name)) {
					break;
				}
				;
			}
			conn.commit();
		} catch (SQLException e) {
			plugin.logger.severe("[LC] Unable to get row database" + e);
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
				}
			}
		}
		return rank;
	}

	public void newP(final String namer) {
		Connection conn = null;
		Statement st = null;
		try {
			conn = getConnection();
			st = conn.createStatement();
			st.executeUpdate("INSERT INTO ExperienceTable (name) VALUES ('"
					+ namer + "')");
			conn.commit();
		} catch (SQLException e) {
			plugin.logger.severe("[LC] Unable to add row to database " + e);
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	public void prepare() {
		Connection conn = null;
		Statement st = null;
		try {
			conn = plugin.SqliteDB.getConnection();
			st = conn.createStatement();
			st.executeUpdate("CREATE TABLE IF NOT EXISTS ExperienceTable (id INTEGER PRIMARY KEY, name VARCHAR(80) NOT NULL);");

			for (Plugin p : plugin.LevelNames.keySet()) {
				ResultSet rs = st
						.executeQuery("SELECT * FROM ExperienceTable;");
				ResultSetMetaData rsmd = rs.getMetaData();
				int ColumnCount = rsmd.getColumnCount();
				for (int i = 1; i <= ColumnCount; i++) {
					String s = rsmd.getColumnName(i);
					// plugin.logger.info(s);
					if (s.equals(plugin.LevelNames.get(p) + "Exp")) {
						break;
					}
					if (i == ColumnCount) {
						st.executeUpdate("ALTER TABLE ExperienceTable ADD "
								+ plugin.LevelNames.get(p)
								+ "Exp DOUBLE(25) NOT NULL DEFAULT 0;");
					}

				}
			}

			conn.commit();
		} catch (SQLException e) {
			plugin.logger.log(Level.SEVERE,
					"[LC] Cannot connect to Sqlite Database");
			plugin.logger.log(Level.SEVERE, "[LC] " + e);
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	public boolean purge() {
		return false;
	}

	public void update(final String name, final String string, final double i) {
		Connection conn = null;
		Statement st = null;
		try {
			conn = getConnection();
			st = conn.createStatement();
			st.executeUpdate("UPDATE ExperienceTable set " + string + "Exp = '"
					+ i + "' WHERE name='" + name + "'");
			conn.commit();
		} catch (SQLException e) {
			plugin.logger.severe("[LC] Unable to update row database" + e);
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
				}
			}
		}
	}

}
