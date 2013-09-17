package org.jfree.data.extension;

import java.util.EventListener;

import org.jfree.data.general.SelectionChangeEvent;

/**
 * DatasetExtensions that support change listeners can implement this interface.
 *  
 * @author zinsmaie
 */
public interface WithChangeListener<T extends EventListener> {

	/**
	 * adds a change listener to the dataset extension<br>
	 * <br>
	 * The listener is triggered if if changes occur except 
	 * the notify flag is set to false (@link #setNotify(boolean)).
	 * In the latter case a change event should be triggered as soon as the notify flag is set to true again.
	 *  
	 * @param listener
	 */
	public void addChangeListener(T listener);

	
	/**
	 * removes a change listener from the dataset extension<br>
	 *  
	 * @param listener
	 */
	public void removeChangeListener(T listener);
	
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
