package li.cryx.expcraft.persist;

import li.cryx.expcraft.ExpCraftCore;
import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.util.Chat;

import org.bukkit.entity.Player;

/**
 * This class implements the common method a persistence manager has to offer
 * and defines the implementation specific ones.
 * 
 * <p>
 * Note that experience starts with <code>0</code>, but level starts with
 * <code>1</code>.
 * </p>
 * 
 * @author cryxli
 */
public abstract class AbstractPersistenceManager {

	/** Scaling factor for the experience scale. Default is 20.0 */
	protected double constant;

	/** Maximal amount of experience that can be earned. Default is 196020.0 */
	protected double maxExp;

	/** Maximal level that can be reached. Default is 100 */
	protected int maxLevel;

	/** Reference to the core */
	protected ExpCraftCore core;

	/** The chat utility to send messages to players */
	private Chat chat;

	/** Create a new manager with default values. */
	public AbstractPersistenceManager() {
		this(20, 100);
	}

	/**
	 * Create a new manager with the given value. The {@link #maxExp} will be
	 * calculated according to the given values.
	 * 
	 * @param constant
	 *            Scaling factor for the experience scale.
	 * @param level
	 *            Maximal level that can be reached
	 */
	public AbstractPersistenceManager(final double constant, final int level) {
		this.constant = constant;
		maxLevel = level;
		calculate();
	}

	/**
	 * Add a certain amount of experience to the module's store for the given
	 * player.
	 * 
	 * @param module
	 *            The module in question
	 * @param player
	 *            Current player
	 * @param exp
	 *            The amount of experience to add.
	 * @return New value of experience
	 */
	public double addExp(final ExpCraftModule module, final Player player,
			final double exp) {
		double oldExp = getExp(module, player);
		if (exp <= 0) {
			return oldExp;
		}
		int oldLevel = getLevel(oldExp);
		double newExp = expInBound(oldExp + exp);
		int newLevel = getLevel(newExp);

		setExp(module, player, newExp);
		if (newLevel > oldLevel) {
			// notify player when he reaches a new level
			chat.notifyLevelGain(module, player, newLevel);
		}
		return newExp;
	}

	/**
	 * Calculate {@link #maxExp} depending on {@link #constant} and
	 * {@link #maxLevel}
	 */
	private void calculate() {
		maxExp = constant * Math.pow(maxLevel - 1, 2);
	}

	/**
	 * This method will return the closest legal value for experience for a
	 * given amount of experience.
	 * 
	 * @param exp
	 *            Experience to check.
	 * @return Closest legal value. If <code>exp</code> is lower then
	 *         <code>0</code>, <code>0</code> is returned. If <code>exp</code>
	 *         is larger than {@link #maxExp}, {@link #maxExp} is returned.
	 */
	protected double expInBound(final double exp) {
		return Math.min(maxExp, Math.max(0, exp));
	}

	abstract public void flush();

	/**
	 * Return the current experience for the given player and module.
	 * 
	 * @param module
	 *            The module in question
	 * @param player
	 *            Current player
	 * @return Current experience. If the <code>player</code> does not have an
	 *         entry for the <code>module</code>, <code>0</code> must be
	 *         returned.
	 */
	abstract public double getExp(ExpCraftModule module, Player player);

	/**
	 * Return the required experience to reach the next level.
	 * 
	 * @param module
	 *            The module in question
	 * @param player
	 *            Current player
	 * @return Experience for next level. Will never be larger than
	 *         {@link #maxExp}.
	 */
	public double getExpForNextLevel(final ExpCraftModule module,
			final Player player) {
		int level = getLevel(module, player);
		int levelInBound = Math.min(maxLevel - 1, Math.max(0, level));
		return Math.min(constant * Math.pow(levelInBound, 2), maxExp);
	}

	private int getLevel(final double exp) {
		return 1 + (int) Math.floor(Math.sqrt(exp / constant));
	}

	/**
	 * Calculate the level that corresponds to the given experience.
	 * 
	 * @param module
	 *            The module in question
	 * @param player
	 *            Current player
	 * @return Corresponding level in the bounds of <code>1</code> to
	 *         {@link #maxLevel}.
	 */
	public int getLevel(final ExpCraftModule module, final Player player) {
		return getLevel(getExp(module, player));
	}

	public void setConstant(final int value) {
		constant = value;
		calculate();
	}

	public void setCore(final ExpCraftCore core) {
		this.core = core;
		chat = new Chat(core);
	}

	/**
	 * Store experience for the given player and module.
	 * 
	 * @param module
	 *            The module in question
	 * @param player
	 *            Current player
	 * @param exp
	 *            Experience to store.
	 */
	abstract public void setExp(ExpCraftModule module, Player player, double exp);

	/**
	 * Change the current player's experience to just match the given level for
	 * the module.
	 * 
	 * @param module
	 *            The module in question
	 * @param player
	 *            Current player
	 * @param level
	 *            Level to set
	 * @return New value of experience
	 */
	public double setLevel(final ExpCraftModule module, final Player player,
			final int level) {
		int levelInBound = Math.min(maxLevel - 1, Math.max(0, level - 1));
		double exp = Math.min(constant * Math.pow(levelInBound, 2), maxExp);
		setExp(module, player, exp);
		return exp;
	}

	public void setMaxLevel(final int level) {
		maxLevel = level;
		calculate();
	}
}
