package li.cryx.expcraft.alchemy.util;

import li.cryx.expcraft.alchemy.recipe.TypedRecipe;

import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

/**
 * This enum describes types of <code>Recipe</code>s.
 * 
 * @author cryxli
 */
public enum RecipeType {
	/**
	 * The recipe is shapeless. It does not matter how the ingreds are arranged.
	 */
	SHAPELESS, //
	/** The recipe is shaped. It does matter how the ingreds are arranged. */
	SHAPED, //
	/** The recipe describes how one material can be "cooked" into another. */
	FURNACE; //

	/**
	 * Return the <code>RecipeType</code> of the given recipe.
	 * 
	 * @param recipe
	 *            The recipe to categorise.
	 * @return The according <code>RecipeType</code>, or, <code>null</code>.
	 */
	public static RecipeType getType(final Recipe recipe) {
		if (recipe instanceof TypedRecipe) {
			return ((TypedRecipe) recipe).getType();
		} else if (recipe instanceof ShapedRecipe) {
			return SHAPED;
		} else if (recipe instanceof ShapelessRecipe) {
			return SHAPELESS;
		} else if (recipe instanceof FurnaceRecipe) {
			return FURNACE;
		} else {
			return null;
		}
	}
}
