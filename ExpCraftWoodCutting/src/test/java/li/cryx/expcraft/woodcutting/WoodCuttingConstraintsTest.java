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
package li.cryx.expcraft.woodcutting;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import li.cryx.expcraft.AbstractPluginTest;

import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.material.Tree;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Verify the (internal) methods' functionality of
 * {@link WoodCuttingConstraints}.
 * 
 * @author cryxli
 */
public class WoodCuttingConstraintsTest extends AbstractPluginTest<WoodCutting> {

	private WoodCuttingConstraints test;

	private static final Material[] TREE_LIKE = new Material[] { Material.LOG,
			Material.WOOD, Material.LEAVES, Material.SAPLING };

	// because order matters within this test class
	private static final TreeSpecies[] TREE_SPECIES = new TreeSpecies[] {
			TreeSpecies.GENERIC, TreeSpecies.BIRCH, TreeSpecies.REDWOOD,
			TreeSpecies.JUNGLE, TreeSpecies.ACACIA, TreeSpecies.DARK_OAK };

	@Test
	public void checkLeafBreaking() {
		Player player = Mockito.mock(Player.class);

		int i = 21;
		for (TreeSpecies type : TREE_SPECIES) {
			Assert.assertFalse(test.checkLeafBreaking(player, 20, type));
			plugin.warnCutBlock(player, i++);
			Assert.assertTrue(test.checkLeafBreaking(player, 30, type));
		}
	}

	@Test
	public void checkTargetBlock() {
		Player player = Mockito.mock(Player.class);

		// logs and planks
		int i = 1;
		for (Material material : new Material[] { Material.LOG, Material.WOOD }) {
			for (TreeSpecies type : new TreeSpecies[] { TreeSpecies.GENERIC,
					TreeSpecies.BIRCH, TreeSpecies.REDWOOD, TreeSpecies.JUNGLE }) {
				Block block = getBlock(material, type);
				// System.out.println(material + "/" + type + "/" + i);
				Assert.assertFalse(test.checkTargetBlock(player, block, 0));
				Mockito.verify(plugin).warnCutBlock(player, i++);
				Assert.assertTrue(test.checkTargetBlock(player, block, 15));
			}
			for (TreeSpecies type : new TreeSpecies[] { TreeSpecies.ACACIA,
					TreeSpecies.DARK_OAK }) {
				Block block = getBlock(
						material == Material.LOG ? Material.LOG_2 : material,
						type);
				// System.out.println(material + "/" + type + "/" + i);
				Assert.assertFalse(test.checkTargetBlock(player, block, 0));
				Mockito.verify(plugin).warnCutBlock(player, i++);
				Assert.assertTrue(test.checkTargetBlock(player, block, 15));
			}
		}

		// fences
		Block block = getBlock(Material.FENCE);
		Assert.assertFalse(test.checkTargetBlock(player, block, 0));
		Mockito.verify(plugin).warnCutBlock(player, 9);
		Assert.assertTrue(test.checkTargetBlock(player, block, 30));

		// other block
		block = getBlock(Material.STONE);
		Assert.assertTrue(test.checkTargetBlock(player, block, 0));
	}

	@Test
	public void checkTool() {
		Player player = Mockito.mock(Player.class);

		// test axes
		Assert.assertTrue(test.checkTool(player, Material.WOOD_AXE, 20));
		Assert.assertTrue(test.checkTool(player, Material.STONE_AXE, 20));
		Assert.assertTrue(test.checkTool(player, Material.IRON_AXE, 20));
		Assert.assertTrue(test.checkTool(player, Material.GOLD_AXE, 20));
		Assert.assertTrue(test.checkTool(player, Material.DIAMOND_AXE, 20));

		Assert.assertFalse(test.checkTool(player, Material.WOOD_AXE, 10));
		Mockito.verify(plugin).warnToolLevel(player, 11);
		Assert.assertFalse(test.checkTool(player, Material.STONE_AXE, 10));
		Mockito.verify(plugin).warnToolLevel(player, 12);
		Assert.assertFalse(test.checkTool(player, Material.IRON_AXE, 10));
		Mockito.verify(plugin).warnToolLevel(player, 13);
		Assert.assertFalse(test.checkTool(player, Material.GOLD_AXE, 10));
		Mockito.verify(plugin).warnToolLevel(player, 14);
		Assert.assertFalse(test.checkTool(player, Material.DIAMOND_AXE, 10));
		Mockito.verify(plugin).warnToolLevel(player, 15);

		// not an axe
		Assert.assertTrue(test.checkTool(player, Material.WOOD_SWORD, 10));
	}

	@Test
	public void checkTreeSpecies() {
		// this test will fail whenever new TreeSpecies are added to the game
		Assert.assertEquals(6, TreeSpecies.values().length);
	}

	@Override
	protected Class<WoodCutting> getClazz() {
		return WoodCutting.class;
	}

	// this test will throw an IndexOutOfBoundsException when bukkit adds a new
	// tree type
	@Test
	public void getExp() {
		// tree like
		for (int i = 0; i < TreeSpecies.values().length; i++) {
			double exp = test.getExp("Log", TREE_SPECIES[i]);
			Assert.assertEquals(i + 1.5, exp, 0);
			exp = test.getExp("Plank", TREE_SPECIES[i]);
			Assert.assertEquals(i + 5.5, exp, 0);
			exp = test.getExp("Leaves", TREE_SPECIES[i]);
			Assert.assertEquals(i + 9.5, exp, 0);
		}

		// error case
		try {
			test.getExp("Log", null);
			Assert.fail();
		} catch (NullPointerException e) {
			// expected
		}
		Assert.assertEquals(0, test.getExp(null, TreeSpecies.GENERIC), 0);
	}

	@Test
	public void getLevel() {
		// tree like
		for (int i = 0; i < TreeSpecies.values().length; i++) {
			int level = test.getLevel("Log", TREE_SPECIES[i]);
			Assert.assertEquals(i + 1, level);
			level = test.getLevel("Plank", TREE_SPECIES[i]);
			Assert.assertEquals(i + 1 + TreeSpecies.values().length, level);
			level = test.getLevel("Leaves", TREE_SPECIES[i]);
			Assert.assertEquals(i + 21, level);
		}

		// error case
		try {
			test.getLevel("Log", null);
			Assert.fail();
		} catch (NullPointerException e) {
			// expected
		}
		Assert.assertEquals(0, test.getLevel(null, TreeSpecies.GENERIC));
	}

	@Test
	public void getTreeType() {
		// for all tree like blocks
		for (Material material : TREE_LIKE) {
			// test all tree types
			// System.out.println(material);
			for (TreeSpecies type : TreeSpecies.values()) {
				Block block;
				if (material == Material.LOG
						&& (type == TreeSpecies.ACACIA || type == TreeSpecies.DARK_OAK)) {
					block = getBlock(Material.LOG_2, type);
				} else {
					block = getBlock(material, type);
				}
				TreeSpecies species = test.getTreeType(block);
				Assert.assertEquals(type, species);
			}
		}

		// none tree like
		Block block = getBlock(Material.STONE);
		Assert.assertNull(test.getTreeType(block));

		// error case
		try {
			test.getTreeType(null);
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
		treeString.put(TreeSpecies.ACACIA, "Acacia");
		treeString.put(TreeSpecies.DARK_OAK, "DarkOak");

		// test all tree types
		for (TreeSpecies type : TreeSpecies.values()) {
			String species = test.getTreeTypeString(type);
			Assert.assertEquals(treeString.get(type), species);
		}

		// error case
		try {
			test.getTreeTypeString(null);
			Assert.fail();
		} catch (NullPointerException e) {
			// expected
		}
	}

	@Before
	public void prepare() {
		test = new WoodCuttingConstraints(plugin);
	}

	@Test
	public void verifyTreeSpecies() {
		// TODO correct this once new trees are implemented correctly
		// for (TreeSpecies tree :TreeSpecies.values()) {
		for (TreeSpecies tree : new TreeSpecies[] { TreeSpecies.GENERIC,
				TreeSpecies.BIRCH, TreeSpecies.REDWOOD, TreeSpecies.JUNGLE }) {
			Assert.assertEquals(tree, new Tree(tree).getSpecies());
		}
	}

}
