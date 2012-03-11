package li.cryx.expcraft.defence;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * This class observes damage dealt to players.
 * 
 * @author cryxli
 */
public class DefenceEntityListener implements Listener {

	/** Reference to plugin. */
	private final Defence plugin;

	/**
	 * Create a new listener for given plugin.
	 * 
	 * @param plugin
	 *            Reference to plugin
	 */
	public DefenceEntityListener(final Defence plugin) {
		this.plugin = plugin;
	}

	/**
	 * Check a players armor. If a worn armor piece does not meet the
	 * requirements, it will be moved into inventory or dropped.
	 * 
	 * @param player
	 *            Current player
	 * @return <code>true</code>, if the player still wears at least one armor
	 *         piece after the test
	 */
	private boolean checkArmor(final Player player) {
		int level = plugin.getPersistence().getLevel(plugin, player);
		PlayerInventory inv = player.getInventory();
		checkArmorPiece(player, level, inv.getBoots());
		checkArmorPiece(player, level, inv.getLeggings());
		checkArmorPiece(player, level, inv.getChestplate());
		checkArmorPiece(player, level, inv.getHelmet());
		return isWearingArmor(player);
	}

	/**
	 * Separates the check depending on armor type (boots, leggins, etc).
	 * 
	 * @param player
	 *            Current player
	 * @param level
	 *            Player's level
	 * @param armor
	 *            Armor piece
	 */
	private void checkArmorPiece(final Player player, final int level,
			final ItemStack armor) {
		if (armor == null) {
			return;
		}
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
		case PUMPKIN:
			// do not check pumpkins
		default:
			break;
		}
	}

	/**
	 * Test whether a player is allowed to wear his boots, or, drop them into
	 * inventory.
	 * 
	 * @param player
	 *            Current player
	 * @param level
	 *            Player's level
	 * @param config
	 *            String identifying a material (wood, iron, etc.)
	 */
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

	/**
	 * Test whether a player is allowed to wear his chestplate, or, drop them
	 * into inventory.
	 * 
	 * @param player
	 *            Current player
	 * @param level
	 *            Player's level
	 * @param config
	 *            String identifying a material (wood, iron, etc.)
	 */
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

	/**
	 * Test whether a player is allowed to wear his helmet, or, drop them into
	 * inventory.
	 * 
	 * @param player
	 *            Current player
	 * @param level
	 *            Player's level
	 * @param config
	 *            String identifying a material (wood, iron, etc.)
	 */
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

	/**
	 * Test whether a player is allowed to wear his leggings, or, drop them into
	 * inventory.
	 * 
	 * @param player
	 *            Current player
	 * @param level
	 *            Player's level
	 * @param config
	 *            String identifying a material (wood, iron, etc.)
	 */
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

	/**
	 * Test whether a player is wearing armor.
	 * 
	 * @param player
	 *            Current player
	 * @return <code>true</code>, if player is wearing at least one piece of
	 *         armor.
	 */
	private boolean isWearingArmor(final Player player) {
		PlayerInventory inv = player.getInventory();
		boolean wears = false;

		if (inv.getBoots() != null) {
			wears = isArmor(inv.getBoots().getType());
		}
		if (!wears && inv.getLeggings() != null) {
			wears = isArmor(inv.getLeggings().getType());
		}
		if (!wears && inv.getChestplate() != null) {
			wears = isArmor(inv.getChestplate().getType());

		}
		if (!wears && inv.getHelmet() != null) {
			wears = isArmor(inv.getHelmet().getType());
		}

		return wears;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDamage(final EntityDamageEvent event) {
		if (event.isCancelled()) {
			// event has been canceled
			return;
		}
		if (event.getCause() != DamageCause.ENTITY_ATTACK
				&& event.getCause() != DamageCause.PROJECTILE) {
			// not a damage type that indicates a fight
			return;
		}
		if (!(event.getEntity() instanceof Player)) {
			// target is not a player
			return;
		}

		Player player = (Player) event.getEntity();
		if (!plugin.getPermission().hasLevel(plugin, player)) {
			// plugin is not active for player
			return;
		}

		if (checkArmor(player)) {
			// get exp proportional to dealt damage (before armor bonus is
			// applied)
			plugin.getPersistence().addExp(
					plugin,
					player,
					event.getDamage()
							* plugin.getConfDouble("ExperienceMultiplier"));
		}
	}

}
