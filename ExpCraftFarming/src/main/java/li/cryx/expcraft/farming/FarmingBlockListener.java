package li.cryx.expcraft.farming;

import java.util.Random;

import org.bukkit.CropState;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Crops;

/**
 * This class implements a block listener that traces block placement and
 * destruction, and, initiates the according farming related actions.
 * 
 * @author cryxli
 */
public class FarmingBlockListener extends BlockListener {

	/** Reference to parent plugin. */
	public Farming plugin;

	/** A random generator to determine bonus drops. */
	private static Random randomGenerator = new Random();

	/** Create a new listener for the given plugin. */
	public FarmingBlockListener(final Farming instance) {
		this.plugin = instance;
	}

	/**
	 * Calculate additional drops when harvesting crops. This is based on user
	 * experience and a bit of randomness.
	 * 
	 * @param player
	 *            Current player.
	 * @param level
	 *            Player's current farming level.
	 * @param block
	 *            The block that was harvested. Additional drops will seam to
	 *            drop at the same location as the given block.
	 */
	private void bonusDropForCrops(final Player player, final int level,
			final Block block) {
		int rnd = randomGenerator.nextInt(100);
		if (rnd >= 0 && rnd < 2
				&& level >= plugin.getConfInt("DropLevel.MelonSeed")) {
			// drop melon seeds
			drop(block, Material.MELON_SEEDS);
		} else if (rnd >= 2 && rnd < 5
				&& level >= plugin.getConfInt("DropLevel.PumpkinSeed")) {
			// drop pumpkin seeds
			drop(block, Material.PUMPKIN_SEEDS);
		} else if (rnd >= 5 && rnd < 10
				&& level >= plugin.getConfInt("DropLevel.CocoaBean")) {
			// drop cocoa bean:
			// drop(block, Material.COCOA_BEAN);
			drop(block, Material.INK_SACK, (short) 3); // 351(3)
		}
	}

	/**
	 * Calculate additional drops when harvesting nether warts. This is based on
	 * user experience and a bit of randomness.
	 * 
	 * @param player
	 *            Current player.
	 * @param level
	 *            Player's current farming level.
	 * @param block
	 *            The block that was harvested. Additional drops will seam to
	 *            drop at the same location as the given block.
	 */
	private void bonusDropForNetherWart(final Player player, final int level,
			final Block block) {
		int rnd = randomGenerator.nextInt(100);
		if (rnd >= 0 && rnd < 2
				&& level >= plugin.getConfInt("DropLevel.GhastTear")) {
			drop(block, Material.GHAST_TEAR);

		} else if (rnd >= 2 && rnd < 4
				&& level >= plugin.getConfInt("DropLevel.BlazePowder")) {
			drop(block, Material.BLAZE_POWDER);
		}
	}

	/**
	 * Drop one item of the given material at the given block's position.
	 * 
	 * @param block
	 *            A block indicating the position to drop the item.
	 * @param material
	 *            The item to drop. Only one unit of that item is dropped at a
	 *            time. If the material uses the damage value to define other
	 *            variation of that material, a damage value of <code>0</code>
	 *            is used.
	 * @see FarmingBlockListener#drop(Block, Material, short)
	 */
	private void drop(final Block block, final Material material) {
		Location locy = new Location(block.getWorld(), block.getX(),
				block.getY(), block.getZ(), 0, 0);
		block.getWorld().dropItem(locy, new ItemStack(material.getId(), 1));
	}

	/**
	 * Drop one item of the given material and damage value at the given block's
	 * position. Damage values are used to indicate, e.g., the color of a piece
	 * of wool.
	 * <p>
	 * Example <code>drop(someBlock, Material.INK_SAC, 3)</code> will drop a
	 * cocoa bean. <code>INK_SAC</code> is a group item that indicates wool dye.
	 * The damage level of <code>3</code> tells bukkit to create a brown
	 * version. And brown dye is a cocoa bean.
	 * </p>
	 * 
	 * @param block
	 *            A block indicating the position to drop the item.
	 * @param material
	 *            The item to drop. Only one unit of that item is dropped at a
	 *            time.
	 */
	private void drop(final Block block, final Material material,
			final short damage) {
		Location locy = new Location(block.getWorld(), block.getX(),
				block.getY(), block.getZ(), 0, 0);
		block.getWorld().dropItem(locy,
				new ItemStack(material.getId(), 1, damage));
	}

	/**
	 * Test whether the given block represents fully grown, ripe crops.
	 * 
	 * @param block
	 *            A block participating in an event.
	 * @return <code>true</code>, only if the block represents crops in state
	 *         ripe.
	 */
	private boolean isRipeCrops(final Block block) {
		if (block.getType() == Material.CROPS) {
			return ((Crops) block.getState().getData()).getState() == CropState.RIPE;
		} else {
			return false;
		}
	}

	/**
	 * Test whether the given block represents fully grown, ripe nether wart.
	 * 
	 * @param block
	 *            A block participating in an event.
	 * @return <code>true</code>, only if the block represents nether wart in
	 *         state ripe.
	 */
	private boolean isRipeNetherWart(final Block block) {
		// TODO cryxli: implement with bukkit for 1.0
		return false;
	}

	// A block has been destroyed
	@Override
	public void onBlockBreak(final BlockBreakEvent event) {
		if (event.isCancelled()
				|| !plugin.getPermission().worldCheck(
						event.getBlock().getWorld())) {
			// event is already consumed
			// or, the world is not managed by the ExpCraft
			return;
		}

		Player player = event.getPlayer();
		if (!plugin.getPermission().hasLevel(plugin, player)) {
			// player does not have permission to us this module
			return;
		}

		Material itemInHand = player.getItemInHand().getType();
		Block block = event.getBlock();
		Material m = block.getType();
		int level = plugin.getPersistence().getLevel(plugin, player);

		if (!plugin.checkTool(player, itemInHand, level)) {
			// player is not allowed to use the tool he's holding
			event.setCancelled(true);
			return;
		}

		if (m == Material.LEAVES) {
			// player has broken a leaf
			int rnd = randomGenerator.nextInt(200);
			if (rnd == 0 && level >= plugin.getConfInt("UseLevel.GoldenApple")) {
				// 5%o
				plugin.getPersistence().addExp(plugin, player,
						plugin.getConfDouble("ExpGain.GoldenApple"));
				drop(block, Material.GOLDEN_APPLE);

			} else if (rnd > 0 && rnd < 10
					&& level >= plugin.getConfInt("UseLevel.Apple")) {
				// 50%o
				plugin.getPersistence().addExp(plugin, player,
						plugin.getConfDouble("ExpGain.Apple"));
				drop(block, Material.APPLE);
			}
		}

		double gained = 0;
		if (m == Material.SUGAR_CANE_BLOCK
				&& level >= plugin.getConfInt("UseLevel.SugarCane")) {
			// harvesting sugar cane
			gained = plugin.getConfDouble("ExpGain.SugarCane");

		} else if (isRipeCrops(event.getBlock())
				&& level >= plugin.getConfInt("UseLevel.Harvest")) {
			// harvesting crops
			gained = plugin.getConfDouble("ExpGain.Harvest");
			bonusDropForCrops(player, level, block);

		} else if (isRipeNetherWart(event.getBlock())
				&& level >= plugin.getConfInt("UseLevel.NetherWart")) {
			// harvesting nether wart
			gained = plugin.getConfDouble("ExpGain.NetherWart");
			bonusDropForNetherWart(player, level, block);

		} else if (m == Material.CACTUS
				&& level >= plugin.getConfInt("UseLevel.Cacti")) {
			// harvesting cacti
			gained = plugin.getConfDouble("ExpGain.Cacti");
		}

		plugin.getPersistence().addExp(plugin, player, gained);
	}

	@Override
	public void onBlockPlace(final BlockPlaceEvent event) {
		if (event.isCancelled()
				|| !plugin.getPermission().worldCheck(
						event.getBlock().getWorld())) {
			// event is already consumed
			// or, the world is not managed by the ExpCraft
			return;
		}

		Player player = event.getPlayer();
		if (!plugin.getPermission().hasLevel(plugin, player)) {
			// player does not have permission to us this module
			return;
		}

		Material m = event.getBlock().getType();
		int level = plugin.getPersistence().getLevel(plugin, player);
		double gained = 0;

		switch (m) {
		case SAPLING: // planting a tree
			if (level >= plugin.getConfInt("UseLevel.Sapling")) {
				gained = plugin.getConfDouble("ExpGain.Sapling");
			}
			break;
		case RED_ROSE: // planting a rose
			if (level >= plugin.getConfInt("UseLevel.RedRose")) {
				gained = plugin.getConfDouble("ExpGain.RedRose");
			}
			break;
		case YELLOW_FLOWER: // planting a dandelion
			if (level >= plugin.getConfInt("UseLevel.YellowFlower")) {
				gained = plugin.getConfDouble("ExpGain.YellowFlower");
			}
			break;
		case BROWN_MUSHROOM:
		case RED_MUSHROOM: // planting a mushroom
			if (level >= plugin.getConfInt("UseLevel.Mushroom")) {
				gained = plugin.getConfDouble("ExpGain.Mushroom");
			}
			break;
		default:
			// placing a block not covered by this module
			return;
		}
		plugin.getPersistence().addExp(plugin, player, gained);
	}

}
