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
 * ----------------------
 * PolarAxisLocation.java
 * ----------------------
 * (C) Copyright 2009, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 25-Nov-2009 : Version 1 (DG);
 *
 */

package org.jfree.chart.plot;

/**
 * Used to indicate the location of an axis on a {@link PolarPlot}.
 *
 * @since 1.0.14
 */
public enum PolarAxisLocation {

    /** Axis left of north. */
    NORTH_LEFT("PolarAxisLocation.NORTH_LEFT"),

    /** Axis right of north. */
    NORTH_RIGHT("PolarAxisLocation.NORTH_RIGHT"),

    /** Axis left of south. */
    SOUTH_LEFT("PolarAxisLocation.SOUTH_LEFT"),

    /** Axis right of south. */
    SOUTH_RIGHT("PolarAxisLocation.SOUTH_RIGHT"),

    /** Axis above east. */
    EAST_ABOVE("PolarAxisLocation.EAST_ABOVE"),

    /** Axis below east. */
    EAST_BELOW("PolarAxisLocation.EAST_BELOW"),

    /** Axis above west. */
    WEST_ABOVE("PolarAxisLocation.WEST_ABOVE"),

    /** Axis below west. */
    WEST_BELOW("PolarAxisLocation.WEST_BELOW");

    /** The name. */
    private String name;

    /**
     * Private constructor.
     *
     * @param name  the name.
     */
    private PolarAxisLocation(String name) {
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
