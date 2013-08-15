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
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test {@link DexterityEntityListener}.
 * 
 * @author cryxli
 */
public class DexterityEntityListenerTest extends AbstractPluginTest<Dexterity> {

	private DexterityEntityListener listener;

	@Override
	protected Class<Dexterity> getClazz() {
		return Dexterity.class;
	}

	/**
	 * Test the normal case of the listeners granting dmg reduction.
	 * 
	 * @param level
	 *            Player's level
	 * @param reduction
	 *            Expected dmg reduction.
	 */
	private void normalCase(final int level, final int reduction) {
		hasModule = true;
		PlayerInventory inv = Mockito.mock(PlayerInventory.class);
		Mockito.when(inv.getBoots()).thenReturn(
				new ItemStack(Material.LEATHER_BOOTS));
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("Player");
		plugin.getPersistence().setLevel(plugin, player, level);
		Mockito.when(player.getInventory()).thenReturn(inv);
		EntityDamageEvent event = new EntityDamageEvent(player,
				DamageCause.FALL, 20.0);
		listener.onEntityDamage(event);
		Assert.assertEquals(20.0 - reduction, event.getDamage(), 0);
	}

	/** Test 1 */
	@Test
	public void playerDoesNotHaveModule() {
		Player player = Mockito.mock(Player.class);
		EntityDamageEvent event = new EntityDamageEvent(player,
				DamageCause.FIRE, 20.0);
		listener.onEntityDamage(event);
		Assert.assertEquals(0, plugin.getPersistence().getExp(plugin, player),
				0);
		Assert.assertEquals(20.0, event.getDamage(), 0);

		event = new EntityDamageEvent(player, DamageCause.FALL, 20.0);
		listener.onEntityDamage(event);
		Assert.assertEquals(20.0, event.getDamage(), 0);

		// if one of the cases fails, a NullPointerException is thrown
	}

	/** Test 2 */
	@Test
	public void playerHasNoBoots() {
		hasModule = true;
		PlayerInventory inv = Mockito.mock(PlayerInventory.class);
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getInventory()).thenReturn(inv);
		EntityDamageEvent event = new EntityDamageEvent(player,
				DamageCause.FALL, 20.0);
		listener.onEntityDamage(event);
		Assert.assertEquals(20.0, event.getDamage(), 0);
	}

	@Before
	public void prepareTestSpecific() {
		Mockito.when(plugin.getLevelCap()).thenReturn(100);
		listener = new DexterityEntityListener(plugin);
	}

	/** Test 3 */
	@Test
	public void testDmgReduction() {
		for (int level = 1; level <= 100; level++) {
			normalCase(level, level / 10);
		}
	}

}
