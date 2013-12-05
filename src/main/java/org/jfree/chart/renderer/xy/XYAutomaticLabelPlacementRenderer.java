/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2008, by Object Refinery Limited and Contributors.
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
 */

package org.jfree.chart.renderer.xy;

import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.forcedirectedlabeling.*;
import org.jfree.chart.renderer.xy.forcedirectedlabeling.Label;
import org.jfree.chart.text.TextBlock;
import org.jfree.chart.text.TextUtilities;
import org.jfree.data.xy.XYDataset;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * A renderer that renders points and labels, and places the labels using the algorithm descriped here:
 * https://www.ads.tuwien.ac.at/research/labeling/ (we have written confirmation that we can use it under LGPL)
 * <p/>
 * Rotation of labels doesn't work, and anchorpoints are ignored.
 *
 * @author Erik Krogh Kristensen, erik@webbies.dk
 */
public class XYAutomaticLabelPlacementRenderer extends XYLineAndShapeRenderer {
    private final int extraBorder;

    /**
     * @param extraBorder How much extra border the algorithm should add around each label.
     */
    public XYAutomaticLabelPlacementRenderer(int extraBorder) {
        this.extraBorder = extraBorder;
    }


    /**
     * For serialization.
     */
    private static final long serialVersionUID = -7435246895986425889L;
    private Point2D.Double[][] coordinates;

    @Override
    public XYItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea, XYPlot plot, XYDataset dataset, PlotRenderingInfo info) {
        XYItemRendererState result = super.initialise(g2, dataArea, plot, dataset, info);
        HashMap<Integer, SeriesItemPair> indexToSeriesItem = new LinkedHashMap<Integer, SeriesItemPair>();

        ArrayList<PointFeature> pointFeatures = new ArrayList<PointFeature>();

        int numberOfSeries = dataset.getSeriesCount();
        coordinates = new Point2D.Double[numberOfSeries][];

        int indexCounter = 0;
        for (int series = 0; series < numberOfSeries; series++) {

            int numberOfItems = dataset.getItemCount(series);
            coordinates[series] = new Point2D.Double[numberOfItems];
            for (int item = 0; item < numberOfItems; item++) {
                XYItemLabelGenerator generator = getItemLabelGenerator(series, item);
                double x1 = dataset.getXValue(series, item);
                double y1 = dataset.getYValue(series, item);
                double xPoint = plot.getDomainAxis().valueToJava2D(x1, dataArea, plot.getDomainAxisEdge());
                double yPoint = plot.getRangeAxis().valueToJava2D(y1, dataArea, plot.getRangeAxisEdge());

                getItemShape(series, item).getBounds2D();
                if (plot.getOrientation() == PlotOrientation.HORIZONTAL) {
                    double tmpX = xPoint;
                    //noinspection SuspiciousNameCombination
                    xPoint = yPoint;
                    //noinspection SuspiciousNameCombination
                    yPoint = tmpX;
                }

                if (generator != null) {
                    Font labelFont = getItemLabelFont(series, item);
                    Paint paint = getItemLabelPaint(series, item);
                    g2.setFont(labelFont);
                    g2.setPaint(paint);
                    String labelText = generator.generateLabel(dataset, series, item);
                    Rectangle2D textBounds = TextUtilities.getTextBounds(labelText, g2, g2.getFontMetrics());
                    xPoint -= extraBorder / 2;
                    yPoint -= extraBorder / 2;
                    double width = textBounds.getWidth() + extraBorder;
                    double height = textBounds.getHeight() + extraBorder;

                    PointFeature pointFeature = new PointFeature(xPoint, yPoint, width, height, labelText, labelFont.getFontName(), indexCounter);
                    indexToSeriesItem.put(indexCounter, new SeriesItemPair(series, item));
                    indexCounter++;
                    pointFeatures.add(pointFeature);
                }
            }
        }


        indexCounter = addMagicBorderBoxes(dataArea, pointFeatures, indexCounter);


        Instance labelsInstance = new Instance(pointFeatures);
        LabelPlacingAlgorithm labelAlgorithm = new ForceDirectedLabeling(labelsInstance);


        Solution solution = labelAlgorithm.createSolution();

        Label[] labels = solution.getLabels();
        for (Label label : labels) {
            int index = label.getNode().getIndex();
            SeriesItemPair seriesItemPair = indexToSeriesItem.get(index);
            if (seriesItemPair != null) {
                int item = seriesItemPair.getItem();
                int series = seriesItemPair.getSeries();
                coordinates[series][item] = label.getTopleft();
            }
        }

        List<List<Label>> unplaceables = new ArrayList<List<Label>>();
        for (Label label : labels) {
            if (label.getUnplacable()) {
                ArrayList<Label> collidingLabels = new ArrayList<Label>();
                unplaceables.add(collidingLabels);
                for (Label neighbour : label.getNeighbours()) {
                    if (neighbour.doesIntersect(label)) {
                        collidingLabels.add(neighbour);
                    }
                }
            }
        }
        System.out.println();


        return result;
    }

    // By adding these "magic" labels, we make sure that in the only valid placement of labels, the real labels doesn't exceed the borders of the dataArea.
    private int addMagicBorderBoxes(Rectangle2D dataArea, ArrayList<PointFeature> pointFeatures, int indexCounter) {
        double height = dataArea.getHeight();
        double heightHalf = (height - extraBorder) / 2;
        double width = dataArea.getWidth();
        double widthHalf = (width - extraBorder) / 2;
        double centerX = dataArea.getCenterX();
        double centerY = dataArea.getCenterY();

        double positionMargin = 3;
        double sizeMargin = 2 * positionMargin;

        int borderCounter = 1;

        double adjustedHeight = heightHalf - sizeMargin;
        double adjustedWidth = widthHalf - sizeMargin;

        Rectangle2D bounds = dataArea.getBounds2D();
        double leftX = bounds.getMinX();
        double rightX = bounds.getMaxX();
        double bottomY = bounds.getMaxY();
        double topY = bounds.getMinY();

        // Top border
        addBorderBox(centerX - positionMargin, topY, adjustedWidth, adjustedHeight, pointFeatures, indexCounter++, borderCounter++);
        addBorderBox(centerX + positionMargin, topY, adjustedWidth, adjustedHeight, pointFeatures, indexCounter++, borderCounter++);

        // Right border
        addBorderBox(rightX, centerY - positionMargin, adjustedWidth, adjustedHeight, pointFeatures, indexCounter++, borderCounter++);
        addBorderBox(rightX, centerY + positionMargin, adjustedWidth, adjustedHeight, pointFeatures, indexCounter++, borderCounter++);

        // Bottom border
        addBorderBox(centerX - positionMargin, bottomY, adjustedWidth, adjustedHeight, pointFeatures, indexCounter++, borderCounter++);
        addBorderBox(centerX + positionMargin, bottomY, adjustedWidth, adjustedHeight, pointFeatures, indexCounter++, borderCounter++);

        // Left border
        addBorderBox(leftX, centerY - positionMargin, adjustedWidth, adjustedHeight, pointFeatures, indexCounter++, borderCounter++);
        addBorderBox(leftX, centerY + positionMargin, adjustedWidth, adjustedHeight, pointFeatures, indexCounter++, borderCounter++);

        return indexCounter;
    }

    private void addBorderBox(double x, double y, double width, double height, ArrayList<PointFeature> pointFeatures, int indexCounter, int counter) {
        pointFeatures.add(new PointFeature(x, y, width, height, "Border: " + counter, "ariel", indexCounter));
    }

    @Override
    protected void drawItemLabel(Graphics2D g2, PlotOrientation orientation, XYDataset dataset, int series, int item, double x, double y, boolean negative) {
        Point2D.Double labelPoint = coordinates[series][item];
        if (labelPoint != null) {
            x = labelPoint.getX();
            y = labelPoint.getY();
        }

        XYItemLabelGenerator generator = getItemLabelGenerator(series, item);
        if (generator != null) {
            Font labelFont = getItemLabelFont(series, item);
            Paint paint = getItemLabelPaint(series, item);
            g2.setFont(labelFont);
            String label = generator.generateLabel(dataset, series, item);

            g2.setPaint(paint);
            TextBlock textBlock = TextUtilities.createTextBlock(label, labelFont, paint);
            float newX = (float) x;
            float newY = (float) y;
            newX += extraBorder;
            newY += extraBorder;
            textBlock.draw(g2, newX, newY, null);
        }
    }

    private static final class SeriesItemPair {
        int series;
        int item;

        private SeriesItemPair(int series, int item) {
            this.series = series;
            this.item = item;
        }

        private int getSeries() {
            return series;
        }

        private int getItem() {
            return item;
        }
    }
}
