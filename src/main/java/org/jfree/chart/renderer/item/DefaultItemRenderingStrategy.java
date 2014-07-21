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
 * ---------------------------------
 * DefaultItemRenderingStrategy.java
 * ---------------------------------
 * (C) Copyright 2013, 2014, by Michael Zinsmaier.
 *
 * Original Author:  Michael Zinsmaier;
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 17-Sep-2013 : Version 1 (MZ);
 *
 */

package org.jfree.chart.renderer.item;

import java.io.Serializable;

import org.jfree.chart.renderer.AbstractRenderer;

/*
 * if necessary this can be improved with respect to speed by implementing the
 * default cases direct in the abstract renderer.
 */

/**
 * A default item rendering strategy defines certain properties e.g. Shape, 
 * Paint, ... for individual items. It should be used with a specific renderer 
 * because it is based on its Shape, Paint, ... per Series properties.
 */
public abstract class DefaultItemRenderingStrategy implements Serializable {

    /** generated serial id     */
    private static final long serialVersionUID = -3637783770791118009L;
    
    /** 
     * the renderer that uses the strategy. This is necessary to get access to
     * the get Property Series methods etc. */
    protected final AbstractRenderer renderer;

    /**
     * creates a new Rendering strategy for the submitted renderer.
     * @param renderer  the renderer.
     */
    public DefaultItemRenderingStrategy(AbstractRenderer renderer) {
        this.renderer = renderer;
    }
    
}
