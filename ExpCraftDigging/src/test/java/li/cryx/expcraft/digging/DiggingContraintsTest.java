package li.cryx.expcraft.digging;

import junit.framework.Assert;
import li.cryx.expcraft.AbstractPluginTest;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Tests the externalised logic of {@link DiggingBlockListener}.
 * 
 * @author cryxli
 */
public class DiggingContraintsTest extends AbstractPluginTest<Digging> {

	private DiggingContraints test;

	/** Test for {@link DiggingContraints#addExperience(Player, Material, int)} */
	@Test
	public void addExperience() {
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("Player");
		test.addExperience(player, Material.STONE, 1);
		Mockito.verify(plugin, Mockito.never()).warnCutBlockLevel(
				Mockito.eq(player), Mockito.anyInt());
		Assert.assertEquals(0, pers.getExp(plugin, player), 0);

		testBlocks(Material.DIRT, "UseLevel.Dirt", 1);
		testBlocks(Material.GRASS, "UseLevel.Grass", 1);
		testBlocks(Material.SNOW, "UseLevel.Snow", 5);
		testBlocks(Material.SOUL_SAND, "UseLevel.SoulSand", 8);
		testBlocks(Material.GRAVEL, "UseLevel.Gravel", 1);
		testBlocks(Material.CLAY, "UseLevel.Clay", 10);
		testBlocks(Material.SAND, "UseLevel.Sand", 2);
		testBlocks(Material.MYCEL, "UseLevel.Mycelium", 1);
	}

	/** Test for {@link DiggingContraints#checkTools(Player, Material, int)} */
	@Test
	public void checkTools() {
		Player player = Mockito.mock(Player.class);
		Assert.assertTrue(test.checkTools(player, Material.AIR, 1));
		Mockito.verify(plugin, Mockito.never()).warnToolLevel(
				Mockito.eq(player), Mockito.anyInt());

		testTool(Material.WOOD_SPADE, 1, -1);
		testTool(Material.STONE_SPADE, 6, 1);
		testTool(Material.IRON_SPADE, 11, 1);
		testTool(Material.GOLD_SPADE, 21, 1);
		testTool(Material.DIAMOND_SPADE, 31, 1);
	}

	/**
	 * Test for
	 * {@link DiggingContraints#fireShovel(Player, int, BlockBreakEvent)}
	 */
	@Test
	public void fireShovel() {
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getItemInHand()).thenReturn(
				new ItemStack(Material.AIR));
		BlockBreakEvent event = new BlockBreakEvent(null, player);
		Assert.assertFalse(test.fireShovel(player, 1, event));
		Assert.assertFalse(test.fireShovel(player, 80, event));

		player = Mockito.mock(Player.class);
		Mockito.when(player.getItemInHand()).thenReturn(
				new ItemStack(Material.GOLD_PICKAXE));
		Block block = Mockito.mock(Block.class);
		Mockito.when(block.getType()).thenReturn(Material.SAND);
		event = new BlockBreakEvent(block, player);
		Assert.assertFalse(test.fireShovel(player, 80, event));

		testfireShovel(Material.SAND);
		testfireShovel(Material.CLAY);
	}

	@Override
	protected Class<Digging> getClazz() {
		return Digging.class;
	}

	@Before
	public void prepareTestSpecific() {
		test = new DiggingContraints(plugin);
	}

	private void testBlocks(final Material material, final String useLevel,
			final double exp) {
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("Player");
		pers.setExp(plugin, player, 0);

		int level = config.getInteger(useLevel);
		config.setInteger(useLevel, 50);
		test.addExperience(player, material, 1);
		Mockito.verify(plugin).warnCutBlockLevel(player, 50);

		config.setInteger(useLevel, 0);
		test.addExperience(player, material, 1);
		config.setInteger(useLevel, level);
		Assert.assertEquals(exp, pers.getExp(plugin, player));
	}

	private void testfireShovel(final Material material) {
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getItemInHand()).thenReturn(
				new ItemStack(Material.GOLD_SPADE));

		World world = Mockito.mock(World.class);

		Block block = Mockito.mock(Block.class);
		Mockito.when(block.getWorld()).thenReturn(world);
		Mockito.when(block.getType()).thenReturn(material);

		BlockBreakEvent event = new BlockBreakEvent(block, player);

		Assert.assertTrue(test.fireShovel(player, 80, event));
		Mockito.verify(block).setType(Material.AIR);
		Mockito.verify(world).dropItem(Mockito.any(Location.class),
				Mockito.any(ItemStack.class));
	}

	private void testTool(final Material material, final int okLevel,
			final int nokLevel) {
		Player player = Mockito.mock(Player.class);

		Assert.assertTrue(test.checkTools(player, material, okLevel));
		Mockito.verify(plugin, Mockito.never()).warnToolLevel(
				Mockito.eq(player), Mockito.anyInt());

		Assert.assertFalse(test.checkTools(player, material, nokLevel));
		Mockito.verify(plugin).warnToolLevel(Mockito.eq(player),
				Mockito.anyInt());
	}

}
