package me.samkio.levelcraftcore.Listeners;

import java.io.File;

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
		checkAccount(player);
	}

	private void checkAccount(Player p) {
		if (plugin.database.equalsIgnoreCase("flatfile")) {
			for (File f : plugin.LevelFiles.values()) {
				if (plugin.FlatFile.contains(p.getName(), f))
					continue;
				if (!plugin.FlatFile.write(p.getName(), 0, f))
					;
			}
		} else if (plugin.database.equalsIgnoreCase("sqlite")) {
			if (plugin.SqliteDB.contains(p.getName()))
				return;
			plugin.SqliteDB.newP(p.getName());
		} else if (plugin.database.equalsIgnoreCase("mysql")) {
			if (plugin.MySqlDB.contains(p.getName()))
				return;
			plugin.MySqlDB.newP(p.getName());
		}
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
