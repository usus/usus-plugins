// Copyright (c) 2005-2006, 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.yellowcount;

import static org.projectusus.core.internal.proportions.sqi.IsisMetrics.CW;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.projectusus.core.internal.UsusCorePlugin;
import org.projectusus.core.internal.proportions.CodeProportionsRatio;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.model.IHotspot;

public class WorkspaceYellowCount {

    private final List<ProjectYellowCount> projectCounts = new ArrayList<ProjectYellowCount>();
    private final CodeProportion codeProportion;

    public WorkspaceYellowCount( List<IProject> ususProjects ) {
        performCount( ususProjects );
        codeProportion = createCodeProportion( ususProjects );
    }

    public CodeProportion getCodeProportion() {
        return codeProportion;
    }

    public int countProjectsWithViolations() {
        return projectCounts.size();
    }

    private void performCount( List<IProject> ususProjects ) {
        for( IProject project : ususProjects ) {
            try {
                add( new ProjectYellowCount( project ) );
            } catch( CoreException cex ) {
                UsusCorePlugin.log( cex );
            }
        }
    }

    private CodeProportion createCodeProportion( List<IProject> ususProjects ) {
        int basis = new CountWSFiles( ususProjects ).compute();
        int violations = countViolations();
        return new CodeProportion( CW, violations, basis, computeSqi( basis ), getHotspots() );
    }

    private List<IHotspot> getHotspots() {
        List<IHotspot> result = new ArrayList<IHotspot>();
        for( ProjectYellowCount projectCount : projectCounts ) {
            result.addAll( projectCount.getHotspots() );
        }
        return result;
    }

    private double computeSqi( int basis ) {
        return new CodeProportionsRatio( countViolations(), basis ).computeReverseIndicator();
    }

    private void add( ProjectYellowCount projectCount ) {
        if( projectCount.getYellowCount() > 0 ) {
            projectCounts.add( projectCount );
        }
    }

    private int countViolations() {
        int result = 0;
        for( ProjectYellowCount projectCount : projectCounts ) {
            result += projectCount.getYellowCount();
        }
        return result;
    }
}
