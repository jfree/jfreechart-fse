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
 * -----------------
 * ColorPainter.java
 * -----------------
 * (C) Copyright 2014, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 25-Apr-2014 : Version 1, based on code from Orson Charts (DG);
 *
 */

package org.jfree.chart.drawable;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import javax.swing.UIManager;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.util.ObjectUtils;
import org.jfree.chart.util.ParamChecks;

/**
 * A painter that fills a rectangle with a solid color.  This is used for 
 * standard background painting by the {@link JFreeChart} class.
 */
public class ColorPainter implements Drawable, Serializable {

    /** The fill color. */
    private Color color;
    
    /**
     * Creates a new instance using the background color for a panel under
     * the currently installed Swing Look &amp; Feel.
     */
    public ColorPainter() {
        this(UIManager.getColor("Panel.background"));
    }
    
    /**
     * Creates a new painter with the specified color.
     * 
     * @param color  the color (<code>null</code> not permitted). 
     */
    public ColorPainter(Color color) {
        ParamChecks.nullNotPermitted(color, "color");
        this.color = color;
    }

    /**
     * Returns the color used by the painter to fill rectangles.
     * 
     * @return The color (never <code>null</code>). 
     */
    public Color getColor() {
        return this.color;
    }
    
    /**
     * Fills the specified <code>area</code> with the color that was specified
     * in the constructor.
     * 
     * @param g2  the graphics target (<code>null</code> not permitted).
     * @param area  the area (<code>null</code> not permitted).
     */
    @Override
    public void draw(Graphics2D g2, Rectangle2D area) {
        ParamChecks.nullNotPermitted(g2, "g2");
        ParamChecks.nullNotPermitted(area, "area");
        g2.setColor(this.color);
        g2.fill(area);
    }

    /**
     * Tests this instance for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ColorPainter)) {
            return true;
        }
        ColorPainter that = (ColorPainter) obj;
        if (!this.color.equals(that.color)) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + ObjectUtils.hashCode(this.color);
        return hash;
    }
    

}
