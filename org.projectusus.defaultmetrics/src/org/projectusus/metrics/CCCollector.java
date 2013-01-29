package org.projectusus.metrics;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InfixExpression.Operator;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.metrics.MetricsCollector;

@SuppressWarnings( "unused" )
public class CCCollector extends MetricsCollector {

    private Counter ccCount = new Counter();

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
    public boolean visit( WhileStatement node ) {
        calculate( node, 1 );
        return true;
    }

    @Override
    public boolean visit( DoStatement node ) {
        calculate( node, 1 );
        return true;
    }

    @Override
    public boolean visit( ForStatement node ) {
        calculate( node, 1 );
        return true;
    }

    @Override
    public boolean visit( EnhancedForStatement node ) {
        calculate( node, 1 );
        return true;
    }

    @Override
    public boolean visit( IfStatement node ) {
        calculate( node, 1 );
        return true;
    }

    @Override
    public boolean visit( SwitchCase node ) {
        calculate( node, 1 );
        return true;
    }

    @Override
    public boolean visit( CatchClause node ) {
        calculate( node, 1 );
        return true;
    }

    @Override
    public boolean visit( ConditionalExpression node ) {
        calculate( node, 1 );
        return true;
    }

    @Override
    public boolean visit( InfixExpression node ) {
        Operator operator = node.getOperator();
        if( operator.equals( Operator.CONDITIONAL_AND ) || operator.equals( Operator.CONDITIONAL_OR ) ) {
            calculate( node, 1 + node.extendedOperands().size() );
        }
        return true;
    }

    public void init( MethodDeclaration node ) {
        ccCount.startNewCount( 1 );
    }

    public void init( Initializer node ) {
        ccCount.startNewCount( 1 );
    }

    public void calculate( ASTNode node, int amount ) {
        ccCount.increaseLastCountBy( amount );
    }

    public void commit( MethodDeclaration node ) {
        getMetricsWriter().putData( getFile(), node, MetricsResults.CC, ccCount.getAndClearCount() );
    }

    public void commit( Initializer node ) {
        getMetricsWriter().putData( getFile(), node, MetricsResults.CC, ccCount.getAndClearCount() );
    }

}
