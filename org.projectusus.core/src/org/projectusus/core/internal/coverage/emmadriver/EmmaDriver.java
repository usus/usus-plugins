// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.coverage.emmadriver;

import static com.mountainminds.eclemma.core.CoverageTools.addJavaCoverageListener;
import static com.mountainminds.eclemma.core.CoverageTools.getJavaModelCoverage;
import static com.mountainminds.eclemma.core.CoverageTools.removeJavaCoverageListener;

import org.eclipse.jdt.core.IJavaProject;
import org.projectusus.core.internal.UsusCorePlugin;
import org.projectusus.core.internal.coverage.IEmmaDriver;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.modelupdate.IUsusModelUpdate;
import org.projectusus.core.internal.proportions.modelupdate.TestRunModelUpdate;
import org.projectusus.core.internal.proportions.rawdata.CodeProportionKind;

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
        // UsusCorePlugin.getUsusModelMetricsWriter().resetInstructionCoverage(); ??
        IJavaProject[] instrumentedProjects = javaModelCoverage.getInstrumentedProjects();
        for( IJavaProject javaProject : instrumentedProjects ) {
            IJavaElementCoverage coverage = javaModelCoverage.getCoverageFor( javaProject );
            UsusCorePlugin.getUsusModelMetricsWriter().setInstructionCoverage( javaProject.getProject(), coverage );
        }
        notifyListeners();
    }

    private void notifyListeners() {
        CodeProportion codeProportion = UsusCorePlugin.getUsusModel().getCodeProportion( CodeProportionKind.TA );
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
