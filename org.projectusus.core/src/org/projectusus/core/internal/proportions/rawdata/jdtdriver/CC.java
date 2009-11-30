package org.projectusus.core.internal.proportions.rawdata.jdtdriver;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.core.dom.InfixExpression.Operator;
import org.projectusus.core.internal.proportions.rawdata.FileRawData;
import org.projectusus.core.internal.proportions.rawdata.WorkspaceRawData;

public class CC extends ASTVisitor {

    private int ccCount;

    @Override
    public boolean visit( MethodDeclaration node ) {
        return init();
    }

    @Override
    public boolean visit( Initializer node ) {
        return init();
    }

    @Override
    public void endVisit( MethodDeclaration node ) {
        submit( node );
    }

    @Override
    public void endVisit( Initializer node ) {
        submit( node );
    }

    @Override
    public boolean visit( WhileStatement node ) {
        return increase();
    }

    @Override
    public boolean visit( DoStatement node ) {
        return increase();
    }

    @Override
    public boolean visit( ForStatement node ) {
        return increase();
    }

    @Override
    public boolean visit( EnhancedForStatement node ) {
        return increase();
    }

    @Override
    public boolean visit( IfStatement node ) {
        return increase();
    }

    @Override
    public boolean visit( SwitchCase node ) {
        return increase();
    }

    @Override
    public boolean visit( CatchClause node ) {
        return increase();
    }

    @Override
    public boolean visit( ConditionalExpression node ) {
        return increase();
    }

    @Override
    public boolean visit( InfixExpression node ) {
        Operator operator = node.getOperator();
        if( operator.equals( Operator.CONDITIONAL_AND ) || operator.equals( Operator.CONDITIONAL_OR ) ) {
            return increase();
        }
        return true;
    }

    private void submit( MethodDeclaration node ) {
        FileRawData currentFileResults = WorkspaceRawData.getInstance().getCurrentProjectRawData().getCurrentFileRawData();
        currentFileResults.setCCValue( node, ccCount );
    }

    private void submit( Initializer node ) {
        FileRawData currentFileResults = WorkspaceRawData.getInstance().getCurrentProjectRawData().getCurrentFileRawData();
        currentFileResults.setCCValue( node, ccCount );
    }

    private boolean increase() {
        ccCount++;
        return true;
    }

    private boolean init() {
        ccCount = 0;
        return true;
    }

}
