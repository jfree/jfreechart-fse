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
 * CategoryPointerAnnotationTests.java
 * -----------------------------------
 * (C) Copyright 2006-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 02-Oct-2006 : Version 1 (DG);
 * 23-Apr-2008 : Added testPublicCloneable() (DG);
 *
 */

package org.jfree.chart.annotations;

import org.jfree.chart.util.PublicCloneable;
import org.junit.Test;

import java.awt.BasicStroke;
import java.awt.Color;
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
 * Tests for the {@link CategoryPointerAnnotation} class.
 */
public class CategoryPointerAnnotationTest  {





    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {

        CategoryPointerAnnotation a1 = new CategoryPointerAnnotation("Label",
                "Key 1", 20.0, Math.PI);
        CategoryPointerAnnotation a2 = new CategoryPointerAnnotation("Label",
                "Key 1", 20.0, Math.PI);
        assertEquals(a1, a2);

        a1 = new CategoryPointerAnnotation("Label2", "Key 1", 20.0, Math.PI);
        assertFalse(a1.equals(a2));
        a2 = new CategoryPointerAnnotation("Label2", "Key 1", 20.0, Math.PI);
        assertEquals(a1, a2);

        a1.setCategory("Key 2");
        assertFalse(a1.equals(a2));
        a2.setCategory("Key 2");
        assertEquals(a1, a2);

        a1.setValue(22.0);
        assertFalse(a1.equals(a2));
        a2.setValue(22.0);
        assertEquals(a1, a2);

        //private double angle;
        a1.setAngle(Math.PI / 4.0);
        assertFalse(a1.equals(a2));
        a2.setAngle(Math.PI / 4.0);
        assertEquals(a1, a2);

        //private double tipRadius;
        a1.setTipRadius(20.0);
        assertFalse(a1.equals(a2));
        a2.setTipRadius(20.0);
        assertEquals(a1, a2);

        //private double baseRadius;
        a1.setBaseRadius(5.0);
        assertFalse(a1.equals(a2));
        a2.setBaseRadius(5.0);
        assertEquals(a1, a2);

        //private double arrowLength;
        a1.setArrowLength(33.0);
        assertFalse(a1.equals(a2));
        a2.setArrowLength(33.0);
        assertEquals(a1, a2);

        //private double arrowWidth;
        a1.setArrowWidth(9.0);
        assertFalse(a1.equals(a2));
        a2.setArrowWidth(9.0);
        assertEquals(a1, a2);

        //private Stroke arrowStroke;
        Stroke stroke = new BasicStroke(1.5f);
        a1.setArrowStroke(stroke);
        assertFalse(a1.equals(a2));
        a2.setArrowStroke(stroke);
        assertEquals(a1, a2);

        //private Paint arrowPaint;
        a1.setArrowPaint(Color.BLUE);
        assertFalse(a1.equals(a2));
        a2.setArrowPaint(Color.BLUE);
        assertEquals(a1, a2);

        //private double labelOffset;
        a1.setLabelOffset(10.0);
        assertFalse(a1.equals(a2));
        a2.setLabelOffset(10.0);
        assertEquals(a1, a2);

    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        CategoryPointerAnnotation a1 = new CategoryPointerAnnotation("Label",
                "A", 20.0, Math.PI);
        CategoryPointerAnnotation a2 = new CategoryPointerAnnotation("Label",
                "A", 20.0, Math.PI);
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
        CategoryPointerAnnotation a1 = new CategoryPointerAnnotation("Label",
                "A", 20.0, Math.PI);
        CategoryPointerAnnotation a2 = (CategoryPointerAnnotation) a1.clone();

        assertNotSame(a1, a2);
        assertSame(a1.getClass(), a2.getClass());
        assertEquals(a1, a2);
    }

    /**
     * Checks that this class implements PublicCloneable.
     */
    @Test
    public void testPublicCloneable() {
        CategoryPointerAnnotation a1 = new CategoryPointerAnnotation("Label",
                "A", 20.0, Math.PI);
        assertTrue(a1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {

        CategoryPointerAnnotation a1 = new CategoryPointerAnnotation("Label",
                "A", 20.0, Math.PI);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(buffer);
        out.writeObject(a1);
        out.close();

        ObjectInput in = new ObjectInputStream(new ByteArrayInputStream(
                buffer.toByteArray()));
        CategoryPointerAnnotation a2 = (CategoryPointerAnnotation) in.readObject();
        in.close();

        assertEquals(a1, a2);

    }

}
