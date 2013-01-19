package org.projectusus.wip.metrics;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.metrics.MetricsCollector;
import org.projectusus.metrics.Counter;

@SuppressWarnings( "unused" )
public class TrainWreckCollector extends MetricsCollector {

    private List<Integer> trainWrecksPerMethod;
    private Counter trainWrecksPerInvocation = new Counter();

    public TrainWreckCollector() {
        super();
    }

    @Override
    public boolean visit( MethodDeclaration node ) {
        initMethodCount();
        return true;
    }

    @Override
    public boolean visit( Initializer node ) {
        initMethodCount();
        return true;
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
    public boolean visit( MethodInvocation node ) {
        if( node.getExpression() instanceof MethodInvocation ) {
            increase();
        } else {
            carryOverToMethod();
            initInvocationCount();
        }
        return true;
    }

    private void submit( MethodDeclaration node ) {
        getMetricsWriter().putData( getFile(), node, MetricsResults.TRAIN_WRECKS, trainWrecksPerMethod );
    }

    private void submit( Initializer node ) {
        getMetricsWriter().putData( getFile(), node, MetricsResults.TRAIN_WRECKS, trainWrecksPerMethod );
    }

    private void increase() {
        trainWrecksPerInvocation.increaseLastCountBy( 1 );
    }

    private void carryOverToMethod() {
        int wreckCount = trainWrecksPerInvocation.getAndClearCount();
        if( wreckCount > 1 ) {
            trainWrecksPerMethod.add( Integer.valueOf( wreckCount ) );
        }
    }

    private void initMethodCount() {
        trainWrecksPerMethod = new ArrayList<Integer>();
    }

    private void initInvocationCount() {
        trainWrecksPerInvocation.startNewCount( 1 );
    }
}
