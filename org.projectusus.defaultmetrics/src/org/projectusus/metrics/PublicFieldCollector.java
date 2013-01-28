package org.projectusus.metrics;

import static org.projectusus.core.basis.MetricsResults.PUBLIC_FIELDS;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.projectusus.core.metrics.MetricsCollector;

public class PublicFieldCollector extends MetricsCollector {

    private Counter count = new Counter();

    @Override
    public boolean visit( TypeDeclaration node ) {
        init( node );
        return true;
    }

    @Override
    public void endVisit( TypeDeclaration node ) {
        commit( node );
    }

    @Override
    public boolean visit( FieldDeclaration node ) {
        calculate( node.getModifiers() );
        return true;
    }

    public void init( @SuppressWarnings( "unused" ) TypeDeclaration node ) {
        count.startNewCount();
    }

    public void calculate( int fieldModifiers ) {
        if( isPublic( fieldModifiers ) && isNotStaticFinal( fieldModifiers ) ) {
            count.increaseLastCountBy( 1 );
        }
    }

    public void commit( TypeDeclaration node ) {
        getMetricsWriter().putData( getFile(), node, PUBLIC_FIELDS, count.getAndClearCount() );
    }

    private boolean isNotStaticFinal( int modifiers ) {
        return (modifiers & Modifier.STATIC) == 0 || (modifiers & Modifier.FINAL) == 0;
    }

    private boolean isPublic( int modifiers ) {
        return (modifiers & Modifier.PUBLIC) != 0;
    }
}
