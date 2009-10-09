// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.coverage;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.projectusus.core.internal.project.FindUsusProjects;
import org.projectusus.core.internal.project.IUSUSProject;


public class AggregateTestCoverageOverWorkspace {

    public TestCoverage compute() {
        TestCoverage totalCoverage = newEmptyTestCoverage();
        for( IProject project : findUsusProjects() ) {
            IUSUSProject ususProject = (IUSUSProject)project.getAdapter( IUSUSProject.class );
            totalCoverage = totalCoverage.add( ususProject.getTestCoverage() );
        }
        return totalCoverage;
    }

    private TestCoverage newEmptyTestCoverage() {
        return new TestCoverage( 0, 0 );
    }

    private List<IProject> findUsusProjects() {
        IProject[] allProjects = getWorkspace().getRoot().getProjects();
        return new FindUsusProjects( allProjects ).compute();
    }
}
