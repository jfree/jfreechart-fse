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
 * ----------------------
 * BorderPainterTest.java
 * ----------------------
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

import java.awt.BasicStroke;
import java.awt.Color;
import org.jfree.chart.TestUtils;
import org.junit.Test;

/**
 * Tests for the {@link BorderPainter} class.
 */
public class BorderPainterTest {
    
    @Test
    public void testEquals() {
        BorderPainter p1 = new BorderPainter(Color.RED, new BasicStroke(1.0f), 
                1.0, 2.0);
        BorderPainter p2 = new BorderPainter(Color.RED, new BasicStroke(1.0f), 
                1.0, 2.0);
        assertEquals(p1, p2);
        
        p1 = new BorderPainter(Color.BLUE, new BasicStroke(1.0f), 1.0, 2.0); 
        assertNotEquals(p1, p2);
        p2 = new BorderPainter(Color.BLUE, new BasicStroke(1.0f), 1.0, 2.0); 
        assertEquals(p1, p2);

        p1 = new BorderPainter(Color.BLUE, new BasicStroke(2.0f), 1.0, 2.0); 
        assertNotEquals(p1, p2);
        p2 = new BorderPainter(Color.BLUE, new BasicStroke(2.0f), 1.0, 2.0); 
        assertEquals(p1, p2);

        p1 = new BorderPainter(Color.BLUE, new BasicStroke(2.0f), 3.0, 2.0); 
        assertNotEquals(p1, p2);
        p2 = new BorderPainter(Color.BLUE, new BasicStroke(2.0f), 3.0, 2.0); 
        assertEquals(p1, p2);

        p1 = new BorderPainter(Color.BLUE, new BasicStroke(2.0f), 3.0, 4.0); 
        assertNotEquals(p1, p2);
        p2 = new BorderPainter(Color.BLUE, new BasicStroke(2.0f), 3.0, 4.0); 
        assertEquals(p1, p2);
    }
    
    @Test
    public void testSerialization() {
        BorderPainter p1 = new BorderPainter(Color.RED, new BasicStroke(1.0f), 
                1.0, 2.0);
        BorderPainter p2 = TestUtils.serialised(p1);
        assertEquals(p1, p2);
    }
}
