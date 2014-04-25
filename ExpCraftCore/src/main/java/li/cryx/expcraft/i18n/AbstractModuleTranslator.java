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

/**
 * This class is the translation factory for a {@link ExpCraftModule}. It is
 * created for each module sepeartely by the core and injected at startup.
 * 
 * @author cryxli
 */
public abstract class AbstractModuleTranslator extends AbstractTranslator {

	/** The language key corresponding to the module's name */
	protected static final String MOD_NAME_KEY = "module.name";

	/**
	 * Factory method to create a new instance of
	 * <code>AbstractModuleTranslator</code> that matches and uses the given
	 * <code>AbstractTranslator</code> as a fallback.
	 * 
	 * @param parent
	 *            Parent translator instance.
	 * @param module
	 *            The module for which this translator is created.
	 * @return An appropriate instance of a
	 *         <code>AbstractModuleTranslator</code>.
	 */
	public static AbstractModuleTranslator create(
			final AbstractTranslator parent, final ExpCraftModule module) {
		if (parent.isMultiLingual()) {
			// multiple languages are supported
			return new AclModuleTranslation(parent, module);
		} else {
			// only the default language is supported
			return new FallbackModuleTranslation(parent, module);
		}
	}

	/** Reference to the parent translator. */
	protected final AbstractTranslator parent;

	/** The default name of the module for which this translator was created. */
	protected String moduleName;

	/**
	 * Create a new instance.
	 * 
	 * @see #create(AbstractTranslator, ExpCraftModule)
	 */
	protected AbstractModuleTranslator(final AbstractTranslator parent,
			final ExpCraftModule module) {
		this.parent = parent;
		moduleName = module.getInfo().getName();
	}

	/**
	 * Translate the module name into the given language.
	 * 
	 * @param locale
	 *            Target language.
	 * @return Translated module name, or, the default module name.
	 */
	public abstract String translateModuleName(Locale locale);

}
