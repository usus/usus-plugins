// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.checkstyledriver;

import java.util.HashMap;
import java.util.Map;

import org.projectusus.core.internal.proportions.sqi.IsisMetrics;

public class ProjectCheckResult {

    private final Map<IsisMetrics, IIsisMetricsCheckResult> results;

    ProjectCheckResult() {
        results = new HashMap<IsisMetrics, IIsisMetricsCheckResult>();
    }

    public IIsisMetricsCheckResult get( IsisMetrics metric ) {
        return results.get( metric );
    }

    void update( IIsisMetricsCheckResult checkResult ) {
        results.put( checkResult.getMetric(), checkResult );
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for( IsisMetrics metrics : results.keySet() ) {
            result.append( metrics.getLabel() );
            result.append( metrics.getLabel().length() == 2 ? " : " : ": " ); // ;-)  //$NON-NLS-1$//$NON-NLS-2$
            result.append( results.get( metrics ) );
            result.append( "\n" ); //$NON-NLS-1$
        }
        return result.toString();
    }

}
