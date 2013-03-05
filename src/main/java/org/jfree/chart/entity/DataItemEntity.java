package org.jfree.chart.entity;

import java.awt.Shape;

import org.jfree.data.general.Dataset;

/**
 * Super class of all entities, that encapsulate the shapes and meta information of rendered data items.
 *  
 * @author zinsmaie
 */
public abstract class DataItemEntity extends ChartEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3785048574533713069L;

	/**
     * Creates a new chart entity.
     *
     * @param area  the area (<code>null</code> not permitted).
     */
    public DataItemEntity(Shape area) {
        super(area);
    }

    /**
     * Creates a new chart entity.
     *
     * @param area  the area (<code>null</code> not permitted).
     * @param toolTipText  the tool tip text (<code>null</code> permitted).
     */
    public DataItemEntity(Shape area, String toolTipText) {
       super(area, toolTipText, null);
    }

    /**
     * Creates a new entity.
     *
     * @param area  the area (<code>null</code> not permitted).
     * @param toolTipText  the tool tip text (<code>null</code> permitted).
     * @param urlText  the URL text for HTML image maps (<code>null</code>
     *                 permitted).
     */
    public DataItemEntity(Shape area, String toolTipText, String urlText) {
        super(area, toolTipText, urlText);
    }

    /**
     * Returns the dataset this entity refers to.  This can be used to
     * differentiate between items in a chart that displays more than one
     * dataset.<br>
     * Uses the general dataset interface to as return value
     *
     * @return The dataset (never <code>null</code>).
     *
     */
	public abstract Dataset getGeneralDataset();
}
