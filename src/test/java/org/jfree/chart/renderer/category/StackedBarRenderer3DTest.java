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
 * StackedBarRenderer3DTests.java
 * ------------------------------
 * (C) Copyright 2003-2009, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 25-Mar-2003 : Version 1 (DG);
 * 18-Jan-2007 : Added many new tests (DG);
 * 23-Apr-2008 : Added testPublicCloneable() (DG);
 * 03-Feb-2009 : Added testFindRangeBounds() (DG);
 * 
 */

package org.jfree.chart.renderer.category;

import org.jfree.chart.util.PublicCloneable;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the {@link StackedBarRenderer3D} class.
 */
public class StackedBarRenderer3DTest  {

    /**
     * Provide access to protected method.
     */
    static class MyRenderer extends StackedBarRenderer3D {
        @Override
		public List createStackedValueList(CategoryDataset dataset,
                Comparable category, int[] includedRows, double base,
                boolean asPercentages) {
            return super.createStackedValueList(dataset, category,
                    includedRows, base, asPercentages);
        }
    }





    /**
     * Some checks for the findRangeBounds() method.
     */
    @Test
    public void testFindRangeBounds() {
        StackedBarRenderer3D r = new StackedBarRenderer3D();
        assertNull(r.findRangeBounds(null));

        // an empty dataset should return a null range
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        assertNull(r.findRangeBounds(dataset));

        dataset.addValue(1.0, "R1", "C1");
        assertEquals(new Range(0.0, 1.0), r.findRangeBounds(dataset));

        dataset.addValue(-2.0, "R1", "C2");
        assertEquals(new Range(-2.0, 1.0), r.findRangeBounds(dataset));

        dataset.addValue(null, "R1", "C3");
        assertEquals(new Range(-2.0, 1.0), r.findRangeBounds(dataset));

        dataset.addValue(2.0, "R2", "C1");
        assertEquals(new Range(-2.0, 3.0), r.findRangeBounds(dataset));

        dataset.addValue(null, "R2", "C2");
        assertEquals(new Range(-2.0, 3.0), r.findRangeBounds(dataset));
    }

    /**
     * Check that the equals() method distinguishes all fields.
     */
    @Test
    public void testEquals() {
        StackedBarRenderer3D r1 = new StackedBarRenderer3D();
        StackedBarRenderer3D r2 = new StackedBarRenderer3D();
        assertEquals(r1, r2);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashcode() {
        StackedBarRenderer3D r1 = new StackedBarRenderer3D();
        StackedBarRenderer3D r2 = new StackedBarRenderer3D();
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
        StackedBarRenderer3D r1 = new StackedBarRenderer3D();
        StackedBarRenderer3D r2 = (StackedBarRenderer3D) r1.clone();

        assertNotSame(r1, r2);
        assertSame(r1.getClass(), r2.getClass());
        assertEquals(r1, r2);
    }

    /**
     * Check that this class implements PublicCloneable.
     */
    @Test
    public void testPublicCloneable() {
        StackedBarRenderer3D r1 = new StackedBarRenderer3D();
        assertTrue(r1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {

        StackedBarRenderer3D r1 = new StackedBarRenderer3D();

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(buffer);
        out.writeObject(r1);
        out.close();

        ObjectInput in = new ObjectInputStream(
                new ByteArrayInputStream(buffer.toByteArray()));
        StackedBarRenderer3D r2 = (StackedBarRenderer3D) in.readObject();
        in.close();

        assertEquals(r1, r2);

    }

    /**
     * A test for the createStackedValueList() method.
     */
    @Test
    public void testCreateStackedValueList1() {
        DefaultCategoryDataset d = new DefaultCategoryDataset();
        d.addValue(1.0, "s0", "c0");
        MyRenderer r = new MyRenderer();
        List l = r.createStackedValueList(d, "c0", new int[] { 0 }, 0.0, false);
        assertEquals(2, l.size());
        assertEquals(0.0, ((Object[]) l.get(0))[1]);
        assertEquals(1.0, ((Object[]) l.get(1))[1]);
    }

    /**
     * A test for the createStackedValueList() method.
     */
    @Test
    public void testCreateStackedValueList2() {
        DefaultCategoryDataset d = new DefaultCategoryDataset();
        d.addValue(-1.0, "s0", "c0");
        MyRenderer r = new MyRenderer();
        List l = r.createStackedValueList(d, "c0", new int[] { 0 }, 0.0, false);
        assertEquals(2, l.size());
        assertEquals(-1.0, ((Object[]) l.get(0))[1]);
        assertEquals(0.0, ((Object[]) l.get(1))[1]);
    }

    /**
     * A test for the createStackedValueList() method.
     */
    @Test
    public void testCreateStackedValueList3() {
        DefaultCategoryDataset d = new DefaultCategoryDataset();
        d.addValue(0.0, "s0", "c0");
        MyRenderer r = new MyRenderer();
        List l = r.createStackedValueList(d, "c0", new int[] { 0 }, 0.0, false);
        assertEquals(2, l.size());
        assertEquals(0.0, ((Object[]) l.get(0))[1]);
        assertEquals(0.0, ((Object[]) l.get(1))[1]);
    }

    /**
     * A test for the createStackedValueList() method.
     */
    @Test
    public void testCreateStackedValueList4() {
        DefaultCategoryDataset d = new DefaultCategoryDataset();
        d.addValue(null, "s0", "c0");
        MyRenderer r = new MyRenderer();
        List l = r.createStackedValueList(d, "c0", new int[] { 0 }, 0.0, false);
        assertEquals(0, l.size());
    }

    /**
     * A test for the createStackedValueList() method.
     */
    @Test
    public void testCreateStackedValueList1a() {
        DefaultCategoryDataset d = new DefaultCategoryDataset();
        d.addValue(1.0, "s0", "c0");
        d.addValue(1.1, "s1", "c0");
        MyRenderer r = new MyRenderer();
        List l = r.createStackedValueList(d, "c0", new int[] { 0, 1 }, 0.0,
                false);
        assertEquals(3, l.size());
        assertEquals(0.0, ((Object[]) l.get(0))[1]);
        assertEquals(1.0, ((Object[]) l.get(1))[1]);
        assertEquals(2.1, ((Object[]) l.get(2))[1]);
    }

    /**
     * A test for the createStackedValueList() method.
     */
    @Test
    public void testCreateStackedValueList1b() {
        DefaultCategoryDataset d = new DefaultCategoryDataset();
        d.addValue(1.0, "s0", "c0");
        d.addValue(-1.1, "s1", "c0");
        MyRenderer r = new MyRenderer();
        List l = r.createStackedValueList(d, "c0", new int[] { 0, 1 }, 0.0,
                false);
        assertEquals(3, l.size());
        assertEquals(-1.1, ((Object[]) l.get(0))[1]);
        assertEquals(0.0, ((Object[]) l.get(1))[1]);
        assertEquals(1.0, ((Object[]) l.get(2))[1]);
    }

    /**
     * A test for the createStackedValueList() method.
     */
    @Test
    public void testCreateStackedValueList1c() {
        DefaultCategoryDataset d = new DefaultCategoryDataset();
        d.addValue(1.0, "s0", "c0");
        d.addValue(0.0, "s1", "c0");
        MyRenderer r = new MyRenderer();
        List l = r.createStackedValueList(d, "c0", new int[] { 0, 1 }, 0.0,
                false);
        assertEquals(3, l.size());
        assertEquals(0.0, ((Object[]) l.get(0))[1]);
        assertEquals(1.0, ((Object[]) l.get(1))[1]);
        assertEquals(1.0, ((Object[]) l.get(2))[1]);
    }

    /**
     * A test for the createStackedValueList() method.
     */
    @Test
    public void testCreateStackedValueList1d() {
        DefaultCategoryDataset d = new DefaultCategoryDataset();
        d.addValue(1.0, "s0", "c0");
        d.addValue(null, "s1", "c0");
        MyRenderer r = new MyRenderer();
        List l = r.createStackedValueList(d, "c0", new int[] { 0, 1 }, 0.0,
                false);
        assertEquals(2, l.size());
        assertEquals(0.0, ((Object[]) l.get(0))[1]);
        assertEquals(1.0, ((Object[]) l.get(1))[1]);
    }

    /**
     * A test for the createStackedValueList() method.
     */
    @Test
    public void testCreateStackedValueList2a() {
        DefaultCategoryDataset d = new DefaultCategoryDataset();
        d.addValue(-1.0, "s0", "c0");
        d.addValue(1.1, "s1", "c0");
        MyRenderer r = new MyRenderer();
        List l = r.createStackedValueList(d, "c0", new int[] { 0, 1 }, 0.0,
                false);
        assertEquals(3, l.size());
        assertEquals(-1.0, ((Object[]) l.get(0))[1]);
        assertEquals(0.0, ((Object[]) l.get(1))[1]);
        assertEquals(1.1, ((Object[]) l.get(2))[1]);
    }

    /**
     * A test for the createStackedValueList() method.
     */
    @Test
    public void testCreateStackedValueList2b() {
        DefaultCategoryDataset d = new DefaultCategoryDataset();
        d.addValue(-1.0, "s0", "c0");
        d.addValue(-1.1, "s1", "c0");
        MyRenderer r = new MyRenderer();
        List l = r.createStackedValueList(d, "c0", new int[] { 0, 1 }, 0.0,
                false);
        assertEquals(3, l.size());
        assertEquals(-2.1, ((Object[]) l.get(0))[1]);
        assertEquals(-1.0, ((Object[]) l.get(1))[1]);
        assertEquals(0.0, ((Object[]) l.get(2))[1]);
    }

    /**
     * A test for the createStackedValueList() method.
     */
    @Test
    public void testCreateStackedValueList2c() {
        DefaultCategoryDataset d = new DefaultCategoryDataset();
        d.addValue(-1.0, "s0", "c0");
        d.addValue(0.0, "s1", "c0");
        MyRenderer r = new MyRenderer();
        List l = r.createStackedValueList(d, "c0", new int[] { 0, 1 }, 0.0,
                false);
        assertEquals(3, l.size());
        assertEquals(-1.0, ((Object[]) l.get(0))[1]);
        assertEquals(0.0, ((Object[]) l.get(1))[1]);
        assertEquals(0.0, ((Object[]) l.get(2))[1]);
    }

    /**
     * A test for the createStackedValueList() method.
     */
    @Test
    public void testCreateStackedValueList2d() {
        DefaultCategoryDataset d = new DefaultCategoryDataset();
        d.addValue(-1.0, "s0", "c0");
        d.addValue(null, "s1", "c0");
        MyRenderer r = new MyRenderer();
        List l = r.createStackedValueList(d, "c0", new int[] { 0, 1 }, 0.0,
                false);
        assertEquals(2, l.size());
        assertEquals(-1.0, ((Object[]) l.get(0))[1]);
        assertEquals(0.0, ((Object[]) l.get(1))[1]);
    }

    /**
     * A test for the createStackedValueList() method.
     */
    @Test
    public void testCreateStackedValueList3a() {
        DefaultCategoryDataset d = new DefaultCategoryDataset();
        d.addValue(0.0, "s0", "c0");
        d.addValue(1.1, "s1", "c0");
        MyRenderer r = new MyRenderer();
        List l = r.createStackedValueList(d, "c0", new int[] { 0, 1 }, 0.0,
                false);
        assertEquals(3, l.size());
        assertEquals(0.0, ((Object[]) l.get(0))[1]);
        assertEquals(0.0, ((Object[]) l.get(1))[1]);
        assertEquals(1.1, ((Object[]) l.get(2))[1]);
    }

    /**
     * A test for the createStackedValueList() method.
     */
    @Test
    public void testCreateStackedValueList3b() {
        DefaultCategoryDataset d = new DefaultCategoryDataset();
        d.addValue(0.0, "s0", "c0");
        d.addValue(-1.1, "s1", "c0");
        MyRenderer r = new MyRenderer();
        List l = r.createStackedValueList(d, "c0", new int[] { 0, 1 }, 0.0,
                false);
        assertEquals(3, l.size());
        assertEquals(-1.1, ((Object[]) l.get(0))[1]);
        assertEquals(0.0, ((Object[]) l.get(1))[1]);
        assertEquals(0.0, ((Object[]) l.get(2))[1]);
    }

    /**
     * A test for the createStackedValueList() method.
     */
    @Test
    public void testCreateStackedValueList3c() {
        DefaultCategoryDataset d = new DefaultCategoryDataset();
        d.addValue(0.0, "s0", "c0");
        d.addValue(0.0, "s1", "c0");
        MyRenderer r = new MyRenderer();
        List l = r.createStackedValueList(d, "c0", new int[] { 0, 1 }, 0.0,
                false);
        assertEquals(3, l.size());
        assertEquals(0.0, ((Object[]) l.get(0))[1]);
        assertEquals(0.0, ((Object[]) l.get(1))[1]);
        assertEquals(0.0, ((Object[]) l.get(2))[1]);
    }

    /**
     * A test for the createStackedValueList() method.
     */
    @Test
    public void testCreateStackedValueList3d() {
        DefaultCategoryDataset d = new DefaultCategoryDataset();
        d.addValue(0.0, "s0", "c0");
        d.addValue(null, "s1", "c0");
        MyRenderer r = new MyRenderer();
        List l = r.createStackedValueList(d, "c0", new int[] { 0, 1 }, 0.0,
                false);
        assertEquals(2, l.size());
        assertEquals(0.0, ((Object[]) l.get(0))[1]);
        assertEquals(0.0, ((Object[]) l.get(1))[1]);
    }

    /**
     * A test for the createStackedValueList() method.
     */
    @Test
    public void testCreateStackedValueList5() {
        DefaultCategoryDataset d = new DefaultCategoryDataset();
        d.addValue(1.0, "s0", "c0");
        d.addValue(null, "s1", "c0");
        d.addValue(2.0, "s2", "c0");
        MyRenderer r = new MyRenderer();
        List l = r.createStackedValueList(d, "c0", new int[] { 0, 1, 2 }, 0.0,
                false);
        assertEquals(3, l.size());
        assertEquals(0.0, ((Object[]) l.get(0))[1]);
        assertEquals(1.0, ((Object[]) l.get(1))[1]);
        assertEquals(3.0, ((Object[]) l.get(2))[1]);
    }
}
