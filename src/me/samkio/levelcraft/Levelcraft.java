package me.samkio.levelcraft;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import me.samkio.levelcraft.Functions.LevelFunctions;
import me.samkio.levelcraft.Functions.PlayerFunctions;
import me.samkio.levelcraft.Listeners.LCBlockListener;
import me.samkio.levelcraft.Listeners.LCEntityListener;
import me.samkio.levelcraft.Listeners.LCPlayerListener;
import me.samkio.levelcraft.SamToolbox.DataMySql;
import me.samkio.levelcraft.SamToolbox.DataSqlite;
import me.samkio.levelcraft.SamToolbox.FurnaceRecipe;
import me.samkio.levelcraft.SamToolbox.Level;
import me.samkio.levelcraft.SamToolbox.Toolbox;
import net.minecraft.server.FurnaceRecipes;

import me.samkio.levelcraft.Skills.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class Levelcraft extends JavaPlugin {
	public
	final Admin Admin = new Admin(this);
    public final Settings Settings = new Settings(this);
	public final Help Help = new Help(this);
	public final PlayerFunctions PlayerFunctions = new PlayerFunctions(this);
	public final LevelFunctions LevelFunctions = new LevelFunctions(this);
	public final Level Level = new Level(this);
	public final Whitelist Whitelist = new Whitelist(this);
	public final DataMySql DataMySql = new DataMySql(this);
	public final DataSqlite DataSqlite = new DataSqlite(this);
	public  final Logger log = Logger.getLogger("Minecraft");
	private final LCPlayerListener playerListener = new LCPlayerListener(this);
	private final LCBlockListener blockListener = new LCBlockListener(this);
	private final LCEntityListener entityListener = new LCEntityListener(this);
	public final Toolbox Toolbox  = new Toolbox (this);
	
	public final Archer Archer  = new Archer (this);
	public final Dig Dig = new Dig(this);
	public final Fisticuffs Fisticuffs = new Fisticuffs(this);
	@SuppressWarnings("unused")
	private final Forge Smithing = new Forge(this);
	public final Mine Mine = new Mine(this);
	public final Range Range = new Range(this);
	public final Slayer Slayer = new Slayer(this);
	public final Wood Wood = new Wood(this);
	
	
	public  String maindirectory = "plugins/LevelCraft/";
	public  String datadirectory = "Experience/";
	public  String configdirectory = "Config/";
	public  File WCExpFile = new File(maindirectory + datadirectory
			+ "WoodCutting.exp");
	public  File MiExpFile = new File(maindirectory + datadirectory
			+ "Mining.exp");
	public  File SlayExpFile = new File(maindirectory + datadirectory
			+ "Slaying.exp");
	public  File RangeExpFile = new File(maindirectory + datadirectory
			+ "Ranging.exp");
	public  File FisticuffsExpFile = new File(maindirectory
			+ datadirectory + "Fisticuffs.exp");
	public  File ArcherExpFile = new File(maindirectory + datadirectory
			+ "Archer.exp");
	public  File DiggingExpFile = new File(maindirectory + datadirectory
			+ "Digging.exp");
	public  File ForgeExpFile = new File(maindirectory + datadirectory
			+ "Forge.exp");
	public  PermissionHandler Permissions;
	public boolean Citizens = false;
	public  ArrayList<FurnaceRecipe> furnaceRecipeObjects = new ArrayList<FurnaceRecipe>();
	
   
	/*
	 * public Levelcraft(PluginLoader pluginLoader, Server instance,
	 * PluginDescriptionFile desc, File folder, File plugin, ClassLoader
	 * cLoader) { super(); }
	 */

	@Override
	public void onDisable() {
		System.out.println("Levelcraft Disabled");

	}

	@Override
	public void onEnable() {
		load();
		registerEvents();
		if(Settings.repairTools){
		populateFurnaceRecipes();
		addFurnaceRecipe(furnaceRecipeObjects);
		}
		if (Settings.UsePerms) {
			setupPermissions();
		} else {
			log.info("[LC] Using Whitelist.");
		}

		setupCitizens();

		PluginDescriptionFile pdfFile = this.getDescription();
		log.info("[LC] Levelcraft version " + pdfFile.getVersion()
				+ " is enabled!");

	}

	private void setupPermissions() {
		Plugin test = this.getServer().getPluginManager()
				.getPlugin("Permissions");

		if (this.Permissions == null) {
			if (test != null) {
				this.Permissions = ((Permissions) test).getHandler();
				log.info("[LC] Using Permissions.");
			} else {
				log.info("[LC] Permissions not found using whitelist.");
				Settings.UsePerms = false;
			}
		}
	}

	private void setupCitizens() {
		Plugin test = this.getServer().getPluginManager().getPlugin("Citizens");
		if (test != null) {
			Citizens = true;
			log.info("[LC] Found Citizens Noted!");
		} else {
			Citizens = false;
		}

	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (commandLabel.equalsIgnoreCase("level")
				|| commandLabel.equalsIgnoreCase("lvl")) {
			if (args.length >= 1 && sender instanceof Player) {
				PlayerFunctions.checkAccount(sender);
				PlayerFunctions.doThis(sender, args, this);
				return true;
			} else {
				About(sender);
				return false;
			}
		} else {
			return false;
		}
	}

	public void About(CommandSender sender) {
		log.info(Settings.c1);
		sender.sendMessage(ChatColor.valueOf(Settings.c1) 
				+ "[LC] ---LevelCraftPlugin By Samkio (C)2011--- ");
		sender.sendMessage(ChatColor.valueOf(Settings.c1)  + "[LC]" + ChatColor.valueOf(Settings.c2) 
				+ " /lvl list - Shows active stats.");
		sender.sendMessage(ChatColor.valueOf(Settings.c1)  + "[LC]" + ChatColor.valueOf(Settings.c2) 
				+ " /lvl [w|m|s|r|f|a|d|c] - Shows stats statisics.");
		sender.sendMessage(ChatColor.valueOf(Settings.c1)  + "[LC]" +ChatColor.valueOf(Settings.c2) 
				+ " /lvl notify - Toggles notifications.");
		sender.sendMessage(ChatColor.valueOf(Settings.c1)  + "[LC]" + ChatColor.valueOf(Settings.c2) 
				+ " /lvl unlocks [w|m|s|r|f|a|d|c] - Shows tool level unlocks.");
		sender.sendMessage(ChatColor.valueOf(Settings.c1)  + "[LC]" + ChatColor.valueOf(Settings.c2) 
				+ " /lvl shout [w|m|s|r|f|a|d|c] - Display level to the server.");
		sender.sendMessage(ChatColor.valueOf(Settings.c1)  + "[LC]" + ChatColor.valueOf(Settings.c2) 
				+ " /lvl or /level - Shows this.");
	}

	void load() {
		new File(maindirectory).mkdirs();
		new File(maindirectory + datadirectory).mkdirs();
		new File(maindirectory + configdirectory).mkdirs();

		Settings.loadMain();
		Settings.loadWoodcut();
		Settings.loadSlayer();
		Settings.loadMine();
		Settings.loadWhitelist();
		Settings.loadRange();
		Settings.loadFisticuffs();
		Settings.loadArcher();
		Settings.loadDigging();
		Settings.loadForge();

		
		if (Settings.database.equalsIgnoreCase("flatfile")) {

			if (!WCExpFile.exists() || !MiExpFile.exists()
					|| !SlayExpFile.exists() || !DiggingExpFile.exists()
					|| !RangeExpFile.exists() || !FisticuffsExpFile.exists() || !ForgeExpFile.exists())
				try {
					WCExpFile.createNewFile();
					MiExpFile.createNewFile();
					SlayExpFile.createNewFile();
					RangeExpFile.createNewFile();
					FisticuffsExpFile.createNewFile();
					ArcherExpFile.createNewFile();
					ForgeExpFile.createNewFile();
					DiggingExpFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
		} else if (Settings.database.equalsIgnoreCase("mysql")) {
			DataMySql.PrepareDB();
		} else if (Settings.database.equalsIgnoreCase("sqlite")) {
			DataSqlite.PrepareDB();
		} else {
			log.severe("[Levelcraft] Nowhere to save data! Edit MainConfig Database.");
			getServer().getPluginManager().disablePlugin(this);
		}
	}

	void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.ENTITY_DAMAGE, this.entityListener,
				Event.Priority.Highest, this);
		pm.registerEvent(Event.Type.ENTITY_TARGET, this.entityListener,
				Event.Priority.Highest, this);
		pm.registerEvent(Event.Type.PLAYER_JOIN, this.playerListener,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener,
				Event.Priority.Highest, this);
		pm.registerEvent(Event.Type.BLOCK_PLACE, blockListener,
				Event.Priority.Low, this);
		//pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener,Event.Priority.Highest, this);
	}

	private void populateFurnaceRecipes() {


		int[] ingredientID = { 276,277,278,279,256,257,258,267,272,273,274,275,283,284,285,286,291,292,293,294,302,303,304,305,306,307,308,309,310,311,312,313,314,315,316,317 };
		int[] resultID =     { 276,277,278,279,256,257,258,267,272,273,274,275,283,284,285,286,291,292,293,294,302,303,304,305,306,307,308,309,310,311,312,313,314,315,316,317 };
		int resultData = 0;
		double cooktime =  0.0D;
		

		for (int i = 0; i < ingredientID.length; i++) {
			net.minecraft.server.ItemStack temp = new net.minecraft.server.ItemStack(resultID[i], 1, resultData);
			CraftItemStack result = new CraftItemStack(temp);
			furnaceRecipeObjects.add(new FurnaceRecipe(ingredientID[i], result,cooktime, resultData));
		}

	}

	public  void addFurnaceRecipe(ArrayList<FurnaceRecipe> recipes) {
		int count = 0;
		for (FurnaceRecipe fr : recipes) {
			net.minecraft.server.ItemStack result = null;
			if (fr.getResult().getTypeId() != 0) {
				result = new net.minecraft.server.ItemStack(fr.getResult()
						.getTypeId(), fr.getResult().getAmount(), fr.getData());
			}
			FurnaceRecipes.a().a(fr.getIngredient(), result);
			count++;
		}
	}

	public boolean shouldNotContinue(String line) {
		if (line.length() == 0) {
			return false;
		}
		return line.trim().charAt(0) != '#';
	}
}
