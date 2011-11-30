package li.cryx.expcraft.perm;

import li.cryx.expcraft.module.ExpCraftModule;

import org.bukkit.entity.Player;

public class PlayerPermissionManager extends AbstractPermissionManager {

	@Override
	public boolean hasAdminCommand(final Player player, final String command) {
		return player.hasPermission("ec.admin." + command);
	}

	@Override
	public boolean hasLevel(final ExpCraftModule module, final Player player) {
		return player.hasPermission("ec.module." + module.getAbbr());
	}

}
