package li.cryx.expcraft.mining;

import junit.framework.Assert;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.junit.Test;
import org.mockito.Mockito;

public class MiningTest {

	private static final Mining module = new Mining();

	@Test
	public void calculateDrop() {
		Assert.assertNull(testCalcDrops(Material.WOOD_PICKAXE, Material.STONE));
		testCalcDrops(Material.STONE_PICKAXE, Material.STONE,
				Material.COBBLESTONE, 1);
		testCalcDrops(Material.GOLD_PICKAXE, Material.STONE,
				Material.COBBLESTONE, 1);
		testCalcDrops(Material.IRON_PICKAXE, Material.STONE,
				Material.COBBLESTONE, 2);
		testCalcDrops(Material.DIAMOND_PICKAXE, Material.STONE,
				Material.COBBLESTONE, 2);

		for (Material material : new Material[] { Material.COBBLESTONE,
				Material.IRON_ORE, Material.MOSSY_COBBLESTONE,
				Material.NETHERRACK, Material.NETHER_BRICK }) {
			Assert.assertNull(testCalcDrops(Material.WOOD_PICKAXE, material));
			testCalcDrops(Material.STONE_PICKAXE, material, material, 1);
			testCalcDrops(Material.GOLD_PICKAXE, material, material, 1);
			testCalcDrops(Material.IRON_PICKAXE, material, material, 2);
			testCalcDrops(Material.DIAMOND_PICKAXE, material, material, 2);
		}

		for (Material material : new Material[] { Material.REDSTONE_ORE,
				Material.GLOWING_REDSTONE_ORE }) {
			Assert.assertNull(testCalcDrops(Material.WOOD_PICKAXE, material));
			testCalcDrops(Material.STONE_PICKAXE, material, Material.REDSTONE,
					0);
			testCalcDrops(Material.GOLD_PICKAXE, material, Material.REDSTONE, 5);
			testCalcDrops(Material.IRON_PICKAXE, material, Material.REDSTONE, 5);
			testCalcDrops(Material.DIAMOND_PICKAXE, material,
					Material.REDSTONE, 8);
		}

		Assert.assertNull(testCalcDrops(Material.WOOD_PICKAXE,
				Material.LAPIS_ORE));
		testCalcDrops(Material.STONE_PICKAXE, Material.LAPIS_ORE,
				Material.INK_SACK, 0);
		testCalcDrops(Material.GOLD_PICKAXE, Material.LAPIS_ORE,
				Material.INK_SACK, 5);
		testCalcDrops(Material.IRON_PICKAXE, Material.LAPIS_ORE,
				Material.INK_SACK, 5);
		testCalcDrops(Material.DIAMOND_PICKAXE, Material.LAPIS_ORE,
				Material.INK_SACK, 8);

		Assert.assertNull(testCalcDrops(Material.WOOD_PICKAXE,
				Material.GLOWSTONE));
		testCalcDrops(Material.STONE_PICKAXE, Material.GLOWSTONE,
				Material.GLOWSTONE_DUST, 2);
		testCalcDrops(Material.GOLD_PICKAXE, Material.GLOWSTONE,
				Material.GLOWSTONE_DUST, 2);
		testCalcDrops(Material.IRON_PICKAXE, Material.GLOWSTONE,
				Material.GLOWSTONE_DUST, 4);
		testCalcDrops(Material.DIAMOND_PICKAXE, Material.GLOWSTONE,
				Material.GLOWSTONE_DUST, 6);

		Assert.assertNull(testCalcDrops(Material.WOOD_PICKAXE,
				Material.SANDSTONE));
		testCalcDrops(Material.STONE_PICKAXE, Material.SANDSTONE,
				Material.SANDSTONE, 1);
		testCalcDrops(Material.GOLD_PICKAXE, Material.SANDSTONE,
				Material.SANDSTONE, 2);
		testCalcDrops(Material.IRON_PICKAXE, Material.SANDSTONE,
				Material.SANDSTONE, 2);
		testCalcDrops(Material.DIAMOND_PICKAXE, Material.SANDSTONE,
				Material.SANDSTONE, 3);

		Assert.assertNull(testCalcDrops(Material.WOOD_PICKAXE,
				Material.COAL_ORE));
		testCalcDrops(Material.STONE_PICKAXE, Material.COAL_ORE, Material.COAL,
				1);
		testCalcDrops(Material.GOLD_PICKAXE, Material.COAL_ORE, Material.COAL,
				2);
		testCalcDrops(Material.IRON_PICKAXE, Material.COAL_ORE, Material.COAL,
				2);
		testCalcDrops(Material.DIAMOND_PICKAXE, Material.COAL_ORE,
				Material.COAL, 3);
	}

	private ItemStack testCalcDrops(final Material itemInHand,
			final Material block) {
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getItemInHand()).thenReturn(
				new ItemStack(itemInHand));

		Block b = Mockito.mock(Block.class);
		Mockito.when(b.getType()).thenReturn(block);

		return module.calculateDrop(b, player);
	}

	private void testCalcDrops(final Material itemInHand, final Material block,
			final Material drop, final int amount) {
		ItemStack stack = testCalcDrops(itemInHand, block);
		Assert.assertEquals(drop, stack.getType());
		Assert.assertEquals(amount, stack.getAmount());
	}
}
