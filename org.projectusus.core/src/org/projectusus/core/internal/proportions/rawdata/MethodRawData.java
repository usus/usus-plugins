// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import java.util.List;

import org.projectusus.core.internal.proportions.model.IHotspot;
import org.projectusus.core.internal.proportions.model.MetricCCHotspot;
import org.projectusus.core.internal.proportions.model.MetricMLHotspot;

class MethodRawData implements IRawData {

    private final int startPosition;
    private final String className;
    private final String methodName;
    private final int lineNumber;

    private int ccValue;
    private int mlValue;

    public MethodRawData( int startPosition, int lineNumber, String className, String methodName ) {
        this.startPosition = startPosition;
        this.lineNumber = lineNumber;
        this.className = className;
        this.methodName = methodName;
    }

    public void setCCValue( int value ) {
        ccValue = value;
    }

    public int getCCValue() {
        return ccValue;
    }

    public void setMLValue( int value ) {
        mlValue = value;
    }

    public int getMLValue() {
        return mlValue;
    }

    public String getMethodName() {
        return methodName;
    }

    public int getSourcePosition() {
        return startPosition;
    }

    public boolean violates( CodeProportionKind metric ) {
        return metric.isViolatedBy( this );
    }

    public int getNumberOf( @SuppressWarnings( "unused" ) CodeProportionUnit unit ) {
        return 1;
    }

    public int getViolationCount( CodeProportionKind metric ) {
        return metric.isViolatedBy( this ) ? 1 : 0;
    }

    public void addToHotspots( CodeProportionKind metric, List<IHotspot> hotspots ) {
        if( metric.isViolatedBy( this ) ) {
            if( metric.equals( CodeProportionKind.CC ) ) {
                hotspots.add( new MetricCCHotspot( className, getMethodName(), getCCValue(), getSourcePosition(), getLineNumber() ) );
            } else if( metric.equals( CodeProportionKind.ML ) ) {
                hotspots.add( new MetricMLHotspot( className, getMethodName(), getMLValue(), getSourcePosition(), getLineNumber() ) );
            }
        }
    }

    public int getOverallMetric( CodeProportionKind metric ) {
        return metric.getValueFor( this );
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void resetRawData() {
        ccValue = 0;
        mlValue = 0;
    }
}
