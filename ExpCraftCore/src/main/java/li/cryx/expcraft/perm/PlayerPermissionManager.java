package li.cryx.expcraft.perm;

import li.cryx.expcraft.module.ExpCraftModule;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

/**
 * This class implements a {@link AbstractPermissionManager} that uses the
 * <code>Player</code> objects to determine permission nodes a player has. This
 * method works for the <b>PermissionsBukkit</b> plugin.
 * 
 * @author cryxli
 */
public class PlayerPermissionManager extends AbstractPermissionManager {

	@Override
	public boolean hasAdminCommand(final Player player, final String command) {
		return player.hasPermission("ec.admin." + command.toLowerCase());
	}

	@Override
	public boolean hasLevel(final ExpCraftModule module, final Player player) {
		// TODO cryxli: remove after testing
		for (PermissionAttachmentInfo info : player.getEffectivePermissions()) {
			String perm = info.getPermission();
			if (perm.startsWith("ec.")) {
				System.out.println(perm + "=" + info.getValue());
			}
		}
		System.out.println("-");

		return player.hasPermission("ec.module." //
				+ module.getAbbr().toLowerCase());
	}

}
