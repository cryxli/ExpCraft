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
package li.cryx.expcraft.module;

import java.util.Random;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * This class defines a special case of {@link ExpCraftModule}s. These modules
 * also occasionally drop additional blocks.
 * 
 * <div>The <code>config.yml</code> has to be extended with:
 * 
 * <pre>
 * Settings.EnableDoubleDrop=true
 * Settings.DropMultiplier=1.0
 * </pre>
 * 
 * or the proper keys have to be specified using the setter methods.</div>
 * 
 * <p>
 * From within the code the two methods {@link #saveDropItem(Player, Block)} and
 * {@link DropExpCraftModule#dropItem(Block, int, Player)} can then be used to
 * trigger a drop. The given block must point to a location and material within
 * the game world. Drops will happen with a probability of about <b>Player's
 * level</b> to <b>3 times the level cap</b>.
 * </p>
 * 
 * <p>
 * With the method {@link #calculateDrop(Block, Player)} the implementing class
 * must provide the <code>ItemStack</code> that should be dropped.
 * </p>
 * 
 * @author cryxli
 */
public abstract class DropExpCraftModule extends ExpCraftModule {

	private static final Random rnd = new Random();

	/** Config key indicating that drops are enabled. */
	private String enableDropKey = "Settings.EnableDoubleDrop";

	/** Config key indicating how probability changes. */
	private String dropMultiplierKey = "Settings.DropMultiplier";

	/**
	 * Calculate which items will be dropped in additional to the given block.
	 * 
	 * 
	 * @param block
	 *            It is expected that the given block is part of a bukkit event
	 *            and therefore represents a location and material (item).
	 * @param player
	 *            Optional reference to involved player. May be
	 *            <code>null</code>.
	 * @return A <code>ItemStack</code> object describing what should be
	 *         dropped. If <code>null</code> is returned, nothing is dropped.
	 */
	protected abstract ItemStack calculateDrop(Block block, Player player);

	/**
	 * Check and eventually drop additional items. Drop will happen only when
	 * enabled in config.
	 * 
	 * @param block
	 *            A real game world block. This object indicates the drop
	 *            location and is used to calculate the dropped item.
	 * @param level
	 *            Current player's level for the module.
	 * @param player
	 *            Optional reference to player.
	 */
	public void dropItem(final Block block, final int level, final Player player) {
		if (!getConfig().getBoolean(enableDropKey, false)) {
			// dropping is not enabled
			return;
		}
		if (rnd.nextInt(3 * getLevelCap()) > level
				* getConfig().getDouble(dropMultiplierKey, 1) - 5) {
			// no luck
			return;
		}

		ItemStack drop = calculateDrop(block, player);
		if (drop != null && drop.getAmount() > 0) {
			block.getWorld().dropItem(block.getLocation(), drop);
		}
	}

	/**
	 * Same as {@link #dropItem(Block, int)} but with checks (world active,
	 * player has permission) before any dropping is calculated.
	 * 
	 * @param player
	 *            Current player.
	 * @param block
	 *            A real game world block. This object indicates the drop
	 *            location and is used to calculate the dropped item.
	 */
	public void saveDropItem(final Player player, final Block block) {
		if (!getPermission().worldCheck(block.getWorld())) {
			// ExpCraft not enabled for this world
			return;
		}
		if (!getPermission().hasModule(this, player)) {
			// player does not have permission to use this plugin
			return;
		}
		// drop
		dropItem(block, getPersistence().getLevel(this, player), player);
	}

	/**
	 * Set the config key controlling the drop probability. The higher this
	 * value the more likely drops will happen. Only values larger than
	 * <code>0.0</code> and less than <code>3.0</code> make sense. A good start
	 * is <code>1.0</code>. The value of the key has to be a double.
	 * 
	 * The key defaults to <code>Settings.DropMultiplier</code>.
	 * 
	 * @param dropMultiplierKey
	 */
	public void setDropMultiplierKey(final String dropMultiplierKey) {
		this.dropMultiplierKey = dropMultiplierKey;
	}

	/**
	 * Set the config key indicating whether drop are enabled. The value must be
	 * a <code>boolean</code>.
	 * 
	 * The key defaults to <code>Settings.EnableDoubleDrop</code>.
	 * 
	 * @param enableDropKey
	 */
	public void setEnableDropKey(final String enableDropKey) {
		this.enableDropKey = enableDropKey;
	}
}
