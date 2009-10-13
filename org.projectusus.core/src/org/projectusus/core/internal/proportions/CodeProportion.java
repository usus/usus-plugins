// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import static org.projectusus.core.internal.proportions.IsisMetrics.CW;
import static org.projectusus.core.internal.proportions.IsisMetrics.TA;

import org.projectusus.core.internal.coverage.TestCoverage;
import org.projectusus.core.internal.yellowcount.IYellowCountResult;

public class CodeProportion {

    private final IsisMetrics metric;
    private final int violations;
    private final int basis;

    public CodeProportion( IsisMetrics metric ) {
        this( metric, 0, 0 );
    }

    public CodeProportion( IsisMetrics metric, int violations, int basis ) {
        this.metric = metric;
        this.violations = violations;
        this.basis = basis;
    }

    public CodeProportion( TestCoverage coverage ) {
        this( TA, coverage.getCoveredCount(), coverage.getTotalCount() );
    }

    public CodeProportion( IYellowCountResult yellowCountResult ) {
        // TODO lf project count is not really the basis for CW
        this( CW, yellowCountResult.getYellowCount(), yellowCountResult.getProjectCount() );
    }

    public Double getGraphValue() {
        return new Double( violations );
    }

    public int getViolations() {
        return violations;
    }

    public int getBasis() {
        return basis;
    }

    @Override
    public String toString() {
        return metric.toString() + ": " + violations + " / " + basis; //$NON-NLS-1$ //$NON-NLS-2$
    }

    public IsisMetrics getMetric() {
        return metric;
    }

    public IHotSpots getHotspots() {
        return new IHotSpots() {
            @Override
            public String toString() {
                return "Hotspots for " + CodeProportion.this.toString();
            }
        };
    }
}
