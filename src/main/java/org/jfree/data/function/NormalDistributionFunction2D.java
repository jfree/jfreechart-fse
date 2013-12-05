/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2011, by Object Refinery Limited and Contributors.
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
 * ---------------------------------
 * NormalDistributionFunction2D.java
 * ---------------------------------
 * (C)opyright 2004-2009, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 25-May-2004 : Version 1 (DG);
 * 21-Nov-2005 : Added getters for the mean and standard deviation (DG);
 * 12-Feb-2009 : Precompute some constants from the function - see bug
 *               2572016 (DG);
 * 28-May-2009 : Implemented equals() and hashCode(), and added serialization
 *               support (DG);
 *
 */

package org.jfree.data.function;

import java.io.Serializable;

import org.jfree.chart.HashUtilities;

/**
 * A normal distribution function.  See
 * http://en.wikipedia.org/wiki/Normal_distribution.
 */
public class NormalDistributionFunction2D implements Function2D, Serializable {

    /** The mean. */
    private double mean;

    /** The standard deviation. */
    private double std;

    /** Precomputed factor for the function value. */
    private double factor;

    /** Precomputed denominator for the function value. */
    private double denominator;

    /**
     * Constructs a new normal distribution function.
     *
     * @param mean  the mean.
     * @param std  the standard deviation (> 0).
     */
    public NormalDistributionFunction2D(double mean, double std) {
        if (std <= 0) {
            throw new IllegalArgumentException("Requires 'std' > 0.");
        }
        this.mean = mean;
        this.std = std;
        // calculate constant values
        this.factor = 1 / (std * Math.sqrt(2.0 * Math.PI));
        this.denominator = 2 * std * std;
    }

    /**
     * Returns the mean for the function.
     *
     * @return The mean.
     */
    public double getMean() {
        return this.mean;
    }

    /**
     * Returns the standard deviation for the function.
     *
     * @return The standard deviation.
     */
    public double getStandardDeviation() {
        return this.std;
    }

    /**
     * Returns the function value.
     *
     * @param x  the x-value.
     *
     * @return The value.
     */
    @Override
    public double getValue(double x) {
        double z = x - this.mean;
        return this.factor * Math.exp(-z * z / this.denominator);
    }

    /**
     * Tests this function for equality with an arbitrary object.
     *
     * @param obj  the object (<code>null</code> permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NormalDistributionFunction2D)) {
            return false;
        }
        NormalDistributionFunction2D that = (NormalDistributionFunction2D) obj;
        if (this.mean != that.mean) {
            return false;
        }
        if (this.std != that.std) {
            return false;
        }
        return true;
    }

    /**
     * Returns a hash code for this instance.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int result = 29;
        result = HashUtilities.hashCode(result, this.mean);
        result = HashUtilities.hashCode(result, this.std);
        return result;
    }

}
