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
 * ------------------
 * DateTickTests.java
 * ------------------
 * (C) Copyright 2004-2012, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 13-May-2004 : Version 1 (DG);
 * 25-Sep-2008 : Extended testEquals() to cover new fields (DG);
 * 17-Jun-2012 : Removed JCommon dependencies (DG);
 * 
 */

package org.jfree.chart.axis;

import org.jfree.chart.ui.TextAnchor;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

/**
 * Tests for the {@link DateTick} class.
 */
public class DateTickTest  {





    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {

        Date d1 = new Date(0L);
        Date d2 = new Date(1L);
        String l1 = "Label 1";
        String l2 = "Label 2";
        TextAnchor ta1 = TextAnchor.CENTER;
        TextAnchor ta2 = TextAnchor.BASELINE_LEFT;

        DateTick t1 = new DateTick(d1, l1, ta1, ta1, Math.PI / 2.0);
        DateTick t2 = new DateTick(d1, l1, ta1, ta1, Math.PI / 2.0);
        assertEquals(t1, t2);

        t1 = new DateTick(d2, l1, ta1, ta1, Math.PI / 2.0);
        assertFalse(t1.equals(t2));
        t2 = new DateTick(d2, l1, ta1, ta1, Math.PI / 2.0);
        assertEquals(t1, t2);

        t1 = new DateTick(d1, l2, ta1, ta1, Math.PI / 2.0);
        assertFalse(t1.equals(t2));
        t2 = new DateTick(d1, l2, ta1, ta1, Math.PI / 2.0);
        assertEquals(t1, t2);

        t1 = new DateTick(d1, l1, ta2, ta1, Math.PI / 2.0);
        assertFalse(t1.equals(t2));
        t2 = new DateTick(d1, l1, ta2, ta1, Math.PI / 2.0);
        assertEquals(t1, t2);

        t1 = new DateTick(d1, l1, ta1, ta2, Math.PI / 2.0);
        assertFalse(t1.equals(t2));
        t2 = new DateTick(d1, l1, ta1, ta2, Math.PI / 2.0);
        assertEquals(t1, t2);

        t1 = new DateTick(d1, l1, ta1, ta1, Math.PI / 3.0);
        assertFalse(t1.equals(t2));
        t2 = new DateTick(d1, l1, ta1, ta1, Math.PI / 3.0);
        assertEquals(t1, t2);

        t1 = new DateTick(TickType.MINOR, d1, l1, ta1, ta1, Math.PI);
        t2 = new DateTick(TickType.MAJOR, d1, l1, ta1, ta1, Math.PI);
        assertFalse(t1.equals(t2));
        t2 = new DateTick(TickType.MINOR, d1, l1, ta1, ta1, Math.PI);
        assertEquals(t1, t2);

    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        Date d1 = new Date(0L);
        String l1 = "Label 1";
        TextAnchor ta1 = TextAnchor.CENTER;

        DateTick t1 = new DateTick(d1, l1, ta1, ta1, Math.PI / 2.0);
        DateTick t2 = new DateTick(d1, l1, ta1, ta1, Math.PI / 2.0);
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
        DateTick t1 = new DateTick(new Date(0L), "Label", TextAnchor.CENTER,
                TextAnchor.CENTER, 10.0);
        DateTick t2 = (DateTick) t1.clone();

        assertNotSame(t1, t2);
        assertSame(t1.getClass(), t2.getClass());
        assertEquals(t1, t2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {

        DateTick t1 = new DateTick(new Date(0L), "Label", TextAnchor.CENTER,
                TextAnchor.CENTER, 10.0);


        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(buffer);
        out.writeObject(t1);
        out.close();

        ObjectInput in = new ObjectInputStream(
                new ByteArrayInputStream(buffer.toByteArray()));
        DateTick t2 = (DateTick) in.readObject();
        in.close();

        assertEquals(t1, t2);

    }

}
