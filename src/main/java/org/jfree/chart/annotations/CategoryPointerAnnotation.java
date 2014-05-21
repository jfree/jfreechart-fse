/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2013, by Object Refinery Limited and Contributors.
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
 * ------------------------------
 * CategoryPointerAnnotation.java
 * ------------------------------
 * (C) Copyright 2006-2012, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Peter Kolb (patch 2809117);
 *
 * Changes:
 * --------
 * 02-Oct-2006 : Version 1 (DG);
 * 06-Mar-2007 : Implemented hashCode() (DG);
 * 24-Jun-2009 : Fire change events (see patch 2809117 by PK) (DG);
 * 30-Mar-2010 : Correct calculation of pointer line (see patch 2954302) (DG);
 * 16-Jun-2012 : Removed JCommon dependencies (DG);
 *
 */

package org.jfree.chart.annotations;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.jfree.chart.util.HashUtils;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.util.ObjectUtils;
import org.jfree.chart.util.PublicCloneable;
import org.jfree.chart.event.AnnotationChangeEvent;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.text.TextUtilities;
import org.jfree.chart.util.ParamChecks;
import org.jfree.chart.util.SerialUtils;
import org.jfree.data.category.CategoryDataset;

/**
 * An arrow and label that can be placed on a {@link CategoryPlot}.  The arrow
 * is drawn at a user-definable angle so that it points towards the (category,
 * value) location for the annotation.
 * <p>
 * The arrow length (and its offset from the (category, value) location) is
 * controlled by the tip radius and the base radius attributes.  Imagine two
 * circles around the (category, value) coordinate: the inner circle defined by
 * the tip radius, and the outer circle defined by the base radius.  Now, draw
 * the arrow starting at some point on the outer circle (the point is
 * determined by the angle), with the arrow tip being drawn at a corresponding
 * point on the inner circle.
 *
 * @since 1.0.3
 */
public class CategoryPointerAnnotation extends CategoryTextAnnotation
        implements Cloneable, PublicCloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -4031161445009858551L;

    /** The default tip radius (in Java2D units). */
    public static final double DEFAULT_TIP_RADIUS = 10.0;

    /** The default base radius (in Java2D units). */
    public static final double DEFAULT_BASE_RADIUS = 30.0;

    /** The default label offset (in Java2D units). */
    public static final double DEFAULT_LABEL_OFFSET = 3.0;

    /** The default arrow length (in Java2D units). */
    public static final double DEFAULT_ARROW_LENGTH = 5.0;

    /** The default arrow width (in Java2D units). */
    public static final double DEFAULT_ARROW_WIDTH = 3.0;

    /** The angle of the arrow's line (in radians). */
    private double angle;

    /**
     * The radius from the (x, y) point to the tip of the arrow (in Java2D
     * units).
     */
    private double tipRadius;

    /**
     * The radius from the (x, y) point to the start of the arrow line (in
     * Java2D units).
     */
    private double baseRadius;

    /** The length of the arrow head (in Java2D units). */
    private double arrowLength;

    /** The arrow width (in Java2D units, per side). */
    private double arrowWidth;

    /** The arrow stroke. */
    private transient Stroke arrowStroke;

    /** The arrow paint. */
    private transient Paint arrowPaint;

    /** The radius from the base point to the anchor point for the label. */
    private double labelOffset;

    /**
     * Creates a new label and arrow annotation.
     *
     * @param label  the label (<code>null</code> permitted).
     * @param key  the category key.
     * @param value  the y-value (measured against the chart's range axis).
     * @param angle  the angle of the arrow's line (in radians).
     */
    public CategoryPointerAnnotation(String label, Comparable key, double value,
            double angle) {

        super(label, key, value);
        this.angle = angle;
        this.tipRadius = DEFAULT_TIP_RADIUS;
        this.baseRadius = DEFAULT_BASE_RADIUS;
        this.arrowLength = DEFAULT_ARROW_LENGTH;
        this.arrowWidth = DEFAULT_ARROW_WIDTH;
        this.labelOffset = DEFAULT_LABEL_OFFSET;
        this.arrowStroke = new BasicStroke(1.0f);
        this.arrowPaint = Color.BLACK;

    }

    /**
     * Returns the angle of the arrow.
     *
     * @return The angle (in radians).
     *
     * @see #setAngle(double)
     */
    public double getAngle() {
        return this.angle;
    }

    /**
     * Sets the angle of the arrow and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param angle  the angle (in radians).
     *
     * @see #getAngle()
     */
    public void setAngle(double angle) {
        this.angle = angle;
        fireAnnotationChanged();
    }

    /**
     * Returns the tip radius.
     *
     * @return The tip radius (in Java2D units).
     *
     * @see #setTipRadius(double)
     */
    public double getTipRadius() {
        return this.tipRadius;
    }

    /**
     * Sets the tip radius and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param radius  the radius (in Java2D units).
     *
     * @see #getTipRadius()
     */
    public void setTipRadius(double radius) {
        this.tipRadius = radius;
        fireAnnotationChanged();
    }

    /**
     * Returns the base radius.
     *
     * @return The base radius (in Java2D units).
     *
     * @see #setBaseRadius(double)
     */
    public double getBaseRadius() {
        return this.baseRadius;
    }

    /**
     * Sets the base radius and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param radius  the radius (in Java2D units).
     *
     * @see #getBaseRadius()
     */
    public void setBaseRadius(double radius) {
        this.baseRadius = radius;
        fireAnnotationChanged();
    }

    /**
     * Returns the label offset.
     *
     * @return The label offset (in Java2D units).
     *
     * @see #setLabelOffset(double)
     */
    public double getLabelOffset() {
        return this.labelOffset;
    }

    /**
     * Sets the label offset (from the arrow base, continuing in a straight
     * line, in Java2D units) and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param offset  the offset (in Java2D units).
     *
     * @see #getLabelOffset()
     */
    public void setLabelOffset(double offset) {
        this.labelOffset = offset;
        fireAnnotationChanged();
    }

    /**
     * Returns the arrow length.
     *
     * @return The arrow length.
     *
     * @see #setArrowLength(double)
     */
    public double getArrowLength() {
        return this.arrowLength;
    }

    /**
     * Sets the arrow length and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param length  the length.
     *
     * @see #getArrowLength()
     */
    public void setArrowLength(double length) {
        this.arrowLength = length;
        fireAnnotationChanged();
    }

    /**
     * Returns the arrow width.
     *
     * @return The arrow width (in Java2D units).
     *
     * @see #setArrowWidth(double)
     */
    public double getArrowWidth() {
        return this.arrowWidth;
    }

    /**
     * Sets the arrow width and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param width  the width (in Java2D units).
     *
     * @see #getArrowWidth()
     */
    public void setArrowWidth(double width) {
        this.arrowWidth = width;
        fireAnnotationChanged();
    }

    /**
     * Returns the stroke used to draw the arrow line.
     *
     * @return The arrow stroke (never <code>null</code>).
     *
     * @see #setArrowStroke(Stroke)
     */
    public Stroke getArrowStroke() {
        return this.arrowStroke;
    }

    /**
     * Sets the stroke used to draw the arrow line and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param stroke  the stroke (<code>null</code> not permitted).
     *
     * @see #getArrowStroke()
     */
    public void setArrowStroke(Stroke stroke) {
        ParamChecks.nullNotPermitted(stroke, "stroke");
        this.arrowStroke = stroke;
        fireAnnotationChanged();
    }

    /**
     * Returns the paint used for the arrow.
     *
     * @return The arrow paint (never <code>null</code>).
     *
     * @see #setArrowPaint(Paint)
     */
    public Paint getArrowPaint() {
        return this.arrowPaint;
    }

    /**
     * Sets the paint used for the arrow and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param paint  the arrow paint (<code>null</code> not permitted).
     *
     * @see #getArrowPaint()
     */
    public void setArrowPaint(Paint paint) {
        if (paint == null) {
            throw new IllegalArgumentException("Null 'paint' argument.");
        }
        this.arrowPaint = paint;
        fireAnnotationChanged();
    }

    /**
     * Draws the annotation.
     *
     * @param g2  the graphics device.
     * @param plot  the plot.
     * @param dataArea  the data area.
     * @param domainAxis  the domain axis.
     * @param rangeAxis  the range axis.
     */
    @Override
    public void draw(Graphics2D g2, CategoryPlot plot, Rectangle2D dataArea,
            CategoryAxis domainAxis, ValueAxis rangeAxis) {

        PlotOrientation orientation = plot.getOrientation();
        RectangleEdge domainEdge = Plot.resolveDomainAxisLocation(
                plot.getDomainAxisLocation(), orientation);
        RectangleEdge rangeEdge = Plot.resolveRangeAxisLocation(
                plot.getRangeAxisLocation(), orientation);
        CategoryDataset dataset = plot.getDataset();
        int catIndex = dataset.getColumnIndex(getCategory());
        int catCount = dataset.getColumnCount();
        double j2DX = domainAxis.getCategoryMiddle(catIndex, catCount,
                dataArea, domainEdge);
        double j2DY = rangeAxis.valueToJava2D(getValue(), dataArea, rangeEdge);
        if (orientation == PlotOrientation.HORIZONTAL) {
            double temp = j2DX;
            j2DX = j2DY;
            j2DY = temp;
        }
        double startX = j2DX + Math.cos(this.angle) * this.baseRadius;
        double startY = j2DY + Math.sin(this.angle) * this.baseRadius;

        double endX = j2DX + Math.cos(this.angle) * this.tipRadius;
        double endY = j2DY + Math.sin(this.angle) * this.tipRadius;

        double arrowBaseX = endX + Math.cos(this.angle) * this.arrowLength;
        double arrowBaseY = endY + Math.sin(this.angle) * this.arrowLength;

        double arrowLeftX = arrowBaseX
            + Math.cos(this.angle + Math.PI / 2.0) * this.arrowWidth;
        double arrowLeftY = arrowBaseY
            + Math.sin(this.angle + Math.PI / 2.0) * this.arrowWidth;

        double arrowRightX = arrowBaseX
            - Math.cos(this.angle + Math.PI / 2.0) * this.arrowWidth;
        double arrowRightY = arrowBaseY
            - Math.sin(this.angle + Math.PI / 2.0) * this.arrowWidth;

        GeneralPath arrow = new GeneralPath();
        arrow.moveTo((float) endX, (float) endY);
        arrow.lineTo((float) arrowLeftX, (float) arrowLeftY);
        arrow.lineTo((float) arrowRightX, (float) arrowRightY);
        arrow.closePath();

        g2.setStroke(this.arrowStroke);
        g2.setPaint(this.arrowPaint);
        Line2D line = new Line2D.Double(startX, startY, arrowBaseX, arrowBaseY);
        g2.draw(line);
        g2.fill(arrow);

        // draw the label
        g2.setFont(getFont());
        g2.setPaint(getPaint());
        double labelX = j2DX
            + Math.cos(this.angle) * (this.baseRadius + this.labelOffset);
        double labelY = j2DY
            + Math.sin(this.angle) * (this.baseRadius + this.labelOffset);
        /* Rectangle2D hotspot = */ TextUtilities.drawAlignedString(getText(),
                g2, (float) labelX, (float) labelY, getTextAnchor());
        // TODO: implement the entity for the annotation

    }

    /**
     * Tests this annotation for equality with an arbitrary object.
     *
     * @param obj  the object (<code>null</code> permitted).
     *
     * @return <code>true</code> or <code>false</code>.
     */
    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CategoryPointerAnnotation)) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        CategoryPointerAnnotation that = (CategoryPointerAnnotation) obj;
        if (this.angle != that.angle) {
            return false;
        }
        if (this.tipRadius != that.tipRadius) {
            return false;
        }
        if (this.baseRadius != that.baseRadius) {
            return false;
        }
        if (this.arrowLength != that.arrowLength) {
            return false;
        }
        if (this.arrowWidth != that.arrowWidth) {
            return false;
        }
        if (!this.arrowPaint.equals(that.arrowPaint)) {
            return false;
        }
        if (!ObjectUtils.equal(this.arrowStroke, that.arrowStroke)) {
            return false;
        }
        if (this.labelOffset != that.labelOffset) {
            return false;
        }
        return true;
    }

    /**
     * Returns a hash code for this instance.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int result = 193;
        long temp = Double.doubleToLongBits(this.angle);
        result = 37 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.tipRadius);
        result = 37 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.baseRadius);
        result = 37 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.arrowLength);
        result = 37 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.arrowWidth);
        result = 37 * result + (int) (temp ^ (temp >>> 32));
        result = 37 * result + HashUtils.hashCodeForPaint(this.arrowPaint);
        result = 37 * result + this.arrowStroke.hashCode();
        temp = Double.doubleToLongBits(this.labelOffset);
        result = 37 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /**
     * Returns a clone of the annotation.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException  if the annotation can't be cloned.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the output stream.
     *
     * @throws IOException if there is an I/O error.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        SerialUtils.writePaint(this.arrowPaint, stream);
        SerialUtils.writeStroke(this.arrowStroke, stream);
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
        this.arrowPaint = SerialUtils.readPaint(stream);
        this.arrowStroke = SerialUtils.readStroke(stream);
    }

}
