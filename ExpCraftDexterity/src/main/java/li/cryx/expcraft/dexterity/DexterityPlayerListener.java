package li.cryx.expcraft.dexterity;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public class DexterityPlayerListener implements Listener {

	private final Dexterity plugin;

	public DexterityPlayerListener(final Dexterity plugin) {
		this.plugin = plugin;
	}

	private boolean checkBoots(final Player player, final int level) {
		final ItemStack boots = player.getInventory().getBoots();
		Material material = null;
		if (boots != null) {
			material = boots.getType();
		}
		if (material == Material.LEATHER_BOOTS
				&& level < plugin.getConfInt("BootsLevel.Leather")) {
			plugin.warnBoots(player);
		} else if (material == Material.CHAINMAIL_BOOTS
				&& level < plugin.getConfInt("BootsLevel.Chainmail")) {
			plugin.warnBoots(player);
		} else if (material == Material.IRON_BOOTS
				&& level < plugin.getConfInt("BootsLevel.Iron")) {
			plugin.warnBoots(player);
		} else if (material == Material.GOLD_BOOTS
				&& level < plugin.getConfInt("BootsLevel.Gold")) {
			plugin.warnBoots(player);
		} else if (material == Material.DIAMOND_BOOTS
				&& level < plugin.getConfInt("BootsLevel.Diamond")) {
			plugin.warnBoots(player);
		} else {
			return true;
		}
		return false;
	}

	// private boolean isBoots(final Material material) {
	private boolean isBoots(final ItemStack item) {
		if (item == null) {
			return false;
		} else {
			final Material material = item.getType();
			return material == Material.LEATHER_BOOTS || //
					material == Material.CHAINMAIL_BOOTS || //
					material == Material.IRON_BOOTS || //
					material == Material.GOLD_BOOTS || //
					material == Material.DIAMOND_BOOTS;
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerMove(final PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (!plugin.getPermission().hasModule(plugin, player)) {
			return;
		}

		int level = plugin.getPersistence().getLevel(plugin, player);
		if (event.getFrom().getBlockY() < event.getTo().getBlockY()
				&& event.getFrom().getBlock().getType() == Material.AIR) {
			// exclude stairs, steps, water, lava...

			// player is jumping, add exp
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfDouble("ExpGain.Jump"));

			if (!checkBoots(player, level)) {
				// wrong level for boots, no bonus
				return;
			}
			if (!isBoots(player.getInventory().getBoots())) {
				// only get bonus when wearing boots
				return;
			}

			// jump higher
			if (level >= plugin.getConfInt("LevelGain.SuperJump")) {
				// // jump 2 blocks (unstable)
				Location loc = event.getTo().clone();
				loc.setY(loc.getY() + 1);
				player.teleport(loc);
			} else if (level >= plugin.getConfInt("LevelGain.AdvancedJump")) {
				// jump 1.5 blocks
				Location loc = event.getTo().clone();
				loc.setY(loc.getY() + 0.5);
				player.teleport(loc);
			}
		}
	}
}
