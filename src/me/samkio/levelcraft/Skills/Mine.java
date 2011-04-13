package me.samkio.levelcraft.Skills;

import me.samkio.levelcraft.Levelcraft;
import me.samkio.levelcraft.Functions.PlayerFunctions;
import me.samkio.levelcraft.SamToolbox.Level;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class Mine {
	public static Levelcraft plugin;
	public static void Place(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();
		if(block.getType() == Material.IRON_ORE || block.getType() == Material.GOLD_ORE){
		double stat = Level.getExp(player, "m");
		if(block.getType() == Material.IRON_ORE){
			stat = stat - plugin.Settings.ExpPerIronOre;
			Level.update(player, "m", stat);
			player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
					+ " Possible Attempt to boost. Lost "+plugin.Settings.ExpPerIronOre+" exp.");
		}else if(block.getType() == Material.GOLD_ORE){
			stat = stat - plugin.Settings.ExpPerGoldOre;
			Level.update(player, "m", stat);
			player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
					+ " Possible Attempt to boost. Lost "+plugin.Settings.ExpPerGoldOre+" exp.");
		}
		}
		
	}
	public static void Destroy(BlockBreakEvent event) {
		Player player = event.getPlayer();
		int iih = player.getItemInHand().getTypeId();
		PlayerFunctions.checkAccount(player);
		if (plugin.Settings.enableMineLevel == true) {
			int level = 0;
			double stat = 0;
            double gained = 0;
			level = Level.getLevel(player, "m");
			stat = Level.getExp(player, "m");

			if (level < plugin.Settings.MIIronPick && iih == 257) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot use this tool. Required Level:"
						+ plugin.Settings.MIIronPick);
				event.setCancelled(true);
			} else if (level < plugin.Settings.MIGoldPick && iih == 285) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot use this tool. Required Level:"
						+ plugin.Settings.MIGoldPick);
				event.setCancelled(true);
			} else if (level < plugin.Settings.MIDiamondPick && iih == 278) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot use this tool. Required Level:"
						+ plugin.Settings.MIDiamondPick);
				event.setCancelled(true);
			} else if (level < plugin.Settings.MIStonePick && iih == 274) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot use this tool. Required Level:"
						+ plugin.Settings.MIStonePick);
				event.setCancelled(true);
			} else if (level < plugin.Settings.MIWoodPick && iih == 270) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot use this tool. Required Level:"
						+ plugin.Settings.MIWoodPick);
				event.setCancelled(true);
			} else if (level < plugin.Settings.StoneLevel
					&& event.getBlock().getType() == Material.STONE) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot mine this block. Required Level:"
						+ plugin.Settings.StoneLevel);
				event.setCancelled(true);
			} else if (level < plugin.Settings.CobbleLevel
					&& event.getBlock().getType() == Material.COBBLESTONE) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot mine this block. Required Level:"
						+ plugin.Settings.CobbleLevel);
				event.setCancelled(true);
			} else if (level < plugin.Settings.RedLevel
					&& event.getBlock().getType() == Material.REDSTONE_ORE) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot mine this block. Required Level:"
						+ plugin.Settings.RedLevel);

				event.setCancelled(true);
			} else if (level < plugin.Settings.IronLevel
					&& event.getBlock().getType() == Material.IRON_ORE) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot mine this block. Required Level:"
						+ plugin.Settings.IronLevel);
				event.setCancelled(true);
			} else if (level < plugin.Settings.GoldLevel
					&& event.getBlock().getType() == Material.GOLD_ORE) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot mine this block. Required Level:"
						+ plugin.Settings.GoldLevel);
				event.setCancelled(true);
			} else if (level < plugin.Settings.DiamondLevel
					&& event.getBlock().getType() == Material.DIAMOND_ORE) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot mine this block. Required Level:"
						+ plugin.Settings.DiamondLevel);
				event.setCancelled(true);
			} else if (level < plugin.Settings.SandStoneLevel
					&& event.getBlock().getType() == Material.SANDSTONE) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot mine this block. Required Level:"
						+ plugin.Settings.SandStoneLevel);
				event.setCancelled(true);
			} else if (level < plugin.Settings.CoalLevel
					&& event.getBlock().getType() == Material.COAL_ORE) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot mine this block. Required Level:"
						+ plugin.Settings.CoalLevel);
				event.setCancelled(true);
			} else if (level < plugin.Settings.MossLevel
					&& event.getBlock().getType() == Material.MOSSY_COBBLESTONE) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot mine this block. Required Level:"
						+ plugin.Settings.MossLevel);
				event.setCancelled(true);
			} else if (level < plugin.Settings.LapisLevel
					&& event.getBlock().getType() == Material.LAPIS_ORE) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot mine this block. Required Level:"
						+ plugin.Settings.LapisLevel);
				event.setCancelled(true);
			} else if (level < plugin.Settings.NetherLevel
					&& event.getBlock().getType() == Material.OBSIDIAN) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot mine this block. Required Level:"
						+ plugin.Settings.NetherLevel);
				event.setCancelled(true);
			} else if (level < plugin.Settings.ObsidianLevel
					&& event.getBlock().getType() == Material.OBSIDIAN) {
				player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]" + ChatColor.valueOf(plugin.Settings.c4)
						+ " Cannot mine this block. Required Level:"
						+ plugin.Settings.ObsidianLevel);
				event.setCancelled(true);

			} else if (event.getBlock().getType() == Material.STONE
					|| event.getBlock().getType() == Material.COBBLESTONE
					|| event.getBlock().getType() == Material.REDSTONE_ORE
					|| event.getBlock().getType() == Material.GLOWING_REDSTONE_ORE
					|| event.getBlock().getType() == Material.IRON_ORE
					|| event.getBlock().getType() == Material.OBSIDIAN
					|| event.getBlock().getType() == Material.GOLD_ORE
					|| event.getBlock().getType() == Material.COAL_ORE
					|| event.getBlock().getType() == Material.MOSSY_COBBLESTONE
					|| event.getBlock().getType() == Material.LAPIS_ORE
					|| event.getBlock().getType() == Material.NETHERRACK
					|| event.getBlock().getType() == Material.DIAMOND_ORE
					|| event.getBlock().getType() == Material.SANDSTONE) {
				if (event.getBlock().getType() == Material.STONE) {

					stat = stat + plugin.Settings.ExpPerStone;
					gained = plugin.Settings.ExpPerStone;
				}
				if (event.getBlock().getType() == Material.OBSIDIAN) {

					stat = stat + plugin.Settings.ExpPerObsidian;
					gained = plugin.Settings.ExpPerObsidian;
				}
				if (event.getBlock().getType() == Material.MOSSY_COBBLESTONE) {

					stat = stat + plugin.Settings.ExpPerMossstone;
					gained = plugin.Settings.ExpPerMossstone;
				}
				if (event.getBlock().getType() == Material.COBBLESTONE) {
					stat = stat + plugin.Settings.ExpPerCobble;
					gained = plugin.Settings.ExpPerCobble;
				}
				if (event.getBlock().getType() == Material.GOLD_ORE) {

					stat = stat + plugin.Settings.ExpPerGoldOre;
					gained = plugin.Settings.ExpPerGoldOre;
				}
				if (event.getBlock().getType() == Material.IRON_ORE) {

					stat = stat + plugin.Settings.ExpPerIronOre;
					gained = plugin.Settings.ExpPerIronOre;
				}
				if (event.getBlock().getType() == Material.LAPIS_ORE) {

					stat = stat + plugin.Settings.ExpPerLapisOre;
					gained = plugin.Settings.ExpPerLapisOre;
				}
				if (event.getBlock().getType() == Material.COAL_ORE) {

					stat = stat + plugin.Settings.ExpPerCoalOre;
					gained = plugin.Settings.ExpPerCoalOre;
				}
		
				if (event.getBlock().getType() == Material.REDSTONE_ORE) {

					stat = stat + plugin.Settings.ExpPerRedstone;
					gained = plugin.Settings.ExpPerRedstone;
				}
				if (event.getBlock().getType() == Material.GLOWING_REDSTONE_ORE) {

					stat = stat + plugin.Settings.ExpPerRedstone;
					gained = plugin.Settings.ExpPerRedstone;
				}
				if (event.getBlock().getType() == Material.NETHERRACK) {

					stat = stat + plugin.Settings.ExpPerNetherrack;
					gained = plugin.Settings.ExpPerNetherrack;
				}
				if (event.getBlock().getType() == Material.DIAMOND_ORE) {

					stat = stat + plugin.Settings.ExpPerDiamondOre;
					gained = plugin.Settings.ExpPerDiamondOre;
				}
				if (event.getBlock().getType() == Material.SANDSTONE) {

					stat = stat + plugin.Settings.ExpPerSandStone;
					gained = plugin.Settings.ExpPerSandStone;
				}
				int aftlevel = 0;
				Level.update(player, "m", stat);
				aftlevel = Level.getLevel(player, "m");
				if (aftlevel > level) {
					player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
							+ ChatColor.valueOf(plugin.Settings.c3)
							+ " Level up! Your Mining level is now " + aftlevel);
				} else if (PlayerFunctions.enabled(player) == true) {
					player.sendMessage(ChatColor.valueOf(plugin.Settings.c1) + "[LC]"
							+ ChatColor.valueOf(plugin.Settings.c3) + " You gained "+gained+" exp.");
				}
			}
		}
	}
}
