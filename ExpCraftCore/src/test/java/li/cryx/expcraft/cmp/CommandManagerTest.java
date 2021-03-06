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
package li.cryx.expcraft.cmp;

import java.util.Locale;
import java.util.logging.Logger;

import junit.framework.Assert;
import li.cryx.expcraft.DummyExpCraft;
import li.cryx.expcraft.DummyModule;
import li.cryx.expcraft.cmd.CommandManager;
import li.cryx.expcraft.i18n.FallbackModuleTranslation;
import li.cryx.expcraft.loader.ModuleInfo;

import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class CommandManagerTest {

	@BeforeClass
	public static void setSystemLanguage() {
		Locale.setDefault(Locale.ENGLISH);
	}

	private Server server;

	private PluginManager pluginManager;

	private DummyExpCraft core;

	private CommandManager cmd;

	private DummyModule testModule;

	@Before
	public void beforeTest() {
		pluginManager = Mockito.mock(PluginManager.class);

		server = Mockito.mock(Server.class);
		Mockito.when(server.getPluginManager()).thenReturn(pluginManager);
		Mockito.when(server.getLogger())
				.thenReturn(Logger.getAnonymousLogger());

		core = new DummyExpCraft(server);

		testModule = new DummyModule();
		testModule.setInfo(new ModuleInfo("Test", "T", null));
		testModule.setTranslator(new FallbackModuleTranslation(core
				.getTranslator(), testModule));

		core.setTestModule(testModule);

		cmd = new CommandManager(core);
	}

	private void infoModuleTest(final Player player) {
		cmd.onCommand(player, "info", "t");
		Mockito.verify(player).sendMessage("Module info here");

		cmd.onCommand(player, "t");
		Mockito.verify(player, Mockito.times(2))
				.sendMessage("Module info here");
	}

	private void playerInfoCheck(final Player player) {
		Mockito.verify(player).sendMessage("§6[EC] --- ExpCraftPlugin ---");
		Mockito.verify(player).sendMessage("§6[EC] §eAvailable commands");
		Mockito.verify(player).sendMessage("§6[EC] §e /lvl all");
		Mockito.verify(player).sendMessage(
				"§6[EC] §e /lvl info <Module> [Page]");
		Mockito.verify(player).sendMessage("§6[EC] §e /lvl getExp <Module>");
		Mockito.verify(player).sendMessage("§6[EC] §e /lvl getLvl <Module>");
		Mockito.verify(player, Mockito.times(6)).sendMessage(
				Mockito.anyString());
	}

	@Test
	public void playerInfoModule() {
		Player player = Mockito.mock(Player.class);

		cmd.onCommand(player, "info", "t");
		Mockito.verify(player).sendMessage("Module info here");

		cmd.onCommand(player, "info", "d");
		Mockito.verify(player).sendMessage("§6[EC] §eNo module found");
	}

	@Test
	public void playerNoOpAll() {
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("cryxli");

		core.getPersistence().setExp(testModule, player, 0);
		cmd.onCommand(player, "all");
		Mockito.verify(player).sendMessage("§6[EC] --- ExpCraftPlugin ---");
		Mockito.verify(player).sendMessage("§6[EC] §eTest (T): 1");
		Mockito.verify(player, Mockito.times(2)).sendMessage(
				Mockito.anyString());
	}

	@Test
	public void playerNoOpGetExp() {
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("cryxli");

		cmd.onCommand(player, "getexp");
		Mockito.verify(player).sendMessage(
				"§6[EC] §eSyntax: /level getExp <Module> [Player]");

		core.getPersistence().setExp(testModule, player, 0);
		cmd.onCommand(player, "getexp", "t");
		Mockito.verify(player)
				.sendMessage("§6[EC] §eTest (T): lv1 at 0 points");

		cmd.onCommand(player, "getexp", "t", "cryxli");
		Mockito.verify(player).sendMessage(
				"§6[EC] §cYou cannot execute that command.");

		cmd.onCommand(player, "getexp", "d", "cryxli");
		Mockito.verify(player, Mockito.times(2)).sendMessage(
				"§6[EC] §cYou cannot execute that command.");
	}

	@Test
	public void playerNoOpInfo() {
		Player player = Mockito.mock(Player.class);
		cmd.onCommand(player, "info");
		playerInfoCheck(player);
	}

	@Test
	public void playerNoOpInfoModule() {
		Player player = Mockito.mock(Player.class);
		infoModuleTest(player);
	}

	@Test
	public void playerNoOpNothing() {
		Player player = Mockito.mock(Player.class);
		cmd.onCommand(player);
		playerInfoCheck(player);
	}

	@Test
	public void playerNoOpSetLvl() {
		Player player = Mockito.mock(Player.class);

		cmd.onCommand(player, "setlvl");
		Mockito.verify(player).sendMessage(
				"§6[EC] §eSyntax: /level setLevel <Module> <Value> [Player]");

		cmd.onCommand(player, "setlvl", "t");
		Mockito.verify(player, Mockito.times(2)).sendMessage(
				"§6[EC] §eSyntax: /level setLevel <Module> <Value> [Player]");

		cmd.onCommand(player, "setlevel", "t", "5");
		Mockito.verify(player).sendMessage(
				"§6[EC] §cYou cannot execute that command.");

		player = Mockito.mock(Player.class);
		cmd.onCommand(player, "setlevel", "t", "asd");
		Mockito.verify(player).sendMessage(
				"§6[EC] §eSyntax: /level setLevel <Module> <Value> [Player]");
		Mockito.verify(player).sendMessage(
				"§6[EC] §e<Value> has to be a natural number");

		cmd.onCommand(player, "setlvl", "t", "5", "cryxli");
		Mockito.verify(player).sendMessage(
				"§6[EC] §cYou cannot execute that command.");

		Mockito.when(server.getPlayer(player.getName())).thenReturn(player);
		cmd.onCommand(player, "setlvl", "t", "5", "cryxli");
		Mockito.verify(player, Mockito.times(2)).sendMessage(
				"§6[EC] §cYou cannot execute that command.");

		cmd.onCommand(player, "setlvl", "d", "15", "cryxli");
		Mockito.verify(player, Mockito.times(3)).sendMessage(
				"§6[EC] §cYou cannot execute that command.");
	}

	@Test
	public void playerOpAll() {
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("cryxli");
		Mockito.when(player.isOp()).thenReturn(true);
		core.getPersistence().setExp(testModule, player, 0);

		cmd.onCommand(player, "all");
		Mockito.verify(player).sendMessage("§6[EC] --- ExpCraftPlugin ---");
		Mockito.verify(player).sendMessage("§6[EC] §eTest (T): 1");

		cmd.onCommand(player, "all", "cryxli");
		Mockito.verify(player).sendMessage("§6[EC] §ePlayer cryxli is offline");

		Mockito.when(server.getPlayer(player.getName())).thenReturn(player);
		cmd.onCommand(player, "all", "cryxli");
		Mockito.verify(player, Mockito.times(2)).sendMessage(
				"§6[EC] --- ExpCraftPlugin ---");
		Mockito.verify(player, Mockito.times(2)).sendMessage(
				"§6[EC] §eTest (T): 1");
	}

	@Test
	public void playerOpGetExp() {
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("cryxli");
		Mockito.when(player.isOp()).thenReturn(true);

		cmd.onCommand(player, "getexp");
		Mockito.verify(player).sendMessage(
				"§6[EC] §eSyntax: /level getExp <Module> [Player]");

		core.getPersistence().setExp(testModule, player, 0);
		cmd.onCommand(player, "getexp", "t");
		Mockito.verify(player)
				.sendMessage("§6[EC] §eTest (T): lv1 at 0 points");

		cmd.onCommand(player, "getexp", "t", "cryxli");
		Mockito.verify(player).sendMessage("§6[EC] §ePlayer cryxli is offline");

		Mockito.when(server.getPlayer(player.getName())).thenReturn(player);
		cmd.onCommand(player, "getexp", "t", "cryxli");
		Mockito.verify(player, Mockito.times(2)).sendMessage(
				"§6[EC] §eTest (T): lv1 at 0 points");

		cmd.onCommand(player, "getexp", "d", "cryxli");
		Mockito.verify(player).sendMessage("§6[EC] §eNo module found");
	}

	@Test
	public void playerOpInfo() {
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.isOp()).thenReturn(true);
		cmd.onCommand(player, "info");
		playerOpInfoCheck(player);
	}

	private void playerOpInfoCheck(final Player player) {
		Mockito.verify(player).sendMessage("§6[EC] --- ExpCraftPlugin ---");
		Mockito.verify(player).sendMessage("§6[EC] §eAvailable commands");
		Mockito.verify(player).sendMessage("§6[EC] §e /lvl all");
		Mockito.verify(player).sendMessage(
				"§6[EC] §e /lvl info <Module> [Page]");
		Mockito.verify(player).sendMessage(
				"§6[EC] §e /lvl getExp <Module> [Player]");
		Mockito.verify(player).sendMessage(
				"§6[EC] §e /lvl getLvl <Module> [Player]");
		Mockito.verify(player).sendMessage(
				"§6[EC] §e /lvl setExp <Module> <Value> [Player]");
		Mockito.verify(player).sendMessage(
				"§6[EC] §e /lvl setLvl <Module> <Value> [Player]");
		Mockito.verify(player, Mockito.times(8)).sendMessage(
				Mockito.anyString());
	}

	@Test
	public void playerOpInfoModule() {
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.isOp()).thenReturn(true);
		infoModuleTest(player);
	}

	@Test
	public void playerOpNothing() {
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.isOp()).thenReturn(true);
		cmd.onCommand(player);
		playerOpInfoCheck(player);
	}

	@Test
	public void playerOpSetLvl() {
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("cryxli");
		Mockito.when(player.isOp()).thenReturn(true);

		cmd.onCommand(player, "setlvl");
		Mockito.verify(player).sendMessage(
				"§6[EC] §eSyntax: /level setLevel <Module> <Value> [Player]");

		cmd.onCommand(player, "setlvl", "t");
		Mockito.verify(player, Mockito.times(2)).sendMessage(
				"§6[EC] §eSyntax: /level setLevel <Module> <Value> [Player]");

		cmd.onCommand(player, "setlevel", "t", "5");
		Mockito.verify(player).sendMessage("§6[EC] §eTest (T): lv5");
		Assert.assertEquals(5,
				core.getPersistence().getLevel(testModule, player));

		player = Mockito.mock(Player.class);
		Mockito.when(player.isOp()).thenReturn(true);
		cmd.onCommand(player, "setlevel", "t", "asd");
		Mockito.verify(player).sendMessage(
				"§6[EC] §eSyntax: /level setLevel <Module> <Value> [Player]");
		Mockito.verify(player).sendMessage(
				"§6[EC] §e<Value> has to be a natural number");
		Mockito.verify(player, Mockito.times(2)).sendMessage(
				Mockito.anyString());

		cmd.onCommand(player, "setlvl", "t", "10", "testus");
		Mockito.verify(player).sendMessage("§6[EC] §ePlayer testus is offline");

		Player testus = Mockito.mock(Player.class);
		Mockito.when(testus.getName()).thenReturn("testus");
		Mockito.when(server.getPlayer(testus.getName())).thenReturn(testus);
		cmd.onCommand(player, "setlvl", "t", "10", "testus");
		Mockito.verify(player).sendMessage("§6[EC] §eTest (T): lv10");
		Assert.assertEquals(10,
				core.getPersistence().getLevel(testModule, testus));

		cmd.onCommand(player, "setlvl", "d", "15", "testus");
		Mockito.verify(player).sendMessage("§6[EC] §eNo module found");
	}

	@Test
	public void senderAll() {
		CommandSender sender = Mockito.mock(CommandSender.class);

		cmd.onCommand(sender, "all");
		Mockito.verify(sender).sendMessage(
				"§6[EC] §eSyntax: /level all <Player>");

		cmd.onCommand(sender, "all", "cryxli");
		Mockito.verify(sender).sendMessage("§6[EC] §ePlayer cryxli is offline");

		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("cryxli");
		Mockito.when(server.getPlayer(player.getName())).thenReturn(player);
		sender = Mockito.mock(CommandSender.class);
		core.getPersistence().setExp(testModule, player, 0);
		cmd.onCommand(sender, "all", "cryxli");
		Mockito.verify(sender).sendMessage("§6[EC] --- ExpCraftPlugin ---");
		Mockito.verify(sender).sendMessage("§6[EC] §eTest (T): 1");
		Mockito.verify(sender, Mockito.times(2)).sendMessage(
				Mockito.anyString());
	}

	@Test
	public void senderGetExp() {
		CommandSender sender = Mockito.mock(CommandSender.class);

		cmd.onCommand(sender, "getexp");
		Mockito.verify(sender).sendMessage(
				"§6[EC] §eSyntax: /level getExp <Module> <Player>");

		cmd.onCommand(sender, "getexp", "t");
		Mockito.verify(sender, Mockito.times(2)).sendMessage(
				"§6[EC] §eSyntax: /level getExp <Module> <Player>");

		cmd.onCommand(sender, "getexp", "t", "cryxli");
		Mockito.verify(sender).sendMessage("§6[EC] §ePlayer cryxli is offline");

		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("cryxli");
		Mockito.when(server.getPlayer(player.getName())).thenReturn(player);
		core.getPersistence().setExp(testModule, player, 0);
		cmd.onCommand(sender, "getexp", "t", "cryxli");
		Mockito.verify(sender)
				.sendMessage("§6[EC] §eTest (T): lv1 at 0 points");

		cmd.onCommand(sender, "getexp", "d", "cryxli");
		Mockito.verify(sender).sendMessage("§6[EC] §eNo module found");
	}

	@Test
	public void senderInfo() {
		CommandSender sender = Mockito.mock(CommandSender.class);
		cmd.onCommand(sender, "info");
		senderInfoCheck(sender);
	}

	private void senderInfoCheck(final CommandSender sender) {
		Mockito.verify(sender).sendMessage("§6[EC] --- ExpCraftPlugin ---");
		Mockito.verify(sender).sendMessage("§6[EC] §eAvailable commands");
		Mockito.verify(sender).sendMessage("§6[EC] §e /lvl all");
		Mockito.verify(sender).sendMessage(
				"§6[EC] §e /lvl getExp <Module> [Player]");
		Mockito.verify(sender).sendMessage(
				"§6[EC] §e /lvl getLvl <Module> [Player]");
		Mockito.verify(sender).sendMessage(
				"§6[EC] §e /lvl setExp <Module> <Value> [Player]");
		Mockito.verify(sender).sendMessage(
				"§6[EC] §e /lvl setLvl <Module> <Value> [Player]");
	}

	@Test
	public void senderInfoModule() {
		CommandSender sender = Mockito.mock(CommandSender.class);
		cmd.onCommand(sender, "info", "t");
		senderInfoCheck(sender);

		sender = Mockito.mock(CommandSender.class);
		cmd.onCommand(sender, "info", "t", "2");
		senderInfoCheck(sender);
	}

	@Test
	public void senderNothing() {
		CommandSender sender = Mockito.mock(CommandSender.class);
		cmd.onCommand(sender);
		senderInfoCheck(sender);
	}

	@Test
	public void senderSetLvl() {
		CommandSender sender = Mockito.mock(CommandSender.class);

		cmd.onCommand(sender, "setlvl");
		Mockito.verify(sender).sendMessage(
				"§6[EC] §eSyntax: /level setLevel <Module> <Value> <Player>");

		cmd.onCommand(sender, "setlvl", "t");
		Mockito.verify(sender, Mockito.times(2)).sendMessage(
				"§6[EC] §eSyntax: /level setLevel <Module> <Value> <Player>");

		cmd.onCommand(sender, "setlevel", "t", "5");
		Mockito.verify(sender, Mockito.times(3)).sendMessage(
				"§6[EC] §eSyntax: /level setLevel <Module> <Value> <Player>");

		cmd.onCommand(sender, "setlvl", "t", "5", "cryxli");
		Mockito.verify(sender).sendMessage("§6[EC] §ePlayer cryxli is offline");

		sender = Mockito.mock(CommandSender.class);
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("cryxli");
		Mockito.when(server.getPlayer(player.getName())).thenReturn(player);
		cmd.onCommand(sender, "setlvl", "t", "asd", "cryxli");
		Mockito.verify(sender).sendMessage(
				"§6[EC] §eSyntax: /level setLevel <Module> <Value> [Player]");
		Mockito.verify(sender).sendMessage(
				"§6[EC] §e<Value> has to be a natural number");
		Mockito.verify(sender, Mockito.times(2)).sendMessage(
				Mockito.anyString());

		Mockito.when(server.getPlayer(player.getName())).thenReturn(player);
		cmd.onCommand(sender, "setlvl", "t", "5", "cryxli");
		Mockito.verify(sender).sendMessage("§6[EC] §eTest (T): lv5");
		Assert.assertEquals(5,
				core.getPersistence().getLevel(testModule, player));

		cmd.onCommand(sender, "setlvl", "d", "15", "cryxli");
		Mockito.verify(sender).sendMessage("§6[EC] §eNo module found");
	}

}
