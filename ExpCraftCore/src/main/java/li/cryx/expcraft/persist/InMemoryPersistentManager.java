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

import java.util.HashMap;
import java.util.Map;

import li.cryx.expcraft.module.ExpCraftModule;

import org.bukkit.entity.Player;

/**
 * This class is a dummy implementation of a {@link AbstractPersistenceManager}
 * that does not store anything but keeps values in memory.
 * 
 * @author cryxli
 */
public class InMemoryPersistentManager extends AbstractPersistenceManager {

	private final Map<ExpCraftModule, Map<Player, Double>> storage = new HashMap<ExpCraftModule, Map<Player, Double>>();

	public InMemoryPersistentManager() {
		super();
	}

	public InMemoryPersistentManager(final double constant, final int level) {
		super(constant, level);
	}

	@Override
	public void flush() {
	}

	@Override
	public double getExp(final ExpCraftModule module, final Player player) {
		Map<Player, Double> map = getMapForModule(module);
		Double exp = map.get(player);
		if (exp == null) {
			exp = 0.0;
		}
		return exp;
	}

	private Map<Player, Double> getMapForModule(final ExpCraftModule module) {
		Map<Player, Double> map = storage.get(module);
		if (map == null) {
			map = new HashMap<Player, Double>();
			storage.put(module, map);
		}
		return map;
	}

	@Override
	public void setExp(final ExpCraftModule module, final Player player,
			final double exp) {
		Map<Player, Double> map = getMapForModule(module);
		map.put(player, expInBound(exp));
	}

}
