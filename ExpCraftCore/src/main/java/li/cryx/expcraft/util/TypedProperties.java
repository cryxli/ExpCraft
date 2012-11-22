package li.cryx.expcraft.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

/**
 * TODO documentation
 * 
 * @author cryxli
 */
public class TypedProperties extends Properties {

	private static final long serialVersionUID = 6534481428903134476L;

	public TypedProperties() {
	}

	public TypedProperties(final Properties defaults) {
		super(defaults);
	}

	public boolean getBoolean(final String key) {
		return getBoolean(key, false);
	}

	public boolean getBoolean(final String key, final boolean defaultValue) {
		String value = getProperty(key, String.valueOf(defaultValue));
		if (value == null) {
			return defaultValue;
		} else {
			return "1".equals(value) || "true".equalsIgnoreCase(value);
		}
	}

	public byte getByte(final String key) {
		return getByte(key, (byte) 0);
	}

	public byte getByte(final String key, final byte defaultValue) {
		String value = getProperty(key, String.valueOf(defaultValue));
		try {
			return Byte.parseByte(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public double getDouble(final String key) {
		return getDouble(key, 0);
	}

	public double getDouble(final String key, final double defaultValue) {
		String value = getProperty(key, String.valueOf(defaultValue));
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public float getFloat(final String key) {
		return getFloat(key, 0);
	}

	public float getFloat(final String key, final float defaultValue) {
		String value = getProperty(key, String.valueOf(defaultValue));
		try {
			return Float.parseFloat(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public int getInteger(final String key) {
		return getInteger(key, 0);
	}

	public int getInteger(final String key, final int defaultValue) {
		String value = getProperty(key, String.valueOf(defaultValue));
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public long getLong(final String key) {
		return getLong(key, 0);
	}

	public long getLong(final String key, final long defaultValue) {
		String value = getProperty(key, String.valueOf(defaultValue));
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public String getString(final String key) {
		return getProperty(key);
	}

	public String getString(final String key, final String defaultValue) {
		return getProperty(key, defaultValue);
	}

	public void merge(final Properties valuesToMerge) {
		putAll(valuesToMerge);
	}

	public void setBoolean(final String key, final boolean value) {
		setProperty(key, String.valueOf(value));
	}

	public void setByte(final String key, final byte value) {
		setProperty(key, String.valueOf(value));
	}

	public void setDouble(final String key, final double value) {
		setProperty(key, String.valueOf(value));
	}

	public void setFloat(final String key, final float value) {
		setProperty(key, String.valueOf(value));
	}

	public void setInteger(final String key, final int value) {
		setProperty(key, String.valueOf(value));
	}

	public void setLong(final String key, final long value) {
		setProperty(key, String.valueOf(value));
	}

	public void setString(final String key, final String value) {
		setProperty(key, value);
	}

	public void store(final File file, final String comments)
			throws IOException {
		PrintWriter writer = new PrintWriter(file, "ISO-8859-1");
		store(writer, comments);
		writer.close();
	}

	@Override
	public void store(final OutputStream out, final String comments)
			throws IOException {
		store(new OutputStreamWriter(out, Charset.forName("ISO-8859-1")),
				comments);
	}

	public void store(final PrintWriter writer, final String comments) {
		if (comments != null) {
			for (String line : comments.split("\n")) {
				writer.print("# ");
				writer.println(line);
			}
		}
		writer.print("# ");
		writer.println(new Date());

		Set<String> sorted = new TreeSet<String>();
		sorted.addAll(stringPropertyNames());
		for (String key : sorted) {
			writer.print(key);
			writer.print('=');
			writer.println(getProperty(key));
		}
		writer.flush();
	}

	@Override
	public void store(final Writer writer, final String comments)
			throws IOException {
		if (writer instanceof PrintWriter) {
			store((PrintWriter) writer, comments);
		} else {
			store(new PrintWriter(writer), comments);
		}
	}

}
