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
 * LabelBlockTests.java
 * --------------------
 * (C) Copyright 2005-2009, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 01-Sep-2005 : Version 1 (DG);
 * 16-Mar-2007 : Check GradientPaint in testSerialization() (DG);
 * 10-Feb-2009 : Added new fields to testEquals() (DG);
 *
 */

package org.jfree.chart.block;

import org.jfree.chart.text.TextBlockAnchor;
import org.jfree.chart.ui.RectangleAnchor;
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

/**
 * Some tests for the {@link LabelBlock} class.
 */
public class LabelBlockTest  {





    /**
     * Confirm that the equals() method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        LabelBlock b1 = new LabelBlock("ABC", new Font("Dialog",
                Font.PLAIN, 12), Color.RED);
        LabelBlock b2 = new LabelBlock("ABC", new Font("Dialog",
                Font.PLAIN, 12), Color.RED);
        assertEquals(b1, b2);
        assertEquals(b2, b2);

        b1 = new LabelBlock("XYZ", new Font("Dialog", Font.PLAIN, 12),
                Color.RED);
        assertFalse(b1.equals(b2));
        b2 = new LabelBlock("XYZ", new Font("Dialog", Font.PLAIN, 12),
                Color.RED);
        assertEquals(b1, b2);

        b1 = new LabelBlock("XYZ", new Font("Dialog", Font.BOLD, 12),
                Color.RED);
        assertFalse(b1.equals(b2));
        b2 = new LabelBlock("XYZ", new Font("Dialog", Font.BOLD, 12),
                Color.RED);
        assertEquals(b1, b2);

        b1 = new LabelBlock("XYZ", new Font("Dialog", Font.BOLD, 12),
                Color.BLUE);
        assertFalse(b1.equals(b2));
        b2 = new LabelBlock("XYZ", new Font("Dialog", Font.BOLD, 12),
                Color.BLUE);
        assertEquals(b1, b2);

        b1.setToolTipText("Tooltip");
        assertFalse(b1.equals(b2));
        b2.setToolTipText("Tooltip");
        assertEquals(b1, b2);

        b1.setURLText("URL");
        assertFalse(b1.equals(b2));
        b2.setURLText("URL");
        assertEquals(b1, b2);

        b1.setContentAlignmentPoint(TextBlockAnchor.CENTER_RIGHT);
        assertFalse(b1.equals(b2));
        b2.setContentAlignmentPoint(TextBlockAnchor.CENTER_RIGHT);
        assertEquals(b1, b2);

        b1.setTextAnchor(RectangleAnchor.BOTTOM_RIGHT);
        assertFalse(b1.equals(b2));
        b2.setTextAnchor(RectangleAnchor.BOTTOM_RIGHT);
        assertEquals(b1, b2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        LabelBlock b1 = new LabelBlock("ABC", new Font("Dialog",
                Font.PLAIN, 12), Color.RED);
        LabelBlock b2 = (LabelBlock) b1.clone();
        assertNotSame(b1, b2);
        assertSame(b1.getClass(), b2.getClass());
        assertEquals(b1, b2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        GradientPaint gp = new GradientPaint(1.0f, 2.0f, Color.RED, 3.0f, 4.0f,
                Color.BLUE);
        LabelBlock b1 = new LabelBlock("ABC", new Font("Dialog",
                Font.PLAIN, 12), gp);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(buffer);
        out.writeObject(b1);
        out.close();

        ObjectInput in = new ObjectInputStream(
                new ByteArrayInputStream(buffer.toByteArray()));
        LabelBlock b2 = (LabelBlock) in.readObject();
        in.close();
        assertEquals(b1, b2);
    }

}
