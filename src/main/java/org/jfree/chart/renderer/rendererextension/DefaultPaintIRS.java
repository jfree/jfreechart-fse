package org.jfree.chart.renderer.rendererextension;

import java.awt.Paint;

import org.jfree.chart.renderer.AbstractRenderer;

/**
 * Implements a per series default item rendering strategy for the item paint. 
 * {@link DefaultItemRenderingStrategy}
 * 
 * @author zinsmaie
 */
public class DefaultPaintIRS extends DefaultItemRenderingStrategy implements PaintIRS {


	/** a generated serial id */
	private static final long serialVersionUID = 2211937902401233033L;

	/**
	 * creates a new rendering strategy for the submitted renderer using its per series properties
	 * @param renderer
	 */
	public DefaultPaintIRS(AbstractRenderer renderer) {
		super(renderer);
	}

	/**
	 * @return the item paint the renderer defines for the series
	 */
	public Paint getItemPaint(int row, int column) {
		return renderer.lookupSeriesPaint(row);
	}

	/**
	 * @return the item fill paint the renderer defines for the series
	 */
	public Paint getItemFillPaint(int row, int column) {
		return renderer.lookupSeriesFillPaint(row);
	}

	/**
	 * @return the item outline paint the renderer defines for the series
	 */
	public Paint getItemOutlinePaint(int row, int column) {
		return renderer.lookupSeriesOutlinePaint(row);
	}

}
