package li.cryx.expcraft.mining;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;

public class MiningBlockListener extends BlockListener {

	private final Mining plugin;

	public MiningBlockListener(final Mining plugin) {
		this.plugin = plugin;
	}

	private boolean checkPickaxe(final Player player,
			final Material itemInHand, final int level) {
		if (itemInHand == Material.WOOD_PICKAXE
				&& level < plugin.getConfInt("PickaxeLevel.Wooden")) {
			plugin.warnToolUse(player, plugin.getConfInt("PickaxeLevel.Wooden"));

		} else if (itemInHand == Material.STONE_PICKAXE
				&& level < plugin.getConfInt("PickaxeLevel.Stone")) {
			plugin.warnToolUse(player, plugin.getConfInt("PickaxeLevel.Stone"));

		} else if (itemInHand == Material.IRON_PICKAXE
				&& level < plugin.getConfInt("PickaxeLevel.Iron")) {
			plugin.warnToolUse(player, plugin.getConfInt("PickaxeLevel.Iron"));

		} else if (itemInHand == Material.GOLD_PICKAXE
				&& level < plugin.getConfInt("PickaxeLevel.Gold")) {
			plugin.warnToolUse(player, plugin.getConfInt("PickaxeLevel.Gold"));

		} else if (itemInHand == Material.DIAMOND_PICKAXE
				&& level < plugin.getConfInt("PickaxeLevel.Diamond")) {
			plugin.warnToolUse(player,
					plugin.getConfInt("PickaxeLevel.Diamond"));

		} else {
			return true;
		}
		return false;
	}

	private boolean isMinable(final Player player, final Material material,
			final int level) {
		if (material == Material.STONE
				&& level < plugin.getConfInt("UseLevel.Stone")) {
			plugin.warnBlockMine(player, plugin.getConfInt("UseLevel.Stone"));
		} else if (material == Material.COBBLESTONE
				&& level < plugin.getConfInt("UseLevel.Cobble")) {
			plugin.warnBlockMine(player, plugin.getConfInt("UseLevel.Cobble"));
		} else if (material == Material.MOSSY_COBBLESTONE
				&& level < plugin.getConfInt("UseLevel.MossStone")) {
			plugin.warnBlockMine(player,
					plugin.getConfInt("UseLevel.MossStone"));
		} else if (material == Material.COAL_ORE
				&& level < plugin.getConfInt("UseLevel.CoalOre")) {
			plugin.warnBlockMine(player, plugin.getConfInt("UseLevel.CoalOre"));
		} else if (material == Material.IRON_ORE
				&& level < plugin.getConfInt("UseLevel.IronOre")) {
			plugin.warnBlockMine(player, plugin.getConfInt("UseLevel.IronOre"));
		} else if (material == Material.SANDSTONE
				&& level < plugin.getConfInt("UseLevel.SandStone")) {
			plugin.warnBlockMine(player,
					plugin.getConfInt("UseLevel.SandStone"));
		} else if (material == Material.GOLD_ORE
				&& level < plugin.getConfInt("UseLevel.GoldOre")) {
			plugin.warnBlockMine(player, plugin.getConfInt("UseLevel.GoldOre"));
		} else if (material == Material.LAPIS_ORE
				&& level < plugin.getConfInt("UseLevel.LapisOre")) {
			plugin.warnBlockMine(player, plugin.getConfInt("UseLevel.LapisOre"));
		} else if (material == Material.REDSTONE_ORE
				&& level < plugin.getConfInt("UseLevel.Redstone")) {
			plugin.warnBlockMine(player, plugin.getConfInt("UseLevel.Redstone"));
		} else if (material == Material.DIAMOND_ORE
				&& level < plugin.getConfInt("UseLevel.DiamondOre")) {
			plugin.warnBlockMine(player,
					plugin.getConfInt("UseLevel.DiamondOre"));
		} else if (material == Material.OBSIDIAN
				&& level < plugin.getConfInt("UseLevel.Obsidian")) {
			plugin.warnBlockMine(player, plugin.getConfInt("UseLevel.Obsidian"));
		} else if (material == Material.NETHERRACK
				&& level < plugin.getConfInt("UseLevel.Netherrack")) {
			plugin.warnBlockMine(player,
					plugin.getConfInt("UseLevel.Netherrack"));
		} else {
			return true;
		}
		return false;
		// material==Material.NetherRackBrick
	}

	@Override
	public void onBlockBreak(final BlockBreakEvent event) {
		if (event.isCancelled()
				|| !plugin.getPermission().worldCheck(
						event.getBlock().getWorld())) {
			return;
		}

		Player player = event.getPlayer();
		if (!plugin.getPermission().hasLevel(plugin, player)) {
			return;
		}

		Material itemInHand = player.getItemInHand().getType();
		int level = plugin.getPersistence().getLevel(plugin, player);
		if (!checkPickaxe(player, itemInHand, level)) {
			event.setCancelled(true);
			return;
		}

		Material m = event.getBlock().getType();
		if (!isMinable(player, m, level)) {
			event.setCancelled(true);
			return;
		}

		double expGain;
		switch (m) {
		default:
			expGain = 0;
			break;
		case STONE:
			expGain = plugin.getConfDouble("ExpGain.Stone");
			break;
		case COBBLESTONE:
			expGain = plugin.getConfDouble("ExpGain.Cobble");
			break;
		case REDSTONE_ORE:
			expGain = plugin.getConfDouble("ExpGain.Redstone");
			break;
		case GOLD_ORE:
			expGain = plugin.getConfDouble("ExpGain.GoldOre");
			break;
		case IRON_ORE:
			expGain = plugin.getConfDouble("ExpGain.IronOre");
			break;
		case COAL_ORE:
			expGain = plugin.getConfDouble("ExpGain.CoalOre");
			break;
		case LAPIS_ORE:
			expGain = plugin.getConfDouble("ExpGain.LapisOre");
			break;
		case MOSSY_COBBLESTONE:
			expGain = plugin.getConfDouble("ExpGain.MossStone");
			break;
		case OBSIDIAN:
			expGain = plugin.getConfDouble("ExpGain.Obsidian");
			break;
		case DIAMOND_ORE:
			expGain = plugin.getConfDouble("ExpGain.DiamondOre");
			break;
		case NETHERRACK:
			expGain = plugin.getConfDouble("ExpGain.Netherrack");
			break;
		case SANDSTONE:
			expGain = plugin.getConfDouble("ExpGain.SandStone");
			break;
		}
		plugin.getPersistence().addExp(plugin, player, expGain);

		// double drops
		plugin.dropItem(event.getBlock(), level);
	}
}
