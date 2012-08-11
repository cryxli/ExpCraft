package li.cryx.expcraft.mining;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Decision logic used by {@link MiningBlockListener}.
 * 
 * @author cryxli
 */
public class MiningConstraints {

	private final Mining plugin;

	public MiningConstraints(final Mining plugin) {
		this.plugin = plugin;
	}

	private boolean checkAndWarnBlock(final Player player, final int level,
			final String confKey) {
		if (level < plugin.getConfInt(confKey)) {
			plugin.warnBlockMine(player, plugin.getConfInt(confKey));
			return false;
		} else {
			return true;
		}
	}

	private boolean checkAndWarnTool(final Player player, final int level,
			final String confKey) {
		if (level < plugin.getConfInt(confKey)) {
			plugin.warnToolUse(player, plugin.getConfInt(confKey));
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Checks that the item is hand is usable. Will warn player when not.
	 * 
	 * @param player
	 *            Current player.
	 * @param itemInHand
	 *            Item in hand.
	 * @param level
	 *            Player's level in mining.
	 * @return <code>true</code>, if player can use the item in hand.
	 */
	public boolean checkPickaxe(final Player player, final Material itemInHand,
			final int level) {
		if (itemInHand == Material.WOOD_PICKAXE) {
			return checkAndWarnTool(player, level, "PickaxeLevel.Wooden");
		} else if (itemInHand == Material.STONE_PICKAXE) {
			return checkAndWarnTool(player, level, "PickaxeLevel.Stone");
		} else if (itemInHand == Material.IRON_PICKAXE) {
			return checkAndWarnTool(player, level, "PickaxeLevel.Iron");
		} else if (itemInHand == Material.GOLD_PICKAXE) {
			return checkAndWarnTool(player, level, "PickaxeLevel.Gold");
		} else if (itemInHand == Material.DIAMOND_PICKAXE) {
			return checkAndWarnTool(player, level, "PickaxeLevel.Diamond");
		} else {
			return true;
		}
	}

	/**
	 * Check and produce drops for the fire version of the golden pickaxe.
	 * 
	 * @param player
	 *            Current player. Must have a golden pickaxe in hand.
	 * @param level
	 *            Player's level. Must be above
	 *            <code>Settings.FirePickaxeLevel</code> for golden pickaxes to
	 *            act as fire pickaxes.
	 * @param event
	 *            Mining event
	 * @return <code>true</code>, if a special drop was produced.
	 */
	public boolean firePickaxe(final Player player, final int level,
			final BlockBreakEvent event) {
		if (level < plugin.getConfInt("Settings.FirePickaxeLevel")
				|| player.getItemInHand().getType() != Material.GOLD_PICKAXE) {
			// requirements not met
			return false;
		}

		Block block = event.getBlock();
		ItemStack drop;
		switch (block.getType()) {
		case STONE:
			drop = new ItemStack(Material.STONE, 1);
			break;
		case IRON_ORE:
			drop = new ItemStack(Material.IRON_INGOT, 1);
			break;
		case GOLD_ORE:
			drop = new ItemStack(Material.GOLD_INGOT, 1);
			break;
		default:
			return false;
		}

		block.setType(Material.AIR);
		block.getWorld().dropItem(block.getLocation(), drop);
		return true;
	}

	/**
	 * Checks that the target block is minable. Will warn player when not.
	 * 
	 * @param player
	 *            Current player.
	 * @param material
	 *            Block that is mined.
	 * @param level
	 *            Player's level in mining.
	 * @return <code>true</code>, if player can mine target block.
	 */
	public boolean isMinable(final Player player, final Material material,
			final int level) {

		switch (material) {
		case STONE:
			return checkAndWarnBlock(player, level, "UseLevel.Stone");
		case COBBLESTONE:
			return checkAndWarnBlock(player, level, "UseLevel.Cobble");
		case MOSSY_COBBLESTONE:
			return checkAndWarnBlock(player, level, "UseLevel.MossStone");
		case COAL_ORE:
			return checkAndWarnBlock(player, level, "UseLevel.CoalOre");
		case IRON_ORE:
			return checkAndWarnBlock(player, level, "UseLevel.IronOre");
		case SANDSTONE:
			return checkAndWarnBlock(player, level, "UseLevel.SandStone");
		case GOLD_ORE:
			return checkAndWarnBlock(player, level, "UseLevel.GoldOre");
		case LAPIS_ORE:
			return checkAndWarnBlock(player, level, "UseLevel.LapisOre");
		case REDSTONE_ORE:
		case GLOWING_REDSTONE_ORE:
			return checkAndWarnBlock(player, level, "UseLevel.Redstone");
		case DIAMOND_ORE:
			return checkAndWarnBlock(player, level, "UseLevel.DiamondOre");
		case OBSIDIAN:
			return checkAndWarnBlock(player, level, "UseLevel.Obsidian");
		case NETHERRACK:
			return checkAndWarnBlock(player, level, "UseLevel.Netherrack");
		case SMOOTH_BRICK:
			return checkAndWarnBlock(player, level, "UseLevel.StoneBrick");
		default:
			return true;
		}
	}

}
