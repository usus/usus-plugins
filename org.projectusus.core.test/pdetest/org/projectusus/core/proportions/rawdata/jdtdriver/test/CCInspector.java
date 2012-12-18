package org.projectusus.core.proportions.rawdata.jdtdriver.test;

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
import org.projectusus.core.metrics.MetricsCollector;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class CCInspector extends MetricsCollector {

    private Multimap<String, ASTNodeTypes> map = ArrayListMultimap.create();
    private String currentName;

    public Multimap<String, ASTNodeTypes> getMap() {
        return map;
    }

    @Override
    public boolean visit( MethodDeclaration node ) {
        currentName = node.getName().toString();
        return true;
    }

    @Override
    public boolean visit( Initializer node ) {
        currentName = "initializer";
        return true;
    }

    @Override
    public boolean visit( WhileStatement node ) {
        map.put( currentName, new WhileStatementType() );
        return true;
    }

    @Override
    public boolean visit( DoStatement node ) {
        map.put( currentName, new DoStatementType() );
        return true;
    }

    @Override
    public boolean visit( ForStatement node ) {
        map.put( currentName, new ForStatementType() );
        return true;
    }

    @Override
    public boolean visit( EnhancedForStatement node ) {
        map.put( currentName, new EnhancedForStatementType() );
        return true;
    }

    @Override
    public boolean visit( IfStatement node ) {
        map.put( currentName, new IfStatementType() );
        return true;
    }

    @Override
    public boolean visit( SwitchCase node ) {
        map.put( currentName, new SwitchCaseType() );
        return true;
    }

    @Override
    public boolean visit( CatchClause node ) {
        map.put( currentName, new CatchClauseType() );
        return true;
    }

    @Override
    public boolean visit( ConditionalExpression node ) {
        map.put( currentName, new ConditionalExpressionType() );
        return true;
    }

    @Override
    public boolean visit( InfixExpression node ) {
        Operator operator = node.getOperator();
        if( operator == Operator.CONDITIONAL_AND || operator == Operator.CONDITIONAL_OR )
            map.put( currentName, new InfixExpressionType( operator, node.extendedOperands().size() ) );
        return true;
    }
}
