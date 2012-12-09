package org.projectusus.metrics.util;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultMetricsResultVisitor;

public class CyclomaticComplexityVisitor extends DefaultMetricsResultVisitor {

    private int cc = 0;

    @Override
    public void inspectMethod( SourceCodeLocation location, MetricsResults results ) {
        cc += results.getIntValue( MetricsResults.CC, 1 );
    }

    public int getCC() {
        return cc;
    }
}
