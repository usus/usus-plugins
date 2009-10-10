// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

public class CodeProportion {

    private final IsisMetrics metric;
    private final String value;
    private final Double graphValue;

    public CodeProportion( IsisMetrics metric, String value ) {
        this( metric, value, new Double( 0.0 ) );
    }

    public CodeProportion( IsisMetrics metric, String value, int graphValue ) {
        this( metric, value, new Double( graphValue ) );
    }

    public CodeProportion( IsisMetrics metric, String value, Double graphValue ) {
        this.metric = metric;
        this.value = value;
        this.graphValue = graphValue;
    }

    public Double getGraphValue() {
        return graphValue;
    }

    @Override
    public String toString() {
        return metric.toString() + ": " + value;
    }

    public IsisMetrics getMetric() {
        return metric;
    }

    public IHotSpots getHotspots() {
        return new IHotSpots() {
        };
    }
}
