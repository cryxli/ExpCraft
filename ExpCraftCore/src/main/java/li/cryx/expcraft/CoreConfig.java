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
package li.cryx.expcraft;

/**
 * This interface contains the keys used in
 * <code>ExpCraft/config/Core.properties</code>.
 * 
 * @author cryxli
 */
public interface CoreConfig {
	/**
	 * The constant that describes how difficult ExpCraft should be. Defaults to
	 * 20.
	 */
	String LEVEL_CONST = "Levels.Constant";
	/**
	 * Sets the maximal level players can reach with each module. Defaults to
	 * 100.
	 */
	String LEVEL_CAP = "Levels.LevelCap";

	/**
	 * Defines how to persist player progress. Expected values are
	 * <code>bukkit</code> or <code>flatfile</code>. Default is
	 * <code>flatfile</code>. The values are not case sensitiv.
	 */
	String PERSISTENCE_IMPL = "Database";

	/**
	 * Indicator whether to play level-up sound to player that just gained a
	 * level for a module.
	 */
	String CHAT_PLAY_SOUND = "Chat.PlayLevelUpSound";
	/**
	 * Indicator whether to notify the other players about a level gain of a
	 * player.
	 */
	String CHAT_NOTIFY_ALL = "Chat.NotifyAll";

	/** Set the default color for messages displayed to players. */
	String CHAT_COLOR_ONE = "Colors.ColorOne";
	/** Set the high-light color. */
	String CHAT_COLOR_TWO = "Colors.ColorTwo";
	/** Set the color for positive messages. */
	String CHAT_COLOR_GOOD = "Colors.ColorGood";
	/** Set the color for negative messages. */
	String CHAT_COLOR_BAD = "Colors.ColorBad";

	/** Comma separated list of world names for which ExpCraft is enabled. */
	String WORLDS = "Worlds";

	/**
	 * Specify an additional folder to scan for ExpCraft modules. The folder
	 * must be specified relative to the bukkit's plugin folder.
	 */
	String MOD_FOLDER = "Modules.Folder";
	/** Indicator wether to scan the plugins folder for ExpCraft modules. */
	String MODS_IN_PLUGIN_FOLDER = "Modules.ScanPlugins";

}
