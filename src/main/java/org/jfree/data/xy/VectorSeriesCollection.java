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
 * ---------------------------
 * VectorSeriesCollection.java
 * ---------------------------
 * (C) Copyright 2007-2012, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 30-Jan-2007 : Version 1 (DG);
 * 24-May-2007 : Added indexOf(), removeSeries() and removeAllSeries()
 *               methods (DG);
 * 25-May-2007 : Moved from experimental to the main source tree (DG);
 * 22-Apr-2008 : Implemented PublicCloneable (DG);
 * 16-Jun-2012 : Removed JCommon dependencies (DG);
 *
 */

package org.jfree.data.xy;

import java.io.Serializable;
import java.util.List;

import org.jfree.chart.util.ObjectUtilities;
import org.jfree.chart.util.ParamChecks;
import org.jfree.chart.util.PublicCloneable;
import org.jfree.data.general.DatasetChangeEvent;

/**
 * A collection of {@link VectorSeries} objects.
 *
 * @since 1.0.6
 */
public class VectorSeriesCollection extends AbstractXYDataset
        implements VectorXYDataset, PublicCloneable, Serializable {

    /** Storage for the data series. */
    private List<VectorSeries> data;

    /**
     * Creates a new instance of <code>VectorSeriesCollection</code>.
     */
    public VectorSeriesCollection() {
        this.data = new java.util.ArrayList<VectorSeries>();
    }

    /**
     * Adds a series to the collection and sends a {@link DatasetChangeEvent}
     * to all registered listeners.
     *
     * @param series  the series (<code>null</code> not permitted).
     */
    public void addSeries(VectorSeries series) {
        ParamChecks.nullNotPermitted(series, "series");
        this.data.add(series);
        series.addChangeListener(this);
        fireDatasetChanged();
    }

    /**
     * Removes the specified series from the collection and sends a
     * {@link DatasetChangeEvent} to all registered listeners.
     *
     * @param series  the series (<code>null</code> not permitted).
     *
     * @return A boolean indicating whether the series has actually been
     *         removed.
     */
    public boolean removeSeries(VectorSeries series) {
        ParamChecks.nullNotPermitted(series, "series");
        boolean removed = this.data.remove(series);
        if (removed) {
            series.removeChangeListener(this);
            fireDatasetChanged();
        }
        return removed;
    }

    /**
     * Removes all the series from the collection and sends a
     * {@link DatasetChangeEvent} to all registered listeners.
     */
    public void removeAllSeries() {

        // deregister the collection as a change listener to each series in the
        // collection
        for (VectorSeries series : this.data) {
            series.removeChangeListener(this);
        }

        // remove all the series from the collection and notify listeners.
        this.data.clear();
        fireDatasetChanged();

    }

    /**
     * Returns the number of series in the collection.
     *
     * @return The series count.
     */
    @Override
    public int getSeriesCount() {
        return this.data.size();
    }

    /**
     * Returns a series from the collection.
     *
     * @param series  the series index (zero-based).
     *
     * @return The series.
     *
     * @throws IllegalArgumentException if <code>series</code> is not in the
     *     range <code>0</code> to <code>getSeriesCount() - 1</code>.
     */
    public VectorSeries getSeries(int series) {
        if ((series < 0) || (series >= getSeriesCount())) {
            throw new IllegalArgumentException("Series index out of bounds");
        }
        return this.data.get(series);
    }

    /**
     * Returns the key for a series.
     *
     * @param series  the series index (in the range <code>0</code> to
     *     <code>getSeriesCount() - 1</code>).
     *
     * @return The key for a series.
     *
     * @throws IllegalArgumentException if <code>series</code> is not in the
     *     specified range.
     */
    @Override
    public Comparable getSeriesKey(int series) {
        // defer argument checking
        return getSeries(series).getKey();
    }

    /**
     * Returns the index of the specified series, or -1 if that series is not
     * present in the dataset.
     *
     * @param series  the series (<code>null</code> not permitted).
     *
     * @return The series index.
     */
    public int indexOf(VectorSeries series) {
        ParamChecks.nullNotPermitted(series, "series");
        return this.data.indexOf(series);
    }

    /**
     * Returns the number of items in the specified series.
     *
     * @param series  the series (zero-based index).
     *
     * @return The item count.
     *
     * @throws IllegalArgumentException if <code>series</code> is not in the
     *     range <code>0</code> to <code>getSeriesCount() - 1</code>.
     */
    @Override
    public int getItemCount(int series) {
        // defer argument checking
        return getSeries(series).getItemCount();
    }

    /**
     * Returns the x-value for an item within a series.
     *
     * @param series  the series index.
     * @param item  the item index.
     *
     * @return The x-value.
     */
    @Override
    public double getXValue(int series, int item) {
        VectorSeries s = this.data.get(series);
        VectorDataItem di = (VectorDataItem) s.getDataItem(item);
        return di.getXValue();
    }

    /**
     * Returns the x-value for an item within a series.  Note that this method
     * creates a new {@link Double} instance every time it is called---use
     * {@link #getXValue(int, int)} instead, if possible.
     *
     * @param series  the series index.
     * @param item  the item index.
     *
     * @return The x-value.
     */
    @Override
    public Number getX(int series, int item) {
        return getXValue(series, item);
    }

    /**
     * Returns the y-value for an item within a series.
     *
     * @param series  the series index.
     * @param item  the item index.
     *
     * @return The y-value.
     */
    @Override
    public double getYValue(int series, int item) {
        VectorSeries s = this.data.get(series);
        VectorDataItem di = (VectorDataItem) s.getDataItem(item);
        return di.getYValue();
    }

    /**
     * Returns the y-value for an item within a series.  Note that this method
     * creates a new {@link Double} instance every time it is called---use
     * {@link #getYValue(int, int)} instead, if possible.
     *
     * @param series  the series index.
     * @param item  the item index.
     *
     * @return The y-value.
     */
    @Override
    public Number getY(int series, int item) {
        return getYValue(series, item);
    }

    /**
     * Returns the vector for an item in a series.
     *
     * @param series  the series index.
     * @param item  the item index.
     *
     * @return The vector (possibly <code>null</code>).
     */
    @Override
    public Vector getVector(int series, int item) {
        VectorSeries s = this.data.get(series);
        VectorDataItem di = (VectorDataItem) s.getDataItem(item);
        return di.getVector();
    }

    /**
     * Returns the x-component of the vector for an item in a series.
     *
     * @param series  the series index.
     * @param item  the item index.
     *
     * @return The x-component of the vector.
     */
    @Override
    public double getVectorXValue(int series, int item) {
        VectorSeries s = this.data.get(series);
        VectorDataItem di = (VectorDataItem) s.getDataItem(item);
        return di.getVectorX();
    }

    /**
     * Returns the y-component of the vector for an item in a series.
     *
     * @param series  the series index.
     * @param item  the item index.
     *
     * @return The y-component of the vector.
     */
    @Override
    public double getVectorYValue(int series, int item) {
        VectorSeries s = this.data.get(series);
        VectorDataItem di = (VectorDataItem) s.getDataItem(item);
        return di.getVectorY();
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
        if (!(obj instanceof VectorSeriesCollection)) {
            return false;
        }
        VectorSeriesCollection that = (VectorSeriesCollection) obj;
        return ObjectUtilities.equal(this.data, that.data);
    }

    /**
     * Returns a clone of this instance.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if there is a problem.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        VectorSeriesCollection clone
                = (VectorSeriesCollection) super.clone();
        clone.data = ObjectUtilities.deepClone(this.data);
        return clone;
    }

}
