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
 * ---------------------------------------
 * OverLibToolTipTagFragmentGenerator.java
 * ---------------------------------------
 * (C) Copyright 2003-2009, by Richard Atkinson and Contributors.
 *
 * Original Author:  Richard Atkinson;
 * Contributors:     David Gilbert (for Object Refinery Limited);
 *                   Fawad Halim - bug 2690293;
 *
 * Changes
 * -------
 * 12-Aug-2003 : Version 1 (RA);
 * 04-Dec-2007 : Escape tool tip text to fix bug 1400917 (DG);
 * 19-Mar-2009 : Escape apostrophes - see bug 2690293 with fix by FH (DG);
 *
 */

package org.jfree.chart.imagemap;

/**
 * Generates tooltips using the OverLIB library
 * (http://www.bosrup.com/web/overlib/).
 */
public class OverLIBToolTipTagFragmentGenerator
        implements ToolTipTagFragmentGenerator {

    /**
     * Creates a new instance.
     */
    public OverLIBToolTipTagFragmentGenerator() {
        super();
    }

    /**
     * Generates a tooltip string to go in an HTML image map.
     *
     * @param toolTipText  the tooltip text.
     *
     * @return The formatted HTML area tag attribute(s).
     */
    @Override
    public String generateToolTipFragment(String toolTipText) {
        return " onMouseOver=\"return overlib('"
                + ImageMapUtilities.javascriptEscape(toolTipText)
                + "');\" onMouseOut=\"return nd();\"";
    }

}
