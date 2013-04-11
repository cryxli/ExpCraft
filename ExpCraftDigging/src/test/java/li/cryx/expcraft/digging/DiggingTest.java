package li.cryx.expcraft.digging;

import junit.framework.Assert;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.junit.Test;
import org.mockito.Mockito;

public class DiggingTest extends Digging {

	private static final Digging module = new Digging();

	@Test
	public void calculateDrop() {
		testDrops(Material.GRASS, Material.DIRT, null, 1, 1, 1, 1);
		testDrops(Material.GRAVEL, Material.GRAVEL, null, 1, 1, 2, 2);
		testDrops(Material.SOUL_SAND, Material.SOUL_SAND, null, 1, 1, 2, 2);
		testDrops(Material.DIRT, Material.DIRT, null, 1, 1, 2, 2);
		testDrops(Material.SAND, Material.SAND, null, 1, 1, 2, 2);
		testDrops(Material.CLAY, Material.CLAY_BALL, null, 4, 4, 8, 8);
		testDrops(Material.SNOW_BLOCK, Material.SNOW_BALL, null, 4, 4, 8, 8);

		// MC 1.5
		for (int data = 1; data <= 8; data++) {
			System.out.println(data - 1);
			testDrops(Material.SNOW, (byte) (data - 1), Material.SNOW_BALL,
					null, data % 4, data % 4, data % 4, data % 4);
		}
	}

	/** prepare and test */
	private ItemStack testCalcDrops(final Material itemInHand,
			final Material block, final Byte data) {
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getItemInHand()).thenReturn(
				new ItemStack(itemInHand));

		Block b = Mockito.mock(Block.class);
		Mockito.when(b.getType()).thenReturn(block);
		Mockito.when(b.getData()).thenReturn(data == null ? -1 : data);

		return module.calculateDrop(b, player);
	}

	/** test and verify */
	private void testCalcDrops(final Material itemInHand, final Material block,
			final Byte data, final Material drop, final int amount) {
		ItemStack stack = testCalcDrops(itemInHand, block, data);
		Assert.assertEquals(drop, stack.getType());
		Assert.assertEquals(amount, stack.getAmount());
	}

	private void testDrops(final Material source, final Byte data,
			final Material drop, final Integer... amounts) {
		final Material[] tools = { Material.WOOD_SPADE, Material.STONE_SPADE,
				Material.GOLD_SPADE, Material.IRON_SPADE,
				Material.DIAMOND_SPADE };
		for (int i = 0; i < tools.length; i++) {
			if (amounts[i] == null) {
				Assert.assertNull(testCalcDrops(tools[i], source, data));
			} else {
				testCalcDrops(tools[i], source, data, drop, amounts[i]);
			}
		}
	}

	/**
	 * Dig the source block with all types of shovels and compare it to the drop
	 * block and the different amounts.
	 * 
	 * @param source
	 *            Material of the source block.
	 * @param drop
	 *            Material of the dropped block/item.
	 * @param amounts
	 *            Amounts for wooden, (cobble)stone, golden, iron and diamond
	 *            tool.
	 */
	private void testDrops(final Material source, final Material drop,
			final Integer... amounts) {
		testDrops(source, null, drop, amounts);
	}

}
