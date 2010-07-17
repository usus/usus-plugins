package org.projectusus.statistics;

import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultCockpitExtension;

public class ClassSizeStatistic extends DefaultCockpitExtension {

    private static int KG_LIMIT = 20;
    private static String isisMetrics_kg = "Class size";

    public ClassSizeStatistic() {
        super( isisMetrics_kg, KG_LIMIT );
    }

    @Override
    public void inspectClass( SourceCodeLocation location, MetricsResults results ) {
        addViolation( location, results.getIntValue( MetricsResults.METHODS ) );
    }

    public CodeStatistic getBasis() {
        return numberOfClasses();
    }
}
