package li.cryx.expcraft.template;

import java.text.MessageFormat;
import java.util.logging.Logger;

import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.util.Chat;

import org.bukkit.entity.Player;

public class Template extends ExpCraftModule {
	public final Logger LOG = Logger.getLogger("EC-Template");

	private Chat chat;

	// TODO example listener
	// private TemplateBlockListener blockListener;

	/**
	 * Create listeners and link them to this plugin and a common
	 * <code>Chat</code> component.
	 */
	private void createListeners() {
		// use one chat tool
		chat = new Chat(getCore());

		// TODO create your listeners like
		// blockListener = new ScavengerBlockListener(this);
	}

	@Override
	public void displayInfo(final Player sender) {
		chat.info(sender, MessageFormat.format("*** {0} ({1}) ***", getInfo()
				.getName(), getInfo().getAbbr()));

		// TODO Send information about the plugin to the player.
	}

	/**
	 * Load config from disk merge missing default values and store them to
	 * disk.
	 */
	private void loadConfig() {
		// force loading config from default once
		getConfig();
		// ...to store at first run
		saveConfig();
	}

	@Override
	public void onDisable() {
		// disabled plugins don't get events; no need to unregister
		// listeners
		LOG.info(getInfo().getFullName() + " disabled");

		// TODO if you instantiated other things, that are not under control of
		// bukkit, disable them here too
	}

	@Override
	public void onEnable() {
		// pre-load config
		loadConfig();
		// register listeners
		registerEvents();
		// ready
		LOG.info(getInfo().getFullName() + " enabled");
	}

	/** Register the listeners */
	private void registerEvents() {
		// ensure the listeners are ready
		createListeners();
		// register listeners
		// TODO add your listeners, like
		// registerEvents(blockListener);
	}
}
