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
 * --------------------
 * ItemLabelAnchor.java
 * --------------------
 * (C) Copyright 2003-2008, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 29-Apr-2003 : Version 1 (DG);
 * 19-Feb-2004 : Moved to org.jfree.chart.labels package, added readResolve()
 *               method (DG);
 * 11-Jan-2005 : Removed deprecated code in preparation for the 1.0.0
 *               release (DG);
 *
 */

package org.jfree.chart.labels;


/**
 * An enumeration of the positions that a value label can take, relative to an
 * item in a {@link org.jfree.chart.plot.CategoryPlot}.
 */
public enum ItemLabelAnchor {

    /** CENTER. */
    CENTER("ItemLabelAnchor.CENTER"),

    /** INSIDE1. */
    INSIDE1("ItemLabelAnchor.INSIDE1"),

    /** INSIDE2. */
    INSIDE2("ItemLabelAnchor.INSIDE2"),

    /** INSIDE3. */
    INSIDE3("ItemLabelAnchor.INSIDE3"),

    /** INSIDE4. */
    INSIDE4("ItemLabelAnchor.INSIDE4"),

    /** INSIDE5. */
    INSIDE5("ItemLabelAnchor.INSIDE5"),

    /** INSIDE6. */
    INSIDE6("ItemLabelAnchor.INSIDE6"),

    /** INSIDE7. */
    INSIDE7("ItemLabelAnchor.INSIDE7"),

    /** INSIDE8. */
    INSIDE8("ItemLabelAnchor.INSIDE8"),

    /** INSIDE9. */
    INSIDE9("ItemLabelAnchor.INSIDE9"),

    /** INSIDE10. */
    INSIDE10("ItemLabelAnchor.INSIDE10"),

    /** INSIDE11. */
    INSIDE11("ItemLabelAnchor.INSIDE11"),

    /** INSIDE12. */
    INSIDE12("ItemLabelAnchor.INSIDE12"),

    /** OUTSIDE1. */
    OUTSIDE1("ItemLabelAnchor.OUTSIDE1"),

    /** OUTSIDE2. */
    OUTSIDE2("ItemLabelAnchor.OUTSIDE2"),

    /** OUTSIDE3. */
    OUTSIDE3("ItemLabelAnchor.OUTSIDE3"),

    /** OUTSIDE4. */
    OUTSIDE4("ItemLabelAnchor.OUTSIDE4"),

    /** OUTSIDE5. */
    OUTSIDE5("ItemLabelAnchor.OUTSIDE5"),

    /** OUTSIDE6. */
    OUTSIDE6("ItemLabelAnchor.OUTSIDE6"),

    /** OUTSIDE7. */
    OUTSIDE7("ItemLabelAnchor.OUTSIDE7"),

    /** OUTSIDE8. */
    OUTSIDE8("ItemLabelAnchor.OUTSIDE8"),

    /** OUTSIDE9. */
    OUTSIDE9("ItemLabelAnchor.OUTSIDE9"),

    /** OUTSIDE10. */
    OUTSIDE10("ItemLabelAnchor.OUTSIDE10"),

    /** OUTSIDE11. */
    OUTSIDE11("ItemLabelAnchor.OUTSIDE11"),

    /** OUTSIDE12. */
    OUTSIDE12("ItemLabelAnchor.OUTSIDE12");

    /** The name. */
    private String name;

    /**
     * Private constructor.
     *
     * @param name  the name.
     */
    private ItemLabelAnchor(String name) {
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
