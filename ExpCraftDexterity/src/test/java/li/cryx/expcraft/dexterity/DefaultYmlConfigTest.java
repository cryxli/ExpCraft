package li.cryx.expcraft.dexterity;

import java.io.InputStream;

import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Ensures that the config is readable by bukkit.
 * 
 * @author cryxli
 */
public class DefaultYmlConfigTest {

	@Test
	public void testDefaultConfig() {
		InputStream in = getClass().getClassLoader().getResourceAsStream(
				"config.yml");
		YamlConfiguration defaults = YamlConfiguration.loadConfiguration(in);
		Assert.assertNotNull(defaults);

		Assert.assertTrue(defaults.isConfigurationSection("BootsLevel"));
		Assert.assertEquals(0, defaults.getInt("BootsLevel.Leather"));
		Assert.assertEquals(5, defaults.getInt("BootsLevel.Chainmail"));
		Assert.assertEquals(10, defaults.getInt("BootsLevel.Iron"));
		Assert.assertEquals(20, defaults.getInt("BootsLevel.Gold"));
		Assert.assertEquals(30, defaults.getInt("BootsLevel.Diamond"));

		Assert.assertTrue(defaults.isConfigurationSection("ExpGain"));
		Assert.assertEquals(0.5, defaults.getDouble("ExpGain.Jump"), 0);

		Assert.assertTrue(defaults.isConfigurationSection("Settings"));
		Assert.assertEquals(1,
				defaults.getDouble("Settings.FallDmgMultiplier"), 0);

		// TODO cryxli: add other fields
	}

	/**
	 * Initially I wanted to create a config like
	 * 
	 * <pre>
	 * Settings:
	 *   FallDmg:
	 *     Multiplier: 1.0
	 *   other entries:
	 *     ...
	 * </pre>
	 * 
	 * this test shows that it doesn't work.
	 */
	@Ignore
	@Test
	public void testExpected() {
		InputStream in = getClass().getClassLoader().getResourceAsStream(
				"test.yml");
		YamlConfiguration defaults = YamlConfiguration.loadConfiguration(in);
		Assert.assertNotNull(defaults);

		Assert.assertTrue(defaults.isConfigurationSection("Settings"));
		Assert.assertTrue(defaults.isConfigurationSection("Settings.FallDmg"));
		Assert.assertEquals(1,
				defaults.getDouble("Settings.FallDmg.Multiplier"), 0);
	}

}
