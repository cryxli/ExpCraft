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
package li.cryx.expcraft.defence;

import junit.framework.Assert;
import li.cryx.expcraft.AbstractPluginTest;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

/**
 * Tests externalised logic for {@link DefenceEntityListener}.
 * 
 * @author cryxli
 */
public class DefenceConstraintsTest extends AbstractPluginTest<Defence> {

	private DefenceConstraints test;

	/** Test for {@link DefenceConstraints#checkArmor(Player)} */
	@Test
	public void checkArmor() {
		PlayerInventory inv = Mockito.mock(PlayerInventory.class);
		Mockito.when(inv.getBoots()).thenReturn(
				new ItemStack(Material.LEATHER_BOOTS));
		Mockito.when(inv.getLeggings()).thenReturn(
				new ItemStack(Material.LEATHER_LEGGINGS));
		Mockito.when(inv.getChestplate()).thenReturn(
				new ItemStack(Material.LEATHER_CHESTPLATE));
		Mockito.when(inv.getHelmet()).thenReturn(
				new ItemStack(Material.LEATHER_HELMET));
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("Player");
		Mockito.when(player.getInventory()).thenReturn(inv);
		plugin.getPersistence().setLevel(plugin, player, 50);
		DefenceConstraints mock = Mockito.spy(test);
		Assert.assertTrue(mock.checkArmor(player));
		Mockito.verify(mock).checkBoots(player, 50, "Leather");
		Mockito.verify(mock).checkLeggings(player, 50, "Leather");
		Mockito.verify(mock).checkChestplate(player, 50, "Leather");
		Mockito.verify(mock).checkHelmet(player, 50, "Leather");
	}

	/**
	 * Test for
	 * {@link DefenceConstraints#checkArmorPiece(Player, int, ItemStack)}
	 */
	@Test
	public void checkArmorPiece() {
		DefenceConstraints mock = Mockito.spy(test);
		// boots
		mock.checkArmorPiece(null, 50, new ItemStack(Material.LEATHER_BOOTS));
		Mockito.verify(mock).checkBoots(null, 50, "Leather");
		mock.checkArmorPiece(null, 50, new ItemStack(Material.CHAINMAIL_BOOTS));
		Mockito.verify(mock).checkBoots(null, 50, "Chainmail");
		mock.checkArmorPiece(null, 50, new ItemStack(Material.IRON_BOOTS));
		Mockito.verify(mock).checkBoots(null, 50, "Iron");
		mock.checkArmorPiece(null, 50, new ItemStack(Material.GOLD_BOOTS));
		Mockito.verify(mock).checkBoots(null, 50, "Gold");
		mock.checkArmorPiece(null, 50, new ItemStack(Material.DIAMOND_BOOTS));
		Mockito.verify(mock).checkBoots(null, 50, "Diamond");
		// leggings
		mock.checkArmorPiece(null, 50, new ItemStack(Material.LEATHER_LEGGINGS));
		Mockito.verify(mock).checkLeggings(null, 50, "Leather");
		mock.checkArmorPiece(null, 50, new ItemStack(
				Material.CHAINMAIL_LEGGINGS));
		Mockito.verify(mock).checkLeggings(null, 50, "Chainmail");
		mock.checkArmorPiece(null, 50, new ItemStack(Material.IRON_LEGGINGS));
		Mockito.verify(mock).checkLeggings(null, 50, "Iron");
		mock.checkArmorPiece(null, 50, new ItemStack(Material.GOLD_LEGGINGS));
		Mockito.verify(mock).checkLeggings(null, 50, "Gold");
		mock.checkArmorPiece(null, 50, new ItemStack(Material.DIAMOND_LEGGINGS));
		Mockito.verify(mock).checkLeggings(null, 50, "Diamond");
		// chestplates
		mock.checkArmorPiece(null, 50, new ItemStack(
				Material.LEATHER_CHESTPLATE));
		Mockito.verify(mock).checkChestplate(null, 50, "Leather");
		mock.checkArmorPiece(null, 50, new ItemStack(
				Material.CHAINMAIL_CHESTPLATE));
		Mockito.verify(mock).checkChestplate(null, 50, "Chainmail");
		mock.checkArmorPiece(null, 50, new ItemStack(Material.IRON_CHESTPLATE));
		Mockito.verify(mock).checkChestplate(null, 50, "Iron");
		mock.checkArmorPiece(null, 50, new ItemStack(Material.GOLD_CHESTPLATE));
		Mockito.verify(mock).checkChestplate(null, 50, "Gold");
		mock.checkArmorPiece(null, 50, new ItemStack(
				Material.DIAMOND_CHESTPLATE));
		Mockito.verify(mock).checkChestplate(null, 50, "Diamond");
		// helmets
		mock.checkArmorPiece(null, 50, new ItemStack(Material.LEATHER_HELMET));
		Mockito.verify(mock).checkHelmet(null, 50, "Leather");
		mock.checkArmorPiece(null, 50, new ItemStack(Material.CHAINMAIL_HELMET));
		Mockito.verify(mock).checkHelmet(null, 50, "Chainmail");
		mock.checkArmorPiece(null, 50, new ItemStack(Material.IRON_HELMET));
		Mockito.verify(mock).checkHelmet(null, 50, "Iron");
		mock.checkArmorPiece(null, 50, new ItemStack(Material.GOLD_HELMET));
		Mockito.verify(mock).checkHelmet(null, 50, "Gold");
		mock.checkArmorPiece(null, 50, new ItemStack(Material.DIAMOND_HELMET));
		Mockito.verify(mock).checkHelmet(null, 50, "Diamond");
	}

	/** Test for {@link DefenceConstraints#checkBoots(Player, int, String)} */
	@Test
	public void checkBoots() {
		for (String s : new String[] { "Leather", "Chainmail", "Iron", "Gold",
				"Diamond" }) {
			test.checkBoots(null, 50, s);
			try {
				test.checkBoots(null, -1, s);
				Assert.fail();
			} catch (NullPointerException e) {
				// expected
			}
		}

		// inventory full
		PlayerInventory inv = Mockito.mock(PlayerInventory.class);
		Mockito.when(inv.getBoots()).thenReturn(
				new ItemStack(Material.IRON_BOOTS, 1, (short) 10));
		Mockito.when(inv.firstEmpty()).thenReturn(-1);
		World world = Mockito.mock(World.class);
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getWorld()).thenReturn(world);
		Mockito.when(player.getInventory()).thenReturn(inv);
		Mockito.when(player.getLocation()).thenReturn(null);

		test.checkBoots(player, 1, "Iron");

		ArgumentCaptor<ItemStack> stack = ArgumentCaptor
				.forClass(ItemStack.class);
		Mockito.verify(inv).setBoots(null);
		Mockito.verify(world).dropItem(Mockito.eq((Location) null),
				stack.capture());
		Assert.assertEquals(Material.IRON_BOOTS, stack.getValue().getType());
		Assert.assertEquals(1, stack.getValue().getAmount());
		Assert.assertEquals(10, stack.getValue().getDurability());

		// inventory not empty
		inv = Mockito.mock(PlayerInventory.class);
		Mockito.when(inv.getBoots()).thenReturn(
				new ItemStack(Material.IRON_BOOTS, 1, (short) 10));
		Mockito.when(inv.firstEmpty()).thenReturn(5);
		player = Mockito.mock(Player.class);
		Mockito.when(player.getInventory()).thenReturn(inv);
		Mockito.when(player.getLocation()).thenReturn(null);

		test.checkBoots(player, 1, "Iron");

		stack = ArgumentCaptor.forClass(ItemStack.class);
		Mockito.verify(inv).setBoots(null);
		Mockito.verify(inv).setItem(Mockito.eq(5), stack.capture());
		Assert.assertEquals(Material.IRON_BOOTS, stack.getValue().getType());
		Assert.assertEquals(1, stack.getValue().getAmount());
		Assert.assertEquals(10, stack.getValue().getDurability());
	}

	/** Test for {@link DefenceConstraints#checkChestplate(Player, int, String)} */
	@Test
	public void checkChestplate() {
		for (String s : new String[] { "Leather", "Chainmail", "Iron", "Gold",
				"Diamond" }) {
			test.checkChestplate(null, 50, s);
			try {
				test.checkChestplate(null, -1, s);
				Assert.fail();
			} catch (NullPointerException e) {
				// expected
			}
		}

		// inventory full
		PlayerInventory inv = Mockito.mock(PlayerInventory.class);
		Mockito.when(inv.getChestplate()).thenReturn(
				new ItemStack(Material.IRON_CHESTPLATE, 1, (short) 10));
		Mockito.when(inv.firstEmpty()).thenReturn(-1);
		World world = Mockito.mock(World.class);
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getWorld()).thenReturn(world);
		Mockito.when(player.getInventory()).thenReturn(inv);
		Mockito.when(player.getLocation()).thenReturn(null);

		test.checkChestplate(player, 1, "Iron");

		ArgumentCaptor<ItemStack> stack = ArgumentCaptor
				.forClass(ItemStack.class);
		Mockito.verify(inv).setChestplate(null);
		Mockito.verify(world).dropItem(Mockito.eq((Location) null),
				stack.capture());
		Assert.assertEquals(Material.IRON_CHESTPLATE, stack.getValue()
				.getType());
		Assert.assertEquals(1, stack.getValue().getAmount());
		Assert.assertEquals(10, stack.getValue().getDurability());

		// inventory not empty
		inv = Mockito.mock(PlayerInventory.class);
		Mockito.when(inv.getChestplate()).thenReturn(
				new ItemStack(Material.IRON_CHESTPLATE, 1, (short) 10));
		Mockito.when(inv.firstEmpty()).thenReturn(5);
		player = Mockito.mock(Player.class);
		Mockito.when(player.getInventory()).thenReturn(inv);
		Mockito.when(player.getLocation()).thenReturn(null);

		test.checkChestplate(player, 1, "Iron");

		stack = ArgumentCaptor.forClass(ItemStack.class);
		Mockito.verify(inv).setChestplate(null);
		Mockito.verify(inv).setItem(Mockito.eq(5), stack.capture());
		Assert.assertEquals(Material.IRON_CHESTPLATE, stack.getValue()
				.getType());
		Assert.assertEquals(1, stack.getValue().getAmount());
		Assert.assertEquals(10, stack.getValue().getDurability());
	}

	/** Test for {@link DefenceConstraints#checkHelmet(Player, int, String)} */
	@Test
	public void checkHelmet() {
		for (String s : new String[] { "Leather", "Chainmail", "Iron", "Gold",
				"Diamond" }) {
			test.checkHelmet(null, 50, s);
			try {
				test.checkHelmet(null, -1, s);
				Assert.fail();
			} catch (NullPointerException e) {
				// expected
			}
		}

		// inventory full
		PlayerInventory inv = Mockito.mock(PlayerInventory.class);
		Mockito.when(inv.getHelmet()).thenReturn(
				new ItemStack(Material.IRON_HELMET, 1, (short) 10));
		Mockito.when(inv.firstEmpty()).thenReturn(-1);
		World world = Mockito.mock(World.class);
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getWorld()).thenReturn(world);
		Mockito.when(player.getInventory()).thenReturn(inv);
		Mockito.when(player.getLocation()).thenReturn(null);

		test.checkHelmet(player, 1, "Iron");

		ArgumentCaptor<ItemStack> stack = ArgumentCaptor
				.forClass(ItemStack.class);
		Mockito.verify(inv).setHelmet(null);
		Mockito.verify(world).dropItem(Mockito.eq((Location) null),
				stack.capture());
		Assert.assertEquals(Material.IRON_HELMET, stack.getValue().getType());
		Assert.assertEquals(1, stack.getValue().getAmount());
		Assert.assertEquals(10, stack.getValue().getDurability());

		// inventory not empty
		inv = Mockito.mock(PlayerInventory.class);
		Mockito.when(inv.getHelmet()).thenReturn(
				new ItemStack(Material.IRON_HELMET, 1, (short) 10));
		Mockito.when(inv.firstEmpty()).thenReturn(5);
		player = Mockito.mock(Player.class);
		Mockito.when(player.getInventory()).thenReturn(inv);
		Mockito.when(player.getLocation()).thenReturn(null);

		test.checkHelmet(player, 1, "Iron");

		stack = ArgumentCaptor.forClass(ItemStack.class);
		Mockito.verify(inv).setHelmet(null);
		Mockito.verify(inv).setItem(Mockito.eq(5), stack.capture());
		Assert.assertEquals(Material.IRON_HELMET, stack.getValue().getType());
		Assert.assertEquals(1, stack.getValue().getAmount());
		Assert.assertEquals(10, stack.getValue().getDurability());
	}

	/** Test for {@link DefenceConstraints#checkLeggings(Player, int, String)} */
	@Test
	public void checkLeggings() {
		for (String s : new String[] { "Leather", "Chainmail", "Iron", "Gold",
				"Diamond" }) {
			test.checkLeggings(null, 50, s);
			try {
				test.checkLeggings(null, -1, s);
				Assert.fail();
			} catch (NullPointerException e) {
				// expected
			}
		}

		// inventory full
		PlayerInventory inv = Mockito.mock(PlayerInventory.class);
		Mockito.when(inv.getLeggings()).thenReturn(
				new ItemStack(Material.IRON_LEGGINGS, 1, (short) 10));
		Mockito.when(inv.firstEmpty()).thenReturn(-1);
		World world = Mockito.mock(World.class);
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getWorld()).thenReturn(world);
		Mockito.when(player.getInventory()).thenReturn(inv);
		Mockito.when(player.getLocation()).thenReturn(null);

		test.checkLeggings(player, 1, "Iron");

		ArgumentCaptor<ItemStack> stack = ArgumentCaptor
				.forClass(ItemStack.class);
		Mockito.verify(inv).setLeggings(null);
		Mockito.verify(world).dropItem(Mockito.eq((Location) null),
				stack.capture());
		Assert.assertEquals(Material.IRON_LEGGINGS, stack.getValue().getType());
		Assert.assertEquals(1, stack.getValue().getAmount());
		Assert.assertEquals(10, stack.getValue().getDurability());

		// inventory not empty
		inv = Mockito.mock(PlayerInventory.class);
		Mockito.when(inv.getLeggings()).thenReturn(
				new ItemStack(Material.IRON_LEGGINGS, 1, (short) 10));
		Mockito.when(inv.firstEmpty()).thenReturn(5);
		player = Mockito.mock(Player.class);
		Mockito.when(player.getInventory()).thenReturn(inv);
		Mockito.when(player.getLocation()).thenReturn(null);

		test.checkLeggings(player, 1, "Iron");

		stack = ArgumentCaptor.forClass(ItemStack.class);
		Mockito.verify(inv).setLeggings(null);
		Mockito.verify(inv).setItem(Mockito.eq(5), stack.capture());
		Assert.assertEquals(Material.IRON_LEGGINGS, stack.getValue().getType());
		Assert.assertEquals(1, stack.getValue().getAmount());
		Assert.assertEquals(10, stack.getValue().getDurability());
	}

	@Override
	protected Class<Defence> getClazz() {
		return Defence.class;
	}

	/** Test for {@link DefenceConstraints#isArmor(Material)} */
	@Test
	public void isArmor() {
		// armor by material
		for (String mat : new String[] { "LEATHER_", "CHAINMAIL_", "IRON_",
				"GOLD_", "DIAMOND_" }) {
			// armor by piece
			for (String piece : new String[] { "BOOTS", "CHESTPLATE", "HELMET",
					"LEGGINGS" }) {
				Material material = Material.valueOf(mat + piece);
				Assert.assertTrue(test.isArmor(material));
			}
		}

		// others
		Assert.assertFalse(test.isArmor(Material.STONE));
		Assert.assertFalse(test.isArmor(Material.DIRT));
		Assert.assertFalse(test.isArmor(Material.AIR));
	}

	/** Test for {@link DefenceConstraints#isWearingArmor(Player)} */
	@Test
	public void isWearingArmor() {
		isWearingArmorTest(Material.AIR, Material.AIR, Material.AIR,
				Material.AIR, false);
		isWearingArmorTest(Material.AIR, Material.AIR, Material.AIR,
				Material.LEATHER_HELMET, true);
		isWearingArmorTest(Material.AIR, Material.AIR,
				Material.LEATHER_CHESTPLATE, Material.AIR, true);
		isWearingArmorTest(Material.AIR, Material.LEATHER_LEGGINGS,
				Material.AIR, Material.AIR, true);
		isWearingArmorTest(Material.LEATHER_BOOTS, Material.AIR, Material.AIR,
				Material.AIR, true);
	}

	private void isWearingArmorTest(final Material boots,
			final Material leggings, final Material chest,
			final Material helmet, final boolean result) {
		PlayerInventory inv = Mockito.mock(PlayerInventory.class);
		Mockito.when(inv.getBoots()).thenReturn(new ItemStack(boots));
		Mockito.when(inv.getLeggings()).thenReturn(new ItemStack(leggings));
		Mockito.when(inv.getChestplate()).thenReturn(new ItemStack(chest));
		Mockito.when(inv.getHelmet()).thenReturn(new ItemStack(helmet));

		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getInventory()).thenReturn(inv);

		Assert.assertEquals(result, test.isWearingArmor(player));
	}

	@Before
	public void prepareTestSpecific() {
		test = new DefenceConstraints(plugin);
	}
}
