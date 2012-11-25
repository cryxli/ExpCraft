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

import junit.framework.Assert;
import li.cryx.expcraft.module.ExpCraftModule;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Verifies the calculations done by {@link ModuleInfo}.
 * 
 * @author cryxli
 */
public class ModuleInfoTest {

	private ModuleInfo info;

	@Test
	public void constructorFromProperties() {
		// prepare
		Properties prop = new Properties();
		prop.setProperty(ModuleConstants.PROP.MAIN_CLASS, "class");
		prop.setProperty(ModuleConstants.PROP.MODULE_ABBR, "M");
		prop.setProperty(ModuleConstants.PROP.MODULE_NAME, "Mod");
		prop.setProperty(ModuleConstants.PROP.MODULE_VERSION, "1.0");

		// test
		info = new ModuleInfo(prop);

		// verify
		Assert.assertEquals("class", info.getMain());
		Assert.assertEquals("M", info.getAbbr());
		Assert.assertEquals("Mod", info.getName());
		Assert.assertEquals("1.0", info.getVersion());
	}

	@Test
	public void getFullName() {
		// no version
		Assert.assertEquals("Mod null", info.getFullName());

		// both
		info.setVersion("1.0");
		Assert.assertEquals("Mod 1.0", info.getFullName());

		// no name
		info.setName(null);
		Assert.assertEquals("null 1.0", info.getFullName());

		// nothing
		info.setVersion(null);
		Assert.assertEquals("null null", info.getFullName());
	}

	@Test
	public void isInitialised() {
		// no loader, no module
		Assert.assertFalse(info.isInitialised());

		// no module
		info.setLoader(getClass().getClassLoader());
		Assert.assertFalse(info.isInitialised());

		// ok
		ExpCraftModule mod = Mockito.mock(ExpCraftModule.class);
		info.setModule(mod);
		Assert.assertTrue(info.isInitialised());
	}

	@Test
	public void isValid() {
		// ok
		Assert.assertTrue(info.isValid());
		Assert.assertFalse(info.isInitialised());

		// JAR is null
		info = new ModuleInfo(null, "Mod", "M", "class");
		Assert.assertFalse(info.isValid());

		// JAR file does not exist
		info = new ModuleInfo(new File("pom.xml.jar"), "Mod", "M", "class");
		Assert.assertFalse(info.isValid());

		File jar = new File("pom.xml");
		// no name
		info = new ModuleInfo(jar, null, "M", "class");
		Assert.assertFalse(info.isValid());

		// no abbr
		info = new ModuleInfo(jar, "Mod", null, "class");
		Assert.assertFalse(info.isValid());

		// no class
		info = new ModuleInfo(jar, "Mod", "M", null);
		Assert.assertFalse(info.isValid());
	}

	@Before
	public void prepareValidInfo() {
		File jar = new File("pom.xml");
		info = new ModuleInfo(jar, "Mod", "M", "class");
	}

}
