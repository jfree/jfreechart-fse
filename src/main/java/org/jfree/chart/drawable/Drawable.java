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
 * -------------
 * Drawable.java
 * -------------
 * (C) Copyright 2002-2014, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes (from 30-May-2002)
 * --------------------------
 * 25-Jun-2002 : Version 1 (DG);
 * 14-Jun-2012 : Moved from JCommon to JFreeChart (DG);
 * 
 */

package org.jfree.chart.drawable;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * An interface for an object that can draw itself within an arbitrary 
 * rectangle using Java2D's <code>Graphics2D</code> API.
 */
public interface Drawable {

    /**
     * Draws the object.
     *
     * @param g2  the graphics device (<code>null</code> not permitted).
     * @param area  the drawing bounds (<code>null</code> not permitted).
     */
    public void draw(Graphics2D g2, Rectangle2D area);

}
