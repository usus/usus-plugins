// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.CodeProportionKind;
import org.projectusus.core.basis.CodeProportionUnit;
import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.basis.IHotspot;

class WorkspaceRawData extends RawData<IProject, ProjectRawData> {

    public WorkspaceRawData() {
        super();
    }

    ProjectRawData getProjectRawData( IProject project ) {
        ProjectRawData rawData = super.getRawData( project );
        if( rawData == null ) {
            rawData = new ProjectRawData();
            super.addRawData( project, rawData );
        }
        return rawData;
    }

    public void dropRawData( IProject project ) {
        getProjectRawData( project ).dropRawData();
        remove( project );
    }

    public CodeProportion getCodeProportion( CodeProportionKind metric ) {
        int violations;
        CodeStatistic basis;
        List<IHotspot> hotspots;
        violations = getViolationCount( metric );
        basis = getCodeStatistic( metric.getUnit() );
        hotspots = computeHotspots( metric );
        return new CodeProportion( metric, violations, basis, hotspots );
    }

    // TODO CodeStatistic cachen?
    CodeStatistic getCodeStatistic( CodeProportionUnit unit ) {
        int basis = getNumberOf( unit );
        return new CodeStatistic( unit, basis );
    }

    List<IHotspot> computeHotspots( CodeProportionKind metric ) {
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
