package me.samkio.lccombat;


import java.io.IOException;
import java.util.logging.Level;
import me.samkio.levelcraftcore.util.Properties;
/* This class sets up the configuration for the variables.
 * It also sets up the configuration file with the variables.
 * Important to note it imports the properties functions from LevelCraftCore
 */

public class LCConfiguration {
	public LCCombat plugin;
	//TOOLS
	public int WoodSword;
	public int StoneSword;
	public int IronSword;
	public int GoldSword;
	public int DiamondSword;
	//EXP PER
	public double ExpPerDamageMob;
	public double ExpPerDamagePVP;

	public boolean pvpRangeEnable;
	public int pvpRange;
	public LCConfiguration(LCCombat instance) {
		plugin = instance;
	}
    //loadConfig. Called onEnable in main class.
	public void loadConfig() {
        //Create new properties file with the file string.
		Properties properties = new Properties(plugin.ConfigurationFileString);
		try {
			//Try to load it. If not return an error.
			properties.load();
		} catch (IOException e) {
			plugin.logger.log(Level.SEVERE,"[LC] "+e);
		}
		//Set variables for TOOLS
		this.WoodSword = properties.getInteger("WoodenSwordLevel", 0);
		this.StoneSword = properties.getInteger("StoneSwordLevel", 5);
		this.IronSword = properties.getInteger("IronSwordLevel", 10);
		this.GoldSword = properties.getInteger("GoldSwordLevel", 20);
		this.DiamondSword = properties.getInteger("DiamondSwordLevel", 30);
		
		//Set variables for EXP PER
		this.ExpPerDamageMob = properties.getDouble("ExpPerDamageMob", 5);
		this.ExpPerDamagePVP = properties.getDouble("ExpPerDamagePVP", 5);
		
		//
		this.pvpRangeEnable = properties.getBoolean("EnablePvpOnlyRange",false);
		this.pvpRange = properties.getInteger("PvpRange", 5);


	}
}
