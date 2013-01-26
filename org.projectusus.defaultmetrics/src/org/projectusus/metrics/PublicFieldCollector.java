package org.projectusus.metrics;

import static org.projectusus.core.basis.MetricsResults.PUBLIC_FIELDS;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.projectusus.core.metrics.MetricsCollector;

public class PublicFieldCollector extends MetricsCollector {

    private Counter count = new Counter();

    @Override
    public boolean visit( @SuppressWarnings( "unused" ) TypeDeclaration node ) {
        count.startNewCount();
        return true;
    }

    @Override
    public void endVisit( TypeDeclaration node ) {
        getMetricsWriter().putData( getFile(), node, PUBLIC_FIELDS, count.getAndClearCount() );
    }

    @Override
    public boolean visit( FieldDeclaration node ) {
        int fieldModifiers = node.getModifiers();
        if( isPublic( fieldModifiers ) && isNotStaticFinal( fieldModifiers ) ) {
            count.increaseLastCountBy( 1 );
        }
        return true;
    }

    private boolean isNotStaticFinal( int modifiers ) {
        return (modifiers & Modifier.STATIC) == 0 || (modifiers & Modifier.FINAL) == 0;
    }

    private boolean isPublic( int modifiers ) {
        return (modifiers & Modifier.PUBLIC) != 0;
    }
}
