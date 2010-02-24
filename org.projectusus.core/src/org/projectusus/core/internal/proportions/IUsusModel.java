// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.model.IUsusElement;
import org.projectusus.core.internal.proportions.modelupdate.IUsusModelHistory;
import org.projectusus.core.internal.proportions.rawdata.ClassRepresenter;
import org.projectusus.core.internal.proportions.rawdata.CodeProportionKind;
import org.projectusus.core.internal.proportions.rawdata.CodeProportionUnit;

public interface IUsusModel {

    IUsusElement[] getElements();

    IUsusModelHistory getHistory();

    void addUsusModelListener( IUsusModelListener listener );

    void removeUsusModelListener( IUsusModelListener listener );

    CodeProportion getCodeProportion( CodeProportionKind metric );

    int getViolationCount( IProject project, CodeProportionKind metric );

    int getNumberOf( CodeProportionUnit unit );

    int getNumberOf( IProject project, CodeProportionUnit unit );

    int getNumberOfMethods( IType type ) throws JavaModelException;

    List<Integer> getAllClassesCCDResults();

    int getOverallMetric( CodeProportionKind metric );

    int getOverallMetric( IProject project, CodeProportionKind metric );

    int getSumOfAllDirectChildrenOfAllClasses();

    int getSumOfAllKnownChildrenOfAllClasses();

    Set<ClassRepresenter> getAllClassRepresenters();

    int getCCValue( IMethod method ) throws JavaModelException;

    int getMLValue( IMethod method ) throws JavaModelException;
}
