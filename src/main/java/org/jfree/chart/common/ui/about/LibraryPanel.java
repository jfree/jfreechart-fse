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
 * --------------------------
 * LibraryReferencePanel.java
 * --------------------------
 * (C) Copyright 2002-2012, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: LibraryPanel.java,v 1.6 2008/09/10 09:18:12 mungady Exp $
 *
 * Changes
 * -------
 * 28-Feb-2002 : Version 1 (DG);
 * 08-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 17-Jun-2012 : Moved from JCommon to JFreeChart (DG);
 *
 */

package org.jfree.chart.common.ui.about;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * A panel containing a table that lists the libraries used in a project.
 * <P>
 * Used in the AboutFrame class.
 */
public class LibraryPanel extends JPanel {

    /** The table. */
    private JTable table;

    /** The data. */
    private LibraryTableModel model;

    /**
     * Constructs a LibraryPanel.
     *
     * @param libraries  a list of libraries (represented by Library objects).
     */
    public LibraryPanel(final List libraries) {

        setLayout(new BorderLayout());
        this.model = new LibraryTableModel(libraries);
        this.table = new JTable(this.model);
        add(new JScrollPane(this.table));

    }

    /**
     * Creates a new library panel for the specified project.
     *
     * @param projectInfo  the project information.
     */
    public LibraryPanel(final ProjectInfo projectInfo) {
        this(getLibraries(projectInfo));
    }

    private static List getLibraries (final ProjectInfo info) {
        if (info == null) {
            return new ArrayList();
        }
        final ArrayList libs = new ArrayList();
        collectLibraries(info, libs);
        return libs;
    }

    private static void collectLibraries (final ProjectInfo info,
                                          final List list) {
        Library[] libs = info.getLibraries();
        for (int i = 0; i < libs.length; i++) {
            final Library lib = libs[i];
            if (list.contains(lib) == false) {
                // prevent duplicates, they look ugly ..
                list.add(lib);
                if (lib instanceof ProjectInfo) {
                    collectLibraries((ProjectInfo) lib, list);
                }
            }
        }

        libs = info.getOptionalLibraries();
        for (int i = 0; i < libs.length; i++) {
            final Library lib = libs[i];
            if (list.contains(lib) == false) {
                // prevent duplicates, they look ugly ..
                list.add(lib);
                if (lib instanceof ProjectInfo) {
                    collectLibraries((ProjectInfo) lib, list);
                }
            }
        }
    }

    /**
     * Returns the table model for the library.
     *
     * @return The table model.
     */
    public LibraryTableModel getModel() {
      return this.model;
    }

    /**
     * Returns the <code>JTable</code> for the library.
     *
     * @return The table.
     */
    protected JTable getTable() {
      return this.table;
    }
}
