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
 * ---------------------
 * DateTickUnitType.java
 * ---------------------
 * (C) Copyright 2009, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 09-Jan-2009 : Version 1 (DG);
 *
 */

package org.jfree.chart.axis;

import java.util.Calendar;

/**
 * An enumeration of the unit types for a {@link DateTickUnit} instance.
 *
 * @since 1.0.13
 */
public enum DateTickUnitType {

    /** Year. */
    YEAR("DateTickUnitType.YEAR", Calendar.YEAR),

    /** Month. */
    MONTH("DateTickUnitType.MONTH", Calendar.MONTH),

    /** Day. */
    DAY("DateTickUnitType.DAY", Calendar.DATE),


    /** Hour. */
    HOUR("DateTickUnitType.HOUR", Calendar.HOUR_OF_DAY),

    /** Minute. */
    MINUTE("DateTickUnitType.MINUTE", Calendar.MINUTE),

    /** Second. */
    SECOND("DateTickUnitType.SECOND", Calendar.SECOND),

    /** Millisecond. */
    MILLISECOND("DateTickUnitType.MILLISECOND", Calendar.MILLISECOND);

    /** The name. */
    private String name;

    /** The corresponding field value in Java's Calendar class. */
    private int calendarField;

    /**
     * Private constructor.
     *
     * @param name  the name.
     * @param calendarField  the calendar field.
     */
    private DateTickUnitType(String name, int calendarField) {
        this.name = name;
        this.calendarField = calendarField;
    }

    /**
     * Returns the calendar field.
     *
     * @return The calendar field.
     */
    public int getCalendarField() {
        return this.calendarField;
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
