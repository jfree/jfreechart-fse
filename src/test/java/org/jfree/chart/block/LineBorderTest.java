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
 * --------------------
 * LineBorderTests.java
 * --------------------
 * (C) Copyright 2007-2012, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 16-Mar-2007 : Version 1 (DG);
 * 17-Jun-2012 : Remove JCommon dependencies (DG);
 *
 */

package org.jfree.chart.block;

import org.jfree.chart.ui.RectangleInsets;
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

/**
 * Tests for the {@link LineBorder} class.
 */
public class LineBorderTest  {





    /**
     * Confirm that the equals() method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        LineBorder b1 = new LineBorder(Color.RED, new BasicStroke(1.0f),
                new RectangleInsets(1.0, 1.0, 1.0, 1.0));
        LineBorder b2 = new LineBorder(Color.RED, new BasicStroke(1.0f),
                new RectangleInsets(1.0, 1.0, 1.0, 1.0));
        assertEquals(b1, b2);
        assertEquals(b2, b2);

        b1 = new LineBorder(Color.BLUE, new BasicStroke(1.0f),
                new RectangleInsets(1.0, 1.0, 1.0, 1.0));
        assertFalse(b1.equals(b2));
        b2 = new LineBorder(Color.BLUE, new BasicStroke(1.0f),
                new RectangleInsets(1.0, 1.0, 1.0, 1.0));
        assertEquals(b1, b2);

        b1 = new LineBorder(Color.BLUE, new BasicStroke(1.1f),
                new RectangleInsets(1.0, 1.0, 1.0, 1.0));
        assertFalse(b1.equals(b2));
        b2 = new LineBorder(Color.BLUE, new BasicStroke(1.1f),
                new RectangleInsets(1.0, 1.0, 1.0, 1.0));
        assertEquals(b1, b2);

        b1 = new LineBorder(Color.BLUE, new BasicStroke(1.1f),
                new RectangleInsets(1.0, 2.0, 3.0, 4.0));
        assertFalse(b1.equals(b2));
        b2 = new LineBorder(Color.BLUE, new BasicStroke(1.1f),
                new RectangleInsets(1.0, 2.0, 3.0, 4.0));
        assertEquals(b1, b2);

    }

    /**
     * Immutable - cloning not necessary.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        LineBorder b1 = new LineBorder();
        assertFalse(b1 instanceof Cloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        LineBorder b1 = new LineBorder(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.yellow), new BasicStroke(1.0f),
                new RectangleInsets(1.0, 1.0, 1.0, 1.0));

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(buffer);
        out.writeObject(b1);
        out.close();

        ObjectInput in = new ObjectInputStream(new ByteArrayInputStream(
                buffer.toByteArray()));
        LineBorder b2 = (LineBorder) in.readObject();
        in.close();
        assertEquals(b1, b2);
    }

}
