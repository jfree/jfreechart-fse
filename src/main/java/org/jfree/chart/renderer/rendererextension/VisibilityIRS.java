package org.jfree.chart.renderer.rendererextension;

import java.io.Serializable;

import org.jfree.chart.renderer.AbstractRenderer;

/**
 * Defines an interface to control the visibility of individual items during rendering.
 * Implementing classes can be used together with subclasses of {@link AbstractRenderer}
 * to control the rendering process.<br>
 * Works however only if the descendant of {@link AbstractRenderer} uses the per item method {@link AbstractRenderer#getItemVisible(int, int)}
 * 
 * 
 * @author zinsmaie
 */
public interface VisibilityIRS extends Serializable {

	/**
	 * Specifies an individual item by row, column and returns true if it is visible
	 * 
     * @param row  the row (or series) index (zero-based).
     * @param column  the column (or category) index (zero-based).
	 * 
	 * @return true if the item is visible
	 */
	public boolean getItemVisible(int row, int column);
	
}
