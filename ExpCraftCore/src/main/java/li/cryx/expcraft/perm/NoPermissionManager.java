package li.cryx.expcraft.perm;

import li.cryx.expcraft.module.ExpCraftModule;

import org.bukkit.entity.Player;

/**
 * This class is an implementation of an {@link AbstractPermissionManager} that
 * covers the case when Bukkit is running without any permissions plugin.
 * 
 * @author cryxli
 */
public class NoPermissionManager extends AbstractPermissionManager {

	@Override
	public boolean hasAdminCommand(final Player player, final String command) {
		return player.isOp();
	}

	@Override
	public boolean hasLevel(final ExpCraftModule module, final Player player) {
		return true;
	}

}
