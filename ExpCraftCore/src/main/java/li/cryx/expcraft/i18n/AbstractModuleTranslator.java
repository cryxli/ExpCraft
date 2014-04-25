package li.cryx.expcraft.i18n;

import java.util.Locale;

import li.cryx.expcraft.module.ExpCraftModule;

// TODO doc
public abstract class AbstractModuleTranslator extends AbstractTranslator {

	protected static final String MOD_NAME_KEY = "module.name";

	public static AbstractModuleTranslator create(
			final AbstractTranslator parent, final ExpCraftModule module) {
		if (parent.isMultiLingual()) {
			return new AclModuleTranslation(parent, module);
		} else {
			return new FallbackModuleTranslation(parent, module);
		}
	}

	protected final AbstractTranslator parent;

	protected String moduleName;

	public AbstractModuleTranslator(final AbstractTranslator parent,
			final ExpCraftModule module) {
		this.parent = parent;
		moduleName = module.getInfo().getName();
	}

	public abstract String translateModuleName(Locale locale);

	// TODO

}
