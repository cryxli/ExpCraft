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
package li.cryx.expcraft.persist;

import li.cryx.expcraft.ExpCraft;
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
 * <pre>
 *                /----------------,
 * Level = 1 + -\/  Exp / Constant
 * </pre>
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
	protected ExpCraft core;

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

	// TODO documentation
	public abstract void flush();

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

	// TODO documentation
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

	// TODO documentation
	public void setConstant(final int value) {
		constant = value;
		calculate();
	}

	/**
	 * Link the manager to the ExpCraft core.
	 * 
	 * @param core
	 *            Reference to the core
	 */
	public void setCore(final ExpCraft core) {
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

	/**
	 * Inform the manager about the max level reachable per module.
	 * 
	 * @param level
	 *            The level cap.
	 */
	public void setMaxLevel(final int level) {
		maxLevel = level;
		calculate();
	}
}
