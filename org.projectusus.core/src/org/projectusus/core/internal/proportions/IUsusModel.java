// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.model.IUsusElement;
import org.projectusus.core.internal.proportions.modelupdate.IUsusModelHistory;
import org.projectusus.core.internal.proportions.rawdata.ClassRawData;
import org.projectusus.core.internal.proportions.rawdata.CodeProportionKind;
import org.projectusus.core.internal.proportions.rawdata.CodeProportionUnit;
import org.projectusus.core.internal.proportions.rawdata.IProjectRawData;

public interface IUsusModel {

    IUsusElement[] getElements();

    IUsusModelHistory getHistory();

    void addUsusModelListener( IUsusModelListener listener );

    void removeUsusModelListener( IUsusModelListener listener );

    CodeProportion getCodeProportion( CodeProportionKind metric );

    int getNumberOf( CodeProportionUnit unit );

    void dropRawData( IFile file );

    void dropRawData( IProject project );

    Set<ClassRawData> getAllClassRawData();

    IProjectRawData getProjectRawData( IProject project );

    void resetRawData( IProject project );

    int getAllClassRawDataCount();

    List<Integer> getAllClassesCCDResults();

    int getOverallMetric( CodeProportionKind metric );
}
