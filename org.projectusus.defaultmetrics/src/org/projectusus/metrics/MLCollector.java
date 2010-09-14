package org.projectusus.metrics;

import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.metrics.MetricsCollector;

public class MLCollector extends MetricsCollector {

    private Counter statementCount = new Counter();

    public MLCollector() {
        super();
    }

    @Override
    public boolean visit( @SuppressWarnings( "unused" ) MethodDeclaration node ) {
        statementCount.startNewCount();
        return true;
    }

    @Override
    public boolean visit( @SuppressWarnings( "unused" ) Initializer node ) {
        statementCount.startNewCount();
        return true;
    }

    @Override
    public void endVisit( MethodDeclaration node ) {
        int count = statementCount.getAndClearCount();
        getMetricsWriter().putData( getFile(), node, MetricsResults.ML, count );
    }

    @Override
    public void endVisit( Initializer node ) {
        int count = statementCount.getAndClearCount();
        getMetricsWriter().putData( getFile(), node, MetricsResults.ML, count );
    }

    @Override
    public boolean visit( Block node ) {
        statementCount.increaseLastCountBy( node.statements().size() );
        return true;
    }
}
