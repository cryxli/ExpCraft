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
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLClassLoader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import junit.framework.Assert;
import li.cryx.expcraft.module.ExpCraftModule;
import li.cryx.expcraft.util.FileUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * This unittest verifies parts of the
 * {@link ModuleLoader#attachModule(ModuleInfo)} method.
 * 
 * @author cryxli
 */
public class ModuleLoaderTest {

	/** Filter to find JAR files. */
	private static final FileFilter JAR_FILTER = new FileFilter() {
		@Override
		public boolean accept(final File pathname) {
			return pathname.getName().toLowerCase().endsWith(".jar");
		}
	};

	/** folder containing JARs */
	private static File folder;

	/** test jar for newModule() test. */
	private static File jar;

	@BeforeClass
	public static void createModJar() throws IOException {
		// clean folder
		folder = new File("./target/scan");
		folder.mkdirs();
		for (File file : folder.listFiles(JAR_FILTER)) {
			file.delete();
		}

		// build test jar for newModule() test
		jar = new File(folder, "ModuleLoaderTest.jar");
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(jar));
		// Properties prop = new Properties();
		// prop.setProperty(ModuleConstants.PROP.MAIN_CLASS,
		// "li.cryx.expcraft.loader.DummyExpCraftModule");
		// prop.setProperty(ModuleConstants.PROP.MODULE_NAME, "Mod");
		// prop.setProperty(ModuleConstants.PROP.MODULE_ABBR, "M");
		// prop.setProperty(ModuleConstants.PROP.MODULE_VERSION, "1.0");
		// zos.putNextEntry(new ZipEntry("config/info.properties"));
		// prop.store(zos, null);
		zos.putNextEntry(new ZipEntry(
				"li/cryx/expcraft/loader/DummyExpCraftModule.class"));
		FileUtil.INSTANCE
				.copyFile(
						new File(
								"target/test-classes/li/cryx/expcraft/loader/DummyExpCraftModule.class"),
						zos);
		zos.close();
	}

	// create ClassLoader, if missing
	@Test
	public void newLoader() {
		// prepare
		ModuleInfo info = new ModuleInfo(new File("pom.xml"), "Mod", "M",
				"class");
		ExpCraftModule mod = Mockito.mock(ExpCraftModule.class);
		info.setModule(mod);

		// test
		ExpCraftModule module = new ModuleLoader(getClass().getClassLoader())
				.attachModule(info);

		// verify
		Assert.assertEquals(mod, module);
		Assert.assertNotNull(info.getLoader());
		Assert.assertTrue(info.getLoader() instanceof URLClassLoader);
		Assert.assertEquals(mod, info.getModule());
	}

	// pseudo test to init an ExpCraftModule
	@Test
	public void newModule() {
		// prepare
		ModuleInfo info = new ModuleInfo(jar, "Mod", "M",
				"li.cryx.expcraft.loader.DummyExpCraftModule");

		// test - aborts with a ClassNotFoundException
		ExpCraftModule module = new ModuleLoader(getClass().getClassLoader())
				.attachModule(info);

		// verify
		Assert.assertNotNull(module);
		Assert.assertTrue(module instanceof DummyExpCraftModule);
		Assert.assertNotNull(info.getLoader());
		Assert.assertTrue(info.getLoader() instanceof URLClassLoader);
	}

	// only load, if ModuleInfo is valid
	@Test
	public void notValid() {
		// prepare
		ModuleInfo info = new ModuleInfo(null, "Mod", "M", "class");
		info.setLoader(getClass().getClassLoader());
		info.setModule(Mockito.mock(ExpCraftModule.class));

		// test
		ExpCraftModule module = new ModuleLoader(info.getLoader())
				.attachModule(info);

		// verify
		Assert.assertNull(module);
		Assert.assertNull(info.getLoader());
		Assert.assertNull(info.getModule());
	}

}
