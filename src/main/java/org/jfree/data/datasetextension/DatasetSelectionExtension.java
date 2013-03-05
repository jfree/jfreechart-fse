package org.jfree.data.datasetextension;

import org.jfree.data.datasetextension.optional.WithChangeListener;
import org.jfree.data.general.SelectionChangeListener;

/**
 * Extends a dataset such that each data item has an additional selection state (either true or false)<br>
 *
 * @author zinsmaie
 *
 */
public interface DatasetSelectionExtension<CURSOR extends DatasetCursor> extends DatasetExtension, WithChangeListener<SelectionChangeListener> {
	
	/**
	 * @param cursor specifies the position of the data item
	 * @return true if the data item is selected
	 */
	public boolean isSelected(CURSOR cursor);
	
	/**
	 * sets the selection state of a data item
	 * @param cursor specifies the position of the data item
	 */
	public void setSelected(CURSOR cursor, boolean selected);
	
	/**
	 * sets all data items to unselected
	 */
	public void clearSelection();
	
}
