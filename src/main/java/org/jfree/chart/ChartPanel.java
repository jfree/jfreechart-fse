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
 * ---------------
 * ChartPanel.java
 * ---------------
 * (C) Copyright 2000-2014, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Andrzej Porebski;
 *                   Soren Caspersen;
 *                   Jonathan Nash;
 *                   Hans-Jurgen Greiner;
 *                   Andreas Schneider;
 *                   Daniel van Enckevort;
 *                   David M O'Donnell;
 *                   Arnaud Lelievre;
 *                   Matthias Rose;
 *                   Onno vd Akker;
 *                   Sergei Ivanov;
 *                   Ulrich Voigt - patch 2686040;
 *                   Alessandro Borges - patch 1460845;
 *                   Martin Hoeller;
 *                   Michael Zinsmaier;
 *
 * Changes (from 28-Jun-2001)
 * --------------------------
 * 28-Jun-2001 : Integrated buffering code contributed by S???ren
 *               Caspersen (DG);
 * 18-Sep-2001 : Updated header and fixed DOS encoding problem (DG);
 * 22-Nov-2001 : Added scaling to improve display of charts in small sizes (DG);
 * 26-Nov-2001 : Added property editing, saving and printing (DG);
 * 11-Dec-2001 : Transferred saveChartAsPNG method to new ChartUtilities
 *               class (DG);
 * 13-Dec-2001 : Added tooltips (DG);
 * 16-Jan-2002 : Added an optional crosshair, based on the implementation by
 *               Jonathan Nash. Renamed the tooltips class (DG);
 * 23-Jan-2002 : Implemented zooming based on code by Hans-Jurgen Greiner (DG);
 * 05-Feb-2002 : Improved tooltips setup.  Renamed method attemptSaveAs()
 *               --> doSaveAs() and made it public rather than private (DG);
 * 28-Mar-2002 : Added a new constructor (DG);
 * 09-Apr-2002 : Changed initialisation of tooltip generation, as suggested by
 *               Hans-Jurgen Greiner (DG);
 * 27-May-2002 : New interactive zooming methods based on code by Hans-Jurgen
 *               Greiner. Renamed JFreeChartPanel --> ChartPanel, moved
 *               constants to ChartPanelConstants interface (DG);
 * 31-May-2002 : Fixed a bug with interactive zooming and added a way to
 *               control if the zoom rectangle is filled in or drawn as an
 *               outline. A mouse drag gesture towards the top left now causes
 *               an autoRangeBoth() and is a way to undo zooms (AS);
 * 11-Jun-2002 : Reinstated handleClick method call in mouseClicked() to get
 *               crosshairs working again (DG);
 * 13-Jun-2002 : Added check for null popup menu in mouseDragged method (DG);
 * 18-Jun-2002 : Added get/set methods for minimum and maximum chart
 *               dimensions (DG);
 * 25-Jun-2002 : Removed redundant code (DG);
 * 27-Aug-2002 : Added get/set methods for popup menu (DG);
 * 26-Sep-2002 : Fixed errors reported by Checkstyle (DG);
 * 22-Oct-2002 : Added translation methods for screen <--> Java2D, contributed
 *               by Daniel van Enckevort (DG);
 * 05-Nov-2002 : Added a chart reference to the ChartMouseEvent class (DG);
 * 22-Nov-2002 : Added test in zoom method for inverted axes, supplied by
 *               David M O'Donnell (DG);
 * 14-Jan-2003 : Implemented ChartProgressListener interface (DG);
 * 14-Feb-2003 : Removed deprecated setGenerateTooltips method (DG);
 * 12-Mar-2003 : Added option to enforce filename extension (see bug id
 *               643173) (DG);
 * 08-Sep-2003 : Added internationalization via use of properties
 *               resourceBundle (RFE 690236) (AL);
 * 18-Sep-2003 : Added getScaleX() and getScaleY() methods (protected) as
 *               requested by Irv Thomae (DG);
 * 12-Nov-2003 : Added zooming support for the FastScatterPlot class (DG);
 * 24-Nov-2003 : Minor Javadoc updates (DG);
 * 04-Dec-2003 : Added anchor point for crosshair calculation (DG);
 * 17-Jan-2004 : Added new methods to set tooltip delays to be used in this
 *               chart panel. Refer to patch 877565 (MR);
 * 02-Feb-2004 : Fixed bug in zooming trigger and added zoomTriggerDistance
 *               attribute (DG);
 * 08-Apr-2004 : Changed getScaleX() and getScaleY() from protected to
 *               public (DG);
 * 15-Apr-2004 : Added zoomOutFactor and zoomInFactor (DG);
 * 21-Apr-2004 : Fixed zooming bug in mouseReleased() method (DG);
 * 13-Jul-2004 : Added check for null chart (DG);
 * 04-Oct-2004 : Renamed ShapeUtils --> ShapeUtilities (DG);
 * 11-Nov-2004 : Moved constants back in from ChartPanelConstants (DG);
 * 12-Nov-2004 : Modified zooming mechanism to support zooming within
 *               subplots (DG);
 * 26-Jan-2005 : Fixed mouse zooming for horizontal category plots (DG);
 * 11-Apr-2005 : Added getFillZoomRectangle() method, renamed
 *               setHorizontalZoom() --> setDomainZoomable(),
 *               setVerticalZoom() --> setRangeZoomable(), added
 *               isDomainZoomable() and isRangeZoomable(), added
 *               getHorizontalAxisTrace() and getVerticalAxisTrace(),
 *               renamed autoRangeBoth() --> restoreAutoBounds(),
 *               autoRangeHorizontal() --> restoreAutoDomainBounds(),
 *               autoRangeVertical() --> restoreAutoRangeBounds() (DG);
 * 12-Apr-2005 : Removed working areas, added getAnchorPoint() method,
 *               added protected accessors for tracelines (DG);
 * 18-Apr-2005 : Made constants final (DG);
 * 26-Apr-2005 : Removed LOGGER (DG);
 * 01-Jun-2005 : Fixed zooming for combined plots - see bug report
 *               1212039, fix thanks to Onno vd Akker (DG);
 * 25-Nov-2005 : Reworked event listener mechanism (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 01-Aug-2006 : Fixed minor bug in restoreAutoRangeBounds() (DG);
 * 04-Sep-2006 : Renamed attemptEditChartProperties() -->
 *               doEditChartProperties() and made public (DG);
 * 13-Sep-2006 : Don't generate ChartMouseEvents if the panel's chart is null
 *               (fixes bug 1556951) (DG);
 * 05-Mar-2007 : Applied patch 1672561 by Sergei Ivanov, to fix zoom rectangle
 *               drawing for dynamic charts (DG);
 * 17-Apr-2007 : Fix NullPointerExceptions in zooming for combined plots (DG);
 * 24-May-2007 : When the look-and-feel changes, update the popup menu if there
 *               is one (DG);
 * 06-Jun-2007 : Fixed coordinates for drawing buffer image (DG);
 * 24-Sep-2007 : Added zoomAroundAnchor flag, and handle clearing of chart
 *               buffer (DG);
 * 25-Oct-2007 : Added default directory attribute (DG);
 * 07-Nov-2007 : Fixed (rare) bug in refreshing off-screen image (DG);
 * 07-May-2008 : Fixed bug in zooming that triggered zoom for a rectangle
 *               outside of the data area (DG);
 * 08-May-2008 : Fixed serialization bug (DG);
 * 15-Aug-2008 : Increased default maxDrawWidth/Height (DG);
 * 18-Sep-2008 : Modified creation of chart buffer (DG);
 * 18-Dec-2008 : Use ResourceBundleWrapper - see patch 1607918 by
 *               Jess Thrysoee (DG);
 * 13-Jan-2009 : Fixed zooming methods to trigger only one plot
 *               change event (DG);
 * 16-Jan-2009 : Use XOR for zoom rectangle only if useBuffer is false (DG);
 * 18-Mar-2009 : Added mouse wheel support (DG);
 * 19-Mar-2009 : Added panning on mouse drag support - based on Ulrich
 *               Voigt's patch 2686040 (DG);
 * 26-Mar-2009 : Changed fillZoomRectangle default to true, and only change
 *               cursor for CTRL-mouse-click if panning is enabled (DG);
 * 01-Apr-2009 : Fixed panning, and added different mouse event mask for
 *               MacOSX (DG);
 * 08-Apr-2009 : Added copy to clipboard support, based on patch 1460845
 *               by Alessandro Borges (DG);
 * 09-Apr-2009 : Added overlay support (DG);
 * 10-Apr-2009 : Set chartBuffer background to match ChartPanel (DG);
 * 05-May-2009 : Match scaling (and insets) in doCopy() (DG);
 * 01-Jun-2009 : Check for null chart in mousePressed() method (DG);
 * 08-Jun-2009 : Fixed bug in setMouseWheelEnabled() (DG);
 * 06-Jul-2009 : Clear off-screen buffer to fully transparent (DG);
 * 10-Oct-2011 : localization fix: bug #3353913 (MH);
 * 15-Jun-2012 : Removed JCommon dependencies (DG);
 *
 */

package org.jfree.chart;

import java.awt.AWTEvent;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.event.EventListenerList;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.chart.editor.ChartEditor;
import org.jfree.chart.editor.ChartEditorManager;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.event.ChartChangeEvent;
import org.jfree.chart.event.ChartChangeListener;
import org.jfree.chart.event.ChartProgressEvent;
import org.jfree.chart.event.ChartProgressListener;
import org.jfree.chart.event.OverlayChangeEvent;
import org.jfree.chart.event.OverlayChangeListener;
import org.jfree.chart.panel.AbstractMouseHandler;
import org.jfree.chart.panel.Overlay;
import org.jfree.chart.panel.PanHandler;
import org.jfree.chart.panel.ZoomHandler;
import org.jfree.chart.panel.selectionhandler.SelectionManager;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.Zoomable;
import org.jfree.chart.util.ParamChecks;
import org.jfree.chart.util.ResourceBundleWrapper;
import org.jfree.chart.util.SerialUtils;

/**
 * A Swing GUI component for displaying a {@link JFreeChart} object.
 * <P>
 * The panel registers with the chart to receive notification of changes to any
 * component of the chart.  The chart is redrawn automatically whenever this
 * notification is received.
 */
public class ChartPanel extends JPanel implements ChartChangeListener,
        ChartProgressListener, ActionListener, MouseListener,
        MouseMotionListener, OverlayChangeListener, Printable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 6046366297214274674L;

    /**
     * Default setting for buffer usage.  The default has been changed to
     * <code>true</code> from version 1.0.13 onwards, because of a severe
     * performance problem with drawing the zoom rectangle using XOR (which
     * now happens only when the buffer is NOT used).
     */
    public static final boolean DEFAULT_BUFFER_USED = true;

    /** The default panel width. */
    public static final int DEFAULT_WIDTH = 680;

    /** The default panel height. */
    public static final int DEFAULT_HEIGHT = 420;

    /** The default limit below which chart scaling kicks in. */
    public static final int DEFAULT_MINIMUM_DRAW_WIDTH = 300;

    /** The default limit below which chart scaling kicks in. */
    public static final int DEFAULT_MINIMUM_DRAW_HEIGHT = 200;

    /** The default limit above which chart scaling kicks in. */
    public static final int DEFAULT_MAXIMUM_DRAW_WIDTH = 1024;

    /** The default limit above which chart scaling kicks in. */
    public static final int DEFAULT_MAXIMUM_DRAW_HEIGHT = 768;

    /** The minimum size required to perform a zoom on a rectangle */
    public static final int DEFAULT_ZOOM_TRIGGER_DISTANCE = 10;

    /** Properties action command. */
    public static final String PROPERTIES_COMMAND = "PROPERTIES";

    /**
     * Copy action command.
     *
     * @since 1.0.13
     */
    public static final String COPY_COMMAND = "COPY";

    /** Save action command. */
    public static final String SAVE_COMMAND = "SAVE";

    /** Action command to save as PNG. */
    private static final String SAVE_AS_PNG_COMMAND = "SAVE_AS_PNG";
    
    /** Action command to save as SVG. */
    private static final String SAVE_AS_SVG_COMMAND = "SAVE_AS_SVG";
    
    /** Action command to save as PDF. */
    private static final String SAVE_AS_PDF_COMMAND = "SAVE_AS_PDF";
    
    /** Print action command. */
    public static final String PRINT_COMMAND = "PRINT";

    /** Zoom in (both axes) action command. */
    public static final String ZOOM_IN_BOTH_COMMAND = "ZOOM_IN_BOTH";

    /** Zoom in (domain axis only) action command. */
    public static final String ZOOM_IN_DOMAIN_COMMAND = "ZOOM_IN_DOMAIN";

    /** Zoom in (range axis only) action command. */
    public static final String ZOOM_IN_RANGE_COMMAND = "ZOOM_IN_RANGE";

    /** Zoom out (both axes) action command. */
    public static final String ZOOM_OUT_BOTH_COMMAND = "ZOOM_OUT_BOTH";

    /** Zoom out (domain axis only) action command. */
    public static final String ZOOM_OUT_DOMAIN_COMMAND = "ZOOM_DOMAIN_BOTH";

    /** Zoom out (range axis only) action command. */
    public static final String ZOOM_OUT_RANGE_COMMAND = "ZOOM_RANGE_BOTH";

    /** Zoom reset (both axes) action command. */
    public static final String ZOOM_RESET_BOTH_COMMAND = "ZOOM_RESET_BOTH";

    /** Zoom reset (domain axis only) action command. */
    public static final String ZOOM_RESET_DOMAIN_COMMAND = "ZOOM_RESET_DOMAIN";

    /** Zoom reset (range axis only) action command. */
    public static final String ZOOM_RESET_RANGE_COMMAND = "ZOOM_RESET_RANGE";

    /** The chart that is displayed in the panel. */
    private JFreeChart chart;

    /** Storage for registered (chart) mouse listeners. */
    private transient EventListenerList chartMouseListeners;

    /** A flag that controls whether or not the off-screen buffer is used. */
    private boolean useBuffer;

    /** A flag that indicates that the buffer should be refreshed. */
    private boolean refreshBuffer;

    /** A buffer for the rendered chart. */
    private transient Image chartBuffer;

    /** The height of the chart buffer. */
    private int chartBufferHeight;

    /** The width of the chart buffer. */
    private int chartBufferWidth;

    /**
     * The minimum width for drawing a chart (uses scaling for smaller widths).
     */
    private int minimumDrawWidth;

    /**
     * The minimum height for drawing a chart (uses scaling for smaller
     * heights).
     */
    private int minimumDrawHeight;

    /**
     * The maximum width for drawing a chart (uses scaling for bigger
     * widths).
     */
    private int maximumDrawWidth;

    /**
     * The maximum height for drawing a chart (uses scaling for bigger
     * heights).
     */
    private int maximumDrawHeight;

    /** The popup menu for the frame. */
    private JPopupMenu popup;

    /** The drawing info collected the last time the chart was drawn. */
    private ChartRenderingInfo info;

    /** The chart anchor point. */
    private Point2D anchor;

    /** The scale factor used to draw the chart. */
    private double scaleX;

    /** The scale factor used to draw the chart. */
    private double scaleY;

    /** The plot orientation. */
    private PlotOrientation orientation = PlotOrientation.VERTICAL;

    /** A flag that controls whether or not domain zooming is enabled. */
    private boolean domainZoomable = false;

    /** A flag that controls whether or not range zooming is enabled. */
    private boolean rangeZoomable = false;

    /**
     * The zoom rectangle starting point (selected by the user with a mouse
     * click).  This is a point on the screen, not the chart (which may have
     * been scaled up or down to fit the panel).
     */
    private Point2D zoomPoint = null;

    /** The zoom rectangle (selected by the user with the mouse). */
    private transient Rectangle2D zoomRectangle = null;

    /** Controls if the zoom rectangle is drawn as an outline or filled. */
    private boolean fillZoomRectangle = true;

    /** The minimum distance required to drag the mouse to trigger a zoom. */
    private int zoomTriggerDistance;

    /** A flag that controls whether or not horizontal tracing is enabled. */
    private boolean horizontalAxisTrace = false;

    /** A flag that controls whether or not vertical tracing is enabled. */
    private boolean verticalAxisTrace = false;

    /** A vertical trace line. */
    private transient Line2D verticalTraceLine;

    /** A horizontal trace line. */
    private transient Line2D horizontalTraceLine;

    /** Menu item for zooming in on a chart (both axes). */
    private JMenuItem zoomInBothMenuItem;

    /** Menu item for zooming in on a chart (domain axis). */
    private JMenuItem zoomInDomainMenuItem;

    /** Menu item for zooming in on a chart (range axis). */
    private JMenuItem zoomInRangeMenuItem;

    /** Menu item for zooming out on a chart. */
    private JMenuItem zoomOutBothMenuItem;

    /** Menu item for zooming out on a chart (domain axis). */
    private JMenuItem zoomOutDomainMenuItem;

    /** Menu item for zooming out on a chart (range axis). */
    private JMenuItem zoomOutRangeMenuItem;

    /** Menu item for resetting the zoom (both axes). */
    private JMenuItem zoomResetBothMenuItem;

    /** Menu item for resetting the zoom (domain axis only). */
    private JMenuItem zoomResetDomainMenuItem;

    /** Menu item for resetting the zoom (range axis only). */
    private JMenuItem zoomResetRangeMenuItem;

    /**
     * The default directory for saving charts to file.
     *
     * @since 1.0.7
     */
    private File defaultDirectoryForSaveAs;

    /** A flag that controls whether or not file extensions are enforced. */
    private boolean enforceFileExtensions;

    /** A flag that indicates if original tooltip settings are changed. */
    private boolean toolTipSettingsActive;

    /** 
     * The tooltip initial delay to be used in this chart panel (defaults to
     * the global ToolTipManager setting). 
     */
    private int toolTipInitialDelay;

    /** 
     * The tooltip dismiss delay to be used in this chart panel (defaults to
     * the global ToolTipManager setting). 
     */
    private int toolTipDismissDelay;

    /**
     * The tooltip reshow delay to be used in this chart panel (defaults to
     * the global ToolTipManager setting). 
     */
    private int toolTipReshowDelay;

    /** Original initial tooltip delay of ToolTipManager.sharedInstance(). */
    private int originalToolTipInitialDelay;

    /** Original reshow tooltip delay of ToolTipManager.sharedInstance(). */
    private int originalToolTipReshowDelay;

    /** Original dismiss tooltip delay of ToolTipManager.sharedInstance(). */
    private int originalToolTipDismissDelay;

    /** The factor used to zoom in on an axis range. */
    private double zoomInFactor = 0.5;

    /** The factor used to zoom out on an axis range. */
    private double zoomOutFactor = 2.0;

    /**
     * A flag that controls whether zoom operations are centred on the
     * current anchor point, or the centre point of the relevant axis.
     *
     * @since 1.0.7
     */
    private boolean zoomAroundAnchor;

    /**
     * The paint used to draw the zoom rectangle outline.
     *
     * @since 1.0.13
     */
    private transient Paint zoomOutlinePaint;

    /**
     * The zoom fill paint (should use transparency).
     *
     * @since 1.0.13
     */
    private transient Paint zoomFillPaint;

    /** The resourceBundle for the localization. */
    protected static ResourceBundle localizationResources
            = ResourceBundleWrapper.getBundle(
                    "org.jfree.chart.LocalizationBundle");

    /**
     * A list of overlays for the panel.
     *
     * @since 1.0.13
     */
    private List<Overlay> overlays;

    //adding mouse handlers to support selection ...
    
    private SelectionManager selectionManager;

    /**
     * The mouse handlers that are available to deal with mouse events.
     * 
     * @since 1.0.14
     */
    private List<AbstractMouseHandler> availableLiveMouseHandlers;

    /**
     * The current "live" mouse handler. One of the handlers from the
     * 'availableMouseHandlers' list will be selected (typically in the
     * mousePressed() method) to be the live handler.
     * 
     * @since 1.0.14
     */
    private AbstractMouseHandler liveMouseHandler;

    /**
     * A list of auxiliary mouse handlers that will be called after the live
     * handler has done it's work.
     */
    private List<AbstractMouseHandler> auxiliaryMouseHandlers;

    /**
     * The zoom handler that is installed by default.
     * 
     * @since 1.0.14
     */
    private ZoomHandler zoomHandler;

    /**
     * The selection shape (may be <code>null</code>).
     */
    private Shape selectionShape;
     
    /**
     * The selection fill paint (may be <code>null</code>). 
     */
    private Paint selectionFillPaint;

    /**
     * The selection outline paint
     */
    private Paint selectionOutlinePaint = Color.darkGray;

    /**
     * The selection outline stroke
     */
    private transient Stroke selectionOutlineStroke = new BasicStroke(1.0f,
            BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 4.0f, new float[] {
                         3.0f, 3.0f }, 0.0f);

    /**
     * Constructs a panel that displays the specified chart.
     *
     * @param chart  the chart.
     */
    public ChartPanel(JFreeChart chart) {

        this(
            chart,
            DEFAULT_WIDTH,
            DEFAULT_HEIGHT,
            DEFAULT_MINIMUM_DRAW_WIDTH,
            DEFAULT_MINIMUM_DRAW_HEIGHT,
            DEFAULT_MAXIMUM_DRAW_WIDTH,
            DEFAULT_MAXIMUM_DRAW_HEIGHT,
            DEFAULT_BUFFER_USED,
            true,  // properties
            true,  // save
            true,  // print
            true,  // zoom
            true   // tooltips
        );

    }

    /**
     * Constructs a panel containing a chart.  The <code>useBuffer</code> flag
     * controls whether or not an offscreen <code>BufferedImage</code> is
     * maintained for the chart.  If the buffer is used, more memory is
     * consumed, but panel repaints will be a lot quicker in cases where the
     * chart itself hasn't changed (for example, when another frame is moved
     * to reveal the panel).  WARNING: If you set the <code>useBuffer</code>
     * flag to false, note that the mouse zooming rectangle will (in that case)
     * be drawn using XOR, and there is a SEVERE performance problem with that
     * on JRE6 on Windows.
     *
     * @param chart  the chart.
     * @param useBuffer  a flag controlling whether or not an off-screen buffer
     *                   is used (read the warning above before setting this
     *                   to <code>false</code>).
     */
    public ChartPanel(JFreeChart chart, boolean useBuffer) {

        this(chart, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_MINIMUM_DRAW_WIDTH,
                DEFAULT_MINIMUM_DRAW_HEIGHT, DEFAULT_MAXIMUM_DRAW_WIDTH,
                DEFAULT_MAXIMUM_DRAW_HEIGHT, useBuffer,
                true,  // properties
                true,  // save
                true,  // print
                true,  // zoom
                true   // tooltips
                );

    }

    /**
     * Constructs a JFreeChart panel.
     *
     * @param chart  the chart.
     * @param properties  a flag indicating whether or not the chart property
     *                    editor should be available via the popup menu.
     * @param save  a flag indicating whether or not save options should be
     *              available via the popup menu.
     * @param print  a flag indicating whether or not the print option
     *               should be available via the popup menu.
     * @param zoom  a flag indicating whether or not zoom options should
     *              be added to the popup menu.
     * @param tooltips  a flag indicating whether or not tooltips should be
     *                  enabled for the chart.
     */
    public ChartPanel(JFreeChart chart,
                      boolean properties,
                      boolean save,
                      boolean print,
                      boolean zoom,
                      boolean tooltips) {

        this(chart,
             DEFAULT_WIDTH,
             DEFAULT_HEIGHT,
             DEFAULT_MINIMUM_DRAW_WIDTH,
             DEFAULT_MINIMUM_DRAW_HEIGHT,
             DEFAULT_MAXIMUM_DRAW_WIDTH,
             DEFAULT_MAXIMUM_DRAW_HEIGHT,
             DEFAULT_BUFFER_USED,
             properties,
             save,
             print,
             zoom,
             tooltips
             );

    }

    /**
     * Constructs a JFreeChart panel.
     *
     * @param chart  the chart.
     * @param width  the preferred width of the panel.
     * @param height  the preferred height of the panel.
     * @param minimumDrawWidth  the minimum drawing width.
     * @param minimumDrawHeight  the minimum drawing height.
     * @param maximumDrawWidth  the maximum drawing width.
     * @param maximumDrawHeight  the maximum drawing height.
     * @param useBuffer  a flag that indicates whether to use the off-screen
     *                   buffer to improve performance (at the expense of
     *                   memory).
     * @param properties  a flag indicating whether or not the chart property
     *                    editor should be available via the popup menu.
     * @param save  a flag indicating whether or not save options should be
     *              available via the popup menu.
     * @param print  a flag indicating whether or not the print option
     *               should be available via the popup menu.
     * @param zoom  a flag indicating whether or not zoom options should be
     *              added to the popup menu.
     * @param tooltips  a flag indicating whether or not tooltips should be
     *                  enabled for the chart.
     */
    public ChartPanel(JFreeChart chart, int width, int height,
            int minimumDrawWidth, int minimumDrawHeight, int maximumDrawWidth,
            int maximumDrawHeight, boolean useBuffer, boolean properties,
            boolean save, boolean print, boolean zoom, boolean tooltips) {

        this(chart, width, height, minimumDrawWidth, minimumDrawHeight,
                maximumDrawWidth, maximumDrawHeight, useBuffer, properties,
                true, save, print, zoom, tooltips);
    }

    /**
     * Constructs a JFreeChart panel.
     *
     * @param chart  the chart.
     * @param width  the preferred width of the panel.
     * @param height  the preferred height of the panel.
     * @param minimumDrawWidth  the minimum drawing width.
     * @param minimumDrawHeight  the minimum drawing height.
     * @param maximumDrawWidth  the maximum drawing width.
     * @param maximumDrawHeight  the maximum drawing height.
     * @param useBuffer  a flag that indicates whether to use the off-screen
     *                   buffer to improve performance (at the expense of
     *                   memory).
     * @param properties  a flag indicating whether or not the chart property
     *                    editor should be available via the popup menu.
     * @param copy  a flag indicating whether or not a copy option should be
     *              available via the popup menu.
     * @param save  a flag indicating whether or not save options should be
     *              available via the popup menu.
     * @param print  a flag indicating whether or not the print option
     *               should be available via the popup menu.
     * @param zoom  a flag indicating whether or not zoom options should be
     *              added to the popup menu.
     * @param tooltips  a flag indicating whether or not tooltips should be
     *                  enabled for the chart.
     *
     * @since 1.0.13
     */
    public ChartPanel(JFreeChart chart, int width, int height,
           int minimumDrawWidth, int minimumDrawHeight, int maximumDrawWidth,
           int maximumDrawHeight, boolean useBuffer, boolean properties,
           boolean copy, boolean save, boolean print, boolean zoom,
           boolean tooltips) {

        setChart(chart);
        this.chartMouseListeners = new EventListenerList();
        this.info = new ChartRenderingInfo();
        setPreferredSize(new Dimension(width, height));
        this.useBuffer = useBuffer;
        this.refreshBuffer = false;
        this.minimumDrawWidth = minimumDrawWidth;
        this.minimumDrawHeight = minimumDrawHeight;
        this.maximumDrawWidth = maximumDrawWidth;
        this.maximumDrawHeight = maximumDrawHeight;
        this.zoomTriggerDistance = DEFAULT_ZOOM_TRIGGER_DISTANCE;

        // set up popup menu...
        this.popup = null;
        if (properties || copy || save || print || zoom) {
            this.popup = createPopupMenu(properties, copy, save, print, zoom);
        }

        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK);
        setDisplayToolTips(tooltips);
        addMouseListener(this);
        addMouseMotionListener(this);

        this.defaultDirectoryForSaveAs = null;
        this.enforceFileExtensions = true;

        // initialize ChartPanel-specific tool tip delays with
        // values the from ToolTipManager.sharedInstance()
        ToolTipManager ttm = ToolTipManager.sharedInstance();
        this.toolTipInitialDelay = ttm.getInitialDelay();
        this.toolTipDismissDelay = ttm.getDismissDelay();
        this.toolTipReshowDelay = ttm.getReshowDelay();

        this.zoomAroundAnchor = false;
        this.zoomOutlinePaint = Color.BLUE;
        this.zoomFillPaint = new Color(0, 0, 255, 63);

        this.overlays = new ArrayList<Overlay>();

        this.availableLiveMouseHandlers = new ArrayList<AbstractMouseHandler>();

        this.zoomHandler = new ZoomHandler();
        this.availableLiveMouseHandlers.add(zoomHandler);

        PanHandler panHandler = new PanHandler();
        int panMask = InputEvent.CTRL_MASK;
        // for MacOSX we can't use the CTRL key for mouse drags, see:
        // http://developer.apple.com/qa/qa2004/qa1362.html
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.startsWith("mac os x")) {
            panMask = InputEvent.ALT_MASK;
        }
        panHandler.setModifier(panMask);
        this.availableLiveMouseHandlers.add(panHandler);
        this.auxiliaryMouseHandlers = new ArrayList<AbstractMouseHandler>();
    }

    /**
     * Returns the chart contained in the panel.
     *
     * @return The chart (possibly <code>null</code>).
     */
    public JFreeChart getChart() {
        return this.chart;
    }

    /**
     * Sets the chart that is displayed in the panel.
     *
     * @param chart  the chart (<code>null</code> permitted).
     */
    public void setChart(JFreeChart chart) {

        // stop listening for changes to the existing chart
        if (this.chart != null) {
            this.chart.removeChangeListener(this);
            this.chart.removeProgressListener(this);
        }

        // add the new chart
        this.chart = chart;
        if (chart != null) {
            this.chart.addChangeListener(this);
            this.chart.addProgressListener(this);
            Plot plot = chart.getPlot();
            this.domainZoomable = false;
            this.rangeZoomable = false;
            if (plot instanceof Zoomable) {
                Zoomable z = (Zoomable) plot;
                this.domainZoomable = z.isDomainZoomable();
                this.rangeZoomable = z.isRangeZoomable();
                this.orientation = z.getOrientation();
            }
        }
        else {
            this.domainZoomable = false;
            this.rangeZoomable = false;
        }
        if (this.useBuffer) {
            this.refreshBuffer = true;
        }
        repaint();

    }

    /**
     * Returns the minimum drawing width for charts.
     * <P>
     * If the width available on the panel is less than this, then the chart is
     * drawn at the minimum width then scaled down to fit.
     *
     * @return The minimum drawing width.
     */
    public int getMinimumDrawWidth() {
        return this.minimumDrawWidth;
    }

    /**
     * Sets the minimum drawing width for the chart on this panel.
     * <P>
     * At the time the chart is drawn on the panel, if the available width is
     * less than this amount, the chart will be drawn using the minimum width
     * then scaled down to fit the available space.
     *
     * @param width  The width.
     */
    public void setMinimumDrawWidth(int width) {
        this.minimumDrawWidth = width;
    }

    /**
     * Returns the maximum drawing width for charts.
     * <P>
     * If the width available on the panel is greater than this, then the chart
     * is drawn at the maximum width then scaled up to fit.
     *
     * @return The maximum drawing width.
     */
    public int getMaximumDrawWidth() {
        return this.maximumDrawWidth;
    }

    /**
     * Sets the maximum drawing width for the chart on this panel.
     * <P>
     * At the time the chart is drawn on the panel, if the available width is
     * greater than this amount, the chart will be drawn using the maximum
     * width then scaled up to fit the available space.
     *
     * @param width  The width.
     */
    public void setMaximumDrawWidth(int width) {
        this.maximumDrawWidth = width;
    }

    /**
     * Returns the minimum drawing height for charts.
     * <P>
     * If the height available on the panel is less than this, then the chart
     * is drawn at the minimum height then scaled down to fit.
     *
     * @return The minimum drawing height.
     */
    public int getMinimumDrawHeight() {
        return this.minimumDrawHeight;
    }

    /**
     * Sets the minimum drawing height for the chart on this panel.
     * <P>
     * At the time the chart is drawn on the panel, if the available height is
     * less than this amount, the chart will be drawn using the minimum height
     * then scaled down to fit the available space.
     *
     * @param height  The height.
     */
    public void setMinimumDrawHeight(int height) {
        this.minimumDrawHeight = height;
    }

    /**
     * Returns the maximum drawing height for charts.
     * <P>
     * If the height available on the panel is greater than this, then the
     * chart is drawn at the maximum height then scaled up to fit.
     *
     * @return The maximum drawing height.
     */
    public int getMaximumDrawHeight() {
        return this.maximumDrawHeight;
    }

    /**
     * Sets the maximum drawing height for the chart on this panel.
     * <P>
     * At the time the chart is drawn on the panel, if the available height is
     * greater than this amount, the chart will be drawn using the maximum
     * height then scaled up to fit the available space.
     *
     * @param height  The height.
     */
    public void setMaximumDrawHeight(int height) {
        this.maximumDrawHeight = height;
    }

    /**
     * Returns the X scale factor for the chart.  This will be 1.0 if no
     * scaling has been used.
     *
     * @return The scale factor.
     */
    public double getScaleX() {
        return this.scaleX;
    }

    /**
     * Returns the Y scale factory for the chart.  This will be 1.0 if no
     * scaling has been used.
     *
     * @return The scale factor.
     */
    public double getScaleY() {
        return this.scaleY;
    }

    /**
     * Returns the anchor point.
     *
     * @return The anchor point (possibly <code>null</code>).
     */
    public Point2D getAnchor() {
        return this.anchor;
    }

    /**
     * Sets the anchor point.  This method is provided for the use of
     * subclasses, not end users.
     *
     * @param anchor  the anchor point (<code>null</code> permitted).
     */
    protected void setAnchor(Point2D anchor) {
        this.anchor = anchor;
    }

    /**
     * Returns the popup menu.
     *
     * @return The popup menu.
     */
    public JPopupMenu getPopupMenu() {
        return this.popup;
    }

    /**
     * Sets the popup menu for the panel.
     *
     * @param popup  the popup menu (<code>null</code> permitted).
     */
    public void setPopupMenu(JPopupMenu popup) {
        this.popup = popup;
    }

    /**
     * Returns the chart rendering info from the most recent chart redraw.
     *
     * @return The chart rendering info.
     */
    public ChartRenderingInfo getChartRenderingInfo() {
        return this.info;
    }

    /**
     * A convenience method that switches on mouse-based zooming.
     *
     * @param flag  <code>true</code> enables zooming and rectangle fill on
     *              zoom.
     */
    public void setMouseZoomable(boolean flag) {
        setMouseZoomable(flag, true);
    }

    /**
     * A convenience method that switches on mouse-based zooming.
     *
     * @param flag  <code>true</code> if zooming enabled
     * @param fillRectangle  <code>true</code> if zoom rectangle is filled,
     *                       false if rectangle is shown as outline only.
     */
    public void setMouseZoomable(boolean flag, boolean fillRectangle) {
        setDomainZoomable(flag);
        setRangeZoomable(flag);
        setFillZoomRectangle(fillRectangle);
    }

    /**
     * Returns the flag that determines whether or not zooming is enabled for
     * the domain axis.
     *
     * @return A boolean.
     */
    public boolean isDomainZoomable() {
        return this.domainZoomable;
    }

    /**
     * Sets the flag that controls whether or not zooming is enable for the
     * domain axis.  A check is made to ensure that the current plot supports
     * zooming for the domain values.
     *
     * @param flag  <code>true</code> enables zooming if possible.
     */
    public void setDomainZoomable(boolean flag) {
        if (flag) {
            Plot plot = this.chart.getPlot();
            if (plot instanceof Zoomable) {
                Zoomable z = (Zoomable) plot;
                this.domainZoomable = z.isDomainZoomable();
            }
        }
        else {
            this.domainZoomable = false;
        }
    }

    /**
     * Returns the flag that determines whether or not zooming is enabled for
     * the range axis.
     *
     * @return A boolean.
     */
    public boolean isRangeZoomable() {
        return this.rangeZoomable;
    }

    /**
     * A flag that controls mouse-based zooming on the vertical axis.
     *
     * @param flag  <code>true</code> enables zooming.
     */
    public void setRangeZoomable(boolean flag) {
        if (flag) {
            Plot plot = this.chart.getPlot();
            if (plot instanceof Zoomable) {
                Zoomable z = (Zoomable) plot;
                this.rangeZoomable = z.isRangeZoomable();
            }
        }
        else {
            this.rangeZoomable = false;
        }
    }

    /**
     * Returns the flag that controls whether or not the zoom rectangle is
     * filled when drawn.
     *
     * @return A boolean.
     */
    public boolean getFillZoomRectangle() {
        return this.fillZoomRectangle;
    }

    /**
     * A flag that controls how the zoom rectangle is drawn.
     *
     * @param flag  <code>true</code> instructs to fill the rectangle on
     *              zoom, otherwise it will be outlined.
     */
    public void setFillZoomRectangle(boolean flag) {
        this.fillZoomRectangle = flag;
    }

    /**
     * Returns the zoom trigger distance.  This controls how far the mouse must
     * move before a zoom action is triggered.
     *
     * @return The distance (in Java2D units).
     */
    public int getZoomTriggerDistance() {
        return this.zoomTriggerDistance;
    }

    /**
     * Sets the zoom trigger distance.  This controls how far the mouse must
     * move before a zoom action is triggered.
     *
     * @param distance  the distance (in Java2D units).
     */
    public void setZoomTriggerDistance(int distance) {
        this.zoomTriggerDistance = distance;
    }

    /**
     * Returns the flag that controls whether or not a horizontal axis trace
     * line is drawn over the plot area at the current mouse location.
     *
     * @return A boolean.
     */
    public boolean getHorizontalAxisTrace() {
        return this.horizontalAxisTrace;
    }

    /**
     * A flag that controls trace lines on the horizontal axis.
     *
     * @param flag  <code>true</code> enables trace lines for the mouse
     *      pointer on the horizontal axis.
     */
    public void setHorizontalAxisTrace(boolean flag) {
        this.horizontalAxisTrace = flag;
    }

    /**
     * Returns the horizontal trace line.
     *
     * @return The horizontal trace line (possibly <code>null</code>).
     */
    protected Line2D getHorizontalTraceLine() {
        return this.horizontalTraceLine;
    }

    /**
     * Sets the horizontal trace line.
     *
     * @param line  the line (<code>null</code> permitted).
     */
    protected void setHorizontalTraceLine(Line2D line) {
        this.horizontalTraceLine = line;
    }

    /**
     * Returns the flag that controls whether or not a vertical axis trace
     * line is drawn over the plot area at the current mouse location.
     *
     * @return A boolean.
     */
    public boolean getVerticalAxisTrace() {
        return this.verticalAxisTrace;
    }

    /**
     * A flag that controls trace lines on the vertical axis.
     *
     * @param flag  <code>true</code> enables trace lines for the mouse
     *              pointer on the vertical axis.
     */
    public void setVerticalAxisTrace(boolean flag) {
        this.verticalAxisTrace = flag;
    }

    /**
     * Returns the vertical trace line.
     *
     * @return The vertical trace line (possibly <code>null</code>).
     */
    protected Line2D getVerticalTraceLine() {
        return this.verticalTraceLine;
    }

    /**
     * Sets the vertical trace line.
     *
     * @param line  the line (<code>null</code> permitted).
     */
    protected void setVerticalTraceLine(Line2D line) {
        this.verticalTraceLine = line;
    }

    /**
     * Returns the default directory for the "save as" option.
     *
     * @return The default directory (possibly <code>null</code>).
     *
     * @since 1.0.7
     */
    public File getDefaultDirectoryForSaveAs() {
        return this.defaultDirectoryForSaveAs;
    }

    /**
     * Sets the default directory for the "save as" option.  If you set this
     * to <code>null</code>, the user's default directory will be used.
     *
     * @param directory  the directory (<code>null</code> permitted).
     *
     * @since 1.0.7
     */
    public void setDefaultDirectoryForSaveAs(File directory) {
        if (directory != null) {
            if (!directory.isDirectory()) {
                throw new IllegalArgumentException(
                        "The 'directory' argument is not a directory.");
            }
        }
        this.defaultDirectoryForSaveAs = directory;
    }

    /**
     * Returns <code>true</code> if file extensions should be enforced, and
     * <code>false</code> otherwise.
     *
     * @return The flag.
     *
     * @see #setEnforceFileExtensions(boolean)
     */
    public boolean isEnforceFileExtensions() {
        return this.enforceFileExtensions;
    }

    /**
     * Sets a flag that controls whether or not file extensions are enforced.
     *
     * @param enforce  the new flag value.
     *
     * @see #isEnforceFileExtensions()
     */
    public void setEnforceFileExtensions(boolean enforce) {
        this.enforceFileExtensions = enforce;
    }

    /**
     * Returns the flag that controls whether or not zoom operations are
     * centered around the current anchor point.
     *
     * @return A boolean.
     *
     * @since 1.0.7
     *
     * @see #setZoomAroundAnchor(boolean)
     */
    public boolean getZoomAroundAnchor() {
        return this.zoomAroundAnchor;
    }

    /**
     * Sets the flag that controls whether or not zoom operations are
     * centered around the current anchor point.
     *
     * @param zoomAroundAnchor  the new flag value.
     *
     * @since 1.0.7
     *
     * @see #getZoomAroundAnchor()
     */
    public void setZoomAroundAnchor(boolean zoomAroundAnchor) {
        this.zoomAroundAnchor = zoomAroundAnchor;
    }

    /**
     * Returns the zoom rectangle fill paint.
     *
     * @return The zoom rectangle fill paint (never <code>null</code>).
     *
     * @see #setZoomFillPaint(java.awt.Paint)
     * @see #setFillZoomRectangle(boolean)
     *
     * @since 1.0.13
     */
    public Paint getZoomFillPaint() {
        return this.zoomFillPaint;
    }

    /**
     * Sets the zoom rectangle fill paint.
     *
     * @param paint  the paint (<code>null</code> not permitted).
     *
     * @see #getZoomFillPaint()
     * @see #getFillZoomRectangle()
     *
     * @since 1.0.13
     */
    public void setZoomFillPaint(Paint paint) {
        ParamChecks.nullNotPermitted(paint, "paint");
        this.zoomFillPaint = paint;
    }

    /**
     * Returns the zoom rectangle outline paint.
     *
     * @return The zoom rectangle outline paint (never <code>null</code>).
     *
     * @see #setZoomOutlinePaint(java.awt.Paint)
     * @see #setFillZoomRectangle(boolean)
     *
     * @since 1.0.13
     */
    public Paint getZoomOutlinePaint() {
        return this.zoomOutlinePaint;
    }

    /**
     * Sets the zoom rectangle outline paint.
     *
     * @param paint  the paint (<code>null</code> not permitted).
     *
     * @see #getZoomOutlinePaint()
     * @see #getFillZoomRectangle()
     *
     * @since 1.0.13
     */
    public void setZoomOutlinePaint(Paint paint) {
        this.zoomOutlinePaint = paint;
    }

    /**
     * The mouse wheel handler.
     */
    private MouseWheelHandler mouseWheelHandler;

    /**
     * Returns <code>true</code> if the mouse wheel handler is enabled, and
     * <code>false</code> otherwise.
     *
     * @return A boolean.
     *
     * @since 1.0.13
     */
    public boolean isMouseWheelEnabled() {
        return this.mouseWheelHandler != null;
    }

    /**
     * Enables or disables mouse wheel support for the panel.
     * Note that this method does nothing when running JFreeChart on JRE 1.3.1,
     * because that older version of the Java runtime does not support
     * mouse wheel events.
     *
     * @param flag  a boolean.
     *
     * @since 1.0.13
     */
    public void setMouseWheelEnabled(boolean flag) {
        if (flag && this.mouseWheelHandler == null) {
            this.mouseWheelHandler = new MouseWheelHandler(this);
        }
        else if (!flag && this.mouseWheelHandler != null) {
            this.removeMouseWheelListener(this.mouseWheelHandler);
            this.mouseWheelHandler = null;
        }
    }

    /**
     * Add an overlay to the panel.
     *
     * @param overlay  the overlay (<code>null</code> not permitted).
     *
     * @since 1.0.13
     */
    public void addOverlay(Overlay overlay) {
        ParamChecks.nullNotPermitted(overlay, "overlay");
        this.overlays.add(overlay);
        overlay.addChangeListener(this);
        repaint();
    }

    /**
     * Removes an overlay from the panel.
     *
     * @param overlay  the overlay to remove (<code>null</code> not permitted).
     *
     * @since 1.0.13
     */
    public void removeOverlay(Overlay overlay) {
        ParamChecks.nullNotPermitted(overlay, "overlay");
        boolean removed = this.overlays.remove(overlay);
        if (removed) {
            overlay.removeChangeListener(this);
            repaint();
        }
    }

    /**
     * Handles a change to an overlay by repainting the panel.
     *
     * @param event  the event.
     *
     * @since 1.0.13
     */
    @Override
    public void overlayChanged(OverlayChangeEvent event) {
        repaint();
    }

    /**
     * Switches the display of tooltips for the panel on or off.  Note that
     * tooltips can only be displayed if the chart has been configured to
     * generate tooltip items.
     *
     * @param flag  <code>true</code> to enable tooltips, <code>false</code> to
     *              disable tooltips.
     */
    public void setDisplayToolTips(boolean flag) {
        if (flag) {
            ToolTipManager.sharedInstance().registerComponent(this);
        }
        else {
            ToolTipManager.sharedInstance().unregisterComponent(this);
        }
    }

    /**
     * Returns a string for the tooltip.
     *
     * @param e  the mouse event.
     *
     * @return A tool tip or <code>null</code> if no tooltip is available.
     */
    @Override
    public String getToolTipText(MouseEvent e) {
        String result = null;
        if (this.info != null) {
            EntityCollection entities = this.info.getEntityCollection();
            if (entities != null) {
                Insets insets = getInsets();
                ChartEntity entity = entities.getEntity(
                        (int) ((e.getX() - insets.left) / this.scaleX),
                        (int) ((e.getY() - insets.top) / this.scaleY));
                if (entity != null) {
                    result = entity.getToolTipText();
                }
            }
        }
        return result;
    }

    /**
     * Translates a Java2D point on the chart to a screen location.
     *
     * @param java2DPoint  the Java2D point.
     *
     * @return The screen location.
     */
    public Point translateJava2DToScreen(Point2D java2DPoint) {
        Insets insets = getInsets();
        int x = (int) (java2DPoint.getX() * this.scaleX + insets.left);
        int y = (int) (java2DPoint.getY() * this.scaleY + insets.top);
        return new Point(x, y);
    }

    /**
     * Translates a panel (component) location to a Java2D point.
     *
     * @param screenPoint  the screen location (<code>null</code> not
     *                     permitted).
     *
     * @return The Java2D coordinates.
     */
    public Point2D translateScreenToJava2D(Point screenPoint) {
        Insets insets = getInsets();
        double x = (screenPoint.getX() - insets.left) / this.scaleX;
        double y = (screenPoint.getY() - insets.top) / this.scaleY;
        return new Point2D.Double(x, y);
    }

    /**
     * Applies any scaling that is in effect for the chart drawing to the
     * given rectangle.
     *
     * @param rect  the rectangle (<code>null</code> not permitted).
     *
     * @return A new scaled rectangle.
     */
    public Rectangle2D scale(Rectangle2D rect) {
        Insets insets = getInsets();
        double x = rect.getX() * getScaleX() + insets.left;
        double y = rect.getY() * getScaleY() + insets.top;
        double w = rect.getWidth() * getScaleX();
        double h = rect.getHeight() * getScaleY();
        return new Rectangle2D.Double(x, y, w, h);
    }

    /**
     * Returns the chart entity at a given point.
     * <P>
     * This method will return null if there is (a) no entity at the given
     * point, or (b) no entity collection has been generated.
     *
     * @param viewX  the x-coordinate.
     * @param viewY  the y-coordinate.
     *
     * @return The chart entity (possibly <code>null</code>).
     */
    public ChartEntity getEntityForPoint(int viewX, int viewY) {

        ChartEntity result = null;
        if (this.info != null) {
            Insets insets = getInsets();
            double x = (viewX - insets.left) / this.scaleX;
            double y = (viewY - insets.top) / this.scaleY;
            EntityCollection entities = this.info.getEntityCollection();
            result = entities != null ? entities.getEntity(x, y) : null;
        }
        return result;

    }

    /**
     * Returns the flag that controls whether or not the offscreen buffer
     * needs to be refreshed.
     *
     * @return A boolean.
     */
    public boolean getRefreshBuffer() {
        return this.refreshBuffer;
    }

    /**
     * Sets the refresh buffer flag.  This flag is used to avoid unnecessary
     * redrawing of the chart when the offscreen image buffer is used.
     *
     * @param flag  <code>true</code> indicates that the buffer should be
     *              refreshed.
     */
    public void setRefreshBuffer(boolean flag) {
        this.refreshBuffer = flag;
    }

    /**
     * Paints the component by drawing the chart to fill the entire component,
     * but allowing for the insets (which will be non-zero if a border has been
     * set for this component).  To increase performance (at the expense of
     * memory), an off-screen buffer image can be used.
     *
     * @param g  the graphics device for drawing on.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.chart == null) {
            return;
        }
        Graphics2D g2 = (Graphics2D) g.create();

        // first determine the size of the chart rendering area...
        Dimension size = getSize();
        Insets insets = getInsets();
        Rectangle2D available = new Rectangle2D.Double(insets.left, insets.top,
                size.getWidth() - insets.left - insets.right,
                size.getHeight() - insets.top - insets.bottom);

        // work out if scaling is required...
        boolean scale = false;
        double drawWidth = available.getWidth();
        double drawHeight = available.getHeight();
        this.scaleX = 1.0;
        this.scaleY = 1.0;

        if (drawWidth < this.minimumDrawWidth) {
            this.scaleX = drawWidth / this.minimumDrawWidth;
            drawWidth = this.minimumDrawWidth;
            scale = true;
        }
        else if (drawWidth > this.maximumDrawWidth) {
            this.scaleX = drawWidth / this.maximumDrawWidth;
            drawWidth = this.maximumDrawWidth;
            scale = true;
        }

        if (drawHeight < this.minimumDrawHeight) {
            this.scaleY = drawHeight / this.minimumDrawHeight;
            drawHeight = this.minimumDrawHeight;
            scale = true;
        }
        else if (drawHeight > this.maximumDrawHeight) {
            this.scaleY = drawHeight / this.maximumDrawHeight;
            drawHeight = this.maximumDrawHeight;
            scale = true;
        }

        Rectangle2D chartArea = new Rectangle2D.Double(0.0, 0.0, drawWidth,
                drawHeight);

        // are we using the chart buffer?
        if (this.useBuffer) {

            // do we need to resize the buffer?
            if ((this.chartBuffer == null)
                    || (this.chartBufferWidth != available.getWidth())
                    || (this.chartBufferHeight != available.getHeight())) {
                this.chartBufferWidth = (int) available.getWidth();
                this.chartBufferHeight = (int) available.getHeight();
                GraphicsConfiguration gc = g2.getDeviceConfiguration();
                this.chartBuffer = gc.createCompatibleImage(
                        this.chartBufferWidth, this.chartBufferHeight,
                        Transparency.TRANSLUCENT);
                this.refreshBuffer = true;
            }

            // do we need to redraw the buffer?
            if (this.refreshBuffer) {

                this.refreshBuffer = false; // clear the flag

                Rectangle2D bufferArea = new Rectangle2D.Double(
                        0, 0, this.chartBufferWidth, this.chartBufferHeight);

                // make the background of the buffer clear and transparent
                Graphics2D bufferG2 = (Graphics2D)
                        this.chartBuffer.getGraphics();
                Composite savedComposite = bufferG2.getComposite();
                bufferG2.setComposite(AlphaComposite.getInstance(
                        AlphaComposite.CLEAR, 0.0f));
                Rectangle r = new Rectangle(0, 0, this.chartBufferWidth,
                        this.chartBufferHeight);
                bufferG2.fill(r);
                bufferG2.setComposite(savedComposite);

                if (scale) {
                    AffineTransform saved = bufferG2.getTransform();
                    AffineTransform st = AffineTransform.getScaleInstance(
                            this.scaleX, this.scaleY);
                    bufferG2.transform(st);
                    this.chart.draw(bufferG2, chartArea, this.anchor,
                            this.info);
                    bufferG2.setTransform(saved);
                }
                else {
                    this.chart.draw(bufferG2, bufferArea, this.anchor,
                            this.info);
                }

            }

            // zap the buffer onto the panel...
            g2.drawImage(this.chartBuffer, insets.left, insets.top, this);

        }

        // or redrawing the chart every time...
        else {

            AffineTransform saved = g2.getTransform();
            g2.translate(insets.left, insets.top);
            if (scale) {
                AffineTransform st = AffineTransform.getScaleInstance(
                        this.scaleX, this.scaleY);
                g2.transform(st);
            }
            this.chart.draw(g2, chartArea, this.anchor, this.info);
            g2.setTransform(saved);

        }

        for (Overlay overlay : this.overlays) {
            overlay.paintOverlay(g2, this);
        }

        // redraw the zoom rectangle (if present) - if useBuffer is false,
        // we use XOR so we can XOR the rectangle away again without redrawing
        // the chart
        drawZoomRectangle(g2, !this.useBuffer);
        drawSelectionShape(g2, !this.useBuffer);
        g2.dispose();

        this.anchor = null;
        this.verticalTraceLine = null;
        this.horizontalTraceLine = null;

    }

    /**
     * Receives notification of changes to the chart, and redraws the chart.
     *
     * @param event  details of the chart change event.
     */
    @Override
    public void chartChanged(ChartChangeEvent event) {
        this.refreshBuffer = true;
        Plot plot = this.chart.getPlot();
        if (plot instanceof Zoomable) {
            Zoomable z = (Zoomable) plot;
            this.orientation = z.getOrientation();
        }
        repaint();
    }

    /**
     * Receives notification of a chart progress event.
     *
     * @param event  the event.
     */
    @Override
    public void chartProgress(ChartProgressEvent event) {
        // does nothing - override if necessary
    }

    /**
     * Handles action events generated by the popup menu.
     *
     * @param event  the event.
     */
    @Override
    public void actionPerformed(ActionEvent event) {

        String command = event.getActionCommand();

        // many of the zoom methods need a screen location - all we have is
        // the zoomPoint, but it might be null.  Here we grab the x and y
        // coordinates, or use defaults...
        double screenX = -1.0;
        double screenY = -1.0;
        if (this.zoomPoint != null) {
            screenX = this.zoomPoint.getX();
            screenY = this.zoomPoint.getY();
        }

        if (command.equals(PROPERTIES_COMMAND)) {
            doEditChartProperties();
        }
        else if (command.equals(COPY_COMMAND)) {
            doCopy();
        }
        else if (command.equals(SAVE_AS_PNG_COMMAND)) {
            try {
                doSaveAs();
            }
            catch (IOException e) {
                JOptionPane.showMessageDialog(this, "I/O error occurred.", 
                        "Save As PNG", JOptionPane.WARNING_MESSAGE);
            }
        }
        else if (command.equals(SAVE_AS_SVG_COMMAND)) {
            try {
                saveAsSVG(null);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "I/O error occurred.", 
                        "Save As SVG", JOptionPane.WARNING_MESSAGE);
            }
        }
        else if (command.equals(SAVE_AS_PDF_COMMAND)) {
            saveAsPDF(null);
        }
        else if (command.equals(PRINT_COMMAND)) {
            createChartPrintJob();
        }
        else if (command.equals(ZOOM_IN_BOTH_COMMAND)) {
            zoomInBoth(screenX, screenY);
        }
        else if (command.equals(ZOOM_IN_DOMAIN_COMMAND)) {
            zoomInDomain(screenX, screenY);
        }
        else if (command.equals(ZOOM_IN_RANGE_COMMAND)) {
            zoomInRange(screenX, screenY);
        }
        else if (command.equals(ZOOM_OUT_BOTH_COMMAND)) {
            zoomOutBoth(screenX, screenY);
        }
        else if (command.equals(ZOOM_OUT_DOMAIN_COMMAND)) {
            zoomOutDomain(screenX, screenY);
        }
        else if (command.equals(ZOOM_OUT_RANGE_COMMAND)) {
            zoomOutRange(screenX, screenY);
        }
        else if (command.equals(ZOOM_RESET_BOTH_COMMAND)) {
            restoreAutoBounds();
        }
        else if (command.equals(ZOOM_RESET_DOMAIN_COMMAND)) {
            restoreAutoDomainBounds();
        }
        else if (command.equals(ZOOM_RESET_RANGE_COMMAND)) {
            restoreAutoRangeBounds();
        }

    }

    /**
     * Handles a 'mouse entered' event. This method changes the tooltip delays
     * of ToolTipManager.sharedInstance() to the possibly different values set
     * for this chart panel.
     *
     * @param e  the mouse event.
     */
    @Override
    public void mouseEntered(MouseEvent e) { 
        if (!this.toolTipSettingsActive) {
            ToolTipManager ttm = ToolTipManager.sharedInstance();
            this.originalToolTipInitialDelay = ttm.getInitialDelay();
            ttm.setInitialDelay(this.toolTipInitialDelay);
            this.originalToolTipDismissDelay = ttm.getDismissDelay();
            ttm.setDismissDelay(this.toolTipDismissDelay);
            this.originalToolTipReshowDelay = ttm.getReshowDelay();
            ttm.setReshowDelay(this.toolTipReshowDelay);
            this.toolTipSettingsActive = true;
        }

        if (this.liveMouseHandler != null) {
             this.liveMouseHandler.mouseEntered(e);
        }

        //handle auxiliary handlers
        int mods = e.getModifiers();
        for (AbstractMouseHandler handler : auxiliaryMouseHandlers) {
            if (handler.getModifier() == 0 || 
                    (mods & handler.getModifier()) == handler.getModifier()) {
                handler.mouseEntered(e);
            }
        }
    }

    /**
     * Handles a 'mouse exited' event. This method resets the tooltip delays of
     * ToolTipManager.sharedInstance() to their
     * original values in effect before mouseEntered()
     *
     * @param e  the mouse event.
     */
    @Override
    public void mouseExited(MouseEvent e) {
        if (this.toolTipSettingsActive) {
            // restore original tooltip dealys
            ToolTipManager ttm = ToolTipManager.sharedInstance();
            ttm.setInitialDelay(this.originalToolTipInitialDelay);
            ttm.setDismissDelay(this.originalToolTipDismissDelay);
            ttm.setReshowDelay(this.originalToolTipReshowDelay);
            this.toolTipSettingsActive = false;
        }

        if (this.liveMouseHandler != null) {
            this.liveMouseHandler.mouseExited(e);
        }

        //handle auxiliary handlers
        int mods = e.getModifiers();
        for (AbstractMouseHandler handler : auxiliaryMouseHandlers) {
            if (handler.getModifier() == 0 || 
                    (mods & handler.getModifier()) == handler.getModifier()) {
                handler.mouseExited(e);
            }
        }
    }

    /**
     * Handles a 'mouse pressed' event.
     * <P>
     * This event is the popup trigger on Unix/Linux.  For Windows, the popup
     * trigger is the 'mouse released' event.
     *
     * @param e  The mouse event.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        int mods = e.getModifiers();
        if (e.isPopupTrigger()) {
            if (this.popup != null) {
                displayPopupMenu(e.getX(), e.getY());
            }
            return;
        }

        if (this.liveMouseHandler != null) {
            this.liveMouseHandler.mousePressed(e);
        } else {
            AbstractMouseHandler h = null;
            boolean found = false;
            Iterator<AbstractMouseHandler> iterator 
                    = this.availableLiveMouseHandlers.iterator();
            AbstractMouseHandler nomod = null;
            while (iterator.hasNext() && !found) {
                h = (AbstractMouseHandler) iterator.next();
                if (h.getModifier() == 0 && nomod == null) {
                    nomod = h;
                } else {
                    found = (mods & h.getModifier()) == h.getModifier();
                }
            }
            if (!found && nomod != null) {
                h = nomod;
                found = true;
            }
            if (found) {
                this.liveMouseHandler = h;
                this.liveMouseHandler.mousePressed(e);
            }
        }
        // handle auxiliary handlers

        for (AbstractMouseHandler handler : auxiliaryMouseHandlers) {
            if (handler.getModifier() == 0 || 
                    (mods & handler.getModifier()) == handler.getModifier()) {
                handler.mousePressed(e);
            }
        }
    }

    /**
     * Handles a 'mouse dragged' event.
     *
     * @param e  the mouse event.
     */
    @Override
    public void mouseDragged(MouseEvent e) {

        // if the popup menu has already been triggered, then ignore dragging...
        if (this.popup != null && this.popup.isShowing()) {
            return;
        }

        if (this.liveMouseHandler != null) {
            this.liveMouseHandler.mouseDragged(e);
        }

        //handle auxiliary handlers
        int mods = e.getModifiers();
        for (AbstractMouseHandler handler : auxiliaryMouseHandlers) {
            if (handler.getModifier() == 0 || 
                    (mods & handler.getModifier()) == handler.getModifier()) {
                handler.mouseDragged(e);
            }
        }
    }

    /**
     * Handles a 'mouse released' event.  On Windows, we need to check if this
     * is a popup trigger, but only if we haven't already been tracking a zoom
     * rectangle.
     *
     * @param e  information about the event.
     */
    @Override
    public void mouseReleased(MouseEvent e) {

        if (e.isPopupTrigger()) {
            if (this.popup != null) {
                displayPopupMenu(e.getX(), e.getY());
            }
            return;
        }

        if (this.liveMouseHandler != null) {
            this.liveMouseHandler.mouseReleased(e);
        }

        //handle auxiliary handlers
        int mods = e.getModifiers();
        for (AbstractMouseHandler handler : auxiliaryMouseHandlers) {
            if (handler.getModifier() == 0 || 
                    (mods & handler.getModifier()) == handler.getModifier()) {
                handler.mouseReleased(e);
            }
        }
    }

    /**
     * Receives notification of mouse clicks on the panel. These are
     * translated and passed on to any registered {@link ChartMouseListener}s.
     *
     * @param event  Information about the mouse event.
     */
    @Override
    public void mouseClicked(MouseEvent event) {

        Insets insets = getInsets();
        int x = (int) ((event.getX() - insets.left) / this.scaleX);
        int y = (int) ((event.getY() - insets.top) / this.scaleY);

        this.anchor = new Point2D.Double(x, y);
        if (this.chart == null) {
            return;
        }
        this.chart.setNotify(true);  // force a redraw
        // new entity code...
        Object[] listeners = this.chartMouseListeners.getListeners(
                ChartMouseListener.class);
        if (listeners.length > 0) {
            // handle old listeners
            ChartEntity entity = null;
            if (this.info != null) {
                EntityCollection entities = this.info.getEntityCollection();
                if (entities != null) {
                    entity = entities.getEntity(x, y);
                }
            }
            ChartMouseEvent chartEvent = new ChartMouseEvent(getChart(), event,
                    entity);
            for (int i = listeners.length - 1; i >= 0; i -= 1) {
                ((ChartMouseListener) listeners[i]).chartMouseClicked(chartEvent);
            }
        }

        // handle new mouse handler based listeners
        if (this.liveMouseHandler != null) {
            this.liveMouseHandler.mouseClicked(event);
        }

        // handle auxiliary handlers
        int mods = event.getModifiers();
        for (AbstractMouseHandler handler : auxiliaryMouseHandlers) {
            if (handler.getModifier() == 0 || 
                    (mods & handler.getModifier()) == handler.getModifier()) {
                handler.mouseClicked(event);
            }
        }
    }

    /**
     * Implementation of the MouseMotionListener's method.
     *
     * @param e  the event.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        Graphics2D g2 = (Graphics2D) getGraphics();
        if (this.horizontalAxisTrace) {
            drawHorizontalAxisTrace(g2, e.getX());
        }
        if (this.verticalAxisTrace) {
            drawVerticalAxisTrace(g2, e.getY());
        }
        g2.dispose();

        Object[] listeners = this.chartMouseListeners.getListeners(
                ChartMouseListener.class);
        if (listeners.length > 0) {
            Insets insets = getInsets();
            int x = (int) ((e.getX() - insets.left) / this.scaleX);
            int y = (int) ((e.getY() - insets.top) / this.scaleY);

            ChartEntity entity = null;
            if (this.info != null) {
                EntityCollection entities = this.info.getEntityCollection();
                if (entities != null) {
                    entity = entities.getEntity(x, y);
                }
            }

            // we can only generate events if the panel's chart is not null
            // (see bug report 1556951)
            if (this.chart != null) {
                ChartMouseEvent event = new ChartMouseEvent(getChart(), e, entity);
                for (int i = listeners.length - 1; i >= 0; i -= 1) {
                    ((ChartMouseListener) listeners[i]).chartMouseMoved(event);
                }
            }
        }

        // handle new mouse handler based listeners
        if (this.chart != null) {
             if (this.liveMouseHandler != null) {
                  this.liveMouseHandler.mouseMoved(e);
             }
             //handle auxiliary handlers
             int mods = e.getModifiers();
             for (AbstractMouseHandler handler : auxiliaryMouseHandlers) {
                 if (handler.getModifier() == 0 || 
                         (mods & handler.getModifier()) == handler.getModifier()) {
                     handler.mouseMoved(e);
                 }
             }
        }
    }

    /**
     * Zooms in on an anchor point (specified in screen coordinate space).
     *
     * @param x  the x value (in screen coordinates).
     * @param y  the y value (in screen coordinates).
     */
    public void zoomInBoth(double x, double y) {
        Plot plot = this.chart.getPlot();
        if (plot == null) {
            return;
        }
        // here we tweak the notify flag on the plot so that only
        // one notification happens even though we update multiple
        // axes...
        boolean savedNotify = plot.isNotify();
        plot.setNotify(false);
        zoomInDomain(x, y);
        zoomInRange(x, y);
        plot.setNotify(savedNotify);
    }

    /**
     * Decreases the length of the domain axis, centered about the given
     * coordinate on the screen.  The length of the domain axis is reduced
     * by the value of {@link #getZoomInFactor()}.
     *
     * @param x  the x coordinate (in screen coordinates).
     * @param y  the y-coordinate (in screen coordinates).
     */
    public void zoomInDomain(double x, double y) {
        Plot plot = this.chart.getPlot();
        if (plot instanceof Zoomable) {
            // here we tweak the notify flag on the plot so that only
            // one notification happens even though we update multiple
            // axes...
            boolean savedNotify = plot.isNotify();
            plot.setNotify(false);
            Zoomable z = (Zoomable) plot;
            z.zoomDomainAxes(this.zoomInFactor, this.info.getPlotInfo(),
                    translateScreenToJava2D(new Point((int) x, (int) y)),
                    this.zoomAroundAnchor);
            plot.setNotify(savedNotify);
        }
    }

    /**
     * Decreases the length of the range axis, centered about the given
     * coordinate on the screen.  The length of the range axis is reduced by
     * the value of {@link #getZoomInFactor()}.
     *
     * @param x  the x-coordinate (in screen coordinates).
     * @param y  the y coordinate (in screen coordinates).
     */
    public void zoomInRange(double x, double y) {
        Plot plot = this.chart.getPlot();
        if (plot instanceof Zoomable) {
            // here we tweak the notify flag on the plot so that only
            // one notification happens even though we update multiple
            // axes...
            boolean savedNotify = plot.isNotify();
            plot.setNotify(false);
            Zoomable z = (Zoomable) plot;
            z.zoomRangeAxes(this.zoomInFactor, this.info.getPlotInfo(),
                    translateScreenToJava2D(new Point((int) x, (int) y)),
                    this.zoomAroundAnchor);
            plot.setNotify(savedNotify);
        }
    }

    /**
     * Zooms out on an anchor point (specified in screen coordinate space).
     *
     * @param x  the x value (in screen coordinates).
     * @param y  the y value (in screen coordinates).
     */
    public void zoomOutBoth(double x, double y) {
        Plot plot = this.chart.getPlot();
        if (plot == null) {
            return;
        }
        // here we tweak the notify flag on the plot so that only
        // one notification happens even though we update multiple
        // axes...
        boolean savedNotify = plot.isNotify();
        plot.setNotify(false);
        zoomOutDomain(x, y);
        zoomOutRange(x, y);
        plot.setNotify(savedNotify);
    }

    /**
     * Increases the length of the domain axis, centered about the given
     * coordinate on the screen.  The length of the domain axis is increased
     * by the value of {@link #getZoomOutFactor()}.
     *
     * @param x  the x coordinate (in screen coordinates).
     * @param y  the y-coordinate (in screen coordinates).
     */
    public void zoomOutDomain(double x, double y) {
        Plot plot = this.chart.getPlot();
        if (plot instanceof Zoomable) {
            // here we tweak the notify flag on the plot so that only
            // one notification happens even though we update multiple
            // axes...
            boolean savedNotify = plot.isNotify();
            plot.setNotify(false);
            Zoomable z = (Zoomable) plot;
            z.zoomDomainAxes(this.zoomOutFactor, this.info.getPlotInfo(),
                    translateScreenToJava2D(new Point((int) x, (int) y)),
                    this.zoomAroundAnchor);
            plot.setNotify(savedNotify);
        }
    }

    /**
     * Increases the length the range axis, centered about the given
     * coordinate on the screen.  The length of the range axis is increased
     * by the value of {@link #getZoomOutFactor()}.
     *
     * @param x  the x coordinate (in screen coordinates).
     * @param y  the y-coordinate (in screen coordinates).
     */
    public void zoomOutRange(double x, double y) {
        Plot plot = this.chart.getPlot();
        if (plot instanceof Zoomable) {
            // here we tweak the notify flag on the plot so that only
            // one notification happens even though we update multiple
            // axes...
            boolean savedNotify = plot.isNotify();
            plot.setNotify(false);
            Zoomable z = (Zoomable) plot;
            z.zoomRangeAxes(this.zoomOutFactor, this.info.getPlotInfo(),
                    translateScreenToJava2D(new Point((int) x, (int) y)),
                    this.zoomAroundAnchor);
            plot.setNotify(savedNotify);
        }
    }

    /**
     * Zooms in on a selected region.
     *
     * @param selection  the selected region.
     */
    public void zoom(Rectangle2D selection) {

        // get the origin of the zoom selection in the Java2D space used for
        // drawing the chart (that is, before any scaling to fit the panel)
        Point2D selectOrigin = translateScreenToJava2D(new Point(
                (int) Math.ceil(selection.getX()),
                (int) Math.ceil(selection.getY())));
        PlotRenderingInfo plotInfo = this.info.getPlotInfo();
        Rectangle2D scaledDataArea = getScreenDataArea(
                (int) selection.getCenterX(), (int) selection.getCenterY());
        if ((selection.getHeight() > 0) && (selection.getWidth() > 0)) {

            double hLower = (selection.getMinX() - scaledDataArea.getMinX())
                / scaledDataArea.getWidth();
            double hUpper = (selection.getMaxX() - scaledDataArea.getMinX())
                / scaledDataArea.getWidth();
            double vLower = (scaledDataArea.getMaxY() - selection.getMaxY())
                / scaledDataArea.getHeight();
            double vUpper = (scaledDataArea.getMaxY() - selection.getMinY())
                / scaledDataArea.getHeight();

            Plot p = this.chart.getPlot();
            if (p instanceof Zoomable) {
                // here we tweak the notify flag on the plot so that only
                // one notification happens even though we update multiple
                // axes...
                boolean savedNotify = p.isNotify();
                p.setNotify(false);
                Zoomable z = (Zoomable) p;
                if (z.getOrientation() == PlotOrientation.HORIZONTAL) {
                    z.zoomDomainAxes(vLower, vUpper, plotInfo, selectOrigin);
                    z.zoomRangeAxes(hLower, hUpper, plotInfo, selectOrigin);
                }
                else {
                    z.zoomDomainAxes(hLower, hUpper, plotInfo, selectOrigin);
                    z.zoomRangeAxes(vLower, vUpper, plotInfo, selectOrigin);
                }
                p.setNotify(savedNotify);
            }

        }

    }

    /**
     * Restores the auto-range calculation on both axes.
     */
    public void restoreAutoBounds() {
        Plot plot = this.chart.getPlot();
        if (plot == null) {
            return;
        }
        // here we tweak the notify flag on the plot so that only
        // one notification happens even though we update multiple
        // axes...
        boolean savedNotify = plot.isNotify();
        plot.setNotify(false);
        restoreAutoDomainBounds();
        restoreAutoRangeBounds();
        plot.setNotify(savedNotify);
    }

    /**
     * Restores the auto-range calculation on the domain axis.
     */
    public void restoreAutoDomainBounds() {
        Plot plot = this.chart.getPlot();
        if (plot instanceof Zoomable) {
            Zoomable z = (Zoomable) plot;
            // here we tweak the notify flag on the plot so that only
            // one notification happens even though we update multiple
            // axes...
            boolean savedNotify = plot.isNotify();
            plot.setNotify(false);
            // we need to guard against this.zoomPoint being null
            Point2D zp = (this.zoomPoint != null
                    ? this.zoomPoint : new Point());
            z.zoomDomainAxes(0.0, this.info.getPlotInfo(), zp);
            plot.setNotify(savedNotify);
        }
    }

    /**
     * Restores the auto-range calculation on the range axis.
     */
    public void restoreAutoRangeBounds() {
        Plot plot = this.chart.getPlot();
        if (plot instanceof Zoomable) {
            Zoomable z = (Zoomable) plot;
            // here we tweak the notify flag on the plot so that only
            // one notification happens even though we update multiple
            // axes...
            boolean savedNotify = plot.isNotify();
            plot.setNotify(false);
            // we need to guard against this.zoomPoint being null
            Point2D zp = (this.zoomPoint != null
                    ? this.zoomPoint : new Point());
            z.zoomRangeAxes(0.0, this.info.getPlotInfo(), zp);
            plot.setNotify(savedNotify);
        }
    }

    /**
     * Returns the data area for the chart (the area inside the axes) with the
     * current scaling applied (that is, the area as it appears on screen).
     *
     * @return The scaled data area.
     */
    public Rectangle2D getScreenDataArea() {
        Rectangle2D dataArea = this.info.getPlotInfo().getDataArea();
        Insets insets = getInsets();
        double x = dataArea.getX() * this.scaleX + insets.left;
        double y = dataArea.getY() * this.scaleY + insets.top;
        double w = dataArea.getWidth() * this.scaleX;
        double h = dataArea.getHeight() * this.scaleY;
        return new Rectangle2D.Double(x, y, w, h);
    }

    /**
     * Returns the data area (the area inside the axes) for the plot or subplot,
     * with the current scaling applied.
     *
     * @param x  the x-coordinate (for subplot selection).
     * @param y  the y-coordinate (for subplot selection).
     *
     * @return The scaled data area.
     */
    public Rectangle2D getScreenDataArea(int x, int y) {
        PlotRenderingInfo plotInfo = this.info.getPlotInfo();
        Rectangle2D result;
        if (plotInfo.getSubplotCount() == 0) {
            result = getScreenDataArea();
        }
        else {
            // get the origin of the zoom selection in the Java2D space used for
            // drawing the chart (that is, before any scaling to fit the panel)
            Point2D selectOrigin = translateScreenToJava2D(new Point(x, y));
            int subplotIndex = plotInfo.getSubplotIndex(selectOrigin);
            if (subplotIndex == -1) {
                return null;
            }
            result = scale(plotInfo.getSubplotInfo(subplotIndex).getDataArea());
        }
        return result;
    }

    /**
     * Returns the tool tip initial delay value used inside this chart panel.
     *
     * @return An integer representing the initial delay value, in milliseconds.
     *
     * @see #setToolTipInitialDelay(int) 
     */
    public int getToolTipInitialDelay() {
        return this.toolTipInitialDelay;
    }

    /**
     * Sets the tool tip initial delay value for this chart panel.
     *
     * @param millis  the delay in milliseconds.
     *
     * @see #getToolTipInitialDelay() 
     */
    public void setToolTipInitialDelay(int millis) {
        this.toolTipInitialDelay = millis;
    }

    /**
     * Returns the tool tip dismiss delay value used inside this chart panel.
     *
     * @return An integer representing the dismissal delay value, in
     *         milliseconds.
     *
     * @see #setToolTipDismissDelay(int) 
     */
    public int getToolTipDismissDelay() {
        return this.toolTipDismissDelay;
    }

    /**
     * Sets the tool tip dismiss delay value for this chart panel.
     *
     * @param millis  the delay in milliseconds.
     *
     * @see #getToolTipDismissDelay() 
     */
    public void setToolTipDismissDelay(int millis) {
        this.toolTipDismissDelay = millis;
    }

    /**
     * Returns the tool tip reshow delay value used inside this chart panel.
     *
     * @return An integer representing the reshow delay value, in milliseconds.
     *
     * @see #setToolTipReshowDelay(int) 
     */
    public int getToolTipReshowDelay() {
        return this.toolTipReshowDelay;
    }

    /**
     * Specifies the amount of time that the user has to wait in
     * milliseconds before a tooltip will be reshown.
     *
     * @param millis  the delay in milliseconds.
     *
     * @see #getToolTipReshowDelay() 
     */
    public void setToolTipReshowDelay(int millis) {
        this.toolTipReshowDelay = millis;
    }

    /**
     * Returns the zoom in factor.
     *
     * @return The zoom in factor.
     *
     * @see #setZoomInFactor(double)
     */
    public double getZoomInFactor() {
        return this.zoomInFactor;
    }

    /**
     * Sets the zoom in factor.
     *
     * @param factor  the factor.
     *
     * @see #getZoomInFactor()
     */
    public void setZoomInFactor(double factor) {
        this.zoomInFactor = factor;
    }

    /**
     * Returns the zoom out factor.
     *
     * @return The zoom out factor.
     *
     * @see #setZoomOutFactor(double)
     */
    public double getZoomOutFactor() {
        return this.zoomOutFactor;
    }

    /**
     * Sets the zoom out factor.
     *
     * @param factor  the factor.
     *
     * @see #getZoomOutFactor()
     */
    public void setZoomOutFactor(double factor) {
        this.zoomOutFactor = factor;
    }

    /**
     * Draws zoom rectangle (if present).
     * The drawing is performed in XOR mode, therefore
     * when this method is called twice in a row,
     * the second call will completely restore the state
     * of the canvas.
     *
     * @param g2 the graphics device.
     * @param xor  use XOR for drawing?
     */
    private void drawZoomRectangle(Graphics2D g2, boolean xor) {
        if (this.zoomRectangle != null) {
            if (xor) {
                 // Set XOR mode to draw the zoom rectangle
                g2.setXORMode(Color.GRAY);
            }
            if (this.fillZoomRectangle) {
                g2.setPaint(this.zoomFillPaint);
                g2.fill(this.zoomRectangle);
            }
            else {
                g2.setPaint(this.zoomOutlinePaint);
                g2.draw(this.zoomRectangle);
            }
            if (xor) {
                // Reset to the default 'overwrite' mode
                g2.setPaintMode();
            }
        }
    }

     
     /**
      * Draws zoom rectangle (if present). The drawing is performed in XOR mode,
      * therefore when this method is called twice in a row, the second call will
      * completely restore the state of the canvas.
      * 
      * @param g2
      *            the graphics device.
      * @param xor
      *            use XOR for drawing?
      */
     private void drawSelectionShape(Graphics2D g2, boolean xor) {
          if (this.selectionShape != null) {
               if (xor) {
                    // Set XOR mode to draw the zoom rectangle
                    g2.setXORMode(Color.gray);
               }
               if (this.selectionFillPaint != null) {
                    g2.setPaint(this.selectionFillPaint);
                    g2.fill(this.selectionShape);
               }
               if (this.selectionOutlinePaint != null 
                       && this.selectionOutlineStroke != null) {
                   g2.setPaint(this.selectionOutlinePaint);
                   g2.setStroke(this.selectionOutlineStroke);
                   GeneralPath pp = new GeneralPath(this.selectionShape);
                   pp.closePath();
                   g2.draw(pp);
               }
               if (xor) {
                    // Reset to the default 'overwrite' mode
                    g2.setPaintMode();
               }
          }
     }

    /**
     * Draws a vertical line used to trace the mouse position to the horizontal
     * axis.
     *
     * @param g2 the graphics device.
     * @param x  the x-coordinate of the trace line.
     */
    private void drawHorizontalAxisTrace(Graphics2D g2, int x) {

        Rectangle2D dataArea = getScreenDataArea();

        g2.setXORMode(Color.ORANGE);
        if (((int) dataArea.getMinX() < x) && (x < (int) dataArea.getMaxX())) {

            if (this.verticalTraceLine != null) {
                g2.draw(this.verticalTraceLine);
                this.verticalTraceLine.setLine(x, (int) dataArea.getMinY(), x,
                        (int) dataArea.getMaxY());
            }
            else {
                this.verticalTraceLine = new Line2D.Float(x,
                        (int) dataArea.getMinY(), x, (int) dataArea.getMaxY());
            }
            g2.draw(this.verticalTraceLine);
        }

        // Reset to the default 'overwrite' mode
        g2.setPaintMode();
    }

    /**
     * Draws a horizontal line used to trace the mouse position to the vertical
     * axis.
     *
     * @param g2 the graphics device.
     * @param y  the y-coordinate of the trace line.
     */
    private void drawVerticalAxisTrace(Graphics2D g2, int y) {

        Rectangle2D dataArea = getScreenDataArea();

        g2.setXORMode(Color.ORANGE);
        if (((int) dataArea.getMinY() < y) && (y < (int) dataArea.getMaxY())) {

            if (this.horizontalTraceLine != null) {
                g2.draw(this.horizontalTraceLine);
                this.horizontalTraceLine.setLine((int) dataArea.getMinX(), y,
                        (int) dataArea.getMaxX(), y);
            }
            else {
                this.horizontalTraceLine = new Line2D.Float(
                        (int) dataArea.getMinX(), y, (int) dataArea.getMaxX(),
                        y);
            }
            g2.draw(this.horizontalTraceLine);
        }

        // Reset to the default 'overwrite' mode
        g2.setPaintMode();
    }

    /**
     * Displays a dialog that allows the user to edit the properties for the
     * current chart.
     *
     * @since 1.0.3
     */
    public void doEditChartProperties() {

        ChartEditor editor = ChartEditorManager.getChartEditor(this.chart);
        int result = JOptionPane.showConfirmDialog(this, editor,
                localizationResources.getString("Chart_Properties"),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            editor.updateChart(this.chart);
        }

    }

    /**
     * Copies the current chart to the system clipboard.
     *
     * @since 1.0.13
     */
    public void doCopy() {
        Clipboard systemClipboard
                = Toolkit.getDefaultToolkit().getSystemClipboard();
        Insets insets = getInsets();
        int w = getWidth() - insets.left - insets.right;
        int h = getHeight() - insets.top - insets.bottom;
        ChartTransferable selection = new ChartTransferable(this.chart, w, h,
                getMinimumDrawWidth(), getMinimumDrawHeight(),
                getMaximumDrawWidth(), getMaximumDrawHeight(), true);
        systemClipboard.setContents(selection, null);
    }

    /**
     * Opens a file chooser and gives the user an opportunity to save the chart
     * in PNG format.
     *
     * @throws IOException if there is an I/O error.
     */
    public void doSaveAs() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(this.defaultDirectoryForSaveAs);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    localizationResources.getString("PNG_Image_Files"), "png");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);

        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getPath();
            if (isEnforceFileExtensions()) {
                if (!filename.endsWith(".png")) {
                    filename = filename + ".png";
                }
            }
            ChartUtilities.saveChartAsPNG(new File(filename), this.chart,
                    getWidth(), getHeight());
        }
    }

    /**
     * Saves the chart in SVG format (a filechooser will be displayed so that
     * the user can specify the filename).  Note that this method only works
     * if the JFreeSVG library is on the classpath...if this library is not 
     * present, the method will fail.
     */
    private void saveAsSVG(File f) throws IOException {
        File file = f;
        if (file == null) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(this.defaultDirectoryForSaveAs);
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    localizationResources.getString("SVG_Files"), "svg");
            fileChooser.addChoosableFileFilter(filter);
            fileChooser.setFileFilter(filter);

            int option = fileChooser.showSaveDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                String filename = fileChooser.getSelectedFile().getPath();
                if (isEnforceFileExtensions()) {
                    if (!filename.endsWith(".svg")) {
                        filename = filename + ".svg";
    }
                }
                file = new File(filename);
                if (file.exists()) {
                    String fileExists = localizationResources.getString(
                            "FILE_EXISTS_CONFIRM_OVERWRITE");
                    int response = JOptionPane.showConfirmDialog(this, 
                            fileExists, "Save As SVG", 
                            JOptionPane.OK_CANCEL_OPTION);
                    if (response == JOptionPane.CANCEL_OPTION) {
                        file = null;
                    }
                }
            }
        }

        if (file != null) {
            // use reflection to get the SVG string
            String svg = generateSVG(getWidth(), getHeight());
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(file));
                writer.write("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n");
                writer.write(svg + "\n");
                writer.flush();
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } 

        }
    }
    
    /**
     * Generates a string containing a rendering of the chart in SVG format.
     * This feature is only supported if the JFreeSVG library is included on 
     * the classpath.
     * 
     * @return A string containing an SVG element for the current chart, or 
     *     <code>null</code> if there is a problem with the method invocation
     *     by reflection.
     */
    private String generateSVG(int width, int height) {
        Graphics2D g2 = createSVGGraphics2D(width, height);
        if (g2 == null) {
            throw new IllegalStateException("JFreeSVG library is not present.");
        }
        // we suppress shadow generation, because SVG is a vector format and
        // the shadow effect is applied via bitmap effects...
        g2.setRenderingHint(JFreeChart.KEY_SUPPRESS_SHADOW_GENERATION, true);
        String svg = null;
        Rectangle2D drawArea = new Rectangle2D.Double(0, 0, width, height);
        this.chart.draw(g2, drawArea);
        try {
            Method m = g2.getClass().getMethod("getSVGElement");
            svg = (String) m.invoke(g2);
        } catch (NoSuchMethodException e) {
            // null will be returned
        } catch (SecurityException e) {
            // null will be returned
        } catch (IllegalAccessException e) {
            // null will be returned
        } catch (IllegalArgumentException e) {
            // null will be returned
        } catch (InvocationTargetException e) {
            // null will be returned
        }
        return svg;
    }

    private Graphics2D createSVGGraphics2D(int w, int h) {
        try {
            Class svgGraphics2d = Class.forName("org.jfree.graphics2d.svg.SVGGraphics2D");
            Constructor ctor = svgGraphics2d.getConstructor(int.class, int.class);
            return (Graphics2D) ctor.newInstance(w, h);
        } catch (ClassNotFoundException ex) {
            return null;
        } catch (NoSuchMethodException ex) {
            return null;
        } catch (SecurityException ex) {
            return null;
        } catch (InstantiationException ex) {
            return null;
        } catch (IllegalAccessException ex) {
            return null;
        } catch (IllegalArgumentException ex) {
            return null;
        } catch (InvocationTargetException ex) {
            return null;
        }
    }

    /**
     * Saves the chart in PDF format (a filechooser will be displayed so that
     * the user can specify the filename).  Note that this method only works
     * if the OrsonPDF library is on the classpath...if this library is not
     * present, the method will fail.
     */
    private void saveAsPDF(File f) {
        File file = f;
        if (file == null) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(this.defaultDirectoryForSaveAs);
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    localizationResources.getString("PDF_Files"), "pdf");
            fileChooser.addChoosableFileFilter(filter);
            fileChooser.setFileFilter(filter);

            int option = fileChooser.showSaveDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                String filename = fileChooser.getSelectedFile().getPath();
                if (isEnforceFileExtensions()) {
                    if (!filename.endsWith(".pdf")) {
                        filename = filename + ".pdf";
                    }
                }
                file = new File(filename);
                if (file.exists()) {
                    String fileExists = localizationResources.getString(
                            "FILE_EXISTS_CONFIRM_OVERWRITE");
                    int response = JOptionPane.showConfirmDialog(this, 
                            fileExists, "Save As PDF", 
                            JOptionPane.OK_CANCEL_OPTION);
                    if (response == JOptionPane.CANCEL_OPTION) {
                        file = null;
                    }
                }
            }
        }
        
        if (file != null) {
            writeAsPDF(file, getWidth(), getHeight());
        }
    }

    /**
     * Returns <code>true</code> if OrsonPDF is on the classpath, and 
     * <code>false</code> otherwise.  The OrsonPDF library can be found at
     * http://www.object-refinery.com/pdf/
     * 
     * @return A boolean.
     */
    private boolean isOrsonPDFAvailable() {
        Class pdfDocumentClass = null;
        try {
            pdfDocumentClass = Class.forName("com.orsonpdf.PDFDocument");
        } catch (ClassNotFoundException e) {
            // pdfDocument class will be null so the function will return false
        }
        return (pdfDocumentClass != null);
    }
    
    /**
     * Writes the current chart to the specified file in PDF format.  This 
     * will only work when the OrsonPDF library is found on the classpath.
     * Reflection is used to ensure there is no compile-time dependency on
     * OrsonPDF (which is non-free software).
     * 
     * @param file  the output file (<code>null</code> not permitted).
     * @param w  the chart width.
     * @param h  the chart height.
     */
    private void writeAsPDF(File file, int w, int h) {
        if (!isOrsonPDFAvailable()) {
            throw new IllegalStateException(
                    "OrsonPDF is not present on the classpath.");
        }
        ParamChecks.nullNotPermitted(file, "file");
        try {
            Class pdfDocClass = Class.forName("com.orsonpdf.PDFDocument");
            Object pdfDoc = pdfDocClass.newInstance();
            Method m = pdfDocClass.getMethod("createPage", Rectangle2D.class);
            Rectangle2D rect = new Rectangle(w, h);
            Object page = m.invoke(pdfDoc, rect);
            Method m2 = page.getClass().getMethod("getGraphics2D");
            Graphics2D g2 = (Graphics2D) m2.invoke(page);
            // we suppress shadow generation, because PDF is a vector format and
            // the shadow effect is applied via bitmap effects...
            g2.setRenderingHint(JFreeChart.KEY_SUPPRESS_SHADOW_GENERATION, true);
            Rectangle2D drawArea = new Rectangle2D.Double(0, 0, w, h);
            this.chart.draw(g2, drawArea);
            Method m3 = pdfDocClass.getMethod("writeToFile", File.class);
            m3.invoke(pdfDoc, file);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (InstantiationException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        } catch (SecurityException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException(ex);
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Creates a print job for the chart.
     */
    public void createChartPrintJob() {
        PrinterJob job = PrinterJob.getPrinterJob();
        PageFormat pf = job.defaultPage();
        PageFormat pf2 = job.pageDialog(pf);
        if (pf2 != pf) {
            job.setPrintable(this, pf2);
            if (job.printDialog()) {
                try {
                    job.print();
                }
                catch (PrinterException e) {
                    JOptionPane.showMessageDialog(this, e);
                }
            }
        }
    }

    /**
     * Prints the chart on a single page.
     *
     * @param g  the graphics context.
     * @param pf  the page format to use.
     * @param pageIndex  the index of the page. If not <code>0</code>, nothing
     *                   gets print.
     *
     * @return The result of printing.
     */
    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) {

        if (pageIndex != 0) {
            return NO_SUCH_PAGE;
        }
        Graphics2D g2 = (Graphics2D) g;
        double x = pf.getImageableX();
        double y = pf.getImageableY();
        double w = pf.getImageableWidth();
        double h = pf.getImageableHeight();
        this.chart.draw(g2, new Rectangle2D.Double(x, y, w, h), this.anchor,
                null);
        return PAGE_EXISTS;

    }

    /**
     * Adds a listener to the list of objects listening for chart mouse events.
     *
     * @param listener  the listener (<code>null</code> not permitted).
     */
    public void addChartMouseListener(ChartMouseListener listener) {
        ParamChecks.nullNotPermitted(listener, "listener");
        this.chartMouseListeners.add(ChartMouseListener.class, listener);
    }

    /**
     * Removes a listener from the list of objects listening for chart mouse
     * events.
     *
     * @param listener  the listener.
     */
    public void removeChartMouseListener(ChartMouseListener listener) {
        this.chartMouseListeners.remove(ChartMouseListener.class, listener);
    }

    /**
     * Returns an array of the listeners of the given type registered with the
     * panel.
     *
     * @param listenerType  the listener type.
     *
     * @return An array of listeners.
     */
    @Override
    public <T extends EventListener> T[] getListeners(Class<T> listenerType) {
        if (listenerType == ChartMouseListener.class) {
            // fetch listeners from local storage
            return this.chartMouseListeners.getListeners(listenerType);
        }
        return super.getListeners(listenerType);
    }

    /**
     * Creates a popup menu for the panel.
     *
     * @param properties  include a menu item for the chart property editor.
     * @param save  include a menu item for saving the chart.
     * @param print  include a menu item for printing the chart.
     * @param zoom  include menu items for zooming.
     *
     * @return The popup menu.
     */
    protected JPopupMenu createPopupMenu(boolean properties, boolean save,
            boolean print, boolean zoom) {
        return createPopupMenu(properties, false, save, print, zoom);
    }

    /**
     * Creates a popup menu for the panel.
     *
     * @param properties  include a menu item for the chart property editor.
     * @param copy include a menu item for copying to the clipboard.
     * @param save  include a menu item for saving the chart.
     * @param print  include a menu item for printing the chart.
     * @param zoom  include menu items for zooming.
     *
     * @return The popup menu.
     *
     * @since 1.0.13
     */
    protected JPopupMenu createPopupMenu(boolean properties,
            boolean copy, boolean save, boolean print, boolean zoom) {

        JPopupMenu result = new JPopupMenu(localizationResources.getString("Chart") + ":");
        boolean separator = false;

        if (properties) {
            JMenuItem propertiesItem = new JMenuItem(
                    localizationResources.getString("Properties..."));
            propertiesItem.setActionCommand(PROPERTIES_COMMAND);
            propertiesItem.addActionListener(this);
            result.add(propertiesItem);
            separator = true;
        }

        if (copy) {
            if (separator) {
                result.addSeparator();
            }
            JMenuItem copyItem = new JMenuItem(
                    localizationResources.getString("Copy"));
            copyItem.setActionCommand(COPY_COMMAND);
            copyItem.addActionListener(this);
            result.add(copyItem);
            separator = !save;
        }

        if (save) {
            if (separator) {
                result.addSeparator();
            }
            JMenu saveSubMenu = new JMenu(localizationResources.getString(
                    "Save_as"));
            JMenuItem pngItem = new JMenuItem(localizationResources.getString(
                    "PNG..."));
            pngItem.setActionCommand("SAVE_AS_PNG");
            pngItem.addActionListener(this);
            saveSubMenu.add(pngItem);
            
            if (createSVGGraphics2D(10, 10) != null) {
                JMenuItem svgItem = new JMenuItem(localizationResources.getString(
                        "SVG..."));
                svgItem.setActionCommand("SAVE_AS_SVG");
                svgItem.addActionListener(this);
                saveSubMenu.add(svgItem);                
            }
            
            if (isOrsonPDFAvailable()) {
                JMenuItem pdfItem = new JMenuItem(
                        localizationResources.getString("PDF..."));
                pdfItem.setActionCommand("SAVE_AS_PDF");
                pdfItem.addActionListener(this);
                saveSubMenu.add(pdfItem);
            }
            result.add(saveSubMenu);
            separator = true;
        }

        if (print) {
            if (separator) {
                result.addSeparator();
            }
            JMenuItem printItem = new JMenuItem(
                    localizationResources.getString("Print..."));
            printItem.setActionCommand(PRINT_COMMAND);
            printItem.addActionListener(this);
            result.add(printItem);
            separator = true;
        }

        if (zoom) {
            if (separator) {
                result.addSeparator();
            }

            JMenu zoomInMenu = new JMenu(
                    localizationResources.getString("Zoom_In"));

            this.zoomInBothMenuItem = new JMenuItem(
                    localizationResources.getString("All_Axes"));
            this.zoomInBothMenuItem.setActionCommand(ZOOM_IN_BOTH_COMMAND);
            this.zoomInBothMenuItem.addActionListener(this);
            zoomInMenu.add(this.zoomInBothMenuItem);

            zoomInMenu.addSeparator();

            this.zoomInDomainMenuItem = new JMenuItem(
                    localizationResources.getString("Domain_Axis"));
            this.zoomInDomainMenuItem.setActionCommand(ZOOM_IN_DOMAIN_COMMAND);
            this.zoomInDomainMenuItem.addActionListener(this);
            zoomInMenu.add(this.zoomInDomainMenuItem);

            this.zoomInRangeMenuItem = new JMenuItem(
                    localizationResources.getString("Range_Axis"));
            this.zoomInRangeMenuItem.setActionCommand(ZOOM_IN_RANGE_COMMAND);
            this.zoomInRangeMenuItem.addActionListener(this);
            zoomInMenu.add(this.zoomInRangeMenuItem);

            result.add(zoomInMenu);

            JMenu zoomOutMenu = new JMenu(
                    localizationResources.getString("Zoom_Out"));

            this.zoomOutBothMenuItem = new JMenuItem(
                    localizationResources.getString("All_Axes"));
            this.zoomOutBothMenuItem.setActionCommand(ZOOM_OUT_BOTH_COMMAND);
            this.zoomOutBothMenuItem.addActionListener(this);
            zoomOutMenu.add(this.zoomOutBothMenuItem);

            zoomOutMenu.addSeparator();

            this.zoomOutDomainMenuItem = new JMenuItem(
                    localizationResources.getString("Domain_Axis"));
            this.zoomOutDomainMenuItem.setActionCommand(
                    ZOOM_OUT_DOMAIN_COMMAND);
            this.zoomOutDomainMenuItem.addActionListener(this);
            zoomOutMenu.add(this.zoomOutDomainMenuItem);

            this.zoomOutRangeMenuItem = new JMenuItem(
                    localizationResources.getString("Range_Axis"));
            this.zoomOutRangeMenuItem.setActionCommand(ZOOM_OUT_RANGE_COMMAND);
            this.zoomOutRangeMenuItem.addActionListener(this);
            zoomOutMenu.add(this.zoomOutRangeMenuItem);

            result.add(zoomOutMenu);

            JMenu autoRangeMenu = new JMenu(
                    localizationResources.getString("Auto_Range"));

            this.zoomResetBothMenuItem = new JMenuItem(
                    localizationResources.getString("All_Axes"));
            this.zoomResetBothMenuItem.setActionCommand(
                    ZOOM_RESET_BOTH_COMMAND);
            this.zoomResetBothMenuItem.addActionListener(this);
            autoRangeMenu.add(this.zoomResetBothMenuItem);

            autoRangeMenu.addSeparator();
            this.zoomResetDomainMenuItem = new JMenuItem(
                    localizationResources.getString("Domain_Axis"));
            this.zoomResetDomainMenuItem.setActionCommand(
                    ZOOM_RESET_DOMAIN_COMMAND);
            this.zoomResetDomainMenuItem.addActionListener(this);
            autoRangeMenu.add(this.zoomResetDomainMenuItem);

            this.zoomResetRangeMenuItem = new JMenuItem(
                    localizationResources.getString("Range_Axis"));
            this.zoomResetRangeMenuItem.setActionCommand(
                    ZOOM_RESET_RANGE_COMMAND);
            this.zoomResetRangeMenuItem.addActionListener(this);
            autoRangeMenu.add(this.zoomResetRangeMenuItem);

            result.addSeparator();
            result.add(autoRangeMenu);

        }

        return result;

    }

    /**
     * The idea is to modify the zooming options depending on the type of chart
     * being displayed by the panel.
     *
     * @param x  horizontal position of the popup.
     * @param y  vertical position of the popup.
     */
    protected void displayPopupMenu(int x, int y) {

        if (this.popup == null) {
            return;
        }

        // go through each zoom menu item and decide whether or not to
        // enable it...
        boolean isDomainZoomable = false;
        boolean isRangeZoomable = false;
        Plot plot = (this.chart != null ? this.chart.getPlot() : null);
        if (plot instanceof Zoomable) {
            Zoomable z = (Zoomable) plot;
            isDomainZoomable = z.isDomainZoomable();
            isRangeZoomable = z.isRangeZoomable();
        }

        if (this.zoomInDomainMenuItem != null) {
            this.zoomInDomainMenuItem.setEnabled(isDomainZoomable);
        }
        if (this.zoomOutDomainMenuItem != null) {
            this.zoomOutDomainMenuItem.setEnabled(isDomainZoomable);
        }
        if (this.zoomResetDomainMenuItem != null) {
            this.zoomResetDomainMenuItem.setEnabled(isDomainZoomable);
        }

        if (this.zoomInRangeMenuItem != null) {
            this.zoomInRangeMenuItem.setEnabled(isRangeZoomable);
        }
        if (this.zoomOutRangeMenuItem != null) {
            this.zoomOutRangeMenuItem.setEnabled(isRangeZoomable);
        }

        if (this.zoomResetRangeMenuItem != null) {
            this.zoomResetRangeMenuItem.setEnabled(isRangeZoomable);
        }

        if (this.zoomInBothMenuItem != null) {
            this.zoomInBothMenuItem.setEnabled(isDomainZoomable
                    && isRangeZoomable);
        }
        if (this.zoomOutBothMenuItem != null) {
            this.zoomOutBothMenuItem.setEnabled(isDomainZoomable
                    && isRangeZoomable);
        }
        if (this.zoomResetBothMenuItem != null) {
            this.zoomResetBothMenuItem.setEnabled(isDomainZoomable
                    && isRangeZoomable);
        }

        this.popup.show(this, x, y);

    }

    /**
     * Updates the UI for a LookAndFeel change.
     */
    @Override
    public void updateUI() {
        // here we need to update the UI for the popup menu, if the panel
        // has one...
        if (this.popup != null) {
            SwingUtilities.updateComponentTreeUI(this.popup);
        }
        super.updateUI();
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the output stream.
     *
     * @throws IOException  if there is an I/O error.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        SerialUtils.writePaint(this.zoomFillPaint, stream);
        SerialUtils.writePaint(this.zoomOutlinePaint, stream);
        SerialUtils.writeStroke(this.selectionOutlineStroke, stream);
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the input stream.
     *
     * @throws IOException  if there is an I/O error.
     * @throws ClassNotFoundException  if there is a classpath problem.
     */
    private void readObject(ObjectInputStream stream)
        throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.zoomFillPaint = SerialUtils.readPaint(stream);
        this.zoomOutlinePaint = SerialUtils.readPaint(stream);
        this.selectionOutlineStroke = SerialUtils.readStroke(stream);
          
        // we create a new but empty chartMouseListeners list
        this.chartMouseListeners = new EventListenerList();

        // register as a listener with sub-components...
        if (this.chart != null) {
            this.chart.addChangeListener(this);
        }

    }

     /**
      * Returns the value of the <code>useBuffer</code> flag as set in the
      * constructor.
      * 
      * @return A boolean.
      * 
      * @since 1.0.14
      */
     public boolean getUseBuffer() {
          return this.useBuffer;
     }

     public PlotOrientation getOrientation() {
         return this.orientation;
     }

     /**
      * Adds a mouse handler.
      * 
      * @param handler  the handler (<code>null</code> not permitted).
      * 
      * @see #removeMouseHandler(org.jfree.chart.panel.AbstractMouseHandler)
      */
     public void addMouseHandler(AbstractMouseHandler handler) {
         if (handler.isLiveHandler()) {
             this.availableLiveMouseHandlers.add(handler);
         } else {
             this.auxiliaryMouseHandlers.add(handler);
         }
     }

     /**
      * Removes a mouse handler.
      * 
      * @param handler  the handler (<code>null</code> not permitted).
      * 
      * @return A boolean.
      * 
      * @see #addMouseHandler(org.jfree.chart.panel.AbstractMouseHandler)
      */
     public boolean removeMouseHandler(AbstractMouseHandler handler) {
         if (handler.isLiveHandler()) {
             return this.availableLiveMouseHandlers.remove(handler);
         } else {
             return this.auxiliaryMouseHandlers.remove(handler);
         }          
     }

     /**
      * Clears the 'liveMouseHandler' field.  Each handler is responsible for
      * calling this method when they have finished handling mouse events.
      */
     public void clearLiveMouseHandler() {
         this.liveMouseHandler = null;
     }

     /**
      * Returns the selection shape.
      * 
      * @return The selection shape (possibly <code>null</code>).
      * 
      * @see #setSelectionShape(java.awt.Shape)
      */
     public Shape getSelectionShape() {
         return this.selectionShape;
     }

     /**
      * Sets the selection shape.
      * 
      * @param shape  the selection shape (<code>null</code> permitted).
      * 
      * @see #getSelectionShape()
      */
     public void setSelectionShape(Shape shape) {
         this.selectionShape = shape;
     }

     /**
      * Returns the selection fill paint.
      * 
      * @return The selection fill paint (possibly <code>null</code>).
      * 
      * @see #setSelectionFillPaint(java.awt.Paint)
      */
     public Paint getSelectionFillPaint() {
         return this.selectionFillPaint;
     }

     /**
      * Sets the selection fill paint.
      * 
      * @param paint  the paint (<code>null</code> permitted).
      * 
      * @see #getSelectionFillPaint()
      */
     public void setSelectionFillPaint(Paint paint) {
         this.selectionFillPaint = paint;
     }


     /**
      * Sets the selection outline paint.
      * 
      * @param paint  the paint (<code>null</code> permitted).
      * 
      * @see #getSelectionOutlinePaint()
      */
     public void setSelectionOutlinePaint(Paint paint) {
         this.selectionOutlinePaint = paint;
     }

     /**
      * Sets the selection outline stroke
      * 
      * @param stroke  the paint (<code>null</code> permitted).
      * 
      * @see #getSelectionOutlineStroke()
      */
     public void setSelectionOutlineStroke(Stroke stroke) {
         this.selectionOutlineStroke = stroke;
     }

     /**
      * Returns the zoom rectangle.
      * 
      * @return The zoom rectangle (possibly <code>null</code>).
      * 
      * @since 1.0.14
      */
     public Rectangle2D getZoomRectangle() {
         return this.zoomRectangle;
     }

     /**
      * Sets the zoom rectangle for the panel.
      * 
      * @param rect  the rectangle (<code>null</code> permitted).
      * 
      * @since 1.0.14
      */
     public void setZoomRectangle(Rectangle2D rect) {
         this.zoomRectangle = rect;
     }

     /**
      * @return the zoom handler that is installed per default on each chart 
      *     panel
      */
     public ZoomHandler getZoomHandler() {
         return this.zoomHandler;
     }

     /**
      * Returns a selection manager that can be used for point or area selection. 
      * (e.g. 
      * {@link org.jfree.chart.panel.selectionhandler.RegionSelectionHandler 
      * RegionSelectionHandlers})
      * 
      * @return the selection manager that has been set via setSelectionManager 
      *     or null
      */
     public SelectionManager getSelectionManager() {
         return this.selectionManager;
     }

     /**
      * Sets the selection manager of the ChartPanel. The manager can be 
      * retrieved via the getSelectionManager method to be used for point or 
      * area selection. 
      * 
      * (e.g. 
      * {@link org.jfree.chart.panel.selectionhandler.RegionSelectionHandler 
      * RegionSelectionHandlers})
      * 
      * @param manager
      */
     public void setSelectionManager(SelectionManager manager) {
         this.selectionManager = manager;
     }

}
