package me.samkio.levelcraft;

import org.bukkit.entity.Player;

public class Whitelist {
	public static boolean isAdmin(Player player) {
		if (Settings.UsePerms) {
			if (Levelcraft.Permissions.has(player, "lc.admin")) {
				return true;
			}

		} else {
			for (int i = 0; i < Settings.LCAdmins.length; i++) {
				if (Settings.LCAdmins[i].equalsIgnoreCase(player.getName())) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isAvoid(Player player, String level) {
		if (Settings.UsePerms) {
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
					&& level.equalsIgnoreCase("F")) {
				return false;
			} else if (Levelcraft.Permissions.has(player, "lc.level.range")
					&& level.equalsIgnoreCase("R")) {
				return false;
			} else if (Levelcraft.Permissions.has(player, "lc.level.archery")
					&& level.equalsIgnoreCase("A")) {
				return false;
			}else{
				return true;
			}

		} else {
			for (int i = 0; i < Settings.LCAvoiders.length; i++) {
				if (Settings.LCAvoiders[i].equalsIgnoreCase(player.getName())) {
					return true;
				}
			}
		}
		return false;
	}
}
