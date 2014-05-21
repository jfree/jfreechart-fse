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
 * -------------------
 * TextAnnotation.java
 * -------------------
 * (C) Copyright 2002-2012, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Peter Kolb (patch 2809117);
 *                   Martin Hoeller;
 *
 * Changes:
 * --------
 * 28-Aug-2002 : Version 1 (DG);
 * 07-Nov-2002 : Fixed errors reported by Checkstyle, added accessor
 *               methods (DG);
 * 13-Jan-2003 : Reviewed Javadocs (DG);
 * 26-Mar-2003 : Implemented Serializable (DG);
 * 02-Jun-2003 : Added anchor and rotation settings (DG);
 * 19-Aug-2003 : Added equals() method and implemented Cloneable (DG);
 * 29-Sep-2004 : Updated equals() method (DG);
 * 06-Jun-2005 : Fixed equals() method to work with GradientPaint (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 16-Jan-2007 : Added argument checks, fixed hashCode() method and updated
 *               API docs (DG);
 * 24-Jun-2009 : Fire change events (see patch 2809117 by PK) (DG);
 * 28-Oct-2011 : Added missing argument check, Bug #3428870 (MH);
 * 16-Jun-2012 : Removed JCommon dependencies (DG);
 *
 */

package org.jfree.chart.annotations;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.jfree.chart.util.HashUtils;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.chart.util.ObjectUtils;
import org.jfree.chart.util.PaintUtils;
import org.jfree.chart.event.AnnotationChangeEvent;
import org.jfree.chart.util.ParamChecks;
import org.jfree.chart.util.SerialUtils;

/**
 * A base class for text annotations.  This class records the content but not
 * the location of the annotation.
 */
public class TextAnnotation extends AbstractAnnotation implements Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 7008912287533127432L;

    /** The default font. */
    public static final Font DEFAULT_FONT
            = new Font("SansSerif", Font.PLAIN, 10);

    /** The default paint. */
    public static final Paint DEFAULT_PAINT = Color.BLACK;

    /** The default text anchor. */
    public static final TextAnchor DEFAULT_TEXT_ANCHOR = TextAnchor.CENTER;

    /** The default rotation anchor. */
    public static final TextAnchor DEFAULT_ROTATION_ANCHOR = TextAnchor.CENTER;

    /** The default rotation angle. */
    public static final double DEFAULT_ROTATION_ANGLE = 0.0;

    /** The text. */
    private String text;

    /** The font. */
    private Font font;

    /** The paint. */
    private transient Paint paint;

    /** The text anchor. */
    private TextAnchor textAnchor;

    /** The rotation anchor. */
    private TextAnchor rotationAnchor;

    /** The rotation angle. */
    private double rotationAngle;

    /**
     * Creates a text annotation with default settings.
     *
     * @param text  the text (<code>null</code> not permitted).
     */
    protected TextAnnotation(String text) {
        super();
        ParamChecks.nullNotPermitted(text, "text");
        this.text = text;
        this.font = DEFAULT_FONT;
        this.paint = DEFAULT_PAINT;
        this.textAnchor = DEFAULT_TEXT_ANCHOR;
        this.rotationAnchor = DEFAULT_ROTATION_ANCHOR;
        this.rotationAngle = DEFAULT_ROTATION_ANGLE;
    }

    /**
     * Returns the text for the annotation.
     *
     * @return The text (never <code>null</code>).
     *
     * @see #setText(String)
     */
    public String getText() {
        return this.text;
    }

    /**
     * Sets the text for the annotation.
     *
     * @param text  the text (<code>null</code> not permitted).
     *
     * @see #getText()
     */
    public void setText(String text) {
        ParamChecks.nullNotPermitted(text, "text");
        this.text = text;
        fireAnnotationChanged();
    }

    /**
     * Returns the font for the annotation.
     *
     * @return The font (never <code>null</code>).
     *
     * @see #setFont(Font)
     */
    public Font getFont() {
        return this.font;
    }

    /**
     * Sets the font for the annotation and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param font  the font (<code>null</code> not permitted).
     *
     * @see #getFont()
     */
    public void setFont(Font font) {
        if (font == null) {
            throw new IllegalArgumentException("Null 'font' argument.");
        }
        this.font = font;
        fireAnnotationChanged();
    }

    /**
     * Returns the paint for the annotation.
     *
     * @return The paint (never <code>null</code>).
     *
     * @see #setPaint(Paint)
     */
    public Paint getPaint() {
        return this.paint;
    }

    /**
     * Sets the paint for the annotation and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param paint  the paint (<code>null</code> not permitted).
     *
     * @see #getPaint()
     */
    public void setPaint(Paint paint) {
        ParamChecks.nullNotPermitted(paint, "paint");
        this.paint = paint;
        fireAnnotationChanged();
    }

    /**
     * Returns the text anchor.
     *
     * @return The text anchor.
     *
     * @see #setTextAnchor(TextAnchor)
     */
    public TextAnchor getTextAnchor() {
        return this.textAnchor;
    }

    /**
     * Sets the text anchor (the point on the text bounding rectangle that is
     * aligned to the (x, y) coordinate of the annotation) and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param anchor  the anchor point (<code>null</code> not permitted).
     *
     * @see #getTextAnchor()
     */
    public void setTextAnchor(TextAnchor anchor) {
        if (anchor == null) {
            throw new IllegalArgumentException("Null 'anchor' argument.");
        }
        this.textAnchor = anchor;
        fireAnnotationChanged();
    }

    /**
     * Returns the rotation anchor.
     *
     * @return The rotation anchor point (never <code>null</code>).
     *
     * @see #setRotationAnchor(TextAnchor)
     */
    public TextAnchor getRotationAnchor() {
        return this.rotationAnchor;
    }

    /**
     * Sets the rotation anchor point and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param anchor  the anchor (<code>null</code> not permitted).
     *
     * @see #getRotationAnchor()
     */
    public void setRotationAnchor(TextAnchor anchor) {
        ParamChecks.nullNotPermitted(anchor, "anchor");
        this.rotationAnchor = anchor;
        fireAnnotationChanged();
    }

    /**
     * Returns the rotation angle in radians.
     *
     * @return The rotation angle.
     *
     * @see #setRotationAngle(double)
     */
    public double getRotationAngle() {
        return this.rotationAngle;
    }

    /**
     * Sets the rotation angle and sends an {@link AnnotationChangeEvent} to
     * all registered listeners.  The angle is measured clockwise in radians.
     *
     * @param angle  the angle (in radians).
     *
     * @see #getRotationAngle()
     */
    public void setRotationAngle(double angle) {
        this.rotationAngle = angle;
        fireAnnotationChanged();
    }

    /**
     * Tests this object for equality with an arbitrary object.
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
        // now try to reject equality...
        if (!(obj instanceof TextAnnotation)) {
            return false;
        }
        TextAnnotation that = (TextAnnotation) obj;
        if (!ObjectUtils.equal(this.text, that.getText())) {
            return false;
        }
        if (!ObjectUtils.equal(this.font, that.getFont())) {
            return false;
        }
        if (!PaintUtils.equal(this.paint, that.getPaint())) {
            return false;
        }
        if (!ObjectUtils.equal(this.textAnchor, that.getTextAnchor())) {
            return false;
        }
        if (!ObjectUtils.equal(this.rotationAnchor,
                that.getRotationAnchor())) {
            return false;
        }
        if (this.rotationAngle != that.getRotationAngle()) {
            return false;
        }

        // seem to be the same...
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
        result = 37 * result + this.font.hashCode();
        result = 37 * result + HashUtils.hashCodeForPaint(this.paint);
        result = 37 * result + this.rotationAnchor.hashCode();
        long temp = Double.doubleToLongBits(this.rotationAngle);
        result = 37 * result + (int) (temp ^ (temp >>> 32));
        result = 37 * result + this.text.hashCode();
        result = 37 * result + this.textAnchor.hashCode();
        return result;
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
        SerialUtils.writePaint(this.paint, stream);
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
        this.paint = SerialUtils.readPaint(stream);
    }

}
