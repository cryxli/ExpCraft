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
package li.cryx.expcraft.defence;

import li.cryx.expcraft.i18n.LangKeys;
import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.util.Chat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is the main entry point for the Defence module for ExpCraft.
 * 
 * @author cryxli
 */
public class Defence extends ExpCraftModule {
	/** A logger */
	public final Logger LOG = LoggerFactory.getLogger(Defence.class);

	/** Chat utility. */
	private Chat chat;

	/** The damage listener. */
	private DefenceEntityListener entityListener;

	/**
	 * Create listeners and link them to this plugin and a common
	 * <code>Chat</code> component.
	 */
	private void createListeners() {
		// use one chat tool
		chat = new Chat(getCore());

		entityListener = new DefenceEntityListener(this);
	}

	@Override
	public void displayInfo(final Player player) {
		chat.info(player, LangKeys.MODULE_INFO_TITLE, this, getInfo().getAbbr());

		int level = getPersistence().getLevel(this, player);
		chat.infoPlain(player, translator.translate(player, "info.armor"));
		sendArmorInfo(player, "Leather", level);
		sendArmorInfo(player, "Chainmail", level);
		sendArmorInfo(player, "Iron", level);
		sendArmorInfo(player, "Gold", level);
		sendArmorInfo(player, "Diamond", level);

		double exp = getPersistence().getExp(this, player);
		double nextLvl = getPersistence().getExpForNextLevel(this, player);
		chat.info(player, LangKeys.MODULE_INFO_STATS);
		chat.info(player, LangKeys.MODULE_INFO_LV_EXP, level, exp);
		chat.info(player, LangKeys.MODULE_INFO_NEXT_LV, nextLvl - exp);
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

	/**
	 * Send a message to the player, that he cannot wear a certain piece of
	 * armor.
	 * 
	 * @param player
	 *            Current player
	 * @param material
	 *            Material of the armor piece.
	 * @param armor
	 *            Type of the armor piece.
	 */
	void notifyRequirements(final Player player, final String material,
			final String armor) {
		chat.warnPlain(player,
				translator.translate(player, "warn." + material + "." + armor));
	}

	@Override
	public void onDisable() {
		// disabled plugins don't get events; no need to unregister
		// listeners
		LOG.info(getInfo().getFullName() + " disabled");
	}

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
		registerEvents(entityListener);
	}

	/**
	 * Send info about a material type of armor to the player.
	 * 
	 * @param player
	 *            Current player
	 * @param material
	 *            String identifying armor material
	 * @param level
	 *            Player's level in Defence.
	 */
	private void sendArmorInfo(final Player player, final String material,
			final int level) {
		final int boots = getConfig().getInteger(
				"ArmorLevel." + material + "Boots");
		final int chest = getConfig().getInteger(
				"ArmorLevel." + material + "Chestplate");
		final int helmet = getConfig().getInteger(
				"ArmorLevel." + material + "Helmet");
		final int leggings = getConfig().getInteger(
				"ArmorLevel." + material + "Leggings");

		if (boots == chest && chest == helmet && helmet == leggings) {
			final String text = translator.translate(player, "info.set."
					+ material, boots);
			if (level < boots) {
				chat.badPlain(player, text);
			} else {
				chat.goodPlain(player, text);
			}
		} else {
			final ChatColor bootColor = level < boots ? Chat.getBadColor()
					: Chat.getGoodColor();
			final ChatColor helmetColor = level < helmet ? Chat.getBadColor()
					: Chat.getGoodColor();
			final ChatColor legColor = level < leggings ? Chat.getBadColor()
					: Chat.getGoodColor();
			final ChatColor chestColor = level < chest ? Chat.getBadColor()
					: Chat.getGoodColor();
			final String text = translator.translate( //
					player, //
					"info.each." + material, //
					Chat.getSpecialColor(), //
					bootColor, //
					boots, //
					helmetColor, //
					helmet,//
					legColor, //
					leggings, //
					chestColor, //
					chest);
			chat.infoPlain(player, text);
		}
	}
}
