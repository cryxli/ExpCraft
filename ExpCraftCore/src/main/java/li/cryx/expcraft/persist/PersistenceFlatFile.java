package li.cryx.expcraft.persist;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import li.cryx.expcraft.ExpCraftCore;
import li.cryx.expcraft.module.ExpCraftModule;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 * This class implements a storage using flat files, YALM actually. Each module
 * will have its own file that will be stored in the <code>ExpCraft/data/</code>
 * folder.
 * 
 * @author cryxli
 */
public class PersistenceFlatFile extends AbstractPersistenceManager {

	private static final Logger LOG = Logger.getLogger("ExpCraftCore");

	/** How often to save files [ms]. Defaults to every 10s. */
	private static final long SAVE_INTERVAL = 10 * 1000;

	private boolean running = false;

	/**
	 * Keep the files in memory for faster access. But write them regularly to
	 * disk.
	 */
	private final Map<ExpCraftModule, YamlConfiguration> cache = new HashMap<ExpCraftModule, YamlConfiguration>();
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
		YamlConfiguration data = getModuleData(module);
		String playerName = player.getName().toLowerCase();
		return data.getDouble(playerName, 0);
	}

	private synchronized YamlConfiguration getModuleData(
			final ExpCraftModule module) {
		YamlConfiguration data = cache.get(module);
		if (data == null) {
			data = YamlConfiguration.loadConfiguration(new File(dataFolder,
					module.getModuleName() + ".yml"));
			dirty.put(module, false);
			cache.put(module, data);
		}
		return data;
	}

	private synchronized void saveFiles() {
		for (ExpCraftModule module : cache.keySet()) {
			YamlConfiguration data = getModuleData(module);
			if (dirty.get(module)) {
				try {
					data.save(new File(dataFolder, module.getModuleName()
							+ ".yml"));
					dirty.put(module, false);
				} catch (IOException e) {
					LOG.log(Level.SEVERE, "[EC] Unable to persist level info",
							e);
				}
			}
		}
	}

	@Override
	public synchronized void setCore(final ExpCraftCore core) {
		super.setCore(core);
		dataFolder = new File(core.getDataFolder(), "../ExpCraft/data");

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
		YamlConfiguration data = getModuleData(module);
		String playerName = player.getName().toLowerCase();
		dirty.put(module, true);
		data.set(playerName, exp);
	}

}
