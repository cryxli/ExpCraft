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
package li.cryx.expcraft.farming;

import junit.framework.Assert;
import li.cryx.expcraft.AbstractPluginTest;

import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.CocoaPlant;
import org.bukkit.material.CocoaPlant.CocoaPlantSize;
import org.bukkit.material.Crops;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Tests {@link FarmingBlockBreakListener}
 * 
 * @author cryxli
 */
public class FarmingBlockBreakListenerTest extends AbstractPluginTest<Farming> {

	private FarmingBlockBreakListener listener;

	@Override
	protected Class<Farming> getClazz() {
		return Farming.class;
	}

	private void harvest(final Block block, final String level, final double exp) {
		hasModule = true;
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("player");
		BlockBreakEvent event = new BlockBreakEvent(block, player);

		// no effect
		config.setInteger(level, 5);
		listener.onBlockBreak(event);
		config.setInteger(level, 0);
		Assert.assertFalse(event.isCancelled());
		Assert.assertEquals(0, pers.getExp(plugin, player), 0);

		// harvesting
		listener.onBlockBreak(event);
		Assert.assertFalse(event.isCancelled());
		Assert.assertEquals(exp, pers.getExp(plugin, player), 0);
	}

	/** Test 1 */
	@Test
	public void playerDoesNotHaveModule() {
		Block block = Mockito.mock(Block.class);
		BlockBreakEvent event = new BlockBreakEvent(block, null);
		listener.onBlockBreak(event);
		// this test will throw an NullPointerException when anything is wrong
		Assert.assertFalse(event.isCancelled());
	}

	/** Test 2 */
	@Test
	public void playerDoesNotMeetToolLevel() {
		hasModule = true;
		Block block = Mockito.mock(Block.class);
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("player");
		Mockito.when(player.getItemInHand()).thenReturn(
				new ItemStack(Material.DIAMOND_HOE));
		BlockBreakEvent event = new BlockBreakEvent(block, player);

		listener.onBlockBreak(event);

		Assert.assertTrue(event.isCancelled());
		Assert.assertEquals(0, pers.getExp(plugin, player), 0);
	}

	/** Test 6 */
	@Test
	public void playerHarvestsCactus() {
		Block block = Mockito.mock(Block.class);
		Mockito.when(block.getType()).thenReturn(Material.CACTUS);
		harvest(block, "UseLevel.Cacti", 3);
	}

	/** Test 11 */
	@Test
	public void playerHarvestsCarrot() {
		Block carrot = Mockito.mock(Block.class);
		Mockito.when(carrot.getType()).thenReturn(Material.CARROT);
		Mockito.when(carrot.getData()).thenReturn(CarrotState.RIPE.getData());

		harvest(carrot, "UseLevel.Carrot", 5);
	}

	/** Test 9 */
	@Test
	public void playerHarvestsCocoaBean() {
		CocoaPlant plant = new CocoaPlant(CocoaPlantSize.LARGE);

		BlockState state = Mockito.mock(BlockState.class);
		Mockito.when(state.getData()).thenReturn(plant);

		Block bean = Mockito.mock(Block.class);
		Mockito.when(bean.getType()).thenReturn(Material.COCOA);
		Mockito.when(bean.getState()).thenReturn(state);

		harvest(bean, "UseLevel.CocoaBean", 2);
	}

	/** Test 4 */
	@Test
	public void playerHarvestsCrops() {
		Crops plant = new Crops(CropState.RIPE);

		BlockState state = Mockito.mock(BlockState.class);
		Mockito.when(state.getData()).thenReturn(plant);

		Block crop = Mockito.mock(Block.class);
		Mockito.when(crop.getType()).thenReturn(Material.CROPS);
		Mockito.when(crop.getState()).thenReturn(state);

		harvest(crop, "UseLevel.Harvest", 5);
	}

	/** Test 7 */
	@Test
	public void playerHarvestsMelon() {
		Block plantedMelon = FarmingConstraintsTest.getBlock(
				Material.MELON_BLOCK, Material.MELON_STEM, Material.AIR,
				Material.AIR, Material.AIR);

		harvest(plantedMelon, "UseLevel.Melon", 2);
	}

	/** Test 5 */
	@Test
	public void playerHarvestsNetherWarts() {
		Block wart = Mockito.mock(Block.class);
		Mockito.when(wart.getType()).thenReturn(Material.NETHER_WARTS);
		Mockito.when(wart.getData()).thenReturn(NetherWartState.RIPE.getData());

		harvest(wart, "UseLevel.NetherWart", 5);
	}

	/** Test 10 */
	@Test
	public void playerHarvestsPotato() {
		Block potato = Mockito.mock(Block.class);
		Mockito.when(potato.getType()).thenReturn(Material.POTATO);
		Mockito.when(potato.getData()).thenReturn(PotatoState.RIPE.getData());

		harvest(potato, "UseLevel.Potato", 5);
	}

	/** Test 8 */
	@Test
	public void playerHarvestsPumpkin() {
		Block plantedPumpkin = FarmingConstraintsTest.getBlock(
				Material.PUMPKIN, Material.PUMPKIN_STEM, Material.AIR,
				Material.AIR, Material.AIR);

		harvest(plantedPumpkin, "UseLevel.Pumpkin", 2);
	}

	/** Test 10 */
	@Test
	public void playerHarvestsStone() {
		hasModule = true;
		Block block = Mockito.mock(Block.class);
		Mockito.when(block.getType()).thenReturn(Material.STONE);
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("player");
		BlockBreakEvent event = new BlockBreakEvent(block, player);

		// no effect
		listener.onBlockBreak(event);
		Assert.assertFalse(event.isCancelled());
		Assert.assertEquals(0, pers.getExp(plugin, player), 0);
	}

	/** Test 3 */
	@Test
	public void playerHarvestsSugarCane() {
		Block block = Mockito.mock(Block.class);
		Mockito.when(block.getType()).thenReturn(Material.SUGAR_CANE_BLOCK);
		harvest(block, "UseLevel.SugarCane", 2);
	}

	@Before
	public void prepreTestSpecific() {
		listener = new FarmingBlockBreakListener(plugin);
	}

}
