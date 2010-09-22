package org.projectusus.statistics;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultCockpitExtension;

public class ClassSizeStatistic extends DefaultCockpitExtension {

    private static int KG_LIMIT = 20;

    private static final String label = "Class size (constant limit)"; //$NON-NLS-1$
    private static final String description = label + ": Hotspots are classes with more than %d methods."; //$NON-NLS-1$

    private static String metricsDescription() {
        return String.format( description, new Integer( KG_LIMIT ) );
    }

    public ClassSizeStatistic() {
        super( label, metricsDescription(), codeProportionUnit_CLASS_label, KG_LIMIT );
    }

    @Override
    public void inspectClass( SourceCodeLocation location, MetricsResults results ) {
        addViolation( location, results.getIntValue( MetricsResults.METHODS ) );
    }
}
