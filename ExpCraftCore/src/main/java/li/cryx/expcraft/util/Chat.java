package li.cryx.expcraft.util;

import java.text.MessageFormat;

import li.cryx.expcraft.ExpCraft;
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
	private final ExpCraft core;

	/**
	 * Create a new instance of the chat util. It must be bound to a plugin and
	 * will also lookup the ExpCraft core plugin.
	 * 
	 * @param plugin
	 *            The plugin using the util.
	 * @throws IllegalStateException
	 *             when the ExpCraftCore is not present.
	 */
	public Chat(final ExpCraft core) {
		this.core = core;
		if (core == null) {
			throw new IllegalStateException("ExpCraftCore not found");
		}
	}

	public void bad(final CommandSender sender, final String msg) {
		sender.sendMessage(colorHighlight + "[EC] " + colorBad + msg);
	}

	public void broadcast(final String msg) {
		for (Player player : core.getServer().getOnlinePlayers()) {
			if (getPermission().worldCheck(player.getWorld())) {
				player.sendMessage(colorHighlight + "[EC] " + colorSpecial
						+ msg);
			}
		}
	}

	/** Get the permission manager directly from the core plugin. */
	private AbstractPermissionManager getPermission() {
		return core.getPermissions();
	}

	public void good(final CommandSender sender, final String msg) {
		sender.sendMessage(colorHighlight + "[EC] " + colorGood + msg);
	}

	public void info(final CommandSender sender, final String msg) {
		sender.sendMessage(colorHighlight + "[EC] " + colorSpecial + msg);
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
		good(player, MessageFormat.format(
				"LEVEL UP. You are now level {0} in {1}", newLevel, module
						.getInfo().getName()));

		good(player, MessageFormat.format(
				"See /level {0} - To see what you have unlocked.", module
						.getInfo().getAbbr()));

		if (playSound) {
			player.playSound(player.getLocation(), Sound.LEVEL_UP, 0.5f, 1);
		}

		if (notifyAll) {
			broadcast(MessageFormat.format("{0} is now level {1} in {2}.",
					player.getName(), newLevel, module.getInfo().getName()));
		}
	}

	public void topBar(final CommandSender sender) {
		sender.sendMessage(colorHighlight + "[EC] --- ExpCraftPlugin --- ");
	}

	public void warn(final CommandSender sender, final String msg) {
		sender.sendMessage(colorHighlight + "[EC] " + colorBad + msg);
	}
}
