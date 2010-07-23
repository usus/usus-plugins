package org.projectusus.core.internal.proportions.rawdata;

import org.eclipse.core.resources.IFile;
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

    public void putData( IFile file, MethodDeclaration methodDecl, String dataKey, int value ) {
        // TODO Auto-generated method stub

    }

    public void putData( IFile file, Initializer initializer, String dataKey, int value ) {
        // TODO Auto-generated method stub

    }

    public void putData( IFile file, AbstractTypeDeclaration node, String dataKey, int value ) {
    }

}
