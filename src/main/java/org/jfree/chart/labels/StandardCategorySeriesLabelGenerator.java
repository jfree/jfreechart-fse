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
 * -----------------------------------------
 * StandardCategorySeriesLabelGenerator.java
 * -----------------------------------------
 * (C) Copyright 2005-2012, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 20-Apr-2005 : Version 1 (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 03-May-2006 : Fixed equals() method (bug 1481102) (DG);
 * 31-Mar-2008 : Added hashCode() method to appease FindBugs (DG);
 * 17-Jun-2012 : Removed JCommon dependencies (DG);
 *
 */

package org.jfree.chart.labels;

import org.jfree.chart.HashUtilities;
import org.jfree.chart.util.PublicCloneable;
import org.jfree.data.category.CategoryDataset;

import java.io.Serializable;
import java.text.MessageFormat;

/**
 * A standard series label generator for plots that use data from
 * a {@link org.jfree.data.category.CategoryDataset}.
 */
public class StandardCategorySeriesLabelGenerator
        <RowKey extends Comparable, ColumnKey extends Comparable>
        implements CategorySeriesLabelGenerator<RowKey, ColumnKey>, Cloneable, PublicCloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 4630760091523940820L;

    /** The default item label format. */
    public static final String DEFAULT_LABEL_FORMAT = "{0}";

    /** The format pattern. */
    private String formatPattern;

    /**
     * Creates a default series label generator (uses
     * {@link #DEFAULT_LABEL_FORMAT}).
     */
    public StandardCategorySeriesLabelGenerator() {
        this(DEFAULT_LABEL_FORMAT);
    }

    /**
     * Creates a new series label generator.
     *
     * @param format  the format pattern (<code>null</code> not permitted).
     */
    public StandardCategorySeriesLabelGenerator(String format) {
        if (format == null) {
            throw new IllegalArgumentException("Null 'format' argument.");
        }
        this.formatPattern = format;
    }

    /**
     * Generates a label for the specified series.
     *
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param series  the series.
     *
     * @return A series label.
     */
    @Override
    public String generateLabel(CategoryDataset<RowKey, ColumnKey> dataset, int series) {
        if (dataset == null) {
            throw new IllegalArgumentException("Null 'dataset' argument.");
        }
        return MessageFormat.format(this.formatPattern,
                createItemArray(dataset, series));
    }

    /**
     * Creates the array of items that can be passed to the
     * {@link MessageFormat} class for creating labels.
     *
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param series  the series (zero-based index).
     *
     * @return The items (never <code>null</code>).
     */
    protected Object[] createItemArray(CategoryDataset<RowKey, ColumnKey> dataset, int series) {
        String label = dataset.getRowKey(series).toString();
        return new Object[]{label};
    }

    /**
     * Returns an independent copy of the generator.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if cloning is not supported.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Tests this object for equality with an arbitrary object.
     *
     * @param obj  the other object (<code>null</code> permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StandardCategorySeriesLabelGenerator)) {
            return false;
        }
        StandardCategorySeriesLabelGenerator that
                = (StandardCategorySeriesLabelGenerator) obj;
        return this.formatPattern.equals(that.formatPattern);
    }

    /**
     * Returns a hash code for this instance.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int result = 127;
        result = HashUtilities.hashCode(result, this.formatPattern);
        return result;
    }

}
