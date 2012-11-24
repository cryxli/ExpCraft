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

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Listener for block breaks related to digging.
 * 
 * @author cryxli
 */
public class DiggingBlockListener implements Listener {

	private final Digging plugin;

	private final DiggingContraints test;

	public DiggingBlockListener(final Digging plugin) {
		this.plugin = plugin;
		test = new DiggingContraints(plugin);
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockBreak(final BlockBreakEvent event) {
		if (!plugin.getPermission().worldCheck(event.getBlock().getWorld())) {
			return;
		}

		Player player = event.getPlayer();
		if (!plugin.getPermission().hasModule(plugin, player)) {
			return;
		}

		int level = plugin.getPersistence().getLevel(plugin, player);
		Material itemInHand = player.getItemInHand().getType();
		if (!test.checkTools(player, itemInHand, level)) {
			event.setCancelled(true);
			return;
		}

		Material m = event.getBlock().getType();
		test.addExperience(player, m, level);

		// since fire shovel may drop different items, only do double drops, if
		// nothing special happened
		if (!test.fireShovel(player, level, event)) {
			// double drops
			plugin.dropItem(event.getBlock(), level, player);
		}
	}
}
