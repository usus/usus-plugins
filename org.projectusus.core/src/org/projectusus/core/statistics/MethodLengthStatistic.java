package org.projectusus.core.statistics;

import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_ml;

import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;

public class MethodLengthStatistic extends DefaultStatistic {

    private static int ML_LIMIT = 15;

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

}
