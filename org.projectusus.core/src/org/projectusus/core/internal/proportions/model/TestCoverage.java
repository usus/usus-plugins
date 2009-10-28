// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.model;

import static java.util.Arrays.asList;
import static org.projectusus.core.internal.proportions.sqi.IsisMetrics.TA;

import java.util.List;


class TestCoverage implements ITestCoverage {

    private CodeProportion entry = new CodeProportion( TA );

    public List<CodeProportion> getEntries() {
        return asList( entry );
    }

    void update( CodeProportion entry ) {
        this.entry = entry;
    }
}
