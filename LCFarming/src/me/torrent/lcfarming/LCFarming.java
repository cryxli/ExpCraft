//Declare the package name
package me.torrent.lcfarming;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;
//Add all the required imports.
//Start the class
public class LCFarming extends JavaPlugin
{
  public final Logger logger = Logger.getLogger("Minecraft");
  //Assigns the logger with the value 'logger'
  private final LCBlockListener blockListener = new LCBlockListener(this);
  //Create a block listener
  private final LCPlayerListener playerListener = new LCPlayerListener(this);
  //Create a player listener
  final LCConfiguration LCConfiguration = new LCConfiguration(this);
  //Create a configuration.
  public String ConfigurationFileString = "plugins/LevelCraftCore/Configs/Farming.cfg";
 //Create a Configuration File (String).
  public File ConfigurationFile = new File("plugins/LevelCraftCore/Configs/Farming.cfg");
  //Create a Configuration File(File).
  public Plugin thisPlug;
//Create a Plugin variable 'thisPlug'

  
//onDisable. Called when the plugin is disabled.
  public void onDisable()
  {
  }
//onEnable. Called when the plugin is enabled.
  public void onEnable()
  {
    new File("plugins/LevelCraftCore/Configs/").mkdirs();
  //Create a new directory structure if not existent.
    try {
    	//Create a new configuration file.
      this.ConfigurationFile.createNewFile();
    } catch (IOException e) {
      this.logger.log(Level.SEVERE, "[LC] " + e);
    }
  //Load this configuration file.
    this.LCConfiguration.loadConfig();

    String[] Args = { "Fm", "Seeds", "farm", "farming" };
    getConfiguration().setProperty("ReferenceKeys", Args);
 // Set Reference Keys. One to four a good.
    
    //Set data to be shown on /lvl unlocks <ref>
    String[] Unlocks = { "Wooden Hoe = " + this.LCConfiguration.WoodHoe, "Stone Hone = " + this.LCConfiguration.StoneHoe, 
      "Iron Hoe = " + this.LCConfiguration.IronHoe, "Gold Hoe = " + this.LCConfiguration.GoldHoe, "Diamond Hoe = " + this.LCConfiguration.DiamondHoe };
    getConfiguration().setProperty("LevelUnlocks", Unlocks);
//Sets the LevelUnlocks
    getConfiguration().setProperty("LevelName", "Farming");
//Set the level name.
    getConfiguration().setProperty("ReferenceIndex", "Fm");
//Set the reference of the level.
    getConfiguration().setProperty("Author", "Torrent");
    //Set the author of the plugin.
    
  //Set data in parallel with Unlocks. (This denotes wethere the user has the unlock or not.)
    int[] UnlocksLevel = { this.LCConfiguration.WoodHoe, this.LCConfiguration.StoneHoe, 
      this.LCConfiguration.IronHoe, this.LCConfiguration.GoldHoe, this.LCConfiguration.DiamondHoe };

    
	//Sets the data for /lvl exp <ref>
    String[] Exp = { 
      "Exp Per Till " + this.LCConfiguration.ExpPerTill, 
      "Exp Per Harvest " + this.LCConfiguration.ExpPerHarvest, 
      "Exp Per SugarCane " + this.LCConfiguration.ExpPerSugarCane, 
      "Exp Per Cactus" + this.LCConfiguration.ExpPerCactus, 
      "Exp Per Sapling" + this.LCConfiguration.ExpPerSapling, 
      "Exp Per Yellow Flower" + this.LCConfiguration.ExpPerYellowFlower, 
      "Exp Per Red Flower" + this.LCConfiguration.ExpPerRedRose, 
      "Exp Per Mushroom" + this.LCConfiguration.ExpPerMushroom,
      "Exp Per Chicken" + this.LCConfiguration.ExpPerChicken,
      "Exp Per BoneMeal" + this.LCConfiguration.ExpPerBonemeal, };

    //When the player types /lvl help fm, this will display.
    String[] help = { "Plant crops, till land, and harvest other plants to gain XP","Unlock better farming tools at higher levels." };
   getConfiguration().setProperty("LevelHelp", help);
   
    getConfiguration().setProperty("LevelExpPer", Exp);
  //Set the LevelExpPer to the Exp Array.
    getConfiguration().setProperty("LevelUnlocksLevel", UnlocksLevel);
  //Set the LevelUnlocksLevel to the UnlocksLevel int array.
    this.thisPlug = getServer().getPluginManager().getPlugin("LCFarming");
 // Set Level Name.
    Plugin LevelCraftCore = getServer().getPluginManager().getPlugin("LevelCraftCore");
  //If the plugin for LevelCraftCore is null then disable the plugin. If not register the events and state that it is loaded.
    if (LevelCraftCore == null) {
      this.logger.log(Level.SEVERE, 
        "[LC] Could not fine LevelCraftCore. Disabling.");
      getServer().getPluginManager().disablePlugin(this);
    } else {
      registerEvents();
      this.logger.log(Level.INFO, "[LC] Level Farming "+this.getDescription().getVersion()+" Loaded");
    }
  }

  private void registerEvents()
  //Registers the necessary events
  {
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvent(Event.Type.BLOCK_BREAK, this.blockListener, 
    		//Registers the block break event.
      Event.Priority.Highest, this);
    //Sets how the plugin prioritises the event.
    //No point annotating the rest, they are the same in structure.
    pm.registerEvent(Event.Type.PLAYER_INTERACT, this.playerListener, 
      Event.Priority.Highest, this);
    pm.registerEvent(Event.Type.BLOCK_PLACE, this.blockListener,
    		Event.Priority.Highest, this);
    pm.registerEvent(Event.Type.PLAYER_EGG_THROW, this.playerListener,
    		Event.Priority.Normal, this);
  }
}