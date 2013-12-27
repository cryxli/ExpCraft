package li.cryx.expcraft.persist;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;
import li.cryx.expcraft.ExpCraft;
import li.cryx.expcraft.loader.ModuleInfo;
import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.persist.model.Experience;

import org.bukkit.entity.Player;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.avaje.ebean.BeanState;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.avaje.ebean.SqlUpdate;

/**
 * Verify syntactical correctness of {@link PersistenceDatabaseBukkit}.
 * 
 * @author cryxli
 */
public class PersistenceDatabaseBukkitTest {

	/** Expected update query */
	private static final String UPDATE = "UPDATE ExpCraftTable   SET exp = :exp WHERE module = :module   AND player = :player";

	/** Player's name for this tests. */
	private static final String PLAYER = "cryxli";

	/** Module's abbreviation for this tests. */
	private static final String MODULE = "T";

	/** A mocked module */
	private ExpCraftModule module;

	/** The class to test. */
	private PersistenceDatabaseBukkit pers;

	/** A mocked ExpCraft core */
	private ExpCraft core;

	/** A mocked Avaje server */
	private EbeanServer eBeanServer;

	private Player player;

	/** normal case */
	@Test
	public void getExp() {
		// prepare
		Experience experience = new Experience();
		experience.setModuleEntity(module);
		experience.setPlayerEntity(player);
		experience.setExperience(15.0);
		List<Experience> results = new LinkedList<Experience>();
		results.add(experience);

		// test
		getExp(results, 15);
	}

	/** perform a getExp() test */
	@SuppressWarnings("unchecked")
	private void getExp(final List<Experience> queryResult,
			final double expextedExp) {
		// prepare
		ExpressionList<Experience> eList = Mockito.mock(ExpressionList.class);
		Mockito.when(eList.eq(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(eList);
		Query<Experience> query = Mockito.mock(Query.class);
		Mockito.when(query.where()).thenReturn(eList);
		Mockito.when(query.findList()).thenReturn(queryResult);

		Mockito.when(eBeanServer.createQuery(Experience.class)).thenReturn(
				query);
		Mockito.when(eBeanServer.createEntityBean(Experience.class))
				.thenReturn(new Experience());

		// test
		double exp = pers.getExp(module, player);

		// verify
		Assert.assertEquals(expextedExp, exp, 0);
		Mockito.verify(eList).eq("module", MODULE);
		Mockito.verify(eList).eq("player", PLAYER);
	}

	/** no data found */
	@Test
	public void getExpEmptyList() {
		getExp(new LinkedList<Experience>(), 0);
	}

	/** absolutely no data found */
	@Test
	public void getExpNull() {
		getExp(null, 0);
	}

	/** Reset mocks for each test case. */
	@Before
	public void preparePersistence() {
		eBeanServer = Mockito.mock(EbeanServer.class);

		core = Mockito.mock(ExpCraft.class);
		Mockito.when(core.getDatabase()).thenReturn(eBeanServer);

		pers = new PersistenceDatabaseBukkit();
		pers.setCore(core);

		module = Mockito.mock(ExpCraftModule.class);
		Mockito.when(module.getInfo()).thenReturn(
				new ModuleInfo(null, MODULE, null));

		player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn(PLAYER);
	}

	/** perform a setExp() test */
	@SuppressWarnings("unchecked")
	private void setExp(final double exp, final boolean isNew) {
		// prepare
		ExpressionList<Experience> eList = Mockito.mock(ExpressionList.class);
		Mockito.when(eList.eq(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(eList);
		Query<Experience> query = Mockito.mock(Query.class);
		Mockito.when(query.where()).thenReturn(eList);
		Mockito.when(query.findList()).thenReturn(new ArrayList<Experience>());

		Mockito.when(eBeanServer.createQuery(Experience.class)).thenReturn(
				query);
		Mockito.when(eBeanServer.createEntityBean(Experience.class))
				.thenReturn(new Experience());

		BeanState state = Mockito.mock(BeanState.class);
		Mockito.when(state.isNew()).thenReturn(isNew);
		Mockito.when(eBeanServer.getBeanState(Mockito.any(Experience.class)))
				.thenReturn(state);

		SqlUpdate updateQuery = Mockito.mock(SqlUpdate.class);
		Mockito.when(eBeanServer.createSqlUpdate(Mockito.anyString()))
				.thenReturn(updateQuery);

		// test
		pers.setExp(module, player, exp);

		// verify
		if (isNew) {
			Mockito.verify(eBeanServer).save(Mockito.any(Experience.class));
		} else {
			Mockito.verify(eBeanServer).createSqlUpdate(UPDATE);
			Mockito.verify(updateQuery).setParameter("exp", exp);
			Mockito.verify(updateQuery).setParameter("module", MODULE);
			Mockito.verify(updateQuery).setParameter("player", PLAYER);
			Mockito.verify(updateQuery).execute();
		}
	}

	/** set exp to low value */
	@Test
	public void setExp1() {
		setExp(0.5, false);
	}

	/** set exp to higher value */
	@Test
	public void setExp2() {
		setExp(2545.8, false);
	}

	/** set exp to "natural number" */
	@Test
	public void setExp3() {
		setExp(15.0, false);
	}

	/** set exp the first time */
	@Test
	public void setExp4() {
		setExp(2.0, true);
	}
}
