package org.projectusus.core.filerelations.internal.metrics;

import org.projectusus.core.filerelations.model.ClassDescriptor;

public class ACDCalculator {

    // / The relative ACD of a system with n components is ACD/n.
    // / Thus it is a percentage value in range [0%, 100%].
    public static double getRelativeACD() {
        int numberOfClasses = ClassDescriptor.getAll().size();
        if( numberOfClasses == 0 ) {
            return 0.0;
        }
        return getACD() / numberOfClasses;
    }

    // / The average component dependency (ACD) of a system with n components is
    // CCD/n.
    private static double getACD() {
        int numberOfClasses = ClassDescriptor.getAll().size();
        if( numberOfClasses == 0 ) {
            return 0.0;
        }
        return getCCD() / (double)numberOfClasses;
    }

    // / The cumulative component dependency (CCD) of a (sub)system is the sum
    // over all
    // / components Ci of the (sub)system of the number of components needed to
    // test each Ci incrementally.
    private static int getCCD() {
        int allDependencies = 0;
        for( ClassDescriptor descriptor : ClassDescriptor.getAll() ) {
            allDependencies += descriptor.getCCD();
        }
        return allDependencies;
    }
}
