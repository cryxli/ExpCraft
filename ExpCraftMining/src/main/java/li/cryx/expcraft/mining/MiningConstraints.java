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
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Decision logic used by {@link MiningBlockListener}.
 * 
 * @author cryxli
 */
public class MiningConstraints {

	private final Mining plugin;

	public MiningConstraints(final Mining plugin) {
		this.plugin = plugin;
	}

	private boolean checkAndWarnBlock(final Player player, final int level,
			final String confKey) {
		if (level < plugin.getConfig().getInteger(confKey)) {
			plugin.warnBlockMine(player, plugin.getConfig().getInteger(confKey));
			return false;
		} else {
			return true;
		}
	}

	private boolean checkAndWarnTool(final Player player, final int level,
			final String confKey) {
		if (level < plugin.getConfig().getInteger(confKey)) {
			plugin.warnToolUse(player, plugin.getConfig().getInteger(confKey));
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Checks that the item is hand is usable. Will warn player when not.
	 * 
	 * @param player
	 *            Current player.
	 * @param itemInHand
	 *            Item in hand.
	 * @param level
	 *            Player's level in mining.
	 * @return <code>true</code>, if player can use the item in hand.
	 */
	public boolean checkPickaxe(final Player player, final Material itemInHand,
			final int level) {
		if (itemInHand == Material.WOOD_PICKAXE) {
			return checkAndWarnTool(player, level, "PickaxeLevel.Wooden");
		} else if (itemInHand == Material.STONE_PICKAXE) {
			return checkAndWarnTool(player, level, "PickaxeLevel.Stone");
		} else if (itemInHand == Material.IRON_PICKAXE) {
			return checkAndWarnTool(player, level, "PickaxeLevel.Iron");
		} else if (itemInHand == Material.GOLD_PICKAXE) {
			return checkAndWarnTool(player, level, "PickaxeLevel.Gold");
		} else if (itemInHand == Material.DIAMOND_PICKAXE) {
			return checkAndWarnTool(player, level, "PickaxeLevel.Diamond");
		} else {
			return true;
		}
	}

	/**
	 * Check and produce drops for the fire version of the golden pickaxe.
	 * 
	 * @param player
	 *            Current player. Must have a golden pickaxe in hand.
	 * @param level
	 *            Player's level. Must be above
	 *            <code>Settings.FirePickaxeLevel</code> for golden pickaxes to
	 *            act as fire pickaxes.
	 * @param event
	 *            Mining event
	 * @return <code>true</code>, if a special drop was produced.
	 */
	public boolean firePickaxe(final Player player, final int level,
			final BlockBreakEvent event) {
		if (level < plugin.getConfig().getInteger("Settings.FirePickaxeLevel")
				|| player.getItemInHand().getType() != Material.GOLD_PICKAXE) {
			// requirements not met
			return false;
		}

		Block block = event.getBlock();
		ItemStack drop;
		switch (block.getType()) {
		case STONE:
			drop = new ItemStack(Material.STONE, 1);
			break;
		case IRON_ORE:
			drop = new ItemStack(Material.IRON_INGOT, 1);
			break;
		case GOLD_ORE:
			drop = new ItemStack(Material.GOLD_INGOT, 1);
			break;
		default:
			return false;
		}

		block.setType(Material.AIR);
		block.getWorld().dropItem(block.getLocation(), drop);
		return true;
	}

	/**
	 * Checks that the target block is minable. Will warn player when not.
	 * 
	 * @param player
	 *            Current player.
	 * @param material
	 *            Block that is mined.
	 * @param level
	 *            Player's level in mining.
	 * @return <code>true</code>, if player can mine target block.
	 */
	public boolean isMinable(final Player player, final Material material,
			final int level) {

		switch (material) {
		case STONE:
			return checkAndWarnBlock(player, level, "UseLevel.Stone");
		case COBBLESTONE:
			return checkAndWarnBlock(player, level, "UseLevel.Cobble");
		case MOSSY_COBBLESTONE:
			return checkAndWarnBlock(player, level, "UseLevel.MossStone");
		case COAL_ORE:
			return checkAndWarnBlock(player, level, "UseLevel.CoalOre");
		case IRON_ORE:
			return checkAndWarnBlock(player, level, "UseLevel.IronOre");
		case SANDSTONE:
			return checkAndWarnBlock(player, level, "UseLevel.SandStone");
		case GOLD_ORE:
			return checkAndWarnBlock(player, level, "UseLevel.GoldOre");
		case LAPIS_ORE:
			return checkAndWarnBlock(player, level, "UseLevel.LapisOre");
		case REDSTONE_ORE:
		case GLOWING_REDSTONE_ORE:
			return checkAndWarnBlock(player, level, "UseLevel.Redstone");
		case REDSTONE_BLOCK:
			return checkAndWarnBlock(player, level, "UseLevel.RedstoneBlock");
		case DIAMOND_ORE:
			return checkAndWarnBlock(player, level, "UseLevel.DiamondOre");
		case OBSIDIAN:
			return checkAndWarnBlock(player, level, "UseLevel.Obsidian");
		case NETHERRACK:
			return checkAndWarnBlock(player, level, "UseLevel.Netherrack");
		case NETHER_BRICK:
			return checkAndWarnBlock(player, level, "UseLevel.NetherBrick");
		case SMOOTH_BRICK:
			return checkAndWarnBlock(player, level, "UseLevel.StoneBrick");
		case QUARTZ_BLOCK:
			return checkAndWarnBlock(player, level, "UseLevel.Quartz");
		case QUARTZ_ORE:
			return checkAndWarnBlock(player, level, "UseLevel.QuartzOre");
		default:
			return true;
		}
	}

}
