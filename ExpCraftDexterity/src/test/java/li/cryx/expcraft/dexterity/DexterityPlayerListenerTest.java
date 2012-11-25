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
package li.cryx.expcraft.dexterity;

import junit.framework.Assert;
import li.cryx.expcraft.AbstractPluginTest;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Ensure {@link DexterityPlayerListener}'s functionality.
 * 
 * @author cryxli
 */
public class DexterityPlayerListenerTest extends AbstractPluginTest<Dexterity> {

	private DexterityPlayerListener listener;

	@Override
	protected Class<Dexterity> getClazz() {
		return Dexterity.class;
	}

	/** Test 1 */
	@Test
	public void playerDoesNotHavePlugin() {
		Player player = Mockito.mock(Player.class);
		PlayerMoveEvent event = new PlayerMoveEvent(player, null, null);
		listener.onPlayerMove(event);
		// test will cause a NullPointerException, if anything goes wrong

		Assert.assertEquals(0, plugin.getPersistence().getExp(plugin, player),
				0);
	}

	/** Test 2 */
	@Test
	public void playerFalls() {
		hasModule = true;
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("Player");
		Location from = new Location(null, 0, 65, 0);
		Location to = new Location(null, 0, 64, 0);
		PlayerMoveEvent event = new PlayerMoveEvent(player, from, to);
		listener.onPlayerMove(event);

		Assert.assertEquals(0, plugin.getPersistence().getExp(plugin, player),
				0);
	}

	/** Test 4 */
	@Test
	public void playerJumps() {
		hasModule = true;
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("Player");
		Block block = Mockito.mock(Block.class);
		Mockito.when(block.getType()).thenReturn(Material.AIR);
		World world = Mockito.mock(World.class);
		Mockito.when(world.getBlockAt(Mockito.any(Location.class))).thenReturn(
				block);
		Location from = new Location(world, 0, 64, 0);
		Location to = new Location(world, 0, 65, 0);
		PlayerMoveEvent event = new PlayerMoveEvent(player, from, to);
		listener.onPlayerMove(event);

		Assert.assertEquals(1, plugin.getPersistence().getExp(plugin, player),
				0);
	}

	/** Test 3 */
	@Test
	public void playerSwimsUp() {
		hasModule = true;
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("Player");
		Block block = Mockito.mock(Block.class);
		Mockito.when(block.getType()).thenReturn(Material.WATER);
		World world = Mockito.mock(World.class);
		Mockito.when(world.getBlockAt(Mockito.any(Location.class))).thenReturn(
				block);
		Location from = new Location(world, 0, 64, 0);
		Location to = new Location(world, 0, 65, 0);
		PlayerMoveEvent event = new PlayerMoveEvent(player, from, to);
		listener.onPlayerMove(event);

		Assert.assertEquals(0, plugin.getPersistence().getExp(plugin, player),
				0);
	}

	@Before
	public void prepareTestSpecific() {
		listener = new DexterityPlayerListener(plugin);
	}

}
