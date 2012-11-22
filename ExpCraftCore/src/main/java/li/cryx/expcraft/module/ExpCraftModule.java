package li.cryx.expcraft.module;

import li.cryx.expcraft.ExpCraft;
import li.cryx.expcraft.loader.ModuleInfo;
import li.cryx.expcraft.perm.AbstractPermissionManager;
import li.cryx.expcraft.persist.AbstractPersistenceManager;
import li.cryx.expcraft.util.TypedProperties;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 * This abstract class defines the behaviour of ExpCraft modules.
 * 
 * @author cryxli
 */
public abstract class ExpCraftModule {

	/** Reference to ExpCraft core. */
	private ExpCraft core;

	/** The description of this module. */
	private ModuleInfo info;

	/** Abstraction to handle config files. */
	private ConfigProvider config;

	/**
	 * Event fired by the core to disable the module. Do not override this
	 * method. Override {@link #onEnable()}.
	 */
	public final void disable() {
		onDisable();
		// forget everything
		core = null;
		config = null;
	}

	public abstract void displayInfo(final Player sender);

	/**
	 * Event fired by the core to enable the module. Do not override this
	 * method. Override {@link #onDisable()}.
	 */
	public final void enable() {
		onEnable();
	}

	/**
	 * Get the persisted configuration of the module enriched by the default
	 * values.
	 * 
	 * @return The configuration properties.
	 */
	public TypedProperties getConfig() {
		return config.getConfig();
	}

	/**
	 * Return the reference to the ExpCraft core.
	 * 
	 * @return The ExpCraft core.
	 */
	protected ExpCraft getCore() {
		return core;
	}

	/** Get the module description. */
	public ModuleInfo getInfo() {
		return info;
	}

	/**
	 * Get the level cap of Expcraft.
	 * 
	 * @return Level cap, usually 0 < cap <= 100.
	 */
	public int getLevelCap() {
		return core.getLevelCap();
	}

	/**
	 * Get the permission manager to check player permissions.
	 * 
	 * @return An implementation of the permission manager.
	 */
	public AbstractPermissionManager getPermission() {
		return core.getPermissions();
	}

	/**
	 * Get the persistence manager to store level progress.
	 * 
	 * @return An implementation of the persistence manager.
	 */
	public AbstractPersistenceManager getPersistence() {
		return core.getPersistence();
	}

	/**
	 * Get the bukkit server.
	 * 
	 * @return Reference to the server.
	 */
	public Server getServer() {
		return core.getServer();
	}

	/**
	 * Get notified that the module should start monitoring player events.
	 */
	public abstract void onDisable();

	/**
	 * Get notified that the module should no longer monitor player events.
	 */
	public abstract void onEnable();

	/**
	 * Register a bukkit event listener with the server.
	 * 
	 * @param listener
	 *            The listener to register.
	 */
	protected void registerEvents(final Listener listener) {
		core.getServer().getPluginManager().registerEvents(listener, core);
	}

	/** Save the configuration to disk. */
	public void saveConfig() {
		config.saveConfig();
	}

	/**
	 * Link the module to the ExpCraft core.
	 * 
	 * @param core
	 *            Reference to core.
	 */
	public void setCore(final ExpCraft core) {
		this.core = core;
		if (info != null) {
			config = new ConfigProvider(core, info);
		}
	}

	/**
	 * Set the module description.
	 * 
	 * @param info
	 *            The module description.
	 */
	public void setInfo(final ModuleInfo info) {
		this.info = info;
		if (core != null) {
			config = new ConfigProvider(core, info);
		}
	}

}
