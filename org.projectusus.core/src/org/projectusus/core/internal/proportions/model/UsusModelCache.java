// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.model;

import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.CW;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.TA;

import java.util.List;

import org.projectusus.core.internal.proportions.rawdata.CodeProportionKind;

public class UsusModelCache {

    private final TestCoverage testCoverageCache = new TestCoverage();
    private final CodeProportions codeProportionsCache = new CodeProportions();
    private final Warnings warningsCache = new Warnings();

    public IUsusElement[] getElements() {
        return new IUsusElement[] { codeProportionsCache, warningsCache, testCoverageCache };
    }

    public void refresh( CodeProportion proportion ) {
        CodeProportionKind metric = proportion.getMetric();
        if( metric == TA ) {
            testCoverageCache.refresh( proportion );
        } else if( metric == CW ) {
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
}
