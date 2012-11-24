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
package li.cryx.expcraft.digging;

import junit.framework.Assert;
import li.cryx.expcraft.AbstractPluginTest;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class DiggingBlockListenerTest extends AbstractPluginTest<Digging> {

	private DiggingBlockListener listener;

	@Override
	protected Class<Digging> getClazz() {
		return Digging.class;
	}

	/** Test 4 */
	@Test
	public void playerDigsDirt() {
		hasModule = true;
		Block block = Mockito.mock(Block.class);
		Mockito.when(block.getType()).thenReturn(Material.DIRT);
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("Player");
		Mockito.when(player.getItemInHand()).thenReturn(
				new ItemStack(Material.WOOD_SPADE));
		BlockBreakEvent event = new BlockBreakEvent(block, player);

		listener.onBlockBreak(event);

		Assert.assertFalse(event.isCancelled());
		Assert.assertEquals(1, pers.getExp(plugin, player), 0);
	}

	/** Test 3 */
	@Test
	public void playerDigsStone() {
		hasModule = true;
		Block block = Mockito.mock(Block.class);
		Mockito.when(block.getType()).thenReturn(Material.STONE);
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("Player");
		Mockito.when(player.getItemInHand()).thenReturn(
				new ItemStack(Material.WOOD_SPADE));
		BlockBreakEvent event = new BlockBreakEvent(block, player);

		listener.onBlockBreak(event);

		Assert.assertFalse(event.isCancelled());
		Assert.assertEquals(0, pers.getExp(plugin, player), 0);
	}

	/** Test 1 */
	@Test
	public void playerDoesNotHaveModule() {
		Block block = Mockito.mock(Block.class);
		BlockBreakEvent event = new BlockBreakEvent(block, null);

		listener.onBlockBreak(event);
		Assert.assertFalse(event.isCancelled());
		// test will cause NullPointerException when anything is wrong
	}

	/** Test 2 */
	@Test
	public void playerDoesNotMeetToolLevel() {
		hasModule = true;
		Block block = Mockito.mock(Block.class);
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("Player");
		Mockito.when(player.getItemInHand()).thenReturn(
				new ItemStack(Material.DIAMOND_SPADE));
		BlockBreakEvent event = new BlockBreakEvent(block, player);

		listener.onBlockBreak(event);

		Assert.assertTrue(event.isCancelled());
		Assert.assertEquals(0, pers.getExp(plugin, player), 0);
	}

	@Before
	public void prepareTestSpecific() {
		listener = new DiggingBlockListener(plugin);
	}

}
