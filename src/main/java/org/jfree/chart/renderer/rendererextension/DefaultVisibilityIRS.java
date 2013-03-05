package org.jfree.chart.renderer.rendererextension;

import org.jfree.chart.renderer.AbstractRenderer;

/**
 * Implements a per series default item rendering strategy for the item visibility. 
 * {@link DefaultItemRenderingStrategy}
 * 
 * @author zinsmaie
 */
public class DefaultVisibilityIRS extends DefaultItemRenderingStrategy implements VisibilityIRS {

	/** a generated serial id */
	private static final long serialVersionUID = 559211600589929630L;

	/**
	 * creates a new rendering strategy for the submitted renderer using its per series properties
	 * @param renderer
	 */
	public DefaultVisibilityIRS(AbstractRenderer renderer) {
		super(renderer);
	}

	/**
	 * @return true if the renderer defines the series as visible 
	 */
	public boolean getItemVisible(int row, int column) {
		return renderer.isSeriesVisible(row);
	}

}
