/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2014, by Object Refinery Limited and Contributors.
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
 * LegendItemSource.java
 * ---------------------
 * (C) Copyright 2005-2014, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 23-Feb-2005 : Version 1 (DG);
 * 10-Mar-2013 : Removed LegendItemCollection class (DG);
 *
 */

package org.jfree.chart;

import java.util.List;

/**
 * A source of legend items.  A {@link org.jfree.chart.title.LegendTitle} will
 * maintain a list of sources (often just one) from which it obtains legend
 * items.
 */
public interface LegendItemSource {

    /**
     * Returns a (possibly empty) collection of legend items.
     *
     * @return The legend item collection (never <code>null</code>).
     */
    public List<LegendItem> getLegendItems();

}
