// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.coverage.emmadriver;

import static com.mountainminds.eclemma.core.CoverageTools.addJavaCoverageListener;
import static com.mountainminds.eclemma.core.CoverageTools.getJavaModelCoverage;
import static com.mountainminds.eclemma.core.CoverageTools.removeJavaCoverageListener;
import static org.projectusus.core.internal.proportions.sqi.CodeProportionKind.TA;

import java.util.ArrayList;

import org.eclipse.jdt.core.IJavaProject;
import org.projectusus.core.internal.coverage.IEmmaDriver;
import org.projectusus.core.internal.coverage.TestCoverage;
import org.projectusus.core.internal.proportions.CodeProportionsRatio;
import org.projectusus.core.internal.proportions.UsusModel;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.model.IHotspot;
import org.projectusus.core.internal.proportions.modelupdate.TestRunModelUpdate;

import com.mountainminds.eclemma.core.analysis.IJavaCoverageListener;
import com.mountainminds.eclemma.core.analysis.IJavaElementCoverage;
import com.mountainminds.eclemma.core.analysis.IJavaModelCoverage;

public class EmmaDriver implements IEmmaDriver {

    private final IJavaCoverageListener emmaListener;
    private boolean active;

    public EmmaDriver() {
        emmaListener = createEmmaListener();
        addJavaCoverageListener( emmaListener );
    }

    public void dispose() {
        removeJavaCoverageListener( emmaListener );
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
            TestCoverage coverage = new AggregateTestCoverageOverWorkspace( coverages ).compute();
            updateUsusModel( coverage );
        }
    }

    private void updateUsusModel( TestCoverage coverage ) {
        int covered = coverage.getCoveredCount();
        int total = coverage.getTotalCount();
        double sqi = new CodeProportionsRatio( covered, total ).compute();
        CodeProportion codeProportion = new CodeProportion( TA, covered, total, sqi, new ArrayList<IHotspot>() );
        // TODO lf add hotspots?
        getUsusModel().update( new TestRunModelUpdate( codeProportion ) );
    }

    private IJavaCoverageListener createEmmaListener() {
        return new IJavaCoverageListener() {
            public void coverageChanged() {
                IJavaModelCoverage javaModelCoverage = getJavaModelCoverage();
                if( active && javaModelCoverage != null ) {
                    collectCoverageInfo( javaModelCoverage );
                }
            }
        };
    }

    private UsusModel getUsusModel() {
        return ((UsusModel)UsusModel.getUsusModel());
    }

    public void setActive( boolean active ) {
        this.active = active;
    }
}
