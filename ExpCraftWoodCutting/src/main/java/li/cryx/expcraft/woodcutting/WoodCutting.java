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
package li.cryx.expcraft.woodcutting;

import java.text.MessageFormat;

import li.cryx.expcraft.module.DropExpCraftModule;
import li.cryx.expcraft.util.Chat;
import li.cryx.expcraft.util.ToolQuality;

import org.bukkit.TreeSpecies;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is the main entry point for the Wood Cutting module for ExpCraft.
 * The module manages the use of axes and harvesting of wood.
 * 
 * @author cryxli
 */
public class WoodCutting extends DropExpCraftModule {
	/** Global logger */
	private static final Logger LOG = LoggerFactory
			.getLogger(WoodCutting.class);

	/** The block listener to do all the work. */
	private WoodCuttingBlockListener blockListener;

	/** The chat utility to send messages to players. */
	private Chat chat;

	private final WoodCuttingConstraints test;

	public WoodCutting() {
		test = new WoodCuttingConstraints(this);
	}

	@Override
	protected ItemStack calculateDrop(final Block block, final Player player) {
		ToolQuality quality = ToolQuality.NONE;
		if (player != null) {
			quality = ToolQuality.getQuality(player.getItemInHand());
		}
		if (ToolQuality.isLess(ToolQuality.STONE, quality)) {
			// wooden axe does not give double drops
			return null;
		}

		// TODO correct this once new trees are implemented correctly
		switch (block.getType()) {
		case LOG:
		case WOOD:
			int amount = 1;
			if (ToolQuality.isAtLeast(ToolQuality.IRON, quality)) {
				amount = 2;
			}
			TreeSpecies species = test.getTreeType(block);
			System.out.println("W-Double drop: " + species);
			return new ItemStack(block.getType(), amount, species.getData());

		case LOG_2:
			amount = 1;
			if (ToolQuality.isAtLeast(ToolQuality.IRON, quality)) {
				amount = 2;
			}
			species = test.getTreeType(block);
			System.out.println("w-Double drop: " + species);
			return new ItemStack(block.getType(), amount,
					(byte) (species.getData() - 4));

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
		blockListener = new WoodCuttingBlockListener(this);
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

	// server want the plugin to stop working
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

	/**
	 * Send level info about the given axe to the player. If the player meets
	 * the requirements for the tool, it will be written in "good" (default
	 * GREEN), or in "bad" (default RED) otherwise.
	 * 
	 * @param sender
	 *            Current player
	 * @param material
	 *            String identifying the material of the axe.
	 * @param level
	 *            Player's level
	 */
	private void sendToolInfo(final Player sender, final String material,
			final int level) {
		int toolLevel = getConfig().getInteger("AxeLevel." + material);
		String msg = MessageFormat.format("{0} Axe: {1}", material, toolLevel);
		if (level >= toolLevel) {
			// pleyer meets requirements
			chat.good(sender, msg);
		} else {
			// player cannot use the tool
			chat.bad(sender, msg);
		}
	}

	/**
	 * Warn the player that he can not yet cut the current block.
	 * 
	 * @param player
	 *            Current player.
	 * @param level
	 *            Required level to cut the block.
	 */
	void warnCutBlock(final Player player, final int level) {
		chat.bad(player, MessageFormat.format(
				"Cannot cut this block. Required Level: {0}", level));
	}

	/**
	 * Warn the player the he cannot us the tool.
	 * 
	 * @param player
	 *            Current player.
	 * @param level
	 *            Required level to use the tool.
	 */
	void warnToolLevel(final Player player, final int level) {
		chat.bad(player, MessageFormat.format(
				"Cannot use this tool. Required Level: {0}", level));
	}
}
