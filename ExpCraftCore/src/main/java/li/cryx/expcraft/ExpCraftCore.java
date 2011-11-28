package li.cryx.expcraft;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import li.cryx.expcraft.cmd.CommandManager;
import li.cryx.expcraft.module.ExpCraftConfigLocation;
import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.perm.AbstractPermissionManager;
import li.cryx.expcraft.perm.NoPermissionManager;
import li.cryx.expcraft.perm.PermissionsPermissionManager;
import li.cryx.expcraft.persist.AbstractPersistenceManager;
import li.cryx.expcraft.persist.PersistenceDatabase;
import li.cryx.expcraft.persist.PersistenceFlatFile;
import li.cryx.expcraft.util.Chat;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import com.nijikokun.bukkit.Permissions.Permissions;

public class ExpCraftCore extends ExpCraftConfigLocation {

	private static final Logger LOG = Logger.getLogger("ExpCraftCore");

	private AbstractPersistenceManager persistence;

	private AbstractPermissionManager permission;

	private final CommandManager cmd;

	private final Set<ExpCraftModule> modules = new HashSet<ExpCraftModule>();

	public ExpCraftCore() {
		cmd = new CommandManager(this);
	}

	public Set<ExpCraftModule> getModules() {
		return modules;
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
		Chat.setHighlightColor(config.getString("Colors.ColourOne"));
		Chat.setSpecialColor(config.getString("Colors.ColourTwo"));
		Chat.setGoodColor(config.getString("Colors.ColourGood"));
		Chat.setBadColor(config.getString("Colors.ColourBad"));

		// delegate config to managers
		setPersistence(config);
		setPermission(config);
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd,
			final String commandLabel, final String[] args) {
		if (sender instanceof Player && commandLabel.equalsIgnoreCase("level")
				|| commandLabel.equalsIgnoreCase("lvl")) {

			if (args != null && args.length > 0) {
				// process player commands
				this.cmd.onCommand((Player) sender, cmd, args);
			} else {
				// TODO cryxli: show info about plugin
			}

			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onDisable() {
		if (persistence != null) {
			persistence.flush();
		}
		modules.clear();

		// TODO Auto-generated method stub

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
				modules.add(module);
				module.setPersistence(persistence);
				module.setPermission(permission);
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

		Plugin test = getServer().getPluginManager().getPlugin("Permissions");
		if (test != null) {
			permission = new PermissionsPermissionManager(
					((Permissions) test).getHandler());
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
			persistence = new PersistenceDatabase();
		} else {
			// fallback = "FlatFile"
			persistence = new PersistenceFlatFile();
		}
		persistence.setCore(this);
		persistence.setConstant(config.getInt("Levels.Constant"));
		persistence.setMaxLevel(config.getInt("Levels.LevelCap"));
	}

}
