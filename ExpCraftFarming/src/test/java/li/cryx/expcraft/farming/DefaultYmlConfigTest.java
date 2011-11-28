package li.cryx.expcraft.farming;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;

import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Test;

public class DefaultYmlConfigTest {

	private void checkValues(final YamlConfiguration config) {
		Assert.assertTrue(config.isConfigurationSection("HoeLevel"));
		Assert.assertEquals(0, config.getInt("HoeLevel.Wooden"));
		Assert.assertEquals(5, config.getInt("HoeLevel.Stone"));
		Assert.assertEquals(10, config.getInt("HoeLevel.Iron"));
		Assert.assertEquals(20, config.getInt("HoeLevel.Gold"));
		Assert.assertEquals(30, config.getInt("HoeLevel.Diamond"));

		Assert.assertTrue(config.isConfigurationSection("UseLevel"));
		Assert.assertEquals(0, config.getInt("HoeLevel.Till"));
		Assert.assertEquals(0, config.getInt("HoeLevel.Harvest"));
		Assert.assertEquals(0, config.getInt("HoeLevel.Apple"));
		Assert.assertEquals(0, config.getInt("HoeLevel.SugarCane"));
		Assert.assertEquals(0, config.getInt("HoeLevel.GoldenApple"));
		Assert.assertEquals(0, config.getInt("HoeLevel.Cacti"));
		Assert.assertEquals(0, config.getInt("HoeLevel.Sapling"));
		Assert.assertEquals(0, config.getInt("HoeLevel.RedRose"));
		Assert.assertEquals(0, config.getInt("HoeLevel.YellowFlower"));
		Assert.assertEquals(0, config.getInt("HoeLevel.Mushroom"));
		Assert.assertEquals(0, config.getInt("HoeLevel.Wheat"));

		Assert.assertTrue(config.isConfigurationSection("ExpGain"));
		Assert.assertEquals(1, config.getDouble("ExpGain.Till"), 0);
		Assert.assertEquals(5, config.getDouble("ExpGain.Harvest"), 0);
		Assert.assertEquals(3, config.getDouble("ExpGain.Apple"), 0);
		Assert.assertEquals(100, config.getDouble("ExpGain.GoldenApple"), 0);
		Assert.assertEquals(2, config.getDouble("ExpGain.SugarCane"), 0);
		Assert.assertEquals(3, config.getDouble("ExpGain.Cactus"), 0);
		Assert.assertEquals(0.5, config.getDouble("ExpGain.Sapling"), 0);
		Assert.assertEquals(0.1, config.getDouble("ExpGain.RedRose"), 0);
		Assert.assertEquals(0.1, config.getDouble("ExpGain.YellowFlower"), 0);
		Assert.assertEquals(0.1, config.getDouble("ExpGain.Mushroom"), 0);
		Assert.assertEquals(2, config.getDouble("ExpGain.Wheat"), 0);
	}

	@Test
	public void directLoading() {
		InputStream in = getClass().getClassLoader().getResourceAsStream(
				"config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(in);
		Assert.assertNotNull(config);
		Assert.assertFalse(config.isConfigurationSection("does not exists"));

		checkValues(config);
	}

	@Test
	public void save() throws IOException {
		InputStream in = getClass().getClassLoader().getResourceAsStream(
				"config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(in);
		File file = File.createTempFile("config", ".yml");
		config.save(file);

		YamlConfiguration fromFile = YamlConfiguration.loadConfiguration(file);
		Assert.assertNotNull(fromFile);
		checkValues(fromFile);
	}

	@Test
	public void setAsDefaults() {
		InputStream in = getClass().getClassLoader().getResourceAsStream(
				"config.yml");
		YamlConfiguration defaults = YamlConfiguration.loadConfiguration(in);
		Assert.assertNotNull(defaults);

		YamlConfiguration config = new YamlConfiguration();
		config.set("test", 123);
		Assert.assertFalse(config.isConfigurationSection("ExpGain"));
		config.setDefaults(defaults);

		checkValues(config);
		Assert.assertEquals(123, config.getInt("test"));
	}
}
