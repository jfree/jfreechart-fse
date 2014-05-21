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
 * --------------
 * TestUtils.java
 * --------------
 * (C) Copyright 2007-2014, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 08-Jun-2007 : Version 1 (DG);
 * 19-Mar-2014 : Renamed TestUtils (DG);
 * 
 */

package org.jfree.chart;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

/**
 * Some utility methods for use by the testing code.
 */
public class TestUtils {

    /**
     * Returns <code>true</code> if the collections contains any object that
     * is an instance of the specified class, and <code>false</code> otherwise.
     *
     * @param collection  the collection.
     * @param c  the class.
     *
     * @return A boolean.
     */
    public static boolean containsInstanceOf(Collection collection, Class c) {
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            if (obj != null && obj.getClass().equals(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns an object that is the deserialised form of the supplied object.
     * The original object is serialised to a byte array then deserialised
     * and returned.
     * 
     * @param original  the original object (<code>null</code> not permitted).
     * 
     * @return A serialised and deserialised object.
     */
    public static <T extends Serializable> T serialised(T original) {
        T result = null;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ObjectOutput out;
        try {
            out = new ObjectOutputStream(buffer);
            out.writeObject(original);
            out.close();
            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            result = (T) in.readObject();
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
