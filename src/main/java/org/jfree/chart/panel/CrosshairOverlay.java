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
 * CrosshairOverlay.java
 * ---------------------
 * (C) Copyright 2011, 2012, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 09-Apr-2009 : Version 1 (DG);
 * 19-May-2009 : Fixed FindBugs warnings, patch by Michal Wozniak (DG);
 * 17-Jun-2012 : Removed JCommon dependencies (DG);
 *
 */

package org.jfree.chart.panel;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.ui.RectangleAnchor;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.chart.util.ObjectUtils;
import org.jfree.chart.util.PublicCloneable;
import org.jfree.chart.event.OverlayChangeEvent;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.text.TextUtilities;

/**
 * An overlay for a {@link ChartPanel} that draws crosshairs on a plot.
 *
 * @since 1.0.13
 */
public class CrosshairOverlay extends AbstractOverlay implements Overlay,
        PropertyChangeListener, PublicCloneable, Cloneable, Serializable {

    /** Storage for the crosshairs along the x-axis. */
    private List<Crosshair> xCrosshairs;

    /** Storage for the crosshairs along the y-axis. */
    private List<Crosshair> yCrosshairs;

    /**
     * Default constructor.
     */
    public CrosshairOverlay() {
        super();
        this.xCrosshairs = new java.util.ArrayList<Crosshair>();
        this.yCrosshairs = new java.util.ArrayList<Crosshair>();
    }

    /**
     * Adds a crosshair against the domain axis and sends an
     * {@link OverlayChangeEvent} to all registered listeners.
     *
     * @param crosshair  the crosshair (<code>null</code> not permitted).
     *
     * @see #removeDomainCrosshair(org.jfree.chart.plot.Crosshair)
     * @see #addRangeCrosshair(org.jfree.chart.plot.Crosshair)
     */
    public void addDomainCrosshair(Crosshair crosshair) {
        if (crosshair == null) {
            throw new IllegalArgumentException("Null 'crosshair' argument.");
        }
        this.xCrosshairs.add(crosshair);
        crosshair.addPropertyChangeListener(this);
        fireOverlayChanged();
    }

    /**
     * Removes a domain axis crosshair and sends an {@link OverlayChangeEvent}
     * to all registered listeners.
     *
     * @param crosshair  the crosshair (<code>null</code> not permitted).
     *
     * @see #addDomainCrosshair(org.jfree.chart.plot.Crosshair)
     */
    public void removeDomainCrosshair(Crosshair crosshair) {
        if (crosshair == null) {
            throw new IllegalArgumentException("Null 'crosshair' argument.");
        }
        if (this.xCrosshairs.remove(crosshair)) {
            crosshair.removePropertyChangeListener(this);
            fireOverlayChanged();
        }
    }

    /**
     * Clears all the domain crosshairs from the overlay and sends an
     * {@link OverlayChangeEvent} to all registered listeners.
     */
    public void clearDomainCrosshairs() {
        if (this.xCrosshairs.isEmpty()) {
            return;  // nothing to do
        }
        List<Crosshair> crosshairs = getDomainCrosshairs();
        for (Crosshair crosshair : crosshairs) {
            Crosshair c = crosshair;
            this.xCrosshairs.remove(c);
            c.removePropertyChangeListener(this);
        }
        fireOverlayChanged();
    }

    /**
     * Returns a new list containing the domain crosshairs for this overlay.
     *
     * @return A list of crosshairs.
     */
    public List<Crosshair> getDomainCrosshairs() {
        return new ArrayList<Crosshair>(this.xCrosshairs);
    }

    /**
     * Adds a crosshair against the range axis and sends an
     * {@link OverlayChangeEvent} to all registered listeners.
     *
     * @param crosshair  the crosshair (<code>null</code> not permitted).
     */
    public void addRangeCrosshair(Crosshair crosshair) {
        if (crosshair == null) {
            throw new IllegalArgumentException("Null 'crosshair' argument.");
        }
        this.yCrosshairs.add(crosshair);
        crosshair.addPropertyChangeListener(this);
        fireOverlayChanged();
    }

    /**
     * Removes a range axis crosshair and sends an {@link OverlayChangeEvent}
     * to all registered listeners.
     *
     * @param crosshair  the crosshair (<code>null</code> not permitted).
     *
     * @see #addRangeCrosshair(org.jfree.chart.plot.Crosshair)
     */
    public void removeRangeCrosshair(Crosshair crosshair) {
        if (crosshair == null) {
            throw new IllegalArgumentException("Null 'crosshair' argument.");
        }
        if (this.yCrosshairs.remove(crosshair)) {
            crosshair.removePropertyChangeListener(this);
            fireOverlayChanged();
        }
    }

    /**
     * Clears all the range crosshairs from the overlay and sends an
     * {@link OverlayChangeEvent} to all registered listeners.
     */
    public void clearRangeCrosshairs() {
        if (this.yCrosshairs.isEmpty()) {
            return;  // nothing to do
        }
        List<Crosshair> crosshairs = getRangeCrosshairs();
        for (Crosshair crosshair : crosshairs) {
            Crosshair c = crosshair;
            this.yCrosshairs.remove(c);
            c.removePropertyChangeListener(this);
        }
        fireOverlayChanged();
    }

    /**
     * Returns a new list containing the range crosshairs for this overlay.
     *
     * @return A list of crosshairs.
     */
    public List<Crosshair> getRangeCrosshairs() {
        return new ArrayList<Crosshair>(this.yCrosshairs);
    }

    /**
     * Receives a property change event (typically a change in one of the
     * crosshairs).
     *
     * @param e  the event.
     */
    @Override
    public void propertyChange(PropertyChangeEvent e) {
        fireOverlayChanged();
    }

    /**
     * Paints the crosshairs in the layer.
     *
     * @param g2  the graphics target.
     * @param chartPanel  the chart panel.
     */
    @Override
    public void paintOverlay(Graphics2D g2, ChartPanel chartPanel) {
        Shape savedClip = g2.getClip();
        Rectangle2D dataArea = chartPanel.getScreenDataArea();
        g2.clip(dataArea);
        JFreeChart chart = chartPanel.getChart();
        XYPlot plot = (XYPlot) chart.getPlot();
        ValueAxis xAxis = plot.getDomainAxis();
        RectangleEdge xAxisEdge = plot.getDomainAxisEdge();
        for (Crosshair ch : this.xCrosshairs) {
            if (ch.isVisible()) {
                double x = ch.getValue();
                double xx = xAxis.valueToJava2D(x, dataArea, xAxisEdge);
                if (plot.getOrientation() == PlotOrientation.VERTICAL) {
                    drawVerticalCrosshair(g2, dataArea, xx, ch);
                } else {
                    drawHorizontalCrosshair(g2, dataArea, xx, ch);
                }
            }
        }
        ValueAxis yAxis = plot.getRangeAxis();
        RectangleEdge yAxisEdge = plot.getRangeAxisEdge();
        for (Crosshair ch : this.yCrosshairs) {
            if (ch.isVisible()) {
                double y = ch.getValue();
                double yy = yAxis.valueToJava2D(y, dataArea, yAxisEdge);
                if (plot.getOrientation() == PlotOrientation.VERTICAL) {
                    drawHorizontalCrosshair(g2, dataArea, yy, ch);
                } else {
                    drawVerticalCrosshair(g2, dataArea, yy, ch);
                }
            }
        }
        g2.setClip(savedClip);
    }

    /**
     * Draws a crosshair horizontally across the plot.
     *
     * @param g2  the graphics target.
     * @param dataArea  the data area.
     * @param y  the y-value in Java2D space.
     * @param crosshair  the crosshair.
     */
    protected void drawHorizontalCrosshair(Graphics2D g2, Rectangle2D dataArea,
            double y, Crosshair crosshair) {

        if (y >= dataArea.getMinY() && y <= dataArea.getMaxY()) {
            Line2D line = new Line2D.Double(dataArea.getMinX(), y,
                    dataArea.getMaxX(), y);
            Paint savedPaint = g2.getPaint();
            Stroke savedStroke = g2.getStroke();
            g2.setPaint(crosshair.getPaint());
            g2.setStroke(crosshair.getStroke());
            g2.draw(line);
            if (crosshair.isLabelVisible()) {
                String label = crosshair.getLabelGenerator().generateLabel(
                        crosshair);
                RectangleAnchor anchor = crosshair.getLabelAnchor();
                Point2D pt = calculateLabelPoint(line, anchor, 5, 5);
                float xx = (float) pt.getX();
                float yy = (float) pt.getY();
                TextAnchor alignPt = textAlignPtForLabelAnchorH(anchor);
                Shape hotspot = TextUtilities.calculateRotatedStringBounds(
                        label, g2, xx, yy, alignPt, 0.0, TextAnchor.CENTER);
                if (!dataArea.contains(hotspot.getBounds2D())) {
                    anchor = flipAnchorV(anchor);
                    pt = calculateLabelPoint(line, anchor, 5, 5);
                    xx = (float) pt.getX();
                    yy = (float) pt.getY();
                    alignPt = textAlignPtForLabelAnchorH(anchor);
                    hotspot = TextUtilities.calculateRotatedStringBounds(
                           label, g2, xx, yy, alignPt, 0.0, TextAnchor.CENTER);
                }

                g2.setPaint(crosshair.getLabelBackgroundPaint());
                g2.fill(hotspot);
                g2.setPaint(crosshair.getLabelOutlinePaint());
                g2.draw(hotspot);
                TextUtilities.drawAlignedString(label, g2, xx, yy, alignPt);
            }
            g2.setPaint(savedPaint);
            g2.setStroke(savedStroke);
        }
    }

    /**
     * Draws a crosshair vertically on the plot.
     *
     * @param g2  the graphics target.
     * @param dataArea  the data area.
     * @param x  the x-value in Java2D space.
     * @param crosshair  the crosshair.
     */
    protected void drawVerticalCrosshair(Graphics2D g2, Rectangle2D dataArea,
            double x, Crosshair crosshair) {

        if (x >= dataArea.getMinX() && x <= dataArea.getMaxX()) {
            Line2D line = new Line2D.Double(x, dataArea.getMinY(), x,
                    dataArea.getMaxY());
            Paint savedPaint = g2.getPaint();
            Stroke savedStroke = g2.getStroke();
            g2.setPaint(crosshair.getPaint());
            g2.setStroke(crosshair.getStroke());
            g2.draw(line);
            if (crosshair.isLabelVisible()) {
                String label = crosshair.getLabelGenerator().generateLabel(
                        crosshair);
                RectangleAnchor anchor = crosshair.getLabelAnchor();
                Point2D pt = calculateLabelPoint(line, anchor, 5, 5);
                float xx = (float) pt.getX();
                float yy = (float) pt.getY();
                TextAnchor alignPt = textAlignPtForLabelAnchorV(anchor);
                Shape hotspot = TextUtilities.calculateRotatedStringBounds(
                        label, g2, xx, yy, alignPt, 0.0, TextAnchor.CENTER);
                if (!dataArea.contains(hotspot.getBounds2D())) {
                    anchor = flipAnchorH(anchor);
                    pt = calculateLabelPoint(line, anchor, 5, 5);
                    xx = (float) pt.getX();
                    yy = (float) pt.getY();
                    alignPt = textAlignPtForLabelAnchorV(anchor);
                    hotspot = TextUtilities.calculateRotatedStringBounds(
                           label, g2, xx, yy, alignPt, 0.0, TextAnchor.CENTER);
                }
                g2.setPaint(crosshair.getLabelBackgroundPaint());
                g2.fill(hotspot);
                g2.setPaint(crosshair.getLabelOutlinePaint());
                g2.draw(hotspot);
                TextUtilities.drawAlignedString(label, g2, xx, yy, alignPt);
            }
            g2.setPaint(savedPaint);
            g2.setStroke(savedStroke);
        }
    }

    /**
     * Calculates the anchor point for a label.
     *
     * @param line  the line for the crosshair.
     * @param anchor  the anchor point.
     * @param deltaX  the x-offset.
     * @param deltaY  the y-offset.
     *
     * @return The anchor point.
     */
    private Point2D calculateLabelPoint(Line2D line, RectangleAnchor anchor,
            double deltaX, double deltaY) {
        double x = 0.0;
        double y = 0.0;
        boolean left = (anchor == RectangleAnchor.BOTTOM_LEFT
                || anchor == RectangleAnchor.LEFT
                || anchor == RectangleAnchor.TOP_LEFT);
        boolean right = (anchor == RectangleAnchor.BOTTOM_RIGHT
                || anchor == RectangleAnchor.RIGHT
                || anchor == RectangleAnchor.TOP_RIGHT);
        boolean top = (anchor == RectangleAnchor.TOP_LEFT
                || anchor == RectangleAnchor.TOP
                || anchor == RectangleAnchor.TOP_RIGHT);
        boolean bottom = (anchor == RectangleAnchor.BOTTOM_LEFT
                || anchor == RectangleAnchor.BOTTOM
                || anchor == RectangleAnchor.BOTTOM_RIGHT);
        Rectangle rect = line.getBounds();

        // we expect the line to be vertical or horizontal
        if (line.getX1() == line.getX2()) {  // vertical
            x = line.getX1();
            y = (line.getY1() + line.getY2()) / 2.0;
            if (left) {
                x = x - deltaX;
            }
            if (right) {
                x = x + deltaX;
            }
            if (top) {
                y = Math.min(line.getY1(), line.getY2()) + deltaY;
            }
            if (bottom) {
                y = Math.max(line.getY1(), line.getY2()) - deltaY;
            }
        }
        else {  // horizontal
            x = (line.getX1() + line.getX2()) / 2.0;
            y = line.getY1();
            if (left) {
                x = Math.min(line.getX1(), line.getX2()) + deltaX;
            }
            if (right) {
                x = Math.max(line.getX1(), line.getX2()) - deltaX;
            }
            if (top) {
                y = y - deltaY;
            }
            if (bottom) {
                y = y + deltaY;
            }
        }
        return new Point2D.Double(x, y);
    }

    /**
     * Returns the text anchor that is used to align a label to its anchor
     * point.
     *
     * @param anchor  the anchor.
     *
     * @return The text alignment point.
     */
    private TextAnchor textAlignPtForLabelAnchorV(RectangleAnchor anchor) {
        TextAnchor result = TextAnchor.CENTER;
        if (anchor.equals(RectangleAnchor.TOP_LEFT)) {
            result = TextAnchor.TOP_RIGHT;
        }
        else if (anchor.equals(RectangleAnchor.TOP)) {
            result = TextAnchor.TOP_CENTER;
        }
        else if (anchor.equals(RectangleAnchor.TOP_RIGHT)) {
            result = TextAnchor.TOP_LEFT;
        }
        else if (anchor.equals(RectangleAnchor.LEFT)) {
            result = TextAnchor.HALF_ASCENT_RIGHT;
        }
        else if (anchor.equals(RectangleAnchor.RIGHT)) {
            result = TextAnchor.HALF_ASCENT_LEFT;
        }
        else if (anchor.equals(RectangleAnchor.BOTTOM_LEFT)) {
            result = TextAnchor.BOTTOM_RIGHT;
        }
        else if (anchor.equals(RectangleAnchor.BOTTOM)) {
            result = TextAnchor.BOTTOM_CENTER;
        }
        else if (anchor.equals(RectangleAnchor.BOTTOM_RIGHT)) {
            result = TextAnchor.BOTTOM_LEFT;
        }
        return result;
    }

    /**
     * Returns the text anchor that is used to align a label to its anchor
     * point.
     *
     * @param anchor  the anchor.
     *
     * @return The text alignment point.
     */
    private TextAnchor textAlignPtForLabelAnchorH(RectangleAnchor anchor) {
        TextAnchor result = TextAnchor.CENTER;
        if (anchor.equals(RectangleAnchor.TOP_LEFT)) {
            result = TextAnchor.BOTTOM_LEFT;
        }
        else if (anchor.equals(RectangleAnchor.TOP)) {
            result = TextAnchor.BOTTOM_CENTER;
        }
        else if (anchor.equals(RectangleAnchor.TOP_RIGHT)) {
            result = TextAnchor.BOTTOM_RIGHT;
        }
        else if (anchor.equals(RectangleAnchor.LEFT)) {
            result = TextAnchor.HALF_ASCENT_LEFT;
        }
        else if (anchor.equals(RectangleAnchor.RIGHT)) {
            result = TextAnchor.HALF_ASCENT_RIGHT;
        }
        else if (anchor.equals(RectangleAnchor.BOTTOM_LEFT)) {
            result = TextAnchor.TOP_LEFT;
        }
        else if (anchor.equals(RectangleAnchor.BOTTOM)) {
            result = TextAnchor.TOP_CENTER;
        }
        else if (anchor.equals(RectangleAnchor.BOTTOM_RIGHT)) {
            result = TextAnchor.TOP_RIGHT;
        }
        return result;
    }

    private RectangleAnchor flipAnchorH(RectangleAnchor anchor) {
        RectangleAnchor result = anchor;
        if (anchor.equals(RectangleAnchor.TOP_LEFT)) {
            result = RectangleAnchor.TOP_RIGHT;
        }
        else if (anchor.equals(RectangleAnchor.TOP_RIGHT)) {
            result = RectangleAnchor.TOP_LEFT;
        }
        else if (anchor.equals(RectangleAnchor.LEFT)) {
            result = RectangleAnchor.RIGHT;
        }
        else if (anchor.equals(RectangleAnchor.RIGHT)) {
            result = RectangleAnchor.LEFT;
        }
        else if (anchor.equals(RectangleAnchor.BOTTOM_LEFT)) {
            result = RectangleAnchor.BOTTOM_RIGHT;
        }
        else if (anchor.equals(RectangleAnchor.BOTTOM_RIGHT)) {
            result = RectangleAnchor.BOTTOM_LEFT;
        }
        return result;
    }

    private RectangleAnchor flipAnchorV(RectangleAnchor anchor) {
        RectangleAnchor result = anchor;
        if (anchor.equals(RectangleAnchor.TOP_LEFT)) {
            result = RectangleAnchor.BOTTOM_LEFT;
        }
        else if (anchor.equals(RectangleAnchor.TOP_RIGHT)) {
            result = RectangleAnchor.BOTTOM_RIGHT;
        }
        else if (anchor.equals(RectangleAnchor.TOP)) {
            result = RectangleAnchor.BOTTOM;
        }
        else if (anchor.equals(RectangleAnchor.BOTTOM)) {
            result = RectangleAnchor.TOP;
        }
        else if (anchor.equals(RectangleAnchor.BOTTOM_LEFT)) {
            result = RectangleAnchor.TOP_LEFT;
        }
        else if (anchor.equals(RectangleAnchor.BOTTOM_RIGHT)) {
            result = RectangleAnchor.TOP_RIGHT;
        }
        return result;
    }

    /**
     * Tests this overlay for equality with an arbitrary object.
     *
     * @param obj  the object (<code>null</code> permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CrosshairOverlay)) {
            return false;
        }
        CrosshairOverlay that = (CrosshairOverlay) obj;
        if (!this.xCrosshairs.equals(that.xCrosshairs)) {
            return false;
        }
        if (!this.yCrosshairs.equals(that.yCrosshairs)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a clone of this instance.
     *
     * @return A clone of this instance.
     *
     * @throws java.lang.CloneNotSupportedException if there is some problem
     *     with the cloning.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        CrosshairOverlay clone = (CrosshairOverlay) super.clone();
        clone.xCrosshairs = ObjectUtils.deepClone(this.xCrosshairs);
        clone.yCrosshairs = ObjectUtils.deepClone(this.yCrosshairs);
        return clone;
    }

}
