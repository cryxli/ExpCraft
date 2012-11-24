/*
 * Copyright (c) 2011 Urs P. Stettler, https://github.com/cryxli
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package li.cryx.expcraft.perm;

import java.util.Set;
import java.util.TreeSet;

import li.cryx.expcraft.module.ExpCraftModule;

import org.bukkit.GameMode;
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
	public abstract boolean hasAdminCommand(Player player, String command);

	/**
	 * Check whether the given player has enough rights to use the given module.
	 * 
	 * @param module
	 *            The module in question.
	 * @param player
	 *            Current player
	 * @return Indicator. <code>true</code>, if player can use the module.
	 */
	public abstract boolean hasLevel(ExpCraftModule module, Player player);

	/**
	 * Check whether the given player has enough rights to use the given module
	 * and is in SURVIVAL mode.
	 * 
	 * @param module
	 *            The module in question.
	 * @param player
	 *            Current player
	 * @return Indicator. <code>true</code>, if player can use the module.
	 */
	public boolean hasModule(final ExpCraftModule module, final Player player) {
		return player.getGameMode() == GameMode.SURVIVAL
				&& hasLevel(module, player);
	}

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
	 * @param worldStrs
	 *            An array of Strings containing world names.
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
