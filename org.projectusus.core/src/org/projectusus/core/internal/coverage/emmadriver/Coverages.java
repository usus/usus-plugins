// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.coverage.emmadriver;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.projectusus.core.internal.project.IUSUSProject;

import com.mountainminds.eclemma.core.analysis.ICounter;
import com.mountainminds.eclemma.core.analysis.IJavaElementCoverage;

public class Coverages {

    Map<IProject, IJavaElementCoverage> coverages;

    Coverages() {
        coverages = new HashMap<IProject, IJavaElementCoverage>();
    }

    void add( IProject project, IJavaElementCoverage coverage ) {
        Object adapter = project.getAdapter( IUSUSProject.class );
        if( adapter instanceof IUSUSProject ) {
            IUSUSProject ususProject = (IUSUSProject)adapter;
            if( ususProject.isUsusProject() ) {
                coverages.put( project, coverage );
            }
        }
    }

    IJavaElementCoverage get( IProject project ) {
        return coverages.get( project );
    }

    boolean isEmpty() {
        return coverages.isEmpty();
    }

    ICounter getInstructionCoverage( IProject project ) {
        ICounter result = null;
        IJavaElementCoverage coverage = coverages.get( project );
        if( coverage != null ) {
            result = coverage.getInstructionCounter();
        }
        return result;
    }
}
