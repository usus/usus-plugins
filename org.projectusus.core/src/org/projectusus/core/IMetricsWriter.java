package org.projectusus.core;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public interface IMetricsWriter {

    void putData( IFile file, MethodDeclaration methodDecl, String dataKey, int value );

    void putData( IFile file, Initializer initializer, String dataKey, int value );

    void putData( IFile file, AbstractTypeDeclaration node, String dataKey, int value );
}
