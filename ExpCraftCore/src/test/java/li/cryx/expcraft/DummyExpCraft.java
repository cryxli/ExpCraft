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
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import li.cryx.expcraft.i18n.AbstractTranslator;
import li.cryx.expcraft.i18n.FallbackTranslation;
import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.perm.AbstractPermissionManager;
import li.cryx.expcraft.perm.NoPermissionManager;
import li.cryx.expcraft.persist.AbstractPersistenceManager;
import li.cryx.expcraft.persist.InMemoryPersistentManager;

import org.bukkit.Server;

import com.avaje.ebean.EbeanServer;

public class DummyExpCraft implements IExpCraft {

	private ExpCraftModule testModule;

	private final AbstractPersistenceManager pers = new InMemoryPersistentManager();

	private final AbstractPermissionManager perm = new NoPermissionManager();

	private final AbstractTranslator translator = new FallbackTranslation();

	private final Server server;

	public DummyExpCraft(final Server server) {
		this.server = server;

		// no longer used:
		// new PluginDescriptionFile("ExpCraft", "0", "")
	}

	@Override
	public EbeanServer getDatabase() {
		// not (yet) used in unittests
		return null;
	}

	@Override
	public File getDataFolder() {
		// not (yet) used in unittests
		return null;
	}

	@Override
	public Logger getLogger() {
		return Logger.getAnonymousLogger();
	}

	@Override
	public ExpCraftModule getModuleByAbbr(final String modAbbr) {
		if ("t".equals(modAbbr)) {
			return testModule;
		} else {
			return null;
		}
	}

	@Override
	public Collection<ExpCraftModule> getModules() {
		Set<ExpCraftModule> set = new HashSet<ExpCraftModule>();
		set.add(testModule);
		return set;
	}

	@Override
	public AbstractPermissionManager getPermissions() {
		return perm;
	}

	@Override
	public AbstractPersistenceManager getPersistence() {
		return pers;
	}

	@Override
	public Server getServer() {
		return server;
	}

	@Override
	public AbstractTranslator getTranslator() {
		return translator;
	}

	public void setTestModule(final ExpCraftModule testModule) {
		this.testModule = testModule;
	}

}
