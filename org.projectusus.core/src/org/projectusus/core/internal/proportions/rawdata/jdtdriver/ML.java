package org.projectusus.core.internal.proportions.rawdata.jdtdriver;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.internal.proportions.rawdata.FileRawData;
import org.projectusus.core.internal.proportions.rawdata.WorkspaceRawData;

public class ML extends ASTVisitor {

    private int statementCount;

    @Override
    public boolean visit( MethodDeclaration node ) {
        init();
        return true;
    }

    @Override
    public boolean visit( Initializer node ) {
        init();
        return true;
    }

    @Override
    public void endVisit( MethodDeclaration node ) {
        FileRawData currentFileResults = WorkspaceRawData.getInstance().getCurrentProjectRawData().getCurrentFileRawData();
        currentFileResults.setMLValue( node, statementCount );
    }

    @Override
    public void endVisit( Initializer node ) {
        FileRawData currentFileResults = WorkspaceRawData.getInstance().getCurrentProjectRawData().getCurrentFileRawData();
        currentFileResults.setMLValue( node, statementCount );
    }

    @Override
    public boolean visit( Block node ) {
        statementCount += node.statements().size();
        return true;
    }

    private void init() {
        statementCount = 0;
    }
}
