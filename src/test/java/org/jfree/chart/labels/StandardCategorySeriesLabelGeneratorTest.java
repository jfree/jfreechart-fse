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
 * ----------------------------------------------
 * StandardCategorySeriesLabelGeneratorTests.java
 * ----------------------------------------------
 * (C) Copyright 2006-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 03-May-2006 : Version 1 (DG);
 * 23-Apr-2008 : Added testPublicCloneable() (DG);
 *
 */

package org.jfree.chart.labels;

import org.jfree.chart.util.PublicCloneable;
import org.jfree.data.category.DefaultCategoryDataset;
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
import static org.junit.Assert.assertTrue;

/**
 * Tests for the {@link StandardCategorySeriesLabelGenerator} class.
 */
public class StandardCategorySeriesLabelGeneratorTest  {





    /**
     * Some checks for the generalLabel() method.
     */
    @Test
    public void testGenerateLabel() {
        StandardCategorySeriesLabelGenerator g
                = new StandardCategorySeriesLabelGenerator("{0}");
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(1.0, "R0", "C0");
        dataset.addValue(2.0, "R0", "C1");
        dataset.addValue(3.0, "R1", "C0");
        dataset.addValue(null, "R1", "C1");
        String s = g.generateLabel(dataset, 0);
        assertEquals("R0", s);
    }

    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        StandardCategorySeriesLabelGenerator g1
                = new StandardCategorySeriesLabelGenerator();
        StandardCategorySeriesLabelGenerator g2
                = new StandardCategorySeriesLabelGenerator();
        assertEquals(g1, g2);
        assertEquals(g2, g1);

        g1 = new StandardCategorySeriesLabelGenerator("{1}");
        assertFalse(g1.equals(g2));
        g2 = new StandardCategorySeriesLabelGenerator("{1}");
        assertEquals(g1, g2);
    }

    /**
     * Simple check that hashCode is implemented.
     */
    @Test
    public void testHashCode() {
        StandardCategorySeriesLabelGenerator g1
                = new StandardCategorySeriesLabelGenerator();
        StandardCategorySeriesLabelGenerator g2
                = new StandardCategorySeriesLabelGenerator();
        assertEquals(g1, g2);
        assertEquals(g1.hashCode(), g2.hashCode());
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        StandardCategorySeriesLabelGenerator g1
                = new StandardCategorySeriesLabelGenerator("{1}");
        StandardCategorySeriesLabelGenerator g2 = (StandardCategorySeriesLabelGenerator) g1.clone();


        assertNotSame(g1, g2);
        assertSame(g1.getClass(), g2.getClass());
        assertEquals(g1, g2);
    }

    /**
     * Check to ensure that this class implements PublicCloneable.
     */
    @Test
    public void testPublicCloneable() {
        StandardCategorySeriesLabelGenerator g1
                = new StandardCategorySeriesLabelGenerator("{1}");
        assertTrue(g1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {

        StandardCategorySeriesLabelGenerator g1
                = new StandardCategorySeriesLabelGenerator("{2}");

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(buffer);
        out.writeObject(g1);
        out.close();

        ObjectInput in = new ObjectInputStream(
                new ByteArrayInputStream(buffer.toByteArray()));
        StandardCategorySeriesLabelGenerator g2 = (StandardCategorySeriesLabelGenerator) in.readObject();
        in.close();

        assertEquals(g1, g2);
    }

}
