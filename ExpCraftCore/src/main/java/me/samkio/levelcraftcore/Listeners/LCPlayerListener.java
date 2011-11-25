package me.samkio.levelcraftcore.listeners;

import me.samkio.levelcraftcore.LevelCraftCore;
import me.samkio.levelcraftcore.LevelFunctions;
import me.samkio.levelcraftcore.Whitelist;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.plugin.Plugin;

public class LCPlayerListener extends PlayerListener {
	public LevelCraftCore plugin;

	public LCPlayerListener(final LevelCraftCore instance) {
		plugin = instance;
	}

	@Override
	public void onPlayerChat(final PlayerChatEvent event) {
		if (!plugin.EnableSkillMastery) {
			return;
		}
		Player p = event.getPlayer();
		String str = event.getFormat();
		for (Plugin plug : plugin.LevelNames.keySet()) {
			if (!Whitelist.hasLevel(p, plug)) {
				continue;
			}

			if (!(LevelFunctions.getLevel(p, plug) >= plugin.LevelCap)) {
				continue;
			}

			str = plugin.c1 + "[" + plugin.LevelIndexes.get(plug) + "]WHITE"
					+ str;
		}
		event.setFormat(plugin.Tools.format(str));
	}

	@Override
	public void onPlayerJoin(final PlayerJoinEvent event) {
		Player player = event.getPlayer();
		plugin.Tools.checkAccount(player);
	}

}
