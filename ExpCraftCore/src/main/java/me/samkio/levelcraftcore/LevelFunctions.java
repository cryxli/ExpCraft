package me.samkio.levelcraftcore;

import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class LevelFunctions {
	private static LevelCraftCore plugin;

	public static void addExp(final Player player, final Plugin p,
			final double i) {
		// if(plugin.Permissions.hasLevelNoExp(player, p)) return;
		if (i == 0) {
			return;
		}
		if (!Whitelist.hasLevelExp(player, p)) {
			return;
		}

		int beforeLevel = LevelFunctions.getLevel(player, p);
		LevelFunctions.updateExp(player, p,
				(LevelFunctions.getExp(player, p) + i));
		if (isNotified(player)) {
			if (i > 0) {
				LCChat.good(player, "[" + plugin.LevelIndexes.get(p) + "] "
						+ plugin.lang.YouGained + i + " exp");
			} else if (i < 0) {
				plugin.LCChat.bad(player, "[" + plugin.LevelIndexes.get(p)
						+ "] " + plugin.lang.YouLost + i + " exp");
			}
		}
		int newLevel = LevelFunctions.getLevel(player, p);
		if (beforeLevel < newLevel) {
			LCChat.good(player, plugin.lang.LevelUp + newLevel + " in "
					+ plugin.LevelNames.get(p));
			LCChat.good(player, plugin.lang.SeeLevelUnlocks
					+ plugin.LevelIndexes.get(p)
					+ " - To see what you have unlocked.");

			if (plugin.NotifyAll) {
				LCChat.broadcast(player.getName() + plugin.lang.IsNowLevel
						+ newLevel + " in " + plugin.LevelNames.get(p) + ".");
			}
		}
	}

	public static boolean antiCheat() {
		return plugin.anticheat;
	}

	public static double getExp(final Player player, final Plugin p) {

		plugin.Tools.checkAccount(player);
		if (plugin.database.equalsIgnoreCase("FlatFile")) {
			/*
			 * for (Plugin p1 : plugin.LevelFiles.keySet()) { if (p1 != p)
			 * continue; return plugin.FlatFile.getDouble(player.getName(),
			 * plugin.LevelFiles.get(p1)); }
			 */

			if (plugin.LevelFiles.containsKey(p)
					&& plugin.ExpCache.containsPlugin(p)) {
				/*
				 * for (Plugin p1 : plugin.LevelNames.keySet()) { if (p1 != p)
				 * continue; return
				 * plugin.SqliteDB.getDouble(player.getName(),plugin
				 * .LevelNames.get(p1));
				 */

				double exp = 0;
				// HashMap<Player, Double> expPlayers = ;
				if (plugin.ExpCache.containsPlayer(p, player)) {// In cache, use
																// it !

					exp = plugin.ExpCache.getExp(p, player);
				} else {// Not in cache, add it !
					exp = plugin.FlatFile.getDouble(player.getName(),
							plugin.LevelFiles.get(p));
					synchronized (plugin.ExpCache) {
						plugin.ExpCache.putExp(p, player, exp);
					}
				}
				return exp;
			}
		} else if (plugin.database.equalsIgnoreCase("sqlite")) {
			if (plugin.LevelNames.containsKey(p)
					&& plugin.ExpCache.containsPlugin(p)) {
				/*
				 * for (Plugin p1 : plugin.LevelNames.keySet()) { if (p1 != p)
				 * continue; return
				 * plugin.SqliteDB.getDouble(player.getName(),plugin
				 * .LevelNames.get(p1));
				 */

				double exp = 0;
				// HashMap<Player, Double> expPlayers = plugin.ExpCache.get(p);
				if (plugin.ExpCache.containsPlayer(p, player)) {// In cache, use
																// it !

					exp = plugin.ExpCache.getExp(p, player);
				} else {// Not in cache, add it !
					exp = plugin.SqliteDB.getDouble(player.getName(),
							plugin.LevelNames.get(p));
					synchronized (plugin.ExpCache) {
						plugin.ExpCache.putExp(p, player, exp);
					}
				}
				return exp;
			}
		} else if (plugin.database.equalsIgnoreCase("mysql")) {
			/*
			 * for (Plugin p1 : plugin.LevelNames.keySet()) { if (p1 != p)
			 * continue; return
			 * plugin.MySqlDB.getDouble(player.getName(),plugin.
			 * LevelNames.get(p1)); }
			 */
			// ADD CACHE BY L5D

			if (plugin.LevelNames.containsKey(p)
					&& plugin.ExpCache.containsPlugin(p)) {
				double exp = 0;
				// HashMap<Player, Double> expPlayers = plugin.ExpCache.get(p);
				if (plugin.ExpCache.containsPlayer(p, player)) {// In cache, use
																// it !
					exp = plugin.ExpCache.getExp(p, player);
				} else {// Not in cache, add it !
					exp = plugin.MySqlDB.getDouble(player.getName(),
							plugin.LevelNames.get(p));
					synchronized (plugin.ExpCache) {
						plugin.ExpCache.putExp(p, player, exp);
					}
				}
				return exp;
			}
		}
		return 0;

	}

	public static double getExpLeft(final Player player, final Plugin p) {
		double exp = LevelFunctions.getExp(player, p);
		int level = LevelFunctions.getLevel(player, p);
		if (level >= plugin.LevelCap && plugin.EnableLevelCap) {
			return 0;
		}
		double nextExp = plugin.Constant * ((level) * (level));
		double leftExp = nextExp - exp;
		return leftExp;

	}

	public static int getLevel(final Player player, final Plugin p) {
		if (player == null) {
			return 0;
		}
		plugin.Tools.checkAccount(player);
		int level = 0;
		int max = 1000;
		double constant = plugin.Constant;
		if (plugin.EnableLevelCap) {
			max = plugin.LevelCap;
		}
		double exp = LevelFunctions.getExp(player, p);
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

	public static String getPlayerAtPos(final String string, final int i) {
		for (Plugin p : plugin.LevelReferenceKeys.keySet()) {
			String[] reference = plugin.LevelReferenceKeys.get(p);
			if (plugin.Tools.containsValue(reference, string)) {
				if (plugin.database.equalsIgnoreCase("mysql")) {
					return plugin.MySqlDB.getPlayerAtPos(
							plugin.LevelNames.get(p), i);
				}
				if (plugin.database.equalsIgnoreCase("sqlite")) {
					return plugin.SqliteDB.getPlayerAtPos(
							plugin.LevelNames.get(p), i);
				}
				if (plugin.database.equalsIgnoreCase("flatfile")) {
					return plugin.FlatFile.getPlayerAtPos(
							plugin.LevelNames.get(p), i,
							plugin.LevelFiles.get(p));
				}
			}
		}
		return "None";
	}

	public static Set<Plugin> getPluginsList() {
		return plugin.LevelNames.keySet();
	}

	public static int getPos(final Player sender, final String string) {
		for (Plugin p : plugin.LevelReferenceKeys.keySet()) {
			String[] reference = plugin.LevelReferenceKeys.get(p);
			if (plugin.Tools.containsValue(reference, string)
					&& Whitelist.hasLevel(sender, p)) {
				if (plugin.database.equalsIgnoreCase("mysql")) {
					return plugin.MySqlDB.getPos(sender.getName(),
							plugin.LevelNames.get(p));
				}
				if (plugin.database.equalsIgnoreCase("sqlite")) {
					return plugin.SqliteDB.getPos(sender.getName(),
							plugin.LevelNames.get(p));
				}
				if (plugin.database.equalsIgnoreCase("flatfile")) {
					return plugin.FlatFile.getPos(sender.getName(),
							plugin.LevelFiles.get(p));
				}
			}
		}
		return 0;
	}

	public static boolean isMaster(final Player player, final Plugin p) {
		if (LevelFunctions.getLevel(player, p) >= plugin.LevelCap) {
			return true;
		}
		return false;
	}

	public static boolean isNotified(final Player p) {
		return plugin.Tools.enabled(p);
	}

	public static void updateExp(final Player player, final Plugin p,
			final double i) {
		if (plugin.database.equalsIgnoreCase("flatfile")) {
			/*
			 * for (Plugin p1 : plugin.LevelFiles.keySet()) { if (p1 != p)
			 * continue; plugin.FlatFile.write(player.getName(),
			 * plugin.LevelFiles.get(p1), i); return; }
			 */if (plugin.LevelFiles.containsKey(p)
					&& plugin.ExpCache.containsPlugin(p)) {
				if (!plugin.PeriodicSave) {
					plugin.FlatFile.write(player.getName(),
							plugin.LevelFiles.get(p), i);
				}
				synchronized (plugin.ExpCache) {
					plugin.ExpCache.putExp(p, player, i);
				}

			}
		} else if (plugin.database.equalsIgnoreCase("sqlite")) {
			/*
			 * for (Plugin p1 : plugin.LevelNames.keySet()) { if (p1 != p)
			 * continue; plugin.SqliteDB.update(player.getName(),
			 * plugin.LevelNames.get(p1), i); return; }
			 */
			if (plugin.LevelNames.containsKey(p)
					&& plugin.ExpCache.containsPlugin(p)) {
				if (!plugin.PeriodicSave) {

					plugin.SqliteDB.update(player.getName(),
							plugin.LevelNames.get(p), i);
				}
				synchronized (plugin.ExpCache) {
					plugin.ExpCache.putExp(p, player, i);
				}

			}
		} else if (plugin.database.equalsIgnoreCase("mysql")) {
			/*
			 * for (Plugin p1 : plugin.LevelNames.keySet()) { if (p1 != p)
			 * continue;
			 * plugin.MySqlDB.update(player.getName(),plugin.LevelNames.get(p1),
			 * i); return; }
			 */
			// ADD CACHE BY L5D
			if (plugin.LevelNames.containsKey(p)
					&& plugin.ExpCache.containsPlugin(p)) {
				if (!plugin.PeriodicSave) {
					plugin.MySqlDB.update(player.getName(),
							plugin.LevelNames.get(p), i);
				}
				synchronized (plugin.ExpCache) {
					plugin.ExpCache.putExp(p, player, i);
				}
			}
		}
	}

	public static void updateLevel(final Player player, final Plugin p, int i) {
		if (i >= plugin.LevelCap && plugin.EnableLevelCap) {
			i = plugin.LevelCap;
		}
		double exp = plugin.Constant * ((i - 1) * (i - 1));
		LevelFunctions.updateExp(player, p, exp);

	}

	public LevelFunctions(final LevelCraftCore instance) {
		plugin = instance;
	}

}
