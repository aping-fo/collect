package com.foo.map;

public class MultiKey {

	private Object[] keys;
	private transient int hashCode;

	public MultiKey(Object key1, Object key2) {
		this(new Object[] { key1, key2 });
	}

	public MultiKey(Object[] keys) {
		super();
		if (keys == null) {
			throw new IllegalArgumentException(
					"The array of keys must not be null");
		}

		this.keys = keys;
		calculateHashCode(keys);
	}

	private void calculateHashCode(Object[] keys) {
		int total = 0;
		for (int i = 0; i < keys.length; i++) {
			if (keys[i] != null) {
				total ^= keys[i].hashCode();
			}
		}
		hashCode = total;
	}

	public Object[] getKeys() {
		return keys;
	}

	public int getHashCode() {
		return hashCode;
	}

	public int size() {
		return keys.length;
	}

	public Object getKey(int i) {
		return keys[i];
	}
}
