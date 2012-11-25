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

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * This class implements a block listener that traces block destruction, and,
 * initiates the according savenging related actions.
 * 
 * @author cryxli
 */
public class ScavengerBlockListener implements Listener {

	/** A random generator to determine drops. */
	private static final Random randomGenerator = new Random();

	/** Reference to parent plugin. */
	private final Scavenger plugin;

	/** Externalised logic */
	private final ScavengerConstraints test;

	/** Create a new listener for the given plugin. */
	public ScavengerBlockListener(final Scavenger plugin) {
		this.plugin = plugin;
		test = new ScavengerConstraints(plugin);
	}

	// A block has been destroyed
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockBreak(final BlockBreakEvent event) {
		if (!plugin.getPermission().worldCheck(event.getBlock().getWorld())) {
			// the world is not managed by the ExpCraft
			return;
		}

		Player player = event.getPlayer();
		if (!plugin.getPermission().hasModule(plugin, player)) {
			// player does not have permission to us this module
			return;
		}

		Block block = event.getBlock();
		Material m = block.getType();
		if (!test.isDiggable(m)) {
			// block is not one for scavenging
			return;
		}

		int level = plugin.getPersistence().getLevel(plugin, player);
		int random = randomGenerator.nextInt(test.getRange(level));

		switch (player.getWorld().getEnvironment()) {
		case NETHER:
			test.dropNether(player, level, block, random);
			break;
		case NORMAL:
			test.dropNormal(player, level, block, random);
			break;
		case THE_END:
		default:
			// no drops
			break;
		}
	}
}
