package li.cryx.expcraft.farming;

import junit.framework.Assert;
import li.cryx.expcraft.AbstractPluginTest;

import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.material.CocoaPlant;
import org.bukkit.material.CocoaPlant.CocoaPlantSize;
import org.bukkit.material.Crops;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Tests {@link FarmingConstraints}' methods.
 * 
 * @author cryxli
 */
public class FarmingConstraintsTest extends AbstractPluginTest<Farming> {

	public static Block getBlock(final Material center, final Material south,
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

	/** The class to test. */
	private FarmingConstraints test;

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

	/**
	 * Test for
	 * {@link FarmingConstraints#checkTool(org.bukkit.entity.Player, Material, int)}
	 */
	@Test
	public void checkTool() {
		Player player = Mockito.mock(Player.class);
		Assert.assertTrue(test.checkTool(player, Material.AIR, 1));

		Assert.assertTrue(test.checkTool(player, Material.WOOD_HOE, 1));
		Assert.assertFalse(test.checkTool(player, Material.WOOD_HOE, -1));

		Assert.assertTrue(test.checkTool(player, Material.STONE_HOE, 6));
		Assert.assertFalse(test.checkTool(player, Material.STONE_HOE, 1));

		Assert.assertTrue(test.checkTool(player, Material.IRON_HOE, 11));
		Assert.assertFalse(test.checkTool(player, Material.IRON_HOE, 1));

		Assert.assertTrue(test.checkTool(player, Material.GOLD_HOE, 21));
		Assert.assertFalse(test.checkTool(player, Material.GOLD_HOE, 1));

		Assert.assertTrue(test.checkTool(player, Material.DIAMOND_HOE, 31));
		Assert.assertFalse(test.checkTool(player, Material.DIAMOND_HOE, 1));
	}

	@Override
	protected Class<Farming> getClazz() {
		return Farming.class;
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

	/** Test for {@link FarmingConstraints#isRipeCarrot(Block)} */
	@Test
	public void isRipeCarrots() {
		Block stone = Mockito.mock(Block.class);
		Mockito.when(stone.getType()).thenReturn(Material.STONE);
		Assert.assertFalse(test.isRipeNetherWart(stone));

		for (CarrotState size : CarrotState.values()) {
			Block carrot = Mockito.mock(Block.class);
			Mockito.when(carrot.getType()).thenReturn(Material.CARROT);
			Mockito.when(carrot.getData()).thenReturn(size.getData());

			Assert.assertEquals(size == CarrotState.RIPE,
					test.isRipeCarrot(carrot));
		}
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

	/** Test for {@link FarmingConstraints#isRipePotato(Block)} */
	@Test
	public void isRipePotato() {
		Block stone = Mockito.mock(Block.class);
		Mockito.when(stone.getType()).thenReturn(Material.STONE);
		Assert.assertFalse(test.isRipeNetherWart(stone));

		for (PotatoState size : PotatoState.values()) {
			Block potato = Mockito.mock(Block.class);
			Mockito.when(potato.getType()).thenReturn(Material.POTATO);
			Mockito.when(potato.getData()).thenReturn(size.getData());

			Assert.assertEquals(size == PotatoState.RIPE,
					test.isRipePotato(potato));
		}
	}

	@Before
	public void prepareTestSpecific() {
		test = new FarmingConstraints(plugin);
	}

}
