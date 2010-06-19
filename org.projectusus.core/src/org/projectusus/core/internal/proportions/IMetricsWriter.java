package org.projectusus.core.internal.proportions;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.filerelations.model.BoundType;

public interface IMetricsWriter {

    void addClassReference( BoundType sourceType, BoundType targetType );

    void setCCValue( IFile file, MethodDeclaration methodDecl, int value );

    void setCCValue( IFile file, Initializer initializer, int value );

    void addClass( IFile file, AbstractTypeDeclaration node );

    void setMLValue( IFile file, MethodDeclaration methodDecl, int value );

    void setMLValue( IFile file, Initializer initializer, int value );
}
