package li.cryx.expcraft;

import java.util.Collection;
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
 * TODO documentation
 * 
 * @author cryxli
 */
public class ExpCraft extends JavaPlugin {

	private static final Logger LOG = LoggerFactory.getLogger(ExpCraft.class);

	private final Map<String, ExpCraftModule> modules = new TreeMap<String, ExpCraftModule>();

	private AbstractPermissionManager permission;

	private AbstractPersistenceManager persistence;

	private ConfigProvider config;

	private final CommandManager cmd;

	public ExpCraft() {
		cmd = new CommandManager(this);
	}

	public int getLevelCap() {
		return config.getConfig().getInteger(CoreConfig.LEVEL_CAP);
	}

	public ExpCraftModule getModuleByAbbr(final String modAbbr) {
		if (modAbbr == null) {
			return null;
		} else {
			return modules.get(modAbbr.toLowerCase());
		}
	}

	public Collection<ExpCraftModule> getModules() {
		return modules.values();
	}

	public AbstractPermissionManager getPermissions() {
		return permission;
	}

	public AbstractPersistenceManager getPersistence() {
		return persistence;
	}

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

	private void loadModules() {
		List<ModuleInfo> jars = new JarScanner().scanFolder(getFile()
				.getParentFile());
		if (jars.size() == 0) {
			LOG.info("No modules present. Disabling ExpCraft.");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

		modules.clear();
		for (ModuleInfo info : jars) {
			ExpCraftModule module = new ModuleLoader(getClassLoader())
					.attachModule(info);
			if (module != null) {
				modules.put(info.getAbbr().toLowerCase(), module);
			}
		}
	}

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

	@Override
	public void onDisable() {
		// shutdown modules
		if (modules.size() > 0) {
			for (ExpCraftModule module : modules.values()) {
				module.disable();
			}
			modules.clear();
		}

		// shutdown core components
		permission = null;
		persistence.flush();
		persistence = null;
		config = null;
	}

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

	private void setPermission() {
		Plugin permBukkit = getServer().getPluginManager().getPlugin(
				"PermissionsBukkit");
		if (permBukkit != null) {
			permission = new PermissionsBukkitManager();
			LOG.info("Using PermissionsBukkit.");
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
