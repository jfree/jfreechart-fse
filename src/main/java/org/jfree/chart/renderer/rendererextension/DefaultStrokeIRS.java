package org.jfree.chart.renderer.rendererextension;

import java.awt.Stroke;

import org.jfree.chart.renderer.AbstractRenderer;

/**
 * Implements a per series default item rendering strategy for the item stroke. 
 * {@link DefaultItemRenderingStrategy}
 * 
 * @author zinsmaie
 */
public class DefaultStrokeIRS extends DefaultItemRenderingStrategy implements StrokeIRS {

	/** a generated serial id */
	private static final long serialVersionUID = -8486624082434186176L;

	/**
	 * creates a new rendering strategy for the submitted renderer using its per series properties
	 * @param renderer
	 */
	public DefaultStrokeIRS(AbstractRenderer renderer) {
		super(renderer);
	}

	/**
	 * @return the item stroke the renderer defines for the series
	 */
	public Stroke getItemStroke(int row, int column) {
		return renderer.lookupSeriesStroke(row);
	}

	/**
	 * @return the item outline stroke the renderer defines for the series
	 */
	public Stroke getItemOutlineStroke(int row, int column) {
		return renderer.lookupSeriesOutlineStroke(row);
	}

}
