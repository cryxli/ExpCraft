package li.cryx.expcraft;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.UnknownDependencyException;

public class DummyPluginManager implements PluginManager {

	Set<Plugin> plugins = new HashSet<Plugin>();

	@Override
	public void addPermission(final Permission perm) {
	}

	public void addPlugin(final Plugin plugin) {
		plugins.add(plugin);
	}

	@Override
	public void callEvent(final Event event) {
	}

	@Override
	public void clearPlugins() {
	}

	@Override
	public void disablePlugin(final Plugin plugin) {
	}

	@Override
	public void disablePlugins() {
	}

	@Override
	public void enablePlugin(final Plugin plugin) {
	}

	@Override
	public Set<Permission> getDefaultPermissions(final boolean op) {
		return null;
	}

	@Override
	public Set<Permissible> getDefaultPermSubscriptions(final boolean op) {
		return null;
	}

	@Override
	public Permission getPermission(final String name) {
		return null;
	}

	@Override
	public Set<Permission> getPermissions() {
		return null;
	}

	@Override
	public Set<Permissible> getPermissionSubscriptions(final String permission) {
		return null;
	}

	@Override
	public Plugin getPlugin(final String name) {
		return null;
	}

	@Override
	public Plugin[] getPlugins() {
		Plugin[] p = new Plugin[plugins.size()];
		return plugins.toArray(p);
	}

	@Override
	public boolean isPluginEnabled(final Plugin plugin) {
		return false;
	}

	@Override
	public boolean isPluginEnabled(final String name) {
		return false;
	}

	@Override
	public Plugin loadPlugin(final File file) throws InvalidPluginException,
			InvalidDescriptionException, UnknownDependencyException {
		return null;
	}

	@Override
	public Plugin[] loadPlugins(final File directory) {
		return null;
	}

	@Override
	public void recalculatePermissionDefaults(final Permission perm) {
	}

	@Override
	public void registerEvent(final Type type, final Listener listener,
			final EventExecutor executor, final Priority priority,
			final Plugin plugin) {
	}

	@Override
	public void registerEvent(final Type type, final Listener listener,
			final Priority priority, final Plugin plugin) {
	}

	@Override
	public void registerInterface(final Class<? extends PluginLoader> loader)
			throws IllegalArgumentException {
	}

	@Override
	public void removePermission(final Permission perm) {
	}

	@Override
	public void removePermission(final String name) {
	}

	@Override
	public void subscribeToDefaultPerms(final boolean op,
			final Permissible permissible) {
	}

	@Override
	public void subscribeToPermission(final String permission,
			final Permissible permissible) {
	}

	@Override
	public void unsubscribeFromDefaultPerms(final boolean op,
			final Permissible permissible) {
	}

	@Override
	public void unsubscribeFromPermission(final String permission,
			final Permissible permissible) {
	}
}
