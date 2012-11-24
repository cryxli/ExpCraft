/*
 * Copyright (c) 2011 Urs P. Stettler, https://github.com/cryxli
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
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
