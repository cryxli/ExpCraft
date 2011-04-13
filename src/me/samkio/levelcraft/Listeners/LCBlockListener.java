package me.samkio.levelcraft.Listeners;
import me.samkio.levelcraft.Levelcraft;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

public class LCBlockListener extends BlockListener {
	public Levelcraft plugin;

	public LCBlockListener(Levelcraft instance) {
		plugin = instance;
	}

	public void onBlockBreak(BlockBreakEvent event) {
		if (event.isCancelled()){
			return;
		}
		
		if (!event.isCancelled()) {
			if(!plugin.Whitelist.isAvoid(event.getPlayer(), "w")){
			  plugin.Wood.Destroy(event);
			}
			if(!plugin.Whitelist.isAvoid(event.getPlayer(), "d")){
				 plugin.Dig.Destroy(event);
				}
			if(!plugin.Whitelist.isAvoid(event.getPlayer(), "m")){
				 plugin.Mine.Destroy(event);
			}
			
		}

	}
	public void onBlockPlace(BlockPlaceEvent event){
		if (event.isCancelled()){
			return;
		}
		if(plugin.Settings.AntiBoost){
			if(!plugin.Whitelist.isAvoid(event.getPlayer(), "m")){
				 plugin.Mine.Place(event);
		    }
		}
	}

}
