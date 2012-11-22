package li.cryx.expcraft.digging;

import junit.framework.Assert;
import li.cryx.expcraft.AbstractPluginTest;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class DiggingBlockListenerTest extends AbstractPluginTest<Digging> {

	private DiggingBlockListener listener;

	@Override
	protected Class<Digging> getClazz() {
		return Digging.class;
	}

	/** Test 4 */
	@Test
	public void playerDigsDirt() {
		hasModule = true;
		Block block = Mockito.mock(Block.class);
		Mockito.when(block.getType()).thenReturn(Material.DIRT);
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("Player");
		Mockito.when(player.getItemInHand()).thenReturn(
				new ItemStack(Material.WOOD_SPADE));
		BlockBreakEvent event = new BlockBreakEvent(block, player);

		listener.onBlockBreak(event);

		Assert.assertFalse(event.isCancelled());
		Assert.assertEquals(1, pers.getExp(plugin, player), 0);
	}

	/** Test 3 */
	@Test
	public void playerDigsStone() {
		hasModule = true;
		Block block = Mockito.mock(Block.class);
		Mockito.when(block.getType()).thenReturn(Material.STONE);
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("Player");
		Mockito.when(player.getItemInHand()).thenReturn(
				new ItemStack(Material.WOOD_SPADE));
		BlockBreakEvent event = new BlockBreakEvent(block, player);

		listener.onBlockBreak(event);

		Assert.assertFalse(event.isCancelled());
		Assert.assertEquals(0, pers.getExp(plugin, player), 0);
	}

	/** Test 1 */
	@Test
	public void playerDoesNotHaveModule() {
		Block block = Mockito.mock(Block.class);
		BlockBreakEvent event = new BlockBreakEvent(block, null);

		listener.onBlockBreak(event);
		Assert.assertFalse(event.isCancelled());
		// test will cause NullPointerException when anything is wrong
	}

	/** Test 2 */
	@Test
	public void playerDoesNotMeetToolLevel() {
		hasModule = true;
		Block block = Mockito.mock(Block.class);
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("Player");
		Mockito.when(player.getItemInHand()).thenReturn(
				new ItemStack(Material.DIAMOND_SPADE));
		BlockBreakEvent event = new BlockBreakEvent(block, player);

		listener.onBlockBreak(event);

		Assert.assertTrue(event.isCancelled());
		Assert.assertEquals(0, pers.getExp(plugin, player), 0);
	}

	@Before
	public void prepareTestSpecific() {
		listener = new DiggingBlockListener(plugin);
	}

}
