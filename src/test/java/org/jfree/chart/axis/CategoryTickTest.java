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
 * ----------------------
 * CategoryTickTests.java
 * ----------------------
 * (C) Copyright 2004-2012, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 13-May-2004 : Version 1 (DG);
 * 07-Jan-2005 : Added test for hashCode() (DG);
 * 17-Jun-2012 : Removed JCommon dependencies (DG);
 *
 */

package org.jfree.chart.axis;

import org.jfree.chart.text.TextBlock;
import org.jfree.chart.text.TextBlockAnchor;
import org.jfree.chart.text.TextLine;
import org.jfree.chart.ui.TextAnchor;
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
 * Tests for the {@link CategoryTick} class.
 */
public class CategoryTickTest  {





    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {

        Comparable c1 = "C1";
        Comparable c2 = "C2";
        TextBlock tb1 = new TextBlock();
        tb1.addLine(new TextLine("Block 1"));
        TextBlock tb2 = new TextBlock();
        tb1.addLine(new TextLine("Block 2"));
        TextBlockAnchor tba1 = TextBlockAnchor.CENTER;
        TextBlockAnchor tba2 = TextBlockAnchor.BOTTOM_CENTER;
        TextAnchor ta1 = TextAnchor.CENTER;
        TextAnchor ta2 = TextAnchor.BASELINE_LEFT;

        CategoryTick t1 = new CategoryTick(c1, tb1, tba1, ta1, 1.0f);
        CategoryTick t2 = new CategoryTick(c1, tb1, tba1, ta1, 1.0f);
        assertEquals(t1, t2);

        t1 = new CategoryTick(c2, tb1, tba1, ta1, 1.0f);
        assertFalse(t1.equals(t2));
        t2 = new CategoryTick(c2, tb1, tba1, ta1, 1.0f);
        assertEquals(t1, t2);

        t1 = new CategoryTick(c2, tb2, tba1, ta1, 1.0f);
        assertFalse(t1.equals(t2));
        t2 = new CategoryTick(c2, tb2, tba1, ta1, 1.0f);
        assertEquals(t1, t2);

        t1 = new CategoryTick(c2, tb2, tba2, ta1, 1.0f);
        assertFalse(t1.equals(t2));
        t2 = new CategoryTick(c2, tb2, tba2, ta1, 1.0f);
        assertEquals(t1, t2);

        t1 = new CategoryTick(c2, tb2, tba2, ta2, 1.0f);
        assertFalse(t1.equals(t2));
        t2 = new CategoryTick(c2, tb2, tba2, ta2, 1.0f);
        assertEquals(t1, t2);

        t1 = new CategoryTick(c2, tb2, tba2, ta2, 2.0f);
        assertFalse(t1.equals(t2));
        t2 = new CategoryTick(c2, tb2, tba2, ta2, 2.0f);
        assertEquals(t1, t2);

    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        Comparable c1 = "C1";
        TextBlock tb1 = new TextBlock();
        tb1.addLine(new TextLine("Block 1"));
        tb1.addLine(new TextLine("Block 2"));
        TextBlockAnchor tba1 = TextBlockAnchor.CENTER;
        TextAnchor ta1 = TextAnchor.CENTER;

        CategoryTick t1 = new CategoryTick(c1, tb1, tba1, ta1, 1.0f);
        CategoryTick t2 = new CategoryTick(c1, tb1, tba1, ta1, 1.0f);
        assertEquals(t1, t2);
        int h1 = t1.hashCode();
        int h2 = t2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        CategoryTick t1 = new CategoryTick(
            "C1", new TextBlock(), TextBlockAnchor.CENTER,
            TextAnchor.CENTER, 1.5f
        );
        CategoryTick t2 = (CategoryTick) t1.clone();
        assertNotSame(t1, t2);
        assertSame(t1.getClass(), t2.getClass());
        assertEquals(t1, t2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {

        CategoryTick t1 = new CategoryTick("C1", new TextBlock(),
                TextBlockAnchor.CENTER, TextAnchor.CENTER, 1.5f);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(buffer);
        out.writeObject(t1);
        out.close();

        ObjectInput in = new ObjectInputStream(
                new ByteArrayInputStream(buffer.toByteArray()));
        CategoryTick t2 = (CategoryTick) in.readObject();
        in.close();
        assertEquals(t1, t2);

    }

}
