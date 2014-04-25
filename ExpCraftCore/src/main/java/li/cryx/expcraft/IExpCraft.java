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
package li.cryx.expcraft;

import java.io.File;
import java.util.Collection;
import java.util.logging.Logger;

import li.cryx.expcraft.i18n.AbstractTranslator;
import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.perm.AbstractPermissionManager;
import li.cryx.expcraft.persist.AbstractPersistenceManager;

import org.bukkit.Server;

import com.avaje.ebean.EbeanServer;

/**
 * This interface defines the methods usually presented by {@link ExpCraft}, but
 * while unittest this interface can be mocked.
 * 
 * @author cryxli
 */
public interface IExpCraft {

	/** Get database abstraction (from bukkit) */
	EbeanServer getDatabase();

	/** Get plugin data folder (from bukkit) */
	File getDataFolder();

	/** Get logger factory (from bukkit) */
	Logger getLogger();

	/**
	 * Get the {@link ExpCraftModule} instance matching the given module
	 * abbreviation.
	 * 
	 * @param modAbbr
	 *            An abbreviation identifying a module
	 * @return Matching module, or, <code>null</code>
	 */
	ExpCraftModule getModuleByAbbr(final String modAbbr);

	/** Return all loaded modules. */
	Collection<ExpCraftModule> getModules();

	/** Get the current permissions manager */
	AbstractPermissionManager getPermissions();

	/** Get the current persistence manager */
	AbstractPersistenceManager getPersistence();

	/** Get server (from bukkit) */
	Server getServer();

	/** Get the translation factory of the plugin */
	AbstractTranslator getTranslator();

}
