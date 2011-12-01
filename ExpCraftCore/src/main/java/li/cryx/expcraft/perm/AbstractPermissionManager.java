package li.cryx.expcraft.perm;

import java.util.Set;
import java.util.TreeSet;

import li.cryx.expcraft.module.ExpCraftModule;

import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * This abstract class defined the basic behaviour of permission managers.
 * 
 * @author cryxli
 */
public abstract class AbstractPermissionManager {

	/** List of worlds for which ExpCraft is activated. */
	protected Set<String> worlds = new TreeSet<String>();

	/**
	 * Check whether the given player can issue the indicated admin command.
	 * 
	 * @param player
	 *            Current player
	 * @param command
	 *            The command identified by its permission node
	 * @return <code>true</code>, if the player can execute the command.
	 */
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

	/**
	 * Set the world for which ExpCraft is activated.
	 * 
	 * @param worldStr
	 *            A comma separated string containing world names.
	 */
	public void setWorlds(final String worldStr) {
		setWorlds(worldStr.split(","));
	}

	/**
	 * Set the world for which ExpCraft is activated.
	 * 
	 * @param An
	 *            array of Strings containing world names.
	 */
	public void setWorlds(final String[] worldStrs) {
		worlds.clear();
		for (String world : worldStrs) {
			String name = world.trim().toLowerCase();
			if (name.length() > 0) {
				worlds.add(name);
			}
		}
	}

	/**
	 * Check whether ExpCraft is activated for the given world.
	 * 
	 * @param world
	 *            A world, probably linked to a bukkit event.
	 * @return <code>true</code>, if ExpCraft is activated for the world.
	 */
	public boolean worldCheck(final World world) {
		String name = world.getName().toLowerCase();
		return worlds.contains(name);
	}

}
