package li.cryx.expcraft.dexterity;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Listener to observe player movements. Jumping gives exp.
 * 
 * @author cryxli
 */
public class DexterityPlayerListener implements Listener {

	private final Dexterity plugin;

	public DexterityPlayerListener(final Dexterity plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerMove(final PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (!plugin.getPermission().worldCheck(player.getWorld())
				|| !plugin.getPermission().hasModule(plugin, player)) {
			return;
		}

		if (event.getFrom().getBlockY() < event.getTo().getBlockY()
				&& event.getFrom().getBlock().getType() == Material.AIR) {
			// exclude stairs, steps, water, lava...

			// player is jumping, add exp
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfig().getDouble("ExpGain.Jump"));
		}
	}

}
