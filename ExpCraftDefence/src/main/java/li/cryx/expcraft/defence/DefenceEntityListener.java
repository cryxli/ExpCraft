package li.cryx.expcraft.defence;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * This class observes damage dealt to players.
 * 
 * @author cryxli
 */
public class DefenceEntityListener extends EntityListener {

	private final Defence plugin;

	public DefenceEntityListener(final Defence plugin) {
		this.plugin = plugin;
	}

	private boolean checkArmor(final Player player) {
		int level = plugin.getPersistence().getLevel(plugin, player);
		PlayerInventory inv = player.getInventory();
		checkArmorPiece(player, level, inv.getBoots());
		checkArmorPiece(player, level, inv.getLeggings());
		checkArmorPiece(player, level, inv.getChestplate());
		checkArmorPiece(player, level, inv.getHelmet());
		return isWearingArmor(player);
	}

	private void checkArmorPiece(final Player player, final int level,
			final ItemStack armor) {
		switch (armor.getType()) {
		case LEATHER_BOOTS:
			checkBoots(player, level, "Leather");
			break;
		case LEATHER_CHESTPLATE:
			checkChestplate(player, level, "Leather");
			break;
		case LEATHER_HELMET:
			checkHelmet(player, level, "Leather");
			break;
		case LEATHER_LEGGINGS:
			checkLeggings(player, level, "Leather");
			break;
		case CHAINMAIL_BOOTS:
			checkBoots(player, level, "Chainmail");
			break;
		case CHAINMAIL_CHESTPLATE:
			checkChestplate(player, level, "Chainmail");
			break;
		case CHAINMAIL_HELMET:
			checkHelmet(player, level, "Chainmai");
			break;
		case CHAINMAIL_LEGGINGS:
			checkLeggings(player, level, "Chainmai");
			break;
		case IRON_BOOTS:
			checkBoots(player, level, "Iron");
			break;
		case IRON_CHESTPLATE:
			checkChestplate(player, level, "Iron");
			break;
		case IRON_HELMET:
			checkHelmet(player, level, "Iron");
			break;
		case IRON_LEGGINGS:
			checkLeggings(player, level, "Iron");
			break;
		case GOLD_BOOTS:
			checkBoots(player, level, "Gold");
			break;
		case GOLD_CHESTPLATE:
			checkChestplate(player, level, "Gold");
			break;
		case GOLD_HELMET:
			checkHelmet(player, level, "Gold");
			break;
		case GOLD_LEGGINGS:
			checkLeggings(player, level, "Gold");
			break;
		case DIAMOND_BOOTS:
			checkBoots(player, level, "Diamond");
			break;
		case DIAMOND_CHESTPLATE:
			checkChestplate(player, level, "Diamond");
			break;
		case DIAMOND_HELMET:
			checkHelmet(player, level, "Diamond");
			break;
		case DIAMOND_LEGGINGS:
			checkLeggings(player, level, "Diamond");
			break;
		default:
			break;
		}
	}

	private void checkBoots(final Player player, final int level,
			final String config) {
		if (level < plugin.getConfInt("ArmorLevel." + config + "Boots")) {
			ItemStack item = player.getInventory().getBoots();
			ItemStack drop = new ItemStack(item.getType(), 1,
					item.getDurability());
			player.getInventory().setBoots(null);
			if (player.getInventory().firstEmpty() == -1) {
				player.getWorld().dropItem(player.getLocation(), drop);
			} else {
				player.getInventory().setItem(
						player.getInventory().firstEmpty(), drop);
			}
			plugin.notifyRequirements(player, config, "Boots");
		}
	}

	private void checkChestplate(final Player player, final int level,
			final String config) {
		if (level < plugin.getConfInt("ArmorLevel." + config + "Chestplate")) {
			ItemStack item = player.getInventory().getChestplate();
			ItemStack drop = new ItemStack(item.getType(), 1,
					item.getDurability());
			player.getInventory().setChestplate(null);
			if (player.getInventory().firstEmpty() == -1) {
				player.getWorld().dropItem(player.getLocation(), drop);
			} else {
				player.getInventory().setItem(
						player.getInventory().firstEmpty(), drop);
			}
			plugin.notifyRequirements(player, config, "Chestplate");
		}
	}

	private void checkHelmet(final Player player, final int level,
			final String config) {
		if (level < plugin.getConfInt("ArmorLevel." + config + "Helmet")) {
			ItemStack item = player.getInventory().getHelmet();
			ItemStack drop = new ItemStack(item.getType(), 1,
					item.getDurability());
			player.getInventory().setHelmet(null);
			if (player.getInventory().firstEmpty() == -1) {
				player.getWorld().dropItem(player.getLocation(), drop);
			} else {
				player.getInventory().setItem(
						player.getInventory().firstEmpty(), drop);
			}
			plugin.notifyRequirements(player, config, "Helmet");
		}
	}

	private void checkLeggings(final Player player, final int level,
			final String config) {
		if (level < plugin.getConfInt("ArmorLevel." + config + "Leggings")) {
			ItemStack item = player.getInventory().getLeggings();
			ItemStack drop = new ItemStack(item.getType(), 1,
					item.getDurability());
			player.getInventory().setLeggings(null);
			if (player.getInventory().firstEmpty() == -1) {
				player.getWorld().dropItem(player.getLocation(), drop);
			} else {
				player.getInventory().setItem(
						player.getInventory().firstEmpty(), drop);
			}
			plugin.notifyRequirements(player, config, "Leggings");
		}
	}

	/**
	 * Check the given material for an armor piece.
	 * 
	 * @param material
	 *            A block <code>Material</code>.
	 * @return <code>true</code>, if the given material is indicating armor.
	 *         Note: A pumpkin which can be worn as a helmet does not count as
	 *         armor.
	 */
	private boolean isArmor(final Material material) {
		switch (material) {
		// leather
		case LEATHER_BOOTS:
		case LEATHER_CHESTPLATE:
		case LEATHER_HELMET:
		case LEATHER_LEGGINGS:
			// chainmail
		case CHAINMAIL_BOOTS:
		case CHAINMAIL_CHESTPLATE:
		case CHAINMAIL_HELMET:
		case CHAINMAIL_LEGGINGS:
			// iron
		case IRON_BOOTS:
		case IRON_CHESTPLATE:
		case IRON_HELMET:
		case IRON_LEGGINGS:
			// gold
		case GOLD_BOOTS:
		case GOLD_CHESTPLATE:
		case GOLD_HELMET:
		case GOLD_LEGGINGS:
			// diamond
		case DIAMOND_BOOTS:
		case DIAMOND_CHESTPLATE:
		case DIAMOND_HELMET:
		case DIAMOND_LEGGINGS:
			return true;
		case AIR:
		default:
			return false;
		}
	}

	private boolean isWearingArmor(final Player player) {
		PlayerInventory inv = player.getInventory();
		return isArmor(inv.getBoots().getType()) || //
				isArmor(inv.getLeggings().getType()) || //
				isArmor(inv.getChestplate().getType()) || //
				isArmor(inv.getHelmet().getType());
	}

	@Override
	public void onEntityDamage(final EntityDamageEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (event.getCause() != DamageCause.ENTITY_ATTACK
				&& event.getCause() != DamageCause.PROJECTILE) {
			return;
		}

		if (!(event.getEntity() instanceof Player)) {
			return;
		}
		Player player = (Player) event.getEntity();

		System.out.println(event.getDamage() + " dmg on " + player + "("
				+ event.getCause() + ")");

		event.setCancelled(true);

		if (checkArmor(player)) {
			plugin.getPersistence().addExp(
					plugin,
					player,
					event.getDamage()
							* plugin.getConfDouble("ExperienceMultiplier"));
		}
	}

}
