package org.projectusus.core.internal.proportions.rawdata;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.internal.proportions.IUsusModelWriteAccess;
import org.projectusus.core.internal.proportions.modelupdate.IUsusModelUpdate;

public class NullUsusModelWriteAccess implements IUsusModelWriteAccess {

    public void addClass( IFile file, AbstractTypeDeclaration node ) {
    }

    public void addClassReference( IFile file, AbstractTypeDeclaration referencingType, IJavaElement referencedElement ) {
    }

    public void dropRawData( IFile file ) {
    }

    public void dropRawData( IProject project ) {
    }

    public void resetRawData( IProject project ) {
    }

    public void setCCValue( IFile file, MethodDeclaration methodDecl, int value ) {
    }

    public void setCCValue( IFile file, Initializer initializer, int value ) {
    }

    public void setMLValue( IFile file, MethodDeclaration methodDecl, int value ) {
    }

    public void setMLValue( IFile file, Initializer initializer, int value ) {
    }

    public void update( IUsusModelUpdate updateCommand ) {
    }
}
