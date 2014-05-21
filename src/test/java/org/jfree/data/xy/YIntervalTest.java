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
 * -------------------
 * YIntervalTests.java
 * -------------------
 * (C) Copyright 2006-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 20-Oct-2006 : Version 1 (DG);
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



/**
 * Tests for the {@link YInterval} class.
 */
public class YIntervalTest  {





    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        YInterval i1 = new YInterval(1.0, 0.5, 1.5);
        YInterval i2 = new YInterval(1.0, 0.5, 1.5);
        assertEquals(i1, i2);

        i1 = new YInterval(1.1, 0.5, 1.5);
        assertFalse(i1.equals(i2));
        i2 = new YInterval(1.1, 0.5, 1.5);
        assertEquals(i1, i2);

        i1 = new YInterval(1.1, 0.55, 1.5);
        assertFalse(i1.equals(i2));
        i2 = new YInterval(1.1, 0.55, 1.5);
        assertEquals(i1, i2);

        i1 = new YInterval(1.1, 0.55, 1.55);
        assertFalse(i1.equals(i2));
        i2 = new YInterval(1.1, 0.55, 1.55);
        assertEquals(i1, i2);
    }

    /**
     * This class is immutable.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        YInterval i1 = new YInterval(1.0, 0.5, 1.5);
        assertFalse(i1 instanceof Cloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        YInterval i1 = new YInterval(1.0, 0.5, 1.5);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(i1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
        YInterval i2 = (YInterval) in.readObject();
            in.close();

        assertEquals(i1, i2);
    }

}
