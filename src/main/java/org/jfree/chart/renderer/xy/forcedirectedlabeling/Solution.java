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
*/

package org.jfree.chart.renderer.xy.forcedirectedlabeling;

import java.util.Random;

/**
 * Represents a solution of the map labeling problems
 * (a mapping of the label to (x, y) - coordinates).
 * <br>
 * Note that different threads may access the same solution
 * object (e.g. the search thread & the paint() method).
 * This can be synchronized by using the methods acquireAccess()
 * and releaseAccess().
 *
 * @author Ebner Dietmar, ebner@apm.tuwien.ac.at
 */

public final class Solution {
    //the instance for this solution
    private Instance instance = null;

    //where to place the labels
    private Label[] labels = null;

    /**
     * constructs a new initial Solution to the given instance.
     * For every node a list of neighbours will be generated.
     * The initial position is one of the four possible corner positions
     *
     * @param random the Random given to the labels, to determine initial placement
     * @param inst   a reference to the {@link Instance instance object}
     */
    public Solution(Instance inst, boolean init_solution, Random random) {
        instance = inst;

        if (instance != null) {
            labels = new Label[instance.getNodes().length];

            for (int i = 0; i < instance.getNodes().length; i++) {
                labels[i] = new Label(instance.getNodes()[i], i, random);
            }
        }

        setNeighbours();

        if (init_solution) {
            findInitialPlacement();
        }
    }


    private void setNeighbours() {
        for (int i = 0; i < labels.length; i++) {
            Label current = labels[i];

            //create a list of neighbours
            for (int j = 0; j < labels.length; j++) {
                if (i != j) {
                    if (current.getNode().canIntersect(labels[j].getNode())) {
                        current.addNeighbour(labels[j]);
                    }
                }
            }
        }
    }

    private void findInitialPlacement() {
        for (Label label : labels) {
            if (label.hasNeighbours()) {
                label.findInitialPlacement();
            } else {
                label.moveTo(0., 0.);
            }
        }
    }

    /**
     * returns the array of all {@link Label labels} corresponding to the given {@link Instance instance}
     */
    public Label[] getLabels() {
        return labels;
    }

    /**
     * returns the size of the solution
     */
    public int size() {
        if (instance == null) {
            return 0;
        } else {
            return instance.size();
        }
    }

    /**
     * @return reference to the associated instance
     */
    public Instance getInstance() {
        return instance;
    }

}
