package li.cryx.expcraft;

import java.io.InputStream;

import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.perm.AbstractPermissionManager;
import li.cryx.expcraft.persist.AbstractPersistenceManager;
import li.cryx.expcraft.persist.InMemoryPersistentManager;

import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.material.Tree;
import org.bukkit.plugin.PluginDescriptionFile;
import org.junit.Before;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * This class is intended to setup a mocked environment to test
 * {@link ExpCraftModule}s.
 * 
 * <p>
 * This class will mock a subclass of {@link ExpCraftModule} and load the
 * "test-config.yml" defined in the {@link #configFile} field. The config is
 * loaded once (like <code>@BeforeClass</code>), while a new plugin is created
 * for every test (<code>@Before</code>).
 * </p>
 * 
 * <p>
 * It also registers an {@link InMemoryPersistentManager} and mocks an
 * {@link AbstractPermissionManager} that returns <code>true</code> whenever
 * {@link AbstractPermissionManager#worldCheck(World)} is called and the value
 * of {@link #hasModule} when the method
 * {@link AbstractPermissionManager#hasModule(ExpCraftModule, Player)} is
 * invoked.
 * </p>
 * 
 * @author cryxli
 * 
 * @param <T>
 *            The plugin class to mock.
 */
public abstract class AbstractPluginTest<T extends ExpCraftModule> {
	/** Loaded configuration file. */
	protected YamlConfiguration config;

	/** Mocked {@link ExpCraftModule} subclass. */
	protected T plugin;

	/** Indicator for do-once stuff. */
	private boolean done = false;

	/** Which config file to load. */
	protected String configFile = "test-config.yml";

	protected AbstractPermissionManager perm;

	protected boolean hasModule;

	protected AbstractPersistenceManager pers;

	protected Block getBlock(final Material material) {
		return getBlock(material, 0);
	}

	protected Block getBlock(final Material material, final int data) {
		Block block = Mockito.mock(Block.class);
		Mockito.when(block.getType()).thenReturn(material);
		Mockito.when(block.getTypeId()).thenReturn(material.getId());
		Mockito.when(block.getData()).thenReturn((byte) data);
		return block;
	}

	protected Block getBlock(final Material material, final TreeSpecies type) {
		BlockState state = Mockito.mock(BlockState.class);
		Mockito.when(state.getData()).thenReturn(new Tree(type));

		Block block = getBlock(material, type.getData());
		Mockito.when(block.getState()).thenReturn(state);
		Mockito.when(block.getWorld()).thenReturn(null);

		return block;
	}

	/**
	 * The subclass must define which {@link ExpCraftModule} should be mocked.
	 * 
	 * @return The class of the module's entry point class.
	 */
	protected abstract Class<T> getClazz();

	/** Load the stated config YAML. */
	private void loadConfig() {
		InputStream in = ClassLoader.getSystemResourceAsStream(configFile);
		config = YamlConfiguration.loadConfiguration(in);
	}

	@Before
	public void prepareTest() {
		if (!done) {
			loadConfig();
			done = true;
		}
		resetPlugin();
	}

	/** Reset plugin mock */
	protected void resetPlugin() {
		hasModule = false;

		plugin = Mockito.mock(getClazz());
		Mockito.when(plugin.getConfig()).thenReturn(config);
		Mockito.when(plugin.getModuleName()).thenReturn("Test");
		Mockito.when(plugin.getAbbr()).thenReturn("T");
		Mockito.when(plugin.getDescription()).thenReturn(
				new PluginDescriptionFile("Test", "0", ""));

		perm = Mockito.mock(AbstractPermissionManager.class);
		Mockito.when(
				perm.hasModule(Mockito.eq(plugin), Mockito.any(Player.class)))
				.thenAnswer(new Answer<Boolean>() {
					public Boolean answer(final InvocationOnMock invocation)
							throws Throwable {
						return hasModule;
					}
				});
		Mockito.when(perm.worldCheck(Mockito.any(World.class)))
				.thenReturn(true);
		Mockito.when(plugin.getPermission()).thenReturn(perm);

		pers = new InMemoryPersistentManager();
		Mockito.when(plugin.getPersistence()).thenReturn(pers);
	}
}
