package me.samkio.levelcraftcore.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;

import me.samkio.levelcraftcore.LevelCraftCore;

public class FlatFile {
	public LevelCraftCore plugin;

	public HashMap<String, Set<String>> accountCache = new HashMap<String, Set<String>>();

	// public Set<String> accountCache = new HashSet<String>();

	public FlatFile(final LevelCraftCore instance) {
		plugin = instance;
	}

	public boolean contains(final String str, final File file) {
		String fileName = file.getPath() + file.getName();
		if (accountCache.containsKey(fileName)
				&& accountCache.get(fileName).contains(str)) {
			return true;
		}
		Properties pro = new Properties();
		try {
			FileInputStream in = new FileInputStream(file);
			pro.load(in);

			if (pro.containsKey(str)) {
				in.close();
				if (!accountCache.containsKey(fileName)) {
					accountCache.put(fileName, new HashSet<String>());
				}
				Set<String> set = accountCache.get(fileName);
				set.add(str);
				return true;
			}
			in.close();
		} catch (IOException e) {
			plugin.logger.log(Level.SEVERE,
					"[LC] Error getting value from file.");
			plugin.logger.log(Level.SEVERE, "[LC]" + e);
		}

		return false;
	}

	public double getDouble(final String s, final File file) {
		Properties pro = new Properties();
		try {
			FileInputStream in = new FileInputStream(file);
			pro.load(in);
			String string = pro.getProperty(s);
			double var = Double.parseDouble(string);
			in.close();
			return var;
		} catch (IOException e) {
			plugin.logger.log(Level.SEVERE,
					"[LC] Error getting experience from file.");
			plugin.logger.log(Level.SEVERE, "[LC]" + e);
			return 0;
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getPlayerAtPos(final String string, final int i,
			final File file) {
		Properties pro = new Properties();
		String p = "None";
		try {
			HashMap<String, Double> map = new HashMap<String, Double>();
			ValueComparator bvc = new ValueComparator(map);
			TreeMap<String, Double> sorted_map = new TreeMap(bvc);

			FileInputStream in = new FileInputStream(file);
			pro.load(in);
			for (Object o : pro.keySet()) {
				String player = (String) o;
				map.put(player, Double.parseDouble(pro.getProperty(player)));
			}
			sorted_map.putAll(map);
			int rank = 0;
			for (String key : sorted_map.keySet()) {
				rank++;
				if (rank == i) {
					p = key;
				}
			}

			in.close();
			return p;
		} catch (IOException e) {
			plugin.logger.log(Level.SEVERE,
					"[LC] Error getting experience from file.");
			plugin.logger.log(Level.SEVERE, "[LC]" + e);
			return "None";
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getPos(final String s, final File file) {
		Properties pro = new Properties();
		try {
			HashMap<String, Double> map = new HashMap<String, Double>();
			ValueComparator bvc = new ValueComparator(map);
			TreeMap<String, Double> sorted_map = new TreeMap(bvc);

			FileInputStream in = new FileInputStream(file);
			pro.load(in);
			for (Object o : pro.keySet()) {
				String player = (String) o;
				map.put(player, Double.parseDouble(pro.getProperty(player)));
			}
			sorted_map.putAll(map);
			int rank = 0;
			for (String key : sorted_map.keySet()) {
				rank++;
				if (key.equalsIgnoreCase(s)) {
					break;
					// plugin.logger.info("key/value: " + key +
					// "/"+sorted_map.get(key));
				}
			}

			in.close();
			return rank;
		} catch (IOException e) {
			plugin.logger.log(Level.SEVERE,
					"[LC] Error getting experience from file.");
			plugin.logger.log(Level.SEVERE, "[LC]" + e);
			return 0;
		}

	}

	public String getString(final String s, final File file) {
		Properties pro = new Properties();
		try {
			FileInputStream in = new FileInputStream(file);
			pro.load(in);
			String string = pro.getProperty(s);
			in.close();
			return string;
		} catch (IOException e) {
			plugin.logger.log(Level.SEVERE,
					"[LC] Error getting experience from file.");
			plugin.logger.log(Level.SEVERE, "[LC]" + e);
			return null;
		}

	}

	@SuppressWarnings("deprecation")
	public boolean purge() {
		for (File file : plugin.LevelFiles.values()) {
			Properties pro = new Properties();
			try {
				// plugin.logger.info(file.getName());
				FileInputStream in = new FileInputStream(file);
				pro.load(in);
				ArrayList<String> Removers = new ArrayList<String>();
				for (Object s : pro.keySet()) {
					String str = pro.getProperty((String) s);
					// plugin.logger.info(str);
					double var = Double.parseDouble(str);
					if (var == 0) {
						Removers.add((String) s);
						// plugin.logger.info("Removed Added:"+s);
					}
				}
				for (String s : Removers) {
					pro.remove(s);
				}
				// pro.store(arg0, arg1)
				FileOutputStream d = new FileOutputStream(file);
				pro.save(d, "Purged: [TIME]");
				Removers.clear();
				in.close();
				d.close();
				// String string = pro.getProperty(s);
				// double var = Double.parseDouble(string);
				// in.close();
				// return var;
				continue;
			} catch (IOException e) {
				plugin.logger.log(Level.SEVERE, "[LC] Error purging.");
				plugin.logger.log(Level.SEVERE, "[LC]" + e);
				return false;
			}
		}

		return true;

	}

	public boolean write(final String str, final double value, final File file) {
		return writeS(str, file, String.valueOf(value));
	}

	public void write(final String str, final File file, final double var) {
		writeS(str, file, String.valueOf(var));
	}

	public boolean writeS(final String str, final File file, final String var) {
		Properties pro = new Properties();
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(file);
			pro.load(fis);
			pro.setProperty(str, var);
			fos = new FileOutputStream(file);
			pro.store(fos, null);
			return true;
		} catch (IOException e) {
			plugin.logger.log(Level.SEVERE, "[LC] Error writing to file.");
			plugin.logger.log(Level.SEVERE, "[LC]" + e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
		}
		return false;
	}

}
