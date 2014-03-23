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
package li.cryx.expcraft;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import li.cryx.expcraft.loader.ModuleInfo;
import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.perm.AbstractPermissionManager;
import li.cryx.expcraft.persist.AbstractPersistenceManager;
import li.cryx.expcraft.persist.InMemoryPersistentManager;
import li.cryx.expcraft.util.TypedProperties;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.TreeSpecies;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Tree;
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
 * "test-config.properties" defined in the {@link #configFile} field. The config
 * is loaded once (like <code>@BeforeClass</code>), while a new plugin is
 * created for every test (<code>@Before</code>).
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
	protected TypedProperties config;

	/** Mocked {@link ExpCraftModule} subclass. */
	protected T plugin;

	/** Indicator for do-once stuff. */
	private boolean done = false;

	/** Which config file to load. */
	protected String configFile = "test-config.properties";

	/** A mocked permission manager. */
	protected AbstractPermissionManager perm;

	/** Indicator whether player is affected by the module. */
	protected boolean hasModule;

	/** The in-memory persistence manager. */
	protected AbstractPersistenceManager pers;

	/** A mocked bukkit server */
	protected Server server;

	/**
	 * Create a mocked <code>Block</code>.
	 * 
	 * @param material
	 *            Material of the block
	 * @return The created block
	 */
	protected Block getBlock(final Material material) {
		return getBlock(material, 0);
	}

	/**
	 * Create a mocked <code>Block</code>.
	 * 
	 * @param material
	 *            Material of the block
	 * @param data
	 *            Data value (color, material,damage, durability) of the block
	 * @return The created block
	 */
	protected Block getBlock(final Material material, final int data) {
		Block block = Mockito.mock(Block.class);
		Mockito.when(block.getType()).thenReturn(material);
		// Mockito.when(block.getTypeId()).thenReturn(material.getId());
		Mockito.when(block.getData()).thenReturn((byte) data);
		return block;
	}

	/**
	 * Create a tree related mocked <code>Block</code>.
	 * 
	 * @param material
	 *            Material of the block (sapling, wood, log, stairs)
	 * @param type
	 *            The wood type.
	 * @return The created block
	 */
	protected Block getBlock(final Material material, final TreeSpecies type) {
		// TODO correct this once new trees are implemented correctly
		BlockState state = Mockito.mock(BlockState.class);
		if (material == Material.LOG_2) {
			Mockito.when(state.getData()).thenReturn(
					new MaterialData(material, (byte) (type.getData() - 4)));
		} else {
			Mockito.when(state.getData()).thenReturn(new Tree(type));
		}

		Block block = Mockito.mock(Block.class);
		Mockito.when(block.getType()).thenReturn(material);
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

	/** Load the stated config properties file. */
	private void loadConfig() throws IOException {
		InputStream in = ClassLoader.getSystemResourceAsStream(configFile);
		config = new TypedProperties();
		config.load(in);
	}

	/** Prepare test by loading config and reseting mocked plugin */
	@Before
	public void prepareTest() throws IOException {
		if (!done) {
			loadConfig();
			done = true;
		}
		resetPlugin();
	}

	/** Reset plugin mock */
	protected void resetPlugin() {
		hasModule = false;

		server = Mockito.mock(Server.class);
		Mockito.when(server.getLogger())
				.thenReturn(Logger.getAnonymousLogger());

		plugin = Mockito.mock(getClazz());
		Mockito.when(plugin.getConfig()).thenReturn(config);
		ModuleInfo info = new ModuleInfo("Test", "T", null);
		Mockito.when(plugin.getInfo()).thenReturn(info);

		perm = Mockito.mock(AbstractPermissionManager.class);
		Mockito.when(
				perm.hasModule(Mockito.eq(plugin), Mockito.any(Player.class)))
				.thenAnswer(new Answer<Boolean>() {
					@Override
					public Boolean answer(final InvocationOnMock invocation)
							throws Throwable {
						return hasModule;
					}
				});
		Mockito.when(perm.worldCheck(Mockito.any(World.class)))
				.thenReturn(true);
		Mockito.when(plugin.getPermission()).thenReturn(perm);

		pers = new InMemoryPersistentManager();
		pers.setCore(Mockito.mock(IExpCraft.class));
		Mockito.when(plugin.getPersistence()).thenReturn(pers);
	}
}
