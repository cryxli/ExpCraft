package me.torrent.lcfarming;

import me.samkio.levelcraftcore.LCChat;
import me.samkio.levelcraftcore.LevelFunctions;
import me.samkio.levelcraftcore.Whitelist;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.inventory.ItemStack;

public class LCPlayerListener extends PlayerListener
{
  public LCFarming plugin;

  public LCPlayerListener(LCFarming instance)
  {
    this.plugin = instance;
  }

  public void onPlayerInteract(PlayerInteractEvent event) {
    if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
    	//PlayerInteractEvent, called upon when a player interects with the block. In this case, its when the block is Right Clicked.
      return;
    if (!Whitelist.worldCheck(event.getClickedBlock().getWorld()))   
    	return;
    //Checking if the plugin is enabled in the specific world.
    {
    if (!Whitelist.hasLevel(event.getPlayer(), this.plugin.thisPlug)) {
      return;
    }
    
    Player player = event.getPlayer();
    //Gets the player.
    int iih = player.getItemInHand().getTypeId();
    //Gets the Item in hand.
    Material m = event.getClickedBlock().getType();
    //Gets the clicked block.

    if (!Whitelist.hasLevel(player, this.plugin.thisPlug))
      return;
    int level = LevelFunctions.getLevel(player, this.plugin.thisPlug);

    if ((level < this.plugin.LCConfiguration.IronHoe) && (iih == 292)) {
      LCChat.warn(player, "Cannot use this tool. Required Level:" + 
        this.plugin.LCConfiguration.IronHoe);
      event.setCancelled(true);
      return;
  //If a player is using a Iron Hoe and are under the configuration level, they get denied access to the tool.
    }
    if ((level < this.plugin.LCConfiguration.GoldHoe) && (iih == 294)) {
      LCChat.warn(player, "Cannot use this tool. Required Level:" + 
        this.plugin.LCConfiguration.GoldHoe);
      event.setCancelled(true);
      return;
    //If a player is using a Gold Hoe and are under the configuration level, they get denied access to the tool.
    }if ((level < this.plugin.LCConfiguration.WoodHoe) && (iih == 290)) {
      LCChat.warn(player, "Cannot use this tool. Required Level:" + 
        this.plugin.LCConfiguration.WoodHoe);
      event.setCancelled(true);
      return;
    //If a player is using a Wood Hoe and are under the configuration level, they get denied access to the tool.
    }if ((level < this.plugin.LCConfiguration.DiamondHoe) && (iih == 293)) {
      LCChat.warn(player, "Cannot use this tool. Required Level:" + 
        this.plugin.LCConfiguration.DiamondHoe);
      event.setCancelled(true);
      return;
    //If a player is using a Diamond Hoe and are under the configuration level, they get denied access to the tool.
    }if ((level < this.plugin.LCConfiguration.StoneHoe) && (iih == 291)) {
      LCChat.warn(player, "Cannot use this tool. Required Level:" + 
        this.plugin.LCConfiguration.StoneHoe);
      event.setCancelled(true);
      return;
    //If a player is using a Stone Hoe and are under the configuration level, they get denied access to the tool.
    }
    
    if ((level < this.plugin.LCConfiguration.TillLevel) && 
    		//Checks if the players level is less than the level set of Tilling.
      ((m == Material.GRASS) || (m == Material.DIRT)) && (isHoe(iih)))
    	//Checks if the material clicked with the hoe is either Grass or Dirt
    {
    	LCChat.warn(player, "Cannot till this block. Required Level:" + 
        this.plugin.LCConfiguration.TillLevel);
    	//Wars the user is they aren't a high enough level.
     
    	event.setCancelled(true);
      return;
      //If the player is the correct level, return.
    }
    
    if ((level < this.plugin.LCConfiguration.BonemealLevel) && 
    		//Checks if the players level is less than the set level of Bonemeal.
    		((iih == 351) && (player.getItemInHand().getDurability() == 15)))
    	//Checks if the Item in hand is Bonemeal.
  	  	    {
  	  	      LCChat.warn(player, "Cannot fertilize this plant. Required Level:" + 
  	  	        this.plugin.LCConfiguration.BonemealLevel);
      //Warns the players if they aren't the required level.
  	  	      event.setCancelled(true);
  	  	      return;
  	  	      //If the player is the correct level, return.
  	  	    }   
    //I wont annotate the rest of these restrictions, as its basically the same.
    if ((level < this.plugin.LCConfiguration.BonemealLevel) && 
    		((iih == 351) && (player.getItemInHand().getDurability() == 15) && (m == Material.SAPLING)))
  	  	    {
  	  	      LCChat.warn(player, "Cannot fertilize this plant. Required Level:" + 
  	  	        this.plugin.LCConfiguration.BonemealLevel);

  	  	      event.setCancelled(true);
  	  	      return;
  	  	    }   
    
    if ((iih == 351) && (player.getItemInHand().getDurability() == 15) && (m == Material.SAPLING)) {
      if (level < this.plugin.LCConfiguration.BonemealLevel)
      {
        LCChat.warn(player, "Cannot use this tool. Required Level:" + 
          this.plugin.LCConfiguration.BonemealLevel);

        event.setCancelled(true);
        return;
      }
      LevelFunctions.addExp(event.getPlayer(), this.plugin.thisPlug, 10.0D);
    }
    //This is where the player earns EXP.
    
    if ((iih == 351) && (player.getItemInHand().getDurability() == 15) && (m.getId() == 59)) {
    	//Checks if the item in hand is Bonemeal.
        if (level < this.plugin.LCConfiguration.BonemealLevel)
        {
          LCChat.warn(player, "Cannot use this Bonemeal. Required Level:" + 
            this.plugin.LCConfiguration.BonemealLevel);
          //If the players level is less than the Bonemeal level, it will warn the player and wont let them use it.

          event.setCancelled(true);
          return;
        }
        LevelFunctions.addExp(event.getPlayer(), this.plugin.thisPlug, 10.0D);
        //Adds 10 EXP to the players level.
      }

    double gained = 0.0D;
    if (((m == Material.GRASS) || (m == Material.DIRT)) && (isHoe(iih))) {
      gained = this.plugin.LCConfiguration.ExpPerTill;
    }

    if (gained == 0.0D)
      return;
    LevelFunctions.addExp(player, this.plugin.thisPlug, gained);
  }
  }
  
  public boolean isHoe(int i) {
    return (i == 290) || (i == 291) || (i == 292) || (i == 293) || (i == 294);
  }
  //New method, PlayerEggThrowEvent. This is called upon the player throwing an egg.
  
  public void onPlayerEggThrow(PlayerEggThrowEvent event) { boolean notEgg = event.isHatching();
    if ((!event.isHatching()))
    	//Checks if the egg is hatching
    		return;
    //If the egg doesnt hatch, return.
       if (!Whitelist.worldCheck(event.getPlayer().getWorld()))
    	   return;
       //Checking if the level is enabled in the world.
       
       
     {
      if (!Whitelist.hasLevel(event.getPlayer(), this.plugin.thisPlug))
        return;
      if (!(event.getPlayer() instanceof Player)) return;
      //Checks if the cause of the egg throw is from a player.
    }
    LevelFunctions.addExp(event.getPlayer(), this.plugin.thisPlug, 50.0D);
    //Finally, if the egg is thrown by a player and if it hatches, it awards the player EXP.
  }
}