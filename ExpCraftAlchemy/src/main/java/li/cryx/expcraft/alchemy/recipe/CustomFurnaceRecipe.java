package li.cryx.expcraft.alchemy.recipe;

import li.cryx.expcraft.util.RecipeType;

import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class CustomFurnaceRecipe extends FurnaceRecipe implements TypedRecipe {

	private double exp;

	private int level;

	private boolean moduleRecipe = true;

	public CustomFurnaceRecipe(final ItemStack result, final Material source) {
		super(result, source);
	}

	public CustomFurnaceRecipe(final ItemStack result, final Material source,
			final int data) {
		super(result, source, data);
	}

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
