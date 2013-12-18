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
import org.bukkit.TreeSpecies;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Tree;
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

		Assert.assertNull(testCalcDrop(Material.WOOD_AXE, Material.WOOD));
	}

	@Test
	public void calculateDropTreeSpeciesLogAcacia() {
		testTreeCalcDrop(Material.STONE_AXE, Material.LOG_2, 1,
				TreeSpecies.ACACIA);
		testTreeCalcDrop(Material.GOLD_AXE, Material.LOG_2, 1,
				TreeSpecies.ACACIA);
		testTreeCalcDrop(Material.IRON_AXE, Material.LOG_2, 2,
				TreeSpecies.ACACIA);
		testTreeCalcDrop(Material.DIAMOND_AXE, Material.LOG_2, 2,
				TreeSpecies.ACACIA);
	}

	@Test
	public void calculateDropTreeSpeciesLogBirch() {
		testTreeCalcDrop(Material.STONE_AXE, Material.LOG, 1, TreeSpecies.BIRCH);
		testTreeCalcDrop(Material.GOLD_AXE, Material.LOG, 1, TreeSpecies.BIRCH);
		testTreeCalcDrop(Material.IRON_AXE, Material.LOG, 2, TreeSpecies.BIRCH);
		testTreeCalcDrop(Material.DIAMOND_AXE, Material.LOG, 2,
				TreeSpecies.BIRCH);
	}

	@Test
	public void calculateDropTreeSpeciesLogDarkoak() {
		testTreeCalcDrop(Material.STONE_AXE, Material.LOG_2, 1,
				TreeSpecies.DARK_OAK);
		testTreeCalcDrop(Material.GOLD_AXE, Material.LOG_2, 1,
				TreeSpecies.DARK_OAK);
		testTreeCalcDrop(Material.IRON_AXE, Material.LOG_2, 2,
				TreeSpecies.DARK_OAK);
		testTreeCalcDrop(Material.DIAMOND_AXE, Material.LOG_2, 2,
				TreeSpecies.DARK_OAK);
	}

	@Test
	public void calculateDropTreeSpeciesLogJungle() {
		testTreeCalcDrop(Material.STONE_AXE, Material.LOG, 1,
				TreeSpecies.JUNGLE);
		testTreeCalcDrop(Material.GOLD_AXE, Material.LOG, 1, TreeSpecies.JUNGLE);
		testTreeCalcDrop(Material.IRON_AXE, Material.LOG, 2, TreeSpecies.JUNGLE);
		testTreeCalcDrop(Material.DIAMOND_AXE, Material.LOG, 2,
				TreeSpecies.JUNGLE);
	}

	@Test
	public void calculateDropTreeSpeciesLogOak() {
		testTreeCalcDrop(Material.STONE_AXE, Material.LOG, 1,
				TreeSpecies.GENERIC);
		testTreeCalcDrop(Material.GOLD_AXE, Material.LOG, 1,
				TreeSpecies.GENERIC);
		testTreeCalcDrop(Material.IRON_AXE, Material.LOG, 2,
				TreeSpecies.GENERIC);
		testTreeCalcDrop(Material.DIAMOND_AXE, Material.LOG, 2,
				TreeSpecies.GENERIC);
	}

	@Test
	public void calculateDropTreeSpeciesLogRedwood() {
		testTreeCalcDrop(Material.STONE_AXE, Material.LOG, 1,
				TreeSpecies.REDWOOD);
		testTreeCalcDrop(Material.GOLD_AXE, Material.LOG, 1,
				TreeSpecies.REDWOOD);
		testTreeCalcDrop(Material.IRON_AXE, Material.LOG, 2,
				TreeSpecies.REDWOOD);
		testTreeCalcDrop(Material.DIAMOND_AXE, Material.LOG, 2,
				TreeSpecies.REDWOOD);
	}

	@Test
	public void calculateDropTreeSpeciesWood() {
		// Wood

		testTreeCalcDrop(Material.STONE_AXE, Material.WOOD, 1,
				TreeSpecies.GENERIC);
		testTreeCalcDrop(Material.GOLD_AXE, Material.WOOD, 1,
				TreeSpecies.GENERIC);
		testTreeCalcDrop(Material.IRON_AXE, Material.WOOD, 2,
				TreeSpecies.GENERIC);
		testTreeCalcDrop(Material.DIAMOND_AXE, Material.WOOD, 2,
				TreeSpecies.GENERIC);

		testTreeCalcDrop(Material.STONE_AXE, Material.WOOD, 1,
				TreeSpecies.BIRCH);
		testTreeCalcDrop(Material.GOLD_AXE, Material.WOOD, 1, TreeSpecies.BIRCH);
		testTreeCalcDrop(Material.IRON_AXE, Material.WOOD, 2, TreeSpecies.BIRCH);
		testTreeCalcDrop(Material.DIAMOND_AXE, Material.WOOD, 2,
				TreeSpecies.BIRCH);

		testTreeCalcDrop(Material.STONE_AXE, Material.WOOD, 1,
				TreeSpecies.REDWOOD);
		testTreeCalcDrop(Material.GOLD_AXE, Material.WOOD, 1,
				TreeSpecies.REDWOOD);
		testTreeCalcDrop(Material.IRON_AXE, Material.WOOD, 2,
				TreeSpecies.REDWOOD);
		testTreeCalcDrop(Material.DIAMOND_AXE, Material.WOOD, 2,
				TreeSpecies.REDWOOD);

		testTreeCalcDrop(Material.STONE_AXE, Material.WOOD, 1,
				TreeSpecies.JUNGLE);
		testTreeCalcDrop(Material.GOLD_AXE, Material.WOOD, 1,
				TreeSpecies.JUNGLE);
		testTreeCalcDrop(Material.IRON_AXE, Material.WOOD, 2,
				TreeSpecies.JUNGLE);
		testTreeCalcDrop(Material.DIAMOND_AXE, Material.WOOD, 2,
				TreeSpecies.JUNGLE);

		testTreeCalcDrop(Material.STONE_AXE, Material.WOOD, 1,
				TreeSpecies.ACACIA);
		testTreeCalcDrop(Material.GOLD_AXE, Material.WOOD, 1,
				TreeSpecies.ACACIA);
		testTreeCalcDrop(Material.IRON_AXE, Material.WOOD, 2,
				TreeSpecies.ACACIA);
		testTreeCalcDrop(Material.DIAMOND_AXE, Material.WOOD, 2,
				TreeSpecies.ACACIA);

		testTreeCalcDrop(Material.STONE_AXE, Material.WOOD, 1,
				TreeSpecies.DARK_OAK);
		testTreeCalcDrop(Material.GOLD_AXE, Material.WOOD, 1,
				TreeSpecies.DARK_OAK);
		testTreeCalcDrop(Material.IRON_AXE, Material.WOOD, 2,
				TreeSpecies.DARK_OAK);
		testTreeCalcDrop(Material.DIAMOND_AXE, Material.WOOD, 2,
				TreeSpecies.DARK_OAK);
	}

	private Block getBlock(final Material material, final TreeSpecies type) {
		// TODO correct this once new trees are implemented correctly
		BlockState state = Mockito.mock(BlockState.class);
		if (material == Material.LOG_2) {
			Mockito.when(state.getData()).thenReturn(
					new MaterialData(material, (byte) (type.getData() - 4)));
		} else {
			Mockito.when(state.getData()).thenReturn(new Tree(type));
		}

		Block block = Mockito.mock(Block.class);
		Mockito.when(block.getType()).thenReturn(material);
		Mockito.when(block.getState()).thenReturn(state);
		Mockito.when(block.getWorld()).thenReturn(null);

		return block;
	}

	// prepare and test
	private ItemStack testCalcDrop(final Material itemInHand,
			final Material block) {
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getItemInHand()).thenReturn(
				new ItemStack(itemInHand));

		Block b = getBlock(block, TreeSpecies.GENERIC);

		return module.calculateDrop(b, player);
	}

	// test and verification
	private void testTreeCalcDrop(final Material itemInHand,
			final Material block, final int amount, final TreeSpecies species) {
		ItemStack stack = testTreeCalcDrop(itemInHand, block, species);
		// TODO correct this once new trees are implemented correctly
		TreeSpecies tree = null;
		switch (stack.getType()) {
		case WOOD:
			tree = TreeSpecies.getByData(stack.getData().getData());
			break;
		case LOG:
			tree = ((Tree) stack.getData()).getSpecies();
			break;
		case LOG_2:
			tree = TreeSpecies
					.getByData((byte) (4 + (stack.getData().getData() & 3)));
			break;
		default:
			break;
		}
		Assert.assertEquals(species, tree);
		Assert.assertEquals(block, stack.getType());
		Assert.assertEquals(amount, stack.getAmount());
	}

	// prepare and test
	private ItemStack testTreeCalcDrop(final Material itemInHand,
			final Material block, final TreeSpecies species) {
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getItemInHand()).thenReturn(
				new ItemStack(itemInHand));
		Block b = getBlock(block, species);

		return module.calculateDrop(b, player);
	}

}
