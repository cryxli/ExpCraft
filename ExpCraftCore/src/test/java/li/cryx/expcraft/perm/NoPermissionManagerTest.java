package li.cryx.expcraft.perm;

import org.bukkit.entity.Player;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test for the fallback permission manager.
 * 
 * @author cryxli
 */
public class NoPermissionManagerTest {

	private static final NoPermissionManager perm = new NoPermissionManager();

	/**
	 * Test for
	 * {@link AbstractPermissionManager#hasAdminCommand(Player, String) 
	 */
	@Test
	public void hasAdminCommand() {
		// player
		Player player = Mockito.mock(Player.class);
		Assert.assertFalse(perm.hasAdminCommand(player, null));

		// op
		Mockito.when(player.isOp()).thenReturn(true);
		Assert.assertTrue(perm.hasAdminCommand(player, null));
	}

	/**
	 * Test for
	 * {@link AbstractPermissionManager#hasLevel(li.cryx.expcraft.module.ExpCraftModule, Player) 
	 */
	@Test
	public void hasLevel() {
		// player
		Player player = Mockito.mock(Player.class);
		Assert.assertTrue(perm.hasLevel(null, player));

		// op
		Mockito.when(player.isOp()).thenReturn(true);
		Assert.assertTrue(perm.hasLevel(null, player));
	}
}
