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
 * ------------------------------
 * WaterfallBarRendererTests.java
 * ------------------------------
 * (C) Copyright 2003-2009, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 21-Oct-2003 : Version 1 (DG);
 * 23-Apr-2008 : Added testPublicCloneable() (DG);
 * 04-Feb-2009 : Added testFindRangeBounds() (DG);
 *
 */

package org.jfree.chart.renderer.category;

import org.jfree.chart.util.PublicCloneable;
import org.junit.Test;

import java.awt.Color;
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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the {@link WaterfallBarRenderer} class.
 */
public class WaterfallBarRendererTest  {





    /**
     * Some tests for the findRangeBounds() method.
     */
    @Test
    public void testFindRangeBounds() {
        WaterfallBarRenderer r = new WaterfallBarRenderer();
        assertNull(r.findRangeBounds(null));
    }

    /**
     * Check that the equals() method distinguishes all fields.
     */
    @Test
    public void testEquals() {
        WaterfallBarRenderer r1 = new WaterfallBarRenderer();
        WaterfallBarRenderer r2 = new WaterfallBarRenderer();
        assertEquals(r1, r2);

        // firstBarPaint;
        r1.setFirstBarPaint(Color.cyan);
        assertFalse(r1.equals(r2));
        r2.setFirstBarPaint(Color.cyan);
        assertEquals(r1, r2);

        // lastBarPaint;
        r1.setLastBarPaint(Color.cyan);
        assertFalse(r1.equals(r2));
        r2.setLastBarPaint(Color.cyan);
        assertEquals(r1, r2);

        // positiveBarPaint;
        r1.setPositiveBarPaint(Color.cyan);
        assertFalse(r1.equals(r2));
        r2.setPositiveBarPaint(Color.cyan);
        assertEquals(r1, r2);

        //private Paint negativeBarPaint;
        r1.setNegativeBarPaint(Color.cyan);
        assertFalse(r1.equals(r2));
        r2.setNegativeBarPaint(Color.cyan);
        assertEquals(r1, r2);

    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashcode() {
        WaterfallBarRenderer r1 = new WaterfallBarRenderer();
        WaterfallBarRenderer r2 = new WaterfallBarRenderer();
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
        WaterfallBarRenderer r1 = new WaterfallBarRenderer();
        WaterfallBarRenderer r2 = (WaterfallBarRenderer) r1.clone();
        assertNotSame(r1, r2);
        assertSame(r1.getClass(), r2.getClass());
        assertEquals(r1, r2);

        // quick check for independence
        r1.setFirstBarPaint(Color.yellow);
        assertFalse(r1.equals(r2));
        r2.setFirstBarPaint(Color.yellow);
        assertEquals(r1, r2);

    }

    /**
     * Check that this class implements PublicCloneable.
     */
    @Test
    public void testPublicCloneable() {
        WaterfallBarRenderer r1 = new WaterfallBarRenderer();
        assertTrue(r1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {

        WaterfallBarRenderer r1 = new WaterfallBarRenderer();

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(r1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                new ByteArrayInputStream(buffer.toByteArray())
            );
        WaterfallBarRenderer r2 = (WaterfallBarRenderer) in.readObject();
            in.close();

        assertEquals(r1, r2);

    }

}
