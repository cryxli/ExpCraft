package li.cryx.expcraft.module;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

import li.cryx.expcraft.ExpCraft;
import li.cryx.expcraft.loader.ModuleConstants;
import li.cryx.expcraft.loader.ModuleInfo;
import li.cryx.expcraft.util.TypedProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO documentation
 * 
 * @author cryxli
 */
public class ConfigProvider {

	private static final Logger LOG = LoggerFactory
			.getLogger(ConfigProvider.class);

	private final ModuleInfo info;

	private final File configFolder;

	private TypedProperties config;

	public ConfigProvider(final ExpCraft core, final ModuleInfo info) {
		this.info = info;
		configFolder = new File(core.getDataFolder(), "config");
		configFolder.mkdirs();
	}

	public TypedProperties getConfig() {
		if (config == null) {
			loadConfig();
		}
		return config;
	}

	public File getConfigFile() {
		return new File(configFolder, info.getName() + ".properties");
	}

	private void loadConfig() {
		config = new TypedProperties();

		// load default config
		InputStream in = info.getLoader().getResourceAsStream(
				MessageFormat.format(ModuleConstants.DEFAULT_CONFIG,
						info.getName()));
		if (in != null) {
			try {
				config.load(in);
				in.close();
			} catch (IOException e) {
				LOG.warn("Error loading default config", e);
			}
		}

		// load current config
		File file = getConfigFile();
		if (file.exists()) {
			try {
				FileInputStream fis = new FileInputStream(file);
				config.load(fis);
				fis.close();
			} catch (IOException e) {
				LOG.warn("Error loading config", e);
			}
		}
	}

	public void saveConfig() {
		try {
			config.store(getConfigFile(), MessageFormat.format(
					"Configuration for ExpCraft module {0}", info.getName()));
		} catch (IOException e) {
			LOG.warn("Error saving config", e);
		}
	}
}
