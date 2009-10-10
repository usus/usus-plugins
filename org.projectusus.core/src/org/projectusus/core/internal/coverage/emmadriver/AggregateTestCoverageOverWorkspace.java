// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.coverage.emmadriver;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.projectusus.core.internal.coverage.TestCoverage.from;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.projectusus.core.internal.coverage.TestCoverage;
import org.projectusus.core.internal.project.FindUsusProjects;

import com.mountainminds.eclemma.core.analysis.ICounter;

class AggregateTestCoverageOverWorkspace {

    private final Coverages coverages;

    AggregateTestCoverageOverWorkspace( Coverages coverages ) {
        this.coverages = coverages;
    }

    public TestCoverage compute() {
        TestCoverage totalCoverage = newEmptyTestCoverage();
        for( IProject project : findUsusProjects() ) {
            ICounter instr = coverages.getInstructionCoverage( project );
            if( instr != null ) {
                totalCoverage = totalCoverage.add( from( instr ) );
            }
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
