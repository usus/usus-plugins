package org.projectusus.statistics;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultCockpitExtension;

public class CyclomaticComplexityStatistic extends DefaultCockpitExtension {

    private static int CC_LIMIT = 5;

    private static final String label = "Cyclomatic complexity"; //$NON-NLS-1$
    private static final String description = label + " [0: <= %d]"; //$NON-NLS-1$

    private static String metricsDescription() {
        return String.format( description, new Integer( CC_LIMIT ) );
    }

    public CyclomaticComplexityStatistic() {
        super( label, metricsDescription(), codeProportionUnit_METHOD_label, CC_LIMIT );
    }

    @Override
    public void inspectMethod( SourceCodeLocation location, MetricsResults results ) {
        addViolation( location, results.getIntValue( MetricsResults.CC, 1 ) );
    }
}
