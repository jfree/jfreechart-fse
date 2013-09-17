package org.jfree.data.general;

import java.util.EventObject;

import org.jfree.data.extension.DatasetCursor;
import org.jfree.data.extension.DatasetSelectionExtension;

/**
 * A change event that notifies about a change to the selection state of data items
 *
 * @author zinsmaie
 *
 */
public class SelectionChangeEvent<CURSOR extends DatasetCursor> extends EventObject {

	
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
	public DatasetSelectionExtension<CURSOR> getSelectionExtension() {
        if (this.getSource() instanceof DatasetSelectionExtension) {
        	return (DatasetSelectionExtension<CURSOR>)this.getSource();
        }
        
        //implementation error
        return null;
    }

}
