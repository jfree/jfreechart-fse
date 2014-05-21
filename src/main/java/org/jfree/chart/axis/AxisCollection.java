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
 * -------------------
 * AxisCollection.java
 * -------------------
 * (C) Copyright 2003-2014, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 03-Nov-2003 : Added standard header (DG);
 * 17-Jun-2012 : Removed JCommon dependencies (DG);
 *
 */

package org.jfree.chart.axis;

import java.util.List;

import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.util.ParamChecks;

/**
 * A collection of axes that have been assigned to the TOP, BOTTOM, LEFT or
 * RIGHT of a chart.  This class is used internally by JFreeChart, you won't
 * normally need to use it yourself.
 */
public class AxisCollection {

    /** The axes that need to be drawn at the top of the plot area. */
    private List<Axis> axesAtTop;

    /** The axes that need to be drawn at the bottom of the plot area. */
    private List<Axis> axesAtBottom;

    /** The axes that need to be drawn at the left of the plot area. */
    private List<Axis> axesAtLeft;

    /** The axes that need to be drawn at the right of the plot area. */
    private List<Axis> axesAtRight;

    /**
     * Creates a new empty collection.
     */
    public AxisCollection() {
        this.axesAtTop = new java.util.ArrayList<Axis>();
        this.axesAtBottom = new java.util.ArrayList<Axis>();
        this.axesAtLeft = new java.util.ArrayList<Axis>();
        this.axesAtRight = new java.util.ArrayList<Axis>();
    }

    /**
     * Returns a list of the axes (if any) that need to be drawn at the top of
     * the plot area.
     *
     * @return A list of axes.
     */
    public List<Axis> getAxesAtTop() {
        return this.axesAtTop;
    }

   /**
    * Returns a list of the axes (if any) that need to be drawn at the bottom
    * of the plot area.
    *
    * @return A list of axes.
    */
   public List<Axis> getAxesAtBottom() {
        return this.axesAtBottom;
    }

    /**
     * Returns a list of the axes (if any) that need to be drawn at the left
     * of the plot area.
     *
     * @return A list of axes.
     */
    public List<Axis> getAxesAtLeft() {
        return this.axesAtLeft;
    }

    /**
    * Returns a list of the axes (if any) that need to be drawn at the right
    * of the plot area.
    *
    * @return A list of axes.
    */
    public List<Axis> getAxesAtRight() {
        return this.axesAtRight;
    }

    /**
     * Adds an axis to the collection.
     *
     * @param axis  the axis (<code>null</code> not permitted).
     * @param edge  the edge of the plot that the axis should be drawn on
     *              (<code>null</code> not permitted).
     */
    public void add(Axis axis, RectangleEdge edge) {
        ParamChecks.nullNotPermitted(axis, "axis");
        ParamChecks.nullNotPermitted(edge, "edge");
        if (edge == RectangleEdge.TOP) {
            this.axesAtTop.add(axis);
        }
        else if (edge == RectangleEdge.BOTTOM) {
            this.axesAtBottom.add(axis);
        }
        else if (edge == RectangleEdge.LEFT) {
            this.axesAtLeft.add(axis);
        }
        else if (edge == RectangleEdge.RIGHT) {
            this.axesAtRight.add(axis);
        }
    }

}
