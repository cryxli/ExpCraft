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

import junit.framework.Assert;
import li.cryx.expcraft.AbstractPluginTest;
import li.cryx.expcraft.perm.AbstractPermissionManager;
import li.cryx.expcraft.persist.AbstractPersistenceManager;

import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Verify main use case of {@link WoodCuttingBlockListener}.
 * 
 * @author cryxli
 */
public class WoodCuttingBlockListenerTest extends
		AbstractPluginTest<WoodCutting> {

	@Override
	protected Class<WoodCutting> getClazz() {
		return WoodCutting.class;
	}

	// only one positive case
	@Test
	public void onBlockBreak() {
		// prepare
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getItemInHand()).thenReturn(null);

		AbstractPermissionManager perm = Mockito
				.mock(AbstractPermissionManager.class);
		Mockito.when(perm.worldCheck(null)).thenReturn(true);
		Mockito.when(perm.hasModule(plugin, player)).thenReturn(true);

		AbstractPersistenceManager pers = Mockito
				.mock(AbstractPersistenceManager.class);
		Mockito.when(pers.getLevel(plugin, player)).thenReturn(1);

		Mockito.when(plugin.getPermission()).thenReturn(perm);
		Mockito.when(plugin.getPersistence()).thenReturn(pers);

		// player punches a log of Oak
		// (once the first action a player performed in minecraft :-)
		Block block = getBlock(Material.LOG, TreeSpecies.GENERIC);
		BlockBreakEvent event = new BlockBreakEvent(block, player);
		new WoodCuttingBlockListener(plugin).onBlockBreak(event);
		Assert.assertFalse(event.isCancelled());
		Mockito.verify(pers).addExp(plugin, player, 1.5);
	}

}
