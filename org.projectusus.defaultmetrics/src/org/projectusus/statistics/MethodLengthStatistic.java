package org.projectusus.statistics;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.CockpitExtension;

public class MethodLengthStatistic extends CockpitExtension {

    private static int ML_LIMIT = 15;

    public MethodLengthStatistic() {
        super( codeProportionUnit_METHOD_label, ML_LIMIT );
    }

    @Override
    public void inspectMethod( SourceCodeLocation location, MetricsResults result ) {
        addResult( location, result.getIntValue( MetricsResults.ML ) );
    }

    @Override
    public String getLabel() {
        return "Method length (constant limit)"; //$NON-NLS-1$
    }

    @Override
    protected String getDescription() {
        String description = getLabel() + ": Hotspots are methods with more than %d statements."; //$NON-NLS-1$   
        return String.format( description, new Integer( ML_LIMIT ) );
    }
}
