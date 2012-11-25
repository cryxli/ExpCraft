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
package li.cryx.expcraft.alchemy.recipe;

import li.cryx.expcraft.alchemy.util.RecipeType;

import org.bukkit.inventory.Recipe;

/**
 * This interface defines additional functions recipe must offer when linked to
 * alchemy module.
 * 
 * @author cryxli
 */
public interface TypedRecipe extends Recipe {

	/**
	 * Get the amount of exp a recipe gives.
	 * 
	 * @return Exp gain.
	 */
	double getExp();

	/**
	 * Get the required level to craft this recipe.
	 * 
	 * @return Level requirement.
	 */
	int getLevel();

	/**
	 * Get the type of the recipe.
	 * 
	 * @return {@link RecipeType} for the recipe.
	 */
	RecipeType getType();

	/**
	 * Indicator whether the recipe was introduced by alchemy module or
	 * minecraft.
	 * 
	 * @return <code>true</code>, if recipe is defined by module.
	 */
	boolean isModuleRecipe();

	/**
	 * Set the amount of exp gained when crafting the recipe.
	 * 
	 * @param exp
	 *            Exp gain.
	 */
	void setExp(double exp);

	/**
	 * Set the level required to craft thie recipe.
	 * 
	 * @param level
	 *            Level requirement.
	 */
	void setLevel(int level);

	/**
	 * Set the origin of the recipe.
	 * 
	 * @param moduleRecipe
	 *            <code>true</code> to indicate that the alchemy module defined
	 *            it.
	 */
	void setModuleRecipe(boolean moduleRecipe);
}
