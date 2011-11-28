package li.cryx.expcraft.farming;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import li.cryx.expcraft.perm.AbstractPermissionManager;
import li.cryx.expcraft.persist.AbstractPersistenceManager;
import li.cryx.expcraft.util.Chat;

import org.bukkit.CropState;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Crops;

public class FarmingBlockListener extends BlockListener {

	public Farming plugin;

	private static Random randomGenerator = new Random();

	private Config config;

	private Chat chat;

	public FarmingBlockListener(final Farming instance) {
		this.plugin = instance;
	}

	private void bonusDropCrop(final Player player, final int level,
			final Block block) {
		int rnd = randomGenerator.nextInt(100);
		if (rnd >= 0 && rnd < 2 && (level >= config.DROP_LEVEL.melonSeed)) {
			// drop melon seeds
			drop(block, Material.MELON_SEEDS);
		} else if (rnd >= 2 && rnd < 5
				&& level >= config.DROP_LEVEL.pumpkinSeed) {
			// drop pumpkin seeds
			drop(block, Material.PUMPKIN_SEEDS);
		} else if (rnd >= 5 && rnd < 10 && level >= config.DROP_LEVEL.cocoaBean) {
			// drop cocoa bean:
			// drop(block, Material.COCOA_BEAN);
			drop(block, Material.INK_SACK, (short) 3); // 351(3)
		}
	}

	private boolean checkTool(final Player player, final Material itemInHand,
			final int level) {
		if ((level < config.TOOL_LEVEL.wood)
				&& (itemInHand == Material.WOOD_HOE)) {
			chat.warn(player, "Cannot use this tool. Required Level:"
					+ config.TOOL_LEVEL.wood);
			return false;
		}
		if ((level < config.TOOL_LEVEL.stone)
				&& (itemInHand == Material.STONE_HOE)) {
			chat.warn(player, "Cannot use this tool. Required Level:"
					+ config.TOOL_LEVEL.stone);
			return false;
		}
		if ((level < config.TOOL_LEVEL.iron)
				&& (itemInHand == Material.IRON_HOE)) {
			chat.warn(player, "Cannot use this tool. Required Level:"
					+ config.TOOL_LEVEL.iron);
			return false;
		}
		if ((level < config.TOOL_LEVEL.gold)
				&& (itemInHand == Material.GOLD_HOE)) {
			chat.warn(player, "Cannot use this tool. Required Level:"
					+ config.TOOL_LEVEL.gold);
			return false;
		}
		if ((level < config.TOOL_LEVEL.diamond)
				&& (itemInHand == Material.DIAMOND_HOE)) {
			chat.warn(player, "Cannot use this tool. Required Level:"
					+ config.TOOL_LEVEL.diamond);
			return false;
		}
		return true;
	}

	private void drop(final Block block, final Material material) {
		Location locy = new Location(block.getWorld(), block.getX(),
				block.getY(), block.getZ(), 0, 0);
		block.getWorld().dropItem(locy, new ItemStack(material.getId(), 1));
	}

	private void drop(final Block block, final Material material,
			final short damage) {
		Location locy = new Location(block.getWorld(), block.getX(),
				block.getY(), block.getZ(), 0, 0);
		block.getWorld().dropItem(locy,
				new ItemStack(material.getId(), 1, damage));
	}

	@Override
	public void onBlockBreak(final BlockBreakEvent event) {
		AbstractPermissionManager perm = plugin.getPermission();
		if (event.isCancelled()
				|| !perm.worldCheck(event.getBlock().getWorld())) {
			return;
		}
		// System.out.println(" world check");
		Player player = event.getPlayer();

		if (!perm.hasLevel(plugin, player)) {
			return;
		}
		// System.out.println(" level check");
		Material itemInHand = player.getItemInHand().getType();
		Block block = event.getBlock();
		Material m = block.getType();
		AbstractPersistenceManager pers = plugin.getPersistence();
		int level = pers.getLevel(plugin, player);

		if (!checkTool(player, itemInHand, level)) {
			event.setCancelled(true);
			return;
		}

		if (m == Material.LEAVES) {
			// TODO pablohess: How to detect this event
			int random1 = randomGenerator.nextInt(100);
			Logger.getLogger("Minecraft").log(Level.INFO, "LEAVES: " + random1);
			if ((random1 == 0) && (level >= config.LEVEL.goldenApple)) {
				pers.addExp(plugin, player, config.EXP.goldenApple);
				drop(block, Material.GOLDEN_APPLE);
			} else if ((random1 > 0) && (random1 < 10)
					&& (level >= config.LEVEL.apple)) {
				pers.addExp(plugin, player, config.EXP.apple);
				drop(block, Material.APPLE);
			}
			return;
		}

		double gained = 0;
		if (m == Material.SUGAR_CANE_BLOCK && level >= config.LEVEL.sugarCane) {
			gained = config.EXP.sugarCane;
		}

		Logger.getLogger("ExpCraftFarming").info("Material: " + m);
		if (m == Material.CROPS
				&& ((Crops) event.getBlock().getState().getData()).getState() == CropState.RIPE
				&& level >= config.LEVEL.harvest) {
			Logger.getLogger("ExpCraftFarming").info(
					"State: "
							+ ((Crops) event.getBlock().getState().getData())
									.getState());
			gained = config.EXP.harvest;
			bonusDropCrop(player, level, block);
		}

		if (m == Material.CACTUS && level >= config.LEVEL.cactus) {
			gained = config.EXP.cactus;
		}

		pers.addExp(plugin, player, gained);
	}

	@Override
	public void onBlockPlace(final BlockPlaceEvent event) {
		AbstractPermissionManager perm = plugin.getPermission();
		if (event.isCancelled()
				|| !perm.worldCheck(event.getBlock().getWorld())) {
			return;
		}

		Player player = event.getPlayer();
		if (!perm.hasLevel(plugin, player)) {
			return;
		}

		Material m = event.getBlock().getType();
		AbstractPersistenceManager pers = plugin.getPersistence();
		int level = pers.getLevel(plugin, player);
		double gained = 0;

		switch (m) {
		case SAPLING:
			if (level >= config.LEVEL.sapling) {
				gained = config.EXP.sapling;
			}
			break;
		case CACTUS:
			if (level >= config.LEVEL.cactus) {
				gained = config.EXP.cactus;
			}
			break;
		case RED_ROSE:
			if (level >= config.LEVEL.redRose) {
				gained = config.EXP.redRose;
			}
			break;
		case YELLOW_FLOWER:
			if (level >= config.LEVEL.yellowFlower) {
				gained = config.EXP.yellowFlower;
			}
			break;
		case BROWN_MUSHROOM:
		case RED_MUSHROOM:
			if (level >= config.LEVEL.mushroom) {
				gained = config.EXP.mushroom;
			}
			break;
		default:
			return;
		}
		pers.addExp(plugin, player, gained);
	}

	public void setChat(final Chat chat) {
		this.chat = chat;
	}

	public void setConfig(final Config config) {
		this.config = config;
	}

}
