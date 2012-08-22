package li.cryx.expcraft.dexterity;

import junit.framework.Assert;
import li.cryx.expcraft.AbstractPluginTest;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class DexterityPlayerListenerTest extends AbstractPluginTest<Dexterity> {

	private DexterityPlayerListener listener;

	@Override
	protected Class<Dexterity> getClazz() {
		return Dexterity.class;
	}

	/** Test 1 */
	@Test
	public void playerDoesNotHavePlugin() {
		Player player = Mockito.mock(Player.class);
		PlayerMoveEvent event = new PlayerMoveEvent(player, null, null);
		listener.onPlayerMove(event);
		// test will cause a NullPointerException, if anything goes wrong

		Assert.assertEquals(0, plugin.getPersistence().getExp(plugin, player),
				0);
	}

	/** Test 2 */
	@Test
	public void playerFalls() {
		hasModule = true;
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("Player");
		Location from = new Location(null, 0, 65, 0);
		Location to = new Location(null, 0, 64, 0);
		PlayerMoveEvent event = new PlayerMoveEvent(player, from, to);
		listener.onPlayerMove(event);

		Assert.assertEquals(0, plugin.getPersistence().getExp(plugin, player),
				0);
	}

	/** Test 4 */
	@Test
	public void playerJumps() {
		hasModule = true;
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("Player");
		Block block = Mockito.mock(Block.class);
		Mockito.when(block.getType()).thenReturn(Material.AIR);
		World world = Mockito.mock(World.class);
		Mockito.when(world.getBlockAt(Mockito.any(Location.class))).thenReturn(
				block);
		Location from = new Location(world, 0, 64, 0);
		Location to = new Location(world, 0, 65, 0);
		PlayerMoveEvent event = new PlayerMoveEvent(player, from, to);
		listener.onPlayerMove(event);

		Assert.assertEquals(1, plugin.getPersistence().getExp(plugin, player),
				0);
	}

	/** Test 3 */
	@Test
	public void playerSwimsUp() {
		hasModule = true;
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("Player");
		Block block = Mockito.mock(Block.class);
		Mockito.when(block.getType()).thenReturn(Material.WATER);
		World world = Mockito.mock(World.class);
		Mockito.when(world.getBlockAt(Mockito.any(Location.class))).thenReturn(
				block);
		Location from = new Location(world, 0, 64, 0);
		Location to = new Location(world, 0, 65, 0);
		PlayerMoveEvent event = new PlayerMoveEvent(player, from, to);
		listener.onPlayerMove(event);

		Assert.assertEquals(0, plugin.getPersistence().getExp(plugin, player),
				0);
	}

	@Before
	public void prepareTestSpecific() {
		Mockito.when(plugin.getConfInt(Mockito.anyString()))
				.thenCallRealMethod();
		Mockito.when(plugin.getConfDouble(Mockito.anyString()))
				.thenCallRealMethod();

		listener = new DexterityPlayerListener(plugin);
	}

}
