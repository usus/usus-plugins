// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.model.IHotspot;

public class WorkspaceRawData extends RawData<IProject, ProjectRawData> {

    private IProject currentProject;

    private static WorkspaceRawData instance = new WorkspaceRawData();

    private final AcdModel acdModel = new AcdModel();

    private WorkspaceRawData() {
        super();
    }

    public static WorkspaceRawData getInstance() {
        return instance;
    }

    public AcdModel getAcdModel() {
        return acdModel;
    }

    public void setCurrentProject( IProject project ) {
        this.currentProject = project;
    }

    public ProjectRawData getCurrentProjectRawData() {
        // log warning if currentProject == null
        return getProjectRawData( currentProject );
    }

    ProjectRawData getProjectRawData( IProject project ) {
        return getRawData( project, new ProjectRawData( project ) );
    }

    public void setCurrentFile( IFile currentFile ) {
        getCurrentProjectRawData().setCurrentFile( currentFile );
    }

    public void dropRawData( IProject project ) {
        remove( project );
    }

    public void dropRawData( IFile file ) {
        getCurrentProjectRawData().dropRawData( file );
    }

    public CodeProportion getCodeProportion( CodeProportionKind metric ) {
        int violations = getViolationCount( metric );
        int basis = getViolationBasis( metric );
        double sqi = computeSQI( metric, violations, basis );
        List<IHotspot> hotspots = computeHotspots( metric );
        return new CodeProportion( metric, violations, basis, sqi, hotspots );
    }

    private List<IHotspot> computeHotspots( CodeProportionKind metric ) {
        List<IHotspot> hotspots = new ArrayList<IHotspot>();
        addToHotspots( metric, hotspots );
        return hotspots;
    }

    private double computeSQI( CodeProportionKind metric, int violations, int basis ) {
        double sqi;
        if( metric == CodeProportionKind.ACD ) {
            sqi = 100.0 - acdModel.getRelativeACD() * 100.0;
        } else {
            sqi = new SQIComputer( basis, violations, metric ).compute();
        }
        return sqi;
    }
}
