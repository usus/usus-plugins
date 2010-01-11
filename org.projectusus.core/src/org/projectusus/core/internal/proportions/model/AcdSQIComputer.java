package org.projectusus.core.internal.proportions.model;

import java.util.Set;

import org.projectusus.core.internal.proportions.rawdata.ClassRawData;
import org.projectusus.core.internal.proportions.rawdata.WorkspaceRawData;

public class AcdSQIComputer {

    private Set<ClassRawData> classes;

    public double compute( WorkspaceRawData workspace ) {
        classes = workspace.getAllClassRawData();
        return 100.0 - getRelativeACD_internal() * 100.0;
    }

    public double getRelativeACD() {
        classes = WorkspaceRawData.getInstance().getAllClassRawData();
        return getRelativeACD_internal();
    }

    // internal

    // / <summary>
    // / The relative ACD of a system with n components is ACD/n.
    // / Thus it is a percentage value in range [0%, 100%].
    // / </summary>
    // / <returns></returns>
    private double getRelativeACD_internal() {
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
        for( ClassRawData node : classes ) {
            allDependencies += node.getCCDResult();
        }
        return allDependencies;
    }
}
