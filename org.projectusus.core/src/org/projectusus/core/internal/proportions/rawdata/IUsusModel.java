// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.projectusus.core.IUsusElement;
import org.projectusus.core.IUsusModelListener;
import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.CodeProportionKind;
import org.projectusus.core.basis.CodeProportionUnit;
import org.projectusus.core.internal.proportions.modelupdate.checkpoints.CheckpointHistory;

public interface IUsusModel {

    IUsusElement[] getElements();

    CheckpointHistory getHistory();

    void addUsusModelListener( IUsusModelListener listener );

    void removeUsusModelListener( IUsusModelListener listener );

    CodeProportion getCodeProportion( CodeProportionKind metric );

    int getViolationCount( IProject project, CodeProportionKind metric );

    int getNumberOf( CodeProportionUnit unit );

    int getNumberOf( IProject project, CodeProportionUnit unit );

    int getNumberOfMethods( IType type ) throws JavaModelException;

    int getOverallMetric( CodeProportionKind metric );

    int getOverallMetric( IProject project, CodeProportionKind metric );

    Set<ClassRepresenter> getAllClassRepresenters();

    Set<PackageRepresenter> getAllPackages();

    int getCCValue( IMethod method ) throws JavaModelException;

    int getMLValue( IMethod method ) throws JavaModelException;

    int getNumberOfWarnings( IFile file ) throws JavaModelException;

    int getNumberOfProjectsViolatingCW();

    boolean needsFullRecompute();

    double getRelativeACD();

    YellowCountResult getWarnings();
}
