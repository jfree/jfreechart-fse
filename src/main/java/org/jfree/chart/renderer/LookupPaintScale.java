/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2013, by Object Refinery Limited and Contributors.
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
 * ---------------------
 * LookupPaintScale.java
 * ---------------------
 * (C) Copyright 2006-2013, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 05-Jul-2006 : Version 1 (DG);
 * 31-Jan-2007 : Fixed serialization support (DG);
 * 09-Mar-2007 : Fixed cloning (DG);
 * 14-Jun-2007 : Use double primitive in PaintItem (DG);
 * 28-Mar-2009 : Made PaintItem inner class static (DG);
 * 17-Jun-2012 : Removed JCommon dependencies (DG);
 */

package org.jfree.chart.renderer;

import java.awt.Color;
import java.awt.Paint;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.jfree.chart.util.PaintUtils;
import org.jfree.chart.util.ParamChecks;
import org.jfree.chart.util.PublicCloneable;
import org.jfree.chart.util.SerialUtils;

/**
 * A paint scale that uses a lookup table to associate paint instances
 * with data value ranges.
 *
 * @since 1.0.4
 */
public class LookupPaintScale implements PaintScale, PublicCloneable,
        Serializable {

    /**
     * Stores the paint for a value.
     */
    static class PaintItem implements Comparable<PaintItem>, Serializable {

        /** For serialization. */
        static final long serialVersionUID = 698920578512361570L;

        /** The value. */
        double value;

        /** The paint. */
        transient Paint paint;

        /**
         * Creates a new instance.
         *
         * @param value  the value.
         * @param paint  the paint.
         */
        public PaintItem(double value, Paint paint) {
            this.value = value;
            this.paint = paint;
        }

        /**
         * Compares this item to an arbitrary object.
         *
         * @param that  the object.
         *
         * @return An int defining the relative order of the objects.
         */
        @Override
        public int compareTo(PaintItem that) {
            double d1 = this.value;
            double d2 = that.value;
            if (d1 > d2) {
                return 1;
            }
            if (d1 < d2) {
                return -1;
            }
            return 0;
        }

        /**
         * Tests this item for equality with an arbitrary object.
         *
         * @param obj  the object (<code>null</code> permitted).
         *
         * @return A boolean.
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof PaintItem)) {
                return false;
            }
            PaintItem that = (PaintItem) obj;
            if (this.value != that.value) {
                return false;
            }
            if (!PaintUtils.equal(this.paint, that.paint)) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 37 * hash + (int) (Double.doubleToLongBits(this.value)
                    ^ (Double.doubleToLongBits(this.value) >>> 32));
            hash = 37 * hash + (this.paint != null ? this.paint.hashCode() : 0);
            return hash;
        }

        /**
         * Provides serialization support.
         *
         * @param stream  the output stream.
         *
         * @throws IOException  if there is an I/O error.
         */
        private void writeObject(ObjectOutputStream stream) throws IOException {
            stream.defaultWriteObject();
            SerialUtils.writePaint(this.paint, stream);
        }

        /**
         * Provides serialization support.
         *
         * @param stream  the input stream.
         *
         * @throws IOException  if there is an I/O error.
         * @throws ClassNotFoundException  if there is a classpath problem.
         */
        private void readObject(ObjectInputStream stream)
                throws IOException, ClassNotFoundException {
            stream.defaultReadObject();
            this.paint = SerialUtils.readPaint(stream);
        }

    }

    /** For serialization. */
    static final long serialVersionUID = -5239384246251042006L;

    /** The lower bound. */
    private double lowerBound;

    /** The upper bound. */
    private double upperBound;

    /** The default paint. */
    private transient Paint defaultPaint;

    /** The lookup table. */
    private List<PaintItem> lookupTable;

    /**
     * Creates a new paint scale.
     */
    public LookupPaintScale() {
        this(0.0, 1.0, Color.LIGHT_GRAY);
    }

    /**
     * Creates a new paint scale with the specified default paint.
     *
     * @param lowerBound  the lower bound.
     * @param upperBound  the upper bound.
     * @param defaultPaint  the default paint (<code>null</code> not
     *     permitted).
     */
    public LookupPaintScale(double lowerBound, double upperBound,
            Paint defaultPaint) {
        if (lowerBound >= upperBound) {
            throw new IllegalArgumentException(
                    "Requires lowerBound < upperBound.");
        }
        ParamChecks.nullNotPermitted(defaultPaint, "defaultPaint");
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.defaultPaint = defaultPaint;
        this.lookupTable = new java.util.ArrayList<PaintItem>();
    }

    /**
     * Returns the default paint (never <code>null</code>).
     *
     * @return The default paint.
     */
    public Paint getDefaultPaint() {
        return this.defaultPaint;
    }

    /**
     * Returns the lower bound.
     *
     * @return The lower bound.
     *
     * @see #getUpperBound()
     */
    @Override
    public double getLowerBound() {
        return this.lowerBound;
    }

    /**
     * Returns the upper bound.
     *
     * @return The upper bound.
     *
     * @see #getLowerBound()
     */
    @Override
    public double getUpperBound() {
        return this.upperBound;
    }

    /**
     * Adds an entry to the lookup table.  Any values from <code>n</code> up
     * to but not including the next value in the table take on the specified
     * <code>paint</code>.
     *
     * @param value  the data value.
     * @param paint  the paint (<code>null</code> not permitted).
     *
     * @since 1.0.6
     */
    public void add(double value, Paint paint) {
        ParamChecks.requireInRange(value, "value", lowerBound, upperBound);
        ParamChecks.nullNotPermitted(paint, "paint");

        PaintItem item = new PaintItem(value, paint);
        int index = Collections.binarySearch(this.lookupTable, item);
        if (index >= 0) {
            this.lookupTable.set(index, item);
        }
        else {
            this.lookupTable.add(-(index + 1), item);
        }
    }

    /**
     * Returns the paint associated with the specified value.
     *
     * @param value  the value.
     *
     * @return The paint.
     *
     * @see #getDefaultPaint()
     */
    @Override
    public Paint getPaint(double value) {

        // handle value outside bounds...
        if (value < this.lowerBound || value > this.upperBound
                || Double.isNaN(value)) {
            return this.defaultPaint;
        }

        int count = this.lookupTable.size();
        if (count == 0) {
            return this.defaultPaint;
        }

        // handle special case where value is less that item zero
        PaintItem item = this.lookupTable.get(0);
        if (value < item.value) {
            return this.defaultPaint;
        }

        // for value in bounds, do the lookup...
        int low = 0;
        int high = this.lookupTable.size() - 1;
        while (high - low > 1) {
            int current = (low + high) / 2;
            item = this.lookupTable.get(current);
            if (value >= item.value) {
                low = current;
            }
            else {
                high = current;
            }
        }
        if (high > low) {
            item = this.lookupTable.get(high);
            if (value < item.value) {
                item = this.lookupTable.get(low);
            }
        }
        return (item != null ? item.paint : this.defaultPaint);
    }


    /**
     * Tests this instance for equality with an arbitrary object.
     *
     * @param obj  the object (<code>null</code> permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LookupPaintScale)) {
            return false;
        }
        LookupPaintScale that = (LookupPaintScale) obj;
        if (this.lowerBound != that.lowerBound) {
            return false;
        }
        if (this.upperBound != that.upperBound) {
            return false;
        }
        if (!PaintUtils.equal(this.defaultPaint, that.defaultPaint)) {
            return false;
        }
        if (!this.lookupTable.equals(that.lookupTable)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.lowerBound)
                ^ (Double.doubleToLongBits(this.lowerBound) >>> 32));
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.upperBound)
                ^ (Double.doubleToLongBits(this.upperBound) >>> 32));
        hash = 59 * hash + (this.defaultPaint != null
                ? this.defaultPaint.hashCode() : 0);
        hash = 59 * hash + (this.lookupTable != null
                ? this.lookupTable.hashCode() : 0);
        return hash;
    }

    /**
     * Returns a clone of the instance.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if there is a problem cloning the
     *     instance.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        LookupPaintScale clone = (LookupPaintScale) super.clone();
        clone.lookupTable = new java.util.ArrayList<PaintItem>(this.lookupTable);
        return clone;
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the output stream.
     *
     * @throws IOException  if there is an I/O error.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        SerialUtils.writePaint(this.defaultPaint, stream);
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the input stream.
     *
     * @throws IOException  if there is an I/O error.
     * @throws ClassNotFoundException  if there is a classpath problem.
     */
    private void readObject(ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.defaultPaint = SerialUtils.readPaint(stream);
    }

}
