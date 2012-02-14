package li.cryx.expcraft.persist;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.Locale;

import junit.framework.Assert;
import li.cryx.expcraft.DummyModule;
import li.cryx.expcraft.DummyPlayer;
import li.cryx.expcraft.module.ExpCraftModule;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test database functionality.
 * 
 * @author cryxli
 */
public class DatabasePersistenceTest {

	private PersistenceDatabase db;

	private final static String DB_FILE = "target/test.db";

	@BeforeClass
	public static void setupWrongLocale() {
		// Thanks to seberoth from github, we're aware that with certain
		// system defaults, the first persistence implementation failed.
		// Therefore, before any DB tests are run, we set the system's default
		// locale to one of the troublesome locales. Sorry, Germany.
		Locale.setDefault(Locale.GERMANY);
		Assert.assertEquals("1,23",
				MessageFormat.format("{0,number,0.00}", 1.2345));
		// SQL standard defines floating points to have to be written with "."
		// E.g., German "1,5" would cause the DB to throw an error on every
		// single exp update.
	}

	@Before
	public void prepare() throws SQLException {
		if (db != null) {
			// closing previous database connection
			db.flush();
			db = null;
		}

		File file = new File(DB_FILE);
		if (file.exists() && !file.delete()) {
			// couldn't delete db file, deleting table contents instead
			Connection conn = DriverManager.getConnection("jdbc:sqlite:"
					+ DB_FILE);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM ExpCraftTable");
			stmt.close();
			conn.close();
		}

		// creating new database connection
		db = new PersistenceDatabase();
		db.setDatabase("sqlite");
		db.setDbUrl(DB_FILE);
	}

	@Test
	public void testDbOperations() throws SQLException {
		// prepare test: 1 module, 1 user
		DummyPlayer player = new DummyPlayer("cryxli");
		ExpCraftModule module = new DummyModule("Testing", "T");

		// create player entry
		double exp = db.getExp(module, player);
		Assert.assertEquals(0, exp, 0);

		// change entry
		db.setExp(module, player, 5.5);
		exp = db.getExp(module, player);
		Assert.assertEquals(5.5, exp, 0);

		// update entry
		exp = db.addExp(module, player, 4.5);
		Assert.assertEquals(10, exp, 0);
		// double check
		exp = db.getExp(module, player);
		Assert.assertEquals(10, exp, 0);
	}

	@Test
	public void testLargeExp() throws SQLException {
		// prepare test: 1 module, 1 user
		DummyPlayer player = new DummyPlayer("cryxli");
		ExpCraftModule module = new DummyModule("LargeExp", "Le");

		double exp = db.getExp(module, player);
		Assert.assertEquals(0, exp, 0);

		db.setExp(module, player, 1523.75);
		exp = db.getExp(module, player);
		Assert.assertEquals(1523.75, exp, 0);
	}

	@Test
	public void testMultiModule() throws SQLException {
		// prepare: 2 modules, 1 player
		DummyPlayer player = new DummyPlayer("cryxli");
		ExpCraftModule mod1 = new DummyModule("Testing 1", "T1");
		ExpCraftModule mod2 = new DummyModule("Testing 2", "T2");

		db.getExp(mod1, player);
		db.getExp(mod2, player);

		// manually inspect test results
		Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DB_FILE);
		Statement stmt = conn.createStatement();
		ResultSet set = stmt.executeQuery("SELECT COUNT(*) FROM ExpCraftTable");
		set.next();
		Assert.assertEquals(2, set.getInt(1));
		set.close();
		set = stmt
				.executeQuery("SELECT id,player,module,exp FROM ExpCraftTable ORDER BY id");
		set.next();
		Assert.assertEquals(1, set.getLong(1));
		Assert.assertEquals("cryxli", set.getString(2));
		Assert.assertEquals("T1", set.getString(3));
		Assert.assertEquals(0, set.getDouble(4), 0);
		set.next();
		Assert.assertEquals(2, set.getLong(1));
		Assert.assertEquals("cryxli", set.getString(2));
		Assert.assertEquals("T2", set.getString(3));
		Assert.assertEquals(0, set.getDouble(4), 0);
		set.close();
		stmt.close();
		conn.close();

		// next step
		db.setExp(mod1, player, 1.8);
		db.setExp(mod2, player, 5.3);

		// manually inspect test results
		conn = DriverManager.getConnection("jdbc:sqlite:" + DB_FILE);
		stmt = conn.createStatement();
		set = stmt.executeQuery("SELECT COUNT(*) FROM ExpCraftTable");
		set.next();
		Assert.assertEquals(2, set.getInt(1));
		set.close();
		set = stmt
				.executeQuery("SELECT id,player,module,exp FROM ExpCraftTable ORDER BY id");
		set.next();
		Assert.assertEquals(1, set.getLong(1));
		Assert.assertEquals("cryxli", set.getString(2));
		Assert.assertEquals("T1", set.getString(3));
		Assert.assertEquals(1.8, set.getDouble(4), 0);
		set.next();
		Assert.assertEquals(2, set.getLong(1));
		Assert.assertEquals("cryxli", set.getString(2));
		Assert.assertEquals("T2", set.getString(3));
		Assert.assertEquals(5.3, set.getDouble(4), 0);
		set.close();
		stmt.close();
		conn.close();
	}

	@Test
	public void testMultiPlayer() throws Throwable {
		// preapre: 1 module, 2 players
		DummyPlayer cryxli = new DummyPlayer("cryxli");
		DummyPlayer fakt00r = new DummyPlayer("fakt00r");
		ExpCraftModule module = new DummyModule("Testing", "T");

		db.getExp(module, cryxli);
		db.getExp(module, fakt00r);

		// manually inspect test results
		Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DB_FILE);
		Statement stmt = conn.createStatement();
		ResultSet set = stmt.executeQuery("SELECT COUNT(*) FROM ExpCraftTable");
		set.next();
		Assert.assertEquals(2, set.getInt(1));
		set.close();
		set = stmt
				.executeQuery("SELECT id,player,module,exp FROM ExpCraftTable ORDER BY id");
		set.next();
		Assert.assertEquals(1, set.getLong(1));
		Assert.assertEquals("cryxli", set.getString(2));
		Assert.assertEquals("T", set.getString(3));
		Assert.assertEquals(0, set.getDouble(4), 0);
		set.next();
		Assert.assertEquals(2, set.getLong(1));
		Assert.assertEquals("fakt00r", set.getString(2));
		Assert.assertEquals("T", set.getString(3));
		Assert.assertEquals(0, set.getDouble(4), 0);
		set.close();
		stmt.close();
		conn.close();

		// next step
		db.setExp(module, cryxli, 5.4);
		db.setExp(module, fakt00r, 15.0);

		// manually inspect test results
		conn = DriverManager.getConnection("jdbc:sqlite:" + DB_FILE);
		stmt = conn.createStatement();
		set = stmt.executeQuery("SELECT COUNT(*) FROM ExpCraftTable");
		set.next();
		Assert.assertEquals(2, set.getInt(1));
		set.close();
		set = stmt
				.executeQuery("SELECT id,player,module,exp FROM ExpCraftTable ORDER BY id");
		set.next();
		Assert.assertEquals(1, set.getLong(1));
		Assert.assertEquals("cryxli", set.getString(2));
		Assert.assertEquals("T", set.getString(3));
		Assert.assertEquals(5.4, set.getDouble(4), 0);
		set.next();
		Assert.assertEquals(2, set.getLong(1));
		Assert.assertEquals("fakt00r", set.getString(2));
		Assert.assertEquals("T", set.getString(3));
		Assert.assertEquals(15.0, set.getDouble(4), 0);
		set.close();
		stmt.close();
		conn.close();
	}
}
