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
 * --------------
 * TaskTests.java
 * --------------
 * (C) Copyright 2004-2008, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 30-Jul-2004 : Version 1 (DG);
 *
 */

package org.jfree.data.gantt;

import org.jfree.data.time.SimpleTimePeriod;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

/**
 * Tests for the {@link Task} class.
 */
public class TaskTest  {





    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {

        Task t1 = new Task("T", new Date(1), new Date(2));
        Task t2 = new Task("T", new Date(1), new Date(2));
        assertEquals(t1, t2);
        assertEquals(t2, t1);

        t1.setDescription("X");
        assertFalse(t1.equals(t2));
        t2.setDescription("X");
        assertEquals(t1, t2);

        t1.setDuration(new SimpleTimePeriod(new Date(2), new Date(3)));
        assertFalse(t1.equals(t2));
        t2.setDuration(new SimpleTimePeriod(new Date(2), new Date(3)));
        assertEquals(t1, t2);

        t1.setPercentComplete(0.5);
        assertFalse(t1.equals(t2));
        t2.setPercentComplete(0.5);
        assertEquals(t1, t2);

        t1.addSubtask(new Task("T", new Date(22), new Date(33)));
        assertFalse(t1.equals(t2));
        t2.addSubtask(new Task("T", new Date(22), new Date(33)));
        assertEquals(t1, t2);


    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        Task t1 = new Task("T", new Date(1), new Date(2));
        Task t2 = (Task) t1.clone();
        assertNotSame(t1, t2);
        assertSame(t1.getClass(), t2.getClass());
        assertEquals(t1, t2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {

        Task t1 = new Task("T", new Date(1), new Date(2));

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(t1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                new ByteArrayInputStream(buffer.toByteArray())
            );
        Task t2 = (Task) in.readObject();
            in.close();

        assertEquals(t1, t2);

    }

    /**
     * Check the getSubTaskCount() method.
     */
    @Test
    public void testGetSubTaskCount() {
        Task t1 = new Task("T", new Date(100), new Date(200));
        assertEquals(0, t1.getSubtaskCount());
        t1.addSubtask(new Task("S1", new Date(100), new Date(110)));
        assertEquals(1, t1.getSubtaskCount());
        Task s2 = new Task("S2", new Date(111), new Date(120));
        t1.addSubtask(s2);
        assertEquals(2, t1.getSubtaskCount());
        t1.addSubtask(new Task("S3", new Date(121), new Date(130)));
        assertEquals(3, t1.getSubtaskCount());
        t1.removeSubtask(s2);
        assertEquals(2, t1.getSubtaskCount());
    }

}
