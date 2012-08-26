package li.cryx.expcraft.util;

import junit.framework.Assert;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.Test;

/**
 * Verify functionality of {@link ToolQuality}.
 * 
 * @author cryxli
 */
public class ToolHelperTest {

	@Test
	public void getMaterial() {
		Assert.assertNull(ToolQuality.getMaterial(null));
		Assert.assertNull(ToolQuality.getMaterial(Material.AIR));

		Assert.assertEquals(Material.LEATHER,
				ToolQuality.getMaterial(Material.LEATHER_BOOTS));
		Assert.assertEquals(Material.LEATHER,
				ToolQuality.getMaterial(Material.LEATHER_CHESTPLATE));
		Assert.assertEquals(Material.LEATHER,
				ToolQuality.getMaterial(Material.LEATHER_HELMET));
		Assert.assertEquals(Material.LEATHER,
				ToolQuality.getMaterial(Material.LEATHER_LEGGINGS));

		Assert.assertEquals(Material.WOOD,
				ToolQuality.getMaterial(Material.WOOD_AXE));
		Assert.assertEquals(Material.WOOD,
				ToolQuality.getMaterial(Material.WOOD_HOE));
		Assert.assertEquals(Material.WOOD,
				ToolQuality.getMaterial(Material.WOOD_PICKAXE));
		Assert.assertEquals(Material.WOOD,
				ToolQuality.getMaterial(Material.WOOD_SPADE));
		Assert.assertEquals(Material.WOOD,
				ToolQuality.getMaterial(Material.WOOD_SWORD));
		Assert.assertEquals(Material.WOOD,
				ToolQuality.getMaterial(Material.BOW));

		Assert.assertEquals(Material.STONE,
				ToolQuality.getMaterial(Material.CHAINMAIL_BOOTS));
		Assert.assertEquals(Material.STONE,
				ToolQuality.getMaterial(Material.CHAINMAIL_CHESTPLATE));
		Assert.assertEquals(Material.STONE,
				ToolQuality.getMaterial(Material.CHAINMAIL_HELMET));
		Assert.assertEquals(Material.STONE,
				ToolQuality.getMaterial(Material.CHAINMAIL_LEGGINGS));

		Assert.assertEquals(Material.COBBLESTONE,
				ToolQuality.getMaterial(Material.STONE_AXE));
		Assert.assertEquals(Material.COBBLESTONE,
				ToolQuality.getMaterial(Material.STONE_HOE));
		Assert.assertEquals(Material.COBBLESTONE,
				ToolQuality.getMaterial(Material.STONE_PICKAXE));
		Assert.assertEquals(Material.COBBLESTONE,
				ToolQuality.getMaterial(Material.STONE_SPADE));
		Assert.assertEquals(Material.COBBLESTONE,
				ToolQuality.getMaterial(Material.STONE_SWORD));

		Assert.assertEquals(Material.IRON_INGOT,
				ToolQuality.getMaterial(Material.IRON_BOOTS));
		Assert.assertEquals(Material.IRON_INGOT,
				ToolQuality.getMaterial(Material.IRON_CHESTPLATE));
		Assert.assertEquals(Material.IRON_INGOT,
				ToolQuality.getMaterial(Material.IRON_HELMET));
		Assert.assertEquals(Material.IRON_INGOT,
				ToolQuality.getMaterial(Material.IRON_LEGGINGS));
		Assert.assertEquals(Material.IRON_INGOT,
				ToolQuality.getMaterial(Material.IRON_AXE));
		Assert.assertEquals(Material.IRON_INGOT,
				ToolQuality.getMaterial(Material.IRON_HOE));
		Assert.assertEquals(Material.IRON_INGOT,
				ToolQuality.getMaterial(Material.IRON_PICKAXE));
		Assert.assertEquals(Material.IRON_INGOT,
				ToolQuality.getMaterial(Material.IRON_SPADE));
		Assert.assertEquals(Material.IRON_INGOT,
				ToolQuality.getMaterial(Material.IRON_SWORD));

		Assert.assertEquals(Material.GOLD_INGOT,
				ToolQuality.getMaterial(Material.GOLD_BOOTS));
		Assert.assertEquals(Material.GOLD_INGOT,
				ToolQuality.getMaterial(Material.GOLD_CHESTPLATE));
		Assert.assertEquals(Material.GOLD_INGOT,
				ToolQuality.getMaterial(Material.GOLD_HELMET));
		Assert.assertEquals(Material.GOLD_INGOT,
				ToolQuality.getMaterial(Material.GOLD_LEGGINGS));
		Assert.assertEquals(Material.GOLD_INGOT,
				ToolQuality.getMaterial(Material.GOLD_AXE));
		Assert.assertEquals(Material.GOLD_INGOT,
				ToolQuality.getMaterial(Material.GOLD_HOE));
		Assert.assertEquals(Material.GOLD_INGOT,
				ToolQuality.getMaterial(Material.GOLD_PICKAXE));
		Assert.assertEquals(Material.GOLD_INGOT,
				ToolQuality.getMaterial(Material.GOLD_SPADE));
		Assert.assertEquals(Material.GOLD_INGOT,
				ToolQuality.getMaterial(Material.GOLD_SWORD));

		Assert.assertEquals(Material.DIAMOND,
				ToolQuality.getMaterial(Material.DIAMOND_BOOTS));
		Assert.assertEquals(Material.DIAMOND,
				ToolQuality.getMaterial(Material.DIAMOND_CHESTPLATE));
		Assert.assertEquals(Material.DIAMOND,
				ToolQuality.getMaterial(Material.DIAMOND_HELMET));
		Assert.assertEquals(Material.DIAMOND,
				ToolQuality.getMaterial(Material.DIAMOND_LEGGINGS));
		Assert.assertEquals(Material.DIAMOND,
				ToolQuality.getMaterial(Material.DIAMOND_AXE));
		Assert.assertEquals(Material.DIAMOND,
				ToolQuality.getMaterial(Material.DIAMOND_HOE));
		Assert.assertEquals(Material.DIAMOND,
				ToolQuality.getMaterial(Material.DIAMOND_PICKAXE));
		Assert.assertEquals(Material.DIAMOND,
				ToolQuality.getMaterial(Material.DIAMOND_SPADE));
		Assert.assertEquals(Material.DIAMOND,
				ToolQuality.getMaterial(Material.DIAMOND_SWORD));
	}

	@Test
	public void getQuality() {
		Assert.assertEquals(ToolQuality.NONE,
				ToolQuality.getQuality((ItemStack) null));
		Assert.assertEquals(ToolQuality.NONE,
				ToolQuality.getQuality((Material) null));
		Assert.assertEquals(ToolQuality.NONE,
				ToolQuality.getQuality(new ItemStack(Material.STONE)));
		Assert.assertEquals(ToolQuality.NONE,
				ToolQuality.getQuality(Material.AIR));

		Assert.assertEquals(ToolQuality.WOOD,
				ToolQuality.getQuality(Material.LEATHER_BOOTS));
		Assert.assertEquals(ToolQuality.WOOD,
				ToolQuality.getQuality(Material.LEATHER_CHESTPLATE));
		Assert.assertEquals(ToolQuality.WOOD,
				ToolQuality.getQuality(Material.LEATHER_HELMET));
		Assert.assertEquals(ToolQuality.WOOD,
				ToolQuality.getQuality(Material.LEATHER_LEGGINGS));
		Assert.assertEquals(ToolQuality.WOOD,
				ToolQuality.getQuality(Material.WOOD_AXE));
		Assert.assertEquals(ToolQuality.WOOD,
				ToolQuality.getQuality(Material.WOOD_HOE));
		Assert.assertEquals(ToolQuality.WOOD,
				ToolQuality.getQuality(Material.WOOD_PICKAXE));
		Assert.assertEquals(ToolQuality.WOOD,
				ToolQuality.getQuality(Material.WOOD_SPADE));
		Assert.assertEquals(ToolQuality.WOOD,
				ToolQuality.getQuality(Material.WOOD_SWORD));
		Assert.assertEquals(ToolQuality.WOOD,
				ToolQuality.getQuality(Material.BOW));

		Assert.assertEquals(ToolQuality.STONE,
				ToolQuality.getQuality(Material.CHAINMAIL_BOOTS));
		Assert.assertEquals(ToolQuality.STONE,
				ToolQuality.getQuality(Material.CHAINMAIL_CHESTPLATE));
		Assert.assertEquals(ToolQuality.STONE,
				ToolQuality.getQuality(Material.CHAINMAIL_HELMET));
		Assert.assertEquals(ToolQuality.STONE,
				ToolQuality.getQuality(Material.CHAINMAIL_LEGGINGS));
		Assert.assertEquals(ToolQuality.STONE,
				ToolQuality.getQuality(Material.STONE_AXE));
		Assert.assertEquals(ToolQuality.STONE,
				ToolQuality.getQuality(Material.STONE_HOE));
		Assert.assertEquals(ToolQuality.STONE,
				ToolQuality.getQuality(Material.STONE_PICKAXE));
		Assert.assertEquals(ToolQuality.STONE,
				ToolQuality.getQuality(Material.STONE_SPADE));
		Assert.assertEquals(ToolQuality.STONE,
				ToolQuality.getQuality(Material.STONE_SWORD));

		Assert.assertEquals(ToolQuality.GOLD,
				ToolQuality.getQuality(Material.GOLD_BOOTS));
		Assert.assertEquals(ToolQuality.GOLD,
				ToolQuality.getQuality(Material.GOLD_CHESTPLATE));
		Assert.assertEquals(ToolQuality.GOLD,
				ToolQuality.getQuality(Material.GOLD_HELMET));
		Assert.assertEquals(ToolQuality.GOLD,
				ToolQuality.getQuality(Material.GOLD_LEGGINGS));
		Assert.assertEquals(ToolQuality.GOLD,
				ToolQuality.getQuality(Material.GOLD_AXE));
		Assert.assertEquals(ToolQuality.GOLD,
				ToolQuality.getQuality(Material.GOLD_HOE));
		Assert.assertEquals(ToolQuality.GOLD,
				ToolQuality.getQuality(Material.GOLD_PICKAXE));
		Assert.assertEquals(ToolQuality.GOLD,
				ToolQuality.getQuality(Material.GOLD_SPADE));
		Assert.assertEquals(ToolQuality.GOLD,
				ToolQuality.getQuality(Material.GOLD_SWORD));

		Assert.assertEquals(ToolQuality.IRON,
				ToolQuality.getQuality(Material.IRON_BOOTS));
		Assert.assertEquals(ToolQuality.IRON,
				ToolQuality.getQuality(Material.IRON_CHESTPLATE));
		Assert.assertEquals(ToolQuality.IRON,
				ToolQuality.getQuality(Material.IRON_HELMET));
		Assert.assertEquals(ToolQuality.IRON,
				ToolQuality.getQuality(Material.IRON_LEGGINGS));
		Assert.assertEquals(ToolQuality.IRON,
				ToolQuality.getQuality(Material.IRON_AXE));
		Assert.assertEquals(ToolQuality.IRON,
				ToolQuality.getQuality(Material.IRON_HOE));
		Assert.assertEquals(ToolQuality.IRON,
				ToolQuality.getQuality(Material.IRON_PICKAXE));
		Assert.assertEquals(ToolQuality.IRON,
				ToolQuality.getQuality(Material.IRON_SPADE));
		Assert.assertEquals(ToolQuality.IRON,
				ToolQuality.getQuality(Material.IRON_SWORD));

		Assert.assertEquals(ToolQuality.DIAMOND,
				ToolQuality.getQuality(Material.DIAMOND_BOOTS));
		Assert.assertEquals(ToolQuality.DIAMOND,
				ToolQuality.getQuality(Material.DIAMOND_CHESTPLATE));
		Assert.assertEquals(ToolQuality.DIAMOND,
				ToolQuality.getQuality(Material.DIAMOND_HELMET));
		Assert.assertEquals(ToolQuality.DIAMOND,
				ToolQuality.getQuality(Material.DIAMOND_LEGGINGS));
		Assert.assertEquals(ToolQuality.DIAMOND,
				ToolQuality.getQuality(Material.DIAMOND_AXE));
		Assert.assertEquals(ToolQuality.DIAMOND,
				ToolQuality.getQuality(Material.DIAMOND_HOE));
		Assert.assertEquals(ToolQuality.DIAMOND,
				ToolQuality.getQuality(Material.DIAMOND_PICKAXE));
		Assert.assertEquals(ToolQuality.DIAMOND,
				ToolQuality.getQuality(Material.DIAMOND_SPADE));
		Assert.assertEquals(ToolQuality.DIAMOND,
				ToolQuality.getQuality(Material.DIAMOND_SWORD));
	}

	@Test
	public void isAxe() {
		Assert.assertFalse(ToolQuality.isAxe(null));
		Assert.assertFalse(ToolQuality.isAxe(Material.AIR));

		Assert.assertTrue(ToolQuality.isAxe(Material.WOOD_AXE));
		Assert.assertTrue(ToolQuality.isAxe(Material.STONE_AXE));
		Assert.assertTrue(ToolQuality.isAxe(Material.IRON_AXE));
		Assert.assertTrue(ToolQuality.isAxe(Material.GOLD_AXE));
		Assert.assertTrue(ToolQuality.isAxe(Material.DIAMOND_AXE));
	}

	@Test
	public void isBoots() {
		Assert.assertFalse(ToolQuality.isBoots(null));
		Assert.assertFalse(ToolQuality.isBoots(Material.AIR));

		Assert.assertTrue(ToolQuality.isBoots(Material.LEATHER_BOOTS));
		Assert.assertTrue(ToolQuality.isBoots(Material.CHAINMAIL_BOOTS));
		Assert.assertTrue(ToolQuality.isBoots(Material.IRON_BOOTS));
		Assert.assertTrue(ToolQuality.isBoots(Material.GOLD_BOOTS));
		Assert.assertTrue(ToolQuality.isBoots(Material.DIAMOND_BOOTS));
	}

	@Test
	public void isChestplate() {
		Assert.assertFalse(ToolQuality.isChestplate(null));
		Assert.assertFalse(ToolQuality.isChestplate(Material.AIR));

		Assert.assertTrue(ToolQuality.isChestplate(Material.LEATHER_CHESTPLATE));
		Assert.assertTrue(ToolQuality
				.isChestplate(Material.CHAINMAIL_CHESTPLATE));
		Assert.assertTrue(ToolQuality.isChestplate(Material.IRON_CHESTPLATE));
		Assert.assertTrue(ToolQuality.isChestplate(Material.GOLD_CHESTPLATE));
		Assert.assertTrue(ToolQuality.isChestplate(Material.DIAMOND_CHESTPLATE));
	}

	@Test
	public void isHelmet() {
		Assert.assertFalse(ToolQuality.isHelmet(null));
		Assert.assertFalse(ToolQuality.isHelmet(Material.AIR));

		Assert.assertTrue(ToolQuality.isHelmet(Material.LEATHER_HELMET));
		Assert.assertTrue(ToolQuality.isHelmet(Material.CHAINMAIL_HELMET));
		Assert.assertTrue(ToolQuality.isHelmet(Material.IRON_HELMET));
		Assert.assertTrue(ToolQuality.isHelmet(Material.GOLD_HELMET));
		Assert.assertTrue(ToolQuality.isHelmet(Material.DIAMOND_HELMET));
	}

	@Test
	public void isHoe() {
		Assert.assertFalse(ToolQuality.isHoe(null));
		Assert.assertFalse(ToolQuality.isHoe(Material.AIR));

		Assert.assertTrue(ToolQuality.isHoe(Material.WOOD_HOE));
		Assert.assertTrue(ToolQuality.isHoe(Material.STONE_HOE));
		Assert.assertTrue(ToolQuality.isHoe(Material.IRON_HOE));
		Assert.assertTrue(ToolQuality.isHoe(Material.GOLD_HOE));
		Assert.assertTrue(ToolQuality.isHoe(Material.DIAMOND_HOE));
	}

	@Test
	public void isLeggings() {
		Assert.assertFalse(ToolQuality.isLeggings(null));
		Assert.assertFalse(ToolQuality.isLeggings(Material.AIR));

		Assert.assertTrue(ToolQuality.isLeggings(Material.LEATHER_LEGGINGS));
		Assert.assertTrue(ToolQuality.isLeggings(Material.CHAINMAIL_LEGGINGS));
		Assert.assertTrue(ToolQuality.isLeggings(Material.IRON_LEGGINGS));
		Assert.assertTrue(ToolQuality.isLeggings(Material.GOLD_LEGGINGS));
		Assert.assertTrue(ToolQuality.isLeggings(Material.DIAMOND_LEGGINGS));
	}

	@Test
	public void isPickaxe() {
		Assert.assertFalse(ToolQuality.isPickaxe(null));
		Assert.assertFalse(ToolQuality.isPickaxe(Material.AIR));

		Assert.assertTrue(ToolQuality.isPickaxe(Material.WOOD_PICKAXE));
		Assert.assertTrue(ToolQuality.isPickaxe(Material.STONE_PICKAXE));
		Assert.assertTrue(ToolQuality.isPickaxe(Material.IRON_PICKAXE));
		Assert.assertTrue(ToolQuality.isPickaxe(Material.GOLD_PICKAXE));
		Assert.assertTrue(ToolQuality.isPickaxe(Material.DIAMOND_PICKAXE));
	}

	@Test
	public void isSpade() {
		Assert.assertFalse(ToolQuality.isSpade(null));
		Assert.assertFalse(ToolQuality.isSpade(Material.AIR));

		Assert.assertTrue(ToolQuality.isSpade(Material.WOOD_SPADE));
		Assert.assertTrue(ToolQuality.isSpade(Material.STONE_SPADE));
		Assert.assertTrue(ToolQuality.isSpade(Material.IRON_SPADE));
		Assert.assertTrue(ToolQuality.isSpade(Material.GOLD_SPADE));
		Assert.assertTrue(ToolQuality.isSpade(Material.DIAMOND_SPADE));
	}

	@Test
	public void isSword() {
		Assert.assertFalse(ToolQuality.isSword(null));
		Assert.assertFalse(ToolQuality.isSword(Material.AIR));

		Assert.assertTrue(ToolQuality.isSword(Material.WOOD_SWORD));
		Assert.assertTrue(ToolQuality.isSword(Material.STONE_SWORD));
		Assert.assertTrue(ToolQuality.isSword(Material.IRON_SWORD));
		Assert.assertTrue(ToolQuality.isSword(Material.GOLD_SWORD));
		Assert.assertTrue(ToolQuality.isSword(Material.DIAMOND_SWORD));
	}
}
