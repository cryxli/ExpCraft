package li.cryx.expcraft.mining;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import li.cryx.expcraft.module.DropExpCraftModule;
import li.cryx.expcraft.util.Chat;
import li.cryx.expcraft.util.ToolQuality;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * This class is the main entry point for the mining module for ExpCraft. The
 * plugin manages the use of pickaxes and which blocks players can mine with how
 * much experience.
 * 
 * @author cryxli
 */
public class Mining extends DropExpCraftModule {
	/** Global logger */
	public final Logger LOG = Logger.getLogger("EC-Mining");

	/** The block listener to do all the work. */
	private MiningBlockListener blockListener;

	/** One chat utility to send messages. */
	private Chat chat;

	/** Tool material dependent amounts for double drops. */
	private static final Map<Material, int[]> doubleDrops = new HashMap<Material, int[]>();
	static {
		// stone, gold, iron, diamond
		doubleDrops.put(Material.STONE, new int[] { 1, 1, 2, 2 });
		doubleDrops.put(Material.COBBLESTONE, new int[] { 1, 1, 2, 2 });
		doubleDrops.put(Material.IRON_ORE, new int[] { 1, 1, 2, 2 });
		doubleDrops.put(Material.MOSSY_COBBLESTONE, //
				new int[] { 1, 1, 2, 2 });
		doubleDrops.put(Material.NETHERRACK, new int[] { 1, 1, 2, 2 });
		doubleDrops.put(Material.NETHER_BRICK, new int[] { 1, 1, 2, 2 });
		doubleDrops.put(Material.GOLD_ORE, new int[] { 0, 0, 1, 2 });
		doubleDrops.put(Material.SANDSTONE, new int[] { 1, 2, 2, 3 });
		doubleDrops.put(Material.REDSTONE_ORE, new int[] { 0, 5, 5, 8 });
		doubleDrops.put(Material.GLOWING_REDSTONE_ORE, //
				new int[] { 0, 5, 5, 8 });
		doubleDrops.put(Material.COAL_ORE, new int[] { 1, 2, 2, 3 });
		doubleDrops.put(Material.LAPIS_ORE, new int[] { 0, 5, 5, 8 });
		doubleDrops.put(Material.GLOWSTONE, new int[] { 2, 2, 4, 6 });
	}

	@Override
	public ItemStack calculateDrop(final Block block, final Player player) {
		Material material = block.getType();
		ToolQuality quality = ToolQuality.NONE;
		if (player != null) {
			quality = ToolQuality.getQuality(player.getItemInHand());
		}
		if (ToolQuality.isLess(ToolQuality.STONE, quality)) {
			// wooden tools do not produce double drops
			return null;
		}

		int amount = 1;
		int[] amounts = doubleDrops.get(material);
		if (amounts != null) {
			amount = amounts[quality.getQuality() - 2];
		}

		switch (material) {
		case STONE:
			return new ItemStack(Material.COBBLESTONE, amount);
		case COBBLESTONE:
		case IRON_ORE:
		case MOSSY_COBBLESTONE:
		case NETHERRACK:
		case NETHER_BRICK:
		case GOLD_ORE:
			return new ItemStack(material, amount);
		case OBSIDIAN:
			return new ItemStack(material, 1);
		case SANDSTONE:
			return new ItemStack(Material.SANDSTONE, amount, block.getData());
		case REDSTONE_ORE:
		case GLOWING_REDSTONE_ORE:
			return new ItemStack(Material.REDSTONE, amount);
		case COAL_ORE:
			return new ItemStack(Material.COAL, amount);
		case LAPIS_ORE:
			return new ItemStack(Material.INK_SACK, amount, (short) 4);
		case GLOWSTONE:
			return new ItemStack(Material.GLOWSTONE_DUST, amount);

		default:
			return null;
		}
	}

	/**
	 * Create listeners and link them to this plugin and a common
	 * <code>Chat</code> component.
	 */
	private void createListeners() {
		// use one chat tool
		chat = new Chat(getCore());

		// block listener
		blockListener = new MiningBlockListener(this);
	}

	@Override
	public void displayInfo(final Player sender) {
		chat.info(sender, MessageFormat.format("*** {0} ({1}) ***", getInfo()
				.getName(), getInfo().getAbbr()));

		int level = getPersistence().getLevel(this, sender);
		chat.info(sender, "Tools:");
		sendToolInfo(sender, "Wooden", level);
		sendToolInfo(sender, "Stone", level);
		sendToolInfo(sender, "Iron", level);
		sendToolInfo(sender, "Gold", level);
		sendToolInfo(sender, "Diamond", level);

		double exp = getPersistence().getExp(this, sender);
		double nextLvl = getPersistence().getExpForNextLevel(this, sender);
		chat.info(sender, "Stats:");
		chat.info(sender, MessageFormat.format(
				"Current level: {0}, XP: {1} points", level, exp));
		chat.info(sender, MessageFormat.format(
				"Experience to next level: {0} points", nextLvl - exp));
	}

	/**
	 * Load config from disk merge missing default values and store them to
	 * disk.
	 */
	private void loadConfig() {
		// force loading config from default once
		getConfig();
		// ...to store at first run
		saveConfig();
	}

	// server want the plugin to stop working
	@Override
	public void onDisable() {
		// disabled plugins don't get events; no need to unregister
		// listeners
		LOG.info(getInfo().getFullName() + " disabled");
	}

	// server want the plugin to start working
	@Override
	public void onEnable() {
		// pre-load config
		loadConfig();
		// register listeners
		registerEvents();
		// ready
		LOG.info(getInfo().getFullName() + " enabled");
	}

	/** Register the listeners */
	private void registerEvents() {
		// ensure the listeners are ready
		createListeners();
		// register listeners
		registerEvents(blockListener);
	}

	/**
	 * Send level info about the given pickaxe to the player. If the player
	 * meets the requirements for the tool, it will be written in "good"
	 * (default GREEN), or in "bad" (default RED) otherwise.
	 * 
	 * @param sender
	 *            Current player
	 * @param material
	 *            String identifying the material of the pickaxe.
	 * @param level
	 *            Player's level
	 */
	private void sendToolInfo(final Player sender, final String material,
			final int level) {
		int toolLevel = getConfig().getInteger("PickaxeLevel." + material);
		String msg = MessageFormat.format("{0} Pickaxe: {1}", material,
				toolLevel);
		if (level >= toolLevel) {
			chat.good(sender, msg);
		} else {
			chat.bad(sender, msg);
		}
	}

	/**
	 * Player cannot mine this block
	 * 
	 * @param player
	 *            Current player
	 * @param level
	 *            Level required to mine the block
	 */
	void warnBlockMine(final Player player, final int level) {
		chat.warn(player, MessageFormat.format(
				"Cannot mine this block. Required Level: {0}", level));
	}

	/**
	 * Player cannot use this tool.
	 * 
	 * @param player
	 *            Current player
	 * @param level
	 *            Level required to use the tool
	 */
	void warnToolUse(final Player player, final int level) {
		chat.warn(player, MessageFormat.format(
				"Cannot use this tool. Required Level: {0}", level));
	}

}
