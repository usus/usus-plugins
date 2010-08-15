package org.projectusus.statistics;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultCockpitExtension;

public class MethodLengthStatistic extends DefaultCockpitExtension {

    private static int ML_LIMIT = 15;
    
    private static final String label = "Method length"; //$NON-NLS-1$
    private static final String description = label + " [0: <= %d]"; //$NON-NLS-1$

    private static String metricsDescription() {
        return String.format( description, new Integer( ML_LIMIT ) );
    }

    public MethodLengthStatistic() {
        super( label, metricsDescription(), codeProportionUnit_METHOD_label, ML_LIMIT );
    }

    @Override
    public void inspectMethod( SourceCodeLocation location, MetricsResults result ) {
        addViolation( location, result.getIntValue( MetricsResults.ML ) );
    }
}
