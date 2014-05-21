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
 * DefaultWindDataset.java
 * -----------------------
 * (C) Copyright 2001-2012, by Achilleus Mantzios and Contributors.
 *
 * Original Author:  Achilleus Mantzios;
 * Contributor(s):   David Gilbert (for Object Refinery Limited);
 *
 * Changes
 * -------
 * 06-Feb-2002 : Version 1, based on code contributed by Achilleus
 *               Mantzios (DG);
 * 05-May-2004 : Now extends AbstractXYDataset (DG);
 * 15-Jul-2004 : Switched getX() with getXValue() and getY() with
 *               getYValue() (DG);
 * 02-Feb-2007 : Removed author tags all over JFreeChart sources (DG);
 * 22-Apr-2008 : Implemented PublicCloneable (DG);
 * 17-Jun-2012 : Removed JCommon dependencies (DG);
 *
 */

package org.jfree.data.xy;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.jfree.chart.util.PublicCloneable;

/**
 * A default implementation of the {@link WindDataset} interface.
 */
public class DefaultWindDataset extends AbstractXYDataset
        implements WindDataset, PublicCloneable {

    /** The keys for the series. */
    private List<String> seriesKeys;

    /** Storage for the series data. */
    private List<List<WindDataItem>> allSeriesData;

    /**
     * Constructs a new, empty, dataset.  Since there are currently no methods
     * to add data to an existing dataset, you should probably use a different
     * constructor.
     */
    public DefaultWindDataset() {
        this.seriesKeys = new java.util.ArrayList<String>();
        this.allSeriesData = new java.util.ArrayList<List<WindDataItem>>();
    }

    /**
     * Constructs a dataset based on the specified data array.
     *
     * @param data  the data (<code>null</code> not permitted).
     *
     * @throws NullPointerException if <code>data</code> is <code>null</code>.
     */
    public DefaultWindDataset(Object[][][] data) {
        this(seriesNameListFromDataArray(data), data);
    }

    /**
     * Constructs a dataset based on the specified data array.
     *
     * @param seriesNames  the names of the series (<code>null</code> not
     *     permitted).
     * @param data  the wind data.
     *
     * @throws NullPointerException if <code>seriesNames</code> is
     *     <code>null</code>.
     */
    public DefaultWindDataset(String[] seriesNames, Object[][][] data) {
        this(Arrays.asList(seriesNames), data);
    }

    /**
     * Constructs a dataset based on the specified data array.  The array
     * can contain multiple series, each series can contain multiple items,
     * and each item is as follows:
     * <ul>
     * <li><code>data[series][item][0]</code> - the date (either a
     *   <code>Date</code> or a <code>Number</code> that is the milliseconds
     *   since 1-Jan-1970);</li>
     * <li><code>data[series][item][1]</code> - the wind direction (1 - 12,
     *   like the numbers on a clock face);</li>
     * <li><code>data[series][item][2]</code> - the wind force (1 - 12 on the
     *   Beaufort scale)</li>
     * </ul>
     *
     * @param seriesKeys  the names of the series (<code>null</code> not
     *     permitted).
     * @param data  the wind dataset (<code>null</code> not permitted).
     *
     * @throws IllegalArgumentException if <code>seriesKeys</code>  is
     *     <code>null</code> or the number of series keys does not
     *     match the number of series in the array.
     * @throws NullPointerException if <code>data</code> is <code>null</code>.
     */
    public DefaultWindDataset(List<String> seriesKeys, Object[][][] data) {
        if (seriesKeys == null) {
            throw new IllegalArgumentException("Null 'seriesKeys' argument.");
        }
        if (seriesKeys.size() != data.length) {
            throw new IllegalArgumentException("The number of series keys does "
                    + "not match the number of series in the data array.");
        }
        this.seriesKeys = seriesKeys;
        int seriesCount = data.length;
        this.allSeriesData = new java.util.ArrayList<List<WindDataItem>>(seriesCount);

        for (int seriesIndex = 0; seriesIndex < seriesCount; seriesIndex++) {
            List<WindDataItem> oneSeriesData = new java.util.ArrayList<WindDataItem>();
            int maxItemCount = data[seriesIndex].length;
            for (int itemIndex = 0; itemIndex < maxItemCount; itemIndex++) {
                Object xObject = data[seriesIndex][itemIndex][0];
                if (xObject != null) {
                    Number xNumber;
                    if (xObject instanceof Number) {
                        xNumber = (Number) xObject;
                    }
                    else {
                        if (xObject instanceof Date) {
                            Date xDate = (Date) xObject;
                            xNumber = xDate.getTime();
                        }
                        else {
                            xNumber = 0;
                        }
                    }
                    Number windDir = (Number) data[seriesIndex][itemIndex][1];
                    Number windForce = (Number) data[seriesIndex][itemIndex][2];
                    oneSeriesData.add(new WindDataItem(xNumber, windDir,
                            windForce));
                }
            }
            Collections.sort(oneSeriesData);
            this.allSeriesData.add(seriesIndex, oneSeriesData);
        }

    }

    /**
     * Returns the number of series in the dataset.
     *
     * @return The series count.
     */
    @Override
    public int getSeriesCount() {
        return this.allSeriesData.size();
    }

    /**
     * Returns the number of items in a series.
     *
     * @param series  the series (zero-based index).
     *
     * @return The item count.
     */
    @Override
    public int getItemCount(int series) {
        if (series < 0 || series >= getSeriesCount()) {
            throw new IllegalArgumentException("Invalid series index: "
                    + series);
        }
        List<WindDataItem> oneSeriesData = this.allSeriesData.get(series);
        return oneSeriesData.size();
    }

    /**
     * Returns the key for a series.
     *
     * @param series  the series (zero-based index).
     *
     * @return The series key.
     */
    @Override
    public Comparable getSeriesKey(int series) {
        if (series < 0 || series >= getSeriesCount()) {
            throw new IllegalArgumentException("Invalid series index: "
                    + series);
        }
        return this.seriesKeys.get(series);
    }

    /**
     * Returns the x-value for one item within a series.  This should represent
     * a point in time, encoded as milliseconds in the same way as
     * java.util.Date.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The x-value for the item within the series.
     */
    @Override
    public Number getX(int series, int item) {
        List<WindDataItem> oneSeriesData = this.allSeriesData.get(series);
        WindDataItem windItem = oneSeriesData.get(item);
        return windItem.getX();
    }

    /**
     * Returns the y-value for one item within a series.  This maps to the
     * {@link #getWindForce(int, int)} method and is implemented because
     * <code>WindDataset</code> is an extension of {@link XYDataset}.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The y-value for the item within the series.
     */
    @Override
    public Number getY(int series, int item) {
        return getWindForce(series, item);
    }

    /**
     * Returns the wind direction for one item within a series.  This is a
     * number between 0 and 12, like the numbers on an upside-down clock face.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The wind direction for the item within the series.
     */
    @Override
    public Number getWindDirection(int series, int item) {
        List<WindDataItem> oneSeriesData = this.allSeriesData.get(series);
        WindDataItem windItem = oneSeriesData.get(item);
        return windItem.getWindDirection();
    }

    /**
     * Returns the wind force for one item within a series.  This is a number
     * between 0 and 12, as defined by the Beaufort scale.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The wind force for the item within the series.
     */
    @Override
    public Number getWindForce(int series, int item) {
        List<WindDataItem> oneSeriesData = this.allSeriesData.get(series);
        WindDataItem windItem = oneSeriesData.get(item);
        return windItem.getWindForce();
    }

    /**
     * Utility method for automatically generating series names.
     *
     * @param data  the wind data (<code>null</code> not permitted).
     *
     * @return An array of <i>Series N</i> with N = { 1 .. data.length }.
     *
     * @throws NullPointerException if <code>data</code> is <code>null</code>.
     */
    public static List<String> seriesNameListFromDataArray(Object[][] data) {

        int seriesCount = data.length;
        List<String> seriesNameList = new java.util.ArrayList<String>(seriesCount);
        for (int i = 0; i < seriesCount; i++) {
            seriesNameList.add("Series " + (i + 1));
        }
        return seriesNameList;

    }

    /**
     * Checks this <code>WindDataset</code> for equality with an arbitrary
     * object.  This method returns <code>true</code> if and only if:
     * <ul>
     *   <li><code>obj</code> is not <code>null</code>;</li>
     *   <li><code>obj</code> is an instance of
     *       <code>DefaultWindDataset</code>;</li>
     *   <li>both datasets have the same number of series containing identical
     *       values.</li>
     * <ul>
     *
     * @param obj  the object (<code>null</code> permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DefaultWindDataset)) {
            return false;
        }
        DefaultWindDataset that = (DefaultWindDataset) obj;
        if (!this.seriesKeys.equals(that.seriesKeys)) {
            return false;
        }
        if (!this.allSeriesData.equals(that.allSeriesData)) {
            return false;
        }
        return true;
    }

}

/**
 * A wind data item.
 */
class WindDataItem implements Comparable<WindDataItem>, Serializable {

    /** The x-value. */
    private Number x;

    /** The wind direction. */
    private Number windDir;

    /** The wind force. */
    private Number windForce;

    /**
     * Creates a new wind data item.
     *
     * @param x  the x-value.
     * @param windDir  the direction.
     * @param windForce  the force.
     */
    public WindDataItem(Number x, Number windDir, Number windForce) {
        this.x = x;
        this.windDir = windDir;
        this.windForce = windForce;
    }

    /**
     * Returns the x-value.
     *
     * @return The x-value.
     */
    public Number getX() {
        return this.x;
    }

    /**
     * Returns the wind direction.
     *
     * @return The wind direction.
     */
    public Number getWindDirection() {
        return this.windDir;
    }

    /**
     * Returns the wind force.
     *
     * @return The wind force.
     */
    public Number getWindForce() {
        return this.windForce;
    }

    /**
     * Compares this item to another object.
     *
     * @param item the other object.
     *
     * @return An int that indicates the relative comparison.
     */
    @Override
    public int compareTo(WindDataItem item) {
        if (this.x.doubleValue() > item.x.doubleValue()) {
            return 1;
        }
        else if (this.x.equals(item.x)) {
            return 0;
        }
        else {
            return -1;
        }

    }

    /**
     * Tests this <code>WindDataItem</code> for equality with an arbitrary
     * object.
     *
     * @param obj  the object (<code>null</code> permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return false;
        }
        if (!(obj instanceof WindDataItem)) {
            return false;
        }
        WindDataItem that = (WindDataItem) obj;
        if (!this.x.equals(that.x)) {
            return false;
        }
        if (!this.windDir.equals(that.windDir)) {
            return false;
        }
        if (!this.windForce.equals(that.windForce)) {
            return false;
        }
        return true;
    }

}
