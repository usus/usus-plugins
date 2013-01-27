package org.projectusus.metrics;

import java.util.List;

import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
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
        calculate( node.statements().size() );
        return true;
    }

    @Override
    public boolean visit( IfStatement node ) {
        calculate( countNonBlock( node.getThenStatement() ) + countNonBlock( node.getElseStatement() ) );
        return true;
    }

    @Override
    public boolean visit( EnhancedForStatement node ) {
        calculate( countNonBlock( node.getBody() ) );
        return true;
    }

    @Override
    public boolean visit( ForStatement node ) {
        calculate( countNonBlock( node.getBody() ) );
        return true;
    }

    @Override
    public boolean visit( DoStatement node ) {
        calculate( countNonBlock( node.getBody() ) );
        return true;
    }

    @Override
    public boolean visit( WhileStatement node ) {
        calculate( countNonBlock( node.getBody() ) );
        return true;
    }

    @Override
    public boolean visit( SwitchStatement node ) {
        calculate( countNonBlocks( node.statements() ) );
        return true;
    }

    private int countNonBlocks( List<?> statements ) {
        int count = 0;
        for( Object stmt : statements ) {
            count += countNonBlock( stmt );
        }
        return count;
    }

    private int countNonBlock( Object stmt ) {
        return (stmt instanceof Block) ? 0 : 1;
    }

    protected void init( @SuppressWarnings( "unused" ) MethodDeclaration node ) {
        statementCount.startNewCount();
    }

    protected void init( @SuppressWarnings( "unused" ) Initializer node ) {
        statementCount.startNewCount();
    }

    protected void calculate( int count ) {
        statementCount.increaseLastCountBy( count );
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
