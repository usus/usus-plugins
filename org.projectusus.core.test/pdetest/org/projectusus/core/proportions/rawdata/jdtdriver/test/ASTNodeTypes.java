package org.projectusus.core.proportions.rawdata.jdtdriver.test;

import org.eclipse.jdt.core.dom.InfixExpression.Operator;

public class ASTNodeTypes {
    public Operator getOperator() {
        return null;
    }

    public int getExtendedOperatorCount() {
        return 0;
    }
}

class WhileStatementType extends ASTNodeTypes {
}

class DoStatementType extends ASTNodeTypes {
}

class ForStatementType extends ASTNodeTypes {
}

class EnhancedForStatementType extends ASTNodeTypes {
}

class IfStatementType extends ASTNodeTypes {
}

class SwitchCaseType extends ASTNodeTypes {
}

class CatchClauseType extends ASTNodeTypes {
}

class ConditionalExpressionType extends ASTNodeTypes {
}

class InfixExpressionType extends ASTNodeTypes {
    private Operator op;
    private int count;

    public InfixExpressionType( Operator op, int count ) {
        this.op = op;
        this.count = count;
    }

    @Override
    public Operator getOperator() {
        return op;
    }

    @Override
    public int getExtendedOperatorCount() {
        return count;
    }
};
