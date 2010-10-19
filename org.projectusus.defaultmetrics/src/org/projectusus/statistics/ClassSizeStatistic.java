package org.projectusus.statistics;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.CockpitExtension;

public class ClassSizeStatistic extends CockpitExtension {

    private static int KG_LIMIT = 20;

    public ClassSizeStatistic() {
        super( codeProportionUnit_CLASS_label, KG_LIMIT );
    }

    @Override
    public void inspectClass( SourceCodeLocation location, MetricsResults results ) {
        addResult( location, results.getIntValue( MetricsResults.METHODS ) );
    }

    @Override
    public String getLabel() {
        return "Class size (constant limit)"; //$NON-NLS-1$
    }

    @Override
    protected String getDescription() {
        String description = getLabel() + ": Hotspots are classes with more than %d methods."; //$NON-NLS-1$
        return String.format( description, new Integer( KG_LIMIT ) );
    }
}
