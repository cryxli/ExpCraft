package li.cryx.expcraft.cmd;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import li.cryx.expcraft.ExpCraftCore;
import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.util.Chat;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

/**
 * This class handles commands sent to ExpCraftCore
 * 
 * @author cryxli
 */
public class CommandManager {

	/** Reference to the core. */
	private final ExpCraftCore core;

	/** The chat utility to send answers to the requesting player. */
	private final Chat chat;

	/** Create a new manager for the given core. */
	public CommandManager(final ExpCraftCore core) {
		this.core = core;
		chat = new Chat(core);
	}

	/**
	 * Display all level stats a player has reached so far.
	 * 
	 * @param sender
	 *            Current player
	 */
	private void displayAllLevelForPlayer(final Player sender) {
		List<String> levels = new ArrayList<String>();
		for (ExpCraftModule module : core.getModules()) {
			if (core.getPermissions().hasLevel(module, sender)) {
				levels.add(MessageFormat.format("{0}, ({1}): {2}", //
						module.getName(), //
						module.getAbbr(), //
						core.getPersistence().getLevel(module, sender)));
			}
		}

		if (levels.isEmpty()) {
			chat.warn(sender, "No level found.");
		} else {
			chat.topBar(sender);
			Collections.sort(levels);
			for (String line : levels) {
				chat.info(sender, line);
			}
		}
	}

	/**
	 * Display informaion about ExpCraft, namely the commands the given player
	 * can issue.
	 * 
	 * @param sender
	 *            Current player
	 */
	public void displayCoreInfo(final Player sender) {
		chat.topBar(sender);
		chat.info(sender, "Available commands:");
		chat.info(sender, " /lvl all");
		chat.info(sender, " /lvl info <Module> [Page]");
		if (core.getPermissions().hasAdminCommand(sender, "get")) {
			chat.info(sender, " /lvl getExp <Module> [Player]");
			chat.info(sender, " /lvl getLvl <Module> [Player]");
		} else {
			chat.info(sender, " /lvl getExp <Module>");
			chat.info(sender, " /lvl getLvl <Module>");
		}
		if (core.getPermissions().hasAdminCommand(sender, "set")) {
			chat.info(sender, " /lvl setExp <Module> <Value> [Player]");
			chat.info(sender, " /lvl setLvl <Module> <Value> [Player]");
		}

		// TODO cryxli: display info/about
	}

	/**
	 * Display gathered experience for the given player and module.
	 * 
	 * @param sender
	 *            ConsoleSender requesting the information
	 * @param modAbbr
	 *            Short identifier for a module
	 * @param name
	 *            Name of the player
	 */
	private void displayExp(final Player sender, final String modAbbr,
			final String name) {
		ExpCraftModule module = core.getModuleByAbbr(modAbbr);
		if (module == null) {
			// module not found
			chat.info(sender, "No module found");
		} else if (core.getPermissions().hasLevel(module, sender)) {
			Player player = null;
			if (sender.getName().equalsIgnoreCase(name)) {
				player = sender;
			} else if (core.getPermissions().hasAdminCommand(sender, "get")) {
				player = core.getServer().getPlayer(name);
			}
			if (player == null) {
				// player not found
				chat.info(sender,
						"Player isn't online or you don't have enough rights");
			} else {
				// display
				chat.info(sender,
						MessageFormat.format("{0}, ({1}): {2} points", //
								module.getName(), //
								module.getAbbr(), //
								core.getPersistence().getExp(module, player)));
			}
		} else {
			// no permission
			chat.bad(sender, "You cannot use this module");
		}
	}

	/**
	 * Display the level for the given player and module.
	 * 
	 * @param sender
	 *            ConsoleSender requesting the information
	 * @param modAbbr
	 *            Short identifier for a module
	 * @param name
	 *            Name of the player
	 */
	private void displayLevel(final Player sender, final String modAbbr,
			final String name) {
		ExpCraftModule module = core.getModuleByAbbr(modAbbr);
		if (module == null) {
			// module not found
			chat.info(sender, "No module found");
		} else if (core.getPermissions().hasLevel(module, sender)) {
			Player player = null;
			if (sender.getName().equalsIgnoreCase(name)) {
				player = sender;
			} else if (core.getPermissions().hasAdminCommand(sender, "get")) {
				player = core.getServer().getPlayer(name);
			}
			if (player == null) {
				// player not found
				chat.info(sender,
						"Player isn't online or you don't have enough rights");
			} else {
				// display
				chat.info(sender, MessageFormat.format("{0}, ({1}): {2}", //
						module.getName(), //
						module.getAbbr(), //
						core.getPersistence().getLevel(module, player)));
			}
		} else {
			// no permission
			chat.bad(sender, "You cannot use this module");
		}
	}

	/**
	 * Display information about the given plugin.
	 * 
	 * @param sender
	 *            ConsoleSender requesting the information
	 * @param modAbbr
	 *            Short identifier for a module
	 * @param pageStr
	 *            String containing a number referening to the page to display.
	 */
	private void displayModuleInfo(final Player sender, final String modAbbr,
			final String pageStr) {
		int page;
		try {
			page = Integer.parseInt(pageStr);
		} catch (NumberFormatException e) {
			page = 1;
		}
		ExpCraftModule module = core.getModuleByAbbr(modAbbr);
		if (module == null) {
			// module not found
			chat.info(sender, "No module found");
		} else {
			// have the module display its info
			module.displayInfo(sender, page);
		}
	}

	/**
	 * Change the experience of a player and module
	 * 
	 * @param sender
	 *            ConsoleSender issuing the command
	 * @param modAbbr
	 *            Short identifier for a module
	 * @param valueStr
	 *            New amount of expereience points.
	 * @param name
	 *            Name of the player
	 */
	private void execSetExp(final Player sender, final String modAbbr,
			final String valueStr, final String name) {
		double value;
		try {
			value = Double.parseDouble(valueStr);
		} catch (NumberFormatException e) {
			chat.info(sender, "Experience must be a floating point number");
			return;
		}
		ExpCraftModule module = core.getModuleByAbbr(modAbbr);
		if (module == null) {
			// module not found
			chat.info(sender, "No module found");
		} else if (core.getPermissions().hasAdminCommand(sender, "set")) {
			Player player = core.getServer().getPlayer(name);

			if (player == null) {
				// player not found
				chat.info(sender, "Player isn't online");
			} else {
				// execute
				core.getPersistence().setExp(module, player, value);
				// display
				chat.info(sender,
						MessageFormat.format("{0}, ({1}): {2} points", //
								module.getName(), //
								module.getAbbr(), //
								value));
			}
		} else {
			// no permission
			chat.bad(sender, "You do not have enough rights");
		}
	}

	/**
	 * Change the level of a player and module
	 * 
	 * @param sender
	 *            ConsoleSender issuing the command
	 * @param modAbbr
	 *            Short identifier for a module
	 * @param valueStr
	 *            New level
	 * @param name
	 *            Name of the player
	 */
	private void execSetLevel(final Player sender, final String modAbbr,
			final String valueStr, final String name) {
		int value;
		try {
			value = Integer.parseInt(valueStr);
		} catch (NumberFormatException e) {
			chat.info(sender, "Experience must be a natural number");
			return;
		}
		ExpCraftModule module = core.getModuleByAbbr(modAbbr);
		if (module == null) {
			// module not found
			chat.info(sender, "No module found");
		} else if (core.getPermissions().hasAdminCommand(sender, "set")) {
			Player player = core.getServer().getPlayer(name);

			if (player == null) {
				// player not found
				chat.info(sender, "Player isn't online");
			} else {
				// execute
				core.getPersistence().setLevel(module, player, value);
				// display
				chat.info(sender, MessageFormat.format("{0}, ({1}): {2}", //
						module.getName(), //
						module.getAbbr(), //
						value));
			}
		} else {
			// no permission
			chat.bad(sender, "You do not have enough rights");
		}
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
	public void onCommand(final Player sender, final Command cmd,
			final String[] args) {

		if ("all".equalsIgnoreCase(args[0])) {
			// display all level for a player
			displayAllLevelForPlayer(sender);

		} else if ("getexp".equalsIgnoreCase(args[0])) {
			// display exp for a player and module
			if (args.length < 2) {
				chat.info(sender, "Syntax: /level getExp <Module> [Player]");
			} else if (args.length == 2) {
				displayExp(sender, args[1], sender.getName());
			} else {
				displayExp(sender, args[1], args[2]);
			}

		} else if ("setexp".equalsIgnoreCase(args[0])) {
			// change exp for a player and module
			if (args.length < 3) {
				chat.info(sender,
						"Syntax: /level setExp <Module> <Value> [Player]");
			} else if (args.length == 3) {
				execSetExp(sender, args[1], args[2], sender.getName());
			} else {
				execSetExp(sender, args[1], args[2], args[3]);
			}
		} else if ("setlevel".equalsIgnoreCase(args[0])
				|| "setlvl".equalsIgnoreCase(args[0])) {
			// change level for a player and module
			if (args.length < 3) {
				chat.info(sender,
						"Syntax: /level setLevel <Module> <Value> [Player]");
			} else if (args.length == 3) {
				execSetLevel(sender, args[1], args[2], sender.getName());
			} else {
				execSetLevel(sender, args[1], args[2], args[3]);
			}

		} else if ("getlevel".equalsIgnoreCase(args[0])
				|| "getlvl".equalsIgnoreCase(args[0])) {
			// display level for a player and module
			if (args.length < 2) {
				chat.info(sender, "Syntax: /level getLevel <Module> [Player]");
			} else if (args.length == 2) {
				displayLevel(sender, args[1], sender.getName());
			} else {
				displayLevel(sender, args[1], args[2]);
			}

		} else if ("info".equalsIgnoreCase(args[0])) {
			// display information about ExpCraft or a module
			if (args.length < 2) {
				displayCoreInfo(sender);
			} else if (args.length == 2) {
				displayModuleInfo(sender, args[1], "1");
			} else {
				displayModuleInfo(sender, args[1], args[2]);
			}

		} else {
			if (core.getModuleByAbbr(args[0]) == null) {
				// unknown command, display info about ExpCraft
				displayCoreInfo(sender);

			} else if (args.length == 1) {
				displayModuleInfo(sender, args[0], "1");
			} else {
				displayModuleInfo(sender, args[0], args[1]);
			}

		}

		// TODO cryxli: handle player commands
	}
}
