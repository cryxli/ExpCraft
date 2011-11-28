package li.cryx.expcraft.farming;

import java.text.MessageFormat;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import li.cryx.expcraft.perm.AbstractPermissionManager;
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

	/** Reference to parent plugin. */
	public Farming plugin;

	/** A random generator to determine bonus drops. */
	private static Random randomGenerator = new Random();

	/** A factory to send messages to the player. */
	private Chat chat;

	/** Create a new listener for the given plugin. */
	public FarmingBlockListener(final Farming instance) {
		this.plugin = instance;
	}

	private void bonusDropForCrops(final Player player, final int level,
			final Block block) {
		int rnd = randomGenerator.nextInt(100);
		if (rnd >= 0 && rnd < 2
				&& level >= plugin.getConfInt("DropLevel.MelonSeed")) {
			// drop melon seeds
			drop(block, Material.MELON_SEEDS);
		} else if (rnd >= 2 && rnd < 5
				&& level >= plugin.getConfInt("DropLevel.PumpkinSeed")) {
			// drop pumpkin seeds
			drop(block, Material.PUMPKIN_SEEDS);
		} else if (rnd >= 5 && rnd < 10
				&& level >= plugin.getConfInt("DropLevel.CocoaBean")) {
			// drop cocoa bean:
			// drop(block, Material.COCOA_BEAN);
			drop(block, Material.INK_SACK, (short) 3); // 351(3)
		}
	}

	private void bonusDropForNetherWart(final Player player, final int level,
			final Block block) {
		// TODO cryxli: finalise with bukkit for 1.0
		int rnd = randomGenerator.nextInt(100);
		if (rnd >= 0 && rnd < 2
				&& level >= plugin.getConfInt("DropLevel.GhastTear")) {
			// drop(block, Material.GHAST_TEAR);

		} else if (rnd >= 2 && rnd < 4
				&& level >= plugin.getConfInt("DropLevel.BlazePowder")) {
			// drop(block, Material.BLAZE_POWDER);
		}
	}

	private boolean checkTool(final Player player, final Material itemInHand,
			final int level) {
		if (level < plugin.getConfInt("HoeLevel.Wooden")
				&& itemInHand == Material.WOOD_HOE) {
			warnToolLevel(player, plugin.getConfInt("HoeLevel.Wooden"));

		} else if (level < plugin.getConfInt("HoeLevel.Stone")
				&& itemInHand == Material.STONE_HOE) {
			warnToolLevel(player, plugin.getConfInt("HoeLevel.Stone"));

		} else if (level < plugin.getConfInt("HoeLevel.Iron")
				&& itemInHand == Material.IRON_HOE) {
			warnToolLevel(player, plugin.getConfInt("HoeLevel.Iron"));

		} else if (level < plugin.getConfInt("HoeLevel.Gold")
				&& itemInHand == Material.GOLD_HOE) {
			warnToolLevel(player, plugin.getConfInt("HoeLevel.Gold"));

		} else if (level < plugin.getConfInt("HoeLevel.Diamond")
				&& itemInHand == Material.DIAMOND_HOE) {
			warnToolLevel(player, plugin.getConfInt("HoeLevel.Diamond"));

		} else {
			// all tool checks passed
			return true;
		}
		// one tool check failed
		return false;
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

	/**
	 * Test whether the given block represents fully grown, ripe crops.
	 * 
	 * @param block
	 *            A block participating in an event.
	 * @return <code>true</code>, only if the block represents crops in state
	 *         ripe.
	 */
	private boolean isRipeCrops(final Block block) {
		if (block.getType() == Material.CROPS) {
			return ((Crops) block.getState().getData()).getState() == CropState.RIPE;
		} else {
			return false;
		}
	}

	/**
	 * Test whether the given block represents fully grown, ripe nether wart.
	 * 
	 * @param block
	 *            A block participating in an event.
	 * @return <code>true</code>, only if the block represents nether wart in
	 *         state ripe.
	 */
	private boolean isRipeNetherWart(final Block block) {
		// TODO cryxli: implement with bukkit for 1.0
		return false;
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
		int level = plugin.getPersistence().getLevel(plugin, player);

		if (!checkTool(player, itemInHand, level)) {
			event.setCancelled(true);
			return;
		}

		if (m == Material.LEAVES) {
			// TODO pablohess: How to detect this event
			int rnd = randomGenerator.nextInt(100);
			Logger.getLogger("Minecraft").log(Level.INFO, "LEAVES: " + rnd);

			if (rnd == 0 && level >= plugin.getConfInt("UseLevel.GoldenApple")) {
				plugin.getPersistence().addExp(plugin, player,
						plugin.getConfDouble("ExpGain.GoldenApple"));
				drop(block, Material.GOLDEN_APPLE);

			} else if (rnd > 0 && rnd < 10
					&& level >= plugin.getConfInt("UseLevel.Apple")) {
				plugin.getPersistence().addExp(plugin, player,
						plugin.getConfDouble("ExpGain.Apple"));
				drop(block, Material.APPLE);
			}
		}

		double gained = 0;
		if (m == Material.SUGAR_CANE_BLOCK
				&& level >= plugin.getConfInt("UseLevel.SugarCane")) {
			gained = plugin.getConfDouble("ExpGain.SugarCane");

		} else if (isRipeCrops(event.getBlock())
				&& level >= plugin.getConfInt("UseLevel.Harvest")) {
			gained = plugin.getConfDouble("ExpGain.Harvest");
			bonusDropForCrops(player, level, block);

		} else if (isRipeNetherWart(event.getBlock())
				&& level >= plugin.getConfInt("UseLevel.NetherWart")) {
			gained = plugin.getConfDouble("ExpGain.NetherWart");
			bonusDropForNetherWart(player, level, block);

		} else if (m == Material.CACTUS
				&& level >= plugin.getConfInt("UseLevel.Cacti")) {
			gained = plugin.getConfDouble("ExpGain.Cacti");
		}

		plugin.getPersistence().addExp(plugin, player, gained);
	}

	@Override
	public void onBlockPlace(final BlockPlaceEvent event) {
		if (event.isCancelled()
				|| !plugin.getPermission().worldCheck(
						event.getBlock().getWorld())) {
			return;
		}

		Player player = event.getPlayer();
		if (!plugin.getPermission().hasLevel(plugin, player)) {
			return;
		}

		Material m = event.getBlock().getType();
		int level = plugin.getPersistence().getLevel(plugin, player);
		double gained = 0;

		switch (m) {
		case SAPLING:
			if (level >= plugin.getConfInt("UseLevel.Sapling")) {
				gained = plugin.getConfDouble("ExpGain.Sapling");
			}
			break;
		case RED_ROSE:
			if (level >= plugin.getConfInt("UseLevel.RedRose")) {
				gained = plugin.getConfDouble("ExpGain.RedRose");
			}
			break;
		case YELLOW_FLOWER:
			if (level >= plugin.getConfInt("UseLevel.YellowFlower")) {
				gained = plugin.getConfDouble("ExpGain.YellowFlower");
			}
			break;
		case BROWN_MUSHROOM:
		case RED_MUSHROOM:
			if (level >= plugin.getConfInt("UseLevel.Mushroom")) {
				gained = plugin.getConfDouble("ExpGain.Mushroom");
			}
			break;
		default:
			return;
		}
		plugin.getPersistence().addExp(plugin, player, gained);
	}

	public void setChat(final Chat chat) {
		this.chat = chat;
	}

	private void warnToolLevel(final Player player, final int level) {
		chat.warn(player, MessageFormat.format( //
				"Cannot use this tool. Required Level: {0}", level));
	}
}
