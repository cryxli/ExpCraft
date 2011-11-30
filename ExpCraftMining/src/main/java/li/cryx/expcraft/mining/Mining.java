package li.cryx.expcraft.mining;

import java.text.MessageFormat;
import java.util.logging.Logger;

import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.util.Chat;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;

public class Mining extends ExpCraftModule {
	public final Logger LOG = Logger.getLogger("EC-Mining");

	private MiningBlockListener blockListener;

	private Chat chat;

	/**
	 * Create listeners and link them to this plugin and a common
	 * <code>Chat</code> component.
	 */
	private void createListeners() {
		// use one chat tool
		chat = new Chat(this);

		// block listener
		blockListener = new MiningBlockListener(this);
	}

	@Override
	public String getAbbr() {
		return "M";
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
		return "Mining";
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

	// server want the plugin to stop working
	@Override
	public void onDisable() {
		// disabled plugins don't get events; no need to unregister
		// listeners
		LOG.info("[EC] " + getDescription().getFullName() + " disabled");
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

	/** Register the listeners */
	private void registerEvents() {
		// ensure the listeners are ready
		createListeners();
		// register listeners
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener,
				Event.Priority.Highest, this);
	}

	/**
	 * Player cannot mine this block
	 * 
	 * @param player
	 *            Current player
	 * @param level
	 *            Level required to mine the block
	 */
	void warnBlockMine(final Player player, final int level) {
		chat.warn(player, MessageFormat.format(
				"Cannot mine this block. Required Level: {0}", level));
	}

	/**
	 * Player cannot use this tool.
	 * 
	 * @param player
	 *            Current player
	 * @param level
	 *            Level required to use the tool
	 */
	void warnToolUse(final Player player, final int level) {
		chat.warn(player, MessageFormat.format(
				"Cannot use this tool. Required Level: {0}", level));
	}
}
