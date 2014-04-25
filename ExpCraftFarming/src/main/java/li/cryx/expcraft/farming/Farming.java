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
package li.cryx.expcraft.farming;

import java.util.logging.Logger;

import li.cryx.expcraft.i18n.LangKeys;
import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.util.Chat;

import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * This class is the main entry point of the "Farming" module for ExpCraft. This
 * module restricts the use of hoes. Player gain experiance by, e.g., havesting
 * crops, sugar cane and cacti, or, planting roses, dandelions and saplings and
 * other planting related activities. With high enough levels players may also
 * gain additional goodies, that usually do not drop when harvesting.
 * 
 * @author cryxli
 */
public class Farming extends ExpCraftModule {

	private static final Logger LOG = Logger.getLogger("EC-Farming");

	/** Listen to block break events. */
	private FarmingBlockBreakListener blockBreakListener = null;
	/** Listen to block place events. */
	private FarmingBlockPlaceListener blockPlaceListener = null;

	/** Listen to player interact events. */
	private FarmingPlayerListener playerListener = null;

	/** A factory to send messages to the player. */
	private Chat chat;

	/**
	 * Create listeners and link them to this plugin and a common
	 * <code>Chat</code> component.
	 */
	private void createListeners() {
		// use one chat tool
		chat = new Chat(getCore());

		// block listener
		blockBreakListener = new FarmingBlockBreakListener(this);
		blockPlaceListener = new FarmingBlockPlaceListener(this);

		// player listener
		playerListener = new FarmingPlayerListener(this);
	}

	@Override
	public void displayInfo(final Player sender) {
		chat.info(sender, LangKeys.MODULE_INFO_TITLE, this, getInfo().getAbbr());

		int level = getPersistence().getLevel(this, sender);
		chat.infoPlain(sender, translator.translate(sender, "info.tools"));
		sendToolInfo(sender, "Wooden", level);
		sendToolInfo(sender, "Stone", level);
		sendToolInfo(sender, "Iron", level);
		sendToolInfo(sender, "Gold", level);
		sendToolInfo(sender, "Diamond", level);

		double exp = getPersistence().getExp(this, sender);
		double nextLvl = getPersistence().getExpForNextLevel(this, sender);
		chat.info(sender, LangKeys.MODULE_INFO_STATS);
		chat.info(sender, LangKeys.MODULE_INFO_LV_EXP, level, exp);
		chat.info(sender, LangKeys.MODULE_INFO_NEXT_LV, nextLvl - exp);
	}

	/**
	 * Test whether the given material represents a hoe of any kind.
	 * 
	 * @param material
	 *            The item type.
	 * @return <code>true</code>, only if the type corresponds to a hoe made of
	 *         any material.
	 */
	boolean isHoe(final Material material) {
		return material == Material.WOOD_HOE //
				|| material == Material.STONE_HOE //
				|| material == Material.IRON_HOE //
				|| material == Material.GOLD_HOE //
				|| material == Material.DIAMOND_HOE;
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
		registerEvents(blockBreakListener);
		registerEvents(blockPlaceListener);
		registerEvents(playerListener);
	}

	private void sendToolInfo(final Player sender, final String material,
			final int level) {
		int toolLevel = getConfig().getInteger("HoeLevel." + material);
		String msg = translator
				.translate(sender, "info." + material, toolLevel);
		if (level >= toolLevel) {
			chat.goodPlain(sender, msg);
		} else {
			chat.badPlain(sender, msg);
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
		chat.warnPlain(player,
				translator.translate(player, "warn.block", reqLevel));
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
		chat.warnPlain(player, translator.translate(player, "warn.tool", level));
	}
}
