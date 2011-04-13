package me.samkio.levelcraft;

import java.io.IOException;


import me.samkio.levelcraft.SamToolbox.PropertyFunctions;

public class Settings {
	public  Levelcraft plugin;
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

	

	public Settings(Levelcraft instance) {
		plugin = instance;
	}

	public  void loadMain() {

		String propertiesFile =  plugin.maindirectory
				+  plugin.configdirectory + "MainConfig.properties";
		PropertyFunctions properties = new PropertyFunctions(propertiesFile);
		try {
			properties.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.c1 = properties.getString("Colour-One","GOLD");
        this.c2 = properties.getString("Colour-Two","YELLOW");
        this.c3 = properties.getString("Colour-Good","GREEN");
        this.c4 = properties.getString("Colour-Bad","RED");
        this.repairTools = properties.getBoolean("Enable-Repair-Tools",true);
        this.enableWCLevel = properties.getBoolean("Enable-Woodcuting-Level", true);
        this.enableForgeLevel = properties.getBoolean("Enable-Forge-Level", true);
        this.enableMineLevel = properties.getBoolean("Enable-Mining-Level", true);
        this.enableSlayerLevel = properties.getBoolean("Enable-Slayer-Level", true);
        this.enableRangeLevel = properties.getBoolean("Enable-Range-Level", true);
        this.enableFisticuffsLevel = properties.getBoolean(
				"Enable-Fisticuffs-Level", true);
        this.enableArcherLevel = properties.getBoolean("Enable-Archer-Level", true);
        this.enableDigLevel = properties.getBoolean("Enable-Digging-Level", true);
        this.Constant = properties.getInteger("Level-Constant", 20);
        this.database = properties.getString("Database", "flatfile");
		this.MySqlDir = properties.getString("MySqlDatabaseDirectory",
				"localhost:3306/LC");
		this.MySqlUser = properties.getString("MySqlDatabaseUsername", "root");
		this.MySqlPass = properties.getString("MySqlDatabasePassword", "");
		this.UsePerms = properties.getBoolean("User-Permissions", true);
		this.AntiBoost = properties.getBoolean("AntiBoost", true);
		properties.save();

	}

	public  void loadWhitelist() {
		String propertiesFile =  plugin.maindirectory
				+  plugin.configdirectory + "Whitelist.properties";
		PropertyFunctions properties = new PropertyFunctions(propertiesFile);
		try {
			properties.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.rawAdmins = properties.getString("Admins", "Name1,Name2");
		this.rawAvoid = properties.getString("Whitelist", "Name1,Name2");
		if (rawAdmins != null) {
			this.LCAdmins = rawAdmins.split(",");

		}
		if (rawAvoid != null) {
			this.LCAvoiders = rawAvoid.split(",");

		}
		properties.save();

	}

	public  void loadWoodcut() {
		String propertiesFile =  plugin.maindirectory
				+  plugin.configdirectory + "WoodCuttingConfig.properties";
		PropertyFunctions properties = new PropertyFunctions(propertiesFile);
		try {
			properties.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.WCWoodAxe = properties.getInteger("Wooden-Axe-Level", 0);
		this.WCStoneAxe = properties.getInteger("Stone-Axe-Level", 5);
		this.WCIronAxe = properties.getInteger("Iron-Axe-Level", 10);
		this.WCGoldAxe = properties.getInteger("Gold-Axe-Level", 20);
		this.WCDiamondAxe = properties.getInteger("Diamond-Axe-Level", 30);
		this.ExpPerLog = properties.getDouble("Experience-Per-Log", 5);
		this.ExpPerPlank = properties.getDouble("Experience-Per-Plank", 2);
		this.LogLevel = properties.getInteger("Level-for-Log", 0);
		this.PlankLevel = properties.getInteger("Level-for-Plank", 0);

		properties.save();
	}

	public  void loadSlayer() {
		String propertiesFile =  plugin.maindirectory
				+  plugin.configdirectory + "SlayerConfig.properties";
		PropertyFunctions properties = new PropertyFunctions(propertiesFile);
		try {
			properties.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.SlayWoodSword = properties.getInteger("Wooden-Sword-Level", 0);
		this.SlayStoneSword = properties.getInteger("Stone-Sword-Level", 5);
		this.SlayIronSword = properties.getInteger("Iron-Sword-Level", 10);
		this.SlayGoldSword = properties.getInteger("Gold-Sword-Level", 20);
		this.SlayDiamondSword = properties.getInteger("Diamond-Sword-Level", 30);
		this.ExpPerDamage = properties.getDouble("Experience-per-1-damage", 2);

		properties.save();
	}

	public  void loadRange() {
		String propertiesFile =  plugin.maindirectory
				+  plugin.configdirectory + "RangeConfig.properties";
		PropertyFunctions properties = new PropertyFunctions(propertiesFile);
		try {
			properties.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.Rangep5 = properties.getInteger("Range-.5-Damage", 0);
		this.Range1p0 = properties.getInteger("Range-1.0-Damage", 5);
		this.Range1p5 = properties.getInteger("Range-1.5-Damage", 10);
		this.Range2p0 = properties.getInteger("Range-2.0-Damage", 20);
		this.Range2p5 = properties.getInteger("Range-2.5-Damage", 30);
		this.Range3p0 = properties.getInteger("Range-3.0-Damage", 50);

		properties.save();
	}

	public  void loadArcher() {
		String propertiesFile =  plugin.maindirectory
				+  plugin.configdirectory + "ArcherConfig.properties";
		PropertyFunctions properties = new PropertyFunctions(propertiesFile);
		try {
			properties.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.Archerp5 = properties.getInteger("Archer-.5-Damage", 0);
		this.Archer1p0 = properties.getInteger("Archer-1.0-Damage", 5);
		this.Archer1p5 = properties.getInteger("Archer-1.5-Damage", 10);
		this.Archer2p0 = properties.getInteger("Archer-2.0-Damage", 20);
		this.Archer2p5 = properties.getInteger("Archer-2.5-Damage", 30);
		this.Archer3p0 = properties.getInteger("Archer-3.0-Damage", 50);

		properties.save();
	}

	public  void loadFisticuffs() {
		String propertiesFile =  plugin.maindirectory
				+  plugin.configdirectory + "FisticuffsConfig.properties";
		PropertyFunctions properties = new PropertyFunctions(propertiesFile);
		try {
			properties.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.FisticuffsWoodSword = properties.getInteger(
				"Fisticuffs-Wooden-Sword-Level", 0);
		this.FisticuffsStoneSword = properties.getInteger(
				"Fisticuffs-Stone-Sword-Level", 5);
		this.FisticuffsIronSword = properties.getInteger(
				"Fisticuffs-Iron-Sword-Level", 10);
		this.FisticuffsGoldSword = properties.getInteger(
				"Fisticuffs-Gold-Sword-Level", 20);
		this.FisticuffsDiamondSword = properties.getInteger(
				"Fisticuffs-Diamond-Sword-Level", 30);

		properties.save();
	}

	public  void loadMine() {
		String propertiesFile =  plugin.maindirectory
				+  plugin.configdirectory + "MiningConfig.properties";
		PropertyFunctions properties = new PropertyFunctions(propertiesFile);
		try {
			properties.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.MIWoodPick = properties.getInteger("Wooden-Pick-Level", 0);
		this.MIStonePick = properties.getInteger("Stone-Pick-Level", 5);
		this.MIIronPick = properties.getInteger("Iron-Pick-Level", 10);
		this.MIGoldPick = properties.getInteger("Gold-Pick-Level", 20);
		this.MIDiamondPick = properties.getInteger("Diamond-Pick-Level", 30);

		this.ExpPerStone = properties.getDouble("Experience-Per-Stone", 5);
		this.ExpPerCobble = properties.getDouble("Experience-Per-Cobble", 2);
		this.ExpPerRedstone = properties.getDouble("Experience-Per-Redstone", 20);
		this.ExpPerGoldOre = properties.getDouble("Experience-Per-GoldOre", 30);
		this.ExpPerIronOre = properties.getDouble("Experience-Per-IronOre", 20);
		this.ExpPerCoalOre = properties.getDouble("Experience-Per-CoalOre", 10);
		this.ExpPerLapisOre = properties.getDouble("Experience-Per-LapisOre", 100);
		this.ExpPerMossstone = properties.getDouble("Experience-Per-MossStone", 10);
		this.ExpPerObsidian = properties.getDouble("Experience-Per-Obsidian", 200);
		this.ExpPerDiamondOre = properties.getDouble("Experience-Per-DiamondOre",
				100);
		this.ExpPerNetherrack = properties
				.getInteger("Experience-Per-Netherrack", 3);
		this.ExpPerSandStone = properties.getInteger("Experience-Per-SandStone", 3);
		this.StoneLevel = properties.getInteger("Level-for-Stone", 0);
		this.SandStoneLevel = properties.getInteger("Level-for-SandStone", 0);
		this.CobbleLevel = properties.getInteger("Level-for-Cobble", 0);
		this.RedLevel = properties.getInteger("Level-for-Redstone", 10);
		this.GoldLevel = properties.getInteger("Level-for-GoldOre", 20);
		this.IronLevel = properties.getInteger("Level-for-IronOre", 5);
		this.CoalLevel = properties.getInteger("Level-for-CoalOre", 5);
		this.LapisLevel = properties.getInteger("Level-for-LapisOre", 20);
		this.MossLevel = properties.getInteger("Level-for-MossStone", 10);
		this.ObsidianLevel = properties.getInteger("Level-for-Obsidian", 35);
		this.DiamondLevel = properties.getInteger("Level-for-Diamond", 25);
		this.NetherLevel = properties.getInteger("Level-for-Nether", 0);

		properties.save();
	}

	public  void loadForge() {
		String propertiesFile =  plugin.maindirectory
				+  plugin.configdirectory + "ForgeConfig.properties";
		PropertyFunctions properties = new PropertyFunctions(propertiesFile);
		try {
			properties.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.ExpPerIronbar = properties.getDouble("Experience-Per-IronBar",10);
		this.ExpPerGoldbar = properties.getDouble("Experience-Per-GoldBar",20);
		this.ironBarLevel = properties.getInteger("Level-for-IronBar",0);
		this.goldBarLevel = properties.getInteger("Level-for-GoldBar",5);
		properties.save();
	}

	public  void loadDigging() {
		String propertiesFile = plugin.maindirectory
				+  plugin.configdirectory + "DiggingConfig.properties";
		PropertyFunctions properties = new PropertyFunctions(propertiesFile);
		try {
			properties.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.DWoodShov = properties.getInteger("Wooden-Shovel-Level", 0);
		this.DStoneShov = properties.getInteger("Stone-Shovel-Level", 5);
		this.DIronShov = properties.getInteger("Iron-Shovel-Level", 10);
		this.DGoldShov = properties.getInteger("Gold-Shovel-Level", 20);
		this.DDiamondShov = properties.getInteger("Diamond-Shovel-Level", 30);

		this.ExpPerSand = properties.getDouble("Experience-Per-Sand", 3);
		this.ExpPerGravel = properties.getDouble("Experience-Per-Gravel", 3);
		this.ExpPerDirt = properties.getDouble("Experience-Per-Dirt", 2);
		this.ExpPerGrass = properties.getDouble("Experience-Per-Grass", 3);
		this.ExpPerSoulSand = properties.getDouble("Experience-Per-SoulSand", 5);
		this.ExpPerSnow = properties.getDouble("Experience-Per-Snow", 10);
		this.ExpPerClay = properties.getDouble("Experience-Per-Clay", 10);
		this.SandLevel = properties.getInteger("Level-for-Sand", 0);
		this.GravelLevel = properties.getInteger("Level-for-Gravel", 0);
		this.DirtLevel = properties.getInteger("Level-for-Dirt", 0);
		this.GrassLevel = properties.getInteger("Level-for-Grass", 0);
		this.SoulSandLevel = properties.getInteger("Level-for-SoulSand", 0);
		this.SnowLevel = properties.getInteger("Level-for-Snow", 0);
		this.ClayLevel = properties.getInteger("Level-for-Clay", 0);

		properties.save();

	}

	
}
