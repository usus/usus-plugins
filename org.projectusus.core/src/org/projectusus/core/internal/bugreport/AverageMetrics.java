// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.bugreport;

import static org.projectusus.core.internal.util.CoreTexts.AverageMetrics_overall;

import org.projectusus.core.internal.proportions.sqi.ProjectResults;

public class AverageMetrics implements IAverageMetrics {

    public double getAverageCyclomaticComplexity() {
        return 0;
    }

    public double getAverageMethodLength() {
        return 0;
    }

    public double getAverageNumberOfMethodsInClass() {
        return 0;
    }

    public void addProjectResults( ProjectResults projectResults ) {
        // TODO: Implement this
    }

    public String getName() {
        return AverageMetrics_overall;
    }

}
