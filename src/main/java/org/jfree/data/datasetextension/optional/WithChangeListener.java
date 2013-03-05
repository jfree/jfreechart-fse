package org.jfree.data.datasetextension.optional;

import java.util.EventListener;

import org.jfree.data.general.SelectionChangeEvent;

/**
 * DatasetExtensions that support change listeners can implement this interface. The
 * <code>addListener<code> method is missing to allow a type specific implementation (e.g.
 * addSelectionChangeListener, ...)
 *  
 * @author zinsmaie
 */
public interface WithChangeListener {

	//typically includes also a type spcific addChangeListener method
	
	/**
	 * removes a change listener from the dataset extension<br>
	 *  
	 * @param listener
	 */
	public void removeChangeListener(EventListener listener);
	
    /**
     * Sets a flag that controls whether or not listeners receive
     * {@link SelectionChangeEvent} notifications.
     *
     * @param notify If the flag is set to false the listeners are no longer informed about changes.
     *  If the flag is set to true and some changes occurred an event should be triggered. 
     */
    public void setNotify(boolean notify);
    
    /**
     * @return true if the notification flag is active 
     */
    public boolean isNotify();
	
}
