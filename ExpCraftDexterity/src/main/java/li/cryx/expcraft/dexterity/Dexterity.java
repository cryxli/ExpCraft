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
package li.cryx.expcraft.dexterity;

import li.cryx.expcraft.i18n.LangKeys;
import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.util.Chat;

import org.bukkit.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is the main class of the dexterity module for ExpCraft.
 * 
 * @author cryxli
 */
public class Dexterity extends ExpCraftModule {

	private static final Logger LOG = LoggerFactory.getLogger(Dexterity.class);

	private Chat chat;

	private DexterityEntityListener entityListener;

	private DexterityPlayerListener playerListener;

	/**
	 * Create listeners and link them to this plugin and a common
	 * <code>Chat</code> component.
	 */
	private void createListeners() {
		// use one chat tool
		chat = new Chat(getCore());

		// player listener
		playerListener = new DexterityPlayerListener(this);

		// entity listener
		entityListener = new DexterityEntityListener(this);
	}

	@Override
	public void displayInfo(final Player sender) {
		chat.info(sender, LangKeys.MODULE_INFO_TITLE, this, getInfo().getAbbr());

		int level = getPersistence().getLevel(this, sender);
		chat.infoPlain(sender, translator.translate(sender, "info.boots"));
		sendToolInfo(sender, "Leather", level);
		sendToolInfo(sender, "Chainmail", level);
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
	}// server want the plugin to start working

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
		registerEvents(playerListener);
	}

	/**
	 * Send level info about the given boots to the player. If the player meets
	 * the requirements for the armor, it will be written in "good" (default
	 * GREEN), or in "bad" (default RED) otherwise.
	 * 
	 * @param sender
	 *            Current player
	 * @param material
	 *            String identifying the material of the boots.
	 * @param level
	 *            Player's level
	 */
	private void sendToolInfo(final Player sender, final String material,
			final int level) {
		int toolLevel = getConfig().getInteger("BootsLevel." + material);
		String msg = translator
				.translate(sender, "info." + material, toolLevel);
		if (level >= toolLevel) {
			chat.goodPlain(sender, msg);
		} else {
			chat.badPlain(sender, msg);
		}
	}

	/**
	 * Warn the player that she does not meet the requirements for her current
	 * boots.
	 * 
	 * @param player
	 *            Current player
	 * @param material
	 *            String identifying the material of the boots.
	 * @param level
	 *            Required level to get bonus for current boots.
	 */
	void warnBoots(final Player player, final String material, final int level) {
		chat.badPlain(player,
				translator.translate(player, "warn.level." + material, level));
	}

}
