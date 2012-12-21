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
 * OHLCItemTests.java
 * ------------------
 * (C) Copyright 2006-2009, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 04-Dec-2006 : Version 1 (DG);
 * 23-May-2009 : Added testHashCode() (DG);
 *
 */

package org.jfree.data.time.ohlc;

import org.jfree.data.time.Year;
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
 * Tests for the {@link OHLCItem} class.
 */
public class OHLCItemTest  {





    private static final double EPSILON = 0.00000000001;

    /**
     * Some checks for the constructor.
     */
    @Test
    public void testConstructor1() {
        OHLCItem item1 = new OHLCItem(new Year(2006), 2.0, 4.0, 1.0, 3.0);
        assertEquals(new Year(2006), item1.getPeriod());
        assertEquals(2.0, item1.getOpenValue(), EPSILON);
        assertEquals(4.0, item1.getHighValue(), EPSILON);
        assertEquals(1.0, item1.getLowValue(), EPSILON);
        assertEquals(3.0, item1.getCloseValue(), EPSILON);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        OHLCItem item1 = new OHLCItem(new Year(2006), 2.0, 4.0, 1.0, 3.0);
        OHLCItem item2 = new OHLCItem(new Year(2006), 2.0, 4.0, 1.0, 3.0);
        assertEquals(item1, item2);
        assertEquals(item2, item1);

        // period
        item1 = new OHLCItem(new Year(2007), 2.0, 4.0, 1.0, 3.0);
        assertFalse(item1.equals(item2));
        item2 = new OHLCItem(new Year(2007), 2.0, 4.0, 1.0, 3.0);
        assertEquals(item1, item2);

        // open
        item1 = new OHLCItem(new Year(2007), 2.2, 4.0, 1.0, 3.0);
        assertFalse(item1.equals(item2));
        item2 = new OHLCItem(new Year(2007), 2.2, 4.0, 1.0, 3.0);
        assertEquals(item1, item2);

        // high
        item1 = new OHLCItem(new Year(2007), 2.2, 4.4, 1.0, 3.0);
        assertFalse(item1.equals(item2));
        item2 = new OHLCItem(new Year(2007), 2.2, 4.4, 1.0, 3.0);
        assertEquals(item1, item2);

        // low
        item1 = new OHLCItem(new Year(2007), 2.2, 4.4, 1.1, 3.0);
        assertFalse(item1.equals(item2));
        item2 = new OHLCItem(new Year(2007), 2.2, 4.4, 1.1, 3.0);
        assertEquals(item1, item2);

        // close
        item1 = new OHLCItem(new Year(2007), 2.2, 4.4, 1.1, 3.3);
        assertFalse(item1.equals(item2));
        item2 = new OHLCItem(new Year(2007), 2.2, 4.4, 1.1, 3.3);
        assertEquals(item1, item2);
    }

    /**
     * Some checks for the clone() method.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        OHLCItem item1 = new OHLCItem(new Year(2006), 2.0, 4.0, 1.0, 3.0);
        OHLCItem item2 = (OHLCItem) item1.clone();
        assertNotSame(item1, item2);
        assertSame(item1.getClass(), item2.getClass());
        assertEquals(item1, item2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        OHLCItem item1 = new OHLCItem(new Year(2006), 2.0, 4.0, 1.0, 3.0);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(item1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
        OHLCItem item2 = (OHLCItem) in.readObject();
            in.close();

        assertEquals(item1, item2);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashcode() {
        OHLCItem i1 = new OHLCItem(new Year(2009), 2.0, 4.0, 1.0, 3.0);
        OHLCItem i2 = new OHLCItem(new Year(2009), 2.0, 4.0, 1.0, 3.0);
        assertEquals(i1, i2);
        int h1 = i1.hashCode();
        int h2 = i2.hashCode();
        assertEquals(h1, h2);
    }

}
