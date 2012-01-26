package li.cryx.expcraft.cmd;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * This class is a data structure holding cleaned player and console command
 * data.
 * 
 * @author cryxli
 */
class CmdArgs {
	/**
	 * Source of the command. May be a player but bay also be the server
	 * console.
	 */
	CommandSender sender;

	/** The target player of the command. */
	Player player;

	/** Arguments of the command. */
	String[] args;

	public CmdArgs(final CommandSender sender, final Player player,
			final String... args) {
		this.sender = sender;
		this.player = player;
		this.args = args;
	}

	/** Readable debugging output. */
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("{\"clazz\":\"CmdArgs\",");
		buf.append("\"sender\":{").append(sender).append("},");
		buf.append("\"player\":{").append(player).append("},");
		buf.append("\"args\":[");
		for (String s : args) {
			buf.append("\"").append(s).append("\",");
		}
		if (args.length > 0) {
			buf.deleteCharAt(buf.length() - 1);
		}
		buf.append("]");
		buf.append("}");
		return buf.toString();
	}
}
