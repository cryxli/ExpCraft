/*
 * Copyright (c) 2011 Urs P. Stettler, https://github.com/cryxli
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package li.cryx.expcraft.cmd;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import li.cryx.expcraft.IExpCraft;
import li.cryx.expcraft.i18n.LangKeys;
import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.util.Chat;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * This class handles commands sent to ExpCraftCore
 * 
 * @author cryxli
 */
public class CommandManager {

	/** Reference to the core. */
	private final IExpCraft core;

	/** The chat utility to send answers to the requesting player. */
	private final Chat chat;

	/** Create a new manager for the given core. */
	public CommandManager(final IExpCraft core) {
		this.core = core;
		chat = new Chat(core);
	}

	/**
	 * Display all level stats a player has reached so far.
	 * 
	 * @param sender
	 *            Current player
	 */
	private void executeCmdAll(final CommandSender sender, final Player player) {
		List<String> levels = new ArrayList<String>();
		for (ExpCraftModule module : core.getModules()) {
			if (core.getPermissions().hasLevel(module, player)) {
				levels.add(MessageFormat.format("{0} ({1}): {2}", //
						chat.translate(sender, module), //
						module.getInfo().getAbbr(), //
						core.getPersistence().getLevel(module, player)));
			}
		}

		if (levels.isEmpty()) {
			chat.warn(sender, LangKeys.LEVEL_NOT_FOUND);
		} else {
			chat.topBar(sender);
			Collections.sort(levels);
			for (String line : levels) {
				chat.infoPlain(sender, line);
			}
		}
	}

	/**
	 * Send experience of given player for given module to given sender.
	 * 
	 * @param sender
	 *            The player issuing the command.
	 * @param player
	 *            The player who's experience should be queried.
	 * @param modAbbr
	 *            Short name of the module.
	 */
	private void executeCmdGetExp(final CommandSender sender,
			final Player player, final String modAbbr) {

		ExpCraftModule module = core.getModuleByAbbr(modAbbr);
		if (module == null) {
			// module not found
			chat.info(sender, LangKeys.MODULE_NOT_FOUND);

		} else if (sender != player
				|| core.getPermissions().hasLevel(module, player)) {

			// display
			chat.info(sender, //
					LangKeys.PLUGIN_GET_EXP, //
					module, //
					module.getInfo().getAbbr(), //
					core.getPersistence().getLevel(module, player), //
					core.getPersistence().getExp(module, player) //
			);

		} else {
			// no permission
			chat.bad(sender, LangKeys.NOT_ALLOWED);
		}
	}

	/**
	 * Display informaion about ExpCraft, namely the commands the given player
	 * can issue.
	 * 
	 * @param sender
	 *            Current player
	 */
	private void executeCmdInfoCore(final CommandSender sender,
			final Player player) {
		chat.topBar(sender);
		chat.info(sender, LangKeys.PLUGIN_COMMANDS);
		chat.infoPlain(sender, " /lvl all");
		if (player != null) {
			chat.infoPlain(sender, " /lvl info <Module> [Page]");
		}
		if (player == null
				|| core.getPermissions().hasAdminCommand(player, "get")) {
			chat.infoPlain(sender, " /lvl getExp <Module> [Player]");
			chat.infoPlain(sender, " /lvl getLvl <Module> [Player]");
		} else {
			chat.infoPlain(sender, " /lvl getExp <Module>");
			chat.infoPlain(sender, " /lvl getLvl <Module>");
		}
		if (player == null
				|| core.getPermissions().hasAdminCommand(player, "set")) {
			chat.infoPlain(sender, " /lvl setExp <Module> <Value> [Player]");
			chat.infoPlain(sender, " /lvl setLvl <Module> <Value> [Player]");
		}
	}

	/**
	 * Display information about the given module.
	 * 
	 * @param sender
	 *            ConsoleSender requesting the information
	 * @param modAbbr
	 *            Short identifier for a module
	 */
	// TODO i18n
	private void executeCmdInfoModule(final CommandSender sender,
			final Player player, final String modAbbr) {
		ExpCraftModule module = core.getModuleByAbbr(modAbbr);
		if (module == null) {
			// module not found
			chat.info(sender, LangKeys.MODULE_NOT_FOUND);
		} else {
			// have the module display its info
			module.displayInfo(player);
		}
	}

	/**
	 * Change the experience of a player and module
	 * 
	 * @param sender
	 *            ConsoleSender issuing the command
	 * @param player
	 *            Target player for command.
	 * @param modAbbr
	 *            Short identifier for a module
	 * @param exp
	 *            New amount of experience points.
	 */
	private void executeCmdSetExp(final CommandSender sender,
			final Player player, final String modAbbr, final double exp) {
		ExpCraftModule module = core.getModuleByAbbr(modAbbr);
		if (module == null) {
			// module not found
			chat.info(player, LangKeys.MODULE_NOT_FOUND);
		} else if (sender != player
				|| core.getPermissions().hasLevel(module, player)) {
			// execute
			core.getPersistence().setExp(module, player, exp);
			// display
			chat.info(sender, LangKeys.EXP_INFO, //
					module, //
					module.getInfo().getAbbr(), //
					exp //
			);
		} else {
			// no permission
			msgNoPermission(sender);
		}
	}

	/**
	 * Change the level of a player and module
	 * 
	 * @param sender
	 *            CommandSender issuing the command
	 * @param player
	 *            Target player for the command
	 * @param modAbbr
	 *            Short identifier for a module
	 * @param level
	 *            New level
	 */
	private void executeCmdSetLevel(final CommandSender sender,
			final Player player, final String modAbbr, final int level) {
		ExpCraftModule module = core.getModuleByAbbr(modAbbr);
		if (module == null) {
			// module not found
			chat.info(sender, LangKeys.MODULE_NOT_FOUND);
		} else if (sender != player
				|| core.getPermissions().hasAdminCommand(player, "set")) {
			// execute
			core.getPersistence().setLevel(module, player, level);
			// display
			chat.info(sender, //
					LangKeys.LEVEL_INFO, //
					module, //
					module.getInfo().getAbbr(), //
					level //
			);
		} else {
			// no permission
			msgNoPermission(sender);
		}
	}

	private void msgNoPermission(final CommandSender sender) {
		chat.bad(sender, LangKeys.CMD_NOT_ALLOWED);
	}

	/**
	 * Process a command received by the core.
	 * 
	 * @param sender
	 *            CommandSender issuing the command
	 * @param cmd
	 *            The ExCraft sub-command
	 * @param args
	 *            Optional arguments for the command
	 */
	public void onCommand(final CommandSender sender, final String... args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}

		if (args == null || args.length == 0) {
			preprocessCmdInfo(sender, player, new String[] { "info" });

		} else if ("all".equalsIgnoreCase(args[0])) {
			preprocessCmdAll(sender, player, args);

		} else if ("getexp".equalsIgnoreCase(args[0])
				|| "getlevel".equalsIgnoreCase(args[0])
				|| "getlvl".equalsIgnoreCase(args[0])) {
			preprocessCmdGetExp(sender, player, args);

		} else if ("setexp".equalsIgnoreCase(args[0])) {
			preprocessCmdSetExp(sender, player, args);

		} else if ("setlevel".equalsIgnoreCase(args[0])
				|| "setlvl".equalsIgnoreCase(args[0])) {
			preprocessCmdSetLevel(sender, player, args);

		} else {
			preprocessCmdInfo(sender, player, args);
		}
	}

	private void preprocessCmdAll(final CommandSender sender, Player player,
			final String[] args) {
		if (player == null) {
			// console command
			if (args.length >= 2) {
				player = core.getServer().getPlayer(args[1]);
				if (player == null) {
					// no player found
					chat.info(sender, LangKeys.PLAYER_OFFLINE, args[1]);
				} else {
					executeCmdAll(sender, player);
				}
			} else {
				chat.infoPlain(sender, "Syntax: /level all <Player>");
			}

		} else if (args.length == 1) {
			// for player, by player
			executeCmdAll(sender, player);

		} else if (args.length >= 2
				&& core.getPermissions().hasAdminCommand(player, "get")) {
			// for player, by op
			player = core.getServer().getPlayer(args[1]);
			if (player == null) {
				// no player found
				chat.info(sender, LangKeys.PLAYER_OFFLINE, args[1]);
			} else {
				executeCmdAll(sender, player);
			}
		} else {

			msgNoPermission(sender);
		}
	}

	private void preprocessCmdGetExp(final CommandSender sender,
			final Player player, final String[] args) {

		CmdArgs cmdArgs = preprocessTwoArgCmd(sender, player,
				"getExp <Module>", "get", args);
		if (cmdArgs != null) {
			executeCmdGetExp(cmdArgs.sender, cmdArgs.player, cmdArgs.args[0]);
		}
	}

	private void preprocessCmdInfo(final CommandSender sender,
			final Player player, final String[] args) {

		// handle the following cases:
		// - /level info
		// - /level info <module>
		// - /level
		// - /level <module>
		int len = 0;
		if ("info".equalsIgnoreCase(args[0])) {
			len = 1;
		}

		if (args.length < 1 + len) {
			// no module stated
			executeCmdInfoCore(sender, player);

		} else if (player != null && args.length == 1 + len) {
			// only module, no page
			executeCmdInfoModule(sender, player, args[len]);

		} else {
			executeCmdInfoCore(sender, player);
		}
	}

	private void preprocessCmdSetExp(final CommandSender sender,
			final Player player, final String[] args) {

		CmdArgs cmdArgs = preprocessThreeArgCmd(sender, player,
				"setExp <Module> <Value>", "set", args);
		if (cmdArgs != null) {
			try {
				double exp = Double.parseDouble(cmdArgs.args[1]);
				executeCmdSetExp(cmdArgs.sender, cmdArgs.player,
						cmdArgs.args[0], exp);
			} catch (NumberFormatException e) {
				chat.infoPlain(sender,
						"Syntax: /level setExp <Module> <Value> [Player]");
				chat.info(sender, LangKeys.VALUE_FLOAT);
			}
		}
	}

	private void preprocessCmdSetLevel(final CommandSender sender,
			final Player player, final String[] args) {

		CmdArgs cmdArgs = preprocessThreeArgCmd(sender, player,
				"setLevel <Module> <Value>", "set", args);
		if (cmdArgs != null) {
			try {
				int level = Integer.parseInt(cmdArgs.args[1]);
				executeCmdSetLevel(cmdArgs.sender, cmdArgs.player,
						cmdArgs.args[0], level);
			} catch (NumberFormatException e) {
				chat.infoPlain(sender,
						"Syntax: /level setLevel <Module> <Value> [Player]");
				chat.info(sender, LangKeys.VALUE_INTEGER);
			}
		}
	}

	private CmdArgs preprocessThreeArgCmd(final CommandSender sender,
			Player player, final String cmdInfo, final String adminCmd,
			final String[] args) {

		if (player == null) {
			// console command
			if (args.length >= 4) {
				player = core.getServer().getPlayer(args[3]);
				if (player == null) {
					// no player found
					chat.info(sender, LangKeys.PLAYER_OFFLINE, args[3]);
				} else {
					return new CmdArgs(sender, player, args[1], args[2]);
				}
			} else {
				chat.infoPlain(sender, "Syntax: /level " + cmdInfo
						+ " <Player>");
			}
		} else if (args.length < 3) {
			// wrong number of arguments
			chat.infoPlain(sender, "Syntax: /level " + cmdInfo + " [Player]");
		} else if (args.length == 3) {
			// for player, by player
			return new CmdArgs(sender, player, args[1], args[2]);
		} else if (args.length >= 3
				&& core.getPermissions().hasAdminCommand(player, adminCmd)) {
			// for player, by op
			player = core.getServer().getPlayer(args[3]);
			if (player == null) {
				// no player found
				chat.info(sender, LangKeys.PLAYER_OFFLINE, args[3]);
			} else {
				return new CmdArgs(sender, player, args[1], args[2]);
			}
		} else {

			msgNoPermission(sender);
		}

		return null;
	}

	private CmdArgs preprocessTwoArgCmd(final CommandSender sender,
			Player player, final String cmdInfo, final String adminCmd,
			final String[] args) {

		if (player == null) {
			// console command
			if (args.length >= 3) {
				player = core.getServer().getPlayer(args[2]);
				if (player == null) {
					// no player found
					chat.info(sender, LangKeys.PLAYER_OFFLINE, args[2]);
				} else {
					return new CmdArgs(sender, player, args[1]);
				}
			} else {
				chat.infoPlain(sender, "Syntax: /level " + cmdInfo
						+ " <Player>");
			}

		} else if (args.length < 2) {
			// wrong number of arguments
			chat.infoPlain(sender, "Syntax: /level " + cmdInfo + " [Player]");

		} else if (args.length == 2) {
			// for player, by player
			return new CmdArgs(sender, player, args[1]);

		} else if (args.length >= 2
				&& core.getPermissions().hasAdminCommand(player, adminCmd)) {
			// for player, by op
			player = core.getServer().getPlayer(args[2]);
			if (player == null) {
				// no player found
				chat.info(sender, LangKeys.PLAYER_OFFLINE, args[2]);
			} else {
				return new CmdArgs(sender, player, args[1]);
			}

		} else {

			msgNoPermission(sender);
		}

		return null;
	}
}
