package me.samkio.levelcraftcore.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import org.bukkit.plugin.Plugin;

import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;



import me.samkio.levelcraftcore.LevelCraftCore;

public class MySqlDB {
	public LevelCraftCore plugin;
	public static Connection conn = null;
	public static Statement st = null;

	public MySqlDB(LevelCraftCore instance) {
		plugin = instance;
		createConnection();
	}

	public boolean createConnection() {
		if(conn==null){
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				plugin.logger.log(Level.SEVERE, "[LC]" + e);
				return false;
			}
			try {
				conn = DriverManager.getConnection("jdbc:mysql://" + plugin.MySqlDir
						+ "", "" + plugin.MySqlUser + "", "" + plugin.MySqlPass + "");
			} catch (SQLException e) {
				plugin.logger.log(Level.SEVERE, "[LC]" + e);
				return false;
			}
		}
		if(st==null){
			try {
				st = (Statement) conn.createStatement();
			} catch (SQLException e) {
				plugin.logger.log(Level.SEVERE, "[LC]" + e);
				return false;
			}
		}
		return true;
	}
	
	public void closeConnection(){
		if(st==null)
			try {
				st.close();
				st = null;
			} catch (SQLException e) {
				plugin.logger.log(Level.SEVERE, "[LC]" + e);
			}		
		if(conn==null)
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				plugin.logger.log(Level.SEVERE, "[LC]" + e);
			}
	}
	
	public void prepare() {
		if(!createConnection()) return;
		try {
			st.executeUpdate("CREATE TABLE IF NOT EXISTS ExperienceTable (`id` INT( 11 ) NOT NULL AUTO_INCREMENT, `name` VARCHAR(30) NOT NULL,PRIMARY KEY ( `id` ),UNIQUE KEY(`name`)) ENGINE = MYISAM;");	

			//One time is sufisant
			ResultSet rs = st.executeQuery("SELECT * FROM `ExperienceTable`;");
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			
			for (Plugin p : plugin.LevelNames.keySet()) {
			int ColumnCount = rsmd.getColumnCount();
				for (int i = 1; i <= ColumnCount; i++) {
					String s = rsmd.getColumnName(i);
					if (s.equals(plugin.LevelNames.get(p) + "Exp"))
						break;
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
		}
	}

	public boolean contains(String name) {
		if(!createConnection()) return false;
			boolean isTrue = false;
			try {

				ResultSet rs = st.executeQuery("SELECT name FROM ExperienceTable WHERE name=('"+ name + "') LIMIT 1");
				while (rs.next()) {
					isTrue = true;
				}
			} catch (SQLException e) {
				plugin.logger.log(Level.SEVERE, "[LC]" + e);
			}
			return isTrue;
		
	}

	public void newP(String namer) {
		if(!createConnection()) return;
			try {
				st.executeUpdate("INSERT IGNORE INTO ExperienceTable (name) VALUES ('"+namer+"')");
			} catch (SQLException ex) {
				plugin.logger.log(Level.SEVERE, "[LC]" + ex);
				return;
			}
	}

	public double getDouble(String name, String string) {
		if(!createConnection()) return 0 ;
			double exp = 0;
			try {
				ResultSet rs = st.executeQuery("SELECT " + string
						+ "Exp FROM ExperienceTable WHERE name=('" + name + "') LIMIT 1");
				while (rs.next()) {
					exp = rs.getDouble(string+"Exp");
				}
			} catch (SQLException e) {
				plugin.logger.log(Level.SEVERE, "[LC]" + e);
			}
			return exp;

	}

	public void update(String name, String string, double i) {
		if(!createConnection()) return;

			try {

				st.executeUpdate("UPDATE LOW_PRIORITY ExperienceTable set "+string+"Exp = '"+i+"' WHERE name='"+name+"' LIMIT 1");
			} catch (SQLException ex) {
				plugin.logger.log(Level.SEVERE, "[LC]" + ex);
				return;
			}
	}
	public int getPos(String name,String string){
		if(!createConnection()) return 0;
		int rank = 0;
		try {
			
			ResultSet rs = st.executeQuery("SELECT COUNT(`id`) AS `count` FROM `ExperienceTable` WHERE `"+string+"Exp`>=(SELECT `"+string+"Exp` FROM `ExperienceTable` WHERE `name`=('"+name+"') LIMIT 1);");

			if (rs.next()) {
				rank = rs.getInt("count");
			}

		} catch (SQLException e) {
			plugin.logger.log(Level.SEVERE, "[LC]" + e);
		}
		return rank;
	}

	public String getPlayerAtPos(String string, int i) {
		if(!createConnection()) return "0";
		String p = "None";
		try {
			ResultSet rs = st.executeQuery("SELECT name FROM ExperienceTable ORDER BY "+string+"Exp DESC LIMIT "+i+", 1");
			
			if (rs.next()) {
				p = rs.getString("name");
			}
		} catch (SQLException e) {
			plugin.logger.log(Level.SEVERE, "[LC]" + e);
		}
		return p;
	}

}
