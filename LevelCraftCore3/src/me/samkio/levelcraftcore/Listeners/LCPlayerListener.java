package me.samkio.levelcraftcore.Listeners;

import me.samkio.levelcraftcore.LevelCraftCore;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.plugin.Plugin;

public class LCPlayerListener extends PlayerListener {
	public LevelCraftCore plugin;

	public LCPlayerListener(LevelCraftCore instance) {
		plugin = instance;
	}

	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		plugin.Tools.checkAccount(player);
	}

	@SuppressWarnings("static-access")
	public void onPlayerChat(PlayerChatEvent event) {

		if (!plugin.EnableSkillMastery)
			return;
		Player p = event.getPlayer();
		String str = event.getFormat();
		for (Plugin plug : plugin.LevelNames.keySet()) {
			if (!plugin.Permissions.hasLevel(p, plug))
				continue;

			if (!(plugin.LevelFunctions.getLevel(p, plug) >= plugin.LevelCap))
				continue;

			str = plugin.c1 + "[" + plugin.LevelIndexes.get(plug) + "]WHITE"
					+ str;
		}
		event.setFormat(plugin.Tools.format(str));
	}

}
