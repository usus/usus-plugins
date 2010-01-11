// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.model.CodeStatistic;
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

    public void resetRawData( IProject project ) {
        ProjectRawData rawData = super.getRawData( project );
        if( rawData != null ) {
            rawData.resetRawData();
        }
    }

    public void resetRawData( IFile file ) {
        FileRawData rawData = ((ProjectRawData)JDTSupport.getProjectRawDataFor( file )).getRawData( file );
        if( rawData != null ) {
            rawData.resetRawData();
        }
    }

    public void dropRawData( IProject project ) {
        resetRawData( project );
        remove( project );
    }

    public void dropRawData( IFile file ) {
        resetRawData( file );
        ((ProjectRawData)JDTSupport.getProjectRawDataFor( file )).dropRawData( file );
    }

    public CodeProportion getCodeProportion( CodeProportionKind metric ) {
        int violations = getViolationCount( metric );
        CodeStatistic basis = getCodeStatistic( metric.getUnit() );
        List<IHotspot> hotspots = computeHotspots( metric );
        return new CodeProportion( metric, violations, basis, hotspots );
    }

    public CodeStatistic getCodeStatistic( CodeProportionUnit unit ) {
        int basis = getNumberOf( unit );
        return new CodeStatistic( unit, basis );
    }

    private List<IHotspot> computeHotspots( CodeProportionKind metric ) {
        List<IHotspot> hotspots = new ArrayList<IHotspot>();
        addToHotspots( metric, hotspots );
        return hotspots;
    }

    public Set<ClassRawData> getAllClassRawData() {
        Set<ClassRawData> allClassRawData = new HashSet<ClassRawData>();
        for( ProjectRawData projectRD : getAllRawDataElements() ) {
            for( FileRawData fileRD : projectRD.getAllRawDataElements() ) {
                allClassRawData.addAll( fileRD.getAllRawDataElements() );
            }
        }
        return allClassRawData;
    }
}
