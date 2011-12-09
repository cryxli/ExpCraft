package li.cryx.expcraft.cmp;

import junit.framework.Assert;
import li.cryx.expcraft.DummyCommandSender;
import li.cryx.expcraft.DummyExpCraftCore;
import li.cryx.expcraft.DummyModule;
import li.cryx.expcraft.DummyPlayer;
import li.cryx.expcraft.DummyPluginManager;
import li.cryx.expcraft.DummyServer;
import li.cryx.expcraft.ExpCraftCore;
import li.cryx.expcraft.cmd.CommandManager;

import org.bukkit.plugin.PluginDescriptionFile;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CommandManagerTest {

	private static DummyServer server;
	private static DummyPluginManager pluginManager;

	private static ExpCraftCore core;
	private static CommandManager cmd;
	private static DummyModule testModule;

	@BeforeClass
	public static void init() {
		PluginDescriptionFile pdf = new PluginDescriptionFile("ExpCraft", "0",
				"this");

		pluginManager = new DummyPluginManager();
		testModule = new DummyModule("Test", "T");
		pluginManager.addPlugin(testModule);

		server = new DummyServer();
		server.setPluginManager(pluginManager);

		pluginManager = new DummyPluginManager();
		core = new DummyExpCraftCore(server, pdf);
		cmd = new CommandManager(core);

		core.onEnable();
	}

	private final DummyCommandSender sender = new DummyCommandSender("CONSOLE");

	private final DummyPlayer player = new DummyPlayer("cryxli");

	private void playerInfoCheck() {
		String[] lines = player.getLastMessage().split("\n");
		Assert.assertEquals(6, lines.length);
		Assert.assertEquals("§6[EC] --- ExpCraftPlugin --- ", lines[0]);
		Assert.assertEquals("§6[EC] §eAvailable commands:", lines[1]);
		Assert.assertEquals("§6[EC] §e /lvl all", lines[2]);
		Assert.assertEquals("§6[EC] §e /lvl info <Module> [Page]", lines[3]);
		Assert.assertEquals("§6[EC] §e /lvl getExp <Module>", lines[4]);
		Assert.assertEquals("§6[EC] §e /lvl getLvl <Module>", lines[5]);
	}

	@Test
	public void playerInfoModule() {
		cmd.onCommand(player, "info", "t");
		Assert.assertEquals("Module info here, page: 1\n",
				player.getLastMessage());

		player.clearMsgCache();
		cmd.onCommand(player, "info", "t", "2");
		Assert.assertEquals("Module info here, page: 2\n",
				player.getLastMessage());

		player.clearMsgCache();
		cmd.onCommand(player, "info", "d");
		Assert.assertEquals("§6[EC] §eNo module found\n",
				player.getLastMessage());
	}

	@Test
	public void playerNoOpAll() {
		core.getPersistence().setExp(testModule, player, 0);
		cmd.onCommand(player, "all");
		String[] lines = player.getLastMessage().split("\n");
		Assert.assertEquals("§6[EC] --- ExpCraftPlugin --- ", lines[0]);
		Assert.assertEquals("§6[EC] §eTest (T): 1", lines[1]);
	}

	@Test
	public void playerNoOpGetExp() {
		cmd.onCommand(player, "getexp");
		Assert.assertEquals(
				"§6[EC] §eSyntax: /level getExp <Module> [Player]\n",
				player.getLastMessage());

		player.clearMsgCache();
		core.getPersistence().setExp(testModule, player, 0);
		cmd.onCommand(player, "getexp", "t");
		Assert.assertEquals("§6[EC] §eTest (T): lv1 at 0 points\n",
				player.getLastMessage());

		player.clearMsgCache();
		cmd.onCommand(player, "getexp", "t", "cryxli");
		Assert.assertEquals("§6[EC] §cYou cannot execute that command.\n",
				player.getLastMessage());

		player.clearMsgCache();
		cmd.onCommand(player, "getexp", "d", "cryxli");
		Assert.assertEquals("§6[EC] §cYou cannot execute that command.\n",
				player.getLastMessage());
	}

	@Test
	public void playerNoOpInfo() {
		cmd.onCommand(player, "info");
		playerInfoCheck();
	}

	@Test
	public void playerNoOpNothing() {
		cmd.onCommand(player);
		playerInfoCheck();
	}

	@Test
	public void playerNoOpSetLvl() {
		cmd.onCommand(player, "setlvl");
		Assert.assertEquals(
				"§6[EC] §eSyntax: /level setLevel <Module> <Value> [Player]\n",
				player.getLastMessage());

		player.clearMsgCache();
		cmd.onCommand(player, "setlvl", "t");
		Assert.assertEquals(
				"§6[EC] §eSyntax: /level setLevel <Module> <Value> [Player]\n",
				player.getLastMessage());

		player.clearMsgCache();
		cmd.onCommand(player, "setlevel", "t", "5");
		Assert.assertEquals("§6[EC] §cYou cannot execute that command.\n",
				player.getLastMessage());

		player.clearMsgCache();
		cmd.onCommand(player, "setlevel", "t", "asd");
		String[] lines = player.getLastMessage().split("\n");
		Assert.assertEquals(2, lines.length);
		Assert.assertEquals(
				"§6[EC] §eSyntax: /level setLevel <Module> <Value> [Player]",
				lines[0]);
		Assert.assertEquals("§6[EC] §e  <Value> has to be a natural number",
				lines[1]);

		player.clearMsgCache();
		cmd.onCommand(player, "setlvl", "t", "5", "cryxli");
		Assert.assertEquals("§6[EC] §cYou cannot execute that command.\n",
				player.getLastMessage());

		player.clearMsgCache();
		server.addPlayer(player);
		cmd.onCommand(player, "setlvl", "t", "5", "cryxli");
		Assert.assertEquals("§6[EC] §cYou cannot execute that command.\n",
				player.getLastMessage());

		player.clearMsgCache();
		cmd.onCommand(player, "setlvl", "d", "15", "cryxli");
		Assert.assertEquals("§6[EC] §cYou cannot execute that command.\n",
				player.getLastMessage());
	}

	@Test
	public void playerOpAll() {
		player.setOp(true);
		core.getPersistence().setExp(testModule, player, 0);
		cmd.onCommand(player, "all");
		String[] lines = player.getLastMessage().split("\n");
		Assert.assertEquals("§6[EC] --- ExpCraftPlugin --- ", lines[0]);
		Assert.assertEquals("§6[EC] §eTest (T): 1", lines[1]);

		player.clearMsgCache();
		cmd.onCommand(player, "all", "cryxli");
		Assert.assertEquals("§6[EC] §ePlayer cryxli is offline\n",
				player.getLastMessage());

		server.addPlayer(player);
		player.clearMsgCache();
		cmd.onCommand(player, "all", "cryxli");
		lines = player.getLastMessage().split("\n");
		Assert.assertEquals("§6[EC] --- ExpCraftPlugin --- ", lines[0]);
		Assert.assertEquals("§6[EC] §eTest (T): 1", lines[1]);
	}

	@Test
	public void playerOpGetExp() {
		player.setOp(true);
		cmd.onCommand(player, "getexp");
		Assert.assertEquals(
				"§6[EC] §eSyntax: /level getExp <Module> [Player]\n",
				player.getLastMessage());

		player.clearMsgCache();
		core.getPersistence().setExp(testModule, player, 0);
		cmd.onCommand(player, "getexp", "t");
		Assert.assertEquals("§6[EC] §eTest (T): lv1 at 0 points\n",
				player.getLastMessage());

		player.clearMsgCache();
		cmd.onCommand(player, "getexp", "t", "cryxli");
		Assert.assertEquals("§6[EC] §ePlayer cryxli is offline\n",
				player.getLastMessage());

		server.addPlayer(player);
		player.clearMsgCache();
		cmd.onCommand(player, "getexp", "t", "cryxli");
		Assert.assertEquals("§6[EC] §eTest (T): lv1 at 0 points\n",
				player.getLastMessage());

		player.clearMsgCache();
		cmd.onCommand(player, "getexp", "d", "cryxli");
		Assert.assertEquals("§6[EC] §eNo module found\n",
				player.getLastMessage());
	}

	@Test
	public void playerOpInfo() {
		player.setOp(true);
		cmd.onCommand(player, "info");
		playerOpInfoCheck();
	}

	private void playerOpInfoCheck() {
		String[] lines = player.getLastMessage().split("\n");
		Assert.assertEquals(8, lines.length);
		Assert.assertEquals("§6[EC] --- ExpCraftPlugin --- ", lines[0]);
		Assert.assertEquals("§6[EC] §eAvailable commands:", lines[1]);
		Assert.assertEquals("§6[EC] §e /lvl all", lines[2]);
		Assert.assertEquals("§6[EC] §e /lvl info <Module> [Page]", lines[3]);
		Assert.assertEquals("§6[EC] §e /lvl getExp <Module> [Player]", lines[4]);
		Assert.assertEquals("§6[EC] §e /lvl getLvl <Module> [Player]", lines[5]);
		Assert.assertEquals("§6[EC] §e /lvl setExp <Module> <Value> [Player]",
				lines[6]);
		Assert.assertEquals("§6[EC] §e /lvl setLvl <Module> <Value> [Player]",
				lines[7]);
	}

	@Test
	public void playerOpNothing() {
		player.setOp(true);
		cmd.onCommand(player);
		playerOpInfoCheck();
	}

	@Test
	public void playerOpSetLvl() {
		player.setOp(true);
		cmd.onCommand(player, "setlvl");
		Assert.assertEquals(
				"§6[EC] §eSyntax: /level setLevel <Module> <Value> [Player]\n",
				player.getLastMessage());

		player.clearMsgCache();
		cmd.onCommand(player, "setlvl", "t");
		Assert.assertEquals(
				"§6[EC] §eSyntax: /level setLevel <Module> <Value> [Player]\n",
				player.getLastMessage());

		player.clearMsgCache();
		cmd.onCommand(player, "setlevel", "t", "5");
		Assert.assertEquals("§6[EC] §eTest (T): lv5\n", player.getLastMessage());
		Assert.assertEquals(5,
				core.getPersistence().getLevel(testModule, player));

		player.clearMsgCache();
		cmd.onCommand(player, "setlevel", "t", "asd");
		String[] lines = player.getLastMessage().split("\n");
		Assert.assertEquals(2, lines.length);
		Assert.assertEquals(
				"§6[EC] §eSyntax: /level setLevel <Module> <Value> [Player]",
				lines[0]);
		Assert.assertEquals("§6[EC] §e  <Value> has to be a natural number",
				lines[1]);

		player.clearMsgCache();
		cmd.onCommand(player, "setlvl", "t", "10", "testus");
		Assert.assertEquals("§6[EC] §ePlayer testus is offline\n",
				player.getLastMessage());

		DummyPlayer testus = new DummyPlayer("testus");
		server.addPlayer(testus);
		player.clearMsgCache();
		cmd.onCommand(player, "setlvl", "t", "10", "testus");
		Assert.assertEquals("§6[EC] §eTest (T): lv10\n",
				player.getLastMessage());
		Assert.assertEquals(10,
				core.getPersistence().getLevel(testModule, testus));

		player.clearMsgCache();
		cmd.onCommand(player, "setlvl", "d", "15", "testus");
		Assert.assertEquals("§6[EC] §eNo module found\n",
				player.getLastMessage());
	}

	@SuppressWarnings("unused")
	private void printStringArray(final String[] ss) {
		for (String s : ss) {
			System.out.println(s);
		}
	}

	@Test
	public void senderAll() {
		cmd.onCommand(sender, "all");
		Assert.assertEquals("§6[EC] §eSyntax: /level all <Player>\n",
				sender.getLastMessage());

		sender.clearMsgCache();
		cmd.onCommand(sender, "all", "cryxli");
		Assert.assertEquals("§6[EC] §ePlayer cryxli is offline\n",
				sender.getLastMessage());

		server.addPlayer(player);
		core.getPersistence().setExp(testModule, player, 0);
		sender.clearMsgCache();
		cmd.onCommand(sender, "all", "cryxli");
		String[] lines = sender.getLastMessage().split("\n");
		Assert.assertEquals(2, lines.length);
		Assert.assertEquals("§6[EC] --- ExpCraftPlugin --- ", lines[0]);
		Assert.assertEquals("§6[EC] §eTest (T): 1", lines[1]);
	}

	@Test
	public void senderGetExp() {
		cmd.onCommand(sender, "getexp");
		Assert.assertEquals(
				"§6[EC] §eSyntax: /level getExp <Module> <Player>\n",
				sender.getLastMessage());

		sender.clearMsgCache();
		cmd.onCommand(sender, "getexp", "t");
		Assert.assertEquals(
				"§6[EC] §eSyntax: /level getExp <Module> <Player>\n",
				sender.getLastMessage());

		sender.clearMsgCache();
		cmd.onCommand(sender, "getexp", "t", "cryxli");
		Assert.assertEquals("§6[EC] §ePlayer cryxli is offline\n",
				sender.getLastMessage());

		server.addPlayer(player);
		core.getPersistence().setExp(testModule, player, 0);
		sender.clearMsgCache();
		cmd.onCommand(sender, "getexp", "t", "cryxli");
		Assert.assertEquals("§6[EC] §eTest (T): lv1 at 0 points\n",
				sender.getLastMessage());

		sender.clearMsgCache();
		cmd.onCommand(sender, "getexp", "d", "cryxli");
		Assert.assertEquals("§6[EC] §eNo module found\n",
				sender.getLastMessage());
	}

	@Test
	public void senderInfo() {
		cmd.onCommand(sender, "info");
		senderInfoCheck();
	}

	private void senderInfoCheck() {
		String[] lines = sender.getLastMessage().split("\n");
		Assert.assertEquals(7, lines.length);
		Assert.assertEquals("§6[EC] --- ExpCraftPlugin --- ", lines[0]);
		Assert.assertEquals("§6[EC] §eAvailable commands:", lines[1]);
		Assert.assertEquals("§6[EC] §e /lvl all", lines[2]);
		Assert.assertEquals("§6[EC] §e /lvl getExp <Module> [Player]", lines[3]);
		Assert.assertEquals("§6[EC] §e /lvl getLvl <Module> [Player]", lines[4]);
		Assert.assertEquals("§6[EC] §e /lvl setExp <Module> <Value> [Player]",
				lines[5]);
		Assert.assertEquals("§6[EC] §e /lvl setLvl <Module> <Value> [Player]",
				lines[6]);
	}

	@Test
	public void senderInfoModule() {
		cmd.onCommand(sender, "info", "t");
		senderInfoCheck();
	}

	@Test
	public void senderNothing() {
		cmd.onCommand(sender);
		senderInfoCheck();
	}

	@Test
	public void senderSetLvl() {
		cmd.onCommand(sender, "setlvl");
		Assert.assertEquals(
				"§6[EC] §eSyntax: /level setLevel <Module> <Value> <Player>\n",
				sender.getLastMessage());

		sender.clearMsgCache();
		cmd.onCommand(sender, "setlvl", "t");
		Assert.assertEquals(
				"§6[EC] §eSyntax: /level setLevel <Module> <Value> <Player>\n",
				sender.getLastMessage());

		sender.clearMsgCache();
		cmd.onCommand(sender, "setlevel", "t", "5");
		Assert.assertEquals(
				"§6[EC] §eSyntax: /level setLevel <Module> <Value> <Player>\n",
				sender.getLastMessage());

		sender.clearMsgCache();
		cmd.onCommand(sender, "setlvl", "t", "5", "cryxli");
		Assert.assertEquals("§6[EC] §ePlayer cryxli is offline\n",
				sender.getLastMessage());

		sender.clearMsgCache();
		server.addPlayer(player);
		cmd.onCommand(sender, "setlvl", "t", "asd", "cryxli");
		String[] lines = sender.getLastMessage().split("\n");
		Assert.assertEquals(2, lines.length);
		Assert.assertEquals(
				"§6[EC] §eSyntax: /level setLevel <Module> <Value> [Player]",
				lines[0]);
		Assert.assertEquals("§6[EC] §e  <Value> has to be a natural number",
				lines[1]);

		// Assert.assertEquals(1, core.getPersistence().getLevel(testModule,
		// player));
		sender.clearMsgCache();
		server.addPlayer(player);
		cmd.onCommand(sender, "setlvl", "t", "5", "cryxli");
		Assert.assertEquals("§6[EC] §eTest (T): lv5\n", sender.getLastMessage());
		Assert.assertEquals(5,
				core.getPersistence().getLevel(testModule, player));

		sender.clearMsgCache();
		cmd.onCommand(sender, "setlvl", "d", "15", "cryxli");
		Assert.assertEquals("§6[EC] §eNo module found\n",
				sender.getLastMessage());
	}

	@Before
	public void setup() {
		server.clearPlayers();

		sender.clearMsgCache();
		player.clearMsgCache();
		player.setOp(false);
	}

}
