package org.projectusus.statistics;

import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultCockpitExtension;

public class MethodLengthStatistic extends DefaultCockpitExtension {

    private static int ML_LIMIT = 15;
    private static String isisMetrics_ml = "Method length";

    public MethodLengthStatistic() {
        super( isisMetrics_ml, ML_LIMIT );
    }

    @Override
    public void inspectMethod( SourceCodeLocation location, MetricsResults result ) {
        addViolation( location, result.getIntValue( MetricsResults.ML ) );
    }

    public CodeStatistic getBasis() {
        return numberOfMethods();
    }
}
