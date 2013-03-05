package org.jfree.data.general;

import java.util.EventListener;

/**
 * A listener that wants to be informed about changes of the selection state of data items
 * 
 * @author zinsmaie
 *
 */
public interface SelectionChangeListener extends EventListener {


	/**
	 * called if the selection state of at least one observed data item changes
	 * @param event
	 */
    public void selectionChanged(SelectionChangeEvent event);

}
