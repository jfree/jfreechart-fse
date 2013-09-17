package org.jfree.data.datasetextension;

import java.io.Serializable;

/**
 * a marker interface for a dataset cursor. A cursor encapsulates 
 * the position of an item in a dataset and provides methods to read and write
 * this position. A cursor can thus be used as pointer to a data item (by its position not
 * by reference) as long as the dataset remains unchanged. 
 * 
 * @author zinsmaie
 *
 */
public interface DatasetCursor extends Serializable {
	
}
