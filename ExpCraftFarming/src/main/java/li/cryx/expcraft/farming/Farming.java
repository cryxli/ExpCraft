package li.cryx.expcraft.farming;

import java.text.MessageFormat;
import java.util.logging.Logger;

import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.util.Chat;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
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

	/** Listen to block break and block place events. */
	private FarmingBlockListener blockListener = null;

	/** Listen to player interact events. */
	private FarmingPlayerListener playerListener = null;

	/** A factory to send messages to the player. */
	private Chat chat;

	/**
	 * Test whether the given item is a hoe. All hoes are taken into
	 * consideration with their respective requirements defined in the config.
	 * 
	 * @param player
	 *            Current player.
	 * @param itemInHand
	 *            The item the current player is holding in his hand.
	 * @param level
	 *            Player's farming level.
	 * @return <code>true</code>, only if the given item is a hoe of any kind
	 *         and the player's level allows him to use this particular hoe.
	 */
	boolean checkTool(final Player player, final Material itemInHand,
			final int level) {
		if (level < getConfInt("HoeLevel.Wooden")
				&& itemInHand == Material.WOOD_HOE) {
			warnToolLevel(player, getConfInt("HoeLevel.Wooden"));

		} else if (level < getConfInt("HoeLevel.Stone")
				&& itemInHand == Material.STONE_HOE) {
			warnToolLevel(player, getConfInt("HoeLevel.Stone"));

		} else if (level < getConfInt("HoeLevel.Iron")
				&& itemInHand == Material.IRON_HOE) {
			warnToolLevel(player, getConfInt("HoeLevel.Iron"));

		} else if (level < getConfInt("HoeLevel.Gold")
				&& itemInHand == Material.GOLD_HOE) {
			warnToolLevel(player, getConfInt("HoeLevel.Gold"));

		} else if (level < getConfInt("HoeLevel.Diamond")
				&& itemInHand == Material.DIAMOND_HOE) {
			warnToolLevel(player, getConfInt("HoeLevel.Diamond"));

		} else {
			// all tool checks passed
			return true;
		}
		// one tool check failed
		return false;
	}

	/**
	 * Create listeners and link them to this plugin and a common
	 * <code>Chat</code> component.
	 */
	private void createListeners() {
		// use one chat tool
		chat = new Chat(this);

		// block listener
		blockListener = new FarmingBlockListener(this);

		// player listener
		playerListener = new FarmingPlayerListener(this);
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
	public String getName() {
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

	// server wants the plugin to stop working
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
		pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener,
				Event.Priority.Highest, this);
		pm.registerEvent(Event.Type.BLOCK_PLACE, blockListener,
				Event.Priority.Highest, this);
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
