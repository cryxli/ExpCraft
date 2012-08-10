package li.cryx.expcraft.perm;

import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.perm.AbstractPermissionManager;

import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test to verify the implemented methods of the abstract class
 * {@link AbstractPermissionManager}.
 * 
 * @author cryxli
 */
public class AbstractPermissionManagerTest extends AbstractPermissionManager {

	private boolean hasLevel = false;

	@Override
	public boolean hasAdminCommand(final Player player, final String command) {
		return false;
	}

	@Override
	public boolean hasLevel(final ExpCraftModule module, final Player player) {
		return hasLevel;
	}

	/**
	 * Test for
	 * {@link AbstractPermissionManager#hasModule(ExpCraftModule, Player)}
	 */
	@Test
	public void hasModule() {
		// (1) player has the correct game mode
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getGameMode()).thenReturn(GameMode.SURVIVAL);

		// (1a) player does not have the required level
		hasLevel = false;
		Assert.assertFalse(hasModule(null, player));

		// (1b) player has the required level
		hasLevel = true;
		Assert.assertTrue(hasModule(null, player));

		// (2) player does not have the correct game mode
		player = Mockito.mock(Player.class);
		Mockito.when(player.getGameMode()).thenReturn(GameMode.CREATIVE);

		// (2a) player does not have the required level
		hasLevel = false;
		Assert.assertFalse(hasModule(null, player));

		// (2b) player has the required level
		hasLevel = true;
		Assert.assertFalse(hasModule(null, player));
	}

	/** Test for {@link AbstractPermissionManager#worldCheck(World)} */
	@Test
	public void worldCheck() {
		World world = Mockito.mock(World.class);
		Mockito.when(world.getName()).thenReturn("World");
		World heaven = Mockito.mock(World.class);
		Mockito.when(heaven.getName()).thenReturn("Heaven");

		// no worlds registered
		setWorlds("");
		Assert.assertFalse(worldCheck(world));
		Assert.assertFalse(worldCheck(heaven));

		// register a world
		setWorlds("World");
		Assert.assertTrue(worldCheck(world));
		Assert.assertFalse(worldCheck(heaven));

		// register two worlds
		setWorlds("World,Heaven");
		Assert.assertTrue(worldCheck(world));
		Assert.assertTrue(worldCheck(heaven));
	}

}
