package org.projectusus.metrics.util;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultMetricsResultVisitor;

public class ClassSizeVisitor extends DefaultMetricsResultVisitor {

    private int kg = 0;

    @Override
    public void inspectClass( SourceCodeLocation location, MetricsResults results ) {
        kg += results.getIntValue( MetricsResults.METHODS, 1 );
    }

    public int getClassSizeSum() {
        return kg;
    }
}
