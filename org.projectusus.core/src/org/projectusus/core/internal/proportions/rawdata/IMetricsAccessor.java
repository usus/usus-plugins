package org.projectusus.core.internal.proportions.rawdata;

import java.util.ArrayList;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.CodeProportionKind;
import org.projectusus.core.basis.CodeProportionUnit;
import org.projectusus.core.filerelations.FileRelationMetrics;

public interface IMetricsAccessor {

    int getNumberOf( CodeProportionUnit unit );

    int getNumberOf( IProject project, CodeProportionUnit unit );

    int getNumberOfMethods( IType type );

    int getOverallMetric( CodeProportionKind metric );

    int getOverallMetric( IProject project, CodeProportionKind metric );

    int getCCD( IType type );

    int getViolationCount( IProject project, CodeProportionKind metric );

    Set<ClassRepresenter> getAllClassRepresenters();

    Set<PackageRepresenter> getAllPackages();

    int getCCValue( IMethod method );

    int getMLValue( IMethod method );

    double getRelativeACD();

    int getNumberOfWarnings( IFile file );

    YellowCountResult getWarnings();

    ArrayList<CodeProportion> getCodeProportions();

    int getNumberOfProjectsViolatingCW();

    FileRelationMetrics getFileRelationMetrics();

}
