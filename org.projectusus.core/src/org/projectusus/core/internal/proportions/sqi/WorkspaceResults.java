// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.model.IHotspot;
import org.projectusus.core.internal.proportions.sqi.acd.AcdModel;

public class WorkspaceResults extends Results<IProject, ProjectResults> {

    private IProject currentProject;

    private static final WorkspaceResults instance = new WorkspaceResults();

    private final AcdModel acdModel = new AcdModel();

    private WorkspaceResults() {
        super();
    }

    public static WorkspaceResults getInstance() {
        return instance;
    }

    public AcdModel getAcdModel() {
        return acdModel;
    }

    public void setCurrentProject( IProject project ) {
        this.currentProject = project;
    }

    public ProjectResults getCurrentProjectResults() {
        // log warning if currentProject == null
        return getProjectResults( currentProject );
    }

    public ProjectResults getProjectResults( IProject project ) {
        return getResults( project, new ProjectResults( project ) );
    }

    public void setCurrentFile( IFile currentFile ) {
        getCurrentProjectResults().setCurrentFile( currentFile );
    }

    public void dropResults( IProject project ) {
        remove( project );
    }

    public void dropResults( IFile file ) {
        getCurrentProjectResults().dropResults( file );
    }

    public CodeProportion getCodeProportion( IsisMetrics metric ) {
        int violations = getViolationCount( metric );
        int basis = getViolationBasis( metric );
        double sqi = 0.0;
        if( metric == IsisMetrics.ACD ) {

        } else {
            sqi = new SQIComputer( basis, violations, metric.getCalibration() ).compute();
        }
        List<IHotspot> hotspots = new ArrayList<IHotspot>();
        addHotspots( metric, hotspots );
        return new CodeProportion( metric, violations, basis, sqi, hotspots );
    }
}
