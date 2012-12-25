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
 * --------------
 * AxisTests.java
 * --------------
 * (C) Copyright 2003-2012, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 13-Aug-2003 : Version 1 (DG);
 * 06-Jan-2004 : Added tests for axis line attributes (DG);
 * 07-Jan-2005 : Added hashCode() test (DG);
 * 25-Sep-2008 : Extended equals() to cover new fields (DG);
 * 17-Jun-2012 : Removed JCommon dependencies (DG);
 *
 */

package org.jfree.chart.axis;

import org.jfree.chart.ui.RectangleInsets;
import org.junit.Test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

/**
 * Tests for the {@link Axis} class.
 */
public class AxisTest  {





    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        CategoryAxis a1 = new CategoryAxis("Test");
        a1.setAxisLinePaint(Color.RED);
        CategoryAxis a2 = (CategoryAxis) a1.clone();
        assertNotSame(a1, a2);
        assertSame(a1.getClass(), a2.getClass());
        assertEquals(a1, a2);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {

        Axis a1 = new CategoryAxis("Test");
        Axis a2 = new CategoryAxis("Test");
        assertEquals(a1, a2);

        // visible flag...
        a1.setVisible(false);
        assertFalse(a1.equals(a2));
        a2.setVisible(false);
        assertEquals(a1, a2);

        // label...
        a1.setLabel("New Label");
        assertFalse(a1.equals(a2));
        a2.setLabel("New Label");
        assertEquals(a1, a2);

        // label font...
        a1.setLabelFont(new Font("Dialog", Font.PLAIN, 8));
        assertFalse(a1.equals(a2));
        a2.setLabelFont(new Font("Dialog", Font.PLAIN, 8));
        assertEquals(a1, a2);

        // label paint...
        a1.setLabelPaint(new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.BLACK));
        assertFalse(a1.equals(a2));
        a2.setLabelPaint(new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.BLACK));
        assertEquals(a1, a2);

        // label insets...
        a1.setLabelInsets(new RectangleInsets(10.0, 10.0, 10.0, 10.0));
        assertFalse(a1.equals(a2));
        a2.setLabelInsets(new RectangleInsets(10.0, 10.0, 10.0, 10.0));
        assertEquals(a1, a2);

        // label angle...
        a1.setLabelAngle(1.23);
        assertFalse(a1.equals(a2));
        a2.setLabelAngle(1.23);
        assertEquals(a1, a2);

        // axis line visible...
        a1.setAxisLineVisible(false);
        assertFalse(a1.equals(a2));
        a2.setAxisLineVisible(false);
        assertEquals(a1, a2);

        // axis line stroke...
        BasicStroke s = new BasicStroke(1.1f);
        a1.setAxisLineStroke(s);
        assertFalse(a1.equals(a2));
        a2.setAxisLineStroke(s);
        assertEquals(a1, a2);

        // axis line paint...
        a1.setAxisLinePaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLACK));
        assertFalse(a1.equals(a2));
        a2.setAxisLinePaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLACK));
        assertEquals(a1, a2);

        // tick labels visible flag...
        a1.setTickLabelsVisible(false);
        assertFalse(a1.equals(a2));
        a2.setTickLabelsVisible(false);
        assertEquals(a1, a2);

        // tick label font...
        a1.setTickLabelFont(new Font("Dialog", Font.PLAIN, 12));
        assertFalse(a1.equals(a2));
        a2.setTickLabelFont(new Font("Dialog", Font.PLAIN, 12));
        assertEquals(a1, a2);

        // tick label paint...
        a1.setTickLabelPaint(new GradientPaint(1.0f, 2.0f, Color.yellow,
                3.0f, 4.0f, Color.BLACK));
        assertFalse(a1.equals(a2));
        a2.setTickLabelPaint(new GradientPaint(1.0f, 2.0f, Color.yellow,
                3.0f, 4.0f, Color.BLACK));
        assertEquals(a1, a2);

        // tick label insets...
        a1.setTickLabelInsets(new RectangleInsets(10.0, 10.0, 10.0, 10.0));
        assertFalse(a1.equals(a2));
        a2.setTickLabelInsets(new RectangleInsets(10.0, 10.0, 10.0, 10.0));
        assertEquals(a1, a2);

        // tick marks visible flag...
        a1.setTickMarksVisible(false);
        assertFalse(a1.equals(a2));
        a2.setTickMarksVisible(false);
        assertEquals(a1, a2);

        // tick mark inside length...
        a1.setTickMarkInsideLength(1.23f);
        assertFalse(a1.equals(a2));
        a2.setTickMarkInsideLength(1.23f);
        assertEquals(a1, a2);

        // tick mark outside length...
        a1.setTickMarkOutsideLength(1.23f);
        assertFalse(a1.equals(a2));
        a2.setTickMarkOutsideLength(1.23f);
        assertEquals(a1, a2);

        // tick mark stroke...
        a1.setTickMarkStroke(new BasicStroke(2.0f));
        assertFalse(a1.equals(a2));
        a2.setTickMarkStroke(new BasicStroke(2.0f));
        assertEquals(a1, a2);

        // tick mark paint...
        a1.setTickMarkPaint(new GradientPaint(1.0f, 2.0f, Color.cyan,
                3.0f, 4.0f, Color.BLACK));
        assertFalse(a1.equals(a2));
        a2.setTickMarkPaint(new GradientPaint(1.0f, 2.0f, Color.cyan,
                3.0f, 4.0f, Color.BLACK));
        assertEquals(a1, a2);

        // tick mark outside length...
        a1.setFixedDimension(3.21f);
        assertFalse(a1.equals(a2));
        a2.setFixedDimension(3.21f);
        assertEquals(a1, a2);

        a1.setMinorTickMarksVisible(true);
        assertFalse(a1.equals(a2));
        a2.setMinorTickMarksVisible(true);
        assertEquals(a1, a2);

        a1.setMinorTickMarkInsideLength(1.23f);
        assertFalse(a1.equals(a2));
        a2.setMinorTickMarkInsideLength(1.23f);
        assertEquals(a1, a2);

        a1.setMinorTickMarkOutsideLength(3.21f);
        assertFalse(a1.equals(a2));
        a2.setMinorTickMarkOutsideLength(3.21f);
        assertEquals(a1, a2);

    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        Axis a1 = new CategoryAxis("Test");
        Axis a2 = new CategoryAxis("Test");
        assertEquals(a1, a2);
        int h1 = a1.hashCode();
        int h2 = a2.hashCode();
        assertEquals(h1, h2);
    }

}
