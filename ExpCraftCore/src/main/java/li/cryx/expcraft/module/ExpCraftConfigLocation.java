package li.cryx.expcraft.module;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * This class changes to location of the config files for ExpCraft modules. They
 * will be located in the <code>plugins/ExpCraft/config</code> directory.
 * 
 * @author cryxli
 */
public abstract class ExpCraftConfigLocation extends JavaPlugin {

	/** Keep the configuration in memory. */
	private FileConfiguration newConfig;

	/** Lazy load the config location. */
	private File configFile;

	/**
	 * Load config file for the current module.
	 * 
	 * @see #getConfigFile()
	 */
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

	/**
	 * This method changes the default location of the plugin config from
	 * <code><b>Plugin</b>/config.yml</code> to
	 * <code>ExpCraft/config/<b>Module</b>.yml</code>. While <b>Plugin</b> is
	 * the name of the module with which it registers with bukkit server, while
	 * <b>Module</b> is get result of {@link #getName()} method.
	 */
	private File getConfigFile() {
		if (configFile == null) {
			File folder = new File(getDataFolder().getParentFile(),
					"ExpCraft/config");
			configFile = new File(folder, getName() + ".yml");
		}
		return configFile;
	}

	/** Get the full name of the module. */
	abstract public String getModuleName();

	/**
	 * Save config of the module.
	 * 
	 * @see #getConfigFile()
	 */
	@Override
	public void saveConfig() {
		try {
			newConfig.save(getConfigFile());
		} catch (IOException ex) {
			Logger.getLogger(ExpCraftConfigLocation.class.getName()).log(
					Level.SEVERE, "Could not save config to " + configFile, ex);
		}
	}

	protected void unloadConfig() {
		newConfig = null;
	}

}
