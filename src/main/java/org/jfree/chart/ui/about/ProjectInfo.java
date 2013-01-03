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
 * ----------------
 * ProjectInfo.java
 * ----------------
 * (C) Copyright 2001-2012, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes (since 27-Jun-2002)
 * ---------------------------
 * 27-Jun-2002 : Added logo, updated source header and Javadocs (DG);
 * 08-Oct-2002 : Added set methods for most attributes. Fixed errors reported by Checkstyle (DG);
 * 17-Jun-2012 : Moved from JCommon to JFreeChart (DG); 
 * 
 */

package org.jfree.chart.ui.about;

import java.awt.*;
import java.util.List;

/**
 * A class for recording the basic information about a free or open source software project.
 */
public class ProjectInfo extends BasicProjectInfo {

    /** An optional project logo. */
    private Image logo;

    /** The licence text. */
    private String licenceText;

    /** A list of contributors. */
    private List<Contributor> contributors;

    /**
     * Constructs an empty project info object.
     */
    public ProjectInfo() {
        // nothing required
    }

    /**
     * Constructs a project info object.
     *
     * @param name  the name of the project.
     * @param version  the version.
     * @param info  other info (usually a URL).
     * @param logo  the project logo.
     * @param copyright  a copyright statement.
     * @param licenceName  the name of the licence that applies to the project.
     * @param licenceText  the text of the licence that applies to the project.
     */
    public ProjectInfo(final String name,
                       final String version,
                       final String info,
                       final Image logo,
                       final String copyright,
                       final String licenceName,
                       final String licenceText) {

        super(name, version, info, copyright, licenceName);
        this.logo = logo;
        this.licenceText = licenceText;

    }

    /**
     * Returns the logo.
     *
     * @return the project logo.
     */
    public Image getLogo() {
        return this.logo;
    }

    /**
     * Sets the project logo.
     *
     * @param logo  the project logo.
     */
    public void setLogo(final Image logo) {
        this.logo = logo;
    }

    /**
     * Returns the licence text.
     *
     * @return the licence text.
     */
    public String getLicenceText() {
        return this.licenceText;
    }

    /**
     * Sets the project licence text.
     *
     * @param licenceText  the licence text.
     */
    public void setLicenceText(final String licenceText) {
        this.licenceText = licenceText;
    }

    /**
     * Returns the list of contributors for the project.
     *
     * @return the list of contributors.
     */
    public List<Contributor> getContributors() {
        return this.contributors;
    }

    /**
     * Sets the list of contributors.
     *
     * @param contributors  the list of contributors.
     */
    public void setContributors(final List<Contributor> contributors) {
        this.contributors = contributors;
    }

    /**
     * Returns a string describing the project.
     *
     * @return a string describing the project.
     */
    @Override
    public String toString() {

        final StringBuilder result = new StringBuilder();
        result.append(getName());
        result.append(" version ");
        result.append(getVersion());
        result.append(".\n");
        result.append(getCopyright());
        result.append(".\n");
        result.append("\n");
        result.append("For terms of use, see the licence below.\n");
        result.append("\n");
        result.append("FURTHER INFORMATION:");
        result.append(getInfo());
        result.append("\n");
        result.append("CONTRIBUTORS:");
        if (this.contributors != null) {
            for (Contributor contributor : this.contributors) {
                result.append(contributor.getName());
                result.append(" (");
                result.append(contributor.getEmail());
                result.append(").");
            }
        } else {
            result.append("None");
        }

        result.append("\n");
        result.append("OTHER LIBRARIES USED BY ");
        result.append(getName());
        result.append(":");
        final Library[] libraries = getLibraries();
        if (libraries.length != 0) {
            for (final Library lib : libraries) {
                result.append(lib.getName());
                result.append(" ");
                result.append(lib.getVersion());
                result.append(" (");
                result.append(lib.getInfo());
                result.append(").");
            }
        } else {
            result.append("None");
        }
        result.append("\n");
        result.append(getName());
        result.append(" LICENCE TERMS:");
        result.append("\n");
        result.append(getLicenceText());

        return result.toString();

    }

}
