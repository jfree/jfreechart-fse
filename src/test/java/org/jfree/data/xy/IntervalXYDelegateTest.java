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
 * ----------------------------
 * IntervalXYDelegateTests.java
 * ----------------------------
 * (C) Copyright 2005-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 21-Feb-2005 : Version 1 (DG);
 * 06-Oct-2005 : Updated for testEquals() for method name change (DG);
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
 * Some checks for the {@link IntervalXYDelegate} class.
 */
public class IntervalXYDelegateTest  {





    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
       XYSeries s1 = new XYSeries("Series");
       s1.add(1.2, 3.4);
       XYSeriesCollection c1 = new XYSeriesCollection();
       c1.addSeries(s1);
       IntervalXYDelegate d1 = new IntervalXYDelegate(c1);

       XYSeries s2 = new XYSeries("Series");
       XYSeriesCollection c2 = new XYSeriesCollection();
       s2.add(1.2, 3.4);
       c2.addSeries(s2);
       IntervalXYDelegate d2 = new IntervalXYDelegate(c2);

       assertEquals(d1, d2);
       assertEquals(d2, d1);

       d1.setAutoWidth(false);
       assertFalse(d1.equals(d2));
       d2.setAutoWidth(false);
       assertEquals(d1, d2);

       d1.setIntervalPositionFactor(0.123);
       assertFalse(d1.equals(d2));
       d2.setIntervalPositionFactor(0.123);
       assertEquals(d1, d2);

       d1.setFixedIntervalWidth(1.23);
       assertFalse(d1.equals(d2));
       d2.setFixedIntervalWidth(1.23);
       assertEquals(d1, d2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        XYSeries s1 = new XYSeries("Series");
        s1.add(1.2, 3.4);
        XYSeriesCollection c1 = new XYSeriesCollection();
        c1.addSeries(s1);
        IntervalXYDelegate d1 = new IntervalXYDelegate(c1);

        IntervalXYDelegate d2 = (IntervalXYDelegate) d1.clone();
        assertNotSame(d1, d2);
        assertSame(d1.getClass(), d2.getClass());
        assertEquals(d1, d2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        XYSeries s1 = new XYSeries("Series");
        s1.add(1.2, 3.4);
        XYSeriesCollection c1 = new XYSeriesCollection();
        c1.addSeries(s1);
        IntervalXYDelegate d1 = new IntervalXYDelegate(c1);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(d1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                new ByteArrayInputStream(buffer.toByteArray())
            );
        IntervalXYDelegate d2 = (IntervalXYDelegate) in.readObject();
            in.close();

        assertEquals(d1, d2);

    }

}
