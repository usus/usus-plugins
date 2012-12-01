package org.projectusus.metrics;

import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.metrics.MetricsCollector;

public class AbstractClassCollector extends MetricsCollector {

    @Override
    public boolean visit( TypeDeclaration node ) {
        return isAbstract( node ) ? markAsAbstract( node ) : markAsConcrete( node );
    }

    @Override
    public boolean visit( AnnotationTypeDeclaration node ) {
        return markAsConcrete( node );
    }

    @Override
    public boolean visit( EnumDeclaration node ) {
        return markAsConcrete( node );
    }

    private boolean isAbstract( TypeDeclaration node ) {
        return node.isInterface() || Modifier.isAbstract( node.getModifiers() );
    }

    private boolean markAsAbstract( TypeDeclaration node ) {
        return writeAbstractness( node, 1 );
    }

    private boolean markAsConcrete( AbstractTypeDeclaration node ) {
        return writeAbstractness( node, 0 );
    }

    private boolean writeAbstractness( AbstractTypeDeclaration node, int value ) {
        getMetricsWriter().putData( getFile(), node, MetricsResults.ABSTRACTNESS, value );
        return true;
    }

}
