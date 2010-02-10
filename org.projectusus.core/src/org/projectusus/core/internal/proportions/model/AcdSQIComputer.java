package org.projectusus.core.internal.proportions.model;

import java.util.List;

import org.projectusus.core.internal.UsusCorePlugin;

public class AcdSQIComputer {

    private int numberOfClasses;
    private List<Integer> ccdResults;

    public double compute( List<Integer> ccdResults ) {
        this.numberOfClasses = ccdResults.size();
        this.ccdResults = ccdResults;
        return 100.0 - getRelativeACD_internal() * 100.0;
    }

    public double getRelativeACD() {
        numberOfClasses = UsusCorePlugin.getUsusModel().getAllClassRawDataCount();
        ccdResults = UsusCorePlugin.getUsusModel().getAllClassesCCDResults();
        return getRelativeACD_internal();
    }

    // internal

    // / <summary>
    // / The relative ACD of a system with n components is ACD/n.
    // / Thus it is a percentage value in range [0%, 100%].
    // / </summary>
    // / <returns></returns>
    private double getRelativeACD_internal() {
        if( numberOfClasses == 0 ) {
            return 0.0;
        }
        return getACD() / numberOfClasses;
    }

    // / <summary>
    // / The average component dependency (ACD) of a system with n components is
    // CCD/n.
    // / </summary>
    // / <returns></returns>
    private double getACD() {
        if( numberOfClasses == 0 ) {
            return 0.0;
        }
        return getCCD() / (double)numberOfClasses;
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
        for( Integer result : ccdResults ) {
            allDependencies += result.intValue();
        }
        return allDependencies;
    }
}
