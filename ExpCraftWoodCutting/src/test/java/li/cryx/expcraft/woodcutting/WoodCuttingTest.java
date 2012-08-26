package li.cryx.expcraft.woodcutting;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class WoodCuttingTest {

	private static final WoodCutting module = new WoodCutting();

	@Test
	public void calculateDrop() {
		Assert.assertNull(testCalcDrop(Material.STONE, Material.STONE));
		Assert.assertNull(testCalcDrop(Material.STONE, Material.LOG));

		Assert.assertNull(testCalcDrop(Material.WOOD_AXE, Material.LOG));
		testCalcDrop(Material.STONE_AXE, Material.LOG, 1);
		testCalcDrop(Material.GOLD_AXE, Material.LOG, 1);
		testCalcDrop(Material.IRON_AXE, Material.LOG, 2);
		testCalcDrop(Material.DIAMOND_AXE, Material.LOG, 2);
		Assert.assertNull(testCalcDrop(Material.WOOD_AXE, Material.WOOD));
		testCalcDrop(Material.STONE_AXE, Material.WOOD, 1);
		testCalcDrop(Material.GOLD_AXE, Material.WOOD, 1);
		testCalcDrop(Material.IRON_AXE, Material.WOOD, 2);
		testCalcDrop(Material.DIAMOND_AXE, Material.WOOD, 2);
	}

	private ItemStack testCalcDrop(final Material itemInHand,
			final Material block) {
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getItemInHand()).thenReturn(
				new ItemStack(itemInHand));

		Block b = Mockito.mock(Block.class);
		Mockito.when(b.getType()).thenReturn(block);

		return module.calculateDrop(b, player);
	}

	private void testCalcDrop(final Material itemInHand, final Material block,
			final int amount) {
		ItemStack stack = testCalcDrop(itemInHand, block);
		Assert.assertEquals(block, stack.getType());
		Assert.assertEquals(amount, stack.getAmount());
	}

}
