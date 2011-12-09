package li.cryx.expcraft;

import java.io.File;

import org.bukkit.plugin.PluginDescriptionFile;

public class DummyExpCraftCore extends ExpCraftCore {
	public DummyExpCraftCore(final DummyServer server,
			final PluginDescriptionFile pdf) {
		super();
		initialize(null, server, pdf, new File("target/plugins/test"), null,
				getClass().getClassLoader());
	}
}
