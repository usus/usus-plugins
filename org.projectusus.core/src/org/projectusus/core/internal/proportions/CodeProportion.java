// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

public class CodeProportion {

    private final IsisMetrics metric;
    private final String value;

    CodeProportion( IsisMetrics metric, String value ) {
        this.metric = metric;
        this.value = value;
    }

    @Override
    public String toString() {
        return metric.toString() + ": " + value;
    }

    public IsisMetrics getMetric() {
        return metric;
    }
}
