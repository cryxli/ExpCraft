package me.torrent.lcfarming;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.samkio.levelcraftcore.LCChat;
import me.samkio.levelcraftcore.LevelFunctions;
import me.samkio.levelcraftcore.Whitelist;

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

public class LCBlockListener extends BlockListener {
	public LCFarming plugin;

	private static Random randomGenerator = new Random();

	public LCBlockListener(LCFarming instance) {
		this.plugin = instance;
	}

	private void bonusDropCrop(Player player, int level, Block block) {
		int rnd = randomGenerator.nextInt(100);
		if (rnd >= 0 && rnd < 2
				&& (level >= this.plugin.LCConfiguration.DropMelonSeedLevel)) {
			// drop melon seeds
			drop(block, Material.MELON_SEEDS);
		} else if (rnd >= 2 && rnd < 5
				&& level >= plugin.LCConfiguration.DropPumpkinSeedLevel) {
			// drop pumpkin seeds
			drop(block, Material.PUMPKIN_SEEDS);
		} else if (rnd >= 5 && rnd < 10
				&& level >= plugin.LCConfiguration.DropCocoaBeanLevel) {
			// TODO drop cocoa bean
			// drop(block, Material.COCOA_BEAN);
			drop(block, Material.INK_SACK, (short) 3); // 351(3)
		}
	}

	private boolean checkTool(Player player, Material itemInHand, int level) {
		if ((level < this.plugin.LCConfiguration.IronHoe)
				&& (itemInHand == Material.IRON_HOE)) {
			LCChat.warn(player, "Cannot use this tool. Required Level:"
					+ this.plugin.LCConfiguration.IronHoe);
			return false;
		}
		if ((level < this.plugin.LCConfiguration.GoldHoe)
				&& (itemInHand == Material.GOLD_HOE)) {
			LCChat.warn(player, "Cannot use this tool. Required Level:"
					+ this.plugin.LCConfiguration.GoldHoe);
			return false;
		}
		if ((level < this.plugin.LCConfiguration.WoodHoe)
				&& (itemInHand == Material.WOOD_HOE)) {
			LCChat.warn(player, "Cannot use this tool. Required Level:"
					+ this.plugin.LCConfiguration.WoodHoe);
			return false;
		}
		if ((level < this.plugin.LCConfiguration.DiamondHoe)
				&& (itemInHand == Material.DIAMOND_HOE)) {
			LCChat.warn(player, "Cannot use this tool. Required Level:"
					+ this.plugin.LCConfiguration.DiamondHoe);
			return false;
		}
		if ((level < this.plugin.LCConfiguration.StoneHoe)
				&& (itemInHand == Material.STONE_HOE)) {
			LCChat.warn(player, "Cannot use this tool. Required Level:"
					+ this.plugin.LCConfiguration.StoneHoe);
			return false;
		}
		return true;
	}

	private void drop(Block block, int itemType) {
		Location locy = new Location(block.getWorld(), block.getX(),
				block.getY(), block.getZ(), 0, 0);
		block.getWorld().dropItem(locy, new ItemStack(itemType, 1));
	}

	private void drop(Block block, Material material) {
		Location locy = new Location(block.getWorld(), block.getX(),
				block.getY(), block.getZ(), 0, 0);
		block.getWorld().dropItem(locy, new ItemStack(material.getId(), 1));
	}

	private void drop(Block block, Material material, short damage) {
		Location locy = new Location(block.getWorld(), block.getX(),
				block.getY(), block.getZ(), 0, 0);
		block.getWorld().dropItem(locy,
				new ItemStack(material.getId(), 1, damage));

	}

	@Override
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.isCancelled()) {
			return;
		}
		// System.out.println(event.getBlock().getType());

		if (!Whitelist.worldCheck(event.getBlock().getWorld()))
			return;
		// System.out.println(" world check");
		Player player = event.getPlayer();

		if (!Whitelist.hasLevel(player, this.plugin.thisPlug))
			return;
		// System.out.println(" level check");
		Material itemInHand = player.getItemInHand().getType();
		Block block = event.getBlock();
		Material m = block.getType();
		int level = LevelFunctions.getLevel(player, this.plugin.thisPlug);

		if (!checkTool(player, itemInHand, level)) {
			event.setCancelled(true);
			return;
		}

		if (m == Material.LEAVES) {
			int random1 = randomGenerator.nextInt(100);
			Logger.getLogger("Minecraft").log(Level.INFO, "LEAVES: " + random1);
			if ((random1 == 0)
					&& (level >= this.plugin.LCConfiguration.GoldenAppleLevel)) {
				LevelFunctions.addExp(player, this.plugin.thisPlug,
						this.plugin.LCConfiguration.ExpPerGoldenApple);
				drop(block, 322); // Material.GOLDEN_APPLE);
			} else if ((random1 > 0) && (random1 < 10)
					&& (level >= this.plugin.LCConfiguration.AppleLevel)) {
				LevelFunctions.addExp(player, this.plugin.thisPlug,
						this.plugin.LCConfiguration.ExpPerApple);
				drop(block, Material.APPLE);
			}
			return;
		}

		double gained = 0;
		if (m == Material.SUGAR_CANE_BLOCK
				&& level >= this.plugin.LCConfiguration.SugarCaneLevel) {
			gained = this.plugin.LCConfiguration.ExpPerSugarCane;
		}

		if (m == Material.CROPS
				&& ((Crops) event.getBlock().getState().getData()).getState() == CropState.RIPE
				&& level >= this.plugin.LCConfiguration.HarvestLevel) {
			gained = this.plugin.LCConfiguration.ExpPerHarvest;
			bonusDropCrop(player, level, block);
		}

		if (m == Material.CACTUS
				&& level >= this.plugin.LCConfiguration.CactusLevel) {
			gained = this.plugin.LCConfiguration.ExpPerCactus;
		}

		if (gained > 0) {
			LevelFunctions.addExp(player, this.plugin.thisPlug, gained);
		}
	}

	@Override
	public void onBlockPlace(BlockPlaceEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (!Whitelist.worldCheck(event.getBlock().getWorld())) {
			return;
		}
		Player player = event.getPlayer();

		if (!Whitelist.hasLevel(player, this.plugin.thisPlug)) {
			return;
		}
		Material m = event.getBlock().getType();
		int level = LevelFunctions.getLevel(player, this.plugin.thisPlug);

		double gained = 0.0;

		switch (m) {
		case SAPLING:
			if (level >= this.plugin.LCConfiguration.SaplingLevel) {
				gained = this.plugin.LCConfiguration.ExpPerSapling;
			}
			break;
		case CACTUS:
			if (level >= this.plugin.LCConfiguration.CactusLevel) {
				gained = this.plugin.LCConfiguration.ExpPerCactus;
			}
			break;
		case RED_ROSE:
			if (level >= this.plugin.LCConfiguration.RedRoseLevel) {
				gained = this.plugin.LCConfiguration.ExpPerRedRose;
			}
			break;
		case YELLOW_FLOWER:
			if (level >= this.plugin.LCConfiguration.YellowFlowerLevel) {
				gained = this.plugin.LCConfiguration.ExpPerYellowFlower;
			}
			break;
		case BROWN_MUSHROOM:
			if (level >= this.plugin.LCConfiguration.MushroomLevel) {
				gained = this.plugin.LCConfiguration.ExpPerMushroom;
			}
			break;
		case RED_MUSHROOM:
			if (level >= this.plugin.LCConfiguration.MushroomLevel) {
				gained = this.plugin.LCConfiguration.ExpPerMushroom;
			}
			break;
		default:
			return;
		}
		LevelFunctions.addExp(player, this.plugin.thisPlug, gained);
	}
}
