package org.jfree.data.datasetextension.optional;

import org.jfree.data.datasetextension.DatasetCursor;
import org.jfree.data.datasetextension.DatasetIterator;
import org.jfree.data.datasetextension.DatasetLabelExtension;

/**
 * A {@link DatasetLabelExtension} that provides iterators over all data item positions
 * and over the data item position of a defined label.
 * 
 * @author zinsmaie
 *
 */
public interface IterableLabel<CURSOR extends DatasetCursor> {

	/**
	 * @return an iterator over all data item positions
	 */
	public DatasetIterator<CURSOR> getIterator();
	
	/**
	 * @param label
	 * @return an iterator over all data item positions with a label 
	 * attribute equal to the specified parameter
	 */
	public DatasetIterator<CURSOR> getLabelIterator(int label);
	
}
