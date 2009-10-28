// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi;

import java.util.List;

public class MethodResults implements IResults {

    private final String packageName;
    private final String className;
    private final String methodName;
    private final int lineNo;
    private int ccResult;
    private int mlResult;

    public MethodResults( String packageName, String className, String methodName, int lineNo ) {
        this.packageName = packageName;
        this.className = className;
        this.methodName = methodName;
        this.lineNo = lineNo;
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

    public int getLineNo() {
        return lineNo;
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

    public void getViolationNames( IsisMetrics metric, List<String> violations ) {
        if( metric.isViolatedBy( this ) ) {
            violations.add( this.getMethodName() );
        }
    }

    public void getViolationLineNumbers( IsisMetrics metric, List<Integer> violations ) {
        if( metric.isViolatedBy( this ) ) {
            violations.add( new Integer( this.getLineNo() ) );
        }
    }

}
