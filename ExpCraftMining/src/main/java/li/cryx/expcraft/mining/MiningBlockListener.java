package li.cryx.expcraft.mining;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class MiningBlockListener implements Listener {

	private final Mining plugin;

	private final MiningConstraints test;

	public MiningBlockListener(final Mining plugin) {
		this.plugin = plugin;
		test = new MiningConstraints(plugin);
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockBreak(final BlockBreakEvent event) {
		if (!plugin.getPermission().worldCheck(event.getBlock().getWorld())) {
			return;
		}

		Player player = event.getPlayer();
		if (!plugin.getPermission().hasModule(plugin, player)) {
			return;
		}

		Material itemInHand = player.getItemInHand().getType();
		int level = plugin.getPersistence().getLevel(plugin, player);
		if (!test.checkPickaxe(player, itemInHand, level)) {
			event.setCancelled(true);
			return;
		}

		Material m = event.getBlock().getType();
		if (!test.isMinable(player, m, level)) {
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
		case GLOWING_REDSTONE_ORE:
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
		case SMOOTH_BRICK:
			expGain = plugin.getConfDouble("ExpGain.StoneBrick");
			break;
		}
		plugin.getPersistence().addExp(plugin, player, expGain);

		// fire pickaxe can produce different drops, therefore do only call
		// double drops when nothing special happened
		if (!test.firePickaxe(player, level, event)) {
			// double drops
			plugin.dropItem(event.getBlock(), level);
		}
	}

}
