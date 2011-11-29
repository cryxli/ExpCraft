package li.cryx.expcraft;

import li.cryx.expcraft.module.ExpCraftModule;

public class DummyModule extends ExpCraftModule {

	private String name;

	private String abbr;

	public DummyModule(final String name, final String abbr) {
		this.name = name;
		this.abbr = abbr;
	}

	@Override
	public String getAbbr() {
		return abbr;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void onDisable() {
	}

	@Override
	public void onEnable() {
	}

	public void setAbbr(final String abbr) {
		this.abbr = abbr;
	}

	public void setName(final String name) {
		this.name = name;
	}

}