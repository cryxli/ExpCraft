package li.cryx.expcraft.persist;

import li.cryx.expcraft.module.ExpCraftModule;

import org.bukkit.entity.Player;
import org.junit.Assert;
import org.junit.Test;

public class AbstractPersistenceManagerTest extends AbstractPersistenceManager {

	private double expectedExp = 0;

	private double returnExp = 0;

	@Test
	public void checkSettings() {
		Assert.assertEquals(20, constant, 0);
		Assert.assertEquals(100, maxLevel);
		Assert.assertEquals(196020, maxExp, 0);
	}

	@Override
	public void flush() {
	}

	@Override
	public double getExp(final ExpCraftModule module, final Player player) {
		return returnExp;
	}

	@Override
	public void setExp(final ExpCraftModule module, final Player player,
			final double exp) {
		Assert.assertEquals(expectedExp, expInBound(exp), 0);
	}

	@Test
	public void testAddExp() {
		// basic cases
		returnExp = 0;
		expectedExp = 0;
		addExp(null, null, 0);

		returnExp = 0;
		expectedExp = 5;
		addExp(null, null, 5);

		returnExp = 10.5;
		expectedExp = 10.5;
		addExp(null, null, 0);

		returnExp = 10.5;
		expectedExp = 16.25;
		addExp(null, null, 5.75);

		// lower bound
		returnExp = 0;
		expectedExp = 0;
		addExp(null, null, -10);

		// upper bound
		returnExp = maxExp;
		expectedExp = maxExp;
		addExp(null, null, maxExp + 871);
	}

	@Test
	public void testExpInBound() {
		// basic cases
		Assert.assertEquals(0, expInBound(0), 0);
		Assert.assertEquals(100, expInBound(100), 0);
		Assert.assertEquals(12.5, expInBound(12.5), 0);
		// lower bound
		Assert.assertEquals(0, expInBound(-159), 0);
		// upper bound
		Assert.assertEquals(maxExp, expInBound(maxExp + 801), 0);
	}

	@Test
	public void testGainLevel() {
		returnExp = 0;
		expectedExp = 20;
		// because of test-setup a level-up will cause a NullPointerException
		// but only after the exp has been set
		try {
			addExp(null, null, 20);
			Assert.fail();
		} catch (NullPointerException e) {
		}
	}

	@Test
	public void testGetLevel() {
		// basic cases
		returnExp = 0;
		Assert.assertEquals(1, getLevel(null, null));

		returnExp = 421;
		Assert.assertEquals(5, getLevel(null, null));

		returnExp = 19;
		Assert.assertEquals(1, getLevel(null, null));

		returnExp = 20;
		Assert.assertEquals(2, getLevel(null, null));

		returnExp = 21;
		Assert.assertEquals(2, getLevel(null, null));

		returnExp = 113535;
		Assert.assertEquals(76, getLevel(null, null));

		// lower bound
		returnExp = -1;
		Assert.assertEquals(1, getLevel(null, null));

		returnExp = -maxExp;
		Assert.assertEquals(1, getLevel(null, null));

		// upper bound
		returnExp = maxExp + 456;
		Assert.assertEquals(maxLevel, getLevel(null, null));

		// everything
		for (int i = 1; i <= maxLevel; i++) {
			returnExp = 5 + constant * Math.pow(i - 1, 2);
			Assert.assertEquals(i, getLevel(null, null));
		}
	}

	@Test
	public void testSetLevel() {
		// basic cases
		expectedExp = 20;
		setLevel(null, null, 2);

		expectedExp = 500;
		setLevel(null, null, 6);

		expectedExp = 112500;
		setLevel(null, null, 76);

		// lower bound
		expectedExp = 0;
		setLevel(null, null, 0);

		expectedExp = 0;
		setLevel(null, null, -1);

		// upper bound
		expectedExp = maxExp;
		setLevel(null, null, maxLevel + 2);

		// everything
		for (int i = 1; i <= maxLevel; i++) {
			expectedExp = constant * Math.pow(i - 1, 2);
			setLevel(null, null, i);
		}
	}
}
