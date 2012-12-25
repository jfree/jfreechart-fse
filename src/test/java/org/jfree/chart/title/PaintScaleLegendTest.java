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
 * PaintScaleLegendTests.java
 * --------------------------
 * (C) Copyright 2007, 2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 22-Jan-2007 : Version 1 (DG);
 * 18-Jun-2008 : Extended testEquals() for new field (DG);
 *
 */

package org.jfree.chart.title;

import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.renderer.GrayPaintScale;
import org.jfree.chart.renderer.LookupPaintScale;
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

/**
 * Tests for the {@link PaintScaleLegend} class.
 */
public class PaintScaleLegendTest  {





    /**
     * Test that the equals() method distinguishes all fields.
     */
    @Test
    public void testEquals() {

        // default instances
        PaintScaleLegend l1 = new PaintScaleLegend(new GrayPaintScale(),
                new NumberAxis("X"));
        PaintScaleLegend l2 = new PaintScaleLegend(new GrayPaintScale(),
                new NumberAxis("X"));
        assertEquals(l1, l2);
        assertEquals(l2, l1);

        // paintScale
        l1.setScale(new LookupPaintScale());
        assertFalse(l1.equals(l2));
        l2.setScale(new LookupPaintScale());
        assertEquals(l1, l2);

        // axis
        l1.setAxis(new NumberAxis("Axis 2"));
        assertFalse(l1.equals(l2));
        l2.setAxis(new NumberAxis("Axis 2"));
        assertEquals(l1, l2);

        // axisLocation
        l1.setAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
        assertFalse(l1.equals(l2));
        l2.setAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
        assertEquals(l1, l2);

        // axisOffset
        l1.setAxisOffset(99.0);
        assertFalse(l1.equals(l2));
        l2.setAxisOffset(99.0);
        assertEquals(l1, l2);

        // stripWidth
        l1.setStripWidth(99.0);
        assertFalse(l1.equals(l2));
        l2.setStripWidth(99.0);
        assertEquals(l1, l2);

        // stripOutlineVisible
        l1.setStripOutlineVisible(!l1.isStripOutlineVisible());
        assertFalse(l1.equals(l2));
        l2.setStripOutlineVisible(l1.isStripOutlineVisible());
        assertEquals(l1, l2);

        // stripOutlinePaint
        l1.setStripOutlinePaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        assertFalse(l1.equals(l2));
        l2.setStripOutlinePaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        assertEquals(l1, l2);

        // stripOutlineStroke
        l1.setStripOutlineStroke(new BasicStroke(1.1f));
        assertFalse(l1.equals(l2));
        l2.setStripOutlineStroke(new BasicStroke(1.1f));
        assertEquals(l1, l2);

        // backgroundPaint
        l1.setBackgroundPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        assertFalse(l1.equals(l2));
        l2.setBackgroundPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        assertEquals(l1, l2);

        l1.setSubdivisionCount(99);
        assertFalse(l1.equals(l2));
        l2.setSubdivisionCount(99);
        assertEquals(l1, l2);

    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashcode() {
        PaintScaleLegend l1 = new PaintScaleLegend(new GrayPaintScale(),
                new NumberAxis("X"));
        PaintScaleLegend l2 = new PaintScaleLegend(new GrayPaintScale(),
                new NumberAxis("X"));
        assertEquals(l1, l2);
        int h1 = l1.hashCode();
        int h2 = l2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        PaintScaleLegend l1 = new PaintScaleLegend(new GrayPaintScale(),
                new NumberAxis("X"));
        PaintScaleLegend l2 = (PaintScaleLegend) l1.clone();
        assertNotSame(l1, l2);
        assertSame(l1.getClass(), l2.getClass());
        assertEquals(l1, l2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        PaintScaleLegend l1 = new PaintScaleLegend(new GrayPaintScale(),
                new NumberAxis("X"));

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(l1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
        PaintScaleLegend l2 = (PaintScaleLegend) in.readObject();
            in.close();

        assertEquals(l1, l2);
    }

}
