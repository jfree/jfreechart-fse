package org.jfree.data.datasetextension;

import org.jfree.data.datasetextension.optional.WithChangeListener;
import org.jfree.data.general.LabelChangeListener;

/**
 * Extends a dataset such that each data item has an additional label attribute
 * that assigns a class to this item. Each item is part of exactly one label class.
 * 
 * @author zinsmaie
 *
 */
public interface DatasetLabelExtension extends DatasetExtension, WithChangeListener {
	
	/** default class for not labeled data items. */
	public final int NO_LABEL = -1;
	
	/**
	 * @param cursor specifies the position of the data item
	 * @return the label attribute of the data item
	 */
	public int getLabel(DatasetCursor cursor);
	
	/**
	 * @param cursor specifies the position of the data item
	 * @param label the new label value for the data item
	 */
	public void setLabel(DatasetCursor cursor, int label);
	

	//Listener

	/**
	 * adds a label change listener to the dataset extension<br>
	 * <br>
	 * the listener is triggered if a data item label changes except 
	 * the notify flag is set to false (@link #setNotify(boolean)).
	 * In the latter case a change event should be triggered as soon as the notify flag is set to true again.
	 *  
	 * @param listener
	 */
	public void addLabelChangeListener(LabelChangeListener listener);
	

}
