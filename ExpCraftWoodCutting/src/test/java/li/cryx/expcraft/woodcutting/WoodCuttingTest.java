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
package li.cryx.expcraft.woodcutting;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test that ensures the double drops work as expected.
 * 
 * @author cryxli
 */
public class WoodCuttingTest {

	private static final WoodCutting module = new WoodCutting();

	// test double drops depending on tool quality
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

	// prepare and test
	private ItemStack testCalcDrop(final Material itemInHand,
			final Material block) {
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getItemInHand()).thenReturn(
				new ItemStack(itemInHand));

		Block b = Mockito.mock(Block.class);
		Mockito.when(b.getType()).thenReturn(block);

		return module.calculateDrop(b, player);
	}

	// test and verification
	private void testCalcDrop(final Material itemInHand, final Material block,
			final int amount) {
		ItemStack stack = testCalcDrop(itemInHand, block);
		Assert.assertEquals(block, stack.getType());
		Assert.assertEquals(amount, stack.getAmount());
	}

}
