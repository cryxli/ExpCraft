package li.cryx.expcraft.farming;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * This class implements a player listener to trace a player's actions before
 * they take place. E.g. player is swinging a hoe he cannot yet use.
 * 
 * @author cryxli
 */
public class FarmingPlayerListener implements Listener {

	/** Reference to parent plugin. */
	private final Farming plugin;

	/** Create a new listener for the given plugin. */
	public FarmingPlayerListener(final Farming instance) {
		this.plugin = instance;
	}

	// Player want to do something
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteract(final PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			// player does not right-click
			return;
		}

		Material m = event.getClickedBlock().getType();
		if (m != Material.DIRT && m != Material.GRASS) {
			// we only want to monitor tilling
			return;
		}

		Player player = event.getPlayer();
		if (!plugin.getPermission().hasModule(plugin, player)) {
			// player does not have permission to us this module
			return;
		}

		Material itemInHand = player.getItemInHand().getType();
		int level = plugin.getPersistence().getLevel(plugin, player);
		if (!plugin.checkTool(player, itemInHand, level)) {
			// player is not allowed to use the tool he's holding
			event.setCancelled(true);
			return;
		}

		if (plugin.isHoe(itemInHand)) {
			// ensure player can till
			if (level < plugin.getConfInt("UseLevel.Till")) {
				plugin.warnCutBlockLevel(player,
						plugin.getConfInt("UseLevel.Till"));
				event.setCancelled(true);
				return;
			}

			// till grass or dirt
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfDouble("ExpGain.Till"));
		}
	}

}