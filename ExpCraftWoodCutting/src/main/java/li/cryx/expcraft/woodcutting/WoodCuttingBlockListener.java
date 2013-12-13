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

import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Listener to detect breaking of wooden blocks (planks and logs) and leaves.
 * 
 * @author cryxli
 */
public class WoodCuttingBlockListener implements Listener {

	/** Reference to the ExpCraft module */
	private final WoodCutting plugin;

	private final WoodCuttingConstraints test;

	/** Create a new listener for the given module. */
	public WoodCuttingBlockListener(final WoodCutting plugin) {
		this.plugin = plugin;
		test = new WoodCuttingConstraints(plugin);
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockBreak(final BlockBreakEvent event) {
		if (!plugin.getPermission().worldCheck(event.getBlock().getWorld())) {
			// ExpCraft is not activated for the current world
			return;
		}

		Player player = event.getPlayer();
		if (!plugin.getPermission().hasModule(plugin, player)) {
			// player is not allowed to use this plugin
			return;
		}

		Material itemInHand = null;
		if (player.getItemInHand() != null) {
			itemInHand = player.getItemInHand().getType();
		}

		int level = plugin.getPersistence().getLevel(plugin, player);
		if (itemInHand != null && !test.checkTool(player, itemInHand, level)) {
			// player cannot use the item in hand
			event.setCancelled(true);
			return;
		}

		Block block = event.getBlock();
		if (!test.checkTargetBlock(player, block, level)) {
			// player cannot collect the block
			event.setCancelled(true);
			return;
		}

		double gained = 0;
		switch (block.getType()) {
		case LOG:
		case LOG_2:
			gained = test.getExp("Log", test.getTreeType(block));
			break;
		case WOOD: // wooden plank
			gained = test.getExp("Plank", test.getTreeType(block));
			break;
		case LEAVES:
			if (itemInHand == Material.SHEARS) {
				TreeSpecies type = test.getTreeType(block);
				if (test.checkLeafBreaking(player, level, type)) {
					gained = test.getExp("Leaves", type);
				} else {
					event.setCancelled(true);
					return;
				}
			}
			break;
		case FENCE:
			gained = plugin.getConfig().getDouble("ExpGain.Fence");
			break;
		default:
			break;
		}
		plugin.getPersistence().addExp(plugin, player, gained);

		// double drops
		plugin.dropItem(event.getBlock(), level, player);
	}
}
