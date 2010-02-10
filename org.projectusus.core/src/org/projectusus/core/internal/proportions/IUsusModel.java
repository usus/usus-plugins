// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.model.IUsusElement;
import org.projectusus.core.internal.proportions.modelupdate.IUsusModelHistory;
import org.projectusus.core.internal.proportions.rawdata.CodeProportionKind;
import org.projectusus.core.internal.proportions.rawdata.CodeProportionUnit;
import org.projectusus.core.internal.proportions.rawdata.IClassRawData;
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

    IProjectRawData getProjectRawData( IProject project );

    void resetRawData( IProject project );

    List<Integer> getAllClassesCCDResults();

    int getOverallMetric( CodeProportionKind metric );

    int getSumOfAllDirectChildrenOfAllClasses();

    int getSumOfAllKnownChildrenOfAllClasses();

    IClassRawData getClassRawData( IType clazz ) throws JavaModelException;

    int getNumberOf( IProject project, CodeProportionUnit unit );

    int getOverallMetric( IProject project, CodeProportionKind metric );
}
