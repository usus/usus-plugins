// Copyright (c) 2005-2006, 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.yellowcount;

import static org.projectusus.core.internal.proportions.sqi.IsisMetrics.CW;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.projectusus.core.internal.proportions.CodeProportionsRatio;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.model.IHotspot;

class WorkspaceCount {

    private final List<ProjectCount> projectCounts = new ArrayList<ProjectCount>();
    private final List<IProject> ususProjects;

    WorkspaceCount( List<IProject> ususProjects ) {
        this.ususProjects = ususProjects;
    }

    void add( ProjectCount projectCount ) {
        if( !projectCount.isEmpty() ) {
            projectCounts.add( projectCount );
        }
    }

    int countViolations() {
        int result = 0;
        for( ProjectCount projectCount : projectCounts ) {
            result += projectCount.sum();
        }
        return result;
    }

    int countProjectsWithViolations() {
        return projectCounts.size();
    }

    CodeProportion createCodeProportion() {
        int basis = new CountWSFiles( ususProjects ).compute();
        int violations = countViolations();
        return new CodeProportion( CW, violations, basis, computeSqi( basis ), getHotspots() );
    }

    private List<IHotspot> getHotspots() {
        List<IHotspot> result = new ArrayList<IHotspot>();
        for( ProjectCount projectCount : projectCounts ) {
            result.addAll( projectCount.getHotspots() );
        }
        return result;
    }

    private double computeSqi( int basis ) {
        return new CodeProportionsRatio( countViolations(), basis ).computeReverseIndicator();
    }
}
