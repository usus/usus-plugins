package org.projectusus.statistics;

import static org.projectusus.core.internal.util.CoreTexts.codeProportionUnit_METHOD_label;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultCockpitExtension;

public class MethodLengthStatistic extends DefaultCockpitExtension {

    private static int ML_LIMIT = 15;
    private static String isisMetrics_ml = "Method length";

    public MethodLengthStatistic() {
        super( isisMetrics_ml, codeProportionUnit_METHOD_label, ML_LIMIT );
    }

    @Override
    public void inspectMethod( SourceCodeLocation location, MetricsResults result ) {
        addViolation( location, result.getIntValue( MetricsResults.ML ) );
    }
}
