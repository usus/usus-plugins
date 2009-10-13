// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.checkstyledriver;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.projectusus.core.internal.proportions.IsisMetrics;

class CheckResultsCollector {

    private final Map<IProject, ProjectCheckResult> projectResults = new HashMap<IProject, ProjectCheckResult>();

    void addResult( IProject project, IsisMetrics metrics, IIsisMetricsCheckResult result ) {
        getProjectResult( project ).update( result );
    }

    ProjectCheckResult getProjectResult( IProject project ) {
        ProjectCheckResult result = projectResults.get( project );
        if( result == null ) {
            result = newProjectResult( project );
        }
        return result;
    }

    ProjectCheckResult cumulate() {
        ProjectCheckResult result = new ProjectCheckResult();
        for( IsisMetrics metric : IsisMetrics.values() ) {
            int cases = 0;
            int violations = 0;
            Collection<ProjectCheckResult> checkResults = projectResults.values();
            for( ProjectCheckResult checkResult : checkResults ) {
                IIsisMetricsCheckResult resultForMetric = checkResult.get( metric );
                if( resultForMetric != null ) {
                    cases += resultForMetric.getNumberOfCases();
                    violations += resultForMetric.getNumberOfViolations();
                }
            }
            result.update( new IsisMetricsCheckResult( metric, cases, violations ) );
        }
        return result;
    }

    private ProjectCheckResult newProjectResult( IProject project ) {
        ProjectCheckResult result = new ProjectCheckResult();
        projectResults.put( project, result );
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for( IProject project : projectResults.keySet() ) {
            result.append( project.getName() );
            result.append( "\n" ); //$NON-NLS-1$
            result.append( projectResults.get( project ) );
            result.append( "\n\n" ); //$NON-NLS-1$
        }
        return result.toString();
    }
}
