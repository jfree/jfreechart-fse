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
 * ---------------
 * XYDataItem.java
 * ---------------
 * (C) Copyright 2003-2014, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 05-Aug-2003 : Renamed XYDataPair --> XYDataItem (DG);
 * 03-Feb-2004 : Fixed bug in equals() method (DG);
 * 21-Feb-2005 : Added setY(double) method (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 30-Nov-2007 : Implemented getXValue() and getYValue(), plus toString() for
 *               debugging use (DG);
 * 10-Jun-2009 : Reimplemented cloning (DG);
 * 16-Jun-2012 : Removed JCommon dependencies (DG);
 *
 */

package org.jfree.data.xy;

import java.io.Serializable;
import org.jfree.chart.util.ObjectUtils;
import org.jfree.chart.util.ParamChecks;

/**
 * Represents one (x, y) data item for an {@link XYSeries}.  Note that
 * subclasses are REQUIRED to support cloning.
 */
public class XYDataItem implements Cloneable, Comparable<XYDataItem>, 
        Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 2751513470325494890L;

    /** The x-value (<code>null</code> not permitted). */
    private Number x;

    /** The y-value. */
    private Number y;

    /**
     * Constructs a new data item.
     *
     * @param x  the x-value (<code>null</code> NOT permitted).
     * @param y  the y-value (<code>null</code> permitted).
     */
    public XYDataItem(Number x, Number y) {
        ParamChecks.nullNotPermitted(x, "x");
        this.x = x;
        this.y = y;
    }

    /**
     * Constructs a new data item.
     *
     * @param x  the x-value.
     * @param y  the y-value.
     */
    public XYDataItem(double x, double y) {
        this(new Double(x), new Double(y));
    }

    /**
     * Returns the x-value.
     *
     * @return The x-value (never <code>null</code>).
     */
    public Number getX() {
        return this.x;
    }

    /**
     * Returns the x-value as a double primitive.
     *
     * @return The x-value.
     *
     * @see #getX()
     * @see #getYValue()
     *
     * @since 1.0.9
     */
    public double getXValue() {
        // this.x is not allowed to be null...
        return this.x.doubleValue();
    }

    /**
     * Returns the y-value.
     *
     * @return The y-value (possibly <code>null</code>).
     */
    public Number getY() {
        return this.y;
    }

    /**
     * Returns the y-value as a double primitive.
     *
     * @return The y-value.
     *
     * @see #getY()
     * @see #getXValue()
     *
     * @since 1.0.9
     */
    public double getYValue() {
        double result = Double.NaN;
        if (this.y != null) {
            result = this.y.doubleValue();
        }
        return result;
    }

    /**
     * Sets the y-value for this data item.  Note that there is no
     * corresponding method to change the x-value.
     *
     * @param y  the new y-value.
     */
    public void setY(double y) {
        setY(new Double(y));
    }

    /**
     * Sets the y-value for this data item.  Note that there is no
     * corresponding method to change the x-value.
     *
     * @param y  the new y-value (<code>null</code> permitted).
     */
    public void setY(Number y) {
        this.y = y;
    }

    /**
     * Returns an integer indicating the order of this object relative to
     * another object.
     * <P>
     * For the order we consider only the x-value:
     * negative == "less-than", zero == "equal", positive == "greater-than".
     *
     * @param dataItem  the object being compared to.
     *
     * @return An integer indicating the order of this data pair object
     *      relative to another object.
     */
    @Override
    public int compareTo(XYDataItem dataItem) {
        int result;
        double compare = this.x.doubleValue()
                         - dataItem.getX().doubleValue();
        if (compare > 0.0) {
            result = 1;
        } else {
            if (compare < 0.0) {
                result = -1;
            }
            else {
                result = 0;
            }
        }
        return result;
    }

    /**
     * Returns a clone of this object.
     *
     * @return A clone.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public XYDataItem copy() {
        try {
            return (XYDataItem) clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    /**
     * Tests if this object is equal to another.
     *
     * @param obj  the object to test against for equality (<code>null</code>
     *             permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof XYDataItem)) {
            return false;
        }
        XYDataItem that = (XYDataItem) obj;
        if (!this.x.equals(that.x)) {
            return false;
        }
        if (!ObjectUtils.equal(this.y, that.y)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a hash code.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int result;
        result = this.x.hashCode();
        result = 29 * result + (this.y != null ? this.y.hashCode() : 0);
        return result;
    }

    /**
     * Returns a string representing this instance, primarily for debugging
     * use.
     *
     * @return A string.
     */
    @Override
    public String toString() {
        return "[" + getXValue() + ", " + getYValue() + "]";
    }

}
