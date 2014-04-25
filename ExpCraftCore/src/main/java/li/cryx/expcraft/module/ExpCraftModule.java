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
package li.cryx.expcraft.module;

import java.io.File;
import java.util.Locale;

import li.cryx.expcraft.ExpCraft;
import li.cryx.expcraft.i18n.AbstractModuleTranslator;
import li.cryx.expcraft.loader.ModuleInfo;
import li.cryx.expcraft.perm.AbstractPermissionManager;
import li.cryx.expcraft.persist.AbstractPersistenceManager;
import li.cryx.expcraft.util.TypedProperties;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 * This abstract class defines the behaviour of ExpCraft modules.
 * 
 * @author cryxli
 */
public abstract class ExpCraftModule {

	/** Reference to ExpCraft core. */
	private ExpCraft core;

	/** The description of this module. */
	private ModuleInfo info;

	/** Abstraction to handle config files. */
	private ConfigProvider config;

	// TODO doc
	protected AbstractModuleTranslator translator;

	/**
	 * Event fired by the core to disable the module. Do not override this
	 * method. Override {@link #onEnable()}.
	 */
	public final void disable() {
		onDisable();
		// forget everything
		core = null;
		config = null;
	}

	public abstract void displayInfo(final Player sender);

	/**
	 * Event fired by the core to enable the module. Do not override this
	 * method. Override {@link #onDisable()}.
	 */
	public final void enable() {
		onEnable();
	}

	/**
	 * Get the persisted configuration of the module enriched by the default
	 * values.
	 * 
	 * @return The configuration properties.
	 */
	public TypedProperties getConfig() {
		return config.getConfig();
	}

	/**
	 * Get the File object pointing to the persisted configuration.
	 * 
	 * @return A file pointing to the config.
	 */
	public File getConfigFile() {
		return config.getConfigFile();
	}

	/**
	 * Return the reference to the ExpCraft core.
	 * 
	 * @return The ExpCraft core.
	 */
	protected ExpCraft getCore() {
		return core;
	}

	/** Get the module description. */
	public ModuleInfo getInfo() {
		return info;
	}

	/**
	 * Get the level cap of Expcraft.
	 * 
	 * @return Level cap, usually 0 < cap <= 100.
	 */
	public int getLevelCap() {
		return core.getLevelCap();
	}

	/**
	 * Get the permission manager to check player permissions.
	 * 
	 * @return An implementation of the permission manager.
	 */
	public AbstractPermissionManager getPermission() {
		return core.getPermissions();
	}

	/**
	 * Get the persistence manager to store level progress.
	 * 
	 * @return An implementation of the persistence manager.
	 */
	public AbstractPersistenceManager getPersistence() {
		return core.getPersistence();
	}

	/**
	 * Get the bukkit server.
	 * 
	 * @return Reference to the server.
	 */
	public Server getServer() {
		return core.getServer();
	}

	public String getTranslatedName(final Locale locale) {
		return translator.translateModuleName(locale);
	}

	/**
	 * Get notified that the module should start monitoring player events.
	 */
	public abstract void onDisable();

	/**
	 * Get notified that the module should no longer monitor player events.
	 */
	public abstract void onEnable();

	/**
	 * Register a bukkit event listener with the server.
	 * 
	 * @param listener
	 *            The listener to register.
	 */
	protected void registerEvents(final Listener listener) {
		core.getServer().getPluginManager().registerEvents(listener, core);
	}

	/** Save the configuration to disk. */
	public void saveConfig() {
		config.saveConfig();
	}

	/**
	 * Link the module to the ExpCraft core.
	 * 
	 * @param core
	 *            Reference to core.
	 */
	public void setCore(final ExpCraft core) {
		this.core = core;
		if (info != null) {
			config = new ConfigProvider(core, info);
		}
	}

	/**
	 * Set the module description.
	 * 
	 * @param info
	 *            The module description.
	 */
	public void setInfo(final ModuleInfo info) {
		this.info = info;
		if (core != null) {
			config = new ConfigProvider(core, info);
		}
	}

	// TODO doc
	public void setTranslator(final AbstractModuleTranslator translator) {
		this.translator = translator;
	}

}
