package org.projectusus.core.internal.proportions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import com.mountainminds.eclemma.core.analysis.IJavaElementCoverage;

public interface IUsusModelMetricsWriter {

    void addClassReference( IFile file, AbstractTypeDeclaration referencingType, IJavaElement referencedElement );

    void setCCValue( IFile file, MethodDeclaration methodDecl, int value );

    void setCCValue( IFile file, Initializer initializer, int value );

    void addClass( IFile file, AbstractTypeDeclaration node );

    void setMLValue( IFile file, MethodDeclaration methodDecl, int value );

    void setMLValue( IFile file, Initializer initializer, int value );

    void setInstructionCoverage( IProject project, IJavaElementCoverage coverage );

    void resetInstructionCoverage();

}
