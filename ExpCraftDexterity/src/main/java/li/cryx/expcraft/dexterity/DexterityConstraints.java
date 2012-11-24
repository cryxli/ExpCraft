package li.cryx.expcraft.dexterity;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DexterityConstraints {

	private final Dexterity plugin;

	public DexterityConstraints(final Dexterity plugin) {
		this.plugin = plugin;
	}

	/**
	 * Checks whether the given player is wearing boots and meets the level
	 * requirements to do so. Will war the player if the level requirement is
	 * not met.
	 * 
	 * @param player
	 *            Current player
	 * @param level
	 *            Player's level
	 * @return <code>true</code>, if the player is wearing boots and meets their
	 *         level requirements.
	 */
	public boolean checkBoots(final Player player, final int level) {
		final ItemStack boots = player.getInventory().getBoots();
		Material material = null;
		if (boots != null) {
			material = boots.getType();
		}
		if (material == null) {
			return false;
		}

		switch (material) {
		case LEATHER_BOOTS:
			return checkBoots(player, level, "Leather");
		case CHAINMAIL_BOOTS:
			return checkBoots(player, level, "Chainmail");
		case IRON_BOOTS:
			return checkBoots(player, level, "Iron");
		case GOLD_BOOTS:
			return checkBoots(player, level, "Gold");
		case DIAMOND_BOOTS:
			return checkBoots(player, level, "Diamond");
		default:
			return false;
		}
	}

	/**
	 * Check whether player's level matched the requirements for the boots she
	 * is wearing. Will send a warning to the player, if not.
	 * 
	 * @param player
	 *            Current player.
	 * @param level
	 *            Player's level.
	 * @param config
	 *            String identifying the material of the boots.
	 * @return <code>true</code>, if the player has the level to wear the boots.
	 */
	private boolean checkBoots(final Player player, final int level,
			final String config) {
		int reqLevel = plugin.getConfig().getInteger("BootsLevel." + config);
		if (level < reqLevel) {
			plugin.warnBoots(player, config, reqLevel);
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Tests whether the given stack is boots.
	 * 
	 * @param item
	 *            An inventory item.
	 * @return <code>true</code>, if the item is a pair of boots of any
	 *         material.
	 */
	public boolean isBoots(final ItemStack item) {
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

}
