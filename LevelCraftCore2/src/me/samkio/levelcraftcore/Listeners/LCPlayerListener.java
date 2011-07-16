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

	@SuppressWarnings("static-access")
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if(plugin.AllowClassSwitch){
			if(plugin.Class.isInValidClass(player.getName())) return;
			plugin.LCChat.topBar(player);
			plugin.LCChat.good(player, "Greeting's "+player.getName() +" this server hosts LevelCraft!");
			plugin.LCChat.good(player, "Please choose a class. /level class - for more infomation.");
			plugin.LCChat.good(player, "Default Class is: "+plugin.Class.DefaultClass);
		}
		plugin.Tools.checkAccount(player);
	}


	@SuppressWarnings("static-access")
	public void onPlayerChat(PlayerChatEvent event) {
		if(plugin.UseClasses){
			Player p = event.getPlayer();
			String str = event.getFormat();
			String PlayersClass = plugin.Class.DefaultClass;
			if(plugin.Class.isInValidClass(p.getName())) {
				PlayersClass = plugin.Class.getClass(p.getName());
			}
			plugin.Class.getPrefix(PlayersClass);
			str = plugin.c1 + plugin.Class.getPrefix(PlayersClass) +"WHITE"+ str;
			event.setFormat(plugin.Tools.format(str));
			
		}
		
		
		
		
		if (!plugin.EnableSkillMastery)
			return;
		Player p = event.getPlayer();
		String str = event.getFormat();
		for (Plugin plug : plugin.LevelNames.keySet()) {
			if (!plugin.Permissions.hasLevel(p, plug))
				continue;
			if(plugin.UseClasses){
				String PlayersClass = plugin.Class.DefaultClass;
				if(plugin.Class.isInValidClass(p.getName())) {
					PlayersClass = plugin.Class.getClass(p.getName());
				}
			//	plugin.logger.info(""+plugin.LevelNames.get(plug));
				//plugin.logger.info(""+plugin.LevelFunctions.getLevel(p, plug)+"-"+ plugin.Class.getMax(PlayersClass,plugin.LevelNames.get(plug)));
				if (!(plugin.LevelFunctions.getLevel(p, plug) >= plugin.Class.getMax(PlayersClass,plugin.LevelNames.get(plug))))
					continue;
			}else{
			if (!(plugin.LevelFunctions.getLevel(p, plug) >= plugin.LevelCap))
				continue;
			}
			
			str = plugin.c1 + "[" + plugin.LevelIndexes.get(plug) + "]WHITE"
					+ str;
		}
		event.setFormat(plugin.Tools.format(str));
	}

}
