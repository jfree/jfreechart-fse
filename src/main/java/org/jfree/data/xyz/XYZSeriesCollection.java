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
 * ----------------
 * XYZDataItem.java
 * ----------------
 * (C) Copyright 2014, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited).
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 25-Apr-2014 : Version 1, copied from the Orson Charts project 
 *     (http://www.object-refinery.com/orsoncharts) (DG);
 *
 */

package org.jfree.data.xyz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.util.ObjectUtils;
import org.jfree.chart.util.ParamChecks;
import org.jfree.data.xy.AbstractXYDataset;
import org.jfree.data.xy.XYZDataset;

/**
 * A collection of {@link XYZSeries} objects (implements the {@link XYZDataset}
 * interface).
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 */
@SuppressWarnings("serial")
public class XYZSeriesCollection extends AbstractXYDataset 
        implements XYZDataset, Serializable {

    /** Storage for the data series. */
    private final List<XYZSeries> series;

    /**
     * Creates a new (empty) <code>XYZSeriesCollection</code> instance.
     */
    public XYZSeriesCollection() {
        this.series = new ArrayList<XYZSeries>();
    }

    /**
     * Returns the number of series in the collection.
     * 
     * @return The number of series in the collection. 
     */
    @Override
    public int getSeriesCount() {
        return this.series.size();
    }
    
    /**
     * Returns the index of the series with the specified key, or 
     * <code>-1</code> if there is no series with the specified key.
     * 
     * @param key  the key (<code>null</code> not permitted).
     * 
     * @return The series index or <code>-1</code>. 
     */
    public int getSeriesIndex(Comparable<?> key) {
        ParamChecks.nullNotPermitted(key, "key");
        return getSeriesKeys().indexOf(key);
    }

    /**
     * Returns a new list containing all the series keys.  Modifying this list 
     * will have no impact on the <code>XYZSeriesCollection</code> instance.
     * 
     * @return A list containing the series keys (possibly empty, but never 
     *     <code>null</code>).
     */
    public List<Comparable<?>> getSeriesKeys() {
        List<Comparable<?>> result = new ArrayList<Comparable<?>>();
        for (XYZSeries s : this.series) {
            result.add(s.getKey());
        }
        return result;
    }
    
    /**
     * Returns the key for the specified series.
     * 
     * @param seriesIndex  the series index.
     * 
     * @return The series key.
     * 
     * @since 1.3
     */
    @Override
    public Comparable<?> getSeriesKey(int seriesIndex) {
        return getSeries(seriesIndex).getKey();
    }

    /**
     * Adds a series to the collection (note that the series key must be
     * unique within the collection).
     * 
     * @param series  the series (<code>null</code> not permitted). 
     */
    public void add(XYZSeries series) {
        ParamChecks.nullNotPermitted(series, "series");
        if (getSeriesIndex(series.getKey()) >= 0) {
            throw new IllegalArgumentException("Another series with the same key already exists within the collection.");
        }
        this.series.add(series);
    }
    
    /**
     * Returns the series with the specified index.
     * 
     * @param index  the series index.
     * 
     * @return The series.
     * 
     * @since 1.2
     */
    public XYZSeries getSeries(int index) {
        return this.series.get(index);
    }
    
    /**
     * Returns the series with the specified key, or <code>null</code> if 
     * there is no such series.
     * 
     * @param key  the key (<code>null</code> not permitted).
     * 
     * @return The series. 
     * 
     * @since 1.2
     */
    public XYZSeries getSeries(Comparable<?> key) {
        ParamChecks.nullNotPermitted(key, "key");
        for (XYZSeries s : this.series) {
            if (s.getKey().equals(key)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Returns the number of items in the specified series.
     * 
     * @param seriesIndex  the series index.
     * 
     * @return The number of items in the specified series. 
     */
    @Override
    public int getItemCount(int seriesIndex) {
        ParamChecks.nullNotPermitted(this, null);
        XYZSeries s = this.series.get(seriesIndex);
        return s.getItemCount();
    }

    /**
     * Returns the x-value for one item in a series.
     * 
     * @param seriesIndex  the series index.
     * @param itemIndex  the item index.
     * 
     * @return The x-value. 
     */
    @Override
    public double getXValue(int seriesIndex, int itemIndex) {
        XYZSeries s = this.series.get(seriesIndex);
        return s.getXValue(itemIndex);
    }

    /**
     * Returns the y-value for one item in a series.
     * 
     * @param seriesIndex  the series index.
     * @param itemIndex  the item index.
     * 
     * @return The y-value. 
     */
    @Override
    public double getYValue(int seriesIndex, int itemIndex) {
        XYZSeries s = this.series.get(seriesIndex);
        return s.getYValue(itemIndex);
    }

    /**
     * Returns the z-value for one item in a series.
     * 
     * @param seriesIndex  the series index.
     * @param itemIndex  the item index.
     * 
     * @return The z-value. 
     */
    @Override
    public double getZValue(int seriesIndex, int itemIndex) {
        XYZSeries s = this.series.get(seriesIndex);
        return s.getZValue(itemIndex);
    }

    /**
     * Tests this dataset for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> not permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof XYZSeriesCollection)) {
            return false;
        }
        XYZSeriesCollection that = (XYZSeriesCollection) obj;
        if (!this.series.equals(that.series)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + ObjectUtils.hashCode(this.series);
        return hash;
    }

    @Override
    public Number getX(int series, int item) {
        return getXValue(series, item);
    }

    @Override
    public Number getY(int series, int item) {
        return getYValue(series, item);
    }

    @Override
    public Number getZ(int series, int item) {
        return getZValue(series, item);
    }

}
