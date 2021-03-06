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

/**
 * Helper class offering some String operations.
 * 
 * @author cryxli
 */
public enum StringUtils {
	INSTANCE;

	/**
	 * Test whether the given string contains text. Spaces, tabs, etc. do not
	 * count.
	 * 
	 * @param str
	 *            String to inspect.
	 * @return <code>true</code>, if the String is not <code>null</code> and
	 *         contains at least one none-white space character.
	 */
	public boolean isNotBlank(final String str) {
		return str != null && str.trim().length() > 0;
	}

	/**
	 * Test whether the given string contains text. Spaces, tabs, etc. will also
	 * count.
	 * 
	 * @param str
	 *            String to inspect.
	 * @return <code>true</code>, if the String is not <code>null</code> and
	 *         contains at least one character.
	 */
	public boolean isNotEmpty(final String str) {
		return str != null && str.length() > 0;
	}

}
