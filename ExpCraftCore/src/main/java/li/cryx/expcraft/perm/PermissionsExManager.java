package li.cryx.expcraft.perm;

import li.cryx.expcraft.module.ExpCraftModule;

import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import ru.tehkode.permissions.exceptions.PermissionsNotAvailable;

public class PermissionsExManager extends AbstractPermissionManager {

	private final PermissionManager pex;

	public PermissionsExManager() throws PermissionsNotAvailable {
		pex = PermissionsEx.getPermissionManager();
	}

	@Override
	public boolean hasAdminCommand(final Player player, final String command) {
		return pex.has(player, "ec.admin." + command.toLowerCase());
	}

	@Override
	public boolean hasLevel(final ExpCraftModule module, final Player player) {
		return pex.has(player, "ec.module." //
				+ module.getInfo().getAbbr().toLowerCase());
	}

}
