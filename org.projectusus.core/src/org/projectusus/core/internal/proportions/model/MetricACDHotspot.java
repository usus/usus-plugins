// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.model;

public class MetricACDHotspot extends Hotspot {

    public MetricACDHotspot( String className, int classCCD, int sourcePosition, int lineNumber ) {
        super( className, classCCD, sourcePosition, lineNumber );
    }

    @Override
    public String toString() {
        return getName() + " (ACD = " + getMetricsValue() + ")"; //$NON-NLS-1$//$NON-NLS-2$ 
    }
}
