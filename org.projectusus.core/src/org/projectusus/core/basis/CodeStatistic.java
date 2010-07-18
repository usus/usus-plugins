// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.basis;

import org.eclipse.core.runtime.PlatformObject;

public class CodeStatistic extends PlatformObject {

    private final int value;
    private final String label;

    public CodeStatistic( String label ) {
        this( label, 0 );
    }

    public CodeStatistic( String label, int value ) {
        this.label = label;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value + " " + label; //$NON-NLS-1$
    }
}
