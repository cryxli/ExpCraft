package li.cryx.expcraft;

import java.util.Set;

import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

public class DummyCommandSender implements CommandSender {

	/** Store received messages. */
	private StringBuffer buf = new StringBuffer();

	private final String name;

	private boolean operator = false;

	public DummyCommandSender(final String name) {
		this.name = name;
	}

	@Override
	public PermissionAttachment addAttachment(final Plugin plugin) {
		return null;
	}

	@Override
	public PermissionAttachment addAttachment(final Plugin plugin,
			final int ticks) {
		return null;
	}

	@Override
	public PermissionAttachment addAttachment(final Plugin plugin,
			final String name, final boolean value) {
		return null;
	}

	@Override
	public PermissionAttachment addAttachment(final Plugin plugin,
			final String name, final boolean value, final int ticks) {
		return null;
	}

	/** Reset the cache for received messages. */
	public void clearMsgCache() {
		buf = new StringBuffer();
	}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions() {
		return null;
	}

	public String getLastMessage() {
		return buf.toString();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Server getServer() {
		return null;
	}

	@Override
	public boolean hasPermission(final Permission perm) {
		return false;
	}

	@Override
	public boolean hasPermission(final String name) {
		return false;
	}

	@Override
	public boolean isOp() {
		return operator;
	}

	@Override
	public boolean isPermissionSet(final Permission perm) {
		return false;
	}

	@Override
	public boolean isPermissionSet(final String name) {
		return false;
	}

	@Override
	public void recalculatePermissions() {
	}

	@Override
	public void removeAttachment(final PermissionAttachment attachment) {
	}

	@Override
	public void sendMessage(final String message) {
		buf.append(message);
		buf.append('\n');
	}

	@Override
	public void setOp(final boolean isOperator) {
		operator = isOperator;
	}
}
