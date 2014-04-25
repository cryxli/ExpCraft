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

import java.util.Locale;

import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.minecraft.acl.Language;

import org.bukkit.command.CommandSender;

/**
 * Implementation of {@link AbstractModuleTranslator} that delegates the work to
 * the bukkit plugin <code>Language</code>.
 * 
 * @author cryxli
 */
public class AclModuleTranslation extends AbstractModuleTranslator {

	/** Reference to the plugin translator */
	private final Language lng;

	/** Create an instance. */
	public AclModuleTranslation(final AbstractTranslator parent,
			final ExpCraftModule module) {
		super(parent, module);
		// create an instance of the translator from the plugin
		// using the second factory method, since this module is not a bukkit
		// plugin.
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
			// key was not found, try parent translator
			trans = parent.translate(sender, msgKey, arguments);
		}
		return trans;
	}

	@Override
	public String translateModuleName(final Locale locale) {
		final String text = lng.translate(locale, MOD_NAME_KEY);
		if (text.startsWith("XXX ") && text.endsWith(" XXX")) {
			// key was not found, return internal module name instead
			return moduleName;
		} else {
			return text;
		}
	}
}
