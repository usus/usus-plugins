// Copyright (c) 2005-2006, 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.model;

import org.projectusus.core.IMetricCWHotspot;

public class MetricCWHotspot extends Hotspot implements IMetricCWHotspot {

    public MetricCWHotspot( int count ) {
        super( count, 1, 1 );
    }

    public String getFileName() {
        return getFile().getName();
    }

    public int getWarningCount() {
        return getHotness();
    }

    @Override
    public String toString() {
        return getFileName() + " (CW = " + getWarningCount() + ")"; //$NON-NLS-1$//$NON-NLS-2$ 
    }
}
