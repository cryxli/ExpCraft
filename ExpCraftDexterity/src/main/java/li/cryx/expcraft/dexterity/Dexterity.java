package li.cryx.expcraft.dexterity;

import java.text.MessageFormat;

import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.util.Chat;

import org.bukkit.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dexterity extends ExpCraftModule {

	private static final Logger LOG = LoggerFactory.getLogger(Dexterity.class);

	private Chat chat;

	private DexterityEntityListener entityListener;

	private DexterityPlayerListener playerListener;

	/**
	 * Create listeners and link them to this plugin and a common
	 * <code>Chat</code> component.
	 */
	private void createListeners() {
		// use one chat tool
		chat = new Chat(getCore());

		// player listener
		playerListener = new DexterityPlayerListener(this);

		// entity listener
		entityListener = new DexterityEntityListener(this);
	}

	@Override
	public void displayInfo(final Player sender) {
		chat.info(sender, MessageFormat.format("*** {0} ({1}) ***", getInfo()
				.getName(), getInfo().getAbbr()));

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
		LOG.info(getInfo().getFullName() + " disabled");
	}// server want the plugin to start working

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
		registerEvents(entityListener);
		registerEvents(playerListener);
	}

	/**
	 * Send level info about the given boots to the player. If the player meets
	 * the requirements for the armor, it will be written in "good" (default
	 * GREEN), or in "bad" (default RED) otherwise.
	 * 
	 * @param sender
	 *            Current player
	 * @param material
	 *            String identifying the material of the boots.
	 * @param level
	 *            Player's level
	 */
	private void sendToolInfo(final Player sender, final String material,
			final int level) {
		int toolLevel = getConfig().getInteger("BootsLevel." + material);
		String msg = MessageFormat
				.format("{0} Boots: {1}", material, toolLevel);
		if (level >= toolLevel) {
			chat.good(sender, msg);
		} else {
			chat.bad(sender, msg);
		}
	}

	/**
	 * Warn the player that she does not meet the requirements for her current
	 * boots.
	 * 
	 * @param player
	 *            Current player
	 * @param material
	 *            String identifying the material of the boots.
	 * @param level
	 *            Required level to get bonus for current boots.
	 */
	void warnBoots(final Player player, final String material, final int level) {
		chat.bad(player, MessageFormat.format(
				"To wear {0} Boots you have to be lv{1}", material, level));
	}

}
