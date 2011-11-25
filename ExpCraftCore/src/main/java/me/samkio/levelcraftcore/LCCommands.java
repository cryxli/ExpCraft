package me.samkio.levelcraftcore;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class LCCommands {
	public LevelCraftCore plugin;

	public LCCommands(final LevelCraftCore instance) {
		plugin = instance;
	}

	public void about(final CommandSender sender) {
		LCChat.topBar(sender);
		LCChat.info(sender, "/lvl list - " + plugin.lang.ShowsActive);
		LCChat.info(sender, "/lvl notify - " + plugin.lang.ToggleNote);
		LCChat.info(sender, "/lvl " + plugin.Tools.getIndexBar((Player) sender)
				+ " - " + plugin.lang.ShowsLevelStats);
		LCChat.info(sender, "/lvl unlocks [REF] <Page> - "
				+ plugin.lang.ShowsToolBlock);
		LCChat.info(sender, "/lvl exp [REF] <Page> - " + plugin.lang.ShowsExp);
		LCChat.info(sender, "/lvl shout [REF] - " + plugin.lang.Shout);
		LCChat.info(sender, "/lvl total - " + plugin.lang.ShowsTotal);
		LCChat.info(sender, "/lvl all - " + plugin.lang.ShowsAll);
		LCChat.info(sender, "/lvl rank [REF] - " + plugin.lang.ShowsRank);
		LCChat.info(sender, "/lvl top [REF] - " + plugin.lang.ShowsTop);
		LCChat.info(sender, "/lvl help [REF] - " + plugin.lang.ShowsHelp);

		if (plugin.EnableCapes) {
			LCChat.info(sender, "/lvl cape [REF] - Selects Master Cape");
		}
		LCChat.info(sender, "/lvl  - " + plugin.lang.ShowsThis);
		if (plugin.Permissions.isAdmin(sender)) {
			LCChat.good(sender, "/lvl admin - " + plugin.lang.AdminCommands);
		}
		return;

	}

	private void Admin(final Player sender, final String[] args) {
		if (!plugin.Permissions.isAdmin(sender)) {
			LCChat.warn(sender, plugin.lang.YouDoNotHavePermission);
			return;
		}
		if (args.length <= 1) {
			plugin.LCAdminCommands.help(sender);
			return;
		} else {
			plugin.LCAdminCommands.determineMethid(sender, args);
			return;
		}

	}

	public void all(final Player sender) {
		List<String> levels = new ArrayList<String>();
		for (Plugin p : plugin.LevelNames.keySet()) {
			if (Whitelist.hasLevel(sender, p)) {
				levels.add(MessageFormat.format("{0}, ({1}): {2}", //
						plugin.LevelNames.get(p), //
						plugin.LevelIndexes.get(p), //
						LevelFunctions.getLevel(sender, p)));
			}
		}

		if (levels.isEmpty()) {
			LCChat.warn(sender, plugin.lang.NoLevelFound);
		} else {
			LCChat.topBar(sender);
			for (String line : levels) {
				LCChat.info(sender, line);
			}
		}
	}

	public void credits(final CommandSender sender) {
		LCChat.topBar(sender);
		LCChat.good(sender, "LevelCraftCore by Samkio.");
		LCChat.good(sender, "SkillCapes by Indy12.");
		for (Plugin p : plugin.LevelAuthors.keySet()) {
			LCChat.good(sender, plugin.LevelNames.get(p) + " by "
					+ plugin.LevelAuthors.get(p) + ".");
		}
	}

	public void determineMethod(final Player sender, final String[] args) {
		if (args[0].equalsIgnoreCase("list")) {
			plugin.LCCommands.listLevels(sender);
		} else if (args[0].equalsIgnoreCase("total")) {
			plugin.LCCommands.Total(sender);
		} else if (args[0].equalsIgnoreCase("all")) {
			plugin.LCCommands.all(sender);
		} else if (args[0].equalsIgnoreCase("credits")) {
			plugin.LCCommands.credits(sender);
		} else if (args[0].equalsIgnoreCase("notify")) {
			plugin.LCCommands.Notify(sender);
		} else if (args[0].equalsIgnoreCase("admin")) {
			plugin.LCCommands.Admin(sender, args);
		} else if (args[0].equalsIgnoreCase("shout")) {
			if (args.length < 2) {
				LCChat.info(sender, "Syntax: /level shout [REF]");
			} else {
				plugin.LCCommands.Shout(sender, args[1]);
			}
		} else if (args[0].equalsIgnoreCase("unlocks")) {
			if (args.length < 2) {
				LCChat.info(sender, "Syntax: /level unlocks [REF] <PageNo>");
			} else if (args.length < 3) {
				plugin.LCCommands.Unlocks(sender, args[1], 1);
			} else {
				plugin.LCCommands.Unlocks(sender, args[1],
						plugin.Tools.convertToInt(args[2]));
			}
		} else if (args[0].equalsIgnoreCase("exp")) {
			if (args.length < 2) {
				LCChat.info(sender, "Syntax: /level exp [REF] <PageNo>");
			} else if (args.length < 3) {
				plugin.LCCommands.Exp(sender, args[1], 1);
			} else {
				plugin.LCCommands.Exp(sender, args[1],
						plugin.Tools.convertToInt(args[2]));
			}
		} else if (args[0].equalsIgnoreCase("rank")) {
			if (args.length < 2) {
				LCChat.info(sender, "Syntax: /level rank [REF]");
			} else {
				plugin.LCCommands.Rank(sender, args[1]);
			}
		} else if (args[0].equalsIgnoreCase("help")) {
			if (args.length < 2) {
				LCChat.info(sender, "Syntax: /level help [REF]");
			} else {
				plugin.LCCommands.LevelHelp(sender, args[1]);
			}
		} else if (args[0].equalsIgnoreCase("top")) {
			if (args.length < 2) {
				LCChat.info(sender, "Syntax: /level top [REF]");
			} else {
				plugin.LCCommands.top(sender, args[1]);
			}
		} else {
			if (args.length == 1) {
				plugin.LCCommands.showStat(sender, args[0]);
			} else {
				LCChat.info(sender,
						"No command found. /level - for all commands.");
			}
		}
	}

	private void Exp(final Player sender, final String string,
			final Integer page) {
		for (Plugin p : plugin.LevelReferenceKeys.keySet()) {
			String[] reference = plugin.LevelReferenceKeys.get(p);

			if (plugin.Tools.containsValue(reference, string)
					&& Whitelist.hasLevel(sender, p)) {
				LCChat.topBar(sender);
				String[] Exp = plugin.LevelExp.get(p);
				int maxPages = (Exp.length / plugin.ExpLines);
				if (maxPages <= 0) {
					maxPages = 1;
				}
				if (page > maxPages) {
					LCChat.warn(sender, "No Page.");
					return;
				}
				LCChat.info(sender, "Showing Experience Table for "
						+ plugin.LevelNames.get(p) + ". Page " + page + " of "
						+ maxPages);
				int startingPoint = page * plugin.ExpLines - plugin.ExpLines;
				int endingPoint = startingPoint + plugin.ExpLines;
				if (endingPoint > Exp.length) {
					endingPoint = Exp.length;
				}
				for (int i = startingPoint; i < endingPoint; i++) {
					LCChat.info(sender, Exp[i]);
				}

				/*
				 * for (String s : plugin.LevelExp.get(p)) {
				 * plugin.LCChat.info(sender, s); }
				 */
				return;
			}
		}
		LCChat.warn(sender, plugin.lang.NoLevelFound);
	}

	private void LevelHelp(final Player sender, final String string) {
		for (Plugin p : plugin.LevelReferenceKeys.keySet()) {
			String[] reference = plugin.LevelReferenceKeys.get(p);
			if (plugin.Tools.containsValue(reference, string)
					&& Whitelist.hasLevel(sender, p)) {
				LCChat.topBar(sender);
				if (plugin.LevelHelp.get(p) == null) {
					LCChat.info(sender, plugin.lang.NoHelpFileYet);
					return;
				}
				for (String s : plugin.LevelHelp.get(p)) {
					LCChat.info(sender, s);
				}
				break;
			}
		}
	}

	public void listLevels(final Player player) {
		StringBuffer buf = new StringBuffer();
		buf.append(plugin.lang.YourActiveLevels);
		boolean one = false;
		for (Plugin p : plugin.LevelNames.keySet()) {
			if (one && Whitelist.hasLevelExp(player, p)) {
				buf.append(",");
			}
			if (Whitelist.hasLevelExp(player, p)) {
				buf.append(plugin.LevelNames.get(p));
				buf.append("(");
				buf.append(plugin.LevelIndexes.get(p));
				buf.append(")");
				one = true;
			}
		}
		LCChat.info(player, buf.toString());
	}

	private void Notify(final Player sender) {
		plugin.Tools.toggleNotify(sender);
	}

	private void Rank(final Player sender, final String string) {

		for (Plugin p : plugin.LevelReferenceKeys.keySet()) {
			String[] reference = plugin.LevelReferenceKeys.get(p);
			if (plugin.Tools.containsValue(reference, string)
					&& Whitelist.hasLevel(sender, p)) {
				LCChat.info(
						sender,
						plugin.lang.YouAreNumber
								+ LevelFunctions.getPos(sender, string)
								+ " in " + plugin.LevelNames.get(p));
				return;
			}
		}
		LCChat.warn(sender, plugin.lang.NoLevelFound);
	}

	private void Shout(final Player sender, final String string) {
		if (!plugin.Permissions.canShout(sender)) {
			LCChat.warn(sender, plugin.lang.YouDoNotHavePermission);
			return;
		}
		if (string.equalsIgnoreCase("total")) {
			boolean oneStat = false;
			int TotalLevel = 0;
			for (Plugin p : plugin.LevelNames.keySet()) {
				if (Whitelist.hasLevel(sender, p)) {
					TotalLevel = TotalLevel
							+ LevelFunctions.getLevel(sender, p);
					oneStat = true;
				}
			}
			if (oneStat) {
				LCChat.broadcast(sender.getName() + "'s total "
						+ plugin.lang.LevelIs + +TotalLevel + ".");

			} else {
				LCChat.warn(sender, plugin.lang.NoLevelFound);
			}
			return;
		}
		for (Plugin p : plugin.LevelReferenceKeys.keySet()) {
			String[] reference = plugin.LevelReferenceKeys.get(p);
			if (plugin.Tools.containsValue(reference, string)
					&& Whitelist.hasLevel(sender, p)) {
				LCChat.broadcast(sender.getName() + "'s "
						+ plugin.LevelNames.get(p) + plugin.lang.LevelIs
						+ LevelFunctions.getLevel(sender, p) + ".");
				return;
			}
		}
		LCChat.info(sender, "You do not have that level to shout.");
	}

	public void showStat(final Player s, final String string) {
		for (Plugin p : plugin.LevelReferenceKeys.keySet()) {
			String[] reference = plugin.LevelReferenceKeys.get(p);
			if (plugin.Tools.containsValue(reference, string)
					&& Whitelist.hasLevel(s, p)) {
				LCChat.topBar(s);
				LCChat.info(s, plugin.LevelNames.get(p) + plugin.lang.LevelS
						+ LevelFunctions.getLevel(s, p));
				LCChat.info(
						s,
						plugin.LevelNames.get(p)
								+ plugin.lang.Experience
								+ plugin.Tools.roundTwoDecimals(LevelFunctions
										.getExp(s, p)));
				LCChat.info(
						s,
						plugin.lang.ExperienceToNextLevel
								+ plugin.Tools.roundTwoDecimals(LevelFunctions
										.getExpLeft(s, p)));
				return;
			}
		}
		LCChat.info(s, plugin.lang.None);
	}

	private void top(final Player sender, final String string) {
		for (Plugin p : plugin.LevelReferenceKeys.keySet()) {
			String[] reference = plugin.LevelReferenceKeys.get(p);
			if (plugin.Tools.containsValue(reference, string)) {
				LCChat.info(sender, "==" + plugin.lang.TopPlayersIn + " "
						+ plugin.LevelNames.get(p) + "==");
				LCChat.info(
						sender,
						"1. "
								+ LevelFunctions.getPlayerAtPos(string, 1)
								+ " ("
								+ LevelFunctions.getLevel(
										plugin.getServer().getPlayer(
												LevelFunctions.getPlayerAtPos(
														string, 1)), p) + ")");
				LCChat.info(
						sender,
						"2. "
								+ LevelFunctions.getPlayerAtPos(string, 2)
								+ " ("
								+ LevelFunctions.getLevel(
										plugin.getServer().getPlayer(
												LevelFunctions.getPlayerAtPos(
														string, 2)), p) + ")");
				LCChat.info(
						sender,
						"3. "
								+ LevelFunctions.getPlayerAtPos(string, 3)
								+ " ("
								+ LevelFunctions.getLevel(
										plugin.getServer().getPlayer(
												LevelFunctions.getPlayerAtPos(
														string, 3)), p) + ")");
				LCChat.info(
						sender,
						"4. "
								+ LevelFunctions.getPlayerAtPos(string, 4)
								+ " ("
								+ LevelFunctions.getLevel(
										plugin.getServer().getPlayer(
												LevelFunctions.getPlayerAtPos(
														string, 4)), p) + ")");
				LCChat.info(
						sender,
						"5. "
								+ LevelFunctions.getPlayerAtPos(string, 5)
								+ " ("
								+ LevelFunctions.getLevel(
										plugin.getServer().getPlayer(
												LevelFunctions.getPlayerAtPos(
														string, 5)), p) + ")");
				return;
			}
		}
		LCChat.warn(sender, plugin.lang.NoLevelFound);
	}

	private void Total(final Player sender) {
		boolean oneStat = false;
		int TotalLevel = 0;
		double TotalExp = 0;
		String HighestLevel = plugin.lang.None;
		int HighestLevelInt = 0;
		for (Plugin p : plugin.LevelNames.keySet()) {
			if (Whitelist.hasLevel(sender, p)) {
				TotalLevel = TotalLevel + LevelFunctions.getLevel(sender, p);
				TotalExp = TotalExp + LevelFunctions.getExp(sender, p);
				if (HighestLevelInt < LevelFunctions.getLevel(sender, p)) {
					HighestLevel = plugin.LevelNames.get(p);
					HighestLevelInt = LevelFunctions.getLevel(sender, p);
				}
				oneStat = true;
			}
		}
		if (oneStat) {
			LCChat.topBar(sender);
			LCChat.info(sender, plugin.lang.TotalLevel + TotalLevel);
			LCChat.info(sender, plugin.lang.TotalExp + TotalExp);
			LCChat.info(sender, plugin.lang.HighestLevel + HighestLevel);

		} else {
			LCChat.warn(sender, plugin.lang.NoLevelFound);
		}
	}

	private void Unlocks(final Player sender, final String string,
			final Integer page) {
		for (Plugin p : plugin.LevelReferenceKeys.keySet()) {
			String[] reference = plugin.LevelReferenceKeys.get(p);
			if (plugin.Tools.containsValue(reference, string)
					&& Whitelist.hasLevel(sender, p)) {
				LCChat.topBar(sender);
				String[] Unlocks = plugin.LevelUnlocks.get(p);
				int maxPages = (Unlocks.length / plugin.UnlockLines);
				if (maxPages <= 0) {
					maxPages = 1;
				}
				if (page > maxPages) {
					LCChat.warn(sender, "No Page.");
					return;
				}
				int[] UnlockLevel = plugin.LevelUnlocksLevel.get(p);
				int level = LevelFunctions.getLevel(sender, p);
				LCChat.info(sender,
						"Showing Unlocks for " + plugin.LevelNames.get(p)
								+ ". Page " + page + " of " + maxPages);
				int startingPoint = page * plugin.UnlockLines
						- plugin.UnlockLines;
				int endingPoint = startingPoint + plugin.UnlockLines;
				if (endingPoint > Unlocks.length) {
					endingPoint = Unlocks.length;
				}
				for (int i = startingPoint; i < endingPoint; i++) {
					if (UnlockLevel[i] > level) {
						LCChat.warn(sender, Unlocks[i]);
					} else {
						LCChat.good(sender, Unlocks[i]);
					}
				}
				return;
			}
		}
		LCChat.warn(sender, plugin.lang.NoLevelFound);
	}

}
