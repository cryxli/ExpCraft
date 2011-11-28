package li.cryx.expcraft.perm;

import java.util.Set;
import java.util.TreeSet;

import li.cryx.expcraft.module.ExpCraftModule;

import org.bukkit.World;
import org.bukkit.entity.Player;

public abstract class AbstractPermissionManager {

	protected Set<String> worlds = new TreeSet<String>();

	abstract public boolean hasAdminCommand(Player player, String command);

	/**
	 * Check whether the given player has enough rights to use the given module.
	 * 
	 * @param module
	 *            The module in question.
	 * @param player
	 *            Current player
	 * @return Indicator. <code>true</code>, if player can use the module.
	 */
	abstract public boolean hasLevel(ExpCraftModule module, Player player);

	public void setWorlds(final String worldStr) {
		worldStr.split(",");
	}

	public void setWorlds(final String[] worldStrs) {
		worlds.clear();
		for (String world : worldStrs) {
			worlds.add(world.trim().toLowerCase());
		}
	}

	public boolean worldCheck(final World world) {
		String name = world.getName().toLowerCase();
		return worlds.contains(name);
	}

	// TODO cryxli: everything

}
