package me.samkio.levelcraft.Listeners;
import me.samkio.levelcraft.Levelcraft;
import me.samkio.levelcraft.Whitelist;
import me.samkio.levelcraft.Skills.Dig;
import me.samkio.levelcraft.Skills.Mine;
import me.samkio.levelcraft.Skills.Wood;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

public class LCBlockListener extends BlockListener {
	public static Levelcraft plugin;

	public LCBlockListener(Levelcraft instance) {
		plugin = instance;
	}

	public void onBlockBreak(BlockBreakEvent event) {
		if (event.isCancelled()){
			return;
		}
		
		if (!event.isCancelled()) {
			if(!Whitelist.isAvoid(event.getPlayer(), "w")){
			  Wood.Destroy(event);
			}
			if(!Whitelist.isAvoid(event.getPlayer(), "d")){
				  Dig.Destroy(event);
				}
			if(!Whitelist.isAvoid(event.getPlayer(), "m")){
			  Mine.Destroy(event);
			}
			
		}

	}
	public void onBlockPlace(BlockPlaceEvent event){
		if (event.isCancelled()){
			return;
		}
		if(plugin.Settings.AntiBoost){
			if(!Whitelist.isAvoid(event.getPlayer(), "m")){
				  Mine.Place(event);
		    }
		}
	}

}
