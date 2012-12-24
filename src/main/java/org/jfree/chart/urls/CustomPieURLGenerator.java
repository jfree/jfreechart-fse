/* ======================================
 * JFreeChart : a free Java chart library
 * ======================================
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
 * --------------------------
 * CustomPieURLGenerator.java
 * --------------------------
 * (C) Copyright 2004-2012, by David Basten and Contributors.
 *
 * Original Author:  David Basten;
 * Contributors:     -;
 *
 * Changes:
 * --------
 * 04-Feb-2004 : Version 1, contributed by David Basten based on
 *               CustomXYURLGenerator by Richard Atkinson (added to main source
 *               tree on 25-May-2004);
 * 17-Jun-2012 : Removed JCommon dependencies (DG);
 *
 */

package org.jfree.chart.urls;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jfree.chart.util.PublicCloneable;
import org.jfree.chart.plot.MultiplePiePlot;
import org.jfree.data.general.PieDataset;

/**
 * A custom URL generator for pie charts.
 */
public class CustomPieURLGenerator implements PieURLGenerator,
        Cloneable, PublicCloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 7100607670144900503L;

    /** Storage for the URLs. */
    private List<Map<Comparable, String>> urls;

    /**
     * Creates a new <code>CustomPieURLGenerator</code> instance, initially
     * empty.  Call {@link #addURLs(Map)} to specify the URL fragments to be
     * used.
     */
    public CustomPieURLGenerator() {
        this.urls = new ArrayList<Map<Comparable, String>>();
    }

    /**
     * Generates a URL fragment.
     *
     * @param dataset  the dataset (ignored).
     * @param key  the item key.
     * @param pieIndex  the pie index.
     *
     * @return A string containing the generated URL.
     *
     * @see #getURL(Comparable, int)
     */
    @Override
	public String generateURL(PieDataset dataset, Comparable key,
                              int pieIndex) {
        return getURL(key, pieIndex);
    }

    /**
     * Returns the number of URL maps stored by the renderer.
     *
     * @return The list count.
     *
     * @see #addURLs(Map)
     */
    public int getListCount() {
        return this.urls.size();
    }

    /**
     * Returns the number of URLs in a given map (specified by its position
     * in the map list).
     *
     * @param list  the list index (zero based).
     *
     * @return The URL count.
     *
     * @see #getListCount()
     */
    public int getURLCount(int list) {
        int result = 0;
        Map<Comparable, String> urlMap = this.urls.get(list);
        if (urlMap != null) {
            result = urlMap.size();
        }
        return result;
    }

    /**
     * Returns the URL for a section in the specified map.
     *
     * @param key  the key.
     * @param mapIndex  the map index.
     *
     * @return The URL.
     */
    public String getURL(Comparable key, int mapIndex) {
        String result = null;
        if (mapIndex < getListCount()) {
            Map<Comparable, String> urlMap = this.urls.get(mapIndex);
            if (urlMap != null) {
                result = urlMap.get(key);
            }
        }
        return result;
    }

    /**
     * Adds a map containing <code>(key, URL)</code> mappings where each
     * <code>key</code> is an instance of <code>Comparable</code>
     * (corresponding to the key for an item in a pie dataset) and each
     * <code>URL</code> is a <code>String</code> representing a URL fragment.
     * <br><br>
     * The map is appended to an internal list...you can add multiple maps
     * if you are working with, say, a {@link MultiplePiePlot}.
     *
     * @param urlMap  the URLs (<code>null</code> permitted).
     */
    public void addURLs(Map<Comparable, String> urlMap) {
        this.urls.add(urlMap);
    }

    /**
     * Tests if this object is equal to another.
     *
     * @param o  the other object.
     *
     * @return A boolean.
     */
    @Override
	public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (o instanceof CustomPieURLGenerator) {
            CustomPieURLGenerator generator = (CustomPieURLGenerator) o;
            if (getListCount() != generator.getListCount()) {
                return false;
            }
            for (int pieItem = 0; pieItem < getListCount(); pieItem++) {
                if (getURLCount(pieItem) != generator.getURLCount(pieItem)) {
                    return false;
                }
                Set<Comparable> keySet = this.urls.get(pieItem).keySet();
                for (Comparable key : keySet) {
                    if (!getURL(key, pieItem).equals(
                            generator.getURL(key, pieItem))) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Returns a clone of the generator.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if cloning is not supported.
     */
    @Override
	public Object clone() throws CloneNotSupportedException {
        CustomPieURLGenerator urlGen = (CustomPieURLGenerator) super.clone();
        urlGen.urls = new ArrayList<Map<Comparable, String>>();

        for (Map<Comparable, String> map : this.urls) {

            Map<Comparable, String> newMap = new HashMap<Comparable, String>();
            for (Map.Entry<Comparable, String> entry : map.entrySet()) {
                newMap.put(entry.getKey(), entry.getValue());
            }

            urlGen.addURLs(newMap);
        }

        return urlGen;
    }

}
