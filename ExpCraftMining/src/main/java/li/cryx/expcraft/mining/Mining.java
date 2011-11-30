package li.cryx.expcraft.mining;

import java.text.MessageFormat;
import java.util.logging.Logger;

import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.util.Chat;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;

/**
 * This class is the main entry point for the mining module for ExpCraft. The
 * plugin manages the use of pickaxes and which blocks players can mine with how
 * much experience.
 * 
 * @author cryxli
 */
public class Mining extends ExpCraftModule {
	/** Global logger */
	public final Logger LOG = Logger.getLogger("EC-Mining");

	/** The block listener to do all the work. */
	private MiningBlockListener blockListener;

	/** One chat utility to send messages. */
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
	public void displayInfo(final Player sender, final int page) {
		chat.info(sender,
				MessageFormat.format("*** {0} ({1}) ***", getName(), getAbbr()));

		int level = getPersistence().getLevel(this, sender);
		chat.info(sender, "Tools:");
		sendToolInfo(sender, "Wooden", level);
		sendToolInfo(sender, "Stone", level);
		sendToolInfo(sender, "Iron", level);
		sendToolInfo(sender, "Gold", level);
		sendToolInfo(sender, "Diamond", level);
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
	 * Send level info about the given pickaxe to the player. If the player
	 * meets the requirements for the tool, it will be written in "good"
	 * (default GREEN), or in "bad" (default RED) otherwise.
	 * 
	 * @param sender
	 *            Current player
	 * @param material
	 *            String identifying the material of the pickaxe.
	 * @param level
	 *            Player's level
	 */
	private void sendToolInfo(final Player sender, final String material,
			final int level) {
		int toolLevel = getConfInt("PickaxeLevel." + material);
		String msg = MessageFormat.format("{0} Pickaxe: {1}", material,
				toolLevel);
		if (level >= toolLevel) {
			chat.good(sender, msg);
		} else {
			chat.bad(sender, msg);
		}
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
