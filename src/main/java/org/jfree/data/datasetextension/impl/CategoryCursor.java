package org.jfree.data.datasetextension.impl;

import org.jfree.data.datasetextension.DatasetCursor;

/**
 * A DatasetCursor implementation for category datasets.
 * @author zinsmaie
 */
public class CategoryCursor implements DatasetCursor {

	/** a generated serial id. */
	private static final long serialVersionUID = 7086987028899208483L;
	
	/** stores the key of the row position */
	public Comparable rowKey;
	
	/** stores the key of the column position */
	public Comparable columnKey;

	/**
	 * creates a cursor without assigned position (the cursor will only
	 * be valid if setPosition is called at some time)
	 */
	public CategoryCursor() {
	}
	
	/**
	 * Default category cursor constructor. Sets the cursor position to the specified values.
	 * @param rowKey
	 * @param columnKey
	 */
	public CategoryCursor(Comparable rowKey, Comparable columnKey) {
		this.rowKey = rowKey;
		this.columnKey = columnKey;
	}

	/**
	 * sets the cursor position to the specified values
	 * @param rowKey
	 * @param columnKey
	 */
	public void setPosition(Comparable rowKey, Comparable columnKey) {
		this.rowKey = rowKey;
		this.columnKey = columnKey;
	}
	
	//depend on the implementation of comparable
	//if the keys overrides hashCode and equals these methods will function
	//for the cursor (e.g. String, Integer, ...)

	public int hashCode() {
		int result = 31 + ((columnKey == null) ? 0 : columnKey.hashCode());
		result = 31 * result + ((rowKey == null) ? 0 : rowKey.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoryCursor other = (CategoryCursor) obj;
		if (columnKey == null) {
			if (other.columnKey != null)
				return false;
		} else if (!columnKey.equals(other.columnKey))
			return false;
		if (rowKey == null) {
			if (other.rowKey != null)
				return false;
		} else if (!rowKey.equals(other.rowKey))
			return false;
		return true;
	}
	
	
}
