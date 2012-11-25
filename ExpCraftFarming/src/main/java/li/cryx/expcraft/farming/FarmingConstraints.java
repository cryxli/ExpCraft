/*
 * Copyright (c) 2011 Urs P. Stettler, https://github.com/cryxli
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package li.cryx.expcraft.farming;

import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
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

	private final Farming plugin;

	public FarmingConstraints(final Farming plugin) {
		this.plugin = plugin;
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
	 * Test whether the given item is a hoe. All hoes are taken into
	 * consideration with their respective requirements defined in the config.
	 * 
	 * @param player
	 *            Current player.
	 * @param itemInHand
	 *            The item the current player is holding in his hand.
	 * @param level
	 *            Player's farming level.
	 * @return <code>true</code>, only if the given item is a hoe of any kind
	 *         and the player's level allows him to use this particular hoe.
	 */
	public boolean checkTool(final Player player, final Material itemInHand,
			final int level) {
		if (level < plugin.getConfig().getInteger("HoeLevel.Wooden")
				&& itemInHand == Material.WOOD_HOE) {
			plugin.warnToolLevel(player,
					plugin.getConfig().getInteger("HoeLevel.Wooden"));

		} else if (level < plugin.getConfig().getInteger("HoeLevel.Stone")
				&& itemInHand == Material.STONE_HOE) {
			plugin.warnToolLevel(player,
					plugin.getConfig().getInteger("HoeLevel.Stone"));

		} else if (level < plugin.getConfig().getInteger("HoeLevel.Iron")
				&& itemInHand == Material.IRON_HOE) {
			plugin.warnToolLevel(player,
					plugin.getConfig().getInteger("HoeLevel.Iron"));

		} else if (level < plugin.getConfig().getInteger("HoeLevel.Gold")
				&& itemInHand == Material.GOLD_HOE) {
			plugin.warnToolLevel(player,
					plugin.getConfig().getInteger("HoeLevel.Gold"));

		} else if (level < plugin.getConfig().getInteger("HoeLevel.Diamond")
				&& itemInHand == Material.DIAMOND_HOE) {
			plugin.warnToolLevel(player,
					plugin.getConfig().getInteger("HoeLevel.Diamond"));

		} else {
			// all tool checks passed
			return true;
		}
		// one tool check failed
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
	 * Test whether the given block represents fully grown, ripe carrots.
	 * 
	 * @param block
	 *            A block participating in an event.
	 * @return <code>true</code>, only if the block represents carrots in state
	 *         ripe.
	 */
	public boolean isRipeCarrot(final Block block) {
		// TODO cryxli: implement the proper way, once bukkit is ready
		if (block.getType() == Material.CARROT) {
			return block.getData() == CarrotState.RIPE.getData();
		} else {
			return false;
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

	/**
	 * Test whether the given block represents fully grown, ripe potato.
	 * 
	 * @param block
	 *            A block participating in an event.
	 * @return <code>true</code>, only if the block represents potato in state
	 *         ripe.
	 */
	public boolean isRipePotato(final Block block) {
		// TODO cryxli: implement the proper way, once bukkit is ready
		if (block.getType() == Material.POTATO) {
			return block.getData() == PotatoState.RIPE.getData();
		} else {
			return false;
		}
	}

}
