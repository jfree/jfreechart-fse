package org.jfree.chart.panel.selectionhandler;

/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2009, by Object Refinery Limited and Contributors.
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
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *
 * ---------------------
 * SelectionHandler.java
 * ---------------------
 * (C) Copyright 2009, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 19-Jun-2009 : Version 1 (DG);
 *
 */

import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.util.ShapeUtilities;

/**
 * A mouse handler that allows data items to be selected. The selection shape
 * is a circle that can be expanded by dragging the mouse away from the starting
 * point. 
 * 
 * Will only work together with a ChartPanel as event source
 * 
 * @author zinsmaie
 */
public class CircularRegionSelectionHandler extends RegionSelectionHandler {

	/** a generated serial id. */
	private static final long serialVersionUID = 5627320657050195767L;

	/**
	 * The selection path (in Java2D space).
	 */
	private Ellipse2D selectionCircle;

	/**
	 * The start mouse point.
	 */
	private Point2D startPoint;

	/**
	 * Creates a new default instance.
	 */
	public CircularRegionSelectionHandler() {
		super();
		this.selectionCircle = null;
		this.startPoint = null;
	}
	
	/**
	 * Creates a new instance with a modifier restriction
	 * @param modifier e.g. shift has to be pressed InputEvents.SHIFT_MASK
	 */
	public CircularRegionSelectionHandler(int modifier) {
		super(modifier);
		this.selectionCircle = null;
		this.startPoint = null;
	}

	/**
	 * Creates a new selection handler with the specified attributes.
	 * 
	 * @param outlineStroke
	 *            the outline stroke.
	 * @param outlinePaint
	 *            the outline paint.
	 * @param fillPaint
	 *            the fill paint.
	 */
	public CircularRegionSelectionHandler(Stroke outlineStroke,
			Paint outlinePaint, Paint fillPaint) {
		super(outlineStroke, outlinePaint, fillPaint);
		this.selectionCircle = null;
		this.startPoint = null;
	}

	/**
	 * starts the circle selection by fixing the center of the circle
	 * 
	 * @param e the event.
	 */
	public void mousePressed(MouseEvent e) {
		if (!(e.getSource() instanceof ChartPanel)) {
			return;
		}
		ChartPanel panel = (ChartPanel) e.getSource();
		Rectangle2D dataArea = panel.getScreenDataArea();
		if (dataArea.contains(e.getPoint())) {
				SelectionManager selectionManager = panel.getSelectionManager();
				if (selectionManager != null) {
					if (!e.isShiftDown()) {
						   selectionManager.clearSelection();
					}
					Point pt = e.getPoint();
					this.startPoint = new Point(pt);
					this.selectionCircle = new Ellipse2D.Double(pt.x, pt.y, 1,1);
			}
		}
	}

	/**
	 * adjusts the radius of the circle according to the actual mouse position
	 * 
	 * @param e the event.
	 */
	public void mouseDragged(MouseEvent e) {
		ChartPanel panel = (ChartPanel) e.getSource();
		
		if (this.startPoint == null) {
			panel.clearLiveMouseHandler();
			return; // we never started a selection
		}
		Point pt = e.getPoint();
		Point2D pt2 = ShapeUtilities.getPointInRectangle(pt.x, pt.y,
				panel.getScreenDataArea());

		double r = startPoint.distance(pt2);

		if (r <= 0) {
			r = 1.0;
		}

		// ensure that r is small enough to hold the circle in the drawing area
		// selecting things that are not visible / not painted would be
		// problematic
		Point corner1Test = new Point((int) (startPoint.getX()),
				(int) (startPoint.getY() + r));
		r = startPoint.distance(ShapeUtilities.getPointInRectangle(
				corner1Test.getX(), corner1Test.getY(),
				panel.getScreenDataArea()));

		Point corner2Test = new Point((int) (startPoint.getX() + r),
				(int) (startPoint.getY()));
		r = startPoint.distance(ShapeUtilities.getPointInRectangle(
				corner2Test.getX(), corner2Test.getY(),
				panel.getScreenDataArea()));

		Point corner3Test = new Point((int) (startPoint.getX() - r),
				(int) (startPoint.getY()));
		r = startPoint.distance(ShapeUtilities.getPointInRectangle(
				corner3Test.getX(), corner3Test.getY(),
				panel.getScreenDataArea()));

		Point corner4Test = new Point((int) (startPoint.getX()),
				(int) (startPoint.getY() - r));
		r = startPoint.distance(ShapeUtilities.getPointInRectangle(
				corner4Test.getX(), corner4Test.getY(),
				panel.getScreenDataArea()));

		this.selectionCircle = new Ellipse2D.Double((startPoint.getX() - r),
				(startPoint.getY() - r), (2.0 * r), (2.0 * r));

		panel.setSelectionShape(selectionCircle);
		panel.setSelectionFillPaint(this.fillPaint);
		panel.setSelectionOutlinePaint(this.outlinePaint);
		panel.repaint();
	}

	/**
	 * finishes the selection and calls the {@link SelectionManager} of
	 * the event source. The SelectionManager is then responsible for the processing
	 * of the geometric selection.
	 */
	public void mouseReleased(MouseEvent e) {
		ChartPanel panel = (ChartPanel) e.getSource();
		if (this.startPoint == null) {
			panel.clearLiveMouseHandler();
			return; // we never started a selection
		}
		SelectionManager selectionManager = panel.getSelectionManager();

		// do something with the selection shape
		if (selectionManager != null) {
			selectionManager.select(new GeneralPath(this.selectionCircle));
		}

		panel.setSelectionShape(null);
		this.selectionCircle = null;
		this.startPoint = null;
		panel.repaint();
		panel.clearLiveMouseHandler();
	}

}
