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

import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

/**
 * This class adds the {@link TypedRecipe} functionality to bukkit
 * <code>FurnaceRecipe</code>s.
 * 
 * @author cryxli
 */
public class CustomFurnaceRecipe extends FurnaceRecipe implements TypedRecipe {

	/** Exp gained when crafting the recipe. */
	private double exp;

	/** Level requirement to craft recipe. */
	private int level;

	private boolean moduleRecipe = true;

	/** Inherited super constructor. */
	public CustomFurnaceRecipe(final ItemStack result, final Material source) {
		super(result, source);
	}

	/** Convenient method for super constructor. */
	public CustomFurnaceRecipe(final ItemStack result, final Material source,
			final int data) {
		super(result, source, data);
	}

	/** Convenient method for super constructor. */
	public CustomFurnaceRecipe(final ItemStack result, final MaterialData source) {
		super(result, source);
	}

	@Override
	public double getExp() {
		return exp;
	}

	@Override
	public int getLevel() {
		return level;
	}

	/**
	 * Always returns {@link RecipeType.FURNACE}.
	 * 
	 * @see TypedRecipe#getType()
	 */
	@Override
	public RecipeType getType() {
		return RecipeType.FURNACE;
	}

	@Override
	public boolean isModuleRecipe() {
		return moduleRecipe;
	}

	@Override
	public void setExp(final double exp) {
		this.exp = exp;
	}

	@Override
	public void setLevel(final int level) {
		this.level = level;
	}

	@Override
	public void setModuleRecipe(final boolean moduleRecipe) {
		this.moduleRecipe = moduleRecipe;
	}

}
