package org.projectusus.core.internal.proportions.rawdata.collectors;

import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.projectusus.core.basis.MetricsResults;

public class ClassCollector extends Collector {

    public ClassCollector() {
        super();
    }

    @Override
    public boolean visit( TypeDeclaration node ) {
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
        getMetricsWriter().putData( getFile(), node, MetricsResults.CLASS_CREATION, 0 );
        return true;
    }
}
