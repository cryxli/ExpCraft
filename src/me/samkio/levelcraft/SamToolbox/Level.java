package me.samkio.levelcraft.SamToolbox;


import me.samkio.levelcraft.Levelcraft;


import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
public class Level {
	public  Levelcraft plugin;
	public Level(Levelcraft instance) {
		plugin = instance;
	}

	public  double getExp(CommandSender sender, String s) {
		if (plugin.Settings.database.equalsIgnoreCase("flatfile")) {
			if (s.equalsIgnoreCase("w")) {
				Double exp = plugin.LevelFunctions.getExp(sender, plugin.WCExpFile);
				return exp;
			}
			if (s.equalsIgnoreCase("r")) {
				Double exp = plugin.LevelFunctions.getExp(sender, plugin.RangeExpFile);
				return exp;
			}
			if (s.equalsIgnoreCase("s")) {
				Double exp = plugin.LevelFunctions.getExp(sender, plugin.SlayExpFile);
				return exp;
			}
			if (s.equalsIgnoreCase("m")) {
				Double exp = plugin.LevelFunctions.getExp(sender, plugin.MiExpFile);
				return exp;
			}
			if (s.equalsIgnoreCase("c")) {
				Double exp = plugin.LevelFunctions.getExp(sender, plugin.FisticuffsExpFile);
				return exp;
			}
			if (s.equalsIgnoreCase("f")) {
				Double exp = plugin.LevelFunctions.getExp(sender, plugin.ForgeExpFile);
				return exp;
			}
			if (s.equalsIgnoreCase("a")) {
				Double exp = plugin.LevelFunctions.getExp(sender, plugin.ArcherExpFile);
				return exp;
			}
			if (s.equalsIgnoreCase("d")) {
				Double exp = plugin.LevelFunctions.getExp(sender, plugin.DiggingExpFile);
				return exp;
			}
		}

		if (plugin.Settings.database.equalsIgnoreCase("mysql")) {
			if (s.equalsIgnoreCase("w")) {
				Double exp = plugin.DataMySql.getExp(sender, "WoodcuttingExp");
				return exp;
			}
			if (s.equalsIgnoreCase("r")) {
				Double exp = plugin.DataMySql.getExp(sender, "RangingExp");
				return exp;
			}
			if (s.equalsIgnoreCase("s")) {
				Double exp = plugin.DataMySql.getExp(sender, "SlayingExp");
				return exp;
			}
			if (s.equalsIgnoreCase("m")) {
				Double exp = plugin.DataMySql.getExp(sender, "MiningExp");
				return exp;
			}
			if (s.equalsIgnoreCase("c")) {
				Double exp = plugin.DataMySql.getExp(sender, "FisticuffsExp");
				return exp;
			}
			if (s.equalsIgnoreCase("f")) {
				Double exp = plugin.DataMySql.getExp(sender, "ForgeExp");
				return exp;
			}
			if (s.equalsIgnoreCase("a")) {
				Double exp = plugin.DataMySql.getExp(sender, "ArcheryExp");
				return exp;
			}
			if (s.equalsIgnoreCase("d")) {
				Double exp = plugin.DataMySql.getExp(sender, "DiggingExp");
				return exp;
			}
			return 0;
		}
		if (plugin.Settings.database.equalsIgnoreCase("sqlite")) {
			if (s.equalsIgnoreCase("w")) {
				Double exp = plugin.DataSqlite.getExp(sender, "WoodcuttingExp");
				return exp;
			}
			if (s.equalsIgnoreCase("r")) {
				Double exp = plugin.DataSqlite.getExp(sender, "RangingExp");
				return exp;
			}
			if (s.equalsIgnoreCase("s")) {
				Double exp = plugin.DataSqlite.getExp(sender, "SlayingExp");
				return exp;
			}
			if (s.equalsIgnoreCase("m")) {
				Double exp = plugin.DataSqlite.getExp(sender, "MiningExp");
				return exp;
			}
			if (s.equalsIgnoreCase("c")) {
				Double exp = plugin.DataSqlite.getExp(sender, "FisticuffsExp");
				return exp;
			}
			if (s.equalsIgnoreCase("f")) {
				Double exp = plugin.DataSqlite.getExp(sender, "ForgeExp");
				return exp;
			}
			if (s.equalsIgnoreCase("a")) {
				Double exp = plugin.DataSqlite.getExp(sender, "ArcheryExp");
				return exp;
			}
			if (s.equalsIgnoreCase("d")) {
				Double exp = plugin.DataSqlite.getExp(sender, "DiggingExp");
				return exp;
			}
		}
		return 0;
	}

	public  int getLevel(CommandSender sender, String s) {
		if (plugin.Settings.database.equalsIgnoreCase("flatfile")) {
			if (s.equalsIgnoreCase("w")) {
				int level = plugin.LevelFunctions.getLevel(sender, plugin.WCExpFile);
				return level;
			}
			if (s.equalsIgnoreCase("r")) {
				int level = plugin.LevelFunctions.getLevel(sender, plugin.RangeExpFile);
				return level;
			}
			if (s.equalsIgnoreCase("s")) {
				int level = plugin.LevelFunctions.getLevel(sender, plugin.SlayExpFile);
				return level;
			}
			if (s.equalsIgnoreCase("m")) {
				int level = plugin.LevelFunctions.getLevel(sender, plugin.MiExpFile);
				return level;
			}
			if (s.equalsIgnoreCase("c")) {
				int level = plugin.LevelFunctions.getLevel(sender, plugin.FisticuffsExpFile);
				return level;
			}if (s.equalsIgnoreCase("f")) {
				int level = plugin.LevelFunctions.getLevel(sender, plugin.ForgeExpFile);
				return level;
			}
			if (s.equalsIgnoreCase("a")) {
				int level = plugin.LevelFunctions.getLevel(sender, plugin.ArcherExpFile);
				return level;
			}
			if (s.equalsIgnoreCase("d")) {
				int level = plugin.LevelFunctions.getLevel(sender, plugin.DiggingExpFile);
				return level;
			}
		}

		if (plugin.Settings.database.equalsIgnoreCase("mysql")) {
			if (s.equalsIgnoreCase("w")) {
				int level = plugin.DataMySql.getLevel(sender, "WoodcuttingExp");
				return level;
			}
			if (s.equalsIgnoreCase("r")) {
				int level = plugin.DataMySql.getLevel(sender, "RangingExp");
				return level;
			}
			if (s.equalsIgnoreCase("s")) {
				int level = plugin.DataMySql.getLevel(sender, "SlayingExp");
				return level;
			}
			if (s.equalsIgnoreCase("m")) {
				int level = plugin.DataMySql.getLevel(sender, "MiningExp");
				return level;
			}
			if (s.equalsIgnoreCase("c")) {
				int level = plugin.DataMySql.getLevel(sender, "FisticuffsExp");
				return level;
			}
			if (s.equalsIgnoreCase("f")) {
				int level = plugin.DataMySql.getLevel(sender, "ForgeExp");
				return level;
			}
			if (s.equalsIgnoreCase("a")) {
				int level = plugin.DataMySql.getLevel(sender, "ArcheryExp");
				return level;
			}
			if (s.equalsIgnoreCase("d")) {
				int level = plugin.DataMySql.getLevel(sender, "DiggingExp");
				return level;
			}
			return 0;
		}
		if (plugin.Settings.database.equalsIgnoreCase("sqlite")) {
			if (s.equalsIgnoreCase("w")) {
				int level = plugin.DataSqlite.getLevel(sender, "WoodcuttingExp");
				return level;
			}
			if (s.equalsIgnoreCase("r")) {
				int level = plugin.DataSqlite.getLevel(sender, "RangingExp");
				return level;
			}
			if (s.equalsIgnoreCase("s")) {
				int level = plugin.DataSqlite.getLevel(sender, "SlayingExp");
				return level;
			}
			if (s.equalsIgnoreCase("m")) {
				int level = plugin.DataSqlite.getLevel(sender, "MiningExp");
				return level;
			}
			if (s.equalsIgnoreCase("c")) {
				int level = plugin.DataSqlite.getLevel(sender, "FisticuffsExp");
				return level;
			}
			if (s.equalsIgnoreCase("f")) {
				int level = plugin.DataSqlite.getLevel(sender, "ForgeExp");
				return level;
			}
			if (s.equalsIgnoreCase("a")) {
				int level = plugin.DataSqlite.getLevel(sender, "ArcheryExp");
				return level;
			}
			if (s.equalsIgnoreCase("d")) {
				int level = plugin.DataSqlite.getLevel(sender, "DiggingExp");
				return level;
			}
		}
		return 0;
	}

	public  double getExpLeft(CommandSender sender, String s) {
		if (plugin.Settings.database.equalsIgnoreCase("flatfile")) {
			if (s.equalsIgnoreCase("w")) {
				Double exp = plugin.LevelFunctions.getExpLeft(sender, plugin.WCExpFile);
				return exp;
			}
			if (s.equalsIgnoreCase("r")) {
				Double exp = plugin.LevelFunctions.getExpLeft(sender, plugin.RangeExpFile);
				return exp;
			}
			if (s.equalsIgnoreCase("s")) {
				Double exp = plugin.LevelFunctions.getExpLeft(sender, plugin.SlayExpFile);
				return exp;
			}
			if (s.equalsIgnoreCase("m")) {
				Double exp = plugin.LevelFunctions.getExpLeft(sender, plugin.MiExpFile);
				return exp;
			}
			if (s.equalsIgnoreCase("c")) {
				Double exp = plugin.LevelFunctions.getExpLeft(sender, plugin.FisticuffsExpFile);
				return exp;
			}
			if (s.equalsIgnoreCase("f")) {
				Double exp = plugin.LevelFunctions.getExpLeft(sender, plugin.ForgeExpFile);
				return exp;
			}
			if (s.equalsIgnoreCase("a")) {
				Double exp = plugin.LevelFunctions.getExpLeft(sender, plugin.ArcherExpFile);
				return exp;
			}
			if (s.equalsIgnoreCase("d")) {
				Double exp = plugin.LevelFunctions.getExpLeft(sender, plugin.DiggingExpFile);
				return exp;
			}
		}
		if (plugin.Settings.database.equalsIgnoreCase("mysql")) {
			if (s.equalsIgnoreCase("w")) {
				Double exp = plugin.DataMySql.getExpLeft(sender, "WoodcuttingExp");
				return exp;
			}
			if (s.equalsIgnoreCase("r")) {
				Double exp = plugin.DataMySql.getExpLeft(sender, "RangingExp");
				return exp;
			}
			if (s.equalsIgnoreCase("s")) {
				Double exp = plugin.DataMySql.getExpLeft(sender, "SlayingExp");
				return exp;
			}
			if (s.equalsIgnoreCase("m")) {
				Double exp = plugin.DataMySql.getExpLeft(sender, "MiningExp");
				return exp;
			}
			if (s.equalsIgnoreCase("c")) {
				Double exp = plugin.DataMySql.getExpLeft(sender, "FisticuffsExp");
				return exp;
			}
			if (s.equalsIgnoreCase("f")) {
				Double exp = plugin.DataMySql.getExpLeft(sender, "ForgeExp");
				return exp;
			}
			if (s.equalsIgnoreCase("a")) {
				Double exp = plugin.DataMySql.getExpLeft(sender, "ArcheryExp");
				return exp;
			}
			if (s.equalsIgnoreCase("d")) {
				Double exp = plugin.DataMySql.getExpLeft(sender, "DiggingExp");
				return exp;
			}
			return 0;
		}
		if (plugin.Settings.database.equalsIgnoreCase("sqlite")) {
			if (s.equalsIgnoreCase("w")) {
				Double exp = plugin.DataSqlite.getExpLeft(sender, "WoodcuttingExp");
				return exp;
			}
			if (s.equalsIgnoreCase("r")) {
				Double exp = plugin.DataSqlite.getExpLeft(sender, "RangingExp");
				return exp;
			}
			if (s.equalsIgnoreCase("s")) {
				Double exp = plugin.DataSqlite.getExpLeft(sender, "SlayingExp");
				return exp;
			}
			if (s.equalsIgnoreCase("m")) {
				Double exp = plugin.DataSqlite.getExpLeft(sender, "MiningExp");
				return exp;
			}
			if (s.equalsIgnoreCase("c")) {
				Double exp = plugin.DataSqlite.getExpLeft(sender, "FisticuffsExp");
				return exp;
			}
			if (s.equalsIgnoreCase("f")) {
				Double exp = plugin.DataSqlite.getExpLeft(sender, "ForgeExp");
				return exp;
			}
			if (s.equalsIgnoreCase("a")) {
				Double exp = plugin.DataSqlite.getExpLeft(sender, "ArcheryExp");
				return exp;
			}
			if (s.equalsIgnoreCase("d")) {
				Double exp = plugin.DataSqlite.getExpLeft(sender, "DiggingExp");
				return exp;
			}
		}
		return 0;
	}
	public  boolean update(Player sender, String s, Double exp) {
		if (plugin.Settings.database.equalsIgnoreCase("flatfile")) {
			if (s.equalsIgnoreCase("w")) {
				plugin.LevelFunctions.write(sender, exp, plugin.WCExpFile);
				return true;
			}
			if (s.equalsIgnoreCase("r")) {
				plugin.LevelFunctions.write(sender, exp, plugin.RangeExpFile);
				return true;
			}
			if (s.equalsIgnoreCase("s")) {
				plugin.LevelFunctions.write(sender, exp, plugin.SlayExpFile);
				return true;
			}
			if (s.equalsIgnoreCase("m")) {
				plugin.LevelFunctions.write(sender, exp, plugin.MiExpFile);
				return true;
			}
			if (s.equalsIgnoreCase("c")) {
				plugin.LevelFunctions.write(sender, exp, plugin.FisticuffsExpFile);
				return true;
			}
			if (s.equalsIgnoreCase("f")) {
				plugin.LevelFunctions.write(sender, exp, plugin.ForgeExpFile);
				return true;
			}
			if (s.equalsIgnoreCase("a")) {
				plugin.LevelFunctions.write(sender, exp, plugin.ArcherExpFile);
				return true;
			}
			if (s.equalsIgnoreCase("d")) {
				plugin.LevelFunctions.write(sender, exp, plugin.DiggingExpFile);
				return true;
			}
			
		}
		if (plugin.Settings.database.equalsIgnoreCase("mysql")) {
			if (s.equalsIgnoreCase("w")) {
				plugin.DataMySql.update(sender, "WoodcuttingExp", exp);
				return true;
			}
			if (s.equalsIgnoreCase("r")) {
				plugin.DataMySql.update(sender, "RangingExp", exp);
				return true;
			}
			if (s.equalsIgnoreCase("s")) {
				plugin.DataMySql.update(sender, "SlayingExp", exp);
				return true;
			}
			if (s.equalsIgnoreCase("m")) {
				plugin.DataMySql.update(sender, "MiningExp", exp);
				return true;
			}
			if (s.equalsIgnoreCase("c")) {
				plugin.DataMySql.update(sender, "FisticuffsExp", exp);
				return true;
			}
			if (s.equalsIgnoreCase("f")) {
				plugin.DataMySql.update(sender, "ForgeExp", exp);
				return true;
			}
			if (s.equalsIgnoreCase("a")) {
				plugin.DataMySql.update(sender, "ArcheryExp", exp);
				return true;
			}
			if (s.equalsIgnoreCase("d")) {
				plugin.DataMySql.update(sender, "DiggingExp", exp);
				return true;
			}
			return false;
		}
		if (plugin.Settings.database.equalsIgnoreCase("sqlite")) {
			if (s.equalsIgnoreCase("w")) {
				plugin.DataSqlite.update(sender, "MiningExp", exp);
				return true;
			}
			if (s.equalsIgnoreCase("r")) {
				plugin.DataSqlite.update(sender, "RangingExp", exp);
				return true;
			}
			if (s.equalsIgnoreCase("s")) {
				plugin.DataSqlite.update(sender, "SlayingExp", exp);
				return true;
			}
			if (s.equalsIgnoreCase("m")) {
				plugin.DataSqlite.update(sender, "MiningExp", exp);
				return true;
			}
			if (s.equalsIgnoreCase("c")) {
				plugin.DataSqlite.update(sender, "FisticuffsExp", exp);
				return true;
			}
			if (s.equalsIgnoreCase("f")) {
				plugin.DataSqlite.update(sender, "ForgeExp", exp);
				return true;
			}
			if (s.equalsIgnoreCase("a")) {
				plugin.DataSqlite.update(sender, "ArcheryExp", exp);
				return true;
			}
			if (s.equalsIgnoreCase("d")) {
				plugin.DataSqlite.update(sender, "DiggingExp", exp);
				return true;
			}
		}
		return false;
	}
}