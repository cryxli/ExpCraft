/*
 * Copyright (c) 2011 Urs P. Stettler, https://github.com/cryxli
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package li.cryx.expcraft.i18n;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import li.cryx.expcraft.module.ExpCraftModule;

import org.bukkit.command.CommandSender;

/**
 * An implementation to lookup translation keys from message bundles (properties
 * files).
 * 
 * @author cryxli
 */
public class FallbackModuleTranslation extends AbstractModuleTranslator {

	/** ClassLoader of the {@link ExpCraftModule} */
	private final ClassLoader classLoader;

	/**
	 * Create a translation factory for the given module.
	 * 
	 * @param parent
	 *            The parent translation factory.
	 * @param module
	 *            THe module
	 */
	public FallbackModuleTranslation(final AbstractTranslator parent,
			final ExpCraftModule module) {
		super(parent, module);
		classLoader = module.getClass().getClassLoader();
	}

	/** Get the resource bundle */
	private ResourceBundle getBundle() {
		try {
			return ResourceBundle.getBundle(
					FallbackTranslation.TRANSLATE_BASE_NAME,
					FallbackTranslation.DEFAULT_LOCALE, classLoader);
		} catch (MissingResourceException e) {
			// not found, create a dummy bundle
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
