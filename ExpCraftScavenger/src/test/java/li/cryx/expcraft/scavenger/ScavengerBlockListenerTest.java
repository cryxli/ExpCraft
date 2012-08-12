package li.cryx.expcraft.scavenger;

import li.cryx.expcraft.AbstractPluginTest;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Tests the block listeners.
 * 
 * @author cryxli
 */
public class ScavengerBlockListenerTest extends AbstractPluginTest<Scavenger> {

	private ScavengerBlockListener listener;

	@Override
	protected Class<Scavenger> getClazz() {
		return Scavenger.class;
	}

	private void playerDigs(final Material material, final Environment worldType) {
		hasModule = true;
		World world = Mockito.mock(World.class);
		Mockito.when(world.getEnvironment()).thenReturn(worldType);
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("Player");
		Mockito.when(player.getWorld()).thenReturn(world);
		Block block = Mockito.mock(Block.class);
		Mockito.when(block.getWorld()).thenReturn(world);
		Mockito.when(block.getType()).thenReturn(material);
		BlockBreakEvent event = new BlockBreakEvent(block, player);

		listener.onBlockBreak(event);

		Mockito.verify(player).getWorld();
		Mockito.verify(world).getEnvironment();
	}

	/** Test 3 */
	@Test
	public void playerDigsDirt() {
		playerDigs(Material.DIRT, Environment.NORMAL);
	}

	/** Test 4 */
	@Test
	public void playerDigsSoulsand() {
		playerDigs(Material.SOUL_SAND, Environment.NETHER);
	}

	/** Test 2 */
	@Test
	public void playerDigsStone() {
		hasModule = true;
		Player player = Mockito.mock(Player.class);
		Block block = Mockito.mock(Block.class);
		Mockito.when(block.getType()).thenReturn(Material.STONE);
		BlockBreakEvent event = new BlockBreakEvent(block, player);

		listener.onBlockBreak(event);

		Mockito.verify(player, Mockito.never()).getName();
	}

	/** Test 1 */
	@Test
	public void playerDoesNotHaveModule() {
		Block block = Mockito.mock(Block.class);
		BlockBreakEvent event = new BlockBreakEvent(block, null);
		listener.onBlockBreak(event);
		Mockito.verify(block, Mockito.never()).getType();
	}

	@Before
	public void prepareTestSpecific() {
		Mockito.when(plugin.getConfInt(Mockito.anyString()))
				.thenCallRealMethod();
		Mockito.when(plugin.getConfDouble(Mockito.anyString()))
				.thenCallRealMethod();

		listener = new ScavengerBlockListener(plugin);
	}

}
