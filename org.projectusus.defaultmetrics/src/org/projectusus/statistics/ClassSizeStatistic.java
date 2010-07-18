package org.projectusus.statistics;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultCockpitExtension;

public class ClassSizeStatistic extends DefaultCockpitExtension {

    private static int KG_LIMIT = 20;
    private static String isisMetrics_kg = "Class size"; //$NON-NLS-1$

    public ClassSizeStatistic() {
        super( isisMetrics_kg, codeProportionUnit_CLASS_label, KG_LIMIT );
    }

    @Override
    public void inspectClass( SourceCodeLocation location, MetricsResults results ) {
        addViolation( location, results.getIntValue( MetricsResults.METHODS ) );
    }
}
