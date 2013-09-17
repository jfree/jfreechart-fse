package org.jfree.chart.renderer.rendererextension;

import java.awt.Font;

import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.renderer.AbstractRenderer;

/**
 * Implements a per series default item rendering strategy for labels. 
 * {@link DefaultItemRenderingStrategy}
 * 
 * @author zinsmaie
 */
public class DefaultLabelIRS extends DefaultItemRenderingStrategy implements LabelIRS {

	/** a generated serial id */
	private static final long serialVersionUID = 5516468638404616499L;

	/**
	 * creates a new rendering strategy for the submitted renderer using its per series properties
	 * @param renderer
	 */
	public DefaultLabelIRS(AbstractRenderer renderer) {
		super(renderer);
	}

	/**
	 * @return the label font the renderer defines for the series (or the base item label font if no series font is specified)
	 */
	public Font getItemLabelFont(int row, int column) {
            Font result = renderer.getSeriesItemLabelFont(row);
            if (result == null) {
                result = renderer.getDefaultItemLabelFont();
            }
        return result;
	}

	/**
	 * @return the visibility the renderer defines for the series
	 */
	public boolean isItemLabelVisible(int row, int column) {
		return renderer.isSeriesItemLabelsVisible(row);
	}

	/**
	 * @return the item label position that is defined in the renderer for the series
	 */
	public ItemLabelPosition getPositiveItemLabelPosition(int row, int column) {
		return renderer.getSeriesPositiveItemLabelPosition(row);
	}

	/**
	 * @return the item label position that is defined in the renderer for the series
	 */
	public ItemLabelPosition getNegativeItemLabelPosition(int row, int column) {
		return renderer.getSeriesNegativeItemLabelPosition(row);
	}

	
	
}
