package org.projectusus.core.statistics;

import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_cc;

import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;

public class CyclomaticComplexityStatistic extends DefaultStatistic {

    private static int CC_LIMIT = 5;

    public CyclomaticComplexityStatistic() {
        super( isisMetrics_cc, CC_LIMIT );
    }

    public CyclomaticComplexityStatistic( JavaModelPath path ) {
        super( isisMetrics_cc, path, CC_LIMIT );
    }

    @Override
    public void inspectMethod( SourceCodeLocation location, MetricsResults results ) {
        addViolation( location, results.getIntValue( MetricsResults.CC, 1 ) );
    }

    public CodeStatistic getBasis() {
        return numberOfMethods();
    }

    public CyclomaticComplexityStatistic visitAndReturn() {
        visit();
        return this;
    }

}
