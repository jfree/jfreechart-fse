/*
Copyright Dietmar Ebner, 2004, ebner@apm.tuwien.ac.at

This file is part of PFLP.

PFLP is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

PFLP is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with PFLP; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/

package org.jfree.chart.renderer.xy.forcedirectedlabeling;

/**
 * This class represents a city with it's location and the
 * required space for it's corresponding label.
 * The corresponding coordinates are normalized to be
 * greater than 0 in each component
 *
 * @author Ebner Dietmar, ebner@apm.tuwien.ac.at
 */
public final class PointFeature {
    static public final String DEFAULT_FONT = "Arial";

    /**
     * the x coordinate of the city
     */
    private double x = 0;
    /**
     * the x coordinate of the city
     */
    private double y = 0;
    /**
     * the width of the corresponding label
     */
    private double width = 0;
    /**
     * the height of the corresponding label
     */
    private double height = 0;
    /**
     * the name of this city
     */
    private String text = "";
    /**
     * the font used to display the corresponding label
     */
    private String font = DEFAULT_FONT;

    private int index = 0;

    /**
     * constructs a new node with the given size and width
     */
    public PointFeature(
            double x,
            double y,
            double width,
            double height,
            String text,
            String font,
            int index) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.font = font;
        this.index = index;
    }

    /**
     * returns true if and only if the label can intersect in any location with the
     * label of the given node "node"
     *
     * @param node the node to compare
     */
    public boolean canIntersect(PointFeature node) {
        return (
                (getWidth() + node.getWidth() >= Math.abs(getX() - node.getX()))
                        && (getHeight() + node.getHeight() >= Math.abs(getY() - node.getY())));
    }

    /**
     * @return name of the font for the associated label
     */
    public String getFont() {
        return font;
    }

    /**
     * @return Height of the label
     */
    public double getHeight() {
        return height;
    }

    /**
     * @return Width of the label
     */
    public double getWidth() {
        return width;
    }

    /**
     * @return the Index of the label.
     */
    public int getIndex() {
        return index;
    }

    /**
     * @return Text for the label
     */
    public String getText() {
        return text;
    }

    /**
     * @return x-coodinate of the label
     */
    public double getX() {
        return x;
    }

    /**
     * @return y-coodinate of the label
     */
    public double getY() {
        return y;
    }

    /**
     * @param d new x-coordinate
     */
    public void setX(double d) {
        x = d;
    }

    /**
     * @param d new y-coordinate
     */
    public void setY(double d) {
        y = d;
    }
}
