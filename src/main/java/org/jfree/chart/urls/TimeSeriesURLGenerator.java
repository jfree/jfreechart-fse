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
 * ---------------------------
 * TimeSeriesURLGenerator.java
 * ---------------------------
 * (C) Copyright 2002-2014, by Richard Atkinson and Contributors.
 *
 * Original Author:  Richard Atkinson;
 * Contributors:     David Gilbert (for Object Refinery Limited);
 *
 * Changes:
 * --------
 * 29-Aug-2002 : Initial version (RA);
 * 09-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 23-Mar-2003 : Implemented Serializable (DG);
 * 15-Jul-2004 : Switched getX() with getXValue() and getY() with
 *               getYValue() (DG);
 * 13-Jan-2005 : Modified for XHTML 1.0 compliance (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 06-Jul-2006 : Swap call to dataset's getX() --> getXValue() (DG);
 * 17-Apr-2007 : Added null argument checks to constructor, new accessor
 *               methods, added equals() override and used new URLUtilities
 *               class to encode series key and date (DG);
 *
 */

package org.jfree.chart.urls;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.util.ParamChecks;

import org.jfree.data.xy.XYDataset;

/**
 * A URL generator for time series charts.
 */
public class TimeSeriesURLGenerator implements XYURLGenerator, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -9122773175671182445L;

    /** A formatter for the date. */
    private DateFormat dateFormat = DateFormat.getInstance();

    /** Prefix to the URL */
    private String prefix = "index.html";

    /** Name to use to identify the series */
    private String seriesParamName = "series";

    /** Name to use to identify the item */
    private String itemParamName = "item";

    /**
     * Default constructor.
     */
    public TimeSeriesURLGenerator() {
        super();
    }

    /**
     * Construct TimeSeriesURLGenerator overriding defaults.
     *
     * @param dateFormat  a formatter for the date (<code>null</code> not
     *         permitted).
     * @param prefix  the prefix of the URL (<code>null</code> not permitted).
     * @param seriesParamName  the name of the series parameter in the URL
     *         (<code>null</code> not permitted).
     * @param itemParamName  the name of the item parameter in the URL
     *         (<code>null</code> not permitted).
     */
    public TimeSeriesURLGenerator(DateFormat dateFormat, String prefix,
            String seriesParamName, String itemParamName) {

        ParamChecks.nullNotPermitted(dateFormat, "dateFormat");
        ParamChecks.nullNotPermitted(prefix, "prefix");
        ParamChecks.nullNotPermitted(seriesParamName, "seriesParamName");
        ParamChecks.nullNotPermitted(itemParamName, "itemParamName");

        this.dateFormat = (DateFormat) dateFormat.clone();
        this.prefix = prefix;
        this.seriesParamName = seriesParamName;
        this.itemParamName = itemParamName;
    }

    /**
     * Returns a clone of the date format assigned to this URL generator.
     *
     * @return The date format (never <code>null</code>).
     *
     * @since 1.0.6
     */
    public DateFormat getDateFormat() {
        return (DateFormat) this.dateFormat.clone();
    }

    /**
     * Returns the prefix string.
     *
     * @return The prefix string (never <code>null</code>).
     *
     * @since 1.0.6
     */
    public String getPrefix() {
        return this.prefix;
    }

    /**
     * Returns the series parameter name.
     *
     * @return The series parameter name (never <code>null</code>).
     *
     * @since 1.0.6
     */
    public String getSeriesParamName() {
        return this.seriesParamName;
    }

    /**
     * Returns the item parameter name.
     *
     * @return The item parameter name (never <code>null</code>).
     *
     * @since 1.0.6
     */
    public String getItemParamName() {
        return this.itemParamName;
    }

    /**
     * Generates a URL for a particular item within a series.
     *
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param series  the series number (zero-based index).
     * @param item  the item number (zero-based index).
     *
     * @return The generated URL.
     */
    @Override
    public String generateURL(XYDataset dataset, int series, int item) {
        String result = this.prefix;
        try {
            boolean firstParameter = !result.contains("?");
            Comparable seriesKey = dataset.getSeriesKey(series);
            if (seriesKey != null) {
                result += firstParameter ? "?" : "&amp;";
                result += this.seriesParamName + "=" + URLEncoder.encode(
                        seriesKey.toString(), "UTF-8");
                firstParameter = false;
            }

            long x = (long) dataset.getXValue(series, item);
            String xValue = this.dateFormat.format(new Date(x));
            result += firstParameter ? "?" : "&amp;";
            result += this.itemParamName + "=" + URLEncoder.encode(xValue,
                    "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }

    /**
     * Tests this generator for equality with an arbitrary object.
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
        if (!(obj instanceof TimeSeriesURLGenerator)) {
            return false;
        }
        TimeSeriesURLGenerator that = (TimeSeriesURLGenerator) obj;
        if (!this.dateFormat.equals(that.dateFormat)) {
            return false;
        }
        if (!this.itemParamName.equals(that.itemParamName)) {
            return false;
        }
        if (!this.prefix.equals(that.prefix)) {
            return false;
        }
        if (!this.seriesParamName.equals(that.seriesParamName)) {
            return false;
        }
        return true;
    }

}
