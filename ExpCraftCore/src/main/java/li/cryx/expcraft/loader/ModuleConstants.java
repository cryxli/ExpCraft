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
