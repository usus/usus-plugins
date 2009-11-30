// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.model;

import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.CW;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.TA;

import org.projectusus.core.internal.proportions.rawdata.CodeProportionKind;

public class UsusModelRootNode {

    private final TestCoverage testCoverage = new TestCoverage();
    private final CodeProportions codeProportions = new CodeProportions();
    private final Warnings warnings = new Warnings();

    public IUsusElement[] getElements() {
        return new IUsusElement[] { codeProportions, warnings, testCoverage };
    }

    public void add( CodeProportion proportion ) {
        CodeProportionKind metric = proportion.getMetric();
        if( metric == TA ) {
            testCoverage.update( proportion );
        } else if( metric == CW ) {
            warnings.update( proportion );
        } else {
            codeProportions.update( proportion );
        }
    }
}
