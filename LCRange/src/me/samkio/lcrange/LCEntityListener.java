package me.samkio.lcrange;

import me.samkio.levelcraftcore.LevelFunctions;
import me.samkio.levelcraftcore.Whitelist;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;

public class LCEntityListener extends EntityListener{
	public LCRange plugin;

	public LCEntityListener(LCRange lcRange) {
		plugin = lcRange;
	}

	public void onEntityDamage(EntityDamageEvent event) {
		if (event.isCancelled())
			return;
		if (!Whitelist.worldCheck(event.getEntity().getWorld()))
			return;
		if (!(event instanceof EntityDamageByProjectileEvent))
			return;
		if (((EntityDamageByProjectileEvent) event).getProjectile() instanceof Arrow
				&& ((EntityDamageByEntityEvent) event).getDamager() instanceof Player
				&& Whitelist.hasLevel(
						(Player) ((EntityDamageByEntityEvent) event)
								.getDamager(), plugin.thisPlug)) {
			plugin.entityListener.EntityDamageByProj((EntityDamageByProjectileEvent) event);
			return;
		}

	}

	private void EntityDamageByProj(EntityDamageByProjectileEvent event) {
		if(!(event.getDamager() instanceof Player)) return;
		if(event.getEntity() instanceof Player){
		Player Damager = (Player) ((EntityDamageByProjectileEvent) event).getDamager();
		Player Damagee = (Player) event.getEntity();
		if(Damager==Damagee) return;
		}

		Player player = (Player) event.getDamager();
		int level = LevelFunctions.getLevel(player, plugin.thisPlug);
			if (level >= plugin.LCConfiguration.Range0p5 && level < plugin.LCConfiguration.Range1p0) {
				event.setDamage(1);
			}else if(level >= plugin.LCConfiguration.Range1p0 && level < plugin.LCConfiguration.Range1p5){
				event.setDamage(2);
			}else if(level >= plugin.LCConfiguration.Range1p5 && level < plugin.LCConfiguration.Range2p0){
				event.setDamage(3);
			}else if(level >= plugin.LCConfiguration.Range2p0 && level < plugin.LCConfiguration.Range2p5){
				event.setDamage(4);
			}else if(level >= plugin.LCConfiguration.Range2p5 && level < plugin.LCConfiguration.Range3p0){
				event.setDamage(5);
			}else if(level >= plugin.LCConfiguration.Range3p0){
				event.setDamage(6);
			}
			LevelFunctions.addExp(player, plugin.thisPlug,plugin.LCConfiguration.PerDamageExp * event.getDamage() );

	}
	
}
