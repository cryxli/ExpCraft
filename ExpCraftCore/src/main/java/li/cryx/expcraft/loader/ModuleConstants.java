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
package li.cryx.expcraft.loader;

/**
 * TODO documentation
 * 
 * @author cryxli
 */
public interface ModuleConstants {

	/** Property keys in a <code>config/info.properties</code> file. */
	public static final class PROP {
		/** Key to the display name of the module. */
		public static final String MODULE_NAME = "ec.module.name";
		/** Key to the abbreviation of the module. */
		public static final String MODULE_ABBR = "ec.module.abbr";
		/** Fully qualified name of the main class of the module. */
		public static final String MAIN_CLASS = "ec.main.class";
		// TODO documentation
		public static final String MODULE_VERSION = "ec.module.version";
	}

	/**
	 * Location and name of the info properties file describing an expcraft
	 * module.
	 */
	String INFO_FILE = "config/info.properties";

	/**
	 * Location and name of the default configuration file of an expcraft
	 * module.
	 */
	String DEFAULT_CONFIG = "config/{0}.properties";

}
