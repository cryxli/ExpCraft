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

import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.perm.AbstractPermissionManager;
import li.cryx.expcraft.perm.NoPermissionManager;
import li.cryx.expcraft.persist.AbstractPersistenceManager;
import li.cryx.expcraft.persist.InMemoryPersistentManager;

import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;

public class DummyExpCraft extends ExpCraft {

	private ExpCraftModule testModule;

	private final AbstractPersistenceManager pers = new InMemoryPersistentManager();

	private final AbstractPermissionManager perm = new NoPermissionManager();

	public DummyExpCraft(final Server server, final PluginDescriptionFile pdf) {
		super();
		File folder = new File("target/plugins");
		initialize(null, //
				server, //
				pdf, //
				new File(folder, "TestPlugin"), //
				new File(folder, "some.jar"), //
				getClass().getClassLoader());
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

	public void setTestModule(final ExpCraftModule testModule) {
		this.testModule = testModule;
	}
}
