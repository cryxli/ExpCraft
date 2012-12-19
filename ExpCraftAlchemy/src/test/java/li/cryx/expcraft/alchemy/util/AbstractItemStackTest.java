package li.cryx.expcraft.alchemy.util;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.inventory.ItemFactory;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * This abstract test class is needed since Bukkit recommended 1.4.5-R1.0,
 * because the handling of <code>ItemStack</code>s changed significantly.
 * 
 * @author cryxli
 */
public class AbstractItemStackTest {

	/**
	 * Guard to ensure that <code>Bukkit</code> is only initialised once every
	 * test run.
	 */
	private static boolean bukkitInitialised = false;

	/**
	 * Prepare static references in <code>Bukkit</code> and <code>Server</code>.
	 */
	@BeforeClass
	public static void prepareBukkit() {
		if (bukkitInitialised) {
			// because Bukkit.setServer() must only be called once
			return;
		}
		bukkitInitialised = true;

		// create a ItemFactory.class mock
		ItemFactory itemFactory = Mockito.mock(ItemFactory.class);
		Mockito.when(itemFactory.equals(null, null)).thenReturn(true);

		// create a Server.class mock
		Server server = Mockito.mock(Server.class);
		Mockito.when(server.getItemFactory()).thenReturn(itemFactory);
		Mockito.when(server.getLogger())
				.thenReturn(Logger.getAnonymousLogger());

		// the Bukkit.class requires a reference to the Server.class
		// implementation
		Bukkit.setServer(server);
	}

	@Ignore
	@Test
	public void noItemStackTest() {
	}

}
