package li.cryx.expcraft.i18n;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import li.cryx.expcraft.module.ExpCraftModule;

import org.bukkit.command.CommandSender;

public class FallbackModuleTranslation extends AbstractModuleTranslator {

	private final ClassLoader classLoader;

	public FallbackModuleTranslation(final AbstractTranslator parent,
			final ExpCraftModule module) {
		super(parent, module);
		classLoader = module.getClass().getClassLoader();
	}

	private ResourceBundle getBundle() {
		try {
			return ResourceBundle.getBundle(
					FallbackTranslation.TRANSLATE_BASE_NAME,
					FallbackTranslation.DEFAULT_LOCALE, classLoader);
		} catch (MissingResourceException e) {
			return new ResourceBundle() {
				@Override
				public Enumeration<String> getKeys() {
					return new Hashtable<String, String>().elements();
				}

				@Override
				protected Object handleGetObject(final String key) {
					return null;
				}
			};
		}
	}

	@Override
	public Locale getLanguage(final CommandSender sender) {
		return FallbackTranslation.DEFAULT_LOCALE;
	}

	@Override
	public boolean isMultiLingual() {
		return false;
	}

	@Override
	public void sendMessage(final CommandSender sender, final String msgKey,
			final Object... arguments) {
		sender.sendMessage(translate(sender, msgKey, arguments));
	}

	@Override
	public String translate(final CommandSender sender, final String msgKey,
			final Object... arguments) {
		ResourceBundle bundle = getBundle();
		String text;
		if (bundle.containsKey(msgKey)) {
			text = MessageFormat.format(bundle.getString(msgKey), arguments);
		} else {
			text = parent.translate(sender, msgKey, arguments);
		}
		return text;
	}

	@Override
	public String translateModuleName(final Locale locale) {
		ResourceBundle bundle = getBundle();
		if (bundle.containsKey(MOD_NAME_KEY)) {
			return bundle.getString(MOD_NAME_KEY);
		} else {
			return moduleName;
		}
	}

}
