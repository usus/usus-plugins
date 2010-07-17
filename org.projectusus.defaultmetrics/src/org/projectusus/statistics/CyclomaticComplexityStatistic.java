package org.projectusus.statistics;

import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultCockpitExtension;

public class CyclomaticComplexityStatistic extends DefaultCockpitExtension {

    private static int CC_LIMIT = 5;
    private static String isisMetrics_cc = "Cyclomatic complexity";

    public CyclomaticComplexityStatistic() {
        super( isisMetrics_cc, CC_LIMIT );
    }

    @Override
    public void inspectMethod( SourceCodeLocation location, MetricsResults results ) {
        addViolation( location, results.getIntValue( MetricsResults.CC, 1 ) );
    }

    public CodeStatistic getBasis() {
        return numberOfMethods();
    }
}
