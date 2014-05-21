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
 * ------------------------
 * IntervalMarkerTests.java
 * ------------------------
 * (C) Copyright 2004-2012, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 14-Jun-2004 : Version 1 (DG);
 * 05-Sep-2006 : Added checks for MarkerChangeEvents (DG);
 * 17-Jun-2012 : Removed JCommon dependencies (DG);
 *
 */

package org.jfree.chart.plot;

import org.jfree.chart.event.MarkerChangeEvent;
import org.jfree.chart.event.MarkerChangeListener;
import org.jfree.chart.ui.GradientPaintTransformType;
import org.jfree.chart.ui.GradientPaintTransformer;
import org.jfree.chart.ui.StandardGradientPaintTransformer;
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
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

/**
 * Tests for the {@link IntervalMarker} class.
 */
public class IntervalMarkerTest

    implements MarkerChangeListener {

    MarkerChangeEvent lastEvent;

    /**
     * Records the last event.
     *
     * @param event  the last event.
     */
    @Override
    public void markerChanged(MarkerChangeEvent event) {
        this.lastEvent = event;
    }





    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {

        IntervalMarker m1 = new IntervalMarker(45.0, 50.0);
        IntervalMarker m2 = new IntervalMarker(45.0, 50.0);
        assertEquals(m1, m2);
        assertEquals(m2, m1);

        m1 = new IntervalMarker(44.0, 50.0);
        assertFalse(m1.equals(m2));
        m2 = new IntervalMarker(44.0, 50.0);
        assertEquals(m1, m2);

        m1 = new IntervalMarker(44.0, 55.0);
        assertFalse(m1.equals(m2));
        m2 = new IntervalMarker(44.0, 55.0);
        assertEquals(m1, m2);

        GradientPaintTransformer t = new StandardGradientPaintTransformer(
                GradientPaintTransformType.HORIZONTAL);
        m1.setGradientPaintTransformer(t);
        assertFalse(m1.equals(m2));
        m2.setGradientPaintTransformer(t);
        assertEquals(m1, m2);

    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        IntervalMarker m1 = new IntervalMarker(45.0, 50.0);
        IntervalMarker m2 = (IntervalMarker) m1.clone();
        assertNotSame(m1, m2);
        assertSame(m1.getClass(), m2.getClass());
        assertEquals(m1, m2);
    }

   /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {

        IntervalMarker m1 = new IntervalMarker(45.0, 50.0);


        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(buffer);
        out.writeObject(m1);
        out.close();

        ObjectInput in = new ObjectInputStream(
                new ByteArrayInputStream(buffer.toByteArray()));
        IntervalMarker m2 = (IntervalMarker) in.readObject();
        in.close();

        assertEquals(m1, m2);

    }

    private static final double EPSILON = 0.0000000001;

    /**
     * Some checks for the getStartValue() and setStartValue() methods.
     */
    @Test
    public void testGetSetStartValue() {
        IntervalMarker m = new IntervalMarker(1.0, 2.0);
        m.addChangeListener(this);
        this.lastEvent = null;
        assertEquals(1.0, m.getStartValue(), EPSILON);
        m.setStartValue(0.5);
        assertEquals(0.5, m.getStartValue(), EPSILON);
        assertEquals(m, this.lastEvent.getMarker());
    }

    /**
     * Some checks for the getEndValue() and setEndValue() methods.
     */
    @Test
    public void testGetSetEndValue() {
        IntervalMarker m = new IntervalMarker(1.0, 2.0);
        m.addChangeListener(this);
        this.lastEvent = null;
        assertEquals(2.0, m.getEndValue(), EPSILON);
        m.setEndValue(0.5);
        assertEquals(0.5, m.getEndValue(), EPSILON);
        assertEquals(m, this.lastEvent.getMarker());
    }
}
