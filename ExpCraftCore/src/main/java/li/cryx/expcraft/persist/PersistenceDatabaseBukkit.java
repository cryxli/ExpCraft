package li.cryx.expcraft.persist;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.PersistenceException;

import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.persist.model.Experience;

import org.bukkit.entity.Player;

import com.avaje.ebean.Query;

public class PersistenceDatabaseBukkit extends AbstractPersistenceManager {

	@Override
	public void flush() {
	}

	@Override
	public double getExp(final ExpCraftModule module, final Player player) {
		try {
			return getExperience(module, player).getExperience();
		} catch (PersistenceException e) {
			core.getLogger().log(Level.SEVERE,
					"Unable to retrieve experience.", e);
			return 0;
		}
	}

	private Experience getExperience(final ExpCraftModule module,
			final Player player) {
		Query<Experience> query = core.getDatabase().createQuery(
				Experience.class);
		query.where().eq("module", module.getAbbr())
				.eq("player", player.getName()).setMaxRows(1);
		List<Experience> exp = query.findList();
		if (exp == null || exp.size() == 0) {
			Experience xp = core.getDatabase().createEntityBean(
					Experience.class);
			xp.setPlayerEntity(player);
			xp.setModuleEntity(module);
			xp.setExperience(0);
			return xp;
		} else {
			return exp.get(0);
		}
	}

	@Override
	public void setExp(final ExpCraftModule module, final Player player,
			final double exp) {
		try {
			Experience xp = getExperience(module, player);
			xp.setExperience(exp);
			core.getDatabase().save(xp);
		} catch (PersistenceException e) {
			core.getLogger().log(Level.SEVERE, "Unable to persist experience.",
					e);
		}
	}

}
