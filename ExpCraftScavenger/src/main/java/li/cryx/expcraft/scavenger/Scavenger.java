package li.cryx.expcraft.scavenger;

import java.text.MessageFormat;
import java.util.logging.Logger;

import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.util.Chat;

import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;

public class Scavenger extends ExpCraftModule {
	public final Logger LOG = Logger.getLogger("EC-Scavenger");

	private ScavengerBlockListener blockListener;

	private Chat chat;

	/**
	 * Create listeners and link them to this plugin and a common
	 * <code>Chat</code> component.
	 */
	private void createListeners() {
		// use one chat tool
		chat = new Chat(this);

		// block listener
		blockListener = new ScavengerBlockListener(this);
	}

	@Override
	public void displayInfo(final Player sender, final int page) {
		chat.info(sender,
				MessageFormat.format("*** {0} ({1}) ***", getName(), getAbbr()));

		chat.info(sender,
				"Digging through dirt, etc. you can find other items.");
		chat.info(sender, "The more you search, the more you'll find.");
	}

	@Override
	public String getAbbr() {
		return "Sc";
	}

	/**
	 * Get the <code>int</code> value of the given config key.
	 * 
	 * @param key
	 *            Key in the config YAML.
	 * @return Value associated with the given key.
	 */
	double getConfDouble(final String key) {
		return getConfig().getDouble(key);
	}

	/**
	 * Get the <code>double</code> value of the given config key.
	 * 
	 * @param key
	 *            Key in the config YAML.
	 * @return Value associated with the given key.
	 */
	int getConfInt(final String key) {
		return getConfig().getInt(key);
	}

	@Override
	public String getName() {
		return "Scavenger";
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

	// server want the plugin to start working
	@Override
	public void onEnable() {
		// pre-load config
		loadConfig();
		// register listeners
		registerEvents();
		// ready
		LOG.info("[EC] " + getDescription().getFullName() + " enabled");
	}

	// server want the plugin to stop working
	@Override
	public void onModuleDisable() {
		// disabled plugins don't get events; no need to unregister
		// listeners
		LOG.info("[EC] " + getDescription().getFullName() + " disabled");
	}

	/** Register the listeners */
	private void registerEvents() {
		// ensure the listeners are ready
		createListeners();
		// register listeners
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Type.BLOCK_BREAK, blockListener, Priority.Highest,
				this);
	}

	void sendHint(final Player player, final String msg) {
		chat.info(player, msg);
	}
}
