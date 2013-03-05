package org.jfree.data.datasetextension.impl;

import java.util.HashMap;
import java.util.Iterator;

import org.jfree.data.datasetextension.DatasetCursor;
import org.jfree.data.datasetextension.DatasetIterator;
import org.jfree.data.datasetextension.DatasetSelectionExtension;
import org.jfree.data.datasetextension.optional.IterableSelection;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.PieDataset;
import org.jfree.data.general.SelectionChangeListener;

/**
 * Extends a pie dataset with a selection state for each data item. 
 * @author zinsmaie
 *
 */
public class PieDatasetSelectionExtension extends AbstractDatasetSelectionExtension implements IterableSelection {

	/** a generated serial id */
	private static final long serialVersionUID = -1735271052194147081L;

	/** private ref to the stored dataset to avoid casting same as ({@link AbstractDatasetSelectionExtension#dataset})*/
	private PieDataset dataset;
	
	
	/** storage for the selection attributes of the data items. */
	private HashMap selectionData;
	
	
	/**
	 * Creates a separate selection extension for the specified dataset.
	 * @param dataset
	 */
	public PieDatasetSelectionExtension(PieDataset dataset) {
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
	public PieDatasetSelectionExtension(PieDataset dataset, SelectionChangeListener initialListener) {
		super(dataset);
		addChangeListener(initialListener);
	}
	

	/**
	 * {@link DatasetSelectionExtension#isSelected(DatasetCursor)}
	 */
	public boolean isSelected(DatasetCursor cursor) {
		if (cursor instanceof PieCursor) {
			//anything else is an implementation error
			PieCursor c = (PieCursor) cursor;		
			
			if (Boolean.TRUE.equals(this.selectionData.get(c.key))) {
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
		if (cursor instanceof PieCursor) {
			//anything else is an implementation error
			PieCursor c = (PieCursor) cursor;
			if (selected) {
				this.selectionData.put(c.key, new Boolean(selected));
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
		this.selectionData = new HashMap();
		Iterator iter = this.dataset.getKeys().iterator();
		
		while (iter.hasNext()) {
			//all keys are comparable
			Comparable key = (Comparable) iter.next();
			this.selectionData.put(key, new Boolean(false));
		}
		notifiyIfRequired();
	}
	
	
	//ITERATOR IMPLEMENTATION
	
	/**
	 * {@link IterableSelection#getIterator()}
	 */
	public DatasetIterator getIterator() {
		return new PieDatasetSelectionIterator();
	}

	/**
	 * {@link IterableSelection#getSelectionIterator(boolean)}
	 */
	public DatasetIterator getSelectionIterator(boolean selected) {
		return new PieDatasetSelectionIterator(selected);
	}

	
	/**
	 * Allows to iterate over all data items or the selected / unselected data items.
	 * Provides on each iteration step a DatasetCursor that defines the position of the data item.
	 * 
	 * @author zinsmaie
	 */
	private class PieDatasetSelectionIterator implements DatasetIterator {

		//could be improved wtr speed by storing selected elements directly for faster access
		//however storage efficiency would decrease
		
		/** a generated serial id */
		private static final long serialVersionUID = -9037547822331524470L;
		
		/** current section initialized before the start of the dataset */
		private int section = -1;
		/** return all data item positions (null), only the selected (true) or only the unselected (false) */
		private Boolean filter = null;
		
		/**
		 * creates an iterator over all data item positions
		 */
		public PieDatasetSelectionIterator() {
		}
		
		/** creates an iterator that iterates either over all selected or all unselected data item positions.
		 * 
		 * @param selected if true the iterator will iterate over the selected data item positions
		 */
		public PieDatasetSelectionIterator(boolean selected) {
			filter = new Boolean(selected);
		}

		/** {@link Iterator#hasNext() */
		public boolean hasNext() {
			if (nextPosition() != -1) {
				return true;
			} 			
			return false;
		}

		/**
		 * {@link Iterator#next()}
		 */
		public Object next() {
			this.section = nextPosition();
			Comparable key = dataset.getKey(this.section);
			return new PieCursor(key);
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
		 * @return an array holding the next position section 
		 */
		private int nextPosition() {
			int pSection = this.section;
			while ((pSection+1) < dataset.getItemCount()) {
				if (filter != null) {
					Comparable key = dataset.getKey((pSection+1));
					if (!(filter.equals(selectionData.get(key)))) {
						pSection++;
						continue;
					}				
				}

				//success
				return (pSection+1);
			}

			return -1;
		}
	}
}
