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

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onEntityDamage(final EntityDamageEvent event) {
		// TODO Does this include dmg from sssss-boom?
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
			double dmg = event.getDamage();
			plugin.getPersistence().addExp(plugin, player,
					dmg * plugin.getConfig().getDouble("ExperienceMultiplier"));

			// TODO add material to the equation
			// reduce dmg depending on exp to levelCap ratio
			int level = plugin.getPersistence().getLevel(plugin, player);
			double progress = 1.0 * (plugin.getLevelCap() - level)
					/ plugin.getLevelCap();
			double reducedDmg = 1 + Math.floor(dmg * progress);
			event.setDamage(reducedDmg);
		}
	}

}
