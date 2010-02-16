// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.bugreport;

import java.io.Serializable;

public class BugMetrics implements Serializable {

    private static final long serialVersionUID = 8848470186591089059L;

    private int cyclomaticComplexity;
    private int methodLength;
    private int numberOfMethods;

    public int getCyclomaticComplexity() {
        return cyclomaticComplexity;
    }

    public void setCyclomaticComplexity( int cyclomaticComplexity ) {
        this.cyclomaticComplexity = cyclomaticComplexity;
    }

    public int getMethodLength() {
        return methodLength;
    }

    public void setMethodLength( int methodLength ) {
        this.methodLength = methodLength;
    }

    public int getNumberOfMethods() {
        return numberOfMethods;
    }

    public void setNumberOfMethods( int numberOfMethods ) {
        this.numberOfMethods = numberOfMethods;
    }

    public void add( BugMetrics other ) {
        cyclomaticComplexity += other.getCyclomaticComplexity();
        methodLength += other.getMethodLength();
        numberOfMethods += other.getNumberOfMethods();
    }

}
