package me.samkio.levelcraft.Skills;
import me.samkio.levelcraft.Levelcraft;
import me.samkio.levelcraft.Functions.LevelFunctions;
import me.samkio.levelcraft.Functions.PlayerFunctions;
import me.samkio.levelcraft.SamToolbox.DataMySql;
import me.samkio.levelcraft.SamToolbox.DataSqlite;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Slayer {
	public static Levelcraft plugin;
	public static void attack(EntityDamageByEntityEvent event) {
		Player player = (Player) event.getDamager();
		int iih = player.getItemInHand().getTypeId();
		PlayerFunctions.checkAccount(player);
		int level = 0;
		double stat = 0;
		if (plugin.Settings.enableSlayerLevel == true) {
			if (plugin.Settings.database.equalsIgnoreCase("flatfile")) {
				level = LevelFunctions.getLevel(player, Levelcraft.SlayExpFile);
				stat = LevelFunctions.getExp(player, Levelcraft.SlayExpFile);
			} else if (plugin.Settings.database.equalsIgnoreCase("sqlite")) {
				stat = DataSqlite.getExp(player, "SlayingExp");
				level = DataSqlite.getLevel(player, "SlayingExp");
			}
			else if (plugin.Settings.database.equalsIgnoreCase("mysql")) {
				stat = DataMySql.getExp(player, "SlayingExp");
				level = DataMySql.getLevel(player, "SlayingExp");
			}
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
				if (plugin.Settings.database.equalsIgnoreCase("flatfile")) {
					LevelFunctions.write(player, stat, Levelcraft.SlayExpFile);
					aftlevel = LevelFunctions.getLevel(player,
							Levelcraft.SlayExpFile);
				} else if (plugin.Settings.database.equalsIgnoreCase("sqlite")) {
					DataSqlite.update(player, "SlayingExp", stat);
					aftlevel = DataSqlite.getLevel(player, "SlayingExp");
				}
				else if (plugin.Settings.database.equalsIgnoreCase("mysql")) {
					DataMySql.update(player, "SlayingExp", stat);
					aftlevel = DataMySql.getLevel(player, "SlayingExp");
				}
				if (aftlevel > level) {
					player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
							+ ChatColor.valueOf(plugin.Settings.c3)
							+ " Level up! Your Slayer level is now " + aftlevel);
				} else if (PlayerFunctions.enabled(player) == true) {
					player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
							+ ChatColor.valueOf(plugin.Settings.c3) + " You gained "+plugin.Settings.ExpPerDamage+" exp.");
				}

			}

		}
	}
}
