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
 * CloneUtils.java
 * ---------------
 * (C) Copyright 2014, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 08-Apr-2014 : Version 1 (DG);
 *
 */

package org.jfree.chart.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Utilities for cloning.
 * 
 * @since 1.0.18
 */
public class CloneUtils {
    
    /**
     * Returns a new map that contains the same keys and cloned copied of the
     * values.
     * 
     * @param source  the source map (<code>null</code> not permitted).
     * 
     * @return A new map. 
     * 
     * @since 1.0.18
     */
    public static Map cloneMapValues(Map source) {
        ParamChecks.nullNotPermitted(source, "source");
        Map result = new HashMap();
        for (Object key : source.keySet()) {
            Object value = source.get(key);
            if (value != null) {
                try {
                    result.put(key, ObjectUtils.clone(value));
                } catch (CloneNotSupportedException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                result.put(key, null);
            }
        }
        return result;
    }
   
}
