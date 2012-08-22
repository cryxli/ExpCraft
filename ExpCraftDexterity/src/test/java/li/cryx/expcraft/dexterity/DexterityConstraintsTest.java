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

public class DexterityConstraintsTest extends AbstractPluginTest<Dexterity> {

	private DexterityConstraints test;

	@Test
	public void checkBoots() {
		checkBoots(Material.LEATHER_BOOTS, "Leather");
		checkBoots(Material.CHAINMAIL_BOOTS, "Chainmail");
		checkBoots(Material.GOLD_BOOTS, "Gold");
		checkBoots(Material.IRON_BOOTS, "Iron");
		checkBoots(Material.DIAMOND_BOOTS, "Diamond");
	}

	private void checkBoots(final Material material, final String config) {
		PlayerInventory inv = Mockito.mock(PlayerInventory.class);
		Mockito.when(inv.getBoots()).thenReturn(new ItemStack(material));
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("Player");
		Mockito.when(player.getInventory()).thenReturn(inv);

		Assert.assertFalse(test.checkBoots(player, -1));
		Mockito.verify(plugin).warnBoots(player, config,
				plugin.getConfInt("BootsLevel." + config));

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
		Mockito.when(plugin.getConfInt(Mockito.anyString()))
				.thenCallRealMethod();
		Mockito.when(plugin.getConfDouble(Mockito.anyString()))
				.thenCallRealMethod();

		test = new DexterityConstraints(plugin);
	}

}
