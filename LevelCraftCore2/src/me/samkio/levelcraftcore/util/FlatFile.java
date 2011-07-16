package me.samkio.levelcraftcore.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.TreeMap;
import java.util.logging.Level;

import me.samkio.levelcraftcore.LevelCraftCore;

public class FlatFile {
	public LevelCraftCore plugin;

	public FlatFile(LevelCraftCore instance) {
		plugin = instance;
	}

	public boolean write(String s, double v, File file) {
		Properties pro = new Properties();
		String value = (new Double(v)).toString();
		try {
			FileInputStream in = new FileInputStream(file);
			pro.load(in);
			pro.setProperty(s, value);
			pro.store(new FileOutputStream(file), null);
			in.close();
			return true;
		} catch (IOException e) {
			plugin.logger.log(Level.SEVERE, "[LC] Error writing user to file.");
			plugin.logger.log(Level.SEVERE, "[LC]" + e);
			return false;
		}

	}
    @SuppressWarnings("deprecation")
	public boolean purge(){
    	for(File file:plugin.LevelFiles.values()){
    	Properties pro = new Properties();
		try {
			//plugin.logger.info(file.getName());
			FileInputStream in = new FileInputStream(file);
			pro.load(in);
			ArrayList<String> Removers = new ArrayList<String>();
			for(Object s:pro.keySet()){
				String str = pro.getProperty((String) s);
				//plugin.logger.info(str);
				double var = Double.parseDouble(str);
				if(var==0){
					Removers.add((String) s);
					//plugin.logger.info("Removed Added:"+s);
				}
			}
			for(String s:Removers){
				pro.remove(s);
			}
			//pro.store(arg0, arg1)
			FileOutputStream d = new FileOutputStream(file);
			pro.save(d, "Purged: [TIME]");
			Removers.clear();
			in.close();
			//String string = pro.getProperty(s);
			//double var = Double.parseDouble(string);
			//in.close();
			//return var;
			continue;
		} catch (IOException e) {
			plugin.logger.log(Level.SEVERE,
					"[LC] Error getting experience from file.");
			plugin.logger.log(Level.SEVERE, "[LC]" + e);
			return false;
		}
    	}
    	
    	
		return true;
    	
    }
	public double getDouble(String s, File file) {
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
	public int getPos(String s, File file) {
		Properties pro = new Properties();
		try {
			HashMap<String,Double> map = new HashMap<String,Double>();
			ValueComparator bvc =  new ValueComparator(map);
		    TreeMap<String,Double> sorted_map = new TreeMap(bvc);

			FileInputStream in = new FileInputStream(file);
			pro.load(in);
			for(Object  o:pro.keySet()){
				String player = (String) o;
				map.put(player,Double.parseDouble(pro.getProperty(player)));
			}
			sorted_map.putAll(map);
			int rank = 0;
			for (String key : sorted_map.keySet()) {
				rank++;
				if(key.equalsIgnoreCase(s)) break;
	           // plugin.logger.info("key/value: " + key + "/"+sorted_map.get(key));
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
	public boolean contains(String str, File file) {
		Properties pro = new Properties();
		try {
			FileInputStream in = new FileInputStream(file);
			pro.load(in);

			if (pro.containsKey(str))

				return true;
		} catch (IOException e) {
			plugin.logger.log(Level.SEVERE,
					"[LC] Error getting value from file.");
			plugin.logger.log(Level.SEVERE, "[LC]" + e);
		}

		return false;
	}

	public void write(String str, File file, double var) {
		Properties pro = new Properties();
		String stringvar = (new Double(var)).toString();
		try {
			FileInputStream in = new FileInputStream(file);
			pro.load(in);
			pro.setProperty(str, stringvar);
			pro.store(new FileOutputStream(file), null);
			in.close();
		} catch (IOException e) {
			plugin.logger.log(Level.SEVERE, "[LC] Error writing to file.");
			plugin.logger.log(Level.SEVERE, "[LC]" + e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getPlayerAtPos(String string, int i, File file) {
		Properties pro = new Properties();
		String p = "None";
		try {
			HashMap<String,Double> map = new HashMap<String,Double>();
			ValueComparator bvc =  new ValueComparator(map);
		    TreeMap<String,Double> sorted_map = new TreeMap(bvc);

			FileInputStream in = new FileInputStream(file);
			pro.load(in);
			for(Object  o:pro.keySet()){
				String player = (String) o;
				map.put(player,Double.parseDouble(pro.getProperty(player)));
			}
			sorted_map.putAll(map);
			int rank = 0;
			for (String key : sorted_map.keySet()) {
				rank++;
				if(rank==i){
					p=key;
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

}
