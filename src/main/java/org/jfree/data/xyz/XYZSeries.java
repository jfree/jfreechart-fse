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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.io.Serializable;
import org.jfree.chart.util.ParamChecks;

/**
 * A data series containing a sequence of <code>(x, y, z)</code> data items.  
 * The series has a key to identify it, and can be added to an 
 * {@link XYZSeriesCollection} to create a dataset.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 */
@SuppressWarnings("serial")
public class XYZSeries implements Serializable {

    /** The series key (never <code>null</code>). */
    private final Comparable<?> key;

    /** The data items in the series. */
    private final List<XYZDataItem> items;

    /**
     * Creates a new series with the specified key.
     * 
     * @param key  the key (<code>null</code> not permitted). 
     */
    public XYZSeries(Comparable<?> key) {
        ParamChecks.nullNotPermitted(key, "key");
        this.key = key;
        this.items = new ArrayList<XYZDataItem>();
    }

    /**
     * Returns the series key.
     * 
     * @return The series key (never <code>null</code>). 
     */
    public Comparable<?> getKey() {
        return this.key;
    }
    
    /**
     * Returns the number of items in the series.
     * 
     * @return The number of items in the series. 
     */
    public int getItemCount() {
        return this.items.size();
    }
    
    /**
     * Returns the x-value for the specified item.
     * 
     * @param itemIndex  the item index.
     * 
     * @return The x-value. 
     */
    public double getXValue(int itemIndex) {
        return this.items.get(itemIndex).getX();
    }
  
    /**
     * Returns the y-value for the specified item.
     * 
     * @param itemIndex  the item index.
     * 
     * @return The y-value. 
     */
    public double getYValue(int itemIndex) {
        return this.items.get(itemIndex).getY();
    }

    /**
     * Returns the z-value for the specified item.
     * 
     * @param itemIndex  the item index.
     * 
     * @return The z-value. 
     */
    public double getZValue(int itemIndex) {
        return this.items.get(itemIndex).getZ();
    }

    /**
     * Adds a new data item to the series.
     * 
     * @param x  the x-value.
     * @param y  the y-value.
     * @param z  the z-value.
     */
    public void add(double x, double y, double z) {
        add(new XYZDataItem(x, y, z));   
    }

    /**
     * Adds a new data item to the series.
     * 
     * @param item  the data item (<code>null</code> not permitted).
     */
    public void add(XYZDataItem item) {
        ParamChecks.nullNotPermitted(item, "item");
        this.items.add(item);
    }

    /**
     * Tests this series for equality with an arbitrary object.
     * 
     * @param obj  the object to test (<code>null</code> permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof XYZSeries)) {
            return false;
        }
        XYZSeries that = (XYZSeries) obj;
        if (!this.key.equals(that.key)) {
            return false;
        }
        if (!this.items.equals(that.items)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.key);
        return hash;
    }

}
