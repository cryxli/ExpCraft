package li.cryx.expcraft.alchemy.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import li.cryx.expcraft.alchemy.recipe.CustomFurnaceRecipe;
import li.cryx.expcraft.alchemy.recipe.CustomShapedRecipe;
import li.cryx.expcraft.alchemy.recipe.CustomShapelessRecipe;
import li.cryx.expcraft.alchemy.recipe.TypedRecipe;
import li.cryx.expcraft.util.RecipeFactory;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.Test;

/**
 * Test for {@link RecipeFactory}'s main functionality.
 * 
 * @author cryxli
 */
public class RecipeFactoryTest {

	private void checkItemStack(final ItemStack stack, final Material material,
			final int dur, final int amount) {
		Assert.assertEquals(material, stack.getType());
		Assert.assertEquals(dur, stack.getDurability());
		Assert.assertEquals(amount, stack.getAmount());
	}

	@Test
	/** Test for {@link RecipeFactory#furnaceFromMap()} */
	public void furnaceFromMap() {
		// prepare
		// - shape: FURNACE
		// output: IRON_INGOT;;3
		// input: BUCKET;;1
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("shape", "FURNACE");
		inputMap.put("output", "IRON_INGOT;;3");
		inputMap.put("input", "BUCKET;;1");

		// test
		TypedRecipe tr = RecipeFactory.createFromMap(inputMap);

		// verify
		Assert.assertTrue(tr instanceof CustomFurnaceRecipe);
		CustomFurnaceRecipe recipe = (CustomFurnaceRecipe) tr;
		checkItemStack(recipe.getInput(), Material.BUCKET, -1, 1);
		checkItemStack(recipe.getResult(), Material.IRON_INGOT, -1, 3);
	}

	@Test
	/** Test for {@link RecipeFactory#shapedFromMap()} */
	public void shapedFromMap() {
		// prepare
		// - shape: SHAPED
		// output: CHAINMAIL_HELMET;;1
		// input:
		// - STONE;;1,STONE;;1,STONE;;1
		// - STONE;;1,AIR;;1,STONE;;1
		// level: 10
		// exp: 5.0
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("shape", "SHAPED");
		inputMap.put("output", "CHAINMAIL_HELMET;;1");
		inputMap.put("level", 10);
		inputMap.put("exp", 5.0);
		List<String> shape = new LinkedList<String>();
		shape.add("STONE;;1,STONE;;1,STONE;;1");
		shape.add("STONE;;1,AIR;;1,STONE;;1");
		inputMap.put("input", shape);

		// test
		TypedRecipe tr = RecipeFactory.createFromMap(inputMap);

		// verify
		Assert.assertTrue(tr instanceof CustomShapedRecipe);
		CustomShapedRecipe recipe = (CustomShapedRecipe) tr;
		checkItemStack(recipe.getResult(), Material.CHAINMAIL_HELMET, -1, 1);
		Assert.assertEquals(10, recipe.getLevel());
		Assert.assertEquals(5, recipe.getExp(), 0);
		String[] ss = recipe.getShape();
		Assert.assertEquals(2, ss.length);
		Assert.assertEquals("abc", ss[0]);
		Assert.assertEquals("def", ss[1]);
		Map<Character, ItemStack> map = recipe.getIngredientMap();
		Assert.assertEquals(6, map.size());
		checkItemStack(map.get('a'), Material.STONE, -1, 1);
		checkItemStack(map.get('b'), Material.STONE, -1, 1);
		checkItemStack(map.get('c'), Material.STONE, -1, 1);
		checkItemStack(map.get('d'), Material.STONE, -1, 1);
		Assert.assertNull(map.get('e'));
		checkItemStack(map.get('f'), Material.STONE, -1, 1);
	}

	@Test
	/** Test for {@link RecipeFactory#shapelessFromMap()} */
	public void shapelessFromMap() {
		// prepare
		// - shape: SHAPELESS
		// output: DIRT;;2
		// input:
		// - SAND;;1
		// - COBBLESTONE;;1
		// level: 0
		// exp: 1.0
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("shape", "SHAPELESS");
		inputMap.put("output", "DIRT;;2");
		inputMap.put("level", 0);
		inputMap.put("exp", 1.0);
		List<String> input = new LinkedList<String>();
		input.add("SAND;;1");
		input.add("COBBLESTONE;;1");
		inputMap.put("input", input);

		// test
		TypedRecipe tr = RecipeFactory.createFromMap(inputMap);

		// verify
		Assert.assertTrue(tr instanceof CustomShapelessRecipe);
		CustomShapelessRecipe recipe = (CustomShapelessRecipe) tr;
		checkItemStack(recipe.getResult(), Material.DIRT, -1, 2);
		Assert.assertEquals(0, recipe.getLevel());
		Assert.assertEquals(1, recipe.getExp(), 0);
		List<ItemStack> ingred = recipe.getIngredientList();
		Assert.assertEquals(2, ingred.size());
		checkItemStack(ingred.get(0), Material.SAND, -1, 1);
		checkItemStack(ingred.get(1), Material.COBBLESTONE, -1, 1);
	}

	@Test
	/** Test for {@link RecipeFactory#stringToStack(String)} */
	public void stringToStack() {
		// test 1 - null
		Assert.assertNull(RecipeFactory.stringToStack(null));

		// test 2 - wrong amount of data
		Assert.assertNull(RecipeFactory.stringToStack(""));
		Assert.assertNull(RecipeFactory.stringToStack(";"));
		Assert.assertNull(RecipeFactory.stringToStack(";;;"));

		// test 3 - wrong amount
		Assert.assertNull(RecipeFactory.stringToStack("1;;one"));

		// test 4 - wrong data
		Assert.assertNull(RecipeFactory.stringToStack("1;data;1"));

		// test 5 - wrong material
		Assert.assertNull(RecipeFactory.stringToStack("PLASTIC;;1"));
		Assert.assertNull(RecipeFactory.stringToStack("752;;1"));

		// test 6a - STONE x 1
		ItemStack stack = RecipeFactory.stringToStack("STONE;;1");
		Assert.assertEquals(Material.STONE, stack.getType());
		Assert.assertEquals(-1, stack.getDurability());
		Assert.assertEquals(1, stack.getAmount());
		// test 6b - STONE(1) x 1
		stack = RecipeFactory.stringToStack("1;0;2");
		Assert.assertEquals(Material.STONE, stack.getType());
		Assert.assertEquals(0, stack.getDurability());
		Assert.assertEquals(2, stack.getAmount());

		// test 7a - RED(14) WOOL x 3
		stack = RecipeFactory.stringToStack("WOOL;14;3");
		Assert.assertEquals(Material.WOOL, stack.getType());
		Assert.assertEquals(14, stack.getDurability());
		Assert.assertEquals(3, stack.getAmount());
		// test 7b - RED(14) WOOL(35) x 4
		stack = RecipeFactory.stringToStack("35;14;4");
		Assert.assertEquals(Material.WOOL, stack.getType());
		Assert.assertEquals(14, stack.getDurability());
		Assert.assertEquals(4, stack.getAmount());

		// test 8a - WOOL as ingred, color does not matter
		stack = RecipeFactory.stringToStack("WOOL;;5");
		Assert.assertEquals(Material.WOOL, stack.getType());
		Assert.assertEquals(-1, stack.getDurability());
		Assert.assertEquals(5, stack.getAmount());
		// test 8b - WOOL(35) as ingred, color does not matter
		stack = RecipeFactory.stringToStack("35;-1;6");
		Assert.assertEquals(Material.WOOL, stack.getType());
		Assert.assertEquals(-1, stack.getDurability());
		Assert.assertEquals(6, stack.getAmount());
	}

}
