package me.samkio.levelcraft.Skills;
import me.samkio.levelcraft.Levelcraft;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Slayer {
	public  Levelcraft plugin;
	public Slayer(Levelcraft instance) {
		plugin = instance;
	}
	public  void attack(EntityDamageByEntityEvent event) {
		Player player = (Player) event.getDamager();
		int iih = player.getItemInHand().getTypeId();
		plugin.PlayerFunctions.checkAccount(player);
		int level = 0;
		double stat = 0;
		if (plugin.Settings.enableSlayerLevel == true) {
			level = plugin.Level.getLevel(player, "s");
			stat = plugin.Level.getExp(player, "s");
			if (level < plugin.Settings.SlayIronSword && iih == 267) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot use this tool. Required Level:"
						+ plugin.Settings.SlayIronSword);
				event.setCancelled(true);
			} else if (level < plugin.Settings.SlayGoldSword && iih == 283) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot use this tool. Required Level:"
						+ plugin.Settings.SlayGoldSword);
				event.setCancelled(true);
			} else if (level < plugin.Settings.SlayDiamondSword && iih == 276) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot use this tool. Required Level:"
						+ plugin.Settings.SlayDiamondSword);
				event.setCancelled(true);
			} else if (level < plugin.Settings.SlayStoneSword && iih == 272) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot use this tool. Required Level:"
						+ plugin.Settings.SlayStoneSword);
				event.setCancelled(true);
			} else if (level < plugin.Settings.SlayWoodSword && iih == 268) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot use this tool. Required Level:"
						+ plugin.Settings.SlayWoodSword);
				event.setCancelled(true);
			} else {

				stat = stat + plugin.Settings.ExpPerDamage;
				int aftlevel = 0;
				plugin.Level.update(player, "s",stat);
				aftlevel = plugin.Level.getLevel(player, "s");
				
				if (aftlevel > level) {
					player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
							+ ChatColor.valueOf(plugin.Settings.c3)
							+ "Level up! Your Slayer level is now " + aftlevel);
				} else if (plugin.PlayerFunctions.enabled(player) == true) {
					player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
							+ ChatColor.valueOf(plugin.Settings.c3) + " You gained "+plugin.Settings.ExpPerDamage+" exp.");
				}

			}

		}
	}
}
