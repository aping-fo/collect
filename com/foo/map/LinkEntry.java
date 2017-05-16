package com.foo.map;

public class LinkEntry extends HashEntry {

	protected LinkEntry(HashEntry next, int hashCode, Object key, Object value) {
		super(next, hashCode, key, value);
	}

	/** The entry before this one in the order */
	protected LinkEntry before;
	/** The entry after this one in the order */
	protected LinkEntry after;
}
