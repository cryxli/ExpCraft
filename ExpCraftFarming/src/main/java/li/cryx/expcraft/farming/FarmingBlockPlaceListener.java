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

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * This class implements a block listener that traces block placement, and,
 * initiates the according farming related actions.
 * 
 * @author cryxli
 */
public class FarmingBlockPlaceListener implements Listener {

	/** Reference to parent plugin. */
	public Farming plugin;

	/** Create a new listener for the given plugin. */
	public FarmingBlockPlaceListener(final Farming instance) {
		this.plugin = instance;
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockPlace(final BlockPlaceEvent event) {
		if (!plugin.getPermission().worldCheck(event.getBlock().getWorld())) {
			// the world is not managed by the ExpCraft
			return;
		}

		Player player = event.getPlayer();
		if (!plugin.getPermission().hasModule(plugin, player)) {
			// player does not have permission to us this module
			return;
		}

		Material m = event.getBlock().getType();
		int level = plugin.getPersistence().getLevel(plugin, player);
		double gained = 0;

		switch (m) {
		case SAPLING: // planting a tree
			if (level >= plugin.getConfig().getInteger("UseLevel.Sapling")) {
				gained = plugin.getConfig().getDouble("ExpGain.Sapling");
			}
			break;
		case RED_ROSE: // planting a rose
			if (level >= plugin.getConfig().getInteger("UseLevel.RedRose")) {
				gained = plugin.getConfig().getDouble("ExpGain.RedRose");
			}
			break;
		case YELLOW_FLOWER: // planting a dandelion
			if (level >= plugin.getConfig().getInteger("UseLevel.YellowFlower")) {
				gained = plugin.getConfig().getDouble("ExpGain.YellowFlower");
			}
			break;
		case BROWN_MUSHROOM:
		case RED_MUSHROOM: // planting a mushroom
			if (level >= plugin.getConfig().getInteger("UseLevel.Mushroom")) {
				gained = plugin.getConfig().getDouble("ExpGain.Mushroom");
			}
			break;
		default:
			// placing a block not covered by this module
			return;
		}
		plugin.getPersistence().addExp(plugin, player, gained);
	}
}
