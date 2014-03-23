package li.cryx.expcraft;

import java.io.File;
import java.util.Collection;
import java.util.logging.Logger;

import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.perm.AbstractPermissionManager;
import li.cryx.expcraft.persist.AbstractPersistenceManager;

import org.bukkit.Server;

import com.avaje.ebean.EbeanServer;

public interface IExpCraft {

	EbeanServer getDatabase();

	File getDataFolder();

	Logger getLogger();

	ExpCraftModule getModuleByAbbr(final String modAbbr);

	Collection<ExpCraftModule> getModules();

	AbstractPermissionManager getPermissions();

	AbstractPersistenceManager getPersistence();

	Server getServer();

}
