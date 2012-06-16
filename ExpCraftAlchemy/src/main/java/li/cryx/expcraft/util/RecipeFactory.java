package li.cryx.expcraft.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import li.cryx.expcraft.alchemy.recipe.CustomFurnaceRecipe;
import li.cryx.expcraft.alchemy.recipe.CustomShapedRecipe;
import li.cryx.expcraft.alchemy.recipe.CustomShapelessRecipe;
import li.cryx.expcraft.alchemy.recipe.TypedRecipe;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class RecipeFactory {
	private static Logger LOG = Logger.getLogger("RecipeFactory");

	/**
	 * Convert a YAML map into a recipe.
	 * 
	 * @param map
	 *            Definition of the recipe
	 *            <dl>
	 *            <dt><code>shape</code></dt>
	 *            <dd>One of the enums from {@link RecipeType} as String</dd>
	 *            <dt><code>output</code></dt>
	 *            <dd>The resulting <code>ItemStack</code>. See
	 *            {@link #stringToStack(String)}</dd>
	 *            <dt><code>input</code></dt>
	 *            <dd>Depending on the <code>shape</code>, containing the
	 *            required <code>ItemStacks</code> for the recipe</dd>
	 *            <dt><code>level</code></dt>
	 *            <dd>Level required to craft the recipe.</dd>
	 *            <dt><code>exp</code></dt>
	 *            <dd>Experience gained when crafting thie recipe.</dd>
	 *            </dl>
	 * @return The translated recipe. <code>null</code> when the definition
	 *         contains errors.
	 */
	public static TypedRecipe createFromMap(final Map<String, Object> map) {
		// recipe type
		RecipeType shape;
		try {
			shape = RecipeType.valueOf((String) map.get("shape"));
		} catch (ClassCastException e) {
			LOG.warning("Cannot read recipe shape from " + map);
			return null;
		}
		if (shape == null) {
			LOG.warning("Cannot determine recipe shape from " + map);
			return null;
		}

		// recipe result/output
		ItemStack output;
		try {
			output = stringToStack((String) map.get("output"));
		} catch (ClassCastException e) {
			LOG.warning("Cannot read output itemstack from " + map);
			return null;
		}
		if (output == null) {
			LOG.warning("Cannot determine output itemstack from " + map);
			return null;
		}

		TypedRecipe recipe;
		switch (shape) {
		case SHAPED:
			recipe = shapedFromMap(output, map);
			break;
		case SHAPELESS:
			recipe = shapelessFromMap(output, map);
			break;
		case FURNACE:
			recipe = furnanceFromMap(output, map);
			break;
		default:
			LOG.warning("Unknown recipe shape in " + map);
			return null;
		}
		if (recipe == null) {
			return null;
		}

		// level
		Object level = map.get("level");
		if (level instanceof Integer) {
			recipe.setLevel((Integer) level);
		} else if (level instanceof String) {
			try {
				recipe.setLevel(Integer.parseInt((String) level));
			} catch (NumberFormatException e) {
			}
		}

		// exp
		Object exp = map.get("exp");
		recipe.setExp(1);
		if (exp instanceof Double) {
			recipe.setExp((Double) exp);
		} else if (exp instanceof String) {
			try {
				recipe.setExp(Double.parseDouble((String) exp));
			} catch (NumberFormatException e) {
			}
		}

		// done
		return recipe;
	}

	@SuppressWarnings("unchecked")
	private static CustomFurnaceRecipe furnanceFromMap(final ItemStack output,
			final Map<String, Object> map) {
		CustomFurnaceRecipe recipe = new CustomFurnaceRecipe(output,
				Material.AIR);

		Object obj = map.get("input");
		ItemStack stack;
		if (obj instanceof List) {
			try {
				stack = stringToStack(((List<String>) obj).get(0));
			} catch (ClassCastException e) {
				LOG.warning("Cannot read input itemstack list from " + map);
				return null;
			}
		} else if (obj instanceof String) {
			stack = stringToStack((String) obj);
		} else {
			LOG.warning("Cannot read input itemstack from " + map);
			return null;
		}
		if (stack == null) {
			LOG.warning("Cannot determine input itemstack from " + map);
			return null;
		}
		recipe.setInput(stack.getData());

		return recipe;
	}

	@SuppressWarnings("unchecked")
	private static CustomShapedRecipe shapedFromMap(final ItemStack output,
			final Map<String, Object> map) {
		CustomShapedRecipe recipe = new CustomShapedRecipe(output);

		// prepare input
		Object obj = map.get("input");
		List<String> rawShape;
		if (obj instanceof List) {
			try {
				rawShape = (List<String>) obj;
			} catch (ClassCastException e) {
				LOG.warning("Cannot read input shape from " + map);
				return null;
			}
		} else if (obj instanceof String) {
			rawShape = new LinkedList<String>();
			rawShape.add((String) obj);
		} else {
			LOG.warning("Cannot determine input shape from " + map);
			return null;
		}

		// build shape
		Map<Character, MaterialData> ingreds = new TreeMap<Character, MaterialData>();
		StringBuffer buf = new StringBuffer();
		char ch = 'a';
		for (String row : rawShape) {
			String[] cols = row.split(",");
			for (String col : cols) {
				buf.append(ch);
				ItemStack item = stringToStack(col);
				if (item != null) {
					ingreds.put(ch, item.getData());
				}
				ch++;
			}
			buf.append(',');
		}

		// apply shape
		if (ingreds.size() == 0) {
			LOG.warning("Cannot create input shape from " + map);
			return null;
		}
		try {
			recipe.shape(buf.toString().split(","));
			for (Map.Entry<Character, MaterialData> e : ingreds.entrySet()) {
				recipe.setIngredient(e.getKey(), e.getValue());
			}
		} catch (IllegalArgumentException e) {
			LOG.warning("Illegal shape from " + map);
			return null;
		}

		// done
		return recipe;
	}

	/**
	 * Create a {@link CustomShapelessRecipe} from data in the given map.
	 * 
	 * @param output
	 *            The resulting <code>ItemStack</code> for the recipe.
	 * @param map
	 *            The recipe definition.
	 *            <dl>
	 *            <dt><code>input</code></dt>
	 *            <dd>List of input <code>ItemStack</code>s</dd>
	 *            </dl>
	 * 
	 * @return
	 */
	private static CustomShapelessRecipe shapelessFromMap(
			final ItemStack output, final Map<String, Object> map) {
		CustomShapelessRecipe recipe = new CustomShapelessRecipe(output);

		Object obj = map.get("input");
		if (obj instanceof List) {
			try {
				@SuppressWarnings("unchecked")
				List<String> ingreds = (List<String>) obj;
				int counter = 0;
				for (String s : ingreds) {
					ItemStack stack = stringToStack(s);
					if (stack == null) {
						LOG.warning("Cannot decipher '" + s
								+ "' input itemstack from " + map);
					} else {
						recipe.addIngredient(stack.getAmount(), stack.getData());
						counter++;
					}
				}
				if (counter == 0) {
					LOG.warning("No input itemstack found in " + map);
					return null;
				}
			} catch (ClassCastException e) {
				LOG.warning("Cannot read input itemstack from " + map);
				return null;
			}
		} else if (obj instanceof String) {
			ItemStack stack = stringToStack((String) obj);
			if (stack == null) {
				LOG.warning("Cannot determine input itemstack from " + map);
				return null;
			}
			recipe.addIngredient(stack.getAmount(), stack.getData());
		} else {
			LOG.warning("Unknown input format ("
					+ (obj == null ? "null" : obj.getClass())
					+ ") for input itemstack from " + map);
			return null;
		}

		return recipe;
	}

	/**
	 * Turn a String representation of a <code>ItemStack</code> back into an
	 * <code>ItemStack</code>. The representation follows the EBNF:
	 * 
	 * <p>
	 * ItemStackString := ItemMaterial ";" ItemData ";" ItemAmount.<br />
	 * ItemMaterial := &lt;ID of a block&gt; | &lt;Enum name from
	 * <code>Material</code>&gt;.<br />
	 * ItemData := &lt;empty string&gt; | &lt;natural number&gt;.<br />
	 * ItemAmount := &lt;natural number larger than 0&gt;.
	 * </p>
	 * 
	 * @param stack
	 *            A string representation of an <code>ItemStack</code>.
	 * @return The deciphered <code>ItemStack</code>. Will return
	 *         <code>null</code> when format does not match, numbers could not
	 *         be parsed, or, Material is unknown.
	 */
	public static ItemStack stringToStack(final String stack) {
		if (stack == null) {
			return null;
		}
		String[] ss = stack.split(";");
		if (ss.length != 3) {
			return null;
		}
		int amount;
		try {
			amount = Integer.parseInt(ss[2]);
		} catch (NumberFormatException e) {
			return null;
		}
		short data;
		if (ss[1] == null || ss[1].length() == 0 || ss[1].trim() == "-1") {
			data = -1;
		} else {
			try {
				data = Short.parseShort(ss[1]);
			} catch (NumberFormatException e) {
				return null;
			}
		}
		Material material = Material.getMaterial(ss[0].trim());
		if (material == null) {
			try {
				material = Material.getMaterial(Integer.parseInt(ss[0]));
			} catch (NumberFormatException e) {
				return null;
			}
			if (material == null) {
				return null;
			}
		}

		return new ItemStack(material, amount, data);
	}
}
