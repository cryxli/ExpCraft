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
package li.cryx.expcraft.scavenger;

import java.util.UUID;

import li.cryx.expcraft.AbstractPluginTest;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Tests the block listeners.
 * 
 * @author cryxli
 */
public class ScavengerBlockListenerTest extends AbstractPluginTest<Scavenger> {

	private ScavengerBlockListener listener;

	@Override
	protected Class<Scavenger> getClazz() {
		return Scavenger.class;
	}

	private Player getMockedPlayer() {
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("Player");
		Mockito.when(player.getUniqueId()).thenReturn(
				new UUID(0, "Player".hashCode()));
		Mockito.when(player.getName()).thenReturn("Player");
		return player;
	}

	private void playerDigs(final Material material, final Environment worldType) {
		hasModule = true;
		World world = Mockito.mock(World.class);
		Mockito.when(world.getEnvironment()).thenReturn(worldType);
		Player player = getMockedPlayer();
		Mockito.when(player.getWorld()).thenReturn(world);
		Block block = Mockito.mock(Block.class);
		Mockito.when(block.getWorld()).thenReturn(world);
		Mockito.when(block.getType()).thenReturn(material);
		BlockBreakEvent event = new BlockBreakEvent(block, player);

		listener.onBlockBreak(event);

		Mockito.verify(player).getWorld();
		Mockito.verify(world).getEnvironment();
	}

	/** Test 3 */
	@Test
	public void playerDigsDirt() {
		playerDigs(Material.DIRT, Environment.NORMAL);
	}

	/** Test 4 */
	@Test
	public void playerDigsSoulsand() {
		playerDigs(Material.SOUL_SAND, Environment.NETHER);
	}

	/** Test 2 */
	@Test
	public void playerDigsStone() {
		hasModule = true;
		Player player = getMockedPlayer();
		Block block = Mockito.mock(Block.class);
		Mockito.when(block.getType()).thenReturn(Material.STONE);
		BlockBreakEvent event = new BlockBreakEvent(block, player);

		listener.onBlockBreak(event);

		Mockito.verify(player, Mockito.never()).getName();
	}

	/** Test 1 */
	@Test
	public void playerDoesNotHaveModule() {
		Block block = Mockito.mock(Block.class);
		BlockBreakEvent event = new BlockBreakEvent(block, null);
		listener.onBlockBreak(event);
		Mockito.verify(block, Mockito.never()).getType();
	}

	@Before
	public void prepareTestSpecific() {
		listener = new ScavengerBlockListener(plugin);
	}

}
