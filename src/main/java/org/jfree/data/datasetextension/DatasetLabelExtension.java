package org.jfree.data.datasetextension;

import org.jfree.data.datasetextension.optional.WithChangeListener;
import org.jfree.data.general.SelectionChangeListener;

/**
 * Extends a dataset such that each data item has an additional label attribute
 * that assigns a class to this item. Each item is part of exactly one label class.
 * 
 * @author zinsmaie
 *
 */
public interface DatasetLabelExtension<CURSOR extends DatasetCursor> extends DatasetExtension, WithChangeListener<SelectionChangeListener<CURSOR>> {
	
	/** default class for not labeled data items. */
	public final int NO_LABEL = -1;
	
	/**
	 * @param cursor specifies the position of the data item
	 * @return the label attribute of the data item
	 */
	public int getLabel(CURSOR cursor);
	
	/**
	 * @param cursor specifies the position of the data item
	 * @param label the new label value for the data item
	 */
	public void setLabel(CURSOR cursor, int label);
	
}
