package li.cryx.expcraft;

import java.io.File;

import li.cryx.expcraft.cmd.CommandManager;
import li.cryx.expcraft.persist.AbstractPersistenceManager;

import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;

/**
 * Usually you will not need an instance of {@link ExpCraftCore} while running
 * your tests. However, if you test case causes a level increase the
 * {@link CommandManager} which is part of the
 * {@link AbstractPersistenceManager} will need it. If you run into an according
 * <code>NullPointerException</code>, create a stub like this:
 * 
 * <pre>
 * // create a server mock
 * Server server = Mockito.mock(Server.class);
 * // this method is called by {@link CommandManager} to broadcast messages
 * Mockito.when(server.getOnlinePlayers()).thenReturn(new Player[] {});
 * // instantiate the core stub
 * ExpCraftCoreStub core = new ExpCraftCoreStub(server, new PluginDescriptionFile(
 * 		&quot;ExpCraft&quot;, &quot;0&quot;, &quot;&quot;));
 * // set the core on the {@link AbstractPersistenceManager}
 * pers.setCore(core);
 * </pre>
 * 
 * @author cryxli
 */
public class ExpCraftCoreStub extends ExpCraft {
	public ExpCraftCoreStub(final Server server, final PluginDescriptionFile pdf) {
		super();
		File folder = new File("target/plugins");
		initialize(null, //
				server, //
				pdf, //
				new File(folder, "test"), //
				new File(folder, "some.jar"), //
				getClass().getClassLoader());
	}
}
