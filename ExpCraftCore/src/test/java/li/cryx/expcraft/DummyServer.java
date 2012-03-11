package li.cryx.expcraft;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.help.HelpMap;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.map.MapView;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.scheduler.BukkitScheduler;

import com.avaje.ebean.config.ServerConfig;

public class DummyServer implements Server {

	private final Set<Player> onlinePlayers = new HashSet<Player>();

	private PluginManager pluginManager;

	public void addPlayer(final Player player) {
		onlinePlayers.add(player);
	}

	@Override
	public boolean addRecipe(final Recipe recipe) {
		return false;
	}

	@Override
	public void banIP(final String address) {
	}

	@Override
	public int broadcast(final String message, final String permission) {
		return 0;
	}

	@Override
	public int broadcastMessage(final String message) {
		return 0;
	}

	public void clearPlayers() {
		onlinePlayers.clear();
	}

	@Override
	public void clearRecipes() {
	}

	@Override
	public void configureDbConfig(final ServerConfig config) {

	}

	@Override
	public Inventory createInventory(final InventoryHolder owner, final int size) {
		return null;
	}

	@Override
	public Inventory createInventory(final InventoryHolder owner,
			final int size, final String title) {
		return null;
	}

	@Override
	public Inventory createInventory(final InventoryHolder owner,
			final InventoryType type) {
		return null;
	}

	@Override
	public MapView createMap(final World world) {
		return null;
	}

	@Override
	public World createWorld(final WorldCreator creator) {
		return null;
	}

	@Override
	public boolean dispatchCommand(final CommandSender sender,
			final String commandLine) throws CommandException {
		return false;
	}

	@Override
	public boolean getAllowEnd() {
		return false;
	}

	@Override
	public boolean getAllowFlight() {
		return false;
	}

	@Override
	public boolean getAllowNether() {
		return false;
	}

	@Override
	public Set<OfflinePlayer> getBannedPlayers() {
		return null;
	}

	@Override
	public String getBukkitVersion() {
		return null;
	}

	@Override
	public Map<String, String[]> getCommandAliases() {
		return null;
	}

	@Override
	public ConsoleCommandSender getConsoleSender() {
		return null;
	}

	@Override
	public GameMode getDefaultGameMode() {
		return null;
	}

	@Override
	public boolean getGenerateStructures() {
		return false;
	}

	@Override
	public HelpMap getHelpMap() {
		return null;
	}

	@Override
	public String getIp() {
		return null;
	}

	@Override
	public Set<String> getIPBans() {
		return null;
	}

	@Override
	public Set<String> getListeningPluginChannels() {
		return null;
	}

	@Override
	public Logger getLogger() {
		return null;
	}

	@Override
	public MapView getMap(final short id) {
		return null;
	}

	@Override
	public int getMaxPlayers() {
		return 0;
	}

	@Override
	public Messenger getMessenger() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public OfflinePlayer getOfflinePlayer(final String name) {
		return null;
	}

	@Override
	public OfflinePlayer[] getOfflinePlayers() {
		return null;
	}

	@Override
	public boolean getOnlineMode() {
		return false;
	}

	@Override
	public Player[] getOnlinePlayers() {
		Player[] players = new Player[onlinePlayers.size()];
		return onlinePlayers.toArray(players);
	}

	@Override
	public Set<OfflinePlayer> getOperators() {
		return null;
	}

	@Override
	public Player getPlayer(final String name) {
		for (Player player : onlinePlayers) {
			if (name.equals(player.getName())) {
				return player;
			}
		}
		return null;
	}

	@Override
	public Player getPlayerExact(final String name) {
		return null;
	}

	@Override
	public PluginCommand getPluginCommand(final String name) {
		return null;
	}

	@Override
	public PluginManager getPluginManager() {
		return pluginManager;
	}

	@Override
	public int getPort() {
		return 0;
	}

	@Override
	public List<Recipe> getRecipesFor(final ItemStack result) {
		return null;
	}

	@Override
	public BukkitScheduler getScheduler() {
		return null;
	}

	@Override
	public String getServerId() {
		return null;
	}

	@Override
	public String getServerName() {
		return null;
	}

	@Override
	public ServicesManager getServicesManager() {
		return null;
	}

	@Override
	public int getSpawnRadius() {
		return 0;
	}

	@Override
	public int getTicksPerAnimalSpawns() {
		return 0;
	}

	@Override
	public int getTicksPerMonsterSpawns() {
		return 0;
	}

	@Override
	public String getUpdateFolder() {
		return null;
	}

	@Override
	public File getUpdateFolderFile() {
		return null;
	}

	@Override
	public String getVersion() {
		return null;
	}

	@Override
	public int getViewDistance() {
		return 0;
	}

	@Override
	public Set<OfflinePlayer> getWhitelistedPlayers() {
		return null;
	}

	@Override
	public World getWorld(final String name) {
		return null;
	}

	@Override
	public World getWorld(final UUID uid) {
		return null;
	}

	@Override
	public File getWorldContainer() {
		return null;
	}

	@Override
	public List<World> getWorlds() {
		return new ArrayList<World>();
	}

	@Override
	public String getWorldType() {
		return "DEFAULT";
	}

	@Override
	public boolean hasWhitelist() {
		return false;
	}

	@Override
	public List<Player> matchPlayer(final String name) {
		return null;
	}

	@Override
	public Iterator<Recipe> recipeIterator() {
		return null;
	}

	@Override
	public void reload() {

	}

	@Override
	public void reloadWhitelist() {
	}

	@Override
	public void resetRecipes() {
	}

	@Override
	public void savePlayers() {

	}

	@Override
	public void sendPluginMessage(final Plugin source, final String channel,
			final byte[] message) {
	}

	@Override
	public void setDefaultGameMode(final GameMode mode) {
	}

	public void setPluginManager(final PluginManager pluginManager) {
		this.pluginManager = pluginManager;
	}

	@Override
	public void setSpawnRadius(final int value) {
	}

	@Override
	public void setWhitelist(final boolean value) {
	}

	@Override
	public void shutdown() {
	}

	@Override
	public void unbanIP(final String address) {
	}

	@Override
	public boolean unloadWorld(final String name, final boolean save) {
		return false;
	}

	@Override
	public boolean unloadWorld(final World world, final boolean save) {
		return false;
	}

	@Override
	public boolean useExactLoginLocation() {
		return false;
	}
}
