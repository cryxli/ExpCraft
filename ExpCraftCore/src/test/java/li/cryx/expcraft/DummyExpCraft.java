package li.cryx.expcraft;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.perm.AbstractPermissionManager;
import li.cryx.expcraft.perm.NoPermissionManager;
import li.cryx.expcraft.persist.AbstractPersistenceManager;
import li.cryx.expcraft.persist.InMemoryPersistentManager;

import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;

public class DummyExpCraft extends ExpCraft {

	private ExpCraftModule testModule;

	private final AbstractPersistenceManager pers = new InMemoryPersistentManager();

	private final AbstractPermissionManager perm = new NoPermissionManager();

	public DummyExpCraft(final Server server, final PluginDescriptionFile pdf) {
		super();
		File folder = new File("target/plugins");
		initialize(null, //
				server, //
				pdf, //
				new File(folder, "TestPlugin"), //
				new File(folder, "some.jar"), //
				getClass().getClassLoader());
	}

	@Override
	public ExpCraftModule getModuleByAbbr(final String modAbbr) {
		if ("t".equals(modAbbr)) {
			return testModule;
		} else {
			return null;
		}
	}

	@Override
	public Collection<ExpCraftModule> getModules() {
		Set<ExpCraftModule> set = new HashSet<ExpCraftModule>();
		set.add(testModule);
		return set;
	}

	@Override
	public AbstractPermissionManager getPermissions() {
		return perm;
	}

	@Override
	public AbstractPersistenceManager getPersistence() {
		return pers;
	}

	public void setTestModule(final ExpCraftModule testModule) {
		this.testModule = testModule;
	}
}
