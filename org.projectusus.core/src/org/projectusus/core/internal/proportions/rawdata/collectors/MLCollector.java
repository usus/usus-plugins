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
    public boolean visit( @SuppressWarnings( "unused" ) MethodDeclaration node ) {
        statementCount.startNewCount();
        return true;
    }

    @Override
    public boolean visit( @SuppressWarnings( "unused" ) Initializer node ) {
        statementCount.startNewCount();
        return true;
    }

    @Override
    public void endVisit( MethodDeclaration node ) {
        int count = statementCount.getAndClearCount();
        UsusCorePlugin.getUsusModelMetricsWriter().setMLValue( file, node, count );
    }

    @Override
    public void endVisit( Initializer node ) {
        int count = statementCount.getAndClearCount();
        UsusCorePlugin.getUsusModelMetricsWriter().setMLValue( file, node, count );
    }

    @Override
    public boolean visit( Block node ) {
        statementCount.increaseLastCountBy( node.statements().size() );
        return true;
    }
}
