package me.samkio.levelcraftcore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.reader.UnicodeReader;

public class Class {
	public LevelCraftCore plugin;
	@SuppressWarnings("rawtypes")
	Map<String, Map> data;
	private final Yaml yaml = new Yaml(new SafeConstructor());
	public String DefaultClass;
	public Class(LevelCraftCore instance) {
		plugin = instance;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void load(){
		File file = new File("plugins/LevelCraftCore/Classes.yml");
		try {
			file.createNewFile();
		} catch (IOException e) {
			plugin.logger.severe("[LC]" + e);
		}
		FileInputStream rx = null;
		try {
			rx = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			plugin.logger.severe("[LC]" + e);
			return;
		}
		data = (Map) this.yaml.load(new UnicodeReader(rx));
		try {
			rx.close();
		} catch (IOException e) {
			plugin.logger.severe("[LC]" + e);
			return;
		}
		blankTest();
		getDefault();
		return;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void getDefault() {
		Map<String, Map> Classes = data.get("Classes");
		for(String S:Classes.keySet()){
			Map<String,Object> ClassData = Classes.get(S);
			//plugin.logger.info(ClassData.get("Default")+"");
			if(ClassData.get("Default").equals(true)){ this.DefaultClass = S;
		//	plugin.logger.info(this.DefaultClass);
			break;
			}
		}
		
	}

	public void blankTest(){
		if(data!=null) return;
		@SuppressWarnings("rawtypes")
		Map<String, Map> Classes = new HashMap<String, Map>();
		Map<String,Object> ClassDataM = new HashMap<String,Object>();
		Map<String,Object> ClassDataL = new HashMap<String,Object>();
		Map<String,Object> ClassDataW = new HashMap<String,Object>();
		Map<String,Object> ClassDataMa = new HashMap<String,Object>();
		Map<String,Object> ClassDataF = new HashMap<String,Object>();
		Map<String,Object> ClassDataE = new HashMap<String,Object>();
		
		String[] Miner = {"Mining","Forgery","Excavation","Scavenger"};
		String[] Lumberjack = {"WoodCutting","Farming"};
		String[] Warrior = { "Combat","Range","Defence","Health","Dexterity"};
		String[] Mage = {"Sorcery","Prayer","Health"};
		String[] Farmer = {"Farming"};
		String[] Demolitionist = {"Explosives"};
		String[] AllM = {"*"};
		String[] AllW = {"*"};
		String[] AllL = {"*"};
		String[] AllEx = {"*"};
		String[] AllF = {"*"};
		String[] AllMa = {"*"};
		
		
		ClassDataM.put("Levelable", Miner);
		ClassDataM.put("UnLevelable", AllM);
		ClassDataM.put("Default",false);
		ClassDataM.put("LevelCap", 100);
		ClassDataM.put("Multiplier", 1);
		ClassDataM.put("Prefix", "[Miner]");
		Classes.put("Miner", ClassDataM);
		//ClassData.clear();
		
		ClassDataL.put("Levelable", Lumberjack);
		ClassDataL.put("UnLevelable", AllL);
		ClassDataL.put("Default",false);
		ClassDataL.put("Multiplier", 1);
		ClassDataL.put("Prefix", "[Lumberjack]");
		Classes.put("Lumberjack", ClassDataL);
		//ClassData.clear();
		ClassDataW.put("Levelable", Warrior);
		ClassDataW.put("UnLevelable", AllW);
		ClassDataW.put("Default",true);
		ClassDataW.put("Multiplier", 1);
		ClassDataW.put("Prefix", "[Warrior]");
		Classes.put("Warrior", ClassDataW);
		//ClassData.clear();
		ClassDataMa.put("Levelable", Mage);
		ClassDataMa.put("UnLevelable", AllMa);
		ClassDataMa.put("Default",false);
		ClassDataMa.put("Multiplier", 1);
		ClassDataMa.put("Prefix", "[Mage]");
		Classes.put("Mage", ClassDataMa);
		//ClassData.clear();
		ClassDataF.put("Levelable",Farmer);
		ClassDataF.put("UnLevelable", AllF);
		ClassDataF.put("Default",false);
		ClassDataF.put("Multiplier", 1);
		ClassDataF.put("Prefix", "[Farmer]");
		Classes.put("Farmer", ClassDataF);
		//ClassData.clear();
		ClassDataE.put("Levelable", Demolitionist);
		ClassDataE.put("UnLevelable", AllEx);
		ClassDataE.put("Default",false);
		ClassDataE.put("Multiplier", 1);
		ClassDataE.put("Prefix", "[Demolitionist]");
		Classes.put("Demolitionist", ClassDataE);
		
		@SuppressWarnings("rawtypes")
		Map<String, Map> Users = new HashMap<String, Map>();
		Map<String, Object> UserData = new HashMap<String, Object>();
		UserData.put("Class", "Warrior");
		Users.put("Samkio", UserData);
		
		@SuppressWarnings("rawtypes")
		Map<String, Map> Data = new HashMap<String, Map>();
		Data.put("Users", Users);
		Data.put("Classes", Classes);
		
		
		data = Data;
		write();
	}
	public void write(){
		File file = new File("plugins/LevelCraftCore/Classes.yml");
		FileWriter tx = null;
		try {
			tx = new FileWriter(file, false);
		} catch (IOException e) {
			plugin.logger.severe("[LC]" + e);
			return;
		}
		try {
			tx.write(this.yaml.dump(data));
			tx.flush();
		} catch (IOException e) {
			plugin.logger.severe("[LC]" + e);
			return;
		} finally {
			try {
				tx.close();
			} catch (IOException e) {
				plugin.logger.severe("[LC]" + e);
				return;
			}
		}
		load();
		return;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getClass(String s){
		Map<String, Map> Users = data.get("Users");
		if(Users==null) return "";
		if(Users.containsKey(s)){
			Map<String, Object> UserData = Users.get(s);
			return  (String) UserData.get("Class");
		}
		return "";
		
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean isInValidClass(String s){
		Map<String, Map> Users = data.get("Users");
		Map<String, Map> Classes = data.get("Classes");
		String Group = "";
		if(Users==null) return false;
		if(Users.containsKey(s)){
			Map<String, Object> UserData = Users.get(s);
			Group = (String) UserData.get("Class");
		}else{
			return false;
		}
		if(Classes.containsKey(Group)) return true;
		return false;
		
	}
	public String[] ripLevels(String[] s){
		String[] newArray = new String[s.length];
		//plugin.logger.info(s.length+"");
		for(int i=0;i<s.length;i++){
			//plugin.logger.info("Nom");
			if(s[i].contains("=")){
				//plugin.logger.info("Nomx");
				String[] split = s[i].split("=");
				//nNew.add(split[0]);
				newArray[i] = split[0];
			}else{
			//	plugin.logger.info("NomE");
				newArray[i] = s[i];
			}
//plugin.logger.info(nNew.get(i));
		}
	//	String[] strArray = new String[s.length-1];
	//	plugin.logger.info(strArray[0]+"/");
		//return nNew.toArray(strArray);
		return newArray;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int getMax(String group,String level){
		Map<String, Map> Classes = data.get("Classes");
		Map<String, Object> ClassData = Classes.get(group);
		Object[] Arrayed = ((ArrayList) ClassData.get("Levelable")).toArray();
		String[] String_Array=new String[Arrayed.length];
		for (int i=0;i<String_Array.length;i++) String_Array[i]=Arrayed[i].toString();	
		for(String s:String_Array){
			if(!s.contains("=")) break;
			String[] split = s.split("=");
			//plugin.logger.info(split[0] + "/" + level );
			if(split[0].equalsIgnoreCase(level)) {
				//plugin.logger.info(plugin.Tools.convertToInt(split[1])+"");
				return plugin.Tools.convertToInt(split[1]);
				}
			
		}
		return plugin.LevelCap;
		
		
		
	}
	
	
	
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String[] getLevelableLevels(String s){
		Map<String, Map> Classes = data.get("Classes");
		Map<String, Object> ClassData = Classes.get(s);
		//plugin.logger.info(ClassData+"/");
		Object[] Arrayed = ((ArrayList) ClassData.get("Levelable")).toArray();
		//plugin.logger.info(Arrayed[0]+"/");
		String[] String_Array=new String[Arrayed.length];
		
		for (int i=0;i<String_Array.length;i++) {
			//plugin.logger.info(Arrayed[i].toString());
			String_Array[i]=Arrayed[i].toString();	
		}
		//plugin.logger.info(String_Array[0]);
		return ripLevels(String_Array);		
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String[] getUnLevelableLevels(String s){
		Map<String, Map> Classes = data.get("Classes");
		//plugin.logger.info(Classes+"");
	//plugin.logger.info(s+"");
		Map<String, Object> ClassData = Classes.get(s);
		//plugin.logger.info(ClassData.get("UnLevelable")+"");
		Object[] Arrayed = ((ArrayList) ClassData.get("UnLevelable")).toArray();
		String[] String_Array=new String[Arrayed.length];
		for (int i=0;i<String_Array.length;i++) String_Array[i]=Arrayed[i].toString();
		return String_Array;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getMultiplier(String s){
		Map<String, Map> Classes = data.get("Classes");
		Map<String, Object> ClassData = Classes.get(s);
		return (Integer) ClassData.get("Multiplier");			
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getPrefix(String s){
		Map<String, Map> Classes = data.get("Classes");
		Map<String, Object> ClassData = Classes.get(s);
		return (String) ClassData.get("Prefix");			
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean deleteUser(String s){
		Map<String, Map> Users = data.get("Users");
		if(Users==null) return false;
		if(!Users.containsKey(s)) return true;
		Users.remove(s);
		Map<String, Map> Data = new HashMap<String, Map>();
		Data.put("Users", Users);
		Data.put("Classes", data.get("Classes"));
		data = Data;
		write();
		load();
		return true;
		
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean addUser(String s,String c){
		Map<String, Map> Users = data.get("Users");
		if(Users!=null) {
		if(Users.containsKey(s)) return false;
		}else{
			Users = new HashMap<String,Map>();
		}
		Map<String, Object> UserData = new HashMap<String, Object>();
		UserData.put("Class", c);
		Users.put(s, UserData);
		Map<String, Map> Data = new HashMap<String, Map>();
		Data.put("Users", Users);
		Data.put("Classes", data.get("Classes"));
		data = Data;
		write();
		load();
		return true;
		
	}
	
	
	
}
