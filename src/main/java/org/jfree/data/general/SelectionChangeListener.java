package org.jfree.data.general;

import java.util.EventListener;

import org.jfree.data.extension.DatasetCursor;

/**
 * A listener that wants to be informed about changes of the selection state of data items
 * 
 * @author zinsmaie
 *
 */
public interface SelectionChangeListener<CURSOR extends DatasetCursor> extends EventListener {


	/**
	 * called if the selection state of at least one observed data item changes
	 * @param event
	 */
    public void selectionChanged(SelectionChangeEvent<CURSOR> event);

}
