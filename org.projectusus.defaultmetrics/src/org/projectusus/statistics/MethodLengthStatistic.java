package org.projectusus.statistics;

import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;

public class MethodLengthStatistic extends DefaultStatistic {

    private static int ML_LIMIT = 15;
    private static String isisMetrics_ml = "Method length";

    public MethodLengthStatistic() {
        super( isisMetrics_ml, ML_LIMIT );
    }

    public MethodLengthStatistic( JavaModelPath path ) {
        super( isisMetrics_ml, path, ML_LIMIT );
    }

    @Override
    public void inspectMethod( SourceCodeLocation location, MetricsResults result ) {
        addViolation( location, result.getIntValue( MetricsResults.ML ) );
    }

    public CodeStatistic getBasis() {
        return numberOfMethods();
    }

    public MethodLengthStatistic visitAndReturn() {
        visit();
        return this;
    }

}
