package me.samkio.levelcraft;

import org.bukkit.entity.Player;

public class Whitelist {
	public static Levelcraft plugin;
	public static boolean isAdmin(Player player) {
		if (plugin.Settings.UsePerms) {
			if (Levelcraft.Permissions.has(player, "lc.admin")) {
				return true;
			}

		} else {
			for (int i = 0; i < plugin.Settings.LCAdmins.length; i++) {
				if (plugin.Settings.LCAdmins[i].equalsIgnoreCase(player.getName())) {
					return true;
				}
			}
		}
		return false;
	}
	public static boolean isShouter(Player player) {
		if (plugin.Settings.UsePerms) {
			if (Levelcraft.Permissions.has(player, "lc.shout")) {
				return true;
			}

		} 
		return false;
	}

	public static boolean isAvoid(Player player, String level) {
		if (plugin.Settings.UsePerms) {
			if (Levelcraft.Permissions.has(player, "lc.level.woodcut")
					&& level.equalsIgnoreCase("W")) {
				return false;
			} else if (Levelcraft.Permissions.has(player, "lc.level.mining")
					&& level.equalsIgnoreCase("M")) {
				return false;
			} else if (Levelcraft.Permissions.has(player, "lc.level.slaying")
					&& level.equalsIgnoreCase("S")) {
				return false;
			} else if (Levelcraft.Permissions.has(player, "lc.level.fisticuffs")
					&& level.equalsIgnoreCase("C")) {
				return false;
			} else if (Levelcraft.Permissions.has(player, "lc.level.range")
					&& level.equalsIgnoreCase("R")) {
				return false;
			} else if (Levelcraft.Permissions.has(player, "lc.level.archery")
					&& level.equalsIgnoreCase("A")) {
				return false;
			} else if (Levelcraft.Permissions.has(player, "lc.level.digging")
					&& level.equalsIgnoreCase("D")) {
				return false;
			} else if (Levelcraft.Permissions.has(player, "lc.level.forge")
					&& level.equalsIgnoreCase("F")) {
				return false;
			}else{
				return true;
			}

		} else {
			for (int i = 0; i < plugin.Settings.LCAvoiders.length; i++) {
				if (plugin.Settings.LCAvoiders[i].equalsIgnoreCase(player.getName())) {
					return true;
				}
			}
		}
		return false;
	}
}
