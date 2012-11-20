package li.cryx.expcraft;

import java.io.File;

import li.cryx.expcraft.module.ExpCraftModule;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

public class DummyModule extends ExpCraftModule {

	private String name;

	private String abbr;

	public DummyModule(final String name, final String abbr) {
		this.name = name;
		this.abbr = abbr;
	}

	public DummyModule(final String name, final String abbr,
			final Server server, final PluginDescriptionFile pdf) {
		this(name, abbr);
		initialize(null, //
				server, //
				pdf, //
				new File("target/plugins/test"), //
				null, //
				getClass().getClassLoader());
	}

	@Override
	public void displayInfo(final Player sender, final int page) {
		sender.sendMessage("Module info here, page: " + page);
	}

	@Override
	public String getAbbr() {
		return abbr;
	}

	@Override
	public String getModuleName() {
		return name;
	}

	@Override
	public void onModuleDisable() {
	}

	@Override
	protected void onModuleEnable() {
	}

	public void setAbbr(final String abbr) {
		this.abbr = abbr;
	}

	public void setName(final String name) {
		this.name = name;
	}

}
