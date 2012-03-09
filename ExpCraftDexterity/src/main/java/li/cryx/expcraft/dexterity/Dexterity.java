package li.cryx.expcraft.dexterity;

import java.text.MessageFormat;
import java.util.logging.Logger;

import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.util.Chat;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

public class Dexterity extends ExpCraftModule {

	private static final Logger LOG = Logger.getLogger("EC-Dexterity");

	private Chat chat;

	private DexterityEntityListener entityListener;

	private DexterityPlayerListener playerListener;

	/**
	 * Create listeners and link them to this plugin and a common
	 * <code>Chat</code> component.
	 */
	private void createListeners() {
		// use one chat tool
		chat = new Chat(this);

		// player listener
		playerListener = new DexterityPlayerListener(this);

		// entity listener
		entityListener = new DexterityEntityListener(this);
	}

	@Override
	public void displayInfo(final Player sender, final int page) {
		chat.info(sender,
				MessageFormat.format("*** {0} ({1}) ***", getName(), getAbbr()));

		int level = getPersistence().getLevel(this, sender);
		chat.info(sender, "Boots:");
		sendToolInfo(sender, "Leather", level);
		sendToolInfo(sender, "Chainmail", level);
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

		// TODO cryxli: show info
	}

	@Override
	public String getAbbr() {
		return "Dx";
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
	public String getModuleName() {
		return "Dexterity";
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
	}// server want the plugin to start working

	/** Register the listeners */
	private void registerEvents() {
		// ensure the listeners are ready
		createListeners();
		// register listeners
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(entityListener, this);
		pm.registerEvents(playerListener, this);
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
		int toolLevel = getConfInt("BootsLevel." + material);
		String msg = MessageFormat
				.format("{0} Boots: {1}", material, toolLevel);
		if (level >= toolLevel) {
			chat.good(sender, msg);
		} else {
			chat.bad(sender, msg);
		}
	}

	void warnBoots(final Player player) {
		// TODO Auto-generated method stub

	}

}
