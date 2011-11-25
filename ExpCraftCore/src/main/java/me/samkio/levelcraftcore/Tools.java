package me.samkio.levelcraftcore;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Tools {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map sortByValue(final Map map) {
		List list = new LinkedList(map.entrySet());
		Collections.sort(list, new Comparator() {
			@Override
			public int compare(final Object o1, final Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getValue())
						.compareTo(((Map.Entry) (o2)).getValue());
			}
		});

		Map result = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	public LevelCraftCore plugin;

	public Tools(final LevelCraftCore instance) {
		plugin = instance;
	}

	public void checkAccount(final Player p) {

		if (plugin.database.equalsIgnoreCase("flatfile")) {
			/*
			 * if(plugin.Specialisation){
			 * if(!plugin.FlatFile.contains(p.getName(), plugin.Special)) {
			 * plugin.FlatFile.writeS(p.getName(), plugin.Special, "NULL"); } }
			 */
			for (File f : plugin.LevelFiles.values()) {
				if (plugin.FlatFile.contains(p.getName(), f)) {
					continue;
				}
				if (!plugin.FlatFile.write(p.getName(), 0, f)) {
					;
				}
			}
		} else if (plugin.database.equalsIgnoreCase("sqlite")) {
			if (plugin.SqliteDB.contains(p.getName())) {
				return;
			}
			plugin.SqliteDB.newP(p.getName());
		} else if (plugin.database.equalsIgnoreCase("mysql")) {
			if (plugin.MySqlDB.contains(p.getName())) {
				return;
			}
			plugin.MySqlDB.newP(p.getName());
		}
	}

	public boolean containsValue(final String[] Array, final String s) {
		for (int i = 0; i < Array.length; i++) {
			if (Array[i].equalsIgnoreCase(s)) {
				return true;
			}

		}
		return false;
	}

	public int convertToInt(final String s) {
		return Integer.parseInt(s);
	}

	public boolean enabled(final CommandSender sender) {
		return plugin.NotifyUsers.containsKey(sender);
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

	public String getIndexBar(final Player player) {
		StringBuffer buf = new StringBuffer("[");
		boolean one = false;
		for (Plugin p1 : plugin.LevelIndexes.keySet()) {
			if (one && Whitelist.hasLevelExp(player, p1)) {
				buf.append("/");
			}
			if (Whitelist.hasLevelExp(player, p1)) {
				buf.append(plugin.LevelIndexes.get(p1));
				one = true;
			}
		}
		return buf.append("]").toString();
	}

	public Plugin getPluginFromName(final String s) {
		for (Plugin p : plugin.LevelNames.keySet()) {
			if (plugin.LevelNames.get(p).equalsIgnoreCase(s)) {
				return p;
			}
		}
		return null;
	}

	public double roundTwoDecimals(final double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(d));
	}

	public void toggleNotify(final CommandSender sender) {
		if (enabled(sender)) {
			plugin.NotifyUsers.remove(sender);
			LCChat.good(sender, plugin.lang.NotifyOff);
		} else {
			plugin.NotifyUsers.put((Player) sender, "");
			LCChat.good(sender, plugin.lang.NotifyOn);
		}
	}
}
