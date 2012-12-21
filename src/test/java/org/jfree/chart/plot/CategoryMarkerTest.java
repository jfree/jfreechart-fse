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
 * CategoryMarkerTests.java
 * ------------------------
 * (C) Copyright 2005-2007, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 22-Mar-2005 : Version 1 (DG);
 *
 */

package org.jfree.chart.plot;

import org.jfree.chart.event.MarkerChangeEvent;
import org.jfree.chart.event.MarkerChangeListener;
import org.junit.Test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
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
import static org.junit.Assert.fail;

/**
 * Some tests for the {@link CategoryMarker} class.
 */
public class CategoryMarkerTest
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
        CategoryMarker m1 = new CategoryMarker("A");
        CategoryMarker m2 = new CategoryMarker("A");
        assertEquals(m1, m2);
        assertEquals(m2, m1);

        //key
        m1 = new CategoryMarker("B");
        assertFalse(m1.equals(m2));
        m2 = new CategoryMarker("B");
        assertEquals(m1, m2);

        //paint
        m1 = new CategoryMarker("A", new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.yellow), new BasicStroke(1.1f));
        assertFalse(m1.equals(m2));
        m2 = new CategoryMarker("A", new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.yellow), new BasicStroke(1.1f));
        assertEquals(m1, m2);

        //stroke
        m1 = new CategoryMarker("A", new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.yellow), new BasicStroke(2.2f));
        assertFalse(m1.equals(m2));
        m2 = new CategoryMarker("A", new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.yellow), new BasicStroke(2.2f));
        assertEquals(m1, m2);

        //outlinePaint
        m1 = new CategoryMarker("A", new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.yellow), new BasicStroke(2.2f), Color.RED,
                new BasicStroke(1.0f), 1.0f);
        assertFalse(m1.equals(m2));
        m2 = new CategoryMarker("A", new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.yellow), new BasicStroke(2.2f), Color.RED,
                new BasicStroke(1.0f), 1.0f);
        assertEquals(m1, m2);

        //outlineStroke
        m1 = new CategoryMarker("A", new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.yellow), new BasicStroke(2.2f), Color.RED,
                new BasicStroke(3.3f), 1.0f);
        assertFalse(m1.equals(m2));
        m2 = new CategoryMarker("A", new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.yellow), new BasicStroke(2.2f), Color.RED,
                new BasicStroke(3.3f), 1.0f);
        assertEquals(m1, m2);

        //alpha
        m1 = new CategoryMarker("A", new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.yellow), new BasicStroke(2.2f), Color.RED,
                new BasicStroke(1.0f), 0.5f);
        assertFalse(m1.equals(m2));
        m2 = new CategoryMarker("A", new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.yellow), new BasicStroke(2.2f), Color.RED,
                new BasicStroke(1.0f), 0.5f);
        assertEquals(m1, m2);

    }

    /**
     * Check cloning.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        CategoryMarker m1 = new CategoryMarker("A", new GradientPaint(1.0f,
                2.0f, Color.WHITE, 3.0f, 4.0f, Color.yellow),
                new BasicStroke(1.1f));
        CategoryMarker m2 = (CategoryMarker) m1.clone();
        assertNotSame(m1, m2);
        assertSame(m1.getClass(), m2.getClass());
        assertEquals(m1, m2);
    }

   /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {

        CategoryMarker m1 = new CategoryMarker("A", new GradientPaint(1.0f,
                2.0f, Color.WHITE, 3.0f, 4.0f, Color.yellow),
                new BasicStroke(1.1f));


        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(buffer);
        out.writeObject(m1);
        out.close();

        ObjectInput in = new ObjectInputStream(
                new ByteArrayInputStream(buffer.toByteArray()));
        CategoryMarker m2 = (CategoryMarker) in.readObject();
        in.close();

        assertEquals(m1, m2);

    }

    /**
     * Some checks for the getKey() and setKey() methods.
     */
    @Test
    public void testGetSetKey() {
        CategoryMarker m = new CategoryMarker("X");
        m.addChangeListener(this);
        this.lastEvent = null;
        assertEquals("X", m.getKey());
        m.setKey("Y");
        assertEquals("Y", m.getKey());
        assertEquals(m, this.lastEvent.getMarker());

        // check null argument...
        try {
            m.setKey(null);
            fail("Expected an IllegalArgumentException for null.");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Null 'key' argument.", e.getMessage());
        }
    }

    /**
     * Some checks for the getDrawAsLine() and setDrawAsLine() methods.
     */
    @Test
    public void testGetSetDrawAsLine() {
        CategoryMarker m = new CategoryMarker("X");
        m.addChangeListener(this);
        this.lastEvent = null;
        assertEquals(false, m.getDrawAsLine());
        m.setDrawAsLine(true);
        assertEquals(true, m.getDrawAsLine());
        assertEquals(m, this.lastEvent.getMarker());
    }
}
