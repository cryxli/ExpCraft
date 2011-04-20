package me.samkio.levelcraftcore;



import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class LCCommands {
	public LevelCraftCore plugin;

	public LCCommands(LevelCraftCore instance) {
		plugin = instance;
	}

	@SuppressWarnings("static-access")
	public void showStat(Player s, String string) {
		for (Plugin p : plugin.LevelReferenceKeys.keySet()) {
			String[] reference = plugin.LevelReferenceKeys.get(p);
			if(plugin.Tools.containsValue(reference,string) && plugin.Permissions.hasLevel(s, p)){
			plugin.LCChat.topBar(s);
			plugin.LCChat.info(s, plugin.LevelNames.get(p)+" Level: "+plugin.LevelFunctions.getLevel(s, p));
			plugin.LCChat.info(s, plugin.LevelNames.get(p)+" Experience: "+plugin.Tools.roundTwoDecimals(plugin.LevelFunctions.getExp(s, p)));
			plugin.LCChat.info(s, "Experience to next level: "+plugin.Tools.roundTwoDecimals(plugin.LevelFunctions.getExpLeft(s, p)));
			return;
			}
		}
		plugin.LCChat.info(s, "No Level Found.");
	}
    @SuppressWarnings("static-access")
	public void listLevels(Player player){
    	String s = "Your Active Levels are: ";
    	boolean one =false;
    	for (Plugin p : plugin.LevelNames.keySet()) {
            if(one && plugin.Permissions.hasLevel(player, p)) s = s + ",";
    		if(plugin.Permissions.hasLevel(player, p)){ s = s+plugin.LevelNames.get(p)+"("+plugin.LevelIndexes.get(p)+")";
    		one = true;
    		}
    	}
    	plugin.LCChat.info(player, s);
    }
	@SuppressWarnings("static-access")
	public void about(CommandSender sender) {
		plugin.LCChat.topBar(sender);
		plugin.LCChat.info(sender, "/lvl list - Shows active stats.");
		plugin.LCChat.info(sender, "/lvl notify - Toggles notifications.");
		plugin.LCChat.info(sender, "/lvl "+ plugin.Tools.getIndexBar((Player) sender)+" - Shows Level Statistics." );
		plugin.LCChat.info(sender, "/lvl unlocks "+ plugin.Tools.getIndexBar((Player) sender)+" - Shows Tool and Block unlocks." );
		plugin.LCChat.info(sender, "/lvl exp "+ plugin.Tools.getIndexBar((Player) sender)+" - Shows Experience For Actions." );
		plugin.LCChat.info(sender, "/lvl shout "+ plugin.Tools.getIndexBar((Player) sender)+" - Shouts Level Statistics." );
		plugin.LCChat.info(sender, "/lvl total - Shows total Level.");
		plugin.LCChat.info(sender, "/lvl all - Shows all Levels.");
        plugin.LCChat.info(sender, "/lvl  - Shows this.");
        if(plugin.Permissions.isAdmin(sender)){
        	plugin.LCChat.good(sender, "/lvl admin - Admin Commands.");
        }
        return;
	
	}
	@SuppressWarnings("static-access")
	public void credits(CommandSender sender) {
		plugin.LCChat.topBar(sender);
		plugin.LCChat.good(sender, "LevelCraftCore by Samkio.");
        for(Plugin p:plugin.LevelAuthors.keySet()){
        	plugin.LCChat.good(sender, plugin.LevelNames.get(p)+" by "+plugin.LevelAuthors.get(p)+".");
        }
        return;
	
	}

	@SuppressWarnings("static-access")
	public void determineMethod(Player sender, String[] args) {
		if(args.length==1){
			if(args[0].equalsIgnoreCase("list")){
				plugin.LCCommands.listLevels(sender);
				return;
			}else if(args[0].equalsIgnoreCase("total")){
				plugin.LCCommands.Total(sender);
				return;
			}else if(args[0].equalsIgnoreCase("all")){
				plugin.LCCommands.All(sender);
				return;
			}else if(args[0].equalsIgnoreCase("credits")){
				plugin.LCCommands.credits(sender);
				return;
			}else if(args[0].equalsIgnoreCase("notify")){
				plugin.LCCommands.Notify(sender);
				return;
			}else if(args[0].equalsIgnoreCase("admin")){
				plugin.LCCommands.Admin(sender, args);
				return;
			}else if(!args[0].equalsIgnoreCase("admin")){
				plugin.LCCommands.showStat(sender, args[0]);
				return;
			}
		}else if(args.length==2){
			if(args[0].equalsIgnoreCase("shout")){
				plugin.LCCommands.Shout(sender,args[1]);
				return;
			}else if(args[0].equalsIgnoreCase("unlocks")){
				plugin.LCCommands.Unlocks(sender,args[1]);
				return;
			}else if(args[0].equalsIgnoreCase("exp")){
				plugin.LCCommands.Exp(sender,args[1]);
				return;
				
			/*}else if(args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("reload")){
				if(plugin.LCAdminCommands.reload()){
					plugin.LCChat.good(sender, "LevelCraft Sucessfully Reloaded");
				}else{
					plugin.LCChat.warn(sender, "LevelCraft Could not be Reloaded");
				}*/
			}
		}else if(args[0].equalsIgnoreCase("admin")){
			plugin.LCCommands.Admin(sender,args);
			return;
		}else{
			plugin.LCChat.info(sender, "Wrong Syntax. Please use '/level' for more infomation.");
		}
		
	}

	@SuppressWarnings("static-access")
	private void Exp(Player sender, String string) {
	   for (Plugin p : plugin.LevelReferenceKeys.keySet()) {
			String[] reference = plugin.LevelReferenceKeys.get(p);
			if(plugin.Tools.containsValue(reference,string) && plugin.Permissions.hasLevel(sender, p)){
				plugin.LCChat.topBar(sender);
				for(String s:plugin.LevelExp.get(p)){
					plugin.LCChat.info(sender,s);
				}
			break;
			}
		}
	}

	@SuppressWarnings("static-access")
	private void Admin(Player sender, String[] args) {
		if(!plugin.Permissions.isAdmin(sender)){
			plugin.LCChat.warn(sender, "You do not have permission to use this command.");
			return;
		}
		if(args.length <= 1){
			plugin.LCAdminCommands.Help(sender);
			return;
		}else if(args.length >= 4){
			plugin.LCAdminCommands.determineMethid(sender,args);
			return;
		}else{
			plugin.LCAdminCommands.Syntax(sender);
			return;
		}
		
	}

	@SuppressWarnings("static-access")
	private void Unlocks(Player sender, String string) {
		for (Plugin p : plugin.LevelReferenceKeys.keySet()) {
			String[] reference = plugin.LevelReferenceKeys.get(p);
			if(plugin.Tools.containsValue(reference,string) && plugin.Permissions.hasLevel(sender, p)){
				plugin.LCChat.topBar(sender);
				String[] Unlocks = plugin.LevelUnlocks.get(p);
				int[] UnlockLevel = plugin.LevelUnlocksLevel.get(p);
				int level = plugin.LevelFunctions.getLevel(sender, p);				
				for(int i = 0; i < Unlocks.length;i++){
					if(UnlockLevel[i]>level){
						plugin.LCChat.warn(sender, Unlocks[i]);
					}else{
						plugin.LCChat.good(sender, Unlocks[i]);
					}
				}
			break;
			}
		}
	}

	@SuppressWarnings("static-access")
	private void Shout(Player sender, String string) {
		
		if(!plugin.Permissions.canShout(sender)){ plugin.LCChat.warn(sender,"You do not have permission to shout."); return; }
		if(string.equalsIgnoreCase("total")){
			boolean oneStat = false;
			int TotalLevel = 0;
			for (Plugin p : plugin.LevelNames.keySet()) {
				if(plugin.Permissions.hasLevel(sender, p)){
				TotalLevel = TotalLevel + plugin.LevelFunctions.getLevel(sender, p);
				oneStat = true;
				}
			}		
			if(oneStat){
				plugin.LCChat.broadcast(sender.getName()+"'s total level is "+TotalLevel+".");
				
			}else{
				plugin.LCChat.warn(sender, "No levels found.");
			}
			return;
		}
		for (Plugin p : plugin.LevelReferenceKeys.keySet()) {
			String[] reference = plugin.LevelReferenceKeys.get(p);
			if(plugin.Tools.containsValue(reference,string) && plugin.Permissions.hasLevel(sender, p)){
				plugin.LCChat.broadcast(sender.getName()+"'s "+plugin.LevelNames.get(p) +" level is "+plugin.LevelFunctions.getLevel(sender,p)+".");
			break;
			}
		}
		
	}

	private void Notify(Player sender) {
		plugin.Tools.toggleNotify(sender);
		return;
		
	}

	@SuppressWarnings("static-access")
	private void Total(Player sender) {
		boolean oneStat = false;
		int TotalLevel = 0;
		double TotalExp = 0;
		String HighestLevel = "None";
		int HighestLevelInt = 0;
		for (Plugin p : plugin.LevelNames.keySet()) {
			if(plugin.Permissions.hasLevel(sender, p)){
			TotalLevel = TotalLevel + plugin.LevelFunctions.getLevel(sender, p);
			TotalExp = TotalExp + plugin.LevelFunctions.getExp(sender, p);
			if(HighestLevelInt<plugin.LevelFunctions.getLevel(sender, p)) {HighestLevel = plugin.LevelNames.get(p); HighestLevelInt =plugin.LevelFunctions.getLevel(sender, p); }
			oneStat = true;
			}
		}		
		if(oneStat){
			plugin.LCChat.topBar(sender);
			plugin.LCChat.info(sender, "Total Level: "+TotalLevel);
			plugin.LCChat.info(sender, "Total Experience: "+TotalExp);
			plugin.LCChat.info(sender, "Highest Level: "+HighestLevel);
			
		}else{
			plugin.LCChat.warn(sender, "No levels found.");
		}
	}

	@SuppressWarnings("static-access")
	public void All(Player sender) {
		boolean oneStat = false;
		String levels = "";
		for (Plugin p : plugin.LevelNames.keySet()) {
			if(plugin.Permissions.hasLevel(sender, p)){
			levels = levels + plugin.LevelNames.get(p)+"("+plugin.LevelIndexes.get(p)+"): " + plugin.LevelFunctions.getLevel(sender, p)+".";
			oneStat = true;
			}
		}
		
		if(oneStat){
			String[] lines = levels.split("\\.");			
			plugin.LCChat.topBar(sender);
			for(int i = 0; i < lines.length;i++){
				plugin.LCChat.info(sender, lines[i]);
			}
			return;
		}else{
			plugin.LCChat.warn(sender, "No levels found.");
		}
		
	}

	
}
