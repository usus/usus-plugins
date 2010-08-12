package org.projectusus.statistics;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultCockpitExtension;

public class LinearCyclomaticComplexityStatistic extends DefaultCockpitExtension {

    private static int CC_LIMIT = 4;
    private static String label = "Cyclomatic complexity (linear: 0: <= %d | 1: %d)"; //$NON-NLS-1$

    private double linearViolations = 0.0;

    private static String metricsLabel() {
        return String.format( label, new Integer( CC_LIMIT ), new Integer( 2 * CC_LIMIT ) );
    }

    public LinearCyclomaticComplexityStatistic() {
        super( metricsLabel(), codeProportionUnit_METHOD_label, CC_LIMIT );
    }

    @Override
    public void inspectMethod( SourceCodeLocation location, MetricsResults results ) {
        int ccValue = results.getIntValue( MetricsResults.CC, 1 );
        addViolation( location, ccValue );
        int exceedingCC = ccValue - CC_LIMIT;
        if( exceedingCC > 0 ) {
            linearViolations += ((double)exceedingCC / CC_LIMIT);
        }
    }

    @Override
    public double getLevel() {
        return calculateLevel( linearViolations, getBasis() );
    }

}
