package me.samkio.levelcraftcore;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LCChat {
	public static LevelCraftCore plugin;

	public static void broadcast(final String s) {
		LCChat.broadcastWorld(s);
	}

	public static void broadcastWorld(final String s) {
		for (Player p : plugin.getServer().getOnlinePlayers()) {
			if (Whitelist.worldCheck(p.getWorld())) {
				p.sendMessage(ChatColor.valueOf(plugin.c1) + "[LC] "
						+ ChatColor.valueOf(plugin.c2) + s);
			}
		}
	}

	public static void good(final CommandSender p, final String s) {
		p.sendMessage(ChatColor.valueOf(plugin.c1) + "[LC] "
				+ ChatColor.valueOf(plugin.c3) + s);
	}

	public static void info(final CommandSender p, final String s) {
		p.sendMessage(ChatColor.valueOf(plugin.c1) + "[LC] "
				+ ChatColor.valueOf(plugin.c2) + s);
	}

	public static void topBar(final CommandSender p) {
		p.sendMessage(ChatColor.valueOf(plugin.c1)
				+ "[LC] ---LevelCraftPlugin (C)2011--- ");
	}

	public static void warn(final CommandSender p, final String s) {
		p.sendMessage(ChatColor.valueOf(plugin.c1) + "[LC] "
				+ ChatColor.valueOf(plugin.c4) + s);
	}

	public LCChat(final LevelCraftCore instance) {
		LCChat.plugin = instance;
	}

	public void bad(final CommandSender p, final String s) {
		p.sendMessage(ChatColor.valueOf(plugin.c1) + "[LC] "
				+ ChatColor.valueOf(plugin.c4) + s);
	}

}
