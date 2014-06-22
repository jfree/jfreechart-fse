package org.jfree.chart.plot;

import java.awt.*;
import java.io.Serializable;

/**
 * A category marker that has some extra attributes used
 * for markers in {@link SpiderWebPlot}.
 * <p/>
 * The new features include a label angle, an enum for the placement
 * of the marker label on the radar area, and a boolean determining
 * whether or not the label is to follow the radar area i.e. be circular.
 *
 * @author Jesenko Mehmedbasic
 *         created 22-05-12 16:14
 * @see {@link CategoryMarker}
 */
public class PolarCategoryMarker extends CategoryMarker implements Serializable {
    /** The rendering algorithm */
    private MarkerRenderType renderType = MarkerRenderType.LINE_CENTER_LEFT;

    /** The angle of the label */
    private double labelAngle;

    /** Whether or not the label is on an arc. */
    private boolean labelOnArc = false;

    /**
     * Constructs a PolarCategoryMarker for given the key.
     *
     * @param key the key for the marker.
     */
    public PolarCategoryMarker(Comparable key) {
        super(key);
    }

    /**
     * Constructs a PolarCategoryMarker for the given key.
     *
     * @param key    the key for the marker.
     * @param paint  the paint for the marker.
     * @param stroke the stroke of the marker line.
     */
    public PolarCategoryMarker(Comparable key, Paint paint, Stroke stroke) {
        super(key, paint, stroke);
    }


    public MarkerRenderType getRenderType() {
        return renderType;
    }

    /**
     * Sets the render type for this marker,
     * <code>null</code> values not allowed.
     *
     * @param renderType the render type to set.
     *
     * @see MarkerRenderType
     */
    public void setRenderType(MarkerRenderType renderType) {
        if (renderType == null) {
            throw new IllegalArgumentException("Null 'renderType' argument.");
        }
        this.renderType = renderType;
    }

    /**
     * Gets the label angle in degrees.
     * <p/>
     * NOTE: This is ignored if {@link #labelOnArc} is true.
     *
     * @return the angle of the label.
     */
    public double getLabelAngle() {
        return labelAngle;
    }

    /**
     * Sets the label angle.
     * <p/>
     * NOTE: This is ignored if {@link #labelOnArc} is true.
     *
     * @param labelAngle the angle of the label in degrees.
     */
    public void setLabelAngle(double labelAngle) {
        this.labelAngle = labelAngle;
    }

    /**
     * Gets the value for {@link #labelOnArc}.
     *
     * @return the value for {@link #labelOnArc}
     */
    public boolean isLabelOnArc() {
        return labelOnArc;
    }

    /**
     * Sets the value for {@link #labelOnArc}.
     *
     * @param labelOnArc the value for {@link #labelOnArc}
     */
    public void setLabelOnArc(boolean labelOnArc) {
        this.labelOnArc = labelOnArc;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof PolarCategoryMarker)) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        PolarCategoryMarker that = (PolarCategoryMarker) obj;

        boolean sameRenderTypes = getRenderType().equals(that.getRenderType());
        boolean sameAngles = getLabelAngle() == that.getLabelAngle();
        boolean sameLabelArcs = isLabelOnArc() == that.isLabelOnArc();

        return sameRenderTypes && sameAngles && sameLabelArcs;

    }
}
