// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.model;

import static java.util.Arrays.asList;
import static org.projectusus.core.basis.CodeProportionKind.CW;

import java.util.List;

import org.projectusus.core.IWarnings;
import org.projectusus.core.basis.CodeProportion;

class Warnings implements IWarnings {

    private CodeProportion entry = new CodeProportion( CW );

    void refresh( CodeProportion entry ) {
        this.entry = entry;
    }

    public List<CodeProportion> getEntries() {
        return asList( entry );
    }

    public CodeProportion getProportion() {
        return entry;
    }
}
