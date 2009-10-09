// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

public enum IsisMetrics {

    TA( "Test coverage" ), PC( "Package cycles" ), CC( "Cyclomatic complexity" ), ACD( "Average component dependency" ), KG( "Class size" ), ML( "Method length" ), CW(
            "Compiler warnings" );

    private final String label;

    private IsisMetrics( String label ) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
