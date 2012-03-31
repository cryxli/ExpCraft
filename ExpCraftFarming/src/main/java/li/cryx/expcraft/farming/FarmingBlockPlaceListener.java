package li.cryx.expcraft.farming;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * This class implements a block listener that traces block placement, and,
 * initiates the according farming related actions.
 * 
 * @author cryxli
 */
public class FarmingBlockPlaceListener implements Listener {

	/** Reference to parent plugin. */
	public Farming plugin;

	/** Create a new listener for the given plugin. */
	public FarmingBlockPlaceListener(final Farming instance) {
		this.plugin = instance;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockPlace(final BlockPlaceEvent event) {
		if (event.isCancelled()
				|| !plugin.getPermission().worldCheck(
						event.getBlock().getWorld())) {
			// event is already consumed
			// or, the world is not managed by the ExpCraft
			return;
		}

		Player player = event.getPlayer();
		if (!plugin.getPermission().hasModule(plugin, player)) {
			// player does not have permission to us this module
			return;
		}

		Material m = event.getBlock().getType();
		int level = plugin.getPersistence().getLevel(plugin, player);
		double gained = 0;

		switch (m) {
		case SAPLING: // planting a tree
			if (level >= plugin.getConfInt("UseLevel.Sapling")) {
				gained = plugin.getConfDouble("ExpGain.Sapling");
			}
			break;
		case RED_ROSE: // planting a rose
			if (level >= plugin.getConfInt("UseLevel.RedRose")) {
				gained = plugin.getConfDouble("ExpGain.RedRose");
			}
			break;
		case YELLOW_FLOWER: // planting a dandelion
			if (level >= plugin.getConfInt("UseLevel.YellowFlower")) {
				gained = plugin.getConfDouble("ExpGain.YellowFlower");
			}
			break;
		case BROWN_MUSHROOM:
		case RED_MUSHROOM: // planting a mushroom
			if (level >= plugin.getConfInt("UseLevel.Mushroom")) {
				gained = plugin.getConfDouble("ExpGain.Mushroom");
			}
			break;
		default:
			// placing a block not covered by this module
			return;
		}
		plugin.getPersistence().addExp(plugin, player, gained);
	}
}
