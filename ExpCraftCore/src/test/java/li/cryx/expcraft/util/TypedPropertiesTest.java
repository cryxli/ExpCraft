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

import java.io.CharArrayWriter;
import java.io.IOException;
import java.util.Properties;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * Ensures correctness of {@link TypedProperties}.
 * 
 * @author cryxli
 */
public class TypedPropertiesTest {

	private TypedProperties prop;

	@Test
	public void getBoolean() {
		// key not set
		Assert.assertFalse(prop.getBoolean("does.not.exist"));
		Assert.assertFalse(prop.getBoolean("does.not.exist", false));
		Assert.assertTrue(prop.getBoolean("does.not.exist", true));

		// value not a boolean
		prop.setProperty("bool", "foo");
		Assert.assertFalse(prop.getBoolean("bool"));
		Assert.assertFalse(prop.getBoolean("bool", false));
		Assert.assertFalse(prop.getBoolean("bool", true));

		// value is a boolean = 1
		prop.setProperty("bool", "1");
		Assert.assertTrue(prop.getBoolean("bool"));
		Assert.assertTrue(prop.getBoolean("bool", false));
		Assert.assertTrue(prop.getBoolean("bool", true));

		// value is a boolean = true
		prop.setProperty("bool", "true");
		Assert.assertTrue(prop.getBoolean("bool"));
		Assert.assertTrue(prop.getBoolean("bool", false));
		Assert.assertTrue(prop.getBoolean("bool", true));

		// value is a boolean = 0
		prop.setProperty("bool", "0");
		Assert.assertFalse(prop.getBoolean("bool"));
		Assert.assertFalse(prop.getBoolean("bool", false));
		Assert.assertFalse(prop.getBoolean("bool", true));

		// value is a boolean = false
		prop.setProperty("bool", "false");
		Assert.assertFalse(prop.getBoolean("bool"));
		Assert.assertFalse(prop.getBoolean("bool", false));
		Assert.assertFalse(prop.getBoolean("bool", true));

		// set values
		prop.setBoolean("bool", true);
		Assert.assertTrue(prop.getBoolean("bool"));
		Assert.assertTrue(prop.getBoolean("bool", false));
		Assert.assertTrue(prop.getBoolean("bool", true));
		prop.setBoolean("bool", false);
		Assert.assertFalse(prop.getBoolean("bool"));
		Assert.assertFalse(prop.getBoolean("bool", false));
		Assert.assertFalse(prop.getBoolean("bool", true));
	}

	@Test
	public void getByte() {
		// key not set
		Assert.assertEquals(0, prop.getByte("does.not.exist"));
		Assert.assertEquals(0, prop.getByte("does.not.exist", (byte) 0));
		Assert.assertEquals(1, prop.getByte("does.not.exist", (byte) 1));

		// value not a byte
		prop.setProperty("byte", "foo");
		Assert.assertEquals(0, prop.getByte("byte"));
		Assert.assertEquals(0, prop.getByte("byte", (byte) 0));
		Assert.assertEquals(1, prop.getByte("byte", (byte) 1));

		// value is a byte
		prop.setProperty("byte", "15");
		Assert.assertEquals(15, prop.getByte("byte"));
		Assert.assertEquals(15, prop.getByte("byte", (byte) 0));
		Assert.assertEquals(15, prop.getByte("byte", (byte) 1));

		// set values
		prop.setByte("byte", (byte) 42);
		Assert.assertEquals(42, prop.getByte("byte"));
		Assert.assertEquals(42, prop.getByte("byte", (byte) 0));
		Assert.assertEquals(42, prop.getByte("byte", (byte) 1));

		// out of range
		prop.setProperty("byte", String.valueOf(Byte.MIN_VALUE - 1));
		Assert.assertEquals(0, prop.getByte("byte"));
		Assert.assertEquals(0, prop.getByte("byte", (byte) 0));
		Assert.assertEquals(1, prop.getByte("byte", (byte) 1));
		prop.setProperty("byte", String.valueOf(Byte.MAX_VALUE + 1));
		Assert.assertEquals(0, prop.getByte("byte"));
		Assert.assertEquals(0, prop.getByte("byte", (byte) 0));
		Assert.assertEquals(1, prop.getByte("byte", (byte) 1));
	}

	@Test
	public void getDouble() {
		// key not set
		Assert.assertEquals(0, prop.getDouble("does.not.exist"), 0);
		Assert.assertEquals(0, prop.getDouble("does.not.exist", 0), 0);
		Assert.assertEquals(1, prop.getDouble("does.not.exist", 1), 0);

		// value not a double
		prop.setProperty("double", "foo");
		Assert.assertEquals(0, prop.getDouble("double"), 0);
		Assert.assertEquals(0, prop.getDouble("double", 0), 0);
		Assert.assertEquals(1, prop.getDouble("double", 1), 0);

		// value is a double
		prop.setProperty("double", "15");
		Assert.assertEquals(15, prop.getDouble("double"), 0);
		Assert.assertEquals(15, prop.getDouble("double", 0), 0);
		Assert.assertEquals(15, prop.getDouble("double", 1), 0);

		// set values
		prop.setDouble("double", 42);
		Assert.assertEquals(42, prop.getDouble("double"), 0);
		Assert.assertEquals(42, prop.getDouble("double", 0), 0);
		Assert.assertEquals(42, prop.getDouble("double", 1), 0);
	}

	@Test
	public void getFloat() {
		// key not set
		Assert.assertEquals(0.0f, prop.getFloat("does.not.exist"));
		Assert.assertEquals(0.0f, prop.getFloat("does.not.exist", 0.0f));
		Assert.assertEquals(1.0f, prop.getFloat("does.not.exist", 1.0f));

		// value not a float
		prop.setProperty("float", "foo");
		Assert.assertEquals(0.0f, prop.getFloat("float"));
		Assert.assertEquals(0.0f, prop.getFloat("float", 0.0f));
		Assert.assertEquals(1.0f, prop.getFloat("float", 1.0f));

		// value is a float
		prop.setProperty("float", "15");
		Assert.assertEquals(15.0f, prop.getFloat("float"));
		Assert.assertEquals(15.0f, prop.getFloat("float", 0.0f));
		Assert.assertEquals(15.0f, prop.getFloat("float", 1.0f));

		// set values
		prop.setDouble("float", 42);
		Assert.assertEquals(42.0f, prop.getFloat("float"));
		Assert.assertEquals(42.0f, prop.getFloat("float", 0.0f));
		Assert.assertEquals(42.0f, prop.getFloat("float", 1.0f));
	}

	@Test
	public void getInteger() {
		// key not set
		Assert.assertEquals(0, prop.getInteger("does.not.exist"));
		Assert.assertEquals(0, prop.getInteger("does.not.exist", 0));
		Assert.assertEquals(1, prop.getInteger("does.not.exist", 1));

		// value not an int
		prop.setProperty("int", "foo");
		Assert.assertEquals(0, prop.getInteger("int"));
		Assert.assertEquals(0, prop.getInteger("int", 0));
		Assert.assertEquals(1, prop.getInteger("int", 1));

		// value is an int
		prop.setProperty("int", "15");
		Assert.assertEquals(15, prop.getInteger("int"));
		Assert.assertEquals(15, prop.getInteger("int", 0));
		Assert.assertEquals(15, prop.getInteger("int", 1));

		// set values
		prop.setInteger("int", 42);
		Assert.assertEquals(42, prop.getInteger("int"));
		Assert.assertEquals(42, prop.getInteger("int", 0));
		Assert.assertEquals(42, prop.getInteger("int", 1));

		// out of range
		prop.setProperty("int", String.valueOf(Integer.MIN_VALUE - 1l));
		Assert.assertEquals(0, prop.getInteger("int"));
		Assert.assertEquals(0, prop.getInteger("int", 0));
		Assert.assertEquals(1, prop.getInteger("int", 1));
		prop.setProperty("int", String.valueOf(Integer.MAX_VALUE + 1l));
		Assert.assertEquals(0, prop.getInteger("int"));
		Assert.assertEquals(0, prop.getInteger("int", 0));
		Assert.assertEquals(1, prop.getInteger("int", 1));
	}

	@Test
	public void getLong() {
		// key not set
		Assert.assertEquals(0, prop.getLong("does.not.exist"));
		Assert.assertEquals(0, prop.getLong("does.not.exist", 0));
		Assert.assertEquals(1, prop.getLong("does.not.exist", 1));

		// value not a long
		prop.setProperty("long", "foo");
		Assert.assertEquals(0, prop.getLong("long"));
		Assert.assertEquals(0, prop.getLong("long", 0));
		Assert.assertEquals(1, prop.getLong("long", 1));

		// value is a long
		prop.setProperty("long", "15");
		Assert.assertEquals(15, prop.getLong("long"));
		Assert.assertEquals(15, prop.getLong("long", 0));
		Assert.assertEquals(15, prop.getLong("long", 1));

		// set values
		prop.setLong("long", 42);
		Assert.assertEquals(42, prop.getLong("long"));
		Assert.assertEquals(42, prop.getLong("long", 0));
		Assert.assertEquals(42, prop.getLong("long", 1));
	}

	@Test
	public void getString() {
		// key not set
		Assert.assertNull(prop.getString("does.not.exist"));
		Assert.assertNull(prop.getString("does.not.exist", null));
		Assert.assertEquals("", prop.getString("does.not.exist", ""));

		prop.setString("string", "foo");
		Assert.assertEquals("foo", prop.getString("string"));
		Assert.assertEquals("foo", prop.getString("string", null));
		Assert.assertEquals("foo", prop.getString("string", ""));
	}

	@Test
	public void merge() {
		// prepare
		prop.setProperty("first", "first");
		prop.setProperty("replaced", "first");

		Properties anotherProp = new Properties();
		anotherProp.setProperty("replaced", "second");
		anotherProp.setProperty("second", "second");

		// test
		prop.merge(anotherProp);

		// verify
		Assert.assertEquals("first", prop.getProperty("first"));
		Assert.assertEquals("second", prop.getProperty("replaced"));
		Assert.assertEquals("second", prop.getProperty("second"));
	}

	@Before
	public void prepareProperties() {
		// reset properties
		prop = new TypedProperties();
	}

	@Test
	public void store() throws IOException {
		// prepare
		CharArrayWriter buf = new CharArrayWriter();

		prop.setProperty("first", "1");
		prop.setProperty("second", "2");

		String comments = "first comment\nsecond comment";

		// test
		prop.store(buf, comments);

		// verify
		String[] lines = buf.toString().split("\n");
		Assert.assertEquals(5, lines.length);
		Assert.assertEquals("# first comment", lines[0].trim());
		Assert.assertEquals("# second comment", lines[1].trim());
		Assert.assertEquals("# ", lines[2].substring(0, 2)); // timestamp
		Assert.assertEquals("first=1", lines[3].trim());
		Assert.assertEquals("second=2", lines[4].trim());
	}
}
