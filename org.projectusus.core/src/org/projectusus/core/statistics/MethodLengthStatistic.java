package org.projectusus.core.statistics;

import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;

public class MethodLengthStatistic extends DefaultStatistic {

    private static int ML_LIMIT = 15;

    public MethodLengthStatistic() {
        super( ML_LIMIT );
    }

    public MethodLengthStatistic( JavaModelPath path ) {
        super( path, ML_LIMIT );
    }

    @Override
    public void inspectMethod( SourceCodeLocation location, MetricsResults result ) {
        addViolation( location, result.getIntValue( MetricsResults.ML ) );
    }

}
