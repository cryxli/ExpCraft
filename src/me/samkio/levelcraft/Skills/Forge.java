package me.samkio.levelcraft.Skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import me.samkio.levelcraft.Levelcraft;
import me.samkio.levelcraft.SamToolbox.FurnaceWorkThread;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Forge {
	public static ArrayList<Integer> tasks = new ArrayList<Integer>();
	public static final Logger log = Logger.getLogger("Minecraft");
	public static Levelcraft plugin;
	public static HashMap<Integer, net.minecraft.server.ItemStack[]> previousResult = new HashMap<Integer, net.minecraft.server.ItemStack[]>();
	public static HashMap<Integer, net.minecraft.server.ItemStack[]> previousResult2 = new HashMap<Integer, net.minecraft.server.ItemStack[]>();
	public Forge(Levelcraft instance) {
		plugin = instance;
	}

	public static void doT(PlayerInteractEvent e) {

		if ((e.getClickedBlock().getTypeId() == 61 || e.getClickedBlock()
				.getTypeId() == 62)
				&& e.getAction() == Action.RIGHT_CLICK_BLOCK
				&& plugin.Settings.enableForgeLevel) {
			// e.getPlayer().sendMessage("EVENTCALLED");
			FurnaceWorkThread task = new FurnaceWorkThread(e.getPlayer(),
					plugin);
			int id = plugin.getServer().getScheduler()
					.scheduleSyncRepeatingTask(plugin, task, 0, 1);
			tasks.add(Integer.valueOf(id));
			task.addID(id);
		}
	}
}
