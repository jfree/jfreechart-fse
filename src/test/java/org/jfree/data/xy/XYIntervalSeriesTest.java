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
 * --------------------------
 * XYIntervalSeriesTests.java
 * --------------------------
 * (C) Copyright 2006, 2007, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 20-Oct-2006 : Version 1, based on XYSeriesTests (DG);
 * 13-Feb-2007 : Added testValues() (DG);
 * 27-Nov-2007 : Added testClear() method (DG);
 *
 */

package org.jfree.data.xy;

import org.jfree.data.general.SeriesChangeEvent;
import org.jfree.data.general.SeriesChangeListener;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the {@link XYIntervalSeries} class.
 */
public class XYIntervalSeriesTest
        implements SeriesChangeListener {

    SeriesChangeEvent lastEvent;

    /**
     * Records the last event.
     *
     * @param event  the event.
     */
    @Override
    public void seriesChanged(SeriesChangeEvent event) {
        this.lastEvent = event;
    }





    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {

        XYIntervalSeries s1 = new XYIntervalSeries("s1");
        XYIntervalSeries s2 = new XYIntervalSeries("s1");
        assertEquals(s1, s2);

        // seriesKey
        s1 = new XYIntervalSeries("s2");
        assertFalse(s1.equals(s2));
        s2 = new XYIntervalSeries("s2");
        assertEquals(s1, s2);

        // autoSort
        s1 = new XYIntervalSeries("s2", false, true);
        assertFalse(s1.equals(s2));
        s2 = new XYIntervalSeries("s2", false, true);
        assertEquals(s1, s2);

        // allowDuplicateValues
        s1 = new XYIntervalSeries("s2", false, false);
        assertFalse(s1.equals(s2));
        s2 = new XYIntervalSeries("s2", false, false);
        assertEquals(s1, s2);

        // add a value
        s1.add(1.0, 0.5, 1.5, 2.0, 1.9, 2.1);
        assertFalse(s1.equals(s2));
        s2.add(1.0, 0.5, 1.5, 2.0, 1.9, 2.1);
        assertEquals(s2, s1);

        // add another value
        s1.add(2.0, 0.5, 1.5, 2.0, 1.9, 2.1);
        assertFalse(s1.equals(s2));
        s2.add(2.0, 0.5, 1.5, 2.0, 1.9, 2.1);
        assertEquals(s2, s1);

        // remove a value
        s1.remove(1.0);
        assertFalse(s1.equals(s2));
        s2.remove(1.0);
        assertEquals(s2, s1);

    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        XYIntervalSeries s1 = new XYIntervalSeries("s1");
        s1.add(1.0, 0.5, 1.5, 2.0, 1.9, 2.01);
        XYIntervalSeries s2 = (XYIntervalSeries) s1.clone();

        assertNotSame(s1, s2);
        assertSame(s1.getClass(), s2.getClass());
        assertEquals(s1, s2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {

        XYIntervalSeries s1 = new XYIntervalSeries("s1");
        s1.add(1.0, 0.5, 1.5, 2.0, 1.9, 2.1);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(buffer);
        out.writeObject(s1);
        out.close();

        ObjectInput in = new ObjectInputStream(
                new ByteArrayInputStream(buffer.toByteArray()));
        XYIntervalSeries s2 = (XYIntervalSeries) in.readObject();
        in.close();
        assertEquals(s1, s2);

    }

    /**
     * Simple test for the indexOf() method.
     */
    @Test
    public void testIndexOf() {
        XYIntervalSeries s1 = new XYIntervalSeries("Series 1");
        s1.add(1.0, 1.0, 1.0, 2.0, 1.9, 2.1);
        s1.add(2.0, 2.0, 2.0, 3.0, 2.9, 3.1);
        s1.add(3.0, 3.0, 3.0, 4.0, 3.9, 4.1);
        assertEquals(0, s1.indexOf(1.0));
    }

    /**
     * A check for the indexOf() method for an unsorted series.
     */
    @Test
    public void testIndexOf2() {
        XYIntervalSeries s1 = new XYIntervalSeries("Series 1", false, true);
        s1.add(1.0, 1.0, 1.0, 2.0, 1.9, 2.1);
        s1.add(3.0, 3.0, 3.0, 3.0, 2.9, 3.1);
        s1.add(2.0, 2.0, 2.0, 2.0, 1.9, 2.1);
        assertEquals(0, s1.indexOf(1.0));
        assertEquals(1, s1.indexOf(3.0));
        assertEquals(2, s1.indexOf(2.0));
    }

    /**
     * Simple test for the remove() method.
     */
    @Test
    public void testRemove() {
        XYIntervalSeries s1 = new XYIntervalSeries("Series 1");
        s1.add(1.0, 1.0, 1.0, 2.0, 1.9, 2.1);
        s1.add(2.0, 2.0, 2.0, 2.0, 1.9, 2.1);
        s1.add(3.0, 3.0, 3.0, 3.0, 2.9, 3.1);
        assertEquals(3, s1.getItemCount());

        s1.remove(2.0);
        assertEquals(3.0, s1.getX(1));

        s1.remove(1.0);
        assertEquals(3.0, s1.getX(0));
    }

    private static final double EPSILON = 0.0000000001;

    /**
     * When items are added with duplicate x-values, we expect them to remain
     * in the order they were added.
     */
    @Test
    public void testAdditionOfDuplicateXValues() {
        XYIntervalSeries s1 = new XYIntervalSeries("Series 1");
        s1.add(1.0, 1.0, 1.0, 1.0, 1.0, 1.0);
        s1.add(2.0, 2.0, 2.0, 2.0, 2.0, 2.0);
        s1.add(2.0, 3.0, 3.0, 3.0, 3.0, 3.0);
        s1.add(2.0, 4.0, 4.0, 4.0, 4.0, 4.0);
        s1.add(3.0, 5.0, 5.0, 5.0, 5.0, 5.0);
        assertEquals(1.0, s1.getYValue(0), EPSILON);
        assertEquals(2.0, s1.getYValue(1), EPSILON);
        assertEquals(3.0, s1.getYValue(2), EPSILON);
        assertEquals(4.0, s1.getYValue(3), EPSILON);
        assertEquals(5.0, s1.getYValue(4), EPSILON);
    }

    /**
     * Some checks for the add() method for an UNSORTED series.
     */
    @Test
    public void testAdd() {
        XYIntervalSeries series = new XYIntervalSeries("Series", false, true);
        series.add(5.0, 5.50, 5.50, 5.50, 5.50, 5.50);
        series.add(5.1, 5.51, 5.51, 5.51, 5.51, 5.51);
        series.add(6.0, 6.6, 6.6, 6.6, 6.6, 6.6);
        series.add(3.0, 3.3, 3.3, 3.3, 3.3, 3.3);
        series.add(4.0, 4.4, 4.4, 4.4, 4.4, 4.4);
        series.add(2.0, 2.2, 2.2, 2.2, 2.2, 2.2);
        series.add(1.0, 1.1, 1.1, 1.1, 1.1, 1.1);
        assertEquals(5.5, series.getYValue(0), EPSILON);
        assertEquals(5.51, series.getYValue(1), EPSILON);
        assertEquals(6.6, series.getYValue(2), EPSILON);
        assertEquals(3.3, series.getYValue(3), EPSILON);
        assertEquals(4.4, series.getYValue(4), EPSILON);
        assertEquals(2.2, series.getYValue(5), EPSILON);
        assertEquals(1.1, series.getYValue(6), EPSILON);
    }

    /**
     * A simple check that the maximumItemCount attribute is working.
     */
    @Test
    public void testSetMaximumItemCount() {
        XYIntervalSeries s1 = new XYIntervalSeries("S1");
        assertEquals(Integer.MAX_VALUE, s1.getMaximumItemCount());
        s1.setMaximumItemCount(2);
        assertEquals(2, s1.getMaximumItemCount());
        s1.add(1.0, 1.1, 1.1, 1.1, 1.1, 1.1);
        s1.add(2.0, 2.2, 2.2, 2.2, 2.2, 2.2);
        s1.add(3.0, 3.3, 3.3, 3.3, 3.3, 3.3);
        assertEquals(2.0, s1.getX(0).doubleValue(), EPSILON);
        assertEquals(3.0, s1.getX(1).doubleValue(), EPSILON);
    }

    /**
     * Check that the maximum item count can be applied retrospectively.
     */
    @Test
    public void testSetMaximumItemCount2() {
        XYIntervalSeries s1 = new XYIntervalSeries("S1");
        s1.add(1.0, 1.1, 1.1, 1.1, 1.1, 1.1);
        s1.add(2.0, 2.2, 2.2, 2.2, 2.2, 2.2);
        s1.add(3.0, 3.3, 3.3, 3.3, 2.2, 2.2);
        s1.setMaximumItemCount(2);
        assertEquals(2.0, s1.getX(0).doubleValue(), EPSILON);
        assertEquals(3.0, s1.getX(1).doubleValue(), EPSILON);
    }

    /**
     * Some checks for the new accessor methods added in 1.0.5.
     */
    @Test
    public void testValues() {
        XYIntervalSeries s1 = new XYIntervalSeries("S1");
        s1.add(2.0, 1.0, 3.0, 5.0, 4.0, 6.0);
        assertEquals(2.0, s1.getX(0).doubleValue(), EPSILON);
        assertEquals(1.0, s1.getXLowValue(0), EPSILON);
        assertEquals(3.0, s1.getXHighValue(0), EPSILON);
        assertEquals(5.0, s1.getYValue(0), EPSILON);
        assertEquals(4.0, s1.getYLowValue(0), EPSILON);
        assertEquals(6.0, s1.getYHighValue(0), EPSILON);
    }

    /**
     * Some checks for the clear() method.
     */
    @Test
    public void testClear() {
        XYIntervalSeries s1 = new XYIntervalSeries("S1");
        s1.addChangeListener(this);
        s1.clear();
        assertNull(this.lastEvent);
        assertTrue(s1.isEmpty());
        s1.add(1.0, 2.0, 3.0, 4.0, 5.0, 6.0);
        assertFalse(s1.isEmpty());
        s1.clear();
        assertNotNull(this.lastEvent);
        assertTrue(s1.isEmpty());
    }

}
