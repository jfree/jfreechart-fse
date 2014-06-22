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

/**
 * The thread implementing the algorithm.
 *
 * @author Ebner Dietmar, ebner@apm.tuwien.ac.at
 */

public abstract class LabelPlacingAlgorithm {
    protected Instance instance;

    public LabelPlacingAlgorithm(Instance instance) {
        this.instance = instance;
    }

    public String toString() {
        return "pflp search algorithm: force directed labeling";
    }

    public Solution createSolution() {
        boolean halt = false;

        preCompute(instance);

        //start doing the "real" work...
        while (true) {
            if (halt) {
                return getFinishedSolution();
            }

            halt = iterate();
        }
    }

    /**
     * Called after iterate has returned false.
     * Used to obtain the final solution from the algoritm.
     *
     * @return the solution
     */
    protected abstract Solution getFinishedSolution();

    /**
     * does the real work
     *
     * @return true <-> algorithm is ready
     */
    protected abstract boolean iterate();

    /**
     * computations that should happen only once
     */
    protected abstract void preCompute(Instance instance);
}
