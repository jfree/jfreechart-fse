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
 * SystemProperties.java
 * ---------------------
 * (C) Copyright 2002-2012, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes (from 26-Oct-2001)
 * --------------------------
 * 04-Mar-2002 : Version 1 (DG);
 * 08-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 17-Jun-2012 : Moved from JCommon to JFreeChart (DG);
 *
 */

package org.jfree.chart.common.ui.about;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.jfree.chart.common.ui.SortableTable;

/**
 * A utility class for working with system properties.
 */
public class SystemProperties {

    /**
     * Private constructor prevents object creation.
     */
    private SystemProperties () {
    }
    
    /**
     * Creates and returns a JTable containing all the system properties.  This method returns a
     * table that is configured so that the user can sort the properties by clicking on the table
     * header.
     *
     * @return a system properties table.
     */
    public static SortableTable createSystemPropertiesTable() {

        final SystemPropertiesTableModel properties = new SystemPropertiesTableModel();
        final SortableTable table = new SortableTable(properties);

        final TableColumnModel model = table.getColumnModel();
        TableColumn column = model.getColumn(0);
        column.setPreferredWidth(200);
        column = model.getColumn(1);
        column.setPreferredWidth(350);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        return table;

    }

}
