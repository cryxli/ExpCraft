package me.samkio.levelcraft.Listeners;

import me.samkio.levelcraft.Levelcraft;

import me.samkio.levelcraft.Skills.Forge;

import org.bukkit.entity.Player;
//import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

public class LCPlayerListener extends PlayerListener {
	public Levelcraft plugin;
	public Forge smith;

	public LCPlayerListener(Levelcraft instance) {
		plugin = instance;
	}

	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		plugin.PlayerFunctions.checkAccount(player);
	}

/*	public void onPlayerInteract(PlayerInteractEvent e) {

		Forge.doT(e);

	} */
}