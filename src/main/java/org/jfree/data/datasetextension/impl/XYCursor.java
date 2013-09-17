package org.jfree.data.datasetextension.impl;

import org.jfree.data.datasetextension.DatasetCursor;

/**
 * A DatasetCursor implementation for xy datasets.
 * @author zinsmaie
 */
public class XYCursor implements DatasetCursor {

	/** a generated serial id */
	private static final long serialVersionUID = -8005904310382047935L;
	
	/** stores the series position */
	public int series;
	/** stores the item position */
	public int item;

	/**
	 * creates a cursor without assigned position (the cursor will only
	 * be valid if setPosition is called at some time)
	 */
	public XYCursor() {
	}
	
	/**
	 * Default xy cursor constructor. Sets the cursor position to the specified values.
	 * @param series
	 * @param item
	 */
	public XYCursor(int series, int item) {
		this.series = series;
		this.item = item;
	}

	/**
	 * sets the cursor position to the specified values
	 * @param series
	 * @param item
	 */
	public void setPosition(int series, int item) {
		this.series = series;
		this.item = item;
	}

	//in contrast to the other cursor implementations
	//hasCode and equals for the XYCursor work guaranteed as expected
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + item;
		result = prime * result + series;
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XYCursor other = (XYCursor) obj;
		if (item != other.item)
			return false;
		if (series != other.series)
			return false;
		return true;
	}
	
	
}
