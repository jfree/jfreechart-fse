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
 * ------------------------------
 * DateTickMarkPositionTests.java
 * ------------------------------
 * (C) Copyright 2004-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 19-May-2004 : Version 1 (DG);
 *
 */

package org.jfree.chart.axis;

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
import static org.junit.Assert.assertSame;

/**
 * Tests for the {@link DateTickMarkPosition} class.
 */
public class DateTickMarkPositionTest  {





    /**
     * Test equals() method.
     */
    @Test
    public void testEquals() {
        assertEquals(DateTickMarkPosition.START, DateTickMarkPosition.START);
        assertEquals(DateTickMarkPosition.MIDDLE, DateTickMarkPosition.MIDDLE);
        assertEquals(DateTickMarkPosition.END, DateTickMarkPosition.END);
        assertFalse(DateTickMarkPosition.START.equals(null));
        assertFalse(DateTickMarkPosition.START.equals(
                DateTickMarkPosition.END));
        assertFalse(DateTickMarkPosition.MIDDLE.equals(
                DateTickMarkPosition.END));
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        DateTickMarkPosition a1 = DateTickMarkPosition.END;
        DateTickMarkPosition a2 = DateTickMarkPosition.END;
        assertEquals(a1, a2);
        int h1 = a1.hashCode();
        int h2 = a2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        DateTickMarkPosition p1 = DateTickMarkPosition.MIDDLE;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(buffer);
        out.writeObject(p1);
        out.close();

        ObjectInput in = new ObjectInputStream(
                new ByteArrayInputStream(buffer.toByteArray()));
        DateTickMarkPosition p2 = (DateTickMarkPosition) in.readObject();
        in.close();

        assertSame(p1, p2);
    }

}
