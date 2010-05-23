package org.projectusus.core.internal.proportions.rawdata;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.filerelations.model.BoundType;
import org.projectusus.core.internal.proportions.IMetricsWriter;

@SuppressWarnings( "unused" )
public class NullMetricsWriter implements IMetricsWriter {

    public void addClass( IFile file, AbstractTypeDeclaration node ) {
        // just do nothing harmful
    }

    public void addClassReference( BoundType sourceType, BoundType targetType ) {
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

    public void resetInstructionCoverage() {
        // TODO Auto-generated method stub
    }

    public void setWarningsCount( IFile file, int markerCount ) {
        // TODO Auto-generated method stub
    }

    public void setWarningsCount( IProject project, int markerCount ) {
        // TODO Auto-generated method stub
    }

}
