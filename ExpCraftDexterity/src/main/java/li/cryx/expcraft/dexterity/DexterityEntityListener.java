package li.cryx.expcraft.dexterity;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class DexterityEntityListener implements Listener {

	private final Dexterity plugin;

	private final DexterityConstraints test;

	public DexterityEntityListener(final Dexterity plugin) {
		this.plugin = plugin;
		test = new DexterityConstraints(plugin);
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onEntityDamage(final EntityDamageEvent event) {
		if (!plugin.getPermission().worldCheck(event.getEntity().getWorld())) {
			return;
		}

		if (!(event.getEntity() instanceof Player)) {
			return;
		}
		if (event.getCause() != DamageCause.FALL) {
			return;
		}

		System.out.println("fall");
		Player player = (Player) event.getEntity();
		if (!plugin.getPermission().hasModule(plugin, player)) {
			return;
		}

		int level = plugin.getPersistence().getLevel(plugin, player);
		if (!test.checkBoots(player, level)) {
			// wrong level for boots
			return;
		} else if (!test.isBoots(player.getInventory().getBoots())) {
			// not wearing boots
			return;
		}

		// calculate dmg reduction
		int damageReduction = (int) Math.floor(level //
				* 10.0 / plugin.getLevelCap() //
				* plugin.getConfDouble("Settings.FallDmgMultiplier"));
		// apply reduction
		int dmg = Math.max(0, event.getDamage() - damageReduction);
		event.setDamage(dmg);
	}

}
