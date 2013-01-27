package org.projectusus.metrics;

import java.util.List;

import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.metrics.MetricsCollector;

public class MLCollector extends MetricsCollector {

    private Counter statementCount = new Counter();

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
    public boolean visit( Block node ) {
        calculate( node.statements() );
        return true;
    }

    protected void init( @SuppressWarnings( "unused" ) MethodDeclaration node ) {
        statementCount.startNewCount();
    }

    protected void init( @SuppressWarnings( "unused" ) Initializer node ) {
        statementCount.startNewCount();
    }

    protected void calculate( List<?> statements ) {
        statementCount.increaseLastCountBy( statements.size() );
    }

    protected void commit( MethodDeclaration node ) {
        int count = statementCount.getAndClearCount();
        getMetricsWriter().putData( getFile(), node, MetricsResults.ML, count );
    }

    protected void commit( Initializer node ) {
        int count = statementCount.getAndClearCount();
        getMetricsWriter().putData( getFile(), node, MetricsResults.ML, count );
    }
}
