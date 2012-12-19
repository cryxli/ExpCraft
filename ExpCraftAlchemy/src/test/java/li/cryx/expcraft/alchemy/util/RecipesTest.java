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

import junit.framework.Assert;
import li.cryx.expcraft.alchemy.recipe.CustomFurnaceRecipe;
import li.cryx.expcraft.alchemy.recipe.CustomShapedRecipe;
import li.cryx.expcraft.alchemy.recipe.CustomShapelessRecipe;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Verify functionality of {@link Recipes} helper class.
 * 
 * @author cryxli
 */
public class RecipesTest extends AbstractItemStackTest {

	private static CustomShapelessRecipe shapeless;
	private static CustomShapelessRecipe shapelessClone;
	private static CustomFurnaceRecipe furnace;
	private static CustomFurnaceRecipe furnaceClone;
	private static CustomShapedRecipe shaped;
	private static CustomShapedRecipe shapedClone;
	private static CustomShapedRecipe turned;

	@BeforeClass
	public static void prepare() {
		shapeless = new CustomShapelessRecipe(Material.MELON_SEEDS, 1);
		shapeless.addIngredient(Material.MELON);

		shapelessClone = new CustomShapelessRecipe(Material.MELON_SEEDS, 1);
		shapelessClone.addIngredient(Material.MELON);

		furnace = new CustomFurnaceRecipe(
				new ItemStack(Material.IRON_INGOT, 1), Material.IRON_ORE);
		furnaceClone = new CustomFurnaceRecipe(new ItemStack(
				Material.IRON_INGOT, 1), Material.IRON_ORE);

		shaped = new CustomShapedRecipe(Material.TORCH, 4);
		shaped.shape("c", "s");
		shaped.setIngredient('c', Material.COAL);
		shaped.setIngredient('s', Material.STICK);
		shapedClone = new CustomShapedRecipe(Material.TORCH, 4);
		shapedClone.shape("a", "b");
		shapedClone.setIngredient('a', Material.COAL);
		shapedClone.setIngredient('b', Material.STICK);

		turned = new CustomShapedRecipe(Material.TORCH, 4);
		turned.shape("cs");
		turned.setIngredient('c', Material.COAL);
		turned.setIngredient('s', Material.STICK);
	}

	@Test
	public void cloneRecipe() {
		CustomShapedRecipe clone = Recipes.clone(shaped);
		Assert.assertNotNull(clone);
		Assert.assertFalse(clone == shaped);
		Assert.assertTrue(Recipes.equals(shaped, clone));
	}

	@Test
	public void equalsRecipe() {
		// general
		Assert.assertFalse(Recipes.equals((Recipe) null, null));
		Assert.assertFalse(Recipes.equals((Recipe) shapeless, null));
		Assert.assertFalse(Recipes.equals(null, (Recipe) shapeless));
		Assert.assertTrue(Recipes.equals(shapeless, shapeless));
		Assert.assertTrue(Recipes.equals(furnace, furnace));

		Assert.assertFalse(Recipes.equals(shapeless, furnace));
		Assert.assertFalse(Recipes.equals(furnace, shapeless));
		Assert.assertFalse(Recipes.equals(shaped, shapeless));
		Assert.assertFalse(Recipes.equals(shapeless, shaped));
		Assert.assertFalse(Recipes.equals(shaped, furnace));
		Assert.assertFalse(Recipes.equals(furnace, shaped));

		// shapeless
		Assert.assertTrue(Recipes.equals(shapelessClone, shapeless));
		Assert.assertTrue(Recipes.equals(shapeless, shapelessClone));

		Assert.assertTrue(Recipes.equals((Recipe) shapelessClone,
				(Recipe) shapeless));
		Assert.assertTrue(Recipes.equals((Recipe) shapeless,
				(Recipe) shapelessClone));

		// furnace
		Assert.assertTrue(Recipes.equals(furnaceClone, furnace));
		Assert.assertTrue(Recipes.equals(furnace, furnaceClone));

		Assert.assertTrue(Recipes.equals((Recipe) furnaceClone,
				(Recipe) furnace));
		Assert.assertTrue(Recipes.equals((Recipe) furnace,
				(Recipe) furnaceClone));

		// shaped
		Assert.assertTrue(Recipes.equals(shapedClone, shaped));
		Assert.assertTrue(Recipes.equals(shaped, shapedClone));

		Assert.assertTrue(Recipes.equals((Recipe) shapedClone, (Recipe) shaped));
		Assert.assertTrue(Recipes.equals((Recipe) shaped, (Recipe) shapedClone));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void unturnRecipe() {
		Recipe recipe = Recipes.unturn(furnace);
		Assert.assertTrue(recipe == furnace);

		recipe = Recipes.unturn(shapeless);
		Assert.assertTrue(recipe == shapeless);

		recipe = Recipes.unturn(turned);
		Assert.assertNotNull(recipe);
		Assert.assertFalse(recipe == shaped);
		Assert.assertFalse(recipe == turned);
		Assert.assertTrue(Recipes.equals(recipe, shaped));

		// Fence Gate
		CustomShapedRecipe gate = new CustomShapedRecipe(Material.FENCE_GATE, 1);
		gate.shape("sws", "sws");
		gate.setIngredient('s', Material.STICK);
		gate.setIngredient('w', Material.WOOD);

		CustomShapedRecipe gateTurned = new CustomShapedRecipe(
				Material.FENCE_GATE, 1);
		gateTurned.shape("sw", "ss", "ws");
		gateTurned.setIngredient('s', Material.STICK);
		gateTurned.setIngredient('w', Material.WOOD);

		recipe = Recipes.unturn(gateTurned);
		Assert.assertNotNull(recipe);
		Assert.assertFalse(recipe == gate);
		Assert.assertFalse(recipe == gateTurned);
		Assert.assertTrue(Recipes.equals(recipe, gate));

		// Bucket
		CustomShapedRecipe bucket = new CustomShapedRecipe(Material.BUCKET, 1);
		bucket.shape("i i", " i ");
		bucket.setIngredient('i', Material.IRON_INGOT);

		CustomShapedRecipe bucketTurned = new CustomShapedRecipe(
				Material.BUCKET, 1);
		bucketTurned.shape("i ", "i ", "i ");
		bucketTurned.setIngredient('i', Material.IRON_INGOT);

		recipe = Recipes.unturn(bucketTurned);
		Assert.assertNotNull(recipe);
		Assert.assertFalse(recipe == bucket);
		Assert.assertFalse(recipe == bucketTurned);
		Assert.assertTrue(Recipes.equals(recipe, bucket));
	}
}
