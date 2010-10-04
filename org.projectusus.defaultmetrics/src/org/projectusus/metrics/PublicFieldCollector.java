package org.projectusus.metrics;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.projectusus.core.metrics.MetricsCollector;

public class PublicFieldCollector extends MetricsCollector {

    public static final String PUBLIC_FIELDS = "public fields";
    private int count;

    @Override
    public boolean visit( TypeDeclaration node ) {
        count = 0;
        return super.visit( node );
    }

    @Override
    public void endVisit( TypeDeclaration node ) {
        getMetricsWriter().putData( getFile(), node, PUBLIC_FIELDS, count );
    }

    @Override
    public boolean visit( FieldDeclaration node ) {
        int fieldModifiers = node.getModifiers();
        if( (fieldModifiers & Modifier.PUBLIC) != 0 ) {
            count++;
        }
        return super.visit( node );
    }

}
