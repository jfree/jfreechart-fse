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
 * MarkerTests.java
 * ----------------
 * (C) Copyright 2006-2012, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 05-Sep-2006 : Version 1 (DG);
 * 17-Jun-2012 : Removed JCommon dependencies (DG);
 *
 */

package org.jfree.chart.plot;

import org.jfree.chart.event.MarkerChangeEvent;
import org.jfree.chart.event.MarkerChangeListener;
import org.jfree.chart.ui.LengthAdjustmentType;
import org.jfree.chart.ui.RectangleAnchor;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.ui.TextAnchor;
import org.junit.Test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.EventListener;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests for the {@link Marker} class.
 */
public class MarkerTest  implements MarkerChangeListener {

    MarkerChangeEvent lastEvent;





    /**
     * Some checks for the getPaint() and setPaint() methods.
     */
    @Test
    public void testGetSetPaint() {
        // we use ValueMarker for the tests, because we need a concrete
        // subclass...
        ValueMarker m = new ValueMarker(1.1);
        m.addChangeListener(this);
        this.lastEvent = null;
        assertEquals(Color.gray, m.getPaint());
        m.setPaint(Color.BLUE);
        assertEquals(Color.BLUE, m.getPaint());
        assertEquals(m, this.lastEvent.getMarker());

        // check null argument...
        try {
            m.setPaint(null);
            fail("Expected an IllegalArgumentException for null.");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Null 'paint' argument.", e.getMessage());
        }
    }

    /**
     * Some checks for the getStroke() and setStroke() methods.
     */
    @Test
    public void testGetSetStroke() {
        // we use ValueMarker for the tests, because we need a concrete
        // subclass...
        ValueMarker m = new ValueMarker(1.1);
        m.addChangeListener(this);
        this.lastEvent = null;
        assertEquals(new BasicStroke(0.5f), m.getStroke());
        m.setStroke(new BasicStroke(1.1f));
        assertEquals(new BasicStroke(1.1f), m.getStroke());
        assertEquals(m, this.lastEvent.getMarker());

        // check null argument...
        try {
            m.setStroke(null);
            fail("Expected an IllegalArgumentException for null.");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Null 'stroke' argument.", e.getMessage());
        }
    }

    /**
     * Some checks for the getOutlinePaint() and setOutlinePaint() methods.
     */
    @Test
    public void testGetSetOutlinePaint() {
        // we use ValueMarker for the tests, because we need a concrete
        // subclass...
        ValueMarker m = new ValueMarker(1.1);
        m.addChangeListener(this);
        this.lastEvent = null;
        assertEquals(Color.gray, m.getOutlinePaint());
        m.setOutlinePaint(Color.yellow);
        assertEquals(Color.yellow, m.getOutlinePaint());
        assertEquals(m, this.lastEvent.getMarker());

        // check null argument...
        m.setOutlinePaint(null);
        assertEquals(null, m.getOutlinePaint());
    }

    /**
     * Some checks for the getOutlineStroke() and setOutlineStroke() methods.
     */
    @Test
    public void testGetSetOutlineStroke() {
        // we use ValueMarker for the tests, because we need a concrete
        // subclass...
        ValueMarker m = new ValueMarker(1.1);
        m.addChangeListener(this);
        this.lastEvent = null;
        assertEquals(new BasicStroke(0.5f), m.getOutlineStroke());
        m.setOutlineStroke(new BasicStroke(1.1f));
        assertEquals(new BasicStroke(1.1f), m.getOutlineStroke());
        assertEquals(m, this.lastEvent.getMarker());

        // check null argument...
        m.setOutlineStroke(null);
        assertEquals(null, m.getOutlineStroke());
    }

    private static final float EPSILON = 0.000000001f;

    /**
     * Some checks for the getAlpha() and setAlpha() methods.
     */
    @Test
    public void testGetSetAlpha() {
        // we use ValueMarker for the tests, because we need a concrete
        // subclass...
        ValueMarker m = new ValueMarker(1.1);
        m.addChangeListener(this);
        this.lastEvent = null;
        assertEquals(0.8f, m.getAlpha(), EPSILON);
        m.setAlpha(0.5f);
        assertEquals(0.5f, m.getAlpha(), EPSILON);
        assertEquals(m, this.lastEvent.getMarker());
    }

    /**
     * Some checks for the getLabel() and setLabel() methods.
     */
    @Test
    public void testGetSetLabel() {
        // we use ValueMarker for the tests, because we need a concrete
        // subclass...
        ValueMarker m = new ValueMarker(1.1);
        m.addChangeListener(this);
        this.lastEvent = null;
        assertEquals(null, m.getLabel());
        m.setLabel("XYZ");
        assertEquals("XYZ", m.getLabel());
        assertEquals(m, this.lastEvent.getMarker());

        // check null argument...
        m.setLabel(null);
        assertEquals(null, m.getLabel());
    }

    /**
     * Some checks for the getLabelFont() and setLabelFont() methods.
     */
    @Test
    public void testGetSetLabelFont() {
        // we use ValueMarker for the tests, because we need a concrete
        // subclass...
        ValueMarker m = new ValueMarker(1.1);
        m.addChangeListener(this);
        this.lastEvent = null;
        assertEquals(new Font("SansSerif", Font.PLAIN, 9), m.getLabelFont());
        m.setLabelFont(new Font("SansSerif", Font.BOLD, 10));
        assertEquals(new Font("SansSerif", Font.BOLD, 10), m.getLabelFont());
        assertEquals(m, this.lastEvent.getMarker());

        // check null argument...
        try {
            m.setLabelFont(null);
            fail("Expected an IllegalArgumentException for null.");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Null 'font' argument.", e.getMessage());
        }
    }

    /**
     * Some checks for the getLabelPaint() and setLabelPaint() methods.
     */
    @Test
    public void testGetSetLabelPaint() {
        // we use ValueMarker for the tests, because we need a concrete
        // subclass...
        ValueMarker m = new ValueMarker(1.1);
        m.addChangeListener(this);
        this.lastEvent = null;
        assertEquals(Color.BLACK, m.getLabelPaint());
        m.setLabelPaint(Color.RED);
        assertEquals(Color.RED, m.getLabelPaint());
        assertEquals(m, this.lastEvent.getMarker());

        // check null argument...
        try {
            m.setLabelPaint(null);
            fail("Expected an IllegalArgumentException for null.");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Null 'paint' argument.", e.getMessage());
        }
    }

    /**
     * Some checks for the getLabelAnchor() and setLabelAnchor() methods.
     */
    @Test
    public void testGetSetLabelAnchor() {
        // we use ValueMarker for the tests, because we need a concrete
        // subclass...
        ValueMarker m = new ValueMarker(1.1);
        m.addChangeListener(this);
        this.lastEvent = null;
        assertEquals(RectangleAnchor.TOP_LEFT, m.getLabelAnchor());
        m.setLabelAnchor(RectangleAnchor.TOP);
        assertEquals(RectangleAnchor.TOP, m.getLabelAnchor());
        assertEquals(m, this.lastEvent.getMarker());

        // check null argument...
        try {
            m.setLabelAnchor(null);
            fail("Expected an IllegalArgumentException for null.");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Null 'anchor' argument.", e.getMessage());
        }
    }

    /**
     * Some checks for the getLabelOffset() and setLabelOffset() methods.
     */
    @Test
    public void testGetSetLabelOffset() {
        // we use ValueMarker for the tests, because we need a concrete
        // subclass...
        ValueMarker m = new ValueMarker(1.1);
        m.addChangeListener(this);
        this.lastEvent = null;
        assertEquals(new RectangleInsets(3, 3, 3, 3), m.getLabelOffset());
        m.setLabelOffset(new RectangleInsets(1, 2, 3, 4));
        assertEquals(new RectangleInsets(1, 2, 3, 4), m.getLabelOffset());
        assertEquals(m, this.lastEvent.getMarker());

        // check null argument...
        try {
            m.setLabelOffset(null);
            fail("Expected an IllegalArgumentException for null.");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Null 'offset' argument.", e.getMessage());
        }
    }

    /**
     * Some checks for the getLabelOffsetType() and setLabelOffsetType()
     * methods.
     */
    @Test
    public void testGetSetLabelOffsetType() {
        // we use ValueMarker for the tests, because we need a concrete
        // subclass...
        ValueMarker m = new ValueMarker(1.1);
        m.addChangeListener(this);
        this.lastEvent = null;
        assertEquals(LengthAdjustmentType.CONTRACT, m.getLabelOffsetType());
        m.setLabelOffsetType(LengthAdjustmentType.EXPAND);
        assertEquals(LengthAdjustmentType.EXPAND, m.getLabelOffsetType());
        assertEquals(m, this.lastEvent.getMarker());

        // check null argument...
        try {
            m.setLabelOffsetType(null);
            fail("Expected an IllegalArgumentException for null.");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Null 'adj' argument.", e.getMessage());
        }
    }

    /**
     * Some checks for the getLabelTextAnchor() and setLabelTextAnchor()
     * methods.
     */
    @Test
    public void testGetSetLabelTextAnchor() {
        // we use ValueMarker for the tests, because we need a concrete
        // subclass...
        ValueMarker m = new ValueMarker(1.1);
        m.addChangeListener(this);
        this.lastEvent = null;
        assertEquals(TextAnchor.CENTER, m.getLabelTextAnchor());
        m.setLabelTextAnchor(TextAnchor.BASELINE_LEFT);
        assertEquals(TextAnchor.BASELINE_LEFT, m.getLabelTextAnchor());
        assertEquals(m, this.lastEvent.getMarker());

        // check null argument...
        try {
            m.setLabelTextAnchor(null);
            fail("Expected an IllegalArgumentException for null.");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Null 'anchor' argument.", e.getMessage());
        }
    }

    /**
     * Checks that a CategoryPlot deregisters listeners when clearing markers.
     */
    @Test
    public void testListenersWithCategoryPlot() {
        CategoryPlot plot = new CategoryPlot();
        CategoryMarker marker1 = new CategoryMarker("X");
        ValueMarker marker2 = new ValueMarker(1.0);
        plot.addDomainMarker(marker1);
        plot.addRangeMarker(marker2);
        EventListener[] listeners1 = marker1.getListeners(
                MarkerChangeListener.class);
        assertTrue(Arrays.asList(listeners1).contains(plot));
        EventListener[] listeners2 = marker1.getListeners(
                MarkerChangeListener.class);
        assertTrue(Arrays.asList(listeners2).contains(plot));
        plot.clearDomainMarkers();
        plot.clearRangeMarkers();
        listeners1 = marker1.getListeners(MarkerChangeListener.class);
        assertFalse(Arrays.asList(listeners1).contains(plot));
        listeners2 = marker1.getListeners(MarkerChangeListener.class);
        assertFalse(Arrays.asList(listeners2).contains(plot));
    }

    /**
     * Checks that an XYPlot deregisters listeners when clearing markers.
     */
    @Test
    public void testListenersWithXYPlot() {
        XYPlot plot = new XYPlot();
        ValueMarker marker1 = new ValueMarker(1.0);
        ValueMarker marker2 = new ValueMarker(2.0);
        plot.addDomainMarker(marker1);
        plot.addRangeMarker(marker2);
        EventListener[] listeners1 = marker1.getListeners(
                MarkerChangeListener.class);
        assertTrue(Arrays.asList(listeners1).contains(plot));
        EventListener[] listeners2 = marker1.getListeners(
                MarkerChangeListener.class);
        assertTrue(Arrays.asList(listeners2).contains(plot));
        plot.clearDomainMarkers();
        plot.clearRangeMarkers();
        listeners1 = marker1.getListeners(MarkerChangeListener.class);
        assertFalse(Arrays.asList(listeners1).contains(plot));
        listeners2 = marker1.getListeners(MarkerChangeListener.class);
        assertFalse(Arrays.asList(listeners2).contains(plot));
    }

    /**
     * Records the last event.
     *
     * @param event  the event.
     */
    @Override
    public void markerChanged(MarkerChangeEvent event) {
        this.lastEvent = event;
    }

}
