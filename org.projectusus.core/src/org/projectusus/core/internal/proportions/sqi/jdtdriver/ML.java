package org.projectusus.core.internal.proportions.sqi.jdtdriver;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.internal.proportions.sqi.FileResults;
import org.projectusus.core.internal.proportions.sqi.WorkspaceResults;

public class ML extends ASTVisitor {

    private int statementCount;

    @Override
    public boolean visit( MethodDeclaration node ) {
        statementCount = 0;
        return true;
    }

    @Override
    public void endVisit( MethodDeclaration node ) {
        FileResults currentFileResults = WorkspaceResults.getInstance().getCurrentProjectResults().getCurrentFileResults();
        currentFileResults.setMLResult( node, statementCount );
    }

    @Override
    public boolean visit( Block node ) {
        statementCount += node.statements().size();
        return true;
    }
}
