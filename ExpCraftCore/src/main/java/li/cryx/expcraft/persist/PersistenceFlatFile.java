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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import li.cryx.expcraft.IExpCraft;
import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.util.TypedProperties;

import org.bukkit.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements a storage using flat files, YALM actually. Each module
 * will have its own file that will be stored in the <code>ExpCraft/data/</code>
 * folder.
 * 
 * @author cryxli
 */
public class PersistenceFlatFile extends AbstractPersistenceManager {

	private static final Logger LOG = LoggerFactory.getLogger("ExpCraftCore");

	/** How often to save files [ms]. Defaults to every 10s. */
	private static final long SAVE_INTERVAL = 10 * 1000;

	private boolean running = false;

	/**
	 * Keep the files in memory for faster access. But write them regularly to
	 * disk.
	 */
	private final Map<ExpCraftModule, TypedProperties> cache = new HashMap<ExpCraftModule, TypedProperties>();
	/** Keep track whether files have changed. */
	private final Map<ExpCraftModule, Boolean> dirty = new HashMap<ExpCraftModule, Boolean>();

	/** Folder containing the module files. */
	private File dataFolder;

	@Override
	public void flush() {
		running = false;
		saveFiles();
	}

	@Override
	public double getExp(final ExpCraftModule module, final Player player) {
		TypedProperties data = getModuleData(module);
		String playerName = player.getName().toLowerCase();
		return data.getDouble(playerName, 0);
	}

	private synchronized TypedProperties getModuleData(
			final ExpCraftModule module) {
		TypedProperties data = cache.get(module);
		if (data == null) {
			data = new TypedProperties();
			File file = new File(dataFolder, module.getInfo().getName()
					+ ".properties");
			if (file.exists()) {
				try {
					FileInputStream fis = new FileInputStream(file);
					data.load(fis);
					fis.close();
				} catch (IOException e) {
					LOG.error("Unable to load module data for "
							+ module.getInfo().getName(), e);
				}
			}
			dirty.put(module, false);
			cache.put(module, data);
		}
		return data;
	}

	private synchronized void saveFiles() {
		for (ExpCraftModule module : cache.keySet()) {
			TypedProperties data = getModuleData(module);
			if (dirty.get(module)) {
				try {
					FileOutputStream fos = new FileOutputStream(new File(
							dataFolder, module.getInfo().getName()
									+ ".properties"));
					data.store(fos, null);
					fos.close();
					dirty.put(module, false);
				} catch (IOException e) {
					LOG.error("Unable to persist level info", e);
				}
			}
		}
	}

	@Override
	public synchronized void setCore(final IExpCraft core) {
		super.setCore(core);
		dataFolder = new File(core.getDataFolder(), "data");
		dataFolder.mkdirs();

		// start storing interval
		Thread intervalStorage = new Thread() {
			@Override
			public void run() {
				running = true;
				while (running) {
					saveFiles();
					try {
						Thread.sleep(SAVE_INTERVAL);
					} catch (InterruptedException e) {
					}
				}
			}
		};
		intervalStorage.setDaemon(true);
		intervalStorage.start();
	}

	@Override
	public void setExp(final ExpCraftModule module, final Player player,
			final double exp) {
		TypedProperties data = getModuleData(module);
		String playerName = player.getName().toLowerCase();
		dirty.put(module, true);
		data.setDouble(playerName, exp);
	}

}
