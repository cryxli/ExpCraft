package me.samkio.levelcraft.Listeners;


import me.samkio.levelcraft.Levelcraft;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;

import com.fullwall.Citizens.NPCs.NPCManager;

public class LCEntityListener extends EntityListener{
	public Levelcraft plugin;
	public LCEntityListener(Levelcraft instance) {
		plugin = instance;
	}
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.isCancelled()){
			return;
		}
		if(plugin.Citizens){
		Entity ent = event.getEntity();
		if(NPCManager.isNPC(ent)) return;
		}
		if (event instanceof EntityDamageByProjectileEvent){
			if(((EntityDamageByProjectileEvent) event).getProjectile() instanceof Arrow && ((EntityDamageByEntityEvent) event).getDamager() instanceof Player && plugin.Settings.enableRangeLevel == true && event.getEntity() instanceof Monster && ! plugin.Whitelist.isAvoid((Player) ((EntityDamageByEntityEvent) event).getDamager(), "r")){
				 plugin.Range.attack((EntityDamageByProjectileEvent) event);
				return;
			}else if(((EntityDamageByProjectileEvent) event).getDamager() instanceof Player && plugin.Settings.enableArcherLevel == true && event.getEntity() instanceof Player && ! plugin.Whitelist.isAvoid((Player) ((EntityDamageByEntityEvent) event).getDamager(), "a")){
				Player Damager = (Player) ((EntityDamageByProjectileEvent) event).getDamager();
				Player Damagee = (Player) event.getEntity();
				if(!(Damager.getName()==Damagee.getName())){
					 plugin.Archer.attack((EntityDamageByProjectileEvent) event);
				}
				return;
			}
		}
		if (event instanceof EntityDamageByEntityEvent){
				if(((EntityDamageByEntityEvent) event).getDamager() instanceof Player && plugin.Settings.enableSlayerLevel == true && event.getEntity() instanceof Monster && ! plugin.Whitelist.isAvoid((Player) ((EntityDamageByEntityEvent) event).getDamager(), "s")){
					plugin.Slayer.attack((EntityDamageByEntityEvent) event);
					return;
				} else if(((EntityDamageByEntityEvent) event).getDamager() instanceof Player && plugin.Settings.enableFisticuffsLevel == true && event.getEntity() instanceof Player && ! plugin.Whitelist.isAvoid((Player) ((EntityDamageByEntityEvent) event).getDamager(), "c")){
					Player Damager = (Player) ((EntityDamageByEntityEvent) event).getDamager();
					Player Damagee = (Player) event.getEntity();
					if(!(Damager.getName()==Damagee.getName())){
						 plugin.Fisticuffs.attack((EntityDamageByEntityEvent) event);
					}
					return;
				}
			}
		}
	}


