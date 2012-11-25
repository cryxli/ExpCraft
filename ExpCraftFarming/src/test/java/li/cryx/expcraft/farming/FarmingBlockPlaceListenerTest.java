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

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Tests {@link FarmingBlockPlaceListener}.
 * 
 * @author cryxli
 */
public class FarmingBlockPlaceListenerTest extends AbstractPluginTest<Farming> {

	private FarmingBlockPlaceListener listener;

	@Override
	protected Class<Farming> getClazz() {
		return Farming.class;
	}

	private void plantMonitoredItem(final Material material,
			final String useLevel, final double exp) {
		hasModule = true;
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("player");
		Block block = getBlock(material);
		BlockPlaceEvent event = new BlockPlaceEvent(block, null, null, null,
				player, true);

		// no effect
		config.setInteger(useLevel, 50);
		listener.onBlockPlace(event);
		config.setInteger(useLevel, 0);

		Assert.assertFalse(event.isCancelled());
		Assert.assertEquals(0, pers.getExp(plugin, player), 0);

		// placed
		listener.onBlockPlace(event);
		Assert.assertFalse(event.isCancelled());
		Assert.assertEquals(exp, pers.getExp(plugin, player), 0);
	}

	private void plantUnmonitoredItem(final Material material) {
		hasModule = true;
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("player");
		Block block = getBlock(material);
		BlockPlaceEvent event = new BlockPlaceEvent(block, null, null, null,
				player, true);

		// no effect
		listener.onBlockPlace(event);

		Assert.assertFalse(event.isCancelled());
		Assert.assertEquals(0, pers.getExp(plugin, player), 0);
	}

	/** Test 1 */
	@Test
	public void playerDoesNotHaveModule() {
		Block block = getBlock(Material.STONE);
		BlockPlaceEvent event = new BlockPlaceEvent(block, null, null, null,
				null, true);

		listener.onBlockPlace(event);

		// will throw a NullPointerException if anything is wrong
		Assert.assertFalse(event.isCancelled());
	}

	/** Test 4 */
	@Test
	public void playerPlants() {
		plantMonitoredItem(Material.RED_ROSE, "UseLevel.RedRose", 0.1);
	}

	/** Test 6 */
	@Test
	public void playerPlantsBrownMushroom() {
		plantMonitoredItem(Material.BROWN_MUSHROOM, "UseLevel.Mushroom", 0.1);
	}

	/** Test 11 */
	@Test
	public void playerPlantsCocoaBeans() {
		// Completely correct: INK_SACK with data=3
		plantUnmonitoredItem(Material.INK_SACK);
	}

	/** Test 10 */
	@Test
	public void playerPlantsMelon() {
		plantUnmonitoredItem(Material.MELON_SEEDS);
	}

	/** Test 9 */
	@Test
	public void playerPlantsPumpkin() {
		plantUnmonitoredItem(Material.PUMPKIN_SEEDS);
	}

	/** Test 7 */
	@Test
	public void playerPlantsRedMushroom() {
		plantMonitoredItem(Material.RED_MUSHROOM, "UseLevel.Mushroom", 0.1);
	}

	/** Test 3 */
	@Test
	public void playerPlantsSapling() {
		plantMonitoredItem(Material.SAPLING, "UseLevel.Sapling", 0.5);
	}

	/** Test 2 */
	@Test
	public void playerPlantsStone() {
		plantUnmonitoredItem(Material.STONE);
	}

	/** Test 8 */
	@Test
	public void playerPlantsWheat() {
		plantUnmonitoredItem(Material.SEEDS);
	}

	/** Test 5 */
	@Test
	public void playerPlantsYellowFlower() {
		plantMonitoredItem(Material.YELLOW_FLOWER, "UseLevel.YellowFlower", 0.1);
	}

	@Before
	public void prepreTestSpecific() {
		listener = new FarmingBlockPlaceListener(plugin);
	}
}
