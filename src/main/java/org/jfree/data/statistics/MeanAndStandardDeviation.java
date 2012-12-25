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
 * -----------------------------
 * MeanAndStandardDeviation.java
 * -----------------------------
 * (C) Copyright 2003-2012, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 05-Feb-2002 : Version 1 (DG);
 * 05-Feb-2005 : Added equals() method and implemented Serializable (DG);
 * 02-Oct-2007 : Added getMeanValue() and getStandardDeviationValue() methods
 *               for convenience, and toString() method for debugging (DG);
 * 17-Jun-2012 : Removed JCommon dependencies (DG);
 *
 */

package org.jfree.data.statistics;

import org.jfree.chart.util.ObjectUtilities;

import java.io.Serializable;

/**
 * A simple data structure that holds a mean value and a standard deviation
 * value.  This is used in the
 * {@link org.jfree.data.statistics.DefaultStatisticalCategoryDataset} class.
 */
public class MeanAndStandardDeviation implements Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 7414153420015721515L;

    /** The mean. */
    private Number mean;

    /** The negative deviation. */
    private Number deviationNegative;

    /** The positive deviation. */
    private Number deviationPositive;

    /**
     * Creates a new mean and standard deviation record.
     *
     * @param mean      the mean.
     * @param deviation the standard deviation.
     */
    public MeanAndStandardDeviation(double mean, double deviation) {
        this(new Double(mean), new Double(deviation));
    }

    /**
     * Creates a new mean and standard deviation record.
     *
     * @param mean      the mean (<code>null</code> permitted).
     * @param deviation the standard deviation (<code>null</code>
     *                          permitted.
     */
    public MeanAndStandardDeviation(Number mean, Number deviation) {
        this.mean = mean;
        if (deviation != null) {
            this.deviationNegative = deviation.doubleValue() / 2;
            this.deviationPositive = deviation.doubleValue() / 2;
        }
    }

    /**
     * Creates a new mean and standard deviation record.
     *
     * @param mean                      the mean (<code>null</code> permitted).
     * @param deviationNegative the negative standard deviation (<code>null</code> permitted.
     * @param deviationPositive the positive standard deviation (<code>null</code> permitted.
     */
    public MeanAndStandardDeviation(Number mean, Number deviationNegative, Number deviationPositive) {
        this.mean = mean;
        this.deviationNegative = deviationNegative;
        this.deviationPositive = deviationPositive;
    }

    /**
     * Returns the mean.
     *
     * @return The mean.
     */
    public Number getMean() {
        return this.mean;
    }

    /**
     * Returns the mean as a double primitive.  If the underlying mean is
     * <code>null</code>, this method will return <code>Double.NaN</code>.
     *
     * @return The mean.
     *
     * @see #getMean()
     * @since 1.0.7
     */
    public double getMeanValue() {
        double result = Double.NaN;
        if (this.mean != null) {
            result = this.mean.doubleValue();
        }
        return result;
    }


    /**
     * Returns the standard deviation.
     *
     * @return The standard deviation.
     */
    public Number getStandardDeviation() {

        double negative = getStandardDeviationNegativeValue();
        double positive = getStandardDeviationPositiveValue();

        return negative + positive;
    }

    /**
     * Returns the standard deviation as a double primitive.  If the underlying
     * standard deviation is <code>null</code>, this method will return
     * <code>Double.NaN</code>.
     *
     * @return The standard deviation.
     *
     * @since 1.0.7
     */
    public double getStandardDeviationValue() {
        return getStandardDeviation().doubleValue();
    }

    /**
     * Tests this instance for equality with an arbitrary object.
     *
     * @param obj the object (<code>null</code> permitted).
     *
     * @return A boolean.
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof MeanAndStandardDeviation) {
            MeanAndStandardDeviation that = (MeanAndStandardDeviation) obj;
            if (!ObjectUtilities.equal(this.mean, that.mean)) {
                return false;
            }
            boolean negativeDeviationEquals = ObjectUtilities.equal(this.deviationNegative, that.deviationNegative);
            boolean positiveDeviationEquals = ObjectUtilities.equal(this.deviationPositive, that.deviationPositive);
            return !(!negativeDeviationEquals || !positiveDeviationEquals);
        }
        return false;
    }

    /**
     * Gets the negative deviation.
     *
     * @return the negative deviation.
     */
    public Number getDeviationNegative() {
        return deviationNegative;
    }

    /**
     * Gets the negative deviation as a primitive.
     * If it's null, the resulting value is NaN.
     *
     * @return the negative deviation.
     */
    public double getStandardDeviationNegativeValue() {
        if (deviationNegative == null) {
            return Double.NaN;
        }
        return deviationNegative.doubleValue();
    }


    /**
     * Gets the positive deviation.
     *
     * @return the positive deviation.
     */
    public double getStandardDeviationPositiveValue() {
        if (deviationPositive == null) {
            return Double.NaN;
        }
        return deviationPositive.doubleValue();
    }

    /**
     * Gets the positive deviation.
     *
     * @return the positive deviation.
     */
    public Number getDeviationPositive() {
        return deviationPositive;
    }

    /**
     * Returns a string representing this instance.
     *
     * @return A string.
     *
     * @since 1.0.7
     */
    public String toString() {
        return "[" + this.mean + ", " + this.deviationNegative
                + ", " + this.deviationPositive + "]";
    }

}
