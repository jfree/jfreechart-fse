/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2013, by Object Refinery Limited and Contributors.
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
 * -----------------------
 * NumberCellRenderer.java
 * -----------------------
 * (C) Copyright 2000-2013, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes (from 26-Oct-2001)
 * --------------------------
 * 26-Oct-2001 : Changed package to com.jrefinery.ui.*;
 * 11-Mar-2002 : Updated import statements (DG);
 * 17-Jun-2012 : Moved from JCommon to JFreeChart (DG);
 *
 */

package org.jfree.chart.ui;

import java.awt.Component;
import java.text.NumberFormat;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * A table cell renderer that formats numbers with right alignment in each cell.
 */
public class NumberCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 6141158525258247290L;

    /**
     * Default constructor - builds a renderer that right justifies the
     * contents of a table cell.
     */
    public NumberCellRenderer() {
        super();
        setHorizontalAlignment(SwingConstants.RIGHT);
    }

    /**
     * Returns itself as the renderer. Supports the TableCellRenderer interface.
     *
     * @param table  the table.
     * @param value  the data to be rendered.
     * @param isSelected  a boolean that indicates whether or not the cell is
     *                    selected.
     * @param hasFocus  a boolean that indicates whether or not the cell has
     *                  the focus.
     * @param row  the (zero-based) row index.
     * @param column  the (zero-based) column index.
     *
     * @return the component that can render the contents of the cell.
     */
    @Override
    public Component getTableCellRendererComponent(final JTable table,
            final Object value, final boolean isSelected,
            final boolean hasFocus, final int row, final int column) {

        setFont(null);
        final NumberFormat nf = NumberFormat.getNumberInstance();
        if (value != null) {
          setText(nf.format(value));
        }
        else {
          setText("");
        }
        if (isSelected) {
            setBackground(table.getSelectionBackground());
        }
        else {
            setBackground(null);
        }
        return this;
    }

}
