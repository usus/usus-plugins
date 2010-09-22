package org.projectusus.statistics;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultCockpitExtension;

public class CyclomaticComplexityStatistic extends DefaultCockpitExtension {

    private static int CC_LIMIT = 5;

    public CyclomaticComplexityStatistic() {
        super( codeProportionUnit_METHOD_label, CC_LIMIT );
    }

    @Override
    public void inspectMethod( SourceCodeLocation location, MetricsResults results ) {
        addViolation( location, results.getIntValue( MetricsResults.CC, 1 ) );
    }

    @Override
    public String getLabel() {
        return "Cyclomatic complexity (constant limit)"; //$NON-NLS-1$
    }

    @Override
    protected String getDescription() {
        String description = getLabel() + ": Hotspots are methods with a CC greater than %d."; //$NON-NLS-1$
        return String.format( description, new Integer( CC_LIMIT ) );
    }
}
