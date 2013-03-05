package org.jfree.data.general;

import java.util.EventListener;

/**
 * A listener that wants to be informed about changes of the label attribute of data items
 * 
 * @author zinsmaie
 *
 */
public interface LabelChangeListener extends EventListener {

	/**
	 * called if at least one label of an observed data item changes
	 * @param event
	 */
    public void labelChanged(LabelChangeEvent event);

}
