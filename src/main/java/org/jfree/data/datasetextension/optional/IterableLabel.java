package org.jfree.data.datasetextension.optional;

import org.jfree.data.datasetextension.DatasetIterator;
import org.jfree.data.datasetextension.DatasetLabelExtension;

/**
 * A {@link DatasetLabelExtension} that provides iterators over all data item positions
 * and over the data item position of a defined label.
 * 
 * @author zinsmaie
 *
 */
public interface IterableLabel {

	/**
	 * @return an iterator over all data item positions
	 */
	public DatasetIterator getIterator();
	
	/**
	 * @param label
	 * @return an iterator over all data item positions with a label 
	 * attribute equal to the specified parameter
	 */
	public DatasetIterator getLabelIterator(int label);
	
}
