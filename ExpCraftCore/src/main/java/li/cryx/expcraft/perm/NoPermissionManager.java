package li.cryx.expcraft.perm;

import li.cryx.expcraft.module.ExpCraftModule;

import org.bukkit.entity.Player;

/**
 * This class is an implementation of an {@link AbstractPermissionManager} that
 * covers the case when Bukkit is running without any permissions plugin. By
 * default all player can use all loaded {@link ExpCraftModule}s and only
 * Operators can execute admin commands.
 * 
 * @author cryxli
 */
public class NoPermissionManager extends AbstractPermissionManager {

	/** Is <code>true</code>, if the player is an Operator. */
	@Override
	public boolean hasAdminCommand(final Player player, final String command) {
		return player.isOp();
	}

	/** Will always return <code>true</code> */
	@Override
	public boolean hasLevel(final ExpCraftModule module, final Player player) {
		return true;
	}

}
