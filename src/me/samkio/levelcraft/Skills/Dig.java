package me.samkio.levelcraft.Skills;

import me.samkio.levelcraft.Levelcraft;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

public class Dig {
	public  Levelcraft plugin;
	public Dig(Levelcraft instance) {
		plugin = instance;
	}
	public  void Destroy(BlockBreakEvent event) {
		Player player = event.getPlayer();
		int iih = player.getItemInHand().getTypeId();
		plugin.PlayerFunctions.checkAccount(player);
		if (plugin.Settings.enableDigLevel == true) {
			int level = 0;
			double stat = 0;
            double gained = 0;
			level = plugin.Level.getLevel(player, "d");
			stat = plugin.Level.getExp(player, "d");
         
			if (level < plugin.Settings.DIronShov && iih == 256) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot use this tool. Required Level:"
						+ plugin.Settings.DIronShov);
				event.setCancelled(true);
			} else if (level < plugin.Settings.DGoldShov && iih == 284) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot use this tool. Required Level:"
						+ plugin.Settings.DGoldShov);
				event.setCancelled(true);
			} else if (level < plugin.Settings.DDiamondShov && iih == 277) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot use this tool. Required Level:"
						+ plugin.Settings.DDiamondShov);
				event.setCancelled(true);
			} else if (level < plugin.Settings.DStoneShov && iih == 273) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot use this tool. Required Level:"
						+ plugin.Settings.DStoneShov);
				event.setCancelled(true);
			} else if (level < plugin.Settings.DWoodShov && iih == 269) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot use this tool. Required Level:"
						+ plugin.Settings.DWoodShov);
				event.setCancelled(true);
				
			} else if (level < plugin.Settings.SandLevel
					&& event.getBlock().getType() == Material.SAND) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot dig this block. Required Level:"
						+ plugin.Settings.SandLevel);
				event.setCancelled(true);
			} else if (level < plugin.Settings.GravelLevel
					&& event.getBlock().getType() == Material.GRAVEL) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot dig this block. Required Level:"
						+ plugin.Settings.GravelLevel);
				event.setCancelled(true);
			} else if (level < plugin.Settings.GrassLevel
					&& event.getBlock().getType() == Material.GRASS) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot dig this block. Required Level:"
						+ plugin.Settings.GrassLevel);

				event.setCancelled(true);
			} else if (level < plugin.Settings.DirtLevel
					&& event.getBlock().getType() == Material.DIRT) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot dig this block. Required Level:"
						+ plugin.Settings.DirtLevel);
				event.setCancelled(true);
			}else if (level < plugin.Settings.DirtLevel
					&& event.getBlock().getType() == Material.SOIL) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot dig this block. Required Level:"
						+ plugin.Settings.DirtLevel);
				event.setCancelled(true);
			} else if (level < plugin.Settings.SoulSandLevel
					&& event.getBlock().getType() == Material.SOUL_SAND) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot dig this block. Required Level:"
						+ plugin.Settings.SoulSandLevel);
				event.setCancelled(true);
			} else if (level < plugin.Settings.ClayLevel
					&& event.getBlock().getType() == Material.CLAY) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot dig this block. Required Level:"
						+ plugin.Settings.ClayLevel);
				event.setCancelled(true);
			} else if (level < plugin.Settings.SnowLevel
					&& event.getBlock().getType() == Material.SNOW_BLOCK) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot dig this block. Required Level:"
						+ plugin.Settings.SnowLevel);
				event.setCancelled(true);
			} else if (level < plugin.Settings.SnowLevel
					&& event.getBlock().getType() == Material.SNOW) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot dig this block. Required Level:"
						+ plugin.Settings.SnowLevel);
				event.setCancelled(true);
			} 
			
			 else if (event.getBlock().getType() == Material.SNOW
					|| event.getBlock().getType() == Material.SNOW_BLOCK
					|| event.getBlock().getType() == Material.DIRT
					|| event.getBlock().getType() == Material.GRASS
					|| event.getBlock().getType() == Material.SAND
					|| event.getBlock().getType() == Material.SOUL_SAND
					|| event.getBlock().getType() == Material.SOIL
					|| event.getBlock().getType() == Material.GRAVEL
					|| event.getBlock().getType() == Material.CLAY) {
				if (event.getBlock().getType() == Material.SNOW) {

					stat = stat + plugin.Settings.ExpPerSnow;
					gained = plugin.Settings.ExpPerSnow;
				}
				if (event.getBlock().getType() == Material.SNOW_BLOCK) {

					stat = stat + plugin.Settings.ExpPerSnow;
					gained = plugin.Settings.ExpPerSnow;
				}
				if (event.getBlock().getType() == Material.CLAY) {

					stat = stat + plugin.Settings.ExpPerClay;
					gained = plugin.Settings.ExpPerClay;
				}
				if (event.getBlock().getType() == Material.DIRT) {

					stat = stat + plugin.Settings.ExpPerDirt;
					gained = plugin.Settings.ExpPerDirt;
				}
				if (event.getBlock().getType() == Material.GRASS) {
					stat = stat + plugin.Settings.ExpPerGrass;
					gained = plugin.Settings.ExpPerGrass;
				}
				if (event.getBlock().getType() == Material.SOIL) {

					stat = stat + plugin.Settings.ExpPerDirt;
					gained = plugin.Settings.ExpPerDirt;
				}
				if (event.getBlock().getType() == Material.SAND) {

					stat = stat + plugin.Settings.ExpPerSand;
					gained = plugin.Settings.ExpPerSand;
				}
				if (event.getBlock().getType() == Material.SOUL_SAND) {

					stat = stat + plugin.Settings.ExpPerSoulSand;
					gained = plugin.Settings.ExpPerSoulSand;
				}
				if (event.getBlock().getType() == Material.GRAVEL) {

					stat = stat + plugin.Settings.ExpPerGravel;
					gained = plugin.Settings.ExpPerGravel;
				}
				
				int aftlevel = 0;
				plugin.Level.update(player, "d", stat);
				aftlevel = plugin.Level.getLevel(player, "d");
				if (aftlevel > level) {
					player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
							+ ChatColor.valueOf(plugin.Settings.c3)
							+ "Level up! Your Digging level is now " + aftlevel);
				} else if (plugin.PlayerFunctions.enabled(player) == true) {
					player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
							+ ChatColor.valueOf(plugin.Settings.c3) + " You gained "+gained+" exp.");
				}
			}
		}
	}
}
