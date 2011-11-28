package li.cryx.expcraft.cmd;

import java.text.MessageFormat;
import java.util.ArrayList;
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
			for (String line : levels) {
				chat.info(sender, line);
			}
		}
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
				chat.info(sender, "Player isn't online");
			} else {
				// display
				chat.info(sender,
						MessageFormat.format("{0}, ({1}): {2} points", //
								module.getName(), //
								module.getAbbr(), //
								core.getPersistence().getExp(module, sender)));
			}
		} else {
			// no permission
			chat.bad(sender, "You cannot use this module");
		}
	}

	private void execSetExp(final Player sender, final String modAbbr,
			final String valueStr, final String name) {
		double value;
		try {
			value = Double.valueOf(valueStr);
		} catch (NumberFormatException e) {
			chat.info(sender, "Experience must be a floating number");
			return;
		}
		ExpCraftModule module = getModuleByAbbr(modAbbr);
		if (module == null) {
			// module not found
			chat.info(sender, "No module found");
		} else if (core.getPermissions().hasLevel(module, sender)) {
			Player player = null;
			if (sender.getName().equalsIgnoreCase(name)) {
				player = sender;
			} else if (core.getPermissions().hasAdminCommand(sender, "set")) {
				player = core.getServer().getPlayer(name);
			}
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
			chat.bad(sender, "You cannot use this module");
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
		} else {
			chat.info(sender, "Unknown command.");
		}

		// TODO cryxli: handle player commands
	}
}
