// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import java.util.ArrayList;
import java.util.List;

public class AcdModel {

    private List<AdjacencyNode> classes = new ArrayList<AdjacencyNode>();

    public void add( AdjacencyNode node ) {
        classes.add( node );
    }

    public void remove( AdjacencyNode node ) {
        classes.remove( node );
    }

    // / <summary>
    // / The relative ACD of a system with n components is ACD/n.
    // / Thus it is a percentage value in range [0%, 100%].
    // / </summary>
    // / <returns></returns>
    public double getRelativeACD() {
        if( classes.size() == 0 ) {
            return 0.0;
        }
        return getACD() / classes.size();
    }

    // / <summary>
    // / The average component dependency (ACD) of a system with n components is
    // CCD/n.
    // / </summary>
    // / <returns></returns>
    private double getACD() {
        if( classes.size() == 0 ) {
            return 0.0;
        }
        return getCCD() / (double)classes.size();
    }

    // / <summary>
    // / The cumulative component dependency (CCD) of a (sub)system is the sum
    // over all
    // / components Ci of the (sub)system of the number of components needed to
    // test each Ci incrementally.
    // / </summary>
    // / <returns></returns>
    private int getCCD() {
        int allDependencies = 0;
        for( AdjacencyNode node : classes ) {
            allDependencies += node.getCCD();
        }
        return allDependencies;
    }
}
