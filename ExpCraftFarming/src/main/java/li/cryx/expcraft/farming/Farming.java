package li.cryx.expcraft.farming;

import java.util.logging.Level;
import java.util.logging.Logger;

import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.util.Chat;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class Farming extends ExpCraftModule {

	private static final Logger LOG = Logger.getLogger("ECFarming");

	private FarmingBlockListener blockListener = null;

	private FarmingPlayerListener playerListener = null;

	private Config config;

	private void createListeners() {
		Chat chat = new Chat(this);
		if (blockListener == null) {
			blockListener = new FarmingBlockListener(this);
		}
		blockListener.setChat(chat);
		blockListener.setConfig(config);

		if (playerListener == null) {
			playerListener = new FarmingPlayerListener(this);
		}
		playerListener.setChat(chat);
		playerListener.setConfig(config);
	}

	@Override
	public String getAbbr() {
		return "Fm";
	}

	@Override
	public String getName() {
		return "Farming";
	}

	private void loadConfig() {
		FileConfiguration conf = getConfig();
		config = new Config();

		config.TOOL_LEVEL.wood = conf.getInt("HoeLevel.Wooden");
		config.TOOL_LEVEL.stone = conf.getInt("HoeLevel.Stone");
		config.TOOL_LEVEL.iron = conf.getInt("HoeLevel.Iron");
		config.TOOL_LEVEL.gold = conf.getInt("HoeLevel.Gold");
		config.TOOL_LEVEL.diamond = conf.getInt("HoeLevel.Diamond");

		config.LEVEL.till = conf.getInt("UseLevel.Till");
		config.LEVEL.harvest = conf.getInt("UseLevel.Harvest");
		config.LEVEL.apple = conf.getInt("UseLevel.Apple");
		config.LEVEL.sugarCane = conf.getInt("UseLevel.SugarCane");
		config.LEVEL.goldenApple = conf.getInt("UseLevel.GoldenApple");
		config.LEVEL.cactus = conf.getInt("UseLevel.Cacti");
		config.LEVEL.sapling = conf.getInt("UseLevel.Sapling");
		config.LEVEL.redRose = conf.getInt("UseLevel.RedRose");
		config.LEVEL.yellowFlower = conf.getInt("UseLevel.YellowFlower");
		config.LEVEL.mushroom = conf.getInt("UseLevel.Mushroom");
		config.LEVEL.wheat = conf.getInt("UseLevel.Wheat");

		config.EXP.till = conf.getDouble("ExpGain.Till");
		config.EXP.harvest = conf.getDouble("ExpGain.Harvest");
		config.EXP.apple = conf.getDouble("ExpGain.Apple");
		config.EXP.sugarCane = conf.getDouble("ExpGain.SugarCane");
		config.EXP.goldenApple = conf.getDouble("ExpGain.GoldenApple");
		config.EXP.cactus = conf.getDouble("ExpGain.Cacti");
		config.EXP.sapling = conf.getDouble("ExpGain.Sapling");
		config.EXP.redRose = conf.getDouble("ExpGain.RedRose");
		config.EXP.yellowFlower = conf.getDouble("ExpGain.YellowFlower");
		config.EXP.mushroom = conf.getDouble("ExpGain.Mushroom");
		config.EXP.wheat = conf.getDouble("ExpGain.Wheat");

		config.DROP_LEVEL.pumpkinSeed = conf.getInt("DropLevel.PumpkinSeed");
		config.DROP_LEVEL.melonSeed = conf.getInt("DropLevel.MelonSeed");
		config.DROP_LEVEL.cocoaBean = conf.getInt("DropLevel.CocoaBean");

		saveConfig();
	}

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub

		LOG.info("[EC] " + getDescription().getFullName() + " disabled");
	}

	@Override
	public void onEnable() {
		loadConfig();
		createListeners();
		// TODO Auto-generated method stub

		Plugin expCraftCore = getServer().getPluginManager().getPlugin(
				"ExpCraftCore");
		if (expCraftCore == null) {
			LOG.log(Level.SEVERE,
					"[EC] Could not find ExpCraftCore. Disabling "
							+ getDescription().getName());
			getServer().getPluginManager().disablePlugin(this);
			return;
		} else {
			registerEvents();
			LOG.info("[EC] " + getDescription().getFullName() + " enabled");
		}

	}

	private void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener,
				Event.Priority.Highest, this);
		pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener,
				Event.Priority.Highest, this);
		pm.registerEvent(Event.Type.BLOCK_PLACE, blockListener,
				Event.Priority.Highest, this);
		// pm.registerEvent(Event.Type.PLAYER_EGG_THROW, playerListener,
		// Event.Priority.Normal, this);
	}

}
