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
 * DialTextAnnotationTests.java
 * ----------------------------
 * (C) Copyright 2006-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 03-Nov-2006 : Version 1 (DG);
 *
 */

package org.jfree.chart.plot.dial;

import org.junit.Test;

import java.awt.Color;
import java.awt.Font;
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
import static org.junit.Assert.assertTrue;



/**
 * Tests for the {@link DialTextAnnotation} class.
 */
public class DialTextAnnotationTest  {





    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        DialTextAnnotation a1 = new DialTextAnnotation("A1");
        DialTextAnnotation a2 = new DialTextAnnotation("A1");
        assertEquals(a1, a2);

        // angle
        a1.setAngle(1.1);
        assertFalse(a1.equals(a2));
        a2.setAngle(1.1);
        assertEquals(a1, a2);

        // radius
        a1.setRadius(9.9);
        assertFalse(a1.equals(a2));
        a2.setRadius(9.9);
        assertEquals(a1, a2);

        // font
        Font f = new Font("SansSerif", Font.PLAIN, 14);
        a1.setFont(f);
        assertFalse(a1.equals(a2));
        a2.setFont(f);
        assertEquals(a1, a2);

        // paint
        a1.setPaint(Color.RED);
        assertFalse(a1.equals(a2));
        a2.setPaint(Color.RED);
        assertEquals(a1, a2);

        // label
        a1.setLabel("ABC");
        assertFalse(a1.equals(a2));
        a2.setLabel("ABC");
        assertEquals(a1, a2);

        // check an inherited attribute
        a1.setVisible(false);
        assertFalse(a1.equals(a2));
        a2.setVisible(false);
        assertEquals(a1, a2);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        DialTextAnnotation a1 = new DialTextAnnotation("A1");
        DialTextAnnotation a2 = new DialTextAnnotation("A1");
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
        // test a default instance
        DialTextAnnotation a1 = new DialTextAnnotation("A1");
        DialTextAnnotation a2 = (DialTextAnnotation) a1.clone();
        assertNotSame(a1, a2);
        assertSame(a1.getClass(), a2.getClass());
        assertEquals(a1, a2);

        // check that the listener lists are independent
        MyDialLayerChangeListener l1 = new MyDialLayerChangeListener();
        a1.addChangeListener(l1);
        assertTrue(a1.hasListener(l1));
        assertFalse(a2.hasListener(l1));

    }


    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        // test a default instance
        DialTextAnnotation a1 = new DialTextAnnotation("A1");

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(a1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
        DialTextAnnotation a2 = (DialTextAnnotation) in.readObject();
            in.close();

        assertEquals(a1, a2);

        // test a custom instance
        a1 = new DialTextAnnotation("A1");
        a1.setPaint(new GradientPaint(1.0f, 2.0f, Color.RED, 3.0f, 4.0f,
                Color.BLUE));


        buffer = new ByteArrayOutputStream();
        out = new ObjectOutputStream(buffer);
            out.writeObject(a1);
            out.close();

        in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            a2 = (DialTextAnnotation) in.readObject();
            in.close();
        assertEquals(a1, a2);


    }

}
