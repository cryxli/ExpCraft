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
package li.cryx.expcraft.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * This class helps categorizing tools, armor and weapons.
 * 
 * @author cryxli
 */
public enum ToolQuality {
	/** not a tool, armor or weapon, quality = 0 */
	NONE(0),
	/** LEATHER and WOOD, quality = 1 */
	WOOD(1),
	/** COBBLESTONE and CHAINMAIL, quality = 2 */
	STONE(2),
	/** GOLD, quality = 3 */
	GOLD(3),
	/** IRON, quality = 4 */
	IRON(4),
	/** DIAMOND, quality = 5 */
	DIAMOND(5);

	/**
	 * Get the material a tool, armor or weapon is made of.
	 * 
	 * @param type
	 *            The material describing the tool/armor.
	 * @return One of LEATHER, WOOD, STONE (for chainmail), COBBLESTONE,
	 *         IRON_INGOT, GOLD_INGOT, DIAMOND, or, <code>null</code>.
	 */
	public static Material getMaterial(final Material type) {
		if (type == null) {
			return null;
		}
		switch (type) {
		case LEATHER_BOOTS:
		case LEATHER_CHESTPLATE:
		case LEATHER_HELMET:
		case LEATHER_LEGGINGS:
			return Material.LEATHER;
		case WOOD_AXE:
		case WOOD_HOE:
		case WOOD_PICKAXE:
		case WOOD_SPADE:
		case WOOD_SWORD:
		case BOW:
			return Material.WOOD;
		case CHAINMAIL_BOOTS:
		case CHAINMAIL_CHESTPLATE:
		case CHAINMAIL_HELMET:
		case CHAINMAIL_LEGGINGS:
			return Material.STONE;
		case STONE_AXE:
		case STONE_HOE:
		case STONE_PICKAXE:
		case STONE_SPADE:
		case STONE_SWORD:
			return Material.COBBLESTONE;
		case IRON_BOOTS:
		case IRON_CHESTPLATE:
		case IRON_HELMET:
		case IRON_LEGGINGS:
		case IRON_AXE:
		case IRON_HOE:
		case IRON_PICKAXE:
		case IRON_SPADE:
		case IRON_SWORD:
			return Material.IRON_INGOT;
		case GOLD_BOOTS:
		case GOLD_CHESTPLATE:
		case GOLD_HELMET:
		case GOLD_LEGGINGS:
		case GOLD_AXE:
		case GOLD_HOE:
		case GOLD_PICKAXE:
		case GOLD_SPADE:
		case GOLD_SWORD:
			return Material.GOLD_INGOT;
		case DIAMOND_BOOTS:
		case DIAMOND_CHESTPLATE:
		case DIAMOND_HELMET:
		case DIAMOND_LEGGINGS:
		case DIAMOND_AXE:
		case DIAMOND_HOE:
		case DIAMOND_PICKAXE:
		case DIAMOND_SPADE:
		case DIAMOND_SWORD:
			return Material.DIAMOND;
		default:
			return null;
		}
	}

	/**
	 * Get a numerical classification of the material a tool, armor or weapon is
	 * made of.
	 * 
	 * @param stack
	 *            The tool or armor.
	 * @return Quality of tool, or, <code>0</code>, if <code>stack</code> is
	 *         <code>null</code>.
	 * @see #getQuality(Material)
	 */
	public static ToolQuality getQuality(final ItemStack stack) {
		if (stack == null) {
			return NONE;
		} else {
			return getQuality(stack.getType());
		}
	}

	/**
	 * Get a numerical classification of the material a tool, armor or weapon is
	 * made of.
	 * 
	 * @param type
	 *            The material describing the tool/armor.
	 * @return One of
	 *         <dl>
	 *         <dt>1</td>
	 *         <dd>LEATHER, WOOD</dd>
	 *         <dt>2</td>
	 *         <dd>STONE, COBBLESTONE</dd>
	 *         <dt>3</td>
	 *         <dd>GOLD_INGOT</dd>
	 *         <dt>4</td>
	 *         <dd>IRON_INGOT</dd>
	 *         <dt>5</td>
	 *         <dd>DIAMOND</dd>
	 *         <dt>0</td>
	 *         <dd>anything else</dd> </ul>
	 * @see ToolQuality#getMaterial(Material)
	 */
	public static ToolQuality getQuality(final Material type) {
		Material material = getMaterial(type);
		if (material == null) {
			return NONE;
		}
		switch (material) {
		case LEATHER:
		case WOOD:
			return WOOD;
		case STONE:
		case COBBLESTONE:
			return STONE;
		case GOLD_INGOT:
			return GOLD;
		case IRON_INGOT:
			return IRON;
		case DIAMOND:
			return DIAMOND;
		default:
			return NONE;
		}
	}

	public static boolean isAtLeast(final ToolQuality expected,
			final ToolQuality actual) {
		return actual.getQuality() >= expected.getQuality();
	}

	public static boolean isAtMost(final ToolQuality expected,
			final ToolQuality actual) {
		return actual.getQuality() <= expected.getQuality();
	}

	/**
	 * Check whether the given tool is an axe of any material.
	 * 
	 * @param type
	 *            The material describing the tool.
	 * @return <code>true</code>, if the tool is an axe.
	 */
	public static boolean isAxe(final Material type) {
		if (type == null) {
			return false;
		}
		switch (type) {
		case WOOD_AXE:
		case STONE_AXE:
		case IRON_AXE:
		case GOLD_AXE:
		case DIAMOND_AXE:
			return true;
		default:
			return false;
		}
	}

	/**
	 * Check whether the given armor is boots of any material.
	 * 
	 * @param type
	 *            The material describing the armor.
	 * @return <code>true</code>, if the armor is boots.
	 */
	public static boolean isBoots(final Material type) {
		if (type == null) {
			return false;
		}
		switch (type) {
		case LEATHER_BOOTS:
		case CHAINMAIL_BOOTS:
		case IRON_BOOTS:
		case GOLD_BOOTS:
		case DIAMOND_BOOTS:
			return true;
		default:
			return false;
		}
	}

	/**
	 * Check whether the given armor is a chestplate of any material.
	 * 
	 * @param type
	 *            The material describing the armor.
	 * @return <code>true</code>, if the armor is a chestplate.
	 */
	public static boolean isChestplate(final Material type) {
		if (type == null) {
			return false;
		}
		switch (type) {
		case LEATHER_CHESTPLATE:
		case CHAINMAIL_CHESTPLATE:
		case IRON_CHESTPLATE:
		case GOLD_CHESTPLATE:
		case DIAMOND_CHESTPLATE:
			return true;
		default:
			return false;
		}
	}

	public static boolean isGeather(final ToolQuality expected,
			final ToolQuality actual) {
		return actual.getQuality() > expected.getQuality();
	}

	/**
	 * Check whether the given armor is a helmet of any material.
	 * 
	 * @param type
	 *            The material describing the armor.
	 * @return <code>true</code>, if the armor is a helmet.
	 */
	public static boolean isHelmet(final Material type) {
		if (type == null) {
			return false;
		}
		switch (type) {
		case LEATHER_HELMET:
		case CHAINMAIL_HELMET:
		case IRON_HELMET:
		case GOLD_HELMET:
		case DIAMOND_HELMET:
			return true;
		default:
			return false;
		}
	}

	/**
	 * Check whether the given tool is a hoe of any material.
	 * 
	 * @param type
	 *            The material describing the tool.
	 * @return <code>true</code>, if the tool is a hoe.
	 */
	public static boolean isHoe(final Material type) {
		if (type == null) {
			return false;
		}
		switch (type) {
		case WOOD_HOE:
		case STONE_HOE:
		case IRON_HOE:
		case GOLD_HOE:
		case DIAMOND_HOE:
			return true;
		default:
			return false;
		}
	}

	/**
	 * Check whether the given armor is leggings of any material.
	 * 
	 * @param type
	 *            The material describing the armor.
	 * @return <code>true</code>, if the armor is an leggings.
	 */
	public static boolean isLeggings(final Material type) {
		if (type == null) {
			return false;
		}
		switch (type) {
		case LEATHER_LEGGINGS:
		case CHAINMAIL_LEGGINGS:
		case IRON_LEGGINGS:
		case GOLD_LEGGINGS:
		case DIAMOND_LEGGINGS:
			return true;
		default:
			return false;
		}
	}

	public static boolean isLess(final ToolQuality expected,
			final ToolQuality actual) {
		return actual.getQuality() < expected.getQuality();
	}

	/**
	 * Check whether the given tool is a pickaxe of any material.
	 * 
	 * @param type
	 *            The material describing the tool.
	 * @return <code>true</code>, if the tool is a pickaxe.
	 */
	public static boolean isPickaxe(final Material type) {
		if (type == null) {
			return false;
		}
		switch (type) {
		case WOOD_PICKAXE:
		case STONE_PICKAXE:
		case IRON_PICKAXE:
		case GOLD_PICKAXE:
		case DIAMOND_PICKAXE:
			return true;
		default:
			return false;
		}
	}

	/**
	 * Check whether the given tool is a spade of any material.
	 * 
	 * @param type
	 *            The material describing the tool.
	 * @return <code>true</code>, if the tool is a spade.
	 */
	public static boolean isSpade(final Material type) {
		if (type == null) {
			return false;
		}
		switch (type) {
		case WOOD_SPADE:
		case STONE_SPADE:
		case IRON_SPADE:
		case GOLD_SPADE:
		case DIAMOND_SPADE:
			return true;
		default:
			return false;
		}
	}

	/**
	 * Check whether the given weapon is a sword of any material.
	 * 
	 * @param type
	 *            The material describing the weapon.
	 * @return <code>true</code>, if the weapon is a sword.
	 */
	public static boolean isSword(final Material type) {
		if (type == null) {
			return false;
		}
		switch (type) {
		case WOOD_SWORD:
		case STONE_SWORD:
		case IRON_SWORD:
		case GOLD_SWORD:
		case DIAMOND_SWORD:
			return true;
		default:
			return false;
		}
	}

	private final int quality;

	private ToolQuality(final int quality) {
		this.quality = quality;
	}

	public int getQuality() {
		return quality;
	}

}
