package org.projectusus.core.statistics;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;

public class ClassSizeStatistic extends DefaultStatistic {

    private static int KG_LIMIT = 20;

    public ClassSizeStatistic() {
        super( KG_LIMIT );
    }

    @Override
    public void inspectClass( SourceCodeLocation location, MetricsResults results ) {
        addViolation( location, results.getIntValue( MetricsResults.METHODS ) );
    }

}
