package li.cryx.expcraft.perm;

import li.cryx.expcraft.module.ExpCraftModule;

import org.bukkit.entity.Player;

/**
 * This class implements a {@link AbstractPermissionManager} that uses the
 * <code>Player</code> objects to determine permission nodes a player has. This
 * method works for the <a
 * href="http://dev.bukkit.org/server-mods/permbukkit/">PermissionsBukkit</a>
 * plugin.
 * 
 * @author cryxli
 */
public class PermissionsBukkitManager extends AbstractPermissionManager {

	@Override
	public boolean hasAdminCommand(final Player player, final String command) {
		return player.hasPermission("ec.admin." + command.toLowerCase());
	}

	@Override
	public boolean hasLevel(final ExpCraftModule module, final Player player) {
		return player.hasPermission("ec.module." //
				+ module.getAbbr().toLowerCase());
	}
}