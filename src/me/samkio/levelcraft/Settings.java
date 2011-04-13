package me.samkio.levelcraft;

import java.io.IOException;


import me.samkio.levelcraft.SamToolbox.PropertyFunctions;

public class Settings {
	
	public  String c1;
	public  String c2;
	public  String c3;
	public  String c4;
	public  boolean repairTools;
	public  boolean enableWCLevel;
	public  boolean enableMineLevel;
	public  boolean enableRangeLevel;
	public  boolean enableSlayerLevel;
	public  boolean enableFisticuffsLevel;
	public  boolean enableArcherLevel;
	public  boolean enableDigLevel;
	public  boolean enableForgeLevel;

	public  int WCWoodAxe;
	public  int WCStoneAxe;
	public  int WCIronAxe;
	public  int WCGoldAxe;
	public  int WCDiamondAxe;

	public  int DWoodShov;
	public  int DStoneShov;
	public  int DIronShov;
	public  int DGoldShov;
	public  int DDiamondShov;

	public  int SlayWoodSword;
	public  int SlayStoneSword;
	public  int SlayIronSword;
	public  int SlayGoldSword;
	public  int SlayDiamondSword;

	public  int FisticuffsWoodSword;
	public  int FisticuffsStoneSword;
	public  int FisticuffsIronSword;
	public  int FisticuffsGoldSword;
	public  int FisticuffsDiamondSword;

	public  int MIWoodPick;
	public  int MIStonePick;
	public  int MIIronPick;
	public  int MIGoldPick;
	public  int MIDiamondPick;

	public  double ExpPerLog;
	public  double ExpPerPlank;
	public  int LogLevel;
	public  int PlankLevel;

	public  double ExpPerStone;
	public  double ExpPerCobble;
	public  double ExpPerRedstone;
	public  double ExpPerGoldOre;
	public  double ExpPerIronOre;
	public  double ExpPerCoalOre;
	public  double ExpPerLapisOre;
	public  double ExpPerMossstone;
	public  double ExpPerObsidian;
	public  double ExpPerDiamondOre;
	public  double ExpPerNetherrack;
	public  double ExpPerSandStone;
	public  double ExpPerDamage;

	public  double ExpPerSand;
	public  double ExpPerClay;
	public  double ExpPerGravel;
	public  double ExpPerDirt;
	public  double ExpPerGrass;
	public  double ExpPerSoulSand;
	public  double ExpPerSnow;

	public  int SandLevel;
	public  int GravelLevel;
	public  int DirtLevel;
	public  int GrassLevel;
	public  int SoulSandLevel;
	public  int SnowLevel;
	public  int ClayLevel;

	public  int ironBarLevel;
	public  int goldBarLevel;
	public  double ExpPerIronbar;
	public  double ExpPerGoldbar;

	public  int Rangep5;
	public  int Range1p0;
	public  int Range1p5;
	public  int Range2p0;
	public  int Range2p5;
	public  int Range3p0;

	public  int Archerp5;
	public  int Archer1p0;
	public  int Archer1p5;
	public  int Archer2p0;
	public  int Archer2p5;
	public  int Archer3p0;

	public  int StoneLevel;
	public  int CobbleLevel;
	public  int IronLevel;
	public  int RedLevel;
	public  int GoldLevel;
	public  int CoalLevel;
	public  int LapisLevel;
	public  int MossLevel;
	public  int ObsidianLevel;
	public  int DiamondLevel;
	public  int NetherLevel;
	public  int SandStoneLevel;
	public  String rawAdmins;
	public  String rawAvoid;
	public  String[] LCAdmins;
	public  String[] LCAvoiders;

	public  int Constant;
	public  String database;
	public  String MySqlDir;
	public  String MySqlUser;
	public  String MySqlPass;
	public  boolean AntiBoost;
	public  boolean UsePerms;

	public  void loadMain() {

		String propertiesFile = Levelcraft.maindirectory
				+ Levelcraft.configdirectory + "MainConfig.properties";
		PropertyFunctions properties = new PropertyFunctions(propertiesFile);
		try {
			properties.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Levelcraft.log.info("LOL");
        c4 = properties.getString("Colour-One","GOLD");
        c2 = properties.getString("Colour-Two","YELLOW");
        c3 = properties.getString("Colour-Good","GREEN");
        c4 = properties.getString("Colour-Bad","RED");
        repairTools = properties.getBoolean("Enable-Repair-Tools",true);
		enableWCLevel = properties.getBoolean("Enable-Woodcuting-Level", true);
		enableForgeLevel = properties.getBoolean("Enable-Forge-Level", true);
		enableMineLevel = properties.getBoolean("Enable-Mining-Level", true);
		enableSlayerLevel = properties.getBoolean("Enable-Slayer-Level", true);
		enableRangeLevel = properties.getBoolean("Enable-Range-Level", true);
		enableFisticuffsLevel = properties.getBoolean(
				"Enable-Fisticuffs-Level", true);
		enableArcherLevel = properties.getBoolean("Enable-Archer-Level", true);
		enableDigLevel = properties.getBoolean("Enable-Digging-Level", true);
		Constant = properties.getInteger("Level-Constant", 20);
		database = properties.getString("Database", "flatfile");
		MySqlDir = properties.getString("MySqlDatabaseDirectory",
				"localhost:3306/LC");
		MySqlUser = properties.getString("MySqlDatabaseUsername", "root");
		MySqlPass = properties.getString("MySqlDatabasePassword", "");
		UsePerms = properties.getBoolean("User-Permissions", true);
		AntiBoost = properties.getBoolean("AntiBoost", true);
		properties.save();

	}

	public  void loadWhitelist() {
		String propertiesFile = Levelcraft.maindirectory
				+ Levelcraft.configdirectory + "Whitelist.properties";
		PropertyFunctions properties = new PropertyFunctions(propertiesFile);
		try {
			properties.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		rawAdmins = properties.getString("Admins", "Name1,Name2");
		rawAvoid = properties.getString("Whitelist", "Name1,Name2");
		if (rawAdmins != null) {
			LCAdmins = rawAdmins.split(",");

		}
		if (rawAvoid != null) {
			LCAvoiders = rawAvoid.split(",");

		}
		properties.save();

	}

	public  void loadWoodcut() {
		String propertiesFile = Levelcraft.maindirectory
				+ Levelcraft.configdirectory + "WoodCuttingConfig.properties";
		PropertyFunctions properties = new PropertyFunctions(propertiesFile);
		try {
			properties.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		WCWoodAxe = properties.getInteger("Wooden-Axe-Level", 0);
		WCStoneAxe = properties.getInteger("Stone-Axe-Level", 5);
		WCIronAxe = properties.getInteger("Iron-Axe-Level", 10);
		WCGoldAxe = properties.getInteger("Gold-Axe-Level", 20);
		WCDiamondAxe = properties.getInteger("Diamond-Axe-Level", 30);
		ExpPerLog = properties.getDouble("Experience-Per-Log", 5);
		ExpPerPlank = properties.getDouble("Experience-Per-Plank", 2);
		LogLevel = properties.getInteger("Level-for-Log", 0);
		PlankLevel = properties.getInteger("Level-for-Plank", 0);

		properties.save();
	}

	public  void loadSlayer() {
		String propertiesFile = Levelcraft.maindirectory
				+ Levelcraft.configdirectory + "SlayerConfig.properties";
		PropertyFunctions properties = new PropertyFunctions(propertiesFile);
		try {
			properties.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SlayWoodSword = properties.getInteger("Wooden-Sword-Level", 0);
		SlayStoneSword = properties.getInteger("Stone-Sword-Level", 5);
		SlayIronSword = properties.getInteger("Iron-Sword-Level", 10);
		SlayGoldSword = properties.getInteger("Gold-Sword-Level", 20);
		SlayDiamondSword = properties.getInteger("Diamond-Sword-Level", 30);
		ExpPerDamage = properties.getDouble("Experience-per-1-damage", 2);

		properties.save();
	}

	public  void loadRange() {
		String propertiesFile = Levelcraft.maindirectory
				+ Levelcraft.configdirectory + "RangeConfig.properties";
		PropertyFunctions properties = new PropertyFunctions(propertiesFile);
		try {
			properties.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Rangep5 = properties.getInteger("Range-.5-Damage", 0);
		Range1p0 = properties.getInteger("Range-1.0-Damage", 5);
		Range1p5 = properties.getInteger("Range-1.5-Damage", 10);
		Range2p0 = properties.getInteger("Range-2.0-Damage", 20);
		Range2p5 = properties.getInteger("Range-2.5-Damage", 30);
		Range3p0 = properties.getInteger("Range-3.0-Damage", 50);

		properties.save();
	}

	public  void loadArcher() {
		String propertiesFile = Levelcraft.maindirectory
				+ Levelcraft.configdirectory + "ArcherConfig.properties";
		PropertyFunctions properties = new PropertyFunctions(propertiesFile);
		try {
			properties.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Archerp5 = properties.getInteger("Archer-.5-Damage", 0);
		Archer1p0 = properties.getInteger("Archer-1.0-Damage", 5);
		Archer1p5 = properties.getInteger("Archer-1.5-Damage", 10);
		Archer2p0 = properties.getInteger("Archer-2.0-Damage", 20);
		Archer2p5 = properties.getInteger("Archer-2.5-Damage", 30);
		Archer3p0 = properties.getInteger("Archer-3.0-Damage", 50);

		properties.save();
	}

	public  void loadFisticuffs() {
		String propertiesFile = Levelcraft.maindirectory
				+ Levelcraft.configdirectory + "FisticuffsConfig.properties";
		PropertyFunctions properties = new PropertyFunctions(propertiesFile);
		try {
			properties.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FisticuffsWoodSword = properties.getInteger(
				"Fisticuffs-Wooden-Sword-Level", 0);
		FisticuffsStoneSword = properties.getInteger(
				"Fisticuffs-Stone-Sword-Level", 5);
		FisticuffsIronSword = properties.getInteger(
				"Fisticuffs-Iron-Sword-Level", 10);
		FisticuffsGoldSword = properties.getInteger(
				"Fisticuffs-Gold-Sword-Level", 20);
		FisticuffsDiamondSword = properties.getInteger(
				"Fisticuffs-Diamond-Sword-Level", 30);

		properties.save();
	}

	public  void loadMine() {
		String propertiesFile = Levelcraft.maindirectory
				+ Levelcraft.configdirectory + "MiningConfig.properties";
		PropertyFunctions properties = new PropertyFunctions(propertiesFile);
		try {
			properties.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MIWoodPick = properties.getInteger("Wooden-Pick-Level", 0);
		MIStonePick = properties.getInteger("Stone-Pick-Level", 5);
		MIIronPick = properties.getInteger("Iron-Pick-Level", 10);
		MIGoldPick = properties.getInteger("Gold-Pick-Level", 20);
		MIDiamondPick = properties.getInteger("Diamond-Pick-Level", 30);

		ExpPerStone = properties.getDouble("Experience-Per-Stone", 5);
		ExpPerCobble = properties.getDouble("Experience-Per-Cobble", 2);
		ExpPerRedstone = properties.getDouble("Experience-Per-Redstone", 20);
		ExpPerGoldOre = properties.getDouble("Experience-Per-GoldOre", 30);
		ExpPerIronOre = properties.getDouble("Experience-Per-IronOre", 20);
		ExpPerCoalOre = properties.getDouble("Experience-Per-CoalOre", 10);
		ExpPerLapisOre = properties.getDouble("Experience-Per-LapisOre", 100);
		ExpPerMossstone = properties.getDouble("Experience-Per-MossStone", 10);
		ExpPerObsidian = properties.getDouble("Experience-Per-Obsidian", 200);
		ExpPerDiamondOre = properties.getDouble("Experience-Per-DiamondOre",
				100);
		ExpPerNetherrack = properties
				.getInteger("Experience-Per-Netherrack", 3);
		ExpPerSandStone = properties.getInteger("Experience-Per-SandStone", 3);
		StoneLevel = properties.getInteger("Level-for-Stone", 0);
		SandStoneLevel = properties.getInteger("Level-for-SandStone", 0);
		CobbleLevel = properties.getInteger("Level-for-Cobble", 0);
		RedLevel = properties.getInteger("Level-for-Redstone", 10);
		GoldLevel = properties.getInteger("Level-for-GoldOre", 20);
		IronLevel = properties.getInteger("Level-for-IronOre", 5);
		CoalLevel = properties.getInteger("Level-for-CoalOre", 5);
		LapisLevel = properties.getInteger("Level-for-LapisOre", 20);
		MossLevel = properties.getInteger("Level-for-MossStone", 10);
		ObsidianLevel = properties.getInteger("Level-for-Obsidian", 35);
		DiamondLevel = properties.getInteger("Level-for-Diamond", 25);
		NetherLevel = properties.getInteger("Level-for-Nether", 0);

		properties.save();
	}

	public  void loadForge() {
		String propertiesFile = Levelcraft.maindirectory
				+ Levelcraft.configdirectory + "ForgeConfig.properties";
		PropertyFunctions properties = new PropertyFunctions(propertiesFile);
		try {
			properties.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ExpPerIronbar = properties.getDouble("Experience-Per-IronBar",10);
		ExpPerGoldbar = properties.getDouble("Experience-Per-GoldBar",20);
		ironBarLevel = properties.getInteger("Level-for-IronBar",0);
		goldBarLevel = properties.getInteger("Level-for-GoldBar",5);
		properties.save();
	}

	public  void loadDigging() {
		String propertiesFile = Levelcraft.maindirectory
				+ Levelcraft.configdirectory + "DiggingConfig.properties";
		PropertyFunctions properties = new PropertyFunctions(propertiesFile);
		try {
			properties.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DWoodShov = properties.getInteger("Wooden-Shovel-Level", 0);
		DStoneShov = properties.getInteger("Stone-Shovel-Level", 5);
		DIronShov = properties.getInteger("Iron-Shovel-Level", 10);
		DGoldShov = properties.getInteger("Gold-Shovel-Level", 20);
		DDiamondShov = properties.getInteger("Diamond-Shovel-Level", 30);

		ExpPerSand = properties.getDouble("Experience-Per-Sand", 3);
		ExpPerGravel = properties.getDouble("Experience-Per-Gravel", 3);
		ExpPerDirt = properties.getDouble("Experience-Per-Dirt", 2);
		ExpPerGrass = properties.getDouble("Experience-Per-Grass", 3);
		ExpPerSoulSand = properties.getDouble("Experience-Per-SoulSand", 5);
		ExpPerSnow = properties.getDouble("Experience-Per-Snow", 10);
		ExpPerClay = properties.getDouble("Experience-Per-Clay", 10);
		SandLevel = properties.getInteger("Level-for-Sand", 0);
		GravelLevel = properties.getInteger("Level-for-Gravel", 0);
		DirtLevel = properties.getInteger("Level-for-Dirt", 0);
		GrassLevel = properties.getInteger("Level-for-Grass", 0);
		SoulSandLevel = properties.getInteger("Level-for-SoulSand", 0);
		SnowLevel = properties.getInteger("Level-for-Snow", 0);
		ClayLevel = properties.getInteger("Level-for-Clay", 0);

		properties.save();

	}

	
}
