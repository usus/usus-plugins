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

    private static WorkspaceRawData instance = new WorkspaceRawData();

    private WorkspaceRawData() {
        super();
    }

    public static WorkspaceRawData getInstance() {
        return instance;
    }

    ProjectRawData getProjectRawData( IProject project ) {
        ProjectRawData rawData = super.getRawData( project );
        if( rawData == null ) {
            rawData = new ProjectRawData( project );
            super.addRawData( project, rawData );
        }
        return rawData;
    }

    public void dropRawData( IProject project ) {
        remove( project );
    }

    public void dropRawData( IFile file ) {
        ((ProjectRawData)file.getProject().getAdapter( IProjectRawData.class )).dropRawData( file );
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
            sqi = 100.0; // TODO - acdModel.getRelativeACD() * 100.0;
        } else {
            sqi = new SQIComputer( basis, violations, metric ).compute();
        }
        return sqi;
    }
}
