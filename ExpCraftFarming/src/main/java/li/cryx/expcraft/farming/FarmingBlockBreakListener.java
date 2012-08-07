package li.cryx.expcraft.farming;

import java.util.Random;

import org.bukkit.CropState;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.CocoaPlant;
import org.bukkit.material.CocoaPlant.CocoaPlantSize;
import org.bukkit.material.Crops;

/**
 * This class implements a block listener that traces block destruction, and,
 * initiates the according farming related actions.
 * 
 * @author cryxli
 */
public class FarmingBlockBreakListener implements Listener {

	/** Reference to parent plugin. */
	public Farming plugin;

	/** A random generator to determine bonus drops. */
	private static Random randomGenerator = new Random();

	/** Create a new listener for the given plugin. */
	public FarmingBlockBreakListener(final Farming instance) {
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
	 * Test the blocks around the given one.
	 * 
	 * @param center
	 *            The block in the center.
	 * @param aroundType
	 *            Material of the blocks around the center block.
	 * @return <code>true</code>, if at least one of the blocks around is of the
	 *         given material.
	 */
	private boolean checkAround(final Block center, final Material aroundType) {
		Block stem = center.getRelative(BlockFace.SOUTH);
		if (stem.getType() == aroundType) {
			return true;
		}
		stem = center.getRelative(BlockFace.NORTH);
		if (stem.getType() == aroundType) {
			return true;
		}
		stem = center.getRelative(BlockFace.WEST);
		if (stem.getType() == aroundType) {
			return true;
		}
		stem = center.getRelative(BlockFace.EAST);
		if (stem.getType() == aroundType) {
			return true;
		}
		return false;
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
	 * @see FarmingBlockBreakListener#drop(Block, Material, short)
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
	 * Test whether the given block is a melon next to a stem.
	 * 
	 * @param block
	 *            The block to test
	 * @return <code>true</code>, if the harvested block is a grown melon
	 */
	private boolean isMelon(final Block block) {
		if (block.getType() != Material.MELON_BLOCK) {
			return false;
		} else {
			return checkAround(block, Material.MELON_STEM);
		}
	}

	/**
	 * Test whether the given block is a pumpkin next to a stem.
	 * 
	 * @param block
	 *            The block to test
	 * @return <code>true</code>, if the harvested block is a grown pumpkin
	 */
	private boolean isPumpkin(final Block block) {
		if (block.getType() != Material.PUMPKIN) {
			return false;
		} else {
			return checkAround(block, Material.PUMPKIN_STEM);
		}
	}

	/**
	 * Test whether the given block represents fully grown cocoa bean.
	 * 
	 * @param block
	 *            A block participating in an event.
	 * @return <code>true</code>, only if the block represents a cocoa plant of
	 *         size large.
	 */
	private boolean isRipeCocoaBean(final Block block) {
		if (block.getType() == Material.COCOA) {
			return ((CocoaPlant) block.getState().getData()).getSize() == CocoaPlantSize.LARGE;
		} else {
			return false;
		}
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
		// TODO cryxli: implement the proper way, once bukkit is ready
		if (block.getType() == Material.NETHER_WARTS) {
			return block.getData() == NetherWartState.RIPE.getData();
		} else {
			return false;
		}
	}

	// A block has been destroyed
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(final BlockBreakEvent event) {
		if (event.isCancelled()
				|| !plugin.getPermission().worldCheck(
						event.getBlock().getWorld())) {
			// event is already consumed
			// or, the world is not managed by the ExpCraft
			return;
		}

		Player player = event.getPlayer();
		if (!plugin.getPermission().hasModule(plugin, player)) {
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

		} else if (isRipeCrops(block)
				&& level >= plugin.getConfInt("UseLevel.Harvest")) {
			// harvesting crops
			gained = plugin.getConfDouble("ExpGain.Harvest");
			bonusDropForCrops(player, level, block);

		} else if (isRipeNetherWart(block)
				&& level >= plugin.getConfInt("UseLevel.NetherWart")) {
			// harvesting nether wart
			gained = plugin.getConfDouble("ExpGain.NetherWart");
			bonusDropForNetherWart(player, level, block);

		} else if (m == Material.CACTUS
				&& level >= plugin.getConfInt("UseLevel.Cacti")) {
			// harvesting cacti
			gained = plugin.getConfDouble("ExpGain.Cacti");

		} else if (isMelon(block)
				&& level >= plugin.getConfInt("UseLevel.Melon")) {
			// harvest melon
			gained = plugin.getConfDouble("ExpGain.Melon");

		} else if (isPumpkin(block)
				&& level >= plugin.getConfInt("UseLevel.Pumpkin")) {
			// harvest pumpkins
			gained = plugin.getConfDouble("ExpGain.Pumpkin");

		} else if (isRipeCocoaBean(block)
				&& level >= plugin.getConfInt("UseLevel.CocoaBean")) {
			// harvest cocoa bean
			gained = plugin.getConfDouble("ExpGain.CocoaBean");

		}
		plugin.getPersistence().addExp(plugin, player, gained);
	}
}
