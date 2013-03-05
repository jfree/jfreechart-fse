package org.jfree.chart.panel.selectionhandler;

import java.awt.event.MouseEvent;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.panel.AbstractMouseHandler;

/**
 * An auxiliary mouse handler that selects data items on click.
 *  
 * Will only work together with a ChartPanel as event source 
 *  
 * @author zinsmaie
 */
public class MouseClickSelectionHandler extends AbstractMouseHandler {
		
	/** a generated serial id. */
	private static final long serialVersionUID = 1101598509484156300L;

	/**
	 * default constructor 
	 */
		public MouseClickSelectionHandler() {
			super();
		}
	
		/**
		 * Creates a new instance with a modifier restriction
		 * @param modifier e.g. shift has to be pressed InputEvents.SHIFT_MASK
		 */
	    public MouseClickSelectionHandler(int modifier) {
			super(modifier);
	    }

	    /**
	     * point wise selection
	     * <br><br>
	     * delegates to the {@link SelectionManager} of the ChartPanel, this
	     * listener is paired with.
	     */
		public void mouseClicked(MouseEvent e) {
	    	if (!(e.getSource() instanceof ChartPanel)) {
				return;
			}
	    	
	    	ChartPanel panel = (ChartPanel)e.getSource();
	    	SelectionManager selectionManager = panel.getSelectionManager();
	    	if (selectionManager != null) {
				if (!e.isShiftDown()) {
					   selectionManager.clearSelection();
					   panel.getChart().fireChartChanged();
				}
	    		selectionManager.select(e.getX(), e.getY());
	    		panel.getChart().fireChartChanged();
	    	}
	    }

		/**
		 * this is not a live handler
		 */
		public boolean isLiveHandler() {
			return false;
		}

	
}
