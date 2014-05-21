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
 * NumberTickUnitTests.java
 * ------------------------
 * (C) Copyright 2005-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 5-Jul-2005 : Version 1 (DG);
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
import java.text.DecimalFormat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Some tests for the {@link NumberTickUnit} class.
 */
public class NumberTickUnitTest  {





    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        NumberTickUnit t1 = new NumberTickUnit(1.23, new DecimalFormat("0.00"));
        NumberTickUnit t2 = new NumberTickUnit(1.23, new DecimalFormat("0.00"));
        assertEquals(t1, t2);
        assertEquals(t2, t1);

        t1 = new NumberTickUnit(3.21, new DecimalFormat("0.00"));
        assertFalse(t1.equals(t2));
        t2 = new NumberTickUnit(3.21, new DecimalFormat("0.00"));
        assertEquals(t1, t2);

        t1 = new NumberTickUnit(3.21, new DecimalFormat("0.000"));
        assertFalse(t1.equals(t2));
        t2 = new NumberTickUnit(3.21, new DecimalFormat("0.000"));
        assertEquals(t1, t2);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        NumberTickUnit t1 = new NumberTickUnit(1.23, new DecimalFormat("0.00"));
        NumberTickUnit t2 = new NumberTickUnit(1.23, new DecimalFormat("0.00"));
        int h1 = t1.hashCode();
        int h2 = t2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * This is an immutable class so it doesn't need to be cloneable.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        NumberTickUnit t1 = new NumberTickUnit(1.23, new DecimalFormat("0.00"));
        assertFalse(t1 instanceof Cloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        NumberTickUnit t1 = new NumberTickUnit(1.23, new DecimalFormat("0.00"));

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(buffer);
        out.writeObject(t1);
        out.close();

        ObjectInput in = new ObjectInputStream(
                new ByteArrayInputStream(buffer.toByteArray()));
        NumberTickUnit t2 = (NumberTickUnit) in.readObject();
        in.close();

        assertEquals(t1, t2);
    }

}
