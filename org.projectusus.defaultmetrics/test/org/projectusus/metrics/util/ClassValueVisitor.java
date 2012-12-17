package org.projectusus.metrics.util;

import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultMetricsResultVisitor;

public class ClassValueVisitor extends DefaultMetricsResultVisitor {
    private final String valueName;
    private int valueSum = 0;
    private String name;

    public ClassValueVisitor( String valueName ) {
        this.valueName = valueName;
    }

    @Override
    public void inspectClass( SourceCodeLocation location, MetricsResults results ) {
        name = location.getName();
        valueSum += results.getIntValue( valueName );
    }

    public int getValueSum() {
        return valueSum;
    }

    public String getName() {
        return name;
    }
}
