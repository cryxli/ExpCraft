package li.cryx.expcraft.i18n;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.bukkit.command.CommandSender;

/**
 * An implementation to lookup translation keys from message bundles (properties
 * files).
 * 
 * @author cryxli
 */
public class FallbackTranslation extends AbstractTranslator {

	static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

	static final String TRANSLATE_BASE_NAME = "lang/message";

	private ResourceBundle getBundle() {
		try {
			return ResourceBundle.getBundle(TRANSLATE_BASE_NAME);
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
		return DEFAULT_LOCALE;
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
			text = bundle.getString(msgKey);
		} else {
			text = "XXX " + msgKey + " XXX";
		}
		return MessageFormat.format(text, arguments);
	}

}
