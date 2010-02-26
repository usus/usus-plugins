package org.projectusus.core.internal.proportions.rawdata;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.internal.proportions.IUsusModelMetricsWriter;

import com.mountainminds.eclemma.core.analysis.IJavaElementCoverage;

@SuppressWarnings( "unused" )
public class NullUsusModelMetricsWriter implements IUsusModelMetricsWriter {

    public void addClass( IFile file, AbstractTypeDeclaration node ) {
        // just do nothing harmful
    }

    public void addClassReference( IFile file, AbstractTypeDeclaration referencingType, IJavaElement referencedElement ) {
        // just do nothing harmful
    }

    public void setCCValue( IFile file, MethodDeclaration methodDecl, int value ) {
        // just do nothing harmful
    }

    public void setCCValue( IFile file, Initializer initializer, int value ) {
        // just do nothing harmful
    }

    public void setMLValue( IFile file, MethodDeclaration methodDecl, int value ) {
        // just do nothing harmful
    }

    public void setMLValue( IFile file, Initializer initializer, int value ) {
        // just do nothing harmful
    }

    public void setInstructionCoverage( IProject project, IJavaElementCoverage coverage ) {
        // just do nothing harmful
    }

    public void resetInstructionCoverage() {
        // TODO Auto-generated method stub

    }
}
