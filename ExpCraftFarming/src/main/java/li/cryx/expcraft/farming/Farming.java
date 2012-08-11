package li.cryx.expcraft.farming;

import java.text.MessageFormat;
import java.util.logging.Logger;

import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.util.Chat;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

/**
 * This class is the main entry point of the "Farming" module for ExpCraft. This
 * module restricts the use of hoes. Player gain experiance by, e.g., havesting
 * crops, sugar cane and cacti, or, planting roses, dandelions and saplings and
 * other planting related activities. With high enough levels players may also
 * gain additional goodies, that usually do not drop when harvesting.
 * 
 * @author cryxli
 */
public class Farming extends ExpCraftModule {

	private static final Logger LOG = Logger.getLogger("EC-Farming");

	/** Listen to block break events. */
	private FarmingBlockBreakListener blockBreakListener = null;
	/** Listen to block place events. */
	private FarmingBlockPlaceListener blockPlaceListener = null;

	/** Listen to player interact events. */
	private FarmingPlayerListener playerListener = null;

	/** A factory to send messages to the player. */
	private Chat chat;

	/**
	 * Create listeners and link them to this plugin and a common
	 * <code>Chat</code> component.
	 */
	private void createListeners() {
		// use one chat tool
		chat = new Chat(this);

		// block listener
		blockBreakListener = new FarmingBlockBreakListener(this);
		blockPlaceListener = new FarmingBlockPlaceListener(this);

		// player listener
		playerListener = new FarmingPlayerListener(this);
	}

	@Override
	public void displayInfo(final Player sender, final int page) {
		chat.info(sender, MessageFormat.format("*** {0} ({1}) ***",
				getModuleName(), getAbbr()));

		int level = getPersistence().getLevel(this, sender);
		chat.info(sender, "Tools:");
		sendToolInfo(sender, "Wooden", level);
		sendToolInfo(sender, "Stone", level);
		sendToolInfo(sender, "Iron", level);
		sendToolInfo(sender, "Gold", level);
		sendToolInfo(sender, "Diamond", level);

		double exp = getPersistence().getExp(this, sender);
		double nextLvl = getPersistence().getExpForNextLevel(this, sender);
		chat.info(sender, "Stats:");
		chat.info(sender, MessageFormat.format(
				"Current level: {0}, XP: {1} points", level, exp));
		chat.info(sender, MessageFormat.format(
				"Experience to next level: {0} points", nextLvl - exp));
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
	double getConfDouble(final String key) {
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
	int getConfInt(final String key) {
		// delegate to config
		return getConfig().getInt(key);
	}

	@Override
	public String getModuleName() {
		return "Farming";
	}

	/**
	 * Test whether the given material represents a hoe of any kind.
	 * 
	 * @param material
	 *            The item type.
	 * @return <code>true</code>, only if the type corresponds to a hoe made of
	 *         any material.
	 */
	boolean isHoe(final Material material) {
		return material == Material.WOOD_HOE //
				|| material == Material.STONE_HOE //
				|| material == Material.IRON_HOE //
				|| material == Material.GOLD_HOE //
				|| material == Material.DIAMOND_HOE;
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

	// server wants the plugin to stop working
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
		pm.registerEvents(blockBreakListener, this);
		pm.registerEvents(blockPlaceListener, this);
		pm.registerEvents(playerListener, this);
	}

	private void sendToolInfo(final Player sender, final String material,
			final int level) {
		int toolLevel = getConfInt("HoeLevel." + material);
		String msg = MessageFormat.format("{0} Hoe: {1}", material, toolLevel);
		if (level >= toolLevel) {
			chat.good(sender, msg);
		} else {
			chat.bad(sender, msg);
		}
	}

	/**
	 * Send a warning to the player that he is not allowed to cut the current
	 * block.
	 * 
	 * @param player
	 *            Current player
	 * @param level
	 *            The required level to cut
	 */
	void warnCutBlockLevel(final Player player, final int reqLevel) {
		chat.warn(player, MessageFormat.format( //
				"Cannot cut this block. Required Level: {0}", reqLevel));
	}

	/**
	 * Send a warning to the player that he is not allowed to use the current
	 * tool.
	 * 
	 * @param player
	 *            Current player
	 * @param level
	 *            The required level to use this tool
	 */
	void warnToolLevel(final Player player, final int level) {
		chat.warn(player, MessageFormat.format( //
				"Cannot use this tool. Required Level: {0}", level));
	}
}
