package li.cryx.expcraft.perm;

import li.cryx.expcraft.module.ExpCraftModule;

import org.bukkit.entity.Player;

import com.nijiko.permissions.PermissionHandler;

public class PermissionsPermissionManager extends AbstractPermissionManager {

	private final PermissionHandler handler;

	public PermissionsPermissionManager(final PermissionHandler handler) {
		this.handler = handler;
	}

	@Override
	public boolean hasAdminCommand(final Player player, final String command) {
		return handler.has(player, "ec.admin." + command);
	}

	@Override
	public boolean hasLevel(final ExpCraftModule module, final Player player) {
		// TODO cryxli: consider the following lines
		// for (String playerN : plugin.Bypassers) {
		// if (playerN.equalsIgnoreCase(s.getName())) {
		// return false;
		// }
		// }
		try {
			return handler.has(player, "ec.module." + module.getAbbr());
		} catch (Exception e) {
			return false;
		}
	}
}
