/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2014, by Object Refinery Limited and Contributors.
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
 * ---------------------
 * ColorPainterTest.java
 * ---------------------
 * (C) Copyright 2014, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 25-Apr-2014 : Version 1 (DG);
 *
 */

package org.jfree.chart.drawable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.awt.Color;
import org.jfree.chart.TestUtils;
import org.junit.Test;

/**
 * Tests for the {@link ColorPainter} class.
 */
public class ColorPainterTest {
    
    @Test
    public void testEquals() {
        ColorPainter p1 = new ColorPainter(Color.RED);
        ColorPainter p2 = new ColorPainter(Color.RED);
        assertEquals(p1, p2);
        
        p1 = new ColorPainter(Color.BLUE);
        assertNotEquals(p1, p2);
        p2 = new ColorPainter(Color.BLUE); 
        assertEquals(p1, p2);
    }
    
    @Test
    public void testSerialization() {
        ColorPainter p1 = new ColorPainter(Color.RED);
        ColorPainter p2 = TestUtils.serialised(p1);
        assertEquals(p1, p2);
    }
}
