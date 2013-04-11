/*
 * Copyright (c) 2011 Urs P. Stettler, https://github.com/cryxli
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package li.cryx.expcraft.mining;

import junit.framework.Assert;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Verify double drops capabilities of {@link Mining}.
 * 
 * @author cryxli
 */
public class MiningTest {

	private static final Mining module = new Mining();

	/** Test double drops. */
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

		for (Material material : new Material[] { Material.SANDSTONE,
				Material.QUARTZ_BLOCK }) {
			Assert.assertNull(testCalcDrops(Material.WOOD_PICKAXE, material));
			testCalcDrops(Material.STONE_PICKAXE, material, material, 1);
			testCalcDrops(Material.GOLD_PICKAXE, material, material, 2);
			testCalcDrops(Material.IRON_PICKAXE, material, material, 2);
			testCalcDrops(Material.DIAMOND_PICKAXE, material, material, 3);
		}

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

		// MC 1.5
		Assert.assertNull(testCalcDrops(Material.WOOD_PICKAXE,
				Material.QUARTZ_ORE));
		testCalcDrops(Material.STONE_PICKAXE, Material.QUARTZ_ORE,
				Material.QUARTZ, 1);
		testCalcDrops(Material.GOLD_PICKAXE, Material.QUARTZ_ORE,
				Material.QUARTZ, 1);
		testCalcDrops(Material.IRON_PICKAXE, Material.QUARTZ_ORE,
				Material.QUARTZ, 2);
		testCalcDrops(Material.DIAMOND_PICKAXE, Material.QUARTZ_ORE,
				Material.QUARTZ, 2);

		for (Material itemInHand : new Material[] { Material.WOOD_PICKAXE,
				Material.STONE_PICKAXE, Material.GOLD_PICKAXE,
				Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE }) {
			Assert.assertNull(testCalcDrops(itemInHand, Material.REDSTONE_BLOCK));
		}
	}

	/** prepare and test */
	private ItemStack testCalcDrops(final Material itemInHand,
			final Material block) {
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getItemInHand()).thenReturn(
				new ItemStack(itemInHand));

		Block b = Mockito.mock(Block.class);
		Mockito.when(b.getType()).thenReturn(block);

		return module.calculateDrop(b, player);
	}

	/** test and verify */
	private void testCalcDrops(final Material itemInHand, final Material block,
			final Material drop, final int amount) {
		ItemStack stack = testCalcDrops(itemInHand, block);
		Assert.assertEquals(drop, stack.getType());
		Assert.assertEquals(amount, stack.getAmount());
	}
}
