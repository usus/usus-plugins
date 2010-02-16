// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.model;

public class MetricACDHotspot extends Hotspot implements IMetricACDHotspot {

    private final String className;
    private final int classCCD;

    public MetricACDHotspot( String className, int classCCD, int sourcePosition, int lineNumber ) {
        super( classCCD, sourcePosition, lineNumber );
        this.className = className;
        this.classCCD = classCCD;
    }

    public String getClassName() {
        return className;
    }

    public int getClassCCD() {
        return classCCD;
    }

    @Override
    public String toString() {
        return getClassName() + " (ACD = " + getClassCCD() + ")"; //$NON-NLS-1$//$NON-NLS-2$ 
    }
}
