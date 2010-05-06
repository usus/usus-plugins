package org.projectusus.core.internal.proportions.rawdata;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.filerelations.FileRelationMetrics;
import org.projectusus.core.internal.proportions.IUsusModelMetricsWriter;

import com.mountainminds.eclemma.core.analysis.IJavaModelCoverage;

@SuppressWarnings( "unused" )
public class NullUsusModelMetricsWriter implements IUsusModelMetricsWriter {

    public void addClass( IFile file, AbstractTypeDeclaration node ) {
        // just do nothing harmful
    }

    public void addClassReference( ITypeBinding sourceType, ITypeBinding targetType ) {
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

    public FileRelationMetrics getFileRelationMetrics() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setWarningsCount( IProject project, int markerCount ) {
        // TODO Auto-generated method stub

    }

    public void collectCoverageInfo( IJavaModelCoverage javaModelCoverage ) {
        // TODO Auto-generated method stub

    }
}
