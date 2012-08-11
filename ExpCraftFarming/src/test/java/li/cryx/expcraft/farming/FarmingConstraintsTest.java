package li.cryx.expcraft.farming;

import junit.framework.Assert;

import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.material.CocoaPlant;
import org.bukkit.material.CocoaPlant.CocoaPlantSize;
import org.bukkit.material.Crops;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Tests {@link FarmingConstraints}' methods.
 * 
 * @author cryxli
 */
public class FarmingConstraintsTest {

	/** The class to test. */
	private static final FarmingConstraints test = new FarmingConstraints();

	/** Test for {@link FarmingConstraints#checkAround(Block, Material)} */
	@Test
	public void checkAround() {
		Block block = getBlock(Material.DIRT, Material.AIR, Material.AIR,
				Material.AIR, Material.AIR);
		Assert.assertFalse(test.checkAround(block, Material.STONE));

		block = getBlock(Material.DIRT, Material.STONE, Material.AIR,
				Material.AIR, Material.AIR);
		Assert.assertTrue(test.checkAround(block, Material.STONE));

		block = getBlock(Material.DIRT, Material.AIR, Material.STONE,
				Material.AIR, Material.AIR);
		Assert.assertTrue(test.checkAround(block, Material.STONE));

		block = getBlock(Material.DIRT, Material.AIR, Material.AIR,
				Material.STONE, Material.AIR);
		Assert.assertTrue(test.checkAround(block, Material.STONE));

		block = getBlock(Material.DIRT, Material.AIR, Material.AIR,
				Material.AIR, Material.STONE);
		Assert.assertTrue(test.checkAround(block, Material.STONE));
	}

	private Block getBlock(final Material center, final Material south,
			final Material north, final Material west, final Material east) {
		Block centerBlock = Mockito.mock(Block.class);
		Mockito.when(centerBlock.getType()).thenReturn(center);

		Block block = Mockito.mock(Block.class);
		Mockito.when(block.getType()).thenReturn(south);
		Mockito.when(centerBlock.getRelative(BlockFace.SOUTH))
				.thenReturn(block);

		block = Mockito.mock(Block.class);
		Mockito.when(block.getType()).thenReturn(north);
		Mockito.when(centerBlock.getRelative(BlockFace.NORTH))
				.thenReturn(block);

		block = Mockito.mock(Block.class);
		Mockito.when(block.getType()).thenReturn(west);
		Mockito.when(centerBlock.getRelative(BlockFace.WEST)).thenReturn(block);

		block = Mockito.mock(Block.class);
		Mockito.when(block.getType()).thenReturn(east);
		Mockito.when(centerBlock.getRelative(BlockFace.EAST)).thenReturn(block);

		return centerBlock;
	}

	/** Test for {@link FarmingConstraints#isMelon(Block)} */
	@Test
	public void isMelon() {
		Block stone = Mockito.mock(Block.class);
		Mockito.when(stone.getType()).thenReturn(Material.STONE);
		Assert.assertFalse(test.isMelon(stone));

		Block placedMelon = getBlock(Material.MELON_BLOCK, Material.AIR,
				Material.AIR, Material.AIR, Material.AIR);
		Assert.assertFalse(test.isMelon(placedMelon));

		Block plantedMelon = getBlock(Material.MELON_BLOCK,
				Material.MELON_STEM, Material.AIR, Material.AIR, Material.AIR);
		Assert.assertTrue(test.isMelon(plantedMelon));

		plantedMelon = getBlock(Material.MELON_BLOCK, Material.AIR,
				Material.MELON_STEM, Material.AIR, Material.AIR);
		Assert.assertTrue(test.isMelon(plantedMelon));

		plantedMelon = getBlock(Material.MELON_BLOCK, Material.AIR,
				Material.AIR, Material.MELON_STEM, Material.AIR);
		Assert.assertTrue(test.isMelon(plantedMelon));

		plantedMelon = getBlock(Material.MELON_BLOCK, Material.AIR,
				Material.AIR, Material.AIR, Material.MELON_STEM);
		Assert.assertTrue(test.isMelon(plantedMelon));
	}

	/** Test for {@link FarmingConstraints#isPumpkin(Block)} */
	@Test
	public void isPumpkin() {
		Block stone = Mockito.mock(Block.class);
		Mockito.when(stone.getType()).thenReturn(Material.STONE);
		Assert.assertFalse(test.isPumpkin(stone));

		Block placedPumpkin = getBlock(Material.PUMPKIN, Material.AIR,
				Material.AIR, Material.AIR, Material.AIR);
		Assert.assertFalse(test.isPumpkin(placedPumpkin));

		Block plantedPumpkin = getBlock(Material.PUMPKIN,
				Material.PUMPKIN_STEM, Material.AIR, Material.AIR, Material.AIR);
		Assert.assertTrue(test.isPumpkin(plantedPumpkin));

		plantedPumpkin = getBlock(Material.PUMPKIN, Material.AIR,
				Material.PUMPKIN_STEM, Material.AIR, Material.AIR);
		Assert.assertTrue(test.isPumpkin(plantedPumpkin));

		plantedPumpkin = getBlock(Material.PUMPKIN, Material.AIR, Material.AIR,
				Material.PUMPKIN_STEM, Material.AIR);
		Assert.assertTrue(test.isPumpkin(plantedPumpkin));

		plantedPumpkin = getBlock(Material.PUMPKIN, Material.AIR, Material.AIR,
				Material.AIR, Material.PUMPKIN_STEM);
		Assert.assertTrue(test.isPumpkin(plantedPumpkin));
	}

	/** Test for {@link FarmingConstraints#isRipeCocoaBean(Block)} */
	@Test
	public void isRipeCocoaBean() {
		Block stone = Mockito.mock(Block.class);
		Mockito.when(stone.getType()).thenReturn(Material.STONE);
		Assert.assertFalse(test.isRipeCocoaBean(stone));

		for (CocoaPlantSize size : CocoaPlantSize.values()) {
			CocoaPlant plant = new CocoaPlant(size);

			BlockState state = Mockito.mock(BlockState.class);
			Mockito.when(state.getData()).thenReturn(plant);

			Block bean = Mockito.mock(Block.class);
			Mockito.when(bean.getType()).thenReturn(Material.COCOA);
			Mockito.when(bean.getState()).thenReturn(state);

			Assert.assertEquals(size == CocoaPlantSize.LARGE,
					test.isRipeCocoaBean(bean));
		}
	}

	/** Test for {@link FarmingConstraints#isRipeCrops(Block)} */
	@Test
	public void isRipeCrops() {
		Block stone = Mockito.mock(Block.class);
		Mockito.when(stone.getType()).thenReturn(Material.STONE);
		Assert.assertFalse(test.isRipeCrops(stone));

		for (CropState size : CropState.values()) {
			Crops plant = new Crops(size);

			BlockState state = Mockito.mock(BlockState.class);
			Mockito.when(state.getData()).thenReturn(plant);

			Block crop = Mockito.mock(Block.class);
			Mockito.when(crop.getType()).thenReturn(Material.CROPS);
			Mockito.when(crop.getState()).thenReturn(state);

			Assert.assertEquals(size == CropState.RIPE, test.isRipeCrops(crop));
		}

	}

	/** Test for {@link FarmingConstraints#isRipeNetherWart(Block)} */
	@Test
	public void isRipeNetherWart() {
		Block stone = Mockito.mock(Block.class);
		Mockito.when(stone.getType()).thenReturn(Material.STONE);
		Assert.assertFalse(test.isRipeNetherWart(stone));

		for (NetherWartState size : NetherWartState.values()) {
			Block wart = Mockito.mock(Block.class);
			Mockito.when(wart.getType()).thenReturn(Material.NETHER_WARTS);
			Mockito.when(wart.getData()).thenReturn(size.getData());

			Assert.assertEquals(size == NetherWartState.RIPE,
					test.isRipeNetherWart(wart));
		}
	}

}
