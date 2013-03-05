package org.jfree.data.datasetextension.optional;

import org.jfree.data.datasetextension.DatasetCursor;
import org.jfree.data.datasetextension.DatasetIterator;
import org.jfree.data.datasetextension.DatasetSelectionExtension;

/**
 * Marks a {@link DatasetSelectionExtension} that provides iterators over all data item positions
 * and over the data item position of a all (selected/unselected) items
 * 
 * @author zinsmaie
 *
 */
public interface IterableSelection<CURSOR extends DatasetCursor> {

	/**
	 * @return an iterator over all data item positions
	 */
	public DatasetIterator<CURSOR> getIterator();
	
	/**
	 * @param selected
	 * @return an iterator over the data item positions of all (selected/unselected) items
	 */
	public DatasetIterator<CURSOR> getSelectionIterator(boolean selected);
	
}
