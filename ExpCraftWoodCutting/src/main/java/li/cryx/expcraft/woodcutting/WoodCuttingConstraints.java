package li.cryx.expcraft.woodcutting;

import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.material.Tree;

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
				&& level < plugin.getConfInt("UseLevel.Fence")) {
			plugin.warnCutBlock(player, plugin.getConfInt("UseLevel.Fence"));
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
				&& level < plugin.getConfInt("AxeLevel.Wooden")) {
			plugin.warnToolLevel(player, plugin.getConfInt("AxeLevel.Wooden"));
		} else if (material == Material.STONE_AXE
				&& level < plugin.getConfInt("AxeLevel.Stone")) {
			plugin.warnToolLevel(player, plugin.getConfInt("AxeLevel.Stone"));
		} else if (material == Material.IRON_AXE
				&& level < plugin.getConfInt("AxeLevel.Iron")) {
			plugin.warnToolLevel(player, plugin.getConfInt("AxeLevel.Iron"));
		} else if (material == Material.GOLD_AXE
				&& level < plugin.getConfInt("AxeLevel.Gold")) {
			plugin.warnToolLevel(player, plugin.getConfInt("AxeLevel.Gold"));
		} else if (material == Material.DIAMOND_AXE
				&& level < plugin.getConfInt("AxeLevel.Diamond")) {
			plugin.warnToolLevel(player, plugin.getConfInt("AxeLevel.Diamond"));
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
		return plugin.getConfDouble("ExpGain." + prefix
				+ getTreeTypeString(treeType));
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
		return plugin.getConfInt("UseLevel." + prefix
				+ getTreeTypeString(treeType));
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
