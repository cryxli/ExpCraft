package li.cryx.expcraft.digging;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Listener for block breaks related to digging.
 * 
 * @author cryxli
 */
public class DiggingBlockListener implements Listener {

	private final Digging plugin;

	private final DiggingContraints test;

	public DiggingBlockListener(final Digging plugin) {
		this.plugin = plugin;
		test = new DiggingContraints(plugin);
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

		int level = plugin.getPersistence().getLevel(plugin, player);
		Material itemInHand = player.getItemInHand().getType();
		if (!test.checkTools(player, itemInHand, level)) {
			event.setCancelled(true);
			return;
		}

		Material m = event.getBlock().getType();
		test.addExperience(player, m, level);

		// since fire shovel may drop different items, only do double drops, if
		// nothing special happened
		if (!test.fireShovel(player, level, event)) {
			// double drops
			plugin.dropItem(event.getBlock(), level);
		}
	}
}
