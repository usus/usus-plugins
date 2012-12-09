package org.projectusus.metrics.util;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultMetricsResultVisitor;

public class AbstractnessVisitor extends DefaultMetricsResultVisitor {
    private int abstractness;
    private String name;

    @Override
    public void inspectClass( SourceCodeLocation location, MetricsResults results ) {
        name = location.getName();
        abstractness = results.getIntValue( MetricsResults.ABSTRACTNESS );
    }

    public int getAbstractness() {
        return abstractness;
    }

    public String getName() {
        return name;
    }
}
