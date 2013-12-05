/*
Copyright Dietmar Ebner, 2004, ebner@apm.tuwien.ac.at

This file is part of PFLP.

PFLP is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

PFLP is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with PFLP; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

This code is used in JFreeChart with the authors permission.
*/


package org.jfree.chart.renderer.xy.forcedirectedlabeling;

import java.util.List;

/**
 * This class represents a instance for the map labeling problem.
 * It holds an collection of all points and the given map height and
 * width.
 * <p/>
 * This code is used in JFreeChart with the authors permission.
 *
 * @author Ebner Dietmar, ebner@apm.tuwien.ac.at
 */
public final class Instance {
    private PointFeature[] nodes = null;

    /**
     * creates an instance with the nodes given in Vector v
     */
    public Instance(List<PointFeature> pointFeatures) {
        int i;
        nodes = new PointFeature[pointFeatures.size()];
        for (i = 0; i < pointFeatures.size(); i++) {
            nodes[i] = pointFeatures.get(i);
        }
    }

    /**
     * returns a reference to the array of nodes
     */
    public PointFeature[] getNodes() {
        return (nodes);
    }

    public int size() {
        return nodes.length;
    }
}
