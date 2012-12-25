/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2011, by Object Refinery Limited and Contributors.
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
 * HistogramType.java
 * ------------------
 * (C) Copyright 2004-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 05-Mar-2004 : Version 1 (DG);
 *
 */

package org.jfree.data.statistics;

/**
 * A class for creating constants to represent the histogram type.  See Bloch's
 * enum tip in 'Effective Java'.
 */
public enum HistogramType {

    /** Frequency histogram. */
    FREQUENCY("FREQUENCY"),

    /** Relative frequency. */
    RELATIVE_FREQUENCY("RELATIVE_FREQUENCY"),

    /** Scale area to one. */
    SCALE_AREA_TO_1("SCALE_AREA_TO_1");

    /** The type name. */
    private String name;

    /**
     * Creates a new type.
     *
     * @param name  the name.
     */
    private HistogramType(String name) {
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

}

