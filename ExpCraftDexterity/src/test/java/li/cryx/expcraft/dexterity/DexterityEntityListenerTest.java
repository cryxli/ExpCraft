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
				DamageCause.FALL, 20);
		listener.onEntityDamage(event);
		Assert.assertEquals(20 - reduction, event.getDamage());
	}

	/** Test 1 */
	@Test
	public void playerDoesNotHaveModule() {
		Player player = Mockito.mock(Player.class);
		EntityDamageEvent event = new EntityDamageEvent(player,
				DamageCause.FIRE, 20);
		listener.onEntityDamage(event);
		Assert.assertEquals(0, plugin.getPersistence().getExp(plugin, player),
				0);
		Assert.assertEquals(20, event.getDamage());

		event = new EntityDamageEvent(player, DamageCause.FALL, 20);
		listener.onEntityDamage(event);
		Assert.assertEquals(20, event.getDamage());

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
				DamageCause.FALL, 20);
		listener.onEntityDamage(event);
		Assert.assertEquals(20, event.getDamage());
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
