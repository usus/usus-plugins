// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import static org.projectusus.core.internal.proportions.sqi.IsisMetrics.CW;

import org.projectusus.core.internal.proportions.sqi.IsisMetrics;
import org.projectusus.core.internal.yellowcount.IYellowCountResult;

public class CodeProportion {

    private final IsisMetrics metric;
    private final int violations;
    private final int basis;
    private final double sqi;

    public CodeProportion( IsisMetrics metric ) {
        this( metric, 0, 0, 0 );
    }

    public CodeProportion( IsisMetrics metric, int violations, int basis, double sqi ) {
        this.metric = metric;
        this.violations = violations;
        this.basis = basis;
        this.sqi = sqi;
    }

    public CodeProportion( IYellowCountResult yellowCountResult ) {
        // TODO lf project count is not really the basis for CW, and 42 is not always the answer
        this( CW, yellowCountResult.getYellowCount(), yellowCountResult.getProjectCount(), 42.0 );
    }

    public Double getSQIValue() {
        return new Double( sqi );
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
                return CodeProportion.this.toString();
            }
        };
    }
}
