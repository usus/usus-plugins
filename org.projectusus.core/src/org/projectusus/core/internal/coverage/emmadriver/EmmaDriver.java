// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.coverage.emmadriver;

import static com.mountainminds.eclemma.core.CoverageTools.addJavaCoverageListener;
import static com.mountainminds.eclemma.core.CoverageTools.getJavaModelCoverage;
import static com.mountainminds.eclemma.core.CoverageTools.removeJavaCoverageListener;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.TA;

import java.util.ArrayList;

import org.eclipse.jdt.core.IJavaProject;
import org.projectusus.core.internal.UsusCorePlugin;
import org.projectusus.core.internal.coverage.IEmmaDriver;
import org.projectusus.core.internal.coverage.TestCoverage;
import org.projectusus.core.internal.proportions.CodeProportionsRatio;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.model.CodeStatistic;
import org.projectusus.core.internal.proportions.model.IHotspot;
import org.projectusus.core.internal.proportions.modelupdate.IUsusModelUpdate;
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
        for( IJavaProject javaProject : instrumentedProjects ) {
            IJavaElementCoverage coverage = javaModelCoverage.getCoverageFor( javaProject );
            if( coverage != null ) {
                UsusCorePlugin.getUsusModelMetricsWriter().setInstructionCoverage( javaProject.getProject(), coverage );
            }
        }
        notifyListeners();
    }

    private void notifyListeners() {
        TestCoverage coverage = UsusCorePlugin.getUsusModel().getInstructionCoverage();
        updateUsusModel( coverage );
    }

    private void updateUsusModel( TestCoverage coverage ) {
        int covered = coverage.getCoveredCount();
        int total = coverage.getTotalCount();
        double sqi = new CodeProportionsRatio( covered, total ).compute();
        CodeStatistic statistic = new CodeStatistic( TA.getUnit(), total );
        CodeProportion codeProportion = new CodeProportion( TA, covered, statistic, sqi, new ArrayList<IHotspot>() );
        // TODO lf add hotspots
        IUsusModelUpdate updateCommand = new TestRunModelUpdate( codeProportion );
        UsusCorePlugin.getUsusModelWriteAccess().update( updateCommand );
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

    public void setActive( boolean active ) {
        this.active = active;
    }
}
