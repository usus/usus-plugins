// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.coverage;

import static com.mountainminds.eclemma.core.CoverageTools.addJavaCoverageListener;
import static com.mountainminds.eclemma.core.CoverageTools.getJavaModelCoverage;
import static com.mountainminds.eclemma.core.CoverageTools.removeJavaCoverageListener;

import org.projectusus.core.internal.UsusCorePlugin;

import com.mountainminds.eclemma.core.analysis.IJavaCoverageListener;
import com.mountainminds.eclemma.core.analysis.IJavaModelCoverage;

public class EmmaDriver {

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
        UsusCorePlugin.getUsusModelMetricsWriter().collectCoverageInfo( javaModelCoverage );
    }

    protected IJavaCoverageListener createEmmaListener() {
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
