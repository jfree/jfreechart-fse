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
 * 11-Jan-2016 : Copied from JCommon (DG);
 *
 */

package org.jfree.chart.util;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Paint;

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
        Paint p1 = Color.red;
        Paint p2 = Color.blue;
        Paint p3 = new Color(1, 2, 3, 4);
        Paint p4 = new Color(1, 2, 3, 4);
        Paint p5 = new GradientPaint(
            1.0f, 2.0f, Color.red, 3.0f, 4.0f, Color.yellow
        );
        Paint p6 = new GradientPaint(
            1.0f, 2.0f, Color.red, 3.0f, 4.0f, Color.yellow
        );
        Paint p7 = new GradientPaint(
            1.0f, 2.0f, Color.red, 3.0f, 4.0f, Color.blue
        );
        assertTrue(PaintUtils.equal(null, null));
        assertFalse(PaintUtils.equal(p1, null));
        assertFalse(PaintUtils.equal(null, p1));
        assertFalse(PaintUtils.equal(p1, p2));
        assertTrue(PaintUtils.equal(p3, p3));
        assertTrue(PaintUtils.equal(p3, p4));
        assertTrue(PaintUtils.equal(p5, p6));
        assertFalse(PaintUtils.equal(p5, p7));
    }

}
