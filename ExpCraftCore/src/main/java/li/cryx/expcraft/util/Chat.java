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
package li.cryx.expcraft.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import li.cryx.expcraft.IExpCraft;
import li.cryx.expcraft.i18n.AbstractTranslator;
import li.cryx.expcraft.i18n.LangKeys;
import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.perm.AbstractPermissionManager;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * This class offers all ExpCraft modules a unified way to send messages to
 * players.
 * <p>
 * The colors used in the Chat are taken from the Bukkit's
 * <code>ChatColor</code> enum.
 * </p>
 * 
 * @author cryxli
 */
public class Chat {

	/** Chat color to highlight things. */
	private static ChatColor colorHighlight = ChatColor.GOLD;

	/** Chat color to indicate something special. */
	private static ChatColor colorSpecial = ChatColor.YELLOW;

	/** Chat color to indicate good news. */
	private static ChatColor colorGood = ChatColor.GREEN;

	/** Chat color to indicate bad news. */
	private static ChatColor colorBad = ChatColor.RED;

	/** Indicator whether to broadcast information to all players. */
	private static boolean notifyAll = true;

	/** Indicator whether to play a sound when leveling up. */
	private static boolean playSound = true;

	public static ChatColor getBadColor() {
		return colorBad;
	}

	public static ChatColor getGoodColor() {
		return colorGood;
	}

	public static ChatColor getHighlightColor() {
		return colorHighlight;
	}

	public static ChatColor getSpecialColor() {
		return colorSpecial;
	}

	public static boolean isNotifyAll() {
		return notifyAll;
	}

	public static boolean isPlaySound() {
		return playSound;
	}

	/** Set the bad color, by color name. */
	public static void setBadColor(final String color) {
		ChatColor chatColor = ChatColor.valueOf(color);
		if (chatColor != null) {
			colorBad = chatColor;
		}
	}

	/** Set the good color, by color name. */
	public static void setGoodColor(final String color) {
		ChatColor chatColor = ChatColor.valueOf(color);
		if (chatColor != null) {
			colorGood = chatColor;
		}
	}

	/** Set the highlight color, by color name. */
	public static void setHighlightColor(final String color) {
		ChatColor chatColor = ChatColor.valueOf(color);
		if (chatColor != null) {
			colorHighlight = chatColor;
		}
	}

	public static void setNotifyAll(final boolean enabled) {
		notifyAll = enabled;
	}

	public static void setPlaySound(final boolean enabled) {
		playSound = enabled;
	}

	/** Set the special color, by color name. */
	public static void setSpecialColor(final String color) {
		ChatColor chatColor = ChatColor.valueOf(color);
		if (chatColor != null) {
			colorSpecial = chatColor;
		}
	}

	/** Reference to the core plugin. */
	private final IExpCraft core;

	private final AbstractTranslator i18n;

	/**
	 * Create a new instance of the chat util. It must be bound to a plugin and
	 * will also lookup the ExpCraft core plugin.
	 * 
	 * @param plugin
	 *            The plugin using the util.
	 * @throws IllegalStateException
	 *             when the ExpCraftCore is not present.
	 */
	public Chat(final IExpCraft core) {
		this.core = core;
		if (core == null) {
			throw new IllegalStateException("ExpCraftCore not found");
		}
		i18n = core.getTranslator();
		if (i18n == null) {
			throw new IllegalStateException("AbstractTranslator not found");
		}
	}

	public void bad(final CommandSender sender, final String msg,
			final Object... arguments) {
		final String trans = i18n.translate(sender, msg, arguments);
		badPlain(sender, trans);
	}

	public void badPlain(final CommandSender sender, final String msg) {
		sender.sendMessage(colorHighlight + "[EC] " + colorBad + msg);
	}

	public void broadcast(final String msg, final ExpCraftModule module,
			final Object... arguments) {
		for (Player player : core.getServer().getOnlinePlayers()) {
			if (getPermission().worldCheck(player.getWorld())) {
				String trans = i18n.translate(player, module);
				trans = i18n.translate(player, msg, vargs(trans, arguments));
				player.sendMessage(colorHighlight + "[EC] " + colorSpecial
						+ trans);
			}
		}
	}

	public void broadcast(final String msg, final Object... arguments) {
		for (Player player : core.getServer().getOnlinePlayers()) {
			if (getPermission().worldCheck(player.getWorld())) {
				final String trans = i18n.translate(player, msg, arguments);
				player.sendMessage(colorHighlight + "[EC] " + colorSpecial
						+ trans);
			}
		}
	}

	/** Get the permission manager directly from the core plugin. */
	private AbstractPermissionManager getPermission() {
		return core.getPermissions();
	}

	public void good(final CommandSender sender, final String msg,
			final ExpCraftModule module, final Object... arguments) {
		String trans = i18n.translate(sender, module);
		trans = i18n.translate(sender, msg, vargs(trans, arguments));
		goodPlain(sender, trans);
	}

	public void good(final CommandSender sender, final String msg,
			final Object... arguments) {
		final String trans = i18n.translate(sender, msg, arguments);
		goodPlain(sender, trans);
	}

	public void goodPlain(final CommandSender sender, final String msg) {
		sender.sendMessage(colorHighlight + "[EC] " + colorGood + msg);
	}

	public void info(final CommandSender sender, final String msg,
			final ExpCraftModule module, final Object... arguments) {
		String trans = i18n.translate(sender, module);
		trans = i18n.translate(sender, msg, vargs(trans, arguments));
		infoPlain(sender, trans);
	}

	public void info(final CommandSender sender, final String msg,
			final Object... arguments) {
		final String trans = i18n.translate(sender, msg, arguments);
		infoPlain(sender, trans);
	}

	public void infoPlain(final CommandSender sender, final String line) {
		sender.sendMessage(colorHighlight + "[EC] " + colorSpecial + line);
	}

	/**
	 * Core message: A player reached a new level in the given module.
	 * 
	 * @param module
	 *            The module in question.
	 * @param player
	 *            Current player.
	 * @param newLevel
	 *            Newly reached level.
	 */
	public void notifyLevelGain(final ExpCraftModule module,
			final Player player, final int newLevel) {
		good(player, LangKeys.LEVEL_UP_SELF, //
				module, //
				newLevel);
		good(player, LangKeys.LEVEL_UP_INFO, module.getInfo().getAbbr());

		if (playSound) {
			player.playSound(player.getLocation(), Sound.LEVEL_UP, 0.5f, 1);
		}

		if (notifyAll) {
			broadcast(LangKeys.LEVEL_UP_BROADCAST, module, player.getName(),
					newLevel);
		}
	}

	public void topBar(final CommandSender sender) {
		final String trans = i18n.translate(sender, LangKeys.PLUGIN_TITLE);
		sender.sendMessage(colorHighlight + "[EC] " + trans);
	}

	public String translate(final CommandSender sender,
			final ExpCraftModule module) {
		return i18n.translate(sender, module);
	}

	private Object[] vargs(final Object obj, final Object[] objs) {
		List<Object> list = new ArrayList<Object>();
		list.add(obj);
		list.addAll(Arrays.asList(objs));
		return list.toArray();
	}

	public void warn(final CommandSender sender, final String msg,
			final Object... arguments) {
		final String trans = i18n.translate(sender, msg, arguments);
		sender.sendMessage(colorHighlight + "[EC] " + colorBad + trans);
	}
}
