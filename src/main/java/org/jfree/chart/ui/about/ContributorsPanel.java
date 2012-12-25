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
 * ContributorsPanel.java
 * ----------------------
 * (C) Copyright 2001-2012, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 10-Dec-2001 : Version 1 (DG);
 * 28-Feb-2002 : Moved into package com.jrefinery.ui.about.  Changed import statements and
 *               updated Javadoc comments (DG);
 * 08-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 17-Jun-2012 : Moved from JCommon to JFreeChart (DG);
 *
 */

package org.jfree.chart.ui.about;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.List;

/**
 * A panel containing a table that lists the contributors to a project.
 * <P>
 * Used in the AboutFrame class.
 *
 * @author David Gilbert
 */
public class ContributorsPanel extends JPanel {

    /** The table. */
    private JTable table;

    /** The data. */
    private TableModel model;

    /**
     * Creates a new contributors panel.
     *
     * @param contributors  a list of contributors (represented by Contributor objects).
     */
    public ContributorsPanel(final List<Contributor> contributors) {

        setLayout(new BorderLayout());
        this.model = new ContributorsTableModel(contributors);
        this.table = new JTable(this.model);
        add(new JScrollPane(this.table));

    }

}
