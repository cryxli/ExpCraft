package me.samkio.levelcraft.Skills;
import me.samkio.levelcraft.Levelcraft;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
public class Fisticuffs {
	public  Levelcraft plugin;
	public Fisticuffs(Levelcraft instance) {
		plugin = instance;
	}
	public  void attack(EntityDamageByEntityEvent event) {
		Player player = (Player) event.getDamager();
		int iih = player.getItemInHand().getTypeId();
		plugin.PlayerFunctions.checkAccount(player);
		int level = 0;
		double stat = 0;
		if (plugin.Settings.enableFisticuffsLevel == true) {
		level = plugin.Level.getLevel(player, "c");
			if (level < plugin.Settings.FisticuffsIronSword && iih == 267) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot use this weapon. Required Level:"
						+ plugin.Settings.FisticuffsIronSword);
				event.setCancelled(true);
			} else if (level < plugin.Settings.FisticuffsGoldSword && iih == 283) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot use this weapon. Required Level:"
						+ plugin.Settings.FisticuffsGoldSword);
				event.setCancelled(true);
			} else if (level < plugin.Settings.FisticuffsDiamondSword && iih == 276) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot use this weapon. Required Level:"
						+ plugin.Settings.FisticuffsDiamondSword);
				event.setCancelled(true);
			} else if (level < plugin.Settings.FisticuffsStoneSword && iih == 272) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot use this weapon. Required Level:"
						+ plugin.Settings.FisticuffsStoneSword);
				event.setCancelled(true);
			} else if (level < plugin.Settings.FisticuffsWoodSword && iih == 268) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot use this weapon. Required Level:"
						+ plugin.Settings.FisticuffsWoodSword);
				event.setCancelled(true);
			} else {

				stat = stat + plugin.Settings.ExpPerDamage;
				int aftlevel = 0;
				plugin.Level.update(player, "c", stat);
				aftlevel = plugin.Level.getLevel(player, "c");
				if (aftlevel > level) {
					player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
							+ ChatColor.valueOf(plugin.Settings.c3)
							+ "Level up! Your Fisticuffs level is now " + aftlevel);
				} else if (plugin.PlayerFunctions.enabled(player) == true) {
					player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
							+ ChatColor.valueOf(plugin.Settings.c3) + " You gained "+plugin.Settings.ExpPerDamage+" exp.");
				}
			}
		}
	}
}
