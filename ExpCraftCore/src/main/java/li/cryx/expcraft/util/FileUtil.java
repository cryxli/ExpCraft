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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Utility class to perform file and stream operations.
 * 
 * @author cryxli
 */
public enum FileUtil {
	INSTANCE;

	public void copyFile(final File source, final File dest) throws IOException {
		FileInputStream in = new FileInputStream(source);
		copyFile(in, dest);
		in.close();
	}

	public void copyFile(final File source, final OutputStream dest)
			throws IOException {
		FileInputStream in = new FileInputStream(source);
		copyFile(in, dest);
		in.close();
	}

	public void copyFile(final InputStream source, final File dest)
			throws IOException {
		FileOutputStream out = new FileOutputStream(dest);
		copyFile(source, out);
		out.close();
	}

	public void copyFile(final InputStream source, final OutputStream dest)
			throws IOException {
		byte[] buf = new byte[1024];
		int len = source.read(buf);
		while (len > 0) {
			dest.write(buf, 0, len);
			len = source.read(buf);
		}
	}
}
