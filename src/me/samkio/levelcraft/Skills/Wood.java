package me.samkio.levelcraft.Skills;

import me.samkio.levelcraft.Levelcraft;
import me.samkio.levelcraft.Functions.PlayerFunctions;
import me.samkio.levelcraft.SamToolbox.Level;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;


public class Wood {
	public static Levelcraft plugin;
	public static void Destroy(BlockBreakEvent event) {
		Player player = event.getPlayer();
		int iih = player.getItemInHand().getTypeId();

		if (plugin.Settings.enableWCLevel == true) {
			int level = 0;
			double woodstat = 0;
            double gained = 0;
			level = Level.getLevel(player, "w");
			if (level < plugin.Settings.WCIronAxe && iih == 258) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot use this tool. Required Level:"
						+ plugin.Settings.WCIronAxe);
				event.setCancelled(true);
			} else if (level < plugin.Settings.WCGoldAxe && iih == 286) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot use this tool. Required Level:"
						+ plugin.Settings.WCGoldAxe);
				event.setCancelled(true);
			} else if (level < plugin.Settings.WCDiamondAxe && iih == 279) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot use this tool. Required Level:"
						+ plugin.Settings.WCDiamondAxe);
				event.setCancelled(true);
			} else if (level < plugin.Settings.WCStoneAxe && iih == 275) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot use this tool. Required Level:"
						+ plugin.Settings.WCStoneAxe);
				event.setCancelled(true);
			} else if (level < plugin.Settings.WCWoodAxe && iih == 271) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot use this tool. Required Level:"
						+ plugin.Settings.WCWoodAxe);
				event.setCancelled(true);
			} else if (level < plugin.Settings.LogLevel
					&& event.getBlock().getType() == Material.LOG) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot mine this block. Required Level:"
						+ plugin.Settings.LogLevel);
				event.setCancelled(true);
			} else if (level < plugin.Settings.PlankLevel
					&& event.getBlock().getType() == Material.WOOD) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot mine this block. Required Level:"
						+ plugin.Settings.PlankLevel);
				event.setCancelled(true);
			} else if (event.getBlock().getType() == Material.LOG
					|| event.getBlock().getType() == Material.WOOD) {

				if (event.getBlock().getType() == Material.LOG) {
					woodstat = woodstat + plugin.Settings.ExpPerLog;
					gained = plugin.Settings.ExpPerLog;
				}
				if (event.getBlock().getType() == Material.WOOD) {
					woodstat = woodstat + plugin.Settings.ExpPerPlank;
					gained = plugin.Settings.ExpPerPlank;
				}
				int aftlevel = 0;
				Level.update(player, "w",woodstat);
				aftlevel = Level.getLevel(player, "w");
				if (aftlevel > level) {
					player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
							+ ChatColor.valueOf(plugin.Settings.c3)
							+ " Level up! Your Woodcut level is now "
							+ aftlevel);
				} else if (PlayerFunctions.enabled(player)) {
					player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
							+ ChatColor.valueOf(plugin.Settings.c3) + " You gained "+gained+" exp.");
				}
			}

		}
	}
}
