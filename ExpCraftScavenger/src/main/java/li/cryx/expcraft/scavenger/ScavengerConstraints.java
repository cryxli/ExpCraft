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
package li.cryx.expcraft.scavenger;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Program logic used by {@link ScavengerBlockListener}.
 * 
 * @author cryxli
 */
public class ScavengerConstraints {

	public static final String HINT_GOLD = "hint.gold";

	public static final String HINT_GHAST = "hint.ghast";

	private final Scavenger plugin;

	public ScavengerConstraints(final Scavenger plugin) {
		this.plugin = plugin;
	}

	/**
	 * Drop the given material at the location of the block.
	 * 
	 * @param block
	 *            Location reference in the world.
	 * @param material
	 *            Item to drop.
	 */
	public void dropItem(final Block block, final Material material) {
		Location locy = new Location(block.getWorld(), block.getX(),
				block.getY(), block.getZ(), 0.0f, 0.0f);
		block.getWorld().dropItem(locy, new ItemStack(material, 1));
	}

	/**
	 * Calculate drops for the Nether.
	 * 
	 * @param player
	 *            Current player
	 * @param level
	 *            Player's level
	 * @param block
	 *            The block that was destroyed for location and world reference
	 * @param rnd
	 *            A random number
	 */
	public void dropNether(final Player player, final int level,
			final Block block, final int rnd) {
		if (rnd <= 3
				&& level >= plugin.getConfig()
						.getInteger("DropLevel.GhastTear")) {
			// 4 %o - 4%
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfig().getDouble("ExpGain.GhastTear"));
			dropItem(block, Material.GHAST_TEAR);
			plugin.sendHint(player, HINT_GHAST);

		} else if (rnd > 3 && rnd <= 6
				&& level >= plugin.getConfig().getInteger("DropLevel.Nugget")) {
			// 3 %o - 3%
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfig().getDouble("ExpGain.Nugget"));
			dropItem(block, Material.GOLD_NUGGET);

		} else if (rnd > 6
				&& rnd <= 8
				&& level >= plugin.getConfig().getInteger(
						"DropLevel.BlazePowder")) {
			// 2 %o - 2%
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfig().getDouble("ExpGain.BlazePowder"));
			dropItem(block, Material.BLAZE_POWDER);

		}
	}

	/**
	 * Calculate drops for the normal world.
	 * 
	 * @param player
	 *            Current player
	 * @param level
	 *            Player's level
	 * @param block
	 *            The block that was destroyed
	 * @param rnd
	 *            A random number
	 */
	public void dropNormal(final Player player, final int level,
			final Block block, final int rnd) {
		if (rnd == 0
				&& level >= plugin.getConfig().getInteger(
						"DropLevel.Chestplate")) {
			// 1 %o - 1% (old: 0)
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfig().getDouble("ExpGain.Chestplate"));
			dropItem(block, Material.LEATHER_CHESTPLATE);

		} else if (rnd > 0 && rnd <= 5
				&& level >= plugin.getConfig().getInteger("DropLevel.Saddle")) {
			// 5 %o - 5%
			dropItem(block, Material.SADDLE);
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfig().getDouble("ExpGain.Saddle"));

		} else if (rnd == 6
				&& level >= plugin.getConfig().getInteger("DropLevel.Ingot")) {
			// 1 %o - 1%
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfig().getDouble("ExpGain.Ingot"));
			dropItem(block, Material.GOLD_INGOT);
			plugin.sendHint(player, HINT_GOLD);

		} else if (rnd > 6 && rnd <= 8
				&& level >= plugin.getConfig().getInteger("DropLevel.Bowl")) {
			// 3 %O -3%
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfig().getDouble("ExpGain.Bowl"));
			dropItem(block, Material.BOWL);

		} else if (rnd > 8 && rnd <= 10
				&& level >= plugin.getConfig().getInteger("DropLevel.Boots")) {
			// 2 %o - 2% (ori: 3 %o - 3%)
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfig().getDouble("ExpGain.Boots"));
			dropItem(block, Material.LEATHER_BOOTS);

		} else if (rnd > 10 && rnd <= 12
				&& level >= plugin.getConfig().getInteger("DropLevel.Helmet")) {
			// 2 %o - 2% (ori: 0)
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfig().getDouble("ExpGain.Helmet"));
			dropItem(block, Material.LEATHER_HELMET);

		} else if (rnd > 12 && rnd <= 15
				&& level >= plugin.getConfig().getInteger("DropLevel.Bucket")) {
			// 3 %o - 3% (ori: 4 %o - 4%)
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfig().getDouble("ExpGain.Bucket"));
			dropItem(block, Material.BUCKET);

		} else if (rnd > 15 && rnd <= 17
				&& level >= plugin.getConfig().getInteger("DropLevel.Leggins")) {
			// 2 %o - 2% (ori: 0)
			plugin.getPersistence().addExp(plugin, player,
					plugin.getConfig().getDouble("ExpGain.Leggins"));
			dropItem(block, Material.LEATHER_LEGGINGS);

		}
	}

	/**
	 * Depending on the player's level, he may have a higher chance to find
	 * items.
	 * 
	 * @param level
	 *            Current player's level.
	 * @return
	 */
	public int getRange(final int level) {
		int levelCap = plugin.getLevelCap();
		int i = 1000;
		if (level >= 0.05 * levelCap && level < 0.1 * levelCap) {
			i = 900;
		} else if (level >= 0.1 * levelCap && level < 0.2 * levelCap) {
			i = 850; // 600;
		} else if (level >= 0.2 * levelCap && level < 0.4 * levelCap) {
			i = 650; // 500;
		} else if (level >= 0.4 * levelCap && level < 0.7 * levelCap) {
			i = 400; // 300;
		} else if (level >= 0.7 * levelCap && level < 0.9 * levelCap) {
			i = 200; // 150;
		} else if (level >= 0.9 * levelCap) {
			i = 100;
		}
		return i;
	}

	/**
	 * Test whether the block destroyed was material were scavenging could
	 * happen.
	 * 
	 * @param material
	 *            The destroyed block
	 * @return <code>true</code>, if material is dirt, grass, sand, gravel or
	 *         soulsand.
	 */
	public boolean isDiggable(final Material material) {
		switch (material) {
		case DIRT:
		case GRASS:
		case SOUL_SAND:
		case GRAVEL:
		case CLAY:
		case SAND:
		case MYCEL:
			return true;

			// case SNOW:
		default:
			return false;
		}
	}

}
