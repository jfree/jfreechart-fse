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
 * ------------------
 * PieChartTests.java
 * ------------------
 * (C) Copyright 2002-2008, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 11-Jun-2002 : Version 1 (DG);
 * 17-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 *
 */

package org.jfree.chart;

import org.jfree.chart.event.ChartChangeEvent;
import org.jfree.chart.event.ChartChangeListener;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests for a pie chart.
 *
 */
public class PieChartTest  {

    /** A chart. */
    private JFreeChart pieChart;





    /**
     * Common test setup.
     */
    @Before
    public void setUp() {

        this.pieChart = createPieChart();

    }

    /**
     * Using a regular pie chart, we replace the dataset with null.  Expect to
     * receive notification of a chart change event, and (of course) the
     * dataset should be null.
     */
    @Test
    public void testReplaceDatasetOnPieChart() {
        LocalListener l = new LocalListener();
        this.pieChart.addChangeListener(l);
        PiePlot plot = (PiePlot) this.pieChart.getPlot();
        plot.setDataset(null);
        assertEquals(true, l.flag);
        assertNull(plot.getDataset());
    }

    /**
     * Creates a pie chart.
     *
     * @return The pie chart.
     */
    private static JFreeChart createPieChart() {
        DefaultPieDataset data = new DefaultPieDataset();
        data.setValue("Java", new Double(43.2));
        data.setValue("Visual Basic", new Double(0.0));
        data.setValue("C/C++", new Double(17.5));
        return ChartFactory.createPieChart("Pie Chart", data);
    }

    /**
     * A chart change listener.
     *
     */
    static class LocalListener implements ChartChangeListener {

        /** A flag. */
        private boolean flag;

        /**
         * Event handler.
         *
         * @param event  the event.
         */
        @Override
        public void chartChanged(ChartChangeEvent event) {
            this.flag = true;
        }

    }

}
