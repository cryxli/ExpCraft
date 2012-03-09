package li.cryx.expcraft.digging;

import java.text.MessageFormat;
import java.util.logging.Logger;

import li.cryx.expcraft.module.DropExpCraftModule;
import li.cryx.expcraft.util.Chat;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

public class Digging extends DropExpCraftModule {
	private static final Logger LOG = Logger.getLogger("EC-Farming");

	/** Listen to block break and block place events. */
	private DiggingBlockListener blockListener = null;

	private Chat chat;

	@Override
	protected ItemStack calculateDrop(final Block block) {
		Material material = block.getType();
		switch (material) {
		case GRASS:
			return new ItemStack(Material.DIRT, 1);
		case CLAY:
			return new ItemStack(Material.CLAY_BALL, 4);
		case SNOW:
			return new ItemStack(Material.SNOW_BALL, 4);

		case GRAVEL:
		case SOUL_SAND:
		case DIRT:
		case SAND:
			return new ItemStack(material, 1);

		default:
			return null;
		}
	}

	/**
	 * Create listeners and link them to this plugin and a common
	 * <code>Chat</code> component.
	 */
	private void createListeners() {
		// use one chat tool
		chat = new Chat(this);

		// block listener
		blockListener = new DiggingBlockListener(this);
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
		return "D";
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
		return "Digging";
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
		pm.registerEvents(blockListener, this);
	}

	private void sendToolInfo(final Player sender, final String material,
			final int level) {
		int toolLevel = getConfInt("ShovelLevel." + material);
		String msg = MessageFormat.format("{0} Shovel: {1}", material,
				toolLevel);
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
