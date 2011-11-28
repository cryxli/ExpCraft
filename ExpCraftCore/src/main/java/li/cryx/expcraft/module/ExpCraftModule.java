package li.cryx.expcraft.module;

import li.cryx.expcraft.perm.AbstractPermissionManager;
import li.cryx.expcraft.persist.AbstractPersistenceManager;

/**
 * This abstract class defines the behaviour of ExpCraft modules.
 * 
 * @author cryxli
 */
public abstract class ExpCraftModule extends ExpCraftConfigLocation {

	private AbstractPersistenceManager persistence;

	private AbstractPermissionManager permission;

	/** Get the short reference (1 or 2 characters) to the module) */
	abstract public String getAbbr();

	/** Get the full name of the module. */
	@Override
	abstract public String getName();

	public AbstractPermissionManager getPermission() {
		return permission;
	}

	public AbstractPersistenceManager getPersistence() {
		return persistence;
	}

	public void setPermission(final AbstractPermissionManager permission) {
		this.permission = permission;
	}

	public void setPersistence(final AbstractPersistenceManager persistence) {
		this.persistence = persistence;
	}

}
