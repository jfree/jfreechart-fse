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
 * XYPolygonAnnotationTests.java
 * -----------------------------
 * (C) Copyright 2006-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 10-Jul-2006 : Version 1 (DG);
 * 23-Apr-2008 : Added testPublicCloneable() (DG);
 *
 */

package org.jfree.chart.annotations;

import org.jfree.chart.util.PublicCloneable;
import org.junit.Test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Stroke;
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
import static org.junit.Assert.assertTrue;

/**
 * Tests for the {@link XYPolygonAnnotation} class.
 */
public class XYPolygonAnnotationTest  {





    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        Stroke stroke1 = new BasicStroke(2.0f);
        Stroke stroke2 = new BasicStroke(2.5f);
        XYPolygonAnnotation a1 = new XYPolygonAnnotation(new double[] {1.0,
                2.0, 3.0, 4.0, 5.0, 6.0}, stroke1, Color.RED, Color.BLUE);
        XYPolygonAnnotation a2 = new XYPolygonAnnotation(new double[] {1.0,
                2.0, 3.0, 4.0, 5.0, 6.0}, stroke1, Color.RED, Color.BLUE);
        assertEquals(a1, a2);
        assertEquals(a2, a1);

        a1 = new XYPolygonAnnotation(new double[] {99.0, 2.0, 3.0, 4.0, 5.0,
                6.0}, stroke1, Color.RED, Color.BLUE);
        assertFalse(a1.equals(a2));
        a2 = new XYPolygonAnnotation(new double[] {99.0, 2.0, 3.0, 4.0, 5.0,
                6.0}, stroke1, Color.RED, Color.BLUE);
        assertEquals(a1, a2);

        a1 = new XYPolygonAnnotation(new double[] {99.0, 2.0, 3.0, 4.0, 5.0,
                6.0}, stroke2, Color.RED, Color.BLUE);
        assertFalse(a1.equals(a2));
        a2 = new XYPolygonAnnotation(new double[] {99.0, 2.0, 3.0, 4.0, 5.0,
                6.0}, stroke2, Color.RED, Color.BLUE);
        assertEquals(a1, a2);

        GradientPaint gp1 = new GradientPaint(1.0f, 2.0f, Color.yellow, 3.0f,
                4.0f, Color.WHITE);
        GradientPaint gp2 = new GradientPaint(1.0f, 2.0f, Color.yellow, 3.0f,
                4.0f, Color.WHITE);
        a1 = new XYPolygonAnnotation(new double[] {99.0, 2.0, 3.0, 4.0, 5.0,
                6.0}, stroke2, gp1, Color.BLUE);
        assertFalse(a1.equals(a2));
        a2 = new XYPolygonAnnotation(new double[] {99.0, 2.0, 3.0, 4.0, 5.0,
                6.0}, stroke2, gp2, Color.BLUE);
        assertEquals(a1, a2);

        GradientPaint gp3 = new GradientPaint(1.0f, 2.0f, Color.green, 3.0f,
                4.0f, Color.WHITE);
        GradientPaint gp4 = new GradientPaint(1.0f, 2.0f, Color.green, 3.0f,
                4.0f, Color.WHITE);
        a1 = new XYPolygonAnnotation(new double[] {99.0, 2.0, 3.0, 4.0, 5.0,
                6.0}, stroke2, gp1, gp3);
        assertFalse(a1.equals(a2));
        a2 = new XYPolygonAnnotation(new double[] {99.0, 2.0, 3.0, 4.0, 5.0,
                6.0}, stroke2, gp2, gp4);
        assertEquals(a1, a2);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        Stroke stroke = new BasicStroke(2.0f);
        XYPolygonAnnotation a1 = new XYPolygonAnnotation(new double[] {1.0,
                2.0, 3.0, 4.0, 5.0, 6.0}, stroke, Color.RED, Color.BLUE);
        XYPolygonAnnotation a2 = new XYPolygonAnnotation(new double[] {1.0,
                2.0, 3.0, 4.0, 5.0, 6.0}, stroke, Color.RED, Color.BLUE);
        assertEquals(a1, a2);
        int h1 = a1.hashCode();
        int h2 = a2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        Stroke stroke1 = new BasicStroke(2.0f);
        XYPolygonAnnotation a1 = new XYPolygonAnnotation(new double[] {1.0,
                2.0, 3.0, 4.0, 5.0, 6.0}, stroke1, Color.RED, Color.BLUE);
        XYPolygonAnnotation a2 = (XYPolygonAnnotation) a1.clone();


        assertNotSame(a1, a2);
        assertSame(a1.getClass(), a2.getClass());
        assertEquals(a1, a2);
    }

    /**
     * Checks that this class implements PublicCloneable.
     */
    @Test
    public void testPublicCloneable() {
        Stroke stroke1 = new BasicStroke(2.0f);
        XYPolygonAnnotation a1 = new XYPolygonAnnotation(new double[] {1.0,
                2.0, 3.0, 4.0, 5.0, 6.0}, stroke1, Color.RED, Color.BLUE);
        assertTrue(a1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {

        Stroke stroke1 = new BasicStroke(2.0f);
        XYPolygonAnnotation a1 = new XYPolygonAnnotation(new double[] {1.0,
                2.0, 3.0, 4.0, 5.0, 6.0}, stroke1, Color.RED, Color.BLUE);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(a1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                new ByteArrayInputStream(buffer.toByteArray())
            );
            XYPolygonAnnotation a2 = (XYPolygonAnnotation) in.readObject();
            in.close();

        assertEquals(a1, a2);

    }

}
