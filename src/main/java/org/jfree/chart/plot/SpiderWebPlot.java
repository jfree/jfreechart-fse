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
 * ------------------
 * SpiderWebPlot.java
 * ------------------
 * (C) Copyright 2005-2014, by Heaps of Flavour Pty Ltd and Contributors.
 *
 * Company Info:  http://www.i4-talent.com
 *
 * Original Author:  Don Elliott;
 * Contributor(s):   David Gilbert (for Object Refinery Limited);
 *                   Nina Jeliazkova;
 *
 * Changes
 * -------
 * 28-Jan-2005 : First cut - missing a few features - still to do:
 *                           - needs tooltips/URL/label generator functions
 *                           - ticks on axes / background grid?
 * 31-Jan-2005 : Renamed SpiderWebPlot, added label generator support, and
 *               reformatted for consistency with other source files in
 *               JFreeChart (DG);
 * 20-Apr-2005 : Renamed CategoryLabelGenerator
 *               --> CategoryItemLabelGenerator (DG);
 * 05-May-2005 : Updated draw() method parameters (DG);
 * 10-Jun-2005 : Added equals() method and fixed serialization (DG);
 * 16-Jun-2005 : Added default constructor and get/setDataset()
 *               methods (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 05-Apr-2006 : Fixed bug preventing the display of zero values - see patch
 *               1462727 (DG);
 * 05-Apr-2006 : Added support for mouse clicks, tool tips and URLs - see patch
 *               1463455 (DG);
 * 01-Jun-2006 : Fix bug 1493199, NullPointerException when drawing with null
 *               info (DG);
 * 05-Feb-2007 : Added attributes for axis stroke and paint, while fixing
 *               bug 1651277, and implemented clone() properly (DG);
 * 06-Feb-2007 : Changed getPlotValue() to protected, as suggested in bug
 *               1605202 (DG);
 * 05-Mar-2007 : Restore clip region correctly (see bug 1667750) (DG);
 * 18-May-2007 : Set dataset for LegendItem (DG);
 * 02-Jun-2008 : Fixed bug with chart entities using TableOrder.BY_COLUMN (DG);
 * 02-Jun-2008 : Fixed bug with null dataset (DG);
 * 01-Jun-2009 : Set series key in getLegendItems() (DG);
 * 16-Jun-2012 : Removed JCommon dependencies (DG);
 * 10-Mar-2014 : Removed LegendItemCollection (DG);
 * 21-May-2014 : Added value and category markers, updated label drawing
 *               method to use TextAnchors instead of raw pixel measuring.
 *               Added axis line visibility.
 *               Added a new type of category marker.
 *               (Jesenko Mehmedbasic)
 */

package org.jfree.chart.plot;

import org.jfree.chart.LegendItem;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.text.G2TextMeasurer;
import org.jfree.chart.text.TextBlock;
import org.jfree.chart.text.TextBlockAnchor;
import org.jfree.chart.text.TextUtilities;
import org.jfree.chart.ui.HorizontalAlignment;
import org.jfree.chart.ui.LengthAdjustmentType;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.urls.CategoryURLGenerator;
import org.jfree.chart.util.*;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetUtilities;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.List;

/**
 * A plot that displays data from a {@link CategoryDataset} in the form of a
 * "spider web".  Multiple series can be plotted on the same axis to allow
 * easy comparison.  This plot doesn't support negative values at present.
 */
public class SpiderWebPlot extends Plot implements Cloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -5376340422031599463L;

    /** The default head radius percent (currently 1%). */
    public static final double DEFAULT_HEAD = 0.01;

    /** The default axis label gap (currently 10%). */
    public static final double DEFAULT_AXIS_LABEL_GAP = 0.10;

    /** The default axis line color. */
    public static final Paint DEFAULT_AXIS_LINE_PAINT = Color.BLACK;

    /** The default stroke for the axises. */
    public static final Stroke DEFAULT_AXIS_LINE_STROKE = new BasicStroke(0.5f);


    /** The default interior gap. */
    public static final double DEFAULT_INTERIOR_GAP = 0.25;

    /** The maximum interior gap (currently 40%). */
    public static final double MAX_INTERIOR_GAP = 0.40;

    /** The default starting angle for the radar chart axes. */
    public static final double DEFAULT_START_ANGLE = 90.0;

    /** The default series label font. */
    public static final Font DEFAULT_LABEL_FONT = new Font("SansSerif",
            Font.PLAIN, 10);

    /** The default series label paint. */
    public static final Paint  DEFAULT_LABEL_PAINT = Color.BLACK;

    /** The default series label background paint. */
    public static final Paint  DEFAULT_LABEL_BACKGROUND_PAINT = new Color(255, 255, 192);

    /** The default series label outline paint. */
    public static final Paint  DEFAULT_LABEL_OUTLINE_PAINT = Color.BLACK;

    /** The default series label outline stroke. */
    public static final Stroke DEFAULT_LABEL_OUTLINE_STROKE
            = new BasicStroke(0.5f);

    /** The default series label shadow paint. */
    public static final Paint  DEFAULT_LABEL_SHADOW_PAINT = Color.LIGHT_GRAY;

    /**
     * The default maximum value plotted - forces the plot to evaluate
     * the maximum from the data passed in
     */
    public static final double DEFAULT_MAX_VALUE = -1.0;

    /** The head radius as a percentage of the available drawing area. */
    protected double headPercent;

    /** The vertical space left around the outside of the plot as a percentage. */
    private double interiorGapVertical;

    /** The horizontal space left around the outside of the plot as a percentage. */
    private double interiorGapHorizontal;

    /** The gap between the labels and the axes as a %age of the radius. */
    private double axisLabelGap;

    /**
     * The paints used to draw the axis lines.
     *
     * @since 1.0.17
     */
    private transient Map<Integer, Paint> axisLinePaints = new HashMap<Integer, Paint>();

    /**
     * The strokes used to draw the axis lines.
     *
     * @since 1.0.17
     */
    private transient Map<Integer, Stroke> axisLineStrokes = new HashMap<Integer, Stroke>();

    /**
     * The visibility of the axis lines
     *
     * @since 1.0.15
     */
    private Map<Integer, Boolean> axisLineVisible = new HashMap<Integer, Boolean>();


    /** The dataset. */
    private CategoryDataset dataset;

    /** The maximum value we are plotting against on each category axis */
    private double maxValue;

    /**
     * The data extract order (BY_ROW or BY_COLUMN). This denotes whether
     * the data series are stored in rows (in which case the category names are
     * derived from the column keys) or in columns (in which case the category
     * names are derived from the row keys).
     */
    private TableOrder dataExtractOrder;

    /** The starting angle. */
    private double startAngle;

    /** The direction for drawing the radar axis & plots. */
    private Rotation direction;

    /** The legend item shape. */
    private transient Shape legendItemShape;

    /** A map containing the paint for each series. */
    private transient Map<Integer, Paint> seriesPaintMap;

    /** The base series paint (fallback). */
    private transient Paint baseSeriesPaint;

    /** A map containing the outline paint for each series. */
    private transient Map<Integer, Paint> seriesOutlinePaintMap;

    /** The base series outline paint (fallback). */
    private transient Paint baseSeriesOutlinePaint;

    /** The series outline stroke list. */
    private StrokeList seriesOutlineStrokeList;

    /** The base series outline stroke (fallback). */
    private transient Stroke baseSeriesOutlineStroke;

    /** The font used to display the category labels. */
    private Font labelFont;

    /** The color used to draw the category labels. */
    private transient Paint labelPaint;

    /** The label generator. */
    private CategoryItemLabelGenerator labelGenerator;

    /** controls if the web polygons are filled or not */
    private boolean webFilled = true;

    /** A tooltip generator for the plot (<code>null</code> permitted). */
    private CategoryToolTipGenerator toolTipGenerator;

    /** A URL generator for the plot (<code>null</code> permitted). */
    private CategoryURLGenerator urlGenerator;

    /**
     * A list of domain markers.
     */
    private List<PolarCategoryMarker> domainMarkers = new ArrayList<PolarCategoryMarker>();

    /**
     * A list of value markers.
     */
    private List<ValueMarker> valueMarkers = new ArrayList<ValueMarker>();

    /**
     * A list of interval markers.
     */
    private List<IntervalMarker> intervalMarkers = new ArrayList<IntervalMarker>();


    /**
     * Creates a default plot with no dataset.
     */
    public SpiderWebPlot() {
        this(null);
    }

    /**
     * Creates a new spider web plot with the given dataset, with each row
     * representing a series.
     *
     * @param dataset the dataset (<code>null</code> permitted).
     */
    public SpiderWebPlot(CategoryDataset dataset) {
        this(dataset, TableOrder.BY_ROW);
    }

    /**
     * Creates a new spider web plot with the given dataset.
     *
     * @param dataset the dataset.
     * @param extract controls how data is extracted ({@link TableOrder#BY_ROW}
     *                or {@link TableOrder#BY_COLUMN}).
     */
    public SpiderWebPlot(CategoryDataset dataset, TableOrder extract) {
        super();
        if (extract == null) {
            throw new IllegalArgumentException("Null 'extract' argument.");
        }
        this.dataset = dataset;
        if (dataset != null) {
            dataset.addChangeListener(this);
        }

        this.dataExtractOrder = extract;
        this.headPercent = DEFAULT_HEAD;
        this.axisLabelGap = DEFAULT_AXIS_LABEL_GAP;

        this.interiorGapHorizontal = DEFAULT_INTERIOR_GAP;
        this.interiorGapVertical = DEFAULT_INTERIOR_GAP;
        this.startAngle = DEFAULT_START_ANGLE;
        this.direction = Rotation.CLOCKWISE;
        this.maxValue = DEFAULT_MAX_VALUE;

        this.seriesPaintMap = new HashMap<Integer, Paint>();
        this.baseSeriesPaint = null;

        this.seriesOutlinePaintMap = new HashMap<Integer, Paint>();
        this.baseSeriesOutlinePaint = DEFAULT_OUTLINE_PAINT;

        this.seriesOutlineStrokeList = new StrokeList();
        this.baseSeriesOutlineStroke = DEFAULT_OUTLINE_STROKE;

        this.labelFont = DEFAULT_LABEL_FONT;
        this.labelPaint = DEFAULT_LABEL_PAINT;
        this.labelGenerator = new StandardCategoryItemLabelGenerator();

        this.legendItemShape = DEFAULT_LEGEND_ITEM_CIRCLE;

        setAxisLineVisible(true);
    }

    /**
     * Returns a short string describing the type of plot.
     *
     * @return The plot type.
     */
    public String getPlotType() {
        // return localizationResources.getString("Radar_Plot");
        return ("Spider Web Plot");
    }

    /**
     * Returns the dataset.
     *
     * @return The dataset (possibly <code>null</code>).
     * @see #setDataset(CategoryDataset)
     */
    public CategoryDataset getDataset() {
        return this.dataset;
    }

    /**
     * Sets the dataset used by the plot and sends a {@link org.jfree.chart.event.PlotChangeEvent}
     * to all registered listeners.
     *
     * @param dataset the dataset (<code>null</code> permitted).
     * @see #getDataset()
     */
    public void setDataset(CategoryDataset dataset) {
        // if there is an existing dataset, remove the plot from the list of
        // change listeners...
        if (this.dataset != null) {
            this.dataset.removeChangeListener(this);
        }

        // set the new dataset, and register the chart as a change listener...
        this.dataset = dataset;
        if (dataset != null) {
            dataset.addChangeListener(this);
        }

        // send a dataset change event to self to trigger plot change event
        datasetChanged(new DatasetChangeEvent(this, dataset));
    }

    /**
     * Method to determine if the web chart is to be filled.
     *
     * @return A boolean.
     * @see #setWebFilled(boolean)
     */
    public boolean isWebFilled() {
        return this.webFilled;
    }

    /**
     * Sets the webFilled flag and sends a {@link org.jfree.chart.event.PlotChangeEvent} to all
     * registered listeners.
     *
     * @param flag the flag.
     * @see #isWebFilled()
     */
    public void setWebFilled(boolean flag) {
        this.webFilled = flag;
        fireChangeEvent();
    }

    /**
     * Returns the data extract order (by row or by column).
     *
     * @return The data extract order (never <code>null</code>).
     * @see #setDataExtractOrder(TableOrder)
     */
    public TableOrder getDataExtractOrder() {
        return this.dataExtractOrder;
    }

    /**
     * Sets the data extract order (by row or by column) and sends a
     * {@link org.jfree.chart.event.PlotChangeEvent}to all registered listeners.
     *
     * @param order the order (<code>null</code> not permitted).
     * @throws IllegalArgumentException if <code>order</code> is
     *                                  <code>null</code>.
     * @see #getDataExtractOrder()
     */
    public void setDataExtractOrder(TableOrder order) {
        if (order == null) {
            throw new IllegalArgumentException("Null 'order' argument");
        }
        this.dataExtractOrder = order;
        fireChangeEvent();
    }

    /**
     * Returns the head percent.
     *
     * @return The head percent.
     * @see #setHeadPercent(double)
     */
    public double getHeadPercent() {
        return this.headPercent;
    }

    /**
     * Sets the head percent and sends a {@link org.jfree.chart.event.PlotChangeEvent} to all
     * registered listeners.
     *
     * @param percent the percent.
     * @see #getHeadPercent()
     */
    public void setHeadPercent(double percent) {
        this.headPercent = percent;
        fireChangeEvent();
    }

    /**
     * Returns the start angle for the first radar axis.
     * <BR>
     * This is measured in degrees starting from 3 o'clock (Java Arc2D default)
     * and measuring anti-clockwise.
     *
     * @return The start angle.
     * @see #setStartAngle(double)
     */
    public double getStartAngle() {
        return this.startAngle;
    }

    /**
     * Sets the starting angle and sends a {@link org.jfree.chart.event.PlotChangeEvent} to all
     * registered listeners.
     * <p/>
     * The initial default value is 90 degrees, which corresponds to 12 o'clock.
     * A value of zero corresponds to 3 o'clock... this is the encoding used by
     * Java's Arc2D class.
     *
     * @param angle the angle (in degrees).
     * @see #getStartAngle()
     */
    public void setStartAngle(double angle) {
        this.startAngle = angle;
        fireChangeEvent();
    }

    /**
     * Adds a domain marker and fires a {@link org.jfree.chart.event.PlotChangeEvent}
     *
     * @param marker the marker to add, <code>null</code> values not allowed.
     */
    public void addDomainMarker(PolarCategoryMarker marker) {
        addDomainMarker(marker, true);
    }

    /**
     * Adds a domain marker.
     *
     * @param marker          the marker to add, <code>null</code> values not allowed.
     * @param fireChangeEvent whether or not to fire a change event.
     */
    public void addDomainMarker(PolarCategoryMarker marker, boolean fireChangeEvent) {
        if (marker == null) {
            throw new IllegalArgumentException("Null 'marker' argument");
        }
        this.domainMarkers.add(marker);
        if (fireChangeEvent) {
            fireChangeEvent();
        }
    }

    /**
     * Adds a value marker and fires a {@link org.jfree.chart.event.PlotChangeEvent}.
     * <p/>
     * The types that are accepted (and make sense) are {@link ValueMarker} and
     * {@link IntervalMarker}.
     *
     * @param marker the marker to add, <code>null</code> values not allowed.
     */
    public void addValueMarker(Marker marker) {
        addValueMarker(marker, true);
    }

    /**
     * Adds a value marker.
     * <p/>
     * The types that are accepted (and make sense) are {@link ValueMarker} and
     * {@link IntervalMarker}.
     *
     * @param marker          the marker to add, <code>null</code>
     *                        values not allowed.
     * @param fireChangeEvent whether or not to fire a change event.
     */
    public void addValueMarker(Marker marker, boolean fireChangeEvent) {
        if (marker == null) {
            throw new IllegalArgumentException("Null 'marker' argument");
        }
        if (marker instanceof ValueMarker) {
            this.valueMarkers.add((ValueMarker) marker);
        } else if (marker instanceof IntervalMarker) {
            this.intervalMarkers.add((IntervalMarker) marker);
        }
        if (fireChangeEvent) {
            fireChangeEvent();
        }
    }


    /**
     * Returns the maximum value any category axis can take.
     *
     * @return The maximum value.
     * @see #setMaxValue(double)
     */
    public double getMaxValue() {
        return this.maxValue;
    }

    /**
     * Sets the maximum value any category axis can take and sends
     * a {@link org.jfree.chart.event.PlotChangeEvent} to all registered listeners.
     *
     * @param value the maximum value.
     * @see #getMaxValue()
     */
    public void setMaxValue(double value) {
        this.maxValue = value;
        fireChangeEvent();
    }

    /**
     * Returns the direction in which the radar axes are drawn
     * (clockwise or anti-clockwise).
     *
     * @return The direction (never <code>null</code>).
     * @see #setDirection(Rotation)
     */
    public Rotation getDirection() {
        return this.direction;
    }

    /**
     * Sets the direction in which the radar axes are drawn and sends a
     * {@link org.jfree.chart.event.PlotChangeEvent} to all registered listeners.
     *
     * @param direction the direction (<code>null</code> not permitted).
     * @see #getDirection()
     */
    public void setDirection(Rotation direction) {
        if (direction == null) {
            throw new IllegalArgumentException("Null 'direction' argument.");
        }
        this.direction = direction;
        fireChangeEvent();
    }


    /**
     * Sets the interior gap and sends a {@link org.jfree.chart.event.PlotChangeEvent} to all
     * registered listeners. This controls the space between the edges of the
     * plot and the plot area itself (the region where the axis labels appear).
     *
     * @param percent the gap (as a percentage of the available drawing space).
     * @see #getInteriorGapHorizontal()
     * @see #getInteriorGapVertical()
     */
    public void setInteriorGap(double percent) {
        ParamChecks.requireInRange(percent, "percent", 0d, MAX_INTERIOR_GAP);
        if (this.interiorGapHorizontal != percent || this.interiorGapVertical != percent) {
            setInteriorGapHorizontal(percent, false);
            setInteriorGapVertical(percent, false);
            fireChangeEvent();
        }
    }

    /**
     * Returns the interior gap, measured as a percentage of the available
     * drawing space.
     *
     * @return The gap (as a percentage of the available drawing space).
     * @see #setInteriorGap(double)
     */
    public double getInteriorGapVertical() {
        return interiorGapVertical;
    }


    /**
     * Sets the vertical internal gap and fires a change event.
     *
     * @param percent         the gap (as a percentage of the available drawing space).
     */
    public void setInteriorGapVertical(double percent) {
        setInteriorGapVertical(percent, true);
    }

    /**
     * Sets the vertical internal gap.
     *
     * @param percent         the gap (as a percentage of the available drawing space).
     * @param fireChangeEvent whether or not to fire a change event.
     */

    public void setInteriorGapVertical(double percent, boolean fireChangeEvent) {
        ParamChecks.requireInRange(percent, "percent", 0d, MAX_INTERIOR_GAP);
        this.interiorGapVertical = percent;
        if (fireChangeEvent) {
            fireChangeEvent();
        }
    }

    /**
     * Returns the interior gap, measured as a percentage of the available
     * drawing space.
     *
     * @return The gap (as a percentage of the available drawing space).
     * @see #setInteriorGap(double)
     */
    public double getInteriorGapHorizontal() {
        return interiorGapHorizontal;
    }

    /**
     * Sets the horizontal internal gap.
     * Fires change event.
     *
     * @param percent the gap (as a percentage of the available drawing space).
     */
    public void setInteriorGapHorizontal(double percent) {
        setInteriorGapHorizontal(percent, true);
    }

    /**
     * Sets the horizontal internal gap.
     *
     * @param percent         the gap (as a percentage of the available drawing space).
     * @param fireChangeEvent whether or not to fire a change event.
     */
    public void setInteriorGapHorizontal(double percent, boolean fireChangeEvent) {
        ParamChecks.requireInRange(percent, "percent", 0d, MAX_INTERIOR_GAP);
        this.interiorGapHorizontal = percent;
        if (fireChangeEvent) {
            fireChangeEvent();
        }
    }

    /**
     * Returns the axis label gap.
     *
     * @return The axis label gap.
     * @see #setAxisLabelGap(double)
     */
    public double getAxisLabelGap() {
        return this.axisLabelGap;
    }

    /**
     * Sets the axis label gap and sends a {@link org.jfree.chart.event.PlotChangeEvent} to all
     * registered listeners.
     *
     * @param gap the gap.
     * @see #getAxisLabelGap()
     */
    public void setAxisLabelGap(double gap) {
        this.axisLabelGap = gap;
        fireChangeEvent();
    }


    /**
     * Returns the paint used to draw the axis line for the given key index.
     *
     * @param index the key to get the paint for (<code>null</code>
     *              not permitted).
     * @return the axis paint for the given rowKey, #DEFAULT_AXIS_LINE_PAINT
     * if the rowKey is not found.
     */
    public Paint getAxisLinePaint(int index) {
        Paint paint = axisLinePaints.get(index);
        if (paint == null) {
            return DEFAULT_AXIS_LINE_PAINT;
        }
        return paint;
    }


    /**
     * Sets the paint used to draw all the axis lines and sends a
     * {@link org.jfree.chart.event.PlotChangeEvent} to all registered listeners.
     *
     * @param paint the paint (<code>null</code> not permitted).
     * @see #getAxisLinePaint(int)
     * @since 1.0.4
     */
    public void setAxisLinePaint(Paint paint) {
        ParamChecks.nullNotPermitted(paint, "paint");
        List keys = getKeysByExtractOrder();
        for (int i = 0, keysSize = keys.size(); i < keysSize; i++) {
            axisLinePaints.put(i, paint);
        }
        fireChangeEvent();
    }


    /**
     * Sets the paint used to draw the axis lines for the given key index
     * and sends a {@link org.jfree.chart.event.PlotChangeEvent} to all registered listeners.
     * <p/>
     * Whether its a row key or column key depends on data extraction order.
     *
     * @param index the key index for which to set the axis line paint.
     * @param paint the paint (<code>null</code> not permitted).
     * @see #getAxisLinePaint(int)
     * @since 1.0.14
     */
    public void setAxisLinePaint(int index, Paint paint) {
        ParamChecks.nullNotPermitted(paint, "paint");
        axisLinePaints.put(index, paint);
        fireChangeEvent();
    }

    /**
     * Returns the stroke used to draw the axis lines for the given key index.
     * <p/>
     * Whether its a row key or column key depends on data extraction order.
     *
     * @param index the  key index for which to get the axis line stroke.
     * @return The stroke used to draw the axis lines (never <code>null</code>).
     * @see #setAxisLineStroke(int, Stroke)
     * @see #setAxisLineStroke(Stroke)
     * @see #getAxisLinePaint(int)
     * @since 1.0.4
     */
    public Stroke getAxisLineStroke(int index) {
        Stroke stroke = axisLineStrokes.get(index);
        if (stroke == null) {
            return DEFAULT_AXIS_LINE_STROKE;
        }
        return stroke;
    }

    /**
     * Sets the stroke used to draw the axis lines for all row keys and sends a
     * {@link org.jfree.chart.event.PlotChangeEvent} to all registered listeners.
     *
     * @param stroke the stroke (<code>null</code> not permitted).
     * @see #getAxisLineStroke(int)
     * @since 1.0.4
     */
    public void setAxisLineStroke(Stroke stroke) {
        ParamChecks.nullNotPermitted(stroke, "stroke");
        List keys = getKeysByExtractOrder();
        for (int i = 0, keysSize = keys.size(); i < keysSize; i++) {
            axisLineStrokes.put(i, stroke);
        }
        fireChangeEvent();
    }

    /**
     * Sets the stroke used to draw the axis lines for the given key index
     * and sends a {@link org.jfree.chart.event.PlotChangeEvent} to all registered listeners.
     * <p/>
     * Whether its a row key or column key depends on data extraction order.
     *
     * @param index  the key index for which to set the axis line stroke
     *               (<code>null</code> not permitted).
     * @param stroke the stroke (<code>null</code> not permitted).
     * @see #getAxisLineStroke(int)
     * @since 1.0.4
     */
    public void setAxisLineStroke(int index, Stroke stroke) {
        if (stroke == null) {
            throw new IllegalArgumentException("Null 'stroke' argument.");
        }
        axisLineStrokes.put(index, stroke);
        fireChangeEvent();
    }


    /**
     * Returns whether or not the axis line is visible for the given key.
     * <p/>
     * Whether its a row key or column key depends on data extraction order.
     *
     * @param index the index for which to get visibility of axis.
     * @return true if the axis line is visible, false otherwise.
     * @since 1.0.15
     */
    public boolean isAxisLineVisible(int index) {
        Boolean axisLineVisibleBoolean = axisLineVisible.get(index);
        return Boolean.TRUE.equals(axisLineVisibleBoolean);
    }

    /**
     * Sets whether or not all axis lines are visible and sends a
     * {@link org.jfree.chart.event.PlotChangeEvent} to all registered listeners.
     *
     * @param visible the visibility flag.
     * @since 1.0.15
     */
    public void setAxisLineVisible(boolean visible) {
        List keys = getKeysByExtractOrder();
        for (int i = 0, keysSize = keys.size(); i < keysSize; i++) {
            axisLineVisible.put(i, visible);
        }
        fireChangeEvent();
    }

    /**
     * Sets whether or not the axis line is visible for the given index
     * and sends a {@link org.jfree.chart.event.PlotChangeEvent} to all registered listeners.
     * <p/>
     * Whether its a row key or column key depends on data extraction order.
     *
     * @param index   the key index to set the visibility for.
     * @param visible the visibility flag.
     * @since 1.0.15
     */
    public void setAxisLineVisible(int index, boolean visible) {
        axisLineVisible.put(index, visible);
        fireChangeEvent();
    }

    /**
     * Gets the key list by data extract order.
     * <p/>
     * If #dataExtractOrder is {@link TableOrder#BY_ROW} column keys are used,
     * otherwise it's row keys.
     *
     * @return the list of keys.
     */
    private List getKeysByExtractOrder() {
        List keys;
        if (this.dataExtractOrder == TableOrder.BY_ROW) {
            keys = this.dataset.getColumnKeys();
        } else {
            keys = this.dataset.getRowKeys();
        }
        return keys;
    }

    //// SERIES PAINT /////////////////////////


    /**
     * Returns the paint for the specified series.
     *
     * @param series  the series index (zero-based).
     *
     * @return The paint (never <code>null</code>).
     *
     * @see #setSeriesPaint(int, Paint)
     */
    public Paint getSeriesPaint(int series) {
        Paint result = this.seriesPaintMap.get(series);
        if (result == null) {
            DrawingSupplier supplier = getDrawingSupplier();
            if (supplier != null) {
                Paint p = supplier.getNextPaint();
                this.seriesPaintMap.put(series, p);
                result = p;
            }
            else {
                result = this.baseSeriesPaint;
            }
        }
        return result;
    }

    /**
     * Sets the paint used to fill a series of the radar and sends a
     * {@link org.jfree.chart.event.PlotChangeEvent} to all registered listeners.
     *
     * @param series  the series index (zero-based).
     * @param paint   the paint (<code>null</code> permitted).
     *
     * @see #getSeriesPaint(int)
     */
    public void setSeriesPaint(int series, Paint paint) {
        this.seriesPaintMap.put(series, paint);
        fireChangeEvent();
    }

    /**
     * Returns the base series paint. This is used when no other paint is
     * available.
     *
     * @return The paint (never <code>null</code>).
     *
     * @see #setBaseSeriesPaint(Paint)
     */
    public Paint getBaseSeriesPaint() {
        return this.baseSeriesPaint;
    }

    /**
     * Sets the base series paint.
     *
     * @param paint the paint (<code>null</code> not permitted).
     *
     * @see #getBaseSeriesPaint()
     */
    public void setBaseSeriesPaint(Paint paint) {
        ParamChecks.nullNotPermitted(paint, "paint");
        this.baseSeriesPaint = paint;
        fireChangeEvent();
    }

    //// SERIES OUTLINE PAINT ////////////////////////////

    /**
     * Returns the paint for the specified series.
     *
     * @param series the series index (zero-based).
     * @return The paint (never <code>null</code>).
     */
    public Paint getSeriesOutlinePaint(int series) {
        Paint result = this.seriesOutlinePaintMap.get(series);
        if (result == null) {
            result = this.baseSeriesOutlinePaint;
        }
        return result;
    }

    /**
     * Sets the paint used to fill a series of the radar and sends a
     * {@link org.jfree.chart.event.PlotChangeEvent} to all registered listeners.
     *
     * @param series the series index (zero-based).
     * @param paint  the paint (<code>null</code> permitted).
     */
    public void setSeriesOutlinePaint(int series, Paint paint) {
        this.seriesOutlinePaintMap.put(series, paint);
        fireChangeEvent();
    }

    /**
     * Returns the base series paint. This is used when no other paint is
     * available.
     *
     * @return The paint (never <code>null</code>).
     */
    public Paint getBaseSeriesOutlinePaint() {
        return this.baseSeriesOutlinePaint;
    }

    /**
     * Sets the base series paint.
     *
     * @param paint the paint (<code>null</code> not permitted).
     */
    public void setBaseSeriesOutlinePaint(Paint paint) {
        if (paint == null) {
            throw new IllegalArgumentException("Null 'paint' argument.");
        }
        this.baseSeriesOutlinePaint = paint;
        fireChangeEvent();
    }

    //// SERIES OUTLINE STROKE /////////////////////

    /**
     * Returns the stroke for the specified series.
     *
     * @param series the series index (zero-based).
     * @return The stroke (never <code>null</code>).
     */
    public Stroke getSeriesOutlineStroke(int series) {
        Stroke result = this.seriesOutlineStrokeList.getStroke(series);
        if (result == null) {
            result = this.baseSeriesOutlineStroke;
        }
        return result;
    }

    /**
     * Sets the stroke used to fill a series of the radar and sends a
     * {@link org.jfree.chart.event.PlotChangeEvent} to all registered listeners.
     *
     * @param series the series index (zero-based).
     * @param stroke the stroke (<code>null</code> permitted).
     */
    public void setSeriesOutlineStroke(int series, Stroke stroke) {
        this.seriesOutlineStrokeList.setStroke(series, stroke);
        fireChangeEvent();
    }

    /**
     * Returns the base series stroke. This is used when no other stroke is
     * available.
     *
     * @return The stroke (never <code>null</code>).
     */
    public Stroke getBaseSeriesOutlineStroke() {
        return this.baseSeriesOutlineStroke;
    }

    /**
     * Sets the base series stroke.
     *
     * @param stroke the stroke (<code>null</code> not permitted).
     */
    public void setBaseSeriesOutlineStroke(Stroke stroke) {
        if (stroke == null) {
            throw new IllegalArgumentException("Null 'stroke' argument.");
        }
        this.baseSeriesOutlineStroke = stroke;
        fireChangeEvent();
    }

    /**
     * Returns the shape used for legend items.
     *
     * @return The shape (never <code>null</code>).
     * @see #setLegendItemShape(Shape)
     */
    public Shape getLegendItemShape() {
        return this.legendItemShape;
    }

    /**
     * Sets the shape used for legend items and sends a {@link org.jfree.chart.event.PlotChangeEvent}
     * to all registered listeners.
     *
     * @param shape the shape (<code>null</code> not permitted).
     * @see #getLegendItemShape()
     */
    public void setLegendItemShape(Shape shape) {
        if (shape == null) {
            throw new IllegalArgumentException("Null 'shape' argument.");
        }
        this.legendItemShape = shape;
        fireChangeEvent();
    }

    /**
     * Returns the series label font.
     *
     * @return The font (never <code>null</code>).
     * @see #setLabelFont(Font)
     */
    public Font getLabelFont() {
        return this.labelFont;
    }

    /**
     * Sets the series label font and sends a {@link org.jfree.chart.event.PlotChangeEvent} to all
     * registered listeners.
     *
     * @param font the font (<code>null</code> not permitted).
     * @see #getLabelFont()
     */
    public void setLabelFont(Font font) {
        if (font == null) {
            throw new IllegalArgumentException("Null 'font' argument.");
        }
        this.labelFont = font;
        fireChangeEvent();
    }

    /**
     * Returns the series label paint.
     *
     * @return The paint (never <code>null</code>).
     * @see #setLabelPaint(Paint)
     */
    public Paint getLabelPaint() {
        return this.labelPaint;
    }

    /**
     * Sets the series label paint and sends a {@link org.jfree.chart.event.PlotChangeEvent} to all
     * registered listeners.
     *
     * @param paint the paint (<code>null</code> not permitted).
     * @see #getLabelPaint()
     */
    public void setLabelPaint(Paint paint) {
        if (paint == null) {
            throw new IllegalArgumentException("Null 'paint' argument.");
        }
        this.labelPaint = paint;
        fireChangeEvent();
    }

    /**
     * Returns the label generator.
     *
     * @return The label generator (never <code>null</code>).
     * @see #setLabelGenerator(CategoryItemLabelGenerator)
     */
    public CategoryItemLabelGenerator getLabelGenerator() {
        return this.labelGenerator;
    }

    /**
     * Sets the label generator and sends a {@link org.jfree.chart.event.PlotChangeEvent} to all
     * registered listeners.
     *
     * @param generator the generator (<code>null</code> not permitted).
     * @see #getLabelGenerator()
     */
    public void setLabelGenerator(CategoryItemLabelGenerator generator) {
        if (generator == null) {
            throw new IllegalArgumentException("Null 'generator' argument.");
        }
        this.labelGenerator = generator;
    }

    /**
     * Returns the tool tip generator for the plot.
     *
     * @return The tool tip generator (possibly <code>null</code>).
     * @see #setToolTipGenerator(CategoryToolTipGenerator)
     * @since 1.0.2
     */
    public CategoryToolTipGenerator getToolTipGenerator() {
        return this.toolTipGenerator;
    }

    /**
     * Sets the tool tip generator for the plot and sends a
     * {@link org.jfree.chart.event.PlotChangeEvent} to all registered listeners.
     *
     * @param generator the generator (<code>null</code> permitted).
     * @see #getToolTipGenerator()
     * @since 1.0.2
     */
    public void setToolTipGenerator(CategoryToolTipGenerator generator) {
        this.toolTipGenerator = generator;
        fireChangeEvent();
    }

    /**
     * Returns the URL generator for the plot.
     *
     * @return The URL generator (possibly <code>null</code>).
     * @see #setURLGenerator(CategoryURLGenerator)
     * @since 1.0.2
     */
    public CategoryURLGenerator getURLGenerator() {
        return this.urlGenerator;
    }

    /**
     * Sets the URL generator for the plot and sends a
     * {@link org.jfree.chart.event.PlotChangeEvent} to all registered listeners.
     *
     * @param generator the generator (<code>null</code> permitted).
     * @see #getURLGenerator()
     * @since 1.0.2
     */
    public void setURLGenerator(CategoryURLGenerator generator) {
        this.urlGenerator = generator;
        fireChangeEvent();
    }

    /**
     * Returns a collection of legend items for the spider web chart.
     *
     * @return The legend items (never <code>null</code>).
     */
    @Override
    public List<LegendItem> getLegendItems() {
        List<LegendItem> result = new ArrayList<LegendItem>();
        if (getDataset() == null) {
            return result;
        }
        // TODO : support for fixed legend items
        List<Comparable> keys = null;
        if (this.dataExtractOrder == TableOrder.BY_ROW) {
            keys = this.dataset.getRowKeys();
        } else if (this.dataExtractOrder == TableOrder.BY_COLUMN) {
            keys = this.dataset.getColumnKeys();
        }
        if (keys == null) {
            return result;
        }

        int series = 0;
        Shape shape = getLegendItemShape();
        for (Comparable key : keys) {
            String label = key.toString();
            Paint paint = getSeriesPaint(series);
            Paint outlinePaint = getSeriesOutlinePaint(series);
            Stroke stroke = getSeriesOutlineStroke(series);
            LegendItem item = new LegendItem(label, label,
                    null, null, shape, paint, stroke, outlinePaint);
            item.setDataset(getDataset());
            item.setSeriesKey(key);
            item.setSeriesIndex(series);
            result.add(item);
            series++;
        }
        return result;
    }

    /**
     * Returns a cartesian point from a polar angle, length and bounding box
     *
     * @param bounds the area inside which the point needs to be.
     * @param angle  the polar angle, in degrees.
     * @param length the relative length. Given in percent of maximum extend.
     * @return The cartesian point.
     */
    protected Point2D getWebPoint(Rectangle2D bounds,
                                  double angle, double length) {

        double angrad = Math.toRadians(angle);
        double x = Math.cos(angrad) * length * bounds.getWidth() / 2;
        double y = -Math.sin(angrad) * length * bounds.getHeight() / 2;

        return new Point2D.Double(bounds.getX() + x + bounds.getWidth() / 2,
                bounds.getY() + y + bounds.getHeight() / 2);
    }

    /**
     * Draws the plot on a Java 2D graphics device (such as the screen or a
     * printer).
     *
     * @param g2          the graphics device.
     * @param area        the area within which the plot should be drawn.
     * @param anchor      the anchor point (<code>null</code> permitted).
     * @param parentState the state from the parent plot, if there is one.
     * @param info        collects info about the drawing.
     */
    @Override
    public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor,
                     PlotState parentState, PlotRenderingInfo info) {

        // adjust for insets...
        RectangleInsets insets = getInsets();
        insets.trim(area);

        if (info != null) {
            info.setPlotArea(area);
            info.setDataArea(area);
        }

        drawBackground(g2, area);
        drawOutline(g2, area);

        Shape savedClip = g2.getClip();

        g2.clip(area);
        Composite originalComposite = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                getForegroundAlpha()));

        if (!DatasetUtilities.isEmptyOrNull(this.dataset)) {
            int seriesCount;
            int catCount;
            if (this.dataExtractOrder == TableOrder.BY_ROW) {
                seriesCount = this.dataset.getRowCount();
                catCount = this.dataset.getColumnCount();
            } else {
                seriesCount = this.dataset.getColumnCount();
                catCount = this.dataset.getRowCount();
            }

            // ensure we have a maximum value to use on the axes
            if (this.maxValue == DEFAULT_MAX_VALUE) {
                calculateMaxValue(seriesCount, catCount);
            }

            // Next, setup the plot area

            // adjust the plot area by the interior spacing value
            double gapHorizontal = area.getWidth() * getInteriorGapHorizontal();
            double gapVertical = area.getHeight() * getInteriorGapVertical();

            double X = area.getX() + gapHorizontal / 2;
            double Y = area.getY() + gapVertical / 2;
            double W = area.getWidth() - gapHorizontal;
            double H = area.getHeight() - gapVertical;

            double headW = area.getWidth() * this.headPercent;
            double headH = area.getHeight() * this.headPercent;

            // make the chart area a square
            double min = Math.min(W, H) / 2;
            X = (X + X + W) / 2 - min;
            Y = (Y + Y + H) / 2 - min;
            W = 2 * min;
            H = 2 * min;


            Point2D centre = new Point2D.Double(X + W / 2, Y + H / 2);
            Rectangle2D radarArea = new Rectangle2D.Double(X, Y, W, H);

            if (info != null) {
                info.setPlotArea(radarArea);
            }

            drawIntervalMarkers(g2, radarArea, centre);

            drawValueMarkers(g2, area, centre);

            double lineLength =
                    calculateLineLength(centre, getWebPoint(radarArea, startAngle, 1));

            // draw the axis and category label
            for (int cat = 0; cat < catCount; cat++) {
                double angleDelta = getAngleDelta(catCount, cat);
                double categoryAngle = getStartAngle() + angleDelta;

                Point2D endPoint = getWebPoint(radarArea, categoryAngle, 1);
                // 1 = end of axis
                if (isAxisLineVisible(cat)) {
                    Line2D line = new Line2D.Double(centre, endPoint);
                    g2.setPaint(getAxisLinePaint(cat));
                    g2.setStroke(getAxisLineStroke(cat));
                    g2.draw(line);
                }

                List keys = getKeysByExtractOrder();
                Object key = keys.get(cat);
                List<PolarCategoryMarker> markers = findDomainMarkers(key);
                for (PolarCategoryMarker marker : markers) {
                    if (marker.getRenderType() == MarkerRenderType.LINE_CENTER) {
                        Line2D line = new Line2D.Double(centre, endPoint);
                        g2.setPaint(marker.getOutlinePaint());
                        g2.setStroke(marker.getOutlineStroke());
                        g2.draw(line);

                        if (marker.getLabel() != null) {
                            drawMarkerLabel(
                                    g2,
                                    centre,
                                    radarArea,
                                    lineLength,
                                    categoryAngle,
                                    H,
                                    marker);
                        }
                    } else {
                        double halfCategoryAngle = getAngleDelta(catCount * 2, 1);

                        double previousAngleTemp = categoryAngle - halfCategoryAngle;
                        double nextAngleTemp = categoryAngle + halfCategoryAngle;

                        double previousAngle = Math.max(nextAngleTemp, previousAngleTemp);
                        double nextAngle = Math.min(nextAngleTemp, previousAngleTemp);


                        double labelTransformationAngle;

                        if (marker.getRenderType() == MarkerRenderType.LINE_CENTER_LEFT) {
                            Line2D line = new Line2D.Double(centre, getWebPoint(radarArea, previousAngle, 1));
                            g2.setPaint(marker.getOutlinePaint());
                            g2.setStroke(marker.getOutlineStroke());
                            g2.draw(line);

                            labelTransformationAngle = previousAngle;
                        } else if (marker.getRenderType() == MarkerRenderType.LINE_CENTER_RIGHT) {
                            Line2D line = new Line2D.Double(centre, getWebPoint(radarArea, nextAngle, 1));
                            g2.setPaint(marker.getOutlinePaint());
                            g2.setStroke(marker.getOutlineStroke());
                            g2.draw(line);

                            labelTransformationAngle = nextAngle;
                        } else {
                            labelTransformationAngle = previousAngle;

                            Line2D line = new Line2D.Double(centre, getWebPoint(radarArea, nextAngle, 1));
                            g2.setPaint(marker.getOutlinePaint());
                            g2.setStroke(marker.getOutlineStroke());
                            g2.draw(line);

                            line = new Line2D.Double(centre, getWebPoint(radarArea, previousAngle, 1));
                            g2.setPaint(marker.getOutlinePaint());
                            g2.setStroke(marker.getOutlineStroke());
                            g2.draw(line);
                        }


                        if (marker.getLabel() != null) {
                            drawMarkerLabel(
                                    g2,
                                    centre,
                                    radarArea,
                                    lineLength,
                                    labelTransformationAngle,
                                    H,
                                    marker);
                        }
                    }

                }
                drawLabel(g2, radarArea, cat, categoryAngle, area.getWidth());
            }

            // Now actually plot each of the series polygons..
            for (int series = 0; series < seriesCount; series++) {
                drawRadarPoly(
                        g2, radarArea, info, series, catCount,
                        headH, headW);
            }
        } else {
            drawNoDataMessage(g2, area);
        }
        g2.setClip(savedClip);
        g2.setComposite(originalComposite);
        drawOutline(g2, area);
    }

    /**
     * Transforms the label bounds to the correct offset.
     *
     * @param markerArea      the rectangle surrounding the marker.
     * @param markerOffset    the marker offset.
     * @param labelOffsetType the label offset type.
     * @return The coordinates for drawing the marker label.
     */
    private Rectangle2D transformMarkerLabelBounds(
            Rectangle2D markerArea,
            RectangleInsets markerOffset,
            LengthAdjustmentType labelOffsetType) {

        return markerOffset.createAdjustedRectangle(
                markerArea,
                LengthAdjustmentType.CONTRACT, labelOffsetType);

    }

    /**
     * Draws a marker label.
     *
     * @param g2         the graphics on which to draw.
     * @param centre     the centre of the spiderweb chart.
     * @param lineLength the length of the axis lines.
     * @param height     the height of the area.
     * @param startAngle the angle of the marker.
     * @param marker     the marker to draw.
     */
    private void drawMarkerLabel(Graphics2D g2,
                                 Point2D centre,
                                 Rectangle2D radarArea,
                                 double lineLength,
                                 double startAngle,
                                 double height,
                                 PolarCategoryMarker marker) {
        FontRenderContext context = g2.getFontRenderContext();

        String label = marker.getLabel();

        Rectangle2D labelBounds = getLabelFont().getStringBounds(label, context);
        labelBounds = transformMarkerLabelBounds(
                labelBounds,
                marker.getLabelOffset(),
                LengthAdjustmentType.EXPAND);


        Composite savedComposite = g2.getComposite();
        AffineTransform savedTransform = g2.getTransform();

        g2.setComposite(
                AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g2.setPaint(marker.getLabelPaint());
        g2.setFont(marker.getLabelFont());


        if (marker.isLabelOnArc()) {
            double x1 = 0;

            double x2 = labelBounds.getX();
            double y2 = lineLength + labelBounds.getY();

            double vectorProduct = x1 * x2 + lineLength * y2;

            double sqrt1 = Math.sqrt(x1 * x1 + lineLength * lineLength);
            double sqrt2 = Math.sqrt(x2 * x2 + y2 * y2);

            double deltaRadians = Math.acos((vectorProduct / (sqrt1 * sqrt2)));

            double arcStartAngle =
                    calculateMarkerTextAngle(startAngle + 90) + deltaRadians;

            AffineTransform transform =
                    AffineTransform.getRotateInstance(
                            arcStartAngle,
                            centre.getX(),
                            centre.getY());

            double withoutGap = Math.max(radarArea.getHeight(), radarArea.getWidth());

            double percentage = getMiddleValueOfLastInterval() / this.maxValue;

            LineMetrics metrics = labelFont.getLineMetrics("9M", context);
            double radius = withoutGap * percentage - metrics.getHeight() / 2;
            double centerX = centre.getX() - (radius / 2d);
            double centerY = centre.getY() - (radius / 2d);

            Ellipse2D.Double arc =
                    new Ellipse2D.Double(centerX, centerY, radius, radius);

            g2.setPaint(marker.getLabelPaint());
            g2.setTransform(transform);
            g2.setStroke(new TextStroke(label, marker.getLabelFont()));
            g2.draw(arc);
        } else {
            AffineTransform transform =
                    AffineTransform.getRotateInstance(
                            calculateMarkerTextAngle(startAngle),
                            centre.getX(),
                            centre.getY());


            g2.setTransform(transform);

            Graphics2D labelGraphics
                    = (Graphics2D) g2.create(
                    (int) centre.getX(),
                    (int) (centre.getY() - lineLength),
                    (int) lineLength, (int) lineLength);

            TextBlock block = TextUtilities.createTextBlock(
                    label,
                    marker.getLabelFont(),
                    marker.getLabelPaint());

            block.draw(labelGraphics,
                    (float) labelBounds.getX(),
                    (float) -labelBounds.getY(),
                    TextBlockAnchor.CENTER_LEFT,
                    (float) labelBounds.getX(),
                    (float) -labelBounds.getY(),
                    Math.toRadians(marker.getLabelAngle()));


        }


        g2.setComposite(savedComposite);
        g2.setTransform(savedTransform);
    }

    private double getMiddleValueOfLastInterval() {
        double result = 0;
        if (!intervalMarkers.isEmpty()) {
            ArrayList<IntervalMarker> list = new ArrayList<IntervalMarker>(intervalMarkers);
            Collections.sort(list, new Comparator<IntervalMarker>() {
                public int compare(IntervalMarker o1, IntervalMarker o2) {
                    return -1 * Double.compare(o1.getEndValue(), o2.getEndValue());
                }
            });
            IntervalMarker marker = list.get(0);
            double middle = (marker.getEndValue() - marker.getStartValue()) / 2d;
            result = marker.getStartValue() + middle;
        }

        return result;

    }

    private double calculateMarkerTextAngle(double startAngle) {
        double textAngle = 90 - startAngle;
        if (textAngle < 0) {
            textAngle = 360 + textAngle;
        }
        return Math.toRadians(textAngle);
    }

    private double calculateLineLength(Point2D centre, Point2D endPoint) {
        double deltaX = endPoint.getX() - centre.getX();
        double deltaY = endPoint.getY() - centre.getY();

        double deltaXPow = Math.pow(deltaX, 2);
        double deltaYPow = Math.pow(deltaY, 2);

        return Math.sqrt(deltaXPow + deltaYPow);
    }

    /**
     * Finds the domain marker associated with the given key.
     *
     * @param key the key to find marker for.
     * @return the corresponding markers or an empty list if none exist.
     */
    private List<PolarCategoryMarker> findDomainMarkers(Object key) {
        ArrayList<PolarCategoryMarker> result = new ArrayList<PolarCategoryMarker>();
        for (PolarCategoryMarker marker : this.domainMarkers) {
            if (marker.getKey().equals(key)) {
                result.add(marker);
            }
        }
        return result;
    }

    private double getAngleDelta(int catCount, int cat) {
        return getDirection().getFactor() * cat * 360 / catCount;
    }

    private void drawIntervalMarkers(Graphics2D g2, Rectangle2D radarArea, Point2D centre) {
        for (IntervalMarker marker : this.intervalMarkers) {
            Point2D startPoint = getWebPoint(
                    radarArea, 90,
                    marker.getStartValue() / this.maxValue);
            Point2D endPoint = getWebPoint(
                    radarArea, 90,
                    marker.getEndValue() / this.maxValue);

            double radius = endPoint.distance(centre);

            Ellipse2D.Double highEnd =
                    new Ellipse2D.Double(
                            centre.getX() - radius,
                            centre.getY() - radius,
                            radius * 2d,
                            radius * 2d);

            Area markerArea = new Area(highEnd);


            radius = startPoint.distance(centre);

            Ellipse2D.Double lowEnd =
                    new Ellipse2D.Double(
                            centre.getX() - radius,
                            centre.getY() - radius,
                            radius * 2d,
                            radius * 2d);
            markerArea.subtract(new Area(lowEnd));

            g2.setPaint(marker.getPaint());
            g2.fill(markerArea);
        }
    }

    private void drawValueMarkers(Graphics2D g2, Rectangle2D area, Point2D centre) {
        for (ValueMarker marker : this.valueMarkers) {
            g2.setPaint(marker.getPaint());
            Point2D markerPoint = getWebPoint(
                    area, 90,
                    marker.getValue() / this.maxValue);
            double radius = markerPoint.distance(centre);

            Ellipse2D.Double markerCircle =
                    new Ellipse2D.Double(
                            centre.getX() - radius,
                            centre.getY() - radius,
                            radius * 2d,
                            radius * 2d);

            g2.setStroke(marker.getOutlineStroke());
            g2.draw(markerCircle);
        }
    }

    /**
     * loop through each of the series to get the maximum value
     * on each category axis
     *
     * @param seriesCount the number of series
     * @param catCount    the number of categories
     */
    private void calculateMaxValue(int seriesCount, int catCount) {
        double v;
        Number nV;

        for (int seriesIndex = 0; seriesIndex < seriesCount; seriesIndex++) {
            for (int catIndex = 0; catIndex < catCount; catIndex++) {
                nV = getPlotValue(seriesIndex, catIndex);
                if (nV != null) {
                    v = nV.doubleValue();
                    if (v > this.maxValue) {
                        this.maxValue = v;
                    }
                }
            }
        }
    }

    /**
     * Draws a radar plot polygon.
     *
     * @param g2       the graphics device.
     * @param plotArea the area we are plotting in (already adjusted).
     * @param info     chart rendering info.
     * @param series   the series within the dataset we are plotting
     * @param catCount the number of categories per radar plot
     * @param headH    the data point height
     * @param headW    the data point width
     */
    protected void drawRadarPoly(Graphics2D g2,
                                 Rectangle2D plotArea,
                                 PlotRenderingInfo info,
                                 int series, int catCount,
                                 double headH, double headW) {

        Polygon polygon = new Polygon();

        EntityCollection entities = null;
        if (info != null) {
            entities = info.getOwner().getEntityCollection();
        }

        // plot the data...
        for (int cat = 0; cat < catCount; cat++) {

            Number dataValue = getPlotValue(series, cat);

            if (dataValue != null) {
                double value = dataValue.doubleValue();

                if (value >= 0) { // draw the polygon series...

                    // Finds our starting angle from the centre for this axis

                    double angle = getStartAngle()
                            + (getAngleDelta(catCount, cat));

                    // The following angle calc will ensure there isn't a top
                    // vertical axis - this may be useful if you don't want any
                    // given criteria to 'appear' move important than the
                    // others..
                    //  + (getDirection().getFactor()
                    //        * (cat + 0.5) * 360 / catCount);

                    // find the point at the appropriate distance end point
                    // along the axis/angle identified above and add it to the
                    // polygon

                    Point2D point = getWebPoint(plotArea, angle,
                            value / this.maxValue);
                    polygon.addPoint((int) point.getX(), (int) point.getY());

                    // put an elipse at the point being plotted..

                    Paint paint = getSeriesPaint(series);
                    Paint outlinePaint = getSeriesOutlinePaint(series);
                    Stroke outlineStroke = getSeriesOutlineStroke(series);

                    Ellipse2D head = new Ellipse2D.Double(point.getX()
                            - headW / 2, point.getY() - headH / 2, headW,
                            headH
                    );
                    g2.setPaint(paint);
                    g2.fill(head);
                    g2.setStroke(outlineStroke);
                    g2.setPaint(outlinePaint);
                    g2.draw(head);

                    if (entities != null) {
                        int row;
                        int col;
                        if (this.dataExtractOrder == TableOrder.BY_ROW) {
                            row = series;
                            col = cat;
                        } else {
                            row = cat;
                            col = series;
                        }
                        String tip = null;
                        if (this.toolTipGenerator != null) {
                            tip = this.toolTipGenerator.generateToolTip(
                                    this.dataset, row, col);
                        }

                        String url = null;
                        if (this.urlGenerator != null) {
                            url = this.urlGenerator.generateURL(this.dataset,
                                    row, col);
                        }

                        Shape area = new Rectangle(
                                (int) (point.getX() - headW),
                                (int) (point.getY() - headH),
                                (int) (headW * 2), (int) (headH * 2));
                        CategoryItemEntity entity = new CategoryItemEntity(
                                area, tip, url, this.dataset,
                                this.dataset.getRowKey(row),
                                this.dataset.getColumnKey(col));
                        entities.add(entity);
                    }

                }
            }
        }
        // Plot the polygon

        Paint paint = getSeriesPaint(series);
        g2.setPaint(paint);
        g2.setStroke(getSeriesOutlineStroke(series));
        g2.draw(polygon);

        // Lastly, fill the web polygon if this is required

        if (this.webFilled) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                    0.1f));
            g2.fill(polygon);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                    getForegroundAlpha()));
        }
    }

    /**
     * Returns the value to be plotted at the interseries of the
     * series and the category.  This allows us to plot
     * <code>BY_ROW</code> or <code>BY_COLUMN</code> which basically is just
     * reversing the definition of the categories and data series being
     * plotted.
     *
     * @param series the series to be plotted.
     * @param cat    the category within the series to be plotted.
     * @return The value to be plotted (possibly <code>null</code>).
     * @see #getDataExtractOrder()
     */
    protected Number getPlotValue(int series, int cat) {
        Number value = null;
        if (this.dataExtractOrder == TableOrder.BY_ROW) {
            value = this.dataset.getValue(series, cat);
        } else if (this.dataExtractOrder == TableOrder.BY_COLUMN) {
            value = this.dataset.getValue(cat, series);
        }
        return value;
    }

    /**
     * Draws the label for one axis.
     *
     * @param g2         the graphics device.
     * @param plotArea   the plot area
     * @param cat        the category (zero-based index).
     * @param startAngle the starting angle.
     * @param chartWidth the horizontal gap of the plot
     */
    protected void drawLabel(
            Graphics2D g2, Rectangle2D plotArea, int cat,
            double startAngle, double chartWidth) {

        String label = getLabel(cat);
        g2.setFont(getLabelFont());

        Point2D labelLocation = calculateLabelLocation(
                plotArea,
                startAngle);


        float maxWidth;
        if (labelLocation.getX() < plotArea.getCenterX()) {
            maxWidth = (float) labelLocation.getX();
        } else if (labelLocation.getX() == plotArea.getCenterX()) {
            maxWidth = (float) chartWidth;
        } else {
            maxWidth = (float) (chartWidth - labelLocation.getX());
        }


        TextBlock block = TextUtilities.createTextBlock(
                label,
                getLabelFont(),
                getLabelPaint(),
                maxWidth,
                new G2TextMeasurer(g2));
        block.setLineAlignment(HorizontalAlignment.LEFT);


        Composite saveComposite = g2.getComposite();

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                1.0f));
        g2.setPaint(getLabelPaint());
        g2.setFont(getLabelFont());

        TextBlockAnchor anchor = TextBlockAnchor.CENTER_LEFT;
        if (labelLocation.getX() < plotArea.getCenterX()) {
            anchor = TextBlockAnchor.CENTER_RIGHT;
        } else if (labelLocation.getX() == plotArea.getCenterX()) {
            anchor = TextBlockAnchor.CENTER;
        }

        block.draw(g2,
                (float) labelLocation.getX(),
                (float) labelLocation.getY(),
                anchor);

        g2.setComposite(saveComposite);
    }

    private String getLabel(int cat) {
        String label;
        if (this.dataExtractOrder == TableOrder.BY_ROW) {
            // if series are in rows, then the categories are the column keys
            label = this.labelGenerator.generateColumnLabel(this.dataset, cat);
        } else {
            // if series are in columns, then the categories are the row keys
            label = this.labelGenerator.generateRowLabel(this.dataset, cat);
        }
        return label;
    }

    /**
     * Returns the location for a label.
     *
     * @param plotArea   the plot area
     * @param startAngle the start angle for the pie series.
     * @return The location for a label.
     */
    protected Point2D calculateLabelLocation(
            Rectangle2D plotArea,
            double startAngle) {
        Arc2D arc1 = new Arc2D.Double(plotArea, startAngle, 0, Arc2D.OPEN);
        Point2D point1 = arc1.getEndPoint();

        double deltaX = -(point1.getX() - plotArea.getCenterX())
                * this.axisLabelGap;
        double deltaY = -(point1.getY() - plotArea.getCenterY())
                * this.axisLabelGap;

        double labelX = point1.getX() - deltaX;
        double labelY = point1.getY() - deltaY;

        return new Point2D.Double(labelX, labelY);
    }

    /**
     * Tests this plot for equality with an arbitrary object.
     *
     * @param obj the object (<code>null</code> permitted).
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SpiderWebPlot)) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        SpiderWebPlot that = (SpiderWebPlot) obj;
        if (!this.dataExtractOrder.equals(that.dataExtractOrder)) {
            return false;
        }
        if (this.headPercent != that.headPercent) {
            return false;
        }
        if (this.interiorGapHorizontal != that.interiorGapHorizontal) {
            return false;
        }
        if (this.interiorGapVertical != that.interiorGapVertical) {
            return false;
        }
        if (this.startAngle != that.startAngle) {
            return false;
        }
        if (!this.direction.equals(that.direction)) {
            return false;
        }
        if (this.maxValue != that.maxValue) {
            return false;
        }
        if (this.webFilled != that.webFilled) {
            return false;
        }
        if (this.axisLabelGap != that.axisLabelGap) {
            return false;
        }
        if (!PaintUtils.equalMaps(axisLinePaints, that.axisLinePaints)) {
            return false;
        }
        if (!this.axisLineStrokes.equals(that.axisLineStrokes)) {
            return false;
        }
        if (!ShapeUtils.equal(this.legendItemShape, that.legendItemShape)) {
            return false;
        }
        if (!PaintUtils.equalMaps(this.seriesPaintMap, that.seriesPaintMap)) {
            return false;
        }
        if (!PaintUtils.equal(this.baseSeriesPaint, that.baseSeriesPaint)) {
            return false;
        }
        if (!PaintUtils.equalMaps(this.seriesOutlinePaintMap, that.seriesOutlinePaintMap)) {
            return false;
        }
        if (!PaintUtils.equal(this.baseSeriesOutlinePaint, that.baseSeriesOutlinePaint)) {
            return false;
        }
        if (!this.seriesOutlineStrokeList.equals(that.seriesOutlineStrokeList)) {
            return false;
        }
        if (!this.baseSeriesOutlineStroke.equals(that.baseSeriesOutlineStroke)) {
            return false;
        }
        if (!this.labelFont.equals(that.labelFont)) {
            return false;
        }
        if (!PaintUtils.equal(this.labelPaint, that.labelPaint)) {
            return false;
        }
        if (!this.labelGenerator.equals(that.labelGenerator)) {
            return false;
        }
        if (!ObjectUtils.equal(this.toolTipGenerator, that.toolTipGenerator)) {
            return false;
        }
        if (!ObjectUtils.equal(this.urlGenerator, that.urlGenerator)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a clone of this plot.
     *
     * @return A clone of this plot.
     * @throws CloneNotSupportedException if the plot cannot be cloned for
     *                                    any reason.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        SpiderWebPlot clone = (SpiderWebPlot) super.clone();
        clone.legendItemShape = ShapeUtils.clone(this.legendItemShape);
        clone.seriesPaintMap = new HashMap<Integer, Paint>(this.seriesPaintMap);
        clone.seriesOutlinePaintMap = new HashMap<Integer, Paint>(this.seriesOutlinePaintMap);
        clone.seriesOutlineStrokeList = (StrokeList) this.seriesOutlineStrokeList.clone();
        clone.axisLinePaints = new HashMap<Integer, Paint>(this.axisLinePaints);
        clone.axisLineVisible = new HashMap<Integer, Boolean>(this.axisLineVisible);
        clone.axisLineStrokes = new HashMap<Integer, Stroke>(this.axisLineStrokes);
        clone.valueMarkers= ObjectUtils.deepClone(this.valueMarkers);
        clone.intervalMarkers = ObjectUtils.deepClone(this.intervalMarkers);
        clone.domainMarkers = ObjectUtils.deepClone(this.domainMarkers);
        return clone;
    }

    /**
     * Provides serialization support.
     *
     * @param stream the output stream.
     * @throws IOException if there is an I/O error.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        SerialUtils.writeShape(this.legendItemShape, stream);
        SerialUtils.writePaint(this.baseSeriesPaint, stream);
        SerialUtils.writePaintMap(this.seriesPaintMap, stream);
        SerialUtils.writePaint(this.baseSeriesOutlinePaint, stream);
        SerialUtils.writePaintMap(this.seriesOutlinePaintMap, stream);
        SerialUtils.writeStroke(this.baseSeriesOutlineStroke, stream);
        SerialUtils.writePaint(this.labelPaint, stream);
        SerialUtils.writePaintMap(this.axisLinePaints, stream);
        SerialUtils.writeStrokeMap(this.axisLineStrokes, stream);
    }

    /**
     * Provides serialization support.
     *
     * @param stream the input stream.
     * @throws IOException            if there is an I/O error.
     * @throws ClassNotFoundException if there is a classpath problem.
     */
    private void readObject(ObjectInputStream stream) throws IOException,
            ClassNotFoundException {
        stream.defaultReadObject();
        this.legendItemShape = SerialUtils.readShape(stream);
        this.baseSeriesPaint = SerialUtils.readPaint(stream);
        this.seriesPaintMap = SerialUtils.readPaintMap(stream);
        this.baseSeriesOutlinePaint = SerialUtils.readPaint(stream);
        this.seriesOutlinePaintMap = SerialUtils.readPaintMap(stream);
        this.baseSeriesOutlineStroke = SerialUtils.readStroke(stream);
        this.labelPaint = SerialUtils.readPaint(stream);
        this.axisLinePaints = SerialUtils.readPaintMap(stream);
        this.axisLineStrokes = SerialUtils.readStrokeMap(stream);

        if (this.dataset != null) {
            this.dataset.addChangeListener(this);
        }
    }

}
