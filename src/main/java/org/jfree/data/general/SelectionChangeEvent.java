package org.jfree.data.general;

import java.util.EventObject;

import org.jfree.data.datasetextension.DatasetSelectionExtension;

/**
 * A change event that notifies about a change to the selection state of data items
 *
 * @author zinsmaie
 *
 */
public class SelectionChangeEvent extends EventObject {

	
    /** a generated serial id*/
	private static final long serialVersionUID = 5217307402196957331L;

	/**
     * Constructs a new event. 
     *
     * @param source the source of the event aka the selection extension that changed (the object has to be
     * of type DatasetSelectionExtension and must not be null!)
     */
	public SelectionChangeEvent(Object selectionExtension) {
		super(selectionExtension);
	}

	/**
	 * @return the selection selection extension that triggered the event.
	 */
    public DatasetSelectionExtension getSelectionExtension() {
        if (this.getSource() instanceof DatasetSelectionExtension) {
        	return (DatasetSelectionExtension)this.getSource();
        }
        
        //implementation error
        return null;
    }

}
