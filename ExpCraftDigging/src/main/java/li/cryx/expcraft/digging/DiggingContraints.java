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
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * This class contains decision logic used by the digging module.
 * 
 * @author cryxli
 */
public class DiggingContraints {

	/** Reference to digging module. */
	private final Digging plugin;

	public DiggingContraints(final Digging plugin) {
		this.plugin = plugin;
	}

	/**
	 * Add experience for digging related blocks to player's account.
	 * 
	 * @param player
	 *            The player
	 * @param material
	 *            The block
	 * @param level
	 *            Player's level in digging
	 */
	public void addExperience(final Player player, final Block block,
			final int level) {
		final Material material = block.getType();
		double exp = 0;
		switch (material) {
		case DIRT:
			if (level < plugin.getConfig().getInteger(DiggingConst.LEVEL.DIRT)) {
				plugin.warnCutBlockLevel(player,
						plugin.getConfig().getInteger(DiggingConst.LEVEL.DIRT));
			} else {
				exp = plugin.getConfig().getDouble(DiggingConst.EXP.DIRT);
			}
			break;
		case GRASS:
			if (level < plugin.getConfig().getInteger(DiggingConst.LEVEL.GRASS)) {
				plugin.warnCutBlockLevel(player,
						plugin.getConfig().getInteger(DiggingConst.LEVEL.GRASS));
			} else {
				exp = plugin.getConfig().getDouble(DiggingConst.EXP.GRASS);
			}
			break;
		case SAND:
			if (level < plugin.getConfig().getInteger(DiggingConst.LEVEL.SAND)) {
				plugin.warnCutBlockLevel(player,
						plugin.getConfig().getInteger(DiggingConst.LEVEL.SAND));
			} else {
				exp = plugin.getConfig().getDouble(DiggingConst.EXP.SAND);
			}
			break;
		case CLAY:
			if (level < plugin.getConfig().getInteger(DiggingConst.LEVEL.CLAY)) {
				plugin.warnCutBlockLevel(player,
						plugin.getConfig().getInteger(DiggingConst.LEVEL.CLAY));
			} else {
				exp = plugin.getConfig().getDouble(DiggingConst.EXP.CLAY);
			}
			break;
		case SOUL_SAND:
			if (level < plugin.getConfig().getInteger(
					DiggingConst.LEVEL.SOULSAND)) {
				plugin.warnCutBlockLevel(
						player,
						plugin.getConfig().getInteger(
								DiggingConst.LEVEL.SOULSAND));
			} else {
				exp = plugin.getConfig().getDouble(DiggingConst.EXP.SOULSAND);
			}
			break;
		case GRAVEL:
			if (level < plugin.getConfig()
					.getInteger(DiggingConst.LEVEL.GRAVEL)) {
				plugin.warnCutBlockLevel(player,
						plugin.getConfig()
								.getInteger(DiggingConst.LEVEL.GRAVEL));
			} else {
				exp = plugin.getConfig().getDouble(DiggingConst.EXP.GRAVEL);
			}
			break;
		case SNOW_BLOCK:
			if (level < plugin.getConfig().getInteger(DiggingConst.LEVEL.SNOW)) {
				plugin.warnCutBlockLevel(player,
						plugin.getConfig().getInteger(DiggingConst.LEVEL.SNOW));
			} else {
				exp = plugin.getConfig().getDouble(DiggingConst.EXP.SNOW);
			}
			break;
		case SNOW:
			// TODO MC 1.5
			System.out.println(block.getData());
			break;
		case MYCEL:
			if (level < plugin.getConfig().getInteger(
					DiggingConst.LEVEL.MYCELIUM)) {
				plugin.warnCutBlockLevel(
						player,
						plugin.getConfig().getInteger(
								DiggingConst.LEVEL.MYCELIUM));
			} else {
				exp = plugin.getConfig().getDouble(DiggingConst.EXP.MYCELIUM);
			}
			break;
		default:
			// nothing to do
			break;
		}
		plugin.getPersistence().addExp(plugin, player, exp);
	}

	/**
	 * Check whether given player can use the item in her hand.
	 * 
	 * @param player
	 *            The player
	 * @param itemInHand
	 *            The item in her hand
	 * @param level
	 *            Player's level in digging.
	 * @return <code>true</code>, if the item is not a shovel, or the player can
	 *         use the shovel.
	 */
	public boolean checkTools(final Player player, final Material itemInHand,
			final int level) {

		if (itemInHand == Material.WOOD_SPADE
				&& level < plugin.getConfig().getInteger(
						DiggingConst.SHOVEL.WOODEN)) {
			plugin.warnToolLevel(player,
					plugin.getConfig().getInteger(DiggingConst.SHOVEL.WOODEN));

		} else if (itemInHand == Material.STONE_SPADE
				&& level < plugin.getConfig().getInteger(
						DiggingConst.SHOVEL.STONE)) {
			plugin.warnToolLevel(player,
					plugin.getConfig().getInteger(DiggingConst.SHOVEL.STONE));

		} else if (itemInHand == Material.IRON_SPADE
				&& level < plugin.getConfig().getInteger(
						DiggingConst.SHOVEL.IRON)) {
			plugin.warnToolLevel(player,
					plugin.getConfig().getInteger(DiggingConst.SHOVEL.IRON));

		} else if (itemInHand == Material.GOLD_SPADE
				&& level < plugin.getConfig().getInteger(
						DiggingConst.SHOVEL.GOLD)) {
			plugin.warnToolLevel(player,
					plugin.getConfig().getInteger(DiggingConst.SHOVEL.GOLD));

		} else if (itemInHand == Material.DIAMOND_SPADE
				&& level < plugin.getConfig().getInteger(
						DiggingConst.SHOVEL.DIAMOND)) {
			plugin.warnToolLevel(player,
					plugin.getConfig().getInteger(DiggingConst.SHOVEL.DIAMOND));

		} else {
			return true;
		}
		return false;
	}

	/**
	 * Execute fire shovel actions.
	 * 
	 * @param player
	 *            Current player. Must hold a golden shovel in hand.
	 * @param level
	 *            Player's level. Must be above
	 *            <code>Settings.FireShovelLevel</code> to have golden shovel
	 *            act as fire shovels.
	 * @param event
	 *            Digging event.
	 * @return <code>true</code>, if the fire shovel produced something.
	 */
	public boolean fireShovel(final Player player, final int level,
			final BlockBreakEvent event) {
		if (level < plugin.getConfig().getInteger(
				DiggingConst.FIRE_SHOVEL_LEVEL)
				|| player.getItemInHand().getType() != Material.GOLD_SPADE) {
			// requirements not met
			return false;
		}

		Block block = event.getBlock();
		ItemStack drop;
		switch (block.getType()) {
		case SAND:
			drop = new ItemStack(Material.GLASS, 1);
			break;
		case CLAY:
			drop = new ItemStack(Material.CLAY_BRICK, 4);
			break;
		default:
			return false;
		}

		block.setType(Material.AIR);
		block.getWorld().dropItem(block.getLocation(), drop);
		return true;
	}

}
