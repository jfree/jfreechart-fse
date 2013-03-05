package org.jfree.data.datasetextension.impl;

import org.jfree.data.datasetextension.DatasetCursor;

/**
 * A DatasetCursor implementation for pie datasets.
 * @author zinsmaie
 */
public class PieCursor implements DatasetCursor {

	/** a generated serial id	 */
	private static final long serialVersionUID = -7031433882367850307L;
	
	/** stores the key of the section */
	public Comparable key;
	
	/**
	 * creates a cursor without assigned position (the cursor will only
	 * be valid if setPosition is called at some time)
	 */
	public PieCursor() {
	}
	
	/**
	 * Default pie cursor constructor. Sets the cursor position to the specified value.
	 * @param key
	 */
	public PieCursor(Comparable key) {
		this.key = key;
	}

	/**
	 * sets the cursor position to the specified value
	 * @param key
	 */
	public void setPosition(Comparable key) {
		this.key = key;
	}

	//depend on the implementation of comparable
	//if the key overrides hashCode and equals these methods will function
	//for the cursor (e.g. String, Integer, ...)
	
	public int hashCode() {
		int result = 31  + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PieCursor other = (PieCursor) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

}
