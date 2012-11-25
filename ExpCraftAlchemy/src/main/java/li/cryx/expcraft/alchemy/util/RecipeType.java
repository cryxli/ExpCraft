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
