package li.cryx.expcraft.alchemy;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import li.cryx.expcraft.alchemy.recipe.CustomFurnaceRecipe;
import li.cryx.expcraft.alchemy.recipe.TypedRecipe;
import li.cryx.expcraft.alchemy.util.Recipes;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

/**
 * Storage to handle custom recipes and compare recipes (from events) against
 * them.
 * 
 * @author cryxli
 */
public class RecipeStore {

	/** A default recipe to handle build-in recipes. */
	private final CustomFurnaceRecipe DEFAULT_RECIPE;

	/** List of recipes ordered by resulting ItemStacks. */
	private final Map<ItemStack, List<TypedRecipe>> recipes = new HashMap<ItemStack, List<TypedRecipe>>();

	public RecipeStore(final double defaultExp) {
		DEFAULT_RECIPE = new CustomFurnaceRecipe(new ItemStack(Material.AIR),
				Material.AIR);
		DEFAULT_RECIPE.setExp(defaultExp);
		DEFAULT_RECIPE.setModuleRecipe(false);
	}

	/**
	 * Add a recipe.
	 * 
	 * @param recipe
	 *            The {@link TypedRecipe} to add.
	 */
	public void add(final TypedRecipe recipe) {
		if (recipe == null) {
			return;
		}
		List<TypedRecipe> list = getList(recipe.getResult());
		list.add(recipe);
	}

	/**
	 * Add multiple recipes at once.
	 * 
	 * @param recipes
	 *            A collection of {@link TypedRecipe} to add.
	 */
	public void addAll(final Collection<TypedRecipe> recipes) {
		for (TypedRecipe recipe : recipes) {
			add(recipe);
		}
	}

	/**
	 * Get all recipes that produce the given <code>ItemStack</code>.
	 * 
	 * @param output
	 *            The result of the recipe.
	 * @return A list of recipes producing the given item.
	 */
	private List<TypedRecipe> getList(final ItemStack output) {
		List<TypedRecipe> list = recipes.get(output);
		if (list == null) {
			list = new LinkedList<TypedRecipe>();
			recipes.put(output, list);
		}
		return list;
	}

	/**
	 * Get the module internal recipe matching the given server recipe.
	 * 
	 * @param recipe
	 *            A server recipe, e.g. taken from a crafting event.
	 * @return The matching module internal recipe.
	 */
	public TypedRecipe getRecipe(final Recipe recipe) {
		// look for the given recipe in the internal list
		List<TypedRecipe> list = getList(recipe.getResult());
		for (TypedRecipe internalRecipe : list) {
			if (Recipes.equals(recipe, internalRecipe)) {
				// found, return it
				return internalRecipe;
			}
		}

		// no internal recipe found, return default one
		return DEFAULT_RECIPE;
	}

	/**
	 * Install the stored recipes on the given server.
	 * 
	 * @param server
	 *            The server to which to add the recipes.
	 */
	public void installRecipes(final Server server) {
		int ok = 0;
		int nok = 0;
		for (List<TypedRecipe> recipes : this.recipes.values()) {
			for (TypedRecipe recipe : recipes) {
				if (server.addRecipe(recipe)) {
					ok++;
				} else {
					nok++;
				}
			}
		}
		server.getLogger().info("Recipes added: " + ok + ", skipped: " + nok);
	}

	/**
	 * Resets the given server to its original set of recipes.
	 * 
	 * @param server
	 *            The server to reset.
	 */
	public void uninstallRecipes(final Server server) {
		// TODO if the server ever offers a way to unregister one recipe at a
		// time, use this method!
		server.resetRecipes();
	}

}
