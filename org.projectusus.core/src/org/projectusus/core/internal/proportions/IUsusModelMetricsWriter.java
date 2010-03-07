package org.projectusus.core.internal.proportions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.filerelations.FileRelationMetrics;

import com.mountainminds.eclemma.core.analysis.IJavaElementCoverage;

public interface IUsusModelMetricsWriter {

    void addClassReference( ITypeBinding sourceType, ITypeBinding targetType );

    void setCCValue( IFile file, MethodDeclaration methodDecl, int value );

    void setCCValue( IFile file, Initializer initializer, int value );

    void addClass( IFile file, AbstractTypeDeclaration node );

    void setMLValue( IFile file, MethodDeclaration methodDecl, int value );

    void setMLValue( IFile file, Initializer initializer, int value );

    void setInstructionCoverage( IProject project, IJavaElementCoverage coverage );

    void resetInstructionCoverage();

    void setYellowCount( IFile file, int markerCount );

    FileRelationMetrics getFileRelationMetrics();
}
