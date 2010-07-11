package org.projectusus.core.filerelations.internal.metrics;

import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.statistics.ACDStatistic;

public class ACDCalculator {

    // / The relative ACD of a system with n components is ACD/n.
    // / Thus it is a percentage value in range [0%, 100%].
    public static double getRelativeACD() {
        int numberOfClasses = ClassDescriptor.getAll().size();
        if( numberOfClasses == 0 ) {
            return 0.0;
        }
        ACDStatistic statistic = new ACDStatistic();
        statistic.visit();
        return statistic.getMetricsSum() / (double)(numberOfClasses * numberOfClasses);
    }
}
