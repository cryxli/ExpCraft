package li.cryx.expcraft.perm;

import li.cryx.expcraft.module.ExpCraftModule;

import org.bukkit.entity.Player;

import com.nijiko.permissions.PermissionHandler;

/**
 * This class implements the permission manager using the <a
 * href="https://github.com/TheYeti/Permissions">Permissions</a> plugin in
 * version 2.7.4+.
 * 
 * @author cryxli
 */
public class PermissionsPermissionManager extends AbstractPermissionManager {

	private final PermissionHandler handler;

	public PermissionsPermissionManager(final PermissionHandler handler) {
		this.handler = handler;
	}

	@Override
	public boolean hasAdminCommand(final Player player, final String command) {
		return handler.has(player, "ec.admin." + command.toLowerCase());
	}

	@Override
	public boolean hasLevel(final ExpCraftModule module, final Player player) {
		try {
			return handler.has(player, "ec.module."
					+ module.getAbbr().toLowerCase());
		} catch (Exception e) {
			return false;
		}
	}
}
