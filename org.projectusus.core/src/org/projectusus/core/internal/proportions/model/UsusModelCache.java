// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.model;

import static org.projectusus.core.basis.CodeProportionKind.CW;

import java.util.List;

import org.projectusus.core.IUsusElement;
import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.CodeProportionKind;

public class UsusModelCache {

    private final CodeProportions codeProportionsCache = new CodeProportions();
    private final Warnings warningsCache = new Warnings();

    public IUsusElement[] getElements() {
        return new IUsusElement[] { codeProportionsCache, warningsCache };
    }

    public void refresh( CodeProportion proportion ) {
        CodeProportionKind metric = proportion.getMetric();
        if( metric == CW ) {
            warningsCache.refresh( proportion );
        } else {
            codeProportionsCache.refresh( proportion );
        }
    }

    public void refreshAll( List<CodeProportion> proportions ) {
        for( CodeProportion metric : proportions ) {
            refresh( metric );
        }
    }

    public CodeProportion getCodeProportion( CodeProportionKind kind ) {
        if( kind == CW ) {
            return warningsCache.getProportion();
        }
        return codeProportionsCache.forKind( kind );
    }
}
