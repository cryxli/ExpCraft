package me.samkio.levelcraftcore;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class LevelFunctions {
	public static LevelCraftCore plugin;

	public LevelFunctions(LevelCraftCore instance) {
		plugin = instance;
	}

	@SuppressWarnings("static-access")
	public  static int getLevel(Player player, Plugin p) {
		int level = 0;
		int max = 1000;
		double constant = plugin.Constant;
		if (plugin.EnableLevelCap) {
			max = plugin.LevelCap;
		}
		for (int i = 1; i <= max; i++) {
			double levelAti = constant * (i * i);
			if (levelAti > plugin.LevelFunctions.getExp(player, p)) {
				level = i;
				break;
			} else if (i == max && plugin.EnableLevelCap) {
				level = max;
				break;
			}
		}
		return level;

	}

	public  static double getExp(Player player, Plugin p) {
		if (plugin.database.equalsIgnoreCase("FlatFile")) {
			for (Plugin p1 : plugin.LevelFiles.keySet()) {
				if (p1 != p)
					continue;
				return plugin.FlatFile.getDouble(player.getName(),
						plugin.LevelFiles.get(p1));
			}
		}else if (plugin.database.equalsIgnoreCase("sqlite")) {
			for (Plugin p1 : plugin.LevelNames.keySet()) {
				if (p1 != p)
					continue;
				return plugin.SqliteDB.getDouble(player.getName(),plugin.LevelNames.get(p1));
			}
		}else if (plugin.database.equalsIgnoreCase("mysql")) {
			for (Plugin p1 : plugin.LevelNames.keySet()) {
				if (p1 != p)
					continue;
				return plugin.MySqlDB.getDouble(player.getName(),plugin.LevelNames.get(p1));
			}
		}
		return 0;

	}

	@SuppressWarnings("static-access")
	public  static double getExpLeft(Player player, Plugin p) {
		double exp = plugin.LevelFunctions.getExp(player, p);
		int level = plugin.LevelFunctions.getLevel(player, p);
		if (level >= plugin.LevelCap && plugin.EnableLevelCap)
			return 0;
		double nextExp = plugin.Constant * ((level) * (level));
		double leftExp = nextExp - exp;
		return leftExp;

	}

	@SuppressWarnings("static-access")
	public  static void updateLevel(Player player, Plugin p, int i) {
		if (i >= plugin.LevelCap && plugin.EnableLevelCap)
			i = plugin.LevelCap;
		double exp = plugin.Constant * ((i - 1) * (i - 1));
		plugin.LevelFunctions.updateExp(player, p, exp);

	}

	public  static void updateExp(Player player, Plugin p, double i) {
		if (plugin.database.equalsIgnoreCase("flatfile")) {
			for (Plugin p1 : plugin.LevelFiles.keySet()) {
				if (p1 != p)
					continue;
				plugin.FlatFile.write(player.getName(),
						plugin.LevelFiles.get(p1), i);
				return;
			}
		}else if (plugin.database.equalsIgnoreCase("sqlite")) {
			for (Plugin p1 : plugin.LevelNames.keySet()) {
				if (p1 != p)
					continue;
				plugin.SqliteDB.update(player.getName(),plugin.LevelNames.get(p1), i);
				return;
			}
		}else if (plugin.database.equalsIgnoreCase("mysql")) {
			for (Plugin p1 : plugin.LevelNames.keySet()) {
				if (p1 != p)
					continue;
				plugin.MySqlDB.update(player.getName(),plugin.LevelNames.get(p1), i);
				return;
			}
		}
	}
	@SuppressWarnings("static-access")
	public static  void addExp(Player player, Plugin p, double i){
		int beforeLevel = plugin.LevelFunctions.getLevel(player, p);
		plugin.LevelFunctions.updateExp(player, p, (plugin.LevelFunctions.getExp(player, p)+i));
		if(isNotified(player))plugin.LCChat.good(player, "You gained: "+i+"exp");
		if(beforeLevel<plugin.LevelFunctions.getLevel(player, p)){
			plugin.LCChat.good(player, "LEVEL UP. You are now level "+plugin.LevelFunctions.getLevel(player, p)+" in " + plugin.LevelNames.get(p));
			plugin.LCChat.good(player, "See /level unlocks "+plugin.LevelIndexes.get(p)+" - To see what you have unlocked.");
		}
	}
	public static  boolean isNotified(Player p){
		return plugin.Tools.enabled(p);
	}
}
