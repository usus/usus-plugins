package org.projectusus.core.internal.proportions.rawdata.collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.internal.UsusCorePlugin;

public class MLCollector extends Collector {

    private Counter statementCount = new Counter();

    public MLCollector( IFile file ) {
        super( file );
    }

    @Override
    public boolean visit( MethodDeclaration node ) {
        statementCount.initCount();
        return true;
    }

    @Override
    public boolean visit( Initializer node ) {
        statementCount.initCount();
        return true;
    }

    @Override
    public void endVisit( MethodDeclaration node ) {
        UsusCorePlugin.getUsusModelWriteAccess().setMLValue( file, node, statementCount.getAndClearCount() );
    }

    @Override
    public void endVisit( Initializer node ) {
        UsusCorePlugin.getUsusModelWriteAccess().setMLValue( file, node, statementCount.getAndClearCount() );
    }

    @Override
    public boolean visit( Block node ) {
        statementCount.increaseLastCountBy( node.statements().size() );
        return true;
    }
}
