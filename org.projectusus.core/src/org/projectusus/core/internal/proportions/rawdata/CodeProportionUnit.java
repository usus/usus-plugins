// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import static org.projectusus.core.internal.util.CoreTexts.codeProportionUnit_CLASS_label;
import static org.projectusus.core.internal.util.CoreTexts.codeProportionUnit_FILE_label;
import static org.projectusus.core.internal.util.CoreTexts.codeProportionUnit_LINE_label;
import static org.projectusus.core.internal.util.CoreTexts.codeProportionUnit_METHOD_label;
import static org.projectusus.core.internal.util.CoreTexts.codeProportionUnit_PACKAGE_label;
import static org.projectusus.core.internal.util.CoreTexts.codeProportionUnit_PROJECT_label;

public enum CodeProportionUnit {

    METHOD( codeProportionUnit_METHOD_label ) {
        @Override
        public boolean isMethodKind() {
            return true;
        }
    }, //
    CLASS( codeProportionUnit_CLASS_label ), //
    PACKAGE( codeProportionUnit_PACKAGE_label ), //
    LINE( codeProportionUnit_LINE_label ), //
    JAVAFILE( codeProportionUnit_FILE_label ), //
    PROJECT( codeProportionUnit_PROJECT_label ), //
    ANYFILE( codeProportionUnit_FILE_label );

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
