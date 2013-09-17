package org.jfree.chart.renderer.rendererextension;

import java.awt.Shape;

import org.jfree.chart.renderer.AbstractRenderer;

/**
 * Implements a per series default item rendering strategy for the item shape. 
 * {@link DefaultItemRenderingStrategy}
 * 
 * @author zinsmaie
 */
public class DefaultShapeIRS extends DefaultItemRenderingStrategy implements ShapeIRS {

	/** a generated serial id */
	private static final long serialVersionUID = 7582378597877240617L;

	/**
	 * creates a new rendering strategy for the submitted renderer using its per series properties
	 * @param renderer
	 */
	public DefaultShapeIRS(AbstractRenderer renderer) {
		super(renderer);
	}

	/**
	 * @return the item shape the renderer defines for the series
	 */
	public Shape getItemShape(int row, int column) {
		return renderer.lookupSeriesShape(row);
	}

}
