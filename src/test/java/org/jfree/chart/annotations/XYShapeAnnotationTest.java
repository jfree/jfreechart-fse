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
 * ---------------------------
 * XYShapeAnnotationTests.java
 * ---------------------------
 * (C) Copyright 2004-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 29-Sep-2004 : Version 1 (DG);
 * 07-Jan-2005 : Added hashCode() test (DG);
 * 23-Apr-2008 : Added testPublicCloneable() (DG);
 *
 */

package org.jfree.chart.annotations;

import org.jfree.chart.util.PublicCloneable;
import org.junit.Test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.geom.Rectangle2D;
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
 * Some tests for the {@link XYShapeAnnotation} class.
 */
public class XYShapeAnnotationTest  {





    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {

        XYShapeAnnotation a1 = new XYShapeAnnotation(
                new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0),
                new BasicStroke(1.2f), Color.RED, Color.BLUE);
        XYShapeAnnotation a2 = new XYShapeAnnotation(
                new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0),
                new BasicStroke(1.2f), Color.RED, Color.BLUE);
        assertEquals(a1, a2);
        assertEquals(a2, a1);

        // shape
        a1 = new XYShapeAnnotation(
                new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0),
                new BasicStroke(1.2f), Color.RED, Color.BLUE);
        assertFalse(a1.equals(a2));
        a2 = new XYShapeAnnotation(
                new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0),
                new BasicStroke(1.2f), Color.RED, Color.BLUE);
        assertEquals(a1, a2);

        // stroke
        a1 = new XYShapeAnnotation(
                new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0),
                new BasicStroke(2.3f), Color.RED, Color.BLUE);
        assertFalse(a1.equals(a2));
        a2 = new XYShapeAnnotation(
                new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0),
                new BasicStroke(2.3f), Color.RED, Color.BLUE);
        assertEquals(a1, a2);

        GradientPaint gp1a = new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.RED);
        GradientPaint gp1b = new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.RED);
        GradientPaint gp2a = new GradientPaint(5.0f, 6.0f, Color.pink,
                7.0f, 8.0f, Color.WHITE);
        GradientPaint gp2b = new GradientPaint(5.0f, 6.0f, Color.pink,
                7.0f, 8.0f, Color.WHITE);

        // outlinePaint
        a1 = new XYShapeAnnotation(
                new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0),
                new BasicStroke(2.3f), gp1a, Color.BLUE);
        assertFalse(a1.equals(a2));
        a2 = new XYShapeAnnotation(
                new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0),
                new BasicStroke(2.3f), gp1b, Color.BLUE);
        assertEquals(a1, a2);

        // fillPaint
        a1 = new XYShapeAnnotation(
                new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0),
                new BasicStroke(2.3f), gp1a, gp2a);
        assertFalse(a1.equals(a2));
        a2 = new XYShapeAnnotation(
                new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0),
                new BasicStroke(2.3f), gp1b, gp2b);
        assertEquals(a1, a2);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        XYShapeAnnotation a1 = new XYShapeAnnotation(
                new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0),
                new BasicStroke(1.2f), Color.RED, Color.BLUE);
        XYShapeAnnotation a2 = new XYShapeAnnotation(
                new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0),
                new BasicStroke(1.2f), Color.RED, Color.BLUE);
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
        XYShapeAnnotation a1 = new XYShapeAnnotation(
                new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0),
                new BasicStroke(1.2f), Color.RED, Color.BLUE);
        XYShapeAnnotation a2 = (XYShapeAnnotation) a1.clone();

        assertNotSame(a1, a2);
        assertSame(a1.getClass(), a2.getClass());
        assertEquals(a1, a2);
    }

    /**
     * Checks that this class implements PublicCloneable.
     */
    @Test
    public void testPublicCloneable() {
        XYShapeAnnotation a1 = new XYShapeAnnotation(
                new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0),
                new BasicStroke(1.2f), Color.RED, Color.BLUE);
        assertTrue(a1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        XYShapeAnnotation a1 = new XYShapeAnnotation(
                new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0),
                new BasicStroke(1.2f), Color.RED, Color.BLUE);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(buffer);
        out.writeObject(a1);
        out.close();

        ObjectInput in = new ObjectInputStream(
                new ByteArrayInputStream(buffer.toByteArray()));
        XYShapeAnnotation a2 = (XYShapeAnnotation) in.readObject();
        in.close();

        assertEquals(a1, a2);
    }

}
