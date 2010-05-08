// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.model;

import org.projectusus.core.IMetricCCHotspot;

public class MetricCCHotspot extends Hotspot implements IMetricCCHotspot {

    private final String className;
    private final String methodName;
    private final int cyclomaticComplexity;

    public MetricCCHotspot( String className, String methodName, int cyclomaticComplexity, int sourcePosition, int lineNumber ) {
        super( cyclomaticComplexity, sourcePosition, lineNumber );
        this.className = className;
        this.methodName = methodName;
        this.cyclomaticComplexity = cyclomaticComplexity;
    }

    public String getClassName() {
        return className;
    }

    public int getCyclomaticComplexity() {
        return cyclomaticComplexity;
    }

    public String getMethodName() {
        return methodName;
    }

    @Override
    public String toString() {
        return getClassName() + "." + getMethodName() + " (CC = " + getCyclomaticComplexity() + ")"; //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    }
}
