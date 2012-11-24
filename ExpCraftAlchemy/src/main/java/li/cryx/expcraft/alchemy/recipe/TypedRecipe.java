package li.cryx.expcraft.alchemy.recipe;

import li.cryx.expcraft.alchemy.util.RecipeType;

import org.bukkit.inventory.Recipe;

public interface TypedRecipe extends Recipe {
	double getExp();

	int getLevel();

	/**
	 * Get the type of the recipe.
	 * 
	 * @return {@link RecipeType} for the recipe.
	 */
	RecipeType getType();

	boolean isModuleRecipe();

	void setExp(double exp);

	void setLevel(int level);

	void setModuleRecipe(boolean moduleRecipe);
}
