package me.samkio.levelcraftcore;

import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class LCAdminCommands {
	public  LevelCraftCore plugin;
	public LCAdminCommands(LevelCraftCore instance) {
		plugin = instance;
	}
	@SuppressWarnings("static-access")
	public void Help(Player sender) {
		plugin.LCChat.topBar(sender);
		plugin.LCChat.info(sender, "/lvl admin setlvl <Ref> <Player> <Level> - "+plugin.lang.SetLevelForPlayers);
		plugin.LCChat.info(sender, "/lvl admin setexp <Ref> <Player> <Exp> - "+plugin.lang.SetExperienceForPlayers);
		plugin.LCChat.info(sender, "/lvl admin getexp <Ref> <Player> - "+plugin.lang.GetExperienceOfPlayer);
		plugin.LCChat.info(sender, "/lvl admin getlvl <Ref> <Player> - "+plugin.lang.GetLevelOfPlayer);
		plugin.LCChat.info(sender, "/lvl admin reset <Ref> <Player>  - "+plugin.lang.ResetExperience);
		//plugin.LCChat.info(sender, "/lvl admin reload - Shows this.");	
		plugin.LCChat.info(sender, "/lvl admin - "+plugin.lang.ShowsThis);	
	}
	@SuppressWarnings("static-access")
	public void determineMethid(Player sender, String[] args) {
		if(args[1].equalsIgnoreCase("setlvl") && args.length==5){
			if(plugin.LCAdminCommands.setLevel(args[2],args[3],args[4],sender)){
				plugin.LCChat.good(sender, plugin.lang.LevelSetSuccess);
				return;
			}else{
				plugin.LCChat.warn(sender, plugin.lang.LevelSetFalse);
				return;
			}
		}else if(args[1].equalsIgnoreCase("setexp") && args.length==5){
			if(plugin.LCAdminCommands.setExp(args[2],args[3],args[4],sender)){
				plugin.LCChat.good(sender, plugin.lang.ExperienceSetSuccess);
				return;
			}else{
				plugin.LCChat.warn(sender, plugin.lang.ExperienceSetFalse);
				return;
			}
		}else if(args[1].equalsIgnoreCase("getlvl") && args.length==4){
			plugin.LCChat.good(sender, plugin.LCAdminCommands.getLevel(args[2],args[3],sender));
		}else if(args[1].equalsIgnoreCase("getexp") && args.length==4){
			plugin.LCChat.good(sender, plugin.LCAdminCommands.getExp(args[2],args[3],sender));
		}else if(args[1].equalsIgnoreCase("reset") && args.length==4){
			if(plugin.LCAdminCommands.reset(args[2],args[3],sender)){
				plugin.LCChat.good(sender, plugin.lang.ExperienceResetSuccess);
				return;
			}else{
				plugin.LCChat.warn(sender, plugin.lang.ExperienceResetFalse);
				return;
			}
		}
			else{
			plugin.LCAdminCommands.Syntax(sender);
			return;
		}
		
	}
	/*boolean reload() {
		for(Plugin p: plugin.LevelNames.keySet()){
			plugin.getPluginLoader().disablePlugin(p);
		}
		plugin.LevelAuthors.clear();
        plugin.LevelFiles.clear();
		plugin.LevelUnlocks.clear();
		plugin.LevelReferenceKeys.clear();
		plugin.LevelIndexes.clear();
		for(Plugin p: plugin.LevelNames.keySet()){
		//	plugin.getPluginLoader().enablePlugin(p);
		}
		plugin.LevelNames.clear();
		plugin.onEnable();
		return true;
		
	}*/
	private boolean reset(String ref, String p, Player pl) {
		if(plugin.LCAdminCommands.setExp(ref, p, "0", pl)) return true;
		return false;
	}
	@SuppressWarnings("static-access")
	private String getExp(String ref, String p, Player pl) {
		List<Player> players = plugin.getServer().matchPlayer(p);
		if (players.size() == 0) {
			plugin.LCChat.warn(pl,plugin.lang.NoMatch);
			return "Error.";
		} else if (players.size() != 1) {
			plugin.LCChat.warn(pl,plugin.lang.TooMany);
			return "Error.";
		} else {
			Player leveler = players.get(0);
			for (Plugin plug : plugin.LevelReferenceKeys.keySet()) {
				String[] reference = plugin.LevelReferenceKeys.get(plug);
				if(plugin.Tools.containsValue(reference,ref) && plugin.Permissions.hasLevel(leveler, plug)){
				return leveler.getName()+"'s "+plugin.LevelNames.get(plug)+plugin.lang.ExperienceIs + plugin.LevelFunctions.getExp(leveler, plug);
				}
			}
			
		}
		return "Error";
	}
	@SuppressWarnings("static-access")
	private String getLevel(String ref, String p, Player pl) {
		List<Player> players = plugin.getServer().matchPlayer(p);
		if (players.size() == 0) {
			plugin.LCChat.warn(pl,plugin.lang.NoMatch);
			return "Error.";
		} else if (players.size() != 1) {
			plugin.LCChat.warn(pl,plugin.lang.TooMany);
			return "Error.";
		} else {
			Player leveler = players.get(0);
			for (Plugin plug : plugin.LevelReferenceKeys.keySet()) {
				String[] reference = plugin.LevelReferenceKeys.get(plug);
				if(plugin.Tools.containsValue(reference,ref) && plugin.Permissions.hasLevel(leveler, plug)){
				return leveler.getName()+"'s "+plugin.LevelNames.get(plug)+plugin.lang.LevelIs + plugin.LevelFunctions.getLevel(leveler, plug);
				}
			}
			
		}
		return "Error";
	}
	@SuppressWarnings("static-access")
	private boolean setExp(String ref, String p, String exp, Player pl) {
		double newexp = Double.parseDouble(exp);
		List<Player> players = plugin.getServer().matchPlayer(p);
		if (players.size() == 0) {
			plugin.LCChat.warn(pl,plugin.lang.NoMatch);
			return false;
		} else if (players.size() != 1) {
			plugin.LCChat.warn(pl,plugin.lang.TooMany);
			return false;
		} else {
			Player leveler = players.get(0);
			for (Plugin plug : plugin.LevelReferenceKeys.keySet()) {
				String[] reference = plugin.LevelReferenceKeys.get(plug);
				if(plugin.Tools.containsValue(reference,ref) && plugin.Permissions.hasLevel(leveler, plug)){
				plugin.LevelFunctions.updateExp(leveler, plug, newexp);
				return true;
				}
			}
			
		}
		return false;
	}
	@SuppressWarnings("static-access")
	private boolean setLevel(String ref, String p, String lvl, Player pl) {
		int newlvl = Integer.parseInt(lvl);
		List<Player> players = plugin.getServer().matchPlayer(p);
		if (players.size() == 0) {
			plugin.LCChat.warn(pl,plugin.lang.NoMatch);
			return false;
		} else if (players.size() != 1) {
			plugin.LCChat.warn(pl,plugin.lang.TooMany);
			return false;
		} else {
			Player leveler = players.get(0);
			for (Plugin plug : plugin.LevelReferenceKeys.keySet()) {
				String[] reference = plugin.LevelReferenceKeys.get(plug);
				if(plugin.Tools.containsValue(reference,ref) && plugin.Permissions.hasLevel(leveler, plug)){
				plugin.LevelFunctions.updateLevel(leveler, plug, newlvl);
				return true;
				}
			}
			
		}
		return false;
	}
	@SuppressWarnings("static-access")
	public void Syntax(Player sender) {
		plugin.LCChat.warn(sender, plugin.lang.SyntaxA);
		return;
	}
}
