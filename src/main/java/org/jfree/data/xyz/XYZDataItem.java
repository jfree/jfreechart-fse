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

/**
 * Represents a single <code>(x, y, z)</code> data item, which can be added to 
 * a {@link XYZSeries}.  Instances of this class are immutable.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 */
@SuppressWarnings("serial")
public class XYZDataItem implements Serializable {

    /** The x-value. */
    private final double x;

    /** The y-value. */
    private final double y;

    /** The z-value. */
    private final double z;

    /**
     * Creates a new (immutable) instance.
     * 
     * @param x  the x-value.
     * @param y  the y-value.
     * @param z  the z-value.
     */
    public XYZDataItem(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Returns the x-value.
     * 
     * @return The x-value. 
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y-value.
     * 
     * @return The y-value.
     */
    public double getY() {
        return y;
    }

    /**
     * Returns the z-value.
     * 
     * @return The z-value. 
     */
    public double getZ() {
        return z;
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
        if (!(obj instanceof XYZDataItem)) {
            return false;
        }
        XYZDataItem that = (XYZDataItem) obj;
        if (this.x != that.x) {
            return false;
        }
        if (this.y != that.y) {
            return false;
        }
        if (this.z != that.z) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.x) 
                ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.y) 
                ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.z) 
                ^ (Double.doubleToLongBits(this.z) >>> 32));
        return hash;
    }

}