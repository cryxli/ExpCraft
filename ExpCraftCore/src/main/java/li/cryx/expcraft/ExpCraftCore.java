package li.cryx.expcraft;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import li.cryx.expcraft.cmd.CommandManager;
import li.cryx.expcraft.module.ExpCraftConfigLocation;
import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.perm.AbstractPermissionManager;
import li.cryx.expcraft.perm.NoPermissionManager;
import li.cryx.expcraft.perm.PermissionsPermissionManager;
import li.cryx.expcraft.perm.PlayerPermissionManager;
import li.cryx.expcraft.persist.AbstractPersistenceManager;
import li.cryx.expcraft.persist.PersistenceDatabase;
import li.cryx.expcraft.persist.PersistenceFlatFile;
import li.cryx.expcraft.util.Chat;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import com.nijikokun.bukkit.Permissions.Permissions;

public class ExpCraftCore extends ExpCraftConfigLocation {

	private static final Logger LOG = Logger.getLogger("ExpCraftCore");

	private AbstractPersistenceManager persistence;

	private AbstractPermissionManager permission;

	private final CommandManager cmd;

	/** List of active {@link ExpCraftModule}s. */
	private final Map<String, ExpCraftModule> modules = new TreeMap<String, ExpCraftModule>();

	public ExpCraftCore() {
		cmd = new CommandManager(this);
	}

	/**
	 * Get the module identified by its abbreviation.
	 * 
	 * @param abbr
	 *            Short identifier of a module
	 * @return The instance of the module, or, <code>null</code>, if no module
	 *         exists for the given abbreviation.
	 */
	public ExpCraftModule getModuleByAbbr(final String abbr) {
		return modules.get(abbr.toLowerCase());
	}

	public Collection<ExpCraftModule> getModules() {
		return modules.values();
	}

	@Override
	public String getName() {
		return "Core";
	}

	public AbstractPermissionManager getPermissions() {
		return permission;
	}

	public AbstractPersistenceManager getPersistence() {
		return persistence;
	}

	private void loadConfig() {
		// load config
		FileConfiguration config = getConfig();

		// delegate chat colors
		Chat.setHighlightColor(config.getString("Colors.ColorOne"));
		Chat.setSpecialColor(config.getString("Colors.ColorTwo"));
		Chat.setGoodColor(config.getString("Colors.ColorGood"));
		Chat.setBadColor(config.getString("Colors.ColorBad"));

		// delegate config to managers
		setPersistence(config);
		setPermission(config);
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
		// fotget everything
		if (persistence != null) {
			persistence.flush();
			persistence = null;
		}
		modules.clear();
		permission = null;

		LOG.info("[EC] " + getDescription().getFullName() + " disabled");
	}

	@Override
	public void onEnable() {
		loadConfig();
		saveConfig();

		// scan for ExpCraft modules
		modules.clear();
		PluginManager pm = getServer().getPluginManager();
		StringBuffer buf = new StringBuffer();
		for (Plugin plugin : pm.getPlugins()) {
			if (plugin instanceof ExpCraftModule) {
				// set references on modules
				ExpCraftModule module = (ExpCraftModule) plugin;
				module.setPersistence(persistence);
				module.setPermission(permission);
				module.setLevelCap(getConfig().getInt("Levels.LevelCap"));

				modules.put(module.getAbbr().toLowerCase(), module);
				buf.append(module.getName());
				buf.append(",");
			}
		}

		LOG.info("[EC] " + getDescription().getFullName() + " enabled");
		if (buf.length() > 0) {
			buf.deleteCharAt(buf.length() - 1);
			LOG.info("[EC] Modules: " + buf.toString());
		} else {
			LOG.warning("[EC] No modules loaded!");
		}
	}

	private void setPermission(final FileConfiguration config) {
		// TODO cryxli: load proper PermissionManager
		// TODO cryxli: which one to support?

		Plugin perm = getServer().getPluginManager().getPlugin("Permissions");
		Plugin permBukkit = getServer().getPluginManager().getPlugin(
				"PermissionsBukkit");
		if (permBukkit != null) {
			permission = new PlayerPermissionManager();
			LOG.info("[EC] Using PermissionsBukkit.");
		} else if (perm != null) {
			permission = new PermissionsPermissionManager(
					((Permissions) perm).getHandler());
			LOG.info("[EC] Using Permissions.");
		} else {
			LOG.info("[EC] No Permissions found enabling all levels.");
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
		String worldStr = config.getString("Worlds", buf.toString());
		permission.setWorlds(worldStr);
	}

	private void setPersistence(final FileConfiguration config) {
		// crate persistence manager
		String storageType = config.getString("Database");
		if ("mysql".equalsIgnoreCase(storageType)
				|| "sqlite".equalsIgnoreCase(storageType)) {
			// persist to database
			PersistenceDatabase db = new PersistenceDatabase();
			persistence = db;
			db.setDatabase(storageType);
			db.setDbUrl(config.getString("DbConnection.URL"));
			db.setDbUser(config.getString("DbConnection.User"));
			db.setDbPassword(config.getString("DbConnection.Password"));

		} else {
			// persist to flat file, also fallback = "FlatFile"
			persistence = new PersistenceFlatFile();
		}
		persistence.setCore(this);
		persistence.setConstant(config.getInt("Levels.Constant"));
		persistence.setMaxLevel(config.getInt("Levels.LevelCap"));
	}
}
