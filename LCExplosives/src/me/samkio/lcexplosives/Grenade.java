package me.samkio.lcexplosives;

import net.minecraft.server.EntityTNTPrimed;
import net.minecraft.server.World;

import org.bukkit.Location;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class Grenade {
	final LCExplosives plugin;
	public boolean isHatching;
	public Grenade(LCExplosives lcRange) {
		plugin = lcRange;
	}
	public void eggThrown(final Location loc, Player player, final World world, Egg egg, Event event){
		if(plugin.EnableGrenade.containsKey(player)){
		isHatching = false;
		long actualDelayTime =  20;
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

		public void run() {
		grenade(world, loc);
		}

		public void grenade(World world, Location loc) {

			EntityTNTPrimed tnt = new EntityTNTPrimed((net.minecraft.server.World) world, loc.getX(), loc.getY(), loc.getZ());
			world.a(tnt, loc.getX(), loc.getY(), loc.getZ(), plugin.LCConfiguration.GrenadeRadius);

			}
		}, actualDelayTime);
		}
	

		}
}
