// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.model;

public class MetricCCHotspot extends Hotspot {

    public MetricCCHotspot( String className, String methodName, int cyclomaticComplexity, int sourcePosition, int lineNumber ) {
        super( className + "." + methodName, cyclomaticComplexity, sourcePosition, lineNumber );
    }

    @Override
    public String toString() {
        return getName() + " (CC = " + getMetricsValue() + ")"; //$NON-NLS-1$//$NON-NLS-2$ 
    }
}
