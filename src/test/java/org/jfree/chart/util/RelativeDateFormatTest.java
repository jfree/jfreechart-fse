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
 * ----------------------------
 * RelativeDateFormatTests.java
 * ----------------------------
 * (C) Copyright 2006-2011, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 23-Nov-2006 : Version 1 (DG);
 * 15-Feb-2008 : Added tests for negative dates (DG);
 * 01-Sep-2008 : Added a test for hours and minutes with leading zeroes (DG);
 * 06-Oct-2011 : Fixed bug 3418287 (DG);
 *
 */

package org.jfree.chart.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;



/**
 * Tests for the {@link RelativeDateFormat} class.
 */
public class RelativeDateFormatTest  {





    private Locale savedLocale;

    /**
     * Set a known locale for the tests.
     */
    @Before
    public void setUp() throws Exception {
        this.savedLocale = Locale.getDefault();
        Locale.setDefault(Locale.UK);
    }

    /**
     * Restore the default locale after the tests complete.
     */
    @After
    public void tearDown() throws Exception {
        Locale.setDefault(this.savedLocale);
    }

    /**
     * Some checks for the formatting.
     */
    @Test
    public void testFormat() {
        RelativeDateFormat rdf = new RelativeDateFormat();
        String s = rdf.format(new Date(2 * 60L * 60L * 1000L + 122500L));
        assertEquals("2h2m2.500s", s);
    }

    /**
     * Test that we can configure the RelativeDateFormat to show
     * hh:mm:ss.
     */
    @Test
    public void test2033092() {
        RelativeDateFormat rdf = new RelativeDateFormat();
        rdf.setShowZeroDays(false);
        rdf.setShowZeroHours(false);
        rdf.setMinuteSuffix(":");
        rdf.setHourSuffix(":");
        rdf.setSecondSuffix("");
        DecimalFormat hoursFormatter = new DecimalFormat();
        hoursFormatter.setMaximumFractionDigits(0);
        hoursFormatter.setMaximumIntegerDigits(2);
        hoursFormatter.setMinimumIntegerDigits(2);
        rdf.setHourFormatter(hoursFormatter);
        DecimalFormat minsFormatter = new DecimalFormat();
        minsFormatter.setMaximumFractionDigits(0);
        minsFormatter.setMaximumIntegerDigits(2);
        minsFormatter.setMinimumIntegerDigits(2);
        rdf.setMinuteFormatter(minsFormatter);
        DecimalFormat secondsFormatter = new DecimalFormat();
        secondsFormatter.setMaximumFractionDigits(0);
        secondsFormatter.setMaximumIntegerDigits(2);
        secondsFormatter.setMinimumIntegerDigits(2);
        rdf.setSecondFormatter(secondsFormatter);
        String s = rdf.format(new Date(2 * 60L * 60L * 1000L + 122500L));
        assertEquals("02:02:02", s);
    }

    /**
     * Check that the equals() method can distinguish all fields.
     */
    @Test
    public void testEquals() {
        RelativeDateFormat df1 = new RelativeDateFormat();
        RelativeDateFormat df2 = new RelativeDateFormat();
        assertEquals(df1, df2);

        df1.setBaseMillis(123L);
        assertFalse(df1.equals(df2));
        df2.setBaseMillis(123L);
        assertEquals(df1, df2);

        df1.setDayFormatter(new DecimalFormat("0%"));
        assertFalse(df1.equals(df2));
        df2.setDayFormatter(new DecimalFormat("0%"));
        assertEquals(df1, df2);

        df1.setDaySuffix("D");
        assertFalse(df1.equals(df2));
        df2.setDaySuffix("D");
        assertEquals(df1, df2);

        df1.setHourFormatter(new DecimalFormat("0%"));
        assertFalse(df1.equals(df2));
        df2.setHourFormatter(new DecimalFormat("0%"));
        assertEquals(df1, df2);

        df1.setHourSuffix("H");
        assertFalse(df1.equals(df2));
        df2.setHourSuffix("H");
        assertEquals(df1, df2);

        df1.setMinuteFormatter(new DecimalFormat("0%"));
        assertFalse(df1.equals(df2));
        df2.setMinuteFormatter(new DecimalFormat("0%"));
        assertEquals(df1, df2);

        df1.setMinuteSuffix("M");
        assertFalse(df1.equals(df2));
        df2.setMinuteSuffix("M");
        assertEquals(df1, df2);

        df1.setSecondSuffix("S");
        assertFalse(df1.equals(df2));
        df2.setSecondSuffix("S");
        assertEquals(df1, df2);

        df1.setShowZeroDays(!df1.getShowZeroDays());
        assertFalse(df1.equals(df2));
        df2.setShowZeroDays(!df2.getShowZeroDays());
        assertEquals(df1, df2);

        df1.setSecondFormatter(new DecimalFormat("0.0"));
        assertFalse(df1.equals(df2));
        df2.setSecondFormatter(new DecimalFormat("0.0"));
        assertEquals(df1, df2);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        RelativeDateFormat df1 = new RelativeDateFormat(123L);
        RelativeDateFormat df2 = new RelativeDateFormat(123L);
        assertEquals(df1, df2);
        int h1 = df1.hashCode();
        int h2 = df2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        NumberFormat nf = new DecimalFormat("0");
        RelativeDateFormat df1 = new RelativeDateFormat();
        df1.setSecondFormatter(nf);
        RelativeDateFormat df2 = null;
        df2 = (RelativeDateFormat) df1.clone();
        assertNotSame(df1, df2);
        assertSame(df1.getClass(), df2.getClass());
        assertEquals(df1, df2);

        // is the clone independent
        nf.setMinimumFractionDigits(2);
        assertFalse(df1.equals(df2));
    }

    /**
     * Some tests for negative dates.
     */
    @Test
    public void testNegative() {
        NumberFormat nf = new DecimalFormat("0");
        RelativeDateFormat df1 = new RelativeDateFormat();
        df1.setSecondFormatter(nf);
        assertEquals("-0h0m1s", df1.format(new Date(-1000L)));
    }
}

