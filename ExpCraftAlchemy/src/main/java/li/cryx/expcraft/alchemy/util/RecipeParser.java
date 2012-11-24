package li.cryx.expcraft.alchemy.util;

import java.util.LinkedList;
import java.util.List;

import li.cryx.expcraft.alchemy.recipe.TypedRecipe;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * SAX handler to parse a XML containing recipes as defined by
 * <code>recipe.xsd</code>.
 * 
 * @author cryxli
 */
public class RecipeParser extends DefaultHandler {

	/** List of all loaded recipes, so far. */
	private final List<TypedRecipe> recipes = new LinkedList<TypedRecipe>();

	/** current recipe */
	private GenericRecipe recipe;

	@Override
	public void endElement(final String uri, final String localName,
			final String qName) throws SAXException {
		String tag = qName.toLowerCase();

		if (recipe != null) {
			if ("shaped".equals(tag) || "shapeless".equals(tag)
					|| "furnace".equals(tag)) {
				TypedRecipe typedRecipe = recipe.toTypedRecipe();
				if (typedRecipe != null) {
					recipes.add(typedRecipe);
				}
				recipe = null;
			}
		}
	}

	/**
	 * Get the parsed recipes.
	 * 
	 * @return A list of TypesRecipes. This list is never <code>null</code>, but
	 *         can be empty.
	 */
	public List<TypedRecipe> getRecipes() {
		return recipes;
	}

	/**
	 * Parse an &lt;item&gt; or &lt;input&gt; tag.
	 * 
	 * @param attr
	 *            Attribute list of the current tag.
	 * @return An <code>ItemStack</code> representing the specified item, or,
	 *         <code>null</code>.
	 */
	private ItemStack parseItem(final Attributes attr) {
		String s = attr.getValue("item");
		if (s == null) {
			return null;
		}
		Material material = Material.matchMaterial(s);
		if (material == null) {
			// try block ID and set default
			material = Material.getMaterial(s);
		}
		if (material == null) {
			return new ItemStack(Material.AIR);
		}

		s = attr.getValue("amount");
		int amount = 1;
		if (s != null) {
			try {
				amount = Integer.parseInt(s);
			} catch (NumberFormatException e) {
			}
		}

		s = attr.getValue("data");
		short data = 0;
		if (s != null) {
			try {
				data = Short.parseShort(s);
			} catch (NumberFormatException e) {
			}
		}

		return new ItemStack(material, amount, data);
	}

	/**
	 * Parse an &lt;output&gt; tag.
	 * 
	 * @param attr
	 *            Attribute list of the current tag.
	 */
	private void parseOutput(final Attributes attr) {
		ItemStack stack = parseItem(attr);

		if (stack == null) {
			recipe = null;
		} else {
			recipe.setResult(stack);
		}
	}

	/**
	 * Extract <code>exp</code> and <code>level</code> attributes from the given
	 * tag.
	 * 
	 * @param attr
	 *            Attribute list of the current tag.
	 */
	private void parseRecipeAttributes(final Attributes attr) {
		if (recipe == null) {
			return;
		}

		String s = attr.getValue("level");
		if (s == null) {
			recipe.setLevel(0);
		} else {
			try {
				recipe.setLevel(Integer.parseInt(s));
			} catch (NumberFormatException e) {
				recipe.setLevel(0);
			}
		}

		s = attr.getValue("exp");
		if (s == null) {
			recipe.setExp(1);
		} else {
			try {
				recipe.setExp(Double.parseDouble(s));
			} catch (NumberFormatException e) {
				recipe.setExp(1);
			}
		}
	}

	/**
	 * Extract <code>width</code> and <code>height</code> attributes of a shaped
	 * recipe definition.
	 * 
	 * @param attr
	 *            Attribute list of the current tag.
	 */
	private void parseShapedRecipeAttributes(final Attributes attr) {
		String s = attr.getValue("width");
		if (s != null) {
			try {
				recipe.setWidth(Integer.parseInt(s));
			} catch (NumberFormatException e) {
			}
		}

		s = attr.getValue("height");
		if (s != null) {
			try {
				recipe.setHeight(Integer.parseInt(s));
			} catch (NumberFormatException e) {
			}
		}
	}

	@Override
	public void startElement(final String uri, final String localName,
			final String qName, final Attributes attr) throws SAXException {
		String tag = qName.toLowerCase();

		if (recipe == null) {
			if ("shaped".equals(tag)) {
				recipe = new GenericRecipe(RecipeType.SHAPED);
				parseShapedRecipeAttributes(attr);
				parseRecipeAttributes(attr);
			} else if ("shapeless".equals(tag)) {
				recipe = new GenericRecipe(RecipeType.SHAPELESS);
				parseRecipeAttributes(attr);
			} else if ("furnace".equals(tag)) {
				recipe = new GenericRecipe(RecipeType.FURNACE);
				// TODO parse exp and level, once they can be intercepted
				// parseRecipeAttributes(attr);
			}
		} else {
			if ("output".equals(tag)) {
				parseOutput(attr);
			} else if ("item".equals(tag) || "input".equals(tag)) {
				ItemStack stack = parseItem(attr);
				recipe.addIngred(stack);
			}
		}
	}

}
