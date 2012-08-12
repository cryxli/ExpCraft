package li.cryx.expcraft.scavenger;

import li.cryx.expcraft.AbstractPluginTest;
import li.cryx.expcraft.ExpCraftCoreStub;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

/**
 * Tests public methods of {@link ScavengerConstraints}.
 * 
 * @author cryxli
 */
public class ScavengerConstraintsTest extends AbstractPluginTest<Scavenger> {

	/** Instance to test. */
	private ScavengerConstraints test;

	/** Test for {@link ScavengerConstraints#dropItem(Block, Material)} */
	@Test
	public void dropItem() {
		World world = Mockito.mock(World.class);
		Block block = Mockito.mock(Block.class);
		Mockito.when(block.getWorld()).thenReturn(world);
		Mockito.when(block.getX()).thenReturn(1);
		Mockito.when(block.getY()).thenReturn(2);
		Mockito.when(block.getZ()).thenReturn(3);

		test.dropItem(block, Material.STONE);

		ArgumentCaptor<Location> loc = ArgumentCaptor.forClass(Location.class);
		ArgumentCaptor<ItemStack> stack = ArgumentCaptor
				.forClass(ItemStack.class);
		Mockito.verify(world).dropItem(loc.capture(), stack.capture());
		Assert.assertEquals(Material.STONE, stack.getValue().getType());
		Assert.assertEquals(1, loc.getValue().getX(), 0);
		Assert.assertEquals(2, loc.getValue().getY(), 0);
		Assert.assertEquals(3, loc.getValue().getZ(), 0);
	}

	/** Test for {@link ScavengerConstraints#dropNether(Player, int, Block, int)} */
	@Test
	public void dropNether() {
		testNetherDrop(Material.GHAST_TEAR, 0, 3, 10, 15);
		testNetherDrop(Material.GOLD_NUGGET, 4, 6, 10, 22.2);
		testNetherDrop(Material.BLAZE_POWDER, 7, 8, 20, 30);

		// adding new drop will cause this one to fail
		testNetherDrop(null, 9, 100, 0, false);
	}

	/** Test for {@link ScavengerConstraints#dropNormal(Player, int, Block, int)} */
	@Test
	public void dropNormal() {
		testNormalDrop(Material.LEATHER_CHESTPLATE, 0, 0, 0, 10);
		testNormalDrop(Material.SADDLE, 1, 5, 0, 100);
		testNormalDrop(Material.GOLD_INGOT, 6, 6, 0, 200);
		testNormalDrop(Material.BOWL, 7, 8, 0, 15);
		testNormalDrop(Material.LEATHER_BOOTS, 9, 10, 0, 20);
		testNormalDrop(Material.LEATHER_HELMET, 11, 12, 0, 20);
		testNormalDrop(Material.BUCKET, 13, 15, 0, 200);
		testNormalDrop(Material.LEATHER_LEGGINGS, 16, 17, 0, 15);

		// adding new drop will cause this one to fail
		testNormalDrop(null, 18, 100, 0, false);
	}

	@Override
	protected Class<Scavenger> getClazz() {
		return Scavenger.class;
	}

	/** Test for {@link ScavengerConstraints#getRange(int)} */
	@Test
	public void getRange() {
		Mockito.when(plugin.getLevelCap()).thenReturn(100);

		Assert.assertEquals(1000, test.getRange(1)); // %o
		Assert.assertEquals(1000, test.getRange(4));
		Assert.assertEquals(900, test.getRange(5));
		Assert.assertEquals(850, test.getRange(10));
		Assert.assertEquals(850, test.getRange(19));
		Assert.assertEquals(650, test.getRange(20));
		Assert.assertEquals(650, test.getRange(39));
		Assert.assertEquals(400, test.getRange(40));
		Assert.assertEquals(400, test.getRange(69));
		Assert.assertEquals(200, test.getRange(89));
		Assert.assertEquals(100, test.getRange(90)); // %
		Assert.assertEquals(100, test.getRange(100));
	}

	/** Test for {@link ScavengerConstraints#isDiggable(Material)} */
	@Test
	public void isDiggable() {
		Assert.assertTrue(test.isDiggable(Material.DIRT));
		Assert.assertTrue(test.isDiggable(Material.DIRT));
		Assert.assertTrue(test.isDiggable(Material.GRASS));
		Assert.assertTrue(test.isDiggable(Material.SOUL_SAND));
		Assert.assertTrue(test.isDiggable(Material.GRAVEL));
		Assert.assertTrue(test.isDiggable(Material.CLAY));
		Assert.assertTrue(test.isDiggable(Material.SAND));
		Assert.assertTrue(test.isDiggable(Material.MYCEL));

		Assert.assertFalse(test.isDiggable(Material.SNOW));
		Assert.assertFalse(test.isDiggable(Material.STONE));
	}

	@Before
	public void prepareTestSpecific() {
		Mockito.when(plugin.getConfInt(Mockito.anyString()))
				.thenCallRealMethod();
		Mockito.when(plugin.getConfDouble(Mockito.anyString()))
				.thenCallRealMethod();

		Server server = Mockito.mock(Server.class);
		Mockito.when(server.getOnlinePlayers()).thenReturn(new Player[] {});
		ExpCraftCoreStub core = new ExpCraftCoreStub(server,
				new PluginDescriptionFile("ExpCraft", "0", ""));
		pers.setCore(core);

		test = new ScavengerConstraints(plugin);
	}

	private void testNetherDrop(final Material material, final int rnd,
			final int level, final double exp, final boolean expectDrop) {
		World world = Mockito.mock(World.class);
		Block block = Mockito.mock(Block.class);
		Mockito.when(block.getWorld()).thenReturn(world);
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("Player");

		test.dropNether(player, level, block, rnd);

		if (expectDrop) {
			ArgumentCaptor<ItemStack> stack = ArgumentCaptor
					.forClass(ItemStack.class);
			Mockito.verify(world).dropItem(Mockito.any(Location.class),
					stack.capture());
			Assert.assertEquals(material, stack.getValue().getType());

			Assert.assertEquals(exp, pers.getExp(plugin, player), 0);
			pers.setExp(plugin, player, 0);
		} else {
			Mockito.verify(world, Mockito.never()).dropItem(
					Mockito.any(Location.class), Mockito.any(ItemStack.class));

			Assert.assertEquals(0, pers.getExp(plugin, player), 0);
		}
	}

	private void testNetherDrop(final Material material, final int minRnd,
			final int maxRnd, final int minLevel, final double exp) {
		testNetherDrop(material, minRnd, minLevel - 1, exp, false);
		testNetherDrop(material, maxRnd, minLevel - 1, exp, false);
		testNetherDrop(material, minRnd, minLevel, exp, true);
		testNetherDrop(material, maxRnd, minLevel, exp, true);
	}

	private void testNormalDrop(final Material material, final int rnd,
			final int level, final double exp, final boolean expectDrop) {
		World world = Mockito.mock(World.class);
		Block block = Mockito.mock(Block.class);
		Mockito.when(block.getWorld()).thenReturn(world);
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("Player");

		test.dropNormal(player, level, block, rnd);

		if (expectDrop) {
			ArgumentCaptor<ItemStack> stack = ArgumentCaptor
					.forClass(ItemStack.class);
			Mockito.verify(world).dropItem(Mockito.any(Location.class),
					stack.capture());
			Assert.assertEquals(material, stack.getValue().getType());

			Assert.assertEquals(exp, pers.getExp(plugin, player), 0);
			pers.setExp(plugin, player, 0);
		} else {
			Mockito.verify(world, Mockito.never()).dropItem(
					Mockito.any(Location.class), Mockito.any(ItemStack.class));

			Assert.assertEquals(0, pers.getExp(plugin, player), 0);
		}
	}

	private void testNormalDrop(final Material material, final int minRnd,
			final int maxRnd, final int minLevel, final double exp) {
		testNormalDrop(material, minRnd, minLevel - 1, exp, false);
		testNormalDrop(material, maxRnd, minLevel - 1, exp, false);
		testNormalDrop(material, minRnd, minLevel, exp, true);
		testNormalDrop(material, maxRnd, minLevel, exp, true);
	}
}
