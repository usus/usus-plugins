// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.model;

import org.projectusus.core.IMetricMLHotspot;

public class MetricMLHotspot extends Hotspot implements IMetricMLHotspot {

    private final String className;
    private final String methodName;
    private final int methodLength;

    public MetricMLHotspot( String className, String methodName, int methodLength, int sourcePosition, int lineNumber ) {
        super( methodLength, sourcePosition, lineNumber );
        this.className = className;
        this.methodName = methodName;
        this.methodLength = methodLength;
    }

    public String getClassName() {
        return className;
    }

    public int getMethodLength() {
        return methodLength;
    }

    public String getMethodName() {
        return methodName;
    }

    @Override
    public String toString() {
        return getClassName() + "." + getMethodName() + " (ML = " + getMethodLength() + ")"; //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    }
}
