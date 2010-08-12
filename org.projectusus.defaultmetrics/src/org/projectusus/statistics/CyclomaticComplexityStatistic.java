package org.projectusus.statistics;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultCockpitExtension;

public class CyclomaticComplexityStatistic extends DefaultCockpitExtension {

    private static int CC_LIMIT = 5;
    private static String label = "Cyclomatic complexity (0: <= %d)"; //$NON-NLS-1$

    private static String metricsLabel() {
        return String.format( label, new Integer( CC_LIMIT ) );
    }

    public CyclomaticComplexityStatistic() {
        super( metricsLabel(), codeProportionUnit_METHOD_label, CC_LIMIT );
    }

    @Override
    public void inspectMethod( SourceCodeLocation location, MetricsResults results ) {
        addViolation( location, results.getIntValue( MetricsResults.CC, 1 ) );
    }
}
