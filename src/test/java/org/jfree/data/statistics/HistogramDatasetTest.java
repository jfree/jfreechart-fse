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
 * --------------------------
 * HistogramDatasetTests.java
 * --------------------------
 * (C) Copyright 2005-2014, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 10-Jan-2005 : Version 1 (DG);
 * 21-May-2007 : Added testClearObservations (DG);
 *
 */

package org.jfree.data.statistics;

import org.junit.Test;

import org.jfree.chart.TestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

/**
 * Tests for the {@link HistogramDataset} class.
 */
public class HistogramDatasetTest  {

    /**
     * Ensure that the equals() method can distinguish all fields.
     */
    @Test
    public void testEquals() {
        HistogramDataset d1 = new HistogramDataset("Dataset 1");
        HistogramDataset d2 = new HistogramDataset("Dataset 1");
        assertEquals(d1, d2);

        d1.addBin(new HistogramBin(1.0, 2.0));
        assertFalse(d1.equals(d2));
        d2.addBin(new HistogramBin(1.0, 2.0));
        assertEquals(d1, d2);
    }

    /**
     * Some checks for the clone() method.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        HistogramDataset d1 = new HistogramDataset("Dataset 1");
        HistogramDataset d2 = (HistogramDataset) d1.clone();
        assertNotSame(d1, d2);
        assertSame(d1.getClass(), d2.getClass());
        assertEquals(d1, d2);

        // check that clone is independent of the original
        d2.addBin(new HistogramBin(2.0, 3.0));
        d2.addObservation(2.3);
        assertFalse(d1.equals(d2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        HistogramDataset d1 = new HistogramDataset("D1");
        HistogramDataset d2 = (HistogramDataset) TestUtils.serialised(d1);
        assertEquals(d1, d2);
    }

    private static final double EPSILON = 0.0000000001;

    /**
     * Some checks for the clearObservations() method.
     */
    @Test
    public void testClearObservations() {
        HistogramDataset d1 = new HistogramDataset("D1");
        d1.clearObservations();
        assertEquals(0, d1.getItemCount(0));
        d1.addBin(new HistogramBin(0.0, 1.0));
        d1.addObservation(0.5);
        assertEquals(1.0, d1.getYValue(0, 0), EPSILON);
    }

    /**
     * Some checks for the removeAllBins() method.
     */
    @Test
    public void testRemoveAllBins() {
        HistogramDataset d1 = new HistogramDataset("D1");
        d1.addBin(new HistogramBin(0.0, 1.0));
        d1.addObservation(0.5);
        d1.addBin(new HistogramBin(2.0, 3.0));
        assertEquals(2, d1.getItemCount(0));
        d1.removeAllBins();
        assertEquals(0, d1.getItemCount(0));
    }

}
