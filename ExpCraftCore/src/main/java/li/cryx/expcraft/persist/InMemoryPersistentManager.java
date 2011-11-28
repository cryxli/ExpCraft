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
