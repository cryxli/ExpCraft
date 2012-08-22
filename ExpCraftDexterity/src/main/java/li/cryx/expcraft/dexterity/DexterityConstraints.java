package li.cryx.expcraft.dexterity;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DexterityConstraints {

	private final Dexterity plugin;

	public DexterityConstraints(final Dexterity plugin) {
		this.plugin = plugin;
	}

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
	 * 
	 * @param player
	 * @param level
	 * @param config
	 * @return
	 */
	private boolean checkBoots(final Player player, final int level,
			final String config) {
		if (level < plugin.getConfInt("BootsLevel." + config)) {
			plugin.warnBoots(player);
			return false;
		} else {
			return true;
		}
	}

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
