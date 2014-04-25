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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import li.cryx.expcraft.module.ExpCraftModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class takes valid {@link ModuleInfo}s and creates a new instances of the
 * according {@link ExpCraftModule}.
 * 
 * @author cryxli
 */
public class ModuleLoader {

	private static final Logger LOG = LoggerFactory
			.getLogger(ModuleLoader.class);

	/** Parent ClassLoader. */
	private final ClassLoader classLoader;

	/**
	 * Set the parent ClassLoader. This is the one of the core.
	 * 
	 * @param classLoader
	 *            Parent ClassLoader.
	 */
	public ModuleLoader(final ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	/**
	 * Attach module JAR and load module's main class.
	 * 
	 * @param info
	 *            Description of the module.
	 * @return An instance of the module's main class.
	 */
	public ExpCraftModule attachModule(final ModuleInfo info) {
		if (!info.isValid()) {
			// module description does not contain the necessary information
			// to instantiate the module
			info.setLoader(null);
			info.setModule(null);
			return null;
		}

		if (info.getLoader() == null) {
			// create a new ClassLoader to access the module's JAR file
			URL jar;
			try {
				jar = info.getJar().getAbsoluteFile().toURI().toURL();
			} catch (MalformedURLException e) {
				LOG.error("Cannot attach JAR", e);
				return null;
			}
			info.setLoader(new URLClassLoader(new URL[] { jar }, classLoader) {
				@Override
				public URL getResource(final String name) {
					// invert classloader hierarchy
					URL url = findResource(name);
					if (url == null) {
						url = super.getResource(name);
					}
					return url;
				}
			});
		}

		if (info.getModule() != null) {
			return info.getModule();
		}
		try {
			// load class file from JAR
			Class<?> mainClass = Class.forName(info.getMain(), true,
					info.getLoader());
			// get the ExpCraftModule super-class from the main class
			Class<? extends ExpCraftModule> moduleClass = mainClass
					.asSubclass(ExpCraftModule.class);
			// get the default constructor
			Constructor<? extends ExpCraftModule> constructor = moduleClass
					.getConstructor();
			// create a new instance of the module
			ExpCraftModule module = constructor.newInstance();
			// link the module to its module descriptor
			info.setModule(module);
			module.setInfo(info);
			return module;
		} catch (ClassNotFoundException e) {
			LOG.error("Module class does not exist", e);
		} catch (SecurityException e) {
			LOG.error("Cannot access default constructor", e);
		} catch (NoSuchMethodException e) {
			LOG.error("Default constructor does not exist", e);
		} catch (IllegalArgumentException e) {
			LOG.error("Arguments of default constructor do not match", e);
		} catch (InstantiationException e) {
			LOG.error("Error while init module", e);
		} catch (IllegalAccessException e) {
			LOG.error("Default constructor is hidden", e);
		} catch (InvocationTargetException e) {
			LOG.error("Cannot call default constructor", e);
		}
		return null;
	}
}
