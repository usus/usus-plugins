// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.coverage.emmadriver;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IJavaProject;
import org.projectusus.core.internal.coverage.TestCoverage;
import org.projectusus.core.internal.project.IUSUSProject;

import com.mountainminds.eclemma.core.CoverageTools;
import com.mountainminds.eclemma.core.analysis.ICounter;
import com.mountainminds.eclemma.core.analysis.IJavaCoverageListener;
import com.mountainminds.eclemma.core.analysis.IJavaModelCoverage;

public class EclEmmaListener implements IJavaCoverageListener {

    private final List<ICoverageListener> listeners;

    public EclEmmaListener() {
        this.listeners = new ArrayList<ICoverageListener>();
    }

    public void coverageChanged() {
        IJavaModelCoverage javaModelCoverage = CoverageTools.getJavaModelCoverage();
        IJavaProject[] instrumentedProjects = javaModelCoverage.getInstrumentedProjects();
        for( IJavaProject javaProject : instrumentedProjects ) {
            ICounter lineCoverage = javaModelCoverage.getCoverageFor( javaProject ).getInstructionCounter();
            if( lineCoverage != null ) {
                applyCoverageToUsusProject( javaProject, lineCoverage );
            }
        }
    }

    private void applyCoverageToUsusProject( IJavaProject javaProject, ICounter lineCoverage ) {
        TestCoverage coverage = TestCoverage.from( lineCoverage );
        Object adapter = javaProject.getProject().getAdapter( IUSUSProject.class );
        if( adapter instanceof IUSUSProject ) {
            IUSUSProject ususProject = (IUSUSProject)adapter;
            if( ususProject.isUsusProject() ) {
                ususProject.setTestCoverage( coverage );
            }
        }
        notifyListeners();
    }

    private void notifyListeners() {
        for( ICoverageListener listener : listeners ) {
            listener.coverageChanged();
        }
    }

    public void addCoverageListener( ICoverageListener listener ) {
        listeners.add( listener );
    }

    public void removeCoverageListener( ICoverageListener listener ) {
        listeners.remove( listener );
    }

}
