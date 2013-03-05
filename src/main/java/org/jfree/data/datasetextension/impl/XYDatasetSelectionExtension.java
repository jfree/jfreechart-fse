package org.jfree.data.datasetextension.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jfree.data.datasetextension.DatasetCursor;
import org.jfree.data.datasetextension.DatasetIterator;
import org.jfree.data.datasetextension.DatasetSelectionExtension;
import org.jfree.data.datasetextension.optional.IterableSelection;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.SelectionChangeListener;
import org.jfree.data.xy.XYDataset;

/**
 * Extends a xy dataset with a selection state for each data item. 
 * @author zinsmaie
 *
 */
public class XYDatasetSelectionExtension extends
		AbstractDatasetSelectionExtension implements IterableSelection {

	/** a generated serial id */
	private static final long serialVersionUID = 4859712483757720877L;

	/** private ref to the stored dataset to avoid casting same as ({@link AbstractDatasetSelectionExtension#dataset})*/
	private XYDataset dataset;
	
	/** storage for the selection attributes of the data items. */
	private List[] selectionData;

	
	/**
	 * Creates a separate selection extension for the specified dataset.
	 * @param dataset
	 */
	public XYDatasetSelectionExtension(XYDataset dataset) {
		super(dataset);
		this.dataset = dataset;
		selectionData = new ArrayList[dataset.getSeriesCount()];
		
		initSelection();
	}

	/**
	 * Creates a separate selection extension for the specified dataset. And adds an initial
	 * selection change listener, e.g. a plot that should be redrawn on selection changes.
	 * 
	 * @param dataset
	 * @param initialListener
	 */
	public XYDatasetSelectionExtension(XYDataset dataset,
			SelectionChangeListener initialListener) {
		super(dataset);
		addSelectionChangeListener(initialListener);
	}

	
	/**
	 * a change of the underlying dataset clears the slection and reinitializes it.
	 */
	public void datasetChanged(DatasetChangeEvent event) {
		initSelection();
	}
	

	/**
	 * {@link DatasetSelectionExtension#isSelected(DatasetCursor)}
	 */
	public boolean isSelected(DatasetCursor cursor) {
		if (cursor instanceof XYCursor) {
			// anything else is an implementation error
			XYCursor c = (XYCursor) cursor;
			if (((Boolean) selectionData[c.series].get(c.item)).booleanValue()) {
				return true;
			} else {
				return false;
			}
		}

		// implementation error
		return false;
	}

	/**
	 * {@link DatasetSelectionExtension#setSelected(DatasetCursor, boolean)}
	 */
	public void setSelected(DatasetCursor cursor, boolean selected) {
		if (cursor instanceof XYCursor) {
			// anything else is an implementation error
			XYCursor c = (XYCursor) cursor;
			selectionData[c.series].set(c.item, new Boolean(selected));
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
	 * inits the selection attribute storage and sets all data items to unselected
	 */
	private void initSelection() {
		for (int i = 0; i < dataset.getSeriesCount(); i++) {
			selectionData[i] = new ArrayList(dataset.getItemCount(i));
			for (int j = 0; j < dataset.getItemCount(i); j++) {
				selectionData[i].add(new Boolean(false));
			}
		}
		notifiyIfRequired();
	}

	// ITERATOR IMPLEMENTATION

	/**
	 * {@link IterableSelection#getIterator()}
	 */
	public DatasetIterator getIterator() {
		return new XYDatasetSelectionIterator();
	}

	/**
	 * {@link IterableSelection#getSelectionIterator(boolean)}
	 */
	public DatasetIterator getSelectionIterator(boolean selected) {
		return new XYDatasetSelectionIterator(selected);
	}
	
	

	/**
	 * Allows to iterate over all data items or the selected / unselected data items.
	 * Provides on each iteration step a DatasetCursor that defines the position of the data item.
	 * 
	 * @author zinsmaie
	 */
	private class XYDatasetSelectionIterator implements DatasetIterator {
		
		//could be improved wtr speed by storing selected elements directly for faster access
		//however storage efficiency would decrease
		
		/** a generated serial id */
		private static final long serialVersionUID = 125607273863837608L;
		
		/** current series position */
		private int series = 0;
		/** current item position initialized before the start of the dataset */
		private int item = -1;
		/** return all data item positions (null), only the selected (true) or only the unselected (false) */
		private Boolean filter = null;

		/**
		 * creates an iterator over all data item positions
		 */
		public XYDatasetSelectionIterator() {
		}

		/** creates an iterator that iterates either over all selected or all unselected data item positions.
		 * 
		 * @param selected if true the iterator will iterate over the selected data item positions
		 */
		public XYDatasetSelectionIterator(boolean selected) {
			this.filter = new Boolean(selected);
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
			this.series = newPos[0];
			this.item = newPos[1];
			return new XYCursor(this.series, this.item);
		}

		/** {@link DatasetIteratr#nextCursor()} */
		public DatasetCursor nextCursor() {
			return (DatasetCursor) next();
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
		 * @return an array holding the next position [series, item] 
		 */
		private int[] nextPosition() {
			int pSeries = this.series;
			int pItem = this.item;
			
			while (pSeries < selectionData.length) {
				if ((pItem+1) >= selectionData[pSeries].size()) {
					pSeries++;
					pItem = -1;
					continue;
				}				
				if (filter != null) {
					if (!(filter.equals(selectionData[pSeries].get(pItem + 1)))) {
						pItem++;
						continue;
					}
				}
				
				//success
				return new int[] {pSeries, (pItem+1)};
			}
			
			return new int[] {-1,-1};
		}
	}

}
