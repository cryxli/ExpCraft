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
import li.cryx.expcraft.AbstractPluginTest;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Tests the public methods of {@link MiningConstraints}.
 * 
 * @author cryxli
 */
public class MiningConstraintsTest extends AbstractPluginTest<Mining> {

	/** Instance to test */
	private MiningConstraints test;

	/** Test for {@link MiningConstraints#checkPickaxe(Player, Material, int)} */
	@Test
	public void checkPickaxe() {
		Player player = Mockito.mock(Player.class);
		Assert.assertTrue(test.checkPickaxe(player, Material.AIR, 1));
		Mockito.verify(plugin, Mockito.never()).warnBlockMine(
				Mockito.eq(player), Mockito.anyInt());

		testTool(Material.WOOD_PICKAXE, 1, -1);
		testTool(Material.STONE_PICKAXE, 6, 1);
		testTool(Material.IRON_PICKAXE, 11, 1);
		testTool(Material.GOLD_PICKAXE, 21, 1);
		testTool(Material.DIAMOND_PICKAXE, 31, 1);
	}

	/**
	 * Test for
	 * {@link MiningConstraints#firePickaxe(Player, int, BlockBreakEvent)}
	 */
	@Test
	public void firePickaxe() {
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getItemInHand()).thenReturn(
				new ItemStack(Material.AIR));
		BlockBreakEvent event = new BlockBreakEvent(null, player);
		Assert.assertFalse(test.firePickaxe(player, 1, event));
		Assert.assertFalse(test.firePickaxe(player, 80, event));

		player = Mockito.mock(Player.class);
		Mockito.when(player.getItemInHand()).thenReturn(
				new ItemStack(Material.GOLD_PICKAXE));
		Block block = Mockito.mock(Block.class);
		Mockito.when(block.getType()).thenReturn(Material.DIAMOND_ORE);
		event = new BlockBreakEvent(block, player);
		Assert.assertFalse(test.firePickaxe(player, 80, event));

		testFirePick(Material.STONE);
		testFirePick(Material.IRON_ORE);
		testFirePick(Material.GOLD_ORE);
	}

	@Override
	protected Class<Mining> getClazz() {
		return Mining.class;
	}

	/** Test for {@link MiningConstraints#isMinable(Player, Material, int)} */
	@Test
	public void isMinable() {
		Player player = Mockito.mock(Player.class);
		Assert.assertTrue(test.isMinable(player, Material.AIR, 1));
		Mockito.verify(plugin, Mockito.never()).warnBlockMine(
				Mockito.eq(player), Mockito.anyInt());

		testBlock(Material.STONE, 1, -1);
		testBlock(Material.COBBLESTONE, 1, -1);
		testBlock(Material.MOSSY_COBBLESTONE, 1, -1);
		testBlock(Material.COAL_ORE, 6, 1);
		testBlock(Material.IRON_ORE, 6, 1);
		testBlock(Material.SANDSTONE, 1, -1);
		testBlock(Material.GOLD_ORE, 21, 1);
		testBlock(Material.LAPIS_ORE, 21, 1);
		testBlock(Material.REDSTONE_ORE, 11, 1);
		testBlock(Material.GLOWING_REDSTONE_ORE, 11, 1);
		testBlock(Material.DIAMOND_ORE, 26, 1);
		testBlock(Material.OBSIDIAN, 36, 1);
		testBlock(Material.NETHERRACK, 1, -1);
		testBlock(Material.SMOOTH_BRICK, 6, 1);
		// MC 1.5
		testBlock(Material.QUARTZ_ORE, 15, 14);
		testBlock(Material.QUARTZ_BLOCK, 15, 14);
		testBlock(Material.REDSTONE_BLOCK, 10, 9);
	}

	@Before
	public void prepareTestSpecific() {
		test = new MiningConstraints(plugin);
	}

	private void testBlock(final Material material, final int okLevel,
			final int nokLevel) {
		Player player = Mockito.mock(Player.class);

		Assert.assertTrue(test.isMinable(player, material, okLevel));
		Mockito.verify(plugin, Mockito.never()).warnBlockMine(
				Mockito.eq(player), Mockito.anyInt());

		Assert.assertFalse(test.isMinable(player, material, nokLevel));
		Mockito.verify(plugin).warnBlockMine(Mockito.eq(player),
				Mockito.anyInt());
	}

	private void testFirePick(final Material material) {
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getItemInHand()).thenReturn(
				new ItemStack(Material.GOLD_PICKAXE));
		World world = Mockito.mock(World.class);
		Block block = Mockito.mock(Block.class);
		Mockito.when(block.getWorld()).thenReturn(world);
		Mockito.when(block.getType()).thenReturn(material);
		BlockBreakEvent event = new BlockBreakEvent(block, player);
		Assert.assertTrue(test.firePickaxe(player, 80, event));
		Mockito.verify(block).setType(Material.AIR);
		Mockito.verify(world).dropItem(Mockito.any(Location.class),
				Mockito.any(ItemStack.class));
	}

	private void testTool(final Material material, final int okLevel,
			final int nokLevel) {
		Player player = Mockito.mock(Player.class);

		Assert.assertTrue(test.checkPickaxe(player, material, okLevel));
		Mockito.verify(plugin, Mockito.never()).warnToolUse(Mockito.eq(player),
				Mockito.anyInt());

		Assert.assertFalse(test.checkPickaxe(player, material, nokLevel));
		Mockito.verify(plugin)
				.warnToolUse(Mockito.eq(player), Mockito.anyInt());
	}

}
