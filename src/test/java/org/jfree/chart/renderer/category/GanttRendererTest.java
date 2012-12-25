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
 * -----------------------
 * GanttRendererTests.java
 * -----------------------
 * (C) Copyright 2003-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 22-Oct-2003 : Version 1 (DG);
 * 20-Mar-2007 : Extended testEquals() (DG);
 * 23-Apr-2008 : Added testPublicCloneable() (DG);
 *
 */

package org.jfree.chart.renderer.category;

import org.jfree.chart.util.PublicCloneable;
import org.junit.Test;

import java.awt.Color;
import java.awt.GradientPaint;
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
import static org.junit.Assert.assertTrue;

/**
 * Tests for the {@link GanttRenderer} class.
 */
public class GanttRendererTest  {





    /**
     * Check that the equals() method distinguishes all fields.
     */
    @Test
    public void testEquals() {
        GanttRenderer r1 = new GanttRenderer();
        GanttRenderer r2 = new GanttRenderer();
        assertEquals(r1, r2);

        r1.setCompletePaint(Color.yellow);
        assertFalse(r1.equals(r2));
        r2.setCompletePaint(Color.yellow);
        assertEquals(r1, r2);

        r1.setIncompletePaint(Color.green);
        assertFalse(r1.equals(r2));
        r2.setIncompletePaint(Color.green);
        assertEquals(r1, r2);

        r1.setStartPercent(0.11);
        assertFalse(r1.equals(r2));
        r2.setStartPercent(0.11);
        assertEquals(r1, r2);

        r1.setEndPercent(0.88);
        assertFalse(r1.equals(r2));
        r2.setEndPercent(0.88);
        assertEquals(r1, r2);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashcode() {
        GanttRenderer r1 = new GanttRenderer();
        GanttRenderer r2 = new GanttRenderer();
        assertEquals(r1, r2);
        int h1 = r1.hashCode();
        int h2 = r2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        GanttRenderer r1 = new GanttRenderer();
        GanttRenderer r2 = (GanttRenderer) r1.clone();
        assertNotSame(r1, r2);
        assertSame(r1.getClass(), r2.getClass());
        assertEquals(r1, r2);
    }

    /**
     * Check that this class implements PublicCloneable.
     */
    @Test
    public void testPublicCloneable() {
        GanttRenderer r1 = new GanttRenderer();
        assertTrue(r1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {

        GanttRenderer r1 = new GanttRenderer();
        r1.setCompletePaint(new GradientPaint(1.0f, 2.0f, Color.RED, 3.0f,
                4.0f, Color.BLUE));
        r1.setIncompletePaint(new GradientPaint(4.0f, 3.0f, Color.RED, 2.0f,
                1.0f, Color.BLUE));

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(r1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
        GanttRenderer r2 = (GanttRenderer) in.readObject();
            in.close();

        assertEquals(r1, r2);

    }

}
