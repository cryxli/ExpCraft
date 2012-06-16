package li.cryx.expcraft.alchemy;

import java.util.logging.Logger;

import li.cryx.expcraft.alchemy.recipe.TypedRecipe;
import li.cryx.expcraft.util.Recipes;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class CraftingListener implements Listener {

	private static final int SENTINEL_MAX_STACK_SIZE = 65;

	private static final Logger LOG = Logger.getLogger(CraftingListener.class
			.getName());

	/** Reference to module. */
	private final Alchemy plugin;

	public CraftingListener(final Alchemy alchemy) {
		plugin = alchemy;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onCrafting(final CraftItemEvent event) {
		if (!(event.getWhoClicked() instanceof Player) || event.isCancelled()) {
			// unknown event
			return;
		}
		Player player = (Player) event.getWhoClicked();
		boolean hasPlugin = plugin.getPermission().hasLevel(plugin, player);

		TypedRecipe recipe = plugin
				.getRecipe(Recipes.unturn(event.getRecipe()));

		if (!hasPlugin && recipe.isModuleRecipe()) {
			event.setCancelled(true);
			plugin.warnPlugin(player);
			LOG.fine("module recipe, doesn't have module permissions");

		} else if (hasPlugin) {
			int level = plugin.getPersistence().getLevel(plugin, player);

			if (level < recipe.getLevel()) {
				event.setCancelled(true);
				plugin.warnLevel(player, recipe.getLevel());
				LOG.fine("module recipe, level not met");

			} else {
				LOG.fine("module recipe");

				double multiplyer = 1.0;
				if (event.isShiftClick()) {
					multiplyer = shiftAnalysis(event);
				}
				plugin.getPersistence().addExp(plugin, player,
						multiplyer * recipe.getExp());
			}
		}
	}

	/**
	 * Count how many times the recipe is created when shift-clicking.
	 * 
	 * @param event
	 *            The crafting event.
	 * @return The times the original recipe is executed when shift-clicking.
	 */
	private double shiftAnalysis(final CraftItemEvent event) {
		InventoryView iv = event.getView();

		int max = 0;
		switch (iv.getType()) {
		case WORKBENCH:
		case CRAFTING:
			max = iv.getType().getDefaultSize();
			break;
		case FURNACE:
		default:
			return 1;
		}

		// find itemstack with lowest amount on the current crafting grid
		int count = SENTINEL_MAX_STACK_SIZE;
		for (int i = 1; i <= max; i++) {
			ItemStack stack = iv.getItem(i);
			if (stack != null && stack.getAmount() > 0
					&& stack.getType() != Material.AIR
					&& stack.getAmount() < count) {
				// empty places equal to AIR blocks
				count = stack.getAmount();
			}
		}

		if (count < SENTINEL_MAX_STACK_SIZE) {
			return count;
		} else {
			return 1;
		}
	}
}
