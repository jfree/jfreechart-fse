package org.jfree.data.datasetextension;

import java.io.Serializable;
import java.util.Iterator;

/**
 * Base interface for {@link DatasetCursor} based iterators.  
 * 
 * @author zinsmaie
 */
public interface DatasetIterator<CURSOR extends DatasetCursor> extends Iterator<CURSOR>, Serializable {
	
}
