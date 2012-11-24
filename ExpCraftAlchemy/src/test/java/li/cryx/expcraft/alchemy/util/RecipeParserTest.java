package li.cryx.expcraft.alchemy.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import junit.framework.Assert;
import li.cryx.expcraft.alchemy.recipe.CustomFurnaceRecipe;
import li.cryx.expcraft.alchemy.recipe.CustomShapedRecipe;
import li.cryx.expcraft.alchemy.recipe.CustomShapelessRecipe;
import li.cryx.expcraft.alchemy.recipe.TypedRecipe;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * Verify {@link RecipeParser}'s functionality.
 * 
 * @author cryxli
 */
public class RecipeParserTest {

	private void checkItemStack(final ItemStack stack, final Material material,
			final int data, final int amount) {
		Assert.assertEquals(material, stack.getType());
		Assert.assertEquals(data, stack.getDurability());
		Assert.assertEquals(amount, stack.getAmount());
	}

	@Test
	public void furnaceRecipeDefaults() {
		// prepare
		StringBuffer buf = new StringBuffer();
		buf.append("<furnace>");
		buf.append("	<!-- smelt buckets into iron ingots -->");
		buf.append("	<output item=\"IRON_INGOT\" amount=\"3\" />");
		buf.append("	<input item=\"BUCKET\" />");
		buf.append("</furnace>");

		// test
		List<TypedRecipe> recipes = parse(buf.toString());

		// verify
		Assert.assertEquals(1, recipes.size());
		Assert.assertEquals(RecipeType.FURNACE, recipes.get(0).getType());
		CustomFurnaceRecipe r = (CustomFurnaceRecipe) recipes.get(0);
		Assert.assertEquals(1.0, r.getExp(), 0);
		Assert.assertEquals(0, r.getLevel());
		checkItemStack(r.getResult(), Material.IRON_INGOT, 0, 3);
		checkItemStack(r.getInput(), Material.BUCKET, 0, 1);
	}

	@Test
	public void furnaceRecipeEverything() {
		// prepare
		StringBuffer buf = new StringBuffer();
		buf.append("<furnace exp=\"15\" level=\"50\">");
		buf.append("	<!-- smelt buckets into iron ingots -->");
		buf.append("	<output item=\"IRON_INGOT\" amount=\"3\" data=\"4\" />");
		buf.append("	<input item=\"BUCKET\" amount=\"2\" data=\"5\" />");
		buf.append("</furnace>");

		// test
		List<TypedRecipe> recipes = parse(buf.toString());

		// verify
		Assert.assertEquals(1, recipes.size());
		Assert.assertEquals(RecipeType.FURNACE, recipes.get(0).getType());
		CustomFurnaceRecipe r = (CustomFurnaceRecipe) recipes.get(0);
		Assert.assertEquals(1.0, r.getExp(), 0);
		Assert.assertEquals(0, r.getLevel());
		checkItemStack(r.getResult(), Material.IRON_INGOT, 4, 3);
		checkItemStack(r.getInput(), Material.BUCKET, 5, 1);
	}

	@Test
	public void nothing() {
		// test
		List<TypedRecipe> recipes = parse("");

		// verify
		Assert.assertNotNull(recipes);
		Assert.assertEquals(0, recipes.size());
	}

	private List<TypedRecipe> parse(final InputStream stream) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setNamespaceAware(false);
			SAXParser saxParser = factory.newSAXParser();
			RecipeParser handler = new RecipeParser();
			saxParser.parse(stream, handler);
			return handler.getRecipes();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		}
	}

	private List<TypedRecipe> parse(final String stream) {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><recipe>"
				+ stream + "</recipe>";
		return parse(new ByteArrayInputStream(xml.getBytes(Charset
				.forName("UTF-8"))));
	}

	@Test
	public void shapedRecipeDefaults() {
		// prepare
		StringBuffer buf = new StringBuffer();
		buf.append("<shaped height=\"2\" width=\"3\">");
		buf.append("	<output item=\"CHAINMAIL_BOOTS\" />");
		buf.append("	<inputs>");
		buf.append("		<item item=\"STONE\" />");
		buf.append("		<item item=\"AIR\" />");
		buf.append("		<item item=\"STONE\" />");
		buf.append("		<item item=\"STONE\" />");
		buf.append("		<item item=\"AIR\" />");
		buf.append("		<item item=\"STONE\" />");
		buf.append("	</inputs>");
		buf.append("</shaped>");

		// test
		List<TypedRecipe> recipes = parse(buf.toString());

		// verify
		Assert.assertEquals(1, recipes.size());
		Assert.assertEquals(RecipeType.SHAPED, recipes.get(0).getType());
		CustomShapedRecipe r = (CustomShapedRecipe) recipes.get(0);
		Assert.assertEquals(1.0, r.getExp(), 0);
		Assert.assertEquals(0, r.getLevel());
		checkItemStack(r.getResult(), Material.CHAINMAIL_BOOTS, 0, 1);

		String[] shape = r.getShape();
		Assert.assertNotNull(shape);
		Assert.assertEquals(2, shape.length); // height
		Assert.assertEquals(3, shape[0].length()); // width
		Assert.assertEquals(3, shape[1].length()); // width
		Map<Character, ItemStack> map = r.getIngredientMap();
		char ch = shape[0].charAt(0);
		checkItemStack(map.get(ch), Material.STONE, 0, 1);
		ch = shape[0].charAt(1);
		Assert.assertNull(map.get(ch));
		ch = shape[0].charAt(2);
		checkItemStack(map.get(ch), Material.STONE, 0, 1);
		ch = shape[1].charAt(0);
		checkItemStack(map.get(ch), Material.STONE, 0, 1);
		ch = shape[1].charAt(1);
		Assert.assertNull(map.get(ch));
		ch = shape[1].charAt(2);
		checkItemStack(map.get(ch), Material.STONE, 0, 1);
	}

	@Test
	public void shapedRecipeEverything() {
		// prepare
		StringBuffer buf = new StringBuffer();
		buf.append("<shaped height=\"2\" width=\"3\" exp=\"4.0\" level=\"10\">");
		buf.append("	<output item=\"CHAINMAIL_BOOTS\" amount=\"11\" data=\"12\" />");
		buf.append("	<inputs>");
		buf.append("		<item item=\"STONE\" amount=\"13\" data=\"14\" />");
		buf.append("		<item item=\"AIR\" amount=\"15\" data=\"16\" />");
		buf.append("		<item item=\"STONE\" amount=\"17\" data=\"18\" />");
		buf.append("		<item item=\"STONE\" amount=\"19\" data=\"20\" />");
		buf.append("		<item item=\"AIR\" amount=\"21\" data=\"22\" />");
		buf.append("		<item item=\"STONE\" amount=\"23\" data=\"24\" />");
		buf.append("	</inputs>");
		buf.append("</shaped>");

		// test
		List<TypedRecipe> recipes = parse(buf.toString());

		// verify
		Assert.assertEquals(1, recipes.size());
		Assert.assertEquals(RecipeType.SHAPED, recipes.get(0).getType());
		CustomShapedRecipe r = (CustomShapedRecipe) recipes.get(0);
		Assert.assertEquals(4.0, r.getExp(), 0);
		Assert.assertEquals(10, r.getLevel());
		checkItemStack(r.getResult(), Material.CHAINMAIL_BOOTS, 12, 11);

		String[] shape = r.getShape();
		Assert.assertNotNull(shape);
		Assert.assertEquals(2, shape.length); // height
		Assert.assertEquals(3, shape[0].length()); // width
		Assert.assertEquals(3, shape[1].length()); // width
		Map<Character, ItemStack> map = r.getIngredientMap();
		char ch = shape[0].charAt(0);
		checkItemStack(map.get(ch), Material.STONE, 14, 1);
		ch = shape[0].charAt(1);
		Assert.assertNull(map.get(ch));
		ch = shape[0].charAt(2);
		checkItemStack(map.get(ch), Material.STONE, 18, 1);
		ch = shape[1].charAt(0);
		checkItemStack(map.get(ch), Material.STONE, 20, 1);
		ch = shape[1].charAt(1);
		Assert.assertNull(map.get(ch));
		ch = shape[1].charAt(2);
		checkItemStack(map.get(ch), Material.STONE, 24, 1);
	}

	@Test
	public void shapedRecipeWithoutSize() {
		// prepare
		StringBuffer buf = new StringBuffer();
		buf.append("<shaped>");
		buf.append("	<output item=\"CHAINMAIL_BOOTS\" />");
		buf.append("	<inputs>");
		buf.append("		<item item=\"STONE\" />");
		buf.append("		<item item=\"AIR\" />");
		buf.append("		<item item=\"STONE\" />");
		buf.append("		<item item=\"STONE\" />");
		buf.append("		<item item=\"AIR\" />");
		buf.append("		<item item=\"STONE\" />");
		buf.append("	</inputs>");
		buf.append("</shaped>");

		// test
		List<TypedRecipe> recipes = parse(buf.toString());

		// verify
		Assert.assertEquals(0, recipes.size());
	}

	@Test
	public void shapelessRecipeDefaults() {
		// prepare
		StringBuffer buf = new StringBuffer();
		buf.append("<shapeless>");
		buf.append("	<!-- turn cobblestone and dirt into sand -->");
		buf.append("	<output item=\"SAND\" amount=\"2\" />");
		buf.append("	<inputs>");
		buf.append("		<item item=\"COBBLESTONE\" />");
		buf.append("		<item item=\"DIRT\" />");
		buf.append("	</inputs>");
		buf.append("</shapeless>");

		// test
		List<TypedRecipe> recipes = parse(buf.toString());

		// verify
		Assert.assertEquals(1, recipes.size());
		Assert.assertEquals(RecipeType.SHAPELESS, recipes.get(0).getType());
		CustomShapelessRecipe r = (CustomShapelessRecipe) recipes.get(0);
		Assert.assertEquals(1.0, r.getExp(), 0);
		Assert.assertEquals(0, r.getLevel());
		checkItemStack(r.getResult(), Material.SAND, 0, 2);
		Assert.assertEquals(2, r.getIngredientList().size());
		checkItemStack(r.getIngredientList().get(0), Material.COBBLESTONE, 0, 1);
		checkItemStack(r.getIngredientList().get(1), Material.DIRT, 0, 1);
	}

	@Test
	public void shapelessRecipeEverything() {
		// prepare
		StringBuffer buf = new StringBuffer();
		buf.append("<shapeless exp=\"15\" level=\"50\">");
		buf.append("	<!-- turn cobblestone and dirt into sand -->");
		buf.append("	<output item=\"12\" amount=\"2\" data=\"3\" />");
		buf.append("	<inputs>");
		buf.append("		<item item=\"4\" amount=\"6\" data=\"7\" />");
		buf.append("		<item item=\"3\" amount=\"8\" data=\"9\" />");
		buf.append("	</inputs>");
		buf.append("</shapeless>");

		// test
		List<TypedRecipe> recipes = parse(buf.toString());

		// verify
		Assert.assertEquals(1, recipes.size());
		Assert.assertEquals(RecipeType.SHAPELESS, recipes.get(0).getType());
		CustomShapelessRecipe r = (CustomShapelessRecipe) recipes.get(0);
		Assert.assertEquals(15.0, r.getExp(), 0);
		Assert.assertEquals(50, r.getLevel());
		checkItemStack(r.getResult(), Material.SAND, 3, 2);
		Assert.assertEquals(2, r.getIngredientList().size());
		checkItemStack(r.getIngredientList().get(0), Material.COBBLESTONE, 7, 1);
		checkItemStack(r.getIngredientList().get(1), Material.DIRT, 9, 1);
	}
}
