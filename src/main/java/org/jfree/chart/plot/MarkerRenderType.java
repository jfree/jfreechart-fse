package org.jfree.chart.plot;

/**
 * Defines what rendering logic is used for the marker.
 * <p/>
 * Used to create more specific markings of categories.
 */
public enum MarkerRenderType {
    /** A single line on the left-most edge of the category is shown. */
    LINE_CENTER_LEFT,
    /** A single line on the right-most edge of the category is shown. */
    LINE_CENTER_RIGHT,
    /**
     * A single line is shown in the middle of the category, exactly the
     * same spot as the axis.
     */
    LINE_CENTER,
    /**
     * An area is drawn between the left-most and the right-most edges of
     * the category.
     *
     * NOTE: This is only used in {@link PolarCategoryMarker}.
     */
    AREA
}
