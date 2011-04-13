package me.samkio.levelcraft.SamToolbox;

import java.lang.reflect.Field;

import me.samkio.levelcraft.Levelcraft;
import me.samkio.levelcraft.Skills.Forge;
import net.minecraft.server.ContainerFurnace;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.InventoryPlayer;
import net.minecraft.server.ItemStack;
import net.minecraft.server.TileEntityFurnace;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class FurnaceWorkThread implements Runnable {
	private CraftPlayer craftPlayer;
	private EntityPlayer entityPlayer;
	private Levelcraft plugin;
	private int id;
	private InventoryPlayer inventory;
	private Forge smith;

	public FurnaceWorkThread(Player p, Levelcraft plugin) {
		this.craftPlayer = (CraftPlayer) p;
		this.entityPlayer = craftPlayer.getHandle();
		this.plugin = plugin;
		this.inventory = entityPlayer.inventory; // get its inventory.

	}

	public void addID(int id) {
		this.id = id;
	}


	@SuppressWarnings({ "unused", "static-access" })
	@Override
	public void run() {
		
		if (entityPlayer == null) {
			kill();
			return;
		}
		ContainerFurnace furnace = null;
		try {
			furnace = (ContainerFurnace) entityPlayer.activeContainer;
		} catch (Exception ex) {
			kill();
			return;
		}
		Field privateTileEntity;
		try {
			privateTileEntity = ContainerFurnace.class.getDeclaredField("a");
		} catch (Exception ex) {

			return;
		}
		privateTileEntity.setAccessible(true);
		TileEntityFurnace tileEntity;

		try {
			tileEntity = (TileEntityFurnace) privateTileEntity.get(furnace);
		} catch (Exception ex) {
			return;
		}
		ItemStack result = tileEntity.c_(2);
		ItemStack stack = tileEntity.c_(0);
		ItemStack[] Prev = null;
		ItemStack pIngredient = null;
		ItemStack pFuel = null;
		ItemStack pResult = null;
		ItemStack pHand = null;
		if (smith.previousResult.containsKey(id)) {
				Prev = smith.previousResult.get(id);
				smith.previousResult.remove(id);
				ItemStack[] Current = {tileEntity.c_(0),tileEntity.c_(1),tileEntity.c_(2), inventory.j()};
				smith.previousResult.put(id, Current);
				pIngredient = Prev[0];
				pFuel = Prev[1];
				pResult = Prev[2];
				pHand = Prev[3];
			}
		if(pResult != result) craftPlayer.sendMessage("YODEL");
		if(pFuel != tileEntity.c_(1)) craftPlayer.sendMessage("YODEL2");
		if(pIngredient != tileEntity.c_(0)) craftPlayer.sendMessage("YODEL4");
		if(pHand != inventory.j()) craftPlayer.sendMessage("YODEL3");

		//craftPlayer.sendMessage("ppH:"+pHand2+"/ppR:"+pResult2);
	    craftPlayer.sendMessage("pH:"+pHand+"/pR:"+pResult);
		craftPlayer.sendMessage("cH:"+inventory.j()+"/cR:"+tileEntity.c_(2));
		craftPlayer.sendMessage("================================");  
	/*	if(pResult == inventory.j() && pResult != null){
			craftPlayer.sendMessage("You took something??");
		}
		if(tileEntity.c_(2) != null && pResult != null){
			
	//	craftPlayer.sendMessage(tileEntity.c_(2).count +"/"+pResult.count);
		if(tileEntity.c_(2).count != pResult.count){
			craftPlayer.sendMessage("You took something2??");
		}
		}
		
		
		/*if(pHand !=null && result !=null){
		if(pHand.id == result.id && result != null && pHand.count == 0 && pResult == null){
			craftPlayer.sendMessage("You cannot place stuff.");
			
		}
		/*if(pResult != null){
		if(pHand.id == result.id && result != null && pHand.count == 0 && pResult.id == result.id && pResult.count != result.count){
			craftPlayer.sendMessage("You placed something? :p");
		}
		
		if(pHand.id == result.id && result != null && pResult.id == result.id && pResult.count != result.count && pHand.count != inventory.j().count){
			craftPlayer.sendMessage("You placed something? :p");
		} 
		
		}*/
		//}
		// craftPlayer.sendMessage("Current"+result);
/*
		if (stack != null) {
			if (stack.id == 15
					&& Level.getLevel(craftPlayer, "f") < Settings.ironBarLevel) {
				tileEntity.a(0, (net.minecraft.server.ItemStack) null);
				inventory.a(stack);
				Toolbox.sendMessage(craftPlayer,
						"You do not have the required Forge Level ("
								+ Settings.ironBarLevel + ").", false);
			}
			if (stack.id == 14
					&& Level.getLevel(craftPlayer, "f") < Settings.goldBarLevel) {
				tileEntity.a(0, (net.minecraft.server.ItemStack) null);
				inventory.a(stack);
				Toolbox.sendMessage(craftPlayer,
						"You do not have the required Forge Level ("
								+ Settings.goldBarLevel + ").", false);
			}

		}
*/
	/*	if (result != null) {
			double exp = 0;
			double gained = 0;
			boolean didIt = false;
			if (result.id == 265
					&& Level.getLevel(craftPlayer, "f") >= Settings.ironBarLevel) {
				exp = Level.getExp(craftPlayer, "f");
				exp = exp + Settings.ExpPerIronbar * result.count;
				gained = Settings.ExpPerIronbar * result.count;
				tileEntity.a(2, (net.minecraft.server.ItemStack) null);
				inventory.a(result);

				didIt = true;

			} else if (result.id == 266
					&& Level.getLevel(craftPlayer, "f") >= Settings.goldBarLevel) {
				exp = Level.getExp(craftPlayer, "f");
				exp = exp + Settings.ExpPerGoldbar * result.count;
				gained = Settings.ExpPerGoldbar * result.count;
				tileEntity.a(2, (net.minecraft.server.ItemStack) null);
				inventory.a(result);

				didIt = true;

			}

			if (didIt) {
				int level = Level.getLevel(craftPlayer, "f");
				Level.update(craftPlayer, "f", exp);
				int aftlevel = Level.getLevel(craftPlayer, "f");
				if (aftlevel > level) {
					craftPlayer.sendMessage(ChatColor.GOLD + "[LC]"
							+ ChatColor.GREEN
							+ " Level up! Your Forge level is now " + aftlevel);
				} else if (PlayerFunctions.enabled(craftPlayer) == true) {
					craftPlayer.sendMessage(ChatColor.GOLD + "[LC]"
							+ ChatColor.GREEN + " You gained " + gained
							+ " exp.");
				}
			}
			*/
		ItemStack[] Current = {tileEntity.c_(0),tileEntity.c_(1),tileEntity.c_(2), inventory.j()};
		smith.previousResult.put(id, Current);
	
		if (!craftPlayer.isOnline())
			kill();
	}

	@SuppressWarnings("static-access")
	public void kill() {
		plugin.getServer().getScheduler().cancelTask(id);
		int index = smith.tasks.indexOf(id);
		smith.previousResult.remove(id);
		smith.previousResult2.remove(id);
		if (index != -1)
			smith.tasks.remove(smith.tasks.indexOf(id));
		
	}
}
