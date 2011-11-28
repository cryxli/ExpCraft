package li.cryx.expcraft.module;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ExpCraftConfigLocation extends JavaPlugin {

	private FileConfiguration newConfig;

	private File configFile;

	@Override
	public FileConfiguration getConfig() {
		if (newConfig == null) {
			newConfig = YamlConfiguration.loadConfiguration(getConfigFile());

			InputStream defConfigStream = getResource("config.yml");
			if (defConfigStream != null) {
				YamlConfiguration defConfig = YamlConfiguration
						.loadConfiguration(defConfigStream);

				newConfig.setDefaults(defConfig);
			}

			newConfig.options().copyDefaults(true);
		}
		return newConfig;
	}

	private File getConfigFile() {
		if (configFile == null) {
			File folder = new File(getDataFolder(), "../ExpCraft/config");
			configFile = new File(folder, getName() + ".yml");
		}
		return configFile;
	}

	/** Get the full name of the module. */
	abstract public String getName();

	@Override
	public void saveConfig() {
		try {
			newConfig.save(getConfigFile());
		} catch (IOException ex) {
			Logger.getLogger(ExpCraftConfigLocation.class.getName()).log(
					Level.SEVERE, "Could not save config to " + configFile, ex);
		}
	}

}
