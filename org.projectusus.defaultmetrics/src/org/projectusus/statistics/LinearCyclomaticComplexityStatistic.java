package org.projectusus.statistics;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultCockpitExtension;

public class LinearCyclomaticComplexityStatistic extends DefaultCockpitExtension {

    private static int CC_LIMIT = 5;
    private static String isisMetrics_cc = "Cyclomatic complexity (linear)"; //$NON-NLS-1$

    private double linearViolations = 0.0;

    public LinearCyclomaticComplexityStatistic() {
        super( isisMetrics_cc, codeProportionUnit_METHOD_label, CC_LIMIT );
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
