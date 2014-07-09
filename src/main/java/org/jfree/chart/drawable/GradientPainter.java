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
 * --------------------
 * GradientPainter.java
 * --------------------
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
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import org.jfree.chart.ui.RectangleAnchor;
import org.jfree.chart.util.ObjectUtils;
import org.jfree.chart.util.ParamChecks;

/**
 * A {@link Drawable} that can fill a rectangle with a gradient (the gradient 
 * is generated using anchor points to fit any size rectangle on demand).  
 * Instances of this class are immutable.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 * <br><br>
 * This class is based on code from the Orson Charts project 
 * (http://www.object-refinery.com/orsoncharts).
 */
@SuppressWarnings("serial")
public final class GradientPainter implements Drawable, Serializable {

    /** The first color for the gradient. */
    private final Color color1;
    
    /** The anchor point used to find the starting point for the gradient. */
    private final RectangleAnchor anchor1;
    
    /** The first color for the gradient. */
    private final Color color2;
    
    /** The anchor point used to find the ending point for the gradient. */
    private final RectangleAnchor anchor2;
    
    /**
     * Creates a new instance.
     * 
     * @param color1  the first color for the gradient (<code>null</code> not 
     *     permitted).
     * @param anchor1  the anchor point used to determine the starting point 
     *     for the gradient (<code>null</code> not permitted).
     * @param color2  the second color for the gradient (<code>null</code> not
     *     permitted).
     * @param anchor2  the anchor point used to determine the ending point for
     *     the gradient (<code>null</code> not permitted).
     */
    public GradientPainter(Color color1, RectangleAnchor anchor1, 
            Color color2, RectangleAnchor anchor2) {
        ParamChecks.nullNotPermitted(color1, "color1");
        ParamChecks.nullNotPermitted(anchor1, "anchor1");
        ParamChecks.nullNotPermitted(color2, "color2");
        ParamChecks.nullNotPermitted(anchor2, "anchor2");
        this.color1 = color1;
        this.anchor1 = anchor1;
        this.color2 = color2;
        this.anchor2 = anchor2;
    }
    
    /**
     * Returns the first color for the gradient (as specified via the 
     * constructor).  There is no setter method because instances of this class
     * are immutable.
     * 
     * @return The first color for the gradient (never <code>null</code>). 
     */
    public Color getColor1() {
        return this.color1;
    }
    
    /**
     * Returns the anchor point used to find the starting point for the 
     * gradient (as specified via the constructor).  There is no setter method 
     * because instances of this class are immutable.
     * 
     * @return The anchor point (never <code>null</code>). 
     */
    public RectangleAnchor getAnchor1() {
        return this.anchor1; 
    }
    
    /**
     * Returns the second color for the gradient (as specified via the 
     * constructor).  There is no setter method because instances of this class
     * are immutable.
     * 
     * @return The second color for the gradient (never <code>null</code>). 
     */
    public Color getColor2() {
        return this.color2;
    }
    
    /**
     * Returns the anchor point used to find the ending point for the 
     * gradient (as specified via the constructor).  There is no setter method 
     * because instances of this class are immutable.
     * 
     * @return The anchor point (never <code>null</code>). 
     */
    public RectangleAnchor getAnchor2() {
        return this.anchor2; 
    }
    
    /**
     * Returns a <code>GradientPaint</code> instance with coordinates based 
     * on the painter's anchor points and the supplied rectangle.
     * 
     * @param area  the area (<code>null</code> not permitted).
     * 
     * @return A gradient paint (never <code>null</code>). 
     */
    private GradientPaint createTransformedGradient(Rectangle2D area) {
        // defer arg check
        Point2D pt1 = this.anchor1.getAnchorPoint(area);
        Point2D pt2 = this.anchor2.getAnchorPoint(area);
        return new GradientPaint(pt1, this.color1, pt2, this.color2);
    }
    
    /**
     * Fills the specified <code>area</code> with a gradient paint created
     * using the colors and anchor points of this painter.
     * 
     * @param g2  the graphics target (<code>null</code> not permitted).
     * @param area  the area to fill (<code>null</code> not permitted).
     */
    @Override
    public void draw(Graphics2D g2, Rectangle2D area) {
        Paint saved = g2.getPaint();
        g2.setPaint(createTransformedGradient(area));
        g2.fill(area);
        g2.setPaint(saved);
    }
    
    /**
     * Tests this instance for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> not permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof GradientPainter)) {
            return false;
        }
        GradientPainter that = (GradientPainter) obj;
        if (!this.color1.equals(that.color1)) {
            return false;
        }
        if (!this.anchor1.equals(that.anchor1)) {
            return false;
        }
        if (!this.color2.equals(that.color2)) {
            return false;
        }
        if (!this.anchor2.equals(that.anchor2)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + ObjectUtils.hashCode(this.color1);
        hash = 67 * hash + ObjectUtils.hashCode(this.anchor1);
        hash = 67 * hash + ObjectUtils.hashCode(this.color2);
        hash = 67 * hash + ObjectUtils.hashCode(this.anchor2);
        return hash;
    }
    
}
