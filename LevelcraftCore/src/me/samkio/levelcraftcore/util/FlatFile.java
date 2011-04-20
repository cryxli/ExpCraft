package me.samkio.levelcraftcore.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

import me.samkio.levelcraftcore.LevelCraftCore;




public class FlatFile {
	public  LevelCraftCore plugin;
	public FlatFile(LevelCraftCore instance) {
		plugin = instance;
	}
	public  boolean write(String s, double v, File file) {
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
				plugin.logger.log(Level.SEVERE,"[LC] Error writing user to file.");
				plugin.logger.log(Level.SEVERE,"[LC]"+e);
				return false;
			}

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
			plugin.logger.log(Level.SEVERE,"[LC] Error getting experience from file.");
			plugin.logger.log(Level.SEVERE,"[LC]"+e);
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
			plugin.logger.log(Level.SEVERE,"[LC] Error getting value from file.");
			plugin.logger.log(Level.SEVERE,"[LC]"+e);
		}

		return false;
	}
	public void write(String str,  File file, double var) {
		Properties pro = new Properties();
		String stringvar = (new Double(var)).toString();
		try {
			FileInputStream in = new FileInputStream(file);
			pro.load(in);
			pro.setProperty(str, stringvar);
			pro.store(new FileOutputStream(file), null);
			in.close();
		} catch (IOException e) {
			plugin.logger.log(Level.SEVERE,"[LC] Error writing to file.");
			plugin.logger.log(Level.SEVERE,"[LC]"+e);
		}
	}

	
}
