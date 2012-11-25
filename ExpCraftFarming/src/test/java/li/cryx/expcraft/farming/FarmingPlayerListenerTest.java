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
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * A test for {@link FarmingPlayerListener}.
 * 
 * @author cryxli
 */
public class FarmingPlayerListenerTest extends AbstractPluginTest<Farming> {

	private FarmingPlayerListener listener;

	@Before
	public void beforeTest() {
		hasModule = false;
		Mockito.when(plugin.isHoe(Mockito.any(Material.class)))
				.thenCallRealMethod();

		listener = new FarmingPlayerListener(plugin);
	}

	@Override
	protected Class<Farming> getClazz() {
		return Farming.class;
	}

	/** Test 1 */
	@Test
	public void leftClick() {
		PlayerInteractEvent event = new PlayerInteractEvent(null,
				Action.LEFT_CLICK_BLOCK, null, null, null);
		listener.onPlayerInteract(event);
		// will throw a NullPointerException if anything is wrong
	}

	/** Test 3 */
	@Test
	public void playerDoesNotHaveTheModule() {
		Block block = getBlock(Material.DIRT);
		PlayerInteractEvent event = new PlayerInteractEvent(null,
				Action.RIGHT_CLICK_BLOCK, null, block, null);
		listener.onPlayerInteract(event);
		// will throw a NullPointerException if anything is wrong
	}

	/** Test 5 */
	@Test
	public void playerDoesNotHoldAHow() {
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("player");
		Mockito.when(player.getItemInHand()).thenReturn(
				new ItemStack(Material.AIR));
		Block block = getBlock(Material.DIRT);
		PlayerInteractEvent event = new PlayerInteractEvent(player,
				Action.RIGHT_CLICK_BLOCK, null, block, null);
		hasModule = true;

		listener.onPlayerInteract(event);

		Assert.assertFalse(event.isCancelled());
		Assert.assertEquals(0, pers.getExp(plugin, player), 0);
	}

	/** Test 4 */
	@Test
	public void playerDoesNotMeetToolLevel() {
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("player");
		Mockito.when(player.getItemInHand()).thenReturn(
				new ItemStack(Material.STONE_HOE));
		Block block = getBlock(Material.DIRT);
		PlayerInteractEvent event = new PlayerInteractEvent(player,
				Action.RIGHT_CLICK_BLOCK, null, block, null);
		hasModule = true;

		listener.onPlayerInteract(event);

		Assert.assertTrue(event.isCancelled());
		Assert.assertEquals(0, pers.getExp(plugin, player), 0);
	}

	/** Test 7 */
	@Test
	public void playerIsTilling() {
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("player");
		Mockito.when(player.getItemInHand()).thenReturn(
				new ItemStack(Material.WOOD_HOE));
		Block block = getBlock(Material.DIRT);
		PlayerInteractEvent event = new PlayerInteractEvent(player,
				Action.RIGHT_CLICK_BLOCK, null, block, null);
		hasModule = true;

		listener.onPlayerInteract(event);

		Assert.assertFalse(event.isCancelled());
		Assert.assertEquals(1, pers.getExp(plugin, player), 0);
	}

	/** Test 6 */
	@Test
	public void playerNotHighEnoughLevel() {
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("player");
		Mockito.when(player.getItemInHand()).thenReturn(
				new ItemStack(Material.WOOD_HOE));
		Block block = getBlock(Material.DIRT);
		PlayerInteractEvent event = new PlayerInteractEvent(player,
				Action.RIGHT_CLICK_BLOCK, null, block, null);
		hasModule = true;

		config.setInteger("UseLevel.Till", 2);
		listener.onPlayerInteract(event);
		config.setInteger("UseLevel.Till", 0);

		Assert.assertTrue(event.isCancelled());
		Assert.assertEquals(0, pers.getExp(plugin, player), 0);
		Mockito.verify(plugin).warnCutBlockLevel(player, 2);
	}

	/** Test 2 */
	@Test
	public void rightClickStone() {
		Block block = getBlock(Material.STONE);
		PlayerInteractEvent event = new PlayerInteractEvent(null,
				Action.RIGHT_CLICK_BLOCK, null, block, null);
		listener.onPlayerInteract(event);
		// will throw a NullPointerException if anything is wrong
	}
}
