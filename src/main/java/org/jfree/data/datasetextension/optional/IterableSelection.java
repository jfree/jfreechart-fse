package org.jfree.data.datasetextension.optional;

import org.jfree.data.datasetextension.DatasetIterator;
import org.jfree.data.datasetextension.DatasetSelectionExtension;

/**
 * Marks a {@link DatasetSelectionExtension} that provides iterators over all data item positions
 * and over the data item position of a all (selected/unselected) items
 * 
 * @author zinsmaie
 *
 */
public interface IterableSelection {

	/**
	 * @return an iterator over all data item positions
	 */
	public DatasetIterator getIterator();
	
	/**
	 * @param selected
	 * @return an iterator over the data item positions of all (selected/unselected) items
	 */
	public DatasetIterator getSelectionIterator(boolean selected);
	
}
