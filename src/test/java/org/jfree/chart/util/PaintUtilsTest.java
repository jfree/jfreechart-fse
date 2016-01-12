/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2016, by Object Refinery Limited and Contributors.
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
 * PaintUtilsTest.java
 * -------------------
 * (C) Copyright 2008-2016, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 08-Feb-2008 : Version 1 (DG);
 * 14-Jan-2009 : Updated testEquals() for new field (DG);
 * 11-Jan-2016 : Copied from JCommon plus new tests (DG);
 *
 */

package org.jfree.chart.util;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Some tests for the {@link PaintUtils} class.
 */
public class PaintUtilsTest {

    /**
     * Some checks for the equal(Paint, Paint) method.
     */
    @Test
    public void testEqual() {
        Paint p1 = Color.RED;
        Paint p2 = Color.BLUE;
        Paint p3 = new Color(1, 2, 3, 4);
        Paint p4 = new Color(1, 2, 3, 4);
        Paint p5 = new GradientPaint(1.0f, 2.0f, Color.RED, 3.0f, 4.0f, 
                Color.YELLOW);
        Paint p6 = new GradientPaint(1.0f, 2.0f, Color.RED, 3.0f, 4.0f, 
                Color.YELLOW);
        Paint p7 = new GradientPaint(1.0f, 2.0f, Color.RED, 3.0f, 4.0f, 
                Color.BLUE);
        assertTrue(PaintUtils.equal(null, null));
        assertFalse(PaintUtils.equal(p1, null));
        assertFalse(PaintUtils.equal(null, p1));
        assertFalse(PaintUtils.equal(p1, p2));
        assertTrue(PaintUtils.equal(p3, p3));
        assertTrue(PaintUtils.equal(p3, p4));
        assertTrue(PaintUtils.equal(p5, p6));
        assertFalse(PaintUtils.equal(p5, p7));
    }

    @Test
    public void testLinearGradientPaint() {
        Point2D start1 = new Point2D.Float(0, 0);
        Point2D end1 = new Point2D.Float(50, 50);
        float[] dist1 = {0.0f, 0.2f, 1.0f};
        Color[] colors1 = {Color.RED, Color.WHITE, Color.BLUE};
        LinearGradientPaint p1 = new LinearGradientPaint(start1, end1, dist1, 
                colors1);    

        Point2D start2 = new Point2D.Float(0, 0);
        Point2D end2 = new Point2D.Float(50, 50);
        float[] dist2 = {0.0f, 0.2f, 1.0f};
        Color[] colors2 = {Color.RED, Color.WHITE, Color.BLUE};
        LinearGradientPaint p2 = new LinearGradientPaint(start2, end2, dist2, 
                colors2);
        assertTrue(PaintUtils.equal(p1, p2));
        assertFalse(PaintUtils.equal(p1, Color.RED));
        assertFalse(PaintUtils.equal(p1, null));
        assertFalse(PaintUtils.equal(null, p1));
    }
    
    @Test
    public void testRadialGradientPaint() {
        RadialGradientPaint p1 = new RadialGradientPaint(1.0f, 2.0f, 3.0f, 
                new float[] {0.0f, 0.4f, 1.0f}, 
                new Color[] {Color.RED, Color.GREEN, Color.BLUE});
        RadialGradientPaint p2 = new RadialGradientPaint(1.0f, 2.0f, 3.0f, 
                new float[] {0.0f, 0.4f, 1.0f}, 
                new Color[] {Color.RED, Color.GREEN, Color.BLUE});
        assertTrue(PaintUtils.equal(p1, p2));
        assertFalse(PaintUtils.equal(p1, Color.RED));
        assertFalse(PaintUtils.equal(p1, null));
        assertFalse(PaintUtils.equal(null, p1));

        p1 = new RadialGradientPaint(1.1f, 2.0f, 3.0f, 
                new float[] {0.0f, 0.4f, 1.0f}, 
                new Color[] {Color.RED, Color.GREEN, Color.BLUE});
        assertFalse(PaintUtils.equal(p1, p2));
        p2 = new RadialGradientPaint(1.1f, 2.0f, 3.0f, 
                new float[] {0.0f, 0.4f, 1.0f}, 
                new Color[] {Color.RED, Color.GREEN, Color.BLUE});
        assertTrue(PaintUtils.equal(p1, p2));

        p1 = new RadialGradientPaint(1.1f, 2.2f, 3.0f, 
                new float[] {0.0f, 0.4f, 1.0f}, 
                new Color[] {Color.RED, Color.GREEN, Color.BLUE});
        assertFalse(PaintUtils.equal(p1, p2));
        p2 = new RadialGradientPaint(1.1f, 2.2f, 3.0f, 
                new float[] {0.0f, 0.4f, 1.0f}, 
                new Color[] {Color.RED, Color.GREEN, Color.BLUE});
        assertTrue(PaintUtils.equal(p1, p2));

        p1 = new RadialGradientPaint(1.1f, 2.2f, 3.3f, 
                new float[] {0.0f, 0.4f, 1.0f}, 
                new Color[] {Color.RED, Color.GREEN, Color.BLUE});
        assertFalse(PaintUtils.equal(p1, p2));
        p2 = new RadialGradientPaint(1.1f, 2.2f, 3.3f, 
                new float[] {0.0f, 0.4f, 1.0f}, 
                new Color[] {Color.RED, Color.GREEN, Color.BLUE});
        assertTrue(PaintUtils.equal(p1, p2));

        p1 = new RadialGradientPaint(1.1f, 2.2f, 3.3f, 
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.GREEN, Color.BLUE});
        assertFalse(PaintUtils.equal(p1, p2));
        p2 = new RadialGradientPaint(1.1f, 2.2f, 3.3f, 
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.GREEN, Color.BLUE});
        assertTrue(PaintUtils.equal(p1, p2));
    
        p1 = new RadialGradientPaint(1.1f, 2.2f, 3.3f, 
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.YELLOW, Color.BLUE});
        assertFalse(PaintUtils.equal(p1, p2));
        p2 = new RadialGradientPaint(1.1f, 2.2f, 3.3f, 
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.YELLOW, Color.BLUE});
        assertTrue(PaintUtils.equal(p1, p2));
        
        p1 = new RadialGradientPaint(1.1f, 2.2f, 3.3f,
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.YELLOW, Color.BLUE}, 
                MultipleGradientPaint.CycleMethod.REPEAT);
        assertFalse(PaintUtils.equal(p1, p2));
        p2 = new RadialGradientPaint(1.1f, 2.2f, 3.3f,
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.YELLOW, Color.BLUE},
                MultipleGradientPaint.CycleMethod.REPEAT);
        assertTrue(PaintUtils.equal(p1, p2));
        
        p1 = new RadialGradientPaint(1.1f, 2.2f, 3.3f, 4.4f, 5.5f,
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.YELLOW, Color.BLUE}, 
                MultipleGradientPaint.CycleMethod.REPEAT);
        assertFalse(PaintUtils.equal(p1, p2));
        p2 = new RadialGradientPaint(1.1f, 2.2f, 3.3f, 4.4f, 5.5f,
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.YELLOW, Color.BLUE},
                MultipleGradientPaint.CycleMethod.REPEAT);
        assertTrue(PaintUtils.equal(p1, p2));
    }
}
