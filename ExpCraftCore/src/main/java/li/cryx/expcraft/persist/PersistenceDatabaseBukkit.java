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

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;

import javax.persistence.Column;
import javax.persistence.PersistenceException;
import javax.persistence.Table;

import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.persist.model.Experience;

import org.bukkit.entity.Player;

import com.avaje.ebean.Query;
import com.avaje.ebean.SqlUpdate;

public class PersistenceDatabaseBukkit extends AbstractPersistenceManager {

	private static final String UPDATE;

	static {
		StringBuffer buf = new StringBuffer();
		buf.append("UPDATE ").append(getTable());
		buf.append("   SET ").append(getFieldName("experience"))
				.append(" = :exp");
		buf.append(" WHERE ").append(getFieldName("module"))
				.append(" = :module");
		buf.append("   AND ").append(getFieldName("playerUuid"))
				.append(" = :playerUuid");
		UPDATE = buf.toString();
	}

	private static Class<Experience> getEntity() {
		return Experience.class;
	}

	private static String getFieldName(final String name) {
		for (Field f : getEntity().getDeclaredFields()) {
			if (f.getName().equals(name)) {
				Column column = f.getAnnotation(Column.class);
				if (column != null) {
					return column.name();
				}
				break;
			}
		}
		return name;
	}

	private static String getTable() {
		return getEntity().getAnnotation(Table.class).name();
	}

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
		query.where().eq("module", module.getInfo().getAbbr()) //
				// .eq("player", player.getName()) //
				.eq("playerUuid", player.getUniqueId().toString()) //
				.setMaxRows(1);
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
			if (core.getDatabase().getBeanState(xp).isNew()) {
				xp.setExperience(exp);
				core.getDatabase().save(xp);
			} else {
				SqlUpdate update = core.getDatabase().createSqlUpdate(UPDATE);
				update.setParameter("exp", exp);
				update.setParameter("module", module.getInfo().getAbbr());
				update.setParameter("playerUuid", player.getUniqueId()
						.toString());
				update.execute();
			}
		} catch (PersistenceException e) {
			String msg = MessageFormat
					.format("Unable to persist experience [module={0}, player={1}, exp={2}].",
							module.getInfo().getAbbr(), player.getName(), exp);
			core.getLogger().log(Level.SEVERE, msg, e);
		}
	}

}
