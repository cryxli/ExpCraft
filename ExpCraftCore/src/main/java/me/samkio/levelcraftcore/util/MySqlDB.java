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

public class MySqlDB {
	public LevelCraftCore plugin;
	private final Set<String> accountCache = new HashSet<String>();

	public MySqlDB(final LevelCraftCore instance) {
		plugin = instance;
	}

	public boolean contains(final String name) {
		if (!accountCache.contains(name)) {
			Connection conn = null;
			Statement st = null;
			try {

				conn = createConnection();

				st = conn.createStatement();
				ResultSet rs = st
						.executeQuery("SELECT name FROM ExperienceTable WHERE name=('"
								+ name + "')");
				while (rs.next()) {
					accountCache.add(name);
				}
			} catch (SQLException e) {
				plugin.logger.log(Level.SEVERE, "[LC]" + e);
			} finally {
				try {
					if (st != null) {
						st.close();
					}
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException ex) {
					plugin.logger.log(Level.SEVERE, "[LC]" + ex);
				}
			}
		}
		return accountCache.contains(name);

	}

	public Connection createConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			plugin.logger.log(Level.SEVERE, "[LC]" + e);
		}
		try {
			return DriverManager.getConnection("jdbc:mysql://"
					+ plugin.MySqlDir, plugin.MySqlUser, plugin.MySqlPass);
		} catch (SQLException e) {
			plugin.logger.log(Level.SEVERE, "[LC]" + e);
		}
		return null;
	}

	public double getDouble(final String name, final String string) {
		Connection conn = null;
		Statement st = null;
		double exp = 0;
		try {

			conn = createConnection();

			st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT " + string
					+ "Exp FROM ExperienceTable WHERE name='" + name + "'");
			while (rs.next()) {
				exp = rs.getDouble(string + "Exp");
			}
		} catch (SQLException e) {
			plugin.logger.log(Level.SEVERE, "[LC]" + e);
		} finally {
			try {
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				plugin.logger.log(Level.SEVERE, "[LC]" + ex);
			}
		}
		return exp;

	}

	public String getPlayerAtPos(final String string, final int i) {
		Connection conn = null;
		Statement st = null;
		int rank = 0;
		String p = "None";
		try {

			conn = createConnection();

			st = conn.createStatement();
			ResultSet rs = st
					.executeQuery("SELECT name FROM ExperienceTable ORDER BY "
							+ string + "Exp DESC");

			while (rs.next()) {
				rank++;
				if (rank == i) {
					p = rs.getString("name");
					break;
				}

			}
		} catch (SQLException e) {
			plugin.logger.log(Level.SEVERE, "[LC]" + e);
		} finally {
			try {
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				plugin.logger.log(Level.SEVERE, "[LC]" + ex);
			}
		}
		return p;
	}

	public int getPos(final String name, final String string) {
		Connection conn = null;
		Statement st = null;
		int rank = 0;
		try {

			conn = createConnection();

			st = conn.createStatement();
			ResultSet rs = st
					.executeQuery("SELECT name FROM ExperienceTable ORDER BY "
							+ string + "Exp DESC");

			while (rs.next()) {
				rank++;
				if (rs.getString("name").equalsIgnoreCase(name)) {
					break;
				}
			}

		} catch (SQLException e) {
			plugin.logger.log(Level.SEVERE, "[LC]" + e);
		} finally {
			try {
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				plugin.logger.log(Level.SEVERE, "[LC]" + ex);
			}
		}
		return rank;
	}

	public void newP(final String namer) {
		Connection conn = null;
		Statement st = null;
		try {
			conn = createConnection();
			st = conn.createStatement();
			st.executeUpdate("INSERT INTO ExperienceTable (name) VALUES ('"
					+ namer + "')");
		} catch (SQLException ex) {
			plugin.logger.log(Level.SEVERE, "[LC]" + ex);
			return;
		} finally {
			try {
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				plugin.logger.log(Level.SEVERE, "[LC]" + ex);
			}
		}
	}

	public void prepare() {
		Connection conn = null;
		Statement st = null;
		try {
			conn = createConnection();
			st = conn.createStatement();
			st.executeUpdate("CREATE TABLE IF NOT EXISTS ExperienceTable (`id` INT( 255 ) NOT NULL AUTO_INCREMENT, `name` TEXT NOT NULL,PRIMARY KEY ( `id` )) ENGINE = MYISAM;");

			for (Plugin p : plugin.LevelNames.keySet()) {
				ResultSet rs = st
						.executeQuery("SELECT * FROM `ExperienceTable`;");
				ResultSetMetaData rsmd = rs.getMetaData();
				int ColumnCount = rsmd.getColumnCount();
				for (int i = 1; i <= ColumnCount; i++) {
					String s = rsmd.getColumnName(i);
					if (s.equals(plugin.LevelNames.get(p) + "Exp")) {
						break;
					}
					if (i == ColumnCount) {
						st.executeUpdate("ALTER TABLE ExperienceTable ADD "
								+ plugin.LevelNames.get(p)
								+ "Exp DOUBLE(10,2) NOT NULL DEFAULT 0;");
					}

				}
			}

		} catch (SQLException ex) {
			plugin.logger.log(Level.SEVERE, "[LC]" + ex);
			return;
		} finally {
			try {
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				plugin.logger.log(Level.SEVERE, "[LC]" + ex);
			}
		}
	}

	public void update(final String name, final String string, final double i) {
		Connection conn = null;
		Statement st = null;

		try {
			conn = createConnection();
			st = conn.createStatement();

			st.executeUpdate("UPDATE ExperienceTable set " + string + "Exp = '"
					+ i + "' WHERE name='" + name + "'");
		} catch (SQLException ex) {
			plugin.logger.log(Level.SEVERE, "[LC]" + ex);
			return;
		} finally {
			try {
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				plugin.logger.log(Level.SEVERE, "[LC]" + ex);
			}
		}
	}

}
