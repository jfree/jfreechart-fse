/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2012, by Object Refinery Limited and Contributors.
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
 * -----------------------
 * TimeSeriesDataItem.java
 * -----------------------
 * (C) Copyright 2001-2012, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 11-Oct-2001 : Version 1 (DG);
 * 15-Nov-2001 : Updated Javadoc comments (DG);
 * 29-Nov-2001 : Added cloning (DG);
 * 24-Jun-2002 : Removed unnecessary import (DG);
 * 07-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 13-Mar-2003 : Renamed TimeSeriesDataPair --> TimeSeriesDataItem, moved to
 *               com.jrefinery.data.time package, implemented Serializable (DG)
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 09-Jun-2009 : Tidied up equals() (DG);
 * 17-Jun-2012 : Removed JCommon dependencies (DG);
 * 
 */

package org.jfree.data.time;

import java.io.Serializable;

import org.jfree.chart.util.ObjectUtilities;

/**
 * Represents one data item in a time series.
 * <P>
 * The time period can be any of the following:
 * <ul>
 * <li>{@link Year}</li>
 * <li>{@link Quarter}</li>
 * <li>{@link Month}</li>
 * <li>{@link Week}</li>
 * <li>{@link Day}</li>
 * <li>{@link Hour}</li>
 * <li>{@link Minute}</li>
 * <li>{@link Second}</li>
 * <li>{@link Millisecond}</li>
 * <li>{@link FixedMillisecond}</li>
 * </ul>
 *
 * The time period is an immutable property of the data item.  Data items will
 * often be sorted within a list, and allowing the time period to be changed
 * could destroy the sort order.
 * <P>
 * Implements the <code>Comparable</code> interface so that standard Java
 * sorting can be used to keep the data items in order.
 *
 */
public class TimeSeriesDataItem implements Cloneable, Comparable<TimeSeriesDataItem>, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -2235346966016401302L;

    /** The time period. */
    private RegularTimePeriod period;

    /** The value associated with the time period. */
    private Number value;

    /**
     * Constructs a new data item that associates a value with a time period.
     *
     * @param period  the time period (<code>null</code> not permitted).
     * @param value  the value (<code>null</code> permitted).
     */
    public TimeSeriesDataItem(RegularTimePeriod period, Number value) {
        if (period == null) {
            throw new IllegalArgumentException("Null 'period' argument.");
        }
        this.period = period;
        this.value = value;
    }

    /**
     * Constructs a new data item that associates a value with a time period.
     *
     * @param period  the time period (<code>null</code> not permitted).
     * @param value  the value associated with the time period.
     */
    public TimeSeriesDataItem(RegularTimePeriod period, double value) {
        this(period, new Double(value));
    }

    /**
     * Returns the time period.
     *
     * @return The time period (never <code>null</code>).
     */
    public RegularTimePeriod getPeriod() {
        return this.period;
    }

    /**
     * Returns the value.
     *
     * @return The value (<code>null</code> possible).
     *
     * @see #setValue(java.lang.Number)
     */
    public Number getValue() {
        return this.value;
    }

    /**
     * Sets the value for this data item.
     *
     * @param value  the value (<code>null</code> permitted).
     *
     * @see #getValue()
     */
    public void setValue(Number value) {
        this.value = value;
    }

    /**
     * Tests this object for equality with an arbitrary object.
     *
     * @param obj  the other object (<code>null</code> permitted).
     *
     * @return A boolean.
     */
    @Override
	public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TimeSeriesDataItem)) {
            return false;
        }
        TimeSeriesDataItem that = (TimeSeriesDataItem) obj;
        if (!ObjectUtilities.equal(this.period, that.period)) {
            return false;
        }
        if (!ObjectUtilities.equal(this.value, that.value)) {
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
        result = (this.period != null ? this.period.hashCode() : 0);
        result = 29 * result + (this.value != null ? this.value.hashCode() : 0);
        return result;
    }

    /**
     * Returns an integer indicating the order of this data pair object
     * relative to another object.
     * <P>
     * For the order we consider only the timing:
     * negative == before, zero == same, positive == after.
     *
     * @param o1  The object being compared to.
     *
     * @return An integer indicating the order of the data item object
     *         relative to another object.
     */
    @Override
	public int compareTo(TimeSeriesDataItem o1) {

            return getPeriod().compareTo(o1.getPeriod());

    }

    /**
     * Clones the data item.  Note: there is no need to clone the period or
     * value since they are immutable classes.
     *
     * @return A clone of the data item.
     */
    @Override
	public Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        }
        catch (CloneNotSupportedException e) { // won't get here...
            e.printStackTrace();
        }
        return clone;
    }

}
