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

import li.cryx.expcraft.ExpCraft;
import li.cryx.expcraft.module.ExpCraftModule;

import org.bukkit.command.CommandSender;

/**
 * This class defines the behaviour of the translation factory used by
 * {@link ExpCraft} itself.
 * 
 * @author cryxli
 */
public abstract class AbstractTranslator {

	/**
	 * Get the language of the given <code>CommandSender</code>
	 * 
	 * @param sender
	 *            The console sender or player.
	 * @return A locale specifying the player's language.
	 */
	public abstract Locale getLanguage(CommandSender sender);

	/**
	 * Indicator whether this implementation supports multiple languages.
	 * 
	 * @return <code>true</code>: multiple languages are supported.
	 */
	public abstract boolean isMultiLingual();

	/**
	 * Translate a given text, apply the optional arguments and send the
	 * resulting string to the player.
	 * 
	 * @param sender
	 *            The receiver of the message.
	 * @param msgKey
	 *            The key or text that should be translated.
	 * @param arguments
	 *            Optional arguments that will be applied to the translated
	 *            text.
	 */
	public abstract void sendMessage(CommandSender sender, final String msgKey,
			final Object... arguments);

	/**
	 * Get the translated name of the indicated {@link ExpCraftModule} if the
	 * sender's language.
	 * 
	 * @param sender
	 *            The receiver of the message.
	 * @param module
	 *            A ExpCraft module.
	 * @return Translated name of the module.
	 */
	public String translate(final CommandSender sender,
			final ExpCraftModule module) {
		Locale locale = getLanguage(sender);
		return module.getTranslatedName(locale);
	}

	/**
	 * Translate a given text, apply the optional arguments and return the
	 * resulting string.
	 * 
	 * @param sender
	 *            The receiver of the message.
	 * @param msgKey
	 *            The key or text that should be translated.
	 * @param arguments
	 *            Optional arguments that will be applied to the translated
	 *            text.
	 * @return The translated text.
	 */
	public abstract String translate(final CommandSender sender,
			final String msgKey, final Object... arguments);

}
