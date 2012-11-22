package li.cryx.expcraft.loader;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO documentation
 * 
 * @author cryxli
 */
public class JarScanner {
	private static final Logger LOG = LoggerFactory.getLogger(JarScanner.class);

	/** File filter that returns only accessible <code>*.jar</code> files. */
	private static final FileFilter jarFilter = new FileFilter() {
		@Override
		public boolean accept(final File pathname) {
			return pathname.isFile() && pathname.canRead()
					&& pathname.getName().toLowerCase().endsWith(".jar");
		}
	};

	/**
	 * Test whether the given JAR contains a <code>config/info.properties</code>
	 * and return its contents. The method only extracts the info, it does not
	 * validate them.
	 * 
	 * @param jar
	 *            The JAR file to inspect.
	 * @return A {@link ModuleInfo}, if the <code>info.properties</code> could
	 *         be read, or, <code>null</code>.
	 */
	private ModuleInfo inspectJar(final File jar) {
		final Properties prop = new Properties();
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(jar);
			final InputStream stream = zipFile.getInputStream(new ZipEntry(
					ModuleConstants.INFO_FILE));
			prop.load(stream);
		} catch (NullPointerException e) {
			return null;
		} catch (ZipException e) {
			return null;
		} catch (IOException e) {
			return null;
		} finally {
			if (zipFile != null) {
				try {
					zipFile.close();
				} catch (IOException e) {
				}
			}
		}

		return new ModuleInfo(jar, prop);
	}

	// TODO documentation
	private List<ModuleInfo> inspectJars(final File[] jars) {
		List<ModuleInfo> matches = new LinkedList<ModuleInfo>();
		for (File jar : jars) {
			ModuleInfo info = inspectJar(jar);
			if (info != null && info.isValid()) {
				matches.add(info);
				LOG.info(info.toString());
			}
		}
		return matches;
	}

	/**
	 * Scan the given folder for containing expcraft module JAR files.
	 * 
	 * @param folder
	 *            The folder to inspect.
	 * @return A list of {@link ModuleInfo}s pointing to valid module JAR files.
	 *         The list can be empty, but will never be <code>null</code>.
	 */
	public List<ModuleInfo> scanFolder(final File folder) {
		return inspectJars(folder.listFiles(jarFilter));
	}

}
