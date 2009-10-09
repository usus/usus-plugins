// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.checkstyledriver;

import static java.lang.Integer.parseInt;

import org.projectusus.core.internal.proportions.IsisMetrics;
import org.projectusus.core.internal.proportions.sqi.Violations;

public class IsisMetricsCheckResult implements IIsisMetricsCheckResult {

    private final IsisMetrics metric;
    private final int violations;
    private final int cases;

    IsisMetricsCheckResult( IsisMetrics metric, int cases, int violations ) {
        this.metric = metric;
        this.cases = cases;
        this.violations = violations;
    }

    public IsisMetricsCheckResult( IsisMetrics metrics, Violations violations ) {
        this( metrics, violations.getNumberOfClasses(), violations.getNumberOfViolations() );
    }

    public static IsisMetricsCheckResult fromString( String stringRepresentation ) {
        String[] elems = stringRepresentation.split( ":" );
        return new IsisMetricsCheckResult( readMetrics( elems[0] ), readInt( elems[1] ), readInt( elems[2] ) );
    }

    @Override
    public String toString() {
        return metric.name() + ":" + cases + ":" + violations;
    }

    public IsisMetrics getMetric() {
        return metric;
    }

    public int getNumberOfCases() {
        return cases;
    }

    public int getNumberOfViolations() {
        return violations;
    }

    // internal methods
    // /////////////////

    private static IsisMetrics readMetrics( String stringRepresentation ) {
        return IsisMetrics.valueOf( stringRepresentation );
    }

    private static int readInt( String stringRepresentation ) {
        int result = 0;
        try {
            result = parseInt( stringRepresentation );
        } catch( NumberFormatException numfex ) {
            // ignore
        }
        return result;
    }
}
