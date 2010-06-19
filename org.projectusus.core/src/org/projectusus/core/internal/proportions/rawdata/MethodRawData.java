// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import org.projectusus.core.basis.IMethodRawData;
import org.projectusus.core.basis.IRawData;

public class MethodRawData implements IRawData, IMethodRawData {

    private SourceCodeLocation location;
    // private MetricsResults

    private int ccValue;
    private int mlValue;

    public MethodRawData( int startPosition, int lineNumber, String className, String methodName ) {
        location = new SourceCodeLocation( className + "." + methodName, startPosition, lineNumber ); //$NON-NLS-1$
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

    public void acceptAndGuide( MetricsResultVisitor visitor ) {
        visitor.inspect( location, this );
    }
}
