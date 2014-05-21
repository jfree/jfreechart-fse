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
 * ------------------------
 * VectorDataItemTests.java
 * ------------------------
 * (C) Copyright 2007, 2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 30-Jan-2007 : Version 1 (DG);
 *
 */

package org.jfree.data.xy;

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
 * Tests for the {@link VectorDataItem} class.
 */
public class VectorDataItemTest  {





    /**
     * Test that the equals() method distinguishes all fields.
     */
    @Test
    public void testEquals() {
        // default instances
        VectorDataItem v1 = new VectorDataItem(1.0, 2.0, 3.0, 4.0);
        VectorDataItem v2 = new VectorDataItem(1.0, 2.0, 3.0, 4.0);
        assertEquals(v1, v2);
        assertEquals(v2, v1);

        v1 = new VectorDataItem(1.1, 2.0, 3.0, 4.0);
        assertFalse(v1.equals(v2));
        v2 = new VectorDataItem(1.1, 2.0, 3.0, 4.0);
        assertEquals(v1, v2);

        v1 = new VectorDataItem(1.1, 2.2, 3.0, 4.0);
        assertFalse(v1.equals(v2));
        v2 = new VectorDataItem(1.1, 2.2, 3.0, 4.0);
        assertEquals(v1, v2);

        v1 = new VectorDataItem(1.1, 2.2, 3.3, 4.0);
        assertFalse(v1.equals(v2));
        v2 = new VectorDataItem(1.1, 2.2, 3.3, 4.0);
        assertEquals(v1, v2);

        v1 = new VectorDataItem(1.1, 2.2, 3.3, 4.4);
        assertFalse(v1.equals(v2));
        v2 = new VectorDataItem(1.1, 2.2, 3.3, 4.4);
        assertEquals(v1, v2);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashcode() {
        VectorDataItem v1 = new VectorDataItem(1.0, 2.0, 3.0, 4.0);
        VectorDataItem v2 = new VectorDataItem(1.0, 2.0, 3.0, 4.0);
        assertEquals(v1, v2);
        int h1 = v1.hashCode();
        int h2 = v2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Check cloning.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        VectorDataItem v1 = new VectorDataItem(1.0, 2.0, 3.0, 4.0);
        VectorDataItem v2 = (VectorDataItem) v1.clone();
        assertNotSame(v1, v2);
        assertSame(v1.getClass(), v2.getClass());
        assertEquals(v1, v2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        VectorDataItem v1 = new VectorDataItem(1.0, 2.0, 3.0, 4.0);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(v1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
        VectorDataItem v2 = (VectorDataItem) in.readObject();
            in.close();

        assertEquals(v1, v2);
    }

}
