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

public class PersistenceFlatFile extends AbstractPersistenceManager {

	private static final Logger LOG = Logger.getLogger("ExpCraftCore");

	private static final long SAVE_INTERVAL = 10 * 1000;

	private final Map<ExpCraftModule, YamlConfiguration> cache = new HashMap<ExpCraftModule, YamlConfiguration>();
	private final Map<ExpCraftModule, Boolean> dirty = new HashMap<ExpCraftModule, Boolean>();

	private File dataFolder;

	@Override
	public synchronized void flush() {
		for (ExpCraftModule module : cache.keySet()) {
			YamlConfiguration data = getModuleData(module);
			if (dirty.get(module)) {
				try {
					data.save(new File(getDataFolder(), module.getName()
							+ ".yml"));
					dirty.put(module, false);
				} catch (IOException e) {
					LOG.log(Level.SEVERE, "[EC] Unable to persist level info",
							e);
				}
			}
		}
	}

	private File getDataFolder() {
		return dataFolder;
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
					module.getName() + ".yml"));
			dirty.put(module, false);
			cache.put(module, data);
		}
		return data;
	}

	@Override
	public void setCore(final ExpCraftCore core) {
		super.setCore(core);
		dataFolder = new File(core.getDataFolder(), "../ExpCraft/data");

		Thread intervalStorage = new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(SAVE_INTERVAL);
					} catch (InterruptedException e) {
					}
					flush();
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
