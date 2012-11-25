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
import java.net.URLClassLoader;

import junit.framework.Assert;
import li.cryx.expcraft.module.ExpCraftModule;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * This unittest verifies parts of the
 * {@link ModuleLoader#attachModule(ModuleInfo)} method. The final part, the
 * actual initialisation of the {@link ExpCraftModule} cannot be tested.
 * 
 * @author cryxli
 */
public class ModuleLoaderTest {

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
		ModuleInfo info = new ModuleInfo(new File("pom.xml"), "Mod", "M",
				"class");
		info.setLoader(getClass().getClassLoader());

		// test - aborts with a ClassNotFoundException
		ExpCraftModule module = new ModuleLoader(info.getLoader())
				.attachModule(info);

		// verify
		Assert.assertNull(module);
		Assert.assertNull(info.getModule());
		Assert.assertEquals(getClass().getClassLoader(), info.getLoader());
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
