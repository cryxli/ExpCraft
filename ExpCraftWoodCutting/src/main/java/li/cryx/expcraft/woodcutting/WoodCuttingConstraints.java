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
import org.bukkit.material.Tree;

/**
 * This class contains decision logic used by the wood cutting module.
 * 
 * @author cryxli
 */
public class WoodCuttingConstraints {

	private final WoodCutting plugin;

	public WoodCuttingConstraints(final WoodCutting plugin) {
		this.plugin = plugin;
	}

	/**
	 * Check whether the given player can break the given type of leaves.
	 * 
	 * @param player
	 *            Current player
	 * @param level
	 *            Wood cutting level of current player
	 * @param species
	 *            Tree type
	 * @return <code>true</code>, if the player can harvest the indicated leaf
	 *         type.
	 */
	protected boolean checkLeafBreaking(final Player player, final int level,
			final TreeSpecies species) {
		int levelReq = getLevel("Leaves", species);
		if (level < levelReq) {
			plugin.warnCutBlock(player, levelReq);
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Check whether the player meets the requirements to collect the broken
	 * block, if it is a fence, plank or a log. If not, he receives a warning.
	 * 
	 * @param player
	 *            Current player
	 * @param block
	 *            Destroyed block
	 * @param level
	 *            Player's level in wood cutting
	 * @return <code>true</code>, if the player can collect the block.
	 */
	protected boolean checkTargetBlock(final Player player, final Block block,
			final int level) {
		Material material = block.getType();

		if (material == Material.WOOD) {
			TreeSpecies species = getTreeType(block);
			int levelReq = getLevel("Plank", species);
			if (level < levelReq) {
				plugin.warnCutBlock(player, levelReq);
				return false;
			} else {
				return true;
			}

		} else if (material == Material.LOG) {
			TreeSpecies species = getTreeType(block);
			int levelReq = getLevel("Log", species);
			if (level < levelReq) {
				plugin.warnCutBlock(player, levelReq);
				return false;
			} else {
				return true;
			}

		} else if (material == Material.FENCE
				&& level < plugin.getConfig().getInteger("UseLevel.Fence")) {
			plugin.warnCutBlock(player,
					plugin.getConfig().getInteger("UseLevel.Fence"));
			return false;

		} else {
			return true;
		}
	}

	/**
	 * Check whether the player meets the requirements to use an axe.
	 * 
	 * @param player
	 *            Current player.
	 * @param material
	 *            Material describing the item in hand.
	 * @param level
	 *            Player's level of wood cutting.
	 * @return <code>true</code>, if the player can use the item in hand.
	 */
	protected boolean checkTool(final Player player, final Material material,
			final int level) {
		if (material == Material.WOOD_AXE
				&& level < plugin.getConfig().getInteger("AxeLevel.Wooden")) {
			plugin.warnToolLevel(player,
					plugin.getConfig().getInteger("AxeLevel.Wooden"));
		} else if (material == Material.STONE_AXE
				&& level < plugin.getConfig().getInteger("AxeLevel.Stone")) {
			plugin.warnToolLevel(player,
					plugin.getConfig().getInteger("AxeLevel.Stone"));
		} else if (material == Material.IRON_AXE
				&& level < plugin.getConfig().getInteger("AxeLevel.Iron")) {
			plugin.warnToolLevel(player,
					plugin.getConfig().getInteger("AxeLevel.Iron"));
		} else if (material == Material.GOLD_AXE
				&& level < plugin.getConfig().getInteger("AxeLevel.Gold")) {
			plugin.warnToolLevel(player,
					plugin.getConfig().getInteger("AxeLevel.Gold"));
		} else if (material == Material.DIAMOND_AXE
				&& level < plugin.getConfig().getInteger("AxeLevel.Diamond")) {
			plugin.warnToolLevel(player,
					plugin.getConfig().getInteger("AxeLevel.Diamond"));
		} else {
			return true;
		}
		return false;
	}

	/**
	 * Get the experience for the given tree type.
	 * 
	 * @param prefix
	 *            Prefix in the config: "Log", "Plank", "Leaves"
	 * @param treeType
	 *            Tree type to look up.
	 * @return Experience gained when harvesting the given type.
	 */
	public double getExp(final String prefix, final TreeSpecies treeType) {
		return plugin.getConfig().getDouble(
				"ExpGain." + prefix + getTreeTypeString(treeType));
	}

	/**
	 * Get the level requirement for the given tree type.
	 * 
	 * @param prefix
	 *            Prefix in the config: "Log", "Plank", "Leaves"
	 * @param treeType
	 *            Tree type to look up.
	 * @return Level required to harvest the given type.
	 */
	protected int getLevel(final String prefix, final TreeSpecies treeType) {
		return plugin.getConfig().getInteger(
				"UseLevel." + prefix + getTreeTypeString(treeType));
	}

	/**
	 * Get the tree species of the given log, plank, leaf or sapling.
	 * 
	 * @param block
	 *            THe block to inspect.
	 * @return The <code>TreeSpecies</code> of the given block, or,
	 *         <code>null</code>, if it is not a tree like block.
	 */
	protected TreeSpecies getTreeType(final Block block) {
		switch (block.getType()) {
		case LOG:
		case WOOD:
		case SAPLING:
		case LEAVES:
			Tree tree = (Tree) block.getState().getData();
			return tree.getSpecies();
		default:
			return null;
		}
	}

	/**
	 * Get the string used in config to identify tree types.
	 * 
	 * @param treeType
	 *            The internal tree type.
	 * @return A string containing the tree species description. Defaults to
	 *         <code>Oak</code>.
	 */
	protected String getTreeTypeString(final TreeSpecies treeType) {
		switch (treeType) {
		default:
		case GENERIC:
			return "Oak";
		case BIRCH:
			return "Birch";
		case REDWOOD:
			return "Redwood";
		case JUNGLE:
			return "Jungle";
		}
	}

}
