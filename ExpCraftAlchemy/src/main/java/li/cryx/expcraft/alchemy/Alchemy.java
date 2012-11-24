package li.cryx.expcraft.alchemy;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import li.cryx.expcraft.alchemy.recipe.TypedRecipe;
import li.cryx.expcraft.alchemy.util.RecipeParser;
import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.util.Chat;
import li.cryx.expcraft.util.FileUtil;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Recipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * Main entry point for the ExpCraft module Alchemy (A). Adds custom recipes
 * depending on crafting experience.
 * 
 * @author cryxli
 */
public class Alchemy extends ExpCraftModule {

	private static final Logger LOG = LoggerFactory.getLogger(Alchemy.class);

	private CraftingListener craftListener;

	private RecipeStore store;

	private Chat chat;

	/**
	 * Create listeners and link them to this plugin and a common
	 * <code>Chat</code> component.
	 */
	private void createListeners() {
		// use one chat tool
		chat = new Chat(getCore());
		// add crafting listener
		craftListener = new CraftingListener(this);
	}

	@Override
	public void displayInfo(final Player sender) {
		chat.info(sender, MessageFormat.format("*** {0} ({1}) ***", getInfo()
				.getName(), getInfo().getAbbr()));

		int level = getPersistence().getLevel(this, sender);
		double exp = getPersistence().getExp(this, sender);
		double nextLvl = getPersistence().getExpForNextLevel(this, sender);
		chat.info(sender, "Stats:");
		chat.info(sender, MessageFormat.format(
				"Current level: {0}, XP: {1} points", level, exp));
		chat.info(sender, MessageFormat.format(
				"Experience to next level: {0} points", nextLvl - exp));
	}

	TypedRecipe getRecipe(final Recipe recipe) {
		return store.getRecipe(recipe);
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

		// load recipes
		store = new RecipeStore(getConfig().getDouble("ExpGain.Default"));
		parseRecipes();

		// install recipes
		store.installRecipes(getServer());
	}

	@Override
	public void onDisable() {
		// uninstall recipes
		store.uninstallRecipes(getServer());
		store = null;

		// free some memory
		craftListener = null;
		chat = null;

		// done
		LOG.info(getInfo().getFullName() + " disabled");
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

	private void parseRecipes() {
		File recipe = new File(getConfigFile().getParentFile(), getConfig()
				.getProperty("Recipe.Definition"));
		if (!recipe.exists()
				&& "recipe.xml".equals(getConfig().getProperty(
						"Recipe.Definition"))) {
			// copy example recipes to disk
			try {
				FileUtil.INSTANCE.copyFile(getClass().getClassLoader()
						.getResourceAsStream("config/recipe.xml"), recipe);
			} catch (IOException e) {
				LOG.warn("Failed to copy example recipes to disk.", e);
			}
		}
		if (!recipe.exists()) {
			// stated recipe XML does not exist
			return;
		}

		// load recipes
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setNamespaceAware(false);
			SAXParser saxParser = factory.newSAXParser();
			RecipeParser handler = new RecipeParser();
			saxParser.parse(recipe, handler);
			store.addAll(handler.getRecipes());
		} catch (IOException e) {
			LOG.error("Error reading recipes.", e);
		} catch (ParserConfigurationException e) {
			LOG.error("Error starting SAX parser.", e);
		} catch (SAXException e) {
			LOG.error("Error parsing recipes.", e);
		}
	}

	/** Register the listeners */
	private void registerEvents() {
		// ensure the listeners are ready
		createListeners();
		// register listeners
		registerEvents(craftListener);
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
