package me.samkio.levelcraftcore;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import com.nijikokun.bukkit.Permissions.Permissions;



public class Whitelist {
	public  static  LevelCraftCore plugin;
	public Whitelist(LevelCraftCore instance) {
		plugin = instance;
	}
	@SuppressWarnings("static-access")
	public void LoadPerms(){
		Plugin test = plugin.getServer().getPluginManager()
				.getPlugin("Permissions");

		if (this.plugin.PermissionH == null) {
			if (test != null) {
				this.plugin.PermissionH = ((Permissions) test).getHandler();
				plugin.PermissionUse = true;
				plugin.logger.info("[LC] Using Permissions.");
			} else {
				plugin.logger.info("[LC] No Permissions found enabling all levels.");
				plugin.logger.info("[LC] Admin commands for OP.");
				plugin.PermissionUse = false;
			}
		}
	}
	public static  boolean hasLevel(Player s, Plugin p){
		if(!plugin.PermissionUse) return true;
		if(plugin.PermissionH.has( s, "lc.level."+plugin.LevelNames.get(p).toLowerCase())) return true;
		return false;
		
	}
	public static boolean hasLevelExp(Player s, Plugin p){
		if(!plugin.PermissionUse) return true;
		if(plugin.PermissionH.has( s, "lc.level."+plugin.LevelNames.get(p).toLowerCase()+".allowlevel")) return true;
		return false;
		
	}
	public static boolean worldCheck(World world){
		String w = world.getName();
		for(String s:plugin.Worlds){
			if(w.equalsIgnoreCase(s)) return true;
		}
		return false;
	}
	public boolean isAdmin(CommandSender s){
		if(!plugin.PermissionUse && s.isOp()) return true;
		if(!plugin.PermissionUse) return false;
		if(s instanceof Player){
		if(plugin.PermissionH.has((Player) s, "lc.admin")) return true;
		}else{
			return false;
		}
		return false;
	}
	public boolean canShout(CommandSender s){
		if(!plugin.PermissionUse) return true;
		if(plugin.PermissionH.has((Player) s, "lc.shout")) return true;
		return false;
	}
}
