package li.cryx.expcraft.util;

/**
 * TODO documentation
 * 
 * @author cryxli
 */
public enum StringUtils {
	INSTANCE;

	public boolean isNotBlank(final String str) {
		return str != null && str.trim().length() > 0;
	}

	public boolean isNotEmpty(final String str) {
		return str != null && str.length() > 0;
	}

}
