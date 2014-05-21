/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2014, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.]
 *
 * --------------------
 * RectangleAnchor.java
 * --------------------
 * (C) Copyright 2003-2014, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 31-Oct-2003 (DG);
 * 01-Apr-2004 : Changed java.awt.geom.Dimension2D to org.jfree.ui.Size2D
 *               because of JDK bug 4976448 which persists on JDK 1.3.1 (DG);
 * 21-Jan-2005 : Changed return type of coordinates() method (DG);
 * 16-Jun-2012 : Moved from JCommon to JFreeChart (DG);
 *
 */

package org.jfree.chart.ui;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import org.jfree.chart.util.ParamChecks;

/**
 * Used to indicate an anchor point for a rectangle.
 */
public enum RectangleAnchor {

    /** Center. */
    CENTER("RectangleAnchor.CENTER"),

    /** Top. */
    TOP("RectangleAnchor.TOP"),

    /** Top-Left. */
    TOP_LEFT("RectangleAnchor.TOP_LEFT"),

    /** Top-Right. */
    TOP_RIGHT("RectangleAnchor.TOP_RIGHT"),

    /** Bottom. */
    BOTTOM("RectangleAnchor.BOTTOM"),

    /** Bottom-Left. */
    BOTTOM_LEFT("RectangleAnchor.BOTTOM_LEFT"),

    /** Bottom-Right. */
    BOTTOM_RIGHT("RectangleAnchor.BOTTOM_RIGHT"),

    /** Left. */
    LEFT("RectangleAnchor.LEFT"),

    /** Right. */
    RIGHT("RectangleAnchor.RIGHT");

    /** The name. */
    private final String name;

    /**
     * Private constructor.
     *
     * @param name  the name.
     */
    private RectangleAnchor(String name) {
        this.name = name;
    }

    /**
     * Returns the anchor point relative to the specified rectangle.
     * 
     * @param rectangle  the rectangle (<code>null</code> not permitted).
     * 
     * @return The anchor point (never <code>null</code>). 
     */
    public Point2D getAnchorPoint(Rectangle2D rectangle) {
        ParamChecks.nullNotPermitted(rectangle, "rectangle");
        Point2D result = new Point2D.Double();
        if (this == RectangleAnchor.CENTER) {
            result.setLocation(rectangle.getCenterX(), rectangle.getCenterY());
        } else if (this == RectangleAnchor.TOP) {
            result.setLocation(rectangle.getCenterX(), rectangle.getMinY());
        } else if (this == RectangleAnchor.BOTTOM) {
            result.setLocation(rectangle.getCenterX(), rectangle.getMaxY());
        } else if (this == RectangleAnchor.LEFT) {
            result.setLocation(rectangle.getMinX(), rectangle.getCenterY());
        } else if (this == RectangleAnchor.RIGHT) {
            result.setLocation(rectangle.getMaxX(), rectangle.getCenterY());
        } else if (this == RectangleAnchor.TOP_LEFT) {
            result.setLocation(rectangle.getMinX(), rectangle.getMinY());
        } else if (this == RectangleAnchor.TOP_RIGHT) {
            result.setLocation(rectangle.getMaxX(), rectangle.getMinY());
        } else if (this == RectangleAnchor.BOTTOM_LEFT) {
            result.setLocation(rectangle.getMinX(), rectangle.getMaxY());
        } else if (this == RectangleAnchor.BOTTOM_RIGHT) {
            result.setLocation(rectangle.getMaxX(), rectangle.getMaxY());
        }
        return result;
    }
    
    /**
     * Returns a string representing the object.
     *
     * @return The string.
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Returns the (x, y) coordinates of the specified anchor.
     *
     * @param rectangle  the rectangle.
     * @param anchor  the anchor.
     *
     * @return The (x, y) coordinates.
     * 
     * @deprecated Use {@link #getAnchorPoint(java.awt.geom.Rectangle2D)}.
     */
    public static Point2D coordinates(Rectangle2D rectangle, 
            RectangleAnchor anchor) {
        return anchor.getAnchorPoint(rectangle);
    }

    /**
     * Creates a new rectangle with the specified dimensions that is aligned to
     * the given anchor point <code>(anchorX, anchorY)</code>.
     *
     * @param dimensions  the dimensions (<code>null</code> not permitted).
     * @param anchorX  the x-anchor.
     * @param anchorY  the y-anchor.
     * @param anchor  the anchor (<code>null</code> not permitted).
     *
     * @return A rectangle.
     */
    public static Rectangle2D createRectangle(Size2D dimensions, double anchorX,
            double anchorY, RectangleAnchor anchor) {
        Rectangle2D result = null;
        double w = dimensions.getWidth();
        double h = dimensions.getHeight();
        if (anchor == RectangleAnchor.CENTER) {
            result = new Rectangle2D.Double(anchorX - w / 2.0, 
                    anchorY - h / 2.0, w, h);
        } else if (anchor == RectangleAnchor.TOP) {
            result = new Rectangle2D.Double(anchorX - w / 2.0, 
                    anchorY - h / 2.0, w, h);
        } else if (anchor == RectangleAnchor.BOTTOM) {
            result = new Rectangle2D.Double(anchorX - w / 2.0, 
                    anchorY - h / 2.0, w, h);
        } else if (anchor == RectangleAnchor.LEFT) {
            result = new Rectangle2D.Double(anchorX, anchorY - h / 2.0, w, h);
        } else if (anchor == RectangleAnchor.RIGHT) {
            result = new Rectangle2D.Double(anchorX - w, anchorY - h / 2.0, w, 
                    h);
        } else if (anchor == RectangleAnchor.TOP_LEFT) {
            result = new Rectangle2D.Double(anchorX - w / 2.0, 
                    anchorY - h / 2.0, w, h);
        } else if (anchor == RectangleAnchor.TOP_RIGHT) {
            result = new Rectangle2D.Double(anchorX - w / 2.0, 
                    anchorY - h / 2.0, w, h);
        } else if (anchor == RectangleAnchor.BOTTOM_LEFT) {
            result = new Rectangle2D.Double(anchorX - w / 2.0, 
                    anchorY - h / 2.0, w, h);
        } else if (anchor == RectangleAnchor.BOTTOM_RIGHT) {
            result = new Rectangle2D.Double(anchorX - w / 2.0, 
                    anchorY - h / 2.0, w, h);
        }
        return result;
    }
    
}
