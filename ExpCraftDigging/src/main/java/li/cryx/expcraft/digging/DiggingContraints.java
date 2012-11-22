package li.cryx.expcraft.digging;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * TODO documentation
 * 
 * @author cryxli
 */
public class DiggingContraints {

	private final Digging plugin;

	public DiggingContraints(final Digging plugin) {
		this.plugin = plugin;
	}

	public void addExperience(final Player player, final Material m,
			final int level) {
		double exp = 0;
		switch (m) {
		case DIRT:
			if (level < plugin.getConfig().getInteger("UseLevel.Dirt")) {
				plugin.warnCutBlockLevel(player,
						plugin.getConfig().getInteger("UseLevel.Dirt"));
			} else {
				exp = plugin.getConfig().getDouble("ExpGain.Dirt");
			}
			break;
		case GRASS:
			if (level < plugin.getConfig().getInteger("UseLevel.Grass")) {
				plugin.warnCutBlockLevel(player,
						plugin.getConfig().getInteger("UseLevel.Grass"));
			} else {
				exp = plugin.getConfig().getDouble("ExpGain.Grass");
			}
			break;
		case SAND:
			if (level < plugin.getConfig().getInteger("UseLevel.Sand")) {
				plugin.warnCutBlockLevel(player,
						plugin.getConfig().getInteger("UseLevel.Sand"));
			} else {
				exp = plugin.getConfig().getDouble("ExpGain.Sand");
			}
			break;
		case CLAY:
			if (level < plugin.getConfig().getInteger("UseLevel.Clay")) {
				plugin.warnCutBlockLevel(player,
						plugin.getConfig().getInteger("UseLevel.Clay"));
			} else {
				exp = plugin.getConfig().getDouble("ExpGain.Clay");
			}
			break;
		case SOUL_SAND:
			if (level < plugin.getConfig().getInteger("UseLevel.SoulSand")) {
				plugin.warnCutBlockLevel(player,
						plugin.getConfig().getInteger("UseLevel.SoulSand"));
			} else {
				exp = plugin.getConfig().getDouble("ExpGain.SoulSand");
			}
			break;
		case GRAVEL:
			if (level < plugin.getConfig().getInteger("UseLevel.Gravel")) {
				plugin.warnCutBlockLevel(player,
						plugin.getConfig().getInteger("UseLevel.Gravel"));
			} else {
				exp = plugin.getConfig().getDouble("ExpGain.Gravel");
			}
			break;
		case SNOW:
			if (level < plugin.getConfig().getInteger("UseLevel.Snow")) {
				plugin.warnCutBlockLevel(player,
						plugin.getConfig().getInteger("UseLevel.Snow"));
			} else {
				exp = plugin.getConfig().getDouble("ExpGain.Snow");
			}
			break;
		case MYCEL:
			if (level < plugin.getConfig().getInteger("UseLevel.Mycelium")) {
				plugin.warnCutBlockLevel(player,
						plugin.getConfig().getInteger("UseLevel.Mycelium"));
			} else {
				exp = plugin.getConfig().getDouble("ExpGain.Mycelium");
			}
			break;
		default:
			// nothing to do
			break;
		}
		plugin.getPersistence().addExp(plugin, player, exp);
	}

	public boolean checkTools(final Player player, final Material itemInHand,
			final int level) {

		if (itemInHand == Material.WOOD_SPADE
				&& level < plugin.getConfig().getInteger("ShovelLevel.Wooden")) {
			plugin.warnToolLevel(player,
					plugin.getConfig().getInteger("ShovelLevel.Wooden"));

		} else if (itemInHand == Material.STONE_SPADE
				&& level < plugin.getConfig().getInteger("ShovelLevel.Stone")) {
			plugin.warnToolLevel(player,
					plugin.getConfig().getInteger("ShovelLevel.Stone"));

		} else if (itemInHand == Material.IRON_SPADE
				&& level < plugin.getConfig().getInteger("ShovelLevel.Iron")) {
			plugin.warnToolLevel(player,
					plugin.getConfig().getInteger("ShovelLevel.Iron"));

		} else if (itemInHand == Material.GOLD_SPADE
				&& level < plugin.getConfig().getInteger("ShovelLevel.Gold")) {
			plugin.warnToolLevel(player,
					plugin.getConfig().getInteger("ShovelLevel.Gold"));

		} else if (itemInHand == Material.DIAMOND_SPADE
				&& level < plugin.getConfig().getInteger("ShovelLevel.Diamond")) {
			plugin.warnToolLevel(player,
					plugin.getConfig().getInteger("ShovelLevel.Diamond"));

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
		if (level < plugin.getConfig().getInteger("Settings.FireShovelLevel")
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
