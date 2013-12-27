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
import li.cryx.expcraft.ExpCraftCoreStub;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Verify {@link MiningBlockListener}'s reaction on <code>BlockBreakEvent</code>
 * events.
 * 
 * @author cryxli
 */
public class MiningBlockListenerTest extends AbstractPluginTest<Mining> {

	private MiningBlockListener listener;

	@Override
	protected Class<Mining> getClazz() {
		return Mining.class;
	}

	/** Test 3 */
	@Test
	public void playerCannotMineBlock() {
		hasModule = true;
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getItemInHand()).thenReturn(
				new ItemStack(Material.WOOD_PICKAXE));
		Block block = Mockito.mock(Block.class);
		Mockito.when(block.getType()).thenReturn(Material.OBSIDIAN);
		BlockBreakEvent event = new BlockBreakEvent(block, player);

		listener.onBlockBreak(event);

		Assert.assertTrue(event.isCancelled());
		Mockito.verify(plugin).warnBlockMine(player, 35);
	}

	/** Test 1 */
	@Test
	public void playerDoesNotHaveModule() {
		Player player = Mockito.mock(Player.class);
		Block block = Mockito.mock(Block.class);
		BlockBreakEvent event = new BlockBreakEvent(block, player);

		listener.onBlockBreak(event);
		// will throw a NullPointerException when anything goes wrong.
	}

	/** Test 2 */
	@Test
	public void playerDoesNotMeetToolLevel() {
		hasModule = true;
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getItemInHand()).thenReturn(
				new ItemStack(Material.DIAMOND_PICKAXE));
		Block block = Mockito.mock(Block.class);
		BlockBreakEvent event = new BlockBreakEvent(block, player);

		listener.onBlockBreak(event);

		Assert.assertTrue(event.isCancelled());
		Mockito.verify(plugin).warnToolUse(player, 30);
	}

	/** Test 5 */
	@Test
	public void playerMinesBlocks() {
		testMining(Material.STONE, "UseLevel.Stone", 5);
		testMining(Material.COBBLESTONE, "UseLevel.Cobble", 5);
		testMining(Material.REDSTONE_ORE, "UseLevel.Redstone", 20);
		testMining(Material.GLOWING_REDSTONE_ORE, "UseLevel.Redstone", 20);
		testMining(Material.GOLD_ORE, "UseLevel.GoldOre", 30);
		testMining(Material.IRON_ORE, "UseLevel.IronOre", 20);
		testMining(Material.COAL_ORE, "UseLevel.CoalOre", 10);
		testMining(Material.LAPIS_ORE, "UseLevel.LapisOre", 100);
		testMining(Material.MOSSY_COBBLESTONE, "UseLevel.MossStone", 10);
		testMining(Material.OBSIDIAN, "UseLevel.Obsidian", 200);
		testMining(Material.DIAMOND_ORE, "UseLevel.DiamondOre", 100);
		testMining(Material.NETHERRACK, "UseLevel.Netherrack", 3);
		testMining(Material.NETHER_BRICK, "UseLevel.NetherBrick", 5);
		testMining(Material.SANDSTONE, "UseLevel.SandStone", 3);
		testMining(Material.SMOOTH_BRICK, "UseLevel.StoneBrick", 3);
		// MC 1.5
		testMining(Material.QUARTZ_BLOCK, "UseLevel.Quartz", 1);
		testMining(Material.QUARTZ_ORE, "UseLevel.QuartzOre", 5);
		testMining(Material.REDSTONE_BLOCK, "UseLevel.RedstoneBlock", 1);
	}

	/** Test 4 */
	@Test
	public void playerMinesLeaves() {
		hasModule = true;
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getItemInHand()).thenReturn(
				new ItemStack(Material.WOOD_PICKAXE));
		Block block = Mockito.mock(Block.class);
		Mockito.when(block.getType()).thenReturn(Material.LEAVES);
		BlockBreakEvent event = new BlockBreakEvent(block, player);

		listener.onBlockBreak(event);

		Assert.assertFalse(event.isCancelled());
		Assert.assertEquals(0, pers.getExp(plugin, player), 0);
	}

	@Before
	public void prepareTestSpecific() {
		// for an eventual CommandManager calls
		Mockito.when(server.getOnlinePlayers()).thenReturn(new Player[] {});
		ExpCraftCoreStub core = new ExpCraftCoreStub(server,
				new PluginDescriptionFile("ExpCraft", "0", ""));
		pers.setCore(core);

		listener = new MiningBlockListener(plugin);
	}

	private void testMining(final Material material, final String useLevel,
			final double exp) {
		hasModule = true;
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("Player");
		Mockito.when(player.getItemInHand()).thenReturn(
				new ItemStack(Material.WOOD_PICKAXE));
		Block block = Mockito.mock(Block.class);
		Mockito.when(block.getType()).thenReturn(material);
		BlockBreakEvent event = new BlockBreakEvent(block, player);

		int level = config.getInteger(useLevel);
		config.setInteger(useLevel, 50);
		listener.onBlockBreak(event);
		Assert.assertTrue(event.isCancelled());
		Mockito.verify(plugin).warnBlockMine(player, 50);

		config.setInteger(useLevel, 0);
		event.setCancelled(false);
		listener.onBlockBreak(event);
		config.setInteger(useLevel, level);

		Assert.assertFalse(event.isCancelled());
		Assert.assertEquals(exp, pers.getExp(plugin, player), 0);
	}
}
