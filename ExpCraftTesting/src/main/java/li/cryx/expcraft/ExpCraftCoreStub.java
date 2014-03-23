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

import li.cryx.expcraft.cmd.CommandManager;
import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.perm.AbstractPermissionManager;
import li.cryx.expcraft.persist.AbstractPersistenceManager;

import org.bukkit.Server;

import com.avaje.ebean.EbeanServer;

/**
 * Usually you will not need an instance of {@link ExpCraftCore} while running
 * your tests. However, if you test case causes a level increase the
 * {@link CommandManager} which is part of the
 * {@link AbstractPersistenceManager} will need it. If you run into an according
 * <code>NullPointerException</code>, create a stub like this:
 * 
 * <pre>
 * // this method is called by {@link CommandManager} to broadcast messages
 * Mockito.when(server.getOnlinePlayers()).thenReturn(new Player[] {});
 * // instantiate the core stub
 * ExpCraftCoreStub core = new ExpCraftCoreStub(server, new PluginDescriptionFile(
 * 		&quot;ExpCraft&quot;, &quot;0&quot;, &quot;&quot;));
 * // set the core on the {@link AbstractPersistenceManager}
 * pers.setCore(core);
 * </pre>
 * 
 * @author cryxli
 */
public class ExpCraftCoreStub implements IExpCraft {

	private final Server server;

	private AbstractPermissionManager perm;

	private AbstractPersistenceManager pers;

	/** Create a stub. Link the plugin to the mocked server. */
	public ExpCraftCoreStub(final Server server) {
		this.server = server;
	}

	public ExpCraftCoreStub(final Server server,
			final AbstractPermissionManager perm,
			final AbstractPersistenceManager pers) {
		this(server);
		setPermissions(perm);
		setPersistence(pers);
	}

	@Override
	public EbeanServer getDatabase() {
		// not (yet) used in unittests
		return null;
	}

	@Override
	public File getDataFolder() {
		return new File("target/plugins");
	}

	@Override
	public Logger getLogger() {
		return Logger.getAnonymousLogger();
	}

	@Override
	public ExpCraftModule getModuleByAbbr(final String modAbbr) {
		// not (yet) used in unittests
		return null;
	}

	@Override
	public Collection<ExpCraftModule> getModules() {
		// not (yet) used in unittests
		return null;
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

	public void setPermissions(final AbstractPermissionManager perm) {
		this.perm = perm;
	}

	public void setPersistence(final AbstractPersistenceManager pers) {
		this.pers = pers;
		pers.setCore(this);
	}

}
