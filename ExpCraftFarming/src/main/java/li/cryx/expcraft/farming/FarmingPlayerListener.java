package li.cryx.expcraft.farming;

import java.text.MessageFormat;

import li.cryx.expcraft.util.Chat;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

public class FarmingPlayerListener extends PlayerListener {

	public static boolean isHoe(final Material m) {
		return m == Material.WOOD_HOE //
				|| m == Material.STONE_HOE //
				|| m == Material.IRON_HOE //
				|| m == Material.GOLD_HOE //
				|| m == Material.DIAMOND_HOE;
	}

	private Config config;

	private final Farming plugin;

	private Chat chat;

	public FarmingPlayerListener(final Farming instance) {
		this.plugin = instance;
	}

	public void onBlockPlace(final BlockPlaceEvent event) {
		if (event.isCancelled()) {
			return;
		}
	}

	@Override
	public void onPlayerInteract(final PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}

		Player player = event.getPlayer();
		if (!plugin.getPermission().hasLevel(plugin, player)) {
			return;
		}
		Material itemInHand = player.getItemInHand().getType();
		Material m = event.getClickedBlock().getType();
		int level = plugin.getPersistence().getLevel(plugin, player);

		// ensure player can use hoe
		if (level < config.TOOL_LEVEL.wood && itemInHand == Material.WOOD_HOE) {
			warnToolLevel(player, config.TOOL_LEVEL.wood);
			event.setCancelled(true);
			return;
		}
		if (level < config.TOOL_LEVEL.iron && itemInHand == Material.IRON_HOE) {
			warnToolLevel(player, config.TOOL_LEVEL.iron);
			event.setCancelled(true);
			return;
		}
		if (level < config.TOOL_LEVEL.gold && itemInHand == Material.GOLD_HOE) {
			warnToolLevel(player, config.TOOL_LEVEL.gold);
			event.setCancelled(true);
			return;
		}
		if (level < config.TOOL_LEVEL.stone && itemInHand == Material.STONE_HOE) {
			warnToolLevel(player, config.TOOL_LEVEL.stone);
			event.setCancelled(true);
			return;
		}
		if (level < config.TOOL_LEVEL.diamond
				&& itemInHand == Material.DIAMOND_HOE) {
			warnToolLevel(player, config.TOOL_LEVEL.diamond);
			event.setCancelled(true);
			return;
		}

		// ensure player can till
		if (level < config.LEVEL.till
				&& (m == Material.GRASS || m == Material.DIRT)
				&& isHoe(itemInHand)) {
			warnCutBlockLevel(player, config.LEVEL.till);
			event.setCancelled(true);
			return;
		}

		if (isHoe(itemInHand) && (m == Material.GRASS || m == Material.DIRT)) {
			// till grass or dirt
			plugin.getPersistence().addExp(plugin, player, config.EXP.till);
		}
	}

	public void setChat(final Chat chat) {
		this.chat = chat;
	}

	public void setConfig(final Config config) {
		this.config = config;
	}

	private void warnCutBlockLevel(final Player player, final int reqLevel) {
		chat.warn(player, MessageFormat.format( //
				"Cannot cut this block. Required Level: {0}", reqLevel));
	}

	private void warnToolLevel(final Player player, final int reqLevel) {
		chat.warn(player, MessageFormat.format( //
				"Cannot use this tool. Required Level: {0}", reqLevel));
	}

}