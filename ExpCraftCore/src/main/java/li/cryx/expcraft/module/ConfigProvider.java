/*
 * Copyright (c) 2011 Urs P. Stettler, https://github.com/cryxli
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
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

	/** The description of the module. */
	private final ModuleInfo info;

	/** The folder containing module config. */
	private final File configFolder;

	/** The config file. */
	private TypedProperties config;

	/**
	 * Create a ConfigProvider for a module (specified by its ModuleInfo) loaded
	 * by the given ExpCraft core.
	 * 
	 * @param core
	 *            The {@link ExpCraft} core managing the module.
	 * @param info
	 *            The description of the module.
	 */
	public ConfigProvider(final ExpCraft core, final ModuleInfo info) {
		this.info = info;
		configFolder = new File(core.getDataFolder(), "config");
		configFolder.mkdirs();
	}

	/**
	 * Get the config file. The config file is kept in memory, so calls to this
	 * method are cheap.
	 * 
	 * @return A {@link TypedProperties} containing the modules config file.
	 */
	public TypedProperties getConfig() {
		if (config == null) {
			loadConfig();
		}
		return config;
	}

	/**
	 * Get the location of the config file on disk.
	 * 
	 * @return File pointing to the config file.
	 */
	public File getConfigFile() {
		return new File(configFolder, info.getName() + ".properties");
	}

	/**
	 * Load the module's config. First load the default values from within the
	 * module's JAR and then replace the values with the ones from the config
	 * stored on disk.
	 */
	private void loadConfig() {
		config = new TypedProperties();

		// load default config
		InputStream in = info.getLoader().getResourceAsStream(
				MessageFormat.format(ModuleConstants.DEFAULT_CONFIG,
						info.getName()));
		if (in != null) {
			// ... if it exists
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
			// ... if it exists
			try {
				FileInputStream fis = new FileInputStream(file);
				config.load(fis);
				fis.close();
			} catch (IOException e) {
				LOG.warn("Error loading config", e);
			}
		}
	}

	/** Save the module's config to disk. */
	public void saveConfig() {
		try {
			config.store(getConfigFile(), MessageFormat.format(
					"Configuration for ExpCraft module {0}", info.getName()));
		} catch (IOException e) {
			LOG.warn("Error saving config", e);
		}
	}
}
