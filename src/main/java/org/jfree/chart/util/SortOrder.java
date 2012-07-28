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
 * SortOrder.java
 * --------------
 * (C) Copyright 2003-2012, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 05-Mar-2003 : Version 1 (DG);
 * 13-Mar-2003 : Implemented Serializable (DG);
 * 27-Aug-2003 : Moved from JFreeChart --> JCommon (DG);
 * 29-Jul-2004 : Fixed error in readResolve() method (DG);
 * 16-Jun-2012 : Moved from JCommon to JFreeChart (DG);
 * 
 */

package org.jfree.chart.util;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * Defines tokens used to indicate sorting order (ascending or descending).
 *
 * @author David Gilbert
 */
public final class SortOrder implements Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -2124469847758108312L;
    
    /** Ascending order. */
    public static final SortOrder ASCENDING 
        = new SortOrder("SortOrder.ASCENDING");

    /** Descending order. */
    public static final SortOrder DESCENDING 
        = new SortOrder("SortOrder.DESCENDING");

    /** The name. */
    private String name;

    /**
     * Private constructor.
     *
     * @param name  the name.
     */
    private SortOrder(final String name) {
        this.name = name;
    }

    /**
     * Returns a string representing the object.
     *
     * @return The string.
     */
    @Override
	public String toString() {
        return this.name;
    }

    /**
     * Returns <code>true</code> if this object is equal to the specified 
     * object, and <code>false</code> otherwise.
     *
     * @param obj  the other object.
     *
     * @return A boolean.
     */
    @Override
	public boolean equals(final Object obj) {

        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SortOrder)) {
            return false;
        }

        final SortOrder that = (SortOrder) obj;
        if (!this.name.equals(that.toString())) {
            return false;
        }

        return true;
    }
    
    /**
     * Returns a hash code value for the object.
     *
     * @return The hashcode
     */
    @Override
	public int hashCode() {
        return this.name.hashCode();
    }

    /**
     * Ensures that serialization returns the unique instances.
     * 
     * @return The object.
     * 
     * @throws ObjectStreamException if there is a problem.
     */
    private Object readResolve() throws ObjectStreamException {
        if (this.equals(SortOrder.ASCENDING)) {
            return SortOrder.ASCENDING;
        }
        else if (this.equals(SortOrder.DESCENDING)) {
            return SortOrder.DESCENDING;
        }
        return null;
    }
}
