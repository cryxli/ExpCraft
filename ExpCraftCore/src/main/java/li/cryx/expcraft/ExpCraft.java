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
package li.cryx.expcraft;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.PersistenceException;

import li.cryx.expcraft.cmd.CommandManager;
import li.cryx.expcraft.loader.JarScanner;
import li.cryx.expcraft.loader.ModuleInfo;
import li.cryx.expcraft.loader.ModuleLoader;
import li.cryx.expcraft.module.ConfigProvider;
import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.perm.AbstractPermissionManager;
import li.cryx.expcraft.perm.NoPermissionManager;
import li.cryx.expcraft.perm.PermissionsBukkitManager;
import li.cryx.expcraft.perm.PermissionsExManager;
import li.cryx.expcraft.persist.AbstractPersistenceManager;
import li.cryx.expcraft.persist.PersistenceDatabaseBukkit;
import li.cryx.expcraft.persist.PersistenceFlatFile;
import li.cryx.expcraft.persist.model.Experience;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is the main class of ExCraft. It is also the main entry point for
 * the bukkit plugin.
 * 
 * <p>
 * The ExpCraft pluing resides within the plugins folder of a CraftBukkit
 * server. The aspects of ExpCraft are implemented in {@link ExpCraftModule}
 * which form separate JARs that do not need to be present in the plugins
 * folder, but in the folder specified in the
 * <code>ExpCraft/config/Core.properties</code> config file.
 * </p>
 * 
 * @author cryxli
 */
public class ExpCraft extends JavaPlugin {

	private static final Logger LOG = LoggerFactory.getLogger(ExpCraft.class);

	/** Look-up structure for modules. */
	private final Map<String, ExpCraftModule> modules = new TreeMap<String, ExpCraftModule>();

	/** Implementation of a permission manager. */
	private AbstractPermissionManager permission;

	/** Implementation of a persistence manager. */
	private AbstractPersistenceManager persistence;

	/** The configuration of the ExpCraft core. */
	private ConfigProvider config;

	/** The in-game command manager. */
	private final CommandManager cmd;

	/** Create a new plugin instance. Called by bukkit. */
	public ExpCraft() {
		cmd = new CommandManager(this);
	}

	@Override
	public List<Class<?>> getDatabaseClasses() {
		List<Class<?>> list = new LinkedList<Class<?>>();
		list.add(Experience.class);
		return list;
	}

	/**
	 * Get the level cap of ExpCraft.
	 * 
	 * @return Maximal reachable level for each module.
	 */
	public int getLevelCap() {
		return config.getConfig().getInteger(CoreConfig.LEVEL_CAP);
	}

	/**
	 * Return the ExpCraft module matching the given abbreviation.
	 * 
	 * @param modAbbr
	 *            Module abbreviation.
	 * @return The matching {@link ExpCraftModule}, or, <code>null</code>.
	 */
	public ExpCraftModule getModuleByAbbr(final String modAbbr) {
		if (modAbbr == null) {
			return null;
		} else {
			return modules.get(modAbbr.toLowerCase());
		}
	}

	/**
	 * Get a list containing all active ExpCraft modules.
	 * 
	 * @return Collection of loaded modules.
	 */
	public Collection<ExpCraftModule> getModules() {
		return modules.values();
	}

	/**
	 * Get the permission manager implementation.
	 * 
	 * @return If the bukkit plugin "PermissionsBukkit" is loaded, an
	 *         appropriate manager is return, or, a manager that simply
	 *         distinguishes between ops and no-ops.
	 */
	public AbstractPermissionManager getPermissions() {
		return permission;
	}

	/**
	 * Get the persistence manager implementation.
	 * 
	 * @return Depending on the config, a database or flat-file manager is
	 *         returned.
	 */
	public AbstractPersistenceManager getPersistence() {
		return persistence;
	}

	/**
	 * Load the configuration of the core and merge it with the default
	 * configuration before writing it back to disk. Once loaded the config
	 * stays in memory until the {@link #onDisable()} event is fired.
	 */
	private void loadConfig() {
		// use the same mechanism for config as modules do
		// therefore, create a fake ModuleInfo for the core
		ModuleInfo core = new ModuleInfo("Core", null, null);
		core.setLoader(getClassLoader());
		config = new ConfigProvider(this, core);
		// load config
		config.getConfig();
		// ensure that the config is persisted
		config.saveConfig();
	}

	/**
	 * Scan the specified locations (plugins folder and the folder specified in
	 * the core config) for ExpCraft modules. After this method either the
	 * {@link #modules} list is populated with ExpCraft modules, or, the
	 * {@link #onDisable()} event on the bukkit plugin is fired to shut down
	 * ExpCraft.
	 */
	private void loadModules() {
		List<ModuleInfo> jars = new LinkedList<ModuleInfo>();

		// scan plugins folder
		if (config.getConfig().getBoolean(CoreConfig.MODS_IN_PLUGIN_FOLDER)) {
			jars.addAll(new JarScanner().scanFolder(getFile().getParentFile()));
		}

		// scan stated folder
		File folder = new File(getFile().getParentFile(), config.getConfig()
				.getProperty(CoreConfig.MOD_FOLDER));
		if (folder.exists() && folder.isDirectory()) {
			jars.addAll(new JarScanner().scanFolder(folder));
		}

		// no modules present
		if (jars.size() == 0) {
			LOG.info("No modules present. Disabling ExpCraft.");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

		// load modules
		modules.clear();
		for (ModuleInfo info : jars) {
			ExpCraftModule module = new ModuleLoader(getClassLoader())
					.attachModule(info);
			if (module != null) {
				modules.put(info.getAbbr().toLowerCase(), module);
			}
		}
	}

	/**
	 * Handle in-game and console commands. Delegate them to the modules, if
	 * necessary.
	 */
	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd,
			final String commandLabel, final String[] args) {
		if ("level".equalsIgnoreCase(commandLabel) || //
				"lvl".equalsIgnoreCase(commandLabel)) {

			if (args != null && args.length > 0) {
				// process player commands
				this.cmd.onCommand(sender, args);
			} else {
				// show info about plugin
				this.cmd.onCommand(sender, "info");
			}

			return true;
		} else {
			return false;
		}
	}

	/** Bukkit wants ExpCraft to shut down. */
	@Override
	public void onDisable() {
		// shutdown modules
		if (modules.size() > 0) {
			for (ExpCraftModule module : modules.values()) {
				try {
					module.disable();
				} catch (Exception e) {
					// give other module a chance to shutdown
					// when one fails.
				}
			}
			modules.clear();
		}

		// shutdown core components
		permission = null;
		if (persistence != null) {
			persistence.flush();
			persistence = null;
		}
		config = null;
	}

	/** Bukkit wants ExpCraft to initialize. */
	@Override
	public void onEnable() {
		loadConfig();

		loadModules();
		if (modules.size() == 0) {
			LOG.info("No modules loaded. Disabling ExpCraft.");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

		// init permissions
		setPermission();

		// init persistence
		setPersistence();

		// init modules
		StringBuffer buf = new StringBuffer();
		for (ExpCraftModule module : modules.values()) {
			try {
				module.setCore(this);
				module.enable();
				buf.append(module.getInfo().getName());
				buf.append(",");
			} catch (Exception e) {
				LOG.warn("Error staring " + module.getInfo().getName(), e);
			}
		}
		buf.deleteCharAt(buf.length() - 1);
		LOG.info("Modules: " + buf.toString());
	}

	/**
	 * Set and initialise the {@link ExpCraft#permission} implementation
	 * according to the state of the bukkit server.
	 */
	private void setPermission() {
		Plugin permBukkit = getServer().getPluginManager().getPlugin(
				"PermissionsBukkit");
		Plugin permEx = getServer().getPluginManager().getPlugin(
				"PermissionsEx");
		if (permBukkit != null) {
			permission = new PermissionsBukkitManager();
			LOG.info("Using PermissionsBukkit.");
		} else if (permEx != null) {
			permission = new PermissionsExManager();
			LOG.info("Using PermissionsEx.");
		} else {
			LOG.info("No Permissions found enabling all levels.");
			permission = new NoPermissionManager();
		}

		StringBuffer buf = new StringBuffer();
		for (World world : getServer().getWorlds()) {
			buf.append(world.getName());
			buf.append(",");
		}
		if (buf.length() > 0) {
			buf.deleteCharAt(buf.length() - 1);
		}
		String worldStr = config.getConfig().getProperty(CoreConfig.WORLDS,
				buf.toString());
		permission.setWorlds(worldStr);
	}

	/**
	 * Set an initialise the {@link #persistence} implementation according to
	 * the ExpCraft core config.
	 */
	private void setPersistence() {
		// crate persistence manager
		String storageType = config.getConfig().getString("Database");
		if ("bukkit".equalsIgnoreCase(storageType)) {
			// databse through bukkit
			try {
				// test for ExpCraftTable
				getDatabase().createQuery(Experience.class).findRowCount();
			} catch (PersistenceException e) {
				// create table
				installDDL();
			}
			// create DAO
			persistence = new PersistenceDatabaseBukkit();

		} else {
			// persist to flat file, also fallback = "FlatFile"
			persistence = new PersistenceFlatFile();
		}
		persistence.setCore(this);
		persistence.setConstant(config.getConfig()
				.getInteger("Levels.Constant"));
		persistence.setMaxLevel(config.getConfig()
				.getInteger("Levels.LevelCap"));
	}
}
