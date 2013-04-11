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
package li.cryx.expcraft.digging;

import java.text.MessageFormat;

import li.cryx.expcraft.module.DropExpCraftModule;
import li.cryx.expcraft.util.Chat;
import li.cryx.expcraft.util.ToolQuality;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Digging extends DropExpCraftModule {
	private static final Logger LOG = LoggerFactory.getLogger(Digging.class);

	/** Listen to block break and block place events. */
	private DiggingBlockListener blockListener = null;

	private Chat chat;

	@Override
	public ItemStack calculateDrop(final Block block, final Player player) {
		Material material = block.getType();
		ToolQuality quality = ToolQuality.NONE;
		if (player != null) {
			quality = ToolQuality.getQuality(player.getItemInHand());
		}
		if (ToolQuality.isLess(ToolQuality.STONE, quality)) {
			// wooden spade does not give double drops
			return null;
		}

		switch (material) {
		case GRASS:
			return new ItemStack(Material.DIRT, 1);
		case CLAY:
			return new ItemStack(Material.CLAY_BALL, ToolQuality.isAtLeast(
					ToolQuality.IRON, quality) ? 8 : 4);
		case SNOW:
			// MC 1.5
			// data value + 1 = amount of layers
			return new ItemStack(Material.SNOW_BALL, (block.getData() + 1) % 4);

		case SNOW_BLOCK:
			return new ItemStack(Material.SNOW_BALL, ToolQuality.isAtLeast(
					ToolQuality.IRON, quality) ? 8 : 4);

		case GRAVEL:
		case SOUL_SAND:
		case DIRT:
		case SAND:
			return new ItemStack(material, ToolQuality.isAtLeast(
					ToolQuality.IRON, quality) ? 2 : 1);

		default:
			return null;
		}
	}

	/**
	 * Create listeners and link them to this plugin and a common
	 * <code>Chat</code> component.
	 */
	private void createListeners() {
		// use one chat tool
		chat = new Chat(getCore());

		// block listener
		blockListener = new DiggingBlockListener(this);
	}

	@Override
	public void displayInfo(final Player sender) {
		chat.info(sender, MessageFormat.format("*** {0} ({1}) ***", getInfo()
				.getName(), getInfo().getAbbr()));

		int level = getPersistence().getLevel(this, sender);
		chat.info(sender, "Tools:");
		sendToolInfo(sender, "Wooden", level);
		sendToolInfo(sender, "Stone", level);
		sendToolInfo(sender, "Iron", level);
		sendToolInfo(sender, "Gold", level);
		sendToolInfo(sender, "Diamond", level);

		double exp = getPersistence().getExp(this, sender);
		double nextLvl = getPersistence().getExpForNextLevel(this, sender);
		chat.info(sender, "Stats:");
		chat.info(sender, MessageFormat.format(
				"Current level: {0}, XP: {1} points", level, exp));
		chat.info(sender, MessageFormat.format(
				"Experience to next level: {0} points", nextLvl - exp));
	}

	/**
	 * Load config from disk merge missing default values and store them to
	 * disk.
	 */
	private void loadConfig() {
		// force loading config from default once
		getConfig();
		// ...to store at first run
		saveConfig();
	}

	// server wants the plugin to stop working
	@Override
	public void onDisable() {
		// disabled plugins don't get events; no need to unregister
		// listeners
		LOG.info(getInfo().getFullName() + " disabled");
	}

	// server want the plugin to start working
	@Override
	public void onEnable() {
		// pre-load config
		loadConfig();
		// register listeners
		registerEvents();
		// ready
		LOG.info(getInfo().getFullName() + " enabled");
	}

	/** Register the listeners */
	private void registerEvents() {
		// ensure the listeners are ready
		createListeners();
		// register listeners
		registerEvents(blockListener);
	}

	private void sendToolInfo(final Player sender, final String material,
			final int level) {
		int toolLevel = getConfig().getInteger("ShovelLevel." + material);
		String msg = MessageFormat.format("{0} Shovel: {1}", material,
				toolLevel);
		if (level >= toolLevel) {
			chat.good(sender, msg);
		} else {
			chat.bad(sender, msg);
		}
	}

	/**
	 * Send a warning to the player that he is not allowed to cut the current
	 * block.
	 * 
	 * @param player
	 *            Current player
	 * @param level
	 *            The required level to cut
	 */
	void warnCutBlockLevel(final Player player, final int reqLevel) {
		chat.warn(player, MessageFormat.format( //
				"Cannot cut this block. Required Level: {0}", reqLevel));
	}

	/**
	 * Send a warning to the player that he is not allowed to use the current
	 * tool.
	 * 
	 * @param player
	 *            Current player
	 * @param level
	 *            The required level to use this tool
	 */
	void warnToolLevel(final Player player, final int level) {
		chat.warn(player, MessageFormat.format( //
				"Cannot use this tool. Required Level: {0}", level));
	}
}
