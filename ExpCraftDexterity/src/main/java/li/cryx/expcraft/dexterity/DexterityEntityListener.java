/*
 * Copyright (c) 2011 Urs P. Stettler, https://github.com/cryxli
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package li.cryx.expcraft.dexterity;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

/**
 * Listener that observes fall dmg to players and grants dmg reduction, if they
 * wear boots and meet their level requirements.
 * 
 * @author cryxli
 */
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

		Player player = (Player) event.getEntity();
		if (!plugin.getPermission().hasModule(plugin, player)) {
			return;
		}

		int level = plugin.getPersistence().getLevel(plugin, player);
		if (!test.checkBoots(player, level)) {
			// wrong level for boots
			return;
		}

		// calculate dmg reduction
		int damageReduction = (int) Math.floor(level //
				* 10.0 / plugin.getLevelCap() //
				* plugin.getConfig().getDouble("Settings.FallDmgMultiplier"));
		// apply reduction
		double dmg = Math.max(0, event.getDamage() - damageReduction);
		event.setDamage(dmg);
	}

}
