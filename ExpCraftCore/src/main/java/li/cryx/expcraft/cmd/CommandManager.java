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

public class CommandManager {

	private final ExpCraftCore core;

	private final Chat chat;

	public CommandManager(final ExpCraftCore core) {
		this.core = core;
		chat = new Chat(core);
	}

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

	private void displayCoreInfo(final Player sender) {
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

	private void displayExp(final Player sender, final String modAbbr,
			final String name) {
		ExpCraftModule module = getModuleByAbbr(modAbbr);
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

	private void displayLevel(final Player sender, final String modAbbr,
			final String name) {
		ExpCraftModule module = getModuleByAbbr(modAbbr);
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

	private void displayModuleInfo(final Player sender, final String modAbbr,
			final String pageStr) {
		int page = 1;
		try {
			page = Integer.parseInt(pageStr);
		} catch (NumberFormatException e) {
		}
		ExpCraftModule module = getModuleByAbbr(modAbbr);
		if (module == null) {
			// module not found
			chat.info(sender, "No module found");
		} else {
			// have the module display its info
			module.displayInfo(sender, page);
		}
	}

	private void execSetExp(final Player sender, final String modAbbr,
			final String valueStr, final String name) {
		double value;
		try {
			value = Double.parseDouble(valueStr);
		} catch (NumberFormatException e) {
			chat.info(sender, "Experience must be a floating point number");
			return;
		}
		ExpCraftModule module = getModuleByAbbr(modAbbr);
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

	private void execSetLevel(final Player sender, final String modAbbr,
			final String valueStr, final String name) {
		int value;
		try {
			value = Integer.parseInt(valueStr);
		} catch (NumberFormatException e) {
			chat.info(sender, "Experience must be a natural number");
			return;
		}
		ExpCraftModule module = getModuleByAbbr(modAbbr);
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

	private ExpCraftModule getModuleByAbbr(final String abbr) {
		ExpCraftModule module = null;
		for (ExpCraftModule m : core.getModules()) {
			if (m.getAbbr().equalsIgnoreCase(abbr)) {
				module = m;
				break;
			}
		}
		return module;
	}

	public void onCommand(final Player sender, final Command cmd,
			final String[] args) {

		if ("all".equalsIgnoreCase(args[0])) {
			displayAllLevelForPlayer(sender);

		} else if ("getexp".equalsIgnoreCase(args[0])) {
			if (args.length < 2) {
				chat.info(sender, "Syntax: /level getExp <Module> [Player]");
			} else if (args.length == 2) {
				displayExp(sender, args[1], sender.getName());
			} else {
				displayExp(sender, args[1], args[2]);
			}

		} else if ("setexp".equalsIgnoreCase(args[0])) {
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
			if (args.length < 2) {
				chat.info(sender, "Syntax: /level getLevel <Module> [Player]");
			} else if (args.length == 2) {
				displayLevel(sender, args[1], sender.getName());
			} else {
				displayLevel(sender, args[1], args[2]);
			}

		} else if ("info".equalsIgnoreCase(args[0])) {
			if (args.length < 2) {
				displayCoreInfo(sender);
			} else if (args.length == 2) {
				displayModuleInfo(sender, args[1], "1");
			} else {
				displayModuleInfo(sender, args[1], args[2]);
			}

		} else {
			displayCoreInfo(sender);
		}

		// TODO cryxli: handle player commands
	}
}
