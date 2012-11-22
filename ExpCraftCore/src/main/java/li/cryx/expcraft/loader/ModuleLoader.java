package li.cryx.expcraft.loader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import li.cryx.expcraft.module.ExpCraftModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModuleLoader {

	private static final Logger LOG = LoggerFactory
			.getLogger(ModuleLoader.class);

	private final ClassLoader classLoader;

	public ModuleLoader(final ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public ExpCraftModule attachModule(final ModuleInfo info) {
		if (!info.isValid()) {
			info.setLoader(null);
			info.setModule(null);
			return null;
		}

		if (info.getLoader() == null) {
			URL jar;
			try {
				jar = info.getJar().getAbsoluteFile().toURI().toURL();
			} catch (MalformedURLException e) {
				LOG.error("Cannot attach JAR", e);
				return null;
			}
			info.setLoader(new URLClassLoader(new URL[] { jar }, classLoader));
		}

		if (info.getModule() != null) {
			return info.getModule();
		}
		try {
			Class<?> mainClass = Class.forName(info.getMain(), true,
					info.getLoader());
			Class<? extends ExpCraftModule> moduleClass = mainClass
					.asSubclass(ExpCraftModule.class);
			Constructor<? extends ExpCraftModule> constructor = moduleClass
					.getConstructor();
			ExpCraftModule module = constructor.newInstance();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
