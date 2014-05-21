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
 * -------------------
 * PaintUtils.java
 * -------------------
 * (C) Copyright 2003-2012, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: PaintUtils.java,v 1.10 2007/11/02 17:50:37 taqua Exp $
 *
 * Changes
 * -------
 * 13-Nov-2003 : Version 1 (DG);
 * 04-Oct-2004 : Renamed PaintUtils --> PaintUtils (DG);
 * 23-Feb-2005 : Rewrote equal() method with less indenting required (DG);
 * 16-Jun-2012 : Moved from JCommon to JFreeChart (DG);
 *
 */

package org.jfree.chart.util;

import java.awt.GradientPaint;
import java.awt.Paint;
import java.util.Map;

/**
 * Utility code that relates to <code>Paint</code> objects.
 */
public class PaintUtils {

    /**
     * Private constructor prevents object creation.
     */
    private PaintUtils() {
    }

    /**
     * Returns <code>true</code> if the two <code>Paint</code> objects are equal 
     * OR both <code>null</code>.  This method handles
     * <code>GradientPaint</code> as a special case.
     *
     * @param p1  paint 1 (<code>null</code> permitted).
     * @param p2  paint 2 (<code>null</code> permitted).
     *
     * @return A boolean.
     */
    public static boolean equal(Paint p1, Paint p2) {
        // handle cases where either or both arguments are null
        if (p1 == null) {
            return (p2 == null);   
        }
        if (p2 == null) {
            return false;   
        }
        
        boolean result;
        // handle GradientPaint as a special case...
        if (p1 instanceof GradientPaint && p2 instanceof GradientPaint) {
            GradientPaint gp1 = (GradientPaint) p1;
            GradientPaint gp2 = (GradientPaint) p2;
            result = gp1.getColor1().equals(gp2.getColor1()) 
                && gp1.getColor2().equals(gp2.getColor2())
                && gp1.getPoint1().equals(gp2.getPoint1())    
                && gp1.getPoint2().equals(gp2.getPoint2())
                && gp1.isCyclic() == gp2.isCyclic()
                && gp1.getTransparency() == gp1.getTransparency(); 
        }
        else {
            result = p1.equals(p2);
        }
        return result;
    }

    /**
     * Returns <code>true</code> if the maps contain the same keys and 
     * {@link Paint} values, and <code>false</code> otherwise.
     * 
     * @param m1  map 1 (<code>null</code> not permitted).
     * @param m2  map 2 (<code>null</code> not permitted).
     * 
     * @return A boolean. 
     */
    public static boolean equalMaps(Map<?, Paint> m1, Map<?, Paint> m2) {
        if (m1.size() != m2.size()) {
            return false;
        }
        for (Map.Entry<?, Paint> entry : m1.entrySet()) {
            if (!m2.containsKey(entry.getKey())) {
                return false;
            }
            if (!PaintUtils.equal(entry.getValue(), 
                    m2.get(entry.getKey()))) {
                return false;
            }
        }
        return true;
    }

}
