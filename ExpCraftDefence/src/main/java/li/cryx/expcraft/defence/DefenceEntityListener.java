package li.cryx.expcraft.defence;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

/**
 * This class observes damage dealt to players.
 * 
 * @author cryxli
 */
public class DefenceEntityListener implements Listener {

	/** Reference to plugin. */
	private final Defence plugin;

	private final DefenceConstraints test;

	/**
	 * Create a new listener for given plugin.
	 * 
	 * @param plugin
	 *            Reference to plugin
	 */
	public DefenceEntityListener(final Defence plugin) {
		this.plugin = plugin;
		test = new DefenceConstraints(plugin);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDamage(final EntityDamageEvent event) {
		if (event.isCancelled()) {
			// event has been canceled
			return;
		}
		if (event.getCause() != DamageCause.ENTITY_ATTACK
				&& event.getCause() != DamageCause.PROJECTILE) {
			// not a damage type that indicates a fight
			return;
		}
		if (!(event.getEntity() instanceof Player)) {
			// target is not a player
			return;
		}

		Player player = (Player) event.getEntity();
		if (!plugin.getPermission().hasModule(plugin, player)) {
			// plugin is not active for player
			return;
		}

		if (test.checkArmor(player)) {
			// get exp proportional to dealt damage (before armor bonus is
			// applied)
			plugin.getPersistence().addExp(
					plugin,
					player,
					event.getDamage()
							* plugin.getConfDouble("ExperienceMultiplier"));

			// TODO reduce dmg depending on exp to levelCap and material ratio
		}
	}

}
