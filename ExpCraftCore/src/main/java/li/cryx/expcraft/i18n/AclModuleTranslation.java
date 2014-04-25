package li.cryx.expcraft.i18n;

import java.util.Locale;

import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.minecraft.acl.Language;

import org.bukkit.command.CommandSender;

public class AclModuleTranslation extends AbstractModuleTranslator {

	private final Language lng;

	public AclModuleTranslation(final AbstractTranslator parent,
			final ExpCraftModule module) {
		super(parent, module);
		lng = Language.getInstanceFor(module.getClass().getClassLoader());
	}

	@Override
	public Locale getLanguage(final CommandSender sender) {
		return Language.getLanguage(sender);
	}

	@Override
	public boolean isMultiLingual() {
		return true;
	}

	@Override
	public void sendMessage(final CommandSender sender, final String msgKey,
			final Object... arguments) {
		sender.sendMessage(translate(sender, msgKey, arguments));
	}

	@Override
	public String translate(final CommandSender sender, final String msgKey,
			final Object... arguments) {
		String trans = lng.translate(sender, msgKey, arguments);
		if (trans.startsWith("XXX ") && trans.endsWith(" XXX")) {
			trans = parent.translate(sender, msgKey, arguments);
		}
		return trans;
	}

	@Override
	public String translateModuleName(final Locale locale) {
		final String text = lng.translate(locale, MOD_NAME_KEY);
		if (text.startsWith("XXX ") && text.endsWith(" XXX")) {
			return moduleName;
		} else {
			return text;
		}
	}
}
