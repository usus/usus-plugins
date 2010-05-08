package org.projectusus.core.internal.proportions.rawdata;

import org.projectusus.core.basis.CodeProportionKind;
import org.projectusus.core.basis.IHotspot;
import org.projectusus.core.internal.proportions.model.MetricCWHotspot;

public class WarningsCount {

    private int count;

    public void setWarningsCount( int count ) {
        this.count = count;
    }

    public int getViolationCount( CodeProportionKind metric ) {
        return metric.operatesOnNonJavaFiles() && (count > 0) ? 1 : 0;
    }

    public IHotspot createHotspot() {
        return new MetricCWHotspot( count );
    }

    public int getOverallMetric( CodeProportionKind metric ) {
        if( metric == CodeProportionKind.CW ) {
            return count;
        }
        return 0;
    }
}
