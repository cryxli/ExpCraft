package li.cryx.expcraft.farming;

import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.material.CocoaPlant;
import org.bukkit.material.CocoaPlant.CocoaPlantSize;
import org.bukkit.material.Crops;

/**
 * This class contains the test used by {@link FarmingBlockBreakListener} to
 * determine the gained exp.
 * 
 * @author cryxli
 */
public class FarmingConstraints {

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
	public boolean checkAround(final Block center, final Material aroundType) {
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
	 * Test whether the given block is a melon next to a stem.
	 * 
	 * @param block
	 *            The block to test
	 * @return <code>true</code>, if the harvested block is a grown melon
	 */
	public boolean isMelon(final Block block) {
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
	public boolean isPumpkin(final Block block) {
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
	public boolean isRipeCocoaBean(final Block block) {
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
	public boolean isRipeCrops(final Block block) {
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
	public boolean isRipeNetherWart(final Block block) {
		// TODO cryxli: implement the proper way, once bukkit is ready
		if (block.getType() == Material.NETHER_WARTS) {
			return block.getData() == NetherWartState.RIPE.getData();
		} else {
			return false;
		}
	}

}
