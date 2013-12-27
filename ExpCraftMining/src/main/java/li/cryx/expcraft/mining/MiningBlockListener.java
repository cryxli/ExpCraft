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
package li.cryx.expcraft.mining;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * This class intersects block break events.
 * 
 * @author cryxli
 */
public class MiningBlockListener implements Listener {

	private final Mining plugin;

	private final MiningConstraints test;

	public MiningBlockListener(final Mining plugin) {
		this.plugin = plugin;
		test = new MiningConstraints(plugin);
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

		Material itemInHand = player.getItemInHand().getType();
		int level = plugin.getPersistence().getLevel(plugin, player);
		if (!test.checkPickaxe(player, itemInHand, level)) {
			event.setCancelled(true);
			return;
		}

		Material m = event.getBlock().getType();
		if (!test.isMinable(player, m, level)) {
			event.setCancelled(true);
			return;
		}

		double expGain;
		switch (m) {
		default:
			expGain = 0;
			break;
		case STONE:
			expGain = plugin.getConfig().getDouble("ExpGain.Stone");
			break;
		case COBBLESTONE:
			expGain = plugin.getConfig().getDouble("ExpGain.Cobble");
			break;
		case REDSTONE_ORE:
		case GLOWING_REDSTONE_ORE:
			expGain = plugin.getConfig().getDouble("ExpGain.Redstone");
			break;
		case REDSTONE_BLOCK:
			expGain = plugin.getConfig().getDouble("ExpGain.RedstoneBlock");
			break;
		case GOLD_ORE:
			expGain = plugin.getConfig().getDouble("ExpGain.GoldOre");
			break;
		case IRON_ORE:
			expGain = plugin.getConfig().getDouble("ExpGain.IronOre");
			break;
		case COAL_ORE:
			expGain = plugin.getConfig().getDouble("ExpGain.CoalOre");
			break;
		case LAPIS_ORE:
			expGain = plugin.getConfig().getDouble("ExpGain.LapisOre");
			break;
		case MOSSY_COBBLESTONE:
			expGain = plugin.getConfig().getDouble("ExpGain.MossStone");
			break;
		case OBSIDIAN:
			expGain = plugin.getConfig().getDouble("ExpGain.Obsidian");
			break;
		case DIAMOND_ORE:
			expGain = plugin.getConfig().getDouble("ExpGain.DiamondOre");
			break;
		case NETHERRACK:
			expGain = plugin.getConfig().getDouble("ExpGain.Netherrack");
			break;
		case NETHER_BRICK:
			expGain = plugin.getConfig().getDouble("ExpGain.NetherBrick");
			break;
		case SANDSTONE:
			expGain = plugin.getConfig().getDouble("ExpGain.SandStone");
			break;
		case SMOOTH_BRICK:
			expGain = plugin.getConfig().getDouble("ExpGain.StoneBrick");
			break;
		case QUARTZ_BLOCK:
			expGain = plugin.getConfig().getDouble("ExpGain.Quartz");
			break;
		case QUARTZ_ORE:
			expGain = plugin.getConfig().getDouble("ExpGain.QuartzOre");
			break;
		}
		plugin.getPersistence().addExp(plugin, player, expGain);

		// fire pickaxe can produce different drops, therefore do only call
		// double drops when nothing special happened
		if (!test.firePickaxe(player, level, event)) {
			// double drops
			plugin.dropItem(event.getBlock(), level, player);
		}
	}

}
