/*
 * Copyright (c) 2011 Urs P. Stettler, https://github.com/cryxli
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package li.cryx.expcraft.mining;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import li.cryx.expcraft.i18n.LangKeys;
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
		doubleDrops.put(Material.QUARTZ_ORE, new int[] { 1, 1, 2, 2 });
		doubleDrops.put(Material.MOSSY_COBBLESTONE, //
				new int[] { 1, 1, 2, 2 });
		doubleDrops.put(Material.NETHERRACK, new int[] { 1, 1, 2, 2 });
		doubleDrops.put(Material.NETHER_BRICK, new int[] { 1, 1, 2, 2 });
		doubleDrops.put(Material.GOLD_ORE, new int[] { 0, 0, 1, 2 });
		doubleDrops.put(Material.SANDSTONE, new int[] { 1, 2, 2, 3 });
		doubleDrops.put(Material.QUARTZ_BLOCK, new int[] { 1, 2, 2, 3 });
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
		case QUARTZ_ORE:
			return new ItemStack(Material.QUARTZ, amount);
		case COBBLESTONE:
		case IRON_ORE:
		case MOSSY_COBBLESTONE:
		case NETHERRACK:
		case NETHER_BRICK:
		case GOLD_ORE:
			return new ItemStack(material, amount);
		case OBSIDIAN:
			return new ItemStack(material, 1);
		case QUARTZ_BLOCK:
		case SANDSTONE:
			return new ItemStack(material, amount, block.getData());
		case REDSTONE_ORE:
		case GLOWING_REDSTONE_ORE:
			return new ItemStack(Material.REDSTONE, amount);
		case COAL_ORE:
			return new ItemStack(Material.COAL, amount);
		case LAPIS_ORE:
			return new ItemStack(Material.INK_SACK, amount, (short) 4);
		case GLOWSTONE:
			return new ItemStack(Material.GLOWSTONE_DUST, amount);

		case REDSTONE_BLOCK:
		default:
			// drop nothing
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
		chat.info(sender, LangKeys.MODULE_INFO_TITLE, this, getInfo().getAbbr());

		int level = getPersistence().getLevel(this, sender);
		chat.infoPlain(sender, translator.translate(sender, "info.tools"));
		sendToolInfo(sender, "Wooden", level);
		sendToolInfo(sender, "Stone", level);
		sendToolInfo(sender, "Iron", level);
		sendToolInfo(sender, "Gold", level);
		sendToolInfo(sender, "Diamond", level);

		double exp = getPersistence().getExp(this, sender);
		double nextLvl = getPersistence().getExpForNextLevel(this, sender);
		chat.info(sender, LangKeys.MODULE_INFO_STATS);
		chat.info(sender, LangKeys.MODULE_INFO_LV_EXP, level, exp);
		chat.info(sender, LangKeys.MODULE_INFO_NEXT_LV, nextLvl - exp);
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
		String msg = translator
				.translate(sender, "info." + material, toolLevel);
		if (level >= toolLevel) {
			chat.goodPlain(sender, msg);
		} else {
			chat.badPlain(sender, msg);
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
		chat.warnPlain(player,
				translator.translate(player, "warn.block", level));
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
		chat.warnPlain(player, translator.translate(player, "warn.tool", level));
	}

}
