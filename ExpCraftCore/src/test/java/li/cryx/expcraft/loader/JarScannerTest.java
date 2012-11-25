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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Verifies that {@link JarScanner#scanFolder(File)} only returns valid module
 * JARs.
 * 
 * @author cryxli
 */
public class JarScannerTest {

	private static File folder;

	@BeforeClass
	public static void createTestJars() throws IOException {
		folder = new File("./target/scan");
		folder.mkdirs();

		// JAR without info.properties
		Properties prop = new Properties();
		prop.setProperty(ModuleConstants.PROP.MAIN_CLASS, "class");
		prop.setProperty(ModuleConstants.PROP.MODULE_NAME, "Mod");
		prop.setProperty(ModuleConstants.PROP.MODULE_VERSION, "1.0");
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(
				new File(folder, "missing.jar")));
		zos.putNextEntry(new ZipEntry("foo.properties"));
		prop.store(zos, null);
		zos.close();

		// JAR with incomplete info.properties
		zos = new ZipOutputStream(new FileOutputStream(new File(folder,
				"corrupt.jar")));
		zos.putNextEntry(new ZipEntry(ModuleConstants.INFO_FILE));
		prop.store(zos, null);
		zos.close();

		// JAR with correct info.properties
		prop.setProperty(ModuleConstants.PROP.MODULE_ABBR, "M");
		zos = new ZipOutputStream(new FileOutputStream(new File(folder,
				"valid.jar")));
		zos.putNextEntry(new ZipEntry(ModuleConstants.INFO_FILE));
		prop.store(zos, null);
		zos.close();
	}

	// scan folder for valid module JARs
	@Test
	public void scanFolder() {
		// test
		List<ModuleInfo> jars = new JarScanner().scanFolder(folder);

		// verify
		Assert.assertEquals(1, jars.size());
		ModuleInfo info = jars.get(0);
		Assert.assertEquals("Mod", info.getName());
		Assert.assertEquals("M", info.getAbbr());
		Assert.assertEquals("class", info.getMain());
		Assert.assertEquals("1.0", info.getVersion());
		Assert.assertEquals("valid.jar", info.getJar().getName());
	}

}
