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
package li.cryx.expcraft.dexterity;

import junit.framework.Assert;
import li.cryx.expcraft.AbstractPluginTest;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test the decision logic of the dexterity module.
 * 
 * @author cryxli
 */
public class DexterityConstraintsTest extends AbstractPluginTest<Dexterity> {

	private DexterityConstraints test;

	@Test
	public void checkBoots() {
		checkBoots(Material.LEATHER_BOOTS, "Leather", 0);
		checkBoots(Material.CHAINMAIL_BOOTS, "Chainmail", 5);
		checkBoots(Material.GOLD_BOOTS, "Gold", 20);
		checkBoots(Material.IRON_BOOTS, "Iron", 10);
		checkBoots(Material.DIAMOND_BOOTS, "Diamond", 30);
	}

	private void checkBoots(final Material material, final String config,
			final int level) {
		PlayerInventory inv = Mockito.mock(PlayerInventory.class);
		Mockito.when(inv.getBoots()).thenReturn(new ItemStack(material));
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("Player");
		Mockito.when(player.getInventory()).thenReturn(inv);

		Assert.assertFalse(test.checkBoots(player, -1));
		Mockito.verify(plugin).warnBoots(player, config, level);

		Assert.assertTrue(test.checkBoots(player, 50));
	}

	@Override
	protected Class<Dexterity> getClazz() {
		return Dexterity.class;
	}

	@Test
	public void isBoots() {
		Assert.assertFalse(test.isBoots(null));
		Assert.assertFalse(test.isBoots(new ItemStack(Material.APPLE)));
		Assert.assertTrue(test.isBoots(new ItemStack(Material.LEATHER_BOOTS)));
		Assert.assertTrue(test.isBoots(new ItemStack(Material.CHAINMAIL_BOOTS)));
		Assert.assertTrue(test.isBoots(new ItemStack(Material.GOLD_BOOTS)));
		Assert.assertTrue(test.isBoots(new ItemStack(Material.IRON_BOOTS)));
		Assert.assertTrue(test.isBoots(new ItemStack(Material.DIAMOND_BOOTS)));
	}

	@Before
	public void prepareTestSpecific() {
		test = new DexterityConstraints(plugin);
	}

}
