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

import java.io.File;
import java.util.Properties;

import li.cryx.expcraft.module.DropExpCraftModule;
import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.util.StringUtils;

/**
 * When an ExpCraft module is running, this class contains information about it.
 * Before a module is started, this information is gathered from the module's
 * <code>config/info.properties</code> ({@link ModuleConstants#INFO_FILE}).
 * 
 * @author cryxli
 */
public class ModuleInfo {

	/** Display name of the module. */
	private String name;

	/** Abbreviation of the module. */
	private String abbr;

	/** Version of the module. */
	private String version;

	/** Location of the JAR containing the module. */
	private File jar;

	/**
	 * Fully qualified name of the module's main class. That's the one class
	 * extending {@link ExpCraftModule} or {@link DropExpCraftModule}.
	 */
	private String main;

	/** Reference to the instantiated main class of the module. */
	private ExpCraftModule module;

	/**
	 * Since the module JAR is not part of the VM's class path, it needs a
	 * separate ClassLoader to handle its resources.
	 */
	private ClassLoader loader;

	/** Create an empty module description. */
	public ModuleInfo() {
	}

	/**
	 * Create a module description and extract the values from the given
	 * <code>info.properties</code>.
	 * 
	 * @param moduleJarFile
	 *            Location of the JAR file.
	 * @param infoProp
	 *            The <code>info.properties</code> from within the JAR file.
	 */
	public ModuleInfo(final File moduleJarFile, final Properties infoProp) {
		this(moduleJarFile,//
				infoProp.getProperty(ModuleConstants.PROP.MODULE_NAME), //
				infoProp.getProperty(ModuleConstants.PROP.MODULE_ABBR), //
				infoProp.getProperty(ModuleConstants.PROP.MAIN_CLASS) //
		);
		setVersion(infoProp.getProperty(ModuleConstants.PROP.MODULE_VERSION));
	}

	/**
	 * Create a module description from the mandatory values.
	 * 
	 * @param moduleJarFile
	 *            Location of the JAR file.
	 * @param moduleName
	 *            Display name of the module.
	 * @param moduleAbbreviation
	 *            Abbreviation of the module.
	 * @param fullyQualifiedMainClass
	 *            Fully qualified name of the module's main class.
	 */
	public ModuleInfo(final File moduleJarFile, final String moduleName,
			final String moduleAbbreviation,
			final String fullyQualifiedMainClass) {
		setJar(moduleJarFile);
		setName(moduleName);
		setAbbr(moduleAbbreviation);
		setMain(fullyQualifiedMainClass);
	}

	/**
	 * Create a module description and extract the values from the given
	 * <code>info.properties</code>.
	 * 
	 * @param infoProp
	 *            The <code>info.properties</code> from within the JAR file.
	 */
	public ModuleInfo(final Properties infoProp) {
		this(null, infoProp);
	}

	/**
	 * Create a module description from the mandatory values.
	 * 
	 * @param moduleName
	 *            Display name of the module.
	 * @param moduleAbbreviation
	 *            Abbreviation of the module.
	 * @param fullyQualifiedMainClass
	 *            Fully qualified name of the module's main class.
	 */
	public ModuleInfo(final String moduleName, final String moduleAbbreviation,
			final String fullyQualifiedMainClass) {
		this(null, moduleName, moduleAbbreviation, fullyQualifiedMainClass);
	}

	public String getAbbr() {
		return abbr;
	}

	/**
	 * Get the full name of the module.
	 * 
	 * @return Returns the concatenated name and version of the module.
	 */
	public String getFullName() {
		return getName() + " " + getVersion();
	}

	public File getJar() {
		return jar;
	}

	public ClassLoader getLoader() {
		return loader;
	}

	public String getMain() {
		return main;
	}

	public ExpCraftModule getModule() {
		return module;
	}

	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}

	/**
	 * Test whether a module has been successfully instantiated.
	 * 
	 * @return <code>true</code>, if the description
	 *         {@link ModuleInfo#isValid()} and {@link #loader} and
	 *         {@link #module} are set.
	 * @see ModuleLoader
	 */
	public boolean isInitialised() {
		return loader != null && module != null && isValid();
	}

	/**
	 * Test whether this description point to a valid ExpCraft module JAR.
	 * <p>
	 * Only if a module JAR passes this test a class loader is created and the
	 * module's main class is instantiated.
	 * </p>
	 * 
	 * @return <code>true</code>, if {@link #name}, {@link #abbr} and
	 *         {@link #main} are set and {@link #jar} points to an existing
	 *         file.
	 * @see JarScanner
	 */
	public boolean isValid() {
		return StringUtils.INSTANCE.isNotBlank(name) //
				&& StringUtils.INSTANCE.isNotBlank(abbr) //
				&& StringUtils.INSTANCE.isNotBlank(main) //
				&& jar != null && jar.exists();
	}

	public void setAbbr(final String abbr) {
		this.abbr = abbr;
	}

	public void setJar(final File jar) {
		this.jar = jar;
	}

	public void setLoader(final ClassLoader loader) {
		this.loader = loader;
	}

	public void setMain(final String main) {
		this.main = main;
	}

	public void setModule(final ExpCraftModule module) {
		this.module = module;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setVersion(final String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(getClass().getSimpleName());
		buf.append("={");
		buf.append("\"name\"=");
		if (name == null) {
			buf.append("null");
		} else {
			buf.append('"').append(name).append('"');
		}
		buf.append(',');
		buf.append("\"abbr\"=");
		if (abbr == null) {
			buf.append("null");
		} else {
			buf.append('"').append(abbr).append('"');
		}
		buf.append(',');
		buf.append("\"version\"=");
		if (version == null) {
			buf.append("null");
		} else {
			buf.append('"').append(version).append('"');
		}
		buf.append(',');
		buf.append("\"jar\"=");
		if (jar == null) {
			buf.append("null");
		} else {
			buf.append('"').append(jar).append('"');
		}
		buf.append(',');
		buf.append("\"main\"=");
		if (main == null) {
			buf.append("null");
		} else {
			buf.append('"').append(main).append('"');
		}
		buf.append(',');
		buf.append("\"loader\"=");
		if (loader == null) {
			buf.append("null");
		} else {
			buf.append('"').append(loader).append('"');
		}
		buf.append(',');
		buf.append("\"module\"=");
		if (module == null) {
			buf.append("null");
		} else {
			buf.append('"').append(module).append('"');
		}
		buf.append('}');
		return buf.toString();
	}

}
