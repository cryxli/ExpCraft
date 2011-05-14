package me.samkio.levelcraftcore;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class LevelFunctions {
	public static LevelCraftCore plugin;

	public LevelFunctions(LevelCraftCore instance) {
		plugin = instance;
	}

	public  static Set<Plugin> getPluginsList() {
		return plugin.LevelNames.keySet();
	}	
	
	@SuppressWarnings("static-access")
	public  static int getLevel(Player player, Plugin p) {
		int level = 0;
		int max = 1000;
		double constant = plugin.Constant;
		if (plugin.EnableLevelCap) {
			max = plugin.LevelCap;
		}
		double exp = plugin.LevelFunctions.getExp(player, p);
		for (int i = 1; i <= max; i++) {
			double levelAti = constant * (i * i);
			if (levelAti > exp) {
				level = i;
				break;
			} else if (i == max && plugin.EnableLevelCap) {
				level = max;
				break;
			}
		}
		return level;

	}
	@SuppressWarnings("static-access")
	public static boolean isMaster(Player player,Plugin p){
		if(plugin.LevelFunctions.getLevel(player,p)>=plugin.LevelCap) return true;
		return false;
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
			/*for (Plugin p1 : plugin.LevelNames.keySet()) {
			if (p1 != p)
				continue;
			return plugin.MySqlDB.getDouble(player.getName(),plugin.LevelNames.get(p1));
			}*/
			//ADD CACHE BY L5D
			if(plugin.LevelNames.containsKey(p) && plugin.ExpCache.containsKey(p)){
				double exp=0;
				HashMap<Player, Double> expPlayers = plugin.ExpCache.get(p);
				if (expPlayers.containsKey(player)){//In cache, use it !
					exp = expPlayers.get(player);
				}
				else{//Not in cache, add it !
					exp = plugin.MySqlDB.getDouble(player.getName(),plugin.LevelNames.get(p));
					expPlayers.put(player, exp);
				}
				return exp;
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
			/*for (Plugin p1 : plugin.LevelNames.keySet()) {
				if (p1 != p)
					continue;
				plugin.MySqlDB.update(player.getName(),plugin.LevelNames.get(p1), i);
				return;
			}*/
			//ADD CACHE BY L5D
			Double exp;
			if (plugin.LevelNames.containsKey(p) && plugin.ExpCache.containsKey(p)){
				HashMap<Player, Double> expPlayers = plugin.ExpCache.get(p);
				if (expPlayers.containsKey(p)){//In cache, use it !
					exp = expPlayers.get(p);
					exp = exp + i;
					plugin.MySqlDB.update(player.getName(),plugin.LevelNames.get(p), i);
				}
				else{//Not in cache, add it !
					plugin.MySqlDB.update(player.getName(),plugin.LevelNames.get(p), i);
					exp = plugin.MySqlDB.getDouble(player.getName(),plugin.LevelNames.get(p));
					expPlayers.put(player, exp);
				}
			}
		}
	}
	@SuppressWarnings("static-access")
	public static  void addExp(Player player, Plugin p, double i){
		//if(plugin.Permissions.hasLevelNoExp(player, p)) return;
		int beforeLevel = plugin.LevelFunctions.getLevel(player, p);
		if(!plugin.Permissions.hasLevelExp(player, p)) return;
		plugin.LevelFunctions.updateExp(player, p, (plugin.LevelFunctions.getExp(player, p)+i));
		if(isNotified(player))
			if(i>0) plugin.LCChat.good(player, "["+plugin.LevelIndexes.get(p)+"] "+plugin.lang.YouGained+" "+i+" exp");
			else if(i<0) plugin.LCChat.bad(player, "["+plugin.LevelIndexes.get(p)+"] "+plugin.lang.YouLost+" "+i+" exp");
		int newLevel = plugin.LevelFunctions.getLevel(player, p);
		if(beforeLevel<newLevel){
			plugin.LCChat.good(player, plugin.lang.LevelUp+newLevel+" in " + plugin.LevelNames.get(p));
			plugin.LCChat.good(player, plugin.lang.SeeLevelUnlocks+plugin.LevelIndexes.get(p)+" - To see what you have unlocked.");
		
		if(plugin.NotifyAll){
			plugin.LCChat.broadcast(player.getName()+plugin.lang.IsNowLevel+newLevel+" in "+plugin.LevelNames.get(p)+".");
		}
		}
	}
	public static  boolean isNotified(Player p){
		return plugin.Tools.enabled(p);
	}
	@SuppressWarnings("static-access")
	public static int getPos(Player sender,String string){
			for (Plugin p : plugin.LevelReferenceKeys.keySet()) {
				String[] reference = plugin.LevelReferenceKeys.get(p);
				if(plugin.Tools.containsValue(reference,string) && plugin.Permissions.hasLevel(sender, p)){
					if(plugin.database.equalsIgnoreCase("mysql")) return plugin.MySqlDB.getPos(sender.getName(), plugin.LevelNames.get(p));
					if(plugin.database.equalsIgnoreCase("sqlite")) return plugin.SqliteDB.getPos(sender.getName(), plugin.LevelNames.get(p));
					if(plugin.database.equalsIgnoreCase("flatfile")) return plugin.FlatFile.getPos(sender.getName(), plugin.LevelFiles.get(p));
				}
			}
			return 0;
	}
	public static String getPlayerAtPos(String string,int i){
		for (Plugin p : plugin.LevelReferenceKeys.keySet()) {
			String[] reference = plugin.LevelReferenceKeys.get(p);
			if(plugin.Tools.containsValue(reference,string)){
				if(plugin.database.equalsIgnoreCase("mysql")) return plugin.MySqlDB.getPlayerAtPos(plugin.LevelNames.get(p),i);
				if(plugin.database.equalsIgnoreCase("sqlite")) return plugin.SqliteDB.getPlayerAtPos(plugin.LevelNames.get(p),i);
				if(plugin.database.equalsIgnoreCase("flatfile")) return plugin.FlatFile.getPlayerAtPos(plugin.LevelNames.get(p),i, plugin.LevelFiles.get(p));
			}
		}
		return "None";
}
}
