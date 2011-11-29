package li.cryx.expcraft.scavenger;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.inventory.ItemStack;

/**
 * This class implements a block listener that traces block destruction, and,
 * initiates the according savenging related actions.
 * 
 * @author cryxli
 */
public class ScavengerBlockListener extends BlockListener {

	/** A random generator to determine drops. */
	private static final Random randomGenerator = new Random();

	/** Reference to parent plugin. */
	private final Scavenger plugin;

	/** Create a new listener for the given plugin. */
	public ScavengerBlockListener(final Scavenger instance) {
		plugin = instance;
	}

	private void dropItem(final Block block, final Material material) {
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
	 *            The block that was destroyed
	 * @param rnd
	 *            A random number
	 */
	private void dropNether(final Player player, final int level,
			final Block block, final int rnd) {
		if (rnd <= 3 && level >= plugin.getConfInt("DropLevel.GhastTear")) {
			// 4 %o - 4%
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfDouble("ExpGain.GhastTear"));
			// TODO cryxli: enable with bukkit for 1.0
			// dropItem(block, Material.GHAST_TEAR);
			plugin.sendHint(player, "A Ghast must have been sad, here.");

		} else if (rnd > 3 && rnd <= 6
				&& level >= plugin.getConfInt("DropLevel.Nugget")) {
			// 3 %o - 3%
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfDouble("ExpGain.Nugget"));
			// TODO cryxli: enable with bukkit for 1.0
			// dropItem(block, Material.NUGGET);

		} else if (rnd > 6 && rnd <= 8
				&& level >= plugin.getConfInt("DropLevel.BlazePowder")) {
			// 2 %o - 2%
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfDouble("ExpGain.BlazePowder"));
			// TODO cryxli: enable with bukkit for 1.0
			// dropItem(block, Material.BLAZE_POWDER);

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
	private void dropNormal(final Player player, final int level,
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

		} else if (rnd == 5 && level >= plugin.getConfInt("DropLevel.Ingot")) {
			// 1 %o - 1%
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfDouble("ExpGain.Ingot"));
			dropItem(block, Material.GOLD_INGOT);
			plugin.sendHint(player,
					"Hey, do you see that shiny thing in the sand?.");

		} else if (rnd > 5 && rnd <= 7
				&& level >= plugin.getConfInt("DropLevel.Bowl")) {
			// 3 %O -3%
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfDouble("ExpGain.Bowl"));
			dropItem(block, Material.BOWL);

		} else if (rnd > 7 && rnd <= 9
				&& level >= plugin.getConfInt("DropLevel.Boots")) {
			// 2 %o - 2% (ori: 3 %o - 3%)
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfDouble("ExpGain.Boots"));
			dropItem(block, Material.LEATHER_BOOTS);

		} else if (rnd > 9 && rnd <= 11
				&& level >= plugin.getConfInt("DropLevel.Helmet")) {
			// 2 %o - 2% (ori: 0)
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfDouble("ExpGain.Helmet"));
			dropItem(block, Material.LEATHER_HELMET);

		} else if (rnd > 11 && rnd <= 14
				&& level >= plugin.getConfInt("DropLevel.Bucket")) {
			// 3 %o - 3% (ori: 4 %o - 4%)
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfDouble("ExpGain.Bucket"));
			dropItem(block, Material.BUCKET);

		} else if (rnd > 14 && rnd <= 16
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
	private int getRange(final int level) {
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
	private boolean isDiggable(final Material material) {
		return material == Material.DIRT || //
				material == Material.GRASS || //
				material == Material.SAND || //
				material == Material.GRAVEL || //
				material == Material.SOUL_SAND;
	}

	// A block has been destroyed
	@Override
	public void onBlockBreak(final BlockBreakEvent event) {
		if (event.isCancelled()
				|| !plugin.getPermission().worldCheck(
						event.getBlock().getWorld())) {
			return;
			// event is already consumed
			// or, the world is not managed by the ExpCraft
		}

		Player player = event.getPlayer();
		if (!plugin.getPermission().hasLevel(plugin, player)) {
			// player does not have permission to us this module
			return;
		}

		Block block = event.getBlock();
		Material m = block.getType();
		if (!isDiggable(m)) {
			// block is not one for scavenging
			return;
		}

		int level = plugin.getPersistence().getLevel(plugin, player);
		int random = randomGenerator.nextInt(getRange(level));

		switch (player.getWorld().getEnvironment()) {
		case NETHER:
			dropNether(player, level, block, random);
			break;
		case SKYLANDS: // TODO cryxli: is this the END ???
		case NORMAL:
		default:
			dropNormal(player, level, block, random);
		}
	}
}
