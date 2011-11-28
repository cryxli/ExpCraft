package me.torrent.lcfarming;

import java.io.IOException;
import java.util.logging.Level;

import me.samkio.levelcraftcore.util.Properties;

public class LCConfiguration {
	public LCFarming plugin;
	public int WoodHoe;
	public int StoneHoe;
	public int IronHoe;
	public int GoldHoe;
	public int DiamondHoe;
	public double ExpPerTill;
	public double ExpPerHarvest;
	public double ExpPerSugarCane;
	public double ExpPerApple;
	public double ExpPerGoldenApple;
	public double ExpPerCactus;
	public double ExpPerSapling;
	public double ExpPerRedRose;
	public double ExpPerYellowFlower;
	public double ExpPerMushroom;
	public double ExpPerWheat;
	public int TillLevel;
	public int HarvestLevel;
	public int SugarCaneLevel;
	public int AppleLevel;
	public int GoldenAppleLevel;
	public int CactusLevel;
	public int SaplingLevel;
	public int RedRoseLevel;
	public int YellowFlowerLevel;
	public int MushroomLevel;
	public int AnimalFeedLevel;

	// public int MoreWheatLevel;
	public int MelonLevel;
	public int PumkinLevel;
	public int DropCocoaBeanLevel;
	public int DropMelonSeedLevel;
	public int DropPumpkinSeedLevel;

	public LCConfiguration(LCFarming instance) {
		plugin = instance;
	}

	public void loadConfig() {
		Properties properties = new Properties(plugin.ConfigurationFileString);
		try {
			properties.load();
		} catch (IOException e) {
			plugin.logger.log(Level.SEVERE, "[LC] " + e);
		}

		WoodHoe = properties.getInteger("WoodenHoeLevel", 0);
		StoneHoe = properties.getInteger("StoneHoeLevel", 5);
		IronHoe = properties.getInteger("IronHoeLevel", 10);
		GoldHoe = properties.getInteger("GoldHoeLevel", 20);
		DiamondHoe = properties.getInteger("DiamondHoeLevel", 30);

		ExpPerTill = properties.getDouble("ExpPerTill", 1.0);
		ExpPerHarvest = properties.getDouble("ExpPerHarvest", 5.0);
		ExpPerApple = properties.getDouble("ExpPerApples", 3.0);
		ExpPerGoldenApple = properties.getDouble("ExpPerGoldenApple", 100.0);
		ExpPerSugarCane = properties.getDouble("ExpPerSugarCane", 2.0);
		ExpPerCactus = properties.getDouble("ExpPerCactus", 3.0);
		ExpPerSapling = properties.getDouble("ExpPerSapling", 0.5);
		ExpPerRedRose = properties.getDouble("ExpPerRedRose", 0.1);
		ExpPerYellowFlower = properties.getDouble("ExpPerYellowFlower", 0.1);
		ExpPerMushroom = properties.getDouble("ExpPerMushroom", 0.1);
		ExpPerWheat = properties.getDouble("ExpPerWheat", 2.0);

		TillLevel = properties.getInteger("LevelForTill", 0);
		HarvestLevel = properties.getInteger("LevelForHarvest", 0);
		AppleLevel = properties.getInteger("LevelForApple", 0);
		SugarCaneLevel = properties.getInteger("LevelForSugarCane", 0);
		GoldenAppleLevel = properties.getInteger("LevelForGoldenApple", 0);
		CactusLevel = properties.getInteger("LevelForCacti", 0);
		SaplingLevel = properties.getInteger("LevelForSapling", 0);
		RedRoseLevel = properties.getInteger("LevelForRedRose", 0);
		YellowFlowerLevel = properties.getInteger("LevelForYellowFlower", 0);

		MushroomLevel = properties.getInteger("LevelForMushroom", 0);
		AnimalFeedLevel = properties.getInteger("LevelForWheat", 0);
		MelonLevel = properties.getInteger("LevelForMelon", 0);
		PumkinLevel = properties.getInteger("LevelForPumkin", 0);

		DropCocoaBeanLevel = properties.getInteger("DropCocoaBeanLevel", 70);
		DropMelonSeedLevel = properties.getInteger("DropMelonSeedLevel", 40);
		DropPumpkinSeedLevel = properties
				.getInteger("DropPumpkinSeedLevel", 20);

	}
}