package me.samkio.levelcraftcore;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;


public class LCChat {
	public static LevelCraftCore plugin;
	public LCChat(LevelCraftCore instance) {
		plugin = instance;
	}
	public static void topBar(CommandSender p){
		p.sendMessage(ChatColor.valueOf(plugin.c1)+"[LC] ---LevelCraftPlugin (C)2011--- ");
	}
	public static void info(CommandSender p,String s){
		p.sendMessage(ChatColor.valueOf(plugin.c1)+"[LC] "+ChatColor.valueOf(plugin.c2)+s);
	}
	public static void warn(CommandSender p,String s){
		p.sendMessage(ChatColor.valueOf(plugin.c1)+"[LC] "+ChatColor.valueOf(plugin.c4)+s);
	}
	public static void good(CommandSender p,String s){
		p.sendMessage(ChatColor.valueOf(plugin.c1)+"[LC] "+ChatColor.valueOf(plugin.c3)+s);
	}
	public static void broadcast(String s){
		plugin.getServer().broadcastMessage(ChatColor.valueOf(plugin.c1) + "[LC] "+ ChatColor.valueOf(plugin.c2)+ s);
	}
}
