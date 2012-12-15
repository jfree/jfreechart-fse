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
 * ----------------------
 * RendererUtilities.java
 * ----------------------
 * (C) Copyright 2007-2012, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 19-Apr-2007 : Version 1 (DG);
 * 27-Mar-2009 : Fixed results for unsorted datasets (DG);
 * 19-May-2009 : Fixed FindBugs warnings, patch by Michal Wozniak (DG);
 * 23-Aug-2012 : Fixed rendering anomaly bug 3561093 (DG);
 *
 */

package org.jfree.chart.renderer;

import org.jfree.data.DomainOrder;
import org.jfree.data.xy.XYDataset;

/**
 * Utility methods related to the rendering process.
 *
 * @since 1.0.6
 */
public class RendererUtilities {

    /**
     * Finds the lower index of the range of live items in the specified data
     * series.
     *
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param series  the series index.
     * @param xLow  the lowest x-value in the live range.
     * @param xHigh  the highest x-value in the live range.
     *
     * @return The index of the required item.
     *
     * @since 1.0.6
     *
     * @see #findLiveItemsUpperBound(XYDataset, int, double, double)
     */
    public static int findLiveItemsLowerBound(XYDataset dataset, int series,
            double xLow, double xHigh) {
        if (dataset == null) {
            throw new IllegalArgumentException("Null 'dataset' argument.");
        }
        if (xLow >= xHigh) {
            throw new IllegalArgumentException("Requires xLow < xHigh.");
        }
        int itemCount = dataset.getItemCount(series);
        if (itemCount <= 1) {
            return 0;
        }
        int low;
        int high;
        double lowValue;
        double highValue;
        switch (dataset.getDomainOrder()) { //never null

            case ASCENDING:
                // for data in ascending order by x-value, we are (broadly) looking
                // for the index of the highest x-value that is less than xLow
                low = 0;
                high = itemCount - 1;
                lowValue = dataset.getXValue(series, low);
                if (lowValue >= xLow) {
                    // special case where the lowest x-value is >= xLow
                    return low;
                }
                highValue = dataset.getXValue(series, high);
                if (highValue < xLow) {
                    // special case where the highest x-value is < xLow
                    return high;
                }
                while (high - low > 1) {
                    int mid = (low + high) / 2;
                    double midV = dataset.getXValue(series, mid);
                    if (midV >= xLow) {
                        high = mid;
                    }
                    else {
                        low = mid;
                    }
                }
                return high;

            case DESCENDING:
                // when the x-values are sorted in descending order, the lower
                // bound is found by calculating relative to the xHigh value
                low = 0;
                high = itemCount - 1;
                lowValue = dataset.getXValue(series, low);
                if (lowValue <= xHigh) {
                    return low;
                }
                highValue = dataset.getXValue(series, high);
                if (highValue > xHigh) {
                    return high;
                }
                while (high - low > 1) {
                    int mid = (low + high) / 2;
                    double midV = dataset.getXValue(series, mid);
                    if (midV > xHigh) {
                        low = mid;
                    }
                    else {
                        high = mid;
                    }
                }
                return high;

            default:
                // we don't know anything about the ordering of the x-values,
                // but we can still skip any initial values that fall outside the
                // range...
                int index = 0;
                // skip any items that don't need including...
                double x = dataset.getXValue(series, index);
                while (index < itemCount && x < xLow) {
                    index++;
                    if (index < itemCount) {
                        x = dataset.getXValue(series, index);
                    }
                }
                return Math.min(Math.max(0, index), itemCount - 1);

        }
    }

    /**
     * Finds the upper index of the range of live items in the specified data
     * series.
     *
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param series  the series index.
     * @param xLow  the lowest x-value in the live range.
     * @param xHigh  the highest x-value in the live range.
     *
     * @return The index of the required item.
     *
     * @since 1.0.6
     *
     * @see #findLiveItemsLowerBound(XYDataset, int, double, double)
     */
    public static int findLiveItemsUpperBound(XYDataset dataset, int series,
            double xLow, double xHigh) {
        if (dataset == null) {
            throw new IllegalArgumentException("Null 'dataset' argument.");
        }
        if (xLow >= xHigh) {
            throw new IllegalArgumentException("Requires xLow < xHigh.");
        }
        int itemCount = dataset.getItemCount(series);
        if (itemCount <= 1) {
            return 0;
        }
        int high;
        int mid;
        int low;
        double lowValue;
        double highValue;
        switch(dataset.getDomainOrder()) { //never null

            case ASCENDING:
                low = 0;
                high = itemCount - 1;
                lowValue = dataset.getXValue(series, low);
                if (lowValue > xHigh) {
                    return low;
                }
                highValue = dataset.getXValue(series, high);
                if (highValue <= xHigh) {
                    return high;
                }
                mid = (low + high) / 2;
                while (high - low > 1) {
                    double midV = dataset.getXValue(series, mid);
                    if (midV <= xHigh) {
                        low = mid;
                    }
                    else {
                        high = mid;
                    }
                    mid = (low + high) / 2;
                }
                return mid;

            case DESCENDING:
                // when the x-values are descending, the upper bound is found by
                // comparing against xLow
                low = 0;
                high = itemCount - 1;
                mid = (low + high) / 2;
                lowValue = dataset.getXValue(series, low);
                if (lowValue < xLow) {
                    return low;
                }
                highValue = dataset.getXValue(series, high);
                if (highValue >= xLow) {
                    return high;
                }
                while (high - low > 1) {
                    double midV = dataset.getXValue(series, mid);
                    if (midV >= xLow) {
                        low = mid;
                    }
                    else {
                        high = mid;
                    }
                    mid = (low + high) / 2;
                }
                return mid;

            default:
                // we don't know anything about the ordering of the x-values,
                // but we can still skip any trailing values that fall outside the
                // range...
                int index = itemCount - 1;
                // skip any items that don't need including...
                double x = dataset.getXValue(series, index);
                while (index >= 0 && x > xHigh) {
                    index--;
                    if (index >= 0) {
                        x = dataset.getXValue(series, index);
                    }
                }
                return Math.max(index, 0);
        }
    }

    /**
     * Finds a range of item indices that is guaranteed to contain all the
     * x-values from x0 to x1 (inclusive).
     *
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param series  the series index.
     * @param xLow  the lower bound of the x-value range.
     * @param xHigh  the upper bound of the x-value range.
     *
     * @return The indices of the boundary items.
     */
    public static int[] findLiveItems(XYDataset dataset, int series,
            double xLow, double xHigh) {
        // here we could probably be a little faster by searching for both
        // indices simultaneously, but I'll look at that later if it seems
        // like it matters...
        int i0 = findLiveItemsLowerBound(dataset, series, xLow, xHigh);
        int i1 = findLiveItemsUpperBound(dataset, series, xLow, xHigh);
        if (i0 > i1) {
            i0 = i1;
        }
        return new int[] {i0, i1};
    }

}
