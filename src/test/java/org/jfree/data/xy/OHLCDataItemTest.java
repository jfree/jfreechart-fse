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
 * OHLCDataItemTests.java
 * ----------------------
 * (C) Copyright 2005-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 29-Apr-2005 : Version 1 (DG);
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
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;



/**
 * Tests for the {@link OHLCDataItem} class.
 */
public class OHLCDataItemTest  {





    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        OHLCDataItem i1 = new OHLCDataItem(
            new Date(1L), 1.0, 2.0, 3.0, 4.0, 5.0
        );
        OHLCDataItem i2 = new OHLCDataItem(
            new Date(1L), 1.0, 2.0, 3.0, 4.0, 5.0
        );
        assertEquals(i1, i2);
        assertEquals(i2, i1);
    }

    /**
     * Instances of this class are immutable - cloning not required.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        OHLCDataItem i1 = new OHLCDataItem(
            new Date(1L), 1.0, 2.0, 3.0, 4.0, 5.0
        );
        assertFalse(i1 instanceof Cloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        OHLCDataItem i1 = new OHLCDataItem(
            new Date(1L), 1.0, 2.0, 3.0, 4.0, 5.0
        );

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(i1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                new ByteArrayInputStream(buffer.toByteArray())
            );
        OHLCDataItem i2 = (OHLCDataItem) in.readObject();
            in.close();

        assertEquals(i1, i2);
    }

}
