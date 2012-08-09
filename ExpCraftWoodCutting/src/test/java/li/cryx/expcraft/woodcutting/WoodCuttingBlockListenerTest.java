package li.cryx.expcraft.woodcutting;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import li.cryx.expcraft.perm.AbstractPermissionManager;
import li.cryx.expcraft.persist.AbstractPersistenceManager;

import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.material.Tree;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Verify the (internal) methods' functionality of
 * {@link WoodCuttingBlockListener}.
 * 
 * @author cryxli
 */
public class WoodCuttingBlockListenerTest {

	private static WoodCuttingBlockListener listener;

	private static WoodCutting plugin;

	private static final Material[] TREE_LIKE = new Material[] { Material.LOG,
			Material.WOOD, Material.LEAVES, Material.SAPLING };

	private static final TreeSpecies[] TREE_SPECIES = new TreeSpecies[] {
			TreeSpecies.GENERIC, TreeSpecies.BIRCH, TreeSpecies.REDWOOD,
			TreeSpecies.JUNGLE };

	@BeforeClass
	public static void prepare() {
		plugin = Mockito.mock(WoodCutting.class);
		Mockito.when(plugin.getConfInt("UseLevel.LogOak")).thenReturn(1);
		Mockito.when(plugin.getConfInt("UseLevel.LogBirch")).thenReturn(2);
		Mockito.when(plugin.getConfInt("UseLevel.LogRedwood")).thenReturn(3);
		Mockito.when(plugin.getConfInt("UseLevel.LogJungle")).thenReturn(4);
		Mockito.when(plugin.getConfInt("UseLevel.PlankOak")).thenReturn(5);
		Mockito.when(plugin.getConfInt("UseLevel.PlankBirch")).thenReturn(6);
		Mockito.when(plugin.getConfInt("UseLevel.PlankRedwood")).thenReturn(7);
		Mockito.when(plugin.getConfInt("UseLevel.PlankJungle")).thenReturn(8);
		Mockito.when(plugin.getConfInt("UseLevel.LeavesOak")).thenReturn(21);
		Mockito.when(plugin.getConfInt("UseLevel.LeavesBirch")).thenReturn(22);
		Mockito.when(plugin.getConfInt("UseLevel.LeavesRedwood"))
				.thenReturn(23);
		Mockito.when(plugin.getConfInt("UseLevel.LeavesJungle")).thenReturn(24);
		Mockito.when(plugin.getConfInt("UseLevel.Fence")).thenReturn(9);
		Mockito.when(plugin.getConfDouble("ExpGain.LogOak")).thenReturn(1.5);
		Mockito.when(plugin.getConfDouble("ExpGain.LogBirch")).thenReturn(2.5);
		Mockito.when(plugin.getConfDouble("ExpGain.LogRedwood"))
				.thenReturn(3.5);
		Mockito.when(plugin.getConfDouble("ExpGain.LogJungle")).thenReturn(4.5);
		Mockito.when(plugin.getConfDouble("ExpGain.PlankOak")).thenReturn(5.5);
		Mockito.when(plugin.getConfDouble("ExpGain.PlankBirch"))
				.thenReturn(6.5);
		Mockito.when(plugin.getConfDouble("ExpGain.PlankRedwood")).thenReturn(
				7.5);
		Mockito.when(plugin.getConfDouble("ExpGain.PlankJungle")).thenReturn(
				8.5);
		Mockito.when(plugin.getConfInt("UseLevel.Fence")).thenReturn(9);
		Mockito.when(plugin.getConfInt("AxeLevel.Wooden")).thenReturn(11);
		Mockito.when(plugin.getConfInt("AxeLevel.Stone")).thenReturn(12);
		Mockito.when(plugin.getConfInt("AxeLevel.Iron")).thenReturn(13);
		Mockito.when(plugin.getConfInt("AxeLevel.Gold")).thenReturn(14);
		Mockito.when(plugin.getConfInt("AxeLevel.Diamond")).thenReturn(15);

		// make the intentionally private methods accessible for the test
		listener = new WoodCuttingBlockListener(plugin) {
			@Override
			public boolean checkLeafBreaking(final Player player,
					final int level, final TreeSpecies species) {
				return super.checkLeafBreaking(player, level, species);
			}

			@Override
			protected boolean checkTargetBlock(final Player player,
					final Block block, final int level) {
				return super.checkTargetBlock(player, block, level);
			}

			@Override
			protected boolean checkTool(final Player player,
					final Material material, final int level) {
				return super.checkTool(player, material, level);
			}

			@Override
			public double getExp(final String prefix, final TreeSpecies treeType) {
				return super.getExp(prefix, treeType);
			}

			@Override
			public int getLevel(final String prefix, final TreeSpecies treeType) {
				return super.getLevel(prefix, treeType);
			}

			@Override
			public TreeSpecies getTreeType(final Block block) {
				return super.getTreeType(block);
			}

			@Override
			public String getTreeTypeString(final TreeSpecies treeType) {
				return super.getTreeTypeString(treeType);
			}
		};
	}

	@Test
	public void checkLeafBreaking() {
		Player player = Mockito.mock(Player.class);

		int i = 21;
		for (TreeSpecies type : TREE_SPECIES) {
			Assert.assertFalse(listener.checkLeafBreaking(player, 20, type));
			plugin.warnCutBlock(player, i++);
			Assert.assertTrue(listener.checkLeafBreaking(player, 30, type));
		}
	}

	@Test
	public void checkTargetBlock() {
		Player player = Mockito.mock(Player.class);

		// logs and planks
		int i = 1;
		for (Material material : new Material[] { Material.LOG, Material.WOOD }) {
			for (TreeSpecies type : TREE_SPECIES) {
				Block block = getBlock(material, type);
				Assert.assertFalse(listener.checkTargetBlock(player, block, 0));
				Mockito.verify(plugin).warnCutBlock(player, i++);
				Assert.assertTrue(listener.checkTargetBlock(player, block, 10));
			}
		}

		// fences
		Block block = getBlock(Material.FENCE, TreeSpecies.GENERIC);
		Assert.assertFalse(listener.checkTargetBlock(player, block, 0));
		Mockito.verify(plugin).warnCutBlock(player, 9);
		Assert.assertTrue(listener.checkTargetBlock(player, block, 10));

		// other block
		block = getBlock(Material.STONE, TreeSpecies.GENERIC);
		Assert.assertTrue(listener.checkTargetBlock(player, block, 0));
	}

	@Test
	public void checkTool() {
		Player player = Mockito.mock(Player.class);

		// test axes
		Assert.assertTrue(listener.checkTool(player, Material.WOOD_AXE, 20));
		Assert.assertTrue(listener.checkTool(player, Material.STONE_AXE, 20));
		Assert.assertTrue(listener.checkTool(player, Material.IRON_AXE, 20));
		Assert.assertTrue(listener.checkTool(player, Material.GOLD_AXE, 20));
		Assert.assertTrue(listener.checkTool(player, Material.DIAMOND_AXE, 20));

		Assert.assertFalse(listener.checkTool(player, Material.WOOD_AXE, 10));
		Mockito.verify(plugin).warnToolLevel(player, 11);
		Assert.assertFalse(listener.checkTool(player, Material.STONE_AXE, 10));
		Mockito.verify(plugin).warnToolLevel(player, 12);
		Assert.assertFalse(listener.checkTool(player, Material.IRON_AXE, 10));
		Mockito.verify(plugin).warnToolLevel(player, 13);
		Assert.assertFalse(listener.checkTool(player, Material.GOLD_AXE, 10));
		Mockito.verify(plugin).warnToolLevel(player, 14);
		Assert.assertFalse(listener.checkTool(player, Material.DIAMOND_AXE, 10));
		Mockito.verify(plugin).warnToolLevel(player, 15);

		// not an axe
		Assert.assertTrue(listener.checkTool(player, Material.WOOD_SWORD, 10));
	}

	private Block getBlock(final Material material, final TreeSpecies type) {
		BlockState state = Mockito.mock(BlockState.class);
		Mockito.when(state.getData()).thenReturn(new Tree(type));

		Block block = Mockito.mock(Block.class);
		Mockito.when(block.getType()).thenReturn(material);
		Mockito.when(block.getState()).thenReturn(state);
		Mockito.when(block.getWorld()).thenReturn(null);

		return block;
	}

	// this test will throw an IndexOutOfBoundsException when bukkit adds a new
	// tree type
	@Test
	public void getExp() {
		// tree like
		for (int i = 0; i < TreeSpecies.values().length; i++) {
			double exp = listener.getExp("Log", TREE_SPECIES[i]);
			Assert.assertEquals(i + 1.5, exp, 0);
		}

		// error case
		try {
			listener.getExp("Log", null);
			Assert.fail();
		} catch (NullPointerException e) {
			// expected
		}
		Assert.assertEquals(0, listener.getExp(null, TreeSpecies.GENERIC), 0);
	}

	@Test
	public void getLevel() {
		// tree like
		for (int i = 0; i < TreeSpecies.values().length; i++) {
			int level = listener.getLevel("Log", TREE_SPECIES[i]);
			Assert.assertEquals(i + 1, level);
		}

		// error case
		try {
			listener.getExp("Log", null);
			Assert.fail();
		} catch (NullPointerException e) {
			// expected
		}
		Assert.assertEquals(0, listener.getLevel(null, TreeSpecies.GENERIC));
	}

	@Test
	public void getTreeType() {
		// for all tree like blocks
		for (Material material : TREE_LIKE) {
			// test all tree types
			for (TreeSpecies type : TreeSpecies.values()) {
				Block block = getBlock(material, type);
				TreeSpecies species = listener.getTreeType(block);
				Assert.assertEquals(type, species);
			}
		}

		// none tree like
		Block block = getBlock(Material.STONE, TreeSpecies.GENERIC);
		Assert.assertNull(listener.getTreeType(block));

		// error case
		try {
			listener.getTreeType(null);
			Assert.fail();
		} catch (NullPointerException e) {
			// expected
		}
	}

	@Test
	public void getTreeTypeString() {
		Map<TreeSpecies, String> treeString = new HashMap<TreeSpecies, String>();
		treeString.put(TreeSpecies.GENERIC, "Oak");
		treeString.put(TreeSpecies.BIRCH, "Birch");
		treeString.put(TreeSpecies.REDWOOD, "Redwood");
		treeString.put(TreeSpecies.JUNGLE, "Jungle");

		// test all tree types
		for (TreeSpecies type : TreeSpecies.values()) {
			String species = listener.getTreeTypeString(type);
			Assert.assertEquals(treeString.get(type), species);
		}

		// error case
		try {
			listener.getTreeTypeString(null);
			Assert.fail();
		} catch (NullPointerException e) {
			// expected
		}
	}

	// only one positive case
	@Test
	public void onBlockBreak() {
		// prepare
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getItemInHand()).thenReturn(null);

		AbstractPermissionManager perm = Mockito
				.mock(AbstractPermissionManager.class);
		Mockito.when(perm.worldCheck(null)).thenReturn(true);
		Mockito.when(perm.hasModule(plugin, player)).thenReturn(true);

		AbstractPersistenceManager pers = Mockito
				.mock(AbstractPersistenceManager.class);
		Mockito.when(pers.getLevel(plugin, player)).thenReturn(1);

		Mockito.when(plugin.getPermission()).thenReturn(perm);
		Mockito.when(plugin.getPersistence()).thenReturn(pers);

		// player punches a log of Oak
		// (once the first action a player performed in minecraft :-)
		Block block = getBlock(Material.LOG, TreeSpecies.GENERIC);
		BlockBreakEvent event = new BlockBreakEvent(block, player);
		listener.onBlockBreak(event);
		Assert.assertFalse(event.isCancelled());
		Mockito.verify(pers).addExp(plugin, player, 1.5);
	}
}
