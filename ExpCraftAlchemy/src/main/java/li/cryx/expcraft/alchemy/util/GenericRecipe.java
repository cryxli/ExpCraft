package li.cryx.expcraft.alchemy.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import li.cryx.expcraft.alchemy.recipe.CustomFurnaceRecipe;
import li.cryx.expcraft.alchemy.recipe.CustomShapedRecipe;
import li.cryx.expcraft.alchemy.recipe.CustomShapelessRecipe;
import li.cryx.expcraft.alchemy.recipe.TypedRecipe;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

/**
 * This class is used by {@link RecipeParser} to store recipe information while
 * parsing the XML.
 * 
 * @author cryxli
 */
public class GenericRecipe implements TypedRecipe {

	/** the resulting item(s) */
	private ItemStack result;

	/** experience gained */
	private double exp = 1;

	/** required level */
	private int level = 0;

	/** list of ingredients */
	private final List<ItemStack> ingreds = new LinkedList<ItemStack>();

	/** width of a shaped recipe. */
	private int width;

	/** height of a shaped recipe. */
	private int height;

	/** type of recipe */
	private RecipeType type;

	public GenericRecipe() {
	}

	public GenericRecipe(final RecipeType type) {
		this.type = type;
	}

	public void addIngred(final ItemStack stack) {
		ingreds.add(stack);
	}

	@Override
	public double getExp() {
		return exp;
	}

	@Override
	public int getLevel() {
		return level;
	}

	@Override
	public ItemStack getResult() {
		return result;
	}

	@Override
	public RecipeType getType() {
		return type;
	}

	@Override
	public boolean isModuleRecipe() {
		return true;
	}

	@Override
	public void setExp(final double exp) {
		this.exp = exp;
	}

	public void setHeight(final int height) {
		this.height = height;
	}

	@Override
	public void setLevel(final int level) {
		this.level = level;
	}

	@Override
	public void setModuleRecipe(final boolean moduleRecipe) {
	}

	public void setResult(final ItemStack result) {
		this.result = result;
	}

	public void setType(final RecipeType type) {
		this.type = type;
	}

	public void setWidth(final int width) {
		this.width = width;
	}

	/**
	 * Create a FurnaceRecipe from the set data.
	 * 
	 * @return A FurnaceRecipe, or, <code>null</code> if the data does not
	 *         specify one.
	 */
	public CustomFurnaceRecipe toFurnanceRecipe() {
		if (ingreds.size() != 1 || result == null) {
			return null;
		}

		CustomFurnaceRecipe r = new CustomFurnaceRecipe(result, ingreds.get(0)
				.getType(), ingreds.get(0).getDurability());
		r.setExp(exp);
		r.setLevel(level);
		r.setModuleRecipe(true);
		return r;
	}

	/**
	 * Create a ShapedRecipe from the set data.
	 * 
	 * @return A ShapedRecipe, or, <code>null</code> if the data does not
	 *         specify one.
	 */
	public CustomShapedRecipe toShapedRecipe() {
		if (height < 1 || width < 1 || height > 3 || width > 3
				|| ingreds.size() != width * height || result == null) {
			return null;
		}

		CustomShapedRecipe r = new CustomShapedRecipe(result);
		// build shape
		Map<Character, MaterialData> ingredMap = new TreeMap<Character, MaterialData>();
		StringBuffer buf = new StringBuffer();
		Iterator<ItemStack> iter = ingreds.iterator();
		char ch = 'a';
		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				buf.append(ch);
				ItemStack item = iter.next();
				if (item != null) {
					ingredMap.put(ch, item.getData());
				}
				ch++;
			}
			buf.append(',');
		}

		// apply shape
		r.shape(buf.toString().split(","));
		for (Map.Entry<Character, MaterialData> e : ingredMap.entrySet()) {
			if (e.getValue().getItemType() != Material.AIR) {
				r.setIngredient(e.getKey(), e.getValue());
			}
		}

		r.setExp(exp);
		r.setLevel(level);
		r.setModuleRecipe(true);
		return r;
	}

	/**
	 * Create a ShapelessRecipe from the set data.
	 * 
	 * @return A ShapelessRecipe, or, <code>null</code> if the data does not
	 *         specify one.
	 */
	public CustomShapelessRecipe toShapelessRecipe() {
		if (ingreds.size() < 1 || ingreds.size() > 9 || result == null) {
			return null;
		}

		CustomShapelessRecipe r = new CustomShapelessRecipe(result);
		for (ItemStack ingred : ingreds) {
			r.addIngredient(1, ingred.getData());
		}
		r.setExp(exp);
		r.setLevel(level);
		r.setModuleRecipe(true);
		return r;
	}

	/**
	 * Create a recipe from the set data.
	 * 
	 * @return The requested recipe, or, <code>null</code>, if the recipe
	 *         {@link #type} is not set, or the data does not define the
	 *         requested recipe type.
	 */
	public TypedRecipe toTypedRecipe() {
		if (type == RecipeType.FURNACE) {
			return toFurnanceRecipe();
		} else if (type == RecipeType.SHAPELESS) {
			return toShapelessRecipe();
		} else if (type == RecipeType.SHAPED) {
			return toShapedRecipe();
		} else {
			return null;
		}
	}

}
