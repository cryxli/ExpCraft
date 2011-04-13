package me.samkio.levelcraft.Skills;

import me.samkio.levelcraft.Levelcraft;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;


public class Archer {
	public  Levelcraft plugin;
	public Archer(Levelcraft instance) {
		plugin = instance;
	}
	public  void attack(EntityDamageByProjectileEvent event) {
		Player player = (Player) event.getDamager();
		plugin.PlayerFunctions.checkAccount(player);
		int level = 0;
		double stat = 0;
		if (plugin.Settings.enableRangeLevel == true) {
			level = plugin.Level.getLevel(player, "a");
			stat = plugin.Level.getExp(player, "a");
			if (level >= plugin.Settings.Archerp5 && level < plugin.Settings.Archer1p0) {
				event.setDamage(1);
			}else if(level >= plugin.Settings.Archer1p0 && level < plugin.Settings.Archer1p5){
				event.setDamage(2);
			}else if(level >= plugin.Settings.Archer1p5 && level < plugin.Settings.Archer2p0){
				event.setDamage(3);
			}else if(level >= plugin.Settings.Archer2p0 && level < plugin.Settings.Archer2p5){
				event.setDamage(4);
			}else if(level >= plugin.Settings.Archer2p5 && level < plugin.Settings.Archer3p0){
				event.setDamage(5);
			}else if(level >= plugin.Settings.Archer3p0){
				event.setDamage(6);
			}
			stat = stat + plugin.Settings.ExpPerDamage;
			int aftlevel = 0;
			plugin.Level.update(player, "a", stat);
			aftlevel = plugin.Level.getLevel(player, "a");
			if (aftlevel > level) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
						+ ChatColor.valueOf(plugin.Settings.c3)
						+ "Level up! Your Archery level is now " + aftlevel);
			} else if (plugin.PlayerFunctions.enabled(player) == true) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
						+ ChatColor.valueOf(plugin.Settings.c3) + " You gained "+ plugin.Settings.ExpPerDamage +" exp.");
			}
		}
	}
}
