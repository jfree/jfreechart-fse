package org.jfree.chart.renderer.rendererextension;

import java.awt.Font;
import java.io.Serializable;

import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.renderer.AbstractRenderer;

/**
 * Defines an interface to control the rendering of labels for individual items.
 * Implementing classes can be used together with subclasses of {@link AbstractRenderer}
 * to control the rendering process.<br>
 * Works however only if the descendant of {@link AbstractRenderer} uses per item methods like {@link AbstractRenderer#getItemLabelFont(int, int)}
 * 
 * 
 * @author zinsmaie
 */
public interface LabelIRS extends Serializable {

	/**
	 * Specifies an individual item by row, column and returns the font for its label.
	 * 
     * @param row  the row (or series) index (zero-based).
     * @param column  the column (or category) index (zero-based).
	 * 
	 * @return a Font (never <code>null<code>)
	 */
	public Font getItemLabelFont(int row, int column);

	/**
	 * Specifies an individual item by row, column and returns true if its label should be rendered
	 * 
     * @param row  the row (or series) index (zero-based).
     * @param column  the column (or category) index (zero-based).
	 * 
	 * @return true if the label should be rendered
	 */
	public boolean isItemLabelVisible(int row, int column);
	
	/**
	 * Specifies an individual item by row, column and returns the label position. 
	 * 
     * @param row  the row (or series) index (zero-based).
     * @param column  the column (or category) index (zero-based).
	 * 
	 * @return The item label position (never <code>null</code>).
	 */
	public ItemLabelPosition getPositiveItemLabelPosition(int row, int column);

	/**
	 * Specifies an individual item by row, column and returns the label position. 
	 * 
     * @param row  the row (or series) index (zero-based).
     * @param column  the column (or category) index (zero-based).
	 * 
	 * @return The item label position (never <code>null</code>).
	 */
	public ItemLabelPosition getNegativeItemLabelPosition(int row, int column);
}
