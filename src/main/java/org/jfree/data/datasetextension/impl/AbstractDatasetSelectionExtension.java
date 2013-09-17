package org.jfree.data.datasetextension.impl;

import javax.swing.event.EventListenerList;

import org.jfree.data.datasetextension.DatasetCursor;
import org.jfree.data.datasetextension.DatasetExtension;
import org.jfree.data.datasetextension.DatasetSelectionExtension;
import org.jfree.data.datasetextension.optional.WithChangeListener;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.SelectionChangeEvent;
import org.jfree.data.general.SelectionChangeListener;

/**
 * Base class for separate selection extension implementations. Provides notification handling
 * and listener support. 
 *  
 * @author zinsmaie
 *
 */
public abstract class AbstractDatasetSelectionExtension<CURSOR extends DatasetCursor, DATASET extends Dataset> implements DatasetSelectionExtension<CURSOR>, DatasetChangeListener {

    /** a generated serial id */
	private static final long serialVersionUID = 4206903652292146757L;

	/** Storage for registered listeners. */
    private transient EventListenerList listenerList = new EventListenerList();

    /** notify flag {@link #isNotify()} */
    private boolean notify;
    
    /** dirty flag true if changes occurred is used to trigger a queued change event 
     *  if notify is reset to true.
     */
    private boolean dirty;
    
	/** reference to the extended dataset */
	private final DATASET dataset;
    
    public AbstractDatasetSelectionExtension(DATASET dataset) {
    	this.dataset = dataset;
    	this.dataset.addChangeListener(this);
    }
    
    /**
     * {@link DatasetExtension#getDataset}
     */
    public DATASET getDataset() {
    	return this.dataset;
    }
    
    /** 
     * {@link DatasetSelectionExtension#addChangeListener(org.jfree.data.general.SelectionChangeListener)
     */
    public void addChangeListener(SelectionChangeListener<CURSOR> listener) {
    	this.notify = true;
   		this.listenerList.add(SelectionChangeListener.class, listener);
    }

    /**
	 * {@link WithChangeListener#removeChangeListener(EventListener))
     */
    public void removeChangeListener(SelectionChangeListener<CURSOR> listener) {
   		this.listenerList.remove(SelectionChangeListener.class, listener);
    }


    /**
     * {@link WithChangeListener#setNotify(boolean)}
     */
    public void setNotify(boolean notify) {
    	if (this.notify != notify) {
    		if (notify == false) {
    			//switch notification temporary off
    			this.dirty = false;
    		} else {
    			//switch notification on
    			if (this.dirty == true) {
    				notifyListeners();	
    			}
    		}    		
    		this.notify = notify;
    	}
   }
   
    /**
     * {@link WithChangeListener#isNotify()}
     */
   public boolean isNotify() {
	   return this.notify;
   }
   
   /**
    * can be called by subclasses to trigger notify events depending on the
    * notify flag.
    */
   protected void notifiyIfRequired() {
	   if (this.notify) {
		   notifyListeners();
	   } else {
		   this.dirty = true;
	   }
   }
    
   /**
    * notifies all registered listeners 
    * @param event
    */
   @SuppressWarnings("unchecked") //add method accepts only SelctionChangeListeners<CURSOR>
private void notifyListeners() {
        Object[] listeners = this.listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == SelectionChangeListener.class) {
                ((SelectionChangeListener<CURSOR>) listeners[i + 1]).selectionChanged(new SelectionChangeEvent<CURSOR>(this));
            }
        }
   }
}
