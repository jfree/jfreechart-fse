package org.jfree.data.datasetextension.impl;

import java.util.Iterator;

import org.jfree.data.DefaultKeyedValues2D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.datasetextension.DatasetCursor;
import org.jfree.data.datasetextension.DatasetIterator;
import org.jfree.data.datasetextension.DatasetSelectionExtension;
import org.jfree.data.datasetextension.optional.IterableSelection;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.SelectionChangeListener;

/**
 * Extends a category dataset with a selection state for each data item. 
 * @author zinsmaie
 *
 */
public class CategoryDatasetSelectionExtension extends AbstractDatasetSelectionExtension implements IterableSelection {

	/** a generated serial id */
	private static final long serialVersionUID = 5138359490302459066L;

	/** private ref to the stored dataset to avoid casting same as ({@link AbstractDatasetSelectionExtension#dataset})*/
	private CategoryDataset dataset;

	
	
	//could improve here by using own bool data structure
	
	/** storage for the selection attributes of the data items. */
	private DefaultKeyedValues2D selectionData;
	/** defines true as byte value */
	private final Number TRUE = new Byte((byte) 1);
	/** defines false as byte value */
	private final Number FALSE = new Byte((byte) 0);
	
	
	/**
	 * Creates a separate selection extension for the specified dataset.
	 * @param dataset
	 */
	public CategoryDatasetSelectionExtension(CategoryDataset dataset) {
		super(dataset);
		this.dataset = dataset;
		initSelection();
	}
	
	/**
	 * Creates a separate selection extension for the specified dataset. And adds an initial
	 * selection change listener, e.g. a plot that should be redrawn on selection changes.
	 * 
	 * @param dataset
	 * @param initialListener
	 */
	public CategoryDatasetSelectionExtension(CategoryDataset dataset, SelectionChangeListener initialListener) {
		super(dataset);
		addChangeListener(initialListener);
	}

	/**
	 * {@link DatasetSelectionExtension#isSelected(DatasetCursor)}
	 */
	public boolean isSelected(DatasetCursor cursor) {
		if (cursor instanceof CategoryCursor) {
			//anything else is an implementation error
			CategoryCursor c = (CategoryCursor) cursor;
			if (TRUE == this.selectionData.getValue(c.rowKey, c.columnKey)) {
				return true;
			} else {
				return false;
			}
		} 
		
		//implementation error
		return false;
	}

	/**
	 * {@link DatasetSelectionExtension#setSelected(DatasetCursor, boolean)}
	 */
	public void setSelected(DatasetCursor cursor, boolean selected) {
		if (cursor instanceof CategoryCursor) {
			//anything else is an implementation error
			CategoryCursor c = (CategoryCursor) cursor;
			if (selected) {
				selectionData.setValue(TRUE, c.rowKey, c.columnKey);
			} else {
				selectionData.setValue(FALSE, c.rowKey, c.columnKey);
			}
			notifiyIfRequired();
		} 
	}

	/**
	 * {@link DatasetSelectionExtension#clearSelection()}
	 */
	public void clearSelection() {
		initSelection();
	}
	
	/**
	 * a change of the underlying dataset clears the slection and reinitializes it
	 */
	public void datasetChanged(DatasetChangeEvent event) {
		initSelection();
	}
	
	
	/**
	 * inits the selection attribute storage and sets all data items to unselected
	 */
	private void initSelection() {
		selectionData = new DefaultKeyedValues2D();
		for (int i = 0; i < dataset.getRowCount(); i++) {
			for (int j= 0; j < dataset.getColumnCount(); j++) {
				if (dataset.getValue(i, j) != null) {
					selectionData.addValue(FALSE, dataset.getRowKey(i), dataset.getColumnKey(j));
				}
			}
		}
		notifiyIfRequired();
	}

	
	//ITERATOR
	
	/**
	 * {@link IterableSelection#getIterator()}
	 */
	public DatasetIterator getIterator() {
		return new CategoryDatasetSelectionIterator();
	}

	/**
	 * {@link IterableSelection#getSelectionIterator(boolean)}
	 */
	public DatasetIterator getSelectionIterator(boolean selected) {
		return new CategoryDatasetSelectionIterator(selected);
	}
	
	
	
	
	/**
	 * Allows to iterate over all data items or the selected / unselected data items.
	 * Provides on each iteration step a DatasetCursor that defines the position of the data item.
	 * 
	 * @author zinsmaie
	 */
	private class CategoryDatasetSelectionIterator implements DatasetIterator {

		//could be improved wtr speed by storing selected elements directly for faster access
		//however storage efficiency would decrease

		/** a generated serial id */
		private static final long serialVersionUID = -6861323401482698708L;
		
		
		/** current row position */
		private int row = 0;
		/** current column position initialized before the start of the dataset */
		private int column = -1;
		/** return all data item positions (null), only the selected (true) or only the unselected (false) */
		private Boolean filter = null;
		
		/**
		 * creates an iterator over all data item positions
		 */
		public CategoryDatasetSelectionIterator() {
		}
		
		/** creates an iterator that iterates either over all selected or all unselected data item positions.
		 * 
		 * @param selected if true the iterator will iterate over the selected data item positions
		 */
		public CategoryDatasetSelectionIterator(boolean selected) {
			filter = new Boolean(selected);
		}

		/** {@link Iterator#hasNext() */
		public boolean hasNext() {
			if (nextPosition()[0] != -1) {
				return true;
			} 			
			return false;
		}

		/**
		 * {@link Iterator#next()}
		 */
		public Object next() {
			int[] newPos = nextPosition();
			row = newPos[0];
			column = newPos[1];
			return new CategoryCursor(dataset.getRowKey(row), dataset.getColumnKey(column));
		}

		/** {@link DatasetIteratr#nextCursor()} */
		public DatasetCursor nextCursor() {
			return (DatasetCursor)next();
		}
		
		/**
		 * iterator remove operation is not supported
		 */
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		/**
		 * calculates the next position based on the current position
		 * and the filter status.
		 * @return an array holding the next position [row, column] 
		 */
		private int[] nextPosition() {
			int pRow = this.row;
			int pColumn = this.column;
			while (pRow < dataset.getRowCount()) {
				if ((pColumn+1) >= selectionData.getColumnCount()) {
					pRow++;
					pColumn = -1;
					continue; 
				}
				if (filter != null) {
					if (!(  (filter.equals(Boolean.TRUE) && TRUE.equals(selectionData.getValue(pRow, (pColumn+1)))) || 
						  	(filter.equals(Boolean.FALSE) && FALSE.equals(selectionData.getValue(pRow, (pColumn+1))))
					)) {
						pColumn++;
						continue;
					}
				}
				
				//success
				return new int[]{pRow, (pColumn+1)};
			}
			
			return new int[]{-1,-1};
		}
	}

	
}
