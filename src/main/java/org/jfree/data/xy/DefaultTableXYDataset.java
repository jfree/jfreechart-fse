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
 * --------------------------
 * DefaultTableXYDataset.java
 * --------------------------
 * (C) Copyright 2003-2012, by Richard Atkinson and Contributors.
 *
 * Original Author:  Richard Atkinson;
 * Contributor(s):   Jody Brownell;
 *                   David Gilbert (for Object Refinery Limited);
 *                   Andreas Schroeder;
 *
 * Changes:
 * --------
 * 27-Jul-2003 : XYDataset that forces each series to have a value for every
 *               X-point which is essential for stacked XY area charts (RA);
 * 18-Aug-2003 : Fixed event notification when removing and updating
 *               series (RA);
 * 22-Sep-2003 : Functionality moved from TableXYDataset to
 *               DefaultTableXYDataset (RA);
 * 23-Dec-2003 : Added patch for large datasets, submitted by Jody
 *               Brownell (DG);
 * 16-Feb-2004 : Added pruning methods (DG);
 * 31-Mar-2004 : Provisional implementation of IntervalXYDataset (AS);
 * 01-Apr-2004 : Sound implementation of IntervalXYDataset (AS);
 * 05-May-2004 : Now extends AbstractIntervalXYDataset (DG);
 * 15-Jul-2004 : Switched getX() with getXValue() and getY() with
 *               getYValue() (DG);
 * 18-Aug-2004 : Moved from org.jfree.data --> org.jfree.data.xy (DG);
 * 11-Jan-2005 : Removed deprecated code in preparation for the 1.0.0
 *               release (DG);
 * 05-Oct-2005 : Made the interval delegate a dataset listener (DG);
 * 02-Feb-2007 : Removed author tags all over JFreeChart sources (DG);
 * 22-Apr-2008 : Implemented PublicCloneable (DG);
 * 10-Jun-2009 : Simplified getX() and getY() (DG);
 * 17-Jun-2012 : Removed JCommon dependencies (DG);
 * 
 */

package org.jfree.data.xy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jfree.chart.util.ObjectUtilities;
import org.jfree.chart.util.PublicCloneable;
import org.jfree.data.DomainInfo;
import org.jfree.data.Range;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.SeriesChangeEvent;

/**
 * An {@link XYDataset} where every series shares the same x-values (required
 * for generating stacked area charts).
 */
public class DefaultTableXYDataset extends AbstractIntervalXYDataset
        implements TableXYDataset, IntervalXYDataset, DomainInfo,
                   PublicCloneable {

    /**
     * Storage for the data - this list will contain zero, one or many
     * XYSeries objects.
     */
    private List<XYSeries> data;

    /** Storage for the x values. */
    private HashSet<Number> xPoints;

    /** A flag that controls whether or not events are propogated. */
    private boolean propagateEvents = true;

    /** A flag that controls auto pruning. */
    private boolean autoPrune = false;

    /** The delegate used to control the interval width. */
    private IntervalXYDelegate intervalDelegate;

    /**
     * Creates a new empty dataset.
     */
    public DefaultTableXYDataset() {
        this(false);
    }

    /**
     * Creates a new empty dataset.
     *
     * @param autoPrune  a flag that controls whether or not x-values are
     *                   removed whenever the corresponding y-values are all
     *                   <code>null</code>.
     */
    public DefaultTableXYDataset(boolean autoPrune) {
        this.autoPrune = autoPrune;
        this.data = new ArrayList<XYSeries>();
        this.xPoints = new HashSet<Number>();
        this.intervalDelegate = new IntervalXYDelegate(this, false);
        addChangeListener(this.intervalDelegate);
    }

    /**
     * Returns the flag that controls whether or not x-values are removed from
     * the dataset when the corresponding y-values are all <code>null</code>.
     *
     * @return A boolean.
     */
    public boolean isAutoPrune() {
        return this.autoPrune;
    }

    /**
     * Adds a series to the collection and sends a {@link DatasetChangeEvent}
     * to all registered listeners.  The series should be configured to NOT
     * allow duplicate x-values.
     *
     * @param series  the series (<code>null</code> not permitted).
     */
    public void addSeries(XYSeries series) {
        if (series == null) {
            throw new IllegalArgumentException("Null 'series' argument.");
        }
        if (series.getAllowDuplicateXValues()) {
            throw new IllegalArgumentException(
                "Cannot accept XYSeries that allow duplicate values. "
                + "Use XYSeries(seriesName, <sort>, false) constructor."
            );
        }
        updateXPoints(series);
        this.data.add(series);
        series.addChangeListener(this);
        fireDatasetChanged();
    }

    /**
     * Adds any unique x-values from 'series' to the dataset, and also adds any
     * x-values that are in the dataset but not in 'series' to the series.
     *
     * @param series  the series (<code>null</code> not permitted).
     */
    private void updateXPoints(XYSeries series) {
        if (series == null) {
            throw new IllegalArgumentException("Null 'series' not permitted.");
        }
        Set<Number> seriesXPoints = new HashSet<Number>();
        boolean savedState = this.propagateEvents;
        this.propagateEvents = false;
        for (int itemNo = 0; itemNo < series.getItemCount(); itemNo++) {
            Number xValue = series.getX(itemNo);
            seriesXPoints.add(xValue);
            if (!this.xPoints.contains(xValue)) {
                this.xPoints.add(xValue);
                for (XYSeries dataSeries : this.data) {
                    if (!dataSeries.equals(series)) {
                        dataSeries.add(xValue, null);
                    }
                }
            }
        }
        for (Number xPoint : this.xPoints) {
            if (!seriesXPoints.contains(xPoint)) {
                series.add(xPoint, null);
            }
        }
        this.propagateEvents = savedState;
    }

    /**
     * Updates the x-values for all the series in the dataset.
     */
    public void updateXPoints() {
        this.propagateEvents = false;
        for (XYSeries aData : this.data) {
            updateXPoints(aData);
        }
        if (this.autoPrune) {
            prune();
        }
        this.propagateEvents = true;
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
     * Returns the number of x values in the dataset.
     *
     * @return The number of x values in the dataset.
     */
    @Override
	public int getItemCount() {
        if (this.xPoints == null) {
            return 0;
        }
        else {
            return this.xPoints.size();
        }
    }

    /**
     * Returns a series.
     *
     * @param series  the series (zero-based index).
     *
     * @return The series (never <code>null</code>).
     */
    public XYSeries getSeries(int series) {
        if ((series < 0) || (series >= getSeriesCount())) {
            throw new IllegalArgumentException("Index outside valid range.");
        }
        return this.data.get(series);
    }

    /**
     * Returns the key for a series.
     *
     * @param series  the series (zero-based index).
     *
     * @return The key for a series.
     */
    @Override
	public Comparable getSeriesKey(int series) {
        // check arguments...delegated
        return getSeries(series).getKey();
    }

    /**
     * Returns the number of items in the specified series.
     *
     * @param series  the series (zero-based index).
     *
     * @return The number of items in the specified series.
     */
    @Override
	public int getItemCount(int series) {
        // check arguments...delegated
        return getSeries(series).getItemCount();
    }

    /**
     * Returns the x-value for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The x-value for the specified series and item.
     */
    @Override
	public Number getX(int series, int item) {
        XYSeries s = this.data.get(series);
        return s.getX(item);

    }

    /**
     * Returns the starting X value for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The starting X value.
     */
    @Override
	public Number getStartX(int series, int item) {
        return this.intervalDelegate.getStartX(series, item);
    }

    /**
     * Returns the ending X value for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The ending X value.
     */
    @Override
	public Number getEndX(int series, int item) {
        return this.intervalDelegate.getEndX(series, item);
    }

    /**
     * Returns the y-value for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param index  the index of the item of interest (zero-based).
     *
     * @return The y-value for the specified series and item (possibly
     *         <code>null</code>).
     */
    @Override
	public Number getY(int series, int index) {
        XYSeries s = this.data.get(series);
        return s.getY(index);
    }

    /**
     * Returns the starting Y value for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The starting Y value.
     */
    @Override
	public Number getStartY(int series, int item) {
        return getY(series, item);
    }

    /**
     * Returns the ending Y value for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The ending Y value.
     */
    @Override
	public Number getEndY(int series, int item) {
        return getY(series, item);
    }

    /**
     * Removes all the series from the collection and sends a
     * {@link DatasetChangeEvent} to all registered listeners.
     */
    public void removeAllSeries() {

        // Unregister the collection as a change listener to each series in
        // the collection.
        for (XYSeries series : this.data) {
            series.removeChangeListener(this);
        }

        // Remove all the series from the collection and notify listeners.
        this.data.clear();
        this.xPoints.clear();
        fireDatasetChanged();
    }

    /**
     * Removes a series from the collection and sends a
     * {@link DatasetChangeEvent} to all registered listeners.
     *
     * @param series  the series (<code>null</code> not permitted).
     */
    public void removeSeries(XYSeries series) {

        // check arguments...
        if (series == null) {
            throw new IllegalArgumentException("Null 'series' argument.");
        }

        // remove the series...
        if (this.data.contains(series)) {
            series.removeChangeListener(this);
            this.data.remove(series);
            if (this.data.size() == 0) {
                this.xPoints.clear();
            }
            fireDatasetChanged();
        }

    }

    /**
     * Removes a series from the collection and sends a
     * {@link DatasetChangeEvent} to all registered listeners.
     *
     * @param series  the series (zero based index).
     */
    public void removeSeries(int series) {

        // check arguments...
        if ((series < 0) || (series > getSeriesCount())) {
            throw new IllegalArgumentException("Index outside valid range.");
        }

        // fetch the series, remove the change listener, then remove the series.
        XYSeries s = this.data.get(series);
        s.removeChangeListener(this);
        this.data.remove(series);
        if (this.data.size() == 0) {
            this.xPoints.clear();
        }
        else if (this.autoPrune) {
            prune();
        }
        fireDatasetChanged();

    }

    /**
     * Removes the items from all series for a given x value.
     *
     * @param x  the x-value.
     */
    public void removeAllValuesForX(Number x) {
        if (x == null) {
            throw new IllegalArgumentException("Null 'x' argument.");
        }
        boolean savedState = this.propagateEvents;
        this.propagateEvents = false;
        for (XYSeries series : this.data) {
            series.remove(x);
        }
        this.propagateEvents = savedState;
        this.xPoints.remove(x);
        fireDatasetChanged();
    }

    /**
     * Returns <code>true</code> if all the y-values for the specified x-value
     * are <code>null</code> and <code>false</code> otherwise.
     *
     * @param x  the x-value.
     *
     * @return A boolean.
     */
    protected boolean canPrune(Number x) {
        for (XYSeries series : this.data) {
            if (series.getY(series.indexOf(x)) != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Removes all x-values for which all the y-values are <code>null</code>.
     */
    public void prune() {
        Set<Number> hs = (HashSet<Number>) this.xPoints.clone();
        for (Number x : hs) {
            if (canPrune(x)) {
                removeAllValuesForX(x);
            }
        }
    }

    /**
     * This method receives notification when a series belonging to the dataset
     * changes.  It responds by updating the x-points for the entire dataset
     * and sending a {@link DatasetChangeEvent} to all registered listeners.
     *
     * @param event  information about the change.
     */
    @Override
	public void seriesChanged(SeriesChangeEvent event) {
        if (this.propagateEvents) {
            updateXPoints();
            fireDatasetChanged();
        }
    }

    /**
     * Tests this collection for equality with an arbitrary object.
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
        if (!(obj instanceof DefaultTableXYDataset)) {
            return false;
        }
        DefaultTableXYDataset that = (DefaultTableXYDataset) obj;
        if (this.autoPrune != that.autoPrune) {
            return false;
        }
        if (this.propagateEvents != that.propagateEvents) {
            return false;
        }
        if (!this.intervalDelegate.equals(that.intervalDelegate)) {
            return false;
        }
        if (!ObjectUtilities.equal(this.data, that.data)) {
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
        result = (this.data != null ? this.data.hashCode() : 0);
        result = 29 * result
                 + (this.xPoints != null ? this.xPoints.hashCode() : 0);
        result = 29 * result + (this.propagateEvents ? 1 : 0);
        result = 29 * result + (this.autoPrune ? 1 : 0);
        return result;
    }

    /**
     * Returns an independent copy of this dataset.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if there is some reason that cloning
     *     cannot be performed.
     */
    @Override
	public Object clone() throws CloneNotSupportedException {
        DefaultTableXYDataset clone = (DefaultTableXYDataset) super.clone();
        int seriesCount = this.data.size();
        clone.data = new java.util.ArrayList<XYSeries>(seriesCount);
        for (int i = 0; i < seriesCount; i++) {
            XYSeries series = this.data.get(i);
            clone.data.add((XYSeries)series.clone());
        }

        clone.intervalDelegate = new IntervalXYDelegate(clone);
        // need to configure the intervalDelegate to match the original
        clone.intervalDelegate.setFixedIntervalWidth(getIntervalWidth());
        clone.intervalDelegate.setAutoWidth(isAutoWidth());
        clone.intervalDelegate.setIntervalPositionFactor(
                getIntervalPositionFactor());
        clone.updateXPoints();
        return clone;
    }

    /**
     * Returns the minimum x-value in the dataset.
     *
     * @param includeInterval  a flag that determines whether or not the
     *                         x-interval is taken into account.
     *
     * @return The minimum value.
     */
    @Override
	public double getDomainLowerBound(boolean includeInterval) {
        return this.intervalDelegate.getDomainLowerBound(includeInterval);
    }

    /**
     * Returns the maximum x-value in the dataset.
     *
     * @param includeInterval  a flag that determines whether or not the
     *                         x-interval is taken into account.
     *
     * @return The maximum value.
     */
    @Override
	public double getDomainUpperBound(boolean includeInterval) {
        return this.intervalDelegate.getDomainUpperBound(includeInterval);
    }

    /**
     * Returns the range of the values in this dataset's domain.
     *
     * @param includeInterval  a flag that determines whether or not the
     *                         x-interval is taken into account.
     *
     * @return The range.
     */
    @Override
	public Range getDomainBounds(boolean includeInterval) {
        if (includeInterval) {
            return this.intervalDelegate.getDomainBounds(includeInterval);
        }
        else {
            return DatasetUtilities.iterateDomainBounds(this, includeInterval);
        }
    }

    /**
     * Returns the interval position factor.
     *
     * @return The interval position factor.
     */
    public double getIntervalPositionFactor() {
        return this.intervalDelegate.getIntervalPositionFactor();
    }

    /**
     * Sets the interval position factor. Must be between 0.0 and 1.0 inclusive.
     * If the factor is 0.5, the gap is in the middle of the x values. If it
     * is lesser than 0.5, the gap is farther to the left and if greater than
     * 0.5 it gets farther to the right.
     *
     * @param d the new interval position factor.
     */
    public void setIntervalPositionFactor(double d) {
        this.intervalDelegate.setIntervalPositionFactor(d);
        fireDatasetChanged();
    }

    /**
     * returns the full interval width.
     *
     * @return The interval width to use.
     */
    public double getIntervalWidth() {
        return this.intervalDelegate.getIntervalWidth();
    }

    /**
     * Sets the interval width to a fixed value, and sends a
     * {@link DatasetChangeEvent} to all registered listeners.
     *
     * @param d  the new interval width (must be > 0).
     */
    public void setIntervalWidth(double d) {
        this.intervalDelegate.setFixedIntervalWidth(d);
        fireDatasetChanged();
    }

    /**
     * Returns whether the interval width is automatically calculated or not.
     *
     * @return A flag that determines whether or not the interval width is
     *         automatically calculated.
     */
    public boolean isAutoWidth() {
        return this.intervalDelegate.isAutoWidth();
    }

    /**
     * Sets the flag that indicates whether the interval width is automatically
     * calculated or not.
     *
     * @param b  a boolean.
     */
    public void setAutoWidth(boolean b) {
        this.intervalDelegate.setAutoWidth(b);
        fireDatasetChanged();
    }

}
