package li.cryx.expcraft;

import li.cryx.expcraft.module.ExpCraftModule;

import org.bukkit.entity.Player;

public class DummyModule extends ExpCraftModule {

	@Override
	public void displayInfo(final Player sender) {
		sender.sendMessage("Module info here");
	}

	@Override
	public void onDisable() {
	}

	@Override
	public void onEnable() {
	}

}
