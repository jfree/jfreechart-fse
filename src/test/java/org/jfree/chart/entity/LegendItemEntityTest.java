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
 * LegendItemEntityTests.java
 * --------------------------
 * (C) Copyright 2004-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 20-May-2004 : Version 1 (DG);
 * 18-May-2007 : Added checks for new fields (DG);
 *
 */

package org.jfree.chart.entity;

import org.jfree.data.category.DefaultCategoryDataset;
import org.junit.Test;

import java.awt.geom.Rectangle2D;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

/**
 * Tests for the {@link LegendItemEntity} class.
 */
public class LegendItemEntityTest  {





    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        LegendItemEntity e1 = new LegendItemEntity(new Rectangle2D.Double(1.0,
                2.0, 3.0, 4.0));
        LegendItemEntity e2 = new LegendItemEntity(new Rectangle2D.Double(1.0,
                2.0, 3.0, 4.0));
        assertEquals(e1, e2);

        e1.setArea(new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0));
        assertFalse(e1.equals(e2));
        e2.setArea(new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0));
        assertEquals(e1, e2);

        e1.setToolTipText("New ToolTip");
        assertFalse(e1.equals(e2));
        e2.setToolTipText("New ToolTip");
        assertEquals(e1, e2);

        e1.setURLText("New URL");
        assertFalse(e1.equals(e2));
        e2.setURLText("New URL");
        assertEquals(e1, e2);

        e1.setDataset(new DefaultCategoryDataset());
        assertFalse(e1.equals(e2));
        e2.setDataset(new DefaultCategoryDataset());
        assertEquals(e1, e2);

        e1.setSeriesKey("A");
        assertFalse(e1.equals(e2));
        e2.setSeriesKey("A");
        assertEquals(e1, e2);

    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        LegendItemEntity e1 = new LegendItemEntity(new Rectangle2D.Double(1.0,
                2.0, 3.0, 4.0));
        LegendItemEntity e2 = (LegendItemEntity) e1.clone();

        assertNotSame(e1, e2);
        assertSame(e1.getClass(), e2.getClass());
        assertEquals(e1, e2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        LegendItemEntity e1 = new LegendItemEntity(new Rectangle2D.Double(1.0,
                2.0, 3.0, 4.0));

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(buffer);
        out.writeObject(e1);
        out.close();

        ObjectInput in = new ObjectInputStream(
                new ByteArrayInputStream(buffer.toByteArray()));
        LegendItemEntity e2 = (LegendItemEntity) in.readObject();
        in.close();
        assertEquals(e1, e2);
    }

}
