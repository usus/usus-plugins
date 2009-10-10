// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.coverage.emmadriver;

import static com.mountainminds.eclemma.core.CoverageTools.getJavaModelCoverage;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IJavaProject;
import org.projectusus.core.internal.coverage.ICoverageListener;
import org.projectusus.core.internal.coverage.TestCoverage;

import com.mountainminds.eclemma.core.CoverageTools;
import com.mountainminds.eclemma.core.analysis.IJavaCoverageListener;
import com.mountainminds.eclemma.core.analysis.IJavaElementCoverage;
import com.mountainminds.eclemma.core.analysis.IJavaModelCoverage;

public class EmmaDriver {

    private final List<ICoverageListener> listeners;
    private final IJavaCoverageListener emmaListener;

    public EmmaDriver() {
        listeners = new ArrayList<ICoverageListener>();
        emmaListener = createEmmaListener();
        CoverageTools.addJavaCoverageListener( emmaListener );
    }

    public void dispose() {
        CoverageTools.removeJavaCoverageListener( emmaListener );
    }

    public void addCoverageListener( ICoverageListener listener ) {
        listeners.add( listener );
    }

    public void removeCoverageListener( ICoverageListener listener ) {
        listeners.remove( listener );
    }

    private void collectCoverageInfo( IJavaModelCoverage javaModelCoverage ) {
        IJavaProject[] instrumentedProjects = javaModelCoverage.getInstrumentedProjects();
        Coverages coverages = new Coverages();
        for( IJavaProject javaProject : instrumentedProjects ) {
            IJavaElementCoverage coverage = javaModelCoverage.getCoverageFor( javaProject );
            if( coverage != null ) {
                coverages.add( javaProject.getProject(), coverage );
            }
        }
        notifyListeners( coverages );
    }

    private void notifyListeners( Coverages coverages ) {
        if( !coverages.isEmpty() ) {
            doNotifyListeners( coverages );
        }
    }

    private void doNotifyListeners( Coverages coverages ) {
        TestCoverage coverage = new AggregateTestCoverageOverWorkspace( coverages ).compute();
        for( ICoverageListener listener : listeners ) {
            listener.coverageChanged( coverage );
        }
    }

    private IJavaCoverageListener createEmmaListener() {
        return new IJavaCoverageListener() {
            public void coverageChanged() {
                IJavaModelCoverage javaModelCoverage = getJavaModelCoverage();
                if( javaModelCoverage != null ) {
                    collectCoverageInfo( javaModelCoverage );
                }
            }
        };
    }
}
