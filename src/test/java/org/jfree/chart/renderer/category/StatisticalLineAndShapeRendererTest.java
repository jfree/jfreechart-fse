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
 * -----------------------------------------
 * StatisticalLineAndShapeRendererTests.java
 * -----------------------------------------
 * (C) Copyright 2005-2009, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 15-Jun-2005 : Version 1 (DG);
 * 25-Sep-2006 : Added test1562759() (DG);
 * 23-Apr-2008 : Added testPublicCloneable() (DG);
 * 16-May-2009 : Added testFindRangeBounds() (DG);
 *
 */

package org.jfree.chart.renderer.category;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.util.PublicCloneable;
import org.jfree.data.Range;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;
import org.junit.Test;

import java.awt.Color;
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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the {@link StatisticalLineAndShapeRenderer} class.
 */
public class StatisticalLineAndShapeRendererTest  {





    /**
     * Check that the equals() method distinguishes all fields.
     */
    @Test
    public void testEquals() {
        StatisticalLineAndShapeRenderer r1
            = new StatisticalLineAndShapeRenderer();
        StatisticalLineAndShapeRenderer r2
            = new StatisticalLineAndShapeRenderer();
        assertEquals(r1, r2);
        assertEquals(r2, r1);

        r1.setErrorIndicatorPaint(Color.RED);
        assertFalse(r1.equals(r2));
        r2.setErrorIndicatorPaint(Color.RED);
        assertEquals(r2, r1);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashcode() {
        StatisticalLineAndShapeRenderer r1
            = new StatisticalLineAndShapeRenderer();
        StatisticalLineAndShapeRenderer r2
            = new StatisticalLineAndShapeRenderer();
        assertEquals(r1, r2);
        int h1 = r1.hashCode();
        int h2 = r2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        StatisticalLineAndShapeRenderer r1
                = new StatisticalLineAndShapeRenderer();
        StatisticalLineAndShapeRenderer r2 = (StatisticalLineAndShapeRenderer) r1.clone();
        assertNotSame(r1, r2);
        assertSame(r1.getClass(), r2.getClass());
        assertEquals(r1, r2);
    }

    /**
     * Check that this class implements PublicCloneable.
     */
    @Test
    public void testPublicCloneable() {
        StatisticalLineAndShapeRenderer r1
                = new StatisticalLineAndShapeRenderer();
        assertTrue(r1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {

        StatisticalLineAndShapeRenderer r1
            = new StatisticalLineAndShapeRenderer();

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(r1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
        StatisticalLineAndShapeRenderer r2 = (StatisticalLineAndShapeRenderer) in.readObject();
            in.close();

        assertEquals(r1, r2);

    }

    /**
     * Draws the chart with a <code>null</code> info object to make sure that
     * no exceptions are thrown (particularly by code in the renderer).
     */
    @Test
    public void testDrawWithNullInfo() {
            DefaultStatisticalCategoryDataset dataset
                = new DefaultStatisticalCategoryDataset();
            dataset.add(1.0, 2.0, "S1", "C1");
            dataset.add(3.0, 4.0, "S1", "C2");
            CategoryPlot plot = new CategoryPlot(dataset,
                    new CategoryAxis("Category"), new NumberAxis("Value"),
                    new StatisticalLineAndShapeRenderer());
            JFreeChart chart = new JFreeChart(plot);
            /* BufferedImage image = */ chart.createBufferedImage(300, 200,
                    null);
    }

    /**
     * A simple test for bug report 1562759.
     */
    @Test
    public void test1562759() {
        StatisticalLineAndShapeRenderer r
            = new StatisticalLineAndShapeRenderer(true, false);
        assertTrue(r.getBaseLinesVisible());
        assertFalse(r.getBaseShapesVisible());

        r = new StatisticalLineAndShapeRenderer(false, true);
        assertFalse(r.getBaseLinesVisible());
        assertTrue(r.getBaseShapesVisible());
    }

    /**
     * Some checks for the findRangeBounds() method.
     */
    @Test
    public void testFindRangeBounds() {
        StatisticalLineAndShapeRenderer r
                = new StatisticalLineAndShapeRenderer();
        assertNull(r.findRangeBounds(null));

        // an empty dataset should return a null range
        DefaultStatisticalCategoryDataset dataset
                = new DefaultStatisticalCategoryDataset();
        assertNull(r.findRangeBounds(dataset));

        dataset.add(1.0, 0.5, "R1", "C1");
        assertEquals(new Range(0.5, 1.5), r.findRangeBounds(dataset));

        dataset.add(-2.0, 0.2, "R1", "C2");
        assertEquals(new Range(-2.2, 1.5), r.findRangeBounds(dataset));

        dataset.add(null, null, "R1", "C3");
        assertEquals(new Range(-2.2, 1.5), r.findRangeBounds(dataset));

        dataset.add(5.0, 1.0, "R2", "C3");
        assertEquals(new Range(-2.2, 6.0), r.findRangeBounds(dataset));

        // check that the series visible flag is observed
        r.setSeriesVisible(1, Boolean.FALSE);
        assertEquals(new Range(-2.2, 1.5), r.findRangeBounds(dataset));
    }

}
