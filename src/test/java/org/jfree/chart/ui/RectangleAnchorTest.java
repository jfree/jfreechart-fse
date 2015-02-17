/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2015, by Object Refinery Limited and Contributors.
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
 * -------------------------
 * RectangleAnchorTests.java
 * -------------------------
 * (C) Copyright 2015, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 17-Feb-2015 : Version 1 (DG);
 *
 */

package org.jfree.chart.ui;

import java.awt.geom.Rectangle2D;
import static junit.framework.Assert.assertEquals;
import org.junit.Test;

/**
 * Some tests for the {@link RectangleAnchor} enum.
 */
public class RectangleAnchorTest {
 
    @Test
    public void testCreateRectangle() {
        Size2D s = new Size2D(3.0, 8.0);
        assertEquals(new Rectangle2D.Double(-0.5, -2.0, 3.0, 8.0), 
                RectangleAnchor.createRectangle(s, 1.0, 2.0, 
                RectangleAnchor.CENTER));
        assertEquals(new Rectangle2D.Double(1.0, -2.0, 3.0, 8.0), 
                RectangleAnchor.createRectangle(s, 1.0, 2.0, 
                RectangleAnchor.LEFT));
        assertEquals(new Rectangle2D.Double(-2.0, -2.0, 3.0, 8.0), 
                RectangleAnchor.createRectangle(s, 1.0, 2.0, 
                RectangleAnchor.RIGHT));
        assertEquals(new Rectangle2D.Double(-0.5, 2.0, 3.0, 8.0), 
                RectangleAnchor.createRectangle(s, 1.0, 2.0, 
                RectangleAnchor.TOP));
        assertEquals(new Rectangle2D.Double(1.0, 2.0, 3.0, 8.0), 
                RectangleAnchor.createRectangle(s, 1.0, 2.0, 
                RectangleAnchor.TOP_LEFT));
        assertEquals(new Rectangle2D.Double(-2.0, 2.0, 3.0, 8.0), 
                RectangleAnchor.createRectangle(s, 1.0, 2.0, 
                RectangleAnchor.TOP_RIGHT));
        assertEquals(new Rectangle2D.Double(-0.5, -6.0, 3.0, 8.0), 
                RectangleAnchor.createRectangle(s, 1.0, 2.0, 
                RectangleAnchor.BOTTOM));
        assertEquals(new Rectangle2D.Double(1.0, -6.0, 3.0, 8.0), 
                RectangleAnchor.createRectangle(s, 1.0, 2.0, 
                RectangleAnchor.BOTTOM_LEFT));
        assertEquals(new Rectangle2D.Double(-2.0, -6.0, 3.0, 8.0), 
                RectangleAnchor.createRectangle(s, 1.0, 2.0, 
                RectangleAnchor.BOTTOM_RIGHT));
    }
    
}
