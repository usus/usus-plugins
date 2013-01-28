package org.projectusus.metrics;

import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.metrics.MetricsCollector;

public class ClassCollector extends MetricsCollector {

    @Override
    public boolean visit( TypeDeclaration node ) {
        init( node );
        return true;
    }

    @Override
    public boolean visit( AnnotationTypeDeclaration node ) {
        init( node );
        return true;
    }

    @Override
    public boolean visit( EnumDeclaration node ) {
        init( node );
        return true;
    }

    public void init( AbstractTypeDeclaration node ) {
        getMetricsWriter().putData( getFile(), node, MetricsResults.CLASS_CREATION, 0 );
    }
}
