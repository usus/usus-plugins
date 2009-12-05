// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

public enum CodeProportionUnit {

    METHOD( "methods" ) { //$NON-NLS-1$
        @Override
        public boolean isMethodKind() {
            return true;
        }
    }, //
    CLASS( "classes" ), PACKAGE( "packages" ), FILE( "files" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 

    private final String label;

    private CodeProportionUnit( String label ) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public boolean isMethodKind() {
        return false;
    }
}
