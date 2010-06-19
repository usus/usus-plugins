// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import org.projectusus.core.basis.IMethodRawData;
import org.projectusus.core.basis.IRawData;

public class MethodRawData implements IRawData, IMethodRawData {

    private final int startPosition;
    private final String className;
    private final String methodName;
    private final int lineNumber;

    private int ccValue;
    private int mlValue;

    public MethodRawData( int startPosition, int lineNumber, String className, String methodName ) {
        this.startPosition = startPosition;
        this.lineNumber = lineNumber;
        this.className = className;
        this.methodName = methodName;
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

    public String getMethodName() {
        return methodName;
    }

    public int getSourcePosition() {
        return startPosition;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void acceptAndGuide( MetricsResultVisitor visitor ) {
        visitor.inspect( this );
    }

    public String getClassName() {
        return className;
    }
}
