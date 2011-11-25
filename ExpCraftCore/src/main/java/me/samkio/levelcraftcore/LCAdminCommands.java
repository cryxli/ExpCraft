package me.samkio.levelcraftcore;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class LCAdminCommands {
	public LevelCraftCore plugin;

	public LCAdminCommands(final LevelCraftCore instance) {
		plugin = instance;
	}

	@SuppressWarnings("static-access")
	public void determineMethid(final Player sender, final String[] args) {
		if (args.length >= 4) {
			if (args[1].equalsIgnoreCase("setlvl") && args.length == 5) {
				if (plugin.LCAdminCommands.setLevel(args[2], args[3], args[4],
						sender)) {
					plugin.LCChat.good(sender, plugin.lang.LevelSetSuccess);
					return;
				} else {
					plugin.LCChat.warn(sender, plugin.lang.LevelSetFalse);
					return;
				}
			} else if (args[1].equalsIgnoreCase("setexp") && args.length == 5) {
				if (plugin.LCAdminCommands.setExp(args[2], args[3], args[4],
						sender)) {
					plugin.LCChat
							.good(sender, plugin.lang.ExperienceSetSuccess);
					return;
				} else {
					plugin.LCChat.warn(sender, plugin.lang.ExperienceSetFalse);
					return;
				}
			} else if (args[1].equalsIgnoreCase("getlvl") && args.length == 4) {
				plugin.LCChat.good(sender, plugin.LCAdminCommands.getLevel(
						args[2], args[3], sender));
			} else if (args[1].equalsIgnoreCase("getexp") && args.length == 4) {
				plugin.LCChat
						.good(sender, plugin.LCAdminCommands.getExp(args[2],
								args[3], sender));
			} else if (args[1].equalsIgnoreCase("reset") && args.length == 4) {
				if (plugin.LCAdminCommands.reset(args[2], args[3], sender)) {
					plugin.LCChat.good(sender,
							plugin.lang.ExperienceResetSuccess);
					return;
				} else {
					plugin.LCChat
							.warn(sender, plugin.lang.ExperienceResetFalse);
					return;
				}
			} else {
				plugin.LCAdminCommands.syntax(sender);
				return;
			}

		} else if (args[1].equalsIgnoreCase("reload")) {
			if (plugin.reload()) {
				plugin.LCChat.good(sender, "Reload Successful");
				return;
			} else {
				plugin.LCChat.bad(sender, "Reload Unsuccessful");
				return;
			}
		} else if (args[1].equalsIgnoreCase("purge")) {
			plugin.LCAdminCommands.purge(sender);
		}

	}

	@SuppressWarnings("static-access")
	private String getExp(final String ref, final String p, final Player pl) {
		if (!plugin.Permissions.hasAdminCommand(pl, "getexp")) {
			return "You do not have Permission";
		}
		List<Player> players = plugin.getServer().matchPlayer(p);
		if (players.size() == 0) {
			plugin.LCChat.warn(pl, plugin.lang.NoMatch);
			return "Error.";
		} else if (players.size() != 1) {
			plugin.LCChat.warn(pl, plugin.lang.TooMany);
			return "Error.";
		} else {
			Player leveler = players.get(0);
			for (Plugin plug : plugin.LevelReferenceKeys.keySet()) {
				String[] reference = plugin.LevelReferenceKeys.get(plug);
				if (plugin.Tools.containsValue(reference, ref)
						&& plugin.Permissions.hasLevel(leveler, plug)) {
					return leveler.getName() + "'s "
							+ plugin.LevelNames.get(plug)
							+ plugin.lang.ExperienceIs
							+ plugin.LevelFunctions.getExp(leveler, plug);
				}
			}

		}
		return "Error";
	}

	@SuppressWarnings("static-access")
	private String getLevel(final String ref, final String p, final Player pl) {
		if (!plugin.Permissions.hasAdminCommand(pl, "getlvl")) {
			return "You do not have Permission";
		}
		List<Player> players = plugin.getServer().matchPlayer(p);
		if (players.size() == 0) {
			plugin.LCChat.warn(pl, plugin.lang.NoMatch);
			return "Error.";
		} else if (players.size() != 1) {
			plugin.LCChat.warn(pl, plugin.lang.TooMany);
			return "Error.";
		} else {
			Player leveler = players.get(0);
			for (Plugin plug : plugin.LevelReferenceKeys.keySet()) {
				String[] reference = plugin.LevelReferenceKeys.get(plug);
				if (plugin.Tools.containsValue(reference, ref)
						&& plugin.Permissions.hasLevel(leveler, plug)) {
					return leveler.getName() + "'s "
							+ plugin.LevelNames.get(plug) + plugin.lang.LevelIs
							+ plugin.LevelFunctions.getLevel(leveler, plug);
				}
			}

		}
		return "Error";
	}

	public void help(final Player sender) {
		LCChat.topBar(sender);
		if (Whitelist.hasAdminCommand(sender, "setlvl")) {
			LCChat.info(sender, "/lvl admin setlvl <Ref> <Player> <Level> - "
					+ plugin.lang.SetLevelForPlayers);
		}
		if (Whitelist.hasAdminCommand(sender, "setexp")) {
			LCChat.info(sender, "/lvl admin setexp <Ref> <Player> <Exp> - "
					+ plugin.lang.SetExperienceForPlayers);
		}
		if (Whitelist.hasAdminCommand(sender, "getexp")) {
			LCChat.info(sender, "/lvl admin getexp <Ref> <Player> - "
					+ plugin.lang.GetExperienceOfPlayer);
		}
		if (Whitelist.hasAdminCommand(sender, "getlvl")) {
			LCChat.info(sender, "/lvl admin getlvl <Ref> <Player> - "
					+ plugin.lang.GetLevelOfPlayer);
		}
		if (Whitelist.hasAdminCommand(sender, "reset")) {
			LCChat.info(sender, "/lvl admin reset <Ref> <Player>  - "
					+ plugin.lang.ResetExperience);
		}
		if (Whitelist.hasAdminCommand(sender, "reload")) {
			LCChat.info(sender, "/lvl admin reload  - Reloads LevelCraftCore");
		}
		if (Whitelist.hasAdminCommand(sender, "purge")) {
			LCChat.info(sender, "/lvl admin purge  - Removes old Players.");
		}
		// plugin.LCChat.info(sender, "/lvl admin reload - Shows this.");
		LCChat.info(sender, "/lvl admin - " + plugin.lang.ShowsThis);
	}

	public void purge(final Player sender) {
		if (!Whitelist.hasAdminCommand(sender, "purge")) {
			plugin.LCChat.bad(sender, "You do not have permission..");
			return;
		}
		if (plugin.database.equalsIgnoreCase("FlatFile")) {
			if (plugin.FlatFile.purge()) {
				LCChat.good(sender, "FlatFiles Purged.");
			} else {
				plugin.LCChat.bad(sender, "Failed to Purge FlatFiles.");
			}
			return;
		} else {
			plugin.LCChat.bad(sender,
					"Purge only avaible for FlatFiles at the moment.");
		}
	}

	private boolean reset(final String ref, final String p, final Player pl) {
		if (!Whitelist.hasAdminCommand(pl, "reset")) {
			return false;
		}
		if (plugin.LCAdminCommands.setExp(ref, p, "0", pl)) {
			return true;
		}
		return false;
	}

	private boolean setExp(final String ref, final String p, final String exp,
			final Player pl) {
		if (!Whitelist.hasAdminCommand(pl, "setexp")) {
			return false;
		}
		double newexp = Double.parseDouble(exp);
		List<Player> players = plugin.getServer().matchPlayer(p);
		if (players.size() == 0) {
			LCChat.warn(pl, plugin.lang.NoMatch);
			return false;
		} else if (players.size() != 1) {
			LCChat.warn(pl, plugin.lang.TooMany);
			return false;
		} else {
			Player leveler = players.get(0);
			for (Plugin plug : plugin.LevelReferenceKeys.keySet()) {
				String[] reference = plugin.LevelReferenceKeys.get(plug);
				if (plugin.Tools.containsValue(reference, ref)
						&& Whitelist.hasLevel(leveler, plug)) {
					LevelFunctions.updateExp(leveler, plug, newexp);
					return true;
				}
			}

		}
		return false;
	}

	@SuppressWarnings("static-access")
	private boolean setLevel(final String ref, final String p,
			final String lvl, final Player pl) {
		if (!plugin.Permissions.hasAdminCommand(pl, "setlvl")) {
			return false;
		}
		int newlvl = Integer.parseInt(lvl);
		List<Player> players = plugin.getServer().matchPlayer(p);
		if (players.size() == 0) {
			plugin.LCChat.warn(pl, plugin.lang.NoMatch);
			return false;
		} else if (players.size() != 1) {
			plugin.LCChat.warn(pl, plugin.lang.TooMany);
			return false;
		} else {
			Player leveler = players.get(0);
			for (Plugin plug : plugin.LevelReferenceKeys.keySet()) {
				String[] reference = plugin.LevelReferenceKeys.get(plug);
				if (plugin.Tools.containsValue(reference, ref)
						&& plugin.Permissions.hasLevel(leveler, plug)) {
					plugin.LevelFunctions.updateLevel(leveler, plug, newlvl);
					return true;
				}
			}

		}
		return false;
	}

	public void syntax(final Player sender) {
		LCChat.warn(sender, plugin.lang.SyntaxA);
		return;
	}
}
