package org.projectusus.statistics;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultCockpitExtension;

public class MethodLengthStatistic extends DefaultCockpitExtension {

    private static int ML_LIMIT = 15;
    private static String label = "Method length (0: <= %d)"; //$NON-NLS-1$

    private static String metricsLabel() {
        return String.format( label, new Integer( ML_LIMIT ) );
    }

    public MethodLengthStatistic() {
        super( metricsLabel(), codeProportionUnit_METHOD_label, ML_LIMIT );
    }

    @Override
    public void inspectMethod( SourceCodeLocation location, MetricsResults result ) {
        addViolation( location, result.getIntValue( MetricsResults.ML ) );
    }
}
