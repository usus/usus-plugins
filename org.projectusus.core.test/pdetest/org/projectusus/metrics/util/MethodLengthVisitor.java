package org.projectusus.metrics.util;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultMetricsResultVisitor;

public class MethodLengthVisitor extends DefaultMetricsResultVisitor {

    private int ml = 0;

    @Override
    public void inspectMethod( SourceCodeLocation location, MetricsResults results ) {
        ml += results.getIntValue( MetricsResults.ML, 1 );
    }

    public int getML() {
        return ml;
    }
}
