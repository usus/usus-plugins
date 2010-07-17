package org.projectusus.statistics;

import static org.projectusus.core.internal.util.CoreTexts.codeProportionUnit_METHOD_label;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultCockpitExtension;

public class CyclomaticComplexityStatistic extends DefaultCockpitExtension {

    private static int CC_LIMIT = 5;
    private static String isisMetrics_cc = "Cyclomatic complexity";

    public CyclomaticComplexityStatistic() {
        super( isisMetrics_cc, codeProportionUnit_METHOD_label, CC_LIMIT );
    }

    @Override
    public void inspectMethod( SourceCodeLocation location, MetricsResults results ) {
        addViolation( location, results.getIntValue( MetricsResults.CC, 1 ) );
    }
}
