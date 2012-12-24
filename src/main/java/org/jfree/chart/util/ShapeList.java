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
 * --------------
 * ShapeList.java
 * --------------
 * (C) Copyright 2003-2012, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 13-Aug-2003 : Version 1 (DG);
 * 17-Jun-2008 : Fixed bug in equals() (DG);
 * 16-Jun-2012 : Moved from JCommon to JFreeChart (DG);
 *
 */

package org.jfree.chart.util;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * A table of {@link Shape} objects.
 */
public class ShapeList extends GenericObjectList<Shape> {

    /**
     * Creates a new list.
     */
    public ShapeList() {
        super();
    }

    /**
     * Returns a {@link Shape} object from the list.
     *
     * @param index the index (zero-based).
     *
     * @return The object.
     */
    public Shape getShape(final int index) {
        return get(index);
    }

    /**
     * Sets the {@link Shape} for an item in the list.  The list is expanded
     * if necessary.
     *
     * @param index  the index (zero-based).
     * @param shape  the {@link Shape}.
     */
    public void setShape(final int index, final Shape shape) {
        set(index, shape);
    }

    /**
     * Tests the list for equality with another object (typically also a list).
     *
     * @param obj  the other object (<code>null</code> permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ShapeList)) {
            return false;
        }
        ShapeList that = (ShapeList) obj;
        int listSize = size();
        for (int i = 0; i < listSize; i++) {
            if (!ShapeUtilities.equal((Shape) get(i), (Shape) that.get(i))) {
                return false;
            }
        }
        return true;

    }

    /**
     * Provides serialization support.
     *
     * @param stream  the output stream.
     *
     * @throws IOException  if there is an I/O error.
     */
    private void writeObject(final ObjectOutputStream stream) throws IOException {

        stream.defaultWriteObject();
        final int count = size();
        stream.writeInt(count);
        for (int i = 0; i < count; i++) {
            final Shape shape = getShape(i);
            if (shape != null) {
                stream.writeInt(i);
                SerialUtilities.writeShape(shape, stream);
            } else {
                stream.writeInt(-1);
            }
        }

    }

    /**
     * Provides serialization support.
     *
     * @param stream  the input stream.
     *
     * @throws IOException  if there is an I/O error.
     * @throws ClassNotFoundException  if there is a classpath problem.
     */
    private void readObject(final ObjectInputStream stream) throws IOException, ClassNotFoundException {

        stream.defaultReadObject();
        final int count = stream.readInt();
        for (int i = 0; i < count; i++) {
            final int index = stream.readInt();
            if (index != -1) {
                setShape(index, SerialUtilities.readShape(stream));
            }
        }

    }

}

