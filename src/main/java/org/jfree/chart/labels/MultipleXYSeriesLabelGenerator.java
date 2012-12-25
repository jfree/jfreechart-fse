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
 * -----------------------------------
 * MultipleXYSeriesLabelGenerator.java
 * -----------------------------------
 * (C) Copyright 2004-2012, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 19-Nov-2004 : Version 1 (DG);
 * 18-Apr-2005 : Use StringBuffer (DG);
 * 20-Feb-2007 : Fixed for equals() and cloning() (DG);
 * 31-Mar-2008 : Added hashCode() method to appease FindBugs (DG);
 * 17-Jun-2012 : Removed JCommon dependencies (DG);
 *
 */

package org.jfree.chart.labels;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jfree.chart.HashUtilities;
import org.jfree.chart.util.PublicCloneable;
import org.jfree.data.xy.XYDataset;

/**
 * A series label generator for plots that use data from
 * an {@link org.jfree.data.xy.XYDataset}.
 */
public class MultipleXYSeriesLabelGenerator implements XYSeriesLabelGenerator,
        Cloneable, PublicCloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 138976236941898560L;

    /** The default item label format. */
    public static final String DEFAULT_LABEL_FORMAT = "{0}";

    /** The format pattern for the initial part of the label. */
    private String formatPattern;

    /** The format pattern for additional labels. */
    private String additionalFormatPattern;

    /** Storage for the additional series labels. */
    private Map<Integer, List<String>> seriesLabelLists;

    /**
     * Creates an item label generator using default number formatters.
     */
    public MultipleXYSeriesLabelGenerator() {
        this(DEFAULT_LABEL_FORMAT);
    }

    /**
     * Creates a new series label generator.
     *
     * @param format  the format pattern (<code>null</code> not permitted).
     */
    public MultipleXYSeriesLabelGenerator(String format) {
        if (format == null) {
            throw new IllegalArgumentException("Null 'format' argument.");
        }
        this.formatPattern = format;
        this.additionalFormatPattern = "\n{0}";
        this.seriesLabelLists = new HashMap<Integer, List<String>>();
    }

    /**
     * Adds an extra label for the specified series.
     *
     * @param series  the series index.
     * @param label  the label.
     */
    public void addSeriesLabel(int series, String label) {
        Integer key = series;
        List<String> labelList = this.seriesLabelLists.get(key);
        if (labelList == null) {
            labelList = new java.util.ArrayList<String>();
            this.seriesLabelLists.put(key, labelList);
        }
        labelList.add(label);
    }

    /**
     * Clears the extra labels for the specified series.
     *
     * @param series  the series index.
     */
    public void clearSeriesLabels(int series) {
        Integer key = series;
        this.seriesLabelLists.put(key, null);
    }

    /**
     * Generates a label for the specified series.  This label will be
     * used for the chart legend.
     *
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param series  the series.
     *
     * @return A series label.
     */
    @Override
    public String generateLabel(XYDataset dataset, int series) {
        if (dataset == null) {
            throw new IllegalArgumentException("Null 'dataset' argument.");
        }
        StringBuilder label = new StringBuilder();
        label.append(MessageFormat.format(this.formatPattern,
                createItemArray(dataset, series)));
        Integer key = series;
        List<String> extraLabels = this.seriesLabelLists.get(key);
        if (extraLabels != null) {
            for (String extraLabel : extraLabels) {
                String labelAddition = MessageFormat.format(
                        this.additionalFormatPattern, extraLabel);
                label.append(labelAddition);
            }
        }
        return label.toString();
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
    protected Object[] createItemArray(XYDataset dataset, int series) {
        Object[] result = new Object[1];
        result[0] = dataset.getSeriesKey(series).toString();
        return result;
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
        MultipleXYSeriesLabelGenerator clone
                = (MultipleXYSeriesLabelGenerator) super.clone();
        clone.seriesLabelLists = new HashMap<Integer, List<String>>();
        Set<Integer> keys = this.seriesLabelLists.keySet();
        for (Integer key : keys) {
            List<String> entry = this.seriesLabelLists.get(key);
            List<String> toAdd = entry;
            if (entry instanceof PublicCloneable) {
                PublicCloneable pc = (PublicCloneable) entry;
                toAdd = (List<String>) pc.clone();
            }
            clone.seriesLabelLists.put(key, toAdd);
        }
        return clone;
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
        if (!(obj instanceof MultipleXYSeriesLabelGenerator)) {
            return false;
        }
        MultipleXYSeriesLabelGenerator that
                = (MultipleXYSeriesLabelGenerator) obj;
        if (!this.formatPattern.equals(that.formatPattern)) {
            return false;
        }
        if (!this.additionalFormatPattern.equals(
                that.additionalFormatPattern)) {
            return false;
        }
        if (!this.seriesLabelLists.equals(that.seriesLabelLists)) {
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
        int result = 127;
        result = HashUtilities.hashCode(result, this.formatPattern);
        result = HashUtilities.hashCode(result, this.additionalFormatPattern);
        result = HashUtilities.hashCode(result, this.seriesLabelLists);
        return result;
    }

}
