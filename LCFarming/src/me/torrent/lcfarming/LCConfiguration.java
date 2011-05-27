package me.torrent.lcfarming;

import java.io.IOException;
import java.util.logging.Level;

import me.samkio.levelcraftcore.util.Properties;

public class LCConfiguration
{
  public LCFarming plugin;
  //Tools
  public int WoodHoe;
  public int StoneHoe;
  public int IronHoe;
  public int GoldHoe;
  public int DiamondHoe;
  //EXP Per
  public double ExpPerTill;
  public double ExpPerHarvest;
  public double ExpPerSugarCane;
  public double ExpPerCactus;
  public double ExpPerSapling;
  public double ExpPerRedRose;
  public double ExpPerYellowFlower;
  public double ExpPerMushroom;
  public double ExpPerChicken;
  public double ExpPerBonemeal;
  //Block Level
  public int TillLevel;
  public int HarvestLevel;
  public int SugarCaneLevel;
  public int CactusLevel;
  public int SaplingLevel;
  public int RedRoseLevel;
  public int YellowFlowerLevel;
  public int MushroomLevel;
  public int ChickenLevel;
  public int BonemealLevel;

  public LCConfiguration(LCFarming instance)
  {
    this.plugin = instance;
  }

  public void loadConfig()
  {
    Properties properties = new Properties(this.plugin.ConfigurationFileString);
    try {
      properties.load();
    } catch (IOException e) {
      this.plugin.logger.log(Level.SEVERE, "[LC] " + e);
    }
//Setting the level for the tools.
    this.WoodHoe = properties.getInteger("WoodenHoeLevel", 0);
    this.StoneHoe = properties.getInteger("StoneHoeLevel", 5);
    this.IronHoe = properties.getInteger("IronHoeLevel", 10);
    this.GoldHoe = properties.getInteger("GoldHoeLevel", 20);
    this.DiamondHoe = properties.getInteger("DiamondHoeLevel", 30);

    //Setting the EXP for the blocks interacted with.
    this.ExpPerTill = properties.getDouble("ExpPerTill", 1.0D);
    this.ExpPerHarvest = properties.getDouble("ExpPerHarvest", 5.0D);
    this.ExpPerSugarCane = properties.getDouble("ExpPerSugarCane", 2.0D);
    this.ExpPerCactus = properties.getDouble("ExpPerCactus", 3.0D);
    this.ExpPerSapling = properties.getDouble("ExpPerSapling", 0.5D);
    this.ExpPerRedRose = properties.getDouble("ExpPerRedRose", 0.1D);
    this.ExpPerYellowFlower = properties.getDouble("ExpPerYellowFlower", 0.1D);
    this.ExpPerMushroom = properties.getDouble("ExpPerMushroom", 0.1D);
    this.ExpPerChicken = properties.getDouble("ExpPerChicken", 50.0D);
    this.ExpPerBonemeal = properties.getDouble("ExpPerBoneMeal", 10.0D);

    //Setting the level for the blocks interacted with.
    this.TillLevel = properties.getInteger("LevelForTill", 0);
    this.HarvestLevel = properties.getInteger("LevelForHarvest", 0);
    this.SugarCaneLevel = properties.getInteger("LevelForSugarCane", 0);
    this.CactusLevel = properties.getInteger("LevelForCacti", 0);
    this.SaplingLevel = properties.getInteger("LevelForSapling", 0);
    this.RedRoseLevel = properties.getInteger("LevelForRedRose", 0);
    this.YellowFlowerLevel = properties.getInteger("LevelForYellowFlower", 0);
    this.MushroomLevel = properties.getInteger("LevelForMushroom", 0);
    this.ChickenLevel = properties.getInteger("LevelForChicken", 0);
    this.BonemealLevel = properties.getInteger("LevelForBoneMeal", 0);
  }
}