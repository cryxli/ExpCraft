package li.cryx.expcraft.loader;

import java.io.File;
import java.util.Properties;

import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.util.StringUtils;

/**
 * TODO documentation
 * 
 * @author cryxli
 */
public class ModuleInfo {

	private String name;

	private String abbr;

	private File jar;

	private String main;

	private ClassLoader loader;

	private ExpCraftModule module;

	private String version;

	public ModuleInfo() {
	}

	public ModuleInfo(final File moduleJarFile, final Properties prop) {
		this(moduleJarFile,//
				prop.getProperty(ModuleConstants.PROP.MODULE_NAME), //
				prop.getProperty(ModuleConstants.PROP.MODULE_ABBR), //
				prop.getProperty(ModuleConstants.PROP.MAIN_CLASS) //
		);
		setVersion(prop.getProperty(ModuleConstants.PROP.MODULE_VERSION));
	}

	public ModuleInfo(final File moduleJarFile, final String moduleName,
			final String moduleAbbreviation,
			final String fullyQualifiedMainClass) {
		setJar(moduleJarFile);
		setName(moduleName);
		setAbbr(moduleAbbreviation);
		setMain(fullyQualifiedMainClass);
	}

	public ModuleInfo(final Properties prop) {
		this(null, prop);
	}

	public ModuleInfo(final String moduleName, final String moduleAbbreviation,
			final String fullyQualifiedMainClass) {
		this(null, moduleName, moduleAbbreviation, fullyQualifiedMainClass);
	}

	public String getAbbr() {
		return abbr;
	}

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

	public boolean isInitialised() {
		return loader != null && module != null && isValid();
	}

	public boolean isValid() {
		return StringUtils.INSTANCE.isNotBlank(name) //
				&& StringUtils.INSTANCE.isNotBlank(abbr) //
				&& StringUtils.INSTANCE.isNotBlank(main) //
				&& jar.exists();
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
