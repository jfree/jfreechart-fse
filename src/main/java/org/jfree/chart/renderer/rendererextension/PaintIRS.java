package org.jfree.chart.renderer.rendererextension;

import java.awt.Paint;
import java.io.Serializable;

import org.jfree.chart.renderer.AbstractRenderer;

/**
 * Defines an interface to control the paint variables for individual items during rendering.
 * Implementing classes can be used together with subclasses of {@link AbstractRenderer}
 * to control the rendering process.<br>
 * Works however only if the descendant of {@link AbstractRenderer} uses per item methods like {@link AbstractRenderer#getItemPaint(int, int)}
 * <br>
 * <br>
 * Important Paint is not serializable see {@link IRSUtilities}) for the correct implementation of the custom read and write method. 
 * 
 * @author zinsmaie
 */
public interface PaintIRS extends Serializable {

	/**
	 * Specifies an individual item by row, column and returns the item paint
	 * 
     * @param row  the row (or series) index (zero-based).
     * @param column  the column (or category) index (zero-based).
	 * 
	 * @return a paint (never <code>null<code>)
	 */
	public Paint getItemPaint(int row, int column);


	/**
	 * Specifies an individual item by row, column and returns the item fill paint
	 * 
     * @param row  the row (or series) index (zero-based).
     * @param column  the column (or category) index (zero-based).
	 * 
	 * @return a paint used to fill the item (never <code>null<code>)
	 */
	public Paint getItemFillPaint(int row, int column);
	

	/**
	 * Specifies an individual item by row, column and returns the item outline paint
	 * 
     * @param row  the row (or series) index (zero-based).
     * @param column  the column (or category) index (zero-based).
	 * 
	 * @return a paint used for the item outline (never <code>null<code>)
	 */
	public Paint getItemOutlinePaint(int row, int column);
}
