package li.cryx.expcraft.alchemy;

import java.text.MessageFormat;
import java.util.Map;
import java.util.logging.Logger;

import li.cryx.expcraft.alchemy.recipe.TypedRecipe;
import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.util.Chat;
import li.cryx.expcraft.util.RecipeFactory;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.PluginManager;

public class Alchemy extends ExpCraftModule {

	private static final Logger LOG = Logger.getLogger("EC-Alchemy");

	private CraftingListener craftListener;

	private RecipeStore store;

	private Chat chat;

	/**
	 * Create listeners and link them to this plugin and a common
	 * <code>Chat</code> component.
	 */
	private void createListeners() {
		// use one chat tool
		chat = new Chat(this);

		craftListener = new CraftingListener(this);
		// TODO
	}

	@Override
	public void displayInfo(final Player sender, final int page) {
		chat.info(sender, MessageFormat.format("*** {0} ({1}) ***",
				getModuleName(), getAbbr()));

		// TODO Auto-generated method stub

		int level = getPersistence().getLevel(this, sender);
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
		return "A";
	}

	@Override
	public String getModuleName() {
		return "Alchemy";
	}

	TypedRecipe getRecipe(final Recipe recipe) {
		return store.getRecipe(recipe);
	}

	/**
	 * Load config from disk merge missing default values and store them to
	 * disk.
	 */
	@SuppressWarnings("unchecked")
	private void loadConfig() {
		// force loading config from default once
		FileConfiguration conf = getConfig();
		// ...to store at first run
		saveConfig();

		// load recipes
		store = new RecipeStore(conf.getDouble("ExpGain.Default"));
		if (!conf.isList("Recipe")) {
			// error in recipe list
			LOG.severe("Error in recipe list, no recipe loaded!");
			return;
		} else {
			for (Object obj : conf.getList("Recipe")) {
				if (obj instanceof Map) {
					// System.out.println(obj);
					TypedRecipe recipe = RecipeFactory
							.createFromMap((Map<String, Object>) obj);
					// System.out.println(recipe);
					store.add(recipe);
				}
			}
		}

		// install recipes
		store.installRecipes(getServer());
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
		// uninstall recipes
		store.uninstallRecipes(getServer());
		store = null;

		// free some memory
		craftListener = null;
		chat = null;

		// done
		LOG.info("[EC] " + getDescription().getFullName() + " disabled");
	}

	/** Register the listeners */
	private void registerEvents() {
		// ensure the listeners are ready
		createListeners();
		// register listeners
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(craftListener, this);
	}

	public void warnLevel(final Player player, final int level) {
		chat.warn(player, MessageFormat.format(
				"You cannot yet use this recipe. Requires Level: {0}", level));
	}

	public void warnPlugin(final Player player) {
		chat.warn(player,
				"You cannot use this recipe, it is not enabled for you.");
	}
}
