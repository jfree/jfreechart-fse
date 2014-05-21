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
 * --------------------------------
 * TimeSeriesURLGeneratorTests.java
 * --------------------------------
 * (C) Copyright 2007, 2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 17-Apr-2007 : Version 1 (DG);
 * 23-Apr-2008 : Added testPublicCloneable (DG);
 *
 */

package org.jfree.chart.urls;

import org.jfree.chart.util.PublicCloneable;
import org.jfree.data.xy.DefaultXYDataset;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the {@link TimeSeriesURLGenerator} class.
 */
public class TimeSeriesURLGeneratorTest  {





    /**
     * A basic check for the generateURL() method.
     */
    @Test
    public void testGenerateURL() {
        TimeSeriesURLGenerator g = new TimeSeriesURLGenerator();
        DefaultXYDataset dataset = new DefaultXYDataset();
        dataset.addSeries("Series '1'", new double[][] {{1.0, 2.0},
                {3.0, 4.0}});
        String s = g.generateURL(dataset, 0, 0);
        assertTrue(s.startsWith("index.html?series=Series+%271%27&amp;item="));
    }

    /**
     * Check that the equals() method can distinguish all fields.
     */
    @Test
    public void testEquals() {
        TimeSeriesURLGenerator g1 = new TimeSeriesURLGenerator();
        TimeSeriesURLGenerator g2 = new TimeSeriesURLGenerator();
        assertEquals(g1, g2);

        g1 = new TimeSeriesURLGenerator(new SimpleDateFormat("yyyy"), "prefix",
                "series", "item");
        assertFalse(g1.equals(g2));
        g2 = new TimeSeriesURLGenerator(new SimpleDateFormat("yyyy"), "prefix",
                "series", "item");
        assertEquals(g1, g2);

        g1 = new TimeSeriesURLGenerator(new SimpleDateFormat("yy"), "prefix",
                "series", "item");
        assertFalse(g1.equals(g2));
        g2 = new TimeSeriesURLGenerator(new SimpleDateFormat("yy"), "prefix",
                "series", "item");
        assertEquals(g1, g2);

        g1 = new TimeSeriesURLGenerator(new SimpleDateFormat("yy"), "prefix1",
                "series", "item");
        assertFalse(g1.equals(g2));
        g2 = new TimeSeriesURLGenerator(new SimpleDateFormat("yy"), "prefix1",
                "series", "item");
        assertEquals(g1, g2);

        g1 = new TimeSeriesURLGenerator(new SimpleDateFormat("yy"), "prefix1",
                "series1", "item");
        assertFalse(g1.equals(g2));
        g2 = new TimeSeriesURLGenerator(new SimpleDateFormat("yy"), "prefix1",
                "series1", "item");
        assertEquals(g1, g2);

        g1 = new TimeSeriesURLGenerator(new SimpleDateFormat("yy"), "prefix1",
                "series1", "item1");
        assertFalse(g1.equals(g2));
        g2 = new TimeSeriesURLGenerator(new SimpleDateFormat("yy"), "prefix1",
                "series1", "item1");
        assertEquals(g1, g2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {

        TimeSeriesURLGenerator g1 = new TimeSeriesURLGenerator();

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(g1);
            out.close();

            ObjectInput in = new ObjectInputStream(new ByteArrayInputStream(
                    buffer.toByteArray()));
        TimeSeriesURLGenerator g2 = (TimeSeriesURLGenerator) in.readObject();
            in.close();

        assertEquals(g1, g2);

    }

    /**
     * Checks that the class does not implement PublicCloneable (the generator
     * is immutable).
     */
    @Test
    public void testPublicCloneable() {
        TimeSeriesURLGenerator g1 = new TimeSeriesURLGenerator();
        assertFalse(g1 instanceof PublicCloneable);
    }

}
