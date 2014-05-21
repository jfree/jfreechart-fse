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
 * ArrayUtils.java
 * ---------------
 * (C) Copyright 2003-2014, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: ArrayUtilities.java,v 1.7 2008/09/10 09:21:30 mungady Exp $
 *
 * Changes
 * -------
 * 21-Aug-2003 : Version 1 (DG);
 * 04-Oct-2004 : Renamed ArrayUtils --> ArrayUtilities (DG);
 * 16-Jun-2012 : Moved from JCommon to JFreeChart (DG);
 *
 */

package org.jfree.chart.util;

import java.util.Arrays;

/**
 * Utility methods for working with arrays.
 */
public class ArrayUtils {

    /**
     * Private constructor prevents object creation.
     */
    private ArrayUtils() {
    }

    /**
     * Clones a two dimensional array of floats.
     *
     * @param array  the array.
     *
     * @return A clone of the array.
     */
    public static float[][] clone(float[][] array) {
        if (array == null) {
            return null;
        }
        float[][] result = new float[array.length][];
        System.arraycopy(array, 0, result, 0, array.length);
        for (int i = 0; i < array.length; i++) {
            float[] child = array[i];
            float[] copychild = new float[child.length];
            System.arraycopy(child, 0, copychild, 0, child.length);
            result[i] = copychild;
        }
        return result;
    }

    /**
     * Returns <code>true</code> if all the references in <code>array1</code>
     * are equal to all the references in <code>array2</code> (two
     * <code>null</code> references are considered equal for this test).
     *
     * @param array1  the first array (<code>null</code> permitted).
     * @param array2  the second array (<code>null</code> permitted).
     *
     * @return A boolean.
     */
    public static boolean equalReferencesInArrays(Object[] array1, 
            Object[] array2) {
        if (array1 == null) {
            return (array2 == null);
        }
        if (array2 == null) {
            return false;
        }
        if (array1.length != array2.length) {
            return false;
        }
        for (int i = 0; i < array1.length; i++) {
            if (array1[i] == null) {
                if (array2[i] != null) {
                    return false;
                }
            }
            if (array2[i] == null) {
                if (array1[i] != null) {
                    return false;
                }
            }
            if (array1[i] != array2[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests two float arrays for equality.
     *
     * @param array1  the first array (<code>null</code> permitted).
     * @param array2  the second arrray (<code>null</code> permitted).
     *
     * @return A boolean.
     */
    public static boolean equal(float[][] array1, float[][] array2) {
        if (array1 == null) {
            return (array2 == null);
        }
        if (array2 == null) {
            return false;
        }
        if (array1.length != array2.length) {
            return false;
        }
        for (int i = 0; i < array1.length; i++) {
            if (!Arrays.equals(array1[i], array2[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns <code>true</code> if any two items in the array are equal to
     * one another.  Any <code>null</code> values in the array are ignored.
     *
     * @param array  the array to check.
     *
     * @return A boolean.
     */
    public static boolean hasDuplicateItems(Object[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < i; j++) {
                Object o1 = array[i];
                Object o2 = array[j];
                if (o1 != null && o2 != null) {
                    if (o1.equals(o2)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
