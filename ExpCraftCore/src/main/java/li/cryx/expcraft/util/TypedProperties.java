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
 * This extension to the basic <code>Properties</code> class offers typed
 * getters to retrieve values other than Strings from a properties file.
 * 
 * @author cryxli
 */
public class TypedProperties extends Properties {

	private static final long serialVersionUID = -1616785919463045294L;

	/** Encoding for properties files as defined by the spec. */
	private static final String ENCODING = "ISO-8859-1";

	/** Create an empty properties file. */
	public TypedProperties() {
	}

	/** Create a properties file containing the same values as the given one. */
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

	/**
	 * Store values of this properties file in <em>natural order</em> of their
	 * keys.
	 * 
	 * @param writer
	 *            Properties will be stored in this file.
	 * @param comments
	 *            Optional comments to be written at the beginning of the file.
	 *            Can be <code>null</code>.
	 */
	public void store(final File file, final String comments)
			throws IOException {
		PrintWriter writer = new PrintWriter(file, ENCODING);
		store(writer, comments);
		writer.close();
	}

	/**
	 * Store values of this properties file in <em>natural order</em> of their
	 * keys.
	 * 
	 * @param writer
	 *            Properties will be stored using this stream. The stream
	 *            remains open when this method completes!
	 * @param comments
	 *            Optional comments to be written at the beginning of the file.
	 *            Can be <code>null</code>.
	 */
	@Override
	public void store(final OutputStream out, final String comments)
			throws IOException {
		store(new OutputStreamWriter(out, Charset.forName(ENCODING)), comments);
	}

	/**
	 * Store values of this properties file in <em>natural order</em> of their
	 * keys.
	 * 
	 * @param writer
	 *            Properties will be stored using this writer. You have to
	 *            ensure that the writer uses <code>ISO-8859-1</code> encoding,
	 *            or the file cannot be read! The writer is not closed when this
	 *            method terminates!
	 * @param comments
	 *            Optional comments to be written at the beginning of the file.
	 *            Can be <code>null</code>.
	 */
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

	/**
	 * Store values of this properties file in <em>natural order</em> of their
	 * keys.
	 * 
	 * @param writer
	 *            Properties will be stored using this writer. You have to
	 *            ensure that the writer uses <code>ISO-8859-1</code> encoding,
	 *            or the file cannot be read! The writer is not closed when this
	 *            method terminates!
	 * @param comments
	 *            Optional comments to be written at the beginning of the file.
	 *            Can be <code>null</code>.
	 */
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
