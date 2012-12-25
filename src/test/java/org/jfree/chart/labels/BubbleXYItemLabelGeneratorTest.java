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
 * ------------------------------------
 * BubbleXYItemLabelGeneratorTests.java
 * ------------------------------------
 * (C) Copyright 2006-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 20-Jan-2006 : Version 1 (DG);
 * 23-Apr-2008 : Added testPublicCloneable() (DG);
 *
 */

package org.jfree.chart.labels;

import org.jfree.chart.util.PublicCloneable;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the {@link BubbleXYItemLabelGenerator} class.
 */
public class BubbleXYItemLabelGeneratorTest  {





    /**
     * A series of tests for the equals() method.
     */
    @Test
    public void testEquals() {

        // some setup...
        String f1 = "{1}";
        String f2 = "{2}";
        NumberFormat xnf1 = new DecimalFormat("0.00");
        NumberFormat xnf2 = new DecimalFormat("0.000");
        NumberFormat ynf1 = new DecimalFormat("0.00");
        NumberFormat ynf2 = new DecimalFormat("0.000");
        NumberFormat znf1 = new DecimalFormat("0.00");
        NumberFormat znf2 = new DecimalFormat("0.000");

        BubbleXYItemLabelGenerator g1;
        BubbleXYItemLabelGenerator g2;

        g1 = new BubbleXYItemLabelGenerator(f1, xnf1, ynf1, znf1);
        g2 = new BubbleXYItemLabelGenerator(f1, xnf1, ynf1, znf1);
        assertEquals(g1, g2);
        assertEquals(g2, g1);

        g1 = new BubbleXYItemLabelGenerator(f2, xnf1, ynf1, znf1);
        assertFalse(g1.equals(g2));
        g2 = new BubbleXYItemLabelGenerator(f2, xnf1, ynf1, znf1);
        assertEquals(g1, g2);

        g1 = new BubbleXYItemLabelGenerator(f2, xnf2, ynf1, znf1);
        assertFalse(g1.equals(g2));
        g2 = new BubbleXYItemLabelGenerator(f2, xnf2, ynf1, znf1);
        assertEquals(g1, g2);

        g1 = new BubbleXYItemLabelGenerator(f2, xnf2, ynf2, znf1);
        assertFalse(g1.equals(g2));
        g2 = new BubbleXYItemLabelGenerator(f2, xnf2, ynf2, znf1);
        assertEquals(g1, g2);

        g1 = new BubbleXYItemLabelGenerator(f2, xnf2, ynf2, znf2);
        assertFalse(g1.equals(g2));
        g2 = new BubbleXYItemLabelGenerator(f2, xnf2, ynf2, znf2);
        assertEquals(g1, g2);

        DateFormat xdf1 = new SimpleDateFormat("d-MMM");
        DateFormat xdf2 = new SimpleDateFormat("d-MMM-yyyy");
        DateFormat ydf1 = new SimpleDateFormat("d-MMM");
        DateFormat ydf2 = new SimpleDateFormat("d-MMM-yyyy");
        DateFormat zdf1 = new SimpleDateFormat("d-MMM");
        DateFormat zdf2 = new SimpleDateFormat("d-MMM-yyyy");

        g1 = new BubbleXYItemLabelGenerator(f1, xdf1, ydf1, zdf1);
        g2 = new BubbleXYItemLabelGenerator(f1, xdf1, ydf1, zdf1);
        assertEquals(g1, g2);
        assertEquals(g2, g1);

        g1 = new BubbleXYItemLabelGenerator(f1, xdf2, ydf1, zdf1);
        assertFalse(g1.equals(g2));
        g2 = new BubbleXYItemLabelGenerator(f1, xdf2, ydf1, zdf1);
        assertEquals(g1, g2);

        g1 = new BubbleXYItemLabelGenerator(f1, xdf2, ydf2, zdf1);
        assertFalse(g1.equals(g2));
        g2 = new BubbleXYItemLabelGenerator(f1, xdf2, ydf2, zdf1);
        assertEquals(g1, g2);

        g1 = new BubbleXYItemLabelGenerator(f1, xdf2, ydf2, zdf2);
        assertFalse(g1.equals(g2));
        g2 = new BubbleXYItemLabelGenerator(f1, xdf2, ydf2, zdf2);
        assertEquals(g1, g2);
    }

    /**
     * Simple check that hashCode is implemented.
     */
    @Test
    public void testHashCode() {
        BubbleXYItemLabelGenerator g1
                = new BubbleXYItemLabelGenerator();
        BubbleXYItemLabelGenerator g2
                = new BubbleXYItemLabelGenerator();
        assertEquals(g1, g2);
        assertEquals(g1.hashCode(), g2.hashCode());
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        BubbleXYItemLabelGenerator g1 = new BubbleXYItemLabelGenerator();
        BubbleXYItemLabelGenerator g2 = (BubbleXYItemLabelGenerator) g1.clone();
        assertNotSame(g1, g2);
        assertSame(g1.getClass(), g2.getClass());
        assertEquals(g1, g2);
    }

    /**
     * Check to ensure that this class implements PublicCloneable.
     */
    @Test
    public void testPublicCloneable() {
        BubbleXYItemLabelGenerator g1 = new BubbleXYItemLabelGenerator();
        assertTrue(g1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {

        BubbleXYItemLabelGenerator g1 = new BubbleXYItemLabelGenerator();


        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(buffer);
        out.writeObject(g1);
        out.close();

        ObjectInput in = new ObjectInputStream(
                new ByteArrayInputStream(buffer.toByteArray()));
        BubbleXYItemLabelGenerator g2 = (BubbleXYItemLabelGenerator) in.readObject();
        in.close();

        assertEquals(g1, g2);

    }

    /**
     * Some checks for the testGenerateLabel() method.
     */
    @Test
    public void testGenerateLabel() {
        // check handling when the dataset is a regular XYDataset, not an
        // XYZDataset...
        XYSeries s1 = new XYSeries("S1");
        s1.add(1.0, 2.0);
        s1.add(2.2, 3.3);
        XYSeriesCollection dataset = new XYSeriesCollection(s1);
        BubbleXYItemLabelGenerator g = new BubbleXYItemLabelGenerator();
        assertEquals("{3}", g.generateLabel(dataset, 0, 0));
        assertEquals("{3}", g.generateLabel(dataset, 0, 1));
    }

}
