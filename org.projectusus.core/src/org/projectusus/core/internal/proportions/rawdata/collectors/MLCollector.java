package org.projectusus.core.internal.proportions.rawdata.collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class MLCollector extends Collector {

    private int statementCount;

    public MLCollector( IFile file ) {
        super( file );
    }

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
        getFileRawData().setMLValue( node, statementCount );
    }

    @Override
    public void endVisit( Initializer node ) {
        getFileRawData().setMLValue( node, statementCount );
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
