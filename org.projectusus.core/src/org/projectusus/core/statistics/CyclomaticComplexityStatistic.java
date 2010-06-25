package org.projectusus.core.statistics;

import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;

public class CyclomaticComplexityStatistic extends DefaultStatistic {

    private static int CC_LIMIT = 5;

    public CyclomaticComplexityStatistic() {
        super( CC_LIMIT );
    }

    public CyclomaticComplexityStatistic( JavaModelPath path ) {
        super( path, CC_LIMIT );
    }

    @Override
    public void inspectMethod( SourceCodeLocation location, MetricsResults results ) {
        addViolation( location, results.getIntValue( MetricsResults.CC, 1 ) );
    }

}
