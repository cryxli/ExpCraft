package li.cryx.expcraft.woodcutting;

import junit.framework.Assert;
import li.cryx.expcraft.perm.AbstractPermissionManager;
import li.cryx.expcraft.persist.AbstractPersistenceManager;

import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.junit.Test;
import org.mockito.Mockito;

public class WoodCuttingBlockListenerTest extends AbstractPluginTest {

	// only one positive case
	@Test
	public void onBlockBreak() {
		// prepare
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getItemInHand()).thenReturn(null);

		AbstractPermissionManager perm = Mockito
				.mock(AbstractPermissionManager.class);
		Mockito.when(perm.worldCheck(null)).thenReturn(true);
		Mockito.when(perm.hasModule(plugin, player)).thenReturn(true);

		AbstractPersistenceManager pers = Mockito
				.mock(AbstractPersistenceManager.class);
		Mockito.when(pers.getLevel(plugin, player)).thenReturn(1);

		Mockito.when(plugin.getPermission()).thenReturn(perm);
		Mockito.when(plugin.getPersistence()).thenReturn(pers);

		// player punches a log of Oak
		// (once the first action a player performed in minecraft :-)
		Block block = getBlock(Material.LOG, TreeSpecies.GENERIC);
		BlockBreakEvent event = new BlockBreakEvent(block, player);
		new WoodCuttingBlockListener(plugin).onBlockBreak(event);
		Assert.assertFalse(event.isCancelled());
		Mockito.verify(pers).addExp(plugin, player, 1.5);
	}

}
