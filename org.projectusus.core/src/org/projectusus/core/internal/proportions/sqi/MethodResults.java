// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi;

import java.util.List;

import org.projectusus.core.internal.proportions.model.IHotspot;
import org.projectusus.core.internal.proportions.model.MetricCCHotspot;
import org.projectusus.core.internal.proportions.model.MetricMLHotspot;

public class MethodResults implements IResults {

    private final int startPosition;
    private final String className;
    private final String methodName;

    private int ccResult;
    private int mlResult;

    public MethodResults( int startPosition, String className, String methodName ) {
        this.startPosition = startPosition;
        this.className = className;
        this.methodName = methodName;
    }

    public void setCCResult( int value ) {
        ccResult = value;
    }

    public int getCCResult() {
        return ccResult;
    }

    public void setMLResult( int value ) {
        mlResult = value;
    }

    public int getMLResult() {
        return mlResult;
    }

    public String getMethodName() {
        return methodName;
    }

    public int getSourcePosition() {
        return startPosition;
    }

    public boolean violates( IsisMetrics metric ) {
        return metric.isViolatedBy( this );
    }

    public int getViolationBasis( IsisMetrics metric ) {
        return 1;
    }

    public int getViolationCount( IsisMetrics metric ) {
        return metric.isViolatedBy( this ) ? 1 : 0;
    }

    public void addHotspots( IsisMetrics metric, List<IHotspot> hotspots ) {
        if( metric.isViolatedBy( this ) ) {
            IHotspot hotspot = null;
            if( metric.equals( IsisMetrics.CC ) ) {
                hotspot = new MetricCCHotspot( className, getMethodName(), getCCResult(), getSourcePosition() );
            } else if( metric.equals( IsisMetrics.ML ) ) {
                hotspot = new MetricMLHotspot( className, getMethodName(), getMLResult(), getSourcePosition() );
            }
            hotspots.add( hotspot );
        }
    }

    public int getOverallMetric( IsisMetrics metric ) {
        return metric.getValueFor( this );
    }
}
