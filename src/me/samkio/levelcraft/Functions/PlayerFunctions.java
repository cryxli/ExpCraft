package me.samkio.levelcraft.Functions;

import java.util.HashMap;

import me.samkio.levelcraft.Admin;
import me.samkio.levelcraft.Help;
import me.samkio.levelcraft.Levelcraft;
import me.samkio.levelcraft.Whitelist;
import me.samkio.levelcraft.SamToolbox.DataMySql;
import me.samkio.levelcraft.SamToolbox.DataSqlite;
import me.samkio.levelcraft.SamToolbox.Level;
import me.samkio.levelcraft.SamToolbox.Toolbox;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class PlayerFunctions {
	public static Levelcraft plugin;
	private final static HashMap<CommandSender, Boolean> NotifyUsers = new HashMap<CommandSender, Boolean>();

	public static void doThis(CommandSender sender, String[] split, Levelcraft plugin) {
		if (sender instanceof Player){
			Player player  = (Player) sender;
			if ((split[0].equalsIgnoreCase("wc")
					|| split[0].equalsIgnoreCase("wood")
					|| split[0].equalsIgnoreCase("woodcut") || split[0]
					.equalsIgnoreCase("w")) && plugin.Settings.enableWCLevel == true && !Whitelist.isAvoid((Player) sender, "w")) {
				showStat(sender, "w");
			} else if ((split[0].equalsIgnoreCase("mine")
					|| split[0].equalsIgnoreCase("m") || split[0]
                    .equalsIgnoreCase("mining"))
                    && plugin.Settings.enableMineLevel == true && !Whitelist.isAvoid((Player) sender, "m")) {
				showStat(sender, "m");
			} else if ((split[0].equalsIgnoreCase("slay")
					|| split[0].equalsIgnoreCase("s") || split[0]
					.equalsIgnoreCase("slayer"))
					&& plugin.Settings.enableSlayerLevel == true && !Whitelist.isAvoid((Player) sender, "s")) {
				showStat(sender, "s");
			} else if ((split[0].equalsIgnoreCase("range")
					|| split[0].equalsIgnoreCase("r") || split[0].equalsIgnoreCase("ranging") )
					&& plugin.Settings.enableRangeLevel == true && !Whitelist.isAvoid((Player) sender, "r")) {
				showStat(sender, "r"); 
			} else if ((split[0].equalsIgnoreCase("fist")
					|| split[0].equalsIgnoreCase("c") || split[0].equalsIgnoreCase("fisticuffs"))
				    && plugin.Settings.enableFisticuffsLevel == true && !Whitelist.isAvoid((Player) sender, "c")) {
				showStat(sender, "c"); 
			} else if ((split[0].equalsIgnoreCase("archer")
					|| split[0].equalsIgnoreCase("a") || split[0].equalsIgnoreCase("archery"))
					&& plugin.Settings.enableArcherLevel == true && !Whitelist.isAvoid((Player) sender, "a")) {
				showStat(sender, "a");
			}else if ((split[0].equalsIgnoreCase("dig")
					|| split[0].equalsIgnoreCase("d") || split[0].equalsIgnoreCase("digger"))
					&& plugin.Settings.enableDigLevel == true && !Whitelist.isAvoid((Player) sender, "d")) {
				showStat(sender, "d");
			}else if ((split[0].equalsIgnoreCase("forge")
					|| split[0].equalsIgnoreCase("f") || split[0].equalsIgnoreCase("forger"))
					&& plugin.Settings.enableForgeLevel == true && !Whitelist.isAvoid((Player) sender, "f")) {
				showStat(sender, "f");
			} else if (split[0].equalsIgnoreCase("list")) {
				Help.ListLevels(sender);
			}else if (split[0].equalsIgnoreCase("total")) {
				Help.Total(sender);
			} else if (split[0].equalsIgnoreCase("admin")
					&& Whitelist.isAdmin(player) == true
					&& split.length >= 3) {
				Admin.dothis(sender, split);
			} else if (split[0].equalsIgnoreCase("notify")) {
				toggleNotify(sender);
			} else if (split[0].equalsIgnoreCase("shout") && split.length >= 2) {
				Help.shout(sender, split[1], plugin);
			} else if ((split[0].equalsIgnoreCase("all"))) {
				int level = 0;
				int level2 = 0;
				int level3 = 0;
				int level4 = 0;
				int level5 = 0;
				int level6 = 0;
				int level7 = 0;
				int level8 = 0;
				double mineexp = 0;
				double slayexp = 0;
				double wcexp = 0;
				double rangexp = 0;
				double fisticuffsexp = 0;
				double archeryexp = 0;
				double digexp = 0;
				double forgeexp = 0;
				level = Level.getLevel(sender, "m");
				level2 = Level.getLevel(sender, "s");
				level3 = Level.getLevel(sender, "w");
				level4 = Level.getLevel(sender, "r");
				level5 = Level.getLevel(sender, "c");
				level6 = Level.getLevel(sender, "a");
				level7 = Level.getLevel(sender, "d");
				level8 = Level.getLevel(sender, "f");
				mineexp = Level.getExp(sender, "m");
				slayexp = Level.getExp(sender, "s");
				wcexp = Level.getExp(sender, "w");
				rangexp = Level.getExp(sender, "r");
				fisticuffsexp = Level.getExp(sender, "c");
				archeryexp = Level.getExp(sender, "a");
				digexp = Level.getExp(sender, "d");
				forgeexp = Level.getExp(sender, "f");
				sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1)
						+ "[LC] ---LevelCraftPlugin By Samkio--- ");
				if(plugin.Settings.enableMineLevel && !Whitelist.isAvoid((Player) sender, "m"))
				sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c3)
						+ " (Mining): " + level + ". Exp:" + mineexp);
				if(plugin.Settings.enableSlayerLevel && !Whitelist.isAvoid((Player) sender, "s"))
				sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c3)
						+ " (Slayer): " + level2 + ", Exp:" + slayexp);
				if(plugin.Settings.enableWCLevel && !Whitelist.isAvoid((Player) sender, "w"))
				sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c3)
						+ " (WoodCutting): " + level3 + ". Exp:" + wcexp);
				if(plugin.Settings.enableRangeLevel && !Whitelist.isAvoid((Player) sender, "r"))
				sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c3)
						+ " (Range): " + level4 + ". Exp:" + rangexp);
				if(plugin.Settings.enableFisticuffsLevel && !Whitelist.isAvoid((Player) sender, "c"))
				sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c3)
						+ " (Fitisicuffs): " + level5 + ". Exp:" + fisticuffsexp);
				if(plugin.Settings.enableArcherLevel && !Whitelist.isAvoid((Player) sender, "a"))
				sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c3)
						+ " (Archery): " + level6 + ". Exp:" + archeryexp);
				if(plugin.Settings.enableDigLevel && !Whitelist.isAvoid((Player) sender, "d"))
				sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c3)
						+ " (Digging): " + level7 + ". Exp:" + digexp);
				if(plugin.Settings.enableForgeLevel && !Whitelist.isAvoid((Player) sender, "f"))
				sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c3)
						+ " (Forge): " + level8 + ". Exp:" + forgeexp);
			} else if (split[0].equalsIgnoreCase("unlocks")) {
				if (split.length >= 2) {
					Help.unlocks(sender, split);
				} else {
					Help.IncorrectExp(sender);
				}
			} else {
				sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c2)
						+ " Stat not found type '/level list' to list all stats. ");
			}
		}	
	}

	public static void checkAccount(CommandSender sender) {
		if (plugin.Settings.database.equalsIgnoreCase("flatfile")) {
			boolean HasWCAccount = LevelFunctions.containskey(sender,
					Levelcraft.WCExpFile);
			boolean HasMineAcc = LevelFunctions.containskey(sender,
					Levelcraft.MiExpFile);
			boolean HasSlayAcc = LevelFunctions.containskey(sender,
					Levelcraft.SlayExpFile);
			boolean HasRangeAcc = LevelFunctions.containskey(sender,
					Levelcraft.RangeExpFile);
			boolean HasFisticuffsAcc = LevelFunctions.containskey(sender,
					Levelcraft.FisticuffsExpFile);
			boolean HasArcherAcc = LevelFunctions.containskey(sender,
					Levelcraft.ArcherExpFile);
			boolean HasDigAcc = LevelFunctions.containskey(sender,
					Levelcraft.DiggingExpFile);
			boolean HasForgeAcc = LevelFunctions.containskey(sender,
					Levelcraft.ForgeExpFile);
			if (HasWCAccount == false) {
				LevelFunctions.write(sender, 0, Levelcraft.WCExpFile);
			}
			if (HasDigAcc == false) {
				LevelFunctions.write(sender, 0, Levelcraft.DiggingExpFile);
			}
			if (HasMineAcc == false) {
				LevelFunctions.write(sender, 0, Levelcraft.MiExpFile);
			}

			if (HasSlayAcc == false) {
				LevelFunctions.write(sender, 0, Levelcraft.SlayExpFile);
			}
			if (HasRangeAcc == false) {
				LevelFunctions.write(sender, 0, Levelcraft.RangeExpFile);
			}
			if (HasFisticuffsAcc == false) {
				LevelFunctions.write(sender, 0, Levelcraft.FisticuffsExpFile);
			}
			if (HasArcherAcc == false) {
				LevelFunctions.write(sender, 0, Levelcraft.ArcherExpFile);
			}
			if (HasForgeAcc == false) {
				LevelFunctions.write(sender, 0, Levelcraft.ForgeExpFile);
			}
		} else if (plugin.Settings.database.equalsIgnoreCase("mysql") && DataMySql.PlayerExsists(sender) == false) {
			DataMySql.NewPlayer(sender, 0);
		} else if (plugin.Settings.database.equalsIgnoreCase("sqlite")
				&& DataSqlite.PlayerExsists(sender) == false) {
			DataSqlite.NewPlayer(sender, 0);
		}
	}

	public static boolean enabled(CommandSender sender) {
		return NotifyUsers.containsKey(sender);
	}

	public static void toggleNotify(CommandSender sender) {
		if (enabled(sender)) {
			NotifyUsers.remove(sender);
			Toolbox.sendMessage(sender, "Experience notify disabled.", true);
		} else {
			NotifyUsers.put(sender, null);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c3)
						+ " Experience notify enabled.");
			}
	}

	public static void showStat(CommandSender sender, String string) {
		if (sender instanceof Player) {
			int level = 0;
			double stat = 0;
			double expLeft = 0;
			String str = "NULL";
			if (string.equalsIgnoreCase("W") && plugin.Settings.enableWCLevel && !Whitelist.isAvoid((Player) sender, "w")) {
				level = Level.getLevel(sender, "w");
				stat = Level.getExp(sender, "w");
				expLeft = Level.getExpLeft(sender, "w");
				str = "Woodcut";
			} else if (string.equalsIgnoreCase("M") && plugin.Settings.enableMineLevel && !Whitelist.isAvoid((Player) sender, "m")) {
				level = Level.getLevel(sender, "m");
				stat = Level.getExp(sender, "m");
				expLeft = Level.getExpLeft(sender, "m");
				str = "Mining";
			} else if (string.equalsIgnoreCase("S") && plugin.Settings.enableSlayerLevel && !Whitelist.isAvoid((Player) sender, "s")) {
				level = Level.getLevel(sender, "s");
				stat = Level.getExp(sender, "s");
				expLeft = Level.getExpLeft(sender, "s");
				str = "Slaying";
			} else if (string.equalsIgnoreCase("R") && plugin.Settings.enableRangeLevel && !Whitelist.isAvoid((Player) sender, "r")) {
				level = Level.getLevel(sender, "r");
				stat = Level.getExp(sender, "r");
				expLeft = Level.getExpLeft(sender, "r");
				str = "Ranging";
			} else if (string.equalsIgnoreCase("C") && plugin.Settings.enableFisticuffsLevel && !Whitelist.isAvoid((Player) sender, "c")) {
				level = Level.getLevel(sender, "c");
				stat = Level.getExp(sender, "c");
				expLeft = Level.getExpLeft(sender, "c");
				str = "Fisticuffs";
			} else if (string.equalsIgnoreCase("A") && plugin.Settings.enableArcherLevel && !Whitelist.isAvoid((Player) sender, "a")) {
				level = Level.getLevel(sender, "a");
				stat = Level.getExp(sender, "a");
				expLeft = Level.getExpLeft(sender, "a");
				str = "Archery";
			}else if (string.equalsIgnoreCase("D") && plugin.Settings.enableDigLevel && !Whitelist.isAvoid((Player) sender, "d")) {
				level = Level.getLevel(sender, "d");
				stat = Level.getExp(sender, "d");
				expLeft = Level.getExpLeft(sender, "d");
				str = "Digging";
			}else if (string.equalsIgnoreCase("f") && plugin.Settings.enableForgeLevel && !Whitelist.isAvoid((Player) sender, "f")) {
				level = Level.getLevel(sender, "f");
				stat = Level.getExp(sender, "f");
				expLeft = Level.getExpLeft(sender, "f");
				str = "Forge";
			}

			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1)
					+ "[LC] ---LevelCraftPlugin By Samkio--- ");
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC] " + ChatColor.valueOf(plugin.Settings.c3) + str
					+ " experience: " + stat);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC] " + ChatColor.valueOf(plugin.Settings.c3) + str
					+ " level: " + level);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC] " + ChatColor.valueOf(plugin.Settings.c3)
					+ "Experience left to next level: " + expLeft);
		} else {
			sender.sendMessage("Error: Invalid player!");
		}
	}
}
