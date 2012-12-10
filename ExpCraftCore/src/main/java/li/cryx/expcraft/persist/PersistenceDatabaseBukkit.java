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

import java.text.MessageFormat;
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
			String msg = MessageFormat.format(
					"Unable to retrieve experience [module={0}, player={1}].",
					module.getInfo().getAbbr(), player.getName());
			core.getLogger().log(Level.SEVERE, msg, e);
			return 0;
		}
	}

	private Experience getExperience(final ExpCraftModule module,
			final Player player) throws PersistenceException {
		Query<Experience> query = core.getDatabase().createQuery(
				Experience.class);
		query.where().eq("module", module.getInfo().getAbbr())
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
			String msg = MessageFormat
					.format("Unable to persist experience [module={0}, player={1}, exp={2}].",
							module.getInfo().getAbbr(), player.getName(), exp);
			core.getLogger().log(Level.SEVERE, msg, e);
		}
	}

}
