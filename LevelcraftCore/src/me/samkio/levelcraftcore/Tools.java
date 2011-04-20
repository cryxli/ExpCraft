package me.samkio.levelcraftcore;

import java.text.DecimalFormat;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Tools {
	public  LevelCraftCore plugin;
	public Tools(LevelCraftCore instance) {
		plugin = instance;
	}
	public boolean containsValue(String[] Array, String s) {
		for (int i = 0; i < Array.length; i++) {
			if (Array[i].equalsIgnoreCase(s))
				return true;

		}
		return false;
	}
	public  double roundTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(d));
	}
	public boolean enabled(CommandSender sender) {
		return plugin.NotifyUsers.containsKey(sender);
	}

	@SuppressWarnings("static-access")
	public void toggleNotify(CommandSender sender) {
		if (enabled(sender)) {
			plugin.NotifyUsers.remove(sender);
			plugin.LCChat.good(sender, "Experience notify disabled.");
		} else {
			plugin.NotifyUsers.put((Player) sender, "");
			plugin.LCChat.good(sender, "Experience notify enabled.");
			}
	}
	@SuppressWarnings("static-access")
	public String getIndexBar(Player p){
		String str = "[";
		boolean one = false;
		for (Plugin p1 : plugin.LevelIndexes.keySet()) {
			if(one && plugin.Permissions.hasLevel(p, p1)) str = str + "/";
			if(plugin.Permissions.hasLevel(p, p1)) {str = str + plugin.LevelIndexes.get(p1);
			one = true;
			}
		}
		str = str + "]";
		return str;
	}
	public String format(String str) {
		str = str.replace("BLACK", "\u00A70"); // Black
		str = str.replace("DARK_BLUE", "\u00A71"); // Dark Blue
		str = str.replace("DARK_GREEN", "\u00A72"); // Dark Green
		str = str.replace("DARK_AQUA", "\u00A73"); // Dark Aqua
		str = str.replace("DARK_RED", "\u00A74"); // Dark Red
		str = str.replace("DARK_PURPLE", "\u00A75"); // Dark Purple
		str = str.replace("GOLD", "\u00A76"); // Gold
		str = str.replace("GRAY", "\u00A77"); // Gray
		str = str.replace("DARK_GRAY", "\u00A78"); // Dark Gray
		str = str.replace("BLUE", "\u00A79"); // Blue
		str = str.replace("GREEN", "\u00A7A"); // Green
		str = str.replace("AQUA", "\u00A7B"); // Aqua
		str = str.replace("RED", "\u00A7C"); // Red
		str = str.replace("LIGHT_PURPLE", "\u00A7D"); // Light Purple
		str = str.replace("YELLOW", "\u00A7E"); // Yellow
		str = str.replace("WHITE", "\u00A7F"); // White
		return str;
		}
}
