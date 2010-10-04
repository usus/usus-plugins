package org.projectusus.metrics;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.projectusus.core.metrics.MetricsCollector;

public class PublicFieldCollector extends MetricsCollector {

    public static final String PUBLIC_FIELDS = "public fields"; //$NON-NLS-1$
    private Counter count = new Counter();

    @Override
    public boolean visit( TypeDeclaration node ) {
        count.startNewCount();
        return super.visit( node );
    }

    @Override
    public void endVisit( TypeDeclaration node ) {
        getMetricsWriter().putData( getFile(), node, PUBLIC_FIELDS, count.getAndClearCount() );
    }

    @Override
    public boolean visit( FieldDeclaration node ) {
        int fieldModifiers = node.getModifiers();
        if( (fieldModifiers & Modifier.PUBLIC) != 0 && (fieldModifiers & (Modifier.STATIC | Modifier.FINAL)) == 0 ) {
            count.increaseLastCountBy( 1 );
        }
        return super.visit( node );
    }

}
