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

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import li.cryx.expcraft.alchemy.recipe.CustomShapedRecipe;
import li.cryx.expcraft.alchemy.recipe.TypedRecipe;

import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

/**
 * This helper class offers methods to operate on bukkit <code>Recipe</code>s.
 * 
 * @author cryxli
 */
public class Recipes {

	/**
	 * Create an exact copy of the given ShapedRecipe.
	 * 
	 * @param recipe
	 *            Recipe to copy.
	 * @return A CustomShapedRecipe describing the same recipe.
	 */
	public static CustomShapedRecipe clone(final ShapedRecipe recipe) {
		CustomShapedRecipe clone = new CustomShapedRecipe(recipe.getResult());
		clone.shape(recipe.getShape());
		for (Map.Entry<Character, ItemStack> e : recipe.getIngredientMap()
				.entrySet()) {
			if (e.getValue() != null) {
				clone.setIngredient(e.getKey(), e.getValue().getData());
			}
		}

		if (recipe instanceof TypedRecipe) {
			clone.setExp(((TypedRecipe) recipe).getExp());
			clone.setLevel(((TypedRecipe) recipe).getLevel());
		}

		return clone;
	}

	/**
	 * Compare two furnace recipes.
	 * 
	 * @param r1
	 *            A furnace recipe
	 * @param r2
	 *            Another furnace recipe
	 * @return <code>true</code>, if both recipes produce the same material with
	 *         the same ingred.
	 */
	public static boolean equals(final FurnaceRecipe r1, final FurnaceRecipe r2) {
		// ensure parameters are set
		if (r1 == null || r2 == null || !r1.getResult().equals(r2.getResult())) {
			return false;
		}

		// input must match
		return r1.getInput().equals(r2.getInput());
	}

	/**
	 * Check whether two recipes are the same.
	 * 
	 * @param r1
	 *            A recipe
	 * @param r2
	 *            Another recipe
	 * @return <code>true</code>, if both recipes produce the same material with
	 *         the same ingreds.
	 */
	public static boolean equals(final Recipe r1, final Recipe r2) {
		RecipeType type1 = RecipeType.getType(r1);
		RecipeType type2 = RecipeType.getType(r2);
		// parameters must not be null
		// types of the recipes must match
		// outputs of the recipes must match
		if (r1 == null || r2 == null || type1 != type2
				|| !r1.getResult().equals(r2.getResult())) {
			return false;
		} else if (type1 == RecipeType.SHAPELESS) {
			return equals((ShapelessRecipe) r1, (ShapelessRecipe) r2);
		} else if (type1 == RecipeType.SHAPED) {
			return equals((ShapedRecipe) r1, (ShapedRecipe) r2);
		} else if (type1 == RecipeType.FURNACE) {
			return equals((FurnaceRecipe) r1, (FurnaceRecipe) r2);
		} else { // type1 == null
			return false;
		}
	}

	/**
	 * Compare two shaped recipes.
	 * 
	 * @param r1
	 *            A shapeless recipe
	 * @param r2
	 *            Another shapeless recipe
	 * @return <code>true</code>, if both recipes produce the same material with
	 *         the same ingreds arranged in the exact same way.
	 */
	public static boolean equals(final ShapedRecipe r1, final ShapedRecipe r2) {
		// ensure parameters are set
		if (r1 == null || r2 == null || !r1.getResult().equals(r2.getResult())) {
			return false;
		}

		// check recipe shapes
		String[] shape1 = r1.getShape();
		String[] shape2 = r2.getShape();
		if (shape1 == null || shape2 == null || shape1.length != shape2.length) {
			return false;
		}
		for (int i = 0; i < shape1.length; i++) {
			if (shape1[i] == null || shape2[i] == null
					|| shape1.length != shape2.length) {
				return false;
			}
		}

		// check recipe ingreds
		Map<Character, ItemStack> i1 = r1.getIngredientMap();
		Map<Character, ItemStack> i2 = r2.getIngredientMap();
		for (int row = 0; row < shape1.length; row++) {
			for (int col = 0; col < shape1[row].length(); col++) {
				char ch1 = shape1[row].charAt(col);
				char ch2 = shape2[row].charAt(col);
				ItemStack is1 = i1.get(ch1);
				ItemStack is2 = i2.get(ch2);
				if ((is1 == null && is2 != null)
						|| (is2 == null && is1 != null)
						|| (is1 != null && !is1.equals(is2))) {
					return false;
				}
			}
		}

		// all tests passed
		return true;
	}

	/**
	 * Compare two shapeless recipes.
	 * 
	 * @param r1
	 *            A shapeless recipe
	 * @param r2
	 *            Another shapeless recipe
	 * @return <code>true</code>, if both recipes produce the same material with
	 *         the same ingreds.
	 */
	public static boolean equals(final ShapelessRecipe r1,
			final ShapelessRecipe r2) {
		// ensure parameters are set
		if (r1 == null || r2 == null || !r1.getResult().equals(r2.getResult())) {
			return false;
		} else if (r1 == r2) {
			return true;
		}

		// get recipe ingreds
		List<ItemStack> i1 = r1.getIngredientList();
		List<ItemStack> i2 = r2.getIngredientList();
		// to match the amount of ingreds must match
		if (i1.size() != i2.size()) {
			return false;
		}

		// check each ingred separately
		for (int i = 0; i < i1.size(); i++) {
			if (!i1.get(i).equals(i2.get(i))) {
				return false;
			}
		}

		// all tests passed
		return true;
	}

	/**
	 * Dump the given shaped recipe to the stdout.
	 * 
	 * @param recipe
	 *            a shaped recipe.
	 */
	public static void print(final ShapedRecipe recipe) {
		ItemStack out = recipe.getResult();
		System.out.println(MessageFormat.format("Result: {0}x {1}:{2}",
				out.getAmount(), out.getType(), out.getDurability()));
		System.out.println("Shape:");
		for (String s : recipe.getShape()) {
			System.out.println("  " + s);
		}
		System.out.println("Ingred:");
		for (Character c : recipe.getIngredientMap().keySet()) {
			ItemStack is = recipe.getIngredientMap().get(c);
			if (is != null) {
				System.out.println(MessageFormat.format("  {0} : {1}:{2}", c,
						is.getType(), is.getDurability()));
			}
		}
	}

	/**
	 * Correct representation of shaped recipes until bug <a
	 * href="https://bukkit.atlassian.net/browse/BUKKIT-1334">BUKIT-1334</a> is
	 * fixed. This method also accepts furnace and shapeless recipes and just
	 * returns them unchanged.
	 * 
	 * <p>
	 * <a href="https://bukkit.atlassian.net/browse/BUKKIT-1334">BUKIT-1334</a>
	 * is fixed with Recommended 1.3.1-R1.0
	 * </p>
	 * 
	 * @param recipe
	 *            A (shaped) recipe to correct.
	 * @return The corrected (shaped) recipe, or, the unchanged furnace or
	 *         shapeless recipe.
	 */
	@Deprecated
	public static Recipe unturn(final Recipe recipe) {
		if (recipe instanceof ShapedRecipe) {
			ShapedRecipe shaped = clone((ShapedRecipe) recipe);

			String[] oriShapes = shaped.getShape();
			String line = "";
			for (int i = 0; i < oriShapes.length; i++) {
				line += oriShapes[i];
			}
			int pos = 0;

			String[] turned = new String[oriShapes[0].length()];

			for (int row = 0; row < turned.length; row++) {
				turned[row] = "";
				for (int col = 0; col < oriShapes.length; col++) {
					turned[row] += line.charAt(pos++);
				}
			}
			shaped.shape(turned);

			return shaped;
		} else {
			return recipe;
		}
	}
}
