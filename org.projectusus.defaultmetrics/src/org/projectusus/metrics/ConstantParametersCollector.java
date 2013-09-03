package org.projectusus.metrics;

import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.metrics.MetricsCollector;

public class ConstantParametersCollector extends MetricsCollector {

    private Counter constantParametersCount = new Counter();

    @Override
    public boolean visit( MethodDeclaration node ) {
        init( node );
        return true;
    }

    @Override
    public boolean visit( Initializer node ) {
        init( node );
        return true;
    }

    @Override
    public void endVisit( MethodDeclaration node ) {
        commit( node );
    }

    @Override
    public void endVisit( Initializer node ) {
        commit( node );
    }

    @Override
    public boolean visit( MethodInvocation node ) {
        for( Object argument : node.arguments() ) {
            // should the method have more than 1 arguments?
            if( argument instanceof BooleanLiteral || argument instanceof NullLiteral )
                foundConstantParameter();
        }
        return true;
    }

    public void foundConstantParameter() {
        constantParametersCount.increaseLastCountBy( 1 );
    }

    public void init( @SuppressWarnings( "unused" ) MethodDeclaration node ) {
        constantParametersCount.startNewCount();
    }

    public void init( @SuppressWarnings( "unused" ) Initializer node ) {
        constantParametersCount.startNewCount();
    }

    public void commit( MethodDeclaration node ) {
        int count = constantParametersCount.getAndClearCount();
        getMetricsWriter().putData( getFile(), node, MetricsResults.CP, count );
    }

    public void commit( Initializer node ) {
        int count = constantParametersCount.getAndClearCount();
        getMetricsWriter().putData( getFile(), node, MetricsResults.CP, count );
    }
}
