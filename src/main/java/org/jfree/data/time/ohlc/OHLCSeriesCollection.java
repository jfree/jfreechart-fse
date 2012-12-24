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
 * -------------------------
 * OHLCSeriesCollection.java
 * -------------------------
 * (C) Copyright 2006-2012, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 04-Dec-2006 : Version 1 (DG);
 * 10-Jul-2008 : Added accessor methods for xPosition attribute (DG);
 * 23-May-2009 : Added hashCode() implementation (DG);
 * 26-Jun-2009 : Added removeSeries() methods (DG);
 * 17-Jun-2012 : Removed JCommon dependencies (DG);
 *
 */

package org.jfree.data.time.ohlc;

import java.io.Serializable;
import java.util.List;

import org.jfree.chart.HashUtilities;
import org.jfree.chart.util.ObjectUtilities;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimePeriodAnchor;
import org.jfree.data.xy.AbstractXYDataset;
import org.jfree.data.xy.OHLCDataset;
import org.jfree.data.xy.XYDataset;

/**
 * A collection of {@link OHLCSeries} objects.
 *
 * @since 1.0.4
 *
 * @see OHLCSeries
 */
public class OHLCSeriesCollection extends AbstractXYDataset
                                implements OHLCDataset, Serializable {

    /** Storage for the data series. */
    private List<OHLCSeries> data;

    private TimePeriodAnchor xPosition = TimePeriodAnchor.MIDDLE;

    /**
     * Creates a new instance of <code>OHLCSeriesCollection</code>.
     */
    public OHLCSeriesCollection() {
        this.data = new java.util.ArrayList<OHLCSeries>();
    }

    /**
     * Returns the position within each time period that is used for the X
     * value when the collection is used as an {@link XYDataset}.
     *
     * @return The anchor position (never <code>null</code>).
     *
     * @since 1.0.11
     */
    public TimePeriodAnchor getXPosition() {
        return this.xPosition;
    }

    /**
     * Sets the position within each time period that is used for the X values
     * when the collection is used as an {@link XYDataset}, then sends a
     * {@link DatasetChangeEvent} is sent to all registered listeners.
     *
     * @param anchor  the anchor position (<code>null</code> not permitted).
     *
     * @since 1.0.11
     */
    public void setXPosition(TimePeriodAnchor anchor) {
        if (anchor == null) {
            throw new IllegalArgumentException("Null 'anchor' argument.");
        }
        this.xPosition = anchor;
        notifyListeners(new DatasetChangeEvent(this, this));
    }

    /**
     * Adds a series to the collection and sends a {@link DatasetChangeEvent}
     * to all registered listeners.
     *
     * @param series  the series (<code>null</code> not permitted).
     */
    public void addSeries(OHLCSeries series) {
        if (series == null) {
            throw new IllegalArgumentException("Null 'series' argument.");
        }
        this.data.add(series);
        series.addChangeListener(this);
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
    public OHLCSeries getSeries(int series) {
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
     * Returns the x-value for a time period.
     *
     * @param period  the time period (<code>null</code> not permitted).
     *
     * @return The x-value.
     */
    protected synchronized long getX(RegularTimePeriod period) {
        long result = 0L;
        if (this.xPosition == TimePeriodAnchor.START) {
            result = period.getFirstMillisecond();
        }
        else if (this.xPosition == TimePeriodAnchor.MIDDLE) {
            result = period.getMiddleMillisecond();
        }
        else if (this.xPosition == TimePeriodAnchor.END) {
            result = period.getLastMillisecond();
        }
        return result;
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
        OHLCSeries s = this.data.get(series);
        OHLCItem di = (OHLCItem) s.getDataItem(item);
        RegularTimePeriod period = di.getPeriod();
        return getX(period);
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
	public Number getY(int series, int item) {
        OHLCSeries s = this.data.get(series);
        OHLCItem di = (OHLCItem) s.getDataItem(item);
        return di.getYValue();
    }

    /**
     * Returns the open-value for an item within a series.
     *
     * @param series  the series index.
     * @param item  the item index.
     *
     * @return The open-value.
     */
    @Override
	public double getOpenValue(int series, int item) {
        OHLCSeries s = this.data.get(series);
        OHLCItem di = (OHLCItem) s.getDataItem(item);
        return di.getOpenValue();
    }

    /**
     * Returns the open-value for an item within a series.
     *
     * @param series  the series index.
     * @param item  the item index.
     *
     * @return The open-value.
     */
    @Override
	public Number getOpen(int series, int item) {
        return getOpenValue(series, item);
    }

    /**
     * Returns the close-value for an item within a series.
     *
     * @param series  the series index.
     * @param item  the item index.
     *
     * @return The close-value.
     */
    @Override
	public double getCloseValue(int series, int item) {
        OHLCSeries s = this.data.get(series);
        OHLCItem di = (OHLCItem) s.getDataItem(item);
        return di.getCloseValue();
    }

    /**
     * Returns the close-value for an item within a series.
     *
     * @param series  the series index.
     * @param item  the item index.
     *
     * @return The close-value.
     */
    @Override
	public Number getClose(int series, int item) {
        return getCloseValue(series, item);
    }

    /**
     * Returns the high-value for an item within a series.
     *
     * @param series  the series index.
     * @param item  the item index.
     *
     * @return The high-value.
     */
    @Override
	public double getHighValue(int series, int item) {
        OHLCSeries s = this.data.get(series);
        OHLCItem di = (OHLCItem) s.getDataItem(item);
        return di.getHighValue();
    }

    /**
     * Returns the high-value for an item within a series.
     *
     * @param series  the series index.
     * @param item  the item index.
     *
     * @return The high-value.
     */
    @Override
	public Number getHigh(int series, int item) {
        return getHighValue(series, item);
    }

    /**
     * Returns the low-value for an item within a series.
     *
     * @param series  the series index.
     * @param item  the item index.
     *
     * @return The low-value.
     */
    @Override
	public double getLowValue(int series, int item) {
        OHLCSeries s = this.data.get(series);
        OHLCItem di = (OHLCItem) s.getDataItem(item);
        return di.getLowValue();
    }

    /**
     * Returns the low-value for an item within a series.
     *
     * @param series  the series index.
     * @param item  the item index.
     *
     * @return The low-value.
     */
    @Override
	public Number getLow(int series, int item) {
        return getLowValue(series, item);
    }

    /**
     * Returns <code>null</code> always, because this dataset doesn't record
     * any volume data.
     *
     * @param series  the series index (ignored).
     * @param item  the item index (ignored).
     *
     * @return <code>null</code>.
     */
    @Override
	public Number getVolume(int series, int item) {
        return null;
    }

    /**
     * Returns <code>Double.NaN</code> always, because this dataset doesn't
     * record any volume data.
     *
     * @param series  the series index (ignored).
     * @param item  the item index (ignored).
     *
     * @return <code>Double.NaN</code>.
     */
    @Override
	public double getVolumeValue(int series, int item) {
        return Double.NaN;
    }

    /**
     * Removes the series with the specified index and sends a
     * {@link DatasetChangeEvent} to all registered listeners.
     *
     * @param index  the series index.
     *
     * @since 1.0.14
     */
    public void removeSeries(int index) {
        OHLCSeries series = getSeries(index);
        if (series != null) {
            removeSeries(series);
        }
    }

    /**
     * Removes the specified series from the dataset and sends a
     * {@link DatasetChangeEvent} to all registered listeners.
     *
     * @param series  the series (<code>null</code> not permitted).
     *
     * @return <code>true</code> if the series was removed, and
     *     <code>false</code> otherwise.
     *
     * @since 1.0.14
     */
    public boolean removeSeries(OHLCSeries series) {
        if (series == null) {
            throw new IllegalArgumentException("Null 'series' argument.");
        }
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
     *
     * @since 1.0.14
     */
    public void removeAllSeries() {

        if (this.data.size() == 0) {
            return;  // nothing to do
        }

        // deregister the collection as a change listener to each series in the
        // collection
        for (OHLCSeries series : this.data) {
            series.removeChangeListener(this);
        }

        // remove all the series from the collection and notify listeners.
        this.data.clear();
        fireDatasetChanged();

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
        if (!(obj instanceof OHLCSeriesCollection)) {
            return false;
        }
        OHLCSeriesCollection that = (OHLCSeriesCollection) obj;
        if (!this.xPosition.equals(that.xPosition)) {
            return false;
        }
        return ObjectUtilities.equal(this.data, that.data);
    }

    /**
     * Returns a hash code for this instance.
     *
     * @return A hash code.
     */
    @Override
	public int hashCode() {
        int result = 137;
        result = HashUtilities.hashCode(result, this.xPosition);
        for (OHLCSeries aData : this.data) {
            result = HashUtilities.hashCode(result, aData);
        }
        return result;
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
        OHLCSeriesCollection clone
                = (OHLCSeriesCollection) super.clone();
        clone.data = ObjectUtilities.deepClone(this.data);
        return clone;
    }

}
