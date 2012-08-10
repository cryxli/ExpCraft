package li.cryx.expcraft.woodcutting;

import java.io.InputStream;

import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.material.Tree;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class AbstractPluginTest {

	protected YamlConfiguration config;

	protected WoodCutting plugin;

	private boolean done = false;

	protected String configFile = "test-config.yml";

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

	private void loadConfig() {
		InputStream in = ClassLoader.getSystemResourceAsStream(configFile);
		config = YamlConfiguration.loadConfiguration(in);
	}

	@Ignore
	@Test
	public void noTest() {
	}

	@Before
	public void prepareTest() {
		if (!done) {
			loadConfig();
			done = true;
		}
		resetPlugin();
	}

	protected void resetPlugin() {
		plugin = Mockito.mock(WoodCutting.class);
		Mockito.when(plugin.getConfInt(Mockito.anyString())).thenAnswer(
				new Answer<Integer>() {
					@Override
					public Integer answer(final InvocationOnMock invocation)
							throws Throwable {
						String key = (String) invocation.getArguments()[0];
						return config.getInt(key);
					}
				});
		Mockito.when(plugin.getConfDouble(Mockito.anyString())).thenAnswer(
				new Answer<Double>() {
					@Override
					public Double answer(final InvocationOnMock invocation)
							throws Throwable {
						String key = (String) invocation.getArguments()[0];
						return config.getDouble(key);
					}
				});
	}
}
