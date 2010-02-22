package org.projectusus.core.internal.proportions.rawdata.collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.projectusus.core.internal.UsusCorePlugin;

public class ClassCollector extends Collector {

    public ClassCollector( IFile file ) {
        super( file );
    }

    @Override
    public boolean visit( TypeDeclaration node ) {
        if( node.isInterface() ) {
            return false;
        }
        return addAbstractTypeDeclaration( node );
    }

    @Override
    public boolean visit( AnnotationTypeDeclaration node ) {
        return addAbstractTypeDeclaration( node );
    }

    @Override
    public boolean visit( EnumDeclaration node ) {
        return addAbstractTypeDeclaration( node );
    }

    private boolean addAbstractTypeDeclaration( AbstractTypeDeclaration node ) {
        UsusCorePlugin.getUsusModelWriteAccess().addClass( file, node );
        return true;
    }
}
