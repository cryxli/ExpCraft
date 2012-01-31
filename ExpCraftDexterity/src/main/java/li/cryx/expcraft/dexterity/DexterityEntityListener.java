package li.cryx.expcraft.dexterity;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class DexterityEntityListener implements Listener {

	private final Dexterity plugin;

	public DexterityEntityListener(final Dexterity plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDamage(final EntityDamageEvent event) {
		if (event.isCancelled()
				|| !plugin.getPermission().worldCheck(
						event.getEntity().getWorld())) {
			return;
		}

		if (!(event.getEntity() instanceof Player)) {
			return;
		}
		if (event.getCause() != DamageCause.FALL) {
			return;
		}

		Player player = (Player) event.getEntity();
		if (!plugin.getPermission().hasLevel(plugin, player)) {
			return;
		}

		int level = plugin.getPersistence().getLevel(plugin, player);

		// calculate dmg reduction
		int damageReduction = (int) Math.floor(level //
				* 10.0 / plugin.getLevelCap() //
				* plugin.getConfDouble("Settings.FallDmgMultilier"));
		// apply reduction
		int dmg = Math.max(0, event.getDamage() - damageReduction);
		event.setDamage(dmg);
	}

}
