package me.samkio.lcdexterity;

import me.samkio.levelcraftcore.LevelFunctions;
import me.samkio.levelcraftcore.Whitelist;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class LCPlayerListener extends PlayerListener {
	public LCDexterity plugin;

	public LCPlayerListener(final LCDexterity instance) {
		plugin = instance;
	}

	private boolean isAiryBlock(final Material m) {
		return m == Material.AIR || //
				m == Material.WATER || //
				m == Material.STATIONARY_WATER || //
				m == Material.TORCH || //
				m == Material.LADDER;
	}

	@Override
	public void onPlayerJoin(final PlayerJoinEvent e) {
		Player p1 = e.getPlayer();

		for (Player p : plugin.levels.keySet()) {
			if (p == p1) {
				plugin.levels.remove(p1);
				break;
			}

		}

		plugin.levels.put(p1, LevelFunctions.getLevel(p1, plugin.thisPlug));

		return;
	}

	@Override
	public void onPlayerMove(final PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (!plugin.levels.containsKey(player)) {
			plugin.levels.put(player,
					LevelFunctions.getLevel(player, plugin.thisPlug));
		}
		if (!Whitelist.hasLevel(player, plugin.thisPlug)) {
			return;
		}
		if (event.getFrom().getBlockY() < event.getTo().getBlockY()) {
			LevelFunctions.addExp(player, plugin.thisPlug,
					plugin.LCConfiguration.ExpPerJump);

			Block block = player.getWorld().getBlockAt(
					player.getLocation().getBlockX(),
					player.getLocation().getBlockY() - 2,
					player.getLocation().getBlockZ());
			Material m = block.getType();
			if (isAiryBlock(m)) {
				return;
			}

			if (plugin.LCConfiguration.AllowJump && !player.isSneaking()) {
				double y = 0;
				int x = -2;
				int z = -2;
				int Level = plugin.levels.get(player);
				if (player.getInventory().getBoots().getType() == Material.GOLD_BOOTS
						&& Level >= plugin.LCConfiguration.GoldBoots) {
					y = 0.8 * plugin.LCConfiguration.jumpMultiplier;
				} else if (player.getInventory().getBoots().getType() == Material.DIAMOND_BOOTS
						&& Level >= plugin.LCConfiguration.DiamondBoots) {
					y = 1 * plugin.LCConfiguration.jumpMultiplier;
				}
				if (y > 0) {
					Vector vector = new Vector();
					vector.setX(0);
					vector.setY(y);
					vector.setZ(0);
					player.setVelocity(vector);
				}
			}
		}
	}

	@Deprecated
	public void running(final Player player) {
		if (!player.isSneaking() || !plugin.LCConfiguration.AllowRun) {
			return;
		}
		Block block = player.getWorld().getBlockAt(
				player.getLocation().getBlockX(),
				player.getLocation().getBlockY() - 1,
				player.getLocation().getBlockZ());
		Material m = block.getType();
		if (isAiryBlock(m)) {
			return;
		}

		int Level = plugin.levels.get(player);
		if (player.getInventory().getBoots().getType() == Material.LEATHER_BOOTS
				&& Level >= plugin.LCConfiguration.LeatherBoots) {
			Vector dir = player.getLocation().getDirection()
					.multiply(0.6 * plugin.LCConfiguration.runMultiplier);
			player.setVelocity(dir);
			return;
		} else if (player.getInventory().getBoots().getType() == Material.CHAINMAIL_BOOTS
				&& Level >= plugin.LCConfiguration.ChainBoots) {
			Vector dir = player.getLocation().getDirection()
					.multiply(0.7 * plugin.LCConfiguration.runMultiplier);
			player.setVelocity(dir);
			return;
		} else if (player.getInventory().getBoots().getType() == Material.IRON_BOOTS
				&& Level >= plugin.LCConfiguration.IronBoots) {
			Vector dir = player.getLocation().getDirection()
					.multiply(0.8 * plugin.LCConfiguration.runMultiplier);
			player.setVelocity(dir);
			return;
		} else if (player.getInventory().getBoots().getType() == Material.GOLD_BOOTS
				&& Level >= plugin.LCConfiguration.GoldBoots) {
			Vector dir = player.getLocation().getDirection()
					.multiply(0.9 * plugin.LCConfiguration.runMultiplier);
			player.setVelocity(dir);
			return;
		} else if (player.getInventory().getBoots().getType() == Material.DIAMOND_BOOTS
				&& Level >= plugin.LCConfiguration.DiamondBoots) {
			Vector dir = player.getLocation().getDirection()
					.multiply(1 * plugin.LCConfiguration.runMultiplier);
			player.setVelocity(dir);
			return;
		}
	}
}
