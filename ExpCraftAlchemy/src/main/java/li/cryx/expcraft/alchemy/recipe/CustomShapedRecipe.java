package li.cryx.expcraft.alchemy.recipe;

import li.cryx.expcraft.alchemy.util.RecipeType;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class CustomShapedRecipe extends ShapedRecipe implements TypedRecipe {

	private double exp;

	private int level;

	private boolean moduleRecipe = true;

	public CustomShapedRecipe(final ItemStack result) {
		super(result);
	}

	public CustomShapedRecipe(final Material type, final int amount) {
		super(new ItemStack(type, amount));
	}

	public CustomShapedRecipe(final Material type, final int amount,
			final short damage) {
		super(new ItemStack(type, amount, damage));
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
		return RecipeType.SHAPED;
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
