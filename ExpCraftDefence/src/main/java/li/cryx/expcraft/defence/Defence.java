package li.cryx.expcraft.defence;

import java.text.MessageFormat;
import java.util.logging.Logger;

import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.util.Chat;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

/**
 * This class is the main entry point for the Defence module for ExpCraft.
 * 
 * @author cryxli
 */
public class Defence extends ExpCraftModule {
	/** A logger */
	public final Logger LOG = Logger.getLogger("EC-Defence");

	/** Chat utility. */
	private Chat chat;

	/** The damage listener. */
	private DefenceEntityListener entityListener;

	/**
	 * Create listeners and link them to this plugin and a common
	 * <code>Chat</code> component.
	 */
	private void createListeners() {
		// use one chat tool
		chat = new Chat(this);

		entityListener = new DefenceEntityListener(this);
	}

	@Override
	public void displayInfo(final Player sender, final int page) {
		chat.info(sender, MessageFormat.format("*** {0} ({1}) ***",
				getModuleName(), getAbbr()));

		int level = getPersistence().getLevel(this, sender);
		chat.info(sender, "Armor Sets:");
		sendArmorInfo(sender, "Leather", level);
		sendArmorInfo(sender, "Chainmail", level);
		sendArmorInfo(sender, "Iron", level);
		sendArmorInfo(sender, "Gold", level);
		sendArmorInfo(sender, "Diamond", level);

		double exp = getPersistence().getExp(this, sender);
		double nextLvl = getPersistence().getExpForNextLevel(this, sender);
		chat.info(sender, "Stats:");
		chat.info(sender, MessageFormat.format(
				"Current level: {0}, XP: {1} points", level, exp));
		chat.info(sender, MessageFormat.format(
				"Experience to next level: {0} points", nextLvl - exp));

		// TODO Send information about the plugin to the player.
	}

	@Override
	public String getAbbr() {
		return "De";
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
		// delegate to config
		return getConfig().getInt(key);
	}

	@Override
	public String getModuleName() {
		return "Defence";
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

	/**
	 * Send a message to the player, that he cannot wear a certain piece of
	 * armor.
	 * 
	 * @param player
	 *            Current player
	 * @param material
	 *            Material of the armor piece.
	 * @param armor
	 *            Type of the armor piece.
	 */
	void notifyRequirements(final Player player, final String material,
			final String armor) {
		chat.bad(player, MessageFormat.format("You cannot wear {0} {1}.",
				material, armor));
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
		pm.registerEvents(entityListener, this);
	}

	/**
	 * Send info about a material type of armor to the player.
	 * 
	 * @param sender
	 *            Current player
	 * @param material
	 *            String identifying armor material
	 * @param level
	 *            Player's level in Defence.
	 */
	private void sendArmorInfo(final Player sender, final String material,
			final int level) {
		int boots = getConfInt("ArmorLevel." + material + "Boots");
		int chest = getConfInt("ArmorLevel." + material + "Chestplate");
		int helmet = getConfInt("ArmorLevel." + material + "Helmet");
		int leggings = getConfInt("ArmorLevel." + material + "Leggings");

		if (boots == chest && chest == helmet && helmet == leggings) {
			if (level < boots) {
				chat.bad(sender,
						MessageFormat.format("{0} Armor: {1}", material, boots));
			} else {
				chat.good(sender,
						MessageFormat.format("{0} Armor: {1}", material, boots));
			}
		} else {
			StringBuffer buf = new StringBuffer();
			buf.append(material).append(" Armor, ");

			buf.append(level < boots ? Chat.getBadColor() : Chat.getGoodColor());
			buf.append("B: ").append(boots);
			buf.append(Chat.getSpecialColor());
			buf.append(", ");

			buf.append(level < helmet ? Chat.getBadColor() : Chat
					.getGoodColor());
			buf.append("H: ").append(helmet);
			buf.append(Chat.getSpecialColor());
			buf.append(", ");

			buf.append(level < leggings ? Chat.getBadColor() : Chat
					.getGoodColor());
			buf.append("L: ").append(leggings);
			buf.append(Chat.getSpecialColor());
			buf.append(", ");

			buf.append(level < chest ? Chat.getBadColor() : Chat.getGoodColor());
			buf.append("C: ").append(chest);

			chat.info(sender, buf.toString());
		}
	}
}
