package li.cryx.expcraft.defence;

import junit.framework.Assert;
import li.cryx.expcraft.AbstractPluginTest;
import li.cryx.expcraft.ExpCraftCoreStub;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginDescriptionFile;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class DefenceEntityListenerTest extends AbstractPluginTest<Defence> {

	private DefenceEntityListener test;

	@Override
	protected Class<Defence> getClazz() {
		return Defence.class;
	}

	private void hit(final int dmg, final int level, final int expDmg) {
		hasModule = true;
		PlayerInventory inv = Mockito.mock(PlayerInventory.class);
		Mockito.when(inv.getBoots()).thenReturn(
				new ItemStack(Material.LEATHER_BOOTS));
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("Player");
		Mockito.when(player.getInventory()).thenReturn(inv);
		EntityDamageEvent event = new EntityDamageEvent(player,
				DamageCause.ENTITY_ATTACK, dmg);
		plugin.getPersistence().setLevel(plugin, player, level);
		double oldExp = plugin.getPersistence().getExp(plugin, player);

		test.onEntityDamage(event);

		Assert.assertEquals(2 * dmg,
				plugin.getPersistence().getExp(plugin, player) - oldExp, 0);
		Assert.assertEquals(expDmg, event.getDamage());
		Assert.assertFalse(event.isCancelled());
	}

	@Test
	public void onEntityDamage() {
		hit(1, 1, 1);

		// dmg = 20
		for (int level = 1; level < 100; level++) {
			hit(20, level, 20 - Math.max(0, (level - 1) / 5));
		}

		// dmg = 10
		for (int level = 1; level < 100; level++) {
			hit(10, level, 10 - Math.max(0, (level - 1) / 10));
		}
	}

	@Before
	public void prepareTestSpecific() {
		Mockito.when(plugin.getConfInt(Mockito.anyString()))
				.thenCallRealMethod();
		Mockito.when(plugin.getConfDouble(Mockito.anyString()))
				.thenCallRealMethod();
		Mockito.when(plugin.getLevelCap()).thenReturn(100);

		Server server = Mockito.mock(Server.class);
		Mockito.when(server.getOnlinePlayers()).thenReturn(new Player[] {});
		ExpCraftCoreStub core = new ExpCraftCoreStub(server,
				new PluginDescriptionFile("ExpCraft", "0", ""));
		pers.setCore(core);

		test = new DefenceEntityListener(plugin);
	}
}
