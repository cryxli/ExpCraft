package li.cryx.expcraft.woodcutting;

import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.material.Tree;

/**
 * Listener to detect breaking of wooden blocks (planks and logs) and leaves.
 * 
 * @author cryxli
 */
public class WoodCuttingBlockListener extends BlockListener {

	/** Reference to the ExpCraft module */
	private final WoodCutting plugin;

	/** Create a new listener for the given module. */
	public WoodCuttingBlockListener(final WoodCutting plugin) {
		this.plugin = plugin;
	}

	/**
	 * Check whether the player meets the requirements to collect the broken
	 * block, if it is a plank or a log. If not, he receives a warning.
	 * 
	 * @param player
	 *            Current player
	 * @param block
	 *            Destroyed block
	 * @param level
	 *            Player's level in wood cutting
	 * @return <code>true</code>, if the player can collect the block.
	 */
	private boolean checkTargetBlock(final Player player, final Block block,
			final int level) {
		Material material = block.getType();

		if (material == Material.WOOD // wooden planks
				&& level < plugin.getConfInt("UseLevel.Plank")) {
			plugin.warnCutBlock(player, plugin.getConfInt("UseLevel.Plank"));
			return false;

		} else if (material == Material.LOG) {
			// determine the type of log/tree
			Tree tree = (Tree) block.getState().getData();
			TreeSpecies species = tree.getSpecies();

			if (species == TreeSpecies.REDWOOD // pine
					&& level < plugin.getConfInt("UseLevel.Redwood")) {
				plugin.warnCutBlock(player,
						plugin.getConfInt("UseLevel.Redwood"));
				return false;

			} else if (species == TreeSpecies.BIRCH
					&& level < plugin.getConfInt("UseLevel.Birch")) {
				plugin.warnCutBlock(player, plugin.getConfInt("UseLevel.Birch"));
				return false;

			} else if (level < plugin.getConfInt("UseLevel.Log")) {
				// species == TreeSpecies.GENERIC <-> oak
				plugin.warnCutBlock(player, plugin.getConfInt("UseLevel.Log"));
				return false;

			} else {
				return true;

			}
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
	private boolean checkTool(final Player player, final Material material,
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

	@Override
	public void onBlockBreak(final BlockBreakEvent event) {
		if (event.isCancelled()
				|| !plugin.getPermission().worldCheck(
						event.getBlock().getWorld())) {
			// event has been canceled
			// or ExpCraft is not activated for the current world
			return;
		}

		Player player = event.getPlayer();
		if (!plugin.getPermission().hasLevel(plugin, player)) {
			// player is not allowed to use this plugin
			return;
		}

		Material itemInHand = player.getItemInHand().getType();
		int level = plugin.getPersistence().getLevel(plugin, player);
		if (!checkTool(player, itemInHand, level)) {
			// player cannot use the item in hand
			event.setCancelled(true);
			return;
		}

		Block block = event.getBlock();
		if (!checkTargetBlock(player, block, level)) {
			// player cannot collect the block
			event.setCancelled(true);
			return;
		}

		double gained;
		switch (block.getType()) {
		case LOG:
			gained = plugin.getConfDouble("ExpGain.Log");
			break;
		case WOOD: // wooden plank
			gained = plugin.getConfDouble("ExpGain.Plank");
			break;
		case LEAVES:
			if (itemInHand == Material.SHEARS) {
				gained = plugin.getConfDouble("ExpGain.Leaves");
				break;
			}
		default:
			gained = 0;
			break;
		}
		plugin.getPersistence().addExp(plugin, player, gained);

		// double drops
		plugin.dropItem(event.getBlock(), level);
	}
}
