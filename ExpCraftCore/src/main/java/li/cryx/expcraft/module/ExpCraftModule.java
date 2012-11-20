package li.cryx.expcraft.module;

import li.cryx.expcraft.perm.AbstractPermissionManager;
import li.cryx.expcraft.persist.AbstractPersistenceManager;

import org.bukkit.entity.Player;

/**
 * This abstract class defines the behaviour of ExpCraft modules.
 * 
 * @author cryxli
 */
public abstract class ExpCraftModule extends ExpCraftConfigLocation {

	private AbstractPersistenceManager persistence;

	private AbstractPermissionManager permission;

	private int levelCap;

	/**
	 * A player want information about the module.
	 * 
	 * @param sender
	 *            Current player
	 * @param page
	 *            For multi page settings, which page to display. Will always
	 *            default to <code>1</code>. Usually, you can display 9 lines at
	 *            once.
	 */
	abstract public void displayInfo(Player sender, int page);

	/** Get the short reference (1 or 2 characters) of the module. */
	abstract public String getAbbr();

	/**
	 * Get the current level cap injected by the core.
	 * 
	 * @return Max level reachable
	 */
	public int getLevelCap() {
		return levelCap;
	}

	/** Get the full name of the module. */
	@Override
	abstract public String getModuleName();

	/**
	 * Get the current permission manager injected by the core.
	 * 
	 * @return Current permission manager
	 */
	public AbstractPermissionManager getPermission() {
		return permission;
	}

	/**
	 * Get the current persistence manager injected by the core.
	 * 
	 * @return Curent persistence manager
	 */
	public AbstractPersistenceManager getPersistence() {
		return persistence;
	}

	@Override
	public void onDisable() {
		unloadConfig();
		onModuleDisable();
	}

	/** Delegates <code>JavaPlugin.onDiable()</code> event. */
	public abstract void onModuleDisable();

	public void setLevelCap(final int levelCap) {
		this.levelCap = levelCap;
	}

	public void setPermission(final AbstractPermissionManager permission) {
		this.permission = permission;
	}

	public void setPersistence(final AbstractPersistenceManager persistence) {
		this.persistence = persistence;
	}
}
