package li.cryx.expcraft.farming;

import java.util.logging.Logger;

import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.util.Chat;

import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;

/**
 * This class is the main entry point of the "Farming" module for ExpCraft. This
 * module restricts the use of hoes.
 * 
 * @author cryxli
 */
public class Farming extends ExpCraftModule {

	private static final Logger LOG = Logger.getLogger("EC-Farming");

	/** Listen to block break and block place events. */
	private FarmingBlockListener blockListener = null;

	/** Listen to player interact events. */
	private FarmingPlayerListener playerListener = null;

	/**
	 * Create listeners and link them to this plugin and a common
	 * <code>Chat</code> component.
	 */
	private void createListeners() {
		// use one chat tool
		Chat chat = new Chat(this);

		// lazy init block listener
		if (blockListener == null) {
			blockListener = new FarmingBlockListener(this);
		}
		blockListener.setChat(chat);

		// lazy init player listener
		if (playerListener == null) {
			playerListener = new FarmingPlayerListener(this);
		}
		playerListener.setChat(chat);
	}

	@Override
	public String getAbbr() {
		return "Fm";
	}

	/**
	 * Get the <code>int</code> value of the given config key.
	 * 
	 * @param key
	 *            Key in the config YAML.
	 * @return Value associated with the given key.
	 */
	public double getConfDouble(final String key) {
		// delegate to config
		return getConfig().getDouble(key);
	}

	/**
	 * Get the <code>double</code> value of the given config key.
	 * 
	 * @param key
	 *            Key in the config YAML.
	 * @return Value associated with the given key.
	 */
	public int getConfInt(final String key) {
		// delegate to config
		return getConfig().getInt(key);
	}

	@Override
	public String getName() {
		return "Farming";
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
		LOG.info("[EC] " + getDescription().getFullName() + " disabled");
	}

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
		pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener,
				Event.Priority.Highest, this);
		pm.registerEvent(Event.Type.BLOCK_PLACE, blockListener,
				Event.Priority.Highest, this);
	}

}
