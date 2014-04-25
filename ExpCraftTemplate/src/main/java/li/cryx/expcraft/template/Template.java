package li.cryx.expcraft.template;

import java.util.logging.Logger;

import li.cryx.expcraft.i18n.LangKeys;
import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.util.Chat;

import org.bukkit.entity.Player;

public class Template extends ExpCraftModule {
	public final Logger LOG = Logger.getLogger("EC-Template");

	private Chat chat;

	// TODO example listener
	// private TemplateBlockListener blockListener;

	/**
	 * Create listeners and link them to this plugin and a common
	 * <code>Chat</code> component.
	 */
	private void createListeners() {
		// use one chat tool
		chat = new Chat(getCore());

		// TODO create your listeners like
		// blockListener = new ScavengerBlockListener(this);
	}

	@Override
	public void displayInfo(final Player player) {
		chat.info(player, LangKeys.MODULE_INFO_TITLE, this, getInfo().getAbbr());

		// TODO Send information about the plugin to the player.

		// e.g. a text only present in the module
		chat.infoPlain(player, translator.translate(player, "custom.info"));

		// e.g. texts defined by the core
		int level = getPersistence().getLevel(this, player);
		double exp = getPersistence().getExp(this, player);
		double nextLvl = getPersistence().getExpForNextLevel(this, player);
		chat.info(player, LangKeys.MODULE_INFO_STATS);
		chat.info(player, LangKeys.MODULE_INFO_LV_EXP, level, exp);
		chat.info(player, LangKeys.MODULE_INFO_NEXT_LV, nextLvl - exp);
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
		LOG.info(getInfo().getFullName() + " disabled");

		// TODO if you instantiated other things, that are not under control of
		// bukkit, disable them here too
	}

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
		// TODO add your listeners, like
		// registerEvents(blockListener);
	}
}
