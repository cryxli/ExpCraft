package me.samkio.levelcraft.Skills;

import me.samkio.levelcraft.Levelcraft;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;

public class Range {
	public  Levelcraft plugin;
	public Range(Levelcraft instance) {
		plugin = instance;
	}
	public  void attack(EntityDamageByProjectileEvent event) {
		Player player = (Player) event.getDamager();
		plugin.PlayerFunctions.checkAccount(player);
		int level = 0;
		double stat = 0;
		if (plugin.Settings.enableRangeLevel == true) {			
			level = plugin.Level.getLevel(player, "r");
			stat = plugin.Level.getExp(player, "r");
			if (level >= plugin.Settings.Rangep5 && level < plugin.Settings.Range1p0) {
				event.setDamage(1);
			}else if(level >= plugin.Settings.Range1p0 && level < plugin.Settings.Range1p5){
				event.setDamage(2);
			}else if(level >= plugin.Settings.Range1p5 && level < plugin.Settings.Range2p0){
				event.setDamage(3);
			}else if(level >= plugin.Settings.Range2p0 && level < plugin.Settings.Range2p5){
				event.setDamage(4);
			}else if(level >= plugin.Settings.Range2p5 && level < plugin.Settings.Range3p0){
				event.setDamage(5);
			}else if(level >= plugin.Settings.Range3p0){
				event.setDamage(6);
			}
			stat = stat + plugin.Settings.ExpPerDamage;
			int aftlevel = 0;
			plugin.Level.update(player, "r", stat);
			aftlevel = plugin.Level.getLevel(player, "r");
			if (aftlevel > level) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1)+ "[LC]"
						+ ChatColor.valueOf(plugin.Settings.c3)
						+ "Level up! Your Range level is now " + aftlevel);
			} else if (plugin.PlayerFunctions.enabled(player) == true) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
						+ ChatColor.valueOf(plugin.Settings.c3) + " You gained "+ plugin.Settings.ExpPerDamage+ " exp.");
			}

		}
	}
}
