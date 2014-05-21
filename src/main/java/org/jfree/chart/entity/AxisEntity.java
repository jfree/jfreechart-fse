/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2012, by Object Refinery Limited and Contributors.
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
 * ----------------
 * AxisEntity.java
 * ----------------
 * (C) Copyright 2009-2012, by Object Refinery Limited and Contributors.
 *
 * Original Author:  Peter Kolb;
 * Contributor(s):   ;
 *
 * Changes:
 * --------
 * 15-Feb-2009 : Version 1 (PK);
 * 16-Jun-2012 : Removed JCommon dependencies (DG);
 *
 */

package org.jfree.chart.entity;

import java.awt.Shape;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.jfree.chart.util.HashUtils;
import org.jfree.chart.axis.Axis;
import org.jfree.chart.util.ObjectUtils;
import org.jfree.chart.util.SerialUtils;

/**
 * A class that captures information about an Axis of a chart.
 *
 * @since 1.0.13
 */
public class AxisEntity extends ChartEntity {

    /** For serialization. */
    private static final long serialVersionUID = -4445994133561919083L;
                  //same as for ChartEntity!

    /** The axis for the entity. */
    private Axis axis;

    /**
     * Creates a new axis entity.
     *
     * @param area  the area (<code>null</code> not permitted).
     * @param axis  the axis (<code>null</code> not permitted).
     */
    public AxisEntity(Shape area, Axis axis) {
        // defer argument checks...
        this(area, axis, null);
    }

    /**
     * Creates a new axis entity.
     *
     * @param area  the area (<code>null</code> not permitted).
     * @param axis  the axis (<code>null</code> not permitted).
     * @param toolTipText  the tool tip text (<code>null</code> permitted).
     */
    public AxisEntity(Shape area, Axis axis, String toolTipText) {
        // defer argument checks...
        this(area, axis, toolTipText, null);
    }

    /**
     * Creates a new axis entity.
     *
     * @param area  the area (<code>null</code> not permitted).
     * @param axis  the axis (<code>null</code> not permitted).
     * @param toolTipText  the tool tip text (<code>null</code> permitted).
     * @param urlText  the URL text for HTML image maps (<code>null</code>
     *                 permitted).
     */
    public AxisEntity(Shape area, Axis axis, String toolTipText,
            String urlText) {
        super(area, toolTipText, urlText);
        if (axis == null) {
            throw new IllegalArgumentException("Null 'axis' argument.");
        }

        this.axis = axis;
    }

    /**
     * Returns the axis that occupies the entity area.
     *
     * @return The axis (never <code>null</code>).
     */
    public Axis getAxis() {
        return this.axis;
    }

    /**
     * Returns a string representation of the chart entity, useful for
     * debugging.
     *
     * @return A string.
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder("AxisEntity: ");
        buf.append("tooltip = ");
        buf.append(getToolTipText());
        return buf.toString();
    }

    /**
     * Tests the entity for equality with an arbitrary object.
     *
     * @param obj  the object to test against (<code>null</code> permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AxisEntity)) {
            return false;
        }
        AxisEntity that = (AxisEntity) obj;
        if (!getArea().equals(that.getArea())) {
            return false;
        }
        if (!ObjectUtils.equal(getToolTipText(), that.getToolTipText())) {
            return false;
        }
        if (!ObjectUtils.equal(getURLText(), that.getURLText())) {
            return false;
        }
        if (!(this.axis.equals(that.axis))) {
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
        int result = 39;
        result = HashUtils.hashCode(result, getToolTipText());
        result = HashUtils.hashCode(result, getURLText());
        return result;
    }

    /**
     * Returns a clone of the entity.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if there is a problem cloning the
     *         entity.
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
     * @throws IOException  if there is an I/O error.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        SerialUtils.writeShape(getArea(), stream);
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
        setArea(SerialUtils.readShape(stream));
    }

}