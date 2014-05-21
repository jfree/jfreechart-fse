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
 * ---------------------------------------------
 * StandardToolTipTagFragmentGeneratorTests.java
 * ---------------------------------------------
 * (C) Copyright 2007, 2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 05-Dec-2007 : Version 1 (DG);
 *
 */

package org.jfree.chart.imagemap;

import org.junit.Test;

import static org.junit.Assert.assertEquals;



/**
 * Tests for the {@link StandardToolTipTagFragmentGenerator} class.
 */
public class StandardToolTipTagFragmentGeneratorTest  {





    /**
     * Some checks for the generateURLFragment() method.
     */
    @Test
    public void testGenerateURLFragment() {
        StandardToolTipTagFragmentGenerator g
                = new StandardToolTipTagFragmentGenerator();
        assertEquals(" title=\"abc\" alt=\"\"",
                g.generateToolTipFragment("abc"));
        assertEquals(" title=\"Series &quot;A&quot;, 100.0\" alt=\"\"",
                g.generateToolTipFragment("Series \"A\", 100.0"));
    }

}
