package me.samkio.levelcraftcore.util;

import java.util.Comparator;
import java.util.Map;

class ValueComparator implements Comparator<String> {

	private final Map<String, Double> base;

	public ValueComparator(final Map<String, Double> base) {
		this.base = base;
	}

	@Override
	public int compare(final String a, final String b) {
		Double aDouble = base.get(a);
		Double bDouble = base.get(b);
		if (aDouble != null) {
			return aDouble.compareTo(bDouble);
		} else if (bDouble == null) {
			return 0;
		} else {
			return -1;
		}
	}
}
