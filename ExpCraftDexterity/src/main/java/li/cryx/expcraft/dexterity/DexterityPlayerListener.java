package li.cryx.expcraft.dexterity;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class DexterityPlayerListener implements Listener {

	private final Dexterity plugin;

	private static final double jumpHalfHigher = 0.4;

	private static final double jumpOneHigher = 0.48;

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
		if (!plugin.getPermission().hasLevel(plugin, player)) {
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
				// jump 2 blocks (unstable)
				Vector v = player.getVelocity();
				v.setY(jumpOneHigher);
				player.setVelocity(v);

			} else if (level >= plugin.getConfInt("LevelGain.AdvancedJump")) {
				// jump 1.5 blocks
				Vector v = player.getVelocity();
				v.setY(jumpHalfHigher);
				player.setVelocity(v);
			}
		}
	}

	// less fall dmg depending on exp levelCap ratio and boots material
	@EventHandler(priority = EventPriority.HIGHEST)
	public void playerDamageEvent(final EntityDamageEvent event) {
		if (event.isCancelled() || event.getCause() != DamageCause.FALL) {
			return;
		}
		// entity takes fall damage
		if (!(event.getEntity() instanceof Player)) {
			return;
		}
		Player player = (Player) event.getEntity();
		// player takes fall damage
		if (!plugin.getPermission().hasLevel(plugin, player)) {
			return;
		}
		int level = plugin.getPersistence().getLevel(plugin, player);

		if (!checkBoots(player, level)) {
			// wrong level for boots, no bonus
			return;
		}
		if (!isBoots(player.getInventory().getBoots())) {
			// only get bonus when wearing boots
			return;
		}

		// reduce damage
		int levelCap = plugin.getLevelCap();
		double dmg = 1.0 - 0.75 * level / levelCap;
		event.setDamage((int) Math.ceil(event.getDamage() * dmg));
	}
}
