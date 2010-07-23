// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import org.projectusus.core.basis.IRawData;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.IMetricsResultVisitor;

public class MethodRawData implements IRawData {

    private SourceCodeLocation location;
    private MetricsResults data;

    public MethodRawData( int startPosition, int lineNumber, String className, String methodName ) {
        location = new SourceCodeLocation( className + "." + methodName + "()", startPosition, lineNumber ); //$NON-NLS-1$ //$NON-NLS-2$
        data = new MetricsResults();
    }

    public void putData( String dataKey, int value ) {
        data.put( dataKey, value );
    }

    public void acceptAndGuide( IMetricsResultVisitor visitor ) {
        visitor.inspectMethod( location, data );
    }
}
