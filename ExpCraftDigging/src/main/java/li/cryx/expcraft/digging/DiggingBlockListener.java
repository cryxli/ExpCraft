package li.cryx.expcraft.digging;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.inventory.ItemStack;

public class DiggingBlockListener extends BlockListener {

	private final Digging plugin;

	public DiggingBlockListener(final Digging plugin) {
		this.plugin = plugin;
	}

	private void addExperience(final Player player, final Material m,
			final int level) {
		double exp = 0;
		switch (m) {
		case DIRT:
			if (level < plugin.getConfInt("UseLevel.Dirt")) {
				plugin.warnCutBlockLevel(player,
						plugin.getConfInt("UseLevel.Dirt"));
			} else {
				exp = plugin.getConfDouble("ExpGain.Dirt");
			}
			break;
		case GRASS:
			if (level < plugin.getConfInt("UseLevel.Grass")) {
				plugin.warnCutBlockLevel(player,
						plugin.getConfInt("UseLevel.Grass"));
			} else {
				exp = plugin.getConfDouble("ExpGain.Grass");
			}
			break;
		case SAND:
			if (level < plugin.getConfInt("UseLevel.Sand")) {
				plugin.warnCutBlockLevel(player,
						plugin.getConfInt("UseLevel.Sand"));
			} else {
				exp = plugin.getConfDouble("ExpGain.Sand");
			}
			break;
		case CLAY:
			if (level < plugin.getConfInt("UseLevel.Clay")) {
				plugin.warnCutBlockLevel(player,
						plugin.getConfInt("UseLevel.Clay"));
			} else {
				exp = plugin.getConfDouble("ExpGain.Clay");
			}
			break;
		case SOUL_SAND:
			if (level < plugin.getConfInt("UseLevel.SoulSand")) {
				plugin.warnCutBlockLevel(player,
						plugin.getConfInt("UseLevel.SoulSand"));
			} else {
				exp = plugin.getConfDouble("ExpGain.SoulSand");
			}
			break;
		case GRAVEL:
			if (level < plugin.getConfInt("UseLevel.Gravel")) {
				plugin.warnCutBlockLevel(player,
						plugin.getConfInt("UseLevel.Gravel"));
			} else {
				exp = plugin.getConfDouble("ExpGain.Gravel");
			}
			break;
		case SNOW:
			if (level < plugin.getConfInt("UseLevel.Snow")) {
				plugin.warnCutBlockLevel(player,
						plugin.getConfInt("UseLevel.Snow"));
			} else {
				exp = plugin.getConfDouble("ExpGain.Snow");
			}
			break;
		default:
			// nothing to do
			break;
		}
		plugin.getPersistence().addExp(plugin, player, exp);
	}

	private boolean checkTools(final Player player, final Material itemInHand,
			final int level) {

		if (itemInHand == Material.WOOD_SPADE
				&& level < plugin.getConfInt("ShovelLevel.Wooden")) {
			plugin.warnToolLevel(player,
					plugin.getConfInt("ShovelLevel.Wooden"));

		} else if (itemInHand == Material.STONE_SPADE
				&& level < plugin.getConfInt("ShovelLevel.Stone")) {
			plugin.warnToolLevel(player, plugin.getConfInt("ShovelLevel.Stone"));

		} else if (itemInHand == Material.IRON_SPADE
				&& level < plugin.getConfInt("ShovelLevel.Iron")) {
			plugin.warnToolLevel(player, plugin.getConfInt("ShovelLevel.Iron"));

		} else if (itemInHand == Material.GOLD_SPADE
				&& level < plugin.getConfInt("ShovelLevel.Gold")) {
			plugin.warnToolLevel(player, plugin.getConfInt("ShovelLevel.Gold"));

		} else if (itemInHand == Material.DIAMOND_SPADE
				&& level < plugin.getConfInt("ShovelLevel.Diamond")) {
			plugin.warnToolLevel(player,
					plugin.getConfInt("ShovelLevel.Diamond"));

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
	private boolean fireShovel(final Player player, final int level,
			final BlockBreakEvent event) {
		if (level < plugin.getConfInt("Settings.FireShovelLevel")
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

	@Override
	public void onBlockBreak(final BlockBreakEvent event) {
		if (event.isCancelled()
				|| !plugin.getPermission().worldCheck(
						event.getBlock().getWorld())) {
			return;
		}

		Player player = event.getPlayer();
		if (!plugin.getPermission().hasLevel(plugin, player)) {
			return;
		}

		int level = plugin.getPersistence().getLevel(plugin, player);
		Material itemInHand = player.getItemInHand().getType();
		if (!checkTools(player, itemInHand, level)) {
			event.setCancelled(true);
			return;
		}

		Material m = event.getBlock().getType();
		addExperience(player, m, level);

		// since fire shovel may drop different items, only do double drops, if
		// nothing special happened
		if (!fireShovel(player, level, event)) {
			// double drops
			plugin.dropItem(event.getBlock(), level);
		}
	}
}