package me.samkio.levelcraft;

import me.samkio.levelcraft.SamToolbox.Level;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Help {
	public static Levelcraft plugin;
	public static void IncorrectExp(CommandSender sender) {
		sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
				+ ChatColor.valueOf(plugin.Settings.c2) + " Stat not found.");
		sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
				+ ChatColor.valueOf(plugin.Settings.c2)
				+ "'/level list' to show all stats.");
	}

	public static void ListLevels(CommandSender sender) {
		String list = ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
				+ ChatColor.valueOf(plugin.Settings.c2) + " Stats:";
		if (plugin.Settings.enableWCLevel == true
				&& !Whitelist.isAvoid((Player) sender, "w")) {
			list = list + "WoodCutting(W),";
		}
		if (plugin.Settings.enableMineLevel == true
				&& !Whitelist.isAvoid((Player) sender, "m")) {
			list = list + "Mining(M),";
		}
		if (plugin.Settings.enableSlayerLevel == true
				&& !Whitelist.isAvoid((Player) sender, "s")) {
			list = list + "Slaying(S),";
		}
		if (plugin.Settings.enableRangeLevel == true
				&& !Whitelist.isAvoid((Player) sender, "r")) {
			list = list + "Ranging(R),";
		}
		if (plugin.Settings.enableFisticuffsLevel == true
				&& !Whitelist.isAvoid((Player) sender, "c")) {
			list = list + "Fisticuffs(C),";
		}
		if (plugin.Settings.enableArcherLevel == true
				&& !Whitelist.isAvoid((Player) sender, "a")) {
			list = list + "Archery(A),";
		}
		if (plugin.Settings.enableDigLevel == true
				&& !Whitelist.isAvoid((Player) sender, "d")) {
			list = list + "Digging(D),";
		}
		if (plugin.Settings.enableForgeLevel == true
				&& !Whitelist.isAvoid((Player) sender, "f")) {
			list = list + "Forge(F),";
		}
		sender.sendMessage(list);
	}

	public static void shout(CommandSender sender, String string,
			Levelcraft plugin) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (!Whitelist.isShouter(player)){
				sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1)+"[LC]"+ChatColor.valueOf(plugin.Settings.c4)+" You do not have the permission to shout!");
				return;
			}
			int level = 0;
			if ((string.equalsIgnoreCase("wc")
					|| string.equalsIgnoreCase("wood")
					|| string.equalsIgnoreCase("woodcut") || string
					.equalsIgnoreCase("w")) && plugin.Settings.enableWCLevel == true) {
				level = Level.getLevel(sender, "w");
				plugin.getServer().broadcastMessage(
						ChatColor.valueOf(plugin.Settings.c1) + "[LC] "
								+ ChatColor.valueOf(plugin.Settings.c2)
								+ player.getName() + "'s"
								+ ChatColor.valueOf(plugin.Settings.c1)
								+ " woodcutting level is " + level + ".");
			} else if ((string.equalsIgnoreCase("mine")
					|| string.equalsIgnoreCase("m") || string
					.equalsIgnoreCase("mining"))
					&& plugin.Settings.enableMineLevel == true) {
				level = Level.getLevel(sender, "m");
				plugin.getServer().broadcastMessage(
						ChatColor.valueOf(plugin.Settings.c1) + "[LC] "
								+ ChatColor.valueOf(plugin.Settings.c2)
								+ player.getName() + "'s"
								+ ChatColor.valueOf(plugin.Settings.c1)
								+ " mining level is " + level + ".");
			} else if ((string.equalsIgnoreCase("slay")
					|| string.equalsIgnoreCase("s") || string
					.equalsIgnoreCase("slaying"))
					&& plugin.Settings.enableSlayerLevel == true) {
				level = Level.getLevel(sender, "s");
				plugin.getServer().broadcastMessage(
						ChatColor.valueOf(plugin.Settings.c1) + "[LC] "
								+ ChatColor.valueOf(plugin.Settings.c2)
								+ player.getName() + "'s"
								+ ChatColor.valueOf(plugin.Settings.c1)
								+ " slayer level is " + level + ".");
			} else if ((string.equalsIgnoreCase("range")
					|| string.equalsIgnoreCase("r") || string
					.equalsIgnoreCase("ranging"))
					&& plugin.Settings.enableRangeLevel == true) {
				level = Level.getLevel(sender, "r");
				plugin.getServer().broadcastMessage(
						ChatColor.valueOf(plugin.Settings.c1) + "[LC] "
								+ ChatColor.valueOf(plugin.Settings.c2)
								+ player.getName() + "'s"
								+ ChatColor.valueOf(plugin.Settings.c1)
								+ " range level is " + level + ".");
			} else if ((string.equalsIgnoreCase("Fist")
					|| string.equalsIgnoreCase("c") || string
					.equalsIgnoreCase("Fisticuffs"))
					&& plugin.Settings.enableFisticuffsLevel == true) {
				level = Level.getLevel(sender, "c");
				plugin.getServer().broadcastMessage(
						ChatColor.valueOf(plugin.Settings.c1) + "[LC] "
								+ ChatColor.valueOf(plugin.Settings.c2)
								+ player.getName() + "'s"
								+ ChatColor.valueOf(plugin.Settings.c1)
								+ " fisticuffs level is " + level + ".");
			} else if ((string.equalsIgnoreCase("archer")
					|| string.equalsIgnoreCase("a") || string
					.equalsIgnoreCase("archery"))
					&& plugin.Settings.enableFisticuffsLevel == true) {
				level = Level.getLevel(sender, "a");
				plugin.getServer().broadcastMessage(
						ChatColor.valueOf(plugin.Settings.c1) + "[LC] "
								+ ChatColor.valueOf(plugin.Settings.c2)
								+ player.getName() + "'s"
								+ ChatColor.valueOf(plugin.Settings.c1)
								+ " archery level is " + level + ".");
			} else if ((string.equalsIgnoreCase("digger")
					|| string.equalsIgnoreCase("d") || string
					.equalsIgnoreCase("digging"))
					&& plugin.Settings.enableDigLevel == true) {
				level = Level.getLevel(sender, "d");
				plugin.getServer().broadcastMessage(
						ChatColor.valueOf(plugin.Settings.c1) + "[LC] "
								+ ChatColor.valueOf(plugin.Settings.c2)
								+ player.getName() + "'s"
								+ ChatColor.valueOf(plugin.Settings.c1)
								+ " digging level is " + level + ".");
			} else if ((string.equalsIgnoreCase("forge")
					|| string.equalsIgnoreCase("f") || string
					.equalsIgnoreCase("forger"))
					&& plugin.Settings.enableDigLevel == true) {
				level = Level.getLevel(sender, "f");
				plugin.getServer().broadcastMessage(
						ChatColor.valueOf(plugin.Settings.c1) + "[LC] "
								+ ChatColor.valueOf(plugin.Settings.c2)
								+ player.getName() + "'s"
								+ ChatColor.valueOf(plugin.Settings.c1)
								+ " forger level is " + level + ".");
			} else {
				sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
						+ ChatColor.valueOf(plugin.Settings.c2) + " Stat not found.");
				sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
						+ ChatColor.valueOf(plugin.Settings.c2)
						+ " Please type '/level list' to show all stats.");
			}
		} else {
			sender.sendMessage("Error: Could not broadcast message!");
		}
	}


	public static void unlocks(CommandSender sender, String[] split) {
		if ((split[1].equalsIgnoreCase("wc")
				|| split[1].equalsIgnoreCase("wood")
				|| split[1].equalsIgnoreCase("woodcut") || split[1]
				.equalsIgnoreCase("w")) && plugin.Settings.enableWCLevel == true) {
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1)
					+ "[LC] ---LevelCraftPlugin By Samkio--- ");
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3) + " WoodenAxe: "
					+ plugin.Settings.WCWoodAxe);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3) + " StoneAxe: "
					+ plugin.Settings.WCStoneAxe);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3) + " IronAxe: "
					+ plugin.Settings.WCIronAxe);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3) + " GoldAxe: "
					+ plugin.Settings.WCGoldAxe);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3) + " DiamondAxe: "
					+ plugin.Settings.WCDiamondAxe);
		} else if ((split[1].equalsIgnoreCase("mine")
				|| split[1].equalsIgnoreCase("m") || split[1]
				.equalsIgnoreCase("mining"))
				&& plugin.Settings.enableMineLevel == true) {
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1)
					+ "[LC] ---LevelCraftPlugin By Samkio--- ");
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3) + " WoodenPick: "
					+ plugin.Settings.MIWoodPick);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3) + " StonePick: "
					+ plugin.Settings.MIStonePick);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3) + " IronPick: "
					+ plugin.Settings.MIIronPick);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3) + " GoldPick: "
					+ plugin.Settings.MIGoldPick);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3) + " DiamondPick: "
					+ plugin.Settings.MIDiamondPick);
		} else if ((split[1].equalsIgnoreCase("slay")
				|| split[1].equalsIgnoreCase("s") || split[1]
				.equalsIgnoreCase("slaying"))
				&& plugin.Settings.enableSlayerLevel == true) {
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1)
					+ "[LC] ---LevelCraftPlugin By Samkio--- ");
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3) + " WoodenSword: "
					+ plugin.Settings.SlayWoodSword);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3) + " StoneSword: "
					+ plugin.Settings.SlayStoneSword);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3) + " IronSword: "
					+ plugin.Settings.SlayIronSword);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3) + " GoldSword: "
					+ plugin.Settings.SlayGoldSword);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3) + " DiamondSword: "
					+ plugin.Settings.SlayDiamondSword);
		} else if ((split[1].equalsIgnoreCase("range")
				|| split[1].equalsIgnoreCase("r") || split[1]
				.equalsIgnoreCase("ranging"))
				&& plugin.Settings.enableRangeLevel == true) {
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1)
					+ "[LC] ---LevelCraftPlugin By Samkio--- ");
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3)
					+ " 0.5 Damage per Arrow: " + plugin.Settings.Rangep5);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3)
					+ " 1.0 Damage per Arrow: " + plugin.Settings.Range1p0);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3)
					+ " 1.5 Damage per Arrow: " + plugin.Settings.Range1p5);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3)
					+ " 2.0 Damage per Arrow: " + plugin.Settings.Range2p0);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3)
					+ " 2.5 Damage per Arrow: " + plugin.Settings.Range2p5);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3)
					+ " 3.0 Damage per Arrow: " + plugin.Settings.Range3p0);
		} else if ((split[1].equalsIgnoreCase("fist")
				|| split[1].equalsIgnoreCase("c") || split[1]
				.equalsIgnoreCase("fisticuffs"))
				&& plugin.Settings.enableFisticuffsLevel == true) {
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1)
					+ "[LC] ---LevelCraftPlugin By Samkio--- ");
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3) + " WoodenSword: "
					+ plugin.Settings.FisticuffsWoodSword);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3) + " StoneSword: "
					+ plugin.Settings.FisticuffsStoneSword);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3) + " IronSword: "
					+ plugin.Settings.FisticuffsIronSword);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3) + " GoldSword: "
					+ plugin.Settings.FisticuffsGoldSword);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3) + " DiamondSword: "
					+ plugin.Settings.FisticuffsDiamondSword);
		} else if ((split[1].equalsIgnoreCase("archer")
				|| split[1].equalsIgnoreCase("a") || split[1]
				.equalsIgnoreCase("archery"))
				&& plugin.Settings.enableArcherLevel == true) {
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1)
					+ "[LC] ---LevelCraftPlugin By Samkio--- ");
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3)
					+ " 0.5 Damage per Arrow: " + plugin.Settings.Archerp5);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3)
					+ " 1.0 Damage per Arrow: " + plugin.Settings.Archer1p0);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3)
					+ " 1.5 Damage per Arrow: " + plugin.Settings.Archer1p5);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3)
					+ " 2.0 Damage per Arrow: " + plugin.Settings.Archer2p0);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3)
					+ " 2.5 Damage per Arrow: " + plugin.Settings.Archer2p5);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3)
					+ " 3.0 Damage per Arrow: " + plugin.Settings.Archer3p0);
		} else if ((split[1].equalsIgnoreCase("digger")
				|| split[1].equalsIgnoreCase("d") || split[1]
				.equalsIgnoreCase("digging"))
				&& plugin.Settings.enableDigLevel == true) {
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1)
					+ "[LC] ---LevelCraftPlugin By Samkio--- ");
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3) + " WoodenShovel: "
					+ plugin.Settings.DWoodShov);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3) + " StoneShovel: "
					+ plugin.Settings.DStoneShov);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3) + " IronShovel: "
					+ plugin.Settings.DIronShov);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3) + " GoldShovel: "
					+ plugin.Settings.DGoldShov);
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c3) + " DiamondShovel: "
					+ plugin.Settings.DDiamondShov);
		} else {
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c2) + " Stat not found.");
			sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
					+ ChatColor.valueOf(plugin.Settings.c2)
					+ " Please type '/level list' to show all stats.");
		}
	}

	public static void Total(CommandSender sender) {
		int totalLevel = 0;
		double totalExp = 0;
		String highestLevel = "None";
		int level = 0;
		if (plugin.Settings.enableWCLevel == true
				&& !Whitelist.isAvoid((Player) sender, "w")) {
			totalLevel = totalLevel + Level.getLevel(sender, "w");
			totalExp = totalExp + Level.getExp(sender, "w");
			if (Level.getLevel(sender, "w") > level)
				highestLevel = "WoodCutting";
		}
		if (plugin.Settings.enableMineLevel == true
				&& !Whitelist.isAvoid((Player) sender, "m")) {
			totalLevel = totalLevel + Level.getLevel(sender, "m");
			totalExp = totalExp + Level.getExp(sender, "m");
			if (Level.getLevel(sender, "n") > level)
				highestLevel = "Mining";
		}
		if (plugin.Settings.enableSlayerLevel == true
				&& !Whitelist.isAvoid((Player) sender, "s")) {
			totalLevel = totalLevel + Level.getLevel(sender, "s");
			totalExp = totalExp + Level.getExp(sender, "s");
			if (Level.getLevel(sender, "s") > level)
				highestLevel = "Slayer";
		}
		if (plugin.Settings.enableRangeLevel == true
				&& !Whitelist.isAvoid((Player) sender, "r")) {
			totalLevel = totalLevel + Level.getLevel(sender, "r");
			totalExp = totalExp + Level.getExp(sender, "r");
			if (Level.getLevel(sender, "r") > level)
				highestLevel = "Range";
		}
		if (plugin.Settings.enableFisticuffsLevel == true
				&& !Whitelist.isAvoid((Player) sender, "c")) {
			totalLevel = totalLevel + Level.getLevel(sender, "c");
			totalExp = totalExp + Level.getExp(sender, "c");
			if (Level.getLevel(sender, "c") > level)
				highestLevel = "Fisticuffs";
		}
		if (plugin.Settings.enableArcherLevel == true
				&& !Whitelist.isAvoid((Player) sender, "a")) {
			totalLevel = totalLevel + Level.getLevel(sender, "a");
			totalExp = totalExp + Level.getExp(sender, "a");
			if (Level.getLevel(sender, "a") > level)
				highestLevel = "Archer";
		}
		if (plugin.Settings.enableDigLevel == true
				&& !Whitelist.isAvoid((Player) sender, "d")) {
			totalLevel = totalLevel + Level.getLevel(sender, "d");
			totalExp = totalExp + Level.getExp(sender, "d");
			if (Level.getLevel(sender, "d") > level)
				highestLevel = "Digging";
		}
		if (plugin.Settings.enableForgeLevel == true
				&& !Whitelist.isAvoid((Player) sender, "f")) {
			totalLevel = totalLevel + Level.getLevel(sender, "f");
			totalExp = totalExp + Level.getExp(sender, "f");
			if (Level.getLevel(sender, "f") > level)
				highestLevel = "Forge";
		}
		sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1)
				+ "[LC] ---LevelCraftPlugin By Samkio--- ");
		sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
				+ ChatColor.valueOf(plugin.Settings.c3) + " TotalLevel: " + totalLevel);
		sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
				+ ChatColor.valueOf(plugin.Settings.c3) + " TotalExp: " + totalExp);
		sender.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
				+ ChatColor.valueOf(plugin.Settings.c3) + " HighestLevel: "
				+ highestLevel);
	}

}
