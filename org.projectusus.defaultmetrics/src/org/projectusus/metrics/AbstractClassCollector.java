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
        if( isAbstract( node ) )
            markAsAbstract( node );
        else
            markAsConcrete( node );
        return true;
    }

    @Override
    public boolean visit( AnnotationTypeDeclaration node ) {
        markAsConcrete( node );
        return true;
    }

    @Override
    public boolean visit( EnumDeclaration node ) {
        markAsConcrete( node );
        return true;
    }

    public void markAsAbstract( TypeDeclaration node ) {
        writeAbstractness( node, 1 );
    }

    public void markAsConcrete( AbstractTypeDeclaration node ) {
        writeAbstractness( node, 0 );
    }

    private boolean isAbstract( TypeDeclaration node ) {
        return node.isInterface() || Modifier.isAbstract( node.getModifiers() );
    }

    private void writeAbstractness( AbstractTypeDeclaration node, int value ) {
        getMetricsWriter().putData( getFile(), node, MetricsResults.ABSTRACTNESS, value );
    }

}
