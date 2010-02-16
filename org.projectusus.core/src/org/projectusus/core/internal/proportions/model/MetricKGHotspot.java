// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.model;

public class MetricKGHotspot extends Hotspot implements IMetricKGHotspot {

    private final String className;
    private final int classSize;

    public MetricKGHotspot( String className, int classSize, int sourcePosition, int lineNumber ) {
        super( classSize, sourcePosition, lineNumber );
        this.className = className;
        this.classSize = classSize;
    }

    public String getClassName() {
        return className;
    }

    public int getClassSize() {
        return classSize;
    }

    @Override
    public String toString() {
        return getClassName() + " (KG = " + getClassSize() + ")"; //$NON-NLS-1$//$NON-NLS-2$ 
    }
}
