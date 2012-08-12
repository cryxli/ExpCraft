package li.cryx.expcraft.scavenger;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Progam logic used by {@link ScavengerBlockListener}.
 * 
 * @author cryxli
 */
public class ScavengerConstraints {

	private final Scavenger plugin;

	public ScavengerConstraints(final Scavenger plugin) {
		this.plugin = plugin;
	}

	/**
	 * Drop the given material at the location of the block.
	 * 
	 * @param block
	 *            Location reference in the world.
	 * @param material
	 *            Item to drop.
	 */
	public void dropItem(final Block block, final Material material) {
		Location locy = new Location(block.getWorld(), block.getX(),
				block.getY(), block.getZ(), 0.0f, 0.0f);
		block.getWorld().dropItem(locy, new ItemStack(material, 1));
	}

	/**
	 * Calculate drops for the Nether.
	 * 
	 * @param player
	 *            Current player
	 * @param level
	 *            Player's level
	 * @param block
	 *            The block that was destroyed for location and world reference
	 * @param rnd
	 *            A random number
	 */
	public void dropNether(final Player player, final int level,
			final Block block, final int rnd) {
		if (rnd <= 3 && level >= plugin.getConfInt("DropLevel.GhastTear")) {
			// 4 %o - 4%
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfDouble("ExpGain.GhastTear"));
			dropItem(block, Material.GHAST_TEAR);
			plugin.sendHint(player, "A Ghast must have been sad, here.");

		} else if (rnd > 3 && rnd <= 6
				&& level >= plugin.getConfInt("DropLevel.Nugget")) {
			// 3 %o - 3%
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfDouble("ExpGain.Nugget"));
			dropItem(block, Material.GOLD_NUGGET);

		} else if (rnd > 6 && rnd <= 8
				&& level >= plugin.getConfInt("DropLevel.BlazePowder")) {
			// 2 %o - 2%
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfDouble("ExpGain.BlazePowder"));
			dropItem(block, Material.BLAZE_POWDER);

		}
	}

	/**
	 * Calculate drops for the normal world.
	 * 
	 * @param player
	 *            Current player
	 * @param level
	 *            Player's level
	 * @param block
	 *            The block that was destroyed
	 * @param rnd
	 *            A random number
	 */
	public void dropNormal(final Player player, final int level,
			final Block block, final int rnd) {
		if (rnd == 0 && level >= plugin.getConfInt("DropLevel.Chestplate")) {
			// 1 %o - 1% (old: 0)
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfDouble("ExpGain.Chestplate"));
			dropItem(block, Material.LEATHER_CHESTPLATE);

		} else if (rnd > 0 && rnd <= 5
				&& level >= plugin.getConfInt("DropLevel.Saddle")) {
			// 5 %o - 5%
			dropItem(block, Material.SADDLE);
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfDouble("ExpGain.Saddle"));

		} else if (rnd == 6 && level >= plugin.getConfInt("DropLevel.Ingot")) {
			// 1 %o - 1%
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfDouble("ExpGain.Ingot"));
			dropItem(block, Material.GOLD_INGOT);
			plugin.sendHint(player,
					"Hey, do you see that shiny thing in the sand?.");

		} else if (rnd > 6 && rnd <= 8
				&& level >= plugin.getConfInt("DropLevel.Bowl")) {
			// 3 %O -3%
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfDouble("ExpGain.Bowl"));
			dropItem(block, Material.BOWL);

		} else if (rnd > 8 && rnd <= 10
				&& level >= plugin.getConfInt("DropLevel.Boots")) {
			// 2 %o - 2% (ori: 3 %o - 3%)
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfDouble("ExpGain.Boots"));
			dropItem(block, Material.LEATHER_BOOTS);

		} else if (rnd > 10 && rnd <= 12
				&& level >= plugin.getConfInt("DropLevel.Helmet")) {
			// 2 %o - 2% (ori: 0)
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfDouble("ExpGain.Helmet"));
			dropItem(block, Material.LEATHER_HELMET);

		} else if (rnd > 12 && rnd <= 15
				&& level >= plugin.getConfInt("DropLevel.Bucket")) {
			// 3 %o - 3% (ori: 4 %o - 4%)
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfDouble("ExpGain.Bucket"));
			dropItem(block, Material.BUCKET);

		} else if (rnd > 15 && rnd <= 17
				&& level >= plugin.getConfInt("DropLevel.Leggins")) {
			// 2 %o - 2% (ori: 0)
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfDouble("ExpGain.Leggins"));
			dropItem(block, Material.LEATHER_LEGGINGS);

		}
	}

	/**
	 * Depending on the player's level, he may have a higher chance to find
	 * items.
	 * 
	 * @param level
	 *            Current player's level.
	 * @return
	 */
	public int getRange(final int level) {
		int levelCap = plugin.getLevelCap();
		int i = 1000;
		if (level >= 0.05 * levelCap && level < 0.1 * levelCap) {
			i = 900;
		} else if (level >= 0.1 * levelCap && level < 0.2 * levelCap) {
			i = 850; // 600;
		} else if (level >= 0.2 * levelCap && level < 0.4 * levelCap) {
			i = 650; // 500;
		} else if (level >= 0.4 * levelCap && level < 0.7 * levelCap) {
			i = 400; // 300;
		} else if (level >= 0.7 * levelCap && level < 0.9 * levelCap) {
			i = 200; // 150;
		} else if (level >= 0.9 * levelCap) {
			i = 100;
		}
		return i;
	}

	/**
	 * Test whether the block destroyed was material were scavenging could
	 * happen.
	 * 
	 * @param material
	 *            The destroyed block
	 * @return <code>true</code>, if material is dirt, grass, sand, gravel or
	 *         soulsand.
	 */
	public boolean isDiggable(final Material material) {
		switch (material) {
		case DIRT:
		case GRASS:
		case SOUL_SAND:
		case GRAVEL:
		case CLAY:
		case SAND:
		case MYCEL:
			return true;

			// case SNOW:
		default:
			return false;
		}
	}

}
