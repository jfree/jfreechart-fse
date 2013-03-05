package org.jfree.chart.panel.selectionhandler;

import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

/**
 * Provides methods to handle data item selection based on a selection region
 * or a selection point and to clear the current selection. 
 * 
 * @author zinsmaie
 */
public interface SelectionManager extends Serializable {

	/**
	 * selects the data item at the point x,y
	 * @param x
	 * @param y
	 */
	public void select(double x, double y);
	
	/**
	 * @param selection a rectangular selection area
	 */
	public void select(Rectangle2D selection);
	
	/**
	 * @param selection free defiend selection area
	 */
	public void select(GeneralPath selection);

	/**
	 * clear the current selection (deselect all)
	 */
	public void clearSelection();
	
}
