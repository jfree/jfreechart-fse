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
 * ------------------
 * BorderPainter.java
 * ------------------
 * (C) Copyright 2014, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 25-Apr-2014 : Version 1 (DG);
 */

package org.jfree.chart.drawable;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import org.jfree.chart.util.SerialUtilities;

/**
 * An object that will draw a simple border around an area (with support for
 * rounded corners).  Instances of this class are immutable.
 */
public class BorderPainter implements Drawable, Serializable {

    private Color color;
    
    private transient Stroke stroke;
    
    private double arcHeight;
    
    private double arcWidth;
    
    /**
     * Creates a new instance.
     */
    public BorderPainter() {
        this(Color.BLACK, new BasicStroke(1.0f));
    }

    /**
     * Creates a new instance.
     * 
     * @param color  the color (<code>null</code> not permitted).
     * @param stroke  the stroke (<code>null</code> not permitted).
     */
    public BorderPainter(Color color, Stroke stroke) {
        this(color, stroke, 0.0, 0.0);
    }
    
    /**
     * Creates a new instance.
     * 
     * @param color  the color (<code>null</code> not permitted).
     * @param stroke  the stroke (<code>null</code> not permitted).
     * @param arcH  the arc height (for rounded corners).
     * @param arcW  the arc width (for rounded corners).
     */
    public BorderPainter(Color color, Stroke stroke, double arcH, double arcW) {
        this.color = color;
        this.stroke = stroke;
        this.arcHeight = arcH;
        this.arcWidth = arcW;        
    }
    
    /**
     * Returns the color used to draw the border.  The default value is
     * <code>BLACK</code>.
     * 
     * @return The color (never <code>null</code>). 
     */
    public Color getColor() {
        return color;
    }

    /**
     * Returns the stroke used to draw the border.  The default value is
     * <code>BasicStroke(1.0f)</code>.
     * 
     * @return The stroke (never <code>null</code>). 
     */
    public Stroke getStroke() {
        return stroke;
    }

    /**
     * Returns the arc height for rounded corners.  If this is 0.0, the
     * border will have square corners.  The default value is 0.0.
     * 
     * @return The arc height. 
     */
    public double getArcHeight() {
        return arcHeight;
    }

    /**
     * Returns the arc width for rounded corners.  If this is 0.0, the
     * border will have square corners.  The default value is 0.0.
     * 
     * @return The arc height. 
     */
    public double getArcWidth() {
        return arcWidth;
    }
    
    /**
     * Draws the border for the specified <code>area</code>.
     * 
     * @param g2  the graphics target (<code>null</code> not permitted).
     * @param area  the area for the border (<code>null</code> not permitted).
     */
    @Override
    public void draw(Graphics2D g2, Rectangle2D area) {
        RectangularShape border;
        if (this.arcHeight > 0.0 && this.arcWidth > 0.0) {
            border = new RoundRectangle2D.Double(0, 0, 0, 0, 
                    this.arcWidth, this.arcHeight);
        } else {
            border = new Rectangle2D.Double();
        }
        border.setFrame(area.getX(), area.getY(), area.getWidth() - 1.0, 
                area.getHeight() - 1.0);
        g2.setColor(this.color);
        g2.setStroke(this.stroke);
        g2.draw(border);
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
        if (!(obj instanceof BorderPainter)) {
            return false;
        }
        BorderPainter that = (BorderPainter) obj;
        if (!this.color.equals(that.color)) {
            return false;
        }
        if (!this.stroke.equals(that.stroke)) {
            return false;
        }
        if (this.arcHeight != that.arcHeight) {
            return false;
        }
        if (this.arcWidth != that.arcWidth) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.color);
        hash = 83 * hash + Objects.hashCode(this.stroke);
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.arcHeight) 
                ^ (Double.doubleToLongBits(this.arcHeight) >>> 32));
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.arcWidth) 
                ^ (Double.doubleToLongBits(this.arcWidth) >>> 32));
        return hash;
    }
    
    /**
     * Provides serialization support.
     *
     * @param stream  the output stream.
     *
     * @throws IOException  if there is an I/O error.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        SerialUtilities.writeStroke(this.stroke, stream);
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the input stream.
     *
     * @throws IOException  if there is an I/O error.
     * @throws ClassNotFoundException  if there is a classpath problem.
     */
    private void readObject(ObjectInputStream stream)
        throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.stroke = SerialUtilities.readStroke(stream);
    }
}
