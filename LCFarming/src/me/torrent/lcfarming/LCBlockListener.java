package me.torrent.lcfarming;

import me.samkio.levelcraftcore.LCChat;
import me.samkio.levelcraftcore.LevelFunctions;
import me.samkio.levelcraftcore.Whitelist;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

public class LCBlockListener extends BlockListener
{
  public LCFarming plugin;

  public LCBlockListener(LCFarming instance)
  {
    this.plugin = instance;
  }

  public void onBlockBreak(BlockBreakEvent event) {
	  //onBlockBreak, called upon whenever a player breaks a block.
	    if (event.isCancelled()) {
	    	//If the event isCancelled don't bother and return.
	      return;
	}
    
    if (!Whitelist.worldCheck(event.getBlock().getWorld()))
    return;
    //If the Level isnt enabled in the world, return.
    {
    Player player = event.getPlayer();
//Gets the player.
    if (!Whitelist.hasLevel(player, this.plugin.thisPlug))
      return;
  //If the item in hand is not one of these of it is not a block.
    int iih = player.getItemInHand().getTypeId();
    //Gets the item in hand of the player.
    Material m = event.getBlock().getType();
    //Gets the material of the block broken
    int level = LevelFunctions.getLevel(player, this.plugin.thisPlug);
  //If the level is less than the level for this tool and they are holding the tool do this:
    if ((level < this.plugin.LCConfiguration.IronHoe) && (iih == 292)) {
    	//Warn the player they cannot use the tool and state the required level.
      LCChat.warn(player, "Cannot use this tool. Required Level:" + 
        this.plugin.LCConfiguration.IronHoe);
      event.setCancelled(true);
      return;
    //Cancel the event and return.
    }
    //Repeat for all the tools! :)
    if ((level < this.plugin.LCConfiguration.GoldHoe) && (iih == 294)) {
      LCChat.warn(player, "Cannot use this tool. Required Level:" + 
        this.plugin.LCConfiguration.GoldHoe);
      event.setCancelled(true);
      return;
    }
    if ((level < this.plugin.LCConfiguration.WoodHoe) && (iih == 290)) {
      LCChat.warn(player, "Cannot use this tool. Required Level:" + 
        this.plugin.LCConfiguration.WoodHoe);
      event.setCancelled(true);
      return;
    }
    if ((level < this.plugin.LCConfiguration.DiamondHoe) && (iih == 293)) {
      LCChat.warn(player, "Cannot use this tool. Required Level:" + 
        this.plugin.LCConfiguration.DiamondHoe);
      event.setCancelled(true);
      return;
    }
    if ((level < this.plugin.LCConfiguration.StoneHoe) && (iih == 291)) {
      LCChat.warn(player, "Cannot use this tool. Required Level:" + 
        this.plugin.LCConfiguration.StoneHoe);
      event.setCancelled(true);
      return;
    }

	//If the level is less than the level for the block and the block they destroyed is that block. Do this:
  	    if ((level < this.plugin.LCConfiguration.SugarCaneLevel) && 	
  	      (m == Material.SUGAR_CANE_BLOCK))
  	    //Warn the user they cannot mine the block and state the level of the block.
  	    {
  	      LCChat.warn(player, "Cannot mine this block. Required Level:" + 
  	        this.plugin.LCConfiguration.SugarCaneLevel);
  	  //Set the event to cancelled and return.
  	      event.setCancelled(true);
  	      return;
  	    }  
  	    //Repeat for all of the blocks.
  	  if ((level < this.plugin.LCConfiguration.HarvestLevel) && 
  			((m.getId() == 59) && (event.getBlock().getData() == 7)))
  	  	    {
  	  	      LCChat.warn(player, "Cannot harvest this. Required Level:" + 
  	  	        this.plugin.LCConfiguration.HarvestLevel);

  	  	      event.setCancelled(true);
  	  	      return;
  	  	    }   
  	
  	if ((level < this.plugin.LCConfiguration.SaplingLevel) && 
  			(((m == Material.SAPLING))))
  	  	    {
  	  	      LCChat.warn(player, "Cannot place this block. Required Level:" + 
  	  	        this.plugin.LCConfiguration.SaplingLevel);

  	  	      event.setCancelled(true);
  	  	      return;
  	  	    }   
  	
  	 //If the player managed to go through all that. Do this:
  //Start a double set at 0.
    double gained = 0.0D;
  //if the material is a block registered,
    if ((m == Material.SUGAR_CANE_BLOCK) && (level >= this.plugin.LCConfiguration.SugarCaneLevel))
    { 
    	 //Set the gained exp to that of the block.
      gained = this.plugin.LCConfiguration.ExpPerSugarCane;
    }
    //Repeat for all of the other blocks broken.
    if ((m.getId() == 59) && (event.getBlock().getData() == 7) && (level >= this.plugin.LCConfiguration.HarvestLevel))
    {
      gained = this.plugin.LCConfiguration.ExpPerHarvest;
    }
    if ((m == Material.CACTUS) && (level >= this.plugin.LCConfiguration.CactusLevel))
    {
    	gained = this.plugin.LCConfiguration.ExpPerCactus;
    }
    if (gained == 0.0D)
      return;
    LevelFunctions.addExp(player, this.plugin.thisPlug, gained);
  }
  }
  
  //I wont annotate this next method. Just so aspiring developers can learn :)
  public void onBlockPlace(BlockPlaceEvent event) {
    if (event.isCancelled())
      return;
    if (!Whitelist.worldCheck(event.getBlock().getWorld()))
        return;
      Player player = event.getPlayer();

      if (!Whitelist.hasLevel(player, this.plugin.thisPlug))
        return;
      int iih = player.getItemInHand().getTypeId();
      Material m = event.getBlock().getType();
      int level = LevelFunctions.getLevel(player, this.plugin.thisPlug);
      
      if ((level < this.plugin.LCConfiguration.CactusLevel) && 
    			(m == Material.CACTUS))
    	  	    {
    	  	      LCChat.warn(player, "Cannot destroy / place this block. Required Level:" + 
    	  	        this.plugin.LCConfiguration.CactusLevel);

    	  	      event.setCancelled(true);
    	  	      return;
    	  	    }   
    	if ((level < this.plugin.LCConfiguration.MushroomLevel) && 
    			((((m == Material.BROWN_MUSHROOM) || (m == Material.RED_MUSHROOM)))))
    	  	    {
    	  	      LCChat.warn(player, "Cannot place this block. Required Level:" + 
    	  	        this.plugin.LCConfiguration.MushroomLevel);

    	  	      event.setCancelled(true);
    	  	      return;
    	  	    }   
    	if ((level < this.plugin.LCConfiguration.RedRoseLevel) && 
    			(((m == Material.RED_ROSE))))
    	  	    {
    	  	      LCChat.warn(player, "Cannot place this block. Required Level:" + 
    	  	        this.plugin.LCConfiguration.RedRoseLevel);

    	  	      event.setCancelled(true);
    	  	      return;
    	  	    }   
    	if ((level < this.plugin.LCConfiguration.YellowFlowerLevel) && 
    			(((m == Material.YELLOW_FLOWER))))
    	  	    {
    	  	      LCChat.warn(player, "Cannot place this block. Required Level:" + 
    	  	        this.plugin.LCConfiguration.YellowFlowerLevel);

    	  	      event.setCancelled(true);
    	  	      return;
    	  	    }   
      
      double gained = 0.0D;
      
      if (m == Material.SAPLING && (level >= this.plugin.LCConfiguration.SaplingLevel)) {
      		gained = this.plugin.LCConfiguration.ExpPerSapling; 
      }
      if (m == Material.CACTUS && (level >=this.plugin.LCConfiguration.CactusLevel)) {
    	  gained = this.plugin.LCConfiguration.ExpPerCactus;
      }
      if (m == Material.RED_ROSE && (level >= this.plugin.LCConfiguration.RedRoseLevel)) {
    	
    	  gained = this.plugin.LCConfiguration.ExpPerRedRose;
      }
      if (m == Material.YELLOW_FLOWER && (level >= this.plugin.LCConfiguration.YellowFlowerLevel)) {
    		gained = this.plugin.LCConfiguration.ExpPerYellowFlower; 
      }
      if (m == Material.BROWN_MUSHROOM  && (level >= this.plugin.LCConfiguration.MushroomLevel)) {
    	  gained = this.plugin.LCConfiguration.ExpPerMushroom;
      }
      if (m == Material.RED_MUSHROOM && (level >= this.plugin.LCConfiguration.MushroomLevel)) {
    	  gained = this.plugin.LCConfiguration.ExpPerMushroom;
      }
      if (gained == 0.0D)
        return;
      LevelFunctions.addExp(player, this.plugin.thisPlug, gained);
      
  }
  

}