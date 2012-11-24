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
